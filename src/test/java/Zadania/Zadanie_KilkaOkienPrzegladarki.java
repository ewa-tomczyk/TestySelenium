package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class Zadanie_KilkaOkienPrzegladarki {
  WebDriver driver;
  WebDriverWait wait;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://fakestore.testelka.pl/product/wczasy-relaksacyjne-z-yoga-w-toskanii/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  @Test
  public void addingProductToWishlist() {
    By addToWishlist = By.cssSelector(".add_to_wishlist");
    driver.findElement(addToWishlist).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(addToWishlist));
    driver.findElement(By.cssSelector("[id='menu-menu'] [href$=\"wishlist/\"]")).click();
    Set<String> windows = driver.getWindowHandles();
    String parentWindow = driver.getWindowHandle();
    windows.remove(parentWindow);
    String secondWindow = windows.iterator().next();
    driver.switchTo().window(secondWindow);
    By removeFromWishlist = By.cssSelector(".remove_from_wishlist");
    driver.findElement(removeFromWishlist).click();
    By wishlistEmpty = By.cssSelector(".wishlist-empty");

    Assertions.assertDoesNotThrow(()->    wait.until(ExpectedConditions.presenceOfElementLocated(wishlistEmpty)), "Not empty wishlist");
  }
}
