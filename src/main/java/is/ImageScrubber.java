package is;

import is.objects.ImageMetaData;
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
        File file = new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4731.jpg");
        MetaDataReader metaDataReader = new MetaDataReader();
        ImageMetaData imageMetaData = metaDataReader.readImageMetaData(file);
        LOG.info("File Attributes: {}", imageMetaData.getAttributes());
        file = new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4733.jpg");
        imageMetaData = metaDataReader.readImageMetaData(file);
        LOG.info("File Attributes: {}", imageMetaData.getAttributes());
    }

}
