package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.FileHandler;

public class Screenshots {

  WebDriver driver;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    driver.navigate().to("https://www.zooniverse.org");
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  @Test
  public void testZooniverse() throws IOException {
    driver.findElement(By.cssSelector("button[value='sign-in']")).click();
    driver.findElement(By.cssSelector("input[name='login']")).sendKeys("Ewa17");
    driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Ewa171717");
    driver.findElement(By.cssSelector("form")).submit();
    WebElement userName = driver.findElement(By.cssSelector("span[class='account-bar'] strong"));


    //File userNameScreenshot = userName.getScreenshotAs(OutputType.FILE);
    //FileUtils.copyFile(userNameScreenshot, new File("/Users/ewatomczyk/Desktop/test.jpg"));


    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    //FileHandler.copy(screenshot, new File("/Users/ewatomczyk/Desktop/test.jpg"));
    FileUtils.copyFile(screenshot, new File("/Users/ewatomczyk/Desktop/test.png"));

    Assertions.assertEquals("EWA17", driver.findElement(By.cssSelector("span[class='account-bar'] strong")).getText(), "Username is incorrect");
  }
}
