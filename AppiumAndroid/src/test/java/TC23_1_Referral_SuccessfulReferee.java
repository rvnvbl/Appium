import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import util.ExcelDataSupllier;

public class TC23_1_Referral_SuccessfulReferee extends AndroidSetup {

   int errorcounter = 0;
   String specificVoucherName = "Pre-deployment Referral Test @^_^@";

   @Test(priority = 0)
   public void LoginPageTest() throws Exception {
      try {
         // Clicking of Login with email button on startup screen
         AndroidElement EmailLoginButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "landing_login")));
         EmailLoginButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Checking of fields if redirect to login with email page is successful
         AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "login_email_field")));

         AndroidElement PasswordField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("login_password_field")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void loginFieldsTest_Referral_SuccessfulReferee(String email, String password) throws Exception {

      // Clicking and input of email on email field
      try {
         AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "login_email_field")));
         EmailField.click();
         // -----Edit
         EmailField.sendKeys(email);
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

         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 3, enabled = true)
   private void reeferalPopUpTest() {
      WebDriverWait wait = new WebDriverWait(driver, 30);

      // Pop up description
      try {
         AndroidElement popUpDescription = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("referral_reward_message")));
         if (popUpDescription.getText().contains("Reward was sent to you for a successful referral.")) {
            System.out.println("Popup Description is Correct");
         } else {
            markTestStatus("failed", "FAILED: Pop up description is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pop up description is not found.", driver);
         errorcounter++;
      }

      // Pop up okay button
      try {
         AndroidElement popUpOkaybutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("dialog_ok")));
         popUpOkaybutton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pop up button is not found.", driver);
         errorcounter++;
      }

      // My Voucher title page
      try {
         AndroidElement myVouchersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         if (myVouchersTitle.getText().contains("My Vouchers")) {

         } else {
            markTestStatus("failed", "FAILED: My vouchers title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My voucher title page is not found.", driver);
         errorcounter++;
      }
      driver.navigate().back();
   }

   @Test(priority = 4)
   private void accountPageTest() {
      // Account Button in Homepage
      try {
         AndroidElement myAccountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Account, New notification")));
         myAccountButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My account button is not found.", driver);
         errorcounter++;
      }

      // My account title page
      try {
         AndroidElement myAccountTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         if (myAccountTitle.getText().contains("Account")) {

         } else {
            markTestStatus("failed", "FAILED: My account title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Account title page is not found.", driver);
         errorcounter++;
      }

      // New icon in my voucher field
      try {
         AndroidElement newVoucherNotif = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("account_my_vouchers_new")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: New icon in my voucher is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement myAccountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Account, New notification")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  Red icon in my account is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 5)
   private void voucherPageTest() {
      // My Voucher Button
      try {
         AndroidElement myVoucherButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("account_my_vouchers")));
         myVoucherButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  My voucher button is not found.", driver);
         errorcounter++;
      }

      // My Voucher title page
      try {
         AndroidElement myVouchersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         if (myVouchersTitle.getText().contains("My Vouchers")) {

         } else {
            markTestStatus("failed", "FAILED: My vouchers title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My voucher title page is not found.", driver);
         errorcounter++;
      }
      int counter = 0;
      // Specific Voucher
      try {

         List<AndroidElement> voucherNameList = driver.findElements(By.id("item_voucher_name"));
         for (int i = 0; i < voucherNameList.size(); i++) {
            if (voucherNameList.get(i).getText().contains(specificVoucherName)) {
               System.out.println("The voucher is found");
               counter++;
            }

         }
         if (counter < 0) {
            markTestStatus("failed", "FAILED: Specific voucher is not found.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific voucher is not found.", driver);
         errorcounter++;
      }
      driver.navigate().back();

      // New icon must not show
      try {
         AndroidElement newVoucherNotif = (AndroidElement) new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("account_my_vouchers_new")));
      } catch (Exception e) {
         markTestStatus("passed", "PASSED: New icon in my voucher is not found.", driver);

      }

      // My account red dot must not visible
      try {
         AndroidElement myAccountButton = (AndroidElement) new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Account, New notification")));

      } catch (Exception e) {
         markTestStatus("passed", "PASSED:  Red icon in my account is not found.", driver);
      }
   }

   @Test(priority = 20)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Referral > Successful Referral, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Referral > Successful Referral is passed", driver);
      }
   }

   // This method accepts the status, reason and WebDriver instance and marks the
   // test on BrowserStack
   public static void markTestStatus(String status, String reason, WebDriver driver) {
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

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
               .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release().perform();
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
