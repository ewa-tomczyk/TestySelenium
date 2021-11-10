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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AutomationPracticeAddProductToCart {
  WebDriver driver;
  WebDriverWait wait;
  public WebElement actualMessage;


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

@Test
  public void addProductToCart(){
    driver.findElement(By.cssSelector("[id ='homefeatured'] h5 a[title = 'Printed Chiffon Dress']")).click();
    driver.findElement(By.cssSelector("[id= 'add_to_cart'] button")).click();


  actualMessage = driver.findElement(By.xpath(".//span[contains(text(),'There is 1 item in your cart.')]"));

  Assertions.assertTrue((actualMessage).isDisplayed(), "Product not in the cart");

  }
}
