package is;

import is.workers.PotentialDeletionWorker;
import org.apache.commons.lang3.StringUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageScrubber {

  private final Logger LOG = LoggerFactory.getLogger(ImageScrubber.class);

  public static void main(String[] args) {
    ImageScrubber imageScrubber = new ImageScrubber();
    if ((args.length >= 1) && !StringUtils.isEmpty(args[0])) {
      String directory = args[0];
      imageScrubber.scrub(directory);
    } else {
      System.out.println("Please specify a directory to scrub.");
    }
  }

  public void scrub(String directory) {
    LOG.info("Scrubbing directory {}", directory);
    PotentialDeletionWorker potentialDeletionWorker = new PotentialDeletionWorker();
    potentialDeletionWorker.work(directory);
  }

}
