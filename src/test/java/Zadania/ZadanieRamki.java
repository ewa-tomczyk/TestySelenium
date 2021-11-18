package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class ZadanieRamki {
  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://fakestore.testelka.pl/cwiczenia-z-ramek/");

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void mainPageButtonDisabledTest() {
    driver.switchTo().frame("main-frame");
    WebElement mainPageButton = driver.findElement(By.cssSelector("input[name='main-page']"));

    Assertions.assertFalse(mainPageButton.isEnabled(), "Buton is enabled");
  }

  @Test
  public void imageLinkTest() {
    driver.switchTo().frame("main-frame")
            .switchTo().frame("image");

    WebElement mainPageLink = driver.findElement(By.xpath(".//img[@alt='Wakacje']/.."));
    Assertions.assertEquals("https://fakestore.testelka.pl/", mainPageLink.getAttribute("href"), "Link isn't correct");
  }

  @Test
  public void mainPageButtonEnabledTest() {
    driver.switchTo().frame("main-frame")
            .switchTo().frame("image")
            .switchTo().frame(0);
    WebElement mainPageButton = driver.findElement(By.cssSelector("a.button"));
    Assertions.assertTrue(mainPageButton.isEnabled(), "Buton isn't enabled");
  }

  @Test
  public void logoDisplayedTest() {
    driver.switchTo().frame("main-frame")
            .switchTo().frame("image")
            .switchTo().frame(0);
    WebElement mainPageButton = driver.findElement(By.cssSelector("a.button"));
    mainPageButton.click();
    driver.switchTo().parentFrame()
            .switchTo().parentFrame();

    WebElement climbingButton = driver.findElement(By.cssSelector("a[name='climbing']"));
    climbingButton.click();

    WebElement logo = driver.findElement(By.cssSelector("img.custom-logo"));
    Assertions.assertTrue(logo.isDisplayed(), "Logo isn't displayed");

  }
  }