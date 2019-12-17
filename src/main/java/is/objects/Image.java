package is.objects;

import is.utils.ImageMetaDataUtils;
import java.io.File;
import lombok.Getter;


public class Image {

  @Getter private ImageMetaData imageMetaData;

  @Getter private File imageFile;

  private Image() {}

  public Image(File imageFile) {
    this.imageFile = imageFile;
    imageMetaData = ImageMetaDataUtils.getImageMetaData(imageFile);
  }

  public String getFileName(){
    return imageFile.getName();
  }

}
