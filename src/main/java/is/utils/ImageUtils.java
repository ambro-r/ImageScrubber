package is.utils;

import is.objects.Image;
import is.objects.ImageMetaData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {

  private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

  private ImageUtils() {}

  public static boolean sameSource(Image image01, Image image02) {
    boolean sameSource;
    try {
      sameSource = ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_MAKE);
      sameSource = sameSource && ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_MODEL);
      sameSource = sameSource && ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_DATETIME);
      sameSource = sameSource && ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_DATETIME_DIGITIZED);
    } catch (Exception e) {
      sameSource = Boolean.FALSE;
      LOG.error("Exception occurred : {}", e.getMessage());
    }
    return sameSource;
  }

  public static boolean isCustomRendered(Image image) {
    boolean isCustomRendered = Boolean.FALSE;
    try {
      String value = image.getImageMetaData().getAttribute(ImageMetaData.ATTRIBUTE_CUSTOM_RENDERED);
      if("1".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)) {
        isCustomRendered = Boolean.TRUE;
      }
    } catch (Exception e) {
      LOG.error("Exception occurred : {}", e.getMessage());
    }
    return isCustomRendered;
  }

  private static BufferedImage rotate(Image image) throws ImageReadException, IOException {
    AffineTransform affineTransform = getExifTransformation(image);
    AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BICUBIC);
    BufferedImage originalImage = Imaging.getBufferedImage(image.getImageFile());
    BufferedImage destinationImage = affineTransformOp.createCompatibleDestImage(originalImage, (originalImage.getType() == BufferedImage.TYPE_BYTE_GRAY) ? originalImage.getColorModel() : null );
    Graphics2D g = destinationImage.createGraphics();
    g.setBackground(Color.WHITE);
    g.clearRect(0, 0, destinationImage.getWidth(), destinationImage.getHeight());
    return affineTransformOp.filter(originalImage, destinationImage);
  }

  private static AffineTransform getExifTransformation(Image image) {
    AffineTransform t = new AffineTransform();
    switch (image.getOrientation()) {
      case 1:
        break;
      case 2: // Flip X
        t.scale(-1.0, 1.0);
        t.translate(-image.getWidth(), 0);
        break;
      case 3: // PI rotation
        t.translate(image.getWidth(), image.getLength());
        t.rotate(Math.PI);
        break;
      case 4: // Flip Y
        t.scale(1.0, -1.0);
        t.translate(0, -image.getLength());
        break;
      case 5: // - PI/2 and Flip X
        t.rotate(-Math.PI / 2);
        t.scale(-1.0, 1.0);
        break;
      case 6: // -PI/2 and -width
        t.translate(image.getLength(), 0);
        t.rotate(Math.PI / 2);
        break;
      case 7: // PI/2 and Flip
        t.scale(-1.0, 1.0);
        t.translate(-image.getLength(), 0);
        t.translate(0, image.getWidth());
        t.rotate(  3 * Math.PI / 2);
        break;
      case 8: // PI / 2
        t.translate(0, image.getWidth());
        t.rotate(  3 * Math.PI / 2);
        break;
    }
    return t;
  }

  public static double getDifferencePercent(Image image01, Image image02) {
    double difference = 100.00;
    try {
      BufferedImage bufferedImage01 = rotate(image01);
      BufferedImage bufferedImage02 = rotate(image02);

      int width01 = bufferedImage01.getWidth();
      int height01 = bufferedImage01.getHeight();
      LOG.debug("{}: width = {}; height = {}.", image01.getFileName(), width01, height01);

      int width02 = bufferedImage02.getWidth();
      int height02 = bufferedImage02.getHeight();
      LOG.debug("{}: width = {}; height = {}.", image02.getFileName(), width02, height02);

      if (width01 != width02 || height01 != height02) {
        LOG.debug("Images {} and {} do not have the same dimensions", image01.getFileName(), image02.getFileName());
      } else {
        long diff = 0;
        for (int y = 0; y < height01; y++) {
          for (int x = 0; x < width01; x++) {
            diff += pixelDiff(bufferedImage01.getRGB(x, y), bufferedImage02.getRGB(x, y));
          }
        }
        long maxDiff = 3L * 255 * width01 * height01;
        difference = 100.00 * diff / maxDiff;
      }
    } catch (Exception ioe) {
      LOG.error("Exception occurred : {}", ioe.getMessage());
    }
    LOG.info("Difference between {} and {} is {}%.", image01.getFileName(), image02.getFileName(), difference);
    return difference;
  }

  // https://oscarliang.com/what-s-the-use-of-and-0xff-in-programming-c-plus-p/
  // https://rosettacode.org/wiki/Percentage_difference_between_images#Java
  private static int pixelDiff(int rgb1, int rgb2) {
    int r1 = (rgb1 >> 16) & 0xff;
    int g1 = (rgb1 >> 8) & 0xff;
    int b1 = rgb1 & 0xff;
    int r2 = (rgb2 >> 16) & 0xff;
    int g2 = (rgb2 >> 8) & 0xff;
    int b2 = rgb2 & 0xff;
    return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
  }

}
