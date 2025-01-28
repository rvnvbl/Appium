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

public class TC6_1_CheckOut_Android_WithPWalletBalance_CashOnDelivery extends AndroidSetup {
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
		}
	}

	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	public void LoginFieldsTest_CashOnDelivery(String email, String password) throws Exception {

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
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 3)
	public void TapCartButtonTest() throws Exception {
		// Clicking the cart button
		try {
			AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_cart")));

			CartButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {

			// Scrolling to and clicking the Delivery button
			try {
				driver.findElementByAndroidUIAutomator(
						"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
								+ "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");

				AndroidElement DeliveryOptionButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_delivery_layout_image")));
				
				DeliveryOptionButton.click();


				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

				// Checking if Reminder Modal from Delivery is visible to know if redirect is
				// successful
				AndroidElement ReminderModal = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_container")));
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Delivery button is not found.", driver);
			}

			// Clicking of "OK" Button on reminder
			try {
				AndroidElement ReminderModalAccept = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));
				ReminderModalAccept.click();

				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Reminder OK button is not found.", driver);
			}

			// Clicking of "Accept Terms" Button on Guidelines
			try {
				AndroidElement AcceptTermsButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("terms_conditions_accept")));
				AcceptTermsButton.click();

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

				/*
				 * Checking if Add address is visible to know if redirect is successful Text is
				 * used because element ID changes when there is an existing address already
				 */
				AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions
								.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
										+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
										+ "new UiSelector().text(\"Add Address\"));")));
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			}

			// Find and click address located in Makati
			try {
				AndroidElement MakatiAddress = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
						.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
								+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
								+ "new UiSelector().text(\"Test2, Poblacion, Makati City, Metro Manila, 1313\"));")));

				MakatiAddress.click();

				driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Makati Address cannot be found/error", driver);
			}

			// Checking of Store list if redirected successfully
			try {
				// Checking if Store search bar is visible after clicking the address to know if
				// redirect is successful
				AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Did not redirect to store selection screen correctly.", driver);
			}

			// Clicking the specific store
			try {
				AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions
								.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
										+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
										+ "new UiSelector().text(\"PUREGOLD MAKATI\"));")));
				SpecificStoreButton.click();

				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

				// Verifying and fix if modal appears on first branch option
				try {
					AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30)
							.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

					YesButton.click();

					driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

					// Go back to select store
					driver.pressKey(new KeyEvent(AndroidKey.BACK));

					driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

					AndroidElement RedoSpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30)
							.until(ExpectedConditions.elementToBeClickable(
									MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
											+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
											+ "new UiSelector().text(\"PUREGOLD MAKATI\"));")));

					RedoSpecificStoreButton.click();

					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				} catch (Exception a) {
					System.out.println("Change branch modal did not appear, Work-around not needed");
				}

			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) button error/not found.", driver);
				errorcounter++;
			}

			// Checking of store
			try {
				// Checking if Specific store at top is visible and correct (PUREGOLD MAKATI)
				AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_catalog_text_place")));
				String StoreHeaderString = StoreHeader.getText();

				if (StoreHeaderString.contains("OFFICE")) {
					System.out.println("Test passed: Specific store redirected to is correct");
				} else {
					markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.",
							driver);
				}
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
				errorcounter++;
			}

			try {
				Thread.sleep(5000);

				// Clicking of search field in store homepage
				AndroidElement HomeSearchField = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("catalog_shop_search_text")));

				HomeSearchField.click();

				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Store Homepage Search field error / not found.", driver);
				errorcounter++;
			}

			try {
				// Clicking of search field in search
				AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_searchbar")));

				SearchField.click();

				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Search field error / not found.", driver);
				errorcounter++;
			}

			try {
				// Checking if On-screen keyboard appeared
				boolean isKeyboardShown = driver.isKeyboardShown();

				if (isKeyboardShown) {
					System.out.println("Test passed: On-screen keyboard displayed");
				} else {
					markTestStatus("failed", "FAILED: On-screen keyboard did not display", driver);
					errorcounter++;
				}
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
				errorcounter++;
			}

			try {
				// Clicking of search field in search and searching for specific products (Globe
				// at home prepaid wifi)
				AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_searchbar")));

				SearchField.click();

				SearchField.sendKeys("GLOBE CALLCARD P500");

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				// driver.pressKey(new KeyEvent(AndroidKey.SEARCH));
				// ((AndroidDriver<AndroidElement>) driver).pressKey(new
				// KeyEvent(AndroidKey.SEARCH));

				/*
				 * driver.pressKey(new KeyEvent(AndroidKey.SEARCH)
				 * .withFlag(KeyEventFlag.SOFT_KEYBOARD) .withFlag(KeyEventFlag.KEEP_TOUCH_MODE)
				 * .withFlag(KeyEventFlag.EDITOR_ACTION));
				 */

				TouchAction touchAction = new TouchAction(driver);
				touchAction.tap(PointOption.point(1000, 2100)).perform();

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Search field error / not found.", driver);
				errorcounter++;
			}

			try {
				// Checking of products if all results has GLOBE CALLCARD P500 in
				// keywords
				List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

				// Checking all results
				for (int i = 0; i < Products.size(); i++) {
					AndroidElement SpecificProductName = Products.get(i);

					String SpecificProductNameText = SpecificProductName.getText();
					if (SpecificProductNameText.contains("GLOBE CALLCARD P500")) {
						System.out.println("Product contains GLOBE CALLCARD P500 keyword");
						System.out.println(i);
						markTestStatus("passed", "PASSED: Add to Cart > Store Homepage successful", driver);
					} else {
						markTestStatus("failed", "FAILED: Product with no keyword from search found", driver);
						errorcounter++;
					}
				}

			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Search field error / not found.", driver);
				errorcounter++;
			}

			try {
				// Clicking of Add to cart on the product searched
				AndroidElement SearchedAddToCart = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions
								.visibilityOfElementLocated(MobileBy.id("search_item_add_to_cart_status_text")));

				SearchedAddToCart.click();

				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Product add to cart error / not found.", driver);
				errorcounter++;
			}

			try {
				// Checking if Add to cart is successful
				AndroidElement SearchedQuantity = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("search_product_quantity")));

				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Quantity Field of product error / not found.", driver);
				errorcounter++;
			}

			try {
				// Adding quantity to product
				AndroidElement SearchedQuantityAdd = (AndroidElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.id("search_product_quantity_add")));

				SearchedQuantityAdd.click();

				Thread.sleep(5000);

				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Add button of product error / not found.", driver);
				errorcounter++;
			}

			try {
				// Go back to select store
				driver.pressKey(new KeyEvent(AndroidKey.BACK));

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Back button error", driver);
				errorcounter++;
			}

			// Checking if redirected back to store homepage
			try {
				// Checking if Specific store at top is visible and correct (PUREGOLD AGORA)
				AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_catalog_text_place")));
				String StoreHeaderString = StoreHeader.getText();

				if (StoreHeaderString.contains("OFFICE")) {
					System.out.println("Test passed: Specific store redirected to is correct");
				} else {
					markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.",
							driver);
					errorcounter++;
				}
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
				errorcounter++;
			}

			// Clicking of cart button
			try {
				AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("nav_cart")));

				CartButton.click();

				Thread.sleep(5000);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Cart Button error / not found.", driver);
				errorcounter++;
			}

		}

		// Checking if stock confirm appears
		try {
			AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));

			CartStock.click();
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(350, 300)).perform();
		} catch (Exception e) {
			System.out.println("Stock confirm on cart did not appear, no need to click");
		}

		// Checking if redirect to Cart page is successful
		try {
			AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String CartTitleText = CartTitle.getText();

			if (CartTitleText.equals("Cart")) {
				System.out.println("Successfully redirected to Cart page");
			} else {
				markTestStatus("failed", "FAILED: Cart page title error / not found", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 4)
	public void CartCheckoutTest() throws Exception {
		try {
			// Clicking the Checkout button
			AndroidElement CheckoutButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_checkout")));

			CheckoutButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout button error / not found.", driver);
		}

		// Checking if redirect to Checkout page is successful
		try {
			AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String CartTitleText = CartTitle.getText();

			if (CartTitleText.equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
				markTestStatus("passed", "PASSED: Cart Module successful", driver);
			} else {
				markTestStatus("failed", "FAILED: Checkout page title error / not found", driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout page title is not found.", driver);
		}

	}

	@Test(priority = 5)
	public void BranchVerifyTest() throws Exception {
		try {
			// Checking if Branch is visible
			AndroidElement CheckoutBranch = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_branch_info")));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout branch error / not found.", driver);
		}
	}

	@Test(priority = 6)
	public void ContactVerifyTest() throws Exception {
		try {
			// Checking if Contact details is visible
			AndroidElement CheckoutContact = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_card_delivery")));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout Contact details error / not found.", driver);
		}
	}

	@Test(priority = 7)
	public void ScheduleChevronButtonTest() throws Exception {
		try {
			// Clicking the Chevron button on schedule
			AndroidElement ScheduleChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_delivery_schedule_arrow")));

			ScheduleChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Checkout Schedule button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Delivery Schedule")) {
				System.out.println("Successfully redirected to schedule page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Schedule page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Schedule page.", driver);
		}
	}

	@Test(priority = 8)
	public void DesiredDateTest() throws Exception {
		try {
			// Listing and always selecting the fourth Date which is highlighted
			List<AndroidElement> DateList = driver.findElements(By.id("scheduler_date"));

			// Checking all results
			for (int i = 0; i < DateList.size(); i++) {
				AndroidElement SpecificDateElement = DateList.get(i);

				if (i == 3) {
					SpecificDateElement.click();

					DateString1 = SpecificDateElement.getText().toUpperCase();

					System.out.println(DateString1);

					// checking if 0 is detected on date and removal of it
					try {
						if ((DateString1.indexOf("0")) == 4) {
							DateString1 = DateString1.substring(0, 3) + " " + DateString1.substring(5);

							System.out.println(DateString1);
						}
					} catch (Exception e) {
						System.out.println("no zeroes detected on date, no fix needed");
					}

					driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

					break;
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Highlighted Date error / not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 9)
	public void DesiredTimeTest() throws Exception {
		try {
			// Listing and selecting the first time that is available
			List<AndroidElement> TimeList = driver.findElements(By.id("scheduler_remarks"));

			// Checking all results
			for (int i = 0; i < TimeList.size(); i++) {
				AndroidElement SpecificTimeElement = TimeList.get(i);

				String SpecificTimeAvailabilityText = SpecificTimeElement.getText();
				if (SpecificTimeAvailabilityText.equals("AVAILABLE")) {
					List<AndroidElement> TimeRanges = driver.findElements(By.id("scheduler_time_slot"));
					MobileElement SpecificTime = TimeRanges.get(i);

					TimeString1 = SpecificTime.getText();

					System.out.println(TimeString1);

					List<AndroidElement> TimeRadioButtons = driver.findElements(By.id("scheduler_radio"));
					MobileElement SpecificTimeRadioButton = TimeRadioButtons.get(i);

					SpecificTimeRadioButton.click();

					driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

					break;
				}
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific time error / not found.", driver);
			errorcounter++;
		}

		try {
			// Checking the time and date if it is correct
			AndroidElement TimeAndDate = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_delivery_schedule_info")));

			String TimeAndDateText = TimeAndDate.getText();

			System.out.println(TimeAndDateText);

			if (TimeAndDateText.contains(DateString1) && TimeAndDateText.contains(TimeString1)) {
				System.out.println("Time and date is correct");
			} else {
				markTestStatus("failed", "FAILED: Time and Date is not correct.", driver);
				errorcounter++;
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Time and Date error / not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 10)
	public void PaymentChevronTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_arrow")));

			PaymentMethodChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Payment Method")) {
				System.out.println("Successfully redirected to Payment Method page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
		}
	}

	@Test(priority = 11)
	public void PWalletMethodTest() throws Exception {
		try {
			// Clicking the Puregold Wallet payment option
			AndroidElement PWalletOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Puregold Wallet\"));")));

			PWalletOption.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			Thread.sleep(2000);

			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_info")));

			if ((PaymentMethodElement.getText()).contains("Puregold Wallet")) {
				System.out.println("Successfully changed payment method to Puregold Wallet");
			} else {
				markTestStatus("failed", "FAILED: changed payment method to Puregold Wallet failed", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

		} catch (Exception e) {

			try {
				// Checking if modal has appeared
				AndroidElement TopUpButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
						.visibilityOfElementLocated(MobileBy.id("dialog_top_up_balance_option_top_up")));

				AndroidElement ChangePaymentButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(
								MobileBy.id("dialog_top_up_balance_change_option_payment_method")));

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Modal Buttons error / not found.", driver);
				errorcounter++;
			}

			try {
				// Clicking the Change payment method button
				AndroidElement ChangePaymentButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(
								MobileBy.id("dialog_top_up_balance_change_option_payment_method")));

				ChangePaymentButton.click();

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Change Payment method button error / not found.", driver);
				errorcounter++;
			}

			try {
				// Checking if successfully closed the modal
				AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

				if ((ScheduleTitle.getText()).equals("Payment Method")) {
					System.out.println("Successfully redirected to Payment Method page");
				} else {
					markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
					errorcounter++;
				}
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Payment method title error / not found", driver);
				errorcounter++;
			}
		}

	}

	@Test(priority = 12)
	public void GCashMethodTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method (Skipped if no pwallet balance)
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_arrow")));

			PaymentMethodChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("PWallet has no balance, clicking of payment method skipped");
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Payment Method")) {
				System.out.println("Successfully redirected to Payment Method page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
		}

		try {
			// Clicking the GCash payment option
			AndroidElement GCashOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"GCash Direct\"));")));

			GCashOption.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: GCash Direct Payment method button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_info")));

			if ((PaymentMethodElement.getText()).equals("GCash Direct")) {
				System.out.println("Successfully changed payment method to GCash Direct");
			} else {
				markTestStatus("failed", "FAILED: changed payment method to GCash Direct failed", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
		}
	}

	@Test(priority = 13)
	public void CreditDebitMethodTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_arrow")));

			PaymentMethodChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Payment Method")) {
				System.out.println("Successfully redirected to Payment Method page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
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
		}

		try {
			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_info")));

			if ((PaymentMethodElement.getText()).equals("Credit / Debit Card")) {
				System.out.println("Successfully changed payment method to Credit / Debit Card");
			} else {
				markTestStatus("failed", "FAILED: changed payment method to Credit / Debit Card failed", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
		}
	}

	@Test(priority = 14)
	public void CODMethodTest() throws Exception {
		try {
			// Clicking the Chevron button on Payment method
			AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_arrow")));

			PaymentMethodChevronButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to page
			AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((ScheduleTitle.getText()).equals("Payment Method")) {
				System.out.println("Successfully redirected to Payment Method page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
		}

		try {
			// Clicking the COD payment option
			AndroidElement CashOnDeliveryOption = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions
							.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
									+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
									+ "new UiSelector().text(\"Cash On Delivery\"));")));

			CashOnDeliveryOption.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
		}

		try {
			// Checking if successfully changed payment method
			AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_payment_info")));

			if ((PaymentMethodElement.getText()).equals("Cash On Delivery")) {
				System.out.println("Successfully changed payment method to Cash On Delivery");
			} else {
				markTestStatus("failed", "FAILED: changed payment method to Cash On Delivery failed", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
		}
	}

	@Test(priority = 15)
	public void SpecialInstructionsTest() throws Exception {
		try {
			// Clicking and testing the Special Instructions field
			AndroidElement InstructionsField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_instruction")));

			InstructionsField.click();

			InstructionsField.clear();

			InstructionsField.sendKeys("Test Instruction");

			driver.navigate().back();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Special Instructions field error / not found.", driver);
		}
	}

	@Test(priority = 16, enabled = false)
	public void ApplyVoucherTest() throws Exception {
		try {
			// Getting the initial value of the subtotal
			AndroidElement TotalAmount = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_bottom_total")));

			SubTotalString = TotalAmount.getText();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Total Amount element error / not found.", driver);
		}

		try {
			// Scrolling down to the bottom of the page using an element
			MobileElement SubtotalElement = (MobileElement) driver
					.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".scrollable(true)).scrollIntoView" + "(new UiSelector().id(checkout_total_value));");
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Subtotal element error / not found.", driver);
		}

		try {
			// Clicking the Vouchers chevron button
			AndroidElement VoucherChevron = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_voucher_arrow")));

			VoucherChevron.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Voucher button error / not found.", driver);
		}

		try {
			// Checking if successfully redirected to checkout page
			AndroidElement VoucherTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((VoucherTitle.getText()).equals("My Vouchers")) {
				System.out.println("Successfully redirected to Vouchers page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Vouchers page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Vouchers page.", driver);
		}

		try {
			// Voucher usage (Initial check by verifying if there is an existing voucher
			// that can be used)
			AndroidElement VoucherUseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"USE NOW\"));")));

			VoucherUseButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("No existing vouchers found, proceeding to entering new voucher");

			try {
				// Entering of voucher code
				AndroidElement VoucherTextField = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("voucher_redeem_text")));

				VoucherTextField.click();

				VoucherTextField.sendKeys("AutoVoucher0602");

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Voucher Text Field not found", driver);
			}

			try {
				// clicking of voucher redeem button
				AndroidElement VoucherRedeem = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("voucher_redeem_button")));

				VoucherRedeem.click();

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Voucher Redeem Button not found", driver);
			}

			try {
				// Checking if successfully reloaded voucher page
				AndroidElement VoucherTitle = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

				if ((VoucherTitle.getText()).equals("My Vouchers")) {
					System.out.println("Successfully reloaded Vouchers page");
				} else {
					markTestStatus("failed", "FAILED: Did not reload Vouchers page", driver);
				}

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception b) {
				markTestStatus("failed", "FAILED: Did not redirect to Vouchers page.", driver);
			}

			try {
				// clicking of voucher use button
				AndroidElement VoucherUseAfterRedeemButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions
								.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
										+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
										+ "new UiSelector().text(\"USE NOW\"));")));

				VoucherUseAfterRedeemButton.click();

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Voucher Use Button not found", driver);
			}
		}

		try {
			// Checking if successfully redirected to checkout page
			AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			if ((CheckoutTitle.getText()).equals("Checkout")) {
				System.out.println("Successfully redirected to Checkout page");
			} else {
				markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
		}

		try {
			// Checking if Total updated after using voucher
			AndroidElement TotalElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_bottom_total")));

			if ((TotalElement.getText()).equals(SubTotalString)) {
				markTestStatus("failed", "FAILED: Subtotal did not change after applying voucher", driver);
			} else {
				System.out.println("Successfully applied voucher");
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Total Element error / not found.", driver);
		}
	}

	@Test(priority = 17)
	public void CheckoutSubtotalTest() throws Exception {
		try {
			// Checking if Total price is visible
			AndroidElement TotalElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("checkout_bottom_total")));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Total Element error / not found.", driver);
		}
	}

	@Test(priority = 20)
	public void CartCleanUpTest() throws Exception {
		// Test is performed for reusability of test

		// Checking if redirect to Cart page is successful
		try {

			driver.pressKey(new KeyEvent(AndroidKey.BACK));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// Checking if stock confirm appears
			try {
				AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));

				CartStock.click();
				TouchAction touchAction = new TouchAction(driver);
				touchAction.tap(PointOption.point(350, 300)).perform();
			} catch (Exception e) {
				System.out.println("Stock confirm on cart did not appear, no need to click");
			}

			AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String CartTitleText = CartTitle.getText();

			if (CartTitleText.equals("Cart")) {
				System.out.println("Successfully redirected back to Cart page");
			} else {
				markTestStatus("failed", "FAILED: Cart page title error / not found", driver);
				errorcounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
			errorcounter++;
		}

		try {
			// Checking of products if a product is seen (GLOBE AT HOME) in keywords
			List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

			// Checking all results
			for (int i = 0; i < Products.size(); i++) {
				AndroidElement SpecificProductName = Products.get(i);

				String SpecificProductNameText = SpecificProductName.getText();
				if (SpecificProductNameText.contains("GLOBE CALLCARD P500")) {
					swipeElementAndroid(SpecificProductName, Direction.LEFT);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

				}
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
			errorcounter++;
		}

		try {
			// Checking if Remove button is found after swiping left
			AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_remove")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
			errorcounter++;
		}

		try {
			// Clicking the remove button
			AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_remove")));

			RemoveButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
			errorcounter++;
		}

		try {
			// Clicking the Yes button in confirming the removal of product
			AndroidElement ConfirmRemoveButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

			ConfirmRemoveButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Confirm Remove button error / not found.", driver);
			errorcounter++;
		}

		if (errorcounter > 0) {
			markTestStatus("failed",
					"an error an occurred on Checkout > Cash on Delivery test, Please see logs for details", driver);
		} else {
			markTestStatus("passed", "Checkout > Cash on Delivery Module Successful", driver);
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
