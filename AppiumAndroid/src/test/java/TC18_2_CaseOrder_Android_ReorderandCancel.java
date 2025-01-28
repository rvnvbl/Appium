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

public class TC18_2_CaseOrder_Android_ReorderandCancel extends AndroidSetup {
   int errorcounter = 0;
   String product = "UNMEAT MEAT FREE TAPA 180G";
   int productquantity = 0;
   int qtyPerPiece = 0;
   int qtyPerCase = 0;
   int totalQty = 0;
   int itemPrice = 0;
   int totalQuantity = 0;
   String SubTotalString = "";
   String DateString1 = "";
   String TimeString1 = "";
   String PaymentMethodString = "";
   String ItemName = "";

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
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_CaseOrder_Regular(String email, String password) throws Exception {

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
         
//         AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
//               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
//                     + "new UiSelector().text(\"PUREGOLD DEPARO\"));")));
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

            AndroidElement RedoSpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(
                  MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
                        + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView(" + "new UiSelector().text(\"PUREGOLD DEPARO\"));")));

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

      String header = "Frozen Items";
      // Finding and Scrolling to Globe Product header
      try {
         MobileElement productHeader = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"" + header + "\"));");

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Globe Products header not found", driver);
         errorcounter++;
      }

      try {
         MobileElement selectedProduct = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"" + product + "\"));");

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Tang item error/not found.", driver);
         errorcounter++;
      }

      try {
         Thread.sleep(2000);
         List<AndroidElement> addToCartButton = driver.findElements(By.id("catalog_product_add_to_cart"));
         System.out.println(addToCartButton.size());

         if (addToCartButton.size() >= 4) {
            addToCartButton.get(4).click();
         } else {
            addToCartButton.get(0).click();
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add to cart error/not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 5)
   private void addToCartByPiece() {
      try {
         // Click the "+" on the case order textfield = Quantity
         AndroidElement addQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("piece_add")));
         addQuantityButton.click();
         addQuantityButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add quantity button element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Put a value on the case quantity textfield
         AndroidElement addQuantityTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("piece_quantity")));

         addQuantityTextfield.click();
         addQuantityTextfield.clear();
         addQuantityTextfield.sendKeys("2");
         driver.navigate().back();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add quantity textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the "-" on the case order textfield
      try {
         AndroidElement quantityMinus = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("piece_minus")));
         quantityMinus.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Quantity minus element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Click the "+" on the case order textfield
         AndroidElement addCaseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_add")));
         addCaseButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case button element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Put a value on the case order textfield
         AndroidElement addCaseTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_quantity")));

         addCaseTextfield.click();
         addCaseTextfield.clear();
         addCaseTextfield.sendKeys("2");
         driver.navigate().back();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the "-" on the case order textfield
      try {
         AndroidElement addCaseMinus = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "case_minus")));
         addCaseMinus.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the add to cart button on the pop up
      try {
         AndroidElement addToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("add_to_cart")));

         if (addToCartButton.isEnabled()) {
            addToCartButton.click();
         } else {
            markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 6, enabled = false)
   public void addToCartByCase() {
      String finalCartPiecePerCase = "";
      try {
         // Click the "+" on the case order textfield
         AndroidElement addCaseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_add")));
         addCaseButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case button element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Put a value on the case order textfield
         AndroidElement addCaseTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_quantity")));

         addCaseTextfield.click();
         addCaseTextfield.clear();
         addCaseTextfield.sendKeys("2");
         driver.navigate().back();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the "-" on the case order textfield
      try {
         AndroidElement addCaseMinus = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "case_minus")));

         addCaseMinus.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         AndroidElement cartTotalQty = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "piece_per_case")));

         String initialProductPiece = cartTotalQty.getText();

         int index = initialProductPiece.indexOf('p');
         if (index != -1) {
            finalCartPiecePerCase = initialProductPiece.substring(0, index);
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: cartTotalQty not found.", driver);
         errorcounter++;
      }

      try {
         // Put a value on the case order textfield
         AndroidElement addCaseTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_quantity")));
         int piecePerCaseToInt = Integer.parseInt(finalCartPiecePerCase);
         int pieceOnCaseTextField = Integer.parseInt(addCaseTextfield.getText());
         qtyPerCase = pieceOnCaseTextField * piecePerCaseToInt;
         totalQty = qtyPerCase + qtyPerPiece;

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the add to cart button on the pop up
      try {
         AndroidElement addToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("add_to_cart")));

         if (addToCartButton.isEnabled()) {
            addToCartButton.click();

         } else {
            markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
            errorcounter++;
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 7)
   public void goToCartTest() {
      // Looking and click for the cart button on the nav bar of the page
      try {
         AndroidElement navCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("nav_cart")));

         navCartButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Nav cart button not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 8)
   private void cartTest() {

      // Checking if stock confirm appears
      try {
         AndroidElement CartStock = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "dialog_delivery_ok")));

         CartStock.click();
      } catch (Exception e) {
         System.out.println("Stock confirm on cart did not appear, no need to click");
      }

      // Looking and click for the cart title on the nav bar of the page
      try {
         AndroidElement cartTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart title not found.", driver);
         errorcounter++;
      }

      // Looking and click for the cart button on the nav bar of the page
      try {
         AndroidElement cartProductName = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("cart_item_name")));

         String productCartName = cartProductName.getText();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart product name not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 9)
   private void cartCaseOrderPopUp() {
      int initialQTY = 0;
      int initialPrice = 0;

      String finalCartPiecePerCase = "";
      String totalCartPiece = "";
      String priceOnPopUp = "";
      // Click edit quantity on cart page
      try {
         AndroidElement editQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("cart_edit_case")));
         editQuantityButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Edit quantity not found.", driver);
         errorcounter++;
      }

      try {
         // Click the "+" on the case order textfield = Quantity
         AndroidElement addQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("piece_add")));
         addQuantityButton.click();
         addQuantityButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add quantity button element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Put a value on the case quantity textfield
         AndroidElement addQuantityTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("piece_quantity")));

         addQuantityTextfield.click();
         addQuantityTextfield.clear();
         addQuantityTextfield.sendKeys("2");
         driver.navigate().back();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add quantity textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the "-" on the case order textfield
      try {
         AndroidElement quantityMinus = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("piece_minus")));
         quantityMinus.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Quantity minus element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Click the "+" on the case order textfield
         AndroidElement addCaseButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_add")));
         addCaseButton.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case button element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      try {
         // Put a value on the case order textfield
         AndroidElement addCaseTextfield = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("case_quantity")));

         addCaseTextfield.click();
         addCaseTextfield.clear();
         addCaseTextfield.sendKeys("2");
         driver.navigate().back();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Click the "-" on the case order textfield
      try {
         AndroidElement addCaseMinus = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "case_minus")));
         addCaseMinus.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Product piece per case
      try {
         AndroidElement cartPiecePerCase = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("piece_per_case")));

         String fullCartPiece = cartPiecePerCase.getText();
         finalCartPiecePerCase = fullCartPiece.substring(0, 2);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: cartPiecePerCase not found.", driver);
         errorcounter++;
      }

      // Total quantity on pop up
      // Getting the total product qty in the pop up
      try {
         AndroidElement cartTotalQty = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "total_qty")));

         String initialProductPiece = cartTotalQty.getText();

         int index = initialProductPiece.indexOf('p');
         if (index != -1) {
            finalCartPiecePerCase = initialProductPiece.substring(0, index);
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: cartTotalQty not found.", driver);
         errorcounter++;
      }

      // Total price value on pop up
      try {
         AndroidElement cartPopUpGrandTotal = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("grand_total")));
         priceOnPopUp = cartPopUpGrandTotal.getText();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: cartPopUpGrandTotal not found.", driver);
         errorcounter++;
      }

      // Press the add to cart button
      // Add to cart button on the pop up
      AndroidElement addToCartButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
            "add_to_cart")));

      if (addToCartButton.isEnabled()) {
         addToCartButton.click();

      } else {
         markTestStatus("failed", "FAILED: Add case textfield element not found, redirect to specific product failed.", driver);
         errorcounter++;
      }

      // Getting the cart total price in the cart page and compare it to the total
      // price in the Case Order popup
      try {
         AndroidElement cartSubtotalValue = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("cart_subtotal_value")));
         String grandSubtotal = cartSubtotalValue.getText();
         if (grandSubtotal.equals(priceOnPopUp)) {
            System.out.println("The value is same : " + grandSubtotal + " : " + priceOnPopUp);

         } else {
            System.out.println("The value is not same: " + grandSubtotal + " : " + priceOnPopUp);
            markTestStatus("failed", "FAILED: The total is not same.", driver);
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: cartSubtotalValue not found.", driver);
         errorcounter++;
      }

      // Getting the cart quantity in the cart page and compare it to the qty in the
      // Case Order popup
      try {
         AndroidElement cartTotalQTY = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "cart_quantity")));
         int totalQty = Integer.parseInt(cartTotalQTY.getText());
         int overallQty = qtyPerCase + qtyPerPiece;
         if (totalQty == overallQty) {
            System.out.println("The quantity is same: " + overallQty + " : " + totalQty);
         } else {
            System.out.println("The quantity is not same: " + overallQty + " : " + totalQty);
            markTestStatus("failed", "FAILED: The total is not same.", driver);
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart quantity not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 10)
   public void verifiedInformation() {
      // Checking if the price is equal to the computation of price * quantity
      try {
         AndroidElement cartSubtotalValue = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("cart_subtotal_value")));
         String grandSubtotal = cartSubtotalValue.getText();

         // Remove the other simple on the price, remain the number only.
         String finalTotalPrice = "";
         int index = grandSubtotal.indexOf('.');
         if (index != -1) {
            finalTotalPrice = grandSubtotal.substring(0, index);
         }
         finalTotalPrice = finalTotalPrice.substring(1);
         // System.out.println("The final price is: " + (finalTotalPrice));
         // System.out.println("The final price is: " + totalQty + "*" + itemPrice + "= "
         // + (totalQty * itemPrice));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: cartSubtotalValue not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 11)
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

   @Test(priority = 12)
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

   @Test(priority = 13)
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

   @Test(priority = 14)
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
         //   errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Time and Date error / not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 15)
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

   @Test(priority = 16)
   public void PayInStoreMethodTest() throws Exception {
      try {
         // Clicking the Pay in store payment option
         AndroidElement CreditDebitOption = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"Pay In Store\"));")));

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
   }

   @Test(priority = 17)
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
      try {
         AndroidElement confirmOrderBtn = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy.id(
               "confirmation_orders")));
         confirmOrderBtn.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Confirm order button not found", driver);
         errorcounter++;
      }
   }

   @Test(priority = 18)
   private void reorderTest() {
      // TODO Auto-generated method stub
      // Listing all orders, then clicking on the first one
      try {
         List<AndroidElement> OrdersList = driver.findElements(By.id("order_status"));

         OrdersList.get(0).click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Orders button at homepage is not found.", driver);
         errorcounter++;
      }

      // Checking if redirect to Specific Order is successful
      try {
         AndroidElement SpecificOrderTracker = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("order_tracking_container")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Order Tracker is not found.", driver);
      }

      // Scrolling down to the Cancel Order button for visibility of the order items
      try {
         MobileElement CancelOrderButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"Cancel Order\"));");

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel Order button is not found.", driver);
         errorcounter++;
      }

      // Listing all order items, then getting the text of the first one
      try {
         List<AndroidElement> OrdersList = driver.findElements(By.id("order_item_name"));

         ItemName = OrdersList.get(0).getText();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Order item name is not found.", driver);
         errorcounter++;
      }

      // Clicking the Reorder button
      try {
         AndroidElement ReorderButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("menu_reorder")));

         ReorderButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Reorder button is not found.", driver);
         errorcounter++;
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

      // Listing all Cart items, getting the text of the first one, and comparing if
      // successfully readded to cart
      try {
         List<AndroidElement> CartList = driver.findElements(By.id("cart_item_name"));

         String CartItemName = CartList.get(0).getText();

         if (ItemName.equals(CartItemName)) {
            System.out.println("Successfully re-ordered / re-added to cart the correct item");
         } else {
            markTestStatus("failed", "FAILED: Cart item name is incorrect.", driver);
            errorcounter++;
         }

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart item name is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 19, enabled = true)
   private void clearCart() {
      try {
         // Checking of products if a product is seen (GLOBE AT HOME) in keywords
         List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

         productquantity = Products.size();

         // Checking all results
         for (int i = 0; i < productquantity; i++) {
            AndroidElement SpecificProductName = Products.get(0);

            swipeElementAndroid(SpecificProductName, Direction.LEFT);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            try {
               // Checking if Remove button is found after swiping left
               AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                     MobileBy.id("cart_remove")));

            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
               errorcounter++;
            }

            try {
               // Clicking the remove button
               AndroidElement RemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                     MobileBy.id("cart_remove")));

               RemoveButton.click();

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Remove button error / not found.", driver);
               errorcounter++;
            }

            try {
               // Clicking the Yes button in confirming the removal of product
               AndroidElement ConfirmRemoveButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
                     .visibilityOfElementLocated(MobileBy.id("bPositive")));

               ConfirmRemoveButton.click();

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

               Thread.sleep(5000);
            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Confirm Remove button error / not found.", driver);
               errorcounter++;
            }
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart product error / not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 20, enabled = true)
   private void gotoMyOrderPage() {
      try {
         AndroidElement backButtonCart = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Navigate up")));
         backButtonCart.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Back button in cart page error / not found.", driver);
         errorcounter++;
      }

      try {
         AndroidElement backButtonShop = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Navigate up")));
         backButtonShop.click();
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Back button in shop page error / not found.", driver);
         errorcounter++;
      }

      // Checking if redirect to Orders page is successful
      try {
         AndroidElement OrdersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "title")));

         String OrdersTitleText = OrdersTitle.getText();

         if (OrdersTitleText.equals("Orders")) {
            System.out.println("Successfully redirected to Orders page");
         } else {
            markTestStatus("failed", "FAILED: Orders page title error / not found", driver);
            errorcounter++;
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Orders page title is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 21)
   public void CancelOrderButtonTest() throws Exception {
      // Listing all orders, then clicking on the first one
      try {
         List<AndroidElement> OrdersList = driver.findElements(By.id("order_status"));

         OrdersList.get(0).click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Orders button at homepage is not found.", driver);
         errorcounter++;
      }

      // Checking if redirect to Specific Order is successful
      try {
         AndroidElement SpecificOrderTracker = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("order_tracking_container")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Order Tracker is not found.", driver);
         errorcounter++;
      }

      // Scrolling down to the Cancel Order button for visibility of the order items
      try {
         MobileElement CancelOrderButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"Cancel Order\"));");

         CancelOrderButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel Order button is not found.", driver);
         errorcounter++;
      }

      // Checking if Cancel Order pop up is visible
      try {
         AndroidElement CancelOptionsSheet = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("order_cancel_bottom_sheet")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel Order Options not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 22)
   public void CancelGoBackTest() throws Exception {
      // Clicking of Go back button on cancel sheet
      try {
         AndroidElement GoBackButton = (AndroidElement) driver.findElement(By.id("order_cancel_sheet_go_back"));
         GoBackButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Go back Button is not found.", driver);
         errorcounter++;
      }

      // Checking if redirect to Specific Order is successful
      try {
         AndroidElement CancelOrderButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(MobileBy
               .AndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                     + "new UiSelector().text(\"Cancel Order\"));")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel Order Button is not found.", driver);
         errorcounter++;
      }

      // Re-clicking / Re-opening of cancel order sheet
      try {
         MobileElement CancelOrderButton = (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
               + ".scrollable(true)).scrollIntoView" + "(new UiSelector().text(\"Cancel Order\"));");

         CancelOrderButton.click();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel Order button is not found.", driver);
         errorcounter++;
      }

      // Checking if Cancel Order pop up is visible
      try {
         AndroidElement CancelOptionsSheet = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("order_cancel_bottom_sheet")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel Order Options not found.", driver);
         errorcounter++;
      }

   }

   @Test(priority = 23)
   public void RadioButtonTest() throws Exception {
      // Clicking of first radio button on cancel sheet
      try {
         AndroidElement RadioButton = (AndroidElement) driver.findElement(By.id("order_cancel_sheet_option1"));
         RadioButton.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: First Radio Button is not found.", driver);
         errorcounter++;
      }
   }

   @Test(priority = 24)
   public void CancelOrderConfirmTest() throws Exception {
      // Clicking of Cancel order button on Cancel order sheet
      try {
         AndroidElement CancelOrderConfirm = (AndroidElement) driver.findElement(By.id("order_cancel_sheet_cancel"));
         CancelOrderConfirm.click();

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cancel order confirm Button is not found.", driver);
         errorcounter++;
      }

      // Checking if order is now cancelled (Reorder button must not be existing)
      try {
         Thread.sleep(7000);

         AndroidElement ReorderButton = (AndroidElement) new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("menu_reorder")));

         markTestStatus("failed", "FAILED: Reorder button is still visible, Cancel order failed.", driver);
         errorcounter++;

      } catch (Exception e) {
         System.out.println("Reorder button not found, test success");
      }
   }

   @Test(priority = 25)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "Add to cart > Case Order > Reorder and Cancel, Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Add to cart > Case Order > Reorder and Cancel Successful", driver);
      }
   }

   // This method accepts the status, reason and WebDriver instance and marks the
   // test on BrowserStack
   public static void markTestStatus(String status, String reason, WebDriver driver) {
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

   /****************************************************************************************************************************
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
