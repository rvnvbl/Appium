import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.internal.matchers.Each;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC19_CaseOrder_Homepage extends SetupIOS {

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

		try {
			IOSElement addToCartPopUp = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add Product to Cart")));
			markTestStatus("passed", "PASSED: Add to Cart Pop Up Shows", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5)
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
		try {
			IOSElement editQuantityBtn = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("EDIT QUANTITY")));
			markTestStatus("passed", "PASSED: Edit Quantiy Button Found", driver);
			editQuantityBtn.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Edit Quantiy Button not found.", driver);
			failedCounter++;
		}

		// Add to cart title popup
		try {
			IOSElement addToCartPopUp = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add Product to Cart")));
			markTestStatus("passed", "PASSED: Add to Cart Pop Up Shows", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
			failedCounter++;
		}

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

		// Grand Total Pop Up
		try {
			IOSElement grandTotalPopUp = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("grandTotal")));
			popupGrandTotalPrice = grandTotalPopUp.getText();
			markTestStatus("passed", "PASSED: Grand Total Pop Up Shows", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Grand Total Pop Up Not Shows.", driver);
			failedCounter++;
		}

		// Add to cart button
		try {
			IOSElement addToCartButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add to Cart")));
			markTestStatus("passed", "PASSED: Add to Cart Button Shows", driver);
			addToCartButton.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button not found.", driver);
			failedCounter++;
		}

		// GrandTotal Cart Page
		try {
			IOSElement cartTotalPrice = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));
			cartGrandTotal = cartTotalPrice.getText();
			markTestStatus("passed", "PASSED: Grand Total Pop Up Shows", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Grand Total Pop Up Not Shows.", driver);
			failedCounter++;
		}
		System.out.println("Price per piece:" + productPrice);
		System.out.println("Popup Subtotal Quantity: " + subtotalPerQuantity);
		System.out.println("Popup Subtotal Case: " + subtotalPerCase);
		System.out.println("Popup total quantity:" + popupTotalQuantiy);
		System.out.println("Popup Grand total:" + popupGrandTotalPrice);
		System.out.println("Cart Grand total:" + cartGrandTotal);

	}

	@Test(priority = 7)
	private void validateTheData() {
		// Checking if the calculation is same in the grand total
		int totalPrice = productPrice * popupTotalQuantiy;
		String[] cartTotalArray = cartGrandTotal.split("\\.");
		String fixedValue = cartTotalArray[0].substring(1).replace(",", "");
		int grandTotalInteger = Integer.parseInt(fixedValue);

		if (totalPrice == grandTotalInteger) {
			System.out.println("The total price is same and correct");
		}

		// Checking if the price is same from pop up and cart page
		String[] popUpTotalArray = popupGrandTotalPrice.split("\\.");
		String fixedPopUpValue = popUpTotalArray[0].substring(1).replace(",", "");
		int grandTotalIntegerPopup = Integer.parseInt(fixedPopUpValue);

		if (grandTotalIntegerPopup == grandTotalInteger) {
			System.out.println("The price on pop up and cart is same");
		}
	}

	@Test(priority = 8)
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

	@Test(priority = 11)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Add to Cart Module > Case Order > Homepage has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Add to Cart Module > Case Order > Homepage has successful.", driver);
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

}
