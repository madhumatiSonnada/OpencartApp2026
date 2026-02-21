package com.qa.Myapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class cartpage 
{

	
	public static void main(String[] args) 
	{
		By login=By.id("login");
		// TODO Auto-generated method stub
		System.out.println("This is my cart page ");
	
	}

	public void click()
	{
	  WebDriver driver=new ChromeDriver();
	  //driver.findElement(login);
	}
}
