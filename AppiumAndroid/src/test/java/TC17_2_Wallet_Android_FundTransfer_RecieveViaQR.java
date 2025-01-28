import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

public class TC17_2_Wallet_Android_FundTransfer_RecieveViaQR extends AndroidSetup {
   String SubTotalString = "";
   String DateString1 = "";
   String TimeString1 = "";
   String PaymentMethodString = "";
   String ItemName = "";
   int errorcounter = 0;
   String wrongPwalletPin = "111112";
   String correctPwalletPin = "111111";
   
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

         System.out.println("Test passed: Redirect to Login page successful");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_FundTransfer_RecieveViaQR(String email, String password) throws Exception {

      // Clicking and input of email on email field
      try {
         AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "login_email_field")));
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
         AndroidElement PuregoldImageChecker = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("image")));

         System.out.println("Test passed: Login is successful");
         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 3, enabled = true)
   private void pwalletCorrectPinTest() {
      // Clicking the PWallet button
      try {
         AndroidElement PWalletButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("menu_wallet")));

         PWalletButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: P-Wallet button at homepage is not found.", driver);
         errorcounter++;
      }

      // Verify the Pwallet Pin textfield
      // Send a correct pin
      try {
         AndroidElement pinWalletField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("wallet_enter_pin_code")));

         pinWalletField.clear();
         pinWalletField.sendKeys(correctPwalletPin);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Pin Field not found.", driver);
         errorcounter++;
      }

      // Submit button pwallet pin
      try {
         AndroidElement pwalletSubmitButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .id("wallet_enter_pin_submit")));
         try {
            Assert.assertTrue(pwalletSubmitButton.isEnabled());
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: The submit button is disable.", driver);
            errorcounter++;
         }
         pwalletSubmitButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet submit button not found.", driver);
         errorcounter++;
      }

      // Check if land on P-wallet page
      try {
         AndroidElement pwalletTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));
         try {
            Assert.assertTrue(pwalletTitle.getText().contains("P-Wallet"));
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: The title of Pwallet page is not same.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Title not found.", driver);
         errorcounter++;
      }

      // P-wallet home banner page
      try {
         AndroidElement pwalletBannerImage = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_home_banner_image")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet banner image not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 3, enabled = false)
   private void noPwalletPinField() {
      // Clicking the PWallet button
      try {
         AndroidElement PWalletButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("menu_wallet")));

         PWalletButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: P-Wallet button at homepage is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 4)
   public void FundTrasferTest() {
      try {
         AndroidElement sendPwalletButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_home_send_funds")));
         markTestStatus("passed", "PASSED: Send P-Wallet button is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Send P-Wallet button is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement recievedfQRButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_home_receive_via_qr")));
         markTestStatus("passed", "PASSED: Recieved QR button is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Recieved QR button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 5)
   public void recentTransactionTest() {
      try {
         AndroidElement transactionHistoryLabel = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_home_transaction_label")));
         markTestStatus("passed", "PASSED: Transaction History Label is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Transaction History Label is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement transactionHistoryList = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_home_transaction_list")));
         List<MobileElement> historyList = transactionHistoryList.findElementsByClassName("android.view.ViewGroup");
         markTestStatus("passed", "PASSED: Transaction History List is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Transaction History List is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6)
   public void viewAllTransactionHistoryTest() {
      try {
         AndroidElement viewAllLink = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "wallet_home_transaction_show_all")));
         viewAllLink.click();
         markTestStatus("passed", "PASSED: View All button is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: View All button is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement transactionHistoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("title")));
         String titleTransaction = transactionHistoryTitle.getText();
         if (titleTransaction.equals("Transaction History")) {
            System.out.println("Land on Transaction History Page");
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Not Land on Transaction History Page", driver);
         errorcounter++;
      }

      try {
         AndroidElement transactionHistoryList = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("transaction_history_list")));
         List<MobileElement> historyList = transactionHistoryList.findElementsByClassName("android.view.ViewGroup");
         System.out.println(historyList.size());
         driver.navigate().back();
         markTestStatus("passed", "PASSED: Transaction History List is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Transaction History List is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 7)
   public void recieveViaQRTest() {
      try {
         AndroidElement recieveQRButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("wallet_home_receive_via_qr")));
         recieveQRButton.click();
         markTestStatus("passed", "PASSED: Recieve via QR button clicked.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Recieve via QR button  is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement myPWalletQRTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         String PWalletTitleText = myPWalletQRTitle.getText();

         if (PWalletTitleText.equals("My P-Wallet QR")) {
            System.out.println("Successfully redirected to My P-Wallet QR page");
         } else {
            markTestStatus("failed", "FAILED: My P-Wallet QR page title error / not found", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My P-Wallet QR page title is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement QRCodeImage = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "receive_via_qr_image")));
         markTestStatus("passed", "PASSED: QR code image is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: QR code image is not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement QRPagePwalletID = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("receive_via_qr_pwallet_id")));
         markTestStatus("passed", "PASSED: P-Wallet ID is found.", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: P-Wallet ID is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 13)
   private void checkError() {
      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Wallet > Fund Transfer > Recieve via QR Module test, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Wallet > Fund Transfer > Recieve via QR sucessful", driver);
      }
   }

   // This method accepts the status, reason and WebDriver instance and marks the
   // test on BrowserStack
   public static void markTestStatus(String status, String reason, WebDriver driver) {
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

   /**
    * Performs swipe inside an element
    *
    * @param el the element to swipe
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
