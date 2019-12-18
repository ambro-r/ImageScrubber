package is.utils;

import is.objects.Image;
import is.objects.ImageMetaData;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Image MetaData Utilities")
public class Test_ImageMetaDataUtils {

  @Test
  public void test_getImageMetaData() {
    HashMap<String, String> expectedAttributes = new HashMap<>();
    expectedAttributes.put("Make","Canon");
    expectedAttributes.put("Model","Canon PowerShot SX710 HS");
    expectedAttributes.put("DateTime","2016:06:07 14:41:39");
    expectedAttributes.put("FocalLength","5106/1000 (5.106)");
    expectedAttributes.put("ShutterSpeedValue","329/32 (10.281)");
    expectedAttributes.put("ExifImageWidth","5184");
    expectedAttributes.put("ExifImageLength","3888");
    expectedAttributes.put("Orientation","1");
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      File image01 = new File(classLoader.getResource("samples/IMG_4731.jpg").getFile());
      HashMap<String, String> attributes = ImageMetaDataUtils.getImageMetaData(image01).getAttributes();
      for (Map.Entry<String, String> item : expectedAttributes.entrySet()) {
        assertEquals(item.getValue(), attributes.get(item.getKey()));
      }
    } catch (Exception e) { }
  }

  @Test
  public void test_isDuplicated() {
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      Image image01 =  new Image(new File(classLoader.getResource("samples/IMG_4731.jpg").getFile()));
      Image image02 = new Image(new File(classLoader.getResource("samples/IMG_4733.jpg").getFile()));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_MAKE));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_MODEL));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_DATETIME));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_DATETIME_DIGITIZED));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_GPSINFO));
      assertFalse(ImageMetaDataUtils.isDuplicated(image01, image02, ImageMetaData.ATTRIBUTE_CUSTOM_RENDERED));
      Image image03 = new Image(new File(classLoader.getResource("samples/IMG_4839.jpg").getFile()));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image03, ImageMetaData.ATTRIBUTE_MAKE));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image03, ImageMetaData.ATTRIBUTE_MODEL));
      assertFalse(ImageMetaDataUtils.isDuplicated(image01, image03, ImageMetaData.ATTRIBUTE_DATETIME));
      assertFalse(ImageMetaDataUtils.isDuplicated(image01, image03, ImageMetaData.ATTRIBUTE_DATETIME_DIGITIZED));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image03, ImageMetaData.ATTRIBUTE_GPSINFO));
      assertTrue(ImageMetaDataUtils.isDuplicated(image01, image03, ImageMetaData.ATTRIBUTE_CUSTOM_RENDERED));
    } catch (Exception e) { }
  }

}
