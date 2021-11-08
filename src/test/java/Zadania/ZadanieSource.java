package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ZadanieSource {
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
  public void pageTitle () {
    String wikiTitle = "Wikipedia, wolna encyklopedia";
    driver.navigate().to("https://pl.wikipedia.org");
    Assertions.assertEquals(wikiTitle, driver.getTitle(), "Page title is not " + wikiTitle);
  }

  @Test
  public void pageUrl () {
    String wikiURL = "https://pl.wikipedia.org/wiki/Wikipedia:Strona_g%C5%82%C3%B3wna";
    driver.navigate().to("https://pl.wikipedia.org");
    Assertions.assertEquals(wikiURL, driver.getCurrentUrl(), " Url is not " + wikiURL);
  }

  @Test
  public void pageLanguage () {
    String wikiLanguage = "lang=\"pl\"";
    driver.navigate().to("https://pl.wikipedia.org");
    Assertions.assertTrue(driver.getPageSource().contains(wikiLanguage), "Page doesn't contain language " + wikiLanguage);
    driver.findElement(By.cssSelector("a[title='hiszpański']")).click();
    String esWiki = "Wikipedia, la enciclopedia libre";
    Assertions.assertEquals(esWiki, driver.getTitle(), "Page doesn't contain title " + esWiki);
    String esUrl = "https://es.wikipedia.org/wiki/Wikipedia:Portada";
    Assertions.assertEquals(esUrl, driver.getCurrentUrl(), "Page doesn't contain url " + esUrl);
    String esLanguage = "lang=\"es\"";
    Assertions.assertTrue(driver.getPageSource().contains(esLanguage), "Page doesn't contain language " + esLanguage);

  }
}
