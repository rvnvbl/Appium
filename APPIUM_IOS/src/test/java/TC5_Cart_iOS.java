import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class TC5_Cart_iOS extends SetupIOS {

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
	public void LoginFieldsTest_cart(String email, String password) throws Exception {

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
	public void Tap_Quantity_Btn() throws Exception {

		// variables for Subtotal
		String initial_sub = null;
		String updated_sub = null;

		// Checking of Cart Button if visible
		try {

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			// verify if Cart Button is visible
			IOSElement btn_cart = (IOSElement) new WebDriverWait(driver, 30)
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
				Thread.sleep(5000);
				btn_instore_pickup.click();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
				Thread.sleep(5000);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Store search bar could not be found.", driver);
				failedCounter++;
			}

			// Checking of Puregold branch on store list
			try {
				IOSElement puregold_branch = (IOSElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.elementToBeClickable(MobileBy.name("PUREGOLD MAKATI")));

				Thread.sleep(5000);
				puregold_branch.click();

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Puregold Makati branch is not found / error.", driver);
				failedCounter++;
			}

			// Checking if redirected to store Homepage
			try {
				IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Search for products and brands")));

				markTestStatus("passed", "PASSED: Successfully redirected to store homepage", driver);
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
				Thread.sleep(3000);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Search bar not found.", driver);
				failedCounter++;
			}

			// Verify second search bar
			try {
				IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
						.visibilityOfElementLocated(MobileBy.name("Search for product name or category")));

				searchbar.click();
				Thread.sleep(3000);
				searchbar.sendKeys("Globe Callcard P500");
				searchbar.sendKeys(Keys.ENTER);
				Thread.sleep(5000);

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Search bar not found.", driver);
				failedCounter++;
			}

			// Checking of product list
//			try {
//				IOSElement product = (IOSElement) new WebDriverWait(driver, 30)
//						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("add_cart_MAGGI_MAGIC_SARAP__55G")));
//				Thread.sleep(5000);
//				
//				markTestStatus("passed","PASSED: Search product successful.", driver);
//				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//				
//			} catch (Exception ex) {
//				markTestStatus("failed", "FAILED: Searched product is not found / error.", driver);
//				failedCounter ++;
//			}

			// Checking of Add to Cart Button if visible
			try {

				// get all elements with the element class XCUIElementTypeStaticText
				List<IOSElement> btn_addCart = driver.findElementsByName("ADD TO CART");

				btn_addCart.get(0).click();
				markTestStatus("passed", "PASSED: Add to Cart Button found", driver);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Thread.sleep(5000);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
				failedCounter++;
			}

			// Checking of Cart Button if visible
			try {

				// Navigate back
				driver.navigate().back();
				Thread.sleep(5000);

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

		// Check if redirected to cart page
		try {

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			// verify if redirected to Cart page
			IOSElement lbl_cart = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));

			markTestStatus("passed", "PASSED: Redirected to Cart page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Cart page.", driver);
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
			Thread.sleep(5000);
		}

		// Get Initial Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(3000);
			System.out.println("Initial: " + lbl_Subtotal.getText());
			initial_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Checking of Cart Plus Button if visible
		try {

			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'cart plus'")));

			btn_cartPlus.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Increase qty button found.", driver);
			failedCounter++;
		}

		// Get Updated Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_updatedSubtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(3000);
			System.out.println("Updated: " + lbl_updatedSubtotal.getText());
			updated_sub = lbl_updatedSubtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Check if Subtotal updates
		try {

			if (initial_sub.equals(updated_sub)) {
				markTestStatus("failed", "FAILED: Subtotal value did not update.", driver);
				failedCounter++;
			} else {
				markTestStatus("passed", "Passed: Subtotal value updated.", driver);
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Error in checking subtotal value.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	public void Input_Quantity() throws Exception {

		// variables for Subtotal
		String initial_sub = "";
		String updated_sub = "";

		// Get Initial Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(3000);
			System.out.println("Initial: " + lbl_Subtotal.getText());
			initial_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Get the first quantity textfield element
		try {

			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textField = driver.findElementsByClassName("XCUIElementTypeTextField");

			// selecting the first Textfield element
			list_textField.get(0).click();
			list_textField.get(0).clear();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			list_textField.get(0).sendKeys("3");
			list_textField.get(0).sendKeys(Keys.ENTER);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Quantity textfield not found.", driver);
			failedCounter++;
		}

		// Get Updated Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(3000);
			System.out.println("Updated: " + lbl_Subtotal.getText());
			updated_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Check if Subtotal updates
		try {

			if (initial_sub.equals(updated_sub)) {
				markTestStatus("failed", "FAILED: Subtotal value did not update.", driver);
				failedCounter++;
			} else {
				markTestStatus("passed", "Passed: Subtotal value updated.", driver);
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Error in checking subtotal value.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5, enabled = false)
	public void Search_Item() throws Exception {

		// variables for Subtotal
		String initial_sub = null;
		String updated_sub = null;

		// Get Initial Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(5000);
			initial_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// verify search button
		try {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// verify search button
			IOSElement btn_searchIcon = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart_search")));

			btn_searchIcon.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Search Icon not found.", driver);
			failedCounter++;
		}

		// Verify search bar
		try {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// verify search bar
			IOSElement btn_searchBar = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("Search for product name or category")));

			btn_searchBar.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			btn_searchBar.sendKeys("gardenia buttertoast");
			btn_searchBar.sendKeys(Keys.ENTER);

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Search Bar not found.", driver);
			failedCounter++;
		}

		// Checking of product list
		try {
			IOSElement product = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("add_cart_GARDENIA_BUTTERTOAST_5_SLICE__125G")));

			markTestStatus("passed", "PASSED: Search product successful.", driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Searched product is not found / error.", driver);
			failedCounter++;
		}

		// Checking of Add to Cart Button if visible
		try {

			// get all elements with the element class XCUIElementTypeStaticText
			List<IOSElement> btn_addCart = driver.findElementsByName("ADD TO CART");

			btn_addCart.get(0).click();
			markTestStatus("passed", "PASSED: Add to Cart Button found", driver);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
			failedCounter++;
		}

		Thread.sleep(3000);

		// Checking of Trash bin Button if visible
		try {

			IOSElement btn_trashbin = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("delete quantity")));

			markTestStatus("passed", "PASSED: Trash Bin Button found", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Trash Bin button found.", driver);
			failedCounter++;
		}

		// Checking of Cart Plus Button if visible
		try {

			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart plus")));

			markTestStatus("passed", "PASSED: Increse QTY Button found", driver);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			Thread.sleep(2000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
			failedCounter++;
		}

		// Check if redirected back to Cart page
		try {

			driver.navigate().back();

			IOSElement lbl_cart = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));

			markTestStatus("passed", "PASSED: Redirected back to Cart page", driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Cart page.", driver);
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
			Thread.sleep(5000);
		}

		// Verify if added item is in Cart page
		try {

			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AccessibilityId("GARDENIA BUTTERTOAST 5 SLICE 125G")));

			markTestStatus("passed", "PASSED: Added product is in the cart.", driver);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Added product not found.", driver);
			failedCounter++;
		}

		// Get Updated Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(5000);
			updated_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Check if Subtotal updates
		try {

			if (initial_sub.equals(updated_sub)) {
				markTestStatus("failed", "FAILED: Subtotal value did not update.", driver);
				failedCounter++;
			} else {
				markTestStatus("passed", "Passed: Subtotal value updated.", driver);
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Error in checking subtotal value.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 6, enabled = false)
	public void Remove_Item() throws Exception {

		// variables for Subtotal
		String initial_sub = null;
		String updated_sub = null;

		// Get Initial Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(5000);
			initial_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Swipe Left Product

		try {
			// Get element to be deleted
			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AccessibilityId("GARDENIA BUTTERTOAST 5 SLICE 125G")));

			// swipe left in product text
			swipeElementIOS(added_item, Direction.LEFT);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Thread.sleep(5000);

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

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove Button not found", driver);
			failedCounter++;
		}

		// Check if product has been removed
		try {

			Thread.sleep(20000);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			// Verify if successfully removed item
			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AccessibilityId("GARDENIA BUTTERTOAST 5 SLICE 125G")));

			markTestStatus("failed", "FAILED: Deleted product can still be found", driver);
			failedCounter++;

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("passed", "PASSED: Delete product successful.", driver);
		}

		// Get Updated Subtotal value
		try {

			// verify if redirected to Cart page
			IOSElement lbl_Subtotal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("subtotal")));

			// get value of initial subtotal
			Thread.sleep(5000);
			updated_sub = lbl_Subtotal.getText();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal value not found.", driver);
			failedCounter++;
		}

		// Check if Subtotal updates
		try {

			if (initial_sub.equals(updated_sub)) {
				markTestStatus("failed", "FAILED: Subtotal value did not update.", driver);
				failedCounter++;
			} else {
				markTestStatus("passed", "Passed: Subtotal value updated.", driver);
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Error in checking subtotal value.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 7)
	public void ProceedToCheckout() throws Exception {

		try {

			Thread.sleep(10000);
			// Verify if Checkout Button if visible
			IOSElement btn_checkout = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Checkout")));
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
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 8)
	public void Remove_fromCart() throws Exception {

		// Check if pop up warning appeared to cart page
		try {

			// navigate back to cart page
			driver.navigate().back();
			Thread.sleep(5000);

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
			Thread.sleep(5000);
		}

		// Swipe Left Product

		try {
			// Get element to be deleted
			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cell_row")));

			// swipe left in product text
			swipeElementIOS(added_item, Direction.LEFT);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot perform swipe", driver);
			failedCounter++;
		}

		// Check if product has been removed
		try {

			Thread.sleep(10000);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			// Verify if successfully removed item
			IOSElement added_item = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cell_row")));

			markTestStatus("failed", "FAILED: Deleted product can still be found", driver);
			failedCounter++;

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("passed", "PASSED: Delete product successful.", driver);
		}
	}

	@Test(priority = 9)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Cart Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Cart Module successful.", driver);
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
