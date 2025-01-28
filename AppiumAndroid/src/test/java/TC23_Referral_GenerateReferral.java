import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
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

public class TC23_Referral_GenerateReferral extends AndroidSetup {

   int errorcounter = 0;
   String specificVoucherName = "Automation Referral - STG";

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
   public void loginFieldsTest_Referral_GenerateReferral(String email, String password) throws Exception {

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

   @Test(priority = 3)
   private void accountPageTest() {
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

   @Test(priority = 4)
   private void referralPageTest() throws InterruptedException {
      // My Referral Button
      try {
         AndroidElement myReferralButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("account_referral")));
         myReferralButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED:  My referral button is not found.", driver);
         errorcounter++;
      }

      // My Referrals title page
      try {
         AndroidElement myReferralsTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         if (myReferralsTitle.getText().contains("Referrals")) {

         } else {
            markTestStatus("failed", "FAILED: My referral title page is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My referral title page is not found.", driver);
         errorcounter++;
      }
      int counter = 0;

      // Image List
      try {
         List<AndroidElement> imgList = driver.findElements(By.className("android.widget.ImageView"));
         Assert.assertTrue(imgList.size() != 0);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Image in referral page is not found.", driver);
         errorcounter++;
      }

      // Referral Title Header
      try {
         AndroidElement referallTitleHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("tvReferralTitle")));
         if (referallTitleHeader.getText().contains("Refer and Get Rewards!")) {

         } else {
            markTestStatus("failed", "FAILED: My referral title header is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My referral title header is not found.", driver);
         errorcounter++;
      }

      // Referral Description
      try {
         AndroidElement referralDescription = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("tvReferralSpiel")));
         if (referralDescription.getText().contains("Invite friends & family and earn discounts on every successful referral.")) {

         } else {
            markTestStatus("failed", "FAILED: My referral description is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My referral description is not found.", driver);
         errorcounter++;
      }

      // Referral Button
      // try {
      // AndroidElement referralButton = (AndroidElement) new WebDriverWait(driver,
      // 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
      // .id("referral_code_button")));
      // if (referralButton.getText().contains("Generate Referral Code")) {
      // referralButton.click();
      // } else {
      // markTestStatus("failed", "FAILED: My referral description is not correct.", driver);
      // errorcounter++;
      // }
      // } catch (Exception e) {
      // markTestStatus("failed", "FAILED: My referral description is not found.", driver);
      // errorcounter++;
      // }
      //
      // Thread.sleep(3000);

      // Referral Button Text
      try {
         AndroidElement referralText = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "referral_code")));
         System.out.println(referralText.getText().length());
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My referral description is not found.", driver);
         errorcounter++;
      }
      // How does it work
      try {
         AndroidElement howDoesItWorkLink = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("how_does_referral_work_label")));
         if (howDoesItWorkLink.getText().contains("How does referral work?")) {
            howDoesItWorkLink.click();
         } else {
            markTestStatus("failed", "FAILED: My referral (how does referral work) is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My referral (how does referral work) is not found.", driver);
         errorcounter++;
      }

      // Textfield List
      try {
         int txtCounter = 0;
         String trimMessage = "";
         List<AndroidElement> txtFieldList = driver.findElements(By.className("android.widget.TextView"));
         trimMessage = txtFieldList.get(1).getText();

         for (int i = 0; i < txtFieldList.size(); i++) {
            if (txtFieldList.get(i).getText().contains(trimMessage)) {
               txtCounter++;
            }

            if (txtFieldList.get(i).getText().equalsIgnoreCase("How it works?")) {
               txtCounter++;
            }
         }
         if (txtCounter < 2) {
            markTestStatus("failed", "FAILED: Text in how it work doesnt is not found.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Text in how it work doesnt is not found.", driver);
         errorcounter++;
      }

      // Okay Button
      try {
         AndroidElement okayButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "dialog_ok")));
         okayButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Okayl button is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 20)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Referral > Generate Referral, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Referral > Generate Referral is passed", driver);
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
