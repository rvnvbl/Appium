import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
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
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC13_0_BillsPay_Android_EcBills extends AndroidSetup {
	String SubTotalString = "";
	String DateString1 = "";
	String TimeString1 = "";
	String PaymentMethodString = "";
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
	public void LoginFieldsTest_BillsPay_EcBills(String email, String password) throws Exception {

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
	public void TapBillsPayTest() throws Exception {
		// Scrolling toand clicking the Donation Drive button
		try {
			MobileElement BillsOptionButton = (MobileElement) driver
					.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
							+ ".scrollIntoView(new UiSelector().resourceIdMatches(\".*home_card_pay_bills.*\"))"));

			BillsOptionButton.click();
		} catch (Exception e) {
			try {
				AndroidElement BillsOptionButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_card_pay_bills")));
				BillsOptionButton.click();
			} catch (Exception e1) {
				markTestStatus("failed", "FAILED: Bills Pay Button is not found.", driver);
				errorcounter++;
			}
		}

		// Checking if "Bills Pay" title is visible
		try {
			// Checking if "Bills Pay" title is visible
			AndroidElement BillsPayTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String BillsPayText = BillsPayTitle.getText();

			if (BillsPayText.equals("Bills Pay")) {
				System.out.println("Test passed: Redirect to Bills Pay successful");
			} else {
				markTestStatus("failed", "FAILED: Bills Pay title is not found.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Bills Pay title is not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 4)
	public void EcBillsButtonTest() throws Exception {
		// Clicking ecBills option
		try {
			MobileElement EcBillsOptionButton = (MobileElement) driver
					.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"ecBills\"));");
			EcBillsOptionButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: EcBills Button is not found.", driver);
			errorcounter++;
		}

		// Checking if "ecBills" title is visible
		try {
			// Checking if "ecBills" title is visible
			AndroidElement ecBillsTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String ecBillsText = ecBillsTitle.getText();

			if (ecBillsText.equals("ecBills")) {
				System.out.println("Test passed: Redirect to ecBills successful");
			} else {
				markTestStatus("failed", "FAILED: ecBills title is wrong.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: ecBills title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 5)
	public void TransactionsTest() {
		// Clicking the Transaction History button of the product
		try {
			AndroidElement TransactionHistoryButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("menu_view_transactions")));
			TransactionHistoryButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: EcBills Button is not found.", driver);
			errorcounter++;
		}

		// Checking if "Transaction History" title is visible
		try {
			// Checking if "Transaction History" title is visible
			AndroidElement TransactionHistoryTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String TransactionHistoryText = TransactionHistoryTitle.getText();

			if (TransactionHistoryText.equals("Transaction History")) {
				System.out.println("Test passed: Redirect to Transaction History successful");

				driver.pressKey(new KeyEvent(AndroidKey.BACK));

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

				// Checking if "ecBills" title is visible
				try {
					// Checking if "ecBills" title is visible
					AndroidElement ecBillsTitle = (AndroidElement) new WebDriverWait(driver, 30)
							.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

					String ecBillsText = ecBillsTitle.getText();

					if (ecBillsText.equals("ecBills")) {
						System.out.println("Test passed: Redirect to ecBills successful");
					} else {
						markTestStatus("failed", "FAILED: ecBills title is wrong.", driver);
						errorcounter++;
					}

					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

				} catch (Exception e) {
					markTestStatus("failed", "FAILED: ecBills title is not found.", driver);
					errorcounter++;
				}
			} else {
				markTestStatus("failed", "FAILED: Transaction History title is wrong.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Transaction History Button is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 6)
	public void DonationBillerTest() throws Exception {

		// Clicking Donation option
		try {
			MobileElement DonationOptionButton = (MobileElement) driver
					.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"Donation\"));");
			DonationOptionButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Donation Button is not found.", driver);
			errorcounter++;
		}

		// Checking if "Donation" title is visible
		try {
			// Checking if "Donation" title is visible
			AndroidElement DonationTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String DonationText = DonationTitle.getText();

			if (DonationText.equals("Donation")) {
				System.out.println("Test passed: Redirect to Donation successful");
			} else {
				markTestStatus("failed", "FAILED: Donation title is wrong.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Donation title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 7)
	public void SpecificDonationTest() throws Exception {

		// Clicking "ASIAN CENTER FOR MISSIONS" option
		try {
			MobileElement SpecDonationButton = (MobileElement) driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
							+ "(new UiSelector().text(\"ASIAN CENTER FOR MISSIONS\"));");
			SpecDonationButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Donation Button is not found.", driver);
			errorcounter++;
		}

		// Checking if "Donation" title is visible
		try {
			// Checking if "Donation" title is visible
			AndroidElement DonationTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("billing_title")));

			String DonationText = DonationTitle.getText();

			if (DonationText.equals("ASIAN CENTER FOR MISSIONS")) {
				System.out.println("Test passed: Redirect to Specific Donation successful");
			} else {
				markTestStatus("failed", "FAILED: Specific Donation title is wrong.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Donation title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 8)
	public void MobileNoTest() throws Exception {
		// Clicking the Mobile Number Field
		try {
			List<AndroidElement> BillerFields = driver.findElements(By.id("billing_input_field"));
			AndroidElement MobileNoField = BillerFields.get(0);

			MobileNoField.click();

			MobileNoField.sendKeys("09111111111");

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Mobile Number Field is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 9)
	public void AccountNameTest() throws Exception {
		// Clicking the Name Field
		try {
			List<AndroidElement> BillerFields = driver.findElements(By.id("billing_input_field"));
			AndroidElement NameField = BillerFields.get(1);

			NameField.click();

			NameField.sendKeys("Test Pay");

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Name Field is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 10)
	public void AmountTest() throws Exception {
		// Clicking the Amount Field
		try {
			AndroidElement AmountField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("billing_amount_field")));

			AmountField.click();

			AmountField.sendKeys("1");

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Amount Field is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 11)
	public void ContinueButtonTest() throws Exception {
		// Checking and Clicking the Continue Button
		try {
			AndroidElement ContinueButtonTest = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("pay_bills_checkout_bottom_pay_now")));

			// Checking if Continue button is now enabled
			if (ContinueButtonTest.isEnabled()) {
				ContinueButtonTest.click();
			} else {
				markTestStatus("failed", "FAILED: Continue Button is not enabled.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Continue Button is not found.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully redirected to Payment page
			AndroidElement PaymentTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((PaymentTitle.getText()).equals("Payment")) {
				System.out.println("Successfully redirected to Payment page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment page.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 12)
	public void PaymentChevronTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("pay_bills_checkout_payment_arrow")));

			PaymentMethodChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Payment Method")) {
				System.out.println("Successfully redirected to Payment Method page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 13)
	public void GCashMethodTest() throws Exception {
		try {
			// Clicking the GCash payment option
			AndroidElement GCashOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"GCash Direct\"));")));

			GCashOption.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: GCash Payment method button error / not found.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully redirected to Payment page
			AndroidElement PaymentTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((PaymentTitle.getText()).equals("Payment")) {
				System.out.println("Successfully redirected to Payment page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment page.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("pay_bills_checkout_payment_info")));

			if ((PaymentMethodElement.getText()).equals("GCash Direct")) {
				System.out.println("Successfully changed payment method to Gcash");
			} else {
				markTestStatus("failed", "FAILED: changed payment method to Gcash failed", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 14, enabled = false)
	public void CreditDebitMethodTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("pay_bills_checkout_payment_arrow")));

			PaymentMethodChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Payment Method")) {
				System.out.println("Successfully redirected to Payment Method page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
			errorcounter++;
		}

		try {
			// Clicking the Credit / Debit card payment option
			AndroidElement CreditDebitOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Credit / Debit Card\"));")));

			CreditDebitOption.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully redirected to Payment page
			AndroidElement PaymentTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((PaymentTitle.getText()).equals("Payment")) {
				System.out.println("Successfully redirected to Payment page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment page.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("pay_bills_checkout_payment_info")));

			if ((PaymentMethodElement.getText()).equals("Credit / Debit Card")) {
				System.out.println("Successfully changed payment method to Credit / Debit Card");
			} else {
				markTestStatus("failed", "FAILED: changed payment method to Credit / Debit Card failed", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 15, enabled = true)
	public void errorCheckr() {

		if (errorcounter > 0) {
			markTestStatus("failed",
					"an error an occurred on Bills Pay > ecBills user test, Please see logs for details", driver);
		} else {
			markTestStatus("passed", "Bills Pay > ecBills Module Successful. (EcBills)", driver);
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
