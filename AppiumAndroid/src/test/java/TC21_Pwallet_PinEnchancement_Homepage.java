import java.util.concurrent.TimeUnit;
import javax.sql.rowset.WebRowSet;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import util.ExcelDataSupllier;

public class TC21_Pwallet_PinEnchancement_Homepage extends AndroidSetup {

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

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_PwalletPin_Homepage(String email, String password) throws Exception {

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

         // Checking if Puregold Image from homepage is visible to know if login is successful
         AndroidElement PuregoldImageChecker = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("image")));

         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 3)
   private void navigateToPwalletPageTest() {

      // Locate for Pwallet button on the Homepage
      // Go to the Pwallet Page
      try {
         AndroidElement PWalletButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "menu_wallet")));
         PWalletButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet page button is not found.", driver);
         errorcounter++;
      }

      // Verify the Pwallet Pin Page title
      try {
         AndroidElement pwalletPinTitlePage = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("title")));
         try {
            Assert.assertTrue(pwalletPinTitlePage.getText().contains("P-Wallet PIN"));
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Pwallet page title is not same.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet page button is not found.", driver);
         errorcounter++;
      }

      // Verify the Pwallet Pin Page title
      try {
         AndroidElement pwalletLable = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "wallet_enter_pin_code_title")));
         try {
            Assert.assertTrue(pwalletLable.getText().contains("Enter your P-Wallet PIN"));
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Pwallet label is not same.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallett label is not found.", driver);
         errorcounter++;
      }

      // Submit button pwallet pin
      try {
         AndroidElement pwalletSubmitButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_enter_pin_submit")));
         try {
            Assert.assertTrue(!pwalletSubmitButton.isEnabled());
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: The submit button is enable.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet submit button not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 3, enabled = true)
   private void pwalletWrongPinTest() throws InterruptedException {
      // Verify the Pwallet Pin textfield
      // Send wrong pin
      try {
         AndroidElement pinWalletField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("wallet_enter_pin_code")));
         // Send a wrong key first
         pinWalletField.sendKeys(wrongPwalletPin);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Pin Field not found.", driver);
         errorcounter++;
      }

      // Submit button pwallet pin
      try {
         AndroidElement pwalletSubmitButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("wallet_enter_pin_submit")));
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
   

      // A pop up must appear
      try {
         AndroidElement popUpTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "tvTitle")));
         try {
            Assert.assertTrue(popUpTitle.getText().contains("Sorry"));
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: The popup title is not same.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Popup is not found.", driver);
         errorcounter++;
      }

      // A pop up description must appear
      try {
         AndroidElement popUpDescription = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("tvDescription")));
         try {
            Assert.assertTrue(popUpDescription.getText().contains("Invalid Wallet PIN."));
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: The popup description is not same.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Popup is not found.", driver);
         errorcounter++;
      }

      // A pop up OKAY button must appear
      try {
         AndroidElement popUpOkayButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "bPositive")));
       
         try {
            Assert.assertTrue(popUpOkayButton.getText().contains("OKAY"));
            popUpOkayButton.click();
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: The popup button is not same.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Popup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 4)
   private void pwalletCorrectPinTest() {

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

   @Test(priority = 99)
   private void checkError() {
      if (errorcounter > 0) {
         markTestStatus("failed", "FAILED: an error occurred on Wallet > Homepage > Pwallet Pin test, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "PASSED: Wallet > Homepage > Pwallet Pin Succesfull", driver);
      }
   }
}
