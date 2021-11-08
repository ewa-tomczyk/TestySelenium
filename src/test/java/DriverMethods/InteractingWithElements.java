package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class InteractingWithElements {

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
  public void testZooniverse() {
    driver.findElement(By.cssSelector("button[value='sign-in']")).click();
    driver.findElement(By.cssSelector("input[name='login']")).sendKeys("Ewa17");
    driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Ewa171717");
    driver.findElement(By.cssSelector("form")).submit();

    Assertions.assertEquals("EWA17", driver.findElement(By.cssSelector("span[class='account-bar'] strong")).getText(), "Username is incorrect");
  }
}
