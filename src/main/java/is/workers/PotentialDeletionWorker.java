package is.workers;

import is.objects.Image;
import is.utils.ImageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotentialDeletionWorker {

  private static final Logger LOG = LoggerFactory.getLogger(PotentialDeletionWorker.class);

  public PotentialDeletionWorker() {}

  private boolean isPotentialDeletion(Image original, Image candidate) {
    boolean deletionPotential;
    if(ImageUtils.sameSource(original, candidate)) {
      if(ImageUtils.isCustomRendered(original) && ImageUtils.isCustomRendered(candidate)) {
        deletionPotential = original.getFileName().compareTo(candidate.getFileName()) <= 0;
      } else {
        deletionPotential = ImageUtils.isCustomRendered(candidate);
      }
    } else {
      double difference = ImageUtils.getDifferencePercent(original, candidate);
      deletionPotential = difference <= 20;
    }
    LOG.debug("Deletion potential of {} in relation to {}: {}", candidate.getFileName(), original.getFileName(), deletionPotential);
    return deletionPotential;
  }


  public void work(String directory) {

  }

}
