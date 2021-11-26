package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class Click {
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
  public void click() {
    driver.navigate().to("http://jqueryui.com/selectable/#default");
    //actions.moveByOffset(488, 380).click().build().perform();
    driver.switchTo().frame(0);
    List<WebElement> listElements = driver.findElements(By.cssSelector("#selectable>li"));
    WebElement firstElement = listElements.get(0);
    actions.click(firstElement).build().perform();
  }

  @Test
  public void doubleClick() {
    driver.navigate().to("https://www.plus2net.com/javascript_tutorial/ondblclick-demo.php");
    //actions.moveByOffset(362, 173).doubleClick().build().perform();
    WebElement box = driver.findElement(By.cssSelector("#box"));
    actions.doubleClick(box).build().perform();
  }

  @Test
  public void contextClick() {
    driver.navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo.html");
    WebElement editOptions = driver.findElement(By.cssSelector(".context-menu-icon-edit"));
    WebElement button = driver.findElement(By.cssSelector(".context-menu-one"));
    //actions.moveByOffset(461, 195).contextClick().click(editOptions).build().perform();
    actions.contextClick(button).click(editOptions).build().perform();
  }
}
