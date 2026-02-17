package com.qa.Myapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.Utils.ElementUtil;

public class Registration 
{
	private WebDriver driver;
	private ElementUtil ele;
	
	private By regheader=By.cssSelector("div#content h1");
	private By Firstname=By.id("input-firstname");
	private By lastname=By.id("input-lastname");
	private By email=By.id("input-email");
	private By phone=By.id("input-telephone");
	private By password=By.id("input-password");
	private By confpswd=By.id("input-confirm");
	private By subscribeyes=By.xpath("//label[@class='radio-inline']//input[@name='newsletter' and @value='0']");
	private By subscribeno=By.xpath("//label[@class='radio-inline']//input[@name='newsletter' and @value='1']");
	private By agreecheck=By.name("agree");
	private By continuebtn=By.cssSelector("input.btn");	
	private By successmsg=By.cssSelector("div#content h1");
	private By logoutlink=By.linkText("Logout");
	private By Registerlink=By.linkText("Register");
	
	public Registration(WebDriver driver)
	{
		this.driver=driver;
		ele=new ElementUtil(driver);
	}
	public boolean accountRegistration(String Firstname,String lastname,String email,String phone,String password
			,String subscribe)
	{
		ele.waitForElementVisible(this.Firstname, Constants.DEFAULT_TIMEOUT).sendKeys(Firstname);
		ele.doSendKeys(this.lastname, lastname);
		ele.doSendKeys(this.email, email);
		ele.doSendKeys(this.phone, phone);
		ele.doSendKeys(this.password, password);
		ele.doSendKeys(this.confpswd, password);
		
		if(subscribe.equalsIgnoreCase("yes"))
		{
			ele.doClick(subscribeyes);
		}
		else
		{
			ele.doClick(subscribeno);
		}
		
		ele.doClick(agreecheck);
		ele.doClick(continuebtn);
		
		if(getaccountsuccessmsg().contains(Constants.SUCCESS_MSG))
		{
			gotoRegistrationpage();
			return true;
		}
		return false;
	}
	
	public String  getaccountsuccessmsg()
	{
		return ele.waitForElementVisible(successmsg, Constants.DEFAULT_TIMEOUT).getText();
	}
	private void gotoRegistrationpage()
	{
		ele.doClick(logoutlink);
		ele.doClick(Registerlink);
	}
}
