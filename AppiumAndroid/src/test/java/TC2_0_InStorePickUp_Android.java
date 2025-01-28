import java.time.Duration;
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

public class TC2_0_InStorePickUp_Android extends AndroidSetup {

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

         System.out.println("Test passed: Redirect to Login page successful");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_inStorePickup(String email, String password) throws Exception {

      // Clicking and input of email on email field
      try {
         AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "login_email_field")));
         EmailField.click();
         EmailField.sendKeys(email);

         System.out.println("Test passed: Email input success");
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
            System.out.println("Test passed: Password is hidden");
         } else {
            System.out.println("Test failed: Password is not hidden");
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
            System.out.println("Test passed: Password is shown");
         } else {
            System.out.println("Test failed: Password is hidden");
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
      }

   }

   @Test(priority = 3)
   public void TapInStorePickUpTest() throws Exception {
      // Scrolling to and clicking the pick up in store button
      try {
         MobileElement PickUpOptionButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");
         PickUpOptionButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Checking if Store search bar from In-store pickup is visible to know if
         // redirect is successful
         AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("store_search_bar")));

         markTestStatus("passed", "In Store Pick Up > Store List successful", driver);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: In-store Pickup button is not found.", driver);
      }
   }

   @Test(priority = 4)
   public void StoreTapTest() throws Exception {
      // Create a WebDriverWait instance with a timeout (in seconds)
      WebDriverWait wait = new WebDriverWait(driver, 30);

      // Clicking the specific store
      try {

         // Use WebDriverWait to wait until the element is clickable
         AndroidElement SpecificStoreButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
               .findElementByAndroidUIAutomator(
                     "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PPCI - NORTH COMMONWEALTH\").instance(0))")));

         SpecificStoreButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Verifying and fix if modal appears on first branch option
         try {
            AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
                  "bPositive")));

            YesButton.click();

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         } catch (Exception e) {
            System.out.println("Change branch modal did not appear, Work-around not needed");
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) button error/not found.", driver);
      }

      // Checking of store
      try {
         // Checking if Specific store at top is visible and correct (PPCI - NORTH
         // COMMONWEALTH)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PPCI - NORTH COMMONWEALTH")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
      }
   }

   @Test(priority = 5)
   public void DiffStoreTest() throws Exception {
      // Create a WebDriverWait instance with a timeout (in seconds)
      WebDriverWait wait = new WebDriverWait(driver, 30); // Adjust the timeout as needed
      try {
         // Go back to select store
         driver.pressKey(new KeyEvent(AndroidKey.BACK));
         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         // Checking if Store search bar from In-store pickup is visible to know if
         // redirect is successful
         AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("store_search_bar")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not go back to store selection screen correctly.", driver);
      }

      // Clicking the different store
      try {

         // Use WebDriverWait to wait until the element is clickable
         AndroidElement SpecificStoreButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
               .findElementByAndroidUIAutomator(
                     "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PUREGOLD DEPARO\").instance(0))")));

         SpecificStoreButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Checking if confirmation modal (YES AND NO Buttons) appeared
         AndroidElement NoButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "bNegative")));

         AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "bPositive")));

         System.out.println("Test passed: Change store location modal appeared");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) button error/not found.", driver);
      }
   }

   @Test(priority = 6)
   public void NoModalTest() throws Exception {
      try {
         // Clicking of NO option on modal
         AndroidElement NoButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "bNegative")));

         NoButton.click();

         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

         // Checking if Store search bar from In-store pickup is visible to know if
         // redirect is successful
         AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("store_search_bar")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not go back to store selection screen correctly.", driver);
      }
   }

   @Test(priority = 7)
   public void YesModalTest() throws Exception {
      // Clicking the different store
      try {
         AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"PUREGOLD DEPARO\"));")));
         SpecificStoreButton.click();

         // Clicking of YES on change store location modal
         AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "bPositive")));

         YesButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Checking if store text at top is visible and correct (PUREGOLD 888 CHINATOWN
         // SQUARE)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PUREGOLD DEPARO")) {
            System.out.println("Test passed: Specific store redirected to is correct");
            markTestStatus("passed", "In Store Pick Up > Store Selection successful", driver);
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) button error/not found.", driver);
      }
   }

   @Test(priority = 8)
   public void SearchKeyboardTest() throws Exception {
      try {
         // Go back to select store
         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         // Checking and clicking of Store search bar from In-store pickup
         AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("store_search_bar")));

         StoreSearchBar.click();

         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: In-store Search bar is not found.", driver);
      }

      try {
         // Checking if On-screen keyboard appeared
         boolean isKeyboardShown = driver.isKeyboardShown();

         if (isKeyboardShown) {
            System.out.println("Test passed: On-screen keyboard displayed");
         } else {
            markTestStatus("failed", "FAILED: On-screen keyboard did not display", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
      }

   }

   @Test(priority = 9)
   public void SearchKeywordTest() throws Exception {

      // Clicking and Searching for a specific store (Puregold Makati)
      try {
         AndroidElement StoreSearchField = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "store_search_bar")));
         StoreSearchField.click();
         StoreSearchField.sendKeys("Puregold Makati");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Store search field error/not found", driver);
      }

      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

      // Clicking the specific store search result
      try {
         AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"PUREGOLD MAKATI\"));")));
         SpecificStoreButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store (PUREGOLD MAKATI) error/not found", driver);
      }

      try {
         // Clicking of YES on change store location modal
         AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "bPositive")));

         YesButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Checking if store text at top is visible and correct (PUREGOLD MAKATI)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PUREGOLD MAKATI")) {
            System.out.println("Test passed: Specific store redirected to is correct");
            markTestStatus("passed", "In Store Pick Up > Search Store successful", driver);
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) button error/not found.", driver);
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
                  .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release()
                  .perform();
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
