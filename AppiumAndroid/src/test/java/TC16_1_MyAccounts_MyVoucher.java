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
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC16_1_MyAccounts_MyVoucher extends AndroidSetup {
	String SubTotalString = "";
	String DateString1 = "";
	String TimeString1 = "";
	String PaymentMethodString = "";
	String TempString = "";
	String TempString2 = "";
	String mobileDataCostString = "";
	int errorcounter = 0;

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
			errorcounter++;
		}
	}


	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	public void LoginFieldsTest_MyAccounts_MyVoucher(String email, String password) throws Exception {

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
			errorcounter++;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 3)
	public void navigateToMyAccount() throws Exception {
		// Clicking of login button after the input of the above credentials
		try {
			AndroidElement accountNavigation = (AndroidElement) driver.findElement(By.id("nav_account"));
			accountNavigation.click();

			// Checking if Puregold Image from homepage is visible to know if login is
			// successful

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			errorcounter++;

		}

		AndroidElement accountHeader = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
		String accountHeaderText = accountHeader.getText();

		if (accountHeaderText.equals("Account")) {
			System.out.println("Succesfully land on My Account Page");
			markTestStatus("passed", "Succesfully land on My Account Page", driver);
		} else {
			System.out.println("ERROR: Not Succesfully land on My Account Page");
			markTestStatus("failed", "Succesfully land on My Account Page", driver);
			errorcounter++;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 4)
	public void navigateToMyVoucherTest() {
		try {
			AndroidElement myVoucherList = (AndroidElement) driver.findElement(By.id("account_my_vouchers"));
			myVoucherList.click();

			System.out.println("Voucher button found");
			markTestStatus("passed", "Voucher button found", driver);
		} catch (Exception e) {
			System.out.println("ERROR: Voucher button not found");
			markTestStatus("failed", "Voucher button not found", driver);
			errorcounter++;
		}

		AndroidElement accountHeader = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
		String accountHeaderText = accountHeader.getText();

		if (accountHeaderText.equals("My Vouchers")) {
			System.out.println("Succesfully land on My Vouchers Page");
			markTestStatus("passed", "Succesfully land on My Vouchers Page", driver);
		} else {
			System.out.println("ERROR: Not Succesfully land on My Vouchers Page");
			markTestStatus("failed", "Succesfully land on My Vouchers Page", driver);
			errorcounter++;
		}

	}

	@Test(priority = 5)
	private void selectVoucherTest() {
		try {
			List<AndroidElement> voucherListAvailable = driver.findElements(By.id("item_voucher_terms"));
			voucherListAvailable.get(0).click();
			System.out.println("Succesfully click the first voucher");
			markTestStatus("passed", "Succesfully click the first voucher", driver);
		} catch (Exception e) {
			System.out.println("ERROR: Not Succesfully click the first voucher");
			markTestStatus("failed", "Not Succesfully click the first voucher", driver);
			errorcounter++;
		}

		try {
			AndroidElement voucherName = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("voucher_detail_name")));

			System.out.println("SUCCESS: Voucher name shown");
		} catch (Exception e) {
			System.out.println("ERROR: Voucher name not shown");
			markTestStatus("failed", "Voucher name not shown", driver);
			errorcounter++;
		}

		try {
			AndroidElement voucherCode = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("voucher_detail_code")));
			System.out.println("SUCCESS: Voucher Code shown");
		} catch (Exception e) {
			System.out.println("ERROR: Voucher Code not shown");
			markTestStatus("failed", "Voucher Code not shown", driver);
			errorcounter++;
		}

		try {
			AndroidElement voucherValidity = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("voucher_detail_validity")));
			System.out.println("SUCCESS: Voucher Validity shown");
		} catch (Exception e) {
			System.out.println("ERROR: Voucher Validity not shown");
			markTestStatus("failed", "Voucher Validity not shown", driver);
			errorcounter++;
		}
		driver.navigate().back();
		System.out.println("Land on Vouchers Page");
	}

	@Test(priority = 6)
	public void checker() {
		if (errorcounter <= 0) {
			markTestStatus("passed", "My Accounts > My Voucher Module  is Succesful", driver);
		} else {
			markTestStatus("failed", "My Accounts > My Voucher Module. See the logs for more details", driver);
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
	 * s Performs swipe inside an element
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
