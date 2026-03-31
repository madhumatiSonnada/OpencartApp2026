package com.qa.Myapp.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.log4testng.Logger;

import com.qa.Myapp.Utils.Browser;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory 
{
	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager opmanager;
	public static ThreadLocal<WebDriver> tldriver=new ThreadLocal<WebDriver>();
	
	public static final Logger log=Logger.getLogger(DriverFactory.class);
	
	public WebDriver init_driver(Properties prop)
	{
		String browsername=prop.getProperty("browser").trim();
		
		//System.out.println("The browser name is" +browsername);
		log.info("The browser name is  " +browsername);
		highlight=prop.getProperty("highlight").trim();
		opmanager=new OptionsManager(prop);
		
		if(browsername.equalsIgnoreCase(Browser.CHROME_BROWSER_VALUE))
		{
			log.info("running test on chrome brower....");
			
			if (Boolean.parseBoolean(prop.getProperty("remote")))
			{
				init_remoteWebDriver(Browser.CHROME_BROWSER_VALUE);
			}
			else 
			{
				//local execution
				WebDriverManager.chromedriver().setup();
				//driver=new ChromeDriver(opmanager.getChromeoptions());
				tldriver.set(new ChromeDriver(opmanager.getChromeoptions()));
			}
			
		}
		else if(browsername.equalsIgnoreCase(Browser.FIREFOX_BROWSER_VALUE))
		{
			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				init_remoteWebDriver(Browser.FIREFOX_BROWSER_VALUE);
			}
			else
			{
				WebDriverManager.chromedriver().setup();
				//driver=new FirefoxDriver();
				tldriver.set(new FirefoxDriver(opmanager.getFirefoxOptions()));
			}
			
		}
		else if(browsername.equalsIgnoreCase(Browser.SAFARI_BROWSER_VALUE))
		{
			driver=new SafariDriver();
		}
		else
		{
			log.info("Please pass the correct browser" +browsername);
			//System.out.println("Please pass the correct browser");
		}
		//driver.manage().deleteAllCookies();
		getDriver().manage().window().fullscreen();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	private void init_remoteWebDriver(String browsername )
	{
		
		System.out.println("Running test cases on remote grid server: " +browsername);
		if(browsername.equalsIgnoreCase("chrome"))
			
		{
				try {
					tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),opmanager.getChromeoptions()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		else if(browsername.equalsIgnoreCase("firefox"))
			
		{
				try {
					tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),opmanager.getFirefoxOptions()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static WebDriver getDriver()
	{
		return tldriver.get();
	}
	
	public Properties init_prop()
	{
		prop=new Properties();
		FileInputStream in=null;
		String envname=System.getProperty("env");
		System.out.println("Environment name is "+envname);
		if(envname==null)
		{
			System.out.println("No env name is given so running in the default env");
			try 
			{
				in=new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				switch (envname.toLowerCase()) 
				{
				case "qa":
					in=new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					in=new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				default:
					System.out.println("Please pass the right env name"+envname);
					break;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
		
}
	
	public static String getScreenshot() 
	{
	File srcfile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
	String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
	File destination = new File(path);
	try {
		FileUtils.copyFile(srcfile, destination);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return path;

	}
}