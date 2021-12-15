package DriverMethods;

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

public class TestelkaProjectCart {
  WebDriver driver;
  WebDriverWait wait;



  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.navigate().to("https://fakestore.testelka.pl/");

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void addingProductFromProductPage() {
    driver.navigate().to("https://fakestore.testelka.pl/product/kurs-zeglarski-na-mazurach/");
    WebElement addToCart = driver.findElement(By.cssSelector("[name='add-to-cart']"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", addToCart);
    addToCart.click();

    String message = "Zobacz koszyk\n" +
            "„Kurs żeglarski na Mazurach“ został dodany do koszyka.";
    Assertions.assertEquals(message, driver.findElement(By.cssSelector(".woocommerce-message ")).getText());
  }

  @Test
  public void addingProductFromCategoryPage() {
    driver.navigate().to("https://fakestore.testelka.pl/product-category/windsurfing/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    driver.findElement(By.cssSelector("a[href$='egipt-el-gouna/']+a")).click();
    By seeCart = By.cssSelector("[title='Zobacz koszyk']");

    wait.until(ExpectedConditions.presenceOfElementLocated(seeCart));

    driver.findElement(seeCart).click();
    String cartProduct = driver.findElement(By.cssSelector(".product-name a[href$='el-gouna/']")).getText();
    Assertions.assertEquals("Egipt - El Gouna", cartProduct);
  }


  @Test
  public void adding10ProductsFromProductPage() {

    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();

    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
    driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".woocommerce-message")));

    driver.findElement(By.cssSelector(".cart-contents")).click();
    String cartAmount = driver.findElement(By.cssSelector(".quantity input")).getAttribute("value");
    Assertions.assertEquals("10", cartAmount, "Amount isn't 10");

  }

  @Test
  public void addingACoupleProductsFromProductPage() {
    driver.navigate().to("https://fakestore.testelka.pl/product/fuerteventura-sotavento/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    driver.findElement(By.cssSelector(".quantity input[type='number']")).clear();
    driver.findElement(By.cssSelector(".quantity input[type='number']")).sendKeys("7");
    driver.findElement(By.cssSelector("[name='add-to-cart']")).click();

    driver.findElement(By.cssSelector(".cart-contents")).click();
    String cartAmount = driver.findElement(By.cssSelector(".quantity input")).getAttribute("value");
    Assertions.assertEquals("7", cartAmount, "Amount isn't 7");
  }

  @Test
  public void adding10DifferentProductsFromHomePage() {
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-popular-products'] li a[href='?add-to-cart=4116']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-popular-products'] li a[href='?add-to-cart=4114']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-popular-products'] li a[href='?add-to-cart=393']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-popular-products'] li a[href='?add-to-cart=391']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-on-sale-products'] li a[href='?add-to-cart=61']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-on-sale-products'] li a[href='?add-to-cart=53']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-on-sale-products'] li a[href='?add-to-cart=46']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-on-sale-products'] li a[href='?add-to-cart=40']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-best-selling-products'] li a[href='?add-to-cart=386']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));
    driver.findElement(By.cssSelector("[class='storefront-product-section storefront-best-selling-products'] li a[href='?add-to-cart=389']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart loading']")));

    driver.findElement(By.cssSelector(".cart-contents")).click();
    int cartAmount = driver.findElements(By.cssSelector("[aria-label='Usuń produkt']")).size();
    Assertions.assertEquals(10, cartAmount, "Amount isn't 10");
  }

  }


  //driver.navigate().to("https://fakestore.testelka.pl/product-category/windsurfing/");
  //    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
  //    WebElement product1 =  driver.findElement(By.cssSelector("a[href$='egipt-el-gouna/']+a"));
  //
  //    product1.click();
  //    By seeCart = By.cssSelector("[title='Zobacz koszyk']");

  //WebElement product1 =  driver.findElement(By.cssSelector("a[href$='fuerteventura-sotavento/']+a"));

   // product1.click();
   // driver.navigate().refresh();
  //
  //    wait.until(ExpectedConditions.presenceOfElementLocated(seeCart));
  //String cartAmount = driver.findElement(By.cssSelector(".cart-contents span[class= 'count']")).getText();
    //Assertions.assertEquals("10 Produktów", cartAmount);


