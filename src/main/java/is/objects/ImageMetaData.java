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
    return attributes.get(key);
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
