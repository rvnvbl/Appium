import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Iterator;
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

public class TC15_1_BuyLoad_Android_MobileData extends AndroidSetup {
   String SubTotalString = "";
   String DateString1 = "";
   String TimeString1 = "";
   String PaymentMethodString = "";
   String TempString = "";
   String TempString2 = "";
   String mobileDataCostString = "";
   int errorcounter = 0;

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

         System.out.println("Test 1: Redirect to Login page successful");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_Buyload_MobileData(String email, String password) throws Exception {

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

         System.out.println("Test 5 passed: Login is successful");
         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 3)
   public void TapBuyLoadTest() throws Exception {
      // Scrolling to and clicking the Buy Load button
      try {
         MobileElement BillsOptionButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_buy_load_layout_image\"));");
         BillsOptionButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Buy Load Button is not found.", driver);
         errorcounter++;
      }

      // Checking if "Buy Load" title is visible
      try {
         // Checking if "Buy Load" title is visible
         AndroidElement BillsPayTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         String BillsPayText = BillsPayTitle.getText();

         if (BillsPayText.equals("Buy Load")) {
            System.out.println("Test passed: Redirect to Buy Load successful");
         } else {
            markTestStatus("failed", "FAILED: Buy Load title is not found.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Buy Load title is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 5)
   public void TransactionsTest() throws Exception {
      // Clicking the Transaction History button of the product
      try {
         AndroidElement TransactionHistoryButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("menu_view_transactions")));
         TransactionHistoryButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Transaction History Button is not found.", driver);
         errorcounter++;
      }

      // Checking if "Transaction History" title is visible
      try {
         // Checking if "Transaction History" title is visible
         AndroidElement TransactionHistoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("title")));

         String TransactionHistoryText = TransactionHistoryTitle.getText();

         if (TransactionHistoryText.equals("Transaction History")) {
            System.out.println("Test passed: Redirect to Transaction History successful");

            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            // Checking if "Buy Load" title is visible
            try {
               // Checking if "Buy Load" title is visible
               AndroidElement BuyLoadTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                     MobileBy.id("title")));

               String BuyLoadTitleText = BuyLoadTitle.getText();

               if (BuyLoadTitleText.equals("Buy Load")) {
                  System.out.println("Test passed: Redirect to Buy Load successful");
               } else {
                  markTestStatus("failed", "FAILED: Buy Load title is wrong.", driver);
                  errorcounter++;
               }

               driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Buy Load title is not found.", driver);
               errorcounter++;
            }
         } else {
            markTestStatus("failed", "FAILED: Transaction History title is wrong.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Transaction History Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6)
   public void MobileDataTest() throws InterruptedException {
      try {
         List<AndroidElement> customTextTile = driver.findElements(By.id("buy_load_tab_custom_text_title"));
         System.out.println("The number of header is: " + customTextTile.size());

         String headerText = customTextTile.get(1).getText();

         if (headerText.equalsIgnoreCase("Mobile Data")) {
            System.out.println("Mobile Data Header is shown!");
            customTextTile.get(1).click();
         } else {
            System.out.println("Mobile Data Header is not shown!");
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Mobile Data Header Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 7)
   public void MobileNoTest() throws Exception {
      // Clicking the Mobile Number Field
      try {
         AndroidElement MobileField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "buy_load_mobile_number")));

         MobileField.click();

         MobileField.clear();

         // MobileField.sendKeys("9161111111");
         MobileField.sendKeys("9222222222");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Mobile Number Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 8)
   public void selectingPromo() {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      try {
         AndroidElement allinSurf30 = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Go90 8GB Data All\").instance(0))")));

         allinSurf30.click();
         System.out.println("GOBOOST15 promo click!");

         AndroidElement easySURF99 = (AndroidElement) new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.grocery.puregold:id/promo_name\")).scrollIntoView("
                     + "new UiSelector().text(\"Go120 10GB Data All\"));")));
         easySURF99.click();

         System.out.println("Go120 10GB Data Allpromo click!");

         AndroidElement mobiledataCost = (AndroidElement) new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("buy_load_bottom_amount")));

         mobileDataCostString = mobiledataCost.getText();
         assertTrue(mobileDataCostString.contains("₱120.00"), "The mobile cost is not equal to ₱120.00 ");
         System.out.println("The cost is: ₱120.00");

      } catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("Not Available for Non- Globe");
         AndroidElement MobileField = (AndroidElement) new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "buy_load_mobile_number")));

         MobileField.click();
         MobileField.clear();

         MobileField.sendKeys("9053380211");
         driver.hideKeyboard();
         AndroidElement go50promo;
         try {
            go50promo = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
                  "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Go140\").instance(0))")));

         } catch (Exception e1) {
            go50promo = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
                  "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Go120\").instance(0))")));
         }

         go50promo.click();

         System.out.println("go50 promo click!");

         AndroidElement go90promo;
         try {
            go90promo = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator(
                  "new UiScrollable(new UiSelector()" + ".resourceId(\"com.grocery.puregold:id/promo_name\")).scrollIntoView("
                        + "new UiSelector().text(\"Go120\"));")));
         } catch (Exception e1) {
            go90promo = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator(
                  "new UiScrollable(new UiSelector()" + ".resourceId(\"com.grocery.puregold:id/promo_name\")).scrollIntoView("
                        + "new UiSelector().text(\"Go140\"));")));
         }

         go90promo.click();
         AndroidElement mobiledataCost = (AndroidElement) new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("buy_load_bottom_amount")));

         mobileDataCostString = mobiledataCost.getText();
         assertTrue(mobileDataCostString.contains("₱120.00"), "The mobile cost is not equal to ₱120.00 ");
      }

   }

   @Test(priority = 9)
   public void NextButtonTest() throws Exception {
      // Checking and Clicking the Next Button
      try {
         AndroidElement NextButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "buy_load_next")));

         // Checking if Next button is now enabled
         if (NextButton.isEnabled()) {
            NextButton.click();
         } else {
            markTestStatus("failed", "FAILED: Next Button is not enabled.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Next Button is not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to Payment page
         AndroidElement PaymentTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         if ((PaymentTitle.getText()).equals("Payment")) {
            System.out.println("Successfully redirected to Payment page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Payment page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment page.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 10)
   public void PaymentMobileTest() throws Exception {
      // Mobile Field should have the same number
      try {
         AndroidElement MobileField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "buy_load_checkout_mobile_number")));

         if ((MobileField.getText()).contains(TempString2)) {
            System.out.println("Mobile No in Mobile field is correct");
         } else {
            markTestStatus("failed", "FAILED: Mobile Number is incorrect.", driver);
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Mobile Number Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 11)
   public void PaymentChevronTest() throws Exception {
      try {
         // Clicking the Chevron button on Payment method
         AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("buy_load_checkout_payment_arrow")));

         PaymentMethodChevronButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to page
         AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((ScheduleTitle.getText()).equals("Payment Method")) {
            System.out.println("Successfully redirected to Payment Method page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 12)
   public void GCashMethodTest() throws Exception {
      try {
         // Clicking the GCash payment option
         AndroidElement GCashOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"GCash Direct\"));")));

         GCashOption.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: GCash Payment method button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to Payment page
         AndroidElement PaymentTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         if ((PaymentTitle.getText()).equals("Payment")) {
            System.out.println("Successfully redirected to Payment page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Payment page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment page.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully changed payment method
         AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("buy_load_checkout_payment_info")));

         if ((PaymentMethodElement.getText()).equals("GCash Direct")) {
            System.out.println("Successfully changed payment method to Gcash");
         } else {
            markTestStatus("failed", "FAILED: changed payment method to Gcash failed", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 13)
   public void CreditDebitMethodTest() throws Exception {
      try {
         // Clicking the Chevron button on Payment method
         AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("buy_load_checkout_payment_arrow")));

         PaymentMethodChevronButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to page
         AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((ScheduleTitle.getText()).equals("Payment Method")) {
            System.out.println("Successfully redirected to Payment Method page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
         errorcounter++;
      }

      try {
         // Clicking the Credit / Debit card payment option
         AndroidElement CreditDebitOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"Credit/Debit\"));")));

         CreditDebitOption.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to Payment page
         AndroidElement PaymentTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         if ((PaymentTitle.getText()).equals("Payment")) {
            System.out.println("Successfully redirected to Payment page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Payment page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment page.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully changed payment method
         AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("buy_load_checkout_payment_info")));

         if ((PaymentMethodElement.getText()).equals("Credit/Debit")) {
            System.out.println("Successfully changed payment method to Credit / Debit Card");
         } else {
            markTestStatus("failed", "FAILED: changed payment method to Credit / Debit Card failed", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
         errorcounter++;
      }

      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Bills Pay > ecBills user test, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Bills Pay > ecBills Module Successful", driver);
      }
   }

   @Test(priority = 14)
   public void ProductSummaryTest() throws Exception {
      // Checking The products if correct
      try {
         AndroidElement SummaryField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "buy_load_checkout_purchase_summary")));

         if ((SummaryField.getText()).contains(TempString)) {
            System.out.println("Products in purchase summary is correct");
         } else {
            markTestStatus("failed", "FAILED: Products in purchase summary is incorrect.", driver);
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Purchase Summary is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 15)
   public void PayNowButtonTest() throws Exception {
      // Checking if Pay Now Button is enabled
      try {
         AndroidElement PayNowButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "buy_load_checkout_bottom_pay_now")));

         if (PayNowButton.isEnabled()) {
            System.out.println("Pay Now Button is Enabled");

         } else {
            markTestStatus("failed", "FAILED: Pay Now Button is Disabled.", driver);
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pay Now Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 16)
   public void checker() {
      if (errorcounter <= 0) {
         markTestStatus("passed", "Buy Load > Mobile Data > Mobile is Successful.", driver);
      } else {
         markTestStatus("failed", "Buy Load > Mobile Data > Mobile is Failed. See the logs for more details", driver);
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
