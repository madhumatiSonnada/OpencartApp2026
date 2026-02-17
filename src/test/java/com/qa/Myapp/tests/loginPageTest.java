package com.qa.Myapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Epic("Epic 100- Design login page for opencart")
@Story("US-101 designing login page feature")
public class loginPageTest extends BaseTest
{
	@Test
	@Description("Login page title test.........")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageTitleTest()
	{
		String title=loginpage.getpageTitle();
		Assert.assertEquals(title,Constants.LOGIN_PAGE_TITLE);
	}
	
	@Test
	@Description("Login page URL test.........")
	@Severity(SeverityLevel.NORMAL)
	public void isloginpageurlTest()
	{
		String URL=loginpage.getLoginpageurl();
		Assert.assertTrue(URL.contains(Constants.LOGINPAGE_URL_FRACTION));
	}
	
	@Test
	@Description("Login page forgotpswd link test.........")
	@Severity(SeverityLevel.CRITICAL)
	public void forgotpswdlink()
	{
		Assert.assertTrue(loginpage.forgotpswdlink());
	}
	@Test
	@Description("Login page test with correct credentials.........")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest()
	{
		accpage=loginpage.dologin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertTrue(accpage.isAccountpageheaderexists());
		
	}
	@Test
	@Description("Registration link test.........")
	@Severity(SeverityLevel.CRITICAL)
	public void isreglinkexist()
	{
		Assert.assertTrue(loginpage.isReglinkexist());
	}
}
