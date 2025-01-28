import java.time.Duration;
import java.util.List;
import org.apache.tools.ant.taskdefs.MacroDef.Text;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class TC29_0_Signup_TermsAndCondition extends AndroidSetup {

   int errorcounter = 0;

   @Test(priority = 0)
   public void SignUpWithEmailButtonTes() throws Exception {
      try {
         // Clicking sign-up with email button
         AndroidElement signUpWithEmailButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .id("landing_signup")));
         signUpWithEmailButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Sign-up with email button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1)
   private void signUpPageTest() {
      // Checking sign-up title
      try {
         AndroidElement signUpTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "register_title")));
         Assert.assertTrue(signUpTitle.getText().equals("Sign Up"));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Sign-up title is not found.", driver);
         errorcounter++;
      }

      // Checking firstname textfield
      try {
         AndroidElement firstNameTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("register_firstname_field")));
         Assert.assertTrue(firstNameTextfield.getText().equals("First Name"));
         firstNameTextfield.clear();
         firstNameTextfield.sendKeys("TestFirstName");

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Firstname textfield is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Firstname textfield text is not correct.", driver);
         errorcounter++;
      }

      // Checking lastname textfield
      try {
         AndroidElement lastNameTexfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("register_lastname_field")));
         Assert.assertTrue(lastNameTexfield.getText().equals("Last name"));
         lastNameTexfield.clear();
         lastNameTexfield.sendKeys("TestLastName");

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Lastname textfield is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Lastname textfield text is not correct.", driver);
         errorcounter++;
      }

      // Checking email textfield
      try {
         AndroidElement emailTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("register_email_field")));
         Assert.assertTrue(emailTextField.getText().equals("Email"));
         emailTextField.clear();
         emailTextField.sendKeys("TestEmail@yopmail.com");

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Email textfield is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Email textfield text is not correct.", driver);
         errorcounter++;
      }

      // Checking password textfield
      try {
         AndroidElement passwordTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("register_password_field")));
         Assert.assertTrue(passwordTextField.getText().equals("Password"));
         passwordTextField.clear();
         passwordTextField.sendKeys("Password123!");

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Password textfield is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Password textfield text is not correct.", driver);
         errorcounter++;
      }

      // Checking referral textfield
      try {
         AndroidElement referralTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("register_referral_code_field")));
         Assert.assertTrue(referralTextField.getText().equals("Referral Code (Optional)"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Referral textfield is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Referral textfield text is not correct.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 2)
   private void termsAndConditionTestDecline() {
      // Checking checkbox terms and condition
      try {
         AndroidElement tAndCCheckbox = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("register_note_checkbox")));
         Assert.assertTrue(tAndCCheckbox.getAttribute("checked").equals("false"));
         tAndCCheckbox.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkbox is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Checkbox is already checked.", driver);
         errorcounter++;
      }

      // Checking agree buton
      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));
         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("false"));
         agreeButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is already enabled.", driver);
         errorcounter++;
      }

      // Getting all the textfield in the modal
      List<AndroidElement> textListOnModal = driver.findElementsByClassName("android.widget.TextView");
      swipe(driver, 515, 1200, 490, 773, 500);

      // Checking agree buton

      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));

         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("true"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is still disable.", driver);
         errorcounter++;
      }

      // Checking decline buton
      try {
         AndroidElement declineButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("btnDecline")));

         Assert.assertTrue(declineButton.getAttribute("enabled").equals("true"));
         declineButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Decline button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Decline button is disable.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 3)
   private void termsAndConditionTestAgree() {
      // Checking checkbox terms and condition
      try {
         AndroidElement tAndCCheckbox = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("register_note_checkbox")));
         Assert.assertTrue(tAndCCheckbox.getAttribute("checked").equals("false"));
         tAndCCheckbox.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkbox is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Checkbox is already checked.", driver);
         errorcounter++;
      }

      // Checking agree buton
      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));
         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("false"));
         agreeButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is already enabled.", driver);
         errorcounter++;
      }

      // Getting all the textfield in the modal
      swipe(driver, 515, 1200, 490, 773, 500);

      // Checking agree buton

      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));

         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("true"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is still disable.", driver);
         errorcounter++;
      }

      // Checking decline buton
      try {
         AndroidElement declineButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("btnDecline")));

         Assert.assertTrue(declineButton.getAttribute("enabled").equals("true"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Decline button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Decline button is disable.", driver);
         errorcounter++;
      }

      // Checking agree buton
      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));

         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("true"));
         agreeButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is still disable.", driver);
         errorcounter++;
      }

      // Checking next buton
      try {
         AndroidElement nextButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "register_next")));
         Assert.assertTrue(nextButton.getAttribute("enabled").equals("true"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Next button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Next button is still disable.", driver);
         errorcounter++;
      }

      // Checking checkbox terms and condition
      try {
         AndroidElement tAndCCheckbox = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("register_note_checkbox")));
         Assert.assertTrue(tAndCCheckbox.getAttribute("checked").equals("true"));
         tAndCCheckbox.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkbox is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Checkbox is not checked.", driver);
         errorcounter++;
      }

      // Checking agree buton
      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));
         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("false"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is already enabled.", driver);
         errorcounter++;
      }

      // Checking decline buton
      try {
         AndroidElement declineButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("btnDecline")));

         Assert.assertTrue(declineButton.getAttribute("enabled").equals("true"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Decline button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Decline button is disable.", driver);
         errorcounter++;
      }

      // Checking agree buton
      swipe(driver, 515, 1200, 490, 773, 500);
      try {
         AndroidElement agreeButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "btnAgree")));
         Assert.assertTrue(agreeButton.getAttribute("enabled").equals("true"));
         agreeButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Agree button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Agree button is still disable.", driver);
         errorcounter++;
      }

      // Checking next buton
      try {
         AndroidElement nextButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "register_next")));

         Assert.assertTrue(nextButton.getAttribute("enabled").equals("true"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Next button is not found.", driver);
         errorcounter++;
      } catch (AssertionError e) {
         markTestStatus("failed", "FAILED: Next button is still disable.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 8)
   private void checkError() {
      if (errorcounter > 0) {
         markTestStatus("failed", "FAILED: Signup > Terms and condition failed", driver);
      } else {
         markTestStatus("passed", "SUCCESS: Signup > Terms and condition successful", driver);
      }
   }

   // This method accepts the status, reason and WebDriver instance and marks the
   // test on BrowserStack
   public static void markTestStatus(String status, String reason, WebDriver driver) {
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

   public static void swipe(AndroidDriver<AndroidElement> driver, int startX, int startY, int endX, int endY, int duration) {
      TouchAction touchAction = new TouchAction(driver);
      touchAction.press(PointOption.point(startX, startY)).waitAction().moveTo(PointOption.point(endX, endY)).release().perform();
      System.out.println("Swiped!");
   }

}
