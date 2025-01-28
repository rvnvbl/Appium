import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import util.ExcelDataSupllier;

public class TC22_ShopPage_MyVoucher extends AndroidSetup {
    String SubTotalString = "";
    String DateString1 = "";
    String TimeString1 = "";
    String PaymentMethodString = "";
    String TempString = "";
    String TempString2 = "";
    String mobileDataCostString = "";
    int errorcounter = 0;

    @Test(priority = 0)
    public void LoginPageTest() throws Exception {
        try {
            // Clicking of Login with email button on startup screen
            AndroidElement EmailLoginButton = (AndroidElement) new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.elementToBeClickable(MobileBy.id("landing_login")));
            EmailLoginButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Checking of fields if redirect to login with email page is successful

            AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));

            AndroidElement PasswordField = (AndroidElement) new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_password_field")));

            System.out.println("Test 1: Redirect to Login page successful");
        } catch (Exception e) {
            markTestStatus("failed", "FAILED: Login Button in startup is not found.", driver);
            errorcounter++;
        }
    }


    @Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
    public void LoginFieldsTest_MyAccounts_MyVoucher(String email, String password) throws Exception {

        // Clicking and input of email on email field
        try {
            AndroidElement EmailField = (AndroidElement) new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("login_email_field")));
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
            AndroidElement PuregoldImageChecker = (AndroidElement) new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("image")));

            System.out.println("Test 5 passed: Login is successful");
            markTestStatus("passed", "Log in > Log in As a regular User successful", driver);
        } catch (Exception e) {
            markTestStatus("failed", "FAILED: Login Button is not found.", driver);
            errorcounter++;
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test(priority = 3)
    public void TapInStorePickUpTest() throws Exception {
        // Scrolling to and clicking the pick up in store button
        try {
            MobileElement PickUpOptionButton = (MobileElement) driver.findElementByAndroidUIAutomator(
                    "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView"
                            + "(new UiSelector().resourceId(\"com.grocery.puregold:id/home_in_store_layout_image\"));");
            PickUpOptionButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Checking if Store search bar from In-store pickup is visible to know if
            // redirect is successful
            AndroidElement StoreSearchBar = (AndroidElement) new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("store_search_bar")));

            markTestStatus("passed", "In Store Pick Up > Store List successful", driver);
        } catch (Exception e) {
            markTestStatus("failed", "FAILED: In-store Pickup button is not found.", driver);
        }
    }

    @Test(priority = 4)
    public void StoreTapTest() throws Exception {
        // Clicking the specific store
        try {
            AndroidElement SpecificStoreButton = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions
                    .elementToBeClickable(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
                            + ".resourceId(\"com.android.settings:id/content\")).scrollIntoView("
                            + "new UiSelector().text(\"PUREGOLD DEPARO\"));")));
            SpecificStoreButton.click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Verifying and fix if modal appears on first branch option
            try {
                AndroidElement YesButton = (AndroidElement) new WebDriverWait(driver, 4)
                        .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("bPositive")));

                YesButton.click();

                driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

            } catch (Exception e) {
                System.out.println("Change branch modal did not appear, Work-around not needed");
            }

        } catch (Exception e) {
            markTestStatus("failed", "FAILED: Specific Store(PUREGOLD DEPARO) button error/not found.", driver);      
        }
    }

    @Test(priority = 5)
    public void tapSeeMyVoucherTest() {
        try {
           // Clicking the See My Vouchers button
           AndroidElement TapSeeMyVouchersBtn = (AndroidElement) driver.findElement(By.id("see_voucher_action"));
           TapSeeMyVouchersBtn.click();
           System.out.println("Voucher button found");
           
        } catch (Exception e) {
            markTestStatus("failed", "Voucher button not found", driver);
            errorcounter++;
        }
        
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      
    }
    
    @Test(priority = 6)
   public void verifyMyVouchersTitleTest() {
       
       try {
         //Verify My Vouchers Page Title
          AndroidElement accountHeader = (AndroidElement) new WebDriverWait(driver, 30)
                  .until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("title")));
          String accountHeaderText = accountHeader.getText();

          if (accountHeaderText.equals("My Vouchers")) {
              System.out.println("Succesfully land on My Vouchers Page");
              markTestStatus("passed", "Succesfully land on My Vouchers Page", driver);
              
          } else {
              markTestStatus("failed", "Not Succesfully land on My Vouchers Page", driver);
              errorcounter++;
          }
          
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Not Succesfully land on My Vouchers Page", driver);
      }
          
   }
    
   @Test(priority = 7)
   public void verifyMyVouchersListTest() {

      try {
         // (1) Verify Regular Vouchers Title
         AndroidElement RegularVouchersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.id("voucher_banner")));

         if ((RegularVouchersTitle.getText()).equals("Regular Vouchers")) {
            System.out.println("Regular Vouchers title is displayed");
            markTestStatus("passed", "Regular Vouchers title is displayed", driver);
         } else {
            markTestStatus("failed", "Regular Vouchers title is not displayed", driver);
            errorcounter++;
         }

         // (2) Verify P-Wallet Vouchers Title
         AndroidElement PwalletVouchersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.xpath("//android.view.ViewGroup[4]/android.widget.TextView")));

         if ((PwalletVouchersTitle.getText()).equals("P-Wallet Vouchers")) {
            System.out.println("P-Wallet Vouchers title is displayed");
            markTestStatus("passed", "P-Wallet Vouchers title is displayed", driver);
         } else {
            markTestStatus("failed", "P-Wallet Vouchers title is not displayed", driver);
            errorcounter++;
         }

         // (3) Verify Load Vouchers Title
         AndroidElement LoadVouchersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.xpath("//android.view.ViewGroup[5]/android.widget.TextView")));

         if ((LoadVouchersTitle.getText()).equals("Load Vouchers")) {
            System.out.println("Load Vouchers title is displayed");
            markTestStatus("passed", "Load Vouchers title is displayed", driver);
         } else {
            markTestStatus("failed", "Load Vouchers title is not displayed", driver);
            errorcounter++;
         }

         // (4) Verify Puretreats Vouchers Title
         AndroidElement PureTreatsVouchersTitle = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(
               MobileBy.xpath("//android.view.ViewGroup[6]/android.widget.TextView")));

         if ((PureTreatsVouchersTitle.getText()).equals("Puretreats Vouchers")) {
            System.out.println("Puretreats title is displayed");
            markTestStatus("passed", "Puretreats title is displayed", driver);
         } else {
            markTestStatus("failed", "Puretreats title is not displayed", driver);
            errorcounter++;
         }

         // Verify if voucher card and use now button is available
         AndroidElement VoucherCardName = (AndroidElement) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy
               .id("item_voucher_name")));

         if (VoucherCardName.isDisplayed()) {
            System.out.println("Voucher card is displayed");
            markTestStatus("passed", "Voucher card is displayed", driver);

            // Find all list with use now button
            List<AndroidElement> voucherNameList = driver.findElements(By.id("item_voucher_action"));

            for (int i = 0; i < voucherNameList.size(); i++) {
               if (voucherNameList.get(i).getText().equals("USE NOW")) {
                  System.out.println("Use now button is displayed");
                  markTestStatus("passed", "Use Now button is displayed", driver);
               } else {
                  markTestStatus("failed", "Use Now button is error/not found", driver);
                  errorcounter++;
               }
            }
         }
      } catch (Exception e) {
         markTestStatus("failed", "FAILED: Voucher card is error/not found.", driver);
         errorcounter++;
      }

   }
       
   @Test(priority = 8)
   private void errorChecker() {
      if (errorcounter > 0) {
          markTestStatus("failed", "an error an occurred on Shop Page > See My Vouchers, Please see logs for details", driver);
      } else {
          markTestStatus("passed", "Shop Page > See My Vouchers is successful", driver);
      }
  }

    // This method accepts the status, reason and WebDriver instance and marks the
    // test on BrowserStack
    public static void markTestStatus(String status, String reason, WebDriver driver) {
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
                + status + "\", \"reason\": \"" + reason + "\"}}");
    }

}
