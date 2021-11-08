package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ZadanieLogowanie {
  WebDriver driver;
  String correctusername = "ewa.tomczyk";
  String correctpassword = "Ewa171717!";
  String correctuseremail = "ewa.tomczyk@10clouds.com";
  String incorrectpassword = "000";
  String incorrectusername = "example.com";
  String incorrectuseremail = "example@10.com";
  String actualMessage;
  String actualUserName;
  String expectedMessage;


  @BeforeEach
  public void driverSetup() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    driver.navigate().to("https://fakestore.testelka.pl/moje-konto/");
  }

  @AfterEach
  public void driverQuit() {
    driver.close();
    driver.quit();
  }

  private void loginUser(String username, String password) {
    driver.findElement(By.cssSelector("input[name='username']")).sendKeys(username);
    driver.findElement(By.cssSelector("input[id='password']")).sendKeys(password);
    driver.findElement(By.cssSelector("button[name='login']")).click();
  }


  private String getErrorMessage() {
    return driver.findElement(By.cssSelector("[class='woocommerce-error']")).getText();
  }

  private String getActualUserName() {
   return driver.findElement(By.xpath(".//div[@class=\"woocommerce-MyAccount-content\"]/p/strong[2]")).getText();
  }

  @Test
  public void nazwaUzytkownikaLoginTest1() {

    loginUser(correctusername,correctpassword);

    String expectedUserName = "ewa.tomczyk";
    actualUserName = getActualUserName();
    Assertions.assertEquals(expectedUserName, actualUserName, "Not logged in as " + correctusername);
  }

  @Test
  public void emailLoginTest2() {

    loginUser(correctuseremail,correctpassword);

    String expectedUserName = "ewa.tomczyk";
    actualUserName = getActualUserName();
    Assertions.assertEquals(expectedUserName, actualUserName, "Not logged in as " + correctuseremail);
  }

  @Test
  public void logowanieNieprawidłoweHasloDlaUzytkownika3() {

    loginUser(correctusername,incorrectpassword);
    actualMessage = getErrorMessage();

    String expectedMessage = "Błąd: Wprowadzone hasło dla użytkownika " + correctusername + " jest niepoprawne. Nie pamiętasz hasła?";
    Assertions.assertEquals(expectedMessage, actualMessage, "Error is not correct");
  }

  @Test
  public void logowanieNieprawidłoweHasloDlaEmail4() {

    loginUser(correctuseremail,incorrectpassword);

    actualMessage = getErrorMessage();

    expectedMessage = "BŁĄD: Dla adresu email " + correctuseremail + " podano nieprawidłowe hasło. Nie pamiętasz hasła?";
    Assertions.assertEquals(expectedMessage, actualMessage, "Error is not correct");
  }

  @Test
  public void logowanieNieprawidłowyUsername5() {

    loginUser(incorrectusername,correctpassword);
    actualMessage = getErrorMessage();

    expectedMessage = "Błąd: Brak " + incorrectusername + " wśród zarejestrowanych w witrynie użytkowników. Jeśli nie masz pewności co do nazwy użytkownika, użyj adresu e-mail.";
    Assertions.assertEquals(expectedMessage, actualMessage, "Error is not correct");
  }

  @Test
  public void logowanieNieprawidłowyEmail6() {

    loginUser(incorrectuseremail,correctpassword);
    actualMessage = getErrorMessage();

    expectedMessage = "Nieznany adres email. Proszę sprawdzić ponownie lub wypróbować swoją nazwę użytkownika.";
    Assertions.assertEquals(expectedMessage, actualMessage, "Error is not correct");
  }

  @Test
  public void logowaniePusteDane7() {

    loginUser("",correctpassword);
    actualMessage = getErrorMessage();

    expectedMessage = "Błąd: Nazwa użytkownika jest wymagana.";
    Assertions.assertEquals(expectedMessage, actualMessage, "Error is not correct");
  }

  @Test
  public void logowaniePusteHaslo8() {


    loginUser(correctuseremail, "");
    actualMessage = getErrorMessage();

    String expectedMessage = "Błąd: Hasło jest puste.";
    Assertions.assertEquals(expectedMessage, actualMessage, "Error is not correct");
  }
}
