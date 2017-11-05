package TestCases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import Config.ConfigReader;
import DataProvider.DataProvider;
import Helper_Utility.GetScreenShot;
import Helper_Utility.HelperClass;
import Pages.Login;
import Pages.Product;
//import SCB.SCM_Automation.TestBaseClass;
import TestBase.DriverFactory;
import TestReporter.TestReporterClass;

public class TestCase4 {

	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	HelperClass helperClass;
	ConfigReader objConfig = new ConfigReader();
	
	DataProvider GetExcelData = new DataProvider();
    HashMap<String, String> excelHashMapValues = new HashMap <String, String>();
	
	@BeforeClass
	public void initialize() throws Exception
	{
	 extent = TestReporterClass.GetExtent();
     Properties pro =	objConfig.LoadConfigProperties();
     
     String url= pro.getProperty("AutomationPracticeURL1");
     DriverFactory dirFactory = new DriverFactory();
     driver =dirFactory.BrowserSetUp("Firefox",url);
	
	}
	
	@Test
	public void testScript1() throws Exception
	{
		String TestCaseID = "TC_003";
		GetExcelData.extractExcelData(TestCaseID, excelHashMapValues);
		helperClass = new HelperClass(driver);
	
	    Login lg = new Login (driver, helperClass);
		lg.login(excelHashMapValues.get("Uname"),excelHashMapValues.get("password"));
		
		Product product = new Product(driver, helperClass);
		product.addtocart(excelHashMapValues.get("qunty"));	
	}
	
	@AfterMethod
	public void Reporter(ITestResult result) throws IOException{
		
		//Result Reporting to Extent Logic
		String TestName = result.getMethod().getConstructorOrMethod().getName();
		test = extent.createTest(TestName,"Verification of the test status "+TestName);
				
		System.out.println("This is my test name:" + TestName);
		if(result.getStatus()==ITestResult.FAILURE)
		{	
			System.out.println("entered after failed");
			//extent.createTest(testName:"test1",description:"Test FAILED");
            String screenShotPath = GetScreenShot.capture(driver, "screenShotName");

			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+"Test Case FAILED due to below issues", ExtentColor.RED));
			//here MarkupHelper.createLabel is ue to get the name of test case and to add the color 
			test.fail(result.getThrowable());
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
		
	}

	@AfterClass
	public void tearDown()
	{
		extent.flush();
		driver.close();
	}
	
}
