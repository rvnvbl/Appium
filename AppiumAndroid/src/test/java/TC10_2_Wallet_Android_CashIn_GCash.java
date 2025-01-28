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

public class TC10_2_Wallet_Android_CashIn_GCash extends AndroidSetup {
	String SubTotalString = "";
	String DateString1 = "";
	String TimeString1 = "";
	String PaymentMethodString = "";
	String ItemName = "";
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
			  
			  System.out.println("Test passed: Redirect to Login page successful");
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
			  errorcounter++;
		  }
	  }
	  
	 @Test (priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	  public void LoginFieldsTest_PWallet_Cashin(String email, String password ) throws Exception{
		  
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
	      
	      System.out.println("Test passed: Login is successful");
	      markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
	      }
	      catch (Exception e) {
	    	  markTestStatus("failed", "FAILED: Login Button is not found.", driver);
	    	  errorcounter++;
	      }
	  }
	
	  @Test (priority = 3)
	  public void PWalletCashInTest() throws Exception{
		// Clicking the PWallet button
		  try {
			  AndroidElement PWalletButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("menu_wallet")));
			  
			  PWalletButton.click();
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: P-Wallet button at homepage is not found.", driver);
			  errorcounter++;
		  }
		  
			// Checking if redirect to PWallet page is successful
		  try {
			  AndroidElement PWalletTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String PWalletTitleText = PWalletTitle.getText();
			  
			  if (PWalletTitleText.equals("P-Wallet")) {
				  System.out.println("Successfully redirected to P-Wallet page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: P-Wallet page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: P-Wallet page title is not found.", driver);
			  errorcounter++;
		  }
		  
			// Clicking the Cash In button
		  try {
			  AndroidElement CashInButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_home_header_cash_in")));
			  
			  CashInButton.click();
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Cash In Button is not found.", driver);
			  errorcounter++;
		  }
		  
			// Checking if redirect to PWallet Cash In page is successful
		  try {
			  AndroidElement PWalletCashInTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String PWalletCashInTitleText = PWalletCashInTitle.getText();
			  
			  if (PWalletCashInTitleText.equals("Cash In")) {
				  System.out.println("Successfully redirected to Cash In page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: Cash In page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Cash In page title is not found.", driver);
			  errorcounter++;
		  }
	  }
	  
	  @Test (priority = 4)
	  public void CashInAmountTest() throws Exception{
		// Clicking the 100 Peso Amount Tile option
		  try {
			  AndroidElement HundredPesoTile = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_cash_in_denomination_100")));
			  
			  HundredPesoTile.click();
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: 100 Peso tile is not found.", driver);
			  errorcounter++;
		  }
		  
			// Checking if Amount Text field changes to selected tile
		  try {
			  AndroidElement AmountTextField = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_cash_in_input_amount")));
			  
			  String AmountTextFieldText = AmountTextField.getText();
			  
			  if (AmountTextFieldText.contains("100")) {
				  System.out.println("Successfully changed amount text field to the chosen amount tile");
			  }
			  else {
				  markTestStatus("failed", "FAILED: amount text field did not change to the chosen amount tile", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: amount text field is not found.", driver);
			  errorcounter++;
		  }
		  
	  
		// Checking if Total amount at the bottom changes to the chosen amount tile
	  try {
		  AndroidElement TotalAmount = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_cash_in_bottom_amount")));
		  
		  String TotalAmountText = TotalAmount.getText();
		  
		  if (TotalAmountText.contains("100")) {
			  System.out.println("Successfully changed Total Amount to the chosen amount tile");
		  }
		  else {
			  markTestStatus("failed", "FAILED: Total Amount did not change to the chosen amount tile", driver);
			  errorcounter++;
		  }
	  }
	  catch (Exception e) {
		  markTestStatus("failed", "FAILED: Total Amount is not found.", driver);
		  errorcounter++;
	  }
		  
	  }
	  
	  @Test (priority = 5)
	  public void PaymentChevronTest() throws Exception{
		  try {
				// Clicking the Chevron button on Payment method
			  AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_cash_in_payment_arrow")));	
			  
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
	  
	  @Test (priority = 6)
	  public void GCashMethodTest() throws Exception{
		  try {
				// Clicking the GCash payment option
		      AndroidElement GCashOption = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
		      		        + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
		      		        + "new UiSelector().text(\"GCash\"));")));
			  
		      GCashOption.click();

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: GCash Payment method button error / not found.", driver);
					errorcounter++;
		  }
		  
			// Checking if redirect back to PWallet Cash In page is successful
		  try {
			  AndroidElement PWalletCashInTitle = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
			  
			  String PWalletCashInTitleText = PWalletCashInTitle.getText();
			  
			  if (PWalletCashInTitleText.equals("Cash In")) {
				  System.out.println("Successfully redirected to Cash In page");
			  }
			  else {
				  markTestStatus("failed", "FAILED: Cash In page title error / not found", driver);
				  errorcounter++;
			  }
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Cash In page title is not found.", driver);
			  errorcounter++;
		  }
		  
		  try {
				// Checking if successfully changed payment method
			  AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_cash_in_payment_info")));	
			  
			  if ((PaymentMethodElement.getText()).contains("GCash"))
			  	{
				  System.out.println("Successfully changed payment method to GCash");
				}
			  else
			  	{
				  markTestStatus("failed", "FAILED: change payment method to GCash failed", driver);
				  errorcounter++;
			  	}

			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
					markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
					errorcounter++;
		  }
	  }
	  
	  @Test (priority = 7)
	  public void GCashConfirmTest() throws Exception{
		// Clicking the Confirm cash in button
		  try {
			  AndroidElement ConfirmCashInButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("wallet_cash_in_confirm")));
			  
			  ConfirmCashInButton.click();
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Confirm cash in button is not found.", driver);
			  errorcounter++;
		  }
		  
		  
		  if (errorcounter > 0)
		  {
			  markTestStatus("failed", "an error an occurred on Wallet > Cash In > GCash test, Please see logs for details", driver);
		  }
		  else
		  {
			  markTestStatus("passed", "Wallet > Cash In > GCash Module Successful", driver);
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
