package DriverMethods;

import TestHelpers.TestStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
public class ZrzutyEkranu {
  protected WebDriver driver;
  @RegisterExtension
  TestStatus status = new TestStatus();
  @BeforeEach
  public void setup() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 730));
    driver.manage().window().setPosition(new Point(10, 40));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    driver.navigate().to("https://www.zooniverse.org");
  }
  @AfterEach
  public void driverQuit(TestInfo info) throws IOException {
    if (status.isFailed) {
      System.out.println("Test screenshot is available at: " + takeScreenshot(info));
    }
    driver.quit();
  }
  @Test
  public void screenshotExample() {
    driver.findElement(By.cssSelector("button[value='sign-in']")).click();
    driver.findElement(By.cssSelector("input[name='login']")).sendKeys("Ewa17");
    driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Ewa171717");
    driver.findElement(By.cssSelector("form")).submit();
    WebElement userName = driver.findElement(By.cssSelector("span[class='account-bar'] strong"));
    Assertions.assertEquals("EWA1", userName.getText(),
            "Username displayed on header is not correct. The user was probably not logged in.");
  }
  private String takeScreenshot(TestInfo info) throws IOException {
    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    LocalDateTime timeNow = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
    String path = "/Users/ewatomczyk/Desktop/" + info.getDisplayName() + " " + formatter.format(timeNow) + ".png";
    FileHandler.copy(screenshot, new File(path));
    return path;
  }
}