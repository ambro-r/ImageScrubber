package is.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

  private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

  private ImageUtils() {}

  public static double getDifferencePercent(File imageFile01, File imageFile02) {
    try {
      BufferedImage image01 = ImageIO.read(imageFile01);
      BufferedImage image02 = ImageIO.read(imageFile02);

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
    } catch (IOException ioe) {
      LOG.error("Exception occurred on IO: {}", ioe.getMessage());
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
