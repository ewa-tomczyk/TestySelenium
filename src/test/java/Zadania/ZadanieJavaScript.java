package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ZadanieJavaScript {
  WebDriver driver;
  WebDriverWait wait;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 760));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://fakestore.testelka.pl/product/wakacje-z-yoga-w-kraju-kwitnacej-wisni/");

    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void exampleTest() {
    WebElement scrollElement = driver.findElement(By.cssSelector("[id='tab-description']"));

    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", scrollElement);
    List<WebElement> headerCard = driver.findElements(By.cssSelector("section.storefront-sticky-add-to-cart--slideInDown"));
    Assertions.assertTrue(headerCard.size()==1);

  }

  @Test
  public void asyncTest() {
    long start = System.currentTimeMillis();
    ((JavascriptExecutor) driver).executeAsyncScript(
            "window.setTimeout (arguments [arguments.length - 1], 500);");
    long elapsedTime = System.currentTimeMillis() - start;
    System.out.println("Elapsed time: " + elapsedTime);
  }

  }

