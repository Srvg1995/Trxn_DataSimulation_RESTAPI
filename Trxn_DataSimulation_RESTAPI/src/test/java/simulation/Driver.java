package simulation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class Driver {

	public static String path;
	public static void main(String[] args) throws Throwable {
		String TRANSACTION_ID_VAR = "";
		String TRANSACTION_DATE_VAR = "";
		String TRANSACTION_AMOUNT_VAR = "";
		String PAYER_PSP_VAR = "";
		String PAYEE_PSP_VAR = "";

		//path = "C:\\Users\\gagan\\OneDrive\\Desktop\\dataSimulationToolData\\bank.xlsx"; //Here we are storing the regular path in a variable,but inorder to capture the path in RunTime,below line(System.out.println(args[0]);) is the way.
		 path=args[0]; //This line is to get the EXCEL PATH IN RUNTIME.
		 
		 
		 
		ExcelUtility eLib=new ExcelUtility();

		int rowCount = eLib.getRowcount("trxn");
		System.out.println(rowCount);

		for(int i=1;i<=rowCount;i++) {
			TRANSACTION_ID_VAR="HDFC001002"+new Random().nextInt(10000); //To set the 'TRANSACTION_ID' back to Excel Dynamically
			TRANSACTION_DATE_VAR=new SimpleDateFormat("dd-MM-yyyy").format(new Date());//To set the 'TRANSACTION_DATE' back to Excel Dynamically
			TRANSACTION_AMOUNT_VAR=eLib.getDataFromExcel("trxn", i, 2);
			PAYER_PSP_VAR=eLib.getDataFromExcel("trxn", i, 3);
			PAYEE_PSP_VAR=eLib.getDataFromExcel("trxn", i, 4);

			eLib.setDataIntoExcel("trxn", i, 0, TRANSACTION_ID_VAR);
			eLib.setDataIntoExcel("trxn", i, 1, TRANSACTION_DATE_VAR);

			//			System.out.println(TRANSACTION_ID_VAR);
			//			System.out.println(TRANSACTION_DATE_VAR);
			//			System.out.println(TRANSACTION_AMOUNT_VAR);
			//			System.out.println(PAYER_PSP_VAR);
			//			System.out.println(PAYEE_PSP_VAR);
			//			System.out.println("====================================");

			
			/*
			 * How many times this below RestAssured Program will get execute?
			 * Ans==>Based on no.of data available in EXCEL(if we have 5Row data in Excel,5 times this
			 * RestAssured program will get execute==>This is called 'DATA SIMULATION'.
			 */			

			 String reqBody = "{\"Transaction_ID\":\""+TRANSACTION_ID_VAR+"\",\"Transaction_Mode\":\"ONLINE\",\"Transaction_Date\":\""+TRANSACTION_DATE_VAR+"\",\"Transaction_Time\":\"11:01:28:930 \",\"Transaction_Amount\":\""+TRANSACTION_AMOUNT_VAR+"\",\"Transaction_Type\":\"Transfer\",\"Description\":\"Payer to Payee transaction via online mode\",\"Currency\":\"INR\",\"Location\":\"Mumbai Maharastra\",\"Authorization_Code\":\"C123\",\"Merchant_Information\":\"Merchant Dharavi Mumbai\",\"Batch_Number\":\"06545678\",\"Recurring_Indicator\":\"yes\",\"Tax_Information\":\"GS34567S\",\"Risk_Assessment_Score\":\"199\",\"Promotion_Coupon_Code\":\"CH123\",\"Exchange_Rate\":\"67\",\"Transaction_Code\":\"TR12\",\"Notes\":\"This is a merchant transaction.\",\"Reference_Number\":\"REF991\",\"Device_Information\":\"xiomi Note11\",\"MCC\":\"M123\",\"CVM\":\"OTP\",\"Regulatory_Compliance_Information\":\"KYC\",\"Payer_Details\":{\"Payer_PSP\":\""+PAYER_PSP_VAR+"\",\"Payer_Name\":\"Payer\",\"Bank_Account\":\"HDFC\",\"Account_Type\":\"Savings\",\"IFSC\":\"HDF01\",\"Mobile_Number\":\"9887776676\",\"Address\":\"payer_address@123\",\"Ip_Address\":\"1235@fghj\",\"Mail_Id\":\"mohoan@gmail.com\",\"Balance\":\"690000.90\"},\"Payee_Details\":{\"Payee_PSP\":\""+PAYER_PSP_VAR+"\",\"Payee_Name\":\"Payee\",\"Bank_Account\":\"ICICI\",\"Account_Type\":\"Savings\",\"IFSC\":\"ICIC01\",\"Mobile_Number\":\"9886662222\",\"Address\":\"payee_address@123\",\"Mail_Id\":\"deepak.h@gmail.com\"},\"Transaction_Status\":\"Completed\",\"isUPITransaction\":true,\"Sender_Source\":\"Remitter\",\"Recipient_Destination\":\"Benificiary\"}";
			 given()
			.contentType(ContentType.JSON)
			.body(reqBody)
			.when()
			.post("http://49.249.29.5:8091/add-transaction")
			.then()
			.log().all();

		}

	}

}
