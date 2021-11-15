package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class MetodyNaWebelementach {
  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://fakestore.testelka.pl/");
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  @Test
  public void infoOnElement() {
    WebElement element = driver.findElement(By.cssSelector("#masthead"));

    String text = element.getText();
    String attribute = element.getAttribute("role");
    String cssValue = element.getCssValue("background-color");
    String tag = element.getTagName();
    Point location = element.getLocation();
    Dimension size = element.getSize();
    Rectangle locationAndSize = element.getRect();
    boolean isDislpayed = element.isDisplayed();
    boolean isSelected = element.isSelected();
    boolean isEnabled = element.isEnabled();

  }
}
