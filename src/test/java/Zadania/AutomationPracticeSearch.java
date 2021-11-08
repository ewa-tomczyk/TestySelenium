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


  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
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
    return driver.findElements(By.xpath(".//*[starts-with(@class, 'ajax_block_product ')]")).size();
  }




  // Search onput tests
  @Test
  public void searchFirstWord() {
    searchByInput("Printed");

    Assertions.assertTrue(driver.findElement(By.cssSelector("a[class = 'product_img_link'] [title='Printed Chiffon Dress']")).isDisplayed());
  }

  @Test
  public void searchFieldMiddleWord() {
    searchByInput("Chiffon");

    Assertions.assertTrue(driver.findElement(By.cssSelector("a[class = 'product_img_link'] [title='Printed Chiffon Dress']")).isDisplayed());
  }

  @Test
  public void searchShirtProduct() {
    searchByInput("Faded");

    Assertions.assertFalse(driver.findElement(By.cssSelector("a[class = 'product_img_link'] [title='Printed Chiffon Dress']")).isDisplayed());
  }

  @Test
  public void searchCapitalLetterProduct() {
    searchByInput("CHIFFON");

    Assertions.assertTrue(driver.findElement(By.cssSelector("a[class = 'product_img_link'] [title='Printed Chiffon Dress']")).isDisplayed());
  }
  @Test
  public void searchLowerLettersProduct() {
    searchByInput("chiffon");

    Assertions.assertTrue(driver.findElement(By.cssSelector("a[class = 'product_img_link'] [title='Printed Chiffon Dress']")).isDisplayed());
  }

  @Test
  public void searchIncorrectRecord() {
    searchByInput("chiffron");
    alertMessage = getAlertMessage();

    Assertions.assertEquals("No results were found for your search \"chiffron\"", alertMessage, "Wrong message");
  }

  @Test
  public void searchFEmptyRecord() {
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


    Assertions.assertFalse(driver.findElement(By.cssSelector("h5 a[title='Faded Short Sleeves T-shirt']")).isDisplayed());
    Assertions.assertTrue(driver.findElement(By.cssSelector("h5 a[title = 'Printed Chiffon Dress']")).isDisplayed());

  }
}
