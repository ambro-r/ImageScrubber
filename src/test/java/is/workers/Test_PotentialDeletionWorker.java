package is.workers;

import static org.junit.jupiter.api.Assertions.*;

import is.objects.Image;
import is.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import org.apache.commons.imaging.Imaging;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Test Potential Deletion Worker")
public class Test_PotentialDeletionWorker {

  private Image image01;
  private Image image02;
  private Image image03;
  private Image image04;
  private Image image05;
  private Image image06;
  private Image image07;
  private Image image08;

  @BeforeAll
  public void init() {
    ClassLoader classLoader = getClass().getClassLoader();
    image01 =  new Image(new File(classLoader.getResource("samples/IMG_4731.jpg").getFile()));
    image02 =  new Image(new File(classLoader.getResource("samples/IMG_4732.jpg").getFile()));
    image03 =  new Image(new File(classLoader.getResource("samples/IMG_4733.jpg").getFile()));
    image04 =  new Image(new File(classLoader.getResource("samples/IMG_4736.jpg").getFile()));
    image05 =  new Image(new File(classLoader.getResource("samples/IMG_4839.jpg").getFile()));
    image06 =  new Image(new File(classLoader.getResource("samples/IMG_4840.jpg").getFile()));
    image07 =  new Image(new File(classLoader.getResource("samples/IMG_4841.jpg").getFile()));
    image08 =  new Image(new File(classLoader.getResource("samples/IMG_4844.jpg").getFile()));
  }

  @Test
  public void test_isPotentialDeletion() throws Exception{
    Class[] argClasses = {Image.class, Image.class};
    Method method = PotentialDeletionWorker.class.getDeclaredMethod("isPotentialDeletion", argClasses);
    method.setAccessible(true);

    Object[] argObjects = { image01, image02 };
    boolean isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertTrue(isPotential);

    argObjects = new Object[]{ image02, image03 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertTrue(isPotential);

    argObjects = new Object[]{ image03, image02 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertFalse(isPotential);

    argObjects = new Object[]{ image01, image05 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertFalse(isPotential);

    argObjects = new Object[]{ image05, image06 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertTrue(isPotential);

    argObjects = new Object[]{ image05, image07 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertTrue(isPotential);

    argObjects = new Object[]{ image05, image08 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertTrue(isPotential);

    argObjects = new Object[]{ image02, image06 };
    isPotential = (boolean) method.invoke(PotentialDeletionWorker.class.newInstance(), argObjects);
    assertFalse(isPotential);
  }

}
