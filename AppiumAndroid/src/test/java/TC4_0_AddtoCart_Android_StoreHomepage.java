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

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

import org.openqa.selenium.remote.DesiredCapabilities;

public class TC4_0_AddtoCart_Android_StoreHomepage extends AndroidSetup {

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
   public void LoginFieldsTest_addToCartStore_Homepage(String email, String password) throws Exception {

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

         System.out.println("Test passed: Login is successful");
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
      // Clicking the specific store
      try {

         WebDriverWait wait = new WebDriverWait(driver, 30);
         AndroidElement SpecificStoreButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
               .findElementByAndroidUIAutomator(
                     "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PUREGOLD DEPARO\").instance(0))")));

         SpecificStoreButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         // Verifying and fix if modal appears on first branch option
         try {
            AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
                  "bPositive")));

            YesButton.click();

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            // Go back to select store
            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            AndroidElement RedoSpecificStoreButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
                  .findElementByAndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PUREGOLD DEPARO\").instance(0))")));

            RedoSpecificStoreButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
         } catch (Exception e) {
            System.out.println("Change branch modal did not appear, Work-around not needed");
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) button error/not found.", driver);
      }

      // Checking of store
      try {
         // Checking if Specific store at top is visible and correct (PUREGOLD MAKATI)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PUREGOLD DEPARO")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) did not redirect correctly.", driver);
      }
   }

   @Test(priority = 5)
   public void StoreHomepageAddToCartTest() throws Exception {
      // Finding and Scrolling to Globe Product header
      try {
         MobileElement GlobeProductHeader = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"Globe Products\"));");

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Globe Products header not found", driver);
      }

      // Clicking of the specific Globe product (TRI-CUT LTE SIMS)
      // PROD = AT HOME PREPAID WIFI
      try {
         MobileElement GlobeSimItem = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"TM CALLCARD P50\"));");

         GlobeSimItem.click();
         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: TM CALLCARD P50 error/not found.", driver);
      }

      // Checking if Globe product item description is visible for redirect
      // confirmation
      try {
         AndroidElement ItemDescriptionCheck = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_description_title")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Item description element not found, redirect to specific product failed.", driver);
      }

      // Clicking the add to cart button of the product
      try {
         AndroidElement AddToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("add_to_cart")));

         AddToCartButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         Thread.sleep(3000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }

      // Checking if Quantity, add and trash icon elements are visible instead of add
      // to cart button
      try {
         AndroidElement MinusQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("item_product_quantity")));

         AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_add")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Trash icon, quantity and add elements not visible.", driver);
      }
   }

   @Test(priority = 6)
   public void TrashBinButtonTest() throws Exception {

      // Finding and Clicking of Trash bin button
      try {
         AndroidElement MinusQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         MinusQuantityButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         Thread.sleep(3000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Trash icon element error/not visible.", driver);
      }

      // Checking if Add to cart button is visible again
      try {
         AndroidElement AddToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("add_to_cart")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error/did not appear after clicking the trash bin button.", driver);
      }
   }

   @Test(priority = 7)
   public void IncreaseQuantityTest() throws Exception {

      // Clicking the add to cart button of the product
      try {
         AndroidElement AddToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("add_to_cart")));

         AddToCartButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

         Thread.sleep(3000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }

      // Checking if Quantity, add and trash icon elements are visible instead of add
      // to cart button
      try {
         AndroidElement MinusQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("item_product_quantity")));

         AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_add")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Trash icon, quantity and add elements not visible.", driver);
      }

      // Clicking the add quantity button of the product and checking the quantity if
      // it is now 2
      try {
         AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_add")));

         AddQuantityButton.click();

         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         Thread.sleep(3000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add Quantity button error / not found.", driver);
      }

      // Checking the quantity if it is now 2
      try {
         AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 39).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("item_product_quantity")));

         String QuantityText = QuantityField.getText();

         if (QuantityText.equals("2")) {
            System.out.println("Quantity is now 2");
         } else {
            markTestStatus("failed", "FAILED: Quantity of product did not increase.", driver);
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Quantity field error / not found.", driver);
      }
   }

   @Test(priority = 8)
   public void DecreaseQuantityTest() throws Exception {

      // Clicking the decrease quantity button of the product and checking the
      // quantity if it is now 1
      try {
         AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         AddQuantityButton.click();

         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         Thread.sleep(3000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Decrease Quantity button error / not found.", driver);
      }

      // Checking the quantity if it is now 1
      try {
         AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("item_product_quantity")));

         String QuantityText = QuantityField.getText();

         if (QuantityText.equals("1")) {
            System.out.println("Quantity is now 1");
         } else {
            markTestStatus("failed", "FAILED: Quantity of product did not decrease.", driver);
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Quantity field error / not found.", driver);
      }

   }

   // Additional test for restarting the product quantity and for continuity on
   // re-testing
   @Test(priority = 9)
   public void TrashButtonForResetTest() throws Exception {

      // Clicking the decrease quantity button of the product
      try {
         AndroidElement AddQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         AddQuantityButton.click();

         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         Thread.sleep(3000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Decrease Quantity button error / not found.", driver);
      }

      // Checking if Add to Cart Button is now visible
      try {
         AndroidElement AddToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("add_to_cart")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }
   }

   @Test(priority = 10)
   public void ScanAndGoTest() throws Exception {

      try {
         // Go back to select store
         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }

      // Checking if redirected back to store homepage
      try {
         // Checking if Specific store at top is visible and correct (PUREGOLD MAKATI)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PUREGOLD DEPARO")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD AGORA) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD AGORA) did not redirect correctly.", driver);
      }

      // Clicking of scan and go button and one time use of camera
      try {
         MobileElement ScanAndGoButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/catalog_shop_scan_and_go_image\"));");

         /*
          * AndroidElement ScanAndGoButton = (AndroidElement) new WebDriverWait(driver, 30).until(
          * ExpectedConditions.visibilityOfElementLocated(MobileBy.id( "catalog_shop_scan_and_go_image")));
          * 
          */

         ScanAndGoButton.click();

         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

         AndroidElement OneTimeUseCameraButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("com.android.permissioncontroller:id/permission_allow_one_time_button")));

         OneTimeUseCameraButton.click();

         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

         // Re-clicking of Scan and Go Button
         AndroidElement RedoScanAndGoButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("catalog_shop_scan_and_go_image")));
         RedoScanAndGoButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Scan and Go button error/cannot be found.", driver);
      }

      // Checking if barcode scanner is visible
      try {
         AndroidElement BarcodeScanner = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("barcode_scanner")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Barcode Scanner error/cannot be found.", driver);
      }

   }

   @Test(priority = 11)
   public void SeeMyVouchersTest() throws Exception {

      try {
         // Go back to select store
         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }

      // Checking if redirected back to store homepage
      try {
         // Checking if Specific store at top is visible and correct (PUREGOLD MAKATI)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PUREGOLD DEPARO")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
      }

      // Clicking of See my vouchers button
      try {
         AndroidElement ScanAndGoButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("see_voucher_action")));

         ScanAndGoButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: See my vouchers button error/cannot be found.", driver);
      }

      // Verifying if redirected to vouchers page successfully (title and button)
      try {
         AndroidElement VouchersField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("voucher_redeem_text")));

         AndroidElement VouchersPageTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("title")));
         String VouchersPageTitleText = VouchersPageTitle.getText();

         if (VouchersPageTitleText.equals("My Vouchers")) {
            System.out.println("Test passed: Redirect to vouchers is successful");
         } else {
            markTestStatus("failed", "FAILED: Vouchers did not redirect correctly.", driver);
         }

         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to vouchers page.", driver);
      }

   }

   @Test(priority = 12)
   public void StoreHomepageSearchFieldTest() throws Exception {

      try {
         // Go back to select store
         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }

      try {
         // Clicking of search field in store homepage
         AndroidElement HomeSearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("catalog_shop_search_text")));

         HomeSearchField.click();

         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Store Homepage Search field error / not found.", driver);
      }

      try {
         // Clicking of search field in search
         AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_searchbar")));

         SearchField.click();

         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Search field error / not found.", driver);
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

   @Test(priority = 13)
   public void StoreHomepageSearchKeywordTest() throws Exception {

      try {
         // Clicking of search field in search and searching for specific products
         // (Nescafe Black)
         AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_searchbar")));

         SearchField.click();

         SearchField.sendKeys("Nescafe Black");

         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

         // driver.pressKey(new KeyEvent(AndroidKey.SEARCH));
         // ((AndroidDriver<AndroidElement>) driver).pressKey(new
         // KeyEvent(AndroidKey.SEARCH));

         /*
          * driver.pressKey(new KeyEvent(AndroidKey.SEARCH) .withFlag(KeyEventFlag.SOFT_KEYBOARD)
          * .withFlag(KeyEventFlag.KEEP_TOUCH_MODE) .withFlag(KeyEventFlag.EDITOR_ACTION));
          */

         TouchAction touchAction = new TouchAction(driver);
         touchAction.tap(PointOption.point(1000, 2100)).perform();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Search field error / not found.", driver);
      }

      try {
         // Checking of products if all results has Nescafe Black in keywords
         List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

         // Checking all results
         for (int i = 0; i < Products.size(); i++) {
            AndroidElement SpecificProductName = Products.get(i);

            String SpecificProductNameText = SpecificProductName.getText();
            if (SpecificProductNameText.contains("NESCAFE BLACK")) {
               System.out.println("Product contains NESCAFE BLACK keyword");
               System.out.println(i);
               markTestStatus("passed", "PASSED: Add to Cart > Store Homepage successful", driver);
            } else {
               markTestStatus("failed", "FAILED: Product with no keyword from search found", driver);
            }
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Search field error / not found.", driver);
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
