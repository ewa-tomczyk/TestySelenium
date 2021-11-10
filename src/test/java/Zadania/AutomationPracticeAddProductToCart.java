package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AutomationPracticeAddProductToCart {
  WebDriver driver;
  WebDriverWait wait;


  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    driver.navigate().to("http://automationpractice.multiformis.com/index.php");

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  public void add1ProductToCartFromCategoryPage(){
    driver.findElement(By.cssSelector("[id ='homefeatured'] h5 a[title = 'Printed Chiffon Dress']")).click();
    driver.findElement(By.cssSelector("[id= 'add_to_cart'] button")).click();
    driver.findElement(By.cssSelector("[title='Proceed to checkout']")).click();
  }

  public void add2ProductsToCartFromCategoryPage(){
    driver.findElement(By.cssSelector("[id ='homefeatured'] h5 a[title = 'Printed Chiffon Dress']")).click();
    driver.findElement(By.cssSelector("[class= 'icon-plus']")).click();
    driver.findElement(By.cssSelector("[id= 'add_to_cart'] button")).click();
    driver.findElement(By.cssSelector("[title='Proceed to checkout']")).click();
  }

  public void addProductToCartFromHomepage() {
    Actions act = new Actions(driver);
    WebElement ele = driver.findElement(By.cssSelector("[id ='homefeatured'] h5 a[title = 'Printed Chiffon Dress']"));
    WebElement we = driver.findElement(By.cssSelector("[class= 'ajax_block_product col-xs-12 col-sm-4 col-md-3 last-line first-item-of-tablet-line first-item-of-mobile-line last-mobile-line'] [title= 'Add to cart']"));
    act.moveToElement(ele).moveToElement(we).click().build().perform();
    driver.findElement(By.cssSelector("[title='Proceed to checkout']")).click();
  }

  public void checkCart() {
    driver.findElement(By.cssSelector("div[class= 'shopping_cart'] b")).click();
  }

  @Test
  public void addingProductFromCategoryPage(){
    add1ProductToCartFromCategoryPage();
    checkCart();

    WebElement actualProduct = driver.findElement(By.cssSelector("td[class= 'cart_description'] p a"));
    Assertions.assertTrue((actualProduct).isDisplayed(), "No product in the cart");
  }

  @Test
  public void adding2ProductsFromCategoryPage(){
    add2ProductsToCartFromCategoryPage();
    checkCart();

    String actualDoubleProduct = driver.findElement(By.cssSelector("[name = 'quantity_7_34_0_0_hidden']")).getAttribute("value");
    Assertions.assertEquals("2", actualDoubleProduct, "Incorrect quantity");
  }

  @Test
  public void addingProductFromHomepage() {
    addProductToCartFromHomepage();
    checkCart();

    WebElement actualProduct = driver.findElement(By.cssSelector("td[class= 'cart_description'] p a"));
    Assertions.assertTrue((actualProduct).isDisplayed(), "No product in the cart");
  }

}
