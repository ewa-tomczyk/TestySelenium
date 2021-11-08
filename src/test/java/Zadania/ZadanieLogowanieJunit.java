package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ZadanieLogowanieJunit {
  WebDriver driver;
  String password = "Ewa171717";
  String userFullName = "ewa.tomczyk";
  String errorMessageText;
  String myAccountContent;


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

  @DisplayName("Successful login")
  @ParameterizedTest(name = "User: \"{0}\"")
  @CsvSource ({"TestowyUser", "ewa.tomczyk@10clouds.com"})

  void successfulLogin(String username) {
    loginUser(username,password);

    myAccountContent = getActualUserName();
    Assertions.assertEquals(userFullName, myAccountContent, "Not logged in as " + myAccountContent);
  }


  @DisplayName("Unsuccessful login")
  @ParameterizedTest(name= "User: \"{0}\" with password: {1}")
  @CsvSource({ "ewa.tomczyk, wrong, Błąd: Wprowadzone hasło dla użytkownika ewa.tomczyk jest niepoprawne. Nie pamiętasz hasła?",
          "ewa.tomczyk@10clouds.com, wrong, BŁĄD: Dla adresu email ewa.tomczyk@10clouds.com podano nieprawidłowe hasło. Nie pamiętasz hasła?",
          "example.com, wrong, Błąd: Brak example.com wśród zarejestrowanych w witrynie użytkowników. Jeśli nie masz pewności co do nazwy użytkownika, użyj adresu e-mail.",
          "example@10.com, wrong, Nieznany adres email. Proszę sprawdzić ponownie lub wypróbować swoją nazwę użytkownika.",
          " ' ', wrong, Błąd: Nazwa użytkownika jest wymagana.",
          "ewa.tomczyk@10clouds.com, '',Błąd: Hasło jest puste."
  })

  void unsuccessfulLogin(String username, String password, String expectedMessage) {
    loginUser(username,password);
    errorMessageText = getErrorMessage();
    Assertions.assertEquals(expectedMessage, errorMessageText, "Error is not correct");
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
}
