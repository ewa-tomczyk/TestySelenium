package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AutomationPracticeSearchByCategory {
  WebDriver driver;
  WebDriverWait wait;
  public WebElement expectedProduct;
  public WebElement unExpectedProduct;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    Actions act=new Actions(driver);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    driver.navigate().to("http://automationpractice.multiformis.com/index.php");
    WebElement ele = driver.findElement(By.xpath(".//a[text()= 'Women']"));
    WebElement we =driver.findElement(By.xpath(".//*[text()= 'Women']/following-sibling::ul/li/following-sibling::li/ul/li[3]/a"));
    act.moveToElement(ele).moveToElement(we).click().build().perform();

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  private WebElement getExpectedProduct() {
    return driver.findElement(By.cssSelector("[id= 'product_list'] h5 a[title = 'Printed Chiffon Dress']"));
  }

  private WebElement getUnExpectedProduct() {
    return driver.findElement(By.cssSelector("[id= 'product_list'] h5 a[title = 'Printed Summer Dress']"));
  }

  @Test
  public void searchOnCategoryPage() {

    expectedProduct = getExpectedProduct();
    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product is not found");
  }

  @Test
  public void searchByFilters() {
    driver.findElement(By.xpath(".//a[text()= 'S']")).click();
    driver.findElement(By.xpath(".//a[text()= 'Green']")).click();
    driver.findElement(By.xpath(".//a[text()= 'Midi Dress']")).click();
    driver.findElement(By.xpath(".//a[text()= 'Polyester']")).click();

    unExpectedProduct = getUnExpectedProduct();
    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product is not found");
  }
}
