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
import java.util.List;

public class ZadanieAkcje {
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
    driver.navigate().to("https://fakestore.testelka.pl/actions/");
    actions = new Actions(driver);
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void cartCheck () {

    List<WebElement> menuOptions = driver.findElements(By.cssSelector("#div-context-menu li"));

    WebElement menu = driver.findElement(By.cssSelector("#menu-link"));

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menu);
    actions.keyDown(Keys.CONTROL).click(menu).keyUp(Keys.CONTROL).click(menuOptions.get(2)).build().perform();
    String cartPage = driver.findElement(By.cssSelector("#main .entry-title")).getText();

    Assertions.assertEquals("Koszyk", cartPage, "User isn't on cart page");
  }

  @Test
  public void colorCheck () {

    WebElement box = driver.findElement(By.cssSelector("div #double-click"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", box);

    actions.doubleClick(box).build().perform();
    String color = box.getCssValue("background-color");

    Assertions.assertEquals("rgba(245, 93, 122, 1)", color, "Color isn't pink");
  }

  @Test
  public void textCheck () {
    String text = "myself";
    WebElement textField = driver.findElement(By.cssSelector("#input"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textField);

    WebElement button = driver.findElement(By.cssSelector("[onclick='zatwierdzTekst()']"));
    actions.sendKeys(textField, text).click(button).build().perform();
    String message = driver.findElement(By.cssSelector("#output")).getText();

    Assertions.assertEquals("Wprowadzony tekst: " + text , message, "Wrong text");
  }

  @Test
  public void numberCheck () {
    WebElement numberField = driver.findElement(By.cssSelector("ol#selectable"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", numberField);

    List<WebElement> itemy = driver.findElements(By.cssSelector("ol#selectable li"));

    actions.keyDown(Keys.COMMAND).click(itemy.get(0)).click(itemy.get(4)).click(itemy.get(6)).keyUp(Keys.COMMAND).build().perform();

    Assertions.assertAll(
            ()->Assertions.assertTrue(itemy.get(0).getAttribute("class").contains("ui-selected"), "Item wasn't selected"),
            ()->Assertions.assertTrue(itemy.get(4).getAttribute("class").contains("ui-selected"), "Item wasn't selected"),
            ()->Assertions.assertTrue(itemy.get(6).getAttribute("class").contains("ui-selected"), "Item wasn't selected")
            );
  }

}
