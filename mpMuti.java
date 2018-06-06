package com.cynthia.tests.MasterProduct;

import io.restassured.response.Response;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.codehaus.jettison.json.JSONException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.cynthia.base.TestBase;
import com.cynthia.pageObj.CassandraConnect;
import com.cynthia.pageObj.DB2Connect;
import com.cynthia.pageObj.EMIServices;
import com.cynthia.pageObj.ExcelCompare;
import com.cynthia.utils.TestUtil;
import com.cynthia.utils.XlsReader;

/**
 * 
 * @author cylee1
 * @since 20 Nov 2017
 * 
 * Test Rail id:
 * Story:RCTSITI-
 * 
 * Validation includes:-
 *1.Mater Product GET Service by Multiple Item number present in unity and not present in unity
 *2.We can validate only SI Attributes. For other concepts(Product, offer & TI) no need to validate, as per discussion with PO
 *3.SI has db2 fall back
 *4.To validate that below attributes are present 
 * number, supplierAgreement, originCountry,deptNumber, accountingDeptNumber, consumableGTIN, merchandiseCategoryNumber, merchandiseSubcategoryNumber, finelineNumber, supplierStockId, sellTotalContentQuantity, baseRetail, unitCost, warehousePackQuantity, warehousePackCost, warehousePackSell
 *warehousePackQuantity - default UOM to EA
 *warehousePackCost - currency response is different from DB2
 *warehousePackSell - currency response is different from DB2
 *Validate for commodity type in TI
 *description - populate languageCode
 */
public class MasterProductGetByMultipleItemNumberCanonicalTest extends TestBase {
	
		private static final Logger log = Logger.getLogger(MasterProductGetByMultipleItemNumberCanonicalTest.class);
		Response response,response1;
		EMIServices emi = new EMIServices();
		String url;
		ExcelCompare ec=new ExcelCompare();
		CassandraConnect cass = new CassandraConnect();
		DB2Connect db2 = new DB2Connect();
		
		@Test(enabled =true,priority=0,dataProvider="getTestData")
		public void mpGetbyMultipleItemNumberValidation(String heading,String ItemNumbers,String method,String scenario,String expectedResp) throws InvalidKeyException, IOException, InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, JSONException, EncryptedDocumentException, InvalidFormatException{
			logNdReport(log, heading);
			mpGetbyMultipleItemNumber(ItemNumbers,method,expectedResp);
			List<String> itemNo1=Arrays.asList(ItemNumbers.split("\\s*,\\s*"));
			
			if((scenario.equalsIgnoreCase("positive"))&&(itemNo1.size()<3))
			{
				int arraySize=emi.arraySize(response);
				for(int arraylength=0; arraylength<arraySize;arraylength++)
				{
				
				 Object[] jsonRes=emi.multiResponseSplit(response,arraylength);
				 String GTIN = emi.retreiveMultiConsumableGtin(response,arraylength);
				 if(scenario.equalsIgnoreCase("positive")&&jsonRes[0]!=null)
					{
						//product
					emi.productGetJson(response,"ProdIqsJsn","mpMulti",arraylength);
					iQSserviceProductCall(GTIN);
					emi.productGetIQS(response1,"ProdIqsJsn");
					ec.iqsJsonCompare("ProdIqsJsn");
					
					}
					if(scenario.equalsIgnoreCase("positive")&&jsonRes[1]!=null)
					{
					 // offer
					emi.offerGetbyOfferIDJson(response,"Offer","mpMulti",arraylength);
					iQSserviceOfferCall(GTIN);
					
					emi.iqsOfferRes(response1,"Offer");
					ec.iqsJsonCompare("Offer");
					}
					if(scenario.equalsIgnoreCase("positive")&&jsonRes[2]!=null)
					{
						int tradeItems=(int) jsonRes[5];
						if(tradeItems<3)
						{	
//						//tradeitem
						for(int i=0;i<tradeItems;i++)
						{
						String[] contextData=emi.retreiveContext(response, i,"mpMulti",arraylength);
						String context="informationProviderId:"+contextData[0]+",informationProviderTypeCode:"+contextData[1]+",languageCode:en,recipientGln:"+contextData[2]+",targetMarketCode:"+contextData[3];
						System.out.println("Context>>>>>"+context);
						emi.responseValidationTIGetByGtin(response, "TIGtinCassJson","mpMulti",i,arraylength);
						cass.TICassNode_instExcel(GTIN,context,"TIGtinCassJson");
						ec.excelCompareCass("TIGtinCassJson");
						}
						}
					}
					if(scenario.equalsIgnoreCase("positive")&&jsonRes[3]!=null)
					{
						int itemNo=(int) jsonRes[4];
						//supplyitem will validate if no.of si is less than 4
						if(itemNo<3)
						{
						List<String> itemNoRes=new ArrayList<String>();
						Object[] resp;
						resp=emi.responseValidationSIGetByMultipleItemNumber(response,"SIItemCassJson","mpMulti",arraylength);
						resp=emi.responseValidationSIGetByMultipleItemNumber(response,"SIItemDb2Json","mpMulti",arraylength);
						itemNoRes=(List<String>) resp[0];
						for(int i=0;i<itemNoRes.size();i++)
						{
						boolean cassandraAvailability=false;
						cassandraAvailability=cass.dataAvailabilityCheck(itemNoRes.get(i));
						if(cassandraAvailability==true)
						{
						cass.SICassExcel(itemNoRes.get(i),"SIItemCassJson");	
						ec.excelCompareCassMultiValue("SIItemCassJson",i);
						}
						else{
						
						db2Call(itemNoRes.get(i));
						ec.excelComparedb2Multivalue("SIItemDb2Json",i);}
						}
						}
					
					}
			
			
				
				}
			}	
			if(scenario.equalsIgnoreCase("negative"))
			{
				//Negative scenario validation
				logNdReport(log, "Negative Scenario: "+heading);
				if(expectedResp.equalsIgnoreCase("404")) {
					negativeRespValidate404();
				}
				else if(expectedResp.equalsIgnoreCase("400")) {
					negativeRespValidate400(heading);
				}
			}
		}
	
		private void negativeRespValidate400(String negHeading) {
			try
			{
				String responseMessage = JsonPath.parse(response.getBody().asInputStream()).read("message");
				if (negHeading.equalsIgnoreCase("ItemNo=Nul"))
				Assert.assertTrue(responseMessage.equalsIgnoreCase("No Item Numbers found"));
				else if(negHeading.equalsIgnoreCase("Exceeding length"))
					Assert.assertTrue(responseMessage.equalsIgnoreCase("Number of supply-items exceeds the limit"));	
					
			} catch (Exception e){
				Assert.fail("Invalid error message for status code 400");
			}
			
		}
		private void negativeRespValidate404() {
			try{
				String responseMessage = JsonPath.parse(response.getBody().asInputStream()).read("message");
				Assert.assertTrue(responseMessage.equalsIgnoreCase("ENTITY NOT FOUND"));
			} catch (Exception e){
				Assert.fail("Invalid error message for status code 404");
			}
		}
		
		public void db2Call(String itemNumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException, SQLException, EncryptedDocumentException, InvalidFormatException, IOException
		{
			String query="SELECT * FROM ITEM WHERE item_nbr='"+itemNumber+"';";
			db2.ItemQueryExcel(query,"SIItemDb2Json");
		}
		private void iQSserviceOfferCall(String gTIN) throws InterruptedException {
			url="/item-setup-query-service-app/services/offers/v1/key/GTIN/"+gTIN;
			response1=emi.getIQSResponse(url,"GET");
//			Assert.assertTrue(response1.getStatusCode() ==200, "Expected: 200 But found: " + response.getStatusCode());
		}
		private void iQSserviceProductCall(String gTIN) throws InterruptedException {
			url="/item-setup-query-service-app/services/products/v1/key/GTIN/"+gTIN;
			response1=emi.getIQSResponse(url,"GET");
//			Assert.assertTrue(response1.getStatusCode() ==200, "Expected: 200 But found: " + response.getStatusCode());
		}
		public void mpGetbyMultipleItemNumber(String ItemNumbers,String method,String expectedResp) throws InvalidKeyException, IOException, InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, JSONException{
			url="/emi-services/rest/master-product/searchByItemNbrs?targetMarket=us&div=wm";
			response = emi.getValueOnHeader(url,prop.getProperty("Accept_MPGet"),method,"ItemNumbers",ItemNumbers);
			Assert.assertTrue(response.getStatusCode() == Integer.parseInt(expectedResp), "Expected: " + expectedResp + " But found: " + response.getStatusCode());
		}
		
		
		
		@DataProvider
		public Object[][] getTestData()
		{
			XlsReader testDataXls = new XlsReader(prop.getProperty("testDataXls_EMIServices"));
			return TestUtil.getData(testDataXls, "MPGetMultipleItemNo-Canonical");
//			return TestUtil.getData(testDataXls, "Sheet1");
		}
	

}
