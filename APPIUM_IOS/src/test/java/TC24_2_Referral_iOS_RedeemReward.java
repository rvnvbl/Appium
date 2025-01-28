import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
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
import util.ExcelDataSupllier;

/**
 * @author avibal
 *
 */
public class TC24_2_Referral_iOS_RedeemReward extends SetupIOS {

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
	public void LoginFieldsTest_Referral_RedeemReward(String email, String password) throws Exception {

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
	public void DashboardTest() throws Exception {

		// Checking of P-Wallet Button if visible
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception f) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 3)
	private void navigateToMyVoucherPage() {
		String voucherName = "Pre-deployment Referral 1o1 @^_^@";
		String voucherDescription = "Pre-deployment  Referral Testing 1o1 @^_^@";

		// Account Button
		try {
			IOSElement accountTab = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));

			accountTab.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Account Tab does not found", driver);
			failedCounter++;
		}

		// Voucher Button
		try {
			IOSElement myVouchersOption = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Vouchers")));

			myVouchersOption.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher option does not found", driver);
			failedCounter++;
		}

		// Voucher Page Title
		try {
			IOSElement myVouchersOption = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Vouchers")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher option does not found", driver);
			failedCounter++;
		}

		// Voucher Title
		try {
			List<IOSElement> voucherTitles = driver.findElements(By.name("titleLbl"));
			int counter = 0;

			for (int i = 0; i < voucherTitles.size(); i++) {
				if (voucherTitles.get(i).getText().contains(voucherName)) {
					counter++;
					break;
				}
			}
			if (counter <= 0) {
				markTestStatus("failed", "FAILED: My Voucher title does not found", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher title does not found", driver);
			failedCounter++;
		}

		// Voucher Description
		try {

			List<IOSElement> voucherDescriptions = driver.findElements(By.name("descriptionLbl"));
			int counter = 0;

			for (int i = 0; i < voucherDescriptions.size(); i++) {
				if (voucherDescriptions.get(i).getText().contains(voucherDescription)) {
					counter++;
					break;
				}
			}
			if (counter == 0) {
				markTestStatus("failed", "FAILED: My Voucher description does not found", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher description does not found", driver);
			failedCounter++;
		}
		// Voucher Date
		try {
			IOSElement voucherDate = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("dateLbl")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher date date does not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	private void validateTermsAndCondition() {
		String voucherName = "Pre-deployment Referral 1o1 @^_^@";
		String voucherDescription = "Pre-deployment Referral Testing 1o1 @^_^@";
		// Click terms and Condition
		try {

			List<IOSElement> termsButtons = driver.findElements(By.name("termsBtn"));
			System.out.println("Terms Size: " + termsButtons.size());
			termsButtons.get(termsButtons.size()-1).click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Voucher terms and condition does not found", driver);
			failedCounter++;
		}

		// Verify the element in terms and condition page
		// Voucher Title
		try {
			List<IOSElement> voucherTitles = driver.findElements(By.name("titleLbl"));
			int counter = 0;

			for (int i = 0; i < voucherTitles.size(); i++) {
				if (voucherTitles.get(i).getText().contains(voucherName)) {
					counter++;
					break;
				}
			}
			if (counter <= 0) {
				markTestStatus("failed", "FAILED: My Voucher title does not found", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher title does not found", driver);
			failedCounter++;
		}

		// Get all the text in the page
		List<IOSElement> staticTextList = driver.findElements(By.className("XCUIElementTypeStaticText"));

		// Click the back button
		try {
			IOSElement backButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Vouchers")));
			backButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Back button does not found", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5)
	private void redeemVoucherWrongPwalletID() {
		String wrongPwalletID = "6104529967122688";
		String voucherName = "Pre-deployment Referral 1o1 @^_^@";

		// Click the use now button
		try {
			IOSElement useNowButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("useNowBtn")));
			useNowButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher expiration date does not found", driver);
			failedCounter++;
		}

		// Validate the pop up title
		try {
			IOSElement popUpTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Redeem the " + voucherName)));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) title does not found", driver);
			failedCounter++;
		}

		// Validate the pop up description
		try {
			IOSElement popUpDescription = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet Account number or Email Address")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) description does not found", driver);
			failedCounter++;
		}

		// Look and validate for the popup textfield
		// Enter the wrong Pwallet ID
		try {
			List<IOSElement> txtfieldList = driver.findElements(By.className("XCUIElementTypeTextField"));
			txtfieldList.get(0).sendKeys(wrongPwalletID);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) textfield does not found", driver);
			failedCounter++;
		}

		// Look and validate for the Redeem Button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Redeem")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) button does not found", driver);
			failedCounter++;
		}

		// Validate the pop up title
		try {
			IOSElement popupTitle = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Invalid P-Wallet Account Number")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Error Popup) title does not found", driver);
			failedCounter++;
		}

		// Validate the pop up description
		try {
			IOSElement popUpDescription = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("Sorry, Entered P-Wallet Account Number is Incorrect.")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Error Popup) description does not found", driver);
			failedCounter++;
		}

		// Look and validate to okay button
		try {
			IOSElement okayButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("okayBtn")));
			okayButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Error Popup) button does not found", driver);
			failedCounter++;
		}
	}

	@Test(priority = 6, enabled = true)
	private void redeemVoucherCorrectPwalletID() {
		String correctPwalletID = "6104529967122689";
		String voucherName = "Pre-deployment Referral 1o1 @^_^@";

		// Validate the pop up title
		try {

			IOSElement popUpTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Redeem the " + voucherName)));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) title does not found", driver);
			failedCounter++;
		}

		// Validate the pop up description
		try {
			IOSElement popUpDescription = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet Account number or Email Address")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) description does not found", driver);
			failedCounter++;
		}

		// Look and validate for the popup textfield
		// Enter the correct Pwallet ID
		try {
			List<IOSElement> txtfieldList = driver.findElements(By.className("XCUIElementTypeTextField"));
			txtfieldList.get(0).sendKeys(correctPwalletID);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) textfield does not found", driver);
			failedCounter++;
		}

		// Look and validate for the Redeem Button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Redeem")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Popup) button does not found", driver);
			failedCounter++;
		}

		// Validate the pop up title
		try {
			IOSElement popupTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Successfully Credited")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Correct Popup) title does not found", driver);
			failedCounter++;
		}

		// Validate the pop up description
		try {
			IOSElement popUpDescription = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy
							.name("You have successfully redeemed " + voucherName + " for " + correctPwalletID + "")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Correct Popup) description does not found", driver);
			failedCounter++;
		}

		// Look and validate to okay button
		try {
			IOSElement okayButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("OKAY")));
			okayButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Voucher (Correct Popup) button does not found", driver);
			failedCounter++;
		}
	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Referral > Redeem Reward> has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Referral > Redeem Reward > successful.", driver);
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
