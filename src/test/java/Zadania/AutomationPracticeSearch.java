package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class AutomationPracticeSearch {
  WebDriver driver;
  String alertMessage;
  String expectedProductName;
  WebElement expectedProduct;
  WebElement expectedShirtProduct;

  act.mo


  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    Actions act=new Actions(driver);
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    driver.navigate().to("http://automationpractice.multiformis.com/index.php");
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  private void searchByInput(String searchInput) {
    driver.findElement(By.cssSelector("input[id = 'search_query_top']")).sendKeys(searchInput);
    driver.findElement(By.cssSelector("[name='submit_search']")).click();
  }

  private String getAlertMessage () {
    return driver.findElement(By.cssSelector("[class = 'alert alert-warning']")).getText();

  }

  private int getNumberOfRecords () {
    return driver.findElements(By.cssSelector("ul[id='product_list'] img")).size();
  }

  private String getExpectedProductName() {
    return driver.findElement(By.cssSelector("h5 a[title = 'Printed Chiffon Dress']")).getText();
  }

  private WebElement getExpectedProduct() {
    return driver.findElement(By.cssSelector("h5 a[title = 'Printed Chiffon Dress']"));
  }

  private WebElement getExpectedShirtProduct() {
    return driver.findElement(By.cssSelector("h5 a[title='Faded Short Sleeves T-shirt']"));
  }



  @Test
  public void navigateToCategory () {
    Actions action = new Actions(driver);
    WebElement we = driver.findElement(By.xpath(".//*[@id='block_top_menu']/ul/li/a"));
    WebElement item = driver.findElement(By.cssSelector("[class = 'sfHoverForce'] a[title = 'Summer Dresses']"));
    action.moveToElement(item).moveToElement(we).click().build().perform();
  }


  // Search input tests
  @Test
  public void searchFirstWord() {
    searchByInput("Printed");

    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed());
  }

  @Test
  public void searchFieldMiddleWord() {
    searchByInput("Chiffon");

    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed());
  }

  @Test
  public void searchShirtProduct() {
    searchByInput("Faded");

    expectedShirtProduct = getExpectedShirtProduct();

    Assertions.assertTrue((expectedShirtProduct).isDisplayed());
  }

  @Test
  public void searchCapitalLetterProduct() {
    searchByInput("CHIFFON");

    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed());
  }

  @Test
  public void searchLowerLettersProduct() {
    searchByInput("chiffon");

    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed());
  }

  @Test
  public void searchIncorrectRecord() {
    searchByInput("chiffron");
    alertMessage = getAlertMessage();

    Assertions.assertEquals("No results were found for your search \"chiffron\"", alertMessage, "Wrong message");
  }

  @Test
  public void searchEmptyRecord() {
    searchByInput("");
    alertMessage = getAlertMessage();

    Assertions.assertEquals("Please enter a search keyword", alertMessage, "Wrong message");
  }

  @Test
  public void searchFieldResultCount() {
    searchByInput("Dress");
    int expectedNumberOfRecords = 7;
    int actualNumberOfRecords = getNumberOfRecords();

    Assertions.assertEquals(expectedNumberOfRecords,actualNumberOfRecords );
  }

  // Category search
  @Test
  public void searchCategory() {
    driver.navigate().to("http://automationpractice.multiformis.com/index.php?id_category=11&controller=category");

    expectedProductName = getExpectedProductName();

    Assertions.assertEquals("Printed Chiffon Dress", expectedProductName, "Product is not found");

  }

  @Test
  public void searchByCriteria() {
    driver.navigate().to("http://automationpractice.multiformis.com/index.php?id_category=8&controller=category");
    driver.findElement(By.cssSelector("[title= 'Short dress, long dress, silk dress, printed dress, you will find the perfect dress for summer.']")).click();

    expectedProductName = getExpectedProductName();

    Assertions.assertEquals("Printed Chiffon Dress", expectedProductName, "Product is not found");
  }
}
