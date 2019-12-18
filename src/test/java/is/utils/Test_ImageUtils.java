package is.utils;

import is.objects.Image;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Image Utilities")
public class Test_ImageUtils {

  @Test
  public void test_sameSource() {
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      Image image01 =  new Image(new File(classLoader.getResource("samples/IMG_4731.jpg").getFile()));
      Image image02 = new Image(new File(classLoader.getResource("samples/IMG_4733.jpg").getFile()));
      assertTrue(ImageUtils.sameSource(image01, image02));
      Image image03 = new Image(new File(classLoader.getResource("samples/IMG_4839.jpg").getFile()));
      assertFalse(ImageUtils.sameSource(image01, image03));
    } catch (Exception e) { }
  }

  @Test
  public void test_isCustomRendered() {
    ClassLoader classLoader = getClass().getClassLoader();
    try {
      Image image01 =  new Image(new File(classLoader.getResource("samples/IMG_4731.jpg").getFile()));
      Image image02 = new Image(new File(classLoader.getResource("samples/IMG_4733.jpg").getFile()));
      assertFalse(ImageUtils.isCustomRendered(image01));
      assertTrue(ImageUtils.isCustomRendered(image02));
    } catch (Exception e) { }
  }

}
