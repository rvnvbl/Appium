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
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC7_4_PlaceOrder_Android_InStore_ZeroPayment extends AndroidSetup {
   String SubTotalString = "";
   String DateString1 = "";
   String TimeString1 = "";
   String PaymentMethodString = "";
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

         System.out.println("Test passed: Redirect to Login page successful");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_Instore_ZeroPayment_Method(String email, String password) throws Exception {

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

         // Checking if Puregold Image from homepage is visible to know if login is successful
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

         // Checking if Store search bar from In-store pickup is visible to know if redirect is successful
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
                     "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PUREGOLD MAKATI\").instance(0))")));
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
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PUREGOLD MAKATI\").instance(0))")));

            RedoSpecificStoreButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
         } catch (Exception e) {
            System.out.println("Change branch modal did not appear, Work-around not needed");
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) button error/not found.", driver);
      }

      // Checking of store
      try {
         // Checking if Specific store at top is visible and correct (PUREGOLD MAKATI)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PUREGOLD MAKATI")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
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
      try {
         MobileElement GlobeSimItem = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"TRI-CUT LTE SIMS\"));");

         GlobeSimItem.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Globe product (TRI-CUT LTE SIMS) error/not found.", driver);
      }

      // Checking if Globe product item description is visible for redirect confirmation
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
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart button error / not found.", driver);
      }

      // Checking if Quantity, add and trash icon elements are visible instead of add to cart button
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
   public void NavToCartTest() throws Exception {

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

         if (StoreHeaderString.equals("PUREGOLD MAKATI")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) did not redirect correctly.", driver);
      }

      // Clicking of cart button
      try {
         AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "nav_cart")));

         CartButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart Button error / not found.", driver);
      }

      // Checking if redirect to Cart page is successful
      try {
         AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         String CartTitleText = CartTitle.getText();

         if (CartTitleText.equals("Cart")) {
            System.out.println("Successfully redirected to Cart page");
         } else {
            markTestStatus("failed", "FAILED: Cart page title error / not found", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
      }

   }

   @Test(priority = 7)
   public void CartQuantityFieldInputTest() throws Exception {
      // Clearing the Quantity field and input the set value
      try {
         AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("cart_quantity")));

         QuantityField.clear();

         QuantityField.sendKeys("30");

         driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Quantity Field error / not found.", driver);
      }

      // Comparing the updated Sub-total if it has changed
      try {
         AndroidElement CartSubtotalElement = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("cart_subtotal_value")));

         String UpdatedSubtotal = CartSubtotalElement.getText();

         driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

         if (UpdatedSubtotal.equals(SubTotalString)) {
            markTestStatus("failed", "FAILED: Sub-total did not change.", driver);
         } else {
            System.out.println("Sub-total changed after increasing quantity");
            SubTotalString = UpdatedSubtotal;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Sub-total element error / not found.", driver);
      }
   }

   @Test(priority = 8)
   public void CartCheckoutTest() throws Exception {
      try {
         // Clicking the Checkout button
         AndroidElement CheckoutButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("cart_checkout")));

         CheckoutButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout button error / not found.", driver);
         errorcounter++;
      }

      // Checking if redirect to Checkout page is successful
      try {
         AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         String CartTitleText = CartTitle.getText();

         if (CartTitleText.equals("Checkout")) {
            System.out.println("Successfully redirected to Checkout page");
            markTestStatus("passed", "PASSED: Cart Module successful", driver);
         } else {
            markTestStatus("failed", "FAILED: Checkout page title error / not found", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout page title is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 9)
   public void ScheduleChevronButtonTest() throws Exception {
      try {
         // Clicking the Chevron button on schedule
         AndroidElement ScheduleChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_delivery_schedule_arrow")));

         ScheduleChevronButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout Schedule button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking and clicking of schedule full modal if it appears
         AndroidElement ScheduleFullModal = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("bPositive")));

         ScheduleFullModal.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         System.out.println("Schedule full modal did not appear.");
      }

      try {
         // Checking if successfully redirected to page
         AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((ScheduleTitle.getText()).equals("Pick-up Schedule")) {
            System.out.println("Successfully redirected to schedule page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Schedule page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Schedule page.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 10)
   public void DesiredDateTest() throws Exception {
      try {
         // Listing and always selecting the fifth Date which is highlighted
         List<AndroidElement> DateList = driver.findElements(By.id("scheduler_date"));

         // Checking all results
         for (int i = 0; i < DateList.size(); i++) {
            AndroidElement SpecificDateElement = DateList.get(i);

            if (i == 4) {
               SpecificDateElement.click();

               DateString1 = SpecificDateElement.getText().toUpperCase();

               System.out.println(DateString1);

               // checking if 0 is detected on date and removal of it
               try {
                  if ((DateString1.indexOf("0")) == 4) {
                     DateString1 = DateString1.substring(0, 3) + " " + DateString1.substring(5);

                     System.out.println(DateString1);
                  }
               } catch (Exception e) {
                  System.out.println("no zeroes detected on date, no fix needed");
               }

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

               break;
            }
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Highlighted Date error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 11)
   public void DesiredTimeTest() throws Exception {
      try {
         // Listing and selecting the first time that is available
         List<AndroidElement> TimeList = driver.findElements(By.id("scheduler_remarks"));

         // Checking all results
         for (int i = 0; i < TimeList.size(); i++) {
            AndroidElement SpecificTimeElement = TimeList.get(i);

            String SpecificTimeAvailabilityText = SpecificTimeElement.getText();
            if (SpecificTimeAvailabilityText.equals("AVAILABLE")) {
               List<AndroidElement> TimeRanges = driver.findElements(By.id("scheduler_time_slot"));
               MobileElement SpecificTime = TimeRanges.get(i);

               TimeString1 = SpecificTime.getText();

               System.out.println(TimeString1);

               List<AndroidElement> TimeRadioButtons = driver.findElements(By.id("scheduler_radio"));
               MobileElement SpecificTimeRadioButton = TimeRadioButtons.get(i);

               SpecificTimeRadioButton.click();

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

               break;
            }
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific time error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking the time and date if it is correct
         AndroidElement TimeAndDate = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "checkout_delivery_schedule_info")));

         String TimeAndDateText = TimeAndDate.getText();

         System.out.println(TimeAndDateText);

         if (TimeAndDateText.contains(DateString1) && TimeAndDateText.contains(TimeString1)) {
            System.out.println("Time and date is correct");
         } else {
            markTestStatus("failed", "FAILED: Time and Date is not correct.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Time and Date error / not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 12)
   public void PaymentChevronTest() throws Exception {
      try {
         // Clicking the Chevron button on Payment method
         AndroidElement PaymentMethodChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("checkout_payment_arrow")));

         PaymentMethodChevronButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to page
         AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((ScheduleTitle.getText()).equals("Payment Method")) {
            System.out.println("Successfully redirected to Payment Method page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Payment Method page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 13)
   public void GCashMethodTest() throws Exception {
      try {
         // Clicking the GCash payment option
         AndroidElement GCashOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"GCash Direct\"));")));

         GCashOption.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: GCash Direct Payment method button error / not found.", driver);
      }

      try {
         // Checking if successfully redirected to checkout page
         AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((CheckoutTitle.getText()).equals("Checkout")) {
            System.out.println("Successfully redirected to Checkout page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully changed payment method
         AndroidElement PaymentMethodElement = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_payment_info")));

         if ((PaymentMethodElement.getText()).equals("GCash Direct")) {
            System.out.println("Successfully changed payment method to GCash Direct");
         } else {
            markTestStatus("failed", "FAILED: changed payment method to GCash Direct failed", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 14)
   public void ApplyVoucherTest() throws Exception {
      try {
         // Getting the initial value of the subtotal
         AndroidElement TotalAmount = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "checkout_bottom_total")));

         SubTotalString = TotalAmount.getText();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Total Amount element error / not found.", driver);
         errorcounter++;
      }

      try {
         // Clicking the Vouchers chevron button
         AndroidElement VoucherChevron = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("checkout_voucher_arrow")));

         VoucherChevron.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Voucher button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to checkout page
         AndroidElement VoucherTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         if ((VoucherTitle.getText()).equals("My Vouchers")) {
            System.out.println("Successfully redirected to Vouchers page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Vouchers page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Vouchers page.", driver);
         errorcounter++;
      }

      try {
         // Voucher usage (Initial check by verifying if there is an existing voucher that can be used)
         AndroidElement VoucherUseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"USE NOW\"));")));

         VoucherUseButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         System.out.println("No existing vouchers found, proceeding to entering new voucher");

         try {
            // Entering of voucher code
            AndroidElement VoucherTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("voucher_redeem_text")));

            VoucherTextField.click();

            VoucherTextField.sendKeys("AutoVoucher0602");

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Voucher Text Field not found", driver);
            errorcounter++;
         }

         try {
            // clicking of voucher redeem button
            AndroidElement VoucherRedeem = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("voucher_redeem_button")));

            VoucherRedeem.click();

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Voucher Redeem Button not found", driver);
            errorcounter++;
         }

         try {
            // Checking if successfully reloaded voucher page
            AndroidElement VoucherTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("title")));

            if ((VoucherTitle.getText()).equals("My Vouchers")) {
               System.out.println("Successfully reloaded Vouchers page");
            } else {
               markTestStatus("failed", "FAILED: Did not reload Vouchers page", driver);
               errorcounter++;
            }

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception b) {
            markTestStatus("failed", "FAILED: Did not redirect to Vouchers page.", driver);
            errorcounter++;
         }

         try {
            // clicking of voucher use button
            AndroidElement VoucherUseAfterRedeemButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(
                  MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
                        + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView(" + "new UiSelector().text(\"USE NOW\"));")));

            VoucherUseAfterRedeemButton.click();

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Voucher Use Button not found", driver);
            errorcounter++;
         }
      }

      try {
         // Checking if successfully redirected to checkout page
         AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((CheckoutTitle.getText()).equals("Checkout")) {
            System.out.println("Successfully redirected to Checkout page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Checkout page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 15)
   public void CheckoutSubtotalTest() throws Exception {
      try {
         // Checking if Total updated after using voucher
         AndroidElement TotalElement = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "checkout_bottom_total")));

         if ((TotalElement.getText()).equals(SubTotalString)) {
            markTestStatus("failed", "FAILED: Subtotal did not change after applying voucher", driver);
            errorcounter++;
         } else {
            System.out.println("Successfully applied voucher");
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Total Element error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 16)
   public void PlaceOrderTest() throws Exception {
      try {
         // Clicking the Place order button
         AndroidElement PlaceOrderButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("checkout_place_order")));

         PlaceOrderButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Place order button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if successfully redirected to Confirmed order page
         AndroidElement CheckoutTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((CheckoutTitle.getText()).equals("Confirmation")) {
            System.out.println("Successfully redirected to Confirmation page");
         } else {
            markTestStatus("failed", "FAILED: Did not redirect to Confirmation page", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Confirmation page.", driver);
         errorcounter++;
      }

      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Place Order > In store Zero Payment method Module test, Please see logs for details",
               driver);
      } else {
         markTestStatus("passed", "Place Order > In store Zero payment method Module Successful", driver);
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
