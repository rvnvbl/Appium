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
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC25_2_BillsPay_EcBillNoBller_iOS extends SetupIOS {

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
	public void LoginFieldsTest_BillsPay_EcBills(String email, String password) throws Exception {

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
	public void Navigate_to_Bills_Pay() throws Exception {

		try {
			// verify if Delivery Button is visible and enabled
			IOSElement btn_delivery = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_delivery")));

			// swipe up in element
			swipeElementIOS(btn_delivery, Direction.UP);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

		} catch (Exception ex) {
			markTestStatus("failed", "FAILED: In Store Pickup button is not found.", driver);
			failedCounter++;
		}

		// Checking of Bills pay Button if visible
		try {
			RemoteWebElement btn_billsPay = (RemoteWebElement) driver.findElement(MobileBy.name("home_btn_bills_pay"));
			String elementID = btn_billsPay.getId();
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("element", elementID); // Only for ‘scroll in element’
			scrollObject.put("direction", "down");
			driver.executeScript("mobile:scroll", scrollObject);
			btn_billsPay.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Bills Pay button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Bills Pay page
		try {
			IOSElement lbl_BillsPay = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Bills Pay")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Bills Pay Page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Bills Pay Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4, enabled = true)
	public void Navigate_to_ecBills() throws Exception {

		// Checking of Bills pay Button if visible
		try {
			IOSElement btn_ecBills = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("ecBills")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_ecBills.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: ecBills button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to ecBills page
		try {
			IOSElement lbl_ecBills = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("ecBills")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to ecBills Page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to ecBills Page.", driver);
			failedCounter++;
		}

		// Checking of Bills pay Button if visible
		try {
			IOSElement btn_donaton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Donation")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_donaton.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Donaton button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to ecBills page
		try {
			IOSElement lbl_donaton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Donation")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Donaton Page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Donaton Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	public void maintenanceCheck() throws Exception {

		// Checking of Transaction History Button if visible
		try {
			IOSElement btn_transaction = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Biller Merchant Maintenance")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_transaction.click();

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Maintenance Title not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Transaction History page
		try {
			IOSElement lbl_transaction = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
							"We have ongoing maintenance. Please try again later. Thank you for your understanding.")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Navigate back
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Maintenance Spiel not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Bills Pay > No EcBills Biller Maintenance Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Bills Pay > No EcBills Biller Maintenance Module successfull", driver);
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
