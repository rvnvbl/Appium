import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.core.config.AwaitUnconditionallyReliabilityStrategy;
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
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import util.DataProvider_Login;
import util.ExcelDataSupllier;

/**
 * @author avibal
 *
 */
public class TC24_1_Referral_iOS_GenerateReferral extends SetupIOS {

	int failedCounter = 0;

	@Test(priority = 0)
	public void LoginButtonTest() throws Exception {
		// Clicking of Login with email button on startup screen

		try {
			IOSElement btn_login = (IOSElement) new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Log in")));
			btn_login.click();

			// verify if email textfield is visible and enabled
			IOSElement textfield_email = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Email")));
			// verify if Login Button is visible and enabled
			IOSElement btn_login2 = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Login")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Login Button is not found.", driver);
			failedCounter++;
		}
	}

	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = ExcelDataSupllier.class)
	public void LoginFieldsTest_Referral_GenerateReferral(String email, String password) throws Exception {

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// Clicking and input of email on email field
		try {
			IOSElement EmailField = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Email")));
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
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Password Field is not found.", driver);
			failedCounter++;
		}

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Clicking of login button after the input of the above credentials
		try {
			IOSElement LoginButton = (IOSElement) driver.findElement(MobileBy.AccessibilityId("Login"));
			LoginButton.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
			IOSElement btn_pwallet = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("home wallet")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			markTestStatus("passed", "PASSED: User is redirected to Home Page; P-Wallet is displayed.", driver);

		} catch (Exception f) {
			markTestStatus("failed", "FAILED: User is not redirected to Home Page; P-Wallet is not displayed.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 3)
	public void navaigateToReferralPage() throws Exception {
		// Checking of P-Wallet Button if visible
		try {
			IOSElement btn_account = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));
			btn_account.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Account button is not found.", driver);
			failedCounter++;
		}

		// Checking of P-Wallet Button if visible
		try {
			IOSElement accountTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Account")));

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Account button is not found.", driver);
			failedCounter++;
		}

		// Checking of P-Wallet Button if visible
		try {
			IOSElement referallButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("Referrals")));

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			referallButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Account button is not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 4)
	private void validateReferralPage() {
		// CCheck and validate the share my referral image
		try {
//			IOSElement imgReferral = (IOSElement) new WebDriverWait(driver, 30)
//					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("referralImg")));

			List<IOSElement> imgList = driver.findElements(By.className("XCUIElementTypeImage"));
			System.out.println(imgList.size());
			if (imgList.size() < 0) {
				markTestStatus("failed", "FAILED: Referral image is not found.", driver);
				failedCounter++;
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Referral image is not found.", driver);
			failedCounter++;
		}

		// Check and validate the share my referral title
		try {
			IOSElement referralTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("referralTitleLbl")));
			if (!referralTitle.getText().equals("Refer and Get Rewards!")) {
				markTestStatus("failed", "FAILED: Referral title is not correct", driver);
				failedCounter++;
			}

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Referral title is not found.", driver);
			failedCounter++;
		}

		// Check and validate the share my referral message label
		try {
			IOSElement referralMessage = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("referralMessageLbl")));

			if (!referralMessage.getText()
					.equals("Invite friends & family and earn discounts on every successful referral.")) {
				markTestStatus("failed", "FAILED: Referral message is not correct", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Referral message is not found.", driver);
			failedCounter++;
		}

		// Check and validate the share my referral code label
		try {
			IOSElement referralCode = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("referralCodeLbl")));
			if (referralCode.getText().length() != 8) {
				markTestStatus("failed", "FAILED: Referral code is not have 8 characters.", driver);
				failedCounter++;
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Referral code is not found.", driver);
			failedCounter++;
		}

		// Check and validate the share my referral code button
		try {
			IOSElement shareMyReferralButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("referralBtn")));

			if (!shareMyReferralButton.getText().equals("Share my Referral Code")) {
				markTestStatus("failed", "FAILED: Share my referral button text is not correct.", driver);
				failedCounter++;
			}

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: Share my referral button is not found.", driver);
			failedCounter++;
		}

	}

	@Test(priority = 5)
	private void howReferraWorksLink() {
		// Check and validate the how referral work link
		try {
			IOSElement howReferralLink = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("howLbl")));

			if (!howReferralLink.getText().equals("How does referral work?")) {
				markTestStatus("failed", "FAILED: How referral work message is not correct", driver);
				failedCounter++;
			}

			howReferralLink.click();

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: How does referral work link is not found", driver);
			failedCounter++;
		}

		// Check and validate the how it work title
		try {
			IOSElement howItWorksTitle = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("How it works?")));

		} catch (Exception e) {
			markTestStatus("failed", "FAILED: How referral title is not found.", driver);
			failedCounter++;
		}

		// Check and validate the text in how it works page
		try {
			int counter = 5;
			List<IOSElement> textList = driver.findElements(By.className("XCUIElementTypeStaticText"));
			for (int i = 0; i < textList.size(); i++) {
				if (textList.get(i).getText().contains(
						"1. Tap the “Share my Referral Code” button to send an invite to your friends or family.")) {
					counter--;
					System.out.println("Number 1 is found");
				}

				if (textList.get(i).getText().contains("2. Download the app, sign up and enter the Referral code.")) {
					counter--;
					System.out.println("Number 2 is found");
				}
				if (textList.get(i).getText().contains(
						"3. Upon successful registration, you and your friend/family will receive promo voucher & discount.")) {
					counter--;
					System.out.println("Number 3 is found");
				}
				if (textList.get(i).getText().contains(
						"4. Your friend/family will receive P200 discount and can be used on their first order.")) {
					counter--;
					System.out.println("Number 4 is found");
				}
				if (textList.get(i).getText().contains(
						"5. You will receive the promo voucher after your friend/family place their first order.")) {
					counter--;
					System.out.println("Number 5 is found");
				}
			}

			if (counter != 0) {
				markTestStatus("failed", "FAILED: How referral text is not complete.", driver);
				failedCounter++;
			}
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: How referral text is not found.", driver);
			failedCounter++;
		}

		// Check and validate the how it work title
		try {
			IOSElement howItWorksButton = (IOSElement) new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.name("OKAY")));
			howItWorksButton.click();
		} catch (Exception e) {
			markTestStatus("failed", "FAILED: How referral button is not found.", driver);

		}
	}

	@Test(priority = 20)
	public void CheckFailedCount() throws Exception {

		if (failedCounter != 0) {
			markTestStatus("failed", "FAILED: Referral > Generate Referral has error/s.", driver);
		} else {
			markTestStatus("passed", "PASSED: Referral > Generate Referral successful.", driver);
		}

	}

	// This method accepts the status, reason and WebDriver instance and marks the
	// test on BrowserStack
	public static void markTestStatus(String status, String reason, WebDriver driver) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}

	// Code below is for the swipe actions

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	public void swipeElementIOS(MobileElement el, Direction dir) {
		System.out.println("swipeElementIOS(): dir: '" + dir + "'"); // always log your actions

		// Animation default time:
		// - Android: 300 ms
		// - iOS: 200 ms
		// final value depends on your app and could be greater
		final int ANIMATION_TIME = 200; // ms

		final int PRESS_TIME = 500; // ms

		// init screen variables
		Dimension dims = driver.manage().window().getSize();
		Rectangle rect = el.getRect();

		// check element overlaps screen
		if (rect.x >= dims.width || rect.x + rect.width <= 0 || rect.y >= dims.height || rect.y + rect.height <= 0) {
			throw new IllegalArgumentException("swipeElementIOS(): Element outside screen");
		}

		// init borders per your app screen
		// or make them configurable with function variables
		int leftBorder, rightBorder, upBorder, downBorder;
		leftBorder = 0;
		rightBorder = 0;
		upBorder = 0;
		downBorder = 0;

		// find rect that overlap screen
		if (rect.x < 0) {
			rect.width = rect.width + rect.x;
			rect.x = 0;
		}
		if (rect.y < 0) {
			rect.height = rect.height + rect.y;
			rect.y = 0;
		}
		if (rect.width > dims.width)
			rect.width = dims.width;
		if (rect.height > dims.height)
			rect.height = dims.height;

		PointOption pointOptionStart, pointOptionEnd;
		switch (dir) {
		case DOWN: // from up to down
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + upBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - downBorder);
			break;
		case UP: // from down to up
			pointOptionStart = PointOption.point(rect.x + rect.width / 2, rect.y + rect.height - downBorder);
			pointOptionEnd = PointOption.point(rect.x + rect.width / 2, rect.y + upBorder);
			break;
		case LEFT: // from right to left
			pointOptionStart = PointOption.point(rect.x + rect.width - rightBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + leftBorder, rect.y + rect.height / 2);
			break;
		case RIGHT: // from left to right
			pointOptionStart = PointOption.point(rect.x + leftBorder, rect.y + rect.height / 2);
			pointOptionEnd = PointOption.point(rect.x + rect.width - rightBorder, rect.y + rect.height / 2);
			break;
		default:
			throw new IllegalArgumentException("swipeElementIOS(): dir: '" + dir + "' NOT supported");
		}

		// execute swipe using TouchAction
		try {
			new TouchAction(driver).press(pointOptionStart)
					// a bit more reliable when we add small wait
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release()
					.perform();
		} catch (Exception e) {
			System.err.println("swipeElementIOS(): TouchAction FAILED\n" + e.getMessage());
			return;
		}

		// always allow swipe action to complete
		try {
			Thread.sleep(ANIMATION_TIME);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	// Scroll to element value
	private void scrollToElement(String elementValue) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		HashMap<String, Object> scrollobj = new HashMap<String, Object>();
		scrollobj.put("predicateString", "value == '" + elementValue + "'");
		scrollobj.put("direction", "down");
		executor.executeScript("mobile: scroll", scrollobj);

	}

}
