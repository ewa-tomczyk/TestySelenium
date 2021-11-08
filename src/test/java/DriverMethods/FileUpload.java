package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class FileUpload {
  WebDriver driver;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));

    driver.navigate().to("https://gofile.io/uploadFiles");
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }

  @Test
  public void fileUploadTest() {
    WebElement uploadFileInput = driver.findElement(By.cssSelector("input[type='file']"));
    String expectedFileName = "pic.jpg";
    String path ="/Users/ewatomczyk/Desktop/" +expectedFileName;
    uploadFileInput.sendKeys(path);
    driver.findElement(By.xpath(".//*[@class='banner_continueBtn--3KNKl']/span[contains(text(), 'Continue to Site')]")).click();
    //*driver.findElement(By.cssSelector("button[id='rowUploadSuccess-showFiles']")).click();

    String actualFileNameElement = driver.findElement(By.cssSelector("div[id='rowFolder-tableContent'] span[class='contentName']")).getText();

    Assertions.assertEquals(expectedFileName, actualFileNameElement, "Name is different");

  }
}
