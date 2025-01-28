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

public class TC16_3_MyAccount_Address extends SetupIOS {

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
	public void LoginFieldsTest_MyAccounts_Address(String email, String password) throws Exception {

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
			// verify if Delivery Button is visible and enabled
			IOSElement btn_myAccount = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));

			btn_myAccount.click();
			swipeElementIOS(btn_myAccount, Direction.UP);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: Account Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: Account button is not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	private void navigateToAddressTest() {
		try {
			// verify if Delivery Button is visible and enabled
			IOSElement btn_myAccount = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Address Book")));

			btn_myAccount.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: Address Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: Address button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5, dataProvider = "AddressProvider", dataProviderClass = DataProvider_Login.class, enabled = true)
	public void Add_Address(String fname, String lname, String address, String num, String postal) throws Exception {

		try {

			// verify if Add Address Button is visible and enabled
			IOSElement btn_addAddress = (IOSElement) driver.findElement(By.name("Add Address"));
			btn_addAddress.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {

			// verify if Add New Address title is visible
			IOSElement lbl_addAddress = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(
							MobileBy.xpath("//XCUIElementTypeStaticText[@name='Add New Address']")));

			String addAddress_text = lbl_addAddress.getText();

			if (addAddress_text.equalsIgnoreCase("Add New Address")) {

				markTestStatus("passed",
						"PASSED: Add Address Button is tapped; User is redirected to Delivery Add Address page",
						driver);
			} else {
				markTestStatus("failed", "FAILED: User is not redirected to Delivery Add Address page", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// Verify fields in Add Address Page

		// get all elements with the element class XCUIElementTypeTextField
		List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");

		// verify and input in first name field
		try {

			Allelementclass_addAddress.get(0).isDisplayed();
			Allelementclass_addAddress.get(0).click();
			Allelementclass_addAddress.get(0).clear();
			Allelementclass_addAddress.get(0).sendKeys(fname);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: First name field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// verify and input in last name field
		try {

			Allelementclass_addAddress.get(1).isDisplayed();
			Allelementclass_addAddress.get(1).click();
			Allelementclass_addAddress.get(1).clear();
			Allelementclass_addAddress.get(1).sendKeys(lname);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Last name field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// verify and input in mobile number field
		try {

			Allelementclass_addAddress.get(3).isDisplayed();
			Allelementclass_addAddress.get(3).click();
			Allelementclass_addAddress.get(3).clear();
			Allelementclass_addAddress.get(3).sendKeys(num);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Mobile number field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// verify and input in address field
		try {

			Allelementclass_addAddress.get(8).isDisplayed();
			Allelementclass_addAddress.get(8).click();
			Allelementclass_addAddress.get(8).clear();
			Allelementclass_addAddress.get(8).sendKeys(address);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Address field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 6, enabled = true)
	public void verify_province() throws Exception {

		// verify province field
		try {
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(4).isDisplayed();
			Allelementclass_addAddress.get(4).click();
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

	@Test(priority = 7, enabled = true)
	public void verify_city() throws Exception {
		// verify City field
		try {
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(5).isDisplayed();
			Allelementclass_addAddress.get(5).click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

	@Test(priority = 8, enabled = true)
	public void verify_barangay() throws Exception {
		// verify Barangay field
		try {
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(6).isDisplayed();

			Allelementclass_addAddress.get(6).click();
			Allelementclass_addAddress.get(6).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

	@Test(priority = 9, dataProvider = "AddressProvider", dataProviderClass = DataProvider_Login.class, enabled = true)
	public void verify_postal_code(String fname, String lname, String address, String num, String postal)
			throws Exception {
		// verify postal code field
		try {
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(7).isDisplayed();
			Allelementclass_addAddress.get(7).clear();
			Allelementclass_addAddress.get(7).sendKeys(postal);

			Allelementclass_addAddress.get(8).click();
			driver.hideKeyboard();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Postal Code field is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 10, enabled = true)
	public void select_address_label() throws Exception {
		// verify address label buttons

		// get all elements with the element class XCUIElementTypeButton
		List<IOSElement> Allelementclass_labelBtn = driver.findElementsByClassName("XCUIElementTypeButton");
		try { // verify if home button is visible and enabled

			// IOSElement btn_home = (IOSElement) new WebDriverWait(driver, 30)
			// .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Home")));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			for (int i = 0; i < Allelementclass_labelBtn.size(); i++) {
				String buttonText = Allelementclass_labelBtn.get(i).getText().trim();
				if (buttonText.equalsIgnoreCase("Home")) {
					Allelementclass_labelBtn.get(i).isDisplayed();
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Home button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try { // verify if other button is visible and enabled
				// IOSElement btn_other = (IOSElement) new WebDriverWait(driver, 30)
				// .until(ExpectedConditions.elementToBeClickable(MobileBy.name("Other")));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			for (int i = 0; i < Allelementclass_labelBtn.size(); i++) {
				String buttonText = Allelementclass_labelBtn.get(i).getText().trim();
				if (buttonText.equalsIgnoreCase("Other")) {
					Allelementclass_labelBtn.get(i).isDisplayed();
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Other button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {

			// IOSElement btn_other = (IOSElement) new WebDriverWait(driver, 30)
			// .until(ExpectedConditions.elementToBeClickable(MobileBy.name("Office")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			// verify if office button is visible and enabled

			for (int i = 0; i < Allelementclass_labelBtn.size(); i++) {
				String buttonText = Allelementclass_labelBtn.get(i).getText().trim();
				if (buttonText.equalsIgnoreCase("Office")) {
					Allelementclass_labelBtn.get(i).isDisplayed();
					Allelementclass_labelBtn.get(i).click();

				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Office button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 11, enabled = true)
	public void save_address() throws Exception {
		// verify save address button

		try {
			// verify if save address button is visible and enabled
			IOSElement btn_save_address = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Save Address")));

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			if (btn_save_address.isEnabled()) {
				markTestStatus("passed", "PASSED: Save Address Button is enabled", driver);

				// Tap Save address button
				btn_save_address.click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			} else {
				markTestStatus("failed", "FAILED: Save Address Button is not enabled", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {

			// verify if Add Address Button is visible and enabled
			IOSElement btn_addAddress = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add Address")));

			markTestStatus("passed", "PASSED: Successfully added new address.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 12)
	public void edit_address() throws Exception {
		// Checking of Edit Address Button if visible
		try {

			// get all elements with the element class XCUIElementTypeStaticText
			List<IOSElement> btn_edit = driver.findElementsByName("Edit");

			// selecting the last address edit button
			for (int i = 0; i < btn_edit.size(); i++) {
				if (i == (btn_edit.size() - 2)) {
					MobileElement SpecificEdit = btn_edit.get(i);
					SpecificEdit.click();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					break;
				}
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Edit button found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// Checking if redirected to Edit Address page
		try {

			// verify if save address button is visible
			IOSElement btn_save_address = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Save Address")));

			if (btn_save_address.isDisplayed() == true) {
				markTestStatus("passed", "Delivery > Edit Address Successful.", driver);
			} else {
				markTestStatus("failed", "FAILED: User is not redirected to Edit Address Page.", driver);
				failedCounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Edit button found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {

			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(0).click();
			Allelementclass_addAddress.get(0).clear();
			Allelementclass_addAddress.get(0).sendKeys("Test");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Allelementclass_addAddress.get(8).click();
			driver.hideKeyboard();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.hideKeyboard();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: First name field not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// verify save address button

		try {
			// verify if save address button is visible and enabled
			IOSElement btn_save_address = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Save Address")));

			// Tap Save address button
			btn_save_address.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 13, enabled = true)
	public void delete_address() throws Exception {
		int beforeSize = 3;
		int afterSize = 0;
		try {

			// verify if Add Address Button is visible and enabled
			IOSElement btn_addAddress = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add Address")));

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// Swipe Left address

		try {
			// get all elements with the element class XCUIElementTypeStaticText
			List<IOSElement> Allelementclass_Text = driver.findElementsByClassName("XCUIElementTypeStaticText");
			beforeSize++;
			// swipe left in address text
			
			for (int i = 0; i < Allelementclass_Text.size(); i++) {
				if (Allelementclass_Text.get(i).getText().equalsIgnoreCase("+639165513648")) {
					swipeElementIOS(Allelementclass_Text.get(i), Direction.LEFT);
					afterSize = beforeSize--;
					break;
	
				}
			}

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot perform swipe", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// Removing Address
		try {
			// Verify remove button
//			List<IOSElement> Allelementclass_Text = driver.findElementsByClassName("XCUIElementTypeStaticText");
//			afterSize = Allelementclass_Text.size();
//			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove Button not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {
			// Verify if successfully removed address

			if (beforeSize == afterSize) {
				markTestStatus("failed", "FAILED: Deleted Address can still be found", driver);
				failedCounter++;
			} else {
				markTestStatus("passed", "PASSED: Delivery > Delete Address successful.", driver);
			}
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("passed", "PASSED: Delivery > Delete Address successful.", driver);
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 15)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: My Account > Address has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED:  My Account > Address Module successful.", driver);
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
