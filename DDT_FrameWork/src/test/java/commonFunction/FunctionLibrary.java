package commonFunction;

import org.openqa.selenium.By;
import org.testng.Reporter;

import util.AppUtil;

public class FunctionLibrary  extends AppUtil{
//method for login
	public void adminLogin(String user,String pass)
	{
		driver.findElement(By.xpath(conpro.getProperty("ObjUser"))).sendKeys(user);
		driver.findElement(By.xpath(conpro.getProperty("ObjPass"))).sendKeys(pass);
		driver.findElement(By.xpath(conpro.getProperty("ObjLogin"))).click();
		
	}
	//method for verify admin link
	public boolean isAdminDisplayed()
	{
		if(driver.findElement(By.xpath(conpro.getProperty("ObjAdmin"))).isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//method for verifing error message
	public boolean isErrorMessageDiaplayed()
	{
		String Error_mess = driver.findElement(By.xpath(conpro.getProperty("ObjError"))).getText().toLowerCase();
		if(Error_mess.contains("invalid") || Error_mess.contains("empty"))
		{
			Reporter.log(Error_mess,true);
		return true;	
		}
		else
		{
			return false;
		}

	}
	//method for logout
	public void adminLogout()
	{
		driver.findElement(By.xpath(conpro.getProperty("ObjWelcome"))).click();
		driver.findElement(By.xpath(conpro.getProperty("ObjLogout"))).click();
	}
	
}
