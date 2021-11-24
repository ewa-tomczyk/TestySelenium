package DriverMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Lists {
  WebDriver driver;

  @BeforeEach
  public void driverSetup () throws InterruptedException {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(1280, 720));
    driver.navigate().to("https://allegro.pl/");
    driver.manage().addCookie(new Cookie("gdpr_permission_given", "1"));
    driver.navigate().refresh();
  }

  @AfterEach
  public void driverQuit (){
    driver.quit();
  }

  @Test
  public void selectElement(){
    WebElement productCategories = driver.findElement(By.cssSelector("[data-role='filters-dropdown-toggle']"));
    Select categoriesDropdown = new Select(productCategories);
    categoriesDropdown.selectByIndex(3);
   // categoriesDropdown.selectByValue("/kategoria/kultura-i-rozrywka");
   // categoriesDropdown.selectByVisibleText("Zdrowie");
    Boolean isMultiple = categoriesDropdown.isMultiple();
    //categoriesDropdown.deselectByIndex(3);
    List<WebElement> options = categoriesDropdown.getOptions();
    List<WebElement> selectedOptions = categoriesDropdown.getAllSelectedOptions();
    WebElement firstSelectedOption = categoriesDropdown.getFirstSelectedOption();

  }
}
