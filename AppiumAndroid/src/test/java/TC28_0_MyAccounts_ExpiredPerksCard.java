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
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import net.bytebuddy.description.annotation.AnnotationList.Empty;
import util.ExcelDataSupllier;

public class TC28_0_MyAccounts_ExpiredPerksCard extends AndroidSetup {

   String firstName = "Test First";
   String middleName = "Test Middle";
   String lastName = "Test Last";
   String phoneNumber = "9053380217";

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

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldTest_ExpiredLoyaltyCard(String email, String password) throws Exception {

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

         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 3)
   public void navigateToMyAccount() throws Exception {
      // Clicking of login button after the input of the above credentials
      try {
         AndroidElement accountNavigation = (AndroidElement) driver.findElement(By.id("nav_account"));
         accountNavigation.click();

         // Checking if Puregold Image from homepage is visible to know if login is
         // successful

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;

      }

      AndroidElement accountHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
            "title")));
      String accountHeaderText = accountHeader.getText();

      if (accountHeaderText.equals("Account")) {

         markTestStatus("passed", "Succesfully land on My Account Page", driver);
      } else {
         System.out.println("ERROR: Not Succesfully land on My Account Page");
         markTestStatus("failed", "Succesfully land on My Account Page", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 4)
   private void navigateToLoyaltyCard() {
      // Clicking the loyalty card option
      try {
         AndroidElement loyaltycardOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "account_loyalty")));
         loyaltycardOption.click();
      } catch (Exception e) {

         markTestStatus("failed", "My loyalty card button not found", driver);
         errorcounter++;
      }

      // Getting the page title and check if it succesfully land on "Loyalty Card" page
      AndroidElement profileHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
            "title")));
      String profileHeaderText = profileHeader.getText();
      if (profileHeaderText.equals("Loyalty Card")) {

         markTestStatus("passed", "Succesfully land on  loyalty card page", driver);
      } else {

         markTestStatus("failed", "Not Succesfully land on loyalty card page", driver);
         errorcounter++;
      }
   }

   @Test(priority = 5)
   private void verificationLoyaltyCardPageTest() {
      // Check if the back button is shown
      try {
         AndroidElement backButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Navigate up")));
         Assert.assertTrue(backButton.isEnabled());

      } catch (Exception e) {
         markTestStatus("failed", "Back button not found", driver);
         errorcounter++;
      }

      // Check if the card image is shown
      try {
         AndroidElement cardImage = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "loyalty_display")));
         Assert.assertTrue(cardImage.isDisplayed());

      } catch (Exception e) {
         markTestStatus("failed", "Card image not found", driver);
         errorcounter++;
      }

      // Check if the card number is shown
      try {
         AndroidElement cardNumber = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "loyalty_display_card_number")));
         Assert.assertTrue(!cardNumber.equals(null));

      } catch (Exception e) {
         markTestStatus("failed", "Card number not found", driver);
         errorcounter++;
      }

      // Check if the card name is shown
      try {
         AndroidElement cardName = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "loyalty_display_card_holder_name")));
         Assert.assertTrue(!cardName.equals(null));

      } catch (Exception e) {
         markTestStatus("failed", "Card name not found", driver);
         errorcounter++;
      }

      // Check if the valid card label is shown
      try {
         AndroidElement validCardLabel = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("loyalty_display_card_valid_thru")));
         Assert.assertTrue(!validCardLabel.equals(null));

      } catch (Exception e) {
         markTestStatus("failed", "Valid card label not found", driver);
         errorcounter++;
      }

      // Check if the valid card date is shown
      try {
         AndroidElement validCardDate = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("loyalty_display_card_expiry")));
         Assert.assertTrue(!validCardDate.equals(null));

      } catch (Exception e) {
         markTestStatus("failed", "Valid card date not found", driver);
         errorcounter++;
      }

      // Check if the card status is shown
      try {
         AndroidElement cardPoints = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "loyalty_display_card_points")));
         Assert.assertTrue(!cardPoints.equals(null));

      } catch (Exception e) {
         markTestStatus("failed", "Card 'Expired' status not found", driver);
         errorcounter++;
      }

      // Check if the card renew button is shown
      try {
         AndroidElement cardRenewButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("loyalty_card_renew")));
         Assert.assertTrue(cardRenewButton.isEnabled());

      } catch (Exception e) {
         markTestStatus("failed", "Card renew button not found", driver);
         errorcounter++;
      }

      // Check if the card upload id button is shown
      try {
         AndroidElement cardUploadIdButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("loyalty_card_renew")));
         Assert.assertTrue(cardUploadIdButton.isEnabled());

      } catch (Exception e) {
         markTestStatus("failed", "Card upload id button not found", driver);
         errorcounter++;
      }

      // Loyalty Card Barcode and QR Code label is shown
      List<AndroidElement> textElementList = driver.findElementsByClassName("android.widget.TextView");
      int counter = 0;
      for (int i = 0; i < textElementList.size(); i++) {
         if (textElementList.get(i).getText().equals("Loyalty Card Barcode and QR Code")) {
            counter++;
         }
      }
      if (counter <= 0) {
         markTestStatus("failed", "Loyalty Card Barcode and QR Code label not found", driver);
         errorcounter++;
      }

      // Check if the card barcode is shown
      try {
         AndroidElement cardBarcode = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "loyalty_card_barcode")));
         Assert.assertTrue(cardBarcode.isDisplayed());

      } catch (Exception e) {
         markTestStatus("failed", "Card barcode not found", driver);
         errorcounter++;
      }

      // Check if the card QR code is shown
      try {
         AndroidElement cardQrCode = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "loyalty_card_qr")));
         Assert.assertTrue(cardQrCode.isDisplayed());

      } catch (Exception e) {
         markTestStatus("failed", "Card QR code not found", driver);
         errorcounter++;
      }

      // Check if the card unlink button is shown
      try {
         AndroidElement cardUnlinkButtton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("loyalty_display_unlink")));
         Assert.assertTrue(cardUnlinkButtton.isEnabled());

      } catch (Exception e) {
         markTestStatus("failed", "Card unlink button not found", driver);
         errorcounter++;
      }
   }

   @Test(priority = 20)
   public void checker() {
      if (errorcounter <= 0) {
         markTestStatus("passed", "SUCCESS: Loyalty Card > Expired Perks Card has successful", driver);
      } else {
         markTestStatus("failed", "ERROR: Loyalty Card >Expired Perks Card has failed", driver);
      }
   }

   // This method accepts the status, reason and WebDriver instance and marks the
   // test on BrowserStack
   public static void markTestStatus(String status, String reason, WebDriver driver) {
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

}
