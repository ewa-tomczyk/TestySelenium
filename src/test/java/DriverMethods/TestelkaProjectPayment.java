package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestelkaProjectPayment {
  WebDriver driver;
  WebDriverWait wait;
  String correctusername = "ewa.tomczyk";
  String correctpassword = "Ewa171717!";
  String newUsername = "ewa.tomczyk+7@10clouds.com";
  String newPassword ="+Pass12345!";

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.navigate().to("https://fakestore.testelka.pl/");

    wait = new WebDriverWait(driver, Duration.ofSeconds(15));

  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void errorsOnPaymentPage() {
    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();

    driver.findElement(By.cssSelector(".cart-contents")).click();
    driver.findElement(By.cssSelector("[class='checkout-button button alt wc-forward']")).click();
    WebElement scrollElement = driver.findElement(By.cssSelector("[name='woocommerce_checkout_place_order']"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", scrollElement);
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.findElement(By.cssSelector("[name='woocommerce_checkout_place_order']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    WebElement scrollMessage = driver.findElement(By.cssSelector("[class='woocommerce_error woocommerce-error wc-stripe-error']"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", scrollMessage);
    String errorMessage = driver.findElement(By.cssSelector("[class='woocommerce_error woocommerce-error wc-stripe-error']")).getText();

    Assertions.assertEquals("Numer karty jest niekompletny.", errorMessage);
  }

  @Test
  public void loginOnPaymentPage() {
    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();

    driver.findElement(By.cssSelector(".cart-contents")).click();
    driver.findElement(By.cssSelector("[class='checkout-button button alt wc-forward']")).click();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.findElement(By.cssSelector(".showlogin")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username")));
    driver.findElement(By.cssSelector("#username")).sendKeys(correctusername);
    driver.findElement(By.cssSelector("#password")).sendKeys(correctpassword);
    driver.findElement(By.cssSelector("[name='login']")).click();
    driver.findElement(By.cssSelector("#menu-menu a[href$='/moje-konto/']")).click();

    WebElement account = driver.findElement(By.cssSelector(".delete-me"));
    Assertions.assertTrue(account.isDisplayed(), "User isn't logged in");
  }

  @Test
  public void registrationOnPaymentPage() {
    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();

    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector(".cart-contents")).click();
    driver.findElement(By.cssSelector("[class='checkout-button button alt wc-forward']")).click();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.findElement(By.cssSelector("#billing_first_name")).sendKeys("Ewa");
    driver.findElement(By.cssSelector("#billing_last_name")).sendKeys("Test");
    driver.findElement(By.cssSelector("#billing_address_1")).sendKeys("Test Address");
    driver.findElement(By.cssSelector("#billing_postcode")).sendKeys("12345");
    driver.findElement(By.cssSelector("#billing_city")).sendKeys("Testland");
    driver.findElement(By.cssSelector("#billing_phone")).sendKeys("1234567890");
    driver.findElement(By.cssSelector("#billing_email")).sendKeys(newUsername);
    driver.findElement(By.cssSelector("#createaccount")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.findElement(By.cssSelector("#account_password")).sendKeys(newPassword);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='woocommerce-password-strength good']")));
    driver.switchTo().frame(0);
    WebElement cardNumber = driver.findElement(By.cssSelector(".InputContainer input[name='cardnumber']"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", cardNumber);
    cardNumber.sendKeys("4242424242424242");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(1);
    driver.findElement(By.cssSelector("[name='exp-date']")).sendKeys("1122");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(2);
    driver.findElement(By.cssSelector("[name='cvc']")).sendKeys("111");
    driver.switchTo().defaultContent();
    driver.findElement(By.cssSelector("#terms")).click();
    driver.findElement(By.cssSelector("#place_order")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));


    Assertions.assertEquals("Zamówienie otrzymane", driver.findElement(By.cssSelector("h1[class='entry-title']")).getText(), "Order wasn't placed");
    Assertions.assertTrue(driver.findElement(By.cssSelector("[class='woocommerce-order-overview__order order']")).isDisplayed());
    Assertions.assertEquals("DATA:\n" + "28 grudnia, 2021", driver.findElement(By.cssSelector("[class='woocommerce-order-overview__date date']")).getText());
    Assertions.assertEquals("3 600,00 zł", driver.findElement(By.cssSelector("[class='woocommerce-Price-amount amount'] bdi")).getText());
    Assertions.assertEquals("Karta debetowa/kredytowa (Stripe)", driver.findElement(By.cssSelector("[class='woocommerce-order-overview__payment-method method'] strong")).getText());
    Assertions.assertEquals("Fuerteventura - Sotavento × 1", driver.findElement(By.cssSelector("tbody [class='woocommerce-table__product-name product-name']")).getText());
  }

  @Test
  public void paymentWithoutRegistration() {
    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();

    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector(".cart-contents")).click();
    driver.findElement(By.cssSelector("[class='checkout-button button alt wc-forward']")).click();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.findElement(By.cssSelector("#billing_first_name")).sendKeys("Ewa");
    driver.findElement(By.cssSelector("#billing_last_name")).sendKeys("Test");
    driver.findElement(By.cssSelector("#billing_address_1")).sendKeys("Test Address");
    driver.findElement(By.cssSelector("#billing_postcode")).sendKeys("12345");
    driver.findElement(By.cssSelector("#billing_city")).sendKeys("Testland");
    driver.findElement(By.cssSelector("#billing_phone")).sendKeys("1234567890");
    driver.findElement(By.cssSelector("#billing_email")).sendKeys("ewa.tomczyk+3@10clouds.com");
    driver.switchTo().frame(0);
    WebElement cardNumber = driver.findElement(By.cssSelector(".InputContainer input[name='cardnumber']"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", cardNumber);
    cardNumber.sendKeys("4242424242424242");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(1);
    driver.findElement(By.cssSelector("[name='exp-date']")).sendKeys("1122");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(2);
    driver.findElement(By.cssSelector("[name='cvc']")).sendKeys("111");
    driver.switchTo().defaultContent();
    driver.findElement(By.cssSelector("#terms")).click();
    driver.findElement(By.cssSelector("#place_order")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    Assertions.assertEquals("Zamówienie otrzymane", driver.findElement(By.cssSelector("h1[class='entry-title']")).getText(), "Order wasn't placed");
  }



  @Test
  public void ordersInMyAccount() {
    driver.findElement(By.cssSelector(".primary-navigation a[href$='/moje-konto/']")).click();
    driver.findElement(By.cssSelector("#username")).sendKeys(correctusername);
    driver.findElement(By.cssSelector("#password")).sendKeys(correctpassword);
    driver.findElement(By.cssSelector("[name='login']")).click();
    driver.findElement(By.cssSelector("ul a[href$='/zamowienia/']")).click();

    Assertions.assertTrue(driver.findElement(By.cssSelector("[class='woocommerce-Button button']")).isDisplayed());
  }

  @Test
  public void checkingOrder() {
    driver.findElement(By.cssSelector(".primary-navigation a[href$='/moje-konto/']")).click();
    driver.findElement(By.cssSelector("#username")).sendKeys(newUsername);
    driver.findElement(By.cssSelector("#password")).sendKeys(newPassword);
    driver.findElement(By.cssSelector("[name='login']")).click();

    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();

    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector(".cart-contents")).click();
    driver.findElement(By.cssSelector("[class='checkout-button button alt wc-forward']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.findElement(By.cssSelector("#billing_first_name")).sendKeys("Ewa");
    driver.findElement(By.cssSelector("#billing_last_name")).sendKeys("Test");
    driver.findElement(By.cssSelector("#billing_address_1")).sendKeys("Test Address");
    driver.findElement(By.cssSelector("#billing_postcode")).sendKeys("12345");
    driver.findElement(By.cssSelector("#billing_city")).sendKeys("Testland");
    driver.findElement(By.cssSelector("#billing_phone")).sendKeys("1234567890");

    driver.switchTo().frame(0);
    WebElement cardNumber = driver.findElement(By.cssSelector(".InputContainer input[name='cardnumber']"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", cardNumber);
    cardNumber.sendKeys("4242424242424242");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(1);
    driver.findElement(By.cssSelector("[name='exp-date']")).sendKeys("1122");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(2);
    driver.findElement(By.cssSelector("[name='cvc']")).sendKeys("111");
    driver.switchTo().defaultContent();
    driver.findElement(By.cssSelector("#terms")).click();
    driver.findElement(By.cssSelector("#place_order")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class='blockUI blockOverlay']")));
    driver.navigate().refresh();

    Assertions.assertTrue(driver.findElement(By.cssSelector("[class='woocommerce-order-overview__order order']")).isDisplayed());
    Assertions.assertEquals("28 grudnia, 2021", driver.findElement(By.cssSelector("[class='woocommerce-order-overview__date date']")).getText());
    Assertions.assertEquals("3600,00&nbsp", driver.findElement(By.cssSelector("[class='woocommerce-Price-amount amount'] bdi")).getText());
    Assertions.assertEquals("Karta debetowa/kredytowa (Stripe)", driver.findElement(By.cssSelector("[class='woocommerce-order-overview__payment-method method'] strong")).getText());
    Assertions.assertEquals("Fuerteventura – Sotavento", driver.findElement(By.cssSelector("tbody [class='woocommerce-table__product-name product-name']")).getText());
    Assertions.assertEquals("× 1", driver.findElement(By.cssSelector(".product-quantity")).getText());

  }
}
