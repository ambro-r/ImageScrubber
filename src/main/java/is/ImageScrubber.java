package is;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageScrubber {

  private final Logger LOG = LoggerFactory.getLogger(ImageScrubber.class);

  public static void main(String[] args) {
    ImageScrubber imageScrubber = new ImageScrubber();
    imageScrubber.scrub();
  }

  public void scrub() { }

}
