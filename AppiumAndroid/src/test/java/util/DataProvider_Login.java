package util;

import java.lang.reflect.Method;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;


public class DataProvider_Login {

	private String sTestCaseName;

	private int iTestCaseRow;

	@DataProvider(name = "LoginData")
	public Object[][] getLoginData() throws InvalidFormatException {
		Object data[][] = TestUtil.getTestData("login");
		return data;

	}

	@DataProvider(name = "LoginProvider")
	public static Object[][] getDataFromDataprovider(Method m) {

		// Edit the object the first is Email and second is Password
		// return new Object[][] { { "Email", "Password"} };

		// TC1_0_Login_Android_LoginAsRegularUser
		if (m.getName().equalsIgnoreCase("LoginFieldsTest")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC2_0_InStorePickUp_Android
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_inStorePickup")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC3_0_Delivery_Android
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC4_0_AddtoCart_Android_StoreHomepage
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_addToCartStore_Homepage")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC4_1_AddtoCart_Android_Categories
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_addToCartStore_Categories")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC5_0_Cart_Android
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_cart")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC6_0_CheckOut_Android_NoPWalletBalance_PayInStore
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Payinstore")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC6_1_CheckOut_Android_WithPWalletBalance_CashOnDelivery
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_CashOnDelivery")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC7_0_PlaceOrder_Android_InStore_GCash
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Instore_GCash_Method")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC7_1_PlaceOrder_Android_InStore_InStorePayment
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_InStore_InstorePayment")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC7_2_PlaceOrder_Android_InStore_CreditDebit
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_InStore_Credit_Debit")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC7_3_PlaceOrder_Android_InStore_PWallet
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_InStore_Pwallet")) {
			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		}

		// TC7_4_PlaceOrder_Android_InStore_ZeroPayment
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Instore_ZeroPayment_Method")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC8_0_PlaceOrder_Android_Delivery_GCash
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery_Gcash")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC8_1_PlaceOrder_Android_Delivery_COD
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery_COD")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC8_2_PlaceOrder_Android_Delivery_CreditDebit
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery_CreditDebit")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC8_3_PlaceOrder_Android_Delivery_PWallet
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery_Pwallet")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC8_4_PlaceOrder_Android_Delivery_ZeroPayment
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Delivery_ZeroPayment")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC9_0_OrdersTabAndReorder_Android
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Reorder")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC9_1_CancelOrder_Android
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_CancelOrder")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC10_0_Wallet_Android_SignUp
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PWallet_Signup")) {
			return new Object[][] { { "avibal@yondu.com", "Yondu@123!" } };
		}

		// TC10_1_Wallet_Android_CashIn_PayInStore
		// TC10_2_Wallet_Android_CashIn_GCash
		// TC10_3_Wallet_Android_CashIn_CreditDebit
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PWallet_Cashin")) {
			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		}

		// TC11_0_DonationDrive_Android_GuestUser
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_DonationDrive_GuestUser")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC11_1_DonationDrive_Android_RegularUser
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_DonationDrive_RegUser")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC12_0_PureTreats_Android_ForMe
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PureTreats_TreatForMe")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC12_1_PureTreats_Android_ForAFriend
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_PureTreats_TreatForFriend")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC13_0_BillsPay_Android_EcBills
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_BillsPay_EcBills")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC13_1_BillsPay_Android_EcCash
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_BillsPay_EcCash")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC14_0_LoyaltyCardApplication_Android_TNAP
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_LoyaltyCardApplication_TNAP")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC14_1_LoyaltyCardApplication_Android_Perks
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_LoyaltyCardApplication_PERKS")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC15_0_BuyLoad_Android_Regular
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Buyload_Regular")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC15_1_BuyLoad_Android_MobileData
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_Buyload_MobileData")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC16_0_MyAccounts
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_MyAccounts")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC16_1_MyAccounts_MyVoucher
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_MyAccounts_MyVoucher")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC16_2_MyAccounts_Senior_PWD
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_MyAccounts_Senior_PWD")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC16_3_MyAccounts_Address
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_MyAccounts_Address")) {
			return new Object[][] { { "cching@yondu.com", "Yondu@123" } };
		}

		// TC17_Wallet_Android_FundTransfer_SendViaID
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_FundTransfer_SendViaID")) {
			return new Object[][] { { "udon@yopmail.com", "Password123!" } };
		}

		// TC17_1_Wallet_Android_FundTransfer_SendViaQR
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_FundTransfer_SendViaQR")) {
			return new Object[][] { { "udon@yopmail.com", "Password123!" } };
		}

		// TC17_2_Wallet_Android_FundTransfer_RecieveViaQR
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_FundTransfer_RecieveViaQR")) {
			return new Object[][] { { "udon@yopmail.com", "Password123!" } };
		}

		// TC18_0_CaseOrder_Android_ProductDetails
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_AddToCart_CaseOrdering")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC18_1_CaseOrder_Android_StoreHomepage
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_AddToCart_Quantity")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// TC19_0_Cart_ReplacementItems
		else if (m.getName().equalsIgnoreCase("LoginFieldsTest_CartReplacement_AdvancedSearch")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// Negative Login
		else if (m.getName().equalsIgnoreCase("NegativeLoginFieldsTestEmail")) {
			return new Object[][] { { "mnastorsddds@yondu.com", "TestAccount123" } };
		} else if (m.getName().equalsIgnoreCase("NegativeLoginFieldsTestPassword")) {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };
		}

		// Pwallet Balance Account
		else if (m.getName().equalsIgnoreCase("loginWithPwalletBalance")) {
			return new Object[][] { { "udon@yopmail.com", "Password123!" } };
		} else {
			return new Object[][] { { "mnastor@yondu.com", "TestAccount123" } };

		}
	}

	@DataProvider(name = "Selected_Address_Instore")
	public static Object[][] setAddress() {
		return new Object[][] { { "MANLESS STORE" } };

	}

	@DataProvider(name = "GCash_method")
	public static Object[][] getGCash() {
		return new Object[][] { { "GCash Direct" } };

	}

	@DataProvider(name = "CreditDebit_method")
	public static Object[][] getCreditDebit() {
		return new Object[][] { { "Credit/Debit" } };

	}

	@DataProvider(name = "PWallet_method")
	public static Object[][] getPWallet() {
		return new Object[][] { { "Wallet" } };

	}

	@DataProvider(name = "inStore_method")
	public static Object[][] getInStore() {
		return new Object[][] { { "Pay In Cash" } };

	}

	@DataProvider(name = "COD_method")
	public static Object[][] getCOD() {
		return new Object[][] { { "Cash on Delivery" } };

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


}
