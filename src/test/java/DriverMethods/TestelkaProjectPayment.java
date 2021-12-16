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
}
