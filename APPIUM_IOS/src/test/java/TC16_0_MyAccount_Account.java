import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.clipboard.ClipboardContentType;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC16_0_MyAccount_Account extends SetupIOS {

	int failedCounter = 0;

	@Test(priority = 0)
	public void LoginButtonTest() throws Exception {
		// Clicking of Login with email button on startup screen
		
		try {
			IOSElement btn_login = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Log in")));
			btn_login.click();
			Thread.sleep(2000);

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
	public void LoginFieldsTest_MyAccounts(String email, String password) throws Exception {

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

			Thread.sleep(5000);

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
			Thread.sleep(3000);

			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 3)
	public void Navigate_to_Account() throws Exception {

		try {
			// verify if Account Button is visible and enabled
			IOSElement btn_myAccount = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));

			btn_myAccount.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: Account Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: Account button is not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	private void navigateToMyProfileTest() {
		try {
			// verify if My Profile Button is visible and enabled
			IOSElement btn_myAccount = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Profile")));

			btn_myAccount.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: My Profile Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: My Profile button is not found.", driver);
			failedCounter++;
		}

		try {
			// verify if Complete My Profile Button is visible and enabled
			IOSElement btn_CompleteMyProfilet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Complete My Profile")));

			btn_CompleteMyProfilet.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: Complete My Profile Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: Complete My Profile button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5)
	private void middleNameTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(1).sendKeys("Middle");
			markTestStatus("passed", "PASSED: Middle Name Field Found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Middle Name Field is not found.", driver);
			failedCounter++;
		}

		/*
		 * 0 :Carlos Miguel 1 :Middle 2 :Ching 3 :cching@yondu.com 4 :+63 5 :9165513648
		 * 6 :+63 7 :9XXXXXXXXX 8 :Birthday 9 :Gender 10 :Age Bracket 11 :Occupation
		 * (optional) 12 :Income Bracket (optional) 13 :Province 14 :City 15 :Barangay
		 * 16 :Postal Code 17 :House Number, Building and Street
		 */

	}

	@Test(priority = 6)
	private void secondMobileTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(7).sendKeys("9111111111");
			markTestStatus("passed", "PASSED: Second mobile number field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Second Mobile Field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 7)
	private void provinceTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(13).click();
			markTestStatus("passed", "PASSED: Province field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province Field is not found.", driver);
			failedCounter++;
		}

		try {
			// Select Province
			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
			pickerWheel.get(0).setValue("Metro Manila");
			markTestStatus("passed", "PASSED: Province selected field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province selected is not found.", driver);
			failedCounter++;
		}
		// Click the Done Button
		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
	}

	@Test(priority = 8)
	private void cityTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(14).click();
			markTestStatus("passed", "PASSED: City field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province Field is not found.", driver);
			failedCounter++;
		}

		try {
			// Select city
			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
			pickerWheel.get(0).setValue("Makati City");
			markTestStatus("passed", "PASSED: City Selected field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: City Selected Field is not found.", driver);
			failedCounter++;
		}

		// Clicking done after selecting city
		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
	}

	@Test(priority = 9)
	private void barangayTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(15).click();
			markTestStatus("passed", "PASSED: Barangay field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Barangay Field is not found.", driver);
			failedCounter++;
		}

		try {
			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
			pickerWheel.get(0).setValue("Bangkal");
			markTestStatus("passed", "PASSED: Barangay Selected field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Barangay Selected Field is not found.", driver);
			failedCounter++;
		}

		// select first option
		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
	}

	@Test(priority = 10)
	private void postalCodeTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(16).click();
			allTextFieldMyProfile.get(16).sendKeys("2008");
			markTestStatus("passed", "PASSED: Postal code field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Postal code Field is not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 11)
	private void houseNumberTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(17).click();
			allTextFieldMyProfile.get(17).sendKeys("Test Address");
			markTestStatus("passed", "PASSED: House number field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: House number Field is not found.", driver);
			failedCounter++;
		}

		driver.hideKeyboard();
	}

	@Test(priority = 12)
	private void birthDayTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			driver.setClipboardText("Jan 12, 2000");

			allTextFieldMyProfile.get(8).click();
			allTextFieldMyProfile.get(8).click();
			markTestStatus("passed", "PASSED: Birthday test field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Birthday test Field is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement btn_Paste = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Paste")));
			btn_Paste.click();
			markTestStatus("passed", "PASSED: Paste button found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Paste button is not found.", driver);
			failedCounter++;
		}

	

	}

	@Test(priority = 13)
	private void genderTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(9).click();
			markTestStatus("passed", "PASSED: Gender field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Gender field is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement btn_male = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Male")));

			btn_male.click();
			markTestStatus("passed", "PASSED: Gender selected button found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Gender selected button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 14)
	private void ageTest() {
		try {
			List<IOSElement> allTextFieldMyProfile = driver.findElementsByClassName("XCUIElementTypeTextField");
			allTextFieldMyProfile.get(10).click();
			markTestStatus("passed", "PASSED: Age field found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Age field is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement btn_Age = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("25-34")));
			btn_Age.click();
			markTestStatus("passed", "PASSED: Age selected button found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Age selected button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 15)
	private void checkAgreementTest() {
		try {
			List<IOSElement> allButton = driver.findElementsByClassName("XCUIElementTypeButton");
			allButton.get(2).click();
			markTestStatus("passed", "PASSED: Check agreement button found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Check agreement button is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement btnSave = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Save")));
			markTestStatus("passed", "PASSED: Save button enabled.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save button is not enabled.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 16)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: My Account > My Profile has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED:  My Account > My Profile Module successful.", driver);
		}

	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

	// Code below is for the swipe actions

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	public void swipeElementIOS(MobileElement el, Direction dir) {
		System.out.println("swipeElementIOS(): dir: '" + dir + "'"); // always log your actions

		// Animation default time:
		// - Android: 300 ms
		// - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 200; // ms

		final int PRESS_TIME = 500; // ms

		// init screen variables
		Dimension dims = driver.manage().window().getSize();
		Rectangle rect = el.getRect();

		// check element overlaps screen
		if (rect.x >= dims.width || rect.x + rect.width <= 0 || rect.y >= dims.height || rect.y + rect.height <= 0) {
			throw new IllegalArgumentException("swipeElementIOS(): Element outside screen");
		}

		// init borders per your app screen
		// or make them configurable with function variables
		int leftBorder, rightBorder, upBorder, downBorder;
		leftBorder = 0;
		rightBorder = 0;
		upBorder = 0;
		downBorder = 0;

		// find rect that overlap screen
		if (rect.x < 0) {
			rect.width = rect.width + rect.x;
			rect.x = 0;
		}
		if (rect.y < 0) {
			rect.height = rect.height + rect.y;
			rect.y = 0;
		}
		if (rect.width > dims.width)
			rect.width = dims.width;
		if (rect.height > dims.height)
			rect.height = dims.height;

		PointOption pointOptionStart, pointOptionEnd;
		switch (dir) {
		case DOWN: // from up to down
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + upBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - downBorder);
			break;
		case UP: // from down to up
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - downBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + upBorder);
			break;
		case LEFT: // from right to left
			pointOptionStart = PointOption.point(rect.x + rect.width - rightBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + leftBorder, rect.y + rect.height / 2);
			break;
		case RIGHT: // from left to right
			pointOptionStart = PointOption.point(rect.x + leftBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + rect.width - rightBorder, rect.y + rect.height / 2);
			break;
		default:
			throw new IllegalArgumentException("swipeElementIOS(): dir: '" + dir + "' NOT supported");
		}

		// execute swipe using TouchAction
		try {
			new TouchAction(driver).press(pointOptionStart)
					// a bit more reliable when we add small wait
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release()
					.perform();
		} catch (Exception e) {
			System.err.println("swipeElementIOS(): TouchAction FAILED\n" + e.getMessage());
			return;
		}

		// always allow swipe action to complete
		try {
			Thread.sleep(ANIMATION_TIME);
		} catch (InterruptedException e) {
			// ignore
		}
	}

}
