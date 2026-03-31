package com.qa.Myapp.base;


import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;


import com.qa.Myapp.factory.DriverFactory;
import com.qa.Myapp.pages.AccountPage;
import com.qa.Myapp.pages.LoginPage;
import com.qa.Myapp.pages.ProductinfoPage;
import com.qa.Myapp.pages.Registration;
import com.qa.Myapp.pages.SearchResultsPage;

public class BaseTest 
{
	public DriverFactory df;
	public WebDriver driver;
	public LoginPage loginpage;
	public Properties prop;
	public AccountPage accpage;
	public SearchResultsPage searchpage;
	public Registration registrationpage;
	public ProductinfoPage prodinfo;
	public SoftAssert softAssert;
	
	
	@Parameters({ "browser" })
	@BeforeTest
	public void setup(String browser) {
		df = new DriverFactory();
		prop = df.init_prop();

		if (browser != null) {
			prop.setProperty("browser", browser);
			//prop.setProperty("browserversion", browserVersion);
		}

		
			driver = df.init_driver(prop);
			loginpage = new LoginPage(driver);
		    softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}