package stepDefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Login {
	WebDriver driver = null;
	
	@Before
	public void setUp() {
		//System.setProperty("webdriver.edge.driver", "C:\\Users\\sakth\\BDD_Cucumber_framework\\BDDCucumber\\driver\\msedgedriver.exe");
		//driver = new EdgeDriver();
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sakth\\BDD_Cucumber_framework\\BDDCucumber\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
	
	}
	
	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	@Given("launch the application")
	public void launch_the_application() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("https://www.saucedemo.com/v1/");
		

	}

	@When("application launched")
	public void application_launched() {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed());
		
	   
	}
	@Then("login with {string} and {string}")
	public void login_with_and(String userName, String password) {
	    // Write code here that turns the phrase above into concrete actions
		
		driver.findElement(By.id("user-name")).sendKeys(userName);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("login-button")).click();

	    
	}

	@Then("check login success or not")
	public void check_login_success_or_not() {
	    // Write code here that turns the phrase above into concrete actions
		WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Products']")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Products']")).isDisplayed());

	    
	}

}
