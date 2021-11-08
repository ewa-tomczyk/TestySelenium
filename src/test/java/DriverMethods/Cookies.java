package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.GregorianCalendar;
import java.util.Set;

import static java.lang.Thread.*;

public class Cookies {

  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://www.amazon.com/");
    sleep(1000);
    Assertions.assertEquals(9,driver.manage().getCookies().size());
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void gettingAndDeletingCookies()  {
    Cookie cookieSessionID = driver.manage().getCookieNamed("session-id");
    driver.manage().deleteCookieNamed("session-id");
    Assertions.assertEquals(8,driver.manage().getCookies().size(), "Number of cookies isn't as expected");
    driver.manage().deleteAllCookies();
    Assertions.assertEquals(0,driver.manage().getCookies().size(), "Number of cookies isn't as expected");
  }

  @Test
  public void addingCookies() {
    Cookie newCookie1 = new Cookie("test_cookie1", "test_value", ".amazon.com", "/",
            new GregorianCalendar(2023, 11, 31).getTime(), true, true);
    driver.manage().addCookie(newCookie1);
    Assertions.assertEquals(10,driver.manage().getCookies().size(), "Number of cookies isn't as expected");
    Cookie newCookie2 = new Cookie("test_cookie2", "test_value");
    driver.manage().addCookie(newCookie2);
    Assertions.assertEquals(11,driver.manage().getCookies().size(), "Number of cookies isn't as expected");
    driver.manage().deleteCookie(newCookie1);
    Assertions.assertEquals(10,driver.manage().getCookies().size(), "Number of cookies isn't as expected");

  }
}
