package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class Storage {
  ChromeDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void localStorge() {
    driver.navigate().to("https://airly.org/map/pl/#50.0645188245,19.9494942593");
    LocalStorage local = driver.getLocalStorage();
    String value = local.getItem("persist:map");
    int size = local.size();
    Set<String> keys = local.keySet();
    String removedValue = local.removeItem("persist:map");
    local.setItem("spell", "Allles");
    local.clear();
  }
  @Test
  public void sessionStorge() {
    driver.navigate().to("https://www.youtube.com/watch?v=aIN0lcqXCJo");
    driver.findElement(By.xpath(".//*[text()='I agree']")).click();
    SessionStorage session = driver.getSessionStorage();
    String value = session.getItem("yt-remote-session-app");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(a->session.size()==6);
    int size = session.size();
    Set<String> keys = session.keySet();
    String removedValue = session.removeItem("yt-remote-session-app");
    session.setItem("spell", "Allles");
    session.clear();
  }

}
