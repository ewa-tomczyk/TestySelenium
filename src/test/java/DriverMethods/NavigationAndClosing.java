package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class NavigationAndClosing {

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
  public void getMethod (){
    driver.get("https://google.pl");
  }

  @Test
  public void navigate () throws MalformedURLException {
    driver.navigate().to("https://google.pl");
    driver.navigate().to("https://amazon.com");
    driver.navigate().back();
    driver.navigate().forward();
    driver.navigate().refresh();
  }

}
