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
import org.testng.annotations.Test;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import util.ExcelDataSupllier;

public class TC24_MyAccount_CompleteMyProfile extends AndroidSetup {

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
   public void LoginFieldsTest_MyAccounts_CompleteMyProfile(String email, String password) throws Exception {

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
         AndroidElement myProfileTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         if (myProfileTitle.getText().equalsIgnoreCase("My Profile")) {
            System.out.println("My Profile Complete My Profile Shown");
         }
         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         errorcounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 3)
   private void completeMyProfileTest() {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      // First name field
      try {
         AndroidElement firstNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_first_name_field")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: First name field is not found.", driver);
         errorcounter++;
      }

      // Middle name field
      try {
         AndroidElement middleNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_middle_name_field")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Middle name field is not found.", driver);
         errorcounter++;
      }

      // Last name field
      try {
         AndroidElement lastNameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_last_name_field")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Last name field is not found.", driver);
         errorcounter++;
      }

      // Province Test
      try {
         AndroidElement provinceSpinner = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_province_value")));
         provinceSpinner.click();

         AndroidElement specificProvince = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Metro Manila\").instance(0))")));

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
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Makati City\").instance(0))")));

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
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Poblacion\").instance(0))")));

         specificBarangay.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Barangay field is not found.", driver);
         errorcounter++;
      }

      // Postal Code Test
      try {
         AndroidElement postalField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_postal_field")));
         postalField.sendKeys("2021");
         driver.hideKeyboard();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Postal field is not found.", driver);
         errorcounter++;
      }

      // House Number Test
      try {
         AndroidElement houseNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_house_field")));
         houseNumberField.sendKeys("test address 123");
         driver.hideKeyboard();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: House number field is not found.", driver);
         errorcounter++;
      }

      // Birthday Test
      try {
         AndroidElement birthdayField = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Birthday\").instance(0))")));
         driver.hideKeyboard();
         birthdayField.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Birthday field is not found.", driver);
         errorcounter++;
      }

      // Birthday Date Test
      try {

         List<AndroidElement> datePickerListField = driver.findElements(By.className("android.widget.EditText"));
         List<AndroidElement> datePickerList = driver.findElements(By.className("android.widget.NumberPicker"));

         // datePickerList.get(2).setValue("2000");
         AndroidElement timePicker = datePickerList.get(2);
         int currentYear = 2023;

         while (true) {
            // Get the current time value
            String yearSelected = datePickerListField.get(2).getText();

            // Compare the current time with the target time
            if (currentYear - Integer.parseInt(yearSelected) >= 18) {

               break;
            } else {
               // Scroll the time picker element (you can adjust the swipe direction and distance as needed)
               swipeElementAndroid(timePicker, Direction.DOWN);
            }
         }

      } catch (Exception e) {
         // TODO Auto-generated catch block
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

      // Gender Test
      try {
         AndroidElement genderField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_gender_value")));
         genderField.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Gender field is not found.", driver);
         errorcounter++;
      }

      // Gender page title
      try {
         AndroidElement genderTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));
         if (genderTitle.getText().equals("Gender")) {

         } else {
            markTestStatus("failed", "FAILED: Gender title not found.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Gender title is not found.", driver);
         errorcounter++;
      }

      // Male Option
      try {
         AndroidElement maleOption = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Male\").instance(0))")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Male option is not found.", driver);
         errorcounter++;
      }

      // Female Option
      try {
         AndroidElement femaleOption = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Female\").instance(0))")));
         femaleOption.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Female option is not found.", driver);
         errorcounter++;
      }

      // Age Bracket Field
      try {
         AndroidElement ageField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "contact_info_age_bracket_value")));
         ageField.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Gender title is not found.", driver);
         errorcounter++;
      }

      // Age Page Title
      try {
         AndroidElement ageTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));
         if (ageTitle.getText().equals("Age Bracket")) {

         } else {
            markTestStatus("failed", "FAILED: Age title not found.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age title is not found.", driver);
         errorcounter++;
      }

      // Age Choices Field
      try {
         List<AndroidElement> ageChoicesList = driver.findElements(By.className("android.widget.TextView"));
         ageChoicesList.get(3).click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age title is not found.", driver);
         errorcounter++;
      }

      // Occupation Field
      try {
         AndroidElement occupationField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_occupation_value")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Occupation field is not found.", driver);
         errorcounter++;
      }

      // Income Bracket Field
      try {
         AndroidElement ageBracketField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_income_bracket_value")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Age bracket field is not found.", driver);
         errorcounter++;
      }

      // Income Bracket Field
      try {
         AndroidElement consentCheckbox = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("contact_info_dpa")));
         consentCheckbox.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Consent checkbox is not found.", driver);
         errorcounter++;
      }

      // Save button Field
      try {
         AndroidElement saveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "contact_info_save")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Save button is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 20)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on My Account > Complete My Profile, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "My Account > Complete My Profile is successful", driver);
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
