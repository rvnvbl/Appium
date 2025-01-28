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
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC14_1_LoyaltyCardApplication_Android_Perks extends AndroidSetup {
   String SubTotalString = "";
   String DateString1 = "";
   String TimeString1 = "";
   String PaymentMethodString = "";
   String TempString = "";
   String MonthString = "";
   String DayString = "";
   String YearString = "";

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
   public void LoginFieldsTest_LoyaltyCardApplication_PERKS(String email, String password) throws Exception {

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
   public void TapAccountButtonTest() throws Exception {
      // Scrolling to and clicking the Donation Drive button
      try {
         AndroidElement AccountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("nav_account")));
         AccountButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Nav Account Button is not found.", driver);
         errorcounter++;
      }

      // Checking if "Account" title is visible
      try {
         AndroidElement AccountTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         String AccountText = AccountTitle.getText();

         if (AccountText.equals("Account")) {
            System.out.println("Test passed: Redirect to Account successful");
         } else {
            markTestStatus("failed", "FAILED: Account title is not found.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Account title is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 4)
   public void LoyaltyApplyTest() throws Exception {
      // Clicking the apply for loyalty card button
      try {
         AndroidElement RegisterLoyaltyButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("account_loyalty_registration")));
         RegisterLoyaltyButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Register for Loyalty Card Button is not found.", driver);
         errorcounter++;
      }

      // Checking if "Card Application" title is visible
      try {
         AndroidElement CardApplicationTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("title")));

         String CardApplicationText = CardApplicationTitle.getText();

         if (CardApplicationText.equals("Card Application")) {
            System.out.println("Test passed: Redirect to Card Application successful");
         } else {
            markTestStatus("failed", "FAILED: Card Application title is wrong.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Card Application title is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 5)
   public void ApplicationStatusTest() throws Exception {
      // Clicking the Application Status button
      try {
         AndroidElement AppStatusButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("card_application_view_layout")));
         AppStatusButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Application Status Button is not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if "Application Status" title is visible
         AndroidElement AppStatusTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         String AppStatusTitleText = AppStatusTitle.getText();

         if (AppStatusTitleText.equals("Card Application Status")) {
            System.out.println("Test passed: Redirect to Application Status successful");

            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            // Checking if "Card Application" title is visible
            try {
               AndroidElement CardApplicationTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
                     .visibilityOfElementLocated(MobileBy.id("title")));

               String CardApplicationText = CardApplicationTitle.getText();

               if (CardApplicationText.equals("Card Application")) {
                  System.out.println("Test passed: Redirect to Card Application successful");
               } else {
                  markTestStatus("failed", "FAILED: Card Application title is wrong.", driver);
                  errorcounter++;
               }

               driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Card Application title is not found.", driver);
               errorcounter++;
            }
         } else {
            markTestStatus("failed", "FAILED: Application Status title is wrong.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Application Status Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6)
   public void ApplyOptionsTest() throws Exception {

      // Clicking the Apply option
      try {
         AndroidElement ApplyButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "card_application_apply_layout")));

         ApplyButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Loyalty Apply Button is not found.", driver);
         errorcounter++;
      }

      // Checking if Apply spiel is visible
      try {
         AndroidElement SpielText = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "card_application_loyalty_spiel")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Application Spiel is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 7)
   public void PerksButtonTest() throws Exception {

      // Clicking the "Perks" option
      try {
         AndroidElement PerksButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "card_application_perks_layout")));
         PerksButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: TNAP Button is not found.", driver);
         errorcounter++;
      }

      // Checking if "T&C" title is visible
      try {
         AndroidElement TermsConditionsTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("title")));

         String TermsConditionsTitleText = TermsConditionsTitle.getText();

         if (TermsConditionsTitleText.equals("Terms and Conditions")) {
            System.out.println("Test passed: Redirect to Terms and Conditions successful");
         } else {
            markTestStatus("failed", "FAILED: Terms and Conditions title is wrong.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Terms and Conditions title is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 8)
   public void TermsConditionsTest() throws Exception {

      // Clicking the Accept button
      try {
         AndroidElement AcceptTermsButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("terms_and_conditions_accept_button")));
         AcceptTermsButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Accept Terms button is not found.", driver);
         errorcounter++;
      }

      // Checking if "Perks forms" title is visible
      try {
         AndroidElement PerksTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         String PerksTitleText = PerksTitle.getText();

         if (PerksTitleText.equals("PERKS Application Form")) {
            System.out.println("Test passed: Redirect to Perks forms successful");
         } else {
            markTestStatus("failed", "FAILED: Perks forms title is wrong.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Perks forms title is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 9)
   public void FirstNameTest() throws Exception {
      // Clicking the First Name Field
      try {
         MobileElement FirstNameField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_first_name\"));");

         FirstNameField.click();

         FirstNameField.sendKeys("FirstName");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: First Name Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 10)
   public void MiddleNameTest() throws Exception {
      // Clicking the Middle Name Field
      try {
         MobileElement MiddleNameField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_middle_name\"));");

         MiddleNameField.click();

         MiddleNameField.sendKeys("MiddleName");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Middle Name Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 11)
   public void LastNameTest() throws Exception {
      // Clicking the Last Name Field
      try {
         MobileElement LastNameField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_last_name\"));");

         LastNameField.click();

         LastNameField.sendKeys("LastName");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Last Name Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 12)
   public void BirthDateTest() throws Exception {
      // Clicking the Birthdate Field
      try {
         MobileElement BdateOption = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_birthdate\"));");

         BdateOption.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birth Date Field is not found.", driver);
         errorcounter++;
      }

      // Clicking, Setting and getting the value of the date on the wheel picker
      try {
         List<AndroidElement> DateOptions = driver.findElements(By.id("android:id/numberpicker_input"));
         try {
            AndroidElement SpecificOption1 = DateOptions.get(0);

            DayString = SpecificOption1.getText();
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Bdate Day Field error / not found.", driver);
            errorcounter++;
         }

         try {
            AndroidElement SpecificOption2 = DateOptions.get(1);

            MonthString = SpecificOption2.getText();
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Bdate Month Field error / not found.", driver);
            errorcounter++;
         }

         try {
            AndroidElement SpecificOption3 = DateOptions.get(2);

            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withDuration(Duration.ofSeconds(3)).withElement(ElementOption.element(SpecificOption3));
            TouchAction action = new TouchAction(driver);
            action.longPress(longPressOptions).release().perform();

            SpecificOption3.clear();

            SpecificOption3.sendKeys("1998");

            YearString = SpecificOption3.getText();
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Bdate Year Field error / not found.", driver);
            errorcounter++;
         }

         try {
            AndroidElement OkButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
                  "android:id/button1")));

            OkButton.click();

            Thread.sleep(2000);

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Ok button error / not found.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Wheel picker options are not found.", driver);
         errorcounter++;
      }

      // Checking the Birthdate Field if it contains the date provided
      try {
         MobileElement BdateOption = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_birthdate\"));");
         String BdateOptionText = BdateOption.getText();

         if (BdateOptionText.contains(DayString) && BdateOptionText.contains(MonthString) && BdateOptionText.contains(YearString)) {
            System.out.println("Date is correct");
         } else {
            markTestStatus("failed", "FAILED: Date is not correct.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birth Date Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 13)
   public void CivilStatusTest() throws Exception {
      // Clicking the Civil Status Field
      try {
         MobileElement CivilDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_civil_status\"));");

         CivilDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Civil Status Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Civil Status Field if the selected option is correct
      try {
         MobileElement CivilDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_civil_status\"));");

         if (TempString.equals(CivilDropdown.getText())) {
            System.out.println("Option from Civil Status Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Civil Status Dropdown is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 14)
   public void GenderTest() throws Exception {
      // Clicking the Gender Field
      try {
         MobileElement GenderDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_gender\"));");

         GenderDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Gender Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Gender Field if the selected option is correct
      try {
         MobileElement GenderDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_gender\"));");

         if (TempString.equals(GenderDropdown.getText())) {
            System.out.println("Option from Gender Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Gender Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 15)
   public void TelephoneTest() throws Exception {
      // Clicking the Telephone Field
      try {

         MobileElement telePrefix = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_telephone_prefix\"));");

         telePrefix.click();
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);
         SpecificOption.click();

         MobileElement TeleField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_telephone\"));");

         TeleField.click();

         TeleField.sendKeys("9199999");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Telephone Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 16)
   public void MobileTest() throws Exception {
      // Clicking the Mobile Field
      try {
         MobileElement MobileField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_mobile\"));");

         MobileField.click();

         MobileField.sendKeys("9161111111");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Mobile Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 17)
   public void EmailTest() throws Exception {
      // Clicking the Email Field
      try {
         MobileElement EmailField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_email\"));");

         EmailField.click();

         EmailField.sendKeys("emailtest@test.com");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Email Field is not found.", driver);
         errorcounter++;
      }
   }

   // This test is experimental, Failure can happen with changes of device, OS,
   // app, etc
   @Test(priority = 18)
   public void UploadPictureTest() throws Exception {
      // Clicking the Select File Button on Upload Picture
      try {
         // Getting the field text for comparison on picture upload success
         MobileElement PictureField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_upload_picture\"));");

         TempString = PictureField.getText();

         MobileElement PictureSelectFile = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_upload_picture_button\"));");

         PictureSelectFile.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Picture Select File Button is not found.", driver);
         errorcounter++;
      }

      // Finding and clicking the dropdown option with upload from gallery
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));

         for (int x = 0; x < DropdownOptions.size(); x++) {
            AndroidElement SpecificOption = DropdownOptions.get(x);
            if (SpecificOption.getText().contains("Upload")) {
               SpecificOption.click();

               Thread.sleep(2000);

               break;
            }
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Finding and clicking the first picture option on the gallery
      try {
         List<AndroidElement> PictureOptions = driver.findElements(By.id("com.google.android.documentsui:id/icon_thumb"));
         AndroidElement SpecificPictureOption = PictureOptions.get(0);

         SpecificPictureOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         driver.pressKey(new KeyEvent(AndroidKey.BACK));
         markTestStatus("failed", "FAILED: No Pictures are found.", driver);
         errorcounter++;
      }

      // Checking the Picture Upload Field if the picture select is successful
      try {
         MobileElement PictureUploadField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_upload_picture\"));");

         if (TempString.equals(PictureUploadField.getText())) {
            markTestStatus("failed", "FAILED: Upload File is not successful, File name is not found.", driver);
            errorcounter++;
         } else {
            System.out.println("Picture upload is successful");
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Picture Upload Field is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 19)
   public void IDTypeTest() throws Exception {
      // Clicking the ID Field
      try {
         MobileElement IDDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_id_type\"));");

         IDDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: ID Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the ID Field if the selected option is correct
      try {
         MobileElement IDDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_id_type\"));");

         if (TempString.equals(IDDropdown.getText())) {
            System.out.println("Option from ID Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: ID Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   // This test is experimental, Failure can happen with changes of device, OS,
   // app, etc
   @Test(priority = 20)
   public void UploadIDTest() throws Exception {
      // Clicking the Select File Button on Upload Picture
      try {
         // Getting the field text for comparison on picture upload success
         MobileElement IDField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_upload_id\"));");

         TempString = IDField.getText();

         MobileElement PictureSelectFile = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_upload_id_button\"));");

         PictureSelectFile.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: ID Select File Button is not found.", driver);
         errorcounter++;
      }

      // Finding and clicking the dropdown option with upload from gallery
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));

         for (int x = 0; x < DropdownOptions.size(); x++) {
            AndroidElement SpecificOption = DropdownOptions.get(x);
            if (SpecificOption.getText().contains("Upload")) {
               SpecificOption.click();

               Thread.sleep(2000);

               break;
            }
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Finding and clicking the first picture option on the gallery
      try {
         List<AndroidElement> PictureOptions = driver.findElements(By.id("com.google.android.documentsui:id/icon_thumb"));
         AndroidElement SpecificPictureOption = PictureOptions.get(0);

         SpecificPictureOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         driver.pressKey(new KeyEvent(AndroidKey.BACK));
         markTestStatus("failed", "FAILED: No Pictures are found.", driver);
         errorcounter++;
      }

      // Checking the ID Upload Field if the picture select is successful
      try {
         MobileElement IDUploadField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_upload_id\"));");

         if (TempString.equals(IDUploadField.getText())) {
            markTestStatus("failed", "FAILED: Upload File is not successful, File name is not found.", driver);
            errorcounter++;
         } else {
            System.out.println("ID upload is successful");
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: ID Upload Field is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 21)
   public void OccupationTest() throws Exception {
      // Clicking the Occupation Field
      try {
         MobileElement OccupationDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_occupation\"));");

         OccupationDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Occupation Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Occupation Field if the selected option is correct
      try {
         MobileElement OccupationDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_occupation\"));");

         if (TempString.equals(OccupationDropdown.getText())) {
            System.out.println("Option from Occupation Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Occupation Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 22)
   public void ProvinceTest() throws Exception {
      // Clicking the Province Field
      try {
         MobileElement ProvinceDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_province\"));");

         ProvinceDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Province Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Province Field if the selected option is correct
      try {
         MobileElement ProvinceDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_province\"));");

         if (TempString.equals(ProvinceDropdown.getText())) {
            System.out.println("Option from Province Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Province Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 23)
   public void CityTest() throws Exception {
      // Clicking the City Field
      try {
         MobileElement CityDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_city\"));");

         CityDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: City Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the City Field if the selected option is correct
      try {
         MobileElement CityDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_city\"));");

         if (TempString.equals(CityDropdown.getText())) {
            System.out.println("Option from City Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: City Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 24)
   public void BarangayTest() throws Exception {
      // Clicking the Barangay Field
      try {
         MobileElement BarangayDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_barangay\"));");

         BarangayDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Barangay Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Barangay Field if the selected option is correct
      try {
         MobileElement BarangayDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_barangay\"));");

         if (TempString.equals(BarangayDropdown.getText())) {
            System.out.println("Option from Barangay Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Barangay Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 25)
   public void PostalTest() throws Exception {
      // Clicking the Postal Field
      try {
         MobileElement PostalField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_postal_code\"));");

         PostalField.click();

         PostalField.sendKeys("1111");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Postal Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 26)
   public void HouseNoTest() throws Exception {
      // Clicking the House Number Field
      try {
         MobileElement HouseNoField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_house_number\"));");

         HouseNoField.click();

         HouseNoField.sendKeys("Test Street");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: House Number Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 27, enabled = false)
   public void CompanyNameTest() throws Exception {
      // Clicking the Company Name Field
      try {
         MobileElement CNameField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_name\"));");

         CNameField.click();

         CNameField.sendKeys("CompanyName");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company Name Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 28, enabled = false)
   public void CompanyProvinceTest() throws Exception {
      // Clicking the Company Province Field
      try {
         MobileElement CProvDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_province\"));");

         CProvDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company Province Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Company Province Field if the selected option is correct
      try {
         MobileElement CProvDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_province\"));");

         if (TempString.equals(CProvDropdown.getText())) {
            System.out.println("Option from Company Province Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company Province Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 29, enabled = false)
   public void CompanyCityTest() throws Exception {
      // Clicking the Company City Field
      try {
         MobileElement CCityDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_city\"));");

         CCityDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company City Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Company City Field if the selected option is correct
      try {
         MobileElement CCityDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_city\"));");

         if (TempString.equals(CCityDropdown.getText())) {
            System.out.println("Option from Company City Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company City Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 30, enabled = false)
   public void CompanyBarangayTest() throws Exception {
      // Clicking the Company Barangay Field
      try {
         MobileElement CBarangayDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_barangay\"));");

         CBarangayDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company Barangay Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Company Barangay Field if the selected option is correct
      try {
         MobileElement CBarangayDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_barangay\"));");

         if (TempString.equals(CBarangayDropdown.getText())) {
            System.out.println("Option from Company Barangay Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company Barangay Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 31, enabled = false)
   public void CompanyPostalTest() throws Exception {
      // Clicking the Company Postal Field
      try {
         MobileElement PostalField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_postal_code\"));");

         PostalField.click();

         PostalField.sendKeys("1112");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Postal Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 32, enabled = false)
   public void CompanyHouseNoTest() throws Exception {
      // Clicking the Company House Number Field
      try {
         MobileElement HouseNoField = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_company_house_number\"));");

         HouseNoField.click();

         HouseNoField.sendKeys("Test Company Street");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Company House Number Field is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 33, enabled = false)
   public void VisitFrequencyTest() throws Exception {
      // Clicking the Visit Frequency Field
      try {
         MobileElement VFreqDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_visit\"));");

         VFreqDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Visit Frequency Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Visit Frequency Field if the selected option is correct
      try {
         MobileElement VFreqDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_visit\"));");

         if (TempString.equals(VFreqDropdown.getText())) {
            System.out.println("Option from Visit Frequency Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Visit Frequency Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 34, enabled = false)
   public void MonthlyPurchaseTest() throws Exception {
      // Clicking the Visit Monthly Purchase Field
      try {
         MobileElement MPurchDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_monthly_purchase\"));");

         MPurchDropdown.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Monthly Purchase Dropdown is not found.", driver);
         errorcounter++;
      }

      // Clicking and getting the value of the first option on the dropdown
      try {
         List<AndroidElement> DropdownOptions = driver.findElements(By.id("bottom_sheet_text"));
         AndroidElement SpecificOption = DropdownOptions.get(0);

         TempString = SpecificOption.getText();

         SpecificOption.click();

         Thread.sleep(2000);

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Dropdown options are not found.", driver);
         errorcounter++;
      }

      // Checking the Monthly Purchase Field if the selected option is correct
      try {
         MobileElement MPurchDropdown = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView"
               + "(new UiSelector().resourceId(\"com.grocery.puregold:id/perks_application_form_monthly_purchase\"));");

         if (TempString.equals(MPurchDropdown.getText())) {
            System.out.println("Option from Monthly Purchase Dropdown is correct");
         } else {
            markTestStatus("failed", "FAILED: Option from dropdown is not the same.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(2000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Monthly Purchase Dropdown is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 35)
   public void SubmitFormTest() throws Exception {
      try {
         // Checking the Submit button if it is enabled
         AndroidElement SubmitButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "perks_application_form_button")));

         if (SubmitButton.isEnabled()) {
            System.out.println("Submit button is enabled after filling up fields");
         } else {
            markTestStatus("failed", "FAILED: Submit button is not enabled after filling up fields.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Submit button error / not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 36)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Loyalty Card Application > Perks test, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Loyalty Card Application > Perks Module Successful", driver);
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
