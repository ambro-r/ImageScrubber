package is.utils;

import is.objects.Image;
import is.objects.ImageMetaData;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.Imaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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


  public static double getDifferencePercent(File imageFile01, File imageFile02) {
    try {
      BufferedImage image01 = Imaging.getBufferedImage(imageFile01);
      BufferedImage image02 = Imaging.getBufferedImage(imageFile02);

      int width01 = image01.getWidth();
      int height01 = image01.getHeight();
      LOG.debug("{}: width = {}; height = {}.", imageFile01.getName(), width01, height01);

      int width02 = image02.getWidth();
      int height02 = image02.getHeight();
      LOG.debug("{}: width = {}; height = {}.", imageFile02.getName(), width02, height02);

      if (width01 != width02 || height01 != height02) {
        LOG.info("Images supplied do not have the same dimensions");
      } else {
        long diff = 0;
        for (int y = 0; y < height01; y++) {
          for (int x = 0; x < width01; x++) {
            diff += pixelDiff(image01.getRGB(x, y), image02.getRGB(x, y));
          }
        }
        long maxDiff = 3L * 255 * width01 * height01;
        return 100.0 * diff / maxDiff;
      }
    } catch (Exception ioe) {
      LOG.error("Exception occurred : {}", ioe.getMessage());
    }
    return -1;
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
