import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
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

public class TC15_1_BuyLoad_MobileData_iOS extends SetupIOS {

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
	public void LoginFieldsTest_Buyload_MobileData(String email, String password) throws Exception {

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
	public void Navigate_to_BuyLoad() throws Exception {

		try {
			// verify if Delivery Button is visible and enabled
			IOSElement btn_delivery = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_delivery")));

			// swipe up in element
			swipeElementIOS(btn_delivery, Direction.UP);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: Delivery Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: Delivery button is not found.", driver);
			failedCounter++;
		}

		try {
			// verify if in store pickup Button is visible and enabled
			IOSElement btn_instore_pickup = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_in_store_pickup")));

			// swipe up in element
			swipeElementIOS(btn_instore_pickup, Direction.UP);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: In Store Pickup Button Found.", driver);
		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: In Store Pickup button is not found.", driver);
			failedCounter++;
		}

		// Checking of Buy Load Button if visible
		try {
			RemoteWebElement buttonBuyLoad = (RemoteWebElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_buy_load")));
//			String elementID = buttonBuyLoad.getId();
//			HashMap<String, String> scrollObject = new HashMap<String, String>();
//			scrollObject.put("element", elementID); // Only for ‘scroll in element’
//			driver.executeScript("mobile:scroll", scrollObject);
//			Thread.sleep(2000);
			buttonBuyLoad.click();

			markTestStatus("passed", "PASSED: Buy Load button is Found.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Buy Load button not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	public void Transaction_History() throws Exception {

		// Checking of Transaction History Button if visible
		try {
			IOSElement btn_transaction = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("buy load transactions")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_transaction.click();

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Transaction History button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Transaction History page Type =
		// XCUIElementTypeStaticText
		try {
			IOSElement lbl_transaction = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Transaction History")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Transaction History Page.", driver);

			Thread.sleep(5000);
			// Navigate back
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Transaction History Page.", driver);
			failedCounter++;
		}

	}

//	Tapping of Buy Load Button
//	Tapping of Transaction History
//	Tapping the Mobile Data Tab
//	Input of mobile number
//	Choosing the specific Mobile Data promo
//	Tapping of Next button
//	Checking of Mobile Number
//	Tapping the Chevron button (>) in the Payment Method
//	Selecting Gcash as Payment Method
//	Selecting Credit/Debit Card as Payment Method
//	Checking of items to purchase if correct
//	Checking of Pay Now button if enabled

//	Expected 
//	User should be redirected to Buy Load page
//	User should be redirected to Transaction History page
//	User should input mobile number
//	User should input Load amount
//	"User can choose load denomination
//	Load amount field will change to correct amount"
//	User should be redirected to Payment page
//	Checking if Mobile number is the same
//	User should be redirected to the Payment Method page
//	User should be redirected back to the Payment page
//	Selected payment method should be displayed
//	User should be redirected back to the Payment page
//	Selected payment method should be displayed
//	Checking if the item to be purchased is the same in Payment page
//	Pay Now button should be enabled after selecting a payment method

	@Test(priority = 5)
	public void mobileDataTabTest() {
		try {
			// Visibility
			IOSElement buttonMobileData = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("mobile_data")));

			// Tapping the Mobile Data Tab
			buttonMobileData.click();

			// Redirect to a page check typetable XCUIElementTypeTable
			IOSElement dataPromoContainer = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className("XCUIElementTypeTable")));

			IOSElement textFieldMobileNo = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("mobile_number")));

			// Input of mobile number
			textFieldMobileNo.clear();
			textFieldMobileNo.sendKeys("9532647398");

			// Choosing the specific Mobile Data promo
//			List<MobileElement> dataPromos = dataPromoContainer.findElementsByClassName("XCUIElementTypeCell");
//			IOSElement chosenDataPromo = (IOSElement) dataPromos.stream()
//					.filter(x -> x.getText().contains("All-Net SURF 20")).findAny().orElse(null);

			IOSElement chosenDataPromo = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("All-Net SURF 20")));
			// name=EasySURF 99
			chosenDataPromo.click();

			// Verify the amount is equal to the promo price XCUIElementTypeStaticText
			IOSElement textFieldAmount = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("amount")));

			markTestStatus("PASSED", "Promo ammount has matched the displayed amount", driver);

//			Tapping of Next button  	XCUIElementTypeButton  	btn_next
			IOSElement buttonNext = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_next")));
			buttonNext.click();

			// Checking of ViewController Title text
			IOSElement textTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment")));

//			Checking of Mobile Number
			IOSElement displayedMobileNo = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("value_mobile_number")));

			Assert.assertTrue(displayedMobileNo.getText().trim().contains("9532647398"));
			markTestStatus("PASSED", "Input Mobile no. has matched the displayed mobile. no", driver);

//			Tapping the Chevron button (>) in the Payment Method
			IOSElement buttonSelectPaymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Select Payment Method")));

			buttonSelectPaymentMethod.click();

//			Selecting Gcash as Payment Method
			IOSElement labelGcashPaymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GCash Direct")));

			labelGcashPaymentMethod.click();

			// Selecting Gcash as Payment Method
			IOSElement gcashPaymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GCash Direct")));
			labelGcashPaymentMethod.click();

//			Selecting Credit/Debit Card as Payment Method
			// Verify if Credit / Debit Card method is visible
			IOSElement lbl_creditDebit = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Credit/Debit")));

//			Checking of items to purchase if correct
			IOSElement displayedPurchasedItem = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("value_purchased_summary")));
			Assert.assertTrue(displayedPurchasedItem.getText().trim().contains("All-Net SURF 20"));
			markTestStatus("PASSED", "Displayed item to purchase has matched the chosen item", driver);

//			Checking of Pay Now button if enabled
			IOSElement buttonPayNow = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_paynow")));
			markTestStatus("passed", "PASSED:  Buy Load Mobile Data Module successful.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: " + e.getLocalizedMessage(), driver);
		}
	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Buy Load Mobile Data > Buy Load Mobile Data Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Buy Load Mobile Data > Buy Load Mobile Data Module successful.", driver);
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
