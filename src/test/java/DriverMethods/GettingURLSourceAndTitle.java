package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

public class GettingURLSourceAndTitle {

  WebDriver driver;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void getCurrentURLExample (){
    String googleUrl = "https://www.google.pl/";
    driver.navigate().to("https://google.pl");
    Assertions.assertEquals(googleUrl, driver.getCurrentUrl(), "Current url is not " + googleUrl);
  }
  @Test
  public void getTitle (){
    String googleTitle = "Google";
    driver.navigate().to("https://google.pl");
    Assertions.assertEquals(googleTitle, driver.getTitle(), "Current title is not " + googleTitle);
  }
  @Test
  public void getSource (){
    String googleImage = "/images/branding/googleg/1x/googleg_standard_color_128dp.png";
    driver.navigate().to("https://google.pl");
    Assertions.assertTrue(driver.getPageSource().contains(googleImage), "Page does not contain " + googleImage);
  }

}
