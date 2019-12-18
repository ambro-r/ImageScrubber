package is.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test Image Object")
public class Test_Image {

  private Image image01;
  private Image image02;

  @BeforeAll
  public void init() {
    ClassLoader classLoader = getClass().getClassLoader();
    image01 =  new Image(new File(classLoader.getResource("samples/IMG_4731.jpg").getFile()));
    image02 =  new Image(new File(classLoader.getResource("samples/IMG_4839.jpg").getFile()));
  }

  @Test
  public void test_getOrientation() {
    assertEquals(1, image01.getOrientation());
    assertEquals(8, image02.getOrientation());
  }

  @Test
  public void test_getLength() {
    assertEquals(3888, image01.getLength());
    assertEquals(3888, image02.getLength());
  }

  @Test
  public void test_getWidth() {
    assertEquals(5184, image01.getWidth());
    assertEquals(5184, image02.getWidth());
  }

}
