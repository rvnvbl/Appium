import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.junit.internal.matchers.Each;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class TC19_2_CaseOrder_ReorderandCancel extends SetupIOS {

	int failedCounter = 0;
	int productPrice = 0;
	String subtotalPerCase = "";
	String subtotalPerQuantity = "";
	String cartGrandTotal = "";
	int popupTotalQuantiy = 0;
	String popupGrandTotalPrice = "";

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
	public void LoginFieldsTest_AddToCart_Quantity(String email, String password) throws Exception {

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
			// verify if complete my profile is visible
			IOSElement backButtonCompleteMyProfile = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Complete My Profile")));
			driver.navigate().back();
			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);
		} catch (Exception e) {
			System.out.println("Complete My Profile doesnt show no need to back");
		}

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
	public void clickFirstItem() throws Exception {
		// Work Around if the latest product is in the top
		scrollToElement("Sally's Covid Essentials");
		scrollToElement("Sally's Covid Essentials");
		scrollToElement("Sally's Covid Essentials");

		try {
			// Get all the elements that have add to cart name
			List<IOSElement> btn_addCart = driver.findElementsByName("ADD TO CART");
			// Clicking first item
			btn_addCart.get(0).click();
			markTestStatus("passed", "PASSED: Add to Cart Button found", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
			failedCounter++;
		}

//		try {
//			IOSElement addToCartPopUp = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add Product to Cart")));
//			markTestStatus("passed", "PASSED: Add to Cart Pop Up Shows", driver);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
//			failedCounter++;
//		}
	}

	@Test(priority = 5 , enabled = false)
	public void addToCartQuantityTest() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// Get all the elements that have add to cart name
		List<IOSElement> minusButton = driver.findElementsByName("minusButton");
		List<IOSElement> plusButton = driver.findElementsByName("plusButton");
		List<IOSElement> tfEditQty = driver.findElementsByName("plusMinusTF");

		// Give specific name to the buttons
		IOSElement minusPiece = minusButton.get(0);
		IOSElement minusCase = minusButton.get(1);
		IOSElement plusPiece = plusButton.get(0);
		IOSElement pluseCase = plusButton.get(1);
		IOSElement editPiece = tfEditQty.get(0);
		IOSElement editCase = tfEditQty.get(1);

		// Add Product Quantity in Add to Cart
		plusPiece.click();
		editPiece.clear();
		editPiece.sendKeys("2");
		minusPiece.click();

		// Piece Subtotal
		try {
			IOSElement subtotalPiece = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("pieceSubTotal")));

			productPrice = Integer.parseInt(subtotalPiece.getText().substring(1, 4));
			markTestStatus("passed", "PASSED: Subtoal Piece Pop Up Shows", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtoal Piece not show.", driver);
			failedCounter++;
		}

		// Click done the keyboard will minimize
		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Add Product Case of Order in Add to Cart
		pluseCase.click();
		editCase.clear();
		editCase.sendKeys("2");
		minusCase.click();

		// Click done the keyboard will minimize
		btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Total Quantity in Pop Up
		try {
			IOSElement totalQuantityString = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("totalQuantity")));

			String initialProductPiece = totalQuantityString.getText();
			String finalQuantity = "";
			int index = initialProductPiece.indexOf('p');
			if (index != -1) {
				finalQuantity = initialProductPiece.substring(0, index);
			}
			popupTotalQuantiy = Integer.parseInt(finalQuantity);

			// popupTotalQuantiy = Integer.parseInt(initialProductPiece);
			markTestStatus("passed", "PASSED: Total Quantity Pop Up Shows", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Total Quantity Pop Up not Shows", driver);
			failedCounter++;
		}

		// PieceSubTotal
		try {
			IOSElement pieceSubtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("pieceSubTotal")));

			subtotalPerQuantity = pieceSubtotal.getText();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button not found.", driver);
			failedCounter++;
		}

		// Case Subtotal
		try {
			IOSElement caseSubtotalPrice = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("caseSubTotal")));

			subtotalPerCase = caseSubtotalPrice.getText();

			// subtotalPerCase = Integer.parseInt(finalQuantity);
			markTestStatus("passed", "PASSED: Add to Cart Pop Up Shows", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button not found.", driver);
			failedCounter++;
		}
		// Add to cart button
		try {
			IOSElement addToCartButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add to Cart")));
			markTestStatus("passed", "PASSED: Add to Cart Pop Up Shows", driver);
			addToCartButton.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 6)
	private void caseOrderCartTest() {
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
		// Edit Quantity
//		try {
//			IOSElement editQuantityBtn = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("EDIT QUANTITY")));
//			markTestStatus("passed", "PASSED: Edit Quantiy Button Found", driver);
//			editQuantityBtn.click();
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Edit Quantiy Button not found.", driver);
//			failedCounter++;
//		}
//
//		// Add to cart title popup
//		try {
//			IOSElement addToCartPopUp = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add Product to Cart")));
//			markTestStatus("passed", "PASSED: Add to Cart Pop Up Shows", driver);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
//			failedCounter++;
//		}
//
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		// Get all the elements that have add to cart name
//		List<IOSElement> minusButton = driver.findElementsByName("minusButton");
//		List<IOSElement> plusButton = driver.findElementsByName("plusButton");
//		List<IOSElement> tfEditQty = driver.findElementsByName("plusMinusTF");
//
//		// Give specific name to the buttons
//		IOSElement minusPiece = minusButton.get(0);
//		IOSElement minusCase = minusButton.get(1);
//		IOSElement plusPiece = plusButton.get(0);
//		IOSElement pluseCase = plusButton.get(1);
//		IOSElement editPiece = tfEditQty.get(0);
//		IOSElement editCase = tfEditQty.get(1);
//
//		// Add Product Quantity in Add to Cart
//		plusPiece.click();
//		editPiece.clear();
//		editPiece.sendKeys("2");
//		minusPiece.click();
//
//		// Click done the keyboard will minimize
//		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
//		btn_done.click();
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//		// Add Product Case of Order in Add to Cart
//		pluseCase.click();
//		editCase.clear();
//		editCase.sendKeys("2");
//		minusCase.click();
//
//		// Click done the keyboard will minimize
//		btn_done = (IOSElement) driver.findElement(By.name("Done"));
//		btn_done.click();
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//		// Grand Total Pop Up
//		try {
//			IOSElement grandTotalPopUp = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("grandTotal")));
//			popupGrandTotalPrice = grandTotalPopUp.getText();
//			markTestStatus("passed", "PASSED: Grand Total Pop Up Shows", driver);
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Grand Total Pop Up Not Shows.", driver);
//			failedCounter++;
//		}
//
//		// Add to cart button
//		try {
//			IOSElement addToCartButton = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add to Cart")));
//			markTestStatus("passed", "PASSED: Add to Cart Button Shows", driver);
//			addToCartButton.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: No Add to Cart button not found.", driver);
//			failedCounter++;
//		}
//
//		// GrandTotal Cart Page
//		try {
//			IOSElement cartTotalPrice = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));
//			cartGrandTotal = cartTotalPrice.getText();
//			markTestStatus("passed", "PASSED: Grand Total Pop Up Shows", driver);
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Grand Total Pop Up Not Shows.", driver);
//			failedCounter++;
//		}
//		System.out.println("Price per piece:" + productPrice);
//		System.out.println("Popup Subtotal Quantity: " + subtotalPerQuantity);
//		System.out.println("Popup Subtotal Case: " + subtotalPerCase);
//		System.out.println("Popup total quantity:" + popupTotalQuantiy);
//		System.out.println("Popup Grand total:" + popupGrandTotalPrice);
//		System.out.println("Cart Grand total:" + cartGrandTotal);

	}

	@Test(priority = 7)
	public void Checkout_page() throws Exception {

		// Verify if Checkout Button if visible
		try {

			Thread.sleep(10000);
			IOSElement btn_checkout = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));
			btn_checkout.click();

			markTestStatus("passed", "PASSED: Checkout button is visible.", driver);

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout button not found/disabled", driver);
			failedCounter++;
		}

		// Check if redirected to checkout page
		try {

			Thread.sleep(5000);
			// Verify if redirected to checkout page
			IOSElement lbl_checkout = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Checkout")));

			markTestStatus("passed", "PASSED: Redirected to Checkout page.", driver);

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
			failedCounter++;
		}

		// Checking if Branch is visible
		try {

			Thread.sleep(5000);
			IOSElement lbl_Branch = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Branch")));

			markTestStatus("passed", "PASSED: Branch is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Branch is not displayed.", driver);
			failedCounter++;
		}

		// Checking if Contact is visible
		try {

			IOSElement lbl_contact = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Contact Info")));

			markTestStatus("passed", "PASSED: Contact Information is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Contact Information is not displayed.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 8)
	public void Verify_Pickup_Schedule() throws Exception {

		// Checking if Pickup schedule button is visible
		try {

			IOSElement btn_pickup_schedule = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Select Pickup Time")));
			btn_pickup_schedule.click();

			markTestStatus("passed", "PASSED: Pickup Schedule button is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pickup Schedule button not found.", driver);
			failedCounter++;
		}

		// Checking of Desired Date
		try {
			Thread.sleep(5000);
			IOSElement btn_date = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("date_1")));
			btn_date.click();

			markTestStatus("passed", "PASSED: Select Date successful.", driver);

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

			Thread.sleep(5000);
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

	@Test(priority = 9, dataProvider = "inStore_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_PayInStore(String InStore) throws Exception {

		// Checking if Payment Method button is visible
		try {

			IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("value_payment_method")));
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

		// Select Pay In Cash method
		try {

			Thread.sleep(5000);
			// Verify if Pay In Store method is visible
			IOSElement btn_InStore = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(InStore)));
			btn_InStore.click();

			markTestStatus("passed", "PASSED: Pay In Store button is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Pay In Store button not found.", driver);
			failedCounter++;
		}

		// Verify if Pay In Cash method is displayed
		try {

			Thread.sleep(5000);
			// Verify if Pay In Store method is visible
			IOSElement lbl_InStore = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(InStore)));

			markTestStatus("passed", "PASSED: Change payment method to Pay In Store passed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Change payment method to Pay In Store failed", driver);
			failedCounter++;
		}
	}

	@Test(priority = 10)
	public void Place_Order() throws Exception {

		// Verify if Place Order button is displayed
		try {

			Thread.sleep(5000);
			// Verify if Place Order button is visible
			IOSElement btn_placeOrder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Place Order")));
			btn_placeOrder.click();
			Thread.sleep(5000);

			markTestStatus("passed", "PASSED: Place Order button found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Place Order button not found / error.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 11)
	public void Verify_ConfirmationPage() throws Exception {

		// Verify if Redirected to Confirmation page
		try {

			Thread.sleep(5000);
			IOSElement lbl_Confirmation = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Confirmation")));
			Thread.sleep(5000);

			markTestStatus("passed", "PASSED: Redirected to Confirmation page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Confirmation page / error.", driver);
			failedCounter++;
		}

		// Click My Orders button
		try {

			Thread.sleep(5000);
			IOSElement btn_MyOrders = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Orders")));
			btn_MyOrders.click();
			Thread.sleep(5000);

			markTestStatus("passed", "PASSED: My Orders button found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: My Orders button not found / error.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 12)
	public void Verify_OrderPage() throws Exception {

		// Verify if Redirected to Orders page
		try {

			Thread.sleep(5000);
			IOSElement lbl_Order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));
			Thread.sleep(5000);

			markTestStatus("passed", "PASSED: Redirected to Order page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Order page / error.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 13)
	public void Navigate_orders_page() throws Exception {

		// Navigate to Orders Page
		try {
			// verify if Orders Button is visible and enabled
			IOSElement btn_order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));
			btn_order.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Orders button is not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Orders Page
		try {
			IOSElement lbl_order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));

			markTestStatus("passed", "PASSED: Successfully redirected to Orders page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Orders page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 14)
	public void Order_details_page() throws Exception {

		// Order list
		try {

			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			list_orders.get(0).click();

			markTestStatus("passed", "PASSED: Order list found", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Order list found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 15)
	public void Reorder() throws Exception {

		// Verify if Reorder button is visible
		try {
			IOSElement btn_reorder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Reorder")));
			btn_reorder.click();

			markTestStatus("passed", "PASSED: Reorder button found.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Reorder button not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Cart page
		try {
			IOSElement lbl_cart = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));

			markTestStatus("passed", "PASSED: Successfully redirected to Cart page.", driver);
			Thread.sleep(2000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Cart page / error.", driver);
			failedCounter++;
		}

		// Check if pop up warning appeared to cart page
		try {

			// verify if okay button is visible
			IOSElement btn_okay = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Okay")));
			btn_okay.click();

			markTestStatus("passed", "Popup warning displayed.", driver);
			Thread.sleep(2000);

		} catch (Exception e) {
			markTestStatus("passsed", "No Popup warning displayed.", driver);
		}

		// Verify if item is in the cart
		try {
			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'cart plus'")));

			markTestStatus("passed", "PASSED: Item found.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Item not found / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 16)
	public void Remove_Item() throws Exception {

		// Swipe Left Product

		try {
			// Get element to be deleted
			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'cart plus'")));

			// swipe left in product text
			swipeElementIOS(btn_cartPlus, Direction.LEFT);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot perform swipe", driver);
			failedCounter++;
		}

		// Removing Address
		try {
			// Verify remove button
			IOSElement btn_remove = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("REMOVE")));
			btn_remove.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove Button not found", driver);
			failedCounter++;
		}

		// Check if product has been removed
		try {

			// Verify if successfully removed item
			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'cart plus'")));

			markTestStatus("failed", "FAILED: Deleted product can still be found", driver);
			failedCounter++;

		} catch (Exception e) {
			markTestStatus("passed", "PASSED: Delete product successful.", driver);
			driver.navigate().back();
			driver.navigate().back();
		}
	}

	@Test(priority = 17)
	public void homePageTest() throws Exception {
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

	@Test(priority = 18)
	public void navigateToOrderDetailsPage() throws Exception {

		// Navigate to Orders Page
		try {
			// verify if Orders Button is visible and enabled
			IOSElement btn_order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));
			btn_order.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Orders button is not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Orders Page
		try {
			IOSElement lbl_order = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Orders")));

			markTestStatus("passed", "PASSED: Successfully redirected to Orders page.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Orders page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 19)
	public void validateOrderPage() throws Exception {

		// Order list
		try {

			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			list_orders.get(0).click();

			markTestStatus("passed", "PASSED: Order list found", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Order list found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Orders Details page
		try {
			IOSElement lbl_reorder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Reorder")));

			markTestStatus("passed", "PASSED: Successfully redirected to Orders details page.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Orders details page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 20)
	public void Cancel_Order() throws Exception {

		// Verify if Cancel Order button is visible
		try {

			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			// swipe up in element
			// swipeElementIOS(list_orders.get(0), Direction.UP);
			scrollToElement("Cancel Order");
			IOSElement btn_cancelOrder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Order")));
			btn_cancelOrder.click();

			markTestStatus("passed", "PASSED: Cancel Order button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order button not found.", driver);
			failedCounter++;
		}

		// Checking if Cancel Your Order popup is visible
		try {
			IOSElement lbl_cancel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Your Order")));

			markTestStatus("passed", "PASSED: Cancel Order popup found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order popup not found / error.", driver);
			failedCounter++;
		}

		// Verify if Go Back button is visible
		try {
			IOSElement btn_goBack = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Go Back")));
			btn_goBack.click();

			markTestStatus("passed", "PASSED: Go Back button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Go Back button not found.", driver);
			failedCounter++;
		}

		// Verify if Cancel Order button is visible
		try {
			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_orders = driver.findElementsByClassName("XCUIElementTypeCell");

			// swipe up in element
			// swipeElementIOS(list_orders.get(0), Direction.UP);
			scrollToElement("Cancel Order");
			IOSElement btn_cancelOrder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Order")));
			btn_cancelOrder.click();

			markTestStatus("passed", "PASSED: Cancel Order button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order button not found.", driver);
			failedCounter++;
		}

		// Checking if Cancel Your Order popup is visible
		try {
			IOSElement lbl_cancel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cancel Your Order")));

			markTestStatus("passed", "PASSED: Cancel Order popup found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order popup not found / error.", driver);
			failedCounter++;
		}

		// Select last item in radio button
		try {
			// get all elements with the element class XCUIElementTypeCell
			List<IOSElement> list_radioButton = driver.findElementsByName("cancel unselected");

			// get last index
			Integer idx = list_radioButton.size() - 1;
			list_radioButton.get(idx).click();

			markTestStatus("passed", "PASSED: Radio button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Radio button not found / error.", driver);
			failedCounter++;
		}

		// Verify if Cancel Order button is visible
		try {
			Thread.sleep(5000);
			List<IOSElement> cancelOrderButtons = driver.findElements(By.name("Cancel Order"));
			System.out.println("The number of cancel button is: " + cancelOrderButtons.size());
			cancelOrderButtons.get(1).click();
//			IOSElement btn_cancelOrder = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Cancel Order")));
//			//IOSElement btn_cancelOrder = driver.findElementByIosNsPredicate("type == 'UIAButton' AND label CONTAINS 'Cancel Order'");
//			btn_cancelOrder.click();

			markTestStatus("passed", "PASSED: Cancel Order button found.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Order button not found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Order Cancelled page
		try {
			IOSElement lbl_cancel = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Order Cancelled")));

			markTestStatus("passed", "PASSED: Successfully redirected to Order Cancelled page.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Order Cancelled page / error.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 8, enabled = false)
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

	@Test(priority = 25)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Add to Cart Module > Case Order > Reorder and Cancel has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Add to Cart Module > Case Order > Reorder and Cancel successful.", driver);
		}
	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

	private void scrollToElement(String elementValue) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		HashMap<String, Object> scrollobj = new HashMap<String, Object>();
		scrollobj.put("predicateString", "value == '" + elementValue + "'");
		scrollobj.put("direction", "down");
		executor.executeScript("mobile: scroll", scrollobj);

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
