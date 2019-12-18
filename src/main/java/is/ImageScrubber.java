package is;

import is.workers.PotentialDeletionWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageScrubber {

  private final Logger LOG = LoggerFactory.getLogger(ImageScrubber.class);

  public static void main(String[] args) {
    ImageScrubber imageScrubber = new ImageScrubber();
    imageScrubber.scrub();
  }

  public void scrub() {
    PotentialDeletionWorker potentialDeletionWorker = new PotentialDeletionWorker();
    potentialDeletionWorker.work(        "C:\\Repositories\\Sandpit\\ImageScrubber\\src\\test\\resources\\samples");
  }

}
