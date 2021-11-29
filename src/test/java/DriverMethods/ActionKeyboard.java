package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class ActionKeyboard {

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
  public void sendKeysExample() {
  driver.navigate().to("https://fakestore.testelka.pl/moje-konto/");
  WebElement login = driver.findElement(By.cssSelector("#username"));

  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", login);

  //actions.sendKeys(login, Keys.SHIFT,"testowy user").build().perform();

  actions.click(login).sendKeys(Keys.SHIFT,"testowy user").build().perform();
}
  @Test
  public void pressingKeysExample() {
    driver.navigate().to("http://jqueryui.com/selectable/#default");

    driver.switchTo().frame(0);
    List<WebElement> itemy = driver.findElements(By.cssSelector("li.ui-selectee"));
    actions.keyDown(Keys.CONTROL).click(itemy.get(0)).click(itemy.get(1)).click(itemy.get(2)).keyUp(Keys.CONTROL).click(itemy.get(4)).build().perform();
  }
  }