import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
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
import util.ExcelDataSupllier;

public class TC4_1AddToCart_StoreHomePage_iOS extends SetupIOS {

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
	public void LoginFieldsTest_addToCartStore_Homepage(String email, String password) throws Exception {

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
	public void Navigate_store_homepage() throws Exception {

		// Navigate to Store Homepage
		try {
			// verify if in store pickup Button is visible and enabled
			IOSElement btn_instore_pickup = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_in_store_pickup")));
			btn_instore_pickup.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: In Store Pickup button is not found.", driver);
			failedCounter++;
		}

		// Search Puregold Makati branch in search bar

		try {

			// Checking if Store search bar is visible then input of search item
			IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("Search for Store Branch, City or Province")));
			searchbar.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			searchbar.sendKeys("puregold makati");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Store search bar could not be found.", driver);
			failedCounter++;
		}

		// Checking of Puregold Makati branch on store list
		try {
			IOSElement puregold_branch = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("PUREGOLD MAKATI")));

			puregold_branch.click();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Puregold Makati branch is not found / error.", driver);
			failedCounter++;
		}

		// Checking if redirected to store Homepage
		try {
			IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Search for products and brands")));

			markTestStatus("passed", "PASSED: Successfully redirected to store homepage", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Store Homepage / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	private void selectProduct() {
		String headerHeight = "";
		try {
			scrollToElement("Globe Products");
			IOSElement globeProductsHeader = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Globe Products")));
			headerHeight = getHeight(globeProductsHeader);
			markTestStatus("passed", "PASSED: Successfully locate the header", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to the header / error.", driver);
			failedCounter++;
		}
		// Getting all the add to cart button
		List<IOSElement> addToCartList = driver.findElements(By.name("ADD TO CART"));
		List<IOSElement> displayedAddToCart = new ArrayList<>();
		for (int i = 0; i < addToCartList.size(); i++) {
			if (addToCartList.get(i).isEnabled()) {
				if (getHeight(addToCartList.get(i)).equals("759")) {
					displayedAddToCart.add(addToCartList.get(i));
				}

			}
		}

		// Click the add to cart of the first item from the globe products
		displayedAddToCart.get(0).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Click add so the quantity become 2
		try {
			IOSElement cartPlus = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart plus")));
			cartPlus.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Plus button not found / error.", driver);
			failedCounter++;
		}
		// Click minus button so the quantity become 1
		try {
			IOSElement cartMinus = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart minus")));
			cartMinus.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Minus button not found / error.", driver);
			failedCounter++;
		}
		// Locate Delete Button once the qty is 1
		try {
			IOSElement cartDelete = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("delete quantity")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delete button not found / error.", driver);
			failedCounter++;
		}
		// Click cart icon on Store Homepage
		try {
			IOSElement carticon = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
			carticon.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delete button not found / error.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5)
	private void cartPage() {
		// Check if redirected to cart page
		try {

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			// verify if redirected to Cart page
			IOSElement lbl_cart = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));

			markTestStatus("passed", "PASSED: Redirected to Cart page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Cart page.", driver);
			failedCounter++;
		}

		// Check if pop up warning appeared to cart page
		try {

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			Thread.sleep(5000);

			// verify if okay button is visible
			IOSElement btn_okay = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Okay")));
			btn_okay.click();

			markTestStatus("passed", "Popup warning displayed.", driver);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("passsed", "No Popup warning displayed.", driver);
			failedCounter++;
		}

		try {
			// verify if item is on cart
			IOSElement bnt_delete = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("delete quantity")));
			bnt_delete.click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("passsed", "Item is not displayed on cart.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 11)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Add to Cart Module (Store Homepage) has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Add to Cart Module (Store Homepage) successful.", driver);
		}

	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

	private void scrollToElement(String elementValue) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		HashMap<String, Object> scrollobj = new HashMap<String, Object>();
		scrollobj.put("predicateString", "value == '" + elementValue + "'");
		scrollobj.put("direction", "down");
		executor.executeScript("mobile: scroll", scrollobj);

	}

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

	private String getHeight(MobileElement eleme) {

		String headerLocation = eleme.getLocation().toString();
		String height = headerLocation.substring(headerLocation.lastIndexOf(',') + 1, headerLocation.indexOf(')'))
				.trim();
		return height;
	}

}
