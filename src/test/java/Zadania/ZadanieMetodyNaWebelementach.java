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


public class ZadanieMetodyNaWebelementach {
  WebDriver driver;

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
    WebElement inactiveButton = driver.findElement(By.cssSelector("[name= 'main-page']"));
    WebElement hiddenButton = driver.findElement(By.cssSelector("a[name='sailing']"));
    List<WebElement> yellowButtons = driver.findElements(By.cssSelector(".button"));
    WebElement selectedCheckbox = driver.findElement(By.cssSelector("input[name='selected-checkbox']"));
    WebElement notSelectedCheckbox = driver.findElement(By.cssSelector("input[name='not-selected-checkbox']"));
    List<WebElement> elementsWithButtonClass = driver.findElements(By.cssSelector(".button"));

    Assertions.assertAll("Checking assertions of elements",
            () -> Assertions.assertFalse(inactiveButton.isEnabled(), "Button is not disabled"),
            () -> Assertions.assertFalse(hiddenButton.isDisplayed(), "Button is visible"),
            () -> checkIfButtonsAreYellow(yellowButtons),
            () -> Assertions.assertTrue(selectedCheckbox.isSelected(), "Checkbox isn't marked"),
            () -> Assertions.assertFalse(notSelectedCheckbox.isSelected(), "Checkbox isn't marked"),
            () -> assertElementsHaveCorrectTag(elementsWithButtonClass)
    );

  }

    public void checkIfButtonsAreYellow (List<WebElement> buttons) {
      for (WebElement button : buttons) {
        String color = button.getCssValue("background-color");
        Assertions.assertEquals("rgba(245, 233, 101, 1)", color, "Wrong color");
      }
    }

      public void assertElementsHaveCorrectTag (List<WebElement> elements) {
        for (WebElement element : elements) {
          Assertions.assertEquals("a", element.getTagName(), "Element tag is not a");
        }
      }

  }
