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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.ExcelDataSupllier;

public class TC30_MyProfile_Kyc_Enhancements extends AndroidSetup {
   String preferredProvince = "Metro Manila";
   String preferedCity = "Makati City";
   String preferedBarangay = "Poblacion";

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
   public void LoginFieldTest_MyProfileKycEnhancements(String email, String password) throws Exception {

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

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }

      // Checking the landing page "My Profile" page must show
      try {
         AndroidElement myProfileTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         Assert.assertTrue(myProfileTitle.getText().equals("My Profile"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: My Profile title is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 3)
   private void myProfileFieldsTest() {
      // Checking the first name textfield
      try {
         AndroidElement firstnameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_first_name_field")));
         Assert.assertTrue(!firstnameField.getText().equals("Enter First Name"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Firstname field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Firstname field is equal to default holder.", driver);
         errorcounter++;
      }

      // Checking the middle name textfield
      try {
         AndroidElement middlenameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_middle_name_field")));
         Assert.assertTrue(middlenameField.getText().equals("Enter Middle Name"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Middlename field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Middlename field is already change.", driver);
         errorcounter++;
      }

      // Checking the last name textfield
      try {
         AndroidElement lastnameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_last_name_field")));
         Assert.assertTrue(!lastnameField.getText().equals("Enter Last Name"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Lastname field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Lastname field is equal to default holder", driver);
         errorcounter++;
      }

      // Checking the email textfield
      try {
         AndroidElement emailField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_email_field")));
         Assert.assertTrue(!emailField.getText().equals("Enter Email Address"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Email field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Email field is equal to default holder.", driver);
         errorcounter++;
      }

      // Checking the primary mobile number textfield
      try {
         AndroidElement primaryMobileNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("contact_info_primary_mobile_field")));
         Assert.assertTrue(!primaryMobileNumberField.getText().equals("9XXXXXXXXX"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Primary mobile number field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Primary mobile number field is equal to default holder.", driver);
         errorcounter++;
      }

      // Checking the secondary mobile number textfield
      try {
         AndroidElement secondaryMobileNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("contact_info_secondary_mobile_field")));
         Assert.assertTrue(secondaryMobileNumberField.getText().equals("9XXXXXXXXX"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Secondary mobile number field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Secondary mobile number field is is already change.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 4)
   private void myProfileAddressTest() {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      // Province Test
      try {
         AndroidElement provinceSpinner = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_province_value")));
         provinceSpinner.click();

         AndroidElement specificProvince = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + preferredProvince
                     + "\").instance(0))")));
         specificProvince.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Province field is not found.", driver);
         errorcounter++;
      }
      // City Test
      try {
         AndroidElement citySpinner = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_city_value")));
         citySpinner.click();

         AndroidElement specificCity = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + preferedCity
                     + "\").instance(0))")));

         specificCity.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: City field is not found.", driver);
         errorcounter++;
      }

      // Barangay Test
      try {
         AndroidElement barangaySpinner = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_barangay_value")));
         barangaySpinner.click();

         AndroidElement specificBarangay = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + preferedBarangay
                     + "\").instance(0))")));
         specificBarangay.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Barangay field is not found.", driver);
         errorcounter++;
      }

      // Assert if the selected place is correct
      List<AndroidElement> selectedAddressList = driver.findElementsById("spinner_item_text");
      if (selectedAddressList.size() >= 3 && selectedAddressList.get(0).getText().equals(preferredProvince) && selectedAddressList.get(1).getText()
            .equals(preferedCity) && selectedAddressList.get(2).getText().equals(preferedBarangay)) {

      } else {
         markTestStatus("failed", "FAILED: The address selected is not correct.", driver);
         errorcounter++;
      }

      // Checking the postal code textfield
      try {
         AndroidElement postalCodeField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_postal_field")));
         Assert.assertTrue(postalCodeField.getText().equals("Postal Code"));
         postalCodeField.sendKeys("1234");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Postal code field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Postal code field is not equal to the holder.", driver);
         errorcounter++;
      }

      // Checking the house number/street textfield
      try {
         AndroidElement houseNumberStreetField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("contact_info_house_field")));
         Assert.assertTrue(houseNumberStreetField.getText().equals("Enter Home Number, Building and Street Name"));
         houseNumberStreetField.sendKeys("Test Address, 1234");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: House number field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: House number field is not equal to the holder.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 5)
   private void additionalInformationTest() {
      // Waiting time
      WebDriverWait wait = new WebDriverWait(driver, 30);

      // Scroll until the birthday label found
      try {
         AndroidElement birthdayLabel = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Birthday *\").instance(0))")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday label is not found.", driver);
         errorcounter++;
      }

      // Checking the birthday textfield
      try {
         AndroidElement birthdayField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_birthday_value")));
         Assert.assertTrue(birthdayField.getText().equals("Select Birthday"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Birthday field is is already change.", driver);
         errorcounter++;
      }

      // Checking the gender textfield
      try {
         AndroidElement genderField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_gender_value")));
         Assert.assertTrue(genderField.getText().equals("Select Gender"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Gender field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Gender field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the age textfield
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_age_bracket_value")));
         Assert.assertTrue(ageField.getText().equals("Auto-calculated on birthday selection"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Age field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the occupation textfield
      try {
         AndroidElement occupationField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_occupation_value")));
         Assert.assertTrue(occupationField.getText().equals("Select Occupation"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Occupation field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Occupation field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the income textfield
      try {
         AndroidElement incomeField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_income_bracket_value")));
         Assert.assertTrue(incomeField.getText().equals("Select Income Bracket"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Income field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Income field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the terms and conditon checkbox
      try {
         AndroidElement termsAndConditionCheckBox = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("contact_info_dpa")));
         Assert.assertTrue(termsAndConditionCheckBox.getText().equals(
               "I have read, understood and agreed to Puregold’s Terms & Conditions and Privacy Policy"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Terms and condition checkbox is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Terms and condition checkbox didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the save button
      try {
         AndroidElement saveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_save")));
         Assert.assertTrue(saveButton.isEnabled());

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Save button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Save button is not enable.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6)
   private void birthdayAgeTest() {
      // Waiting time
      WebDriverWait wait = new WebDriverWait(driver, 30);

      // Checking the birthday textfield (Age between less than 18)
      try {
         AndroidElement birthdayField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_birthday_value")));
         Assert.assertTrue(birthdayField.getText().equals("Select Birthday"));
         birthdayField.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Birthday field is is already change.", driver);
         errorcounter++;
      }

      selectBirthYear(2024, 0, 17);

      // Checking if the error message shows
      try {
         AndroidElement birthdayErrorMessage = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("contact_info_birthday_error")));
         Assert.assertTrue(birthdayErrorMessage.getText().equals("You must be 18 years old and above."));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Error message is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Error message is not correct.", driver);
         errorcounter++;
      }

      // Checking the age textfield
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_age_bracket_value")));
         Assert.assertTrue(ageField.getText().equals("Auto-calculated on birthday selection"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Age field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the birthday textfield (Age between 18-24)
      try {
         AndroidElement birthdayField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_birthday_value")));

         birthdayField.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Birthday field is is already change.", driver);
         errorcounter++;
      }

      selectBirthYear(2024, 18, 24);

      // Checking the age textfield
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_age_bracket_value")));
         Assert.assertTrue(ageField.getText().equals("18-24"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Age field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the birthday textfield (Age between 55-64)
      try {
         AndroidElement birthdayField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_birthday_value")));

         birthdayField.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Birthday field is is already change.", driver);
         errorcounter++;
      }

      selectBirthYear(2024, 55, 64);

      // Checking the age textfield
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_age_bracket_value")));
         Assert.assertTrue(ageField.getText().equals("55-64"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Age field didnt show the correct holder.", driver);
         errorcounter++;
      }

      // Checking the birthday textfield (Age greater than 65)
      try {
         AndroidElement birthdayField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_birthday_value")));

         birthdayField.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Birthday field is is already change.", driver);
         errorcounter++;
      }

      selectBirthYear(2024, 65, 100);

      // Checking the age textfield
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_age_bracket_value")));
         Assert.assertTrue(ageField.getText().equals("65+"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age field is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         System.out.println(e);
         markTestStatus("failed", "FAILED: Age field didnt show the correct holder.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 8)
   private void checkError() {
      if (errorcounter > 0) {
         markTestStatus("failed", "FAILED: My Profile > KYC Enhancements Failed", driver);
      } else {
         markTestStatus("passed", "SUCCESS: My Profile > KYC Enhancements Successful", driver);
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

   public static void swipe(AndroidDriver<AndroidElement> driver, int startX, int startY, int endX, int endY, int duration) {
      TouchAction touchAction = new TouchAction(driver);
      touchAction.press(PointOption.point(startX, startY)).waitAction().moveTo(PointOption.point(endX, endY)).release().perform();
      System.out.println("Swiped!");
   }

   private void selectBirthYear(int currentYear, int lowerAge, int higherAge) {
      // Waiting time
      WebDriverWait wait = new WebDriverWait(driver, 30);
      // Birthday Date Test
      try {

         List<AndroidElement> datePickerListField = driver.findElements(By.className("android.widget.EditText"));
         List<AndroidElement> datePickerList = driver.findElements(By.className("android.widget.NumberPicker"));

         // datePickerList.get(2).setValue("2000");
         AndroidElement timePicker = datePickerList.get(2);

         while (true) {
            // Get the current time value
            String yearSelected = datePickerListField.get(2).getText();

            // Compare the current time with the target time
            if (currentYear - Integer.parseInt(yearSelected) >= lowerAge && currentYear - Integer.parseInt(yearSelected) <= higherAge) {

               break;
            } else {
               // Scroll the time picker element (you can adjust the swipe direction and distance as needed)
               swipeElementAndroid(timePicker, Direction.DOWN);
            }
         }

      } catch (Exception e) {

         markTestStatus("failed", "FAILED: Date Picker field is not found.", driver);
         errorcounter++;
      }

      // Okay Button Test
      try {
         AndroidElement okayButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"OK\").instance(0))")));
         okayButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Okay button is not found.", driver);
         errorcounter++;
      }
   }
}
