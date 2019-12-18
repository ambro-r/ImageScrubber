package is.objects;

import is.utils.ImageMetaDataUtils;
import java.io.File;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;


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

  public String getDateTime() {
    return imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_DATETIME);
  }

  public int getLength() {
    String value = imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_EXIF_IMAGE_LENGTH);
    if(StringUtils.isEmpty(value)) {
      value  = imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_RELATED_IMAGE_LENGTH);
    }
    return Integer.valueOf(value);
  }

  public String getMake() {
    return imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_MAKE);
  }

  public String getModel() {
    return imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_MODEL);
  }

  public int getOrientation() {
    return Integer.valueOf(imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_ORIENTATION));
  }

  public int getWidth() {
    String value = imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_EXIF_IMAGE_WIDTH);
    if(StringUtils.isEmpty(value)) {
      value  = imageMetaData.getAttribute(ImageMetaData.ATTRIBUTE_RELATED_IMAGE_WIDTH);
    }
    return Integer.valueOf(value);
  }

}
