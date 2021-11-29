package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class DragAndDrop {
  WebDriver driver;
  Actions actions;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1290, 730));
    driver.manage().window().setPosition(new Point(8, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    actions = new Actions(driver);
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void Drag () {
    driver.navigate().to("https://marcojakob.github.io/dart-dnd/detection_only/");
    WebElement draggableElement = driver.findElement(By.cssSelector(".draggable"));
    //actions.dragAndDropBy(draggableElement, 20, 20).build().perform();

    //92,130
    //72, 110
    //actions.clickAndHold(draggableElement).moveByOffset(20, 20).release().build().perform();
    actions.moveToElement(draggableElement).clickAndHold().moveByOffset(20, 20).release().build().perform();
  }
  @Test
  public void Drag1 () {
    driver.navigate().to("https://marcojakob.github.io/dart-dnd/nested_dropzones/");
    WebElement draggableElement = driver.findElement(By.cssSelector(".draggable"));
    WebElement dropElement = driver.findElement(By.cssSelector(".dropzone-inner"));
    //actions.dragAndDrop(draggableElement, dropElement).build().perform();
    //actions.clickAndHold(draggableElement).moveToElement(dropElement).release().build().perform();
    //actions.clickAndHold(draggableElement).release(dropElement).build().perform();
    actions.clickAndHold(draggableElement).moveToElement(dropElement, 2, 2).build().perform();
  }
}
