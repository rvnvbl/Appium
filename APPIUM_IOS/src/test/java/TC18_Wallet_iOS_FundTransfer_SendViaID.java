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

public class TC18_Wallet_iOS_FundTransfer_SendViaID extends SetupIOS {

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
	public void LoginFieldsTest_FundTransfer_SendViaID(String email, String password) throws Exception {

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

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 3, enabled = false)
	private void navigateToPwalletWithoutPwalletPin() {
		// Checking of P-Wallet Button if pwalletPage
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_pwallet.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to P-Wallet Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 3, enabled = true)
	public void Navigate_to_PWallet_withPinwallet() throws Exception {

		// Checking of P-Wallet Button if pwalletPage
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_pwallet.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to P-Wallet Page.", driver);
			failedCounter++;
		}

		// Check if redirected to P-Wallet page

		try {
			IOSElement enterPwalletLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet PIN")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Enter P-Wallet label not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement enterPwalletLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet PIN")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Enter P-Wallet label not found.", driver);
			failedCounter++;
		}
		// Filling up the pin with correct pin
		try {
			List<IOSElement> pwalletPinTextField = driver.findElements(By.className("XCUIElementTypeTextField"));
			for (int i = 0; i < 6; i++) {
				pwalletPinTextField.get(i).sendKeys("1");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pwallet pin textfield not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement labelForgotPin = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Forgot PIN?")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Forgot label not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement btnSubmit = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Submit")));
			if (btnSubmit.isEnabled()) {
				System.out.println("Submit button is enable");
			} else {
				markTestStatus("failed", "FAILED: Submit button is disabled.", driver);
				failedCounter++;
			}
			btnSubmit.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Submit button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to P-Wallet page
		try {
			IOSElement lbl_pWallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to P-Wallet Page.", driver);

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to P-Wallet Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4, enabled = false)
	private void checkTransactionHistory() {
		// Opening Transaction history via view all link
		try {

			List<IOSElement> showAllElements = driver.findElements(By.name("Show all"));
			List<IOSElement> btnShowAllElements = new ArrayList<>();
			for (int i = 0; i < showAllElements.size(); i++) {
				System.out.println(showAllElements.get(i).getAttribute("type"));
				if (showAllElements.get(i).getAttribute("type").equalsIgnoreCase("XCUIElementTypeButton")) {
					btnShowAllElements.add(showAllElements.get(i));
				}
			}
			btnShowAllElements.get(1).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Show all button not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement transactionHistoryTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Transaction History")));
			markTestStatus("passed", "PASSED: Redirected to Transaction History Page Successful.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Not redirected to Transaction History Page not Successful.", driver);
			failedCounter++;
		}

		try {
			List<IOSElement> textList = driver.findElements(By.className("XCUIElementTypeStaticText"));
			for (int i = 0; i < 4; i++) {

				if (textList.get(i).getText().contains("Received P-Wallet")) {
					System.out.println("Description Found");
				}

				if (textList.get(i).getText().contains("Fund Transfer")) {
					System.out.println("Action Found");
				}

				if (textList.get(i).getText().contains("₱1.00")) {
					System.out.println("Price Found");
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: The Details is not complete.", driver);
			failedCounter++;
		}

		List<IOSElement> chevronList = driver.findElements(By.name("chevron"));
		chevronList.get(0).click();

		try {
			List<IOSElement> textList = driver.findElements(By.className("XCUIElementTypeStaticText"));
			for (int i = 0; i < 4; i++) {

				if (textList.get(i).getText().contains("Jul 17, 2023 10:38:40")) {
					System.out.println("Date Transaction Found");
				}

				if (textList.get(i).getText().contains("2800491912942869")) {
					System.out.println("Wallet ID Found");
				}

				if (textList.get(i).getText().contains("₱1.00")) {
					System.out.println("Price Found");
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: The Details is not complete.", driver);
			failedCounter++;
		}
		// Click back in navigation bar
		driver.navigate().back();
		driver.navigate().back();
	}

	@Test(priority = 5)
	private void checkPwalletPage() {

		// Check if redirected to P-Wallet page
		try {
			IOSElement lbl_pWallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to P-Wallet Page.", driver);

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to P-Wallet Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 6)
	private void sendviaPwalletID() {
		// Send via Pwallet ID
		// Click Send Pwallet
		try {
			IOSElement btnSendPWallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Send Funds")));

			btnSendPWallet.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Send Pwallet button not found.", driver);
			failedCounter++;
		}

		// Modal appear click send via Pwallet ID
		try {
			IOSElement btnSendViaWalletID = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Send via P-Wallet ID")));
			btnSendViaWalletID.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal did not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 7, dataProvider = "PwalletID", dataProviderClass = DataProvider_Login.class)
	public void fundTransferViaIDTest(String Email, String PwalletID, String amount) throws InterruptedException {
		try {
			IOSElement titleFundTransfer = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Fund Transfer")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Fund Transfer title not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement titleFundTransfer = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Avalilable Balance")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Available balance not found.", driver);
			failedCounter++;
		}

		// Check First Field
		// Send text on email field, ID field must disabled.
		// Semd text on ID field, Email field must disabled.
		// Cant check the enability of textfields
		// Textfield List * 0 - Email, 1 - PWallet ID, 3 Amount
		List<IOSElement> textFieldList = driver.findElements(By.className("XCUIElementTypeTextField"));
		textFieldList.get(0).sendKeys(Email);
		textFieldList.get(0).clear();
		textFieldList.get(1).sendKeys(PwalletID);

		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
		textFieldList.get(2).sendKeys(amount);

		btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();

		// Checking if the next button is showed
		try {
			IOSElement btnNext = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Next")));
			btnNext.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Next button not found.", driver);
			failedCounter++;
		}

		// Checking if it landed on the confirmation page
		try {
			IOSElement titleConfirmation = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Confirmation")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Not redirected to Confirmation Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 8, dataProvider = "PwalletID", dataProviderClass = DataProvider_Login.class)
	public void confirmationPageTest(String Email, String PwalletID, String amount) {
		// Getting all the text field in the confirmation page
		try {

			List<IOSElement> confirmationTextFieldList = driver.findElements(By.className("XCUIElementTypeTextField"));
			String pwalletID = confirmationTextFieldList.get(0).getText();
			String pwalletEmail = confirmationTextFieldList.get(1).getText();
			if (pwalletID.contains(PwalletID)) {
				System.out.println("The Pwallet ID is same");
			} else {
				markTestStatus("failed", "FAILED: PWALLET ID is not same.", driver);
				failedCounter++;
			}

			if (pwalletEmail.contains(Email)) {
				System.out.println("The Pwallet email is same");
			} else {
				markTestStatus("failed", "FAILED: PWALLET EMAIL is not same.", driver);
				failedCounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Not redirected to Confirmation Page.", driver);
			failedCounter++;
		}

		try {
			IOSElement btnSendConfirmation = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Send ₱" + amount + ".00")));
			btnSendConfirmation.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Button Pay not found in Confirmation Page.", driver);
			failedCounter++;
		}

		try {
			IOSElement titlePwalletPin = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet PIN")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: P-Wallet Pin title not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 9)
	public void pwalletPinPageTest() {

		try {
			IOSElement enterPwalletLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet PIN")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Enter P-Wallet label not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement enterPwalletLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet PIN")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Enter P-Wallet label not found.", driver);
			failedCounter++;
		}
		// Filling up the pin in the Pwallet Pin
		List<IOSElement> pwalletPinTextField = driver.findElements(By.className("XCUIElementTypeTextField"));
		pwalletPinTextField.get(0).sendKeys("1");
		pwalletPinTextField.get(1).sendKeys("1");
		pwalletPinTextField.get(2).sendKeys("1");
		pwalletPinTextField.get(3).sendKeys("1");
		pwalletPinTextField.get(4).sendKeys("1");
		pwalletPinTextField.get(5).sendKeys("1");

		try {
			IOSElement labelForgotPin = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Forgot PIN?")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Forgot label not found.", driver);
			failedCounter++;
		}

		try {
			IOSElement btnSubmit = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Submit")));
			if (btnSubmit.isEnabled()) {
				System.out.println("Submit button is enable");
			} else {
				markTestStatus("failed", "FAILED: Submit button is disabled.", driver);
				failedCounter++;
			}
			btnSubmit.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Submit button not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 10)
	public void fundTransferSuccessfulPageTest() {
		try {
			IOSElement fundTransferSuccessfulTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Fund Transfer Successful")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Fund Transfer Succesful title not found.", driver);
			failedCounter++;
		}

//		List<IOSElement> textFieldElementClass = driver.findElements(By.className("XCUIElementTypeStaticText"));
//
//		try {
//			IOSElement saveUserFavorites = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Save this user to favorites")));
//			saveUserFavorites.click();
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Save user button not found.", driver);
//			failedCounter++;
//		}
//
//		try {
//			IOSElement addFavoritesModal = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Added to Favorites")));
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Add to favorites modal not found.", driver);
//			failedCounter++;
//		}
//
//		try {
//			IOSElement okayButton = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("OKAY")));
//			okayButton.click();
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Okay button in modal not found.", driver);
//			failedCounter++;
//		}

	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Wallet > Fund Transfer > Send Via ID Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Wallet > Fund Transfer > Send via ID Module successful.", driver);
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
