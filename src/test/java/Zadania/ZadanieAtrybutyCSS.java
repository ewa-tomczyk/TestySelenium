package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.sleep;

public class ZadanieAtrybutyCSS {

  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://fakestore.testelka.pl/moje-konto");
    sleep(5000);
  }

  @AfterEach
  public void driverQuit (){ driver.quit();
  }

  @Test
  public void zadanieAtrybutyCss () {
    driver.findElement(By.id("username"));
    driver.findElement(By.id("password"));
    driver.findElement(By.id("rememberme"));
    driver.findElement(By.name("login"));
    driver.findElement(By.cssSelector("p.lost_password"));
    driver.findElement(By.id("reg_email"));
    driver.findElement(By.id("reg_password"));
    driver.findElement(By.cssSelector("button.woocommerce-form-register__submit"));
    driver.findElement(By.cssSelector("li.cat-item-18"));





  }

}
