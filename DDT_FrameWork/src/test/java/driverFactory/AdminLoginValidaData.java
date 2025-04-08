package driverFactory;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import util.AppUtil;
import util.ExcelFileUtil;

public class AdminLoginValidaData extends AppUtil{
String inputpath ="./FileInput/TestData.xlsx";
String outputpath ="./FileOutPut/ValidLoginResults.xlsx";
String TCSheet ="ValidLoginData";
ExtentReports reports;
ExtentTest logger;
@Test
public void startTest() throws Throwable
{
	//define path to generate Extentreports
	reports = new ExtentReports("./target/Validdata.html");
	//create reference object for ExcelFileUtil class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in TCSheet
	int rc =xl.rowCount(TCSheet);
	Reporter.log("No of rows are::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger=reports.startTest("Valid Login Data");
		//read username and password cell from TCSheet
		String user =xl.getCellData(TCSheet, i, 0);
		String pass = xl.getCellData(TCSheet, i, 1);
		logger.log(LogStatus.INFO,user+"======="+ pass);
		//call login method from function library
		FunctionLibrary lp = new FunctionLibrary();
		lp.adminLogin(user, pass);
		boolean res =lp.isAdminDisplayed();
		if(res)
		{
			//if res is true write as pass into status cell 
			xl.setCellData(TCSheet, i, 2, "Pass", outputpath);
			logger.log(LogStatus.PASS, "Valid Credentails");
		}
		else
		{
			//if res is false write as Fail into status cell 
			xl.setCellData(TCSheet, i, 2, "Fail", outputpath);
			logger.log(LogStatus.FAIL, "Invalid Credentails");
		}
		
		reports.endTest(logger);
		reports.flush();
		lp.adminLogout();
	}
	
}
}
