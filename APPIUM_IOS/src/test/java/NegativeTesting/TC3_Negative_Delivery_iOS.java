package NegativeTesting;

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
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;

public class TC3_Negative_Delivery_iOS extends NegativeSetupIOS {

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
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 1, dataProvider = "LoginProvider", dataProviderClass = DataProvider_Login.class)
	public void LoginFieldsTest(String email, String password) throws Exception {

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
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking of login button after the input of the above credentials
		try {
			IOSElement LoginButton = (IOSElement) driver.findElement(By.name("Login"));
			LoginButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 2)
	public void DashboardTest() throws Exception {
		// Checking of P-Wallet Button if visible
		try {
			// verify if email textfield is visible and enabled
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 3)
	public void Navigate_DeliveryMenu() throws Exception {
		// Checking of Add Address Button if visible

		// Navigate to Add Address Page
		try {
			// verify if Delivery Button is visible and enabled
			IOSElement btn_delivery = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_delivery")));
			btn_delivery.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

			// verify if Delivery Guidelines title is visible to know if redirection is
			// successful
			IOSElement lbl_delivery = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Delivery Guidelines")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delivery button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {

			// verify if accept terms button is visible and enabled
			IOSElement btn_accept = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Accept Terms")));
			btn_accept.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Accept Terms button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {

			// verify if OK button is visible and enabled
			IOSElement btn_ok = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("OK")));
			btn_ok.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: OK button is not found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 4, dataProvider = "AddressProvider", dataProviderClass = DataProvider_Login.class, enabled = false)
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
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add New Address")));

			String addAddress_text = lbl_addAddress.getText();

			if (addAddress_text.equalsIgnoreCase("add new address")) {

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

	@Test(priority = 5, enabled = false)
	public void verify_province() throws Exception {

		// verify province field
		try {
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(4).isDisplayed();
			Allelementclass_addAddress.get(4).click();

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

	@Test(priority = 6, enabled = false)
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

	@Test(priority = 7, enabled = false)
	public void verify_barangay() throws Exception {
		// verify Barangay field
		try {
			List<IOSElement> Allelementclass_addAddress = driver.findElementsByClassName("XCUIElementTypeTextField");
			Allelementclass_addAddress.get(6).isDisplayed();
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

	@Test(priority = 8, dataProvider = "AddressProvider", dataProviderClass = DataProvider_Login.class, enabled = false)
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

	@Test(priority = 9, enabled = false)
	public void select_address_label() throws Exception {
		// verify address label buttons

		// get all elements with the element class XCUIElementTypeButton
		// List<IOSElement> Allelementclass_labelBtn =
		// driver.findElementsByClassName("XCUIElementTypeButton");

		try {
			// verify if home button is visible and enabled
			IOSElement btn_home = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Home")));
			
			// Allelementclass_labelBtn.get(1).isDisplayed();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Home button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {
			// verify if office button is visible and enabled
			IOSElement btn_office = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Office")));
			btn_office.click();
			// Allelementclass_labelBtn.get(2).isDisplayed();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Office button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {
			// verify if other button is visible and enabled
			IOSElement btn_other = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Other")));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//			btn_other.click();

			// Allelementclass_labelBtn.get(3).isDisplayed();
			// Allelementclass_labelBtn.get(3).click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Other button is not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 10, enabled = false)
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

	@Test(priority = 11)
	public void edit_address() throws Exception {

		// Checking of Edit Address Button if visible
		try {

			// get all elements with the element class XCUIElementTypeStaticText
			List<IOSElement> btn_edit = driver.findElementsByName("Edit");

			// selecting the last address edit button
			for (int i = 0; i < btn_edit.size(); i++) {
				if (i == (btn_edit.size() - 1)) {
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

	@Test(priority = 12, enabled = false)
	public void delete_address() throws Exception {

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

			// swipe left in address text
			swipeElementIOS(Allelementclass_Text.get(7), Direction.LEFT);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot perform swipe", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// Removing Address
		try {
			// Verify remove button
			IOSElement btn_remove = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("REMOVE")));
			btn_remove.click();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove Button not found", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		try {
			// Verify if successfully removed address
			IOSElement address_text = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cell_1")));

			markTestStatus("failed", "FAILED: Deleted Address can still be found", driver);
			failedCounter++;

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("passed", "PASSED: Delivery > Delete Address successful.", driver);
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 13)
	public void DeliverySelectAddress() throws Exception {

		try {
			Thread.sleep(5000);
			// Find and click first address
			IOSElement select_address = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("cell_0")));
			select_address.click();

			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot find and select address.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

		// Checking of Store list if redirected successfully
		try {
			// Checking if "I'm Shopping in" title is visible after clicking the address to
			// know if redirection is successful
			IOSElement lbl_shopping = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("I’m shopping in")));
			markTestStatus("passed", "PASSED: Delivery > Select Address successful.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to store selection screen.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 14)
	public void DeliveryStoreList() throws Exception {

		try {
			// Checking of Puregold branches nearby that should show on the list
			// (Makati-Poblacion Area)
			IOSElement puregold_makati_branch = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("PUREGOLD MAKATI")));

			// IOSElement puregold_poblacion_branch = (IOSElement) new WebDriverWait(driver,
			// 30)
			// .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Puregold
			// Poblacion")));

			markTestStatus("passed", "PASSED: Delivery > Store List successful.", driver);
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Store Branches nearby (Puregold Makati and Poblacion) is not visible.",
					driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}

	}

	@Test(priority = 15)
	public void DeliverySearchStore() throws Exception {

		// Search Puregold Makati branch in search bar

		try {
			// Checking if Store search bar is visible then input of search item
			IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("Search for Store Branch, City or Province")));
			searchbar.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			searchbar.sendKeys("Illinois");
			IOSElement searchResults = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("No search results")));
			Assert.assertTrue(searchResults.getText().trim().contains("No search results"));
			markTestStatus("passed", "PASSED: Expected 'No search results' matched", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Store search bar could not be found.", driver);
			failedCounter++;
			System.out.println(e.getMessage());
		}
	}

	@Test(priority = 16)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Delivery Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Delivery Module successful.", driver);
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
