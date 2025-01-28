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

public class TC13_BillsPay_ecBills_iOS extends SetupIOS {

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

	@Test(priority = 4)
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

	}

	@Test(priority = 5)
	public void Transaction_History() throws Exception {

		// Checking of Transaction History Button if visible
		try {
			IOSElement btn_transaction = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("buy load transactions")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_transaction.click();

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Transaction History button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Transaction History page
		try {
			IOSElement lbl_transaction = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Transaction History")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Transaction History Page.", driver);

			Thread.sleep(5000);
			// Navigate back
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Transaction History Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 6)
	public void Select_Biller() throws Exception {

		// Checking of Donation Button if visible
		try {
			IOSElement btn_donation = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Donation")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_donation.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Donation button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Donation page
		try {
			IOSElement lbl_donation = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Donation")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Donation Page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Donation Page.", driver);
			failedCounter++;
		}

		// Checking of Asian Center Button if visible
		try {

			IOSElement btn_option1 = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("ASIAN CENTER FOR MISSIONS")));

			btn_option1.click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No ASIAN CENTER FOR MISSIONS button found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 7, dataProvider = "ecBills", dataProviderClass = DataProvider_Login.class)
	public void Biller_Details(String acct, String name, String amount) throws Exception {

		// Check if redirected to Biller Details page
		try {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// verify Donation title
			IOSElement Donation_title = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Donation")));

			markTestStatus("passed", "Redirected to Donation Details page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Donation Details page.", driver);
			failedCounter++;
		}

		// get all elements with the element class XCUIElementTypeTextField
		List<IOSElement> List_TextField = driver.findElementsByClassName("XCUIElementTypeTextField");

		// verify and input in Account number field
		try {

			List_TextField.get(0).isDisplayed();
			List_TextField.get(0).click();
			List_TextField.get(0).clear();
			List_TextField.get(0).sendKeys(acct);

			Thread.sleep(1000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Account number field is not found", driver);
			failedCounter++;
		}

		// verify and input in Name field
		try {

			List_TextField.get(1).isDisplayed();
			List_TextField.get(1).click();
			List_TextField.get(1).clear();
			List_TextField.get(1).sendKeys(name);

			Thread.sleep(1000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Name field is not found", driver);
			failedCounter++;
		}

		// verify and input in Amount field
		try {

			List_TextField.get(2).isDisplayed();
			List_TextField.get(2).click();
			List_TextField.get(2).clear();
			List_TextField.get(2).sendKeys(amount);

			Thread.sleep(1000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Amount field is not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 8)
	public void ProceedToPayment() throws Exception {

		// Checking of Amount Label if visible
		try {
			IOSElement lbl_amount = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Amount")));
			lbl_amount.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Amount button not found.", driver);
			failedCounter++;
		}

		// Checking of Continue Button if visible
		try {
			IOSElement btn_continue = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Continue")));
			btn_continue.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Continue button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to Payment page
		try {
			IOSElement lbl_Payment = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: Redirected to Payment Page.", driver);

			Thread.sleep(2000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Page.", driver);
			failedCounter++;
		}

		// Checking of Billing info if visible
		try {
			IOSElement lbl_billingInfo = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Billing Information")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Billing Info not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Payment Method page
		try {

			IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));

			markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 9, dataProvider = "GCash_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_GCash(String Gcash) throws Exception {

		// Checking of Payment Method if visible
		try {
			IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Select Payment Method")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_paymentMethod.click();

			Thread.sleep(3000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment Method not found.", driver);
			failedCounter++;
		}

		// Select GCash method
		try {

			Thread.sleep(3000);
			// Verify if GCash method is visible
			IOSElement btn_GCash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(Gcash)));
			btn_GCash.click();

			markTestStatus("passed", "PASSED: GCash button is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: GCash button not found.", driver);
			failedCounter++;
		}

		// Verify if GCash method is displayed
		try {

			Thread.sleep(3000);
			// Verify if GCash method is visible
			IOSElement lbl_GCash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(Gcash)));

			markTestStatus("passed", "PASSED: Change payment method to GCash passed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Change payment method to GCash failed", driver);
			failedCounter++;
		}
	}

	@Test(priority = 10, dataProvider = "CreditDebit_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_CreditDebit(String CreditDebit) throws Exception {

		// Checking if Payment Method button is visible
		try {

			IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GCash Direct")));
			btn_paymentMethod.click();

			markTestStatus("passed", "PASSED: Payment Method button is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment Method button not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Payment Method page
		try {

			IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));

			markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
			failedCounter++;
		}

//		// Select Credit/Debit card method
//		try {
//
//			Thread.sleep(3000);
//			// Verify if Credit/Debit card method is visible
//			IOSElement btn_CreditDebit = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(CreditDebit)));
//			btn_CreditDebit.click();
//
//			markTestStatus("passed", "PASSED: Credit / Debit Card button is displayed.", driver);
//
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Credit / Debit Card button not found.", driver);
//			failedCounter++;
//		}
//
//		// Verify if Credit / Debit Card method is displayed
//		try {
//
//			Thread.sleep(3000);
//			// Verify if Credit / Debit Card method is visible
//			IOSElement lbl_CreditDebit = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(CreditDebit)));
//
//			markTestStatus("passed", "PASSED: Change payment method to Credit / Debit Card passed.", driver);
//
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Change payment method to Credit / Debit Card failed", driver);
//			failedCounter++;
//		}
	}

	@Test(priority = 11, dataProvider = "PWallet_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_PWallet(String PWallet) throws Exception {

		// Checking if Payment Method button is visible
//		try {
//
//			IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Credit / Debit Card")));
//			btn_paymentMethod.click();
//
//			markTestStatus("passed", "PASSED: Payment Method button is displayed.", driver);
//
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Payment Method button not found.", driver);
//			failedCounter++;
//		}

		// Checking if redirected to Payment Method page
//		try {
//
//			IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));
//			markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
//			failedCounter++;
//		}

		// Select P-Wallet method
		try {

			Thread.sleep(3000);
			// Verify if P-Wallet method is visible
			IOSElement btn_Pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(PWallet)));
			btn_Pwallet.click();

			markTestStatus("passed", "PASSED: P-Wallet button is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: P-Wallet button not found.", driver);
			failedCounter++;

			// Check if P-Wallet has balance
			try {

				Thread.sleep(3000);
				// Verify if popup modal is visible
				IOSElement modal_popup = driver.findElementByName("Top up and Pay with Puregold Wallet");

				if (modal_popup.isDisplayed() == true) {
					markTestStatus("passed", "P-Wallet has no balance.", driver);

					// Verify modal buttons if visible
					try {
						// Verify if top up button is visible
						IOSElement btn_topUp = (IOSElement) new WebDriverWait(driver, 30)
								.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Top Up Now")));

						markTestStatus("passed", "Top Up button is displayed.", driver);

						driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					} catch (Exception ex) {
						markTestStatus("failed", "FAILED: Top Up button not found.", driver);
						failedCounter++;
					}

					try {
						// Verify if Change payment method button is visible
						IOSElement btn_changePayment = (IOSElement) new WebDriverWait(driver, 30).until(
								ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Change Payment Method")));

						markTestStatus("passed", "Change Payment Method button is displayed.", driver);

						// click change payment method to close modal
						btn_changePayment.click();
						Thread.sleep(3000);

						// Checking if redirected back to Payment Method page
						try {

							IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(
									ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));

							markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

							driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

						} catch (Exception ex) {
							markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
							failedCounter++;
						}

						// Navigate back to checkout page
						driver.navigate().back();
						driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					} catch (Exception exception) {
						markTestStatus("failed", "FAILED: Change Payment Method button not found.", driver);
						failedCounter++;
					}

				}

				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			} catch (Exception exc) {

				// Check if redirected to checkout page if P-Wallet has balance
				try {

					// P-Wallet has balance
					markTestStatus("passed", "P-Wallet has balance.", driver);

					Thread.sleep(3000);
					// Verify if redirected to checkout page
					IOSElement lbl_checkout = (IOSElement) new WebDriverWait(driver, 30)
							.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));

					markTestStatus("passed", "PASSED: Redirected to Checkout page.", driver);

					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

				} catch (Exception x) {
					markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
					failedCounter++;
				}
			}
		}
	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Bills Pay > ecBills Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Bills Pay > ecBills Module successful.", driver);
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
