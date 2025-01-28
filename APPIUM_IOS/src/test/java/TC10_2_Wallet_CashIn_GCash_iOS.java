import java.time.Duration;
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

public class TC10_2_Wallet_CashIn_GCash_iOS extends SetupIOS {
	
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
			failedCounter ++;
		}
	}

	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	public void LoginFieldsTest_PWallet_Cashin(String email, String password) throws Exception {

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Clicking and input of email on email field
		try {
			IOSElement EmailField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Email")));
			EmailField.click();
			EmailField.sendKeys(email);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Email Field is not found.", driver);
			failedCounter ++;
		}

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		// Clicking and input of password on password field
		try {
			IOSElement PassField = (IOSElement) driver.findElement(MobileBy.AccessibilityId("Password"));
			PassField.click();
			PassField.sendKeys(password);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Password Field is not found.", driver);
			failedCounter ++;
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking of login button after the input of the above credentials
		try {
			IOSElement LoginButton = (IOSElement) driver.findElement(By.name("Login"));
			LoginButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter ++;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 2)
	public void DashboardTest() throws Exception {
		// Checking of P-Wallet Button if visible
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter ++;
		}
	}

	@Test(priority = 3)
	public void Navigate_to_PWallet() throws Exception {
		
		// Checking of P-Wallet Button if visible
		try {
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			btn_pwallet.click();
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to P-Wallet Page.", driver);
			failedCounter ++;
		}
		
		// Check if redirected to P-Wallet page
		try {
			IOSElement lbl_pWallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("P-Wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Redirected to P-Wallet Page.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to P-Wallet Page.", driver);
			failedCounter ++;
		}

	}
	
	@Test(priority = 4)
	public void CashIn_GCash() throws Exception {
		
		//Click Cash In button
		try {
			IOSElement btn_cashIn = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("CASH IN")));
			btn_cashIn.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Cash In Button found.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Cash In Button not found.", driver);
			failedCounter ++;
		}
		
		// Check if redirected to Cash In page
		try {
			IOSElement lbl_cashIn = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cash In")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Redirected to Cash In Page.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Cash In Page.", driver);
			failedCounter ++;
		}
		
		//Check if P100 button is displayed
		try {
			IOSElement btn_100 = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("100")));
			btn_100.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: P100 Button found.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: P100 Button not found.", driver);
			failedCounter ++;
		}
		
		//Check if Input amount textfield is displayed
		try {
			// get all elements with the element class XCUIElementTypeTextField
			List<IOSElement> list_textfield = driver.findElementsByClassName("XCUIElementTypeTextField");
			
			// Get Amount textfield
			list_textfield.get(0).isDisplayed();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Input Amount textfield found.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Input Amount textfield not found.", driver);
			failedCounter ++;
		}
		
		//Check if Payment Method button is displayed
		try {
			// get all elements with the element class XCUIElementTypeButton
			List<IOSElement> list_btn = driver.findElementsByClassName("XCUIElementTypeButton");
			
			// Select Payment Method Button Date
			list_btn.get(8).click();
			list_btn.get(8).getText();
			//IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
			//		.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Select Payment Method")));
			//btn_paymentMethod.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Select Payment Method Button found.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Select Payment Method Button not found.", driver);
			failedCounter ++;
		}
		
		// Check if redirected to Payment Method page
		try {
			IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Payment Method")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Redirected to Payment Method Page.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirect to Payment Method Page.", driver);
			failedCounter ++;
		}
		
		//Check if GCash Direct button is displayed
		try {
			IOSElement btn_gcash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GCash Direct")));
			btn_gcash.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: GCash Button found.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: GCash Button not found.", driver);
			failedCounter ++;
		}
		
		// Check if redirected to Cash In page
		try {
			IOSElement lbl_cashIn = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cash In")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Redirected to Cash In Page.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to Cash In Page.", driver);
			failedCounter ++;
		}
		
/*		//Check if GCash button is displayed
		try {
			IOSElement btn_gcash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("GCash Direct")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Change payment method to GCash passed.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Change payment method to GCash failed.", driver);
			failedCounter ++;
		}
*/
		
		//Check if Confirm button is displayed
		try {
			IOSElement btn_confirm = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Confirm")));
			btn_confirm.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Confirm Button found.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Confirm Button not found.", driver);
			failedCounter ++;
		}

	}
	
	@Test (priority = 5)
	public void GCash_page() throws Exception{
		// Check if redirected to GCash Payment page
		try {
			IOSElement lbl_Gcash = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Login to pay with GCash")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			markTestStatus("passed", "PASSED: Successfully Redirected to GCash Payment Page.", driver);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Did not redirected to GCash Payment Page.", driver);
			failedCounter ++;
		}
	}
	  
	@Test (priority = 6)
	public void CheckFailedCount() throws Exception{
		
		if(failedCounter != 0) {
			markTestStatus("failed", "FAILED: Wallet > GCash Cash In Module has error/s.", driver);
		} else {
			markTestStatus("passed","PASSED: Wallet > GCash Cash In Module successful.", driver);
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
