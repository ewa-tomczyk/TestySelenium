package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Set;

public class Drivers {
    WebDriver driver;

  @BeforeEach
  public void driverSetup() {
    WebDriverManager.safaridriver().setup();
    driver = new SafariDriver();
    driver.manage().window().maximize();
  }

    //@BeforeEach
    //public void driverSetup() throws InterruptedException {
      //System.setProperty("webdriver.opera.driver", "src/main/resources/operadriver_mac64/operadriver");
      //OperaOptions operaOptions = new OperaOptions();
      //operaOptions.setBinary("Macintosh HD/Applications/Opera.app");
      //driver = new OperaDriver(operaOptions);
      //driver.manage().window().maximize();
    //}

    @AfterEach
    public void driverQuit() {
      driver.quit();
    }

  @Test
  public void localStorge() {
    driver.navigate().to("https://airly.org/map/pl/#50.0645188245,19.9494942593");
  }
}