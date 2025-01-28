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
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.ExcelDataSupllier;

public class TC28_1_Checkout_ExpiredLoyalCard extends AndroidSetup {
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

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldTest_ExpiredLoyaltyCard(String email, String password) throws Exception {

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
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"MANLESS STORE\").instance(0))")));

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
                           "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"MANLESS STORE\").instance(0))")));

               RedoSpecificStoreButton.click();

               driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            } catch (Exception a) {
               System.out.println("Change branch modal did not appear, Work-around not needed");
            }

         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Specific Store(MANLESS STORE) button error/not found.", driver);
            errorcounter++;
         }

         // Checking of store
         try {
            // Checking if Specific store at top is visible and correct (MANLESS STORE)
            AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("toolbar_catalog_text_place")));
            String StoreHeaderString = StoreHeader.getText();

            if (StoreHeaderString.equals("MANLESS STORE")) {
               System.out.println("Test passed: Specific store redirected to is correct");
            } else {
               markTestStatus("failed", "FAILED: Specific Store(PUREGOLD AGORA) did not redirect correctly.", driver);
            }
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD AGORA) did not redirect correctly.", driver);
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
            // Clicking of search field in search and searching for specific products (GLOBE
            // CALLCARD P500 prepaid wifi)
            AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                  .id("toolbar_searchbar")));

            SearchField.click();

            SearchField.sendKeys("NESCAFE DISCOVER A DARK ROAST");

            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            TouchAction touchAction = new TouchAction(driver);
            touchAction.tap(PointOption.point(1000, 2100)).perform();

            driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Search field error / not found.", driver);
            errorcounter++;
         }

         try {
            // Checking of products if all results has PURE BASICS PEANUT BUTTER CREAMY 510G
            // in
            // keywords
            List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

            // Checking all results
            for (int i = 0; i < Products.size(); i++) {
               AndroidElement SpecificProductName = Products.get(i);

               String SpecificProductNameText = SpecificProductName.getText();
               if (SpecificProductNameText.contains("NESCAFE DISCOVER A DARK ROAST")) {
                  System.out.println("Product contains PURE BASICS PEANUT BUTTER CREAMY 510G keyword");
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

            if (StoreHeaderString.equals("MANLESS STORE")) {
               System.out.println("Test passed: Specific store redirected to is correct");
            } else {
               markTestStatus("failed", "FAILED: Specific Store(PUREGOLD AGORA) did not redirect correctly.", driver);
               errorcounter++;
            }
         } catch (Exception a) {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD AGORA) did not redirect correctly.", driver);
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
   private void expiredLoyaltyCardButtonTest() {
      // Scroll until the expired loyalty card label
      WebDriverWait wait = new WebDriverWait(driver, 30);
      try {
         AndroidElement loyaltyCardExpirationLabel = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(driver
               .findElementByAndroidUIAutomator(
                     "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Expired Loyalty Card\").instance(0))")));
         loyaltyCardExpirationLabel.isDisplayed();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Expired loyalty card label is not found.", driver);
         errorcounter++;
      }

      // Check if expired loyalty card toggle shows
      try {
         AndroidElement loyaltyCardExpirationToggleButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
               .visibilityOfElementLocated(MobileBy.id("checkout_redeem_expired_switch")));
         Assert.assertTrue(loyaltyCardExpirationToggleButton.isDisplayed());
         loyaltyCardExpirationToggleButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Loyalty card expired button error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6)
   private void expiredLoyaltyCardModalTest() {

      // Get all the text in the modal
      try {
         Thread.sleep(2000);
         int counter = 0;
         List<AndroidElement> textList = driver.findElementsByClassName("android.widget.TextView");
         for (int i = 0; i < textList.size(); i++) {
            if (textList.get(i).getText().equals("Expired Loyalty Card")) {
               counter++;
            }

            if (textList.get(i).getText().equals(
                  "Sorry, your Perks Card is expired and will not be eligible to earn points for this order. Renew your Perks Card to resume earning points.")) {
               counter++;
            }
         }
         if (counter != 2) {
            markTestStatus("failed", "FAILED: The text in the modal is not correct.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: The text in the modal error / not found.", driver);
         errorcounter++;
      }

      // Check if renew button is found on modal
      try {
         AndroidElement modalRenewButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("btnRenew")));
         Assert.assertTrue(modalRenewButton.isEnabled());

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Modal renew button error / not found.", driver);
         errorcounter++;
      }

      // Check if go back button is found on modal
      try {
         AndroidElement modalGoBackButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("btnGoBack")));
         Assert.assertTrue(modalGoBackButton.isEnabled());
         modalGoBackButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Modal go back button error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 7)
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
         // Checking of products if a product is seen (PURE BASICS PEANUT BUTTER CREAMY
         // 510G) in keywords
         List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

         // Checking all results
         for (int i = 0; i < Products.size(); i++) {
            AndroidElement SpecificProductName = Products.get(i);

            String SpecificProductNameText = SpecificProductName.getText();
            if (SpecificProductNameText.contains("NESCAFE DISCOVER A DARK ROAST")) {
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
   }

   @Test(priority = 8)
   private void checkError() {
      if (errorcounter > 0) {
         markTestStatus("failed", "FAILED: Checkout > Expired Loyalty card test failed", driver);
      } else {
         markTestStatus("passed", "SUCCESS: Checkout > Expired loyalty card test successful", driver);
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
