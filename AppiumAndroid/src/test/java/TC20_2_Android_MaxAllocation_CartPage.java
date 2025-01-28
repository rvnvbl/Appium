import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
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
import util.ExcelDataSupllier;

public class TC20_2_Android_MaxAllocation_CartPage extends AndroidSetup {

   int errorCounter = 0;
   int productquantity = 0;
   int numberOfSold = 0;

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
         errorCounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_maxAllocation(String email, String password) throws Exception {

      // Clicking and input of email on email field
      try {
         AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "login_email_field")));
         EmailField.click();
         EmailField.sendKeys(email);

         System.out.println("Test passed: Email input success");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Email Field is not found.", driver);
         errorCounter++;
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
         errorCounter++;
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
            errorCounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Password Field is not found.", driver);
         errorCounter++;
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
         errorCounter++;
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
         errorCounter++;
      }
   }

   @Test(priority = 4)
   public void StoreTapTest() throws Exception {
      // Clicking the specific store
      try {
         AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"PPCI - NORTH COMMONWEALTH\"));")));
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
         errorCounter++;
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
         errorCounter++;
      }
   }

   @Test(priority = 5, enabled = false)
   public void DiffStoreTest() throws Exception {
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
         errorCounter++;
      }

      // Clicking the different store
      try {
         AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"PUREGOLD DEPARO\"));")));
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
         errorCounter++;
      }
   }

   @Test(priority = 6, enabled = false)
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
         errorCounter++;
      }
   }

   @Test(priority = 7, enabled = false)
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
         errorCounter++;
      }
   }

   @Test(priority = 8, enabled = true)
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
         errorCounter++;
      }

      try {
         // Checking if On-screen keyboard appeared
         boolean isKeyboardShown = driver.isKeyboardShown();

         if (isKeyboardShown) {
            System.out.println("Test passed: On-screen keyboard displayed");
         } else {
            markTestStatus("failed", "FAILED: On-screen keyboard did not display", driver);
            errorCounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
         errorCounter++;
      }

   }

   @Test(priority = 9, enabled = true)
   public void SearchKeywordTest() throws Exception {

      // Clicking and Searching for a specific store (Puregold Makati)
      try {
         AndroidElement StoreSearchField = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "store_search_bar")));
         StoreSearchField.click();
         StoreSearchField.sendKeys("Puregold Makati");
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Store search field error/not found", driver);
         errorCounter++;
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
         errorCounter++;
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
            errorCounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PUREGOLD MAKATI) button error/not found.", driver);
         errorCounter++;
      }
   }

   @Test(priority = 10, enabled = true)
   private void selectProductTest() throws InterruptedException {

      int numberOfProduct = 998;

      // Setting up wait for element visibility
      WebDriverWait wait = new WebDriverWait(driver, 30);

      // Locating Sally's Deals header and obtaining its height
      MobileElement sallysDealHeader = (MobileElement) driver.findElementByAndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Sally's Deals\"));");
      String headerHeight = getHeight(sallysDealHeader);

      // Finding all 'Add to Cart' elements and Sally's Deal specific items
      List<AndroidElement> addTocartList = driver.findElements(By.id("flash_deal_add_to_cart"));
      List<AndroidElement> SallysDealaddTocartList = new ArrayList<>();

      int addTocartHeight = 0;

      for (AndroidElement addToCart : addTocartList) {
         int height = Integer.valueOf(getHeight(addToCart));
         int heightDifference = height - Integer.valueOf(headerHeight);
         System.out.println("The height is: " + heightDifference);
         if (heightDifference == 792 && addToCart.isEnabled() && addToCart.isDisplayed()) {
            SallysDealaddTocartList.add(addToCart);
            addTocartHeight = height;
            break; // Assuming only one item meets the condition
         }
      }

      if (!SallysDealaddTocartList.isEmpty()) {
         // Finding 'Sold' elements related to Sally's Deals
         List<AndroidElement> sallysDealSold = driver.findElements(By.id("flash_deal_sold"));

         int soldHeight = addTocartHeight + 122;
         String secondnumberOfSold = "";
         for (AndroidElement soldDisplay : sallysDealSold) {
            int height = Integer.valueOf(getHeight(soldDisplay));
            if (height == soldHeight) {
               secondnumberOfSold = soldDisplay.getText().trim().split("S")[0].trim();
               break; // Assuming only one item meets the condition
            }
         }

         int seconddigitSold = Integer.parseInt(secondnumberOfSold);

         // Clicking on the first item in Sally's Deals
         SallysDealaddTocartList.get(0).click();

         // Waiting and managing quantity changes
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash_deal_quantity")));
         List<AndroidElement> qtyTxtList = driver.findElements(By.id("flash_deal_quantity"));
         int availableQTY = 50 - seconddigitSold;

         // Changing the quantity on the homepage
         int newQuantity = availableQTY - 3;
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash_deal_quantity")));
         qtyTxtList.get(0).clear();
         qtyTxtList.get(0).sendKeys(String.valueOf(numberOfProduct));
         driver.hideKeyboard();
         Thread.sleep(3000);
      }

   }

   @Test(priority = 12, enabled = true)
   private void cartTest() throws InterruptedException {
      try {
         AndroidElement navCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("nav_cart")));
         navCartButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Nav cart button not found.", driver);
         errorCounter++;
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
            errorCounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart page title is not found.", driver);
         errorCounter++;
      }

      // Checkout button
      try {
         AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "cart_checkout")));
         Thread.sleep(3000);
      } catch (Exception e) {
         System.out.println("Stock confirm on cart did not appear, no need to click");
      }

      try {
         // Set a maximum number of attempts to prevent infinite loops
         int maxAttempts = 3;

         for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            // Wait for the plus button to be clickable
            WebDriverWait wait = new WebDriverWait(driver, 3);
            AndroidElement okayPopUpButton = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.id("cart_quantity_add")));
            okayPopUpButton.click();

            // Wait for the "Out of Stock" pop-up with a shorter duration
            WebDriverWait outOfStockWait = new WebDriverWait(driver, 2);
            try {
               outOfStockWait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("tvTitle")));
               // If the "Out of Stock" pop-up is displayed, break out of the loop
               break;
            } catch (TimeoutException e) {
               System.out.println("Out of Stock pop-up not found. Continuing...");
            }
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Plus button not found / error.", driver);
         errorCounter++;
      }

      try {
         AndroidElement okayPopUpButton = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("bPositive")));
         okayPopUpButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED:Max quantity error/not found.", driver);
         errorCounter++;
      }
   }

   @Test(priority = 13, enabled = true)
   private void clearCart() throws InterruptedException {

      try {

         // Get the list of products in the cart
         List<AndroidElement> products = driver.findElements(By.id("cart_item_name"));
         int productQuantity = products.size();

         // Loop through each product in the cart
         for (int i = 0; i < productQuantity; i++) {
            products = driver.findElements(By.id("cart_item_name"));
            AndroidElement specificProductName = products.get(0);
            System.out.println("The number of products is: " + productQuantity);

            // Swipe left on the specific product
            swipeElementAndroid(specificProductName, Direction.LEFT);

            try {
               // Click on the remove button
               AndroidElement removeButton = (AndroidElement) new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(
                     MobileBy.id("cart_remove")));
               removeButton.click();
               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
            } catch (NoSuchElementException e) {
               markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
               errorCounter++;
            }

            try {
               // Click on the confirm remove button
               AndroidElement confirmRemoveButton = (AndroidElement) new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(
                     MobileBy.id("bPositive")));
               confirmRemoveButton.click();
               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
               Thread.sleep(5000);
            } catch (NoSuchElementException e) {
               markTestStatus("failed", "FAILED: Confirm Remove button error / not found.", driver);
               errorCounter++;
            }
         }
      } catch (NoSuchElementException e) {
         markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
         errorCounter++;
      }
   }

   @Test(priority = 20)
   private void checkError() {
      if (errorCounter > 0) {
         markTestStatus("failed", "an error an occurred on Max Allocation Passed (Cart Page) , Please see logs for details", driver);
      } else {
         markTestStatus("passed", "PASSED: Max Allocation Passed (Cart Page)", driver);
      }
   }

   public static void markTestStatus(String status, String reason, WebDriver driver) {
      // This method accepts the status, reason and WebDriver instance and marks the
      // test on BrowserStack
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

   private String getHeight(MobileElement eleme) {

      String headerLocation = eleme.getLocation().toString();
      String height = headerLocation.substring(headerLocation.lastIndexOf(',') + 1, headerLocation.indexOf(')')).trim();
      return height;
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

   private int numberSold(AndroidElement ele) {
      String firstItemSold = ele.getText().trim();
      String firstItemDigit = firstItemSold.substring(0, firstItemSold.indexOf("S"));
      int numberSold = Integer.parseInt(firstItemDigit.trim());
      return numberSold;
   }
}
