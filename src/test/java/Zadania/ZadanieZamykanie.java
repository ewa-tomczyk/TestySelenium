package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ZadanieZamykanie {

  WebDriver driver;

  @BeforeEach
  public void setup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
  }

  @AfterEach
  public void close () {
    driver.close();
    driver.quit();
  }

  @Test
  public void zadanieZamykanie () {

    driver.navigate().to("https://pl.wikipedia.org/");
    driver.navigate().to("https://nasa.gov");
    driver.navigate().back();
    String wikiTitle = "Wikipedia, wolna encyklopedia";
    Assertions.assertEquals(wikiTitle, driver.getTitle(), "The title of this page is not " + wikiTitle);
    driver.navigate().forward();
    String nasaTitle = "NASA";
    Assertions.assertEquals(nasaTitle, driver.getTitle(), "The title of this page is not " + nasaTitle);
  }

}
