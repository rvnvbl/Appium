import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC9_1_Orders_CancelOrder_iOS extends SetupIOS {

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
	public void LoginFieldsTest_CancelOrder(String email, String password) throws Exception {

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
			// verify if email textfield is visible and enabled
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));

			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 3)
	public void Navigate_orders_page() throws Exception {

		// Navigate to Orders Page
		try {
			// verify if Orders Button is visible and enabled
			IOSElement btn_order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));
			btn_order.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Orders button is not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Orders Page
		try {
			IOSElement lbl_order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));

			markTestStatus("passed", "PASSED: Successfully redirected to Orders page.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Orders page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	public void Order_details_page() throws Exception {

		// Order list
		try {

			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			list_orders.get(0).click();

			markTestStatus("passed", "PASSED: Order list found", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Order list found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Orders Details page
		try {
			IOSElement lbl_reorder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Reorder")));

			markTestStatus("passed", "PASSED: Successfully redirected to Orders details page.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Orders details page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	public void Cancel_Order() throws Exception {

		// Verify if Cancel Order button is visible
		try {

			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			// swipe up in element
			// swipeElementIOS(list_orders.get(0), Direction.UP);
			scrollToElement("Cancel Order");
			IOSElement btn_cancelOrder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Order")));
			btn_cancelOrder.click();

			markTestStatus("passed", "PASSED: Cancel Order button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order button not found.", driver);
			failedCounter++;
		}

		// Checking if Cancel Your Order popup is visible
		try {
			IOSElement lbl_cancel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Your Order")));

			markTestStatus("passed", "PASSED: Cancel Order popup found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order popup not found / error.", driver);
			failedCounter++;
		}

		// Verify if Go Back button is visible
		try {
			IOSElement btn_goBack = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Go Back")));
			btn_goBack.click();

			markTestStatus("passed", "PASSED: Go Back button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Go Back button not found.", driver);
			failedCounter++;
		}

		// Verify if Cancel Order button is visible
		try {
			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			// swipe up in element
			// swipeElementIOS(list_orders.get(0), Direction.UP);
			scrollToElement("Cancel Order");
			IOSElement btn_cancelOrder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Order")));
			btn_cancelOrder.click();

			markTestStatus("passed", "PASSED: Cancel Order button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order button not found.", driver);
			failedCounter++;
		}

		// Checking if Cancel Your Order popup is visible
		try {
			IOSElement lbl_cancel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Your Order")));

			markTestStatus("passed", "PASSED: Cancel Order popup found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order popup not found / error.", driver);
			failedCounter++;
		}

		// Select last item in radio button
		try {
			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_radioButton = driver.findElementsByName("cancel unselected");

			// get last index
			Integer idx = list_radioButton.size() - 1;
			list_radioButton.get(idx).click();

			markTestStatus("passed", "PASSED: Radio button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Radio button not found / error.", driver);
			failedCounter++;
		}

		// Verify if Cancel Order button is visible
		try {
			Thread.sleep(5000);
			List<IOSElement> cancelOrderButtons = driver.findElements(By.name("Cancel Order"));
			System.out.println("The number of cancel button is: " + cancelOrderButtons.size());
			cancelOrderButtons.get(1).click();
//			IOSElement btn_cancelOrder = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Cancel Order")));
//			//IOSElement btn_cancelOrder = driver.findElementByIosNsPredicate("type == 'UIAButton' AND label CONTAINS 'Cancel Order'");
//			btn_cancelOrder.click();

			markTestStatus("passed", "PASSED: Cancel Order button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order button not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Order Cancelled page
		try {
			IOSElement lbl_cancel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Order Cancelled")));

			markTestStatus("passed", "PASSED: Successfully redirected to Order Cancelled page.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Order Cancelled page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Orders > Cancel Order module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Orders > Cancel Order Module successful.", driver);
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

	private void scrollToElement(String elementValue) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		HashMap<String, Object> scrollobj = new HashMap<String, Object>();
		scrollobj.put("predicateString", "value == '" + elementValue + "'");
		scrollobj.put("direction", "down");
		executor.executeScript("mobile: scroll", scrollobj);

	}

}
