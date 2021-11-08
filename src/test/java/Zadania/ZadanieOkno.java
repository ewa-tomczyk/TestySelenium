package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.systeminfo.model.Size;

import static java.lang.Thread.sleep;

public class ZadanieOkno {

  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.navigate().to("https://www.amazon.com/");
    sleep(1000);
    Assertions.assertEquals(9,driver.manage().getCookies().size());
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void zadanieOkno() {
    Dimension size = new Dimension(854, 480);
    driver.manage().window().setSize(size);
    Point position = new Point(445, 30);
    driver.manage().window().setPosition(position);

    Assertions.assertEquals(new Dimension(854, 480),driver.manage().window().getSize(), "Size isn't correct");

    Assertions.assertEquals(new Point(445, 30), driver.manage().window().getPosition(),  "Position is incorrect");

    driver.manage().window().maximize();
    driver.manage().window().fullscreen();

  }
}
