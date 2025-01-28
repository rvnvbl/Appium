import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Negative_TC3_0_Delivery_Android extends AndroidSetup {

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

	@Test(priority = 1)
	public void LoginFieldsTest() throws Exception {

		// Clicking and input of email on email field
		try {
			AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));
			EmailField.click();
			EmailField.sendKeys("mnastor@yondu.com");

			System.out.println("Test 2 passed: Email input success");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Field is not found.", driver);
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking and input of password on password field
		try {
			AndroidElement PassField = (AndroidElement) driver.findElement(By.id("login_password_field"));
			PassField.click();
			PassField.sendKeys("TestAccount123");

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
	public void TapAddAddressTest() throws Exception {
		// Scrolling to and clicking the Delivery button
		try {
			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
							+ "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");

			AndroidElement DeliveryOptionButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_delivery_layout_image")));
			
			DeliveryOptionButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// Checking if Reminder Modal from Delivery is visible to know if redirect is
			// successful
			AndroidElement ReminderModal = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_container")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delivery button is not found.", driver);
		}

		// Clicking of "OK" Button on reminder
		try {
			AndroidElement ReminderModalAccept = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));
			ReminderModalAccept.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Reminder OK button is not found.", driver);
		}

		// Clicking of "Accept Terms" Button on Guidelines
		try {
			AndroidElement AcceptTermsButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("terms_conditions_accept")));
			AcceptTermsButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			/*
			 * Checking if Add address is visible to know if redirect is successful Text is
			 * used because element ID changes when there is an existing address already
			 */
			AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Add Address\"));")));
			System.out.println("Address Saved List Shows");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
		}

	}

	@Test(priority = 4)
	public void DeliverySelectAddressTest() throws Exception {
		// Find and click address located in Makati
		try {
			AndroidElement MakatiAddress = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Test2, Poblacion, Makati City, Metro Manila, 1313\"));")));

			MakatiAddress.click();

			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Makati Address cannot be found/error", driver);
		}

		// Checking of Store list if redirected successfully
		try {
			// Checking if Store search bar is visible after clicking the address to know if
			// redirect is successful
			AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to store selection screen correctly.", driver);
		}

	}

	@Test(priority = 5)
	public void DeliveryStoreListTest() throws Exception {
		// Checking of Puregold branches nearby that should show on the list (Makati &
		// Express Delivery Area)
		try {
			AndroidElement PuregoldMakatiBranch = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions
							.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
									+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
									+ "new UiSelector().text(\"PUREGOLD MAKATI\"));")));
			System.out.println("Store List Shows");
			
		} catch (Exception e) {
			markTestStatus("failed",
					"FAILED: Store Branches nearby (Puregold Makati and Express Delivery) is not visible.", driver);
		}

	}

	

	@Test(priority = 6)
	public void DeliveryStoreSelectTest() throws Exception {
		// Clicking the specific store search result (Puregold Express Delivery)

		// Searching for Makati on search bar
		try {
			// Checking if Store search bar is visible then input of search item
			AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));

			StoreSearchBar.click();
			StoreSearchBar.sendKeys("Puregold Canada");
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Store search bar could not be found.", driver);
		}

		// Checking of the cart icon shows
		try {
			// Checking if the cart shows
			AndroidElement cartlogo = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_no_results_image")));
			System.out.println("Test passed: Cart icon displayed");

			// Checking if the no search header
			AndroidElement noSearchHeader = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_not_found_title")));

			String errorHeader = noSearchHeader.getText();
			String result = errorHeader.replaceAll("\\s", "");
			assertTrue(result.contains("Nosearchresults"));

			System.out.println("Test passed: No search header shows");

			// Checking if the no search header
			AndroidElement noSearchMessage = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_not_found_description")));

			String errorMessage = noSearchMessage.getText();
			String resultMessage = errorMessage.replaceAll("\\s", "");
			assertTrue(resultMessage.contains("Wecouldn’tfindanystorebranchmatchingyoursearch."));
			System.out.println("Test passed: Error message shows");
			markTestStatus("passed", "Test passed: Delivery Module (Negative Search) Successful", driver);

			// - "We couldnt find any store branch matching your search"
		} catch (Exception e) {
			// TODO Auto-generated catch block
			markTestStatus("failed", "FAILED: Cart logo could not be found.", driver);
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
