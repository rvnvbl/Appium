import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.tools.ant.taskdefs.condition.And;
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

public class TC16_0_MyAccounts_MyProfile extends AndroidSetup {

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

         System.out.println("Test 1: Redirect to Login page successful");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_MyAccounts(String email, String password) throws Exception {

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
         System.out.println("Succesfully land on My Account Page");
         markTestStatus("passed", "Succesfully land on My Account Page", driver);
      } else {
         System.out.println("ERROR: Not Succesfully land on My Account Page");
         markTestStatus("failed", "Succesfully land on My Account Page", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 4)
   private void navMyProfile() {
      try {

         AndroidElement myProfileNav = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "account_profile_label")));
         myProfileNav.click();

      } catch (Exception e) {
         System.out.println("ERROR: My Profile Button not found");
         markTestStatus("failed", "My Profile Button not found", driver);
         errorcounter++;
      }

      AndroidElement profileHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
            "title")));

      String profileHeaderText = profileHeader.getText();

      if (profileHeaderText.equals("My Profile")) {
         System.out.println("Succesfully land on My Profile Page");
         markTestStatus("passed", "Succesfully land on My Profile Page", driver);
      } else {
         System.out.println("ERROR: Not Succesfully land on My Profile Page");
         markTestStatus("failed", "Not Succesfully land on My Profile Page", driver);
         errorcounter++;
      }
   }

   @Test(priority = 5)
   private void fullNameTest() {
      try {

         AndroidElement fullNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_full_name_field")));
         fullNameField.click();
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Fullname Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement firstNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_first_name_field")));
         firstNameField.sendKeys(firstName);
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (First name Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement middleNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_middle_name_field")));
         middleNameField.sendKeys(middleName);
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Middle name Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement lastNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_last_name_field")));
         lastNameField.sendKeys(lastName);
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Last Name Field) not found", driver);
         errorcounter++;
      }

      driver.navigate().back();
   }

   @Test(priority = 6)
   private void mobileNumberTest() {
      try {
         AndroidElement mobileNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_mobile_field")));
         markTestStatus("passed", "My Profile (Full name) finished", driver);
         mobileNumberField.click();

      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Mobile number Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement mobileNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_mobile_field")));
         mobileNumberField.sendKeys(phoneNumber);
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Mobile number Field) not found", driver);
         errorcounter++;
      }
      driver.navigate().back();
   }

   @Test(priority = 6)
   private void secondaryMobileNumberTest() {

      try {
         AndroidElement secondMobileNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(
               MobileBy.id("contact_info_secondary_mobile_field")));

         markTestStatus("passed", "My Profile (Mobile Number) finished", driver);
         secondMobileNumberField.click();

      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Secondary Mobile number Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement secondMobileNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(
               MobileBy.id("contact_info_mobile_field")));
         secondMobileNumberField.sendKeys(phoneNumber);
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Secondary Mobile number Field) not found", driver);
         errorcounter++;
      }
      driver.navigate().back();
   }

   @Test(priority = 7)
   private void addressTest() {
      try {
         AndroidElement addressTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_address_field")));
         markTestStatus("passed", "My Profile (Secondary Mobile Number) finished", driver);
         addressTest.click();
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Address) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement provinceTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_province_value")));
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Province Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement cityTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_city_value")));
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (City Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement barangayTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_barangay_value")));
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Barangay Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement postalCodeTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_postal_field")));
         postalCodeTest.sendKeys("Postal Test");
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Postal Code Field) not found", driver);
         errorcounter++;
      }

      try {
         AndroidElement houseNumberTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_house_field")));
         houseNumberTest.sendKeys("Test House");;
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (House Number Field) not found", driver);
         errorcounter++;
      }

      driver.navigate().back();
   }

   @Test(priority = 8)
   private void birthdayTest() {
      try {
         AndroidElement birthdayField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_birthday_layout")));
         markTestStatus("passed", "My Profile (Address Field) finished", driver);
         birthdayField.click();

      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Birthday Field) not found", driver);
         errorcounter++;
      }

//      try {
//         AndroidElement datePickerTest = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
//               "button2")));
//         datePickerTest.click();
//      } catch (Exception e) {
//         markTestStatus("failed", "My Profile (Date Picker) not found", driver);
//         errorcounter++;
//      }

      driver.navigate().back();
   }

   @Test(priority = 9)
   private void genderTest() {
      try {
         AndroidElement genderField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_gender_layout")));
         markTestStatus("passed", "My Profile (Birthday Field) finished", driver);
         genderField.click();

      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Gender Field) not found", driver);
         errorcounter++;
      }

      try {
         List<AndroidElement> genderChoices = driver.findElements(By.className("android.widget.TextView"));
         for (int i = 0; i < genderChoices.size(); i++) {
            if (genderChoices.get(i).getText().contains("Male") && genderChoices.get(+1).getText().contains("Female")) {
               System.out.println("Gender choices found");
               break;
            }
         }

      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Gender Choices) not found", driver);
         errorcounter++;
      }
      driver.navigate().back();
   }

   @Test(priority = 10)
   private void ageTest() {
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_age_bracket_field")));
         ageField.click();
         markTestStatus("passed", "My Profile (Gender Field) finished", driver);
      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Age Field) not found", driver);
         errorcounter++;
      }

      try {
         List<AndroidElement> ageChoices = driver.findElements(By.className("android.widget.TextView"));
         System.out.println("The number of Age Choice is: " + ageChoices.size());

      } catch (Exception e) {
         markTestStatus("failed", "My Profile (Age Choices) not found", driver);
         errorcounter++;
      }
      driver.navigate().back();
   }

   @Test(priority = 20)
   public void checker() {
      if (errorcounter <= 0) {
         markTestStatus("passed", "SUCCESS: My Accounts Module > My Profile is Succesful", driver);
      } else {
         markTestStatus("failed", "ERROR: My Accounts Module > My Profile. See the logs for more details", driver);
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
