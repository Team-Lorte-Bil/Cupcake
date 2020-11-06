package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;

import static org.junit.Assert.assertThat;

class UserUITest {
  
  private final static String OS = System.getProperty("os.name").toLowerCase();
  
  private WebDriver driver;
  JavascriptExecutor js;
  
  @BeforeEach
  void setUp() {
    String path = "src/test/resources/geckodriver";
    
    if(OS.contains("win")) path += ".exe";
    
    File file = new File(path);
    System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
    
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
  }
  
  @AfterEach
  void tearDown() {
    driver.quit();
  }
  
  @Test
  void UserLoginAndLogout() {
    // Test name: User login and logout
    // Step # | name | target | value
    // 1 | open | /cupcake/ | 
    driver.get("https://lortebil.team/cupcake/");
    // 2 | setWindowSize | 1539x1052 | 
    driver.manage().window().setSize(new Dimension(1539, 1052));
    // 3 | click | linkText=Log ind | 
    driver.findElement(By.linkText("Log ind")).click();
    // 4 | type | id=inputEmail | test@test.dk
    driver.findElement(By.id("inputEmail")).sendKeys("test@test.dk");
    // 5 | type | id=inputPassword | test
    driver.findElement(By.id("inputPassword")).sendKeys("test");
    // 6 | click | css=.btn | 
    driver.findElement(By.cssSelector(".btn")).click();
    // 7 | assertText | linkText=Log ud | Log ud
    assertThat(driver.findElement(By.linkText("Log ud")).getText(), is("Log ud"));
    // 8 | click | linkText=Log ud | 
    driver.findElement(By.linkText("Log ud")).click();
    // 9 | assertText | linkText=Log ind | Log ind
    assertThat(driver.findElement(By.linkText("Log ind")).getText(), is("Log ind"));
  }
}
