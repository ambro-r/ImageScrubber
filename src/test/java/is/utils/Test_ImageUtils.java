package is.utils;

import static org.junit.jupiter.api.Assertions.*;

import is.objects.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.imaging.Imaging;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test Image Utilities")
public class Test_ImageUtils {

  private Image image01;
  private Image image02;
  private Image image03;

  @BeforeAll
  public void init() {
    ClassLoader classLoader = getClass().getClassLoader();
    image01 =  new Image(new File(classLoader.getResource("samples/IMG_4731.jpg").getFile()));
    image02 =  new Image(new File(classLoader.getResource("samples/IMG_4733.jpg").getFile()));
    image03 = new Image(new File(classLoader.getResource("samples/IMG_4839.jpg").getFile()));
  }

  @Test
  public void test_sameSource() {
    assertTrue(ImageUtils.sameSource(image01, image02));
    assertFalse(ImageUtils.sameSource(image01, image03));
  }

  @Test
  public void test_isCustomRendered() {
    assertFalse(ImageUtils.isCustomRendered(image01));
    assertTrue(ImageUtils.isCustomRendered(image02));
  }

  @Test
  public void test_rotate() throws Exception{
    Class[] argClasses = {Image.class};
    Method method = ImageUtils.class.getDeclaredMethod("rotate", argClasses);
    method.setAccessible(true);

    Object[] argObjects = { image01 };
    BufferedImage originalImage01 = Imaging.getBufferedImage(image01.getImageFile());
    BufferedImage rotatedImage01 = (BufferedImage) method.invoke(ImageUtils.class, argObjects);
    assertEquals(originalImage01.getHeight(), rotatedImage01.getHeight());
    assertEquals(originalImage01.getWidth(), rotatedImage01.getWidth());

    argObjects = new Object[]{ image03 };
    BufferedImage originalImage03 = Imaging.getBufferedImage(image03.getImageFile());
    BufferedImage rotatedImage03 = (BufferedImage) method.invoke(ImageUtils.class, argObjects);
    assertEquals(originalImage03.getWidth(), rotatedImage03.getHeight());
    assertEquals(originalImage03.getHeight(), rotatedImage03.getWidth());
  }

  @Test
  public void test_getDifferencePercent() {
    assertEquals(14.61, new BigDecimal(ImageUtils.getDifferencePercent(image01, image02)).setScale(2, RoundingMode.HALF_UP).doubleValue());
    assertEquals(100.00, ImageUtils.getDifferencePercent(image01, image03));
  }

}
