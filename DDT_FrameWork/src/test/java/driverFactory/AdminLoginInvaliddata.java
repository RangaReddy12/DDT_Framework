package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import util.AppUtil;
import util.ExcelFileUtil;

public class AdminLoginInvaliddata extends AppUtil{
	String inputpath ="./FileInput/TestData.xlsx";
	String outputpath ="./FileOutPut/InvalidLoginData.xlsx";
	ExtentReports reports;
	ExtentTest logger;
	String TCSheet ="InvalidLoginData";
	@Test
	public void startTest() throws Throwable
	{
		//define path to generate Extentreports
		reports = new ExtentReports("./target/InValiddata.html");
		//create reference object for ExcelFileUtil class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//count no of rows in TCSheet
		int rc = xl.rowCount(TCSheet);
		Reporter.log("No of rows are::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			logger= reports.startTest("Invalid Login Data");
			logger.assignAuthor("Ranga");
			logger.assignCategory("Invalid Data");
			String user = xl.getCellData(TCSheet, i, 0);
			String pass = xl.getCellData(TCSheet, i, 1);
			FunctionLibrary lp = new FunctionLibrary();
			lp.adminLogin(user, pass);
			boolean res = lp.isErrorMessageDiaplayed();
			if(res)
			{
				File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screen, new File("./target/Screenshot/"+i+"  "+"invalidlogin.png"));
				xl.setCellData(TCSheet, i, 2, "Pass", outputpath);
				logger.log(LogStatus.PASS, "Invalid Credentails");
				logger.addScreenCapture("./target/Screenshot/"+i+"  "+"invalidlogin.png");
			}
			else
			{
				xl.setCellData(TCSheet, i, 2, "Fail", outputpath);
				logger.log(LogStatus.FAIL, "Valid Credntails");
			}
			reports.endTest(logger);
			reports.flush();
		}
		
		
	}

}
