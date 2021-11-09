package Zadania;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class AutomationPracticeLogin {

  WebDriver driver;
  public String validUsername = "ewa.tomczyk@10clouds.com";
  public String validPassword = "Test12345";
  public String invalidPassword = "Test";
  public String invalidUsername = "sth@10.com";
  public String actualUsername;


  @BeforeEach
  public void driverSetup (){
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
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
    return driver.findElement(By.cssSelector("[class='header_user_info'] span")).getText();
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
    actualUsername = getacctualUsername();
    Assertions.assertEquals(expectedUsername, actualUsername, "User is not logged in");
  }

  @Test
  public void loginInvalidPassword (){
    String errorMessage;
    login(validUsername, invalidPassword);

    String expectedErrorMessage = "There is 1 error\n" + "Invalid password.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

  @Test
  public void loginInvalidUsername (){
    String errorMessage;
    login(invalidUsername, validPassword);

    String expectedErrorMessage = "There is 1 error\n" + "Authentication failed.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

  @Test
  public void loginemptyUsername (){
    String errorMessage;
    login("", validPassword);

    String expectedErrorMessage = "There is 1 error\n" + "An email address required.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

  @Test
  public void loginemptyPassword (){
    String errorMessage;
    login(validUsername, "");

    String expectedErrorMessage = "There is 1 error\n" + "Password is required.";
    errorMessage = getErrorMessage();
    Assertions.assertEquals(expectedErrorMessage, errorMessage, "Wrong message");
  }

}
