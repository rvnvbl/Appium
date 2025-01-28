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
public class TC18_2_Wallet_iOS_FundTransfer_RecieveViaQR extends SetupIOS {

	int failedCounter = 0;

	@Test(priority = 0)
	public void LoginButtonTest() throws Exception {
		// Clicking of Login with email button on startup screen

		try {
			IOSElement btn_login = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Log in")));
			btn_login.click();

			// verify if email textfield is visible and enabled
			IOSElement textfield_email = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Email")));
			// verify if Login Button is visible and enabled
			IOSElement btn_login2 = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Login")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	public void LoginFieldsTest_FundTransfer_RecieveViaQR(String email, String password) throws Exception {

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// Clicking and input of email on email field
		try {
			IOSElement EmailField = (IOSElement) new WebDriverWait(driver, 10)
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
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 10)
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

	}

	@Test(priority = 4, enabled = true)
	private void checkTransactionHistory() {
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
			IOSElement transactionHistoryTitle = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Transaction History")));
			markTestStatus("passed", "PASSED: Redirected to Transaction History Page Successful.", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Not redirected to Transaction History Page not Successful.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5)
	private void checkPwalletPage() {
		// Click back in navigation bar
		driver.navigate().back();
		// Check if redirected to P-Wallet page
		try {
			IOSElement lbl_pWallet = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to P-Wallet Page.", driver);

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to P-Wallet Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 6, dataProvider = "PwalletID", dataProviderClass = DataProvider_Login.class)
	private void recievedViaQRCodeTest(String Email, String PwalletID, String amount) {
		// Checking QR Code Button in Pwallet Page
		try {
			IOSElement btnRecieveQR = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Recieve via QR")));
			btnRecieveQR.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Recived QR Code doesnt found.", driver);
			failedCounter++;
		}

		// Checking if the My P wallet QR Code title is found on Pwallet QR Page
		try {
			IOSElement btnRecieveQR = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My P-Wallet QR")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My P-wallet QR title doesnt found.", driver);
			failedCounter++;
		}

		// Getting all the text on page
		List<IOSElement> textInQRPage = driver.findElements(By.className("XCUIElementTypeStaticText"));

		// 0 : Title
		// 1 : QR Code Instruction
		// 2 : Username
		// 3 : Pwallet ID

		String QrCodeInstruction = textInQRPage.get(1).getText();
		String Username = textInQRPage.get(2).getText();
		String PwalletIDWithLabel = textInQRPage.get(3).getText();
		String PwalletIDonly = PwalletIDWithLabel.substring(12).trim();

		// Check if the Label is same
		if (!QrCodeInstruction.equals(
				"Scan this QR Code on another device to allow other users to transfer funds to your P-Wallet.")) {
			markTestStatus("failed", "FAILED: QR Code instruction doesnt match.", driver);
			failedCounter++;
		}
		if (!PwalletIDonly.equals("6912889404525941")) {
			System.out.println(PwalletID);
			markTestStatus("failed", "FAILED: My P-Wallet ID doesnt match.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Wallet > Fund Transfer > Recieve Via QR Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Wallet > Fund Transfer > Recieve via QR Module successful.", driver);
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
		// - Android: 100 ms
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
