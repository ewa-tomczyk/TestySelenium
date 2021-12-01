package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ZadanieSesja {
  ChromeDriver driver;
  By demoStoreNoticeDismiss = By.cssSelector(".woocommerce-store-notice__dismiss-link");

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(demoStoreNoticeDismiss).click();
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void localStorage() {
    Assertions.assertEquals("1", driver.getLocalStorage().size(), "More than 1 key");
  }

  @Test
  public void sessionStorage() {
    Assertions.assertEquals("2", driver.getSessionStorage().size(), "More than 2 keys");
  }

  @Test
  public void addToCartStorage() {
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(d->driver.getSessionStorage().size()==3);
    Assertions.assertTrue(driver.getSessionStorage().keySet().contains("wc_cart_created"), "No item in the session");
  }

  @Test
  public void removeStorage() {
    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();
    SessionStorage session = driver.getSessionStorage();
    String value = session.getItem("wc_cart_created");
    String removedValue = session.removeItem("wc_cart_created");

  }



}
