package TestCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.seleniumhq.jetty9.io.ClientConnectionFactory.Helper;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.codoid.products.exception.FilloException;

import Config.ConfigReader;
import DataProvider.DataProvider;
import Helper_Utility.GetScreenShot;
import Helper_Utility.HelperClass;
import Pages.Registation;
import TestBase.DriverFactory;
import TestReporter.TestReporterClass;

public class TestCase01 
{
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	HelperClass helperClass;
	
	ConfigReader objConfig = new ConfigReader();
	
	DataProvider GetExcelData = new DataProvider();
    HashMap<String, String> excelHashMapValues = new HashMap <String, String>();
	
    @BeforeSuite
    public void bSuite()
    {
    	extent = TestReporterClass.GetExtent();
   	 
   	 extent.setSystemInfo("Operating System", System.getProperty("os.name"));
   	 extent.setSystemInfo("Java version", System.getProperty("java.runtime.version"));
   	 extent.setSystemInfo("user name", System.getProperty("user.name"));
    }
    
	@BeforeClass
	public void initialize() throws Exception
	{
	  
	 Properties pro =	objConfig.LoadConfigProperties();
     
     String url= pro.getProperty("AutomationPracticeURL");
     DriverFactory dirFactory = new DriverFactory();
     driver =dirFactory.BrowserSetUp("Firefox",url);
	
	}
	
	@Test(priority=1)
	public void Test_case_1() throws FilloException, IOException
	{
		String TestCaseID = "TC_002";
		GetExcelData.extractExcelData(TestCaseID, excelHashMapValues);
		helperClass = new HelperClass(driver);
		
		Registation d = new Registation(driver,helperClass);
		d.doRegistation(excelHashMapValues.get("fname"),excelHashMapValues.get("lname"),excelHashMapValues.get("n_country"),excelHashMapValues.get("month1"),excelHashMapValues.get("date1"),
		excelHashMapValues.get("year1"),excelHashMapValues.get("phn_no"),excelHashMapValues.get("username1"),excelHashMapValues.get("email1"),excelHashMapValues.get("pwd"),excelHashMapValues.get("cfm_pwd"));
        
		WebElement text_messge =driver.findElement(By.xpath("//*[@id='post-49']/div/p"));
		Assert.assertEquals(text_messge.getText(),"Thank you for your registration");
	
	}
	
	@Test
	public void testCase_2()
	{
		Assert.assertTrue(false);
	}
	
	
	@Test
	public void testCase_3()
	{
		Assert.assertTrue(true);
	}
	
	@Test(enabled =true)
	public void testCase_4()
	{
		Assert.assertTrue(true);
	}
	
	@AfterMethod
	public void Reporter(ITestResult result) throws IOException{
		
		//Result Reporting to Extent Logic
		String TestName = result.getMethod().getConstructorOrMethod().getName();
		
		//****** Line updated 
		test = extent.createTest(TestName).assignCategory("Release-1").assignAuthor(System.getProperty("user.name"));
		
		System.out.println("This is my test name:" + TestName);
		if(result.getStatus()==ITestResult.FAILURE)
		{	
			System.out.println("entered after failed");
			//extent.createTest(testName:"test1",description:"Test FAILED");
            String screenShotPath = GetScreenShot.capture(driver, "screenShotName");

            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED due to below issues", ExtentColor.RED));
			//here MarkupHelper.createLabel is ue to get the name of test case and to add the color 
            
            Throwable t =result.getThrowable();
           
          test.log(null, t);
           
            test.fail("Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));

		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			System.out.println("entered after success");
			//extent.createTest("test2","Test PASSED");
			 String screenShotPath = GetScreenShot.capture(driver, "screenShotName");
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"Test Case PASSED", ExtentColor.GREEN));
			test.pass("Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
		}
		else
		{
			System.out.println("entered after skipp");
			//extent.createTest("test3","Test SKIPPED");
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+"Test Case SKIPPED", ExtentColor.YELLOW));
			test.skip(result.getThrowable());
		}
		//*********************************************************
			//driver.close();
	}

	@AfterClass
	public void tearDown() 
	{
		extent.flush();

		driver.close();
		
	}
}
