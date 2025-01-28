import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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

public class TC14_1_LoyaltyCard_Application_PERKS_iOS extends SetupIOS {

	int failedCounter = 0;

	@Test(priority = 0)
	public void LoginButtonTest() throws Exception {
		
		// Clicking of Login with email button on startup screen
	//	driver.switchTo().alert().accept();
	//	driver.switchTo().alert().dismiss();
		
		try {
			IOSElement btn_login = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Log in")));
			btn_login.click();
			Thread.sleep(5000);

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
	public void LoginFieldsTest_LoyaltyCardApplication_PERKS(String email, String password) throws Exception {

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

			Thread.sleep(8000);

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
	public void Navigate_to_LoyaltyCard_Application() throws Exception {

		// Checking of Account Button if visible
		try {
			IOSElement btn_account = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));
			btn_account.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Account Page.", driver);
			failedCounter++;
		}

		// Check if redirected to Account page
		try {
			IOSElement lbl_account = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));

			markTestStatus("passed", "PASSED: Redirected to Account Page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Account Page.", driver);
			failedCounter++;
		}

		// Checking of Loyalty card application if visible
		try {
			IOSElement btn_app = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Apply Membership")));
			btn_app.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Loyalty Card Application Page.", driver);
			failedCounter++;
		}

		// Check if redirected to Loyalty card application page
		try {
			IOSElement lbl_app = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Card Application")));

			markTestStatus("passed", "PASSED: Redirected to Card Application Page.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Card Application Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	public void Card_Application_Status() throws Exception {

		// Checking of View card application status Button if visible
		try {
			IOSElement btn_view = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("View my Card Application Status")));
			btn_view.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Card application status button not found.", driver);
			failedCounter++;
		}

		// Check if redirected to card application status page
		try {
			IOSElement lbl_cardStatus = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Card Application Status")));

			markTestStatus("passed", "PASSED: Redirected to Card Application Status Page.", driver);

			// Navigate back
			driver.navigate().back();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Card Application Status Page.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	public void Select_Card() throws Exception {

		// Checking of Apply for a Loyalty Card Button if visible
		try {
			IOSElement btn_apply = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Apply for a Loyalty Card")));
			btn_apply.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Select a Card Page.", driver);
			failedCounter++;
		}

		// Checking of Apply for a PERKS Card Button if visible
		try {
			IOSElement btn_TNAP = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Perks Card")));
			btn_TNAP.click();
			Thread.sleep(2000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not find PERKS Card.", driver);
			failedCounter++;
		}

		// Checking of Data Privacy Notice page if visible
		try {
			IOSElement btn_accept = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Accept Terms")));
			btn_accept.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Accept Terms button not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 6, dataProvider = "PERKS_Card", dataProviderClass = DataProvider_Login.class)
	public void Application_Form(String fname, String mname, String lname, String child_num, String tel_num,
			String mob_num, String email, String postal, String address, String company_name) throws Exception {

		// Check if redirected to PERKS Application form page
		try {

			// verify PERKS Application forms title
			IOSElement TNAP_Form_page = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Perks Application Form")));

			markTestStatus("passed", "Redirected to PERKS Application form page.", driver);
			Thread.sleep(2000);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to PERKS Application form page.", driver);
			failedCounter++;
		}

		// verify and input in first name field
		try {

			IOSElement field_fname = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_first_name")));
			field_fname.click();
			field_fname.clear();
			field_fname.sendKeys(fname);

			// swipe up in element
			swipeElementIOS(field_fname, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: First name field is not found", driver);
			failedCounter++;
		}

		// verify and input in middle name field
		try {

			IOSElement field_mname = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_middle_name_(optional)")));
			field_mname.click();
			field_mname.clear();
			field_mname.sendKeys(mname);

			// swipe up in element
			swipeElementIOS(field_mname, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Middle name field is not found", driver);
			failedCounter++;
		}

		// verify and input in last name field
		try {

			IOSElement field_lname = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_last_name")));
			field_lname.click();
			field_lname.clear();
			field_lname.sendKeys(lname);

			// swipe up in element
			swipeElementIOS(field_lname, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Last name field is not found", driver);
			failedCounter++;
		}

		// verify Birth date field
		try {

			IOSElement field_bdate = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_birthdate")));
			driver.setClipboardText("Jan 12, 2000");
			field_bdate.click();
			field_bdate.click();
			IOSElement btn_Paste = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Paste")));
			btn_Paste.click();

			// swipe up in element
			swipeElementIOS(field_bdate, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Birth date field is not found", driver);
			failedCounter++;
		}

		// verify Civil status field
		try {

			IOSElement field_civil = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_civil_status")));
			field_civil.click();
			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

			// swipe up in element
			swipeElementIOS(field_civil, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Civil Status field is not found", driver);
			failedCounter++;
		}

		// verify Gender field
		try {

			IOSElement field_gender = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_gender")));
			field_gender.click();
			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

			// swipe up in element
			swipeElementIOS(field_gender, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Gender field is not found", driver);
			failedCounter++;
		}

		// verify and input in Number of children field
		try {

			// IOSElement field_childNum = (IOSElement) new WebDriverWait(driver, 30)
			// .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_number_of_children_(optional)")));
			// field_childNum.click();
			// field_childNum.clear();
			// field_childNum.sendKeys(child_num);

			// swipe up in element
			// swipeElementIOS(field_childNum, Direction.UP);

		} catch (Exception e) {
			// markTestStatus("failed", "FAILED: Child Num field is not found", driver);
			// failedCounter ++;
		}

		// verify and input in Telephone Number field
		try {

			IOSElement field_tel_num = (IOSElement) new WebDriverWait(driver, 30).until(
					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_telephone_no._(home)")));
			field_tel_num.click();
			field_tel_num.clear();
			field_tel_num.sendKeys(tel_num);

			// swipe up in element
			swipeElementIOS(field_tel_num, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Telephone Number field is not found", driver);
			failedCounter++;
		}

		// verify and input in Mobile Number field
		try {

			IOSElement field_mob_num = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_mobile_no.")));
			field_mob_num.click();
			field_mob_num.clear();
			field_mob_num.sendKeys(mob_num);

			// swipe up in element
			swipeElementIOS(field_mob_num, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Mobile Number field is not found", driver);
			failedCounter++;
		}

		// verify and input in Email field
		try {

			IOSElement field_email = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_email_address")));
			field_email.click();
			field_email.clear();
			field_email.sendKeys(email);

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();
			// swipe up in element
			swipeElementIOS(field_email, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Address field is not found", driver);
			failedCounter++;
		}

		// verify upload picture Field
		try {

			// Select file button
			IOSElement btn_select_picture = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_select_picture")));
			btn_select_picture.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Upload Picture field is not found", driver);
			failedCounter++;
		}
		// verify upload modal
		try {
			IOSElement btn_upload = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Upload file from gallery")));
			btn_upload.click();
		} catch (Exception e1) {
			markTestStatus("failed", "FAILED: Profile Upload Modal not found", driver);
			failedCounter++;
		}
		// selecting first image
		try {
			IOSElement firstImage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(
							"/XCUIElementTypeApplication/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[1]")));
			firstImage.click();

		} catch (Exception e1) {
			markTestStatus("failed", "FAILED: Profile First Iamge not found", driver);
			failedCounter++;
		}

		// verify and input in Type of ID field
		try

		{
			scrollDown();
			IOSElement field_TypeID = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_id_type")));
			field_TypeID.click();

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

			for (int i = 0; i < 6; i++) {
				// swipe up in element
				swipeElementIOS(field_TypeID, Direction.UP);
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Type of ID field is not found", driver);
			failedCounter++;
		}

		// verify upload picture Field
		try {

			// Select file button
			IOSElement btn_select_picture = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_select_id")));
			btn_select_picture.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED:ID  Upload Picture field is not found", driver);
			failedCounter++;
		}
		// verify upload modal
		try {
			IOSElement btn_upload = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Upload file from gallery")));
			btn_upload.click();
		} catch (Exception e1) {
			markTestStatus("failed", "FAILED: ID Upload Picture field is not found", driver);
			failedCounter++;
		}
		// selecting first image
		try {
			
			List <IOSElement> imageList = driver.findElementsByClassName("XCUIElementTypeImage");
			System.out.println(imageList.size());
			
			IOSElement fifthImage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(
							"/XCUIElementTypeApplication/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[2]")));
			fifthImage.click();
		} catch (Exception e1) {
			markTestStatus("failed", "FAILED: ID Upload Picture field is not found", driver);
			failedCounter++;
		}

		// verify and input in Occupation field
		try {

			IOSElement field_occupation = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_occupation")));
			field_occupation.click();

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

			// swipe up in element
			swipeElementIOS(field_occupation, Direction.UP);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Occupation field is not found", driver);
			failedCounter++;
		}

		// verify and input in Province field
		try {

			scrollDown();
			IOSElement field_province = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_province")));
			field_province.click();

			// select first option
			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
			pickerWheel.get(0).setValue("Metro Manila");
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Province field is not found", driver);
			failedCounter++;
		}

		// verify and input in City field
		try {
			scrollDown();
			IOSElement field_city = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_city")));
			field_city.click();
			// select first option
			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
			pickerWheel.get(0).setValue("Makati City");
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: City field is not found", driver);
			failedCounter++;
		}

		// verify and input in Barangay field
		try {
			scrollDown();
			IOSElement field_barangay = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_barangay")));
			field_barangay.click();
			// select first option
			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
			pickerWheel.get(0).setValue("Bangkal");
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED:Barangay field is not found", driver);
			failedCounter++;
		}

		// verify and input in Postal Code field
		try {
			scrollDown();
			IOSElement field_postal = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_postal_code")));
			field_postal.click();
			field_postal.clear();
			field_postal.sendKeys(postal);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Postal Code field is not found", driver);
			failedCounter++;
		}

		// verify and input in Address field
		try {
			scrollDown();
			IOSElement field_address = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
					.visibilityOfElementLocated(MobileBy.name("textfield_house_number,_building_and_street_name")));
			field_address.click();
			field_address.clear();
			field_address.sendKeys(address);

			// select first option
			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
			btn_done.click();
			// Hide Keyboard
			driver.hideKeyboard();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Address field is not found", driver);
			failedCounter++;
		}

//		// Check Address Label
//		try {
//			scrollDown();
//			IOSElement lbl_address = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Address")));
//			lbl_address.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Address label not found.", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Company name field
//		try {
//
//			IOSElement field_company = (IOSElement) new WebDriverWait(driver, 30).until(
//					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_company_name_company")));
//			field_company.click();
//			field_company.clear();
//			field_company.sendKeys(company_name);
//
//			// swipe up in element
//			swipeElementIOS(field_company, Direction.UP);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Company Name field is not found", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Province field
//		try {
//
//			IOSElement field_province = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_province_company")));
//			field_province.click();
//			// select first option
//			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
//			pickerWheel.get(0).setValue("Metro Manila");
//			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
//			btn_done.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Company Province field is not found", driver);
//			failedCounter++;
//		}
//
//		// verify and input in City field
//		try {
//
//			IOSElement field_city = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_city_company")));
//			field_city.click();
//			// select first option
//						List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
//						pickerWheel.get(0).setValue("Makati City");
//						IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
//						btn_done.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Company City field is not found", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Barangay field
//		try {
//
//			IOSElement field_barangay = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_barangay_company")));
//			field_barangay.click();
//			// select first option
//			List<IOSElement> pickerWheel = driver.findElements(MobileBy.className("XCUIElementTypePickerWheel"));
//			pickerWheel.get(0).setValue("Bangkal");
//			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
//			btn_done.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Company Barangay field is not found", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Postal Code field
//		try {
//
//			IOSElement field_postal = (IOSElement) new WebDriverWait(driver, 30).until(
//					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_postal_code_company")));
//			field_postal.click();
//			field_postal.clear();
//			field_postal.sendKeys(postal);
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED:Company Postal Code field is not found", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Address field
//		try {
//			scrollDown();
//			IOSElement field_address = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(
//							MobileBy.name("textfield_house_number,_building_and_street_name_company")));
//			field_address.click();
//			field_address.clear();
//			field_address.sendKeys(address);
//
//			Thread.sleep(1000);
//			// Hide Keyboard
//			driver.hideKeyboard();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Company Address field is not found", driver);
//			failedCounter++;
//		}
//
//		// Check Company Label
//		try {
//			scrollDown();
//			IOSElement lbl_address = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Company")));
//			lbl_address.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Company label not found.", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Frequency visit field
//		try {
//			scrollDown();
//			IOSElement field_frequency = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions
//					.visibilityOfElementLocated(MobileBy.name("textfield_frequency_of_visit_in_puregold_per_month")));
//			field_frequency.click();
//			// select first option
//			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
//			btn_done.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Frequency visit  field is not found", driver);
//			failedCounter++;
//		}
//
//		// verify and input in Average monthly purchase field
//		try {
//			scrollDown();
//			IOSElement field_avg_purchase = (IOSElement) new WebDriverWait(driver, 30).until(
//					ExpectedConditions.visibilityOfElementLocated(MobileBy.name("textfield_average_monthly_purchase")));
//			field_avg_purchase.click();
//			// select first option
//			IOSElement btn_done = (IOSElement) driver.findElement(By.name("Done"));
//			btn_done.click();
//
//		} catch (Exception e) {
//			markTestStatus("failed", "FAILED: Average monthly purchase field is not found", driver);
//			failedCounter++;
//		}

		// Check Next Button if visible
		try {
			scrollDown();
			IOSElement btn_Next = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("btn_nxt")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Next button not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Loyalty Card Application for PERKS Card Module has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Loyalty Card Application for PERKS Card Module successful.", driver);
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

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
	}
}
