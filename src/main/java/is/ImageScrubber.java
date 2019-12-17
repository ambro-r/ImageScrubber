package is;

import is.objects.ImageMetaData;
import is.utils.ImageUtils_TEMp;
import is.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageScrubber {

  private final Logger LOG = LoggerFactory.getLogger(ImageScrubber.class);

  public static void main(String[] args) {
    ImageScrubber imageScrubber = new ImageScrubber();
    imageScrubber.scrub();
  }

  public void scrub() {
    File file = new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4731.jpg");
    MetaDataReader metaDataReader = new MetaDataReader();
    ImageMetaData imageMetaData = metaDataReader.readImageMetaData(file);
    LOG.info("File Attributes: {}", imageMetaData.getAttributes());
    LOG.info("Contains Alpha Transparency: {}", ImageUtils_TEMp.containsAlphaTransparency(file));
    File file2 =
        new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4733.jpg");
    imageMetaData = metaDataReader.readImageMetaData(file2);
    LOG.info("File Attributes: {}", imageMetaData.getAttributes());
    LOG.info("Contains Alpha Transparency: {}", ImageUtils_TEMp.containsAlphaTransparency(file2));
    LOG.info("Visual Compare: {}", ImageUtils_TEMp.visuallyCompare(file, file2));

    double p = ImageUtils.getDifferencePercent(file, file2);
    LOG.info("Diff percent: {}", p);
    File file3 =
        new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4839.jpg");
    p = ImageUtils.getDifferencePercent(file, file3);
    LOG.info("Diff percent: {}", p);
    File file4 =
        new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4844.jpg");
    p = ImageUtils.getDifferencePercent(file3, file4);
    LOG.info("Diff percent: {}", p);
    File file5 =
        new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4840.jpg");
    p = ImageUtils.getDifferencePercent(file3, file5);
    LOG.info("Diff percent: {}", p);
  }
}
