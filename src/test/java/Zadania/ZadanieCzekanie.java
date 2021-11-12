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
  By addToCartButton = By.cssSelector("button[name='add-to-cart']");
  By cartButton = By.cssSelector("a[title='Zobacz zawartość koszyka']");
  String incorrectCoupon = "a";
  String correctCoupon = "kwotowy250";
  String minimalCoupon = "kwotowy300";
  String noPromoCode = "kwotowy300bezpromocji";
  String windsurfingCode = "windsurfing350";
  String expiredCode = "starośćnieradość";

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 760));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://fakestore.testelka.pl/product/wspinaczka-via-ferraty/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    driver.findElement(addToCartButton).click();
    driver.findElement(cartButton).click();
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  public void addCouponCode(String cupon) {
    By couponCodeField = By.cssSelector("[id='coupon_code']");
    By applyCouponButton = By.cssSelector("[name='apply_coupon']");

    driver.findElement(couponCodeField).sendKeys(cupon);
    driver.findElement(applyCouponButton).click();
  }

  public void waitForProcessingEnd() {
    By blockedUI = By.cssSelector("div.blockUI ");
    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(blockedUI, 0));
    wait.until(ExpectedConditions.numberOfElementsToBe(blockedUI, 0));
  }

  public String getAlertMessage(){
    By alert = By.cssSelector("[role ='alert']");
    return wait.until(ExpectedConditions.visibilityOfElementLocated(alert)).getText();
  }

  public String getCartValue() {
    return driver.findElement(By.cssSelector("td[data-title = 'Suma'] span[class ='woocommerce-Price-amount amount'] bdi")).getText();
  }

  @Test
  public void addingIncorrectCode() {
    addCouponCode(incorrectCoupon);

    Assertions.assertEquals("Kupon \"" + incorrectCoupon +"\" nie istnieje!", getAlertMessage(), "Wrong error message");
  }

  @Test
  public void addingEmptyCode() {
    addCouponCode("");

    Assertions.assertEquals("Proszę wpisać kod kuponu.", getAlertMessage(), "Wrong error message");
  }

  @Test
  public void addingCorrectCode() {
    addCouponCode(correctCoupon);
    waitForProcessingEnd();

    String expectedCartValue = "2 549,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Kupon został pomyślnie użyty.", getAlertMessage(), "Incorrect message");
  }

  @Test
  public void addingCorrectCodeTwice() {
    addCouponCode(correctCoupon);
    waitForProcessingEnd();
    addCouponCode(correctCoupon);
    waitForProcessingEnd();

    String expectedCartValue = "2 549,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Kupon został zastosowany!", getAlertMessage(), "Incorrect message");
  }

  @Test
  public void addingMinimalValueNotReachedCode() {
    addCouponCode(minimalCoupon);

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Minimalna wartość zamówienia dla tego kuponu to 3 000,00 zł.", getAlertMessage(), "Incorrect message");
  }

  @Test
  public void addingNotForPromoProductsCode() {
    addCouponCode(noPromoCode);

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Przepraszamy, ten kupon nie może być zastosowany do przecenionych produktów.", getAlertMessage(), "Incorrect message");
  }

  @Test
  public void addingWrongCategoryCode() {
    addCouponCode(windsurfingCode);

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Przepraszamy, tego kuponu nie można zastosować do wybranych produktów.", getAlertMessage(), "Incorrect message");
  }

  @Test
  public void addingExpiredCode() {
    addCouponCode(expiredCode);
    waitForProcessingEnd();

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Ten kupon stracił ważność.", getAlertMessage(), "Incorrect message");
  }

  @Test
  public void remove1Code() {
    addCouponCode(correctCoupon);
    waitForProcessingEnd();
    By removeLink = By.cssSelector("a[class ='woocommerce-remove-coupon']");
    wait.until(ExpectedConditions.elementToBeClickable(removeLink)).click();
    waitForProcessingEnd();

    String expectedCartValue = "2 799,00 zł";
    String actualCartValue = getCartValue();
    Assertions.assertEquals(expectedCartValue, actualCartValue, "Incorrect cart value");

    Assertions.assertEquals("Kupon został usunięty.", getAlertMessage(), "Incorrect message");
  }

}
