package com.cynthia.tests.SupplyItem;

import io.restassured.response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.cynthia.base.TestBase;
import com.cynthia.pageObj.DB2Connect;
import com.cynthia.pageObj.ExcelCompare;
import com.cynthia.pageObj.MISLegacyServices;
import com.cynthia.services.SupplyItemGet;
import com.cynthia.utils.TestUtil;
import com.cynthia.utils.XlsReader;

public class SiGetforAssortmentAndShipperWithStoreNo extends TestBase {
	private static final Logger logger = Logger.getLogger(SiGetByMultipleItemNumbersTest.class);
    public Response response;
    private SupplyItemGet supplyItemGet;
    private MISLegacyServices mis =new MISLegacyServices();
    DB2Connect db2 = new DB2Connect();
    ExcelCompare ec=new ExcelCompare();

    SiGetforAssortmentAndShipperWithStoreNo() {
        logNdReport(logger, "Get supply item details for Assortment/shipper/si test started");
    }

    @Test(dataProvider = "testData")
    public void testGetSiGetforAssortmentAndShipperWithStoreNo(String heading,String itemNbrs,String storereq,String storeNo,String market,String division,String scenario,String expectedResponseCode,String anySplCondition,String Errormsg) throws IOException, SQLException, JSONException, EncryptedDocumentException, InvalidFormatException, InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException {
    	logNdReport(logger,heading);
    	logNdReport(logger, "Testing scenario: " + scenario + " for market: " + market + " and division: " + division);
    	getSiGetforAssortmentAndShipperWithStoreNo( itemNbrs, storereq, storeNo, market, division, expectedResponseCode, anySplCondition);
    	if((scenario.equalsIgnoreCase("positive"))&&(!anySplCondition.equalsIgnoreCase("no atrri val")))
    	{
    		 String outputResponse=response.asString();
    		 JSONArray arr1=new JSONArray(outputResponse);
    		 for(int i=0;i<arr1.length();i++)
    		 {
    			 boolean dataSet=false;
    			 String itemNo=null;
    			 JSONObject jsonObj=arr1.getJSONObject(i);
    			 Object si=jsonObj.get("supplyItem");
    			 Object assort=jsonObj.get("assortment");
    			 Object ship=jsonObj.get("shipper");
    			 if(si instanceof JSONObject)
    			 {
    				 JSONObject siJson=jsonObj.getJSONObject("supplyItem");
    				 itemNo=siJson.getString("number");
    				 Assert.assertTrue(itemNbrs.contains(itemNo),"The retreived item number is not queried in the service");
    				 mis.siAttribute(siJson, "SI");
    				 db2Call(itemNo,"SI",market,division);
    				 ec.excelCompare("SI");
    				 dataSet=true;
    			 }
    			 else if(assort instanceof JSONObject)
    			 {
    				 JSONObject assJson=jsonObj.getJSONObject("assortment");
    				 itemNo=assJson.getString("number");
    				 Assert.assertTrue(itemNbrs.contains(itemNo),"The retreived item number is not queried in the service");
    				 mis.siAttribute(assJson, "Assortment");
    				 db2Call(itemNo,"Assortment",market,division);
    				 ec.excelCompare("Assortment");
    				 dataSet=true; 
    			 }
    			 else if(ship instanceof JSONObject)
    			 {
    				 JSONObject shipJson=jsonObj.getJSONObject("shipper");
    				 itemNo=shipJson.getString("number");
    				 Assert.assertTrue(itemNbrs.contains(itemNo),"The retreived item number is not queried in the service");
    				 mis.siAttribute(shipJson, "Shipper");
    				 db2Call(itemNo,"Shipper",market,division);
    				 ec.excelCompare("Shipper");
    				 dataSet=true;
    			 }
    			 Assert.assertTrue(dataSet,"The response must have atleast any data under any of the three items");
    		 }
    		
    	}
    	else if(scenario.equalsIgnoreCase("Negative"))
    			{
			//Negative scenario validation
			logNdReport(logger, "Negative Scenario: "+heading);
			if(expectedResponseCode.equalsIgnoreCase("404")) {
				negativeRespValidate404(Errormsg);
			}
			else if((expectedResponseCode.equalsIgnoreCase("400"))&&(!anySplCondition.equalsIgnoreCase("No Item Numbers Header"))) {
				negativeRespValidate400(Errormsg);
			}
		
    		
    			}
    	
    }
    
    private void negativeRespValidate400(String Errormsg) {
		try
		{
			String responseMessage = JsonPath.parse(response.getBody().asInputStream()).read("message");
			Assert.assertTrue(responseMessage.contains(Errormsg));
		} catch (Exception e){
			Assert.fail("Invalid error message for status code 400");
		}
		
	}



	private void negativeRespValidate404(String Errormsg) {
		try{
			String responseMessage = JsonPath.parse(response.getBody().asInputStream()).read("message");
			Assert.assertTrue(responseMessage.equalsIgnoreCase(Errormsg));
		} catch (Exception e){
			Assert.fail("Invalid error message for status code 404");
		}
	}
   
	public void db2Call(String itemNumber,String sheetName,String market,String division) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException, SQLException, EncryptedDocumentException, InvalidFormatException, IOException
	{
		String query="SELECT * FROM ITEM WHERE item_nbr='"+itemNumber+"';";
		db2.ItemQueryExcel(query,sheetName,market,division);
	}
	
    public void getSiGetforAssortmentAndShipperWithStoreNo(String itemNbrs,String storereq,String storeNo,String market,String division,String expectedResponseCode,String anySplCondition) throws IOException, SQLException {
    	
    	supplyItemGet = new SupplyItemGet();
       response = supplyItemGet.searchWithMultipleAssortmentswithStoreNo(itemNbrs,storereq,storeNo, division, market, anySplCondition);
       Assert.assertTrue(response.getStatusCode() == Integer.parseInt(expectedResponseCode), "Expected: " + expectedResponseCode + " But found: " + response.getStatusCode() + ". hense failing");
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        XlsReader testDataXls = new XlsReader(prop.getProperty("testDataXls_MISLegacy"));
        return TestUtil.getData(testDataXls, "getSiAssortmentStoreNo");
    }
}
