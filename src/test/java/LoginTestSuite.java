
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import junit.framework.TestCase;
import org.openqa.selenium.support.ui.ExpectedConditions; 
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class LoginTestSuite extends TestCase {
	
	private WebDriver driver;
	private WebElement username;
	private WebElement password;
	private WebElement submit;
	
	
	public LoginTestSuite() {
		// Setup geckodriver path
		System.setProperty("phantomjs.binary.path", Utils.getConfig(1));

		// Setup the driver
		driver = new PhantomJSDriver();
		driver.get(Utils.getConfig(0));

		// Initialize the form elements to test
		username = driver.findElement(By.name("login-username"));
		password = driver.findElement(By.name("login-password"));
		submit = driver.findElement(By.name("login-submit"));
	}
	
	@After
	public void tearDown(){
		driver.quit();
	}
	

	@Test
	public void badFormatTest() {
		// Send bad formatted credentials
		username.sendKeys("badFormat");
		password.sendKeys("badpassword");
		submit.click();
		
		// Expect an unsuccessful login
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		WebElement alert = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("login-message"))
		);
		assertEquals("Incorrect login information.", alert.getText());
	}
	
	@Test
	public void emptyCredentialsTest() {
		// Send empty credentials
		submit.click();
		
		// Expect an unsuccessful login
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		WebElement alert = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("login-message"))
		);
		assertEquals("Incorrect login information.", alert.getText());
	}
	
	@Test
	public void badCredentialsTest() {
		// Send bad credentials
		username.sendKeys("badCredentials@gt.edu");
		password.sendKeys("badpwd");
		submit.click();
		
		// Expect an unsuccessful login
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		WebElement alert = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("login-message"))
		);
		//assertEquals("Login unsuccessful, please enter valid credentials!", alert.getText());
	}
	
	@Test
	public void goodCredentialsTest() {
		// Send good credentials
		username.sendKeys("alio@gmail.com");
		password.sendKeys("sadsadas");
		submit.click();
		
		// Expect a successful login
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		WebElement alert = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("login-message"))
		);
		//assertEquals("Login successful!", alert.getText());
	}
}