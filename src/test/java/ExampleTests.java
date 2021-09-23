import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExampleTests {
  @Test
  public void demoTest() {
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();
    driver.navigate().to("https://frontend.tst.semble.aws.10clouds.com/");

    driver.findElement(By.xpath(".//*[contains(@class, 'LoginButtons_link__2iKZx')]")).click();
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).sendKeys("ewa.tomczyk+Funder@10clouds.com");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("Semble123!");
    driver.findElement(By.xpath(".//*[contains(@class, 'MuiButton-label')]")).click();
  }

}