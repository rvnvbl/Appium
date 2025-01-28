import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC10_Wallet_SignUp_iOS extends SetupIOS {

	int failedCounter = 0;

	@Test(priority = 0)
	public void LoginButtonTest() throws Exception {
		// Clicking of Login with email button on startup screen

		try {
			IOSElement btn_login = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Log in")));
			btn_login.click();

			// verify if email textfield is visible and enabled
			IOSElement textfield_email = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Email")));
			// verify if Login Button is visible and enabled
			IOSElement btn_login2 = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Login")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	public void LoginFieldsTest_PWallet_Signup(String email, String password) throws Exception {

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Clicking and input of email on email field
		try {
			IOSElement EmailField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Email")));
			EmailField.click();
			EmailField.sendKeys(email);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Field is not found.", driver);
			failedCounter++;
		}

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		// Clicking and input of password on password field
		try {
			IOSElement PassField = (IOSElement) driver.findElement(MobileBy.AccessibilityId("Password"));
			PassField.click();
			PassField.sendKeys(password);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Password Field is not found.", driver);
			failedCounter++;
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking of login button after the input of the above credentials
		try {
			IOSElement LoginButton = (IOSElement) driver.findElement(By.name("Login"));
			LoginButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter++;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 2)
	public void DashboardTest() throws Exception {
		// Checking of P-Wallet Button if visible
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 3)
	public void Navigate_to_PWallet() throws Exception {

		// Checking of P-Wallet Button if visible
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_pwallet.click();

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to P-Wallet Page.", driver);
			failedCounter++;
		}

		// Check if redirected to P-Wallet page
		try {
			IOSElement btn_getStarted = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GET STARTED")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to P-Wallet Page.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to P-Wallet Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	public void SetUp_PWallet_Page() throws Exception {

		// Click Get Started button
		try {
			IOSElement btn_getStarted = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GET STARTED")));
			btn_getStarted.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Get Started Button found.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Get Started Button not found.", driver);
			failedCounter++;
		}

		// Check if Security Reminder modal is displayed
		try {
			IOSElement lbl_securityReminder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Security Reminders")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Security Reminders modal found.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Security Reminders modal not found.", driver);
			failedCounter++;
		}

		// Check if Security Reminder Proceed button is displayed
		try {
			IOSElement btn_proceed = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Proceed")));
			btn_proceed.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Proceed Button found.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Proceed Button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Setup P-Wallet page
		try {
			IOSElement btn_getStarted = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Setup P-Wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Setup P-Wallet Page.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Setup P-Wallet Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	public void Populate_fields() throws Exception {

		// Verify First Name field
		try {
			Thread.sleep(5000);
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textfield = driver.findElementsByClassName("XCUIElementTypeTextField");

			// Select first name field
			list_textfield.get(0).isDisplayed();

			markTestStatus("passed", "PASSED: First name field found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: First name field not found.", driver);
			failedCounter++;
		}

		// Verify Middle Name field
		try {
			Thread.sleep(5000);
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textfield = driver.findElementsByClassName("XCUIElementTypeTextField");

			// Select middle name field
			list_textfield.get(1).isDisplayed();

			markTestStatus("passed", "PASSED: Middle name field found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Middle name field not found.", driver);
			failedCounter++;
		}

		// Verify Last Name field
		try {
			Thread.sleep(5000);
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textfield = driver.findElementsByClassName("XCUIElementTypeTextField");

			// Select last name field
			list_textfield.get(2).isDisplayed();

			markTestStatus("passed", "PASSED: Last name field found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Last name field not found.", driver);
			failedCounter++;
		}

		// Verify Email Address field
		try {
			Thread.sleep(5000);
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textfield = driver.findElementsByClassName("XCUIElementTypeTextField");

			// Select Email Address field
			list_textfield.get(3).isDisplayed();

			markTestStatus("passed", "PASSED: Email Address field found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Address field not found.", driver);
			failedCounter++;
		}

		// Click on Birthday field
		try {
			Thread.sleep(5000);
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textfield = driver.findElementsByClassName("XCUIElementTypeTextField");

			// Select Birthday field
			list_textfield.get(4).click();

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Populate Birthday field error.", driver);
			failedCounter++;
		}

		// Check if Done Button is visible
		try {
			IOSElement btn_Done = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Done")));
			btn_Done.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Done Button found.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Done Button not found.", driver);
			failedCounter++;
		}

		// Verify and Click Accept Terms radio Button
		try {
			IOSElement radioBtn_Accept = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("reg unticked")));
			radioBtn_Accept.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Accept Terms Radio Button found.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Accept Terms Radio Button not found.", driver);
			failedCounter++;
		}

		// Verify Next Button if displayed and enabled
		try {
			IOSElement btn_next = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Next")));
			btn_next.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Next Button found.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Next Button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to OTP page
		try {
			IOSElement lbl_OTP = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("OTP")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to OTP Page.", driver);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to OTP Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 6)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Wallet Sign Up Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Wallet Sign Up Module successful.", driver);
		}

	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

}
