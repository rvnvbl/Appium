import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class TC4_AddToCart_Categories_iOS extends SetupIOS {

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
	public void LoginFieldsTest_addToCartStore_Homepage(String email, String password) throws Exception {

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

	@Test(priority = 4, enabled = false)
	public void Tap_SearchItem_AddToCart() throws Exception {

		// Click search bar and search Item

		// Verify first search bar
		try {
			IOSElement searchbar = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Search for products and brands")));

			searchbar.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
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
			Thread.sleep(5000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Search bar not found.", driver);
			failedCounter++;
		}

		// Checking of product list
//		try {
//			IOSElement product = (IOSElement) new WebDriverWait(driver, 30).until(
//					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("add_cart_MAGGI_MAGIC_SARAP__55G")));
//
//			markTestStatus("passed", "PASSED: Search product successful.", driver);
//			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//
//		} catch (Exception e) 
//			markTestStatus("failed", "FAILED: Searched product is not found / error.", driver);
//			failedCounter++;
//		}

		// Checking of Add to Cart Button if visible
		try {

			// get all elements with the element class XCUIElementTypeStaticText
			List<IOSElement> btn_addCart = driver.findElementsByName("ADD TO CART");

			if (btn_addCart.size() < 1) {
				System.out.println(btn_addCart.size());
				btn_addCart = driver.findElementsByName("ADD TO CART");
			}

			btn_addCart.get(0).click();
			markTestStatus("passed", "PASSED: Add to Cart Button found", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Add to Cart button found.", driver);
			failedCounter++;
		}

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
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 5, enabled = false)
	public void Tap_CartPlus_btn() throws Exception {
		// Checking of Cart Plus Button if visible
		try {

			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart plus")));

			// btn_cartPlus.click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 6, enabled = false)
	public void Tap_CartMinus_btn() throws Exception {
		// Checking of Cart Minus Button if visible
		try {

			IOSElement btn_cartMinus = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart minus")));

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			markTestStatus("passed", "PASSED: Decrease QTY Button found", driver);
			btn_cartMinus.click();

			// Check if Trash bin Button is visible to know if qty decreased
			try {

				IOSElement btn_trashbin = (IOSElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("delete quantity")));

				markTestStatus("passed", "PASSED: Item qty decreased.", driver);
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Item qty did not decreased.", driver);
				failedCounter++;
			}

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Decrease QTY button found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 7, enabled = false)
	public void Tap_TrashBin_btn() throws Exception {
		// Checking of Trash bin Button if visible
		try {

			IOSElement btn_trashbin = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("delete quantity")));

			btn_trashbin.click();
			markTestStatus("passed", "PASSED: Trash Bin Button found", driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Trash Bin button found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 8, enabled = false)
	public void Tap_Scan_and_Go() throws Exception {

		// Checking of Scan and Go Button if visible
		try {

			driver.navigate().back();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			// verify scan and go button
			IOSElement btn_scan = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("catalog_scan_action")));

			btn_scan.click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Scan and Go button found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Scan Barcode page
		try {

			// verify if redirected to correct page

			IOSElement lbl_scan = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Scan Barcode")));

			markTestStatus("passed", "PASSED: Tap Scan and Go successful.", driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.navigate().back();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Scan Barcode page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 9, enabled = false)
	public void Tap_vouchers() throws Exception {

		// Checking of See Vouchers Button if visible
		try {

			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			// verify See Vouchers button
			IOSElement btn_seeVouchers = (IOSElement) new WebDriverWait(driver, 120)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_see_voucher_list")));

			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			btn_seeVouchers.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No See Vouchers button found.", driver);
			failedCounter++;
		}

		// Checking if redirected to Vouchers page
		try {

			// verify if redirected to correct page
			IOSElement lbl_vouchers = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("My Vouchers")));

			markTestStatus("passed", "PASSED: Tap See My Vouchers successful.", driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Navigate back
			driver.navigate().back();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Vouchers page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 10)
	public void Categories() throws Exception {

		// Checking of Category Button if visible
		try {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// verify Category button
			IOSElement btn_category = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Category")));

			btn_category.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Category button found.", driver);
			failedCounter++;
		}

		// Select category
		try {

			// get all elements with the element class XCUIElementTypeImage
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<IOSElement> list_category = driver.findElementsByClassName("XCUIElementTypeCell");

			// Select third category
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// list_category.get(2).click();
			list_category.get(3).click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Category list found.", driver);
			failedCounter++;
		}

		// Check if redirected to selected category page and verify view all button/page
		try {

			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
			// verify view all button
			List<IOSElement> list_viewAll = driver.findElementsByName("View All");
			Thread.sleep(2000);
			// Tap first view all button
			list_viewAll.get(0).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: View All Button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to view all product under subcategory list
		try {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// verify view all product list title
			IOSElement viewAll_page = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("View All")));

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.navigate().back();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: View All Product list not found.", driver);
			failedCounter++;
		}

		// verify search button
		try {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// verify search button
			IOSElement btn_searchIcon = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("cart search")));

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

			// Checking of On-screen keyboard if visible

			try {
				// Checking if On-screen keyboard appeared
				boolean isKeyboardShown = driver.isKeyboardShown();

				if (isKeyboardShown) {

				} else {

					markTestStatus("failed", "FAILED: On-screen keyboard did not display", driver);
					failedCounter++;

				}
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
				failedCounter++;
			}

			btn_searchBar.sendKeys("hosen lychee");
			btn_searchBar.sendKeys(Keys.ENTER);

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Search Bar not found.", driver);
			failedCounter++;
		}

		// verify product details page
		try {

			// get all elements with the element class XCUIElementTypeImage
			List<IOSElement> btn_productImage = driver.findElementsByClassName("XCUIElementTypeImage");

			// click first product image button
			btn_productImage.get(0).click();
			markTestStatus("passed", "PASSED: Product Image found", driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Product Image found.", driver);
			failedCounter++;
		}

		// Checking of Add to Cart Button if visible
		try {
			IOSElement addToCartProductDetailPage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Add to Cart")));
			Thread.sleep(2000);
			addToCartProductDetailPage.click();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		IOSElement plusCase = plusButton.get(1);
		IOSElement editPiece = tfEditQty.get(0);
		IOSElement editCase = tfEditQty.get(1);

		// Add Product Quantity in Add to Cart
		plusPiece.click();
		editPiece.clear();
		editPiece.sendKeys("2");
		minusPiece.click();
		minusCase.click();

		// Click done the keyboard will minimize
		IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
		btn_done.click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 11)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Add to Cart Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Add to Cart Module successful.", driver);
		}

	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

}
