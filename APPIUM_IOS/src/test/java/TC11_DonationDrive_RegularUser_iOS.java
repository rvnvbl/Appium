import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

public class TC11_DonationDrive_RegularUser_iOS extends SetupIOS {

   int failedCounter = 0;

   @Test(priority = 0)
   public void LoginButtonTest() throws Exception {
      // Clicking of Login with email button on startup screen

      try {
         IOSElement btn_login = (IOSElement) new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(MobileBy.name("Log in")));
         btn_login.click();
         Thread.sleep(5000);

         // verify if email textfield is visible and enabled
         IOSElement textfield_email = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Email")));
         // verify if Login Button is visible and enabled
         IOSElement btn_login2 = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Login")));

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         failedCounter++;
      }
   }

   @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
   public void LoginFieldsTest_DonationDrive_RegUser(String email, String password) throws Exception {

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

      // Clicking and input of email on email field
      try {
         IOSElement EmailField = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .AccessibilityId("Email")));
         EmailField.click();
         EmailField.sendKeys(email);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Email Field is not found.", driver);
         failedCounter++;
      }

      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

      // Clicking and input of password on password field
      try {
         IOSElement PassField = (IOSElement) driver.findElement(MobileBy.AccessibilityId("Password"));
         PassField.click();
         PassField.sendKeys(password);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Password Field is not found.", driver);
         failedCounter++;
      }

      driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

      // Clicking of login button after the input of the above credentials
      try {
         IOSElement LoginButton = (IOSElement) driver.findElement(By.name("Login"));
         LoginButton.click();

         Thread.sleep(8000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Login Button is not found.", driver);
         failedCounter++;
      }

      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
   }

   @Test(priority = 2)
   public void DashboardTest() throws Exception {
      // Checking of P-Wallet Button if visible
      try {
         IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "home wallet")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         Thread.sleep(5000);

         markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
         failedCounter++;
      }
   }

   @Test(priority = 3)
   public void Navigate_to_DonationDrive() throws Exception {

      // Checking of Donation Drive Button if visible
      try {
         // IOSElement btn_donationDrive = (IOSElement) new WebDriverWait(driver, 30)
         // .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home_btn_donation_drive")));
         // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         // btn_donationDrive.click();
         RemoteWebElement btn_donationDrive = (RemoteWebElement) driver.findElement(MobileBy.name("home_btn_donation_drive"));
         String elementID = btn_donationDrive.getId();
         HashMap<String, String> scrollObject = new HashMap<String, String>();
         scrollObject.put("element", elementID); // Only for ‘scroll in element’
         scrollObject.put("direction", "down");
         driver.executeScript("mobile:scroll", scrollObject);
         btn_donationDrive.click();
         markTestStatus("passed", "PASSED: Scrolled to Donation Drive Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Donation Drive Page.", driver);
         failedCounter++;
      }

      // Check if redirected to Donation Drive page
      try {
         IOSElement lbl_donationDrive = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Donation Drive")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Redirected to Donation Drive Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirected to Donation Drive Page.", driver);
         failedCounter++;
      }

   }

   @Test(priority = 4)
   public void AddToCart_Item() throws Exception {

      // Get and click first Donate button
      try {

         // get all elements with the element class XCUIElementTypeButton
         List<IOSElement> btn_donate = driver.findElementsByName("DONATE");

         btn_donate.get(0).click();
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Donate Button found.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Donate Button not found.", driver);
         failedCounter++;
      }

      // Checking of Cart Plus Button if visible
      try {

         IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "cart plus")));

         btn_cartPlus.click();
         driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
         failedCounter++;
      }

      // Checking of Cart Minus Button if visible
      try {

         IOSElement btn_cartMinus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "cart minus")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
         markTestStatus("passed", "PASSED: Decrease QTY Button found", driver);
         btn_cartMinus.click();
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Decrease QTY button found.", driver);
         failedCounter++;
      }

      // Checking of Trash bin Button if visible
      try {

         IOSElement btn_trashbin = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "delete quantity")));

         markTestStatus("passed", "PASSED: Trash Bin Button found", driver);
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Trash Bin button found.", driver);
         failedCounter++;
      }

   }

   @Test(priority = 5)
   public void Cart_Page() throws Exception {

      // Checking of Cart Button if visible
      try {
         IOSElement btn_cart = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         btn_cart.click();

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Cart button not found.", driver);
         failedCounter++;
      }

      // Check if redirected to Cart page
      try {
         IOSElement lbl_cart = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Redirected to Cart Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Cart Page.", driver);
         failedCounter++;
      }

      // Checking of Cart Plus Button if visible
      try {

         IOSElement btn_cartPlus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "cart plus")));

         btn_cartPlus.click();
         driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Increse qty button found.", driver);
         failedCounter++;
      }

      // Checking of Cart Minus Button if visible
      try {

         IOSElement btn_cartMinus = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "cart minus")));

         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
         markTestStatus("passed", "PASSED: Decrease QTY Button found", driver);
         btn_cartMinus.click();
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Decrease QTY button found.", driver);
         failedCounter++;
      }

      // Checking of Trash bin Button if visible
      try {

         IOSElement btn_trashbin = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "delete quantity")));

         markTestStatus("passed", "PASSED: Trash Bin Button found", driver);
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Trash Bin button found.", driver);
         failedCounter++;
      }

   }

   @Test(priority = 6)
   public void ProceedToCheckout() throws Exception {

      // Checking of Checkout Button if visible
      try {
         IOSElement btn_checkout = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Checkout")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         btn_checkout.click();

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Checkout button not found.", driver);
         failedCounter++;
      }

      // Check if redirected to Checkout page
      try {
         IOSElement lbl_checkout = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Checkout")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Redirected to Checkout Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Checkout Page.", driver);
         failedCounter++;
      }

      // Checking of Contact info if visible
      try {
         IOSElement lbl_contactInfo = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Contact Info")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Contact Info not found.", driver);
         failedCounter++;
      }

      // Checking of Payment Method if visible
      try {
         IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Payment Method")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment Method not found.", driver);
         failedCounter++;
      }
   }

   @Test(priority = 7, dataProvider = "GCash_method", dataProviderClass = DataProvider_Login.class)
   public void PaymentMethod_GCash(String Gcash) throws Exception {

      // Checking if redirected to Payment Method page
      try {

         IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Payment Method")));

         markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
         failedCounter++;
      }

      // Checking of Payment Method if visible
      try {
         IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Select Payment Method")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         btn_paymentMethod.click();

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment Method not found.", driver);
         failedCounter++;
      }

      // Select GCash method
      try {

         Thread.sleep(5000);
         // Verify if GCash method is visible
         IOSElement btn_GCash = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(Gcash)));
         btn_GCash.click();

         markTestStatus("passed", "PASSED: GCash Direct button is displayed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: GCash Direct button not found.", driver);
         failedCounter++;
      }

      // Verify if GCash method is displayed
      try {

         Thread.sleep(5000);
         // Verify if GCash method is visible
         IOSElement lbl_GCash = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(Gcash)));

         markTestStatus("passed", "PASSED: Change payment method to GCash Direct passed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Change payment method to GCash Direct failed", driver);
         failedCounter++;
      }
   }

   @Test(priority = 8, dataProvider = "CreditDebit_method", dataProviderClass = DataProvider_Login.class)
   public void PaymentMethod_CreditDebit(String CreditDebit) throws Exception {

      // Checking if Payment Method button is visible
      try {

         IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "GCash Direct")));
         btn_paymentMethod.click();

         markTestStatus("passed", "PASSED: Payment Method button is displayed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment Method button not found.", driver);
         failedCounter++;
      }

      // Checking if redirected to Payment Method page
      try {

         IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Payment Method")));

         markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
         failedCounter++;
      }

      // Select Credit/Debit card method
      try {

         Thread.sleep(5000);
         // Verify if Credit/Debit card method is visible
         IOSElement btn_CreditDebit = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               CreditDebit)));
         btn_CreditDebit.click();

         markTestStatus("passed", "PASSED: Credit / Debit Card button is displayed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Credit / Debit Card button not found.", driver);
         failedCounter++;
      }

      // Verify if Credit / Debit Card method is displayed
      try {

         Thread.sleep(5000);
         // Verify if Credit / Debit Card method is visible
         IOSElement lbl_CreditDebit = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               CreditDebit)));

         markTestStatus("passed", "PASSED: Change payment method to Credit / Debit Card passed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Change payment method to Credit / Debit Card failed", driver);
         failedCounter++;
      }
   }

   @Test(priority = 9, dataProvider = "PWallet_method", dataProviderClass = DataProvider_Login.class, enabled = false)
   public void PaymentMethod_PWallet(String PWallet) throws Exception {

      // Checking if Payment Method button is visible
      try {

         IOSElement btn_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Credit / Debit Card")));
         btn_paymentMethod.click();

         markTestStatus("passed", "PASSED: Payment Method button is displayed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Payment Method button not found.", driver);
         failedCounter++;
      }

      // Checking if redirected to Payment Method page
      try {

         IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Payment Method")));

         markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
         failedCounter++;
      }

      // Select P-Wallet method
      try {

         Thread.sleep(5000);
         // Verify if P-Wallet method is visible
         IOSElement btn_Pwallet = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               PWallet)));
         btn_Pwallet.click();

         markTestStatus("passed", "PASSED: P-Wallet button is displayed.", driver);

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: P-Wallet button not found.", driver);
         failedCounter++;
      }

      // Check if P-Wallet has balance
      try {

         Thread.sleep(5000);
         // Verify if popup modal is visible
         IOSElement modal_popup = driver.findElementByName("Top up and Pay with Puregold Wallet");

         if (modal_popup.isDisplayed() == true) {
            markTestStatus("passed", "P-Wallet has no balance.", driver);

            // Verify modal buttons if visible
            try {
               // Verify if top up button is visible
               IOSElement btn_topUp = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
                     "Top Up Now")));

               markTestStatus("passed", "Top Up button is displayed.", driver);

               driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Top Up button not found.", driver);
               failedCounter++;
            }

            try {
               // Verify if Change payment method button is visible
               IOSElement btn_changePayment = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
                     .name("Change Payment Method")));

               markTestStatus("passed", "Change Payment Method button is displayed.", driver);

               // click change payment method to close modal
               btn_changePayment.click();
               Thread.sleep(10000);

               // Checking if redirected back to Payment Method page
               try {

                  IOSElement lbl_paymentMethod = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
                        MobileBy.name("Payment Method")));

                  markTestStatus("passed", "PASSED: Redirected to Payment Method page.", driver);

                  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

               } catch (Exception e) {
                  markTestStatus("failed", "FAILED: Did not redirect to Payment Method page.", driver);
                  failedCounter++;
               }

               // Navigate back to checkout page
               driver.navigate().back();
               driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            } catch (Exception e) {
               markTestStatus("failed", "FAILED: Change Payment Method button not found.", driver);
               failedCounter++;
            }

         }

         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

      } catch (Exception e) {

         // Check if redirected to checkout page if P-Wallet has balance
         try {

            // P-Wallet has balance
            markTestStatus("passed", "P-Wallet has balance.", driver);

            Thread.sleep(5000);
            // Verify if redirected to checkout page
            IOSElement lbl_checkout = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
                  "Checkout")));

            markTestStatus("passed", "PASSED: Redirected to Checkout page.", driver);

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

         } catch (Exception x) {
            markTestStatus("failed", "FAILED: Did not redirect to Checkout page.", driver);
            failedCounter++;
         }
      }
   }

   @Test(priority = 10)
   public void Remove_CartItem() throws Exception {

      // Check if redirected to Cart page
      try {

         // Navigate back to Cart page
         driver.navigate().back();

         IOSElement lbl_cart = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Cart")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Redirected to Cart Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirect to Cart Page.", driver);
         failedCounter++;
      }

      // Checking of Trash bin Button if visible
      try {

         IOSElement btn_trashbin = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "delete quantity")));

         btn_trashbin.click();

         markTestStatus("passed", "PASSED: Trash Bin Button found", driver);
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         Thread.sleep(5000);

      } catch (Exception e) {
         markTestStatus("failed", "FAILED: No Trash Bin button found.", driver);
         failedCounter++;
      }

      // Check if redirected to Donation Drive page
      try {

         // Navigate back to Donation Drive page
         driver.navigate().back();

         IOSElement lbl_donationDrive = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Donation Drive")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Redirected to Donation Drive Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirected to Donation Drive Page.", driver);
         failedCounter++;
      }

   }

   @Test(priority = 11)
   public void Transaction_History() throws Exception {

      // Checking of Transaction History Button if visible
      try {
         IOSElement btn_transaction = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "buy load transactions")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
         btn_transaction.click();

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Transaction History button not found.", driver);
         failedCounter++;
      }

      // Check if redirected to Transaction History page
      try {
         IOSElement lbl_donationDrive = (IOSElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name(
               "Transaction History")));
         driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

         markTestStatus("passed", "PASSED: Redirected to Transaction History Page.", driver);

         Thread.sleep(5000);
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Did not redirected to Transaction History Page.", driver);
         failedCounter++;
      }

   }

   @Test(priority = 20)
   public void CheckFailedCount() throws Exception {

      if (failedCounter != 0) {
         markTestStatus("failed", "FAILED: Donation Drive as a Regular User Module has error/s.", driver);
      } else {
         markTestStatus("passed", "PASSED: Donation Drive as a Regular User Module successful.", driver);
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
