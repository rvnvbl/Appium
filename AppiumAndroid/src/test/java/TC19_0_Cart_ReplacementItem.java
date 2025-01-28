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

public class TC19_0_Cart_ReplacementItem extends AndroidSetup {
	String productItem = "PURE BASICS CHICKEN SIOMAI 24S";
	String selectedStore = "MANLESS STORE";
	int productquantity = 0;
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
	public void LoginFieldsTest_CartReplacement_AdvancedSearch(String email, String password) throws Exception {

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
	public void TapCartButtonTest() throws Exception {
		// Clicking the cart button
		try {
			AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_cart")));

			CartButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {

			// Scrolling to and clicking the pick up in store button
			try {
				MobileElement PickUpOptionButton = (MobileElement) driver.findElementByAndroidUIAutomator(
						"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
								+ "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");
				PickUpOptionButton.click();

				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

				// Checking if Store search bar from In-store pickup is visible to know if
				// redirect is successful
				AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));

				markTestStatus("passed", "In Store Pick Up > Store List successful", driver);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: In-store Pickup button is not found.", driver);
				errorcounter++;
			}

			// Clicking the specific store
			try {
				AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions
								.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
										+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
										+ "new UiSelector().text(\"" + selectedStore + "\"));")));
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
											+ "new UiSelector().text(\"" + selectedStore + "\"));")));

					RedoSpecificStoreButton.click();

					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				} catch (Exception a) {
					System.out.println("Change branch modal did not appear, Work-around not needed");
				}

			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Specific Store(MANLESS STORE) button error/not found.", driver);
				errorcounter++;
			}

			// Checking of store
			try {
				// Checking if Specific store at top is visible and correct (MANLESS STORE)
				AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(
						ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_catalog_text_place")));
				String StoreHeaderString = StoreHeader.getText();

				if (StoreHeaderString.equals(selectedStore)) {
					System.out.println("Test passed: Specific store redirected to is correct");
				} else {
					markTestStatus("failed",
							"FAILED: Specific Store( " + selectedStore + ") did not redirect correctly.", driver);
				}
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Specific Store(" + selectedStore + ") did not redirect correctly.",
						driver);
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
				// Clicking of search field in search and searching for specific products (GLOBE
				// CALLCARD P500 prepaid wifi)
				AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_searchbar")));

				SearchField.click();

				SearchField.sendKeys(productItem);

				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

				TouchAction touchAction = new TouchAction(driver);
				touchAction.tap(PointOption.point(1000, 2100)).perform();

				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Search field error / not found.", driver);
				errorcounter++;
			}

			try {
				// Checking of products if all results has product name
				// in
				// keywords
				List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

				// Checking all results
				for (int i = 0; i < Products.size(); i++) {
					AndroidElement SpecificProductName = Products.get(i);

					String SpecificProductNameText = SpecificProductName.getText().replaceAll("\\s", "");
					System.out.println(SpecificProductName.getText());
					String productItemNoWhitespace = productItem.replaceAll("\\s", "");
					System.out.println(productItemNoWhitespace);

					if (SpecificProductNameText.contains(productItemNoWhitespace)) {
						System.out.println("Product contains " + productItem);
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

				if (StoreHeaderString.equals(selectedStore)) {
					System.out.println("Test passed: Specific store redirected to is correct");
				} else {
					markTestStatus("failed",
							"FAILED: Specific Store(" + selectedStore + ") did not redirect correctly.", driver);
					errorcounter++;
				}
			} catch (Exception a) {
				markTestStatus("failed", "FAILED: Specific Store(" + selectedStore + ") did not redirect correctly.",
						driver);
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
	public void CartIncreaseQuantityTest() throws Exception {
		// Getting the initial value of Sub-total
		try {
			AndroidElement CartSubtotalElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_subtotal_value")));

			SubTotalString = CartSubtotalElement.getText();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Sub-total element error / not found.", driver);
			errorcounter++;
		}

		// Clicking of increase quantity button
		try {
			AndroidElement IncreaseQuantityButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_quantity_add")));

			IncreaseQuantityButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Increase Quantity button error / not found.", driver);
			errorcounter++;
		}

		// Comparing the updated Sub-total if it has changed
		try {
			AndroidElement CartSubtotalElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_subtotal_value")));

			String UpdatedSubtotal = CartSubtotalElement.getText();
			Thread.sleep(3000);
			if (UpdatedSubtotal.equals(SubTotalString)) {
				markTestStatus("failed", "FAILED: Sub-total did not change.", driver);
				errorcounter++;
			} else {
				System.out.println("Sub-total changed after increasing quantity");
				SubTotalString = UpdatedSubtotal;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Sub-total element error / not found.", driver);
			errorcounter++;
		}

	}

	@Test(priority = 5)
	public void CartQuantityFieldTest() throws Exception {
		// Checking if Quantity field is visible and clicking it
		try {
			Thread.sleep(5000);

			AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_quantity")));

			QuantityField.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Quantity Field error / not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 6)
	public void CartQuantityFieldInputTest() throws Exception {
		// Clearing the Quantity field and input the set value
		try {
			AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_quantity")));

			QuantityField.clear();

			QuantityField.sendKeys("3");

			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Quantity Field error / not found.", driver);
			errorcounter++;
		}

		// Comparing the updated Sub-total if it has changed
		try {
			AndroidElement CartSubtotalElement = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_subtotal_value")));

			String UpdatedSubtotal = CartSubtotalElement.getText();

			if (UpdatedSubtotal.equals(SubTotalString)) {
				markTestStatus("failed", "FAILED: Sub-total did not change.", driver);
				errorcounter++;
			} else {
				System.out.println("Sub-total changed after increasing quantity");
				SubTotalString = UpdatedSubtotal;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Sub-total element error / not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 7)
	private void replacementItemTest() throws InterruptedException {
		// Verifying if replacement button is located ; Then click it
		try {
			AndroidElement replacementItemsButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_add_replacement")));
			replacementItemsButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Replacement item button not found.", driver);
			errorcounter++;
		}
		// Verifying if it successfully landed on the Add Replacement Item Page
		try {
			AndroidElement replacementItemPageTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			if (replacementItemPageTitle.getText().contains("Add Replacement Items")) {
				System.out.println("Succesfully Land on Replacement Items page");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Not land on Replacement Item Page.", driver);
			errorcounter++;
		}
		// Get all the select button of the replacement items available
		List<AndroidElement> productSelectButtons = driver.findElementsById("replacement_items_select");

		// Click the 4 items select button
		for (int i = 0; i < 4; i++) {
			Thread.sleep(2000);
			productSelectButtons.get(i).click();
		}

		// Verify if the warning pop up appear
		try {
			AndroidElement modalDescription = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("tvDescription")));

			if (modalDescription.getText().contains("Can only select up to 3 replacement items.")) {
				System.out.println("Pop up message displayed");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Can only select up to 3 replacement items. Not found.", driver);
			errorcounter++;
		}

		// Click okay button of the popup
		try {
			AndroidElement okayButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));
			okayButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Okay button not found.", driver);
			errorcounter++;
		}

		// Back to the cart page
		driver.navigate().back();

		// Checking if stock confirm appears
		try {
			AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));

			CartStock.click();
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

	@Test(priority = 8)
	private void searchFilterTest() throws InterruptedException {
		// Clicking the search icon on the cart page its located on the top right
		try {
			AndroidElement searchButtonCart = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("menu_search")));
			searchButtonCart.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Search button not found.", driver);
			errorcounter++;
		}

		// Look for the advanced search button and clicked it
		try {
			AndroidElement advanceSearchButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("search_label_empty_advanced_search")));
			advanceSearchButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Advance Search button not found.", driver);
			errorcounter++;
		}
		// Look for the first filter : Category Filter
		try {
			AndroidElement filterCategoryTest = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_category_value")));
			filterCategoryTest.click();
			filterCategoryTest.sendKeys("HOME");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter for category not found.", driver);
			errorcounter++;
		}
		// Look for the second filter : Brand Filter
		try {
			AndroidElement filterBrandTest = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_brand_value")));
			filterBrandTest.click();
			filterBrandTest.sendKeys("OREO");
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter for brand button not found.", driver);
			errorcounter++;
		}

		// Look for the Third Filter filter : Product name Filter
		try {
			AndroidElement filterProductTest = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_product_value")));
			filterProductTest.click();
			filterProductTest.sendKeys("OREO");
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter for product button not found.", driver);
			errorcounter++;
		}

		// Click the search button to go on searching
		try {
			AndroidElement filterSearchButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_filter_results_button")));
			filterSearchButton.click();
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter search button not found.", driver);
			errorcounter++;
		}

		// Looking if there are product exist based on the filter : The result must no
		// one product found based on filter
		try {
			AndroidElement noResultsFound = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("search_label_empty_no_match")));
			String NoResultText = noResultsFound.getText();

			if (NoResultText.equals("We can't find any item matching your search or the item is out of stock.")) {
				System.out.println("No Results Found!");
			} else {
				System.out.println("There are product shows!");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter search button not found.", driver);
			errorcounter++;
		}

		// Click the advanced search button
		try {
			AndroidElement advanceSearchButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("search_label_empty_advanced_search")));
			advanceSearchButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Advance Search button not found.", driver);
			errorcounter++;
		}

		// Look for the first filter : Category Filter
		try {
			AndroidElement filterCategoryTest = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_category_value")));
			filterCategoryTest.click();
			filterCategoryTest.sendKeys("FOOD");

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter for category not found.", driver);
			errorcounter++;
		}

		// Look for the second filter : Brand Filter
		try {
			AndroidElement filterBrandTest = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_brand_value")));
			filterBrandTest.click();
			filterBrandTest.sendKeys("OREO");
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter for brand button not found.", driver);
			errorcounter++;
		}

		// Look for the Third Filter filter : Product name Filter
		try {
			AndroidElement filterProductTest = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_product_value")));
			filterProductTest.click();
			filterProductTest.sendKeys("OREO");
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter for product button not found.", driver);
			errorcounter++;
		}

		// Click the search button, and proceed if there are product same as in the
		// filter : The result must found same item
		try {
			AndroidElement filterSearchButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("adv_search_filter_results_button")));
			filterSearchButton.click();
			driver.navigate().back();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter search button not found.", driver);
			errorcounter++;
		}

		// Click the add to cart of the first item that shows
		try {
			List<AndroidElement> itemResult = driver.findElementsById("search_item_add_to_cart_status_layout");
			itemResult.get(0).click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: First item not add to cart.", driver);
			errorcounter++;
		}

		// Clicking back button to cart
		try {
			AndroidElement backButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.className("android.widget.ImageButton")));
			backButton.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Filter search button not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 9)
	private void cartTest() throws InterruptedException {
		// Checking if stock confirm appears
		try {

			AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));

			CartStock.click();
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

		// Getting all the add replacement button and click the first one.
		try {
			List<AndroidElement> addReplacementListButton = driver.findElementsById("cart_add_replacement");
			addReplacementListButton.get(1).click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Replacement item button not found.", driver);
			errorcounter++;
		}

		// Verifying if successfully land on the Add Replacement Page
		try {
			AndroidElement replacementItemPageTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			if (replacementItemPageTitle.getText().contains("Add Replacement Items")) {
				System.out.println("Succesfully Land on Replacement Items page");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Not land on Replacement Item Page.", driver);
			errorcounter++;
		}

		// Getting all the select button of the availabale items
		List<AndroidElement> productSelectButtons = driver.findElementsById("replacement_items_select");

		// Click the 4 select button of the first four products
		for (int i = 0; i < 4; i++) {
			Thread.sleep(2000);
			productSelectButtons.get(i).click();
		}

		// Verify if the pop up message will show when clicking the fourth product
		try {
			AndroidElement modalDescription = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("tvDescription")));

			if (modalDescription.getText().contains("Can only select up to 3 replacement items.")) {
				System.out.println("Pop up message displayed");
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Can only select up to 3 replacement items. Not found.", driver);
			errorcounter++;
		}

		// Click the okay button of the modal
		try {
			AndroidElement okayButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));
			okayButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Modal: Okay button not found.", driver);
			errorcounter++;
		}

		// Back to the cart page
		driver.navigate().back();

		// Checking if stock confirm appears
		try {
			AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));

			CartStock.click();
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

	@Test(priority = 10)
	private void clearCart() {
		try {
			AndroidElement tapTotal = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_subtotal_value")));

			tapTotal.click();
		} catch (Exception e) {
			System.out.println("Stock confirm on cart did not appear, no need to click");
		}

		try {
			AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));

			CartStock.click();
		} catch (Exception e) {
			System.out.println("Stock confirm on cart did not appear, no need to click");
		}

		try {

			// Checking of products if a product is seen (GLOBE AT HOME) in keywords
			List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));
			productquantity = Products.size();

			// Checking all results
			for (int i = 0; i < productquantity; i++) {
				Products = driver.findElements(By.id("cart_item_name"));
				AndroidElement SpecificProductName = Products.get(0);
				System.out.println("The number of product is: " + productquantity);
				swipeElementAndroid(SpecificProductName, Direction.LEFT);

				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

					Thread.sleep(5000);
				} catch (Exception e) {
					markTestStatus("failed", "FAILED: Confirm Remove button error / not found.", driver);
					errorcounter++;
				}
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
			errorcounter++;
		}
	}

	@Test(priority = 11)
	private void errorChecker() {
		if (errorcounter > 0) {
			markTestStatus("failed",
					"Cart > Replacement Item > Advanced Search has an error, Please see logs for details", driver);
		} else {
			markTestStatus("passed", "Cart > Replacement Item > Advanced Search > Successful", driver);
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
