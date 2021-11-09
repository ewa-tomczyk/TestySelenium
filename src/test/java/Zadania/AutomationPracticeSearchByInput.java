package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class AutomationPracticeSearchByInput {
  public WebDriver driver;
  public WebElement expectedProduct;
  public WebElement expectedShirtProduct;


  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
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

  private WebElement getExpectedProduct() {
    return driver.findElement(By.cssSelector("h5 a[title = 'Printed Chiffon Dress']"));
  }

  private WebElement getExpectedShirtProduct() {
    return driver.findElement(By.cssSelector("h5 a[title='Faded Short Sleeves T-shirt']"));
  }

  @Test
  public void searchFirstWord() {
    searchByInput("Printed");
    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product not found");
  }

  @Test
  public void searchFieldMiddleWord() {
    searchByInput("Chiffon");
    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product not found");
  }

  @Test
  public void searchCapitalLetterProduct() {
    searchByInput("CHIFFON");
    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product not found");
  }

  @Test
  public void searchLowerLettersProduct() {
    searchByInput("chiffon");
    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product not found");
  }

  @Test
  public void searchFirstLettersProduct() {
    searchByInput("dre");
    expectedProduct = getExpectedProduct();

    Assertions.assertTrue((expectedProduct).isDisplayed(), "Product not found");
  }

  @Test
  public void searchShirtProduct() {
    searchByInput("Faded");
    expectedShirtProduct = getExpectedShirtProduct();

    Assertions.assertTrue((expectedShirtProduct).isDisplayed(), "Product not found");
  }

  @Test
  public void searchIncorrectRecord() {
    String alertMessage;
    String expectedMessage = "No results were found for your search \"chiffrrron\"";
    searchByInput("chiffrrron");
    alertMessage = getAlertMessage();

    Assertions.assertEquals(expectedMessage, alertMessage, "Wrong message");
  }

  @Test
  public void searchEmptyRecord() {
    String alertMessage;
    String expectedMessage = "Please enter a search keyword";
    searchByInput("");
    alertMessage = getAlertMessage();

    Assertions.assertEquals(expectedMessage, alertMessage, "Wrong message");
  }

  //Ten test dla słowa "Dress" nie działa za każdym razem więc jego użyteczność jest wątpliwa
  @Test
  public void searchFieldResultCount() {
    searchByInput("Summer");
    int expectedNumberOfRecords = 4;
    int actualNumberOfRecords = driver.findElements(By.cssSelector("ul[id='product_list'] img")).size();

    Assertions.assertEquals(expectedNumberOfRecords,actualNumberOfRecords, "Incorrect number of records");
  }
}
