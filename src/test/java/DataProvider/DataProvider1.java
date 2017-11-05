package DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.poi.util.SystemOutLogger;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;


public class DataProvider1
{
	

	
	public HashMap<String,String> extractExcelData(String testCaseID, HashMap<String, String> excelHashMapValues) throws FilloException, IOException
	{
	
	//	Config.ConfigReader ConfigProp = new Config.ConfigReader();
		//Properties ConfigObj = ConfigProp.LoadConfigProperties();
		Fillo fillo=new Fillo();
		
		//Connection connection=fillo.getConnection(System.getProperty("user.dir")+"\\DataSheet\\TestData.xlsx");
		Connection connection=fillo.getConnection("C:\\Users\\raghull\\Downloads\\workspace_My\\workspace\\SCB\\SCM_Automation\\DataSheet\\TestData.xlsx");
		String strQuery="Select * from CN where TestCaseID='" +testCaseID +"'";
		System.out.println(strQuery);
		
		Recordset recordset=connection.executeQuery(strQuery);
		
		while(recordset.next())
		{
			ArrayList<String> ColCollection = recordset.getFieldNames();
			int Iter;
			int size = ColCollection.size();
			for (Iter=0 ; Iter<= (size-1) ; Iter++)
			{
				String ColName = ColCollection.get(Iter);
				System.out.println(ColName);
				String ColValue = recordset.getField(ColName);
				//System.out.println(ColValue);
				//HashMap<String, String> excelHashMapValues = new HashMap <String, String>();
				excelHashMapValues.put(ColName, ColValue);				
			}
		}
		recordset.close();
		connection.close();
		
		return excelHashMapValues;
	}
	
	
	@Test
	public void test1() throws Exception
	{
		HashMap<String, String> excelHashMapValues = new HashMap <String, String>();
		DataProvider1 dp = new DataProvider1();
	dp.extractExcelData("Tc_02", excelHashMapValues);
	
	}
	
}