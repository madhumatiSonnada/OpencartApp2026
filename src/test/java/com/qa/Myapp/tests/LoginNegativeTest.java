package com.qa.Myapp.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.Myapp.Utils.Errors;
import com.qa.Myapp.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Epic("Epic 100- Design login page for opencart")
@Story("US-101 designing login page feature")
public class LoginNegativeTest extends BaseTest
{
	
	@DataProvider
	public Object[][] invaliddata()
	{
		return new Object[][]
				{
			{"Madhumati123","Madhu"},
			{" ","test@123"},
			{" "," "},
			{"madhu","  "}
				};
	}
	
	@Test(dataProvider = "invaliddata")
	@Description("Login page test with invalid credentials.........")
	@Severity(SeverityLevel.NORMAL)
	public void loginNegativeTest(String un,String pswd)
	{
	Assert.assertTrue(loginpage.doinvalidlogin(un, pswd));
		
		
	}
}
