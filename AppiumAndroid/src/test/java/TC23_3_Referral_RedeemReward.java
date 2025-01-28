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

public class TC23_3_Referral_RedeemReward extends AndroidSetup {

   int errorcounter = 0;
   String specificVoucherName = "Pre-deployment Referral Test @^_^@";
   String pwalletID = "6104529967122689";

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
   public void loginFieldsTest_Referral_RedeemReward(String email, String password) throws Exception {

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

         // Checking if Puregold Image from homepage is visible to know if login is
         // successful
         // AndroidElement myProfileTitle = (AndroidElement) new WebDriverWait(driver,
         // 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
         // .id("title")));
         // if (myProfileTitle.getText().equalsIgnoreCase("My Profile")) {
         // System.out.println("My Profile Complete My Profile Shown");
         // }
         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 4)
   private void accoutPageTest() {
      // Account Button in Homepage
      try {
         AndroidElement myAccountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("nav_account")));
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

   }

   @Test(priority = 6)
   private void termsAndConditionTest() {
      // My Voucher terms and condition list
      List<AndroidElement> voucherTermsAndCondittionList = driver.findElements(By.id("item_voucher_terms"));
      voucherTermsAndCondittionList.get(voucherTermsAndCondittionList.size() - 1).click();

      // Voucher Detail Name
      try {
         AndroidElement myVoucherButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("voucher_detail_name")));
         myVoucherButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  My voucher name is not found.", driver);
         errorcounter++;
      }

      // Voucher Detail Name
      try {
         AndroidElement voucherDescription = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("voucher_detail_description")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  My voucher description is not found.", driver);
         errorcounter++;
      }

      // Voucher Detail Name
      try {
         AndroidElement voucherValidity = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("voucher_detail_validity")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  My voucher validity is not found.", driver);
         errorcounter++;
      }

      // Voucher Detail Name
      try {
         AndroidElement voucherTerms = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "voucher_detail_terms")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  My voucher terms is not found.", driver);
         errorcounter++;
      }

      driver.navigate().back();
   }

   @Test(priority = 7, enabled = true)
   private void redeemPopUpWrongPwalletTest() {

      // Looking for all top up button
      List<AndroidElement> topUpButtonList = driver.findElements(By.id("item_voucher_action"));
      for (int i = 0; i < topUpButtonList.size(); i++) {
         if (topUpButtonList.get(i).getText().contains("TOP UP")) {
            topUpButtonList.get(i).click();
            break;
         }
      }

      try {
         AndroidElement redemmPopUpTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("dialog_promo_voucher_pwallet_title")));
         if (redemmPopUpTitle.getText().contains("Redeem the " + specificVoucherName)) {

         } else {
            markTestStatus("failed", "FAILED: Redeem Popup title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup title page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup Textfield, Send wrong p-wallet ID
      try {
         AndroidElement redeemPopUpTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("dialog_promo_voucher_pwallet_input")));
         redeemPopUpTextField.sendKeys("6104529967122697");

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup textfield page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup button
      try {
         AndroidElement redemmPopUpButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "dialog_promo_voucher_pwallet_button")));
         redemmPopUpButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup button page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup title page
      try {
         AndroidElement redemmPopUpTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("dialog_promo_voucher_pwallet_invalid_title")));
         if (redemmPopUpTitle.getText().contains("Invalid P-Wallet Account Number")) {

         } else {
            markTestStatus("failed", "FAILED: Redeem Popup title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup title page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup Textfield, Send wrong p-wallet ID
      try {
         AndroidElement redemmPopUpTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("dialog_promo_voucher_pwallet_invalid_subtitle")));
         if (redemmPopUpTitle.getText().contains("Sorry, Entered P-Wallet Account Number is Incorrect.")) {

         } else {
            markTestStatus("failed", "FAILED: Redeem Popup subtitle page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup subtitle page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup button
      try {
         AndroidElement tryAgainButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "dialog_promo_voucher_pwallet_invalid_button")));
         tryAgainButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Try Again button page is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 8, enabled = true)
   private void redeemPopUpCorrectPwin() {

      // Looking for all top up button
      List<AndroidElement> topUpButtonList = driver.findElements(By.id("item_voucher_action"));
      for (int i = 0; i < topUpButtonList.size(); i++) {
         if (topUpButtonList.get(i).getText().contains("TOP UP")) {
            topUpButtonList.get(i).click();
            break;
         }
      }

      try {
         AndroidElement redemmPopUpTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("dialog_promo_voucher_pwallet_title")));
         if (redemmPopUpTitle.getText().contains("Redeem the " + specificVoucherName)) {

         } else {
            markTestStatus("failed", "FAILED: Redeem Popup title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup title page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup Textfield, Send wrong p-wallet ID
      try {
         AndroidElement redeemPopUpTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("dialog_promo_voucher_pwallet_input")));
         redeemPopUpTextField.sendKeys(pwalletID);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup textfield page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup button
      try {
         AndroidElement redemmPopUpButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "dialog_promo_voucher_pwallet_button")));
         redemmPopUpButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup button page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup Textfield, Send wrong p-wallet ID
      try {
         AndroidElement redeemPopUpDescription = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("dialog_promo_voucher_pwallet_redeem")));
         if (redeemPopUpDescription.getText().contains("You have successfully redeemed") && redeemPopUpDescription.getText().contains(pwalletID)
               && redeemPopUpDescription.getText().contains(specificVoucherName)) {

         } else {
            markTestStatus("failed", "FAILED: Redeem Popup subtitle page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Popup subtitle page is not found.", driver);
         errorcounter++;
      }

      // Redeem Popup button
      try {
         AndroidElement OKAYbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "dialog_promo_voucher_pwallet_redeem_okay_button")));
         OKAYbutton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Redeem Try Again button page is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 20)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Referral > Redeem Reward Voucher, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Referral > Redeem Reward Voucher is passed", driver);
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
