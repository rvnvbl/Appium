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
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.ExcelDataSupllier;

public class TC21_1_iOS_MaxAllocation_StoreHomepage extends SetupIOS {

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
	public void LoginFieldsTest_maxAllocation(String email, String password) throws Exception {

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
	private void selectProduct() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		int categoryHeader = 0;
		int productQTY = 998;
		try {
			IOSElement sallysDealHeader = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Sally's Deals")));

			// Get the height
			categoryHeader = Integer.parseInt(getHeight(sallysDealHeader));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Sallys Deal Header / error.", driver);
			failedCounter++;
		}

		// Get all the add to cart that are visible on screen

		List<IOSElement> addToCart = driver.findElements(By.name("ADD TO CART"));
		List<IOSElement> sallysAddToCart = new ArrayList<>();
		for (int i = 0; i < addToCart.size(); i++) {
			if (addToCart.get(i).isDisplayed() && addToCart.get(i).isEnabled()
					&& addToCart.get(i).getAttribute("type").equalsIgnoreCase("XCUIElementTypeButton")) {
				sallysAddToCart.add(addToCart.get(i));
			}

		}

		List<IOSElement> lblsold = driver.findElements(By.name("lblSold"));
		List<IOSElement> sallysDealLabelSold = new ArrayList<>();

		for (int i = 0; i < lblsold.size(); i++) {

			if (Integer.valueOf(getHeight(lblsold.get(i))) - Integer.valueOf(categoryHeader) == 304) {
				sallysDealLabelSold.add(lblsold.get(i));
			}
		}

		// Getting the number of Sold Product
		String secondnumberOfSold = sallysDealLabelSold.get(0).getText().trim();
		String seconddigitSoldString = secondnumberOfSold.substring(0, secondnumberOfSold.indexOf("S"));
		int seconddigitSold = Integer.parseInt(seconddigitSoldString.trim());

		System.out.println("Sallys Deal Label Value: " + seconddigitSold);
		int availableQTY = 50 - seconddigitSold;

		// Click the first Add To Cart Button
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(sallysAddToCart.get(0))).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		try {
			Thread.sleep(4000);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<IOSElement> txtFieldQtyList = driver.findElements(By.name("quantityTF"));
			txtFieldQtyList.get(0).clear();
			txtFieldQtyList.get(0).sendKeys("100000");
			driver.hideKeyboard();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Text Field not found / error.", driver);
			failedCounter++;
		}

		try {
			IOSElement popUpMaxQuantity = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Reached Max Quantity")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Max Quantity popup not found / error.", driver);
			failedCounter++;
		}

		try {
			IOSElement okayButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("OKAY")));
			okayButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Max Quantity popup Okay button not found / error.", driver);
			failedCounter++;
		}

		// Send value to textField
		try {
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<IOSElement> txtFieldQtyList = driver.findElements(By.name("quantityTF"));
			txtFieldQtyList.get(0).clear();
			txtFieldQtyList.get(0).sendKeys(String.valueOf(productQTY));
			driver.hideKeyboard();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Text Field not found / error.", driver);
			failedCounter++;
		}



		try {

			for (; true;) {
				// Set the wait

				IOSElement plusButton = (IOSElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.elementToBeClickable(MobileBy.name("cart plus")));

				plusButton.click();

				// Check if the "Out of Stock" pop-up is present
				IOSElement outOfStockPopup = null;

				try {
					outOfStockPopup = driver.findElementById("Reached Max Quantity");
				} catch (Exception e) {
					System.out.println("Error Pop up not found");
					continue;
				}

				if (outOfStockPopup.isDisplayed()) {
					// The "Out of Stock" pop-up is displayed, break out of the loop
					break;
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Plus button not found / error.", driver);
			failedCounter++;
		}

		try {
			IOSElement okayButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("OKAY")));
			okayButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Okay button not found / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	private void cartPageTest() {
		// Cart page
		try {
			IOSElement cartNavButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
			markTestStatus("passed", "PASSED: Cart Button Found", driver);
			cartNavButton.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cart Button not found.", driver);
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
		}

	}

	@Test(priority = 6)
	private void clearCart() {
		List<IOSElement> txtCart = driver.findElementsByClassName("XCUIElementTypeTextField");
		txtCart.get(0).clear();
		txtCart.get(0).sendKeys("1");

		try {

			IOSElement deleteButton = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'delete quantity'")));
			deleteButton.click();
			markTestStatus("passed", "PASSED: Delete button shows", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delete button not shows.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 11)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Max Allocation Passed (Store Homepage) has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Max Allocation Passed (Store Homepage).", driver);
		}

	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

	private void scrollToElement(IOSElement sallysDealHeader) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		HashMap<String, Object> scrollobj = new HashMap<String, Object>();
		Thread.sleep(2000);
		scrollobj.put("predicateString", "value == '" + sallysDealHeader + "'");
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

	private void scrollDown() {
//		HashMap<String, String> scrollObject = new HashMap<>();
//		JavascriptExecutor js = driver;
//		scrollObject.put("direction", "down");
//		scrollObject.put("percent", "1");
//		js.executeScript("mobile: scrollGesture", scrollObject); // or "mobile: swipe"

		HashMap<String, Object> params = new HashMap<>();
		params.put("duration", .5);
		params.put("fromX", 0);
		params.put("fromY", 600);
		params.put("toX", 0);
		params.put("toY", 300);
		driver.executeScript("mobile: dragFromToForDuration", params);

	}

	private void scrollUp() {
		HashMap<String, String> scrollObject = new HashMap<>();
		JavascriptExecutor js = driver;
		scrollObject.put("direction", "up");
		scrollObject.put("pixel", "2");
		js.executeScript("mobile: scroll", scrollObject); // or "mobile: swipe"
	}

	private String getHeight(MobileElement eleme) {

		String headerLocation = eleme.getLocation().toString();
		String height = headerLocation.substring(headerLocation.lastIndexOf(',') + 1, headerLocation.indexOf(')'))
				.trim();
		return height;
	}

}
