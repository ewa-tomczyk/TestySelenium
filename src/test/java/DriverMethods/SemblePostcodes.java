package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SemblePostcodes {
  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1290, 730));
    driver.manage().window().setPosition(new Point(8, 30));

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
    driver.navigate().to("https://frontend.tst.semble.aws.10clouds.com/dashboard/funder");

  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  @Test
  public void navigate () throws MalformedURLException {
    driver.findElement(By.cssSelector("input[name='email']")).sendKeys("ewa.tomczyk+EdgeFunder@10clouds.com");

    driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Semble123!");
    driver.findElement(By.cssSelector("button[class='MuiButtonBase-root MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-disableElevation']")).click();
    driver.navigate().forward();
    String pageTitle = "Funder dashboard | ActionFunder";
    Assertions.assertEquals(pageTitle, driver.getTitle(), "Title isn't " + pageTitle);
    driver.findElement(By.cssSelector("[name='purpose']")).click();
    driver.findElement(By.cssSelector("[name='purpose']")).sendKeys("test");

  }
}
