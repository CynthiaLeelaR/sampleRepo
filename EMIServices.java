package com.cynthia.pageObj;


import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;



import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tools.ant.filters.LineContains.Contains;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONString;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import com.cynthia.base.AuthorizationKey;
import com.cynthia.base.MainBase;

/**
 * 
 * @author cylee1
 *
 */

public class EMIServices extends MainBase {

	public static final String pathmappingSheet="src//main//resources//Mapping_Sheet1.xlsx";
	private static final Logger log = Logger.getLogger(EMIServices.class);
	 Response response;
	    String url = null;
	    String authkey;
	    AuthorizationKey auth=new AuthorizationKey();

	    public Response getResponsetrail(String url,String accept,String method) throws IOException, InvalidKeyException, InterruptedException
	    {
		authkey=AuthorizationKey.authKey(url,method);
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(setGETHeaders(accept, authkey))
   	           .when().get(url).body().jsonPath().get("productDefinition");
   	           
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	return response;
	    }
	    
	 public Response getResponse(String url,String accept,String method) throws IOException, InvalidKeyException, InterruptedException
	    {
		authkey=AuthorizationKey.authKey(url,method);
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(setGETHeaders(accept, authkey))
   	           .when().get(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	return response;
	    }
	 
	 public Response postResponse(String url,String accept,String content,String method,String replaceGtinPayload) throws IOException, InvalidKeyException, InterruptedException
	    {
		authkey=AuthorizationKey.authKey(url,method);
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
             .spec(setGETHeaders(accept,content,authkey))
             .body(replaceGtinPayload)
	           .when().post(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	return response;
	    }
	 
	 public Response putResponse(String url,String accept,String content,String method,String replaceGtinPayload) throws IOException, InvalidKeyException, InterruptedException
	    {
		authkey=AuthorizationKey.authKey(url,method);
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
          .spec(setGETHeaders(accept,content,authkey))
          .body(replaceGtinPayload)
	           .when().put(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	return response;
	    }
	 
	 public Response deleteResponse(String url,String accept,String content,String method,String replaceGtinPayload) throws IOException, InvalidKeyException, InterruptedException
	    {
		authkey=AuthorizationKey.authKey(url,method);
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
          .spec(setGETHeaders(accept,content,authkey))
          .body(replaceGtinPayload)
	           .when().delete(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	return response;
	    }
	 
	 public Response getValueOnHeader(String url,String accept,String method,String header,String headervalue) throws IOException, InvalidKeyException, InterruptedException
	    {

		authkey=AuthorizationKey.authKey(url,method);
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
             .spec(setGETHeaderswithvalue(accept,authkey,header,headervalue))
              .when().get(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	return response;
	    }
	 
	 public Response congoCall(String url,String method) throws IOException, InvalidKeyException, InterruptedException
	    {
		 baseUri = "https://api.qa.wal-mart.com";
         RestAssured.baseURI = baseUri;
//		authkey=AuthorizationKey.authKey(url,method);
		Response responseCongo = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
             .spec(congoHeaderSet()).when().get(url);
	 	Thread.sleep(6000);
	 	logNdReport(log,"Overall service response passed.");
	 	baseUri =null;
	 	return responseCongo;
	    }
	 
	 public Response getSolrResponse(String url) throws IOException, InvalidKeyException, InterruptedException
	    {
		 baseUri = prop.getProperty("solrUrl");
         RestAssured.baseURI = baseUri;
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                 .when().get(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	baseUri =null;
	 	return response;
	    }
	
	 public Response getIQSResponse(String url, String string) throws InterruptedException {
		 baseUri = prop.getProperty("iqsurl");
         RestAssured.baseURI = baseUri;
		response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
				.spec(iqsHeaderSet())
                 .when().get(url);
	 	Thread.sleep(4000);
	 	logNdReport(log,"Overall service response passed.");
	 	 baseUri =null;
	 	return response;
		}
		
	 
	 public void excelClearmulti(String sheetName) throws IOException, EncryptedDocumentException, InvalidFormatException
	 {
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	//Do not delete or change dthe headers
	            	
	            }
	            else{

	            Cell updateCell1=row.getCell(3,Row.CREATE_NULL_AS_BLANK);
	            updateCell1.setCellValue("");
	            Cell updateCell2=row.getCell(1,Row.CREATE_NULL_AS_BLANK);
	            updateCell2.setCellValue("");
	            Cell updateCell3=row.getCell(4,Row.CREATE_NULL_AS_BLANK);
	            updateCell3.setCellValue("");
	            Cell updateCell4=row.getCell(5,Row.CREATE_NULL_AS_BLANK);
	            updateCell4.setCellValue("");
	            }
	            
		 }
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
         workbook.write(outputStream);
		 workbook.close();
		 outputStream.close();
	 }

	 public String[] responseValidationGtinBU(Response response, String gtin,String sheetName) throws JSONException, EncryptedDocumentException, InvalidFormatException, IOException
	 {
		 excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject gtinObj=obj1.getJSONObject(gtin);
		 String congoDetails=gtinObj.getString("congo");
		 String itemnumber=gtinObj.getJSONObject("searchResults").getJSONArray("results").getJSONObject(0).getString("id");
		 JSONObject additionalDetails=gtinObj.getJSONObject("searchResults").getJSONArray("results").getJSONObject(0).getJSONObject("additionalDetail");
		 
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	            if(additionalDetails.has(AttributeNameExcel))
	            {
//	            	System.out.println(AttributeNameExcel);
	            	String retreivevalue=additionalDetails.getString(AttributeNameExcel);
//	            	System.out.println(retreivevalue);
	            	Cell updateCell=row.getCell(3);
	            	 if(retreivevalue==null)
	 	            {
	 	            	// when the value to be enter is null
	 	            	updateCell.setCellValue("null");
	 	            }
	 	            else{
	 	            updateCell.setCellValue(retreivevalue);}

	            }
	            else{
	            	try{
	            		if((AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty"))||(AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode"))){
	            		String retreivevalue=additionalDetails.getJSONArray("sellTotalContent").getJSONObject(0).getString(AttributeNameExcel);
	            		Cell updateCell=row.getCell(3);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
	            		}
	            		else{System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(3);
	                	updateCell.setCellValue("NA");}

	            	}
	            	
	            	catch (Exception e){
	            	System.out.println(e);
	            	System.out.println(AttributeNameExcel+" is missing in json response");
	            	Cell updateCell=row.getCell(3);
	            	updateCell.setCellValue("NA"); 
	            	}
	            	
	            	
	            	}

	            
	            }
	            
		 }

		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
         workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();
		 
	 
		 return new String[]{itemnumber,congoDetails};
	 }

	public boolean congoResponseValidation(Response responsecongo) throws JSONException 
	{
		boolean flag=false;
		 String outputResponse=responsecongo.asString();
		 
		 try{
			 
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject priceDetailArr=obj1.getJSONObject("PriceDetails").getJSONObject("PriceDetail");

			 for(Iterator key=priceDetailArr.keys();key.hasNext();)
	         {
				 String attributename=(String) key.next(); 
				 String attributevalue=priceDetailArr.getString(attributename);
				 String SiAttributesValues=(attributename+":"+attributevalue);
				 System.out.println(SiAttributesValues);
	         }
			 flag = true;
		 }
		 catch(Exception e)
		 {
			flag= false; 
		 }
		 return flag;

	}
	public void enrichValidationFalse(Response response) throws JSONException
	{
		String outputResponse=response.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONArray resultArr=obj1.getJSONArray("results");
		 for(int i=0;i<resultArr.length();i++){
			 JSONObject rsultChild=resultArr.getJSONObject(i);
		   JSONObject additionalDetails=rsultChild.getJSONObject("additionalDetail");
		   int attributePresent=additionalDetails.length();
		 Assert.assertTrue(attributePresent==0,"Even though the enrich flag is set as null, we are getting detailed response of si");
		 }
		
	}
	public String[] responseValidationSIGetConsumableGTIN(Response response,String sheetName) throws JSONException, EncryptedDocumentException, InvalidFormatException, IOException
	 {
		List<String> itemnumber=new ArrayList<String>();
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		 
		 String outputResponse=response.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONArray resultArr=obj1.getJSONArray("results");
		 for(int i=0;i<resultArr.length();i++){
			 if(i==0){updateCellColumnNo=3;} else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
			 JSONObject rsultChild=resultArr.getJSONObject(i);
			 itemnumber.add(rsultChild.getString("id"));
		   JSONObject additionalDetails=rsultChild.getJSONObject("additionalDetail");
		   FileOutputStream outputStream=null;
			 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
			 Sheet sheet=workbook.getSheet(sheetName);
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
		 
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	            if(additionalDetails.has(AttributeNameExcel))
	            {
//	            	System.out.println(AttributeNameExcel);
	            	String retreivevalue=additionalDetails.getString(AttributeNameExcel);
//	            	System.out.println(retreivevalue);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {
	 	            	// when the value to be enter is null
	 	            	updateCell.setCellValue("null");
	 	            }
	 	            else{
	 	            updateCell.setCellValue(retreivevalue);}

	            }
	            else{
	            	try{
	            		if((AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty"))||(AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode"))){
	            		String retreivevalue=additionalDetails.getJSONArray("sellTotalContent").getJSONObject(0).getString(AttributeNameExcel);
	            		Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
	            		}
	            		else{System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");}

	            	}
	            	
	            	catch (Exception e){
	            	System.out.println(e);
	            	System.out.println(AttributeNameExcel+" is missing in json response");
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	updateCell.setCellValue("NA"); 
	            	}
	            	
	            	
	            	}

	            
	            }
	            
		 }
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	        workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();
		 
		}

		
		 String[] ItemNumbers=itemnumber.toArray(new String[itemnumber.size()]);
		 return ItemNumbers;
	 }
	
 public void retreiveResponseAttri(JSONObject jsonresponse) throws JSONException{
//		 String outputResponse=response.asString();
		 JSONObject obj1=jsonresponse;
//		 JSONObject jsonObj2=obj1.getJSONObject("payload");
//		 JSONObject jsonObj2=obj1.getJSONObject("tradeItemHierarchy").getJSONObject("tradeItem");
		 for(Iterator key=obj1.keys();key.hasNext();)
         {
			 String attributename=(String) key.next(); 
//			 String attributevalue=jsonObj2.getString(attributename);
//			 String SiAttributesValues=(attributename+":"+attributevalue);
			 System.out.println(attributename);
			 
			 
			 
         }
	}
	
	public String responseValidationSIGetItemNumber(Response response,String sheetName) throws JSONException, EncryptedDocumentException, InvalidFormatException, IOException
	 {

		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject jsonObj2=obj1.getJSONObject("supplyItem");
		 String itemnumber=obj1.getJSONObject("supplyItem").getString("number");

		 FileOutputStream outputStream=null;
			 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
			 Sheet sheet=workbook.getSheet(sheetName);
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
		 
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	           
	            if(AttributeNameExcel.equalsIgnoreCase("destinationFormat")||AttributeNameExcel.equalsIgnoreCase("factory")||
	            		AttributeNameExcel.equalsIgnoreCase("originCountry")||AttributeNameExcel.equalsIgnoreCase("eligibilityStates"))
	            {
	            	if(jsonObj2.has(AttributeNameExcel))
	            	{
	            	Object desFor=jsonObj2.get(AttributeNameExcel);
	            	 if (desFor instanceof JSONArray){
		            	List<String> destinationFormatCode=new ArrayList<String>();
		            	JSONArray formatArray=jsonObj2.getJSONArray(AttributeNameExcel);
		            	for(int i=0;i<formatArray.length();i++)
		            	{
		            		try
		            		{
		            		destinationFormatCode.add(formatArray.getJSONObject(i).getString("code"));
		            		}
		            		catch (Exception e){
		            			destinationFormatCode.add(formatArray.getJSONObject(i).getString("id"));
		            		}
		            	}
		            	
		            	String retreivevalue=String.valueOf(destinationFormatCode);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null"); }
		 	            else{updateCell.setCellValue(retreivevalue);}
		            	}
	            	 else 
	            		{
	    	            	String retreivevalue=jsonObj2.getString(AttributeNameExcel);
	    	            	Cell updateCell=row.getCell(updateCellColumnNo);
	    	            	 if(retreivevalue==null)
	    	 	            {
	    	 	            	// when the value to be enter is null
	    	 	            	updateCell.setCellValue("null");
	    	 	            }
	    	 	            else{ updateCell.setCellValue(retreivevalue);}
	            		}
	            }
	            else{
    				System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
        			
	            		
	            	}
	            	
	            }
	            else if(jsonObj2.has(AttributeNameExcel))
	            {
//	            	System.out.println(AttributeNameExcel);
	            	String retreivevalue=jsonObj2.getString(AttributeNameExcel);
//	            	System.out.println(retreivevalue);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {
	 	            	// when the value to be enter is null
	 	            	updateCell.setCellValue("null");
	 	            }
	 	            else{ updateCell.setCellValue(retreivevalue);}

	            }
	            else if(AttributeNameExcel.contains("."))
	            {
	            	try{

            			String retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem."+AttributeNameExcel));
            			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {
		 	            	// when the value to be enter is null
		 	            	updateCell.setCellValue("null");
		 	            }
		 	            else{ updateCell.setCellValue(retreivevalue);}
            		}
	            	catch(Exception e){
	            		System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
	            	}
	            	
	            }
	            else{
	            	if(jsonObj2.has("supplyItemAttributes"))
            	            {
            	            	Object tradeItemAttributes = jsonObj2.get("supplyItemAttributes");
            	            	if(tradeItemAttributes instanceof JSONArray)
            	            	{
            	            		JSONArray array1=(JSONArray) tradeItemAttributes;
            	            		List<String> specialAtrri=new ArrayList<String>();
            	            		for(int i=0;i<array1.length();i++)
            	            		{
            	            			String attriname=array1.getJSONObject(i).getString("name");
            	            			specialAtrri.add(attriname);
            	            			if(specialAtrri.contains(AttributeNameExcel)){
            	            			if(attriname.equalsIgnoreCase(AttributeNameExcel))
            	            			{
            	            				String updateSpecialRetreive =array1.getJSONObject(i).getJSONArray("attributeValues").getJSONObject(0).getJSONArray("value").getJSONObject(0).getString("textValue");
            	            				Cell updateCell=row.getCell(updateCellColumnNo);
            	            				if(updateSpecialRetreive==null)
            	    		 	            {updateCell.setCellValue("null"); }
            	    		 	            else{updateCell.setCellValue(updateSpecialRetreive);}
            	            			}
            	            			}
            	            			else
            	            			{
            	            				if(AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty")||AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode")
            	            						||AttributeNameExcel.contains("supplierLeadTime"))
            	            				{
            	            					try{
            	            						String retreivevalue=null;
            	            						if(AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty"))
            	                        			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem.sellTotalContentQuantity.amount"));
            	            						else if(AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode"))
            	            							retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem.sellTotalContentQuantity.uom"));
            	            						else if(AttributeNameExcel.equalsIgnoreCase("supplierLeadTimeUomCode"))
            	            							retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem.supplierLeadTime.uom"));
            	            						else if (AttributeNameExcel.equalsIgnoreCase("supplierLeadTimeQty"))
            	            							retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem.supplierLeadTime.amount"));
            	            						
            	            						Cell updateCell=row.getCell(updateCellColumnNo);
            	            		            	 if(retreivevalue==null)
            	            		 	            {
            	            		 	            	// when the value to be enter is null
            	            		 	            	updateCell.setCellValue("null");
            	            		 	            }
            	            		 	            else{ updateCell.setCellValue(retreivevalue);}
            	                        		
            	            						
            	            					}
            	            					catch(Exception e){
            	            						System.out.println(AttributeNameExcel+" is missing in json response");
                    	    	                	Cell updateCell=row.getCell(updateCellColumnNo);
                    	    	                	updateCell.setCellValue("NA");
            	            					}
            	            				}
            	            				else{
            	            				System.out.println(AttributeNameExcel+" is missing in json response");
            	    	                	Cell updateCell=row.getCell(updateCellColumnNo);
            	    	                	updateCell.setCellValue("NA");
            	            				}
            	            			}
            	            			
            	            		}
            	            		
            	            	}
            	            	else
            	            	{
            	            		String retreivevalue=jsonObj2.getString("supplyItemAttributes");
            		            	Cell updateCell=row.getCell(updateCellColumnNo);
            		            	 if(retreivevalue==null)
            		 	            {updateCell.setCellValue("null"); }
            		 	            else{updateCell.setCellValue(retreivevalue);}
            	            	}
            	            	
            	            }	
            	            else{
            	            	System.out.println(AttributeNameExcel+" is missing in json response");
            	                	Cell updateCell=row.getCell(updateCellColumnNo);
            	                	updateCell.setCellValue("NA");
            	            }
            	        }
	           	}
		 }
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	        workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();
			 return itemnumber;
	 }

	public void responseValidationTIGetByGtin(Response response,String sheetName,String call,int tradeItem,int arraylen) throws JSONException, EncryptedDocumentException, InvalidFormatException, IOException
	 {

		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		
		 JSONObject jsonObj2=null;
		 if(call.equalsIgnoreCase("mp"))
		 {
		 JSONObject obj1=new JSONObject(outputResponse);
		 jsonObj2=obj1.getJSONArray("tradeItemsSupplyItems").getJSONObject(0).getJSONArray("consumableTradeItems").getJSONObject(tradeItem); 
		 }
		 else if(call.equalsIgnoreCase("mpMulti"))
		 {
			 JSONObject obj12=new JSONArray(outputResponse).getJSONObject(arraylen);
			 jsonObj2=obj12.getJSONArray("tradeItemsSupplyItems").getJSONObject(0).getJSONArray("consumableTradeItems").getJSONObject(tradeItem); 
				 
		 }
		 else{
		JSONObject obj1=new JSONObject(outputResponse);
		 jsonObj2=obj1.getJSONObject("tradeItemHierarchy").getJSONObject("tradeItem");
		 }
//		 String gtin=jsonObj2.getString("gtin");

		 FileOutputStream outputStream=null;
			 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
			 Sheet sheet=workbook.getSheet(sheetName);
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
		 
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	            if(AttributeNameExcel.equalsIgnoreCase("lastUpdateTimestamp")||AttributeNameExcel.equals("GTIN")||AttributeNameExcel.equalsIgnoreCase("informationProviderGln")||AttributeNameExcel.equalsIgnoreCase("ownerGln")||AttributeNameExcel.equalsIgnoreCase("tradeItemChildQty"))
	            {
	            if(jsonObj2.has("tradeItemAttributes"))
	            {
	            	Object tradeItemAttributes = jsonObj2.get("tradeItemAttributes");
	            	if(tradeItemAttributes instanceof JSONArray)
	            	{
	            		JSONArray array1=(JSONArray) tradeItemAttributes;
	            		List<String> specialAtrri=new ArrayList<String>();
	            		for(int i=0;i<array1.length();i++)
	            		{
	            			String attriname=array1.getJSONObject(i).getString("name");
	            			specialAtrri.add(attriname);
	            			if(specialAtrri.contains(AttributeNameExcel)){
	            			if(attriname.equalsIgnoreCase(AttributeNameExcel))
	            			{
	            				String updateSpecialRetreive =array1.getJSONObject(i).getJSONArray("attributeValues").getJSONObject(0).getJSONArray("value").getJSONObject(0).getString("textValue");
	            				Cell updateCell=row.getCell(updateCellColumnNo);
	            				if(updateSpecialRetreive==null)
	    		 	            {updateCell.setCellValue("null"); }
	    		 	            else{updateCell.setCellValue(updateSpecialRetreive);}
	            			}
	            			}
	            			else
	            			{
	            				System.out.println(AttributeNameExcel+" is missing in json response");
	    	                	Cell updateCell=row.getCell(updateCellColumnNo);
	    	                	updateCell.setCellValue("NA");
	            			}
	            			
	            		}
	            		
	            	}
	            	else
	            	{
	            		String retreivevalue=jsonObj2.getString("tradeItemAttributes");
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null"); }
		 	            else{updateCell.setCellValue(retreivevalue);}
	            	}
	            	
	            }	
	            else{
	            	System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
	            }
	            }
	            else if(jsonObj2.has(AttributeNameExcel))
	            {
//	            	System.out.println(AttributeNameExcel);
	            	String retreivevalue=jsonObj2.getString(AttributeNameExcel);
//	            	System.out.println(retreivevalue);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {
	 	            	// when the value to be enter is null
	 	            	updateCell.setCellValue("null");
	 	            }
	 	            else{
	 	            updateCell.setCellValue(retreivevalue);}

	            }
	            else{
	            			try
	            			{
	            				String retreivevalue=null;
	            				if(call.equalsIgnoreCase("mp"))
	            				{
	            				 retreivevalue=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems["+tradeItem+"]."+AttributeNameExcel));	
	            				}
	            				else if(call.equalsIgnoreCase("mpMulti"))
	            				{
	            				 retreivevalue=String.valueOf(response.getBody().jsonPath().get("["+arraylen+"].tradeItemsSupplyItems[0].consumableTradeItems["+tradeItem+"]."+AttributeNameExcel));	
	            				}
		            			else{	
		            			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("tradeItemHierarchy.tradeItem."+AttributeNameExcel));
		            			}
	            			Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {
			 	            	// when the value to be enter is null
			 	            	updateCell.setCellValue("null");
			 	            }
			 	            else{ updateCell.setCellValue(retreivevalue);}
	            			}
	            			catch(Exception e){
	            			System.out.println(AttributeNameExcel+" is missing in json response");
   		                	Cell updateCell=row.getCell(updateCellColumnNo);
   		                	updateCell.setCellValue("NA");
	            			}
	            	}
	           	}
		 }
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	        workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();
	 }

	public void responseContextVerification(Response response,String infoProId,String infoTypeCode, String dataRepGLN, 
			String targetmarketcode) throws JSONException {
		SoftAssert s_assert = new SoftAssert();
		 String infoId=String.valueOf(response.getBody().jsonPath().get("tradeItemHierarchy.tradeItem.informationProviderId.value"));
		 s_assert.assertEquals(infoId,infoProId,"InformationProvider id is getting different from the service url=> "+infoProId+" and retreived response=> "+infoId);
		 String infoIdType=String.valueOf(response.getBody().jsonPath().get("tradeItemHierarchy.tradeItem.informationProviderId.type"));
		 s_assert.assertEquals(infoIdType,infoTypeCode,"InformationProviderType Code is getting different from the service url=> "+infoIdType+" and retreived response=> "+infoTypeCode);
		 String recpGln=String.valueOf(response.getBody().jsonPath().get("tradeItemHierarchy.tradeItem.recipientGLN"));
		 s_assert.assertEquals(recpGln,dataRepGLN,"Receipient GLN is getting different from the service url=> "+recpGln+" and retreived response=> "+dataRepGLN);
		 String targetMark=String.valueOf(response.getBody().jsonPath().get("tradeItemHierarchy.tradeItem.targetMarketCode"));
		 s_assert.assertEquals(targetMark,targetmarketcode,"Target market Code getting different from the service url=> "+targetMark+" and retreived response=> "+targetmarketcode);
		 s_assert.assertAll(); 
	}

	public List<String> parentRetreive(Response response2) throws JSONException {
		List<String> parentItem=new ArrayList<String>();
		String outputResponse=response2.asString();
		 JSONObject jsonobj1=new JSONObject(outputResponse);
		 JSONObject jsonObj2=jsonobj1.getJSONObject("tradeItemHierarchy").getJSONObject("tradeItem");
		 if(jsonObj2.has("parentTradeItemGtin")){
		 Object obj1=jsonObj2.get("parentTradeItemGtin");
		 if(obj1 instanceof JSONArray)
		 {JSONArray jarr=jsonObj2.getJSONArray("parentTradeItemGtin");
			for(int i=0;i<jarr.length();i++){
				parentItem.add(jarr.getString(i));
			} 
		 }
		 else{parentItem.add(String.valueOf(obj1));}	 
		 }
//		 System.out.println(">>>>>>>>>>"+parentItem);
		 return parentItem;
	}

	public String productGetJson(Response response,String sheetName,String call,int arrayIndex) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException 
	{
		int updateCellColumnNo=3;
		String gtinResp=null;
		excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		 JSONObject obj1=null;
		 if(call.equalsIgnoreCase("mp"))
		 {
			 obj1=new JSONObject(outputResponse).getJSONObject("productDefinition");
		 }
		 else if(call.equalsIgnoreCase("mpMulti"))
		 {
			 
			  obj1=new JSONArray(outputResponse).getJSONObject(arrayIndex).getJSONObject("productDefinition");
		 }
		 else
		 {
		 obj1=new JSONObject(outputResponse);
		 }
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 {
            Row row = rowIterator.next();
            if(row.getRowNum()==0)
            {
            	Cell cell=row.getCell(2);
	            String cellValue = dataFormatter.formatCellValue(cell);
            	System.out.println("The header is "+cellValue);
            	
            }
            else{
            	Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	             if(obj1.has(AttributeNameExcel))
	            {
	            	String retreivevalue=obj1.getString(AttributeNameExcel);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null");}
	 	            else{updateCell.setCellValue(retreivevalue);}
	            	 if(AttributeNameExcel.equalsIgnoreCase("consumableGTINs"))
		             gtinResp= retreivevalue;
		             
	            }
	             else if(AttributeNameExcel.contains("."))
	             {
	            	 String retreivevalue=null;
	            	 try{
	            		 if(call.equalsIgnoreCase("mp"))
	            		 {
	            		retreivevalue=String.valueOf(response.getBody().jsonPath().get("productDefinition."+AttributeNameExcel));
	            		 }
	            		 else if(call.equalsIgnoreCase("mpMulti"))
	            		 {
	            			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("["+arrayIndex+"].productDefinition."+AttributeNameExcel));
	 	            	 }
	            		 else
	            		 {	 
            		    retreivevalue=String.valueOf(response.getBody().jsonPath().get(AttributeNameExcel));
	            		 }
	         			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);} 
	            	 	}
		            	 catch(Exception e){
		            		 System.out.println(AttributeNameExcel+" is missing in json response");
			                	Cell updateCell=row.getCell(updateCellColumnNo);
			                	updateCell.setCellValue("NA");
		            		 }
	             }
	            else
	            {
	            	 try
         			{
	            		 List<String> prodAtt=new ArrayList<String>();
         				 JSONArray prodAttriArray =obj1.getJSONArray("productAttributes");
         				for(int i=0;i<prodAttriArray.length();i++)
         				{
//         					System.out.println("inside for loop");
	            		 String attrinameJson =prodAttriArray.getJSONObject(i).getString("name");
	            		if(attrinameJson.equalsIgnoreCase(AttributeNameExcel))
	            		{
	            			String retreivevalue=null;
	            			if(call.equalsIgnoreCase("mp"))
		            		 {
		 	            		retreivevalue=String.valueOf(response.getBody().jsonPath().get("productDefinition."+"productAttributes["+i+"].attributeValues[0].value[0].textValue"));
		 	            		 }
	            			 else if(call.equalsIgnoreCase("mpMulti"))
		            		 {
	            			   retreivevalue=String.valueOf(response.getBody().jsonPath().get("["+arrayIndex+"].productDefinition."+"productAttributes["+i+"].attributeValues[0].value[0].textValue"));
			 	             }
		 	            		 else
		 	            		 {	
	            			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("productAttributes["+i+"].attributeValues[0].value[0].textValue"));
		 	            		 }
	            			Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);}
			            	 prodAtt.add(AttributeNameExcel);
	            		}
	            		
         				}
         				if(prodAtt.contains(AttributeNameExcel)){System.out.println("The attribute "+AttributeNameExcel+" is present in the Product Attribute array");}
         				else {System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");}
	      
				    }
         			catch(Exception e)
         			{	
		         			System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
         			}
	            }
	             
             }
            
	 }
	 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
     workbook.write(outputStream);    
	 workbook.close();
	 outputStream.close();	 
	 return gtinResp;	
	}

	public void productGetIQS(Response response1, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		
		int updateCellColumnNo=1;
		 String outputResponse=response1.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject obj2=obj1.getJSONObject("payload");
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 {
		 Row row = rowIterator.next();
         if(row.getRowNum()==0)
         {
         	Cell cell=row.getCell(1);
	        String cellValue = dataFormatter.formatCellValue(cell);
         	System.out.println("The header is "+cellValue);
         	
         }
         else{
         	  Cell cell=row.getCell(0);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	             if(obj2.has(AttributeNameExcel))
	            {
	            	String retreivevalue=obj2.getString(AttributeNameExcel);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null");}
	 	            else{updateCell.setCellValue(retreivevalue);}
	            }
	             else if(AttributeNameExcel.contains("."))
	             {
         		 String retreivevalue=String.valueOf(response1.getBody().jsonPath().get("payload."+AttributeNameExcel));
	         			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);} 
	             }
	            else
	            {
	            	System.out.println(AttributeNameExcel+" is missing in json response");
                	Cell updateCell=row.getCell(updateCellColumnNo);
                	updateCell.setCellValue("NA");
	            } 
	            }
	 }
	 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
     workbook.write(outputStream);    
	 workbook.close();
	 outputStream.close();	
	}

	public List<String> productGetJsonAnyParam(Response response, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		List <String> gtinList=new ArrayList<String>();
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		
		 
		 String outputResponse=response.asString();
		 JSONObject mainObj1=new JSONObject(outputResponse);
		 JSONArray canonList=mainObj1.getJSONArray("canonicalList");
		 for(int i=0;i<canonList.length();i++)
		 {
			 if(i==0)
			 {updateCellColumnNo=3;} 
			 else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
		JSONObject singleObj=canonList.getJSONObject(i);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext())
			 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            	Cell cell=row.getCell(2);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		             if(singleObj.has(AttributeNameExcel))
		            {
		            	String retreivevalue=singleObj.getString(AttributeNameExcel);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null||retreivevalue=="")
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
		            }
		             else if(AttributeNameExcel.contains("."))
		             {
		            	 try{
	            		 String retreivevalue=String.valueOf(response.getBody().jsonPath().get("canonicalList["+i+"]."+AttributeNameExcel));
		         			Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);} 
		            	 	}
			            	 catch(Exception e){
			            		 System.out.println(AttributeNameExcel+" is missing in json response");
				                	Cell updateCell=row.getCell(updateCellColumnNo);
				                	updateCell.setCellValue("NA");
			            		 }
		             }
		            else
		            {
		            	 try
	         			{
		            		 List<String> prodAtt=new ArrayList<String>();
	         				 JSONArray prodAttriArray =singleObj.getJSONArray("productAttributes");
	         				for(int l=0;l<prodAttriArray.length();l++)
	         				{
//	         					System.out.println("inside for loop");
		            		 String attrinameJson =prodAttriArray.getJSONObject(l).getString("name");
		            		if(attrinameJson.equalsIgnoreCase(AttributeNameExcel))
		            		{
//		            			System.out.println("inside if");
		            			String retreivevalue=String.valueOf(response.getBody().jsonPath().get("canonicalList["+i+"].productAttributes["+l+"].attributeValues[0].value[0].textValue"));
			         			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	 prodAtt.add(AttributeNameExcel);
		            		}
		            		
	         				}
	         				if(prodAtt.contains(AttributeNameExcel)){System.out.println("The attribute "+AttributeNameExcel+" is present in the Product Attribute array");}
	         				else {System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");}
		      
					    }
	         			catch(Exception e)
	         			{	
			         			System.out.println(AttributeNameExcel+" is missing in json response");
			                	Cell updateCell=row.getCell(updateCellColumnNo);
			                	updateCell.setCellValue("NA");	
	         			}
		            }
	             }
		 
				 
			 }
			 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
		     workbook.write(outputStream); 
//		     String gtin=String.valueOf(response.getBody().jsonPath().get("canonicalList["+i+"].consumableGTINs[0]"));
		     gtinList.add(String.valueOf(response.getBody().jsonPath().get("canonicalList["+i+"].consumableGTINs[0]")));
		 }
		 workbook.close();
		 outputStream.close();	
//		 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+gtinList);
		return gtinList;
	}

	public String productGetJsonDTO(Response response,String sheetName,String call) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException 
	{
		int updateCellColumnNo=3;
		String gtinResp=null;
		excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		 JSONObject obj1=null;
		 if(call.equalsIgnoreCase("mp"))
		 {
			 obj1=new JSONObject(outputResponse).getJSONObject("productDefinition"); 
		 }
		 else
		 {
		 obj1=new JSONObject(outputResponse);
		 }
		 
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 {
            Row row = rowIterator.next();
            if(row.getRowNum()==0)
            {
            	Cell cell=row.getCell(2);
	            String cellValue = dataFormatter.formatCellValue(cell);
            	System.out.println("The header is "+cellValue);
            	
            }
            else{
            	Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	             if(obj1.has(AttributeNameExcel))
	            {
	            	String retreivevalue=obj1.getString(AttributeNameExcel);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null");}
	 	            else{updateCell.setCellValue(retreivevalue);}
	            	 
		             
	            }
	             else if(AttributeNameExcel.contains("."))
	             {
	            	 String retreivevalue=null;
	            	 if(call.equalsIgnoreCase("mp"))
	            	 {
	            		 retreivevalue=String.valueOf(response.getBody().jsonPath().get("productDefinition."+AttributeNameExcel));
	            	 }
	            	 else{
	            		 retreivevalue=String.valueOf(response.getBody().jsonPath().get(AttributeNameExcel));
		         	 }
            		Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);}
		            	 if(AttributeNameExcel.equalsIgnoreCase("productAttributes.gtin.attributeValues[0].value"))
				             gtinResp= retreivevalue;
	             }
	            else
	            {
	            		
		         			System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
         		 }
	             
             }
            
	 }
	 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
     workbook.write(outputStream);    
	 workbook.close();
	 outputStream.close();	 
	 return gtinResp;	
	}
	
	public void productGetJsonMultiGtin(Response response, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		
		 
		 String outputResponse=response.asString();
		 
		 JSONObject mainObj1=new JSONObject(outputResponse);
		 JSONArray prodList=mainObj1.getJSONArray("productList");
		 for(int i=0;i<prodList.length();i++)
		 {
			 if(i==0)
			 {updateCellColumnNo=3;} 
			 else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
		JSONObject singleObj=prodList.getJSONObject(i);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext())
			 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            	Cell cell=row.getCell(2);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		             if(singleObj.has(AttributeNameExcel))
		            {
		            	String retreivevalue=singleObj.getString(AttributeNameExcel);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
		            }
		             else if(AttributeNameExcel.contains("."))
		             {
		            	 try{
	            		 String retreivevalue=String.valueOf(response.getBody().jsonPath().get("productList["+i+"]."+AttributeNameExcel));
		         			Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);} 
		            	 }
		            	 catch(Exception e){
		            		 System.out.println(AttributeNameExcel+" is missing in json response");
			                	Cell updateCell=row.getCell(updateCellColumnNo);
			                	updateCell.setCellValue("NA");
		            		 }
		             }
		            else
		            {
		            	 try
	         			{
		            		 List<String> prodAtt=new ArrayList<String>();
	         				 JSONArray prodAttriArray =singleObj.getJSONArray("productAttributes");
	         				for(int l=0;l<prodAttriArray.length();l++)
	         				{
//	         					System.out.println("inside for loop");
		            		 String attrinameJson =prodAttriArray.getJSONObject(l).getString("name");
		            		if(attrinameJson.equalsIgnoreCase(AttributeNameExcel))
		            		{
//		            			System.out.println("inside if");
		            			String retreivevalue=String.valueOf(response.getBody().jsonPath().get("productList["+i+"].productAttributes["+l+"].attributeValues[0].value[0].textValue"));
			         			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	 prodAtt.add(AttributeNameExcel);
		            		}
		            		
	         				}
	         				if(prodAtt.contains(AttributeNameExcel)){System.out.println("The attribute "+AttributeNameExcel+" is present in the Product Attribute array");}
	         				else {System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");}
		      
					    }
	         			catch(Exception e)
	         			{	
			         			System.out.println(AttributeNameExcel+" is missing in json response");
			                	Cell updateCell=row.getCell(updateCellColumnNo);
			                	updateCell.setCellValue("NA");	
	         			}
		            }
	             }
		 
				 
			 }
			 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
		     workbook.write(outputStream); 
  	 }
		 workbook.close();
		 outputStream.close();	

	}
	
	public Object[] responseValidationSIGetByMultipleItemNumber(Response response,String sheetName,String call,int arraylen) throws JSONException, EncryptedDocumentException, InvalidFormatException, IOException
	 {
		List<String> itemNo=new ArrayList<String>();
		List<String> gtins=new ArrayList<String>();
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 
		 String outputResponse=response.asString();
		 JSONArray ItemNoArray=null;
//		 JSONObject mainobj=new JSONArray(outputResponse);
		 if(call.equalsIgnoreCase("mp"))
		 {
			 ItemNoArray=new JSONObject(outputResponse).getJSONArray("tradeItemsSupplyItems").getJSONObject(0).getJSONArray("supplyItemTradeItemPack");	 
		 }
		 else if(call.equalsIgnoreCase("mpMulti"))
		 {
			 ItemNoArray=new JSONArray(outputResponse).getJSONObject(arraylen).getJSONArray("tradeItemsSupplyItems").getJSONObject(0).getJSONArray("supplyItemTradeItemPack");	 
		 }
		 else{
		 ItemNoArray=new JSONArray(outputResponse);
		 }
		 for(int l=0;l<ItemNoArray.length();l++)
		 {
			 if(l==0)
			 {updateCellColumnNo=3;} 
			 else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
			 
			JSONObject obj1= ItemNoArray.getJSONObject(l);
		   JSONObject jsonObj2=obj1.getJSONObject("supplyItem");
		    String itemnumber=obj1.getJSONObject("supplyItem").getString("number");
		    String gtin=obj1.getJSONObject("supplyItem").getString("consumableGTIN");
		 
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
		 
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
//	            if(AttributeNameExcel.equalsIgnoreCase("lastUpdateTs")||AttributeNameExcel.equals("isPrimaryVendorInd")||AttributeNameExcel.equalsIgnoreCase("isRetailNotifyStoreInd")||AttributeNameExcel.equalsIgnoreCase("buyerUserId")||AttributeNameExcel.equalsIgnoreCase("omitTraitCode")||AttributeNameExcel.equalsIgnoreCase("lastUpdateProgramId")||
//	            		AttributeNameExcel.equalsIgnoreCase("isRetailVatInclusiveInd")||AttributeNameExcel.equalsIgnoreCase("sendTraitCode")||
//	            		AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty")||AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode"))
//	            {
//	            if(jsonObj2.has("supplyItemAttributes"))
//	            {
//	            	Object tradeItemAttributes = jsonObj2.get("supplyItemAttributes");
//	            	if(tradeItemAttributes instanceof JSONArray)
//	            	{
//	            		JSONArray array1=(JSONArray) tradeItemAttributes;
//	            		List<String> specialAtrri=new ArrayList<String>();
//	            		for(int i=0;i<array1.length();i++)
//	            		{
//	            			String attriname=array1.getJSONObject(i).getString("name");
//	            			specialAtrri.add(attriname);
//	            			if(specialAtrri.contains(AttributeNameExcel)){
//	            			
//	            			if(attriname.equalsIgnoreCase(AttributeNameExcel))
//	            			{
//	            				
//	            				String updateSpecialRetreive =array1.getJSONObject(i).getJSONArray("attributeValues").getJSONObject(0).getJSONArray("value").getJSONObject(0).getString("textValue");
//	            				Cell updateCell=row.getCell(updateCellColumnNo);
//	            				if(updateSpecialRetreive==null)
//	    		 	            { updateCell.setCellValue("null"); }
//	    		 	            else{updateCell.setCellValue(updateSpecialRetreive);}
//	            			}
//	            			}
//	            			else
//	            			{
//	            				System.out.println(AttributeNameExcel+" is missing in json response");
//	    	                	Cell updateCell=row.getCell(updateCellColumnNo);
//	    	                	updateCell.setCellValue("NA");
//	            			}
//	            			
//	            		}
//	            		
//	            	}
//	            	else
//	            	{
//	            		String retreivevalue=jsonObj2.getString("supplyItemAttributes");
//		            	Cell updateCell=row.getCell(updateCellColumnNo);
//		            	 if(retreivevalue==null)
//		 	            {updateCell.setCellValue("null"); }
//		 	            else{updateCell.setCellValue(retreivevalue);}
//	            	}
//	            	
//	            }	
//	            else{
//	            	System.out.println(AttributeNameExcel+" is missing in json response");
//	                	Cell updateCell=row.getCell(updateCellColumnNo);
//	                	updateCell.setCellValue("NA");
//	            }
//	            }
	            if(AttributeNameExcel.equalsIgnoreCase("destinationFormat")||AttributeNameExcel.equalsIgnoreCase("factory")||
	            		AttributeNameExcel.equalsIgnoreCase("originCountry")||AttributeNameExcel.equalsIgnoreCase("eligibilityStates"))
	            {
	            	if(jsonObj2.has(AttributeNameExcel))
	            	{
	            	Object desFor=jsonObj2.get(AttributeNameExcel);
	            	 if (desFor instanceof JSONArray){
		            	List<String> destinationFormatCode=new ArrayList<String>();
		            	JSONArray formatArray=jsonObj2.getJSONArray(AttributeNameExcel);
		            	for(int i=0;i<formatArray.length();i++)
		            	{
		            		try
		            		{
		            		destinationFormatCode.add(formatArray.getJSONObject(i).getString("code"));
		            		}
		            		catch (Exception e){
		            			destinationFormatCode.add(formatArray.getJSONObject(i).getString("id"));
		            		}
		            	}
		            	
		            	String retreivevalue=String.valueOf(destinationFormatCode);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null"); }
		 	            else{updateCell.setCellValue(retreivevalue);}
		            	}
	            	 else 
	            		{
	    	            	String retreivevalue=jsonObj2.getString(AttributeNameExcel);
	    	            	Cell updateCell=row.getCell(updateCellColumnNo);
	    	            	 if(retreivevalue==null)
	    	 	            {
	    	 	            	// when the value to be enter is null
	    	 	            	updateCell.setCellValue("null");
	    	 	            }
	    	 	            else{ updateCell.setCellValue(retreivevalue);}
	            		}
	            }
	            else{
    				System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
        			
	            		
	            	}
	            	
	            }
	            else if(jsonObj2.has(AttributeNameExcel))
	            {
//	            	System.out.println(AttributeNameExcel);
	            	String retreivevalue=jsonObj2.getString(AttributeNameExcel);
//	            	System.out.println(retreivevalue);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {
	 	            	// when the value to be enter is null
	 	            	updateCell.setCellValue("null");
	 	            }
	 	            else{ updateCell.setCellValue(retreivevalue);}

	            }
	            else if(AttributeNameExcel.contains("."))
	            {

            		try{
            			String retreivevalue=null;
            			 if(call.equalsIgnoreCase("mp"))
            			 {
            				 retreivevalue=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].supplyItemTradeItemPack["+l+"].supplyItem."+AttributeNameExcel)); 
            			 }
            			 else if(call.equalsIgnoreCase("mpMulti"))
            			 {
            				 retreivevalue=String.valueOf(response.getBody().jsonPath().get("["+arraylen+"].tradeItemsSupplyItems[0].supplyItemTradeItemPack["+l+"].supplyItem."+AttributeNameExcel)); 
            			 }
            			 else{
            			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem["+l+"]."+AttributeNameExcel));
            			 }
            			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {
		 	            	// when the value to be enter is null
		 	            	updateCell.setCellValue("null");
		 	            }
		 	            else{ updateCell.setCellValue(retreivevalue);}
            			}
            			catch(Exception e){
            				System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");
            			}
            		

            	
	            	
	            }
	            
	            else{

		            if(jsonObj2.has("supplyItemAttributes"))
		            {
		            	Object tradeItemAttributes = jsonObj2.get("supplyItemAttributes");
		            	if(tradeItemAttributes instanceof JSONArray)
		            	{
		            		JSONArray array1=(JSONArray) tradeItemAttributes;
		            		List<String> specialAtrri=new ArrayList<String>();
		            		for(int i=0;i<array1.length();i++)
		            		{
		            			String attriname=array1.getJSONObject(i).getString("name");
		            			specialAtrri.add(attriname);
		            			if(specialAtrri.contains(AttributeNameExcel)){
		            			
		            			if(attriname.equalsIgnoreCase(AttributeNameExcel))
		            			{
		            				
		            				String updateSpecialRetreive =array1.getJSONObject(i).getJSONArray("attributeValues").getJSONObject(0).getJSONArray("value").getJSONObject(0).getString("textValue");
		            				Cell updateCell=row.getCell(updateCellColumnNo);
		            				if(updateSpecialRetreive==null)
		    		 	            { updateCell.setCellValue("null"); }
		    		 	            else{updateCell.setCellValue(updateSpecialRetreive);}
		            			}
		            			}
		            			else
		            			{
    	            				if(AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty")||AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode")
    	            						||AttributeNameExcel.contains("supplierLeadTime"))
    	            				{
    	            					try{
    	            						String retreivevalue=null;
    	            						if(AttributeNameExcel.equalsIgnoreCase("sellTotalContentQty"))
    	                        			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem["+l+"].sellTotalContentQuantity.amount"));
    	            						else if(AttributeNameExcel.equalsIgnoreCase("sellTotalContentUomCode"))
    	            							retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem["+l+"].sellTotalContentQuantity.uom"));
    	            						else if(AttributeNameExcel.equalsIgnoreCase("supplierLeadTimeUomCode"))
    	            							retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem["+l+"].supplierLeadTime.uom"));
    	            						else if (AttributeNameExcel.equalsIgnoreCase("supplierLeadTimeQty"))
    	            							retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItem["+l+"].supplierLeadTime.amount"));
    	            						
    	            						Cell updateCell=row.getCell(updateCellColumnNo);
    	            		            	 if(retreivevalue==null)
    	            		 	            {
    	            		 	            	// when the value to be enter is null
    	            		 	            	updateCell.setCellValue("null");
    	            		 	            }
    	            		 	            else{ updateCell.setCellValue(retreivevalue);}
    	                        		
    	            						
    	            					}
    	            					catch(Exception e){
    	            						System.out.println(AttributeNameExcel+" is missing in json response");
            	    	                	Cell updateCell=row.getCell(updateCellColumnNo);
            	    	                	updateCell.setCellValue("NA");
    	            					}
    	            				}
    	            				else{
    	            				System.out.println(AttributeNameExcel+" is missing in json response");
    	    	                	Cell updateCell=row.getCell(updateCellColumnNo);
    	    	                	updateCell.setCellValue("NA");
    	            				}
    	            			}
		            			
		            		}
		            		
		            	}
		            	else
		            	{
		            		String retreivevalue=jsonObj2.getString("supplyItemAttributes");
			            	Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{updateCell.setCellValue(retreivevalue);}
		            	}
		            	
		            }	
		            else{
		            	
		            	System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");
		            }
		            
	            	
	            	}
	           	}
		 }
		 itemNo.add(itemnumber);
		 gtins.add(gtin);
		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	        workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();
			return new Object[] {itemNo,gtins};
			
	 }

	
	public String offerGetJson(Response response, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		String gtinJson=null;
		excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		 
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject obj2=obj1.getJSONArray("canonicalList").getJSONObject(0);
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
			   Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            }
	            else
	            {
	            	Cell cell=row.getCell(2);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		            if(obj2.has(AttributeNameExcel))
		            {
		            	String retreivevalue=obj2.getString(AttributeNameExcel);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
		            }
		            else if(AttributeNameExcel.contains("."))
		            {
		            	try
		            	{
		            	String retreivevalue=String.valueOf(response.getBody().jsonPath().get("canonicalList[0]."+AttributeNameExcel));
	         			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);}
		            	}
		            	catch(Exception e)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		            }
		            else
		            {
		            	boolean flag=false;
		            	JSONArray offIdenArr=obj2.getJSONArray("offeringIdentifiers");
		            	for(int m=0;m<offIdenArr.length();m++)
		            	{
		            		JSONObject offIdOb=offIdenArr.getJSONObject(m);
		            	if((offIdOb.getString("type")).equalsIgnoreCase(AttributeNameExcel))
		            	{
		            		String retreivevalue=offIdOb.getString("value");
		            		Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);}
			            	 if(AttributeNameExcel.equalsIgnoreCase("GTIN"))
			            	 {gtinJson= retreivevalue;}
			            	 flag=true;
		            	}
		               }
		            	
		            	JSONArray offAttriArr=obj2.getJSONArray("offeringAttributes");
		            	for(int m=0;m<offAttriArr.length();m++)
		            	{
		            		JSONObject offAttrObj=offAttriArr.getJSONObject(m);
		            		
		            		if((offAttrObj.getString("name")).equalsIgnoreCase(AttributeNameExcel))
			            	{
		            			String retreivevalue=offAttrObj.getJSONArray("attributeValues").getJSONObject(0).getJSONArray("value").getJSONObject(0).getString("textValue");
		            			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	flag=true;
			            	}
		            	}
		            	
		            	if(flag==false)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		            	
		            }
		          }
		            
		 		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();	
		 return gtinJson;           
	     }

	public void offerDTOGetMP(Response response,String sheetName) throws JSONException, EncryptedDocumentException, InvalidFormatException, IOException
	{
		excelClearmulti(sheetName);
		 int updateCellColumnNo=3;
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 String outputResponse=response.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject obj2=obj1.getJSONArray("productOfferings").getJSONObject(0).getJSONObject("Offer");
		 
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
		 Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(0);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            }
	            else
	            {
	            	Cell cell=row.getCell(0);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		          
		            if(obj2.has(AttributeNameExcel))
		            {
		            	String retreivevalue=obj2.getString(AttributeNameExcel);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
		            }
		            else if(AttributeNameExcel.contains(","))
		            {
		            	
		            	String[] excelAtrr= AttributeNameExcel.split(",");
		            	
		            	boolean flag1=false;
		            	for(int i=0;i<excelAtrr.length;i++)
		            	{
		            	JSONArray offAttriArr=obj2.getJSONArray("offerAttributes");
		            	for(int m=0;m<offAttriArr.length();m++)
		            	{
		            		JSONObject offAttrObj=offAttriArr.getJSONObject(m);
		            		String resAttri=offAttrObj.getJSONObject("attributeDefinition").getString("attrName");
		            		
		            		if(resAttri.equalsIgnoreCase(excelAtrr[i]))
			            	{
		            			try{
		            			String retreivevalue=offAttrObj.getJSONArray("attributeValues").getJSONObject(0).getString("attributeValue");
		            			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	flag1=true;
		            			}
				            	catch(Exception e)
				            	{
				            		System.out.println(excelAtrr[i]+" is missing in json response");
				                	Cell updateCell=row.getCell(updateCellColumnNo);
				                	updateCell.setCellValue("NA");	
				            	}
				            	
			            	}
		            	}
		            	}
		            	if(flag1==false)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		            	
		            }
		            else if(AttributeNameExcel.contains("."))
		            {
		            	try
		            	{
		            	String retreivevalue=String.valueOf(response.getBody().jsonPath().get("productOfferings[0].Offer."+AttributeNameExcel));
	         			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);}
		            	}
		            	catch(Exception e)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}	
		            }
		            else
		            {

		            	boolean flag=false;
		            	JSONArray offIdenArr=obj2.getJSONArray("offerIdentifiers");
		            	for(int m=0;m<offIdenArr.length();m++)
		            	{
		            		JSONObject offIdOb=offIdenArr.getJSONObject(m);
		            	if((offIdOb.getString("keyName")).equalsIgnoreCase(AttributeNameExcel))
		            	{
		            		String retreivevalue=offIdOb.getString("keyValue");
		            		Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);}
			            	 
			            	 flag=true;
		            	}
		               }
		            	
		            	JSONArray offAttriArr=obj2.getJSONArray("offerAttributes");
		            	for(int m=0;m<offAttriArr.length();m++)
		            	{
		            		JSONObject offAttrObj=offAttriArr.getJSONObject(m);

		            		String resAttri=offAttrObj.getJSONObject("attributeDefinition").getString("attrName");
		            		if(resAttri.equalsIgnoreCase(AttributeNameExcel))
			            	{
		            			try{
		            			String retreivevalue=offAttrObj.getJSONArray("attributeValues").getJSONObject(0).getString("attributeValue");
		            			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	flag=true;
		            			}
				            	catch(Exception e)
				            	{
				            		System.out.println(AttributeNameExcel+" is missing in json response");
				                	Cell updateCell=row.getCell(updateCellColumnNo);
				                	updateCell.setCellValue("NA");	
				            	}
				            	
			            	}
		            	}
		            	
		            	if(flag==false)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		           }
		          } 
		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();	
	
	}
	
	public void iqsOfferRes(Response response1,String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		 int updateCellColumnNo=1;
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 String outputResponse=response1.asString();
		 JSONObject obj1=new JSONObject(outputResponse);
		 JSONObject obj2=obj1.getJSONObject("payload").getJSONObject("Offer");
		 
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
		 Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(0);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            }
	            else
	            {
	            	Cell cell=row.getCell(0);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		          
		            if(obj2.has(AttributeNameExcel))
		            {
		            	String retreivevalue=obj2.getString(AttributeNameExcel);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
		            }
		            else if(AttributeNameExcel.contains(","))
		            {
		            	
		            	String[] excelAtrr= AttributeNameExcel.split(",");
		            	
		            	boolean flag1=false;
		            	for(int i=0;i<excelAtrr.length;i++)
		            	{
		            	JSONArray offAttriArr=obj2.getJSONArray("offerAttributes");
		            	for(int m=0;m<offAttriArr.length();m++)
		            	{
		            		JSONObject offAttrObj=offAttriArr.getJSONObject(m);
		            		String resAttri=offAttrObj.getJSONObject("attributeDefinition").getString("attrName");
		            		
		            		if(resAttri.equalsIgnoreCase(excelAtrr[i]))
			            	{
		            			try{
		            			String retreivevalue=offAttrObj.getJSONArray("attributeValues").getJSONObject(0).getString("attributeValue");
		            			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	flag1=true;
		            			}
				            	catch(Exception e)
				            	{
				            		System.out.println(excelAtrr[i]+" is missing in json response");
				                	Cell updateCell=row.getCell(updateCellColumnNo);
				                	updateCell.setCellValue("NA");	
				            	}
				            	
			            	}
		            	}
		            	}
		            	if(flag1==false)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		            	
		            }
		            else if(AttributeNameExcel.contains("."))
		            {
		            	try
		            	{
		            	String retreivevalue=String.valueOf(response1.getBody().jsonPath().get(AttributeNameExcel));
	         			Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);}
		            	}
		            	catch(Exception e)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}	
		            }
		            else
		            {

		            	boolean flag=false;
		            	JSONArray offIdenArr=obj2.getJSONArray("offerIdentifiers");
		            	for(int m=0;m<offIdenArr.length();m++)
		            	{
		            		JSONObject offIdOb=offIdenArr.getJSONObject(m);
		            	if((offIdOb.getString("keyName")).equalsIgnoreCase(AttributeNameExcel))
		            	{
		            		String retreivevalue=offIdOb.getString("keyValue");
		            		Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);}
			            	 
			            	 flag=true;
		            	}
		               }
		            	
		            	JSONArray offAttriArr=obj2.getJSONArray("offerAttributes");
		            	for(int m=0;m<offAttriArr.length();m++)
		            	{
		            		JSONObject offAttrObj=offAttriArr.getJSONObject(m);

		            		String resAttri=offAttrObj.getJSONObject("attributeDefinition").getString("attrName");
		            		if(resAttri.equalsIgnoreCase(AttributeNameExcel))
			            	{
		            			try{
		            			String retreivevalue=offAttrObj.getJSONArray("attributeValues").getJSONObject(0).getString("attributeValue");
		            			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	flag=true;
		            			}
				            	catch(Exception e)
				            	{
				            		System.out.println(AttributeNameExcel+" is missing in json response");
				                	Cell updateCell=row.getCell(updateCellColumnNo);
				                	updateCell.setCellValue("NA");	
				            	}
				            	
			            	}
		            	}
		            	
		            	if(flag==false)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		           }
		          } 
		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();	
	}
	
	public String offerGetbyOfferIDJson(Response response, String sheetName, String call,int arrayNumb) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		String gtinJson=null;
		excelClearmulti(sheetName);
		 String outputResponse=response.asString();
		 JSONObject obj2=null;
//		 JSONObject obj1=new JSONObject(outputResponse);
		 if(call.equalsIgnoreCase("mp")){
			 obj2=new JSONObject(outputResponse).getJSONArray("productOfferings").getJSONObject(0); 
		 }
		 else if(call.equalsIgnoreCase("mpMulti")){
			 obj2=new JSONArray(outputResponse).getJSONObject(arrayNumb).getJSONArray("productOfferings").getJSONObject(0); 
		 }
		 else{
		  obj2=new JSONObject(outputResponse);
		 }
		 FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
			   Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            }
	            else
	            {
	            	Cell cell=row.getCell(2);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		            if(obj2.has(AttributeNameExcel))
		            {
		            	String retreivevalue=obj2.getString(AttributeNameExcel);
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{updateCell.setCellValue(retreivevalue);}
		            }
		            else if(AttributeNameExcel.contains("."))
		            {
		            	try
		            	{
		            		String retreivevalue=null;
		            		if(call.equalsIgnoreCase("mp")){
		            			retreivevalue=String.valueOf(response.getBody().jsonPath().get("productOfferings[0]."+AttributeNameExcel));
		            		}
		            		 else if(call.equalsIgnoreCase("mpMulti")){
		            			 retreivevalue=String.valueOf(response.getBody().jsonPath().get("["+arrayNumb+"].productOfferings[0]."+AttributeNameExcel));
				           }
		            		else{
		            	 retreivevalue=String.valueOf(response.getBody().jsonPath().get(AttributeNameExcel));
	         			}
		            	Cell updateCell=row.getCell(updateCellColumnNo);
		            	 if(retreivevalue==null)
		 	            {updateCell.setCellValue("null");}
		 	            else{ updateCell.setCellValue(retreivevalue);}
		            	}
		            	catch(Exception e)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		            }
		            else
		            {
		            	boolean flag=false;
		            	JSONArray offIdenArr=obj2.getJSONArray("offeringIdentifiers");
		            	for(int m=0;m<offIdenArr.length();m++)
		            	{
		            		JSONObject offIdOb=offIdenArr.getJSONObject(m);
		            	if((offIdOb.getString("type")).equalsIgnoreCase(AttributeNameExcel))
		            	{
		            		String retreivevalue=offIdOb.getString("value");
		            		Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {updateCell.setCellValue("null");}
			 	            else{ updateCell.setCellValue(retreivevalue);}
			            	 if(AttributeNameExcel.equalsIgnoreCase("GTIN"))
			            	 {gtinJson= retreivevalue;}
			            	 flag=true;
		            	}
		               }
		            	
		            	JSONArray offAttriArr=obj2.getJSONArray("offeringAttributes");
		            	for(int m=0;m<offAttriArr.length();m++)
		            	{
		            		JSONObject offAttrObj=offAttriArr.getJSONObject(m);
		            		
		            		if((offAttrObj.getString("name")).equalsIgnoreCase(AttributeNameExcel))
			            	{
		            			String retreivevalue=offAttrObj.getJSONArray("attributeValues").getJSONObject(0).getJSONArray("value").getJSONObject(0).getString("textValue");
		            			Cell updateCell=row.getCell(updateCellColumnNo);
				            	 if(retreivevalue==null)
				 	            {updateCell.setCellValue("null");}
				 	            else{ updateCell.setCellValue(retreivevalue);}
				            	flag=true;
			            	}
		            	}
		            	
		            	if(flag==false)
		            	{
		            		System.out.println(AttributeNameExcel+" is missing in json response");
		                	Cell updateCell=row.getCell(updateCellColumnNo);
		                	updateCell.setCellValue("NA");	
		            	}
		            	
		            }
		          }
		            
		 		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();	
		 return gtinJson;           
	     }

	public Object[] responseSplit(Response response) throws JSONException 
	{
		
		 JSONObject productjson=null;
		 String offerId=null;
		 int supplyItemSize=0, consumableTradItemSize=0;
		 JSONArray offerArray = null,consumableTradeArray=null,SupplyTradeArray=null;
		String outputResponse=response.asString();
		JSONObject obj2=new JSONObject(outputResponse);
		if(obj2.has("productDefinition"))
		{
			
			Object nullCheck=obj2.get("productDefinition");
			if(nullCheck instanceof JSONObject)
			{
				log.debug("The Gtin is having Product");
			    productjson=obj2.getJSONObject("productDefinition");
			   
			}
		}
		if(obj2.has("productOfferings"))
		{
			Object nullCheck=obj2.get("productOfferings");
			if(nullCheck instanceof JSONArray)	
			{
				JSONArray offArrCheck=	obj2.getJSONArray("productOfferings");
				 if(offArrCheck.length()!=0)
				 {
					 log.debug("The Gtin is having Offer");
					 offerArray=offArrCheck;
					 JSONObject obj=offArrCheck.getJSONObject(0);
//					 retreiveResponseAttri(obj);
					 offerId=String.valueOf(response.getBody().jsonPath().get("productOfferings[0].id"));
		
				 }
			}
		}
		if(obj2.has("tradeItemsSupplyItems"))
		{
			JSONArray tradeSupplyArrCheck=	obj2.getJSONArray("tradeItemsSupplyItems");
			 if(tradeSupplyArrCheck.length()!=0)
			 {
				 JSONObject arrayhold=tradeSupplyArrCheck.getJSONObject(0);
				 if(arrayhold.has("consumableTradeItems"))
				 {
					 
					 Object nullCheck=arrayhold.get("consumableTradeItems");
						if(nullCheck instanceof JSONArray)
						{
							log.debug("The Gtin is having Consumable trade item");
							consumableTradeArray=arrayhold.getJSONArray("consumableTradeItems");
							consumableTradItemSize=consumableTradeArray.length();
//							retreiveResponseAttri(consumableTradeArray.getJSONObject(0));
						}
					 
				 }
				 if(arrayhold.has("supplyItemTradeItemPack"))
				 {
					 Object nullCheck=arrayhold.get("supplyItemTradeItemPack");
						if(nullCheck instanceof JSONArray)
						{
							log.debug("The Gtin is having Supply trade item");
							SupplyTradeArray=arrayhold.getJSONArray("supplyItemTradeItemPack");
							supplyItemSize=SupplyTradeArray.length();
//							supplyTradeRes=response.getBody().jsonPath().get("tradeItemsSupplyItems[0].supplyItemTradeItemPack");
						}
				 }
				
				 
			 }	
		
		}
		return new Object[] {productjson,offerArray,consumableTradeArray,SupplyTradeArray,supplyItemSize,consumableTradItemSize};
	}

	public String[] retreiveContext(Response response,int tradeitem,String call,int callSize) throws JSONException {
		String infoId=null,infoIdType=null, recpGln=null,targetMark=null;
		
		if(call.equalsIgnoreCase("mpMulti")) 
		{
			 infoId=String.valueOf(response.getBody().jsonPath().get("["+callSize+"].tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].informationProviderId.value"));
			  infoIdType=String.valueOf(response.getBody().jsonPath().get("["+callSize+"].tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].informationProviderId.type"));
			  recpGln=String.valueOf(response.getBody().jsonPath().get("["+callSize+"].tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].recipientGLN"));
			  targetMark=String.valueOf(response.getBody().jsonPath().get("["+callSize+"].tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].targetMarketCode"));
			 	
		}
		else{
		 infoId=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].informationProviderId.value"));
		  infoIdType=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].informationProviderId.type"));
		  recpGln=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].recipientGLN"));
		  targetMark=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems["+tradeitem+"].targetMarketCode"));
		}
		 return new String[]{infoId,infoIdType,recpGln,targetMark};
	}

	public String retreiveConsumableGtin(Response response) {
		
		String gtin=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].supplyItemTradeItemPack[0].supplyItem.consumableGTIN"));
		return gtin;
	}
	public String retreiveMultiConsumableGtin(Response response, int arraylength) {
		
		String gtin=String.valueOf(response.getBody().jsonPath().get("["+arraylength+"].tradeItemsSupplyItems[0].supplyItemTradeItemPack[0].supplyItem.consumableGTIN"));
		return gtin;
	}
	
	public int arraySize(Response response) throws JSONException
	{
		String outputResponse=response.asString();
		JSONArray obj2=new JSONArray(outputResponse);
		int arraylength=obj2.length();
		return arraylength;
		
	}
	public Object[] multiResponseSplit(Response response, int arrayNo) throws JSONException 
	{
		
		 JSONObject productjson=null;
		 String offerId=null;
		 int supplyItemSize=0, consumableTradItemSize=0;
		 JSONArray offerArray = null,consumableTradeArray=null,SupplyTradeArray=null;
		String outputResponse=response.asString();
		JSONObject obj2=new JSONArray(outputResponse).getJSONObject(arrayNo);
		if(obj2.has("productDefinition"))
		{
			
			Object nullCheck=obj2.get("productDefinition");
			if(nullCheck instanceof JSONObject)
			{
				log.debug("The Gtin is having Product");
			    productjson=obj2.getJSONObject("productDefinition");

			   
			}
		}
		if(obj2.has("productOfferings"))
		{
			Object nullCheck=obj2.get("productOfferings");
			if(nullCheck instanceof JSONArray)	
			{
				JSONArray offArrCheck=	obj2.getJSONArray("productOfferings");
				 if(offArrCheck.length()!=0)
				 {
					 log.debug("The Gtin is having Offer");
					 offerArray=offArrCheck;
					 offerId=String.valueOf(response.getBody().jsonPath().get("["+arrayNo+"].productOfferings[0].id"));
		
				 }
			}
		}
		if(obj2.has("tradeItemsSupplyItems"))
		{
			JSONArray tradeSupplyArrCheck=	obj2.getJSONArray("tradeItemsSupplyItems");
			 if(tradeSupplyArrCheck.length()!=0)
			 {
				 JSONObject arrayhold=tradeSupplyArrCheck.getJSONObject(0);
				 if(arrayhold.has("consumableTradeItems"))
				 {
					 
					 Object nullCheck=arrayhold.get("consumableTradeItems");
						if(nullCheck instanceof JSONArray)
						{
							log.debug("The Gtin is having Consumable trade item");
							consumableTradeArray=arrayhold.getJSONArray("consumableTradeItems");
							consumableTradItemSize=consumableTradeArray.length();
						}
					 
				 }
				 if(arrayhold.has("supplyItemTradeItemPack"))
				 {
					 Object nullCheck=arrayhold.get("supplyItemTradeItemPack");
						if(nullCheck instanceof JSONArray)
						{
							log.debug("The Gtin is having Supply trade item");
							SupplyTradeArray=arrayhold.getJSONArray("supplyItemTradeItemPack");
							supplyItemSize=SupplyTradeArray.length();
//							supplyTradeRes=response.getBody().jsonPath().get("tradeItemsSupplyItems[0].supplyItemTradeItemPack");
						}
				 }
				
				 
			 }	
		
		}
		return new Object[] {productjson,offerArray,consumableTradeArray,SupplyTradeArray,supplyItemSize,consumableTradItemSize};
	}

	public Object[] responseSplitDTO(Response response) throws JSONException 
	{
		
		 JSONObject productjson=null,consumableTradeItemjson=null,supplyItemjson=null,orderableTradeItemsjson=null;
		 String offerId=null;
		JSONArray offerArray=null;
		String outputResponse=response.asString();
		JSONObject obj2=new JSONObject(outputResponse);
		if(obj2.has("productDefinition"))
		{
			
			Object nullCheck=obj2.get("productDefinition");
			if(nullCheck instanceof JSONObject)
			{
				log.debug("The Gtin is having Product");
			    productjson=obj2.getJSONObject("productDefinition");
			   
			}
		}
		if(obj2.has("productOfferings"))
		{
			Object nullCheck=obj2.get("productOfferings");
			if(nullCheck instanceof JSONArray)	
			{
				JSONArray offArrCheck=	obj2.getJSONArray("productOfferings");
				 if(offArrCheck.length()!=0)
				 {
					 log.debug("The Gtin is having Offer");
					 offerArray=offArrCheck;
					 JSONObject obj=offArrCheck.getJSONObject(0);
					 offerId=String.valueOf(response.getBody().jsonPath().get("productOfferings[0].id"));
		
				 }
			}
		}
		if(obj2.has("tradeItemsSupplyItems"))
		{
			
			JSONArray tradeSupplyArrCheck=	obj2.getJSONArray("tradeItemsSupplyItems");
			 if(tradeSupplyArrCheck.length()!=0)
			 {
				 
				 JSONObject arrayhold=tradeSupplyArrCheck.getJSONObject(0);
				 if(arrayhold.has("consumableTradeItem"))
				 {
					
					 Object nullCheck=arrayhold.get("consumableTradeItem");
						if(nullCheck instanceof JSONObject)
						{
							log.debug("The Gtin is having Consumable trade item");
							consumableTradeItemjson=arrayhold.getJSONObject("consumableTradeItem");

						}
				 }
				 if(arrayhold.has("consumableTradeItems"))
				 {
					
					 Object nullCheck1=arrayhold.get("consumableTradeItems");
						if(nullCheck1 instanceof JSONObject)
						{
							log.debug("The Gtin is having Consumable trade item");
							consumableTradeItemjson=arrayhold.getJSONObject("consumableTradeItems");
//									retreiveResponseAttri(consumableTradeItemjson);
						}
					 
				 }
				 if(arrayhold.has("supplyItem"))
				 {
					 Object nullCheck=arrayhold.get("supplyItem");
						if(nullCheck instanceof JSONObject)
						{
							log.debug("The Gtin is having Supplyitem");
							supplyItemjson=arrayhold.getJSONObject("supplyItem");
//							retreiveResponseAttri(supplyItemjson);
//							retreiveResponseAttri(supplyItemjson.getJSONObject("attributes"));
						}
				 }
				 if(arrayhold.has("orderableTradeItems"))
				 {
					 Object nullCheck=arrayhold.get("orderableTradeItems");
						if((nullCheck!=null)&&(nullCheck instanceof JSONArray))
						{
							JSONArray orderArray=arrayhold.getJSONArray("orderableTradeItems");
							
							if((orderArray.length()!=0)&& (orderArray.get(0) instanceof JSONObject))
							{
							log.debug("The Gtin is having orderabley trade item");
							orderableTradeItemsjson=orderArray.getJSONObject(0);
							}
						}
				 }
				
				 
			 }
			 
		
		}
		if(obj2.has("consumableTradeItems"))
		 {
			 
			 Object nullCheck=obj2.get("consumableTradeItems");
				if(nullCheck instanceof JSONArray)
				{
					JSONArray consArray=obj2.getJSONArray("consumableTradeItems");
					if((consArray.length()!=0)&& (consArray.get(0) instanceof JSONObject))
					{
					log.debug("The Gtin is having Consumable trade item");
					consumableTradeItemjson=consArray.getJSONObject(0);
					}
				}
			 
		 }
		 if(obj2.has("supplyItems"))
		 {
			 Object nullCheck=obj2.get("supplyItems");
			
				if(nullCheck instanceof JSONArray)
				{
					JSONArray suppArray=obj2.getJSONArray("supplyItems");
					if((suppArray.length()!=0)&& (suppArray.get(0) instanceof JSONObject))
					{
					log.debug("The Gtin is having supplyitemitem");
					supplyItemjson=suppArray.getJSONObject(0);
					}
				}
		 }
		 if(obj2.has("orderableTradeItems"))
		 {
			 Object nullCheck=obj2.get("orderableTradeItems");
				if((nullCheck!=null)&&(nullCheck instanceof JSONArray))
				{
					JSONArray orderArray=obj2.getJSONArray("orderableTradeItems");
					
					if((orderArray.length()!=0)&& (orderArray.get(0) instanceof JSONObject))
					{
					log.debug("The Gtin is having orderabley trade item");
					orderableTradeItemsjson=orderArray.getJSONObject(0);
					}
				}
		 }
		return new Object[] {productjson,offerArray,consumableTradeItemjson,supplyItemjson,orderableTradeItemsjson};
	}
	
	public String[] retreiveContextDTO(Response response) throws JSONException {
		String infoId=null,infoIdType=null, recpGln=null,targetMark=null;
		
		
		 infoId=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems.informationProviderId"));
		  infoIdType=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems.informationProviderTypeCode"));
		  recpGln=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems.recipientGln"));
		  targetMark=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItems.targetMarketCode"));
		
		 return new String[]{infoId,infoIdType,recpGln,targetMark};
	}
	
	public String[] retreiveContextDTOtisi(Response response) throws JSONException {
		String infoId=null,infoIdType=null, recpGln=null,targetMark=null;
		infoId=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItem.attributes.informationProviderId"));
		  infoIdType=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItem.attributes.informationProviderTypeCode"));
		  recpGln=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItem.attributes.recipientGln"));
		  targetMark=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItem.attributes.targetMarketCode"));
		
		 return new String[]{infoId,infoIdType,recpGln,targetMark};
	}
	public String[] retreiveContextDTOFlat(Response response) throws JSONException {
		String infoId=null,infoIdType=null, recpGln=null,targetMark=null;
		
		
		 infoId=String.valueOf(response.getBody().jsonPath().get("consumableTradeItems[0].attributes.informationProviderId"));
		  infoIdType=String.valueOf(response.getBody().jsonPath().get("consumableTradeItems[0].attributes.informationProviderTypeCode"));
		  recpGln=String.valueOf(response.getBody().jsonPath().get("consumableTradeItems[0].attributes.recipientGln"));
		  targetMark=String.valueOf(response.getBody().jsonPath().get("consumableTradeItems[0].attributes.targetMarketCode"));
		
		 return new String[]{infoId,infoIdType,recpGln,targetMark};
	}

	public void tradeItemJsonDTOGet(Response response, String sheetName,String call) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		String outputResponse=response.asString();
		JSONObject tradeDTOObj=null;
		if(call.equalsIgnoreCase("DTO"))
			{tradeDTOObj=new JSONObject(outputResponse).getJSONArray("tradeItemsSupplyItems").getJSONObject(0).getJSONObject("consumableTradeItem");}
		else
			{tradeDTOObj=new JSONObject(outputResponse).getJSONArray("consumableTradeItems").getJSONObject(0);}
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 { 
		 Row row = rowIterator.next();
         if(row.getRowNum()==0)
         {
         	Cell cell=row.getCell(2);
	            String cellValue = dataFormatter.formatCellValue(cell);
         	System.out.println("The header is "+cellValue);
         	
         }
         else{
        	 //
	         Cell cell=row.getCell(2);
	         String AttributeNameExcel = dataFormatter.formatCellValue(cell);


         	if(AttributeNameExcel.contains("."))
         	{
         		try
         		{
         			String retreivevalue=null;
         			if(call.equalsIgnoreCase("DTO"))
         				{retreivevalue=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].consumableTradeItem."+AttributeNameExcel));}	
         			else
         				{retreivevalue=String.valueOf(response.getBody().jsonPath().get("consumableTradeItems[0]."+AttributeNameExcel));}
         			Cell updateCell=row.getCell(updateCellColumnNo);
         		if(retreivevalue==null)
	            {updateCell.setCellValue("null"); }
	            else{ updateCell.setCellValue(retreivevalue);}
         		}
         		catch(Exception e)
         		{
         			System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
         		}
         	}
         	else{
         		if(tradeDTOObj.has(AttributeNameExcel))
         		{
         			String retreivevalue=tradeDTOObj.getString(AttributeNameExcel);
         			Cell updateCell=row.getCell(updateCellColumnNo);
             		if(retreivevalue==null)
    	            {updateCell.setCellValue("null"); }
    	            else{ updateCell.setCellValue(retreivevalue);}
         			
         		}
         		else{
         			System.out.println(AttributeNameExcel+" is missing in json response");
                	Cell updateCell=row.getCell(updateCellColumnNo);
                	updateCell.setCellValue("NA");
         		}
         	}
     
         }
	 }
	 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();
	}

	public String supplyItemDTOJson(Response response, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		String outputResponse=response.asString();
		JSONObject supplyDTOObj=new JSONObject(outputResponse).getJSONArray("tradeItemsSupplyItems").getJSONObject(0).getJSONObject("supplyItem");
		String itemNumber = supplyDTOObj.getString("id");
		if(itemNumber.contains("USWM"))
			{itemNumber=itemNumber.replaceAll("USWM", "");}
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 { 
		 Row row = rowIterator.next();
         if(row.getRowNum()==0)
         {
         	Cell cell=row.getCell(2);
	        String cellValue = dataFormatter.formatCellValue(cell);
         	System.out.println("The header is "+cellValue);
         	
         }
         else
         {

	         Cell cell=row.getCell(2);
	         String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	          if(AttributeNameExcel.contains("destinationFormatCode"))
	            {
	            	List<String> destinationFormatCode=new ArrayList<String>();
	            	JSONArray formatArray=supplyDTOObj.getJSONObject("attributes").getJSONArray("destinationFormatCode");
	            	for(int i=0;i<formatArray.length();i++)
	            	{
	            		destinationFormatCode.add(formatArray.getString(i));
	            	}
	            	
	            	String retreivevalue=String.valueOf(destinationFormatCode);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null"); }
	 	            else{updateCell.setCellValue(retreivevalue);}
	            	
	            }
	          else if(AttributeNameExcel.contains("originCountryCode"))
	            {
	            	List<String> originCountryCode=new ArrayList<String>();
	            	JSONArray formatArray=supplyDTOObj.getJSONObject("attributes").getJSONArray("originCountryCode");
	            	for(int i=0;i<formatArray.length();i++)
	            	{
	            		originCountryCode.add(formatArray.getString(i));
	            	}
	            	
	            	String retreivevalue=String.valueOf(originCountryCode);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null"); }
	 	            else{updateCell.setCellValue(retreivevalue);}
	            	
	            }
	          else if(AttributeNameExcel.contains("."))
         	 {
         		try
         		{
         		String retreivevalue=String.valueOf(response.getBody().jsonPath().get("tradeItemsSupplyItems[0].supplyItem."+AttributeNameExcel));	
         		Cell updateCell=row.getCell(updateCellColumnNo);
         		if(retreivevalue==null)
	            {updateCell.setCellValue("null"); }
	            else{ updateCell.setCellValue(retreivevalue);}
         		}
         		catch(Exception e)
         		{
         			System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
         		}
         	}
         	else{
         		if(supplyDTOObj.has(AttributeNameExcel))
         		{
         			String retreivevalue=supplyDTOObj.getString(AttributeNameExcel);
         			Cell updateCell=row.getCell(updateCellColumnNo);
             		if(retreivevalue==null)
    	            {updateCell.setCellValue("null"); }
    	            else{ updateCell.setCellValue(retreivevalue);}
         			
         		}
         		else{
         			System.out.println(AttributeNameExcel+" is missing in json response");
                	Cell updateCell=row.getCell(updateCellColumnNo);
                	updateCell.setCellValue("NA");
         		}
         	}
         
        	 
         }
	 
         
         }
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();
		return itemNumber;
		
	}
	
	public Object[] supplyItemFlatMultiJson(Response response, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		List<String> itemNo=new ArrayList<String>();
		List<String> orderableGtin=new ArrayList<String>();
		excelClearmulti(sheetName);
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		String outputResponse=response.asString();
		int items=new JSONObject(outputResponse).getJSONArray("supplyItems").length();
		for(int supplyArray=0;supplyArray<items;supplyArray++)
		{
			if(supplyArray==0)
			 {updateCellColumnNo=3;} 
			 else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
		JSONObject supplyDTOObj=new JSONObject(outputResponse).getJSONArray("supplyItems").getJSONObject(supplyArray);
		String itemNumber = supplyDTOObj.getString("id");
		if(itemNumber.contains("USWM"))
			{itemNumber=itemNumber.replaceAll("USWM", "");}
		String orderGtin=supplyDTOObj.getJSONObject("attributes").getString("orderablePackGtin");
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 { 
		 Row row = rowIterator.next();
         if(row.getRowNum()==0)
         {
         	Cell cell=row.getCell(2);
	        String cellValue = dataFormatter.formatCellValue(cell);
         	System.out.println("The header is "+cellValue);
         	
         }
         else
         {

	         Cell cell=row.getCell(2);
	         String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	          if(AttributeNameExcel.contains("destinationFormatCode"))
	            {
	            	List<String> destinationFormatCode=new ArrayList<String>();
	            	JSONArray formatArray=supplyDTOObj.getJSONObject("attributes").getJSONArray("destinationFormatCode");
	            	for(int i=0;i<formatArray.length();i++)
	            	{
	            		destinationFormatCode.add(formatArray.getString(i));
	            	}
	            	
	            	String retreivevalue=String.valueOf(destinationFormatCode);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null"); }
	 	            else{updateCell.setCellValue(retreivevalue);}
	            	
	            }
	          else if(AttributeNameExcel.contains("originCountryCode"))
	            {
	            	List<String> originCountryCode=new ArrayList<String>();
	            	JSONArray formatArray=supplyDTOObj.getJSONObject("attributes").getJSONArray("originCountryCode");
	            	for(int i=0;i<formatArray.length();i++)
	            	{
	            		originCountryCode.add(formatArray.getString(i));
	            	}
	            	
	            	String retreivevalue=String.valueOf(originCountryCode);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {updateCell.setCellValue("null"); }
	 	            else{updateCell.setCellValue(retreivevalue);}
	            	
	            }
	          else if(AttributeNameExcel.contains("."))
         	 {
         		try
         		{
         		String retreivevalue=String.valueOf(response.getBody().jsonPath().get("supplyItems["+supplyArray+"]."+AttributeNameExcel));	
         		Cell updateCell=row.getCell(updateCellColumnNo);
         		if(retreivevalue==null)
	            {updateCell.setCellValue("null"); }
	            else{ updateCell.setCellValue(retreivevalue);}
         		}
         		catch(Exception e)
         		{
         			System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
         		}
         	}
         	else{
         		if(supplyDTOObj.has(AttributeNameExcel))
         		{
         			String retreivevalue=supplyDTOObj.getString(AttributeNameExcel);
         			Cell updateCell=row.getCell(updateCellColumnNo);
             		if(retreivevalue==null)
    	            {updateCell.setCellValue("null"); }
    	            else{ updateCell.setCellValue(retreivevalue);}
         			
         		}
         		else{
         			System.out.println(AttributeNameExcel+" is missing in json response");
                	Cell updateCell=row.getCell(updateCellColumnNo);
                	updateCell.setCellValue("NA");
         		}
         	}
         
        	 
         }
	 
         
         }
	 	itemNo.add(itemNumber);
	 	orderableGtin.add(orderGtin);
		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();
		return new Object[] {itemNo,orderableGtin};
		
	}
	public List<String> oderableTradeItemJsonDTOGet(Response response, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		String outputResponse=response.asString();
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		List<String> orderGtin=new ArrayList<String> ();
		int ordetradeDTOObjArr=new JSONObject(outputResponse).getJSONArray("orderableTradeItems").length();
		for(int i=0;i<ordetradeDTOObjArr;i++)
		{
			if(i==0)
			 {updateCellColumnNo=3;} 
			 else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
		JSONObject tradeDTOObj=new JSONObject(outputResponse).getJSONArray("orderableTradeItems").getJSONObject(i);
		String gtin=tradeDTOObj.getString("id");
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
	 
	 while (rowIterator.hasNext())
	 { 
		 Row row = rowIterator.next();
         if(row.getRowNum()==0)
         {
         	Cell cell=row.getCell(2);
	            String cellValue = dataFormatter.formatCellValue(cell);
         	System.out.println("The header is "+cellValue);
         	
         }
         else{
        	 //
	         Cell cell=row.getCell(2);
	         String AttributeNameExcel = dataFormatter.formatCellValue(cell);


         	if(AttributeNameExcel.contains("."))
         	{
         		try
         		{
         			String retreivevalue=String.valueOf(response.getBody().jsonPath().get("orderableTradeItems["+i+"]."+AttributeNameExcel));	
         			
         			Cell updateCell=row.getCell(updateCellColumnNo);
         		if(retreivevalue==null)
	            {updateCell.setCellValue("null"); }
	            else{ updateCell.setCellValue(retreivevalue);}
         		}
         		catch(Exception e)
         		{
         			System.out.println(AttributeNameExcel+" is missing in json response");
	                	Cell updateCell=row.getCell(updateCellColumnNo);
	                	updateCell.setCellValue("NA");
         		}
         	}
         	else{
         		if(tradeDTOObj.has(AttributeNameExcel))
         		{
         			String retreivevalue=tradeDTOObj.getString(AttributeNameExcel);
         			Cell updateCell=row.getCell(updateCellColumnNo);
             		if(retreivevalue==null)
    	            {updateCell.setCellValue("null"); }
    	            else{ updateCell.setCellValue(retreivevalue);}
         			
         		}
         		else{
         			System.out.println(AttributeNameExcel+" is missing in json response");
                	Cell updateCell=row.getCell(updateCellColumnNo);
                	updateCell.setCellValue("NA");
         		}
         	}
     
         }
	 }
	 orderGtin.add(gtin);
	}
	 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
     workbook.write(outputStream);    
		 workbook.close();
		 outputStream.close();
		return orderGtin;
	}

	public Object[] verifyStructureTIP(Response response) throws JSONException {
		JSONObject respTIP=null,tradeProdjson=null;
		JSONArray supplyarray=null;
		JSONObject tradeitem=null,product=null;
		String outputResponse=response.asString();
		respTIP=new JSONObject(outputResponse);
		Assert.assertTrue(respTIP.has("tradeItemProduct"), "TIP structure does not have tradeItemProduct");
		Assert.assertTrue(respTIP.has("supplyItems"), "TIP structure is not having supplyItems");
		Object supp =respTIP.get("supplyItems");
		if(supp instanceof JSONArray)
		{
			int arrSize=respTIP.getJSONArray("supplyItems").length();
			if(arrSize>0)
			{supplyarray=respTIP.getJSONArray("supplyItems");}
		}
		Assert.assertTrue(respTIP.has("errorMessage"), "errorMessage is missing in the TIP structure");
		tradeProdjson=respTIP.getJSONObject("tradeItemProduct");
		Assert.assertTrue(tradeProdjson.has("tradeItem"), "tradeItem is missing in tradeItemProduct of TIP structure");
		Object ti =tradeProdjson.get("tradeItem");
		if(ti instanceof JSONObject)
		{
			tradeitem=tradeProdjson.getJSONObject("tradeItem");
		}
		Assert.assertTrue(tradeProdjson.has("product"), "product is missing in tradeItemProduct of TIP structure");
		Object pro =tradeProdjson.get("product");
		if(pro instanceof JSONObject)
		{
			product=tradeProdjson.getJSONObject("product");
		}
		Assert.assertTrue(tradeProdjson.has("totalNextLevelQuantity"), "totalNextLevelQuantity is missing in tradeItemProduct of TIP structure");
		Assert.assertTrue(tradeProdjson.has("nextLevelTradeItemProduct"), "nextLevelTradeItemProduct is missing in tradeItemProduct of TIP structure");
		return new Object[] {tradeitem,product,supplyarray};
		
	}

	public Object[] responseValidationAssortmentGetByMultipleItemNumber(
			Response response, String sheetName,String concept) throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		List<String> itemNo=new ArrayList<String>();
		List<String> gtins=new ArrayList<String>();
		int updateCellColumnNo=3;
		excelClearmulti(sheetName);
		FileOutputStream outputStream=null;
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 
		 String outputResponse=response.asString();
		 JSONArray ItemNoArray=null;
//		 JSONObject mainobj=new JSONArray(outputResponse);
		ItemNoArray=new JSONArray(outputResponse);
		 
		 for(int l=0;l<ItemNoArray.length();l++)
		 {
			 if(l==0)
			 {updateCellColumnNo=3;} 
			 else{
				 updateCellColumnNo=updateCellColumnNo+1;
				 System.out.println(">>>>>>>>>>>>>"+updateCellColumnNo);
			 }
			 
			JSONObject obj1= ItemNoArray.getJSONObject(l);
		   JSONObject jsonObj2=obj1.getJSONObject(concept);
		    String itemnumber=obj1.getJSONObject(concept).getString("number");
		    String gtin=obj1.getJSONObject(concept).getString("consumableGTIN");
		 
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
		 
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {
	            	Cell cell=row.getCell(2);
		            String cellValue = dataFormatter.formatCellValue(cell);
	            	System.out.println("The header is "+cellValue);
	            	
	            }
	            else{
	            Cell cell=row.getCell(2);
	            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
	       
	            if(jsonObj2.has(AttributeNameExcel))
	            {
//	            	System.out.println(AttributeNameExcel);
	            	String retreivevalue=jsonObj2.getString(AttributeNameExcel);
//	            	System.out.println(retreivevalue);
	            	Cell updateCell=row.getCell(updateCellColumnNo);
	            	 if(retreivevalue==null)
	 	            {
	 	            	// when the value to be enter is null
	 	            	updateCell.setCellValue("null");
	 	            }
	 	            else{ updateCell.setCellValue(retreivevalue);}

	            }
	            else{
	            		try{
	            			String retreivevalue=null;
	            			 
	            			 retreivevalue=String.valueOf(response.getBody().jsonPath().get(concept+"["+l+"]."+AttributeNameExcel));
	            			
	            			Cell updateCell=row.getCell(updateCellColumnNo);
			            	 if(retreivevalue==null)
			 	            {
			 	            	// when the value to be enter is null
			 	            	updateCell.setCellValue("null");
			 	            }
			 	            else{ updateCell.setCellValue(retreivevalue);}
	            			}
	            			catch(Exception e){
	            				System.out.println(AttributeNameExcel+" is missing in json response");
   		                	Cell updateCell=row.getCell(updateCellColumnNo);
   		                	updateCell.setCellValue("NA");
	            			}
	            		

	            	}
	           	}
		 }
		 itemNo.add(itemnumber);
		 gtins.add(gtin);
		}
		 outputStream = new FileOutputStream("target//Mapping_Sheet1.xlsx");
	        workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();
			return new Object[] {itemNo,gtins};
			
	 }
}
