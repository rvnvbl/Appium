import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
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

public class TC25_BillsPay_Android_NoBillsPayCategory extends AndroidSetup {

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
   public void LoginFieldsTest_BillsPay_EcBills(String email, String password) throws Exception {

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
   public void TapBillsPayTest() throws Exception {
      // Scrolling toand clicking the Donation Drive button
      try {
         MobileElement BillsOptionButton = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
               "new UiScrollable(new UiSelector().scrollable(true))"
                     + ".scrollIntoView(new UiSelector().resourceIdMatches(\".*home_card_pay_bills.*\"))"));

         BillsOptionButton.click();
      } catch (Exception e) {
         try {
            AndroidElement BillsOptionButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("home_card_pay_bills")));
            BillsOptionButton.click();
         } catch (Exception e1) {
            markTestStatus("failed", "FAILED: Bills Pay Button is not found.", driver);
            errorcounter++;
         }
      }

      // Checking if "Bills Pay" title is visible
      try {
         // Checking if "Bills Pay" title is visible
         AndroidElement BillsPayTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         String BillsPayText = BillsPayTitle.getText();

         if (BillsPayText.equals("Bills Pay")) {
            System.out.println("Test passed: Redirect to Bills Pay successful");
         } else {
            markTestStatus("failed", "FAILED: Bills Pay title is not found.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Bills Pay title is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 4)
   private void MaintenaceBillspayCategories() throws InterruptedException {

      // Checking Maintenance Image
      List<AndroidElement> pageImage = driver.findElements(By.className("android.widget.ImageView"));
      System.out.println("The number of image is: " + pageImage.size());
      if (pageImage.size() == (0)) {
         markTestStatus("failed", "FAILED: Bills Pay Maintenance image is not found.", driver);
         errorcounter++;
      }

      // Checking Maintenance Title
      AndroidElement MaintenanceTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
            .id("maintenance_title")));

      if (MaintenanceTitle.getText().equals("Bills Pay Maintenance")) {

      } else {
         markTestStatus("failed", "FAILED: Bills Pay Maintenance title is not found.", driver);
         errorcounter++;
      }

      // Checking Maintenance Spiel
      AndroidElement MaintenananceSpiel = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
            .id("section_maintenance_message")));

    
      if (MaintenananceSpiel.getText().contains("We have ongoing maintenance. Please try again later. Thank you for your understanding.")) {

      } else {
         markTestStatus("failed", "FAILED: Bills Pay Maintenance Spiel is not found.", driver);
         errorcounter++;
      }

      // Clicking Back Button

      // Checking Maintenance Image
      List<AndroidElement> backButton = driver.findElements(By.className("android.widget.ImageButton"));
      System.out.println("The number of back button is :" + backButton.size());
      backButton.get(0).click();
   }

   @Test(priority = 15, enabled = true)
   public void errorCheckr() {

      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Bills Pay > No available billspay category, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Bills Pay > No available billspay category Successful.", driver);
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
