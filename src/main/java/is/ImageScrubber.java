package is;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ImageScrubber {

    private final Logger LOG = LoggerFactory.getLogger(ImageScrubber.class);

    public static void main(String[] args) {
    File file = new File("C:\\Repositories\\Sandpit\\ImageScrubber\\samples\\Set 01\\IMG_4731.jpg");
    MetaDataReader metaDataReader = new MetaDataReader();
    metaDataReader.readImageMetaData(file);
    }

}
