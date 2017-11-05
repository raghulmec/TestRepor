package TestBase;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import Config.ConfigReader;

public class DriverFactory {
	public  WebDriver driver;
	
	
	public  WebDriver BrowserSetUp(String browserName,String url ) throws IOException{
		
		Config.ConfigReader ConfigProp = new Config.ConfigReader();
		Properties ConfigObj = ConfigProp.LoadConfigProperties();
		
		if (ConfigObj.getProperty("Browser").equals(browserName))
		{
			System.setProperty("webdriver.gecko.driver","C:\\Users\\swatiw\\Desktop\\geckodriver-v0.16.1-win32\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		else if (ConfigObj.getProperty("Browser").equals(browserName))
		{
			System.setProperty("webdriver.chrome.driver","path");
			driver = new ChromeDriver();
		}
		
		else if (ConfigObj.getProperty("Browser").equals(browserName))
		{
			System.setProperty("webdriver.ie.driver","path");
			driver = new ChromeDriver();
		}
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		return driver;
	}
	
	public void TestDataFileConfig(){
		
	}
	
	public void ResultFolderConfig(){
		
	}
	
	public void ScreenShotPathConfig(){
		
	}
	
	public void CleanUp(){
		
	}
	
	public static void TearDown(){
		
	}

}
