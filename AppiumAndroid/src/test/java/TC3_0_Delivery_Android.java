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

public class TC3_0_Delivery_Android extends AndroidSetup {

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
	public void LoginFieldsTest_Delivery(String email, String password) throws Exception {

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
	public void TapAddAddressTest() throws Exception {
		// Scrolling to and clicking the Delivery button
		try {

			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
							+ "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");

			AndroidElement DeliveryOptionButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_delivery_layout_image")));

			DeliveryOptionButton.click();

			AndroidElement ReminderModal = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_container")));

			// Clicking of "OK" Button on reminder
			try {
				AndroidElement ReminderModalAccept = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));
				ReminderModalAccept.click();

				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			} catch (Exception e) {
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
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			}

			// Clicking of Add address
			try {

				/*
				 * Clicking of Add address Button Text is used because element ID changes when
				 * there is an existing address already
				 */
				AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions
								.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
										+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
										+ "new UiSelector().text(\"Add Address\"));")));

				AddAddressButton.click();

				// Checking if "Add New Address" title is visible
				AndroidElement AddAddressTitle = (AndroidElement) new WebDriverWait(driver, 30)
						.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

				String AddAddressText = AddAddressTitle.getText();

				if (AddAddressText.equals("Add New Address")) {
					System.out.println("Test passed: Redirect to add address successful");
				} else {
					markTestStatus("failed", "FAILED: Add New Address title is not found.", driver);
				}
			} catch (Exception e) {
				markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// Checking if Reminder Modal from Delivery is visible to know if redirect is
			// successful

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delivery button is not found.", driver);
		}

	}

	@Test(priority = 4)
	public void AddressPopulateFieldsTest() throws Exception {

		// Checking of First Name Field
		try {
			AndroidElement FirstNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_first_name_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: First Name Field is not found / error.", driver);
		}

		// Checking of Last Name Field
		try {
			AndroidElement LastNameField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_last_name_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Last Name Field is not found / error.", driver);
		}

		// Checking and input of House Number, Building and Street Name Field
		try {
			AndroidElement HouseBuildingStreetField = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_street_field")));

			HouseBuildingStreetField.click();

			HouseBuildingStreetField.sendKeys("Test4");

			// Closing of on-screen keyboard
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: House Number, Building and Street Name Field is not found / error.",
					driver);
		}

		// Checking of Mobile Number Field
		try {
			AndroidElement MobileNoField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_mobile_number_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Mobile Number Field is not found / error.", driver);
		}
	}

	@Test(priority = 5)
	public void AddressProvinceTest() throws Exception {
		// Checking of Province Drop down
		try {
			AndroidElement ProvinceDrop = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_province_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province Drop down is not found / error.", driver);
		}
	}

	@Test(priority = 6)
	public void AddressCityTest() throws Exception {
		// Checking of City Drop down
		try {
			AndroidElement CityDrop = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_city_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: City Drop down is not found / error.", driver);
		}
	}

	@Test(priority = 7)
	public void AddressBarangayTest() throws Exception {
		// Checking of Barangay drop down
		try {
			AndroidElement BarangayDrop = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_barangay_field")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Barangay Drop down is not found / error.", driver);
		}
	}

	@Test(priority = 8)
	public void AddressPostalTest() throws Exception {
		// Checking and input of Postal Code Field
		try {
			AndroidElement PostalField = (AndroidElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_postal_code_field")));

			PostalField.click();

			PostalField.sendKeys("1200");

			// Closing of on-screen keyboard
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Postal Code Field is not found / error.", driver);
		}
	}

	@Test(priority = 9)
	public void AddressLabelTest() throws Exception {
		// Checking of Home Address Label
		try {
			AndroidElement HomeLabel = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_tag_home")));
			HomeLabel.click();
		} catch (Exception e) {
			System.out.println("FAILED: Home Label is not found / error.");
		}

		// Checking of Office Address Label
		try {
			AndroidElement OfficeLabel = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_tag_office")));
			OfficeLabel.click();
		} catch (Exception e) {
			System.out.println("FAILED: Office Label is not found / error.");
		}

		// Checking and clicking of Other Address Label
		try {
			AndroidElement OtherLabel = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_tag_other")));
			OtherLabel.click();
		} catch (Exception e) {
			System.out.println("FAILED: Other Label is not found / error.");
		}
	}

	@Test(priority = 10)
	public void SaveAddressTest() throws Exception {

		// Clicking of Save Address Button
		try {
			AndroidElement SaveAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.id("address_book_new_save_address")));

			SaveAddressButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found / error.", driver);
		}

		try {
			// Checking if "Add Address" Button is visible
			AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Add Address\"));")));

			markTestStatus("passed", "Successfully added new address", driver);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Add Address button is not found.", driver);
		}
	}

	@Test(priority = 11)
	public void EditAddressTest() throws Exception {
		// Getting all Edit buttons (Edit buttons have no difference on identifiers
		// between each addresses)
		try {
			List<AndroidElement> EditButtons = driver.findElements(By.id("item_address_book_edit"));

			// selecting the last address' edit button
			for (int i = 0; i < EditButtons.size(); i++) {
				if (i == (EditButtons.size() - 1)) {
					MobileElement SpecificEdit = EditButtons.get(i);
					SpecificEdit.click();
					driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					break;
				}
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: No Edit buttons found", driver);
		}

		// Checking if redirected to Edit Address page
		try {
			// Checking if "Edit Address" title is visible
			AndroidElement EditAddressTitle = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));

			String EditAddressText = EditAddressTitle.getText();

			if (EditAddressText.equals("Edit Address")) {
				System.out.println("Test passed: Redirect to add address successful");
				markTestStatus("passed", "Delivery > Edit Address Successful.", driver);
			} else {
				markTestStatus("failed", "FAILED: Edit Address title is not found.", driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Edit Address button is not found.", driver);
		}
	}

	@Test(priority = 12)
	public void SwipeLeftTest() throws Exception {
		// Clicking of Save Address Button to go back to select address page
		try {
			AndroidElement SaveAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_new_save_address")));

			SaveAddressButton.click();

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found / error.", driver);
		}

		try {
			// Checking if "Add Address" Button is visible
			AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Add Address\"));")));
		} catch (Exception e) {
			markTestStatus("failed",
					"FAILED: Add Address button is not found. Did not redirect to Select address screen", driver);
		}

		// Finding the newly created address and swiping left on it
		try {
			// Listing all addresses and finding the newly added address
			List<AndroidElement> AddressList = driver.findElements(By.id("item_address_book_address"));

			for (int i = 0; i < AddressList.size(); i++) {
				AndroidElement SpecificAddress = AddressList.get(i);

				String AddressText = SpecificAddress.getText();

				if (AddressText.contains("Test4")) {
					swipeElementAndroid(SpecificAddress, Direction.LEFT);

					break;
				}

			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Newly added address is not found / error.", driver);
		}

		// Checking of Delete button if visible
		try {
			AndroidElement DeleteAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_remove")));

			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove button is not found / error.", driver);
		}

	}

	@Test(priority = 13)
	public void TapRemoveTest() throws Exception {
		// Clicking of Remove Button from address
		try {
			AndroidElement RemoveAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_remove")));

			RemoveAddressButton.click();

			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove Address button is not found / error.", driver);
		}

		// Checking the remove confirmation modal has appeared
		try {
			AndroidElement NoButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bNegative")));

			AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

			System.out.println("Test passed: Delete Address modal appeared");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delete Address Modal is not found / error.", driver);
		}

	}

	@Test(priority = 14)
	public void RemoveCancelTest() throws Exception {
		// Clicking of Cancel Remove Button from address
		try {
			AndroidElement RemoveAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bNegative")));

			RemoveAddressButton.click();

			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cancel Remove button is not found / error.", driver);
		}

		try {
			// Checking if "Add Address" Button is visible
			AndroidElement AddAddressButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Add Address\"));")));

		} catch (Exception e) {
			markTestStatus("failed",
					"FAILED: Add Address button is not found. Did not redirect to Select address screen", driver);
		}

	}

	@Test(priority = 15)
	public void RemoveConfirmTest() throws Exception {

		// Finding the newly created address and swiping left on it
		try {
			// Listing all addresses and finding the newly added address
			List<AndroidElement> AddressList = driver.findElements(By.id("item_address_book_address"));

			for (int i = 0; i < AddressList.size(); i++) {
				AndroidElement SpecificAddress = AddressList.get(i);

				String AddressText = SpecificAddress.getText();

				if (AddressText.contains("Test4")) {
					swipeElementAndroid(SpecificAddress, Direction.LEFT);

					break;
				}

			}

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Newly added address is not found / error.", driver);
		}

		// Checking of Delete button if visible
		try {
			AndroidElement DeleteAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_remove")));

			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Remove button is not found / error.", driver);
		}

		// Clicking of Remove Button from address
		try {
			AndroidElement RemoveAddressButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("address_book_remove")));

			RemoveAddressButton.click();

			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found / error.", driver);
		}

		// Checking the remove confirmation modal has appeared
		try {
			AndroidElement NoButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bNegative")));

			AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

			System.out.println("Test passed: Delete Address modal appeared");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Delete Address Modal is not found / error.", driver);
		}

		// Clicking of YES Button from Remove address confirmation modal
		try {
			AndroidElement RemoveConfirmButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

			RemoveConfirmButton.click();

			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Save Address button is not found / error.", driver);
		}

		// Check address if remove has been completed
		// Finding the newly created address and swiping left on it
		try {
			// Listing all addresses and finding the newly added address
			List<AndroidElement> AddressList = driver.findElements(By.id("item_address_book_address"));

			for (int i = 0; i < AddressList.size(); i++) {
				AndroidElement SpecificAddress = AddressList.get(i);

				String AddressText = SpecificAddress.getText();

				if (AddressText.contains("Test4")) {
					markTestStatus("failed", "FAILED: New Address can still be found", driver);

					break;
				}

			}
		} catch (Exception e) {
			markTestStatus("passed", "Newly added address has been removed successfully", driver);
		}

	}

	@Test(priority = 16)
	public void DeliverySelectAddressTest() throws Exception {
		// Find and click address located in Makati
		try {
			AndroidElement MakatiAddress = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Test2, Poblacion, Makati City, Metro Manila, 1313\"));")));

			MakatiAddress.click();

			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Makati Address cannot be found/error", driver);
		}

		// Checking of Store list if redirected successfully
		try {
			// Checking if Store search bar is visible after clicking the address to know if
			// redirect is successful
			AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to store selection screen correctly.", driver);
		}

	}

	@Test(priority = 17)
	public void DeliveryStoreListTest() throws Exception {
		// Checking of Puregold branches nearby that should show on the list (Makati &
		// Express Delivery Area)
		try {
			AndroidElement PuregoldMakatiBranch = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions
							.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
									+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
									+ "new UiSelector().text(\"PUREGOLD MAKATI\"));")));

			AndroidElement PuregoldPoblacion = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Puregold Poblacion\"));")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Store Branches nearby (Puregold Makati and Poblacion) is not visible.",
					driver);
		}

	}

	@Test(priority = 18)
	public void DeliverySearchStoreTest() throws Exception {

		// Searching for Makati on search bar
		try {
			// Checking if Store search bar is visible then input of search item
			AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));

			StoreSearchBar.click();

			StoreSearchBar.sendKeys("Puregold Poblacion");

			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Store search bar could not be found.", driver);
		}

		// Checking of On-screen keyboard if visible
		try {
			// Checking if On-screen keyboard appeared
			boolean isKeyboardShown = driver.isKeyboardShown();

			if (isKeyboardShown) {
				System.out.println("Test passed: On-screen keyboard displayed");
			} else {
				markTestStatus("failed", "FAILED: On-screen keyboard did not display", driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
		}

		// Checking of Puregold Poblacion branch on store list
		try {
			AndroidElement PuregoldPoblacion = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"Puregold Poblacion\"));")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: PUREGOLD Poblacion branch is not found / error.", driver);
		}

	}

	@Test(priority = 18)
	public void DeliveryStoreSelectTest() throws Exception {
		// Clicking the specific store search result (Puregold Poblacion)
		try {
			AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_name")));
			SpecificStoreButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Store(Puregold Poblacion) button error/not found.", driver);
		}

		// Clicking of YES on modal if it appears
		try {
			// Clicking of YES on change store location modal
			AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

			YesButton.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Change store modal did not appear");
		}

		try {
			// Checking if store text at top is visible and correct (OFFICE)
			AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_catalog_text_place")));
			String StoreHeaderString = StoreHeader.getText();

			if (StoreHeaderString.equals("OFFICE")) {
				System.out.println("Test passed: Specific store redirected to is correct");
				markTestStatus("passed", "Delivery > Tapping the desired/available store successful", driver);
			} else {
				markTestStatus("failed", "FAILED: Specific Store(Puregold Poblacion) did not redirect correctly.",
						driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Store(Puregold Poblacion) button error/not found.", driver);
		}

	}

	@Test(priority = 19)
	public void DeliveryModalNoTest() throws Exception {
		try {
			// Go back to select store
			driver.pressKey(new KeyEvent(AndroidKey.BACK));

			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

			// Checking and clearing of Store search bar from Delivery
			AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));

			StoreSearchBar.clear();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: In-store Search bar is not found.", driver);
		}

		// Clicking the different store
		try {
			AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"PUREGOLD MAKATI\"));")));
			SpecificStoreButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cannot find PUREGOLD MAKATI on store selection", driver);
		}

		try {
			// Clicking of NO option on modal
			AndroidElement NoButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bNegative")));

			NoButton.click();

			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

			// Checking if Store search bar from In-store pickup is visible to know if
			// redirect is successful
			AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not go back to store selection screen correctly.", driver);
		}

	}

	@Test(priority = 20)
	public void DeliveryModalYesTest() throws Exception {
		// Clicking the different store
		try {
			AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
							+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
							+ "new UiSelector().text(\"PUREGOLD MAKATI\"));")));
			SpecificStoreButton.click();

			// Clicking of YES on change store location modal
			AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

			YesButton.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			// Checking if store text at top is visible and correct (PUREGOLD PASIG)
			AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("toolbar_catalog_text_place")));
			String StoreHeaderString = StoreHeader.getText();

			if (StoreHeaderString.equals("OFFICE")) {
				System.out.println("Test passed: Specific store redirected to is correct");
				markTestStatus("passed", "Delivery Module successful", driver);
			} else {
				markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) button error/not found.", driver);
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
