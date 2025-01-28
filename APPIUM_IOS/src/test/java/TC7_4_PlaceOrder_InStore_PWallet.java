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

public class TC7_4_PlaceOrder_InStore_PWallet extends SetupIOS {

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
	public void LoginFieldsTest_InStore_Pwallet(String email, String password) throws Exception {

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
	public void Verify_CartItem() throws Exception {
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

			// Search Puregold branch in search bar

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
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
			// try {
			// IOSElement product = (IOSElement) new WebDriverWait(driver, 30)
			// .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("add_cart_GLOBE_AT_HOME_PREPAID_WIFI____")));
			// Thread.sleep(5000);
			//
			// markTestStatus("passed","PASSED: Search product successful.", driver);
			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//
			// } catch (Exception ex) {
			// markTestStatus("failed", "FAILED: Searched product is not found / error.",
			// driver);
			// failedCounter ++;
			// }

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

			// Checking Cart Button if visible
			try {
				// navigate back
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				driver.navigate().back();
				Thread.sleep(5000);

				IOSElement btn_cart = (IOSElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
				btn_cart.click();
				Thread.sleep(5000);

				markTestStatus("passed", "PASSED: Cart button found.", driver);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			} catch (Exception ex) {
				markTestStatus("failed", "FAILED: Cart button not found / error.", driver);
				failedCounter++;
			}

		}

	}

	@Test(priority = 4)
	public void Cart_page() throws Exception {

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

		// Checking of Cart Plus Button if visible
		try {

			IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.iOSNsPredicateString("label CONTAINS 'cart plus'")));

			btn_cartPlus.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
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
			IOSElement lbl_Branch = (IOSElement) new WebDriverWait(driver, 60)
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

	@Test(priority = 6)
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

	@Test(priority = 7, dataProvider = "PWallet_method", dataProviderClass = DataProvider_Login.class)
	public void PaymentMethod_PWallet(String PWallet) throws Exception {

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

		// Select P-Wallet method
		try {

			Thread.sleep(5000);
			// Verify if P-Wallet method is visible
			IOSElement btn_Pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(PWallet)));
			btn_Pwallet.click();

			markTestStatus("passed", "PASSED: P-Wallet button is displayed.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: P-Wallet button not found.", driver);
			failedCounter++;
		}

		// Check if P-Wallet has balance
		try {

			Thread.sleep(5000);
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

				} catch (Exception e) {
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
					Thread.sleep(10000);

					// Checking if redirected back to Payment Method page
					try {

						IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
								.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));

						markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

						driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					} catch (Exception e) {
						markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
						failedCounter++;
					}

					// Navigate back to checkout page
					driver.navigate().back();
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

				} catch (Exception e) {
					markTestStatus("failed", "FAILED: Change Payment Method button not found.", driver);
					failedCounter++;
				}

			}

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {

			// Check if redirected to checkout page if P-Wallet has balance
			try {

				// P-Wallet has balance
				markTestStatus("passed", "P-Wallet has balance.", driver);

				Thread.sleep(5000);
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

	@Test(priority = 8, enabled = false)
	public void Place_Order() throws Exception {

		// Verify if Place Order button is displayed
		try {

			Thread.sleep(5000);
			// Verify if Place Order button is visible
			IOSElement btn_placeOrder = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Place Order")));
			btn_placeOrder.click();
			Thread.sleep(20000);

			markTestStatus("passed", "PASSED: Place Order button found.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Place Order button not found / error.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 9, dataProvider = "WalletPIN", dataProviderClass = DataProvider_Login.class, enabled = false)
	public void PWallet_PIN(String PIN) throws Exception {

		// Checking if redirected to P-Wallet PIN page
		try {

			IOSElement lbl_pwallet_pin = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet PIN")));

			markTestStatus("passed", "PASSED: Redirected to P-Wallet PIN page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to P-Wallet PIN page.", driver);
			failedCounter++;
		}

		// Input PIN
		try {

			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> txt_PIN = driver.findElementsByClassName("XCUIElementTypeTextField");

			// Type PIN
			txt_PIN.get(0).sendKeys(PIN);
			Thread.sleep(5000);

			markTestStatus("passed", "PASSED: Redirected to P-Wallet PIN page.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to P-Wallet PIN page.", driver);
			failedCounter++;
		}

		// Verify if Submit button is displayed
		try {

			Thread.sleep(5000);
			// Verify if Submit button is visible
			IOSElement btn_submit = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Submit")));
			btn_submit.click();
			Thread.sleep(5000);

			markTestStatus("passed", "PASSED: Submit button found.", driver);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Submit button not found / error.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 10, enabled = false)
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

	@Test(priority = 11, enabled = false)
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

	@Test(priority = 12)
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

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Place Order for In Store pickup using P-Wallet Module has error/s.",
					driver);
		} else {
			markTestStatus("passed", "PASSED: Place Order for In Store pickup using P-Wallet Module successful.",
					driver);
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
