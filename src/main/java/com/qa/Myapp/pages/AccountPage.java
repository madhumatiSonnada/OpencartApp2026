package com.qa.Myapp.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.Utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage 
{
	private WebDriver driver;
	private ElementUtil ele;
	
	private By search=By.xpath("//input[@placeholder='Search']");
	private By header=By.xpath("//a[text()='Qafox.com']");
	private By accsectionslist=By.cssSelector("div.col-sm-9 h2");
	private By searchbutton=By.cssSelector("div#search button");
	
	public AccountPage(WebDriver driver)
	{
		this.driver=driver;
		ele=new ElementUtil(driver);
	}
	@Step("Verifying account page title")
	public String accpageTitle()
	{
		return ele.waitForTitleIs(Constants.ACCOUNT_PAGE_TITLE,Constants.DEFAULT_TIMEOUT);
	
	}
	@Step("Verifying account page header exists")
	public boolean isAccountpageheaderexists()
	{
		System.out.println(driver.findElement(header).getText());
		return ele.checkElementIsDisplayed(header);
	
	}
	@Step("Verifying search exists")
	public boolean isSearchexists()
	{
		return ele.checkElementIsDisplayed(search);
	}
	
	@Step("searching for product")
	public SearchResultsPage dosearch(String productname)
	{
		if(isSearchexists())
		{
			ele.doSendKeys(search, productname);
			ele.doClick(searchbutton);
			return new SearchResultsPage(driver);
		}
		return null;
	
	}
	@Step("Getting account section list")
	public List<String> getaccountsectionlist()
	{
		List<WebElement> el=ele.getElements(accsectionslist);
		List<String> seclist=new ArrayList<String>();
		for(WebElement e:el)
		{
			String text=e.getText();
			System.out.println(text);
			seclist.add(text);
		}
		return seclist;
	}
}
