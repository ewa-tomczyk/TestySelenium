package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FindingObjects {
  WebDriver driver;

  @BeforeEach
  public void driverSetup() throws InterruptedException, MalformedURLException {
    //WebDriverManager.chromedriver().setup();

    OperaOptions options = new OperaOptions();
    options.setCapability(CapabilityType.VERSION, "96");
    options.setCapability("platform", "MAC");

    driver = new RemoteWebDriver(new URL("http://192.168.0.194:4444/wd/hub"), options);

    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
  }

  @AfterEach
  public void driverQuit() {
    driver.quit();
  }

  @Test
  public void tTest() {
    driver.navigate().to("https://fakestore.testelka.pl/product/grecja-limnos/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
  }
}
