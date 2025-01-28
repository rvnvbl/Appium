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

public class TC11_1_DonationDrive_Android_RegularUser extends AndroidSetup {
	String SubTotalString = "";
	String DateString1 = "";
	String TimeString1 = "";
	String PaymentMethodString = "";
	int errorcounter = 0;
	
	@Test (priority = 0)
	  public void LoginPageTest() throws Exception{
		  try {
			// Clicking of Login with email button on startup screen
			  AndroidElement EmailLoginButton = (AndroidElement) new WebDriverWait(driver, 30).until(
					  ExpectedConditions.elementToBeClickable(MobileBy.id("landing_login")));
			  EmailLoginButton.click();
			  
			  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			  
			// Checking of fields if redirect to login with email page is successful
			  
			  AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(
					  ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));
			  
			  AndroidElement PasswordField = (AndroidElement) new WebDriverWait(driver, 30).until(
					  ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_password_field")));
			  
			  System.out.println("Test 1: Redirect to Login page successful");
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
			  errorcounter++;
		  }
	  }
	  
	 @Test (priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	  public void LoginFieldsTest_DonationDrive_RegUser(String email, String password ) throws Exception{
		  
		  // Clicking and input of email on email field
		  try {
	      AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));
	      EmailField.click();
	      //-----Edit
	      EmailField.sendKeys(email);
	      
	      System.out.println("Test 2 passed: Email input success");
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Email Field is not found.", driver);
		  }
		  
		  driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	  
		// Clicking and input of password on password field
			  try {
		      AndroidElement PassField = (AndroidElement) driver.findElement(By.id("login_password_field"));
		      PassField.click();
		      //-----Edit
		      PassField.sendKeys(password);
		      
		// Verifying of "Password" attribute to check if password is hidden
		      String PassAtt = PassField.getAttribute("Password");
		      if (PassAtt.equals("true"))
		      	{
		    	  System.out.println("Test 3 passed: Password is hidden");
		      	}
		      else
		      	{
		    	  System.out.println("Test 3 failed: Password is not hidden");
		    	  markTestStatus("failed", "FAILED: Password is not hidden.", driver);
		      	}
			  }
			  catch (Exception e) {
				  markTestStatus("failed", "FAILED: Password Field is not found.", driver);
			  }
			  
		      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking and input of password on password field
		  try {
	      AndroidElement ShowPass = (AndroidElement) driver.findElement(By.id("text_input_end_icon"));
	      ShowPass.click();
	      
	      // Verifying of "Password" attribute to check if password is hidden
	      String PassAtt = ShowPass.getAttribute("Password");
	      if (PassAtt.equals("false"))
	      	{
	    	  System.out.println("Test 4 passed: Password is shown");
	      	}
	      else
	      	{
	    	  System.out.println("Test 4 failed: Password is hidden");
	    	  markTestStatus("failed", "FAILED: Password Field is hidden.", driver);
	      	}
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Password Field is not found.", driver);
		  }
		  
	      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	  }
	  
	  @Test (priority = 2)
	  public void LoginButtonTest() throws Exception{
		// Clicking of login button after the input of the above credentials
	      try {
	      AndroidElement LoginButton = (AndroidElement) driver.findElement(By.id("login_next"));
	      LoginButton.click();
	   
	    // Checking if Puregold Image from homepage is visible to know if login is successful
		  AndroidElement PuregoldImageChecker = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("image")));
	      
	      System.out.println("Test 5 passed: Login is successful");
	      markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
	      }
	      catch (Exception e) {
	    	  markTestStatus("failed", "FAILED: Login Button is not found.", driver);
	    	  errorcounter++;
	      }
	      
	      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }
	
  @Test (priority = 3)
  public void TapDonationDriveTest() throws Exception{
	// Scrolling to and clicking the Donation Drive button
	  try {
      MobileElement DonationOptionButton = (MobileElement) driver
    		  .findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()" +
    				  ".scrollable(true)).scrollIntoView"
    				  + "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_donation_drive_layout_image\"));");
      DonationOptionButton.click();
      
      driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      
      // Checking if Donation drive image is visible to know if redirect is successful
	  AndroidElement DonationImage = (AndroidElement) new WebDriverWait(driver, 30).until(
              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_image")));
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Donation Drive button is not found.", driver);
		  errorcounter++;
	  }
	  
	// Checking if "Donation Drive" title is visible
	  try {		  
	      // Checking if "Donation Drive" title is visible
	      AndroidElement DonationDriveTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
	      
	      String DonationDriveText = DonationDriveTitle.getText();
	      
	      if (DonationDriveText.equals("Donation Drive"))
	      	{
	    	  System.out.println("Test passed: Redirect to Donation Drive successful");
	      	}
	      else
	      	{
	    	  markTestStatus("failed", "FAILED: Donation Drive title is not found.", driver);
	    	  errorcounter++;
	      	}
	      
	      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	      
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Donation Drive title is not found.", driver);
			  errorcounter++;
		  }
  }
  
  @Test (priority = 4)
  public void ViewAllTest() throws Exception{
		// Clicking the View all button of a category
	  try {
		  List<AndroidElement> ViewAllButtons = driver.findElements(By.id("catalog_category_view_all"));
		  AndroidElement FirstViewAllButton = ViewAllButtons.get(0);
		  
		  FirstViewAllButton.click();
		  
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Donate button/s error / not found.", driver);
		  errorcounter++;
	  }
	  
		// Checking if specific category "Donation Drive for" title is visible
	  try {		  
	      // Checking if "Donation Drive for" title is visible
	      AndroidElement DonationDriveCategoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
	      
	      String DonationDriveCategoryText = DonationDriveCategoryTitle.getText();
	      
	      if (DonationDriveCategoryText.contains("Donation Drive for"))
	      	{
	    	  System.out.println("Test passed: Redirect to specific Donation Drive Category successful");
	      	}
	      else
	      	{
	    	  markTestStatus("failed", "FAILED: Specific Donation Drive category title is not found.", driver);
	    	  errorcounter++;
	      	}
	      
	      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	      
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Specific Donation Drive category title is not found.", driver);
			  errorcounter++;
		  }
	  
		// Going back and Checking if redirect back to Donation Drive page is successful
	  try {
		  
		  driver.pressKey(new KeyEvent(AndroidKey.BACK));
		  
		  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  
		  AndroidElement DonationTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
		  
		  String DonationTitleText = DonationTitle.getText();
		  
		  if (DonationTitleText.equals("Donation Drive")) {
			  System.out.println("Successfully redirected back to Donation Drive page");
		  }
		  else {
			  markTestStatus("failed", "FAILED: Donation Drive page title error / not found", driver);
			  errorcounter++;
		  }
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Donation Drive page title is not found.", driver);
		  errorcounter++;
	  }
  }
  
  @Test (priority = 5)
  public void DonateItemTest() throws Exception{
		// Clicking the donate button of the product
	  try {
		  List<AndroidElement> DonateButtons = driver.findElements(By.id("donation_drive_donate"));
		  AndroidElement FirstDonateButton = DonateButtons.get(0);
		  
		  FirstDonateButton.click();
		  
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Donate button/s error / not found.", driver);
		  errorcounter++;
	  }
	  
	// Checking if Quantity, add and trash icon elements are visible instead of donate button
	  try {
		  AndroidElement MinusQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_minus")));
		  
		  AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_quantity")));
		  
		  AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_add")));
		  
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Trash icon, quantity and add elements not visible.", driver);
		  errorcounter++;
	  }
  }
  
  @Test (priority = 6)
  public void IncreaseQuantityTest() throws Exception{
	  
	// Clicking the add quantity button of the product and checking the quantity if it is now 2
	  try {
		  AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_add")));
		  
		  AddQuantityButton.click();
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Add Quantity button error / not found.", driver);
		  errorcounter++;
	  }
	  
		// Checking the quantity if it is now 2
	  try {			  
		  AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 39).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_quantity")));
		  
		  String QuantityText = QuantityField.getText();
		  
		  if (QuantityText.equals("2")) {
			  System.out.println("Quantity is now 2");
		  } else {
			  markTestStatus("failed", "FAILED: Quantity of product did not increase.", driver);
			  errorcounter++;
		  }
		  
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Quantity field error / not found.", driver);
		  errorcounter++;
	  }
  }
	  
	  @Test (priority = 7)
	  public void DecreaseQuantityTest() throws Exception{

		// Clicking the decrease quantity button of the product and checking the quantity if it is now 1
		  try {
			  AndroidElement DecQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_minus")));
			  
			  DecQuantityButton.click();
			  
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Decrease Quantity button error / not found.", driver);
			  errorcounter++;
		  }
		  
			// Checking the quantity if it is now 1
		  try {			  
			  AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_quantity")));
			  
			  String QuantityText = QuantityField.getText();
			  
			  if (QuantityText.equals("1")) {
				  System.out.println("Quantity is now 1");
			  } else {
				  markTestStatus("failed", "FAILED: Quantity of product did not decrease.", driver);
				  errorcounter++;
			  }
			  
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Quantity field error / not found.", driver);
			  errorcounter++;
		  }
		  
  }
	  
	  @Test (priority = 8)
	  public void TapCartButtonTest() throws Exception{
		// Clicking the cart button
		  try {
			  AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("nav_cart")));
			  
			  CartButton.click();
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			  Thread.sleep(5000);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Cart button at homepage is not found.", driver);
			  errorcounter++;
		  }
		  
			// Checking if redirect to Cart page is successful
		  try {
			  AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String CartTitleText = CartTitle.getText();
			  
			  if (CartTitleText.equals("Cart")) {
				  System.out.println("Successfully redirected to Cart page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: Cart page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
			  errorcounter++;
		  }
	  }
	  
	  @Test (priority = 9)
	  public void CartDonateCheckTest() throws Exception{
		// Checking if Quantity, add and trash icon elements are visible in cart page
		  try {
			  AndroidElement MinusQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_quantity_sub")));
			  
			  AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_quantity")));
			  
			  AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_quantity_add")));
			  
			  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Trash icon, quantity and add elements not visible.", driver);
			  errorcounter++;
		  }
	  }
	  
	  @Test (priority = 10)
	  public void CartCheckoutTest() throws Exception{
		  try {
				// Clicking the Checkout button
			  AndroidElement CheckoutButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_checkout")));	
			  
			  CheckoutButton.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Checkout button error / not found.", driver);
					errorcounter++;
		  }
		  
			// Checking if redirect to Checkout page is successful
		  try {
			  AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String CartTitleText = CartTitle.getText();
			  
			  if (CartTitleText.equals("Checkout")) {
				  System.out.println("Successfully redirected to Checkout page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: Checkout page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Checkout page title is not found.", driver);
			  errorcounter++;
		  }  
		  
	  }
	  
	  @Test (priority = 11)
	  public void PaymentChevronTest() throws Exception{
		  try {
				// Clicking the Chevron button on Payment method
			  AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_checkout_payment_arrow")));	
			  
			  PaymentMethodChevronButton.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if successfully redirected to page
			  AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));	
			  
			  if ((ScheduleTitle.getText()).equals("Payment Method"))
			  	{
				  System.out.println("Successfully redirected to Payment Method page");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
					errorcounter++;
		  }
	  }
	  
	  @Test (priority = 12)
	  public void GCashMethodTest() throws Exception{
		  try {
				// Clicking the GCash payment option
		      AndroidElement GCashOption = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
		      		        + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
		      		        + "new UiSelector().text(\"GCash Direct\"));")));
			  
		      GCashOption.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: GCash Payment method button error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if successfully redirected to checkout page
			  AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));	
			  
			  if ((CheckoutTitle.getText()).equals("Checkout"))
			  	{
				  System.out.println("Successfully redirected to Checkout page");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if successfully changed payment method
			  AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_checkout_payment_info")));	
			  
			  if ((PaymentMethodElement.getText()).equals("GCash Direct"))
			  	{
				  System.out.println("Successfully changed payment method to Gcash");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: changed payment method to Gcash failed", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
					errorcounter++;
		  }
	  }
	  
	  @Test (priority = 13)
	  public void CreditDebitMethodTest() throws Exception{
		  try {
				// Clicking the Chevron button on Payment method
			  AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_checkout_payment_arrow")));	
			  
			  PaymentMethodChevronButton.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if successfully redirected to page
			  AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));	
			  
			  if ((ScheduleTitle.getText()).equals("Payment Method"))
			  	{
				  System.out.println("Successfully redirected to Payment Method page");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Clicking the Credit / Debit card payment option
		      AndroidElement CreditDebitOption = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
		      		        + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
		      		        + "new UiSelector().text(\"Credit / Debit Card\"));")));
			  
		      CreditDebitOption.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if successfully redirected to checkout page
			  AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));	
			  
			  if ((CheckoutTitle.getText()).equals("Checkout"))
			  	{
				  System.out.println("Successfully redirected to Checkout page");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if successfully changed payment method
			  AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("donation_drive_checkout_payment_info")));	
			  
			  if ((PaymentMethodElement.getText()).equals("Credit / Debit Card"))
			  	{
				  System.out.println("Successfully changed payment method to Credit / Debit Card");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: changed payment method to Credit / Debit Card failed", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
					errorcounter++;
		  }
	  }
	  
	  @Test (priority = 14)
	  public void CartReturnTest() throws Exception{

			// Checking if redirect to Cart page is successful
		  try {
			  
			  driver.pressKey(new KeyEvent(AndroidKey.BACK));
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			  
			  AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String CartTitleText = CartTitle.getText();
			  
			  if (CartTitleText.equals("Cart")) {
				  System.out.println("Successfully redirected back to Cart page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: Cart page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
			  errorcounter++;
		  }
	  }
	  
	  @Test (priority = 15)
	  public void CartProductSwipeLeftTest() throws Exception{
		  try {
				// Checking of products if a product is seen (Donate) in keywords
				  List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));
					  
				  // Checking all results
				  for (int i = 0; i < Products.size(); i++)
				  {
					  AndroidElement SpecificProductName = Products.get(i);
					  
					  String SpecificProductNameText = SpecificProductName.getText();
					  if (SpecificProductNameText.contains("Donate"))
					  {
						  swipeElementAndroid(SpecificProductName, Direction.LEFT);
						  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
						  
					  }
				  }

		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking if Remove button is found after swiping left
			  AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_remove")));	

		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
					errorcounter++;
		  }
	  }
	  
	  @Test (priority = 16)
	  public void CartRemoveTest() throws Exception{
		  try {
				// Clicking the remove button
			  AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_remove")));	
			  
			  RemoveButton.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Clicking the yes button on remove confirmation
			  AndroidElement YesRemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));	
			  
			  YesRemoveButton.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			  Thread.sleep(7000);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
					errorcounter++;
		  }
		  
		  try {
				// Checking of products if a product is seen (Donate) in keywords
				  List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));
					  
				  // Checking all results
				  for (int i = 0; i < Products.size(); i++)
				  {
					  AndroidElement SpecificProductName = Products.get(i);
					  
					  String SpecificProductNameText = SpecificProductName.getText();
					  if (SpecificProductNameText.contains("Donate"))
					  {
						  markTestStatus("failed", "FAILED: Removed product can still be found in cart", driver);
						  errorcounter++;
					  }
				  }

		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
					errorcounter++;
		  }

	  }
	  
	  @Test (priority = 17)
	  public void DonationReturnTest() throws Exception{

			// Going back and Checking if redirect back to Donation Drive page is successful
		  try {
			  
			  driver.pressKey(new KeyEvent(AndroidKey.BACK));
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			  
			  AndroidElement DonationTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String DonationTitleText = DonationTitle.getText();
			  
			  if (DonationTitleText.equals("Donation Drive")) {
				  System.out.println("Successfully redirected back to Donation Drive page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: Donation Drive page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Donation Drive page title is not found.", driver);
			  errorcounter++;
		  }
	  }
	  
	  @Test (priority = 18)
	  public void TransactionHistoryTest() throws Exception{
		// Clicking of Transaction History Button
		  try {
		      AndroidElement TransacHistoryButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("menu_view_transactions")));
		      TransacHistoryButton.click();
	      
	      driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Transaction History button is not found.", driver);
			  errorcounter++;
		  }
		  
		// Checking if "Transaction History" title is visible
		  try {		  
		      // Checking if "Transaction History" title is visible
		      AndroidElement DonationDriveTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
		      
		      String DonationDriveText = DonationDriveTitle.getText();
		      
		      if (DonationDriveText.equals("Transaction History"))
		      	{
		    	  System.out.println("Test passed: Redirect to Transaction History successful");
		      	}
		      else
		      	{
		    	  markTestStatus("failed", "FAILED: Transaction History title is not found.", driver);
		    	  errorcounter++;
		      	}
		      
		      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		      
			  }
			  catch (Exception e) {
				  markTestStatus("failed", "FAILED: Transaction History title is not found.", driver);
				  errorcounter++;
			  }
		  if (errorcounter > 0)
		  {
			  markTestStatus("failed", "an error an occurred on Donation Drive > Regular user test, Please see logs for details", driver);
		  }
		  else
		  {
			  markTestStatus("passed", "Donation Drive > Regular user Module Successful", driver);
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
	    //  - Android: 300 ms
	    //  - iOS: 200 ms
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
	            pointOptionStart = PointOption.point(rect.x + rect.width / 2,
	                    rect.y + edgeBorder);
	            pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
	                    rect.y + rect.height - edgeBorder);
	            break;
	        case UP: // from down to up
	            pointOptionStart = PointOption.point(rect.x + rect.width / 2,
	                    rect.y + rect.height - edgeBorder);
	            pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
	                    rect.y + edgeBorder);
	            break;
	        case LEFT: // from right to left
	            pointOptionStart = PointOption.point(rect.x + rect.width - edgeBorder,
	                    rect.y + rect.height / 2);
	            pointOptionEnd = PointOption.point(rect.x + edgeBorder,
	                    rect.y + rect.height / 2);
	            break;
	        case RIGHT: // from left to right
	            pointOptionStart = PointOption.point(rect.x + edgeBorder,
	                    rect.y + rect.height / 2);
	            pointOptionEnd = PointOption.point(rect.x + rect.width - edgeBorder,
	                    rect.y + rect.height / 2);
	            break;
	        default:
	            throw new IllegalArgumentException("swipeElementAndroid(): dir: '" + dir + "' NOT supported");
	    }

	    // execute swipe using TouchAction
	    try {
	        new TouchAction(driver)
	                .press(pointOptionStart)
	                // a bit more reliable when we add small wait
	                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
	                .moveTo(pointOptionEnd)
	                .release().perform();
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
	    UP,
	    DOWN,
	    LEFT,
	    RIGHT;
	}

}
