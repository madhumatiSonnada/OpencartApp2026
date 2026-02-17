package com.qa.Myapp.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.base.BaseTest;
import com.qa.Myapp.pages.ProductinfoPage;

public class ProductinfopageTest extends BaseTest
{
	@BeforeClass
	public void prodInfoSetup() 
	{
		accpage = loginpage.dologin(prop.getProperty("username"), prop.getProperty("password"));
	}
	@DataProvider
	public Object[][] getProductdata()
	{
		return new Object[][] 
	{
			{"Macbook","MacBook Pro"},
			{"Macbook","MacBook Air"},
			{"Apple","Apple Cinema 30\""}
	};
	}
	@Test(dataProvider = "getProductdata")
	public void productheaderTest(String searchkey,String mainproduct)
	{
		searchpage=accpage.dosearch(searchkey);
		prodinfo=searchpage.Selectmainproduct(mainproduct);
		Assert.assertEquals(prodinfo.getProductheadertext(),mainproduct);
		
	}
	
	@DataProvider
	public Object[][] getimagecount()
	{
		return new Object[][] 
				{
						{"Macbook","MacBook Pro",4},
						{"Macbook","MacBook Air",4},
						{"Apple","Apple Cinema 30\"",8}
				};
	}
	@Test(dataProvider = "getimagecount")
	public void prodimgcountTest(String key,String mainprod,int imgcnt)
	{
		searchpage=accpage.dosearch(key);
		prodinfo=searchpage.Selectmainproduct(mainprod);
	Assert.assertTrue(prodinfo.getProductimagecount()==imgcnt);
	}
	@DataProvider
	public Object[][] productinfodata()
	{
		return new Object[][]
				{
			{"macbook","MacBook Pro","MacBook Pro","4","Apple","Product 18","800","Out Of Stock","$2,000.00","$2,000.00"},
			  {"MacBook","MacBook","MacBook","5","Apple","Product 16","600","Out Of Stock","$602.00","$500.00"},
			  {"MacBook","MacBook Air","MacBook Air","4","Apple","Product 17","700","Out Of Stock","$1,202.00","$1,000.00"}
			
			

			//String searchkey,String mainprod,String prodname,
				};
	}
	
	@Test(dataProvider = "productinfodata")
	public void productinfoTest(String searchkey,String mainprod,String prodname,String imgcnt,String brand,
			String prodcode,String rewardpoint,String availablity,String price,String exprice)
	{
		searchpage=accpage.dosearch(searchkey);
		prodinfo=searchpage.Selectmainproduct(mainprod);
		Map<String,String> proddata=prodinfo.getproductinfo();
		proddata.forEach((k,v) -> System.out.println(k + ":" + v));
		
		softAssert.assertEquals(proddata.get("productname"), prodname);
		softAssert.assertEquals(proddata.get("imagecount"), imgcnt);
		softAssert.assertEquals(proddata.get("Brand"), brand);
		softAssert.assertEquals(proddata.get("Reward Points"),rewardpoint);
		softAssert.assertEquals(proddata.get("price"), price);
		//softAssert.assertEquals(proddata.get("exprice"), exprice);
		softAssert.assertAll();
	}
	
}
