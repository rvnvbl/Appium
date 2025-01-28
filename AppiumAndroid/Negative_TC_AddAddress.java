import static org.testng.Assert.assertTrue;

import java.awt.RenderingHints.Key;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;

public class Negative_TC_AddAddress extends AndroidSetup {
	List<AndroidElement> errorMessageCollection = new ArrayList<>();

	@Test(priority = 0)
	public void LoginPageTest() throws Exception {
		try {
			// Clicking of Login with email button on startup screen
			AndroidElement EmailLoginButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("landing_login")));
			EmailLoginButton.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			// Checking of fields if redirect to login with email page is successful

			AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));

			AndroidElement PasswordField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_password_field")));

			System.out.println("Test 1: Redirect to Login page successful");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
		}
	}

	@Test(priority = 1, dataProvider = "LoginProvider", dataProviderClass = DataProvider_Login.class)
	public void LoginFieldsTest(String email, String password) throws Exception {

		// Clicking and input of email on email field
		try {
			AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));
			EmailField.click();
			// -----Edit
			EmailField.sendKeys(email);

			System.out.println("Test 2 passed: Email input success");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Field is not found.", driver);
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking and input of password on password field
		try {
			AndroidElement PassField = (AndroidElement) driver.findElement(By.id("login_password_field"));
			PassField.click();
			// -----Edit
			PassField.sendKeys(password);

			// Verifying of "Password" attribute to check if password is hidden
			String PassAtt = PassField.getAttribute("Password");
			if (PassAtt.equals("true")) {
				System.out.println("Test 3 passed: Password is hidden");
			} else {
				System.out.println("Test 3 failed: Password is not hidden");
				markTestStatus("failed", "FAILED: Password is not hidden.", driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Password Field is not found.", driver);
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking and input of password on password field
		try {
			AndroidElement ShowPass = (AndroidElement) driver.findElement(By.id("text_input_end_icon"));
			ShowPass.click();

			// Verifying of "Password" attribute to check if password is hidden
			String PassAtt = ShowPass.getAttribute("Password");
			if (PassAtt.equals("false")) {
				System.out.println("Test 4 passed: Password is shown");
			} else {
				System.out.println("Test 4 failed: Password is hidden");
				markTestStatus("failed", "FAILED: Password Field is hidden.", driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Password Field is not found.", driver);
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@Test(priority = 2)
	public void LoginButtonTest() throws Exception {
		// Clicking of login button after the input of the above credentials
		try {
			AndroidElement LoginButton = (AndroidElement) driver.findElement(By.id("login_next"));
			LoginButton.click();

			// Checking if Puregold Image from homepage is visible to know if login is
			// successful
			AndroidElement PuregoldImageChecker = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("image")));

			System.out.println("Test 5 passed: Login is successful");
			markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 3)
	public void TapAccountTab() throws Exception {
		// Scrolling to and clicking the Account Button

		try {
			AndroidElement accountTab = driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
							+ "(new UiSelector().resourceId(\"com.grocery.puregold:id/nav_account\"));");

			accountTab.click();

			AndroidElement headerTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			String headerTitleString = headerTitle.getText();

			if (headerTitleString.equalsIgnoreCase("Account")) {
				System.out.println("Succesfully land on the Account Tab");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Not Land on the Account Tab.", driver);
		}

	}

	@Test(priority = 4)
	public void goToAddAddressTab() {
		// Clicking Address Book
		try {
			AndroidElement addressBookTab = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Address Book\"));")));

			addressBookTab.click();
			System.out.println("AddressBookTab Click");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Address Book not found.", driver);
		}
	}

	@Test(priority = 5)
	public void addAddress() {

		try {
			AndroidElement headerTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			String headerTitleString = headerTitle.getText();

			if (headerTitleString.equalsIgnoreCase("Select Address")) {
				System.out.println("Succesfully land on the Select Address Tab");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Not Redirect to Select Address Tab.", driver);

		}

		AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
						+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
						+ "new UiSelector().text(\"Add Address\"));")));

		AddAddressButton.click();

		// Checking if "Add New Address" title is visible
		AndroidElement AddAddressTitle = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

		String AddAddressText = AddAddressTitle.getText();

		if (AddAddressText.equals("Add New Address")) {
			System.out.println("Test passed: Redirect to add address successful");
		} else {
			markTestStatus("failed", "FAILED: Add New Address title is not found.", driver);
		}

		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

		// Checking if Reminder Modal from Delivery is visible to know if redirect is
		// successful

	}

	@Test(priority = 6)
	public void AddressPopulateFieldsTest() throws Exception {

		// Checking of First Name Field
		try {
			AndroidElement FirstNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_first_name_field")));

			AndroidElement LastNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_last_name_field")));
			FirstNameField.click();
			FirstNameField.sendKeys("");
			FirstNameField.sendKeys("TestingAcc ");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: The format of FIRST NAME is correct.", driver);
		}

		// Checking of Last Name Field
		try {
			AndroidElement LastNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_last_name_field")));
			LastNameField.click();
			LastNameField.sendKeys("TestingAccount ");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Last Name Field is not found / error.", driver);
		}

		// Checking and input of House Number, Building and Street Name Field
		try {
			AndroidElement HouseBuildingStreetField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_street_field")));

			HouseBuildingStreetField.click();

			HouseBuildingStreetField.sendKeys("    ");

			AndroidElement lastNameErrorMessage = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("textinput_error")));
			assertTrue(lastNameErrorMessage.getText().contains("Invalid Last Name"));

			System.out.println("The first name is invalid");
			// Closing of on-screen keyboard
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: House Number, Building and Street Name Field is not found / error.",
					driver);
		}

		// Checking of Mobile Number Field
		try {
			AndroidElement MobileNoField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_mobile_number_field")));
			MobileNoField.sendKeys(" ");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Mobile Number Field is not found / error.", driver);
		}

	}

	@Test(priority = 7)
	public void AddressProvinceTest() throws Exception {
		// Checking of Province Drop down
		try {
			AndroidElement ProvinceDrop = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_province_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province Drop down is not found / error.", driver);
		}
	}

	@Test(priority = 8)
	public void AddressCityTest() throws Exception {
		// Checking of City Drop down
		try {
			AndroidElement CityDrop = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_city_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: City Drop down is not found / error.", driver);
		}
	}

	@Test(priority = 9)
	public void AddressBarangayTest() throws Exception {
		// Checking of Barangay drop down
		try {
			AndroidElement BarangayDrop = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_barangay_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Barangay Drop down is not found / error.", driver);
		}
	}

	@Test(priority = 10)
	public void AddressPostalTest() throws Exception {
		// Checking and input of Postal Code Field
		try {
			AndroidElement PostalField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_postal_code_field")));

			PostalField.click();

			PostalField.sendKeys("    ");

			// Closing of on-screen keyboard
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Postal Code Field is not found / error.", driver);
		}
	}

	@Test(priority = 11)
	public void AddressLabelTest() throws Exception {
		// Checking of Home Address Label
		try {
			AndroidElement HomeLabel = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_tag_home")));
			HomeLabel.click();
		} catch (Exception e) {
			System.out.println("FAILED: Home Label is not found / error.");
		}

		// Checking of Office Address Label
		try {
			AndroidElement OfficeLabel = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_tag_office")));
			OfficeLabel.click();
		} catch (Exception e) {
			System.out.println("FAILED: Office Label is not found / error.");
		}

		// Checking and clicking of Other Address Label
		try {
			AndroidElement OtherLabel = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_tag_other")));
			OtherLabel.click();
		} catch (Exception e) {
			System.out.println("FAILED: Other Label is not found / error.");
		}

		errorMessageCollection.addAll(driver.findElements(MobileBy.id("textinput_error")));
		
		for (int x = 0; x < errorMessageCollection.size(); x++) {

			System.out.println(errorMessageCollection.get(x).getText());
		}

	}

	@Test(priority = 12)
	public void SaveAddressTest() throws Exception {

		// Clicking of Save Address Button
		try {
			AndroidElement SaveAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_save_address")));

			SaveAddressButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found / error.", driver);
		}

		try {
			// Checking if "Add Address" Button is visible
			AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Add Address\"));")));

			markTestStatus("passed", "Successfully added new address", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
		}
	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

	/**
	 * Performs swipe inside an element
	 *
	 * @param el  the element to swipe
	 * @param dir the direction of swipe
	 * @version java-client: 7.3.0
	 **/
	public void swipeElementAndroid(MobileElement el, Direction dir) {
		System.out.println("swipeElementAndroid(): dir: '" + dir + "'"); // always log your actions

		// Animation default time:
		// - Android: 300 ms
		// - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 200; // ms

		final int PRESS_TIME = 200; // ms

		int edgeBorder;
		PointOption pointOptionStart, pointOptionEnd;

		// init screen variables
		Rectangle rect = el.getRect();
		// sometimes it is needed to configure edgeBorders
		// you can also improve borders to have vertical/horizontal
		// or left/right/up/down border variables
		edgeBorder = 0;

		switch (dir) {
		case DOWN: // from up to down
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + edgeBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - edgeBorder);
			break;
		case UP: // from down to up
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - edgeBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + edgeBorder);
			break;
		case LEFT: // from right to left
			pointOptionStart = PointOption.point(rect.x + rect.width - edgeBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + edgeBorder, rect.y + rect.height / 2);
			break;
		case RIGHT: // from left to right
			pointOptionStart = PointOption.point(rect.x + edgeBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + rect.width - edgeBorder, rect.y + rect.height / 2);
			break;
		default:
			throw new IllegalArgumentException("swipeElementAndroid(): dir: '" + dir + "' NOT supported");
		}

		// execute swipe using TouchAction
		try {
			new TouchAction(driver).press(pointOptionStart)
					// a bit more reliable when we add small wait
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release()
					.perform();
		} catch (Exception e) {
			System.err.println("swipeElementAndroid(): TouchAction FAILED\n" + e.getMessage());
			return;
		}

		// always allow swipe action to complete
		try {
			Thread.sleep(ANIMATION_TIME);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

}
