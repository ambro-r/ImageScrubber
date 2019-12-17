package is;

import is.objects.Image;
import is.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ImageScrubber {

  private final Logger LOG = LoggerFactory.getLogger(ImageScrubber.class);

  public static void main(String[] args) {
    ImageScrubber imageScrubber = new ImageScrubber();
    imageScrubber.scrub();
  }


  public void scrub() {
    Image image01 = new Image(new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4731.jpg"));
    Image image02 = new Image(new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4733.jpg"));

    Image image03 = new Image(new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4839.jpg"));
    Image image04 = new Image(new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4844.jpg"));
    Image image05 = new Image(new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4840.jpg"));

    LOG.info("{} & {} same source : {}", image01.getFileName(), image02.getFileName(), ImageUtils.sameSource(image01, image02));
    LOG.info("{} is custom rendered : {}", image01.getFileName(),ImageUtils.isCustomRendered(image01));
    LOG.info("{} is custom rendered : {}", image02.getFileName(),ImageUtils.isCustomRendered(image02));

   /* MetaDataReader metaDataReader = new MetaDataReader();
    ImageMetaData imageMetaData = metaDataReader.readImageMetaData(file);
    LOG.info("File Attributes: {}", imageMetaData.getAttributes());
    LOG.info("Contains Alpha Transparency: {}", ImageUtils_TEMp.containsAlphaTransparency(file));
    */

    /*imageMetaData = metaDataReader.readImageMetaData(file2);
    LOG.info("File Attributes: {}", imageMetaData.getAttributes());
    LOG.info("Contains Alpha Transparency: {}", ImageUtils_TEMp.containsAlphaTransparency(file2));
    LOG.info("Visual Compare: {}", ImageUtils_TEMp.visuallyCompare(file, file2));

    double p = ImageUtils.getDifferencePercent(file, file2);
    LOG.info("Diff percent: {}", p);
    File file3 =
        new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 02\\IMG_4839.jpg");
    double p = ImageUtils.getDifferencePercent(file, file2);
    LOG.info("Diff percent: {}", p);


    p = ImageUtils.getDifferencePercent(file3, file4);
    LOG.info("Diff percent: {}", p);

    p = ImageUtils.getDifferencePercent(file3, file5);
    LOG.info("Diff percent: {}", p);
  */
  }
}
