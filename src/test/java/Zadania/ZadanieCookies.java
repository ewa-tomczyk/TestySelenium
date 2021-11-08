package Zadania;

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

import static java.lang.Thread.sleep;

public class ZadanieCookies {
  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://pl.wikipedia.org/wiki/Wikipedia:Strona_g%C5%82%C3%B3wna");
    sleep(5000);
  }

  @AfterEach
  public void driverQuit (){ driver.quit();
  }

  @Test
  public void ZadanieCookies () {
    driver.manage().getCookies();
    Assertions.assertEquals(7, driver.manage().getCookies().size(), "Cookies number isn't expected");
    Cookie mineCookie = new Cookie("name_mine_cookie", "mine_value", ".wikipedia.org", "/",
            new GregorianCalendar(2023, 11, 31).getTime(), true, true);
    driver.manage().addCookie(mineCookie);
    Assertions.assertEquals(8, driver.manage().getCookies().size(), "Cookies number isn't expected");
    String mineCookieName = mineCookie.getName();
    Assertions.assertEquals(mineCookieName, driver.manage().getCookieNamed("name_mine_cookie").getName(), "wrong cookie");
    driver.manage().deleteCookie(mineCookie);
    Assertions.assertEquals(7, driver.manage().getCookies().size(), "Cookie wasn't deleted");
    driver.manage().deleteCookieNamed("plwikiel-sessionId");
    Assertions.assertEquals(6, driver.manage().getCookies().size(), "Cookie wasn't deleted");
    Cookie cookie = driver.manage().getCookieNamed("plwikimwuser-sessionId");
    Assertions.assertEquals("pl.wikipedia.org", cookie.getDomain(), "wrong domain");
    Assertions.assertEquals("/", cookie.getPath(), "Wrong path");
    Assertions.assertFalse(cookie.isHttpOnly(), "Not Http only");


  }
}
