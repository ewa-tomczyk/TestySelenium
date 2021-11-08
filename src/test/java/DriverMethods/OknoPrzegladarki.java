package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.sleep;

public class OknoPrzegladarki {

    WebDriver driver;

    @BeforeEach
    public void driverSetup () throws InterruptedException {
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver();
      driver.manage().window().setSize(new Dimension(1290, 730));
      driver.manage().window().setPosition(new Point (8, 30));
      driver.navigate().to("https://www.amazon.com/");
      sleep(1000);
      Assertions.assertEquals(9,driver.manage().getCookies().size());
    }

    @AfterEach
    public void driverQuit (){
      driver.quit();
    }

    @Test
  public void windowSettings () throws InterruptedException {
      Point position = driver.manage().window().getPosition();
      Assertions.assertEquals(new Point(8, 30),position, "Position is not as expected" );
      Dimension size = driver.manage().window().getSize();
      Assertions.assertEquals(new Dimension(1290, 730), size, "Size is not as expected");
      driver.manage().window().fullscreen();
      Thread.sleep(300);
      driver.manage().window().maximize();
    }

  }
