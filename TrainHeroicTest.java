

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Created By: 	Nathan Tiedt
 * Date:		03/07/2016
 */
public class TrainHeroicTest {
	
	static WebDriver driver;
	static Random rand;
	static private int uniqueID = 2020;
	static private int NUMBER_OF_TESTS = 1;
	
	public static void main(String[] args) {
		/*
		 * Main class of TrainHeroic Test Automation
		 */
		
		// declare variables
		boolean result;
		String initialPhoneNumberToTest = "123-234-345";
		rand = new Random();
		
		try {
			// Executes a single phone number check then prints results to console.
			for (int i = 0; i < NUMBER_OF_TESTS; i++) {
				// Set up driver
				driver = new FirefoxDriver();
				driver.get("https://dev.trainheroic.com/app/signup/ch#/welcome");
				
				// Adds final number to end of phone number
				// Potentially adds a char instead to test erroneous user input
				String phoneNumberToTest = initialPhoneNumberToTest +  (char)(rand.nextInt(12) + 48);
				// Add one to uniqueID to allow for multiple tests
				uniqueID++;
				result = testPhoneNumber(phoneNumberToTest);
				System.out.println(result + " Test. Testing Phone Number: " + phoneNumberToTest);
				driver.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}
	
	private static boolean testPhoneNumber(String testPhoneNumber) {
		/*
		 * Method to test Phone Numbers for TrainHeroic's Coach Trial Account Creation Page.
		 * Input allows for multiple runs with a different number to test each time.
		 * All other fields are inputed statically
		 * ARGUMENTS:	testPhoneNumber = the phone number to test
		 * RETURNS:		returns whether test succeeds or fails
		 */
		
		// Fill in non-tested fields
		driver.findElement(By.name("fullName")).sendKeys("Jim Smith");
		driver.findElement(By.name("username")).sendKeys("jimsmith" + uniqueID);
		driver.findElement(By.name("email")).sendKeys("jimsmith" + uniqueID + "@gmail.com");
		driver.findElement(By.name("password")).sendKeys("jimsmith" + uniqueID);
		driver.findElement(By.name("newOrgName")).sendKeys("JimSmith" + uniqueID);
		// Fill in phone number to test
		driver.findElement(By.name("phone")).sendKeys(testPhoneNumber);
		// Click submit button
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[2]/div/button")).click();
		
		// Wait to give new page time to load
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		// Return boolean for whether a button with this xpath exists.
		// Xpath is for the create team button.
		try {
			return driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/div/div/button")) != null;
		} catch (Exception e) {
			// Next line commented out due to not needing the stack trace at the moment
//			e.printStackTrace();
			return false;
		}
	}
}
