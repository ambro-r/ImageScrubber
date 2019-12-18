package is.workers;

import is.objects.Image;
import is.utils.ImageUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotentialDeletionWorker {

  private static final Logger LOG = LoggerFactory.getLogger(PotentialDeletionWorker.class);

  static final String[] EXTENSIONS = { "gif", "png", "bmp", "jpg", "jpeg" };

  public PotentialDeletionWorker() {}

  private boolean isPotentialDeletion(Image original, Image candidate) {
    boolean deletionPotential = Boolean.FALSE;
    if(ImageUtils.sameSource(original, candidate)) {
      if(ImageUtils.isCustomRendered(original) && ImageUtils.isCustomRendered(candidate)) {
        deletionPotential = original.getFileName().compareTo(candidate.getFileName()) <= 0;
      } else {
        deletionPotential = ImageUtils.isCustomRendered(candidate);
      }
    }
    if(!deletionPotential) {
      double difference = ImageUtils.getDifferencePercent(original, candidate);
      deletionPotential = difference <= 20;
    }
    LOG.debug("Deletion potential of {} in relation to {}: {}", candidate.getFileName(), original.getFileName(), deletionPotential);
    return deletionPotential;
  }

  private List<Image> getImagesInDirectory(String directory) {
    List<Image> imageList = new ArrayList<>();
    File fileDirectory = new File(directory);
    for(File file : fileDirectory.listFiles()) {
      String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
      if (Arrays.stream(EXTENSIONS).anyMatch(p -> p.equalsIgnoreCase(extension))) {
        imageList.add(new Image(file));
      }
    }
    LOG.debug("{} image files found in directory {}", imageList.size(), directory);
    return imageList;
  }

  private List<Image> getImages(String directory) {
    List<Image> imageList = getImagesInDirectory(directory);
    for(int i = 0; i < imageList.size(); i++) {
      Image original = imageList.get(i);
      for(int j = i + 1; j < imageList.size(); j++) {
        Image candidate = imageList.get(j);
        if (!candidate.isFlaggedForDeletion()) {
          candidate.setFlaggedForDeletion(isPotentialDeletion(original, candidate));
        }
      }
    }
    return imageList;
  }

  public void work(String directory) {
    List<Image> imageList = getImages(directory);
  }

}
