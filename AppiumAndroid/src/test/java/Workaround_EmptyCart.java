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

public class Workaround_EmptyCart extends AndroidSetup {
	String SubTotalString = "";
	String DateString1 = "";
	String TimeString1 = "";
	String PaymentMethodString = "";
	int errorcounter = 0;
	int productquantity = 0;
	
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
		  }
	  }
	  
	  @Test (priority = 1)
	  public void LoginFieldsTest() throws Exception{
		  
		  // Clicking and input of email on email field
		  try {
	      AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(
	              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));
	      EmailField.click();
	      EmailField.sendKeys("mnastor@yondu.com");
	      
	      System.out.println("Test passed: Email input success");
		  }
		  catch (Exception e) {
			  markTestStatus("failed", "FAILED: Email Field is not found.", driver);
		  }
		  
		  driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	  
		// Clicking and input of password on password field
			  try {
		      AndroidElement PassField = (AndroidElement) driver.findElement(By.id("login_password_field"));
		      PassField.click();
		      PassField.sendKeys("TestAccount123");
		      
		// Verifying of "Password" attribute to check if password is hidden
		      String PassAtt = PassField.getAttribute("Password");
		      if (PassAtt.equals("true"))
		      	{
		    	  System.out.println("Test passed: Password is hidden");
		      	}
		      else
		      	{
		    	  System.out.println("Test failed: Password is not hidden");
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
	    	  System.out.println("Test passed: Password is shown");
	      	}
	      else
	      	{
	    	  System.out.println("Test failed: Password is hidden");
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
	      }
	  }
	
	  @Test (priority = 3)
	  public void TapCartButtonTest() throws Exception{
		// Clicking the cart button
		  try {
			  AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30).until(
		              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("home_cart")));
			  
			  CartButton.click();
			  
			  Thread.sleep(5000);
			  
			  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			  
				// Checking if stock confirm appears
			  try {
				  AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30).until(
			              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("dialog_delivery_ok")));
				  
				  CartStock.click();
			  }
			  catch (Exception e) {
				  System.out.println("Stock confirm on cart did not appear, no need to click");
			  }
			  
				// Checking if redirect to Cart page is successful
			  try {
				  Thread.sleep(5000);
				  
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
			  
			  // Test is performed for reusability of test
			  
				  try {
						// Checking of products if a product is seen (GLOBE AT HOME) in keywords
						List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));
							  
						  productquantity = Products.size();
						  
						  // Checking all results
						  for (int i = 0; i < productquantity; i++)
						  {
							  AndroidElement SpecificProductName = Products.get(0);

								  swipeElementAndroid(SpecificProductName, Direction.LEFT);
								  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								  
								  try {
										// Checking if Remove button is found after swiping left
									  AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(
								              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("cart_remove")));	

								  }
								  catch (Exception e) {
											markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
											errorcounter++;
								  }
								  
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
										// Clicking the Yes button in confirming the removal of product
									  AndroidElement ConfirmRemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(
								              ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));	
									  
									  ConfirmRemoveButton.click();

									  driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
									  
									  Thread.sleep(5000);
								  }
								  catch (Exception e) {
											markTestStatus("failed", "FAILED: Confirm Remove button error / not found.", driver);
											errorcounter++;
								  }
						  }

				  }
				  catch (Exception e) {
							markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
							errorcounter++;
				  }
				  
				  
		  }
		  catch (Exception e) {
			  System.out.println("Workaround for emptying of cart not needed");
			  markTestStatus("passed", "Workaround for emptying of cart not needed", driver);
		  }
		  
		  if (errorcounter > 0)
		  {
			  markTestStatus("failed", "an error an occurred on Workaround for emptying of Cart, Please see logs for details", driver);
		  }
		  else
		  {
			  markTestStatus("passed", "Workaround for emptying of Cart Successful", driver);
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
