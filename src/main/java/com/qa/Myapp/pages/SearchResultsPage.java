package com.qa.Myapp.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.Utils.ElementUtil;

public class SearchResultsPage 
{
	
	private WebDriver driver;
	private ElementUtil ele;
	private By searchhearder=By.cssSelector("div#content h1");
	private By productslist=By.cssSelector("div.caption a");
	
	public SearchResultsPage(WebDriver driver)
	{
		this.driver=driver;
		ele=new ElementUtil(driver);
	}
	
	public String issearchheaderexists()
	{
		if(ele.checkElementIsDisplayed(searchhearder))
		{
		return ele.doElementGetText(searchhearder);
	}
	return null;
	}
	public int getproductcount()
	{
		return ele.waitForElementsVisible(productslist, Constants.DEFAULT_TIMEOUT).size();
	}
	public List<String> getproductlist()
	{
		List<WebElement> searchlist=ele.waitForElementsVisible(productslist, Constants.DEFAULT_TIMEOUT);
		List<String> list=new ArrayList<String>();
		for(WebElement e:searchlist)
		{
			String text=e.getText();
			System.out.println(text);
			list.add(text);
		}
		return list;
	}
	
	public ProductinfoPage Selectmainproduct(String productname)
	{
		System.out.println("The productname is "+productname);
		List<WebElement> searchlist=ele.waitForElementsVisible(productslist, Constants.DEFAULT_TIMEOUT);
		for(WebElement e:searchlist)
		{
			String text=e.getText();
			if(text.equals(productname))
			{
				e.click();
				break;
			}
		
		}
		return new ProductinfoPage(driver);
	}
	
}
