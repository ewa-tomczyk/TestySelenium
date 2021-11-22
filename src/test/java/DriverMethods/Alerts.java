package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Alerts {
  WebDriver driver;
  JavascriptExecutor js;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    js = (JavascriptExecutor) driver;
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void promptBoxTest() {
    String javascript = "prompt ('Możesz tutuaj coś wpisać:')";
    js.executeScript(javascript);
    String text = driver.switchTo().alert().getText();
    driver.switchTo().alert().sendKeys("Teest");
    driver.switchTo().alert().accept();
    js.executeScript(javascript);
    driver.switchTo().alert().dismiss();
  }
}
