package com.qa.Myapp.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 102- Design Account page for opencart")
@Story("US-102 designing Account page feature")
public class AccountsPageTest extends BaseTest
{
	@Description("login before account test")
	@BeforeClass
	public void accountSetup()
	{
		accpage=loginpage.dologin(prop.getProperty("username"), prop.getProperty("password"));
	}
	@Test
	@Description("Account title test")
	@Severity(SeverityLevel.MINOR)
	public void acctitletest()
	{
		String title=accpage.accpageTitle();
		System.out.println(title);
		Assert.assertEquals(title, Constants.ACCOUNT_PAGE_TITLE);
	}
	@Test
	@Description("Account header test")
	@Severity(SeverityLevel.MINOR)
	public void accpageheaderexists()
	{
		
		Assert.assertTrue(accpage.isAccountpageheaderexists());
	}
	@Test
	@Description("Is search field exists in account page")
	@Severity(SeverityLevel.CRITICAL)
	public void issearchexists()
	{
		Assert.assertTrue(accpage.isSearchexists());
	}
	@Test
	@Description("Account section list test")
	@Severity(SeverityLevel.NORMAL)
	public void accsectionlist()
	{
		List<String> list=accpage.getaccountsectionlist();
		Assert.assertEquals(list, Constants.ACC_SECTION_LIST);
	}
	@Test
	@Description("search header test")
	@Severity(SeverityLevel.MINOR)
	public void issearchheaderexists()
	{
		searchpage=accpage.dosearch("Macbook");
		String headertext=searchpage.issearchheaderexists();
		Assert.assertTrue(headertext.contains("Macbook"));
		
	}
	@Test
	@Description("Product count test")
	@Severity(SeverityLevel.MINOR)
	public void productcounttest()
	{
		searchpage=accpage.dosearch("Macbook");
		int productcount=searchpage.getproductcount();
		Assert.assertEquals(productcount, Constants.MACBOOK_PRODUCT_COUNT);
	}
	@Test
	@Description("product list test")
	@Severity(SeverityLevel.NORMAL)
	public void productlistTest()
	{
		searchpage=accpage.dosearch("Macbook");
		List<String> prodlist=searchpage.getproductlist();
		System.out.println(prodlist);
	}
}
