package com.qa.Myapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.Utils.ElementUtil;
import com.qa.Myapp.Utils.Errors;

import io.qameta.allure.Step;

public class LoginPage 
{
	//private by locators
		private WebDriver driver;
		private ElementUtil el;
		
		private By emailId=By.id("input-email");
		private By password=By.id("input-password");
		private By login=By.xpath("//input[@value='Login']");
		private By forgotpswd=By.linkText("Forgotten Password");
		private By reglink=By.linkText("Register");
		private By loginerrormsg=By.cssSelector("div.alert.alert-danger.alert-dismissible");
		
		//public constructor
		public LoginPage(WebDriver driver)
		{
			this.driver=driver;
			el=new ElementUtil(driver);
		}
		
		//public page actions
		@Step("Getting login page title")
		public String getpageTitle()
		{
			return el.waitForTitleIs(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIMEOUT);
		}
		@Step("Getting login page URL")
		public String getLoginpageurl()
		{
			return el.waitForURLContains(Constants.LOGINPAGE_URL_FRACTION, Constants.DEFAULT_TIMEOUT);
		}
		
		@Step("checking whether forgotpswd list present")
		public boolean forgotpswdlink()
		{
			return el.checkElementIsDisplayed(forgotpswd);
		}
		@Step("login to the application with username{0} and pswd{1}")
		public AccountPage dologin(String username,String pswd)
		{
			//el.doSendKeys(emailId, username);
			el.waitForElementVisible(emailId, Constants.DEFAULT_TIMEOUT).sendKeys(username);
			el.doSendKeys(password, pswd);
			el.doClick(login);
			return new AccountPage(driver);
		}
		@Step("login to the application with invalid username{0} and pswd{1}")
		public boolean doinvalidlogin(String username,String pswd)
		{
			WebElement Email=el.waitForElementVisible(emailId,Constants.DEFAULT_TIMEOUT);
			Email.clear();
			Email.sendKeys(username);
			el.doSendKeys(password, pswd);
			el.doClick(login);
            String Actualerrormsg=el.doElementGetText(loginerrormsg);
            System.out.println(Actualerrormsg);
            if(Actualerrormsg.contains(Errors.LOGIN_ERROR_MESSAGE))
            {
            	return true;
            }
            return false;
			
		}
		@Step("registration link exists")
		public boolean isReglinkexist()
		{
			return el.waitForElementVisible(reglink, Constants.DEFAULT_TIMEOUT).isDisplayed();
		}
		@Step("Registrations the user")
		public Registration doRegister()
		{
			if(isReglinkexist())
			{
				el.doClick(reglink);
				return new Registration(driver);
			}
			return null;
		}
		
}
