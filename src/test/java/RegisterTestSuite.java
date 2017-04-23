
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import junit.framework.TestCase;
import org.openqa.selenium.support.ui.ExpectedConditions; 
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class RegisterTestSuite extends TestCase {
	
	private WebDriver driver;
	private WebElement reg_name; //register-email
	private WebElement reg_email; //register-email
	private WebElement reg_pass; //register-password
	private WebElement reg_cpass; //register-cpassword
	private WebElement reg_submit; //register-submit


	public RegisterTestSuite() {
		// Setup geckodriver path
		System.setProperty("phantomjs.binary.path", Utils.getConfig(1));

		// Setup the driver
		driver = new FirefoxDriver();
		driver.get(Utils.getConfig(0));

		// Initialize the form elements to test
		reg_name = driver.findElement(By.name("register-name"));
		reg_email = driver.findElement(By.name("register-email"));
		reg_pass = driver.findElement(By.name("register-password")); 
		reg_cpass = driver.findElement(By.name("register-cpassword")); 
		reg_submit = driver.findElement(By.name("register-submit"));
		
	}
	
	@After
	public void tearDown(){
		driver.quit();
	}
	
	@Test
	public void goodRegisterTest() {
		// Send good credentials
		reg_name.sendKeys("bob");
		reg_email.sendKeys(randomEmail());
		reg_pass.sendKeys("password");
		reg_cpass.sendKeys("password");
		reg_submit.click();
		
		// Expect a successful registration
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		Alert alert = wait.until(
				ExpectedConditions.alertIsPresent()
		);
		assertEquals("Registration successful!", alert.getText());
	}

	@Test
	public void emptyRegisterTest() {
		reg_submit.click();
		
		// Expect an unsuccessful registration
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		Alert alert = wait.until(
				ExpectedConditions.alertIsPresent()
		);
		assertEquals("Incorrect user information.", alert.getText());
	}

	@Test
	public void badEmailRegisterTest() {
		// Send good credentials
		reg_name.sendKeys("bob");
		reg_email.sendKeys("bob.com");
		reg_pass.sendKeys("password");
		reg_cpass.sendKeys("password");
		reg_submit.click();
		
		// Expect an unsuccessful registration
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		Alert alert = wait.until(
				ExpectedConditions.alertIsPresent()
		);
		assertEquals("Incorrect user information.", alert.getText());
	}

	@Test
	public void badPasswordTest() {
		// Send good credentials
		reg_name.sendKeys("bob");
		reg_email.sendKeys("bob@gmail.com");
		reg_pass.sendKeys("password1");
		reg_cpass.sendKeys("password2");
		reg_submit.click();
		
		// Expect an unsuccessful registration
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		Alert alert = wait.until(
				ExpectedConditions.alertIsPresent()
		);
		assertEquals("Incorrect user information.", alert.getText());
	}
	
	@Test
	public void existingEmailTest() {
		// Send good credentials
		reg_name.sendKeys("Ali");
		reg_email.sendKeys("alio@gmail.com");
		reg_pass.sendKeys("password");
		reg_cpass.sendKeys("password");
		reg_submit.click();
		
		// Expect an unsuccessful registration
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		Alert alert = wait.until(
				ExpectedConditions.alertIsPresent()
		);
		assertEquals("Registration unsuccessful, please enter valid credentials!", alert.getText());
	}
	
	public String randomEmail() {
		int length = ThreadLocalRandom.current().nextInt(5, 13);
	    StringBuilder builder = new StringBuilder();
	    for(int i = 0; i < length; i++){
	    	int randomNum = ThreadLocalRandom.current().nextInt(((int)'a'), ((int)'z')+1);
	    	builder.append(((char) randomNum));
	    }
	    return builder.toString()+"@gt.edu";
	}
}
