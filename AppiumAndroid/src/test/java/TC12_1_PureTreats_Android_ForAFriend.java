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
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC12_1_PureTreats_Android_ForAFriend extends AndroidSetup {
	String SubTotalString = "";
	String PriceString = "";
	String ProductString = "";
	String CategoryString = "";
	String PaymentMethodString = "";
	int errorcounter = 0;

	@Test(priority = 0)
	public void LoginPageTest() throws Exception {
		try {
			// Clicking of Login with email button on startup screen
			AndroidElement EmailLoginButton = (AndroidElement) new WebDriverWait(driver, 45)
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
	public void LoginFieldsTest_PureTreats_TreatForFriend(String email, String password) throws Exception {

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
	public void TapPureTreatsTest() throws Exception {
		// Scrolling to and clicking the Pure Treats button
		try {
			AndroidElement PureTreatsButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_share_treats")));
			PureTreatsButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// Checking if "Pure Treats" title is visible
			AndroidElement PureTreatsTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String PureTreatsText = PureTreatsTitle.getText();

			if (PureTreatsText.equals("Pure Treats")) {
				System.out.println("Test passed: Redirect to Pure Treats successful");
			} else {
				markTestStatus("failed", "FAILED: Pure Treats title is not found.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pure Treats title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 4)
	public void ViewAllTest() throws Exception {
		// Getting all categories on Pure treats home and finding the first one
		try {
			List<AndroidElement> PureTreatsHomeCategories = driver.findElements(By.id("share_treats_layout"));
			MobileElement FirstPureTreatsHomeCategory = PureTreatsHomeCategories.get(0);

			// Category Header
			MobileElement CategoryHeader = FirstPureTreatsHomeCategory.findElement(By.id("share_treats_header"));

			// Getting the name of the specific category for validation onwards
			MobileElement PureTreatsCategoryName = CategoryHeader.findElement(By.id("share_treats_name"));
			CategoryString = PureTreatsCategoryName.getText();

			// Clicking the view all button of the specific category
			MobileElement ViewAllButton = CategoryHeader.findElement(By.id("share_treats_view_all"));
			ViewAllButton.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: View All button/s error / not found.", driver);
			errorcounter++;
		}

		// Checking if specific category title is visible
		try {
			// Checking if title of page redirected to is equal to the name obtained earlier
			AndroidElement PureTreatsCategoryTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String PureTreatsCategoryText = PureTreatsCategoryTitle.getText();

			if (PureTreatsCategoryText.equals(CategoryString)) {
				System.out.println("Test passed: Redirect to specific Pure Treats Category successful");
			} else {
				markTestStatus("failed", "FAILED: Specific Pure Treats category title is not found.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Pure Treats category title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 5)
	public void PureTreatsBuyItemTest() throws Exception {
		// Getting the details of the first product
		try {
			List<AndroidElement> PureTreatsProducts = driver.findElements(By.id("share_treats_product_layout"));
			AndroidElement FirstPureTreatsProduct = PureTreatsProducts.get(0);

			MobileElement ProductPrice = FirstPureTreatsProduct.findElement(By.id("share_treats_product_price"));
			PriceString = ProductPrice.getText();

			MobileElement ProductName = FirstPureTreatsProduct.findElement(By.id("share_treats_product_title"));
			ProductString = ProductName.getText();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Product Details (Price, Name) error / not found.", driver);
			errorcounter++;
		}

		// Clicking the Buy Now button of the first product
		try {
			List<AndroidElement> PureTreatsProducts = driver.findElements(By.id("share_treats_product_layout"));
			AndroidElement FirstPureTreatsProduct = PureTreatsProducts.get(0);

			MobileElement BuyNowButton = FirstPureTreatsProduct.findElement(By.id("share_treats_product_treat"));
			BuyNowButton.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Buy Now button/s error / not found.", driver);
			errorcounter++;
		}

		// Checking if For Me option on "Select Recipient" modal appeared
		try {
			AndroidElement ForMeOption = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_for_me")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Select Recipient modal error / not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 6)
	public void ForAFriendOptionTest() throws Exception {
		// Clicking of For A Friend option on "Select Recipient" modal that appeared
		try {
			AndroidElement ForAFriendOption = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_for_a_friend")));

			ForAFriendOption.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Select Recipient modal error / not found.", driver);
			errorcounter++;
		}

		// Checking if redirect to Recipient page is successful
		try {
			AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String CartTitleText = CartTitle.getText();

			if (CartTitleText.equals("Recipient Details")) {
				System.out.println("Successfully redirected to Recipient Details page");
			} else {
				markTestStatus("failed", "FAILED: Recipient Details page title error / not found", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Recipient Details page title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 7)
	public void FillUpFields() throws Exception {
		// Clicking and Fill up of First Name Field
		try {
			AndroidElement FirstNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_recipient_first_name")));

			FirstNameField.click();

			FirstNameField.sendKeys("FirstNameTest");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: First name Field error / not found.", driver);
			errorcounter++;
		}

		// Clicking and Fill up of Last Name Field
		try {
			AndroidElement LastNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_recipient_last_name")));

			LastNameField.click();

			LastNameField.sendKeys("LastNameTest");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Last name Field error / not found.", driver);
			errorcounter++;
		}

		// Clicking and Fill up of Number Field
		try {
			AndroidElement NumberField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_recipient_mobile")));

			NumberField.click();

			NumberField.sendKeys("9123456789");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Number Field error / not found.", driver);
			errorcounter++;
		}

		// Clicking and Fill up of Email Field
		try {
			AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_recipient_email")));

			EmailField.click();

			EmailField.sendKeys("TestEmail@gmail.com");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Field error / not found.", driver);
			errorcounter++;
		}

		// Clicking and Fill up of Messages Field
		try {
			AndroidElement MessageField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_recipient_message")));

			MessageField.click();

			MessageField.sendKeys("Test Message");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			boolean isKeyboardShown = driver.isKeyboardShown();

			if (isKeyboardShown) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			} else {
				System.out.println("Keyboard is not shown, no need to back");
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Message Field error / not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 8)
	public void ForAFriendCheckoutTest() throws Exception {
		// Clicking of Continue Button
		try {
			AndroidElement ContinueButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_continue")));

			ContinueButton.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Continue Button error / not found.", driver);
			errorcounter++;
		}

		// Checking if redirect to Checkout page is successful
		try {
			AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String CartTitleText = CartTitle.getText();

			if (CartTitleText.equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Checkout page title error / not found", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout page title is not found.", driver);
			errorcounter++;
		}

		// Checking if Total Price is correct
		try {
			AndroidElement TotalPrice = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_checkout_bottom_amount")));

			String TotalPriceText = TotalPrice.getText();

			if (TotalPriceText.equals(PriceString)) {
				System.out.println("Total price is correct");
			} else {
				markTestStatus("failed", "FAILED: Total Price Text is not correct", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Total Price Text is not found.", driver);
			errorcounter++;
		}

		// Checking if Name of Pure Treats product is correct
		try {
			AndroidElement CheckoutProductName = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_checkout_goods_name")));

			String CheckoutProductNameText = CheckoutProductName.getText();

			if (CheckoutProductNameText.equals(ProductString)) {
				System.out.println("Product Name is correct");
			} else {
				markTestStatus("failed", "FAILED: Product Name is not correct", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Product Name is not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 9)
	public void PaymentChevronTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_checkout_payment_arrow")));

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

	@Test(priority = 10)
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
			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_checkout_payment_info")));

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

	@Test(priority = 11, enabled = false)
	public void CreditDebitMethodTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_checkout_payment_arrow")));

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
			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
			errorcounter++;
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("share_treats_checkout_payment_info")));

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

	@Test(priority = 12)
	public void PureTreatsHomeReturnTest() throws Exception {

		// Going back and checking if redirect to Recipient Details page is successful
		try {

			driver.pressKey(new KeyEvent(AndroidKey.BACK));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String CartTitleText = CartTitle.getText();

			if (CartTitleText.equals("Recipient Details")) {
				System.out.println("Successfully redirected to Recipient Details page");
			} else {
				markTestStatus("failed", "FAILED: Recipient Details page title error / not found", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Recipient Details page title is not found.", driver);
			errorcounter++;
		}

		// Checking and closing of keyboard if visible
		try {

			driver.pressKey(new KeyEvent(AndroidKey.BACK));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			boolean isKeyboardShown = driver.isKeyboardShown();

			if (isKeyboardShown) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} else {
				System.out.println("Keyboard is not shown");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Keyboard Check error.", driver);
		}

		// Going back and checking if redirect to Pure Treats Specific category page is
		// successful
		try {

			driver.pressKey(new KeyEvent(AndroidKey.BACK));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			AndroidElement PureTreatsCategory = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String PureTreatsCategoryText = PureTreatsCategory.getText();

			if (PureTreatsCategoryText.equals(CategoryString)) {
				System.out.println("Successfully redirected back to Specific Category page");
			} else {
				markTestStatus("failed", "FAILED: Specific Category page title error / not found", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Category page title is not found.", driver);
			errorcounter++;
		}

		// Going back and checking if redirect to Pure Treats Home page is successful
		try {

			driver.pressKey(new KeyEvent(AndroidKey.BACK));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			AndroidElement PureTreatsTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String PureTreatsText = PureTreatsTitle.getText();

			if (PureTreatsText.equals("Pure Treats")) {
				System.out.println("Test passed: Redirect to Pure Treats successful");
			} else {
				markTestStatus("failed", "FAILED: Pure Treats title is not found.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pure Treats title is not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 13)
	public void TransactionHistoryTest() throws Exception {
		// Clicking of Transaction History Button
		try {
			AndroidElement TransacHistoryButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("menu_view_transactions")));
			TransacHistoryButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Transaction History button is not found.", driver);
			errorcounter++;
		}

		// Checking if "Transaction History" title is visible
		try {
			// Checking if "Transaction History" title is visible
			AndroidElement DonationDriveTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String DonationDriveText = DonationDriveTitle.getText();

			if (DonationDriveText.equals("Transaction History")) {
				System.out.println("Test passed: Redirect to Transaction History successful");
			} else {
				markTestStatus("failed", "FAILED: Transaction History title is not found.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Transaction History title is not found.", driver);
			errorcounter++;
		}
		if (errorcounter > 0) {
			markTestStatus("failed",
					"an error an occurred on Pure Treats > For A Friend test, Please see logs for details", driver);
		} else {
			markTestStatus("passed", "Pure Treats > For A Friend Module Successful", driver);
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
