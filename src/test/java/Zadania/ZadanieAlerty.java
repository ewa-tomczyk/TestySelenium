package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ZadanieAlerty {
  WebDriver driver;
  JavascriptExecutor js;

  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://jsfiddle.net/nm134se7/");
    js = (JavascriptExecutor) driver;
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void zadanieAlerty() {
    driver.switchTo().frame("result");
    driver.findElement(By.cssSelector("[onclick='confirmFunction()']"));
    String javascript = "prompt ('Wciśnij coś:')";

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(javascript);
    driver.switchTo().alert().accept();
    String text = driver.findElement(By.cssSelector("[id='demo']")).getText();
    Assertions.assertEquals("Wybrana opcja to OK!", text, "Incorrect message");
  }

  @Test
  public void zadanieAlertyDissmiss() {
    driver.switchTo().frame("result");
    driver.findElement(By.cssSelector("[onclick='confirmFunction()']"));
    String javascript = "prompt ('Wciśnij coś:')";

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript(javascript);
    driver.switchTo().alert().dismiss();
    String text = driver.findElement(By.cssSelector("[id='demo']")).getText();
    Assertions.assertEquals("Wybrana opcja to Cancel!", text, "Incorrect message");
  }


}
