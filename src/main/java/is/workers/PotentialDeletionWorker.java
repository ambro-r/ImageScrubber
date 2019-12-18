package is.workers;

import is.objects.Image;
import is.utils.ImageUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
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

  private String getTempDirectory(String directory) {
    String tempDirectory = directory;
    if(!directory.endsWith(File.separator)) {
      tempDirectory += File.separator;
    }
    tempDirectory += "potentialForDeletion";
    LOG.debug("Temporary directory generated: {}", tempDirectory);
    return tempDirectory;
  }


  public void work(String directory) {
    int counter = 0;
    File tempDirectory = new File(getTempDirectory(directory));
    try {
      List<Image> imageList = getImages(directory);

      if(!tempDirectory.exists()) {
        tempDirectory.mkdir();
      }
      for(Image image : imageList) {
        if(image.isFlaggedForDeletion()) {
          File destination = new File(tempDirectory + File.separator + image.getFileName());
          LOG.debug("Moving image {} to {}.", image.getFileName(), destination.getAbsolutePath());
          FileUtils.moveFile(image.getImageFile(), destination);
          counter ++;
        }
      }
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    LOG.info("Moved {} images to {}.", counter, tempDirectory.getAbsolutePath());
  }

}
