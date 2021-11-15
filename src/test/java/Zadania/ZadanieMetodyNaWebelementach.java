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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ZadanieMetodyNaWebelementach {
  WebDriver driver;
  WebElement elements;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://fakestore.testelka.pl/metody-na-elementach/");
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }


  @Test
  public void MetodyWebElementy() {
    WebElement element = driver.findElement(By.cssSelector("[name= 'main-page']"));
    boolean buttonStronaGłówna = element.isEnabled();

    assertEquals(false, buttonStronaGłówna, "Button isn't active");

    WebElement element1 = driver.findElement(By.cssSelector("a[name='sailing']"));
    boolean invisibleButton = element1.isDisplayed();

    assertEquals(false, invisibleButton, "Button is visible");

    WebElement element2 = driver.findElement(By.cssSelector("a[class='button']"));
    String buttonColour = element2.getCssValue("background-color");

    assertEquals("rgba(245, 233, 101, 1)", buttonColour, "Button isn't yellow");

    WebElement element3 = driver.findElement(By.cssSelector("input[name='selected-checkbox']"));
    boolean isCheckboxMarked = element3.isSelected();

    assertEquals(true, isCheckboxMarked, "Checkbox isn't marked");

    WebElement element4 = driver.findElement(By.cssSelector("input[name='not-selected-checkbox']"));
    boolean isCheckboxNotMarked = element4.isSelected();

    assertEquals(false, isCheckboxNotMarked, "Checkbox isn't marked");

    List<WebElement> elementWithButtonClass = driver.findElements(By.cssSelector(".button"));
    assertElementsHaveCorrectTag(elementWithButtonClass);

  }

  public void assertElementsHaveCorrectTag (List<WebElement> elements) {
    for (WebElement element : elements) {
      Assertions.assertEquals("a", element.getTagName());
    }
  }

}
