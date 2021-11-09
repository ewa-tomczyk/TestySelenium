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

public class AutomationPracticeSearchByCategory {
  public WebDriver driver;
  public String expectedProductName;

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
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  private String getExpectedProductName() {
    return driver.findElement(By.cssSelector("h5 a[title = 'Printed Chiffon Dress']")).getText();
  }

  @Test
  public void searchCategory() {

    expectedProductName = getExpectedProductName();
    Assertions.assertEquals("Printed Chiffon Dress", expectedProductName, "Product is not found");
  }

  @Test
  public void searchByCriteria() {
    driver.findElement(By.cssSelector("[title= 'Short dress, long dress, silk dress, printed dress, you will find the perfect dress for summer.']")).click();
    expectedProductName = getExpectedProductName();

    Assertions.assertEquals("Printed Chiffon Dress", expectedProductName, "Product is not found");
  }
}
