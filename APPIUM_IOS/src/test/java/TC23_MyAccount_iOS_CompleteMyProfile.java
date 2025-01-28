import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.core.config.AwaitUnconditionallyReliabilityStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

/**
 * @author avibal
 *
 */
public class TC23_MyAccount_iOS_CompleteMyProfile extends SetupIOS {

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
	public void LoginFieldsTest_Profile_CompleteMyProfile(String email, String password) throws Exception {

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
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Password Field is not found.", driver);
			failedCounter++;
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking of login button after the input of the above credentials
		try {
			IOSElement LoginButton = (IOSElement) driver.findElement(MobileBy.AccessibilityId("Login"));
			LoginButton.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter++;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 2)
	private void completeMyProfilePopUp() {
		// My Profile Title Page
		try {
			IOSElement titlePage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Profile")));

			try {
				Assert.assertTrue(titlePage.getText().equals("My Profile"));
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Title page is not same", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Title page is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 3)
	private void firstNameTest() {
		// Testing the firstname textfield
		try {

			IOSElement firstNameField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("fNameTf")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Firstname field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 4)
	private void middleNameTest() {
		// Testing the middlename textfield
		try {
			IOSElement middleNameField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("mNameTf")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Middlename field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5, enabled =false)
	private void lastNameTest() {
		// Testing the lastname textfield
		try {
			IOSElement lastNameField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("lNameTf")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Lastname field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 6)
	private void emailTest() {
		// Testing the email textfield
		try {
			IOSElement emailField = (IOSElement) driver.findElement(MobileBy.name("emailTf"));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 7)
	private void mainPhoneNumberTest() {
		// Testing the main phone number textfield
		try {
			IOSElement mainPhoneNumberField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("pMobileTf")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Main mobilenumber field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 8)
	private void secondaryPhoneNumberTest() {
		// Testing the secondary phone number textfield
		try {
			IOSElement secondaryPhoneNumberField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("sMobileTf")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Seconndary mobile number field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 9, enabled = true)
	public void verify_province() throws Exception {

		// verify province field
		try {
			// get all elements with the element class XCUIElementTypeTextField
//			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
//			Allelementclass_addAddress.get(4).isDisplayed();
//			Allelementclass_addAddress.get(4).click();

			IOSElement provinceFieldTest = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("provinceTf")));

			provinceFieldTest.click();
			Thread.sleep(1000);

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 10, enabled = true)
	public void verify_city() throws Exception {
		// verify City field
		try {
//			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
//			Allelementclass_addAddress.get(5).isDisplayed();
//			Allelementclass_addAddress.get(5).click();

			IOSElement provinceFieldTest = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cityTf")));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			provinceFieldTest.click();
			Thread.sleep(1000);

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: City field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 11, enabled = true)
	public void verify_barangay() throws Exception {
		// verify Barangay field
		try {
//			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
//			Allelementclass_addAddress.get(6).isDisplayed();
//			Allelementclass_addAddress.get(6).click();
//			Allelementclass_addAddress.get(6).click();

			IOSElement provinceFieldTest = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("barangayTf")));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			provinceFieldTest.click();
			Thread.sleep(1000);

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Barangay field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 12)
	private void postalCodeTest() {
		// Testing the secondary phone number textfield
		try {
			IOSElement postalCodeField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("postalCodeTf")));

			postalCodeField.sendKeys("2013");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Postal Code field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 13)
	private void houseNumberTest() {
		// Testing the secondary phone number textfield
		try {
			IOSElement houseNumberField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("houseNumTf")));

			houseNumberField.sendKeys("Test Address");

			// Hide Keyboard
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			scrollToElement("Income Bracket (optional)");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: House number field is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 14)
	private void birthdayTest() {
		try {
			IOSElement birthdayField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("birthdayTf")));

			birthdayField.sendKeys("July 04, 2002");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
			failedCounter++;
		}

//		try {
//			IOSElement datePicker = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Date Picker")));
//			datePicker.click();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			markTestStatus("failed", "FAILED: Date picker is not found.", driver);
//			failedCounter++;
//		}
//
//		try {
//			IOSElement yearPicker = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Show year picker")));
//			yearPicker.click();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			markTestStatus("failed", "FAILED: Date picker is not found.", driver);
//			failedCounter++;
//		}
//
//		List<IOSElement> typePicker = driver.findElements(By.className("XCUIElementTypePickerWheel"));

	}

	@Test(priority = 15)
	private void GenderTest() {
		try {
			IOSElement genderField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("genderTf")));
			genderField.click();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Gender field is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement genderTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Gender")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Gender title is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement femaleOption = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Female")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Female option is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement maleOption = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Male")));

			maleOption.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Male option is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement myProfileTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Profile")));
		} catch (Exception e) {

			markTestStatus("failed", "FAILED: My Profile Title is not found.", driver);
			failedCounter++;
		}
		scrollToElement("Income Bracket (optional)");
	}

	@Test(priority = 16)
	private void ageBracketTest() {
		try {
			IOSElement ageBracketField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("ageTf")));
			ageBracketField.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Age bracket field is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement ageTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Age Bracket")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Age title is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement ageOption = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("25-34")));
			ageOption.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Age option is not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement myProfileTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Profile")));
		} catch (Exception e) {

			markTestStatus("failed", "FAILED: My Profile Title is not found.", driver);
			failedCounter++;
		}

		scrollToElement("Income Bracket (optional)");

	}

	@Test(priority = 16)
	private void termsAndConditionTest() {

		try {
			IOSElement termsMessage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("termsTv")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Terms & Condition is not found.", driver);
			failedCounter++;
		}
		try {
			IOSElement termsAndConditionCheckbox = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("termsBtn")));
			termsAndConditionCheckbox.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Terms & Condition button is not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Account > Complete My Profile has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Wallet > Complete My Profile successful.", driver);
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

	// Scroll to element value
	private void scrollToElement(String elementValue) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		HashMap<String, Object> scrollobj = new HashMap<String, Object>();
		scrollobj.put("predicateString", "value == '" + elementValue + "'");
		scrollobj.put("direction", "down");
		executor.executeScript("mobile: scroll", scrollobj);

	}

}
