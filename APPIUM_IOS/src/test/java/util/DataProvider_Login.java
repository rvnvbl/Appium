package util;

import java.lang.reflect.Method;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;

public class DataProvider_Login {

	@DataProvider(name = "LoginData")
	public Object[][] getLoginData() throws InvalidFormatException {
		Object data[][] = TestUtil.getTestData("login");
		return data;

	}

	@DataProvider(name = "LoginProvider")
	public static Object[][] getDataFromDataprovider(Method m) {
		if (m.getName().equalsIgnoreCase("LoginFieldsTest")) {
			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_cart")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_inStore_Checkout")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery_Checkout")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_GCash_Method")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_InStore_Delivery")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_CreditDebit_Method")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_ZeroPayment_Method")) {

			return new Object[][] { { "automationsix@mailinator.com", "Password123!" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PWallet_Method")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PWallet_Setup")) {

			return new Object[][] { { "automation2@mailinator.com", "Password123!" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_DonationDrive_RegUser")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_DonationDrive_GuestUser")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PureTreats_TreatForMe")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PureTreats_TreatForFriend")) {

			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("NegativeLoginEmailTest")) {

			return new Object[][] { { "cching_yondu.com", "Yondu@123" } };
		} else if (m.getName().equalsIgnoreCase("NegativeLoginPasswordTest")) {
			return new Object[][] { { "cching@yondu.com", "Yondu@124" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Pwallet_withBalance")) {
			return new Object[][] { { "udon@yopmail.com", "Password123!" } };
		} else if (m.getName().equalsIgnoreCase("LoginFieldsTest_CaseOrder")) {
			return new Object[][] { { "liliaubaldo@mailinator.com", "Password123!" } };
		} else {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}
	}

	@DataProvider(name = "GCash_method")
	public static Object[][] getGCash() {
		return new Object[][] { { "GCash Direct" } };

	}

	@DataProvider(name = "CreditDebit_method")
	public static Object[][] getCreditDebit() {
		return new Object[][] { { "Credit / Debit Card" } };

	}

	@DataProvider(name = "PWallet_method")
	public static Object[][] getPWallet() {
		return new Object[][] { { "Puregold Wallet" } };

	}

	@DataProvider(name = "inStore_method")
	public static Object[][] getInStore() {
		return new Object[][] { { "Pay In Store" } };

	}

	@DataProvider(name = "COD_method")
	public static Object[][] getCOD() {
		return new Object[][] { { "Cash On Delivery" } };

	}

	@DataProvider(name = "AddVoucher")
	public static Object[][] getVoucher() {
		return new Object[][] { { "Automation0613" } };

	}

	@DataProvider(name = "WalletPIN")
	public static Object[][] getPIN() {
		return new Object[][] { { "111111" } };

	}

	@DataProvider(name = "AddressProvider")
	public static Object[][] getAddressData() {
		return new Object[][] { { "Carlos Miguel", "Ching", "Test3", "9165513648", "0002" } };

	}

	@DataProvider(name = "AddressProviderNegative")
	public static Object[][] getAddressDataNegative() {
		return new Object[][] { { "Carlos Miguel", "Ching", "Test3", "9165513648", "xyzvnm" } };

	}

	@DataProvider(name = "RecipientDetails")
	public static Object[][] getRecipientData() {
		return new Object[][] { { "FName Test", "Lname Test", "9123456789", "test@test.com", "Test Message" } };

	}

	@DataProvider(name = "ecBills")
	public static Object[][] getecBillsData() {
		return new Object[][] { { "09123456789", "Name Test", "1000" } };

	}

	@DataProvider(name = "ecCash")
	public static Object[][] getecCashData() {
		return new Object[][] { { "235805121", "09123456789", "1000" } };

	}

	@DataProvider(name = "TNAP_Card")
	public static Object[][] getApplicationData() {
		return new Object[][] {
				{ "Fname", "Mname", "Lname", "8881234", "9165513679", "cching@yondu.com", "1111", "Test Address" } };

	}

	@DataProvider(name = "PERKS_Card")
	public static Object[][] getApplicationDetails() {
		return new Object[][] { { "Fname", "Mname", "Lname", "2", "8881234", "9165513679", "cching@yondu.com", "1111",
				"Test Address", "Test Company" } };

	}

	@DataProvider(name = "PwalletID")
	public static Object[][] setPwalletCredentials() {
		return new Object[][] { { "cching@yondu.com", "6104529967122689", "1" } };

	}

}
