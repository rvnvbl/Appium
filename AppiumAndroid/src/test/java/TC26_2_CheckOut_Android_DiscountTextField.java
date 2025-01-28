import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tools.ant.taskdefs.condition.And;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC26_2_CheckOut_Android_DiscountTextField extends AndroidSetup {
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

         System.out.println("Test 1: Redirect to Login page successful");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_Checkout_Enhancement(String email, String password) throws Exception {

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
   public void TapCartButtonTest() throws Exception {
      // Clicking the cart button
      try {
         AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "home_cart")));

         CartButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {

         // Scrolling to and clicking the pick up in store button
         try {
            MobileElement PickUpOptionButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
                  + ".scrollable(true)).scrollIntoView" + "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");
            PickUpOptionButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Checking if Store search bar from In-store pickup is visible to know if
            // redirect is successful
            AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("store_search_bar")));

            markTestStatus("passed", "In Store Pick Up > Store List successful", driver);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: In-store Pickup button is not found.", driver);
            errorcounter++;
         }

         // Clicking the specific store
         try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            AndroidElement SpecificStoreButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
                  .findElementByAndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PPCI - NORTH COMMONWEALTH\").instance(0))")));
            SpecificStoreButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Verifying and fix if modal appears on first branch option
            try {
               AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                     .id("bPositive")));

               YesButton.click();

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

               // Go back to select store
               driver.pressKey(new KeyEvent(AndroidKey.BACK));

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

               AndroidElement RedoSpecificStoreButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
                     .findElementByAndroidUIAutomator(
                           "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PPCI - NORTH COMMONWEALTH\").instance(0))")));

               RedoSpecificStoreButton.click();

               driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            } catch (Exception a) {
               System.out.println("Change branch modal did not appear, Work-around not needed");
            }

         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) button error/not found.", driver);
            errorcounter++;
         }

         // Checking of store
         try {
            // Checking if Specific store at top is visible and correct (PPCI - NORTH
            // COMMONWEALTH)
            AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("toolbar_catalog_text_place")));
            String StoreHeaderString = StoreHeader.getText();

            if (StoreHeaderString.equals("PPCI - NORTH COMMONWEALTH")) {
               System.out.println("Test passed: Specific store redirected to is correct");
            } else {
               markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) did not redirect correctly.", driver);
            }
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) did not redirect correctly.", driver);
            errorcounter++;
         }

         try {
            Thread.sleep(5000);

            // Clicking of search field in store homepage
            AndroidElement HomeSearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("catalog_shop_search_text")));

            HomeSearchField.click();

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Store Homepage Search field error / not found.", driver);
            errorcounter++;
         }

         try {
            // Clicking of search field in search
            AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("toolbar_searchbar")));

            SearchField.click();

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Search field error / not found.", driver);
            errorcounter++;
         }

         try {
            // Checking if On-screen keyboard appeared
            boolean isKeyboardShown = driver.isKeyboardShown();

            if (isKeyboardShown) {
               System.out.println("Test passed: On-screen keyboard displayed");
            } else {
               markTestStatus("failed", "FAILED: On-screen keyboard did not display", driver);
               errorcounter++;
            }
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
            errorcounter++;
         }

         try {
            // Clicking of search field in search and searching for specific products (Globe
            // at home prepaid wifi)
            AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("toolbar_searchbar")));

            SearchField.click();

            SearchField.sendKeys("GLOBE CALLCARD P500");

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
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Search field error / not found.", driver);
            errorcounter++;
         }

         try {
            // Checking of products if all results has GLOBE CALLCARD P500 in
            // keywords
            List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

            // Checking all results
            for (int i = 0; i < Products.size(); i++) {
               AndroidElement SpecificProductName = Products.get(i);

               String SpecificProductNameText = SpecificProductName.getText();
               if (SpecificProductNameText.contains("GLOBE CALLCARD P500")) {
                  System.out.println("Product contains GLOBE CALLCARD P500 keyword");
                  System.out.println(i);
                  markTestStatus("passed", "PASSED: Add to Cart > Store Homepage successful", driver);
               } else {
                  markTestStatus("failed", "FAILED: Product with no keyword from search found", driver);
                  errorcounter++;
               }
            }

         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Search field error / not found.", driver);
            errorcounter++;
         }

         try {
            // Clicking of Add to cart on the product searched
            AndroidElement SearchedAddToCart = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("search_item_add_to_cart_status_text")));

            SearchedAddToCart.click();

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Product add to cart error / not found.", driver);
            errorcounter++;
         }

         try {
            // Checking if Add to cart is successful
            AndroidElement SearchedQuantity = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("search_product_quantity")));

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Quantity Field of product error / not found.", driver);
            errorcounter++;
         }

         try {
            // Adding quantity to product
            AndroidElement SearchedQuantityAdd = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                  MobileBy.id("search_product_quantity_add")));

            SearchedQuantityAdd.click();

            Thread.sleep(5000);

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Add button of product error / not found.", driver);
            errorcounter++;
         }

         try {
            // Go back to select store
            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Back button error", driver);
            errorcounter++;
         }

         // Checking if redirected back to store homepage
         try {
            // Checking if Specific store at top is visible and correct (PUREGOLD AGORA)
            AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("toolbar_catalog_text_place")));
            String StoreHeaderString = StoreHeader.getText();

            if (StoreHeaderString.equals("PPCI - NORTH COMMONWEALTH")) {
               System.out.println("Test passed: Specific store redirected to is correct");
            } else {
               markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) did not redirect correctly.", driver);
               errorcounter++;
            }
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) did not redirect correctly.", driver);
            errorcounter++;
         }

         // Clicking of cart button
         try {
            AndroidElement CartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("nav_cart")));

            CartButton.click();

            Thread.sleep(5000);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Cart Button error / not found.", driver);
            errorcounter++;
         }

      }

      // Checking if stock confirm appears
      try {
         AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "dialog_delivery_ok")));

         CartStock.click();
         TouchAction touchAction = new TouchAction(driver);
         touchAction.tap(PointOption.point(350, 300)).perform();
      } catch (Exception e) {
         System.out.println("Stock confirm on cart did not appear, no need to click");
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
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 4)
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

   @Test(priority = 5)
   public void BranchVerifyTest() throws Exception {
      try {
         // Checking if Branch is visible
         AndroidElement CheckoutBranch = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("checkout_branch_info")));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout branch error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6)
   public void ContactVerifyTest() throws Exception {
      try {
         // Checking if Contact details is visible
         AndroidElement CheckoutContact = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("checkout_card_contact_info")));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout Contact details error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 7)
   public void ScheduleChevronButtonTest() throws Exception {
      try {
         // Clicking the Chevron button on schedule
         AndroidElement ScheduleChevronButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_delivery_schedule_arrow")));

         ScheduleChevronButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout Schedule button error / not found.", driver);
      }

      try {
         // Checking if successfully redirected to page
         AndroidElement ScheduleTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         if ((ScheduleTitle.getText()).equals("Delivery Schedule")) {
            System.out.println("Successfully redirected to schedule page");
         } else if ((ScheduleTitle.getText()).equals("Pick-up Schedule")) {
            System.out.println("Successfully redirected to schedule page");
         }

         else {
            markTestStatus("failed", "FAILED: Did not redirect to Schedule page", driver);
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Schedule page.", driver);
      }
   }

   @Test(priority = 8)
   public void DesiredDateTest() throws Exception {
      try {
         // Listing and always selecting the fourth Date which is highlighted
         List<AndroidElement> DateList = driver.findElements(By.id("scheduler_date"));

         // Checking all results
         for (int i = 0; i < DateList.size(); i++) {
            AndroidElement SpecificDateElement = DateList.get(i);

            if (i == 3) {
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

   @Test(priority = 9)
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

   @Test(priority = 10, enabled = true)
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

   @Test(priority = 14, enabled = false)
   public void GCashMethodTest() throws Exception {
      try {
         // Clicking the GCash Direct payment option
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

      try {
         // Clicking and testing the Special Instructions field
         AndroidElement InstructionsField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_instruction")));

         InstructionsField.click();
         InstructionsField.clear();
         driver.navigate().back();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         markTestStatus("failed", "FAILED: Instruction field error / not found.", driver);
         errorcounter++;
      }

      // Checking the pwallet discount title
      try {
         AndroidElement pwalletDiscountText = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_title")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount Title not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount button
      try {
         AndroidElement pwalletDiscountbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_switch")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount button not found", driver);
         errorcounter++;
      }
   }

   @Test(priority = 15, enabled = false)
   public void CreditDebitMethodTest() throws Exception {
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

      try {
         // Clicking the Credit / Debit card payment option
         AndroidElement CreditDebitOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"Credit / Debit Card\"));")));

         CreditDebitOption.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
         errorcounter++;
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

         if ((PaymentMethodElement.getText()).equals("Credit / Debit Card")) {
            System.out.println("Successfully changed payment method to Credit / Debit Card");
         } else {
            markTestStatus("failed", "FAILED: changed payment method to Credit / Debit Card failed", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
         errorcounter++;
      }

      try {
         // Clicking and testing the Special Instructions field
         AndroidElement InstructionsField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_instruction")));

         InstructionsField.click();
         InstructionsField.clear();
         driver.navigate().back();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         markTestStatus("failed", "FAILED: Instruction field error / not found.", driver);
         errorcounter++;
      }

      // Checking the pwallet discount title
      try {
         AndroidElement pwalletDiscountText = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_title")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount Title not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount button
      try {
         AndroidElement pwalletDiscountbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_switch")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount button not found", driver);
         errorcounter++;
      }
   }

   @Test(priority = 16, enabled = true)
   public void PayInStoreMethodTest() throws Exception {

      try {
         // Clicking the Pay In Store payment option
         AndroidElement payInStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"Pay In Store\"));")));

         payInStoreButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method button error / not found.", driver);
         errorcounter++;
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

         if ((PaymentMethodElement.getText()).equals("Pay In Store")) {
            System.out.println("Successfully changed payment method to Pay In Store");
         } else {
            markTestStatus("failed", "FAILED: changed payment method to Pay In Store failed", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment method info error / not found.", driver);
         errorcounter++;
      }

      try {
         // Clicking and testing the Special Instructions field
         AndroidElement InstructionsField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_instruction")));

         InstructionsField.click();
         InstructionsField.clear();
         driver.navigate().back();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         markTestStatus("failed", "FAILED: Instruction field error / not found.", driver);
         errorcounter++;
      }

      // Checking the pwallet discount title
      try {
         AndroidElement pwalletDiscountText = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_title")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount Title not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount button
      try {
         AndroidElement pwalletDiscountbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_switch")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount button not found", driver);
         errorcounter++;
      }
   }

   @Test(priority = 17, enabled = true)
   private void PwalletDiscountTest() {
      double dblInputDiscount = 50.10;
      double dblDiscountBreakdown = 0;
      double dblProductValue = 0;

      try {
         // Checking if Total Price is visible
         AndroidElement totalProductPrice = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_bottom_total")));
         dblProductValue = Double.parseDouble(extractValue(totalProductPrice.getText()));
         System.out.println("The product price is: " + dblProductValue);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout product value not found", driver);
         errorcounter++;
      }
      // Checking the pwallet discount button
      try {
         AndroidElement pwalletDiscountbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_switch")));
         pwalletDiscountbutton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount button not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount icon
      try {
         AndroidElement pwalletDiscountInfoIcon = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("iv_info")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount icon not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount label
      try {
         AndroidElement pwalletDiscountLabel = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount_label")));

         Assert.assertTrue(pwalletDiscountLabel.getText().contains("Enter Amount to use as discount"));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount label not found", driver);
         errorcounter++;
      }

      // Available Pwallet Balance Text
      try {
         int counterError = 0;
         List<AndroidElement> textElements = driver.findElements(By.className("android.widget.TextView"));
         for (int i = 0; i < textElements.size(); i++) {
            if (textElements.get(i).getText().contains("Available P-Wallet Balance:"))
               ;
            {
               counterError++;
               break;
            }
         }

         if (counterError <= 0) {
            markTestStatus("failed", "FAILED: Available PWallet balance text (Spiel) not found", driver);
            errorcounter++;
         }

         AndroidElement pwalletBalance = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("pwallet_balance")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Available PWallet balance text not found", driver);
         errorcounter++;
      }

      // Add discount button
      try {
         AndroidElement pwalletDiscountAddDiscountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("pwallet_discount_button")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Add Discount button not found", driver);
         errorcounter++;
      }

      // No number in the textfield
      // Checking the pwallet discount textfield
      try {
         AndroidElement pwalletDiscountTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount")));

         Assert.assertFalse(pwalletDiscountTextField.getText().equals(""));
         pwalletDiscountTextField.click();
         pwalletDiscountTextField.clear();

         pwalletDiscountTextField.sendKeys(String.valueOf(dblInputDiscount));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount textfield not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount button if disabled
      try {
         AndroidElement pwalletAddDiscountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount_button")));
         Assert.assertFalse(pwalletAddDiscountButton.isEnabled());
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount button must disabled", driver);
         errorcounter++;
      }

      // Entering discount less than the product total price
      // Checking the pwallet discount textfield
      try {
         AndroidElement pwalletDiscountTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount")));

         pwalletDiscountTextField.click();
         pwalletDiscountTextField.clear();
         pwalletDiscountTextField.sendKeys(String.valueOf(dblProductValue - 2));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount textfield not found", driver);
         errorcounter++;
      }

      // Checking if the error message found
      try {
         AndroidElement pwalletDiscountError = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount_error")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount error not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount button if disabled
      try {
         AndroidElement pwalletAddDiscountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount_button")));
         Assert.assertFalse(pwalletAddDiscountButton.isEnabled());
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: wallet Discount button must disabled", driver);
         errorcounter++;
      }

      // Checking the pwallet discount textfield
      try {
         AndroidElement pwalletDiscountTextField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount")));

         pwalletDiscountTextField.click();
         pwalletDiscountTextField.clear();
         pwalletDiscountTextField.sendKeys(String.valueOf(dblInputDiscount));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount textfield not found", driver);
         errorcounter++;
      }

   }

   @Test(priority = 18)
   private void switchToPwalletPaymentTest() {

      // Pwallet Discount Close Button
      try {
         AndroidElement pwalletDiscountCloseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("pwallet_discount_close")));

         pwalletDiscountCloseButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount close not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount button
      try {
         AndroidElement pwalletDiscountbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_switch")));
         pwalletDiscountbutton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount button not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount icon
      try {
         AndroidElement pwalletDiscountInfoIcon = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("iv_info")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount icon not found", driver);
         errorcounter++;
      }

      // Add discount button
      try {
         AndroidElement pwalletDiscountAddDiscountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("pwallet_discount_button")));
         pwalletDiscountAddDiscountButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Add Discount button not found", driver);
         errorcounter++;
      }

      // Gettting the modal title and message

      int counterText = 0;
      List<AndroidElement> textInModal = driver.findElements(By.className("android.widget.TextView"));
      for (int i = 0; i < textInModal.size(); i++) {
         String textInField = textInModal.get(i).getText();
         String noSpaceText = removeWhitespace(textInField);
         if (noSpaceText.equals("SwitchtoP-WalletPaymentMethod")) {
            counterText++;
         }

         if (noSpaceText.equals("Yourenteredamountissufficienttocoverthetotalcartamount,wewillswitchthepaymentmethodtoP-Wallet.")) {
            counterText++;
         }

      }
      System.out.println("The number of counter is: " + counterText);
      if (counterText >= 2) {
         System.out.println("All the text found");
      } else {
         markTestStatus("failed", "FAILED: Text in the modal (Switch to P-wallet Payment Method) not found", driver);
         errorcounter++;
      }

      // Proceed button
      try {
         AndroidElement proceedButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("btnProceed")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Proceed button not found", driver);
         errorcounter++;
      }

      // Change discount amount button
      try {
         AndroidElement changeDiscountAmountButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("btnChangeDiscountAmount")));
         changeDiscountAmountButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Change discount ammount button not found", driver);
         errorcounter++;
      }
   }

   @Test(priority = 19)
   private void discountGreaterThanProductPriceTest() {
      // Pwallet Discount Close Button
      try {
         AndroidElement pwalletDiscountCloseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("pwallet_discount_close")));

         pwalletDiscountCloseButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount close not found", driver);
         errorcounter++;
      }
      
      
      // Checking the pwallet discount button
      try {
         AndroidElement pwalletDiscountbutton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("checkout_pwallet_discount_switch")));
         pwalletDiscountbutton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet discount button not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount icon
      try {
         AndroidElement pwalletDiscountInfoIcon = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("iv_info")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet discount icon not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount textfield
      try {
         AndroidElement pwalletDiscountTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount")));

         pwalletDiscountTextfield.clear();
         pwalletDiscountTextfield.sendKeys("1000");
         driver.hideKeyboard();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet discount textfield not found", driver);
         errorcounter++;
      }

      // Checking the pwallet discount error
      try {
         AndroidElement pwalletDiscountError = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("pwallet_discount_error")));

         if (pwalletDiscountError.equals("The amount must be less than or equal to the Total Cart Amount")) {
            markTestStatus("passed", "PASSED: Pwallet discount error message shows", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet discount error message not found", driver);
         errorcounter++;
      }

   }

   @Test(priority = 20)
   public void CheckoutSubtotalTest() throws Exception {

      // Pwallet Discount Close Button
      try {
         AndroidElement pwalletDiscountCloseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("pwallet_discount_close")));

         pwalletDiscountCloseButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Pwallet Discount close not found", driver);
         errorcounter++;
      }

      try {
         // Checking if Total Price is visible
         AndroidElement TotalElement = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "checkout_bottom_total")));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Total Element error / not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 21)
   public void CartCleanUpTest() throws Exception {
      // Test is performed for reusability of test

      // Checking if redirect to Cart page is successful
      try {

         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         // Checking if stock confirm appears
         try {
            AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
                  "dialog_delivery_ok")));

            CartStock.click();
            TouchAction touchAction = new TouchAction(driver);
            touchAction.tap(PointOption.point(350, 300)).perform();
         } catch (Exception e) {
            System.out.println("Stock confirm on cart did not appear, no need to click");
         }

         AndroidElement CartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         String CartTitleText = CartTitle.getText();

         if (CartTitleText.equals("Cart")) {
            System.out.println("Successfully redirected back to Cart page");
         } else {
            markTestStatus("failed", "FAILED: Cart page title error / not found", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
         errorcounter++;
      }

      try {
         // Checking of products if a product is seen (GLOBE AT HOME) in keywords
         List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

         // Checking all results
         for (int i = 0; i < Products.size(); i++) {
            AndroidElement SpecificProductName = Products.get(i);

            String SpecificProductNameText = SpecificProductName.getText();
            if (SpecificProductNameText.contains("GLOBE CALLCARD P500")) {
               swipeElementAndroid(SpecificProductName, Direction.LEFT);
               driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            }
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
         errorcounter++;
      }

      try {
         // Checking if Remove button is found after swiping left
         AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "cart_remove")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Clicking the remove button
         AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "cart_remove")));

         RemoveButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
         errorcounter++;
      }

      try {
         // Clicking the Yes button in confirming the removal of product
         AndroidElement ConfirmRemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("bPositive")));

         ConfirmRemoveButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Confirm Remove button error / not found.", driver);
         errorcounter++;
      }

      if (errorcounter > 0) {
         markTestStatus("failed", "an error an occurred on Checkout > Pwallet Discount (Textfield Test) Error, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Checkout > Pwallet Discount Pwallet Discount (Textfield Test) Successful", driver);
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

   public static String extractValue(String input) {
      String pattern = "₱([\\d,.]+)"; // Regular expression to capture the value after '₱'
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(input);

      if (m.find()) {
         return m.group(1); // Return the matched group
      } else {
         return ""; // Return empty string if no match found
      }
   }

   public static String removeWhitespace(String input) {
      // Using regular expression to replace all whitespace characters
      return input.replaceAll("\\s", "");
   }
}
