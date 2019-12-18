package is.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class ImageMetaData {

  public static String ATTRIBUTE_DATETIME = "DateTime";
  public static String ATTRIBUTE_DATETIME_DIGITIZED = "DateTimeDigitized";
  public static String ATTRIBUTE_GPSINFO = "GPSInfo";
  public static String ATTRIBUTE_MAKE = "Make";
  public static String ATTRIBUTE_MODEL = "Model";
  public static String ATTRIBUTE_CUSTOM_RENDERED = "CustomRendered";
  public static String ATTRIBUTE_ORIENTATION = "Orientation";
  public static String ATTRIBUTE_EXIF_IMAGE_WIDTH = "ExifImageWidth";
  public static String ATTRIBUTE_EXIF_IMAGE_LENGTH = "ExifImageLength";
  public static String ATTRIBUTE_RELATED_IMAGE_LENGTH = "RelatedImageLength";
  public static String ATTRIBUTE_RELATED_IMAGE_WIDTH = "RelatedImageWidth";

  @Getter @Setter private HashMap<String, String> attributes = new HashMap<>();

  public void addAttributes(HashMap<String, String> attributes) {
    this.attributes.putAll(attributes);
  }

  public void addAttribute(String key, String value) {
    if(attributes.containsKey(key)) {
      attributes.replace(key, value);
    } else {
      attributes.put(key, value);
    }
  }

  public String getAttribute(String key) {
    String value = "";
    if (attributes.containsKey(key)) {
      value = attributes.get(key);
    }
    return value;
  }

  @Override
  public String toString() {
    return new StringBuilder("ImageMetaData [")
        .append("attributes=")
        .append(attributes)
        .append("]")
        .toString();
  }
}
