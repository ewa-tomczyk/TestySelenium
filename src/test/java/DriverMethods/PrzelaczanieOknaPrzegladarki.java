package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class PrzelaczanieOknaPrzegladarki {
  WebDriver driver;
  WebDriverWait wait;
  By cookieAccept = By.cssSelector("#cn-accept-cookie");

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://testelka.pl/blog/");
    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    driver.findElement(cookieAccept).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieAccept));
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  @Test
  public void windowHandleTest() {
    driver.findElement(By.cssSelector("span[class='dashicons dashicons-video-alt3']")).click();
    Set<String> windows = driver.getWindowHandles();
    String parentWindow = driver.getWindowHandle();
    windows.remove(parentWindow);
    String secondWindow = windows.iterator().next();
    driver.switchTo().window(secondWindow);
    String activeWindow = driver.getWindowHandle();
    driver.findElement(By.xpath(".//*[contains(text(),'I agree')]")).click();
    driver.findElement(By.cssSelector("#container #logo-icon")).click();
    driver.switchTo().window(parentWindow);

  }
}
