package is.utils;

import is.objects.Image;
import is.objects.ImageMetaData;
import java.io.File;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageMetaDataUtils {

  private static final Logger LOG = LoggerFactory.getLogger(ImageMetaDataUtils.class);

  private ImageMetaDataUtils() {}

  public static ImageMetaData getImageMetaData(File image){
    ImageMetaData imageMetaData = new ImageMetaData();
    try {
      for (ImageMetadataItem imageMetadataItem : Imaging.getMetadata(image).getItems()) {
        String item = imageMetadataItem.toString();
        String key = item.substring(0, item.indexOf(":")).trim();
        String value = item.substring(item.indexOf(":") + 1).replaceAll("'","").trim();
        imageMetaData.addAttribute(key, value);
      }
    } catch (Exception e) {
      LOG.error("Exception occurred : {}", e.getMessage());
    }
    LOG.debug("{}: {}", image.getName(), imageMetaData.toString());
    return imageMetaData;
  }

  public static boolean isDuplicated(Image image01, Image image02, String attribute) {
    return image01.getImageMetaData().getAttribute(attribute).equalsIgnoreCase(image02.getImageMetaData().getAttribute(attribute));
  }
}
