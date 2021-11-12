package Zadania;

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

public class ZadanieCzekanie {
  WebDriver driver;
  WebDriverWait wait;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 760));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  public void addProductToCart() {
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("a[title='Zobacz zawartość koszyka']")).click();
  }

  public void addCouponCode(String cupon) {
    driver.findElement(By.cssSelector("[id='coupon_code']")).sendKeys(cupon);
    driver.findElement(By.cssSelector("[name='apply_coupon']")).click();

    wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[class= 'blockUI blockOverlay']"),0));
  }

  public void removeCouponCodeKwotowy250() {
    driver.findElement(By.cssSelector("a[class ='woocommerce-remove-coupon'][data-coupon= 'kwotowy250']")).click();
    wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[class= 'blockUI blockOverlay']"),0));
  }

  public void removeCouponCode10procent() {
    driver.findElement(By.cssSelector("a[class ='woocommerce-remove-coupon'][data-coupon= '10procent']")).click();
    wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[class= 'blockUI blockOverlay']"),0));
  }

  public String getErrorMessage() {
    return driver.findElement(By.cssSelector("[class='woocommerce-error'] li")).getText();
  }

  public String getMessage(){
    return driver.findElement(By.cssSelector("div[class ='woocommerce-message']")).getText();
  }

  public String getCartValue() {
    return driver.findElement(By.cssSelector("td[data-title = 'Suma'] span[class ='woocommerce-Price-amount amount'] bdi")).getText();
  }

  @Test
  public void zadanieCzekanieIncorrectCode() {
    addProductToCart();
    addCouponCode("a");

    String expectedError = "Kupon \"a\" nie istnieje!";
    String actualErrorMessage = getErrorMessage();

    Assertions.assertEquals(expectedError, actualErrorMessage, "Wrong error message");
  }

  @Test
  public void zadanieCzekanieEmptyCode() {
    addProductToCart();
    addCouponCode("");

    String expectedError = "Proszę wpisać kod kuponu.";
    String actualErrorMessage = getErrorMessage();

    Assertions.assertEquals(expectedError, actualErrorMessage, "Wrong error message");
  }

  @Test
  public void zadanieCzekanieCorrectCode() {
    addProductToCart();
    addCouponCode("kwotowy250");

    String expectedCartValue = "2 549,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedMessage = "Kupon został pomyślnie użyty.";
    String actualMessage = getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieCorrect2Codes() {
    addProductToCart();
    addCouponCode("kwotowy250");
    addCouponCode("10procent");

    String expectedCartValue = "2 269,10 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedMessage = "Kupon został pomyślnie użyty.";
    String actualMessage = getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieMinimalValueNotReached() {
    addProductToCart();
    addCouponCode("kwotowy300");

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedErrorMessage = "Minimalna wartość zamówienia dla tego kuponu to 3 000,00 zł.";
    String actualErrorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieNotForPromoProducts() {
    addProductToCart();
    addCouponCode("kwotowy300bezpromocji ");

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedErrorMessage = "Przepraszamy, ten kupon nie może być zastosowany do przecenionych produktów.";
    String actualErrorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieWrongCategory() {
    addProductToCart();
    addCouponCode("windsurfing350");

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedErrorMessage = "Przepraszamy, tego kuponu nie można zastosować do wybranych produktów.";
    String actualErrorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieExpiredCode() {
    addProductToCart();
    addCouponCode("starośćnieradość");

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedErrorMessage = "Ten kupon stracił ważność.";
    String actualErrorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieApplicationOfSecondInvalidCode() {
    addProductToCart();
    addCouponCode("kwotowy250");
    addCouponCode("kwotowy250pojedynczy");

    String expectedCartValue = "2 549,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedMessage = "Kupon został pomyślnie użyty.";
    String actualMessage = getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieRemove1Code() {
    addProductToCart();
    addCouponCode("kwotowy250");
    wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[class= 'blockUI blockOverlay']"),0));
    addCouponCode("10procent");
    removeCouponCodeKwotowy250();

    String expectedCartValue = "2 519,10 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedMessage = "Kupon został usunięty.";
    String actualMessage = getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage, "Incorrect message");
  }

  @Test
  public void zadanieCzekanieRemove2Codes() {
    addProductToCart();
    addCouponCode("kwotowy250");
    addCouponCode("10procent");
    removeCouponCodeKwotowy250();
    removeCouponCode10procent();

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    String expectedMessage = "Kupon został usunięty.";
    String actualMessage = getMessage();
    Assertions.assertEquals(expectedMessage, actualMessage, "Incorrect message");
  }
}
