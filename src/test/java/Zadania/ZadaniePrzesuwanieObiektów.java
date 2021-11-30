package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ZadaniePrzesuwanieObiektów {
  WebDriver driver;
  Actions actions;
  WebElement draggableElement;
  WebElement dropElement;



  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    driver.navigate().to("https://fakestore.testelka.pl/actions/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();

    actions = new Actions(driver);
    draggableElement = driver.findElement(By.cssSelector("#draggable"));
    dropElement = driver.findElement(By.cssSelector("#droppable"));
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", draggableElement);
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void DragYellowSquare () {
    actions.dragAndDrop(draggableElement, dropElement).build().perform();
    Assertions.assertEquals("Dropped!", dropElement.getText());
  }

  @Test
  public void DragYellowSquareToBottomCorner () {
    actions.clickAndHold(draggableElement).moveToElement(dropElement, 149, 149).release().build().perform();
    Assertions.assertEquals("Dropped!", dropElement.getText());
  }

  @Test
  public void DragYellowSquareByDistance () {
    actions.dragAndDropBy(draggableElement, 160, 40).build().perform();

    Assertions.assertEquals("Dropped!", dropElement.getText());
  }


}
