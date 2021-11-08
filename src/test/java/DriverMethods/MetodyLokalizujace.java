package DriverMethods;

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

import static java.lang.Thread.sleep;

public class MetodyLokalizujace {

  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://pl.wikipedia.org/wiki/Wikipedia:Strona_g%C5%82%C3%B3wna");
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void findingElementsBYID () {
    driver.findElement(By.id("searchInput"));
    driver.findElement(By.name("search"));
    driver.findElement(By.className("searchButton"));

    List<WebElement> externalClassElements = driver.findElements(By.className("external"));

    WebElement elementWithTwoClasses = null;

    for (WebElement externalClassElement: externalClassElements) {
      String elementClass = externalClassElement.getAttribute("class");
      if (elementClass.equals("external text")) {
        elementWithTwoClasses = externalClassElement;
      }
    }
    Assertions.assertTrue(elementWithTwoClasses !=null, "Element was not found");


    int numberOfImages = driver.findElements(By.tagName("img")).size();


  }

  @Test
  public void findingElementsByLinkText () {
    driver.findElement(By.linkText("Wikisłownik"));
    driver.findElement(By.partialLinkText("redagować"));
  }

}
