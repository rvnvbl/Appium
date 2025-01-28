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

public class TC4_1_AddtoCart_Android_Categories extends AndroidSetup {
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
   public void LoginFieldsTest_addToCartStore_Categories(String email, String password) throws Exception {

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
                     "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PPCI - NORTH COMMONWEALTH\").instance(0))")));

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
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"PPCI - NORTH COMMONWEALTH\").instance(0))")));

            RedoSpecificStoreButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
         } catch (Exception e) {
            System.out.println("Change branch modal did not appear, Work-around not needed");
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) button error/not found.", driver);
      }

      // Checking of store
      try {
         // Checking if Specific store at top is visible and correct (PUREGOLD AGORA)
         AndroidElement StoreHeader = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_catalog_text_place")));
         String StoreHeaderString = StoreHeader.getText();

         if (StoreHeaderString.equals("PPCI - NORTH COMMONWEALTH")) {
            System.out.println("Test passed: Specific store redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Specific Store(PPCI - NORTH COMMONWEALTH) did not redirect correctly.", driver);
      }
   }

   @Test(priority = 5)
   public void NavCategoryTest() throws Exception {
      // Clicking on Category tab
      try {
         AndroidElement NavCategoryButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("nav_category")));
         NavCategoryButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Category button error/not found.", driver);
      }

      // Checking of Category title if redirected correctly
      try {
         // Checking if title at top is visible and correct (Category)
         AndroidElement CategoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         String CategoryTitleString = CategoryTitle.getText();

         if (CategoryTitleString.equals("Category")) {
            System.out.println("Test passed: Category button redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Category did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Category did not redirect correctly.", driver);
      }
   }

   @Test(priority = 6)
   public void CategorySelectTest() throws Exception {
      // Listing and selecting the second category which is Beverages
      try {
         List<AndroidElement> Categories = driver.findElements(By.id("catalog_category_icon"));
         System.out.println("Categories number is: " + Categories.size());
         for (int i = 0; i < Categories.size(); i++) {

            if (i == 2) {
               AndroidElement SpecificCategory = Categories.get(i);

               SpecificCategory.click();

               break;
            }
         }

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Categories error/not found.", driver);
      }

      // Checking of Category title chosen if redirected correctly
      try {
         // Checking if title at top is visible and correct (BEVERAGES)
         AndroidElement CategoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));
         String CategoryTitleString = CategoryTitle.getText();

         if (CategoryTitleString.equals("BEVERAGES")) {
            System.out.println("Test passed: Selected Category button redirected to is correct");
         } else {
            markTestStatus("failed", "FAILED: Selected Category did not redirect correctly.", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Selected Category did not redirect correctly.", driver);
      }
   }

   @Test(priority = 7)
   public void CategorySearchFieldTest() throws Exception {

      try {
         // Clicking of search field in store homepage
         AndroidElement CatSearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("menu_search")));

         CatSearchField.click();

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

   @Test(priority = 8)
   public void CategorySearchKeywordTest() throws Exception {

      try {
         // Clicking of search field in search and searching for specific products
         // (Cowhead Premium Flavoured Milk)
         AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_searchbar")));

         SearchField.click();

         SearchField.sendKeys("NESCAFE DISCOVER A DARK ROAST");

         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

         TouchAction touchAction = new TouchAction(driver);
         touchAction.tap(PointOption.point(1000, 2100)).perform();

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Search field error / not found.", driver);
      }

      try {
         // Checking of products if all results has NESCAFE DISCOVER A DARK ROAST in
         // keywords
         List<AndroidElement> Products = driver.findElements(By.id("cart_item_name"));

         // Checking all results
         for (int i = 0; i < Products.size(); i++) {
            AndroidElement SpecificProductName = Products.get(i);

            String SpecificProductNameText = SpecificProductName.getText();
            if (SpecificProductNameText.contains("NESCAFE DISCOVER A DARK ROAST")) {
               System.out.println("Product contains NESCAFE DISCOVER A DARK ROAST keyword");
               System.out.println(i);
            } else {
               markTestStatus("failed", "FAILED: Product with no keyword from search found", driver);
            }
         }

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Search field error / not found.", driver);
      }

   }

   @Test(priority = 9)
   public void CategoryAddToCartTest() throws Exception {

      // Clicking of the specific product (Cowhead Premium Flavoured Milk)
      try {
         AndroidElement BeverageItem = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "search_item_image")));

         BeverageItem.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cowhead Premium Flavoured Milk product error/not found.", driver);
      }

      // Checking if Beverage product item description is visible for redirect
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

   @Test(priority = 10)
   public void TrashBinButtonTest() throws Exception {

      AndroidElement QuantityField = (AndroidElement) new WebDriverWait(driver, 39).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
            "item_product_quantity")));
      QuantityField.clear();
      QuantityField.sendKeys("1");

      // Finding and Clicking of Trash bin button
      try {
         AndroidElement MinusQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         MinusQuantityButton.click();

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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

   @Test(priority = 11 , enabled = false)
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

   @Test(priority = 12 , enabled = false)
   public void DecreaseQuantityTest() throws Exception {

      // Clicking the decrease quantity button of the product and checking the
      // quantity if it is now 1
      try {
         AndroidElement DecQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         DecQuantityButton.click();

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
   @Test(priority = 13 , enabled = false)
   public void TrashButtonFoResetTest() throws Exception {

      // Clicking the decrease quantity button of the product
      try {
         AndroidElement DecQuantityButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_product_quantity_minus")));

         DecQuantityButton.click();

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

   @Test(priority = 14 , enabled = false)
   public void ProductDetailsTest() throws Exception {

      // Checking if Beverage product item description is visible
      try {
         AndroidElement ItemDescriptionCheck = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("item_description_title")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Item description not found.", driver);
      }

   }

   @Test(priority = 15 , enabled = false)
   public void CategoryViewAllTest() throws Exception {
      String SpecificCategoryTitleText = "";

      try {
         // Go back to Search screen and check if elements are visible
         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

         try {
            // Checking if On-screen keyboard appeared
            boolean isKeyboardShown = driver.isKeyboardShown();

            if (isKeyboardShown) {
               driver.pressKey(new KeyEvent(AndroidKey.BACK));

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
            } else {
               System.out.println("On-screen keyboard did not appear, counter measure not needed");
            }
         } catch (Exception e) {
            markTestStatus("failed", "FAILED: Unknown error on checking of keyboard.", driver);
         }

         AndroidElement SearchField = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(
               "toolbar_searchbar")));
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Search field error / not found.", driver);
      }

      try {
         // Go back to Category screen and check if elements are visible
         driver.pressKey(new KeyEvent(AndroidKey.BACK));

         driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

         AndroidElement CategoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         String CategoryTitleText = CategoryTitle.getText();

         if (CategoryTitleText.equals("BEVERAGES")) {
            System.out.println("Successfully went back to chosen Category page");
         } else {
            markTestStatus("failed", "FAILED: Category title is not correct", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Category title error / not found.", driver);
      }

      /*
       * Flow of Code for this test: - Get all sub-category elements - Get the title and click the view
       * all button of the first sub-category - Compare the acquired title to the page title of the
       * sub-category after clicking view all
       */

      try {
         // Listing of all sub-categories and clicking the view all button on the first
         // sub-category
         List<AndroidElement> Products = driver.findElements(By.id("catalog_category_layout"));

         // Checking all results
         for (int i = 0; i < Products.size(); i++) {
            if (i == 0) {
               AndroidElement SpecificCategory = Products.get(i);

               // getting the title of the sub-category in the category page
               MobileElement SpecificCategoryTitle = SpecificCategory.findElement(By.id("catalog_category_name"));

               SpecificCategoryTitleText = SpecificCategoryTitle.getText();

               // getting and clicking the view all button of the chosen sub-category
               MobileElement SpecificCategoryViewAll = SpecificCategory.findElement(By.id("catalog_category_view_all"));

               SpecificCategoryViewAll.click();

               driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
               break;
            }
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No sub-categories found / error.", driver);
      }

      try {
         /*
          * Checking if sub-category page title is same with the acquired title from sub-category element in
          * categories page
          */

         AndroidElement SubCategoryTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("title")));

         String SubCategoryTitleText = SubCategoryTitle.getText();

         if (SubCategoryTitleText.equals(SpecificCategoryTitleText)) {
            System.out.println("Successfully redirected to first sub-category page (View All)");
            markTestStatus("passed", "PASSED: Add to Cart > Categories/Product Details/View all successful", driver);
         } else {
            markTestStatus("failed", "FAILED: Sub-Category title is not correct", driver);
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Sub-Category title error / not found.", driver);
      }

   }

   @Test(priority = 17)
   private void errorChecker() {
      if (errorcounter > 0) {
         markTestStatus("failed", "Add to cart > Category >  Please see logs for details", driver);
      } else {
         markTestStatus("passed", "Add to cart > Category Successful", driver);
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
