package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WykonywanieJavaScryptow {

  WebDriver driver;
  WebDriverWait wait;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://fakestore.testelka.pl/");

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver.manage().timeouts().setScriptTimeout(Duration.ofMillis(1000));
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void exampleTest() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("console.log('PISZE W KONSOLI');");
    String domainName = (String)js.executeScript("return document.domain");
  }

  @Test
  public void asyncTest() {
    long start = System.currentTimeMillis();
    ((JavascriptExecutor) driver).executeAsyncScript(
            "window.setTimeout (arguments [arguments.length - 1], 500);");
    long elapsedTime = System.currentTimeMillis() - start;
    System.out.println("Elapsed time: " + elapsedTime);
  }

  @Test
  public void syncTest() {
    long start = System.currentTimeMillis();
    ((JavascriptExecutor) driver).executeScript(
            "window.setTimeout (arguments [arguments.length - 1], 500);");
    long elapsedTime = System.currentTimeMillis() - start;
    System.out.println("Elapsed time: " + elapsedTime);
  }

}
