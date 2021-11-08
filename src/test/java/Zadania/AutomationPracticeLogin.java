package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AutomationPracticeLogin {

  WebDriver driver;
  String validUsername = "ewa.tomczyk@10clouds.com";
  String validPassword = "Test12345";
  String invalidPassword = "Test";
  String invalidUsername = "sth@10.com";
  String acctualUsername;
  String errorMessage;


  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1295, 738));
    driver.manage().window().setPosition(new Point(5, 30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    driver.navigate().to("http://automationpractice.multiformis.com/index.php");
  }

  @AfterEach
  public void driverQuit (){
    driver.close();
    driver.quit();
  }


  private void login (String username, String password) {
    driver.findElement(By.cssSelector("[class='login']")).click();
    driver.findElement(By.cssSelector("[id='email']")).sendKeys(username);
    driver.findElement(By.cssSelector("[id='passwd']")).sendKeys(password);
    driver.findElement(By.cssSelector("[id='SubmitLogin']")).click();
  }

  public String getacctualUsername () {
    return driver.findElement(By.cssSelector("[class=\'header_user_info\'] span")).getText();
  }

  private String getErrorMessage() {
    return driver.findElement(By.cssSelector("[class='alert alert-danger']")).getText();
  }

  //logowanie - testy
  // brak testu na alert nieprawidłowego emaila - tbd jak przerobie js

  @Test
  public void loginValidData () {
    login(validUsername, validPassword);

    String expectedUsername = "Ewa Test";
    acctualUsername = getacctualUsername();
    Assertions.assertEquals(expectedUsername, acctualUsername, "User is not logged in");
  }

  @Test
  public void loginInvalidPassword (){
    login(validUsername, invalidPassword);

    String expectedErrorMessage = "There is 1 error\n" + "Invalid password.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

  @Test
  public void loginInvalidUsername (){
    login(invalidUsername, validPassword);

    String expectedErrorMessage = "There is 1 error\n" + "Authentication failed.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

  @Test
  public void loginemptyUsername (){
    login("", validPassword);

    String expectedErrorMessage = "There is 1 error\n" + "An email address required.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

  @Test
  public void loginemptyPassword (){
    login(validUsername, "");

    String expectedErrorMessage = "There is 1 error\n" + "Password is required.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

}
