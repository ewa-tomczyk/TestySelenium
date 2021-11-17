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


public class ZadanieRamki {
  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://fakestore.testelka.pl/cwiczenia-z-ramek/");

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @AfterEach
  public void driverQuit (){

    driver.close();
    driver.quit();
  }

  public void nawigateToMainFrame() {
    WebElement ramka = driver.findElement(By.cssSelector("#main-frame"));
    driver.switchTo().frame(ramka);
  }


  public void nawigateToStronaGłównaFrame() {
    WebElement ramkaStronaGłówna = driver.findElement(By.cssSelector("iframe[src= 'https://fakestore.testelka.pl/ramka-button-do-strony-glownej/']"));
    driver.switchTo().frame(ramkaStronaGłówna);
  }

  @Test
  public void zadanieRamki1() {
    nawigateToMainFrame();
    WebElement buttonStronaGlowna = driver.findElement(By.cssSelector("[name='main-page']"));

    Assertions.assertFalse(buttonStronaGlowna.isEnabled(), "Buton isn't enabled");
  }

  @Test
  public void zadanieRamki2() {
    nawigateToMainFrame();
    String image = driver.findElement(By.cssSelector("[id='post-292'] p a")).getCssValue("href");
    Assertions.assertEquals("https://fakestore.testelka.pl/", image, "Link isn't correct");
  }

  @Test
  public void zadanieRamki3() {
    nawigateToStronaGłównaFrame();
    WebElement buttonStronaGlowna2 = driver.findElement(By.cssSelector(".button"));

    Assertions.assertFalse(buttonStronaGlowna2.isEnabled(), "Buton isn't enabled");
  }
}
