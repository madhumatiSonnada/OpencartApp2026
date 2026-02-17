package com.qa.Myapp.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.Myapp.base.BaseTest;

public class RegistrationPageTest extends BaseTest
{
	@BeforeClass
	public void registrationsetup()
	{
		registrationpage=loginpage.doRegister();
	}
	public String randomEmail()
	{
		Random ran=new Random();
		String email="automation"+ran.nextInt(1000)+"@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegisterData()
	{
		return new Object[][]
				{
			{"madhu","sonn","7898789678","madhu@123","no"}
			,{"hanu","babale","898867567","hanu@123","no"}
				};
	}
	
	@Test(dataProvider ="getRegisterData" )
	public void userRegistrationTest(String firstname,String lastname,String tele,String pswd,String subscribe)
	{
		Assert.assertTrue(registrationpage.accountRegistration(firstname,lastname,randomEmail(),tele,pswd,subscribe));
	}
}
