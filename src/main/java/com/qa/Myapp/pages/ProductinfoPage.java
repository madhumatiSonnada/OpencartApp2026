package com.qa.Myapp.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Myapp.Utils.Constants;
import com.qa.Myapp.Utils.ElementUtil;

public class ProductinfoPage 
{
	private WebDriver driver;
	private ElementUtil ele;
	private By productHeader=By.cssSelector("div#content h1");
	private By Productimgcount=By.cssSelector("div#content img");
	private By productmetadate=By.cssSelector("div#product-product ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData=By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
	private By quantity=By.id("input-quantity");
	private By addtocartbtn=By.id("button-cart");
	
	Map<String, String> productinfomap;
	
	public ProductinfoPage(WebDriver driver)
	{
		this.driver=driver;
		ele=new ElementUtil(driver);
	}
	
	public String getProductheadertext()
	{
		return ele.doElementGetText(productHeader).trim();
	}
	
	public int getProductimagecount()
	{
		return ele.waitForElementsVisible(Productimgcount, Constants.DEFAULT_TIMEOUT).size();
	}
	
	public Map<String, String> getproductinfo()
	{
		 productinfomap=new LinkedHashMap<String,String>();
		 productinfomap.put("productname", getProductheadertext());
		 productinfomap.put("imagecount", String.valueOf(getProductimagecount()));
		 getproductmetadata();
		 getpricedata();
		 return productinfomap;
	}
	
	private void getproductmetadata()
	{
		List<WebElement> metadata=ele.waitForElementsVisible(productmetadate, Constants.DEFAULT_TIMEOUT);
		for(WebElement e:metadata)
		{
			String text=e.getText();
			String meta[]=text.split(":");
			String metakey=meta[0].trim();
			String metavalue=meta[1].trim();
			productinfomap.put(metakey, metavalue);
		}
	}
	
	private void getpricedata()
	{
		List<WebElement> pricedata=ele.waitForElementsVisible(productPriceData, Constants.DEFAULT_TIMEOUT);
		String price=pricedata.get(0).getText().trim();
		String exprice=pricedata.get(1).getText().trim();
		productinfomap.put("price", price);
		productinfomap.put("extaxprice", exprice);
		
	}
	
}
