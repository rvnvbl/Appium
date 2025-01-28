import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import io.appium.java_client.pagefactory.iOSBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC26_2_Checkout_PwalletDiscount_ValueTest_iOS extends SetupIOS {

	int failedCounter = 0;
	Double dblsubtotal = 0.00;
	Double dblWalletPoints = 0.00;
	Double dblTextFieldValues = 0.00;

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
	public void LoginFieldsTest_Payinstore(String email, String password) throws Exception {

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
	public void Tap_cart_Btn() throws Exception {

		// Checking of Cart Button if visible
		try {

			// verify if Cart Button is visible
			IOSElement btn_cart = (IOSElement) new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("view_cart")));
			btn_cart.click();

			markTestStatus("passed", "PASSED: Cart button is displayed.", driver);

		} catch (Exception e) {
			// Code for Adding item to cart if cart is empty
			// Navigate to Store Homepage
			try {
				// verify if in store pickup Button is visible and enabled
				IOSElement btn_instore_pickup = (IOSElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_in_store_pickup")));
				btn_instore_pickup.click();

			} catch (Exception ex) {
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

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Store search bar could not be found.", driver);
				failedCounter++;
			}

			// Checking of Puregold branch on store list
			try {
				Thread.sleep(2000);
				IOSElement puregold_branch = (IOSElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.elementToBeClickable(MobileBy.name("PUREGOLD MAKATI")));
				puregold_branch.click();

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Puregold Makati branch is not found / error.", driver);
				failedCounter++;
			}

			// Checking if redirected to store Homepage
			try {
				IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Search for products and brands")));

				markTestStatus("passed", "PASSED: Successfully redirected to store homepage", driver);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Did not redirected to Store Homepage / error.", driver);
				failedCounter++;
			}

			// Click search bar and search Item

			// Verify first search bar
			try {
				IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Search for products and brands")));

				searchbar.click();

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Search bar not found.", driver);
				failedCounter++;
			}

			// Verify second search bar
			try {
				IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
						.visibilityOfElementLocated(MobileBy.name("Search for product name or category")));

				searchbar.click();
				searchbar.sendKeys("Globe Callcard P500");
				searchbar.sendKeys(Keys.ENTER);
				Thread.sleep(3000);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Search bar not found.", driver);
				failedCounter++;
			}

			// Checking of Add to Cart Button if visible
			try {

				Thread.sleep(3000);
				// get all elements with the element class XCUIElementTypeStaticText
				List<IOSElement> btn_addCart = driver.findElementsByName("ADD TO CART");

				btn_addCart.get(0).click();
				markTestStatus("passed", "PASSED: Add to Cart Button found", driver);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
				failedCounter++;
			}

			// Checking of Cart Button if visible
			try {

				// Navigate back
				driver.navigate().back();
				Thread.sleep(2000);

				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

				// verify if Cart Button is visible
				IOSElement btn_cart = (IOSElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
				btn_cart.click();

				markTestStatus("passed", "PASSED: Cart button is displayed.", driver);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Cart button not found.", driver);
				failedCounter++;
			}
		}

		// Check if pop up warning appeared to cart page
		try {
			Thread.sleep(2000);

			// verify if okay button is visible
			IOSElement btn_okay = (IOSElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Okay")));
			btn_okay.click();

			markTestStatus("passed", "Popup warning displayed.", driver);
			Thread.sleep(2000);

		} catch (Exception e) {
			markTestStatus("passsed", "No Popup warning displayed.", driver);
		}

		// Checking of Cart Plus Button if visible
		try {

			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'cart plus'")));
			btn_cartPlus.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 4)
	public void ProceedToCheckout() throws Exception {
		// Proceed to Checkout Page
		try {
			// Verify if Checkout Button if visible
			IOSElement btn_checkout = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));
			Thread.sleep(2000);
			btn_checkout.click();

			markTestStatus("passed", "PASSED: Checkout button is visible.", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout button not found/disabled", driver);
			failedCounter++;
		}

		// Check if redirected to checkout page
		try {

			// Verify if redirected to checkout page
			IOSElement lbl_checkout = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));

			markTestStatus("passed", "PASSED: Redirected to Checkout page.", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	public void Verify_Branch() throws Exception {

		// Checking if Branch is visible
		try {

			IOSElement lbl_Branch = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Branch")));

			markTestStatus("passed", "PASSED: Branch is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Branch is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 6)
	public void Verify_Contact() throws Exception {

		// Checking if Contact is visible
		try {

			IOSElement lbl_contact = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Contact Info")));

			markTestStatus("passed", "PASSED: Contact Information is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Contact Information is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 7)
	public void Verify_Pickup_Schedule() throws Exception {

		// Checking if Pickup schedule button is visible
		try {

			IOSElement btn_pickup_schedule = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Select Pickup Time")));
			btn_pickup_schedule.click();

			markTestStatus("passed", "PASSED: Pickup Schedule button is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pickup Schedule button not found.", driver);
			failedCounter++;
		}

		// Checking of Desired Date
		try {
			Thread.sleep(2000);
			IOSElement btn_date = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("date_1")));
			btn_date.click();

			markTestStatus("passed", "PASSED: Select Date successful.", driver);
			Thread.sleep(2000);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Select Date error.", driver);
			failedCounter++;
		}

		// Select first available time
		try {

			// get all elements with the element class XCUIElementTypeStaticText
			List<IOSElement> list_availableTime = driver.findElementsByName("AVAILABLE");

			// Select first available time
			list_availableTime.get(0).click();

			markTestStatus("passed", "PASSED: Select Time successful.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Select Time error.", driver);
			failedCounter++;
		}

		// Check if redirected to checkout page
		try {

			// Verify if redirected to checkout page
			IOSElement lbl_checkout = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));

			markTestStatus("passed", "PASSED: Redirected to Checkout page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 8, dataProvider = "GCash_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_GCash(String Gcash) throws Exception {

		// Checking if Payment Method button is visible
		try {

			IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("value_payment_method")));
			btn_paymentMethod.click();

			markTestStatus("passed", "PASSED: Payment Method button is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment Method button not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Payment Method page
		try {

			IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));

			markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
			failedCounter++;
		}

		// Select GCash method
		try {

			// Verify if GCash method is visible
			IOSElement btn_GCash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(Gcash)));
			btn_GCash.click();

			markTestStatus("passed", "PASSED: Gcash button is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Gcash button not found.", driver);
			failedCounter++;
		}

		// Verify if GCash method is displayed
		try {

			// Verify if GCash method is visible
			IOSElement lbl_GCash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(Gcash)));

			markTestStatus("passed", "PASSED: Change payment method to Gcash passed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Change payment method to Gcash failed", driver);
			failedCounter++;
		}
	}

	@Test(priority = 9, enabled = true)
	private void PwalletDiscountButtonGcash() {
		// Verify if Special Instruction field is displayed

		IOSElement Instruction_txtField = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(
						MobileBy.iOSNsPredicateString("value == \"E.g. Put frozen foods in a separate bag\"")));
		Instruction_txtField.click();
		markTestStatus("passed", "PASSED: Special Instruction field passed.", driver);

		IOSElement doneButton = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Done")));
		doneButton.click();

		try {
			IOSElement dicountLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountLbl")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount label not found", driver);
			failedCounter++;
		}

		try {
			IOSElement discountToogle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountToggle")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount toogle not found", driver);
			failedCounter++;
		}

		clickPwalletDiscountToogle();

	}

	private void clickPwalletDiscountToogle() {

		// Click toogle button
		try {
			IOSElement discountToogle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountToggle")));
			discountToogle.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount toogle not found", driver);
			failedCounter++;
		}

		// Check title label
		try {
			IOSElement titleLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("titleLbl")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Check title not found", driver);
			failedCounter++;
		}

		// Check redeem label
		try {
			IOSElement redeemLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemLbl")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem label not found", driver);
			failedCounter++;
		}

		// Check input textfield
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input textfield not found", driver);
			failedCounter++;
		}

		// Check remaining points
		try {
			IOSElement remainingPoints = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("pointsLbl")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remaining points not found", driver);
			failedCounter++;
		}

		// Check remaining points label
		try {
			IOSElement remainingPointsLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("pointsTitleLbl")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remaining points label not found", driver);
			failedCounter++;
		}

		// Check redeem button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemBtn")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeen button not found", driver);
			failedCounter++;
		}

		// Check for info button
		try {
			IOSElement infoButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btnInfo")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Info button not found", driver);
			failedCounter++;
		}

		// Click close button
		try {
			IOSElement closeButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("closeBtn")));
			closeButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Close button not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 11, dataProvider = "inStore_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_payInStore(String InStore) throws Exception {

		// Checking if Payment Method button is visible
		try {

			IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("value_payment_method")));
			btn_paymentMethod.click();

			markTestStatus("passed", "PASSED: Payment Method button is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment Method button not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Payment Method page
		try {

			IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));

			markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
			failedCounter++;
		}

		// Select Pay In Cash method
		try {

			// Verify if pay In Store method is visible
			IOSElement btn_InStore = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(InStore)));
			btn_InStore.click();

			markTestStatus("passed", "PASSED: Pay In Cash button is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pay In Cash button not found.", driver);
			failedCounter++;
		}

		// Verify if Pay In Cash method is displayed
		try {

			// Verify if Pay In Store method is visible
			IOSElement lbl_InStore = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(InStore)));

			markTestStatus("passed", "PASSED: Change payment method to Pay In Cash passed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Change payment method to Pay In Cash failed", driver);
			failedCounter++;
		}
	}

	@Test(priority = 12, enabled = true)
	private void PwalletDiscountButtonInstore() {
		// Verify if Special Instruction field is displayed

		IOSElement Instruction_txtField = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(
						MobileBy.iOSNsPredicateString("value == \"E.g. Put frozen foods in a separate bag\"")));
		Instruction_txtField.click();
		markTestStatus("passed", "PASSED: Special Instruction field passed.", driver);

		IOSElement doneButton = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Done")));
		doneButton.click();

		try {
			IOSElement dicountLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountLbl")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount label not found", driver);
			failedCounter++;
		}

		try {
			IOSElement discountToogle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountToggle")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount toogle not found", driver);
			failedCounter++;
		}

		clickPwalletDiscountToogle();
	}

	@Test(priority = 13, enabled = true)
	private void modalTexfieldTest() {

		// Getting the total subtotal
		try {
			IOSElement itemSubtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));
			String subtotal = itemSubtotal.getText();
			dblsubtotal = Double.parseDouble(extractValue(subtotal));
			System.out.println("The subtotal is: " + dblsubtotal);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Item subtotal not found", driver);
			failedCounter++;
		}

		// Click toogle button
		try {
			IOSElement discountToogle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountToggle")));
			discountToogle.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount toogle not found", driver);
			failedCounter++;
		}

		// Check and get the remaining Pwallet balance
		try {
			IOSElement pwalletBalance = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("pointsLbl")));
			String stringBalance = pwalletBalance.getText();
			String finalBalance = stringBalance.replace(",", "");
			dblWalletPoints = Double.parseDouble(extractValue(finalBalance));
			System.out.println("Pwallet Balance is: " + dblWalletPoints);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pwallet balance not found", driver);
			failedCounter++;
		}
		// Checking the done button
		try {
			IOSElement doneButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Done")));
			doneButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Done button is not found", driver);
			failedCounter++;
		}
		// Checking the value of the textfield - Auto populate
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

			String textFieldValue = inputTextfield.getText();
			dblTextFieldValues = Double.parseDouble(textFieldValue);
			System.out.println("Input textfield value: " + dblTextFieldValues);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input field not found", driver);
			failedCounter++;
		}
//		// Checking if subtotal is smaller
//		if (dblsubtotal < dblWalletPoints) {
//			System.out.println("Subtotal is less than wallet balance");
//			Assert.assertTrue(dblTextFieldValues.equals(dblsubtotal));
//		}
//
//		// Click close button
//		try {
//			IOSElement closeButton = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("closeBtn")));
//			closeButton.click();
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Close button not found", driver);
//			failedCounter++;
//		}

	}

	@Test(priority = 14, enabled = true)
	private void pwalletAsPrimaryMOPTest() {
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

			inputTextfield.clear();
			inputTextfield.sendKeys(dblsubtotal.toString());
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input field not found", driver);
			failedCounter++;
		}

		// Checking the done button
		try {
			IOSElement doneButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Done")));
			doneButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Done button is not found", driver);
			failedCounter++;
		}

		// Checking the redeem button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemBtn")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem button is not found", driver);
			failedCounter++;
		}

		// Checking the modal title
		try {
			IOSElement modalTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("titleLbl")));
			try {
				Assert.assertTrue(modalTitle.getText().contains("Switch to P-Wallet Payment Method"));
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Modal Title is not correct", driver);
				failedCounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal Title is not found", driver);
			failedCounter++;
		}

		// Checking the message label
		try {
			IOSElement messageLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("msgLbl")));
			try {
				Assert.assertTrue(messageLabel.getText().contains(
						"Your entered amount is sufficient to cover the total cart amount, we will switch the payment method to P-Wallet."));
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Message label is not correct", driver);
				failedCounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Message label is not found", driver);
			failedCounter++;
		}

		// Checking the proceed button
		try {
			IOSElement proceedButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("okayBtn")));
			try {
				Assert.assertTrue(proceedButton.getText().contains("Proceed"));
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Proceed button is not correct", driver);
				failedCounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Proceed button is not found", driver);
			failedCounter++;
		}

		// Checking the change discount button
		try {
			IOSElement changeDiscountButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("backBtn")));
			try {
				Assert.assertTrue(changeDiscountButton.getText().contains("Change discount amount"));
				changeDiscountButton.click();
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Change discount button is not correct", driver);
				failedCounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Change discount button is not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 15, enabled = true)
	private void discountLessThanSubtotal() {
		// Set the discount in textfield
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

			inputTextfield.clear();
			Double lessThanSubtotal = dblsubtotal - 5;
			inputTextfield.sendKeys(lessThanSubtotal.toString());

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input field not found", driver);
			failedCounter++;
		}

		// Checking the redeem button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemBtn")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem button is not found", driver);
			failedCounter++;
		}
	}

	@Test(priority = 15, enabled = true)
	private void subtotalGreaterThanDiscount() {

		// Click toogle off button
		try {
			IOSElement discountToogle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountToggle")));
			discountToogle.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount toogle not found", driver);
			failedCounter++;
		}

		// Click toogle on button
		try {
			IOSElement discountToogle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("discountToggle")));
			discountToogle.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Discount toogle not found", driver);
			failedCounter++;
		}

		// Set the discount in textfield
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

			inputTextfield.clear();
			Double lessThanSubtotal = dblsubtotal + 10;
			inputTextfield.sendKeys(lessThanSubtotal.toString());

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input field not found", driver);
			failedCounter++;
		}

		// Checking the redeem button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemBtn")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem button is not found", driver);
			failedCounter++;
		}

		// Checking the error message
		try {
			IOSElement errorMessage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("errorLbl")));

			try {
				Assert.assertTrue(errorMessage.getText()
						.contains("The amount must be less than or equal to the Total Cart Amount"));

			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Error message is not correct", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Error messagen is not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 16, enabled = true)
	private void PwalletBalanceIsNotEnough() {
		// Set the discount in textfield
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

			inputTextfield.clear();
			Double greaterWalletPoints = dblWalletPoints + 10;
			inputTextfield.sendKeys(greaterWalletPoints.toString());

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input field not found", driver);
			failedCounter++;
		}

		// Checking the redeem button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemBtn")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem button is not found", driver);
			failedCounter++;
		}

		// Checking the error message
		try {
			IOSElement errorMessage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("errorLbl")));

			try {
				Assert.assertTrue(errorMessage.getText().contains("Insufficient Balance"));

			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Error message is not correct", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Error messagen is not found", driver);
			failedCounter++;
		}

	}

	@Test(priority = 17, enabled = true)
	private void placeOrderTest() {
		// Set the discount in textfield
		try {
			IOSElement inputTextfield = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("inputTf")));

			inputTextfield.clear();
			inputTextfield.sendKeys("1");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input field not found", driver);
			failedCounter++;
		}

		// Checking the redeem button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("redeemBtn")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem button is not found", driver);
			failedCounter++;
		}

		// Checking the place order button
		try {
			IOSElement redeemButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_place_order")));
			redeemButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Redeem button is not found", driver);
			failedCounter++;
		}

		// Checking the page title
		try {
			IOSElement pwalletTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet PIN")));

			try {
				Assert.assertTrue(pwalletTitle.getText().contains("P-Wallet PIN"));

			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Pwallet title page is not correct", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pwallet title page is not found", driver);
			failedCounter++;
		}

		// Checking the pwallet label
		try {
			IOSElement pwalletLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Enter your P-Wallet PIN")));

			try {
				Assert.assertTrue(pwalletLabel.getText().contains("Enter your P-Wallet PIN"));

			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Pwallet label is not correct", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pwallet label is not found", driver);
			failedCounter++;
		}

		// Checking the forget pin label
		try {
			IOSElement forgetPinLabel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Forgot PIN?")));

			try {
				Assert.assertTrue(forgetPinLabel.getText().contains("Forgot PIN?"));

			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Pwallet label is not correct", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pwallet label is not found", driver);
			failedCounter++;
		}
		
		// Get all the textfield in the pwallet pin page
		List<IOSElement> textfieldList = driver.findElementsByClassName("XCUIElementTypeTextField");
		System.out.println("The number of textfield is: " + textfieldList.size());
		
		
		//Back to checkout page
		// Checking the redeem button
		try {
			IOSElement backButtton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));
			backButtton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Back button is not found", driver);
			failedCounter++;
		}
	}

	@Test(priority = 18)
	public void Verify_Subtotal() throws Exception {

//		// Click close button
//		try {
//			IOSElement closeButton = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("closeBtn")));
//			closeButton.click();
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Close button not found", driver);
//			failedCounter++;
//		}

		// Verify if Subtotal field is displayed
		try {

			IOSElement lbl_subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			markTestStatus("passed", "PASSED: Subtotal field is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal field not found", driver);
			failedCounter++;
		}
	}

	@Test(priority = 19)
	public void Remove_fromCart() throws Exception {

		// Check if pop up warning appeared to cart page
		try {

			// navigate back to cart page
			driver.navigate().back();

			// verify if okay button is visible
			IOSElement btn_okay = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Okay")));
			btn_okay.click();

			markTestStatus("passed", "Popup warning displayed.", driver);

		} catch (Exception e) {
			markTestStatus("passsed", "No Popup warning displayed.", driver);
		}

		// Swipe Left Product

		try {
			// Get element to be deleted
			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 20)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cell_row")));

			// swipe left in product text
			swipeElementIOS(added_item, Direction.LEFT);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot perform swipe", driver);
			failedCounter++;
		}

		// Check if product has been removed
		try {

			// Verify if successfully removed item
			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cell_row")));

			markTestStatus("failed", "FAILED: Deleted product can still be found", driver);
			failedCounter++;

		} catch (Exception e) {
			markTestStatus("passed", "PASSED: Delete product successful.", driver);
		}
	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Checkout Module (Pwallet: Value Test) has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Checkout Module (Pwallet: Value Test) successful.", driver);
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

	public static String extractValue(String input) {
		String pattern = "₱([\\d,.]+)"; // Regular expression to capture the value after '₱'
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(input);

		if (m.find()) {
			return m.group(1); // Return the matched group
		} else {
			return ""; // Return empty string if no match found
		}
	}
}
