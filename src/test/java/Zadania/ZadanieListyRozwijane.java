package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ZadanieListyRozwijane {
  WebDriver driver;
  WebDriverWait wait;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 760));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://fakestore.testelka.pl/product-category/windsurfing/");

    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void sortingBottomToUp() {
    WebElement dropdown = driver.findElement(By.cssSelector(".woocommerce-products-header+* select"));
    Select sortingDropdown = new Select(dropdown);
    sortingDropdown.selectByIndex(4);
    driver.navigate().refresh();

    String expectedLowestPrice = "2 900,00 zł";
    String actualLowestPrice = driver.findElement(By.cssSelector("ul[class='products columns-3'] li:first-child span bdi")).getText();

    Assertions.assertEquals(expectedLowestPrice, actualLowestPrice, "Bottom to up sorting doesn't work");
  }

  @Test
  public void sortingToUpBottom() {
    WebElement dropdown = driver.findElement(By.cssSelector(".woocommerce-products-header+* select"));
    Select sortingDropdown = new Select(dropdown);
    sortingDropdown.selectByIndex(5);

    String expectedHighestPrice = "5 399,00 zł";
    String actualHighestPrice = driver.findElement(By.cssSelector("ul[class='products columns-3'] li:first-child span bdi")).getText();

    Assertions.assertEquals(expectedHighestPrice, actualHighestPrice, "Up to bottom sorting doesn't work");
  }
}
