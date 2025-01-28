import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC1_0_Login_Android_LoginAsRegularUser_MyAccounts extends AndroidSetup {

   /*
    * - Elements that use the WebDriverWait waits for the element to be true on the set conditions in a
    * set time eliminating the use of long implicit wait or thread sleep (> 10 seconds of delay). -
    * Elements using AndroidUIAutomator as a locator is used for scrolling to the element to reduce
    * lines of code for scrolling the screen to a specific x and y location. - Some Elements are only
    * used for checking if the screen has successfully redirected to the next page.
    */

   String DayString = "";
   String MonthString = "";
   String YearString = "";
   int errorcounter = 0;

   @Test(priority = 0)
   public void LoginPageTest() throws Exception {
      try {
         // Clicking of Login with email button on startup screen
         Thread.sleep(3000);
         AndroidElement EmailLoginButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "landing_login")));

         // scroll down to the element and click
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
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest(String email, String password) throws Exception {

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
   private void loginButtonTest() {
      // Clicking of login button after the input of the above credentials
      try {
         AndroidElement LoginButton = (AndroidElement) driver.findElement(By.id("login_next"));
         LoginButton.click();

         markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
      } catch (Exception e1) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 3)
   private void completeAccountTest() {
      try {
         AndroidElement proceedButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "btnProceed")));
         proceedButton.click();

         try {
            AndroidElement middlenameField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("contact_info_middle_name_field")));
            middlenameField.sendKeys("Test Middle");
         } catch (Exception e) {
            // TODO Auto-generated catch block
            markTestStatus("failed", "FAILED: Middle name field  is not found.", driver);
         }

         try {
            AndroidElement postalField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("contact_info_postal_field")));
            postalField.sendKeys("1111");
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Postal name field  is not found.", driver);
         }

         try {
            AndroidElement houseNumberField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("contact_info_house_field")));
            houseNumberField.sendKeys("Test Address");
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Middle name field  is not found.", driver);
         }

         try {
            AndroidElement dateFieldSelector = driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
                  + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/contact_info_birthday_value\"));");

            dateFieldSelector.click();

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
                  AndroidElement OkButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                        MobileBy.id("android:id/button1")));

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

         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Date picker  is not found.", driver);
         }

         // Gender Field Checker
         try {
            AndroidElement genderField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("contact_info_gender_value")));
            genderField.click();

            AndroidElement pageTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
                  "title")));

            if (pageTitle.getText().equals("Gender")) {
            } else {
               markTestStatus("failed", "FAILED: Not land on Gender Page.", driver);
            }
            List<AndroidElement> selectOptions = driver.findElements(By.className("android.widget.TextView"));
            selectOptions.get(1).click();
            driver.navigate().back();

         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Gender option is not found.", driver);
         }

         // Age Bracket Checker
         try {
            AndroidElement ageBracket = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("contact_info_age_bracket_value")));
            ageBracket.click();

            AndroidElement pageTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
                  "title")));

            if (pageTitle.getText().equals("Age Bracket")) {
            } else {
               markTestStatus("failed", "FAILED: Not land on Gender Page.", driver);
            }
            List<AndroidElement> selectOptions = driver.findElements(By.className("android.widget.TextView"));
            selectOptions.get(3).click();
            driver.navigate().back();

         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Age Bracket is not found.", driver);
         }

         // Checkbox
         try {
            AndroidElement checkBox = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
                  "contact_info_dpa")));
            checkBox.click();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            markTestStatus("failed", "FAILED: Save contac info no succesfull.", driver);
         }

         // Contact Info Button
         try {
            AndroidElement saveContactInfoButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(
                  MobileBy.id("contact_info_save")));
            saveContactInfoButton.click();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            markTestStatus("failed", "FAILED: Save contac info no succesfull.", driver);
         }

      } catch (Exception e) {
         // Checking if Puregold Image from homepage is visible to know if login is successful
         AndroidElement PuregoldImageChecker = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("image")));

         System.out.println("Test 5 passed: Login is successful");
         markTestStatus("passed", "FAILED: Login Button is not found. No need to complete profile", driver);

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
