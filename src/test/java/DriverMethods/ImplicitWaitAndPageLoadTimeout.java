package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class ImplicitWaitAndPageLoadTimeout {

  WebDriver driver;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void timeoutTest() {
    driver.navigate().to("https://fakestore.testelka.pl/product/grecja-limnos/");
    driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    driver.findElement(By.cssSelector("a[class= 'add_to_wishlist single_add_to_wishlist']")).click();

    driver.findElement(By.id("yith-wcwl-message"));
  }
}
