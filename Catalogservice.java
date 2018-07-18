package com.walmart.pageObj;

import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.walmart.base.AuthorizationKey;
import com.walmart.base.WalmartBaseTest;
//import com.walmart.utils.AuthorizationKey;
/**
 * 
 * @author cylee1
 *
 */
public class Catalogservice extends WalmartBaseTest {
	private static final Logger LOGGER = Logger.getLogger(Catalogservice.class);	
	Response response;
	String authkey;
	 protected SoftAssert sassert = new SoftAssert();
	
	 public RequestSpecification setGETHeaders(String Accept,String key){

		 LOGGER.debug("Content-Type: "+Accept);
		 LOGGER.debug("Authorization:" +key);
	        RequestSpecification reqBuilder = new RequestSpecBuilder()
	    	.addHeader("Content-Type",Accept)
	    	.addHeader("Authorization",key)
	    	.build();
	    	return reqBuilder;    	
	    }
	 public RequestSpecification setGETkey(String key){

		 
		 LOGGER.debug("Authorization:" +key);
	        RequestSpecification reqBuilder = new RequestSpecBuilder()
	    	.addHeader("Authorization",key)
	    	.build();
	    	return reqBuilder;    	
	    }
	 
	 public Response nodeinstancesGET(String Url,String authUrl,String Method,String ExpectedResp) throws IOException, InvalidKeyException, InterruptedException
	    {
			
			int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(authUrl,Method);
//		 Authkey="Basic ZW1pLWJ1c2luZXNzOmdvRXB1S3pIdkUyUWlLUy9LVjA0Tk5EcU4weUtDRTVTMU1FSGhGcVhYM2c9";
		 
		 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
	                 .spec(setGETHeaders(prop.getProperty("Accept_Nodeinstances"), authkey))
	    	           .when().get(Url);
	  	 				response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	    }
	 
	 public Response nodeinstancesGET(String Url,String Method,String ExpectedResp) throws IOException, InvalidKeyException, InterruptedException
	    {
			
			int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(Url,Method);
//		 Authkey="Basic ZW1pLWJ1c2luZXNzOmdvRXB1S3pIdkUyUWlLUy9LVjA0Tk5EcU4weUtDRTVTMU1FSGhGcVhYM2c9";
		 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
	                 .spec(setGETHeaders(prop.getProperty("Accept_Nodeinstances"), authkey))
	    	           .when().get(Url);
	  	 				response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	  	 	
	  	 	
	    }
	 public Response nodeinstanceGetAuthurl(String Url,String Method,String ExpectedResp,String authurl) throws IOException, InvalidKeyException, InterruptedException
	    {
			
			int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(authurl,Method);
		 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
	                 .spec(setGETHeaders(prop.getProperty("Content_Nodeinstance"), authkey))
	    	           .when().get(Url);
	  	 				response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	    }
	 
	  public Response nodeinstanceGet(String Url,String Method,String ExpectedResp) throws IOException, InvalidKeyException, InterruptedException
	    {
			
			int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(Url,Method);
		 response =given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
	                 .spec(setGETHeaders(prop.getProperty("Content_Nodeinstance"), authkey))
	    	           .when().get(Url);
	  	 				response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	    }
	  public Response getWithoutHeader(String Url,String Method,String ExpectedResp) throws IOException, InvalidKeyException, InterruptedException
	    {
			
			int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(Url,Method);
		 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
	                 .spec(setGETkey(authkey))
	    	           .when().get(Url);
	  	 				response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	    }
	  
		public void tiContextvalidation(Response response,String GTIN1,String GTIN2,String Info_id1, String Info_id2,String info_type1,String info_type2,String country1,String country2) throws JSONException
		{
			String[] id_excel ={GTIN1,GTIN2};
			String[] infoProvId_excel={Info_id1,Info_id2};
			String[] informationProviderTypeCode_excel={info_type1,info_type2}; 
			String[] targetMarketCode_excel={country1,country2};
			String outputResponse = response.asString();
			JSONObject jsonobject = new JSONObject(outputResponse);
			JSONArray jsonarray = jsonobject.getJSONArray("nodeinstance");
			int m = 0;
			int[] Context_Count = new int[jsonarray.length()];
			 for (int i = 0; i < jsonarray.length(); i++) 
			    {
					JSONObject jsonobject2 = jsonarray.getJSONObject(i);
					String id = jsonobject2.getString("id");
					LOGGER.debug("Node instance id  of : " +i+" is "+id);
					Assert.assertEquals(id, id_excel[i]);
					JSONArray jsonarray1 = jsonobject2.getJSONArray("contexts");
					Context_Count[i] = jsonarray1.length();
					if(i>0)
					{
						m = Context_Count[i]-1;
					}
					for(int l=0;l<jsonarray1.length();l++)
					{
						JSONObject jsonobject3 = jsonarray1.getJSONObject(l);
						JSONObject jsonobject_context = jsonobject3.getJSONObject("contextParameters");
						String infoProvId = jsonobject_context.getString("informationProviderId");
						LOGGER.debug("informationProviderId of nodeinstances " +i+" and the context parameter "+ l +" is : "+infoProvId);
						Assert.assertEquals(infoProvId, infoProvId_excel[m+l]);
						String informationProviderTypeCode = jsonobject_context.getString("informationProviderTypeCode");
						LOGGER.debug("informationProviderTypeCode of nodeinstances " +i+" and the context parameter "+ l +" is : "+informationProviderTypeCode);
						Assert.assertEquals(informationProviderTypeCode, informationProviderTypeCode_excel[m+l]);
						String targetMarketCode = jsonobject_context.getString("targetMarketCode");
						LOGGER.debug("targetMarketCode of nodeinstances " +i+" and the context parameter "+ l +" is : "+targetMarketCode);
						Assert.assertEquals(targetMarketCode, targetMarketCode_excel[m+l]);
					}
			     }
		}
		
		public void attributeValidation(Response response, List<String> CassandraAttribute, List<String> CassandraValue,String Context_cass) throws JSONException{
			
			String[] CassandraAttributeArray = CassandraAttribute.toArray(new String[CassandraAttribute.size()]);
			String[] CassandraValueArray = CassandraValue.toArray(new String[CassandraValue.size()]);
			
			String outputResponse = response.asString();
			JSONObject jsonobject = new JSONObject(outputResponse);
			String id = jsonobject.getString("id");
			JSONArray jsonarrayContexts = jsonobject.getJSONArray("contexts");
			
			
			
			 for (int i = 0; i < jsonarrayContexts.length(); i++) 
			    {
					JSONObject jsonobject2 = jsonarrayContexts.getJSONObject(i);
					//context values	
					String informationProviderId=null,informationProviderTypeCode=null,languageCode=null,recipientGln=null,
							targetMarketCode=null;	
					JSONObject jsonObjectcontextParameters=jsonobject2.getJSONObject("contextParameters");
					if(jsonObjectcontextParameters.has("informationProviderId"))
					{
					 informationProviderId=jsonObjectcontextParameters.getString("informationProviderId");
					}
					if(jsonObjectcontextParameters.has("informationProviderTypeCode"))
					{
					informationProviderTypeCode=jsonObjectcontextParameters.getString("informationProviderTypeCode");}
					if(jsonObjectcontextParameters.has("languageCode"))
					{
					 languageCode=jsonObjectcontextParameters.getString("languageCode");}
					if(jsonObjectcontextParameters.has("recipientGln"))
					{
					 recipientGln=jsonObjectcontextParameters.getString("recipientGln");}
					if(jsonObjectcontextParameters.has("targetMarketCode"))
					{
					 targetMarketCode=jsonObjectcontextParameters.getString("targetMarketCode");}
					String Context_resp = "informationProviderId:"+informationProviderId+",informationProviderTypeCode:"+informationProviderTypeCode+",languageCode:"+
							languageCode+",recipientGln:"+recipientGln+",targetMarketCode:US:"+targetMarketCode;
					JSONObject jsonObjectAttri=jsonobject2.getJSONObject("attributes");
					//attribute values validation
//					informationProviderId:112557,informationProviderTypeCode:SUPPLIER_NUMBER,languageCode:en,
//					recipientGln:0078742000008,targetMarketCode:US
					if(Context_cass.equalsIgnoreCase(Context_resp)){
					if(jsonObjectAttri.has("isConsumableInd")){
					String ConsumableInd=jsonObjectAttri.getString("isConsumableInd");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("isConsumableInd")){
						LOGGER.debug("Validation of isConsumableInd attribute");
						Reporter.log("Validation of isConsumableInd attribute");
					sassert.assertEquals(CassandraValueArray[l], ConsumableInd,"isConsumableInd cassandra-->"+CassandraValueArray[l]+"json response-->"+ConsumableInd);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute isConsumableInd is not available in the json response of GET service");
				Reporter.log("The attribute isConsumableInd is not available in the json response of GET service");}
					//tradeItemDiscontinuedDate is not available for all the elements
					if(jsonObjectAttri.has("tradeItemDiscontinuedDate")){
					String tradeItemDiscontinuedDate=jsonObjectAttri.getString("tradeItemDiscontinuedDate");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemDiscontinuedDate")){
						LOGGER.debug("Validation of tradeItemDiscontinuedDate attribute");
						Reporter.log("Validation of tradeItemDiscontinuedDate attribute");
						String CassandraValueAltered=CassandraValueArray[l].replaceAll("T.*", "");
					sassert.assertEquals(CassandraValueAltered, tradeItemDiscontinuedDate,"tradeItemDiscontinuedDate cassandra-->"+CassandraValueAltered+"json response-->"+tradeItemDiscontinuedDate);
					break;}
					}
					 }
					else{LOGGER.debug("The attribute tradeItemDiscontinuedDate is not available in the json response of GET service");
					Reporter.log("The attribute tradeItemDiscontinuedDate is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemDescription")){
					String tradeItemDescription=jsonObjectAttri.getString("tradeItemDescription");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemDescription")){
						LOGGER.debug("Validation of tradeItemDescription attribute");
						Reporter.log("Validation of tradeItemDescription attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemDescription,"tradeItemDescription cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemDescription);
					break;}
					}
					 }
					else{LOGGER.debug("The attribute tradeItemDescription is not available in the json response of GET service");
					Reporter.log("The attribute tradeItemDescription is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("batteryTypeCode")){
					String batteryTypeCode=jsonObjectAttri.getString("batteryTypeCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("batteryTypeCode")){
						LOGGER.debug("Validation of batteryTypeCode attribute");
						Reporter.log("Validation of batteryTypeCode attribute");
					sassert.assertEquals(CassandraValueArray[l], batteryTypeCode,"batteryTypeCode cassandra-->"+CassandraValueArray[l]+"json response-->"+batteryTypeCode);
					break;}
					}
			    }
			    else{LOGGER.debug("The attribute batteryTypeCode is not available in the json response of GET service");
				Reporter.log("The attribute batteryTypeCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("crushFactorCode"))
					{
					String crushFactorCode=jsonObjectAttri.getString("crushFactorCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("crushFactorCode"))
					{
						LOGGER.debug("Validation of crushFactorCode attribute");
						Reporter.log("Validation of crushFactorCode attribute");
					sassert.assertEquals(CassandraValueArray[l], crushFactorCode,"crushFactorCode cassandra-->"+CassandraValueArray[l]+"json response-->"+crushFactorCode);
					break;					}
					}
					} 
					else{LOGGER.debug("The attribute crushFactorCode is not available in the json response of GET service");
					Reporter.log("The attribute crushFactorCode is not available in the json response of GET service");}
					
			 
					if(jsonObjectAttri.has("supplierFirstShippableDate")){
					String supplierFirstShippableDate=jsonObjectAttri.getString("supplierFirstShippableDate");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("supplierFirstShippableDate")){
						LOGGER.debug("Validation of supplierFirstShippableDate attribute");
						Reporter.log("Validation of supplierFirstShippableDate attribute");
						String CassandraValueAltered=CassandraValueArray[l].replaceAll("T.*", "");
					sassert.assertEquals(CassandraValueAltered, supplierFirstShippableDate,"supplierFirstShippableDate cassandra-->"+CassandraValueAltered+"json response-->"+supplierFirstShippableDate);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute supplierFirstShippableDate is not available in the json response of GET service");
				Reporter.log("The attribute supplierFirstShippableDate is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("lifecycleStateCode")){
					String lifecycleStateCode=jsonObjectAttri.getString("lifecycleStateCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("lifecycleStateCode")){
						LOGGER.debug("Validation of lifecycleStateCode attribute");
						Reporter.log("Validation of lifecycleStateCode attribute");
					sassert.assertEquals(CassandraValueArray[l], lifecycleStateCode,"lifecycleStateCode cassandra-->"+CassandraValueArray[l]+"json response-->"+lifecycleStateCode);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute lifecycleStateCode is not available in the json response of GET service");
				Reporter.log("The attribute lifecycleStateCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemNetWeight")){
					JSONArray traditemNetArray=jsonObjectAttri.getJSONArray("tradeItemNetWeight");
					String tradeItemNetWeightQty[]=new String[traditemNetArray.length()];
					String tradeItemNetWeightUomCode[]=new String[traditemNetArray.length()];
					for (int k = 0; k < traditemNetArray.length(); k++) 
				    {
					JSONObject jsonobject3 = traditemNetArray.getJSONObject(k);
					tradeItemNetWeightQty[k]=jsonobject3.getString("tradeItemNetWeightQty");
					tradeItemNetWeightUomCode[k]=jsonobject3.getString("tradeItemNetWeightUomCode");
				    }
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemNetWeightQty")){
						LOGGER.debug("Validation of tradeItemNetWeightQty attribute");
						Reporter.log("Validation of tradeItemNetWeightQty attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemNetWeightQty[0],"tradeItemNetWeightQty cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemNetWeightQty[0]);
					break;}
					}
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemNetWeightUomCode")){
						LOGGER.debug("Validation of tradeItemNetWeightUomCode attribute");
						Reporter.log("Validation of tradeItemNetWeightUomCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemNetWeightUomCode[0],"tradeItemNetWeightUomCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemNetWeightUomCode[0]);
					break;}
					}
			    }
				else{LOGGER.debug("The attributes of tradeItemNetWeight are not available in the json response of GET service");
				Reporter.log("The attributes of tradeItemNetWeight are not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("isMasterCartonInd")){
					String isMasterCartonInd=jsonObjectAttri.getString("isMasterCartonInd");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("isMasterCartonInd")){
						LOGGER.debug("Validation of isMasterCartonInd attribute");
						Reporter.log("Validation of isMasterCartonInd attribute");
					sassert.assertEquals(CassandraValueArray[l], isMasterCartonInd,"isMasterCartonInd cassandra-->"+CassandraValueArray[l]+"json response-->"+isMasterCartonInd);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute isMasterCartonInd is not available in the json response of GET service");
				Reporter.log("The attribute isMasterCartonInd is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemWeight")){
					JSONArray traditemWeightArray=jsonObjectAttri.getJSONArray("tradeItemWeight");
					String tradeItemWeightUomCode[]=new String[traditemWeightArray.length()];
					String tradeItemWeightQty[]=new String[traditemWeightArray.length()];
					for (int k = 0; k < traditemWeightArray.length(); k++) 
				    {
					JSONObject jsonobject4 = traditemWeightArray.getJSONObject(k);
					// have to add if condition for inner attributes of array elements also.
					if(jsonobject4.has("tradeItemWeightUomCode")){
					tradeItemWeightUomCode[k]=jsonobject4.getString("tradeItemWeightUomCode");
				    }
					else{LOGGER.debug("The attribute tradeItemWeightUomCode is not found in json response of GET service ");
					Reporter.log("The attribute tradeItemWeightUomCode is not found in json response of GET service ");}
					if(jsonobject4.has("tradeItemWeightQty")){
					tradeItemWeightQty[k]=jsonobject4.getString("tradeItemWeightQty");
				    }
					else{LOGGER.debug("The attribute tradeItemWeightUomCode is not found in json response of GET service ");
					Reporter.log("The attribute tradeItemWeightUomCode is not found in json response of GET service ");}
				    }
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemWeightUomCode")){
						LOGGER.debug("Validation of tradeItemWeightUomCode attribute");
						Reporter.log("Validation of tradeItemWeightUomCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemWeightUomCode[0],"tradeItemWeightUomCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemWeightUomCode[0]);
					break;}
					}
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemWeightQty")){
						LOGGER.debug("Validation of tradeItemWeightQty attribute");
						Reporter.log("Validation of tradeItemWeightQty attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemWeightQty[0],"tradeItemWeightQty cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemWeightQty);
					break;}
					}
			    }
				else{LOGGER.debug("The attributes of tradeItemWeight are not available in the json response of GET service");
				Reporter.log("The attributes of tradeItemWeight are not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("ownerGln")){
					String ownerGln=jsonObjectAttri.getString("ownerGln");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("ownerGln")){
						LOGGER.debug("Validation of ownerGln attribute");
						Reporter.log("Validation of ownerGln attribute");
					sassert.assertEquals(CassandraValueArray[l], ownerGln,"ownerGln cassandra-->"+CassandraValueArray[l]+"json response-->"+ownerGln);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute ownerGln is not available in the json response of GET service");
				Reporter.log("The attribute ownerGln is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("supplierLastShippableDate")){
					String supplierLastShippableDate=jsonObjectAttri.getString("supplierLastShippableDate");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("supplierLastShippableDate")){
						LOGGER.debug("Validation of supplierLastShippableDate attribute");
						Reporter.log("Validation of supplierLastShippableDate attribute");
						String CassandraValueAltered=CassandraValueArray[l].replaceAll("T.*", "");
					sassert.assertEquals(CassandraValueAltered, supplierLastShippableDate,"supplierLastShippableDate cassandra-->"+CassandraValueAltered+"json response-->"+supplierLastShippableDate);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute supplierLastShippableDate is not available in the json response of GET service");
				Reporter.log("The attribute supplierLastShippableDate is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("supplierMinimumOrder"))
					{
					JSONArray supplierMinimumOrderArray=jsonObjectAttri.getJSONArray("supplierMinimumOrder");
					String supplierMinimumOrderQty[]=new String[supplierMinimumOrderArray.length()];
					for (int k = 0; k < supplierMinimumOrderArray.length(); k++) 
				    {
					JSONObject jsonobject5 = supplierMinimumOrderArray.getJSONObject(k);
					if(jsonobject5.has("supplierMinimumOrderQty"))
					{
					supplierMinimumOrderQty[k]=jsonobject5.getString("supplierMinimumOrderQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
						if(CassandraAttributeArray[l].equalsIgnoreCase("supplierMinimumOrderQty")){
							LOGGER.debug("Validation of supplierMinimumOrderQty attribute");
							Reporter.log("Validation of supplierMinimumOrderQty attribute");
						sassert.assertEquals(CassandraValueArray[l], supplierMinimumOrderQty[0],"supplierMinimumOrderQty cassandra-->"+CassandraValueArray[l]+"json response-->"+supplierMinimumOrderQty[0]);
						break;}
						}
					}
				    				
					else{LOGGER.debug("The attribute supplierMinimumOrderQty is not found in json response of GET service ");
					Reporter.log("The attribute supplierMinimumOrderQty is not found in json response of GET service ");}
					}
				    }			    
				else{LOGGER.debug("The attributes of supplierMinimumOrder are not available in the json response of GET service");
				Reporter.log("The attributes of supplierMinimumOrder are not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("palletTiQty")){
					String palletTiQty=jsonObjectAttri.getString("palletTiQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("palletTiQty")){
						LOGGER.debug("Validation of palletTiQty attribute");
						Reporter.log("Validation of palletTiQty attribute");
					sassert.assertEquals(CassandraValueArray[l], palletTiQty,"palletTiQty cassandra-->"+CassandraValueArray[l]+"json response-->"+palletTiQty);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute palletTiQty is not available in the json response of GET service");
				Reporter.log("The attribute palletTiQty is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemPackTypeCode")){
					String tradeItemPackTypeCode=jsonObjectAttri.getString("tradeItemPackTypeCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemPackTypeCode")){
						LOGGER.debug("Validation of tradeItemPackTypeCode attribute");
						Reporter.log("Validation of tradeItemPackTypeCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemPackTypeCode,"tradeItemPackTypeCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemPackTypeCode);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute tradeItemPackTypeCode is not available in the json response of GET service");
				Reporter.log("The attribute tradeItemPackTypeCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("palletHiQty")){
					String palletHiQty=jsonObjectAttri.getString("palletHiQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("palletHiQty")){
						LOGGER.debug("Validation of palletHiQty attribute");
						Reporter.log("Validation of palletHiQty attribute");
					sassert.assertEquals(CassandraValueArray[l], palletHiQty,"palletHiQty cassandra-->"+CassandraValueArray[l]+"json response-->"+palletHiQty);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute palletHiQty is not available in the json response of GET service");
				Reporter.log("The attribute palletHiQty is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("supplierFirstOrderableDate")){
					String supplierFirstOrderableDate=jsonObjectAttri.getString("supplierFirstOrderableDate");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("supplierFirstOrderableDate")){
						LOGGER.debug("Validation of supplierFirstOrderableDate attribute");
						Reporter.log("Validation of supplierFirstOrderableDate attribute");
						String CassandraValueAltered=CassandraValueArray[l].replaceAll("T.*", "");
					sassert.assertEquals(CassandraValueAltered, supplierFirstOrderableDate,"supplierFirstOrderableDate cassandra-->"+CassandraValueAltered+"json response-->"+supplierFirstOrderableDate);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute supplierFirstOrderableDate is not available in the json response of GET service");
				Reporter.log("The attribute supplierFirstOrderableDate is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemGtin")){
					String tradeItemGtin=jsonObjectAttri.getString("tradeItemGtin");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemGtin")){
						LOGGER.debug("Validation of tradeItemGtin attribute");
						Reporter.log("Validation of tradeItemGtin attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemGtin,"tradeItemGtin cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemGtin);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute tradeItemGtin is not available in the json response of GET service");
				Reporter.log("The attribute tradeItemGtin is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("isConveyableInd")){
					String isConveyableInd=jsonObjectAttri.getString("isConveyableInd");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("isConveyableInd")){
						LOGGER.debug("Validation of isConveyableInd attribute");
						Reporter.log("Validation of isConveyableInd attribute");
					sassert.assertEquals(CassandraValueArray[l], isConveyableInd,"isConveyableInd cassandra-->"+CassandraValueArray[l]+"json response-->"+isConveyableInd);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute isConveyableInd is not available in the json response of GET service");
				Reporter.log("The attribute isConveyableInd is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("orderSizingFactor"))
					{
					JSONArray orderSizingFactorArray=jsonObjectAttri.getJSONArray("orderSizingFactor");
					String orderSizingFactorQty[]=new String[orderSizingFactorArray.length()];
					for (int k = 0; k < orderSizingFactorArray.length(); k++) 
				    {
					JSONObject jsonobject6 = orderSizingFactorArray.getJSONObject(k);
					if(jsonobject6.has("orderSizingFactorQty"))
					{
					orderSizingFactorQty[k]=jsonobject6.getString("orderSizingFactorQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("orderSizingFactorQty")){
						LOGGER.debug("Validation of orderSizingFactorQty attribute");
						Reporter.log("Validation of orderSizingFactorQty attribute");
					sassert.assertEquals(CassandraValueArray[l], orderSizingFactorQty[0],"orderSizingFactor cassandra-->"+CassandraValueArray[l]+"json response-->"+orderSizingFactorQty[0]);
					break;}
					}
					}
					
					else{LOGGER.debug("The attribute of  orderSizingFactor->orderSizingFactorQty  is not available in the json response of GET service");
					Reporter.log("The attribute of orderSizingFactor--> orderSizingFactorQty is not available in the json response of GET service");}
				    }	
			    }
				else{LOGGER.debug("The attribute of  orderSizingFactor is not available in the json response of GET service");
				Reporter.log("The attribute of orderSizingFactor is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("isOrderableInd")){
					String isOrderableInd=jsonObjectAttri.getString("isOrderableInd");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("isOrderableInd")){
						LOGGER.debug("Validation of isOrderableInd attribute");
						Reporter.log("Validation of isOrderableInd attribute");
					sassert.assertEquals(CassandraValueArray[l], isOrderableInd,"isOrderableInd cassandra-->"+CassandraValueArray[l]+"json response-->"+isOrderableInd);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute isOrderableInd is not available in the json response of GET service");
				Reporter.log("The attribute isOrderableInd is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("informationProviderGln")){
					String informationProviderGln=jsonObjectAttri.getString("informationProviderGln");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("informationProviderGln")){
						LOGGER.debug("Validation of informationProviderGln attribute");
						Reporter.log("Validation of informationProviderGln attribute");
					sassert.assertEquals(CassandraValueArray[l], informationProviderGln,"informationProviderGln cassandra-->"+CassandraValueArray[l]+"json response-->"+informationProviderGln);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute informationProviderGln is not available in the json response of GET service");
				Reporter.log("The attribute informationProviderGln is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemDimensions")){
					
					JSONArray tradeItemDimensionsArray=jsonObjectAttri.getJSONArray("tradeItemDimensions");
					String tradeItemDimensionsUomCode[]=new String[tradeItemDimensionsArray.length()];
					String tradeItemDimensionsWidthQty[]=new String[tradeItemDimensionsArray.length()];
					String tradeItemDimensionsDepthQty[]=new String[tradeItemDimensionsArray.length()];
					String tradeItemDimensionsHeightQty[]=new String[tradeItemDimensionsArray.length()];
					for (int k = 0; k < tradeItemDimensionsArray.length(); k++) 
				    {
					JSONObject jsonobject7 = tradeItemDimensionsArray.getJSONObject(k);
					if(jsonobject7.has("tradeItemDimensionsUomCode")){
					tradeItemDimensionsUomCode[k]=jsonobject7.getString("tradeItemDimensionsUomCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemDimensionsUomCode")){
						LOGGER.debug("Validation of tradeItemDimensionsUomCode attribute");
						Reporter.log("Validation of tradeItemDimensionsUomCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemDimensionsUomCode[0],"tradeItemDimensionsUomCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemDimensionsUomCode[0]);
					break;}
					}
					}
					else{LOGGER.debug("The attribute tradeItemDimensionsUomCode of tradeItemDimensions are not available in the json response of GET service");
					Reporter.log("The attribute tradeItemDimensionsUomCode of tradeItemDimensions are not available in the json response of GET service");}
					
					if(jsonobject7.has("tradeItemDimensionsWidthQty")){
					tradeItemDimensionsWidthQty[k]=jsonobject7.getString("tradeItemDimensionsWidthQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemDimensionsWidthQty")){
						LOGGER.debug("Validation of tradeItemDimensionsWidthQty attribute");
						Reporter.log("Validation of tradeItemDimensionsWidthQty attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemDimensionsWidthQty[0],"tradeItemDimensionsWidthQty cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemDimensionsWidthQty[0]);
					break;}
					}
					}
					else{LOGGER.debug("The attribute tradeItemDimensionsWidthQty of tradeItemDimensions are not available in the json response of GET service");
					Reporter.log("The attribute tradeItemDimensionsWidthQty of tradeItemDimensions are not available in the json response of GET service");}
					
					if(jsonobject7.has("tradeItemDimensionsDepthQty")){
					tradeItemDimensionsDepthQty[k]=jsonobject7.getString("tradeItemDimensionsDepthQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemDimensionsDepthQty")){
						LOGGER.debug("Validation of tradeItemDimensionsDepthQty attribute");
						Reporter.log("Validation of tradeItemDimensionsDepthQty attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemDimensionsDepthQty[0],"tradeItemDimensionsDepthQty cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemDimensionsDepthQty[0]);
					break;}
					}
					}
					else{LOGGER.debug("The attribute tradeItemDimensionsDepthQty of tradeItemDimensions are not available in the json response of GET service");
					Reporter.log("The attribute tradeItemDimensionsDepthQty of tradeItemDimensions are not available in the json response of GET service");}
					
					if(jsonobject7.has("tradeItemDimensionsHeightQty")){
					tradeItemDimensionsHeightQty[k]=jsonobject7.getString("tradeItemDimensionsHeightQty");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemDimensionsHeightQty"))
					{
						LOGGER.debug("Validation of tradeItemDimensionsHeightQty attribute");
						Reporter.log("Validation of tradeItemDimensionsHeightQty attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemDimensionsHeightQty[0],"tradeItemDimensionsHeightQty cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemDimensionsHeightQty[0]);
					break;}
					}
					}
					else{LOGGER.debug("The attribute tradeItemDimensionsHeightQty of tradeItemDimensions are not available in the json response of GET service");
					Reporter.log("The attribute tradeItemDimensionsHeightQty of tradeItemDimensions are not available in the json response of GET service");}
					
					}
			    

			    }
				else{LOGGER.debug("The attributes of tradeItemDimensions are not available in the json response of GET service");
				Reporter.log("The attributes of tradeItemDimensions are not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemWeightFormatTypeCode")){
					String tradeItemWeightFormatTypeCode=jsonObjectAttri.getString("tradeItemWeightFormatTypeCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemWeightFormatTypeCode")){
						LOGGER.debug("Validation of tradeItemWeightFormatTypeCode attribute");
						Reporter.log("Validation of tradeItemWeightFormatTypeCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemWeightFormatTypeCode,"tradeItemWeightFormatTypeCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemWeightFormatTypeCode);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute tradeItemWeightFormatTypeCode is not available in the json response of GET service");
				Reporter.log("The attribute tradeItemWeightFormatTypeCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("GTIN")){
					String GTIN=jsonObjectAttri.getString("GTIN");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("GTIN")){
						LOGGER.debug("Validation of GTIN attribute");
						Reporter.log("Validation of GTIN attribute");
					sassert.assertEquals(CassandraValueArray[l], GTIN,"GTIN cassandra-->"+CassandraValueArray[l]+"json response-->"+GTIN);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute GTIN is not available in the json response of GET service");
				Reporter.log("The attribute GTIN is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("compositeWoodCertificationCode")){
					String compositeWoodCertificationCode=jsonObjectAttri.getString("compositeWoodCertificationCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("compositeWoodCertificationCode")){
						LOGGER.debug("Validation of compositeWoodCertificationCode attribute");
						Reporter.log("Validation of compositeWoodCertificationCode attribute");
					sassert.assertEquals(CassandraValueArray[l], compositeWoodCertificationCode,"compositeWoodCertificationCode cassandra-->"+CassandraValueArray[l]+"json response-->"+compositeWoodCertificationCode);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute compositeWoodCertificationCode is not available in the json response of GET service");
				Reporter.log("The attribute compositeWoodCertificationCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("consumerGtin")){
					String consumerGtin=jsonObjectAttri.getString("consumerGtin");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("consumerGtin")){
						LOGGER.debug("Validation of consumerGtin attribute");
						Reporter.log("Validation of consumerGtin attribute");
					sassert.assertEquals(CassandraValueArray[l], consumerGtin,"consumerGtin cassandra-->"+CassandraValueArray[l]+"json response-->"+consumerGtin);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute consumerGtin is not available in the json response of GET service");
				Reporter.log("The attribute consumerGtin is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("gpcBrickNbr")){
					String gpcBrickNbr=jsonObjectAttri.getString("gpcBrickNbr");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("gpcBrickNbr")){
						LOGGER.debug("Validation of gpcBrickNbr attribute");
						Reporter.log("Validation of gpcBrickNbr attribute");
					sassert.assertEquals(CassandraValueArray[l], gpcBrickNbr,"consumerGtin cassandra-->"+CassandraValueArray[l]+"json response-->"+gpcBrickNbr);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute gpcBrickNbr is not available in the json response of GET service");
				Reporter.log("The attribute gpcBrickNbr is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("supplierFirstAvailableDate")){
					String supplierFirstAvailableDate=jsonObjectAttri.getString("supplierFirstAvailableDate");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("supplierFirstAvailableDate")){
						LOGGER.debug("Validation of supplierFirstAvailableDate attribute");
						Reporter.log("Validation of supplierFirstAvailableDate attribute");
						String CassandraValueAltered=CassandraValueArray[l].replaceAll("T.*", "");
					sassert.assertEquals(CassandraValueAltered, supplierFirstAvailableDate,"supplierFirstAvailableDate cassandra-->"+CassandraValueAltered+"json response-->"+supplierFirstAvailableDate);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute supplierFirstAvailableDate is not available in the json response of GET service");
				Reporter.log("The attribute supplierFirstAvailableDate is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("consumerGtinFormatCode")){
					String consumerGtinFormatCode=jsonObjectAttri.getString("consumerGtinFormatCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("consumerGtinFormatCode")){
						LOGGER.debug("Validation of consumerGtinFormatCode attribute");
						Reporter.log("Validation of consumerGtinFormatCode attribute");
					sassert.assertEquals(CassandraValueArray[l], consumerGtinFormatCode,"consumerGtinFormatCode cassandra-->"+CassandraValueArray[l]+"json response-->"+consumerGtinFormatCode);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute consumerGtinFormatCode is not available in the json response of GET service");
				Reporter.log("The attribute consumerGtinFormatCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemCube")){
					JSONArray tradeItemCubeArray=jsonObjectAttri.getJSONArray("tradeItemCube");
					String tradeItemCubeQty[]=new String[tradeItemCubeArray.length()];
					String tradeItemCubeUomCode[]=new String[tradeItemCubeArray.length()];
					for (int k = 0; k < tradeItemCubeArray.length(); k++) 
				    {
					JSONObject jsonobject8 = tradeItemCubeArray.getJSONObject(k);
					tradeItemCubeQty[k]=jsonobject8.getString("tradeItemCubeQty");
					tradeItemCubeUomCode[k]=jsonobject8.getString("tradeItemCubeUomCode");
					}
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemCubeQty")){
						LOGGER.debug("Validation of tradeItemCubeQty attribute");
						Reporter.log("Validation of tradeItemCubeQty attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemCubeQty[0],"tradeItemCubeQty cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemCubeQty[0]);
					break;}
					}
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemCubeUomCode")){
						LOGGER.debug("Validation of tradeItemCubeUomCode attribute");
						Reporter.log("Validation of tradeItemCubeUomCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemCubeUomCode[0],"tradeItemCubeUomCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemCubeUomCode[0]);
					break;}
					}
			    }
				else{LOGGER.debug("The attributes of tradeItemCube are not available in the json response of GET service");
				Reporter.log("The attributes of tradeItemCube are not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("msrpAmt")){
					String msrpAmt=jsonObjectAttri.getString("msrpAmt");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("msrpAmt")){
						LOGGER.debug("Validation of msrpAmt attribute");
						Reporter.log("Validation of msrpAmt attribute");
					sassert.assertEquals(CassandraValueArray[l], msrpAmt,"msrpAmt cassandra-->"+CassandraValueArray[l]+"json response-->"+msrpAmt);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute msrpAmt is not available in the json response of GET service");
				Reporter.log("The attribute msrpAmt is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("palletSizeTypeCode")){
					String palletSizeTypeCode=jsonObjectAttri.getString("palletSizeTypeCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("palletSizeTypeCode")){
						LOGGER.debug("Validation of palletSizeTypeCode attribute");
						Reporter.log("Validation of palletSizeTypeCode attribute");
					sassert.assertEquals(CassandraValueArray[l], palletSizeTypeCode," palletSizeTypeCode cassandra-->"+CassandraValueArray[l]+"json response-->"+palletSizeTypeCode);
					break;}
					}
			    }
				else{LOGGER.debug("The attribute palletSizeTypeCode is not available in the json response of GET service");
				Reporter.log("The attribute palletSizeTypeCode is not available in the json response of GET service");}
					
					if(jsonObjectAttri.has("tradeItemGtinFormatCode")){
					String tradeItemGtinFormatCode=jsonObjectAttri.getString("tradeItemGtinFormatCode");
					for(int l=0;l<CassandraAttributeArray.length;l++)
					{
					if(CassandraAttributeArray[l].equalsIgnoreCase("tradeItemGtinFormatCode")){
						LOGGER.debug("Validation of tradeItemGtinFormatCode attribute");
						Reporter.log("Validation of tradeItemGtinFormatCode attribute");
					sassert.assertEquals(CassandraValueArray[l], tradeItemGtinFormatCode,"tradeItemGtinFormatCode cassandra-->"+CassandraValueArray[l]+"json response-->"+tradeItemGtinFormatCode);
					break;}
					}
			    }
					else{LOGGER.debug("The attribute tradeItemGtinFormatCode is not available in the json response of GET service");
					Reporter.log("The attribute tradeItemGtinFormatCode is not available in the json response of GET service");}
					
				
					}
					 sassert.assertAll();
			    }
			
					
			 }

		public Response tiUpdate(String Url,String Method,String ExpectedResp,String replaceGtinPayload) throws IOException, InvalidKeyException, InterruptedException
	    {
			
	     int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(Url,Method);

		 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                 .spec(setGETHeaders(prop.getProperty("Content_Nodeinstance"), authkey))
                 .body(replaceGtinPayload)
    	           .when().put(Url);
  	       response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	    }
				
		public void responseValidationTiUpdate(Response response_update,String GTIN,String conveyable_value,String consumerItemNumber) throws JSONException{
			String outputResponse_update = response_update.asString();
			JSONObject jsonobject = new JSONObject(outputResponse_update);
			String id = jsonobject.getString("id");
			Assert.assertEquals(id, GTIN);
			JSONArray jsonarrayContexts = jsonobject.getJSONArray("contexts");
// will take the particular context which we provided in the payload
			/* "contextParameters": {
        "informationProviderId": "9238",
        "informationProviderTypeCode": "SUPPLIER_NUMBER",
        "languageCode": "en",
        "targetMarketCode": "US" */
			 for (int i = 0; i < jsonarrayContexts.length(); i++) 
			    {
					JSONObject jsonobject2 = jsonarrayContexts.getJSONObject(i);
					JSONObject jsonObjectAttriCont=jsonobject2.getJSONObject("contextParameters");
				if(jsonObjectAttriCont.has("informationProviderId")&jsonObjectAttriCont.has("9238")&jsonObjectAttriCont.has("informationProviderTypeCode")&jsonObjectAttriCont.has("SUPPLIER_NUMBER"))
				{
						if(jsonObjectAttriCont.getString("informationProviderId").equalsIgnoreCase("9238")&jsonObjectAttriCont.getString("informationProviderTypeCode").equalsIgnoreCase("SUPPLIER_NUMBER")&jsonObjectAttriCont.getString("recipientGln").equalsIgnoreCase("0078742000008"))
						{
					
					JSONObject jsonObjectAttri=jsonobject2.getJSONObject("attributes");	
					
					
					
					if(consumerItemNumber.equalsIgnoreCase("null"))
					{
						Reporter.log("Provided consumerItemNbr as null, so it should not be available in the response.");
						LOGGER.debug("Provided consumerItemNbr as null, so it should not be available in the response.");
						Assert.assertFalse(jsonObjectAttri.has("consumerItemNbr"));
					}
					else{
						String Consumer_itemNbr_res=jsonObjectAttri.getString("consumerItemNbr");
						Reporter.log("Consumer_itemNbr_res attribute is available in the response.");
						LOGGER.debug("Consumer_itemNbr_res attribute is available in the response.");
						Assert.assertEquals(Consumer_itemNbr_res, consumerItemNumber);
					}
					
					if(conveyable_value.equalsIgnoreCase("null"))
					{
					Reporter.log("Provided conveyable_value as null, so it should not be available in the response.");
					LOGGER.debug("Provided conveyable_value as null, so it should not be available in the response.");
					Assert.assertFalse(jsonObjectAttri.has("isConveyableInd"));
					}
					else{
						String Conveyable_Ind_Resp=jsonObjectAttri.getString("isConveyableInd");
						Reporter.log("isConveyable attribute is available in the response.");
						LOGGER.debug("isConveyable attribute is available in the response.");
						Assert.assertEquals(Conveyable_Ind_Resp, conveyable_value);
					}
			    }//infoid
				}
		}//for loop
	 }
		
		public Response siCreate(String Url,String ExpectedResp,String replaceGtinPayload) throws IOException, InvalidKeyException, InterruptedException
	    {
			
	     int StatusCode=Integer.parseInt(ExpectedResp);
		 authkey=AuthorizationKey.authKey(Url,"POST");

		 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                 .spec(setGETHeaders(prop.getProperty("Accept_Nodeinstances"), authkey))
                 .body(replaceGtinPayload)
    	           .when().post(Url);
  	       response.then().assertThat().statusCode(StatusCode);	
	  	 	return response;
	    }
		
		public Response siDelete(String UrlDelete,String ExpectedRespDelete) throws InvalidKeyException, InterruptedException{
			int StatusCode=Integer.parseInt(ExpectedRespDelete);
			 authkey=AuthorizationKey.authKey(UrlDelete,"DELETE");
		 	 response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
		                .spec(setGETHeaders(prop.getProperty("Content_Nodeinstance"), authkey))
		                 .when().delete(UrlDelete);
		 	 				response.then().assertThat().statusCode(StatusCode);		
		 	 Thread.sleep(6000);
			return response;
		}
		
		public List<String> siAttributeValidation(Response response) throws JSONException{
            String outputResponse = response.asString();
            String id=null,hierarchyNode=null,sellTotalContentQty=null,sellTotalContentUomCode=null,isReplenishableInd=null,supplierNbr=null
             ,warehouseMinOrderQty=null,replacedItemNbr=null,isCorporateReplenishableInd=null,lastUpdatePgmId = null,warehousePackSellAmt = null,isCannedOrderInd = null,isEcommerceReplenishableInd=null
             ,itemNbr=null,lifecycleStateCode=null,supplierLeadTimeUomCode=null,recipientGln=null,shelfLabel2Description=null,
             warehousePackCostAmt=null,deptNbr=null,lastUpdateTimestamp=null,orderablePackTypeCode=null,buyingRegionCode=null,isCancelWhenOutInd=null,lastUpdateTs=null,isOfferedForSaleInd=null,
            		warehouseAlignmentCode=null,createTs=null,orderablePackQtyUomCode=null,consumableGtin=null,
            		baseRetailUomCode=null,createUserid=null,subclassNbr=null,replenishSubTypeCode=null,
            		informationProviderTypeCode=null,fppRetardRangeInd=null,sendStoreDate=null,financialReportingGroupCode=null,
            finelineNbr=null,hasRigidPlasticPackagingContainerInd=null,isRetailNotifyStoreInd=null,itemChangeSendWalmartWeekNbr=null,
            supplyItemStatusChangeTimestamp=null,hasRfidInd=null,isBackroomScaleInd=null,omitTraitCode=null,
            supplyItemExpireDate=null,lastUpdateProgramId=null,isImportInd=null,accountingDeptNbr=null,orderablePackGtin=null,
            supplierSeqNbr=null,assortmentTypeCode=null,lastUpdateUserid=null,reserveMerchandiseTypeCode=null,
            baseDivisionCode=null,warehousePackCalcMethodCode=null,isShelfLabelRequiredInd=null,informationProviderId=null,
            supplyItemPrimaryDescription=null,supplyItemEffectiveDate=null,hasSecurityTagInd=null,isVariablePriceComparisionInd=null,supplyItemStatusCode=null
            ,itemTypeCode=null,warehouseRotationTypeCode=null,customerRetailAmt=null,warehousePackQty=null,supplyItemChangeReasonCode=null,
            supplierStockId=null,shelfLabel1Description=null,isVariableWeightInd=null,supplyItemCreateDate=null,orderablePackCostAmt=null,
            baseRetailAmt=null;String mbmTypeCode=null,orderablePackQty=null,targetMarketCode=null,legacyProductNbr=null,replenishmentGroupNbr=null,unitCostAmt=null,supplierDeptNbr=null,isReplenishedByUnitInd=null,isRetailVatInclusiveInd=null,merchandiseFamilyID=null
           ,destinationFormatCode=null,factoryId=null,itemEligibilityStateCode=null,originCountryCode=null,warehousePackQtyUomCode=null;
            
            JSONObject jsonobject = new JSONObject(outputResponse);
            
            JSONArray jsonarray = jsonobject.getJSONArray("nodeinstance");

        for(int i=0; i<jsonarray.length(); i++){
        	JSONObject jsonobject1 =jsonarray.getJSONObject(i);
            id=jsonobject1.getString("id");
            
            if(jsonobject1.has("hierarchyNode"))
            {hierarchyNode=jsonobject1.getString("hierarchyNode");}
            else{LOGGER.debug("The response not having hierarchyNode attribute");
            Reporter.log("The response not having hierarchyNode attribute");}
            
            JSONObject jsonObjAttribute = jsonobject1.getJSONObject("attributes");
            
            if(jsonObjAttribute.has("sellTotalContent"))
            {
            JSONArray sellTotalContent=jsonObjAttribute.getJSONArray("sellTotalContent");
          
            for(int m=0;m<sellTotalContent.length();m++)
            {
            JSONObject sellTotalContentobject=sellTotalContent.getJSONObject(m);
            if(sellTotalContentobject.has("sellTotalContentQty")){
                  sellTotalContentQty=sellTotalContentobject.getString("sellTotalContentQty");
            }
            if(sellTotalContentobject.has("sellTotalContentUomCode")){
                  sellTotalContentUomCode=sellTotalContentobject.getString("sellTotalContentUomCode");
            }
            }
            
            } 
            else{LOGGER.debug("The response doesn't have sellTotalContent attribute");
            Reporter.log("The response doesn't have sellTotalContent attribute");}
           
            if (jsonObjAttribute.has("isReplenishableInd")) {
                  isReplenishableInd = jsonObjAttribute.getString("isReplenishableInd");}
            else{LOGGER.debug("isReplenishableInd attribute is not present in the response");
            Reporter.log("isReplenishableInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("supplierNbr")) {
                  supplierNbr = jsonObjAttribute.getString("supplierNbr");}
            else{LOGGER.debug("supplierNbr attribute is not present in the response");
            Reporter.log("supplierNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("warehouseMinOrderQty")) {
                  warehouseMinOrderQty = jsonObjAttribute.getString("warehouseMinOrderQty"); }
            else{LOGGER.debug("warehouseMinOrderQty attribute is not present in the response");
            Reporter.log("warehouseMinOrderQty attribute is not present in the response");}
            
            if (jsonObjAttribute.has("replacedItemNbr")) {
                  replacedItemNbr = jsonObjAttribute.getString("replacedItemNbr");}
            else{LOGGER.debug("replacedItemNbr attribute is not present in the response");
            Reporter.log("replacedItemNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isCorporateReplenishableInd")) {
                  isCorporateReplenishableInd = jsonObjAttribute.getString("isCorporateReplenishableInd"); }
            else{LOGGER.debug("isCorporateReplenishableInd attribute is not present in the response");
            Reporter.log("isCorporateReplenishableInd attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("lastUpdatePgmId")) {
                  lastUpdatePgmId = jsonObjAttribute.getString("lastUpdatePgmId");}
            else{LOGGER.debug("lastUpdatePgmId attribute is not present in the response");
            Reporter.log("lastUpdatePgmId attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("warehousePackSellAmt")) {
                  warehousePackSellAmt = jsonObjAttribute.getString("warehousePackSellAmt");}
            else{LOGGER.debug("warehousePackSellAmt attribute is not present in the response");
            Reporter.log("warehousePackSellAmt attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isCannedOrderInd")) {
                  isCannedOrderInd = jsonObjAttribute.getString("isCannedOrderInd");}
            else{LOGGER.debug("isCannedOrderInd attribute is not present in the response");
            Reporter.log("isCannedOrderInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isEcommerceReplenishableInd")) {
                  isEcommerceReplenishableInd = jsonObjAttribute.getString("isEcommerceReplenishableInd"); }
            else{LOGGER.debug("isEcommerceReplenishableInd attribute is not present in the response");
            Reporter.log("isEcommerceReplenishableInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("itemNbr")) {
                  itemNbr = jsonObjAttribute.getString("itemNbr");}
            else{LOGGER.debug("itemNbr attribute is not present in the response");
            Reporter.log("itemNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("lifecycleStateCode")) {
                  lifecycleStateCode = jsonObjAttribute.getString("lifecycleStateCode");}
            else{LOGGER.debug("lifecycleStateCode attribute is not present in the response");
            Reporter.log("lifecycleStateCode attribute is not present in the response");}
            
            if(jsonObjAttribute.has("supplierLeadTime"))
            {
            JSONArray supplierLeadTime=jsonObjAttribute.getJSONArray("supplierLeadTime");
            for(int l=0;l<supplierLeadTime.length();l++)
            {
            JSONObject supplierLeadTimeobject=supplierLeadTime.getJSONObject(l);
            if(supplierLeadTimeobject.has("supplierLeadTimeUomCode"))
            {supplierLeadTimeUomCode=supplierLeadTimeobject.getString("supplierLeadTimeUomCode");}
            }
            }
            else{LOGGER.debug("supplierLeadTime attribute is not present in the response");
            Reporter.log("supplierLeadTime attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("recipientGln")) {
                  recipientGln = jsonObjAttribute.getString("recipientGln");}
            else{LOGGER.debug("recipientGln attribute is not present in the response");
            Reporter.log("recipientGln attribute is not present in the response");}
            
            if (jsonObjAttribute.has("shelfLabel2Description")) {
                  shelfLabel2Description = jsonObjAttribute.getString("shelfLabel2Description"); }
            else{LOGGER.debug("shelfLabel2Description attribute is not present in the response");
            Reporter.log("shelfLabel2Description attribute is not present in the response");}
            
            if (jsonObjAttribute.has("warehousePackCostAmt")) {
                  warehousePackCostAmt = jsonObjAttribute.getString("warehousePackCostAmt");}
            else{LOGGER.debug("warehousePackCostAmt attribute is not present in the response");
            Reporter.log("warehousePackCostAmt attribute is not present in the response");}
            
            if (jsonObjAttribute.has("deptNbr")) {
                  deptNbr = jsonObjAttribute.getString("deptNbr"); }
            else{LOGGER.debug("deptNbr attribute is not present in the response");
            Reporter.log("deptNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("lastUpdateTimestamp")) {
                  lastUpdateTimestamp = jsonObjAttribute.getString("lastUpdateTimestamp"); }
            else{LOGGER.debug("lastUpdateTimestamp attribute is not present in the response");
            Reporter.log("lastUpdateTimestamp attribute is not present in the response");}
            
            if (jsonObjAttribute.has("orderablePackTypeCode")) {
                  orderablePackTypeCode = jsonObjAttribute.getString("orderablePackTypeCode"); }
            else{LOGGER.debug("orderablePackTypeCode attribute is not present in the response");
            Reporter.log("orderablePackTypeCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("buyingRegionCode")) {
                  buyingRegionCode = jsonObjAttribute.getString("buyingRegionCode");}
            else{LOGGER.debug("buyingRegionCode attribute is not present in the response");
            Reporter.log("buyingRegionCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isCancelWhenOutInd")) {
                  isCancelWhenOutInd = jsonObjAttribute.getString("isCancelWhenOutInd");}
            else{LOGGER.debug("isCancelWhenOutInd attribute is not present in the response");
            Reporter.log("isCancelWhenOutInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("lastUpdateTs")) {
                  lastUpdateTs = jsonObjAttribute.getString("lastUpdateTs"); }
            else{LOGGER.debug("lastUpdateTs attribute is not present in the response");
            Reporter.log("lastUpdateTs attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isOfferedForSaleInd")) {
                  isOfferedForSaleInd = jsonObjAttribute.getString("isOfferedForSaleInd");}
            else{LOGGER.debug("isOfferedForSaleInd attribute is not present in the response");
            Reporter.log("isOfferedForSaleInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("warehouseAlignmentCode")) {
                  warehouseAlignmentCode = jsonObjAttribute.getString("warehouseAlignmentCode");}
            else{LOGGER.debug("warehouseAlignmentCode attribute is not present in the response");
            Reporter.log("warehouseAlignmentCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("createTs")) {
                  createTs = jsonObjAttribute.getString("createTs");}
            else{LOGGER.debug("createTs attribute is not present in the response");
            Reporter.log("createTs attribute is not present in the response");}
            
            if (jsonObjAttribute.has("orderablePackQtyUomCode")) {
                  orderablePackQtyUomCode = jsonObjAttribute.getString("orderablePackQtyUomCode");}
            else{LOGGER.debug("orderablePackQtyUomCode attribute is not present in the response");
            Reporter.log("orderablePackQtyUomCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("consumableGtin")) {
                  consumableGtin = jsonObjAttribute.getString("consumableGtin");}       
            else{LOGGER.debug("consumableGtin attribute is not present in the response");
            Reporter.log("consumableGtin attribute is not present in the response");}
            
            if (jsonObjAttribute.has("baseRetailUomCode")) {
                  baseRetailUomCode = jsonObjAttribute.getString("baseRetailUomCode");}
            else{LOGGER.debug("baseRetailUomCode attribute is not present in the response");
            Reporter.log("baseRetailUomCode attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("createUserid")) {
                  createUserid = jsonObjAttribute.getString("createUserid");}
            else{LOGGER.debug("createUserid attribute is not present in the response");
            Reporter.log("createUserid attribute is not present in the response");}
            
            if (jsonObjAttribute.has("subclassNbr")) {
                  subclassNbr = jsonObjAttribute.getString("subclassNbr");}
            else{LOGGER.debug("subclassNbr attribute is not present in the response");
            Reporter.log("subclassNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("replenishSubTypeCode")) {
                  replenishSubTypeCode = jsonObjAttribute.getString("replenishSubTypeCode");}
                        

            if (jsonObjAttribute.has("informationProviderTypeCode")) {
                  informationProviderTypeCode = jsonObjAttribute.getString("informationProviderTypeCode");}
            else{LOGGER.debug("informationProviderTypeCode attribute is not present in the response");
            Reporter.log("informationProviderTypeCode attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("factoryId")){
            JSONArray ArryfactoryId=jsonObjAttribute.getJSONArray("factoryId");
            factoryId=ArryfactoryId.toString();
            factoryId=factoryId.replace("[", "");
            factoryId=factoryId.replace("]", "");	
            }
            else{LOGGER.debug("factoryId attribute is not present in the response");
            Reporter.log("factoryId attribute is not present in the response");}
            
            if (jsonObjAttribute.has("fppRetardRangeInd")) {
                  fppRetardRangeInd = jsonObjAttribute.getString("fppRetardRangeInd"); }
            else{LOGGER.debug("fppRetardRangeInd attribute is not present in the response");
            Reporter.log("fppRetardRangeInd attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("sendStoreDate")) {
                  sendStoreDate = jsonObjAttribute.getString("sendStoreDate");}
                  else{LOGGER.debug("sendStoreDate attribute is not present in the response");
                  Reporter.log("sendStoreDate attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("financialReportingGroupCode")) {
                  financialReportingGroupCode = jsonObjAttribute.getString("financialReportingGroupCode");}
            else{LOGGER.debug("financialReportingGroupCode attribute is not present in the response");
            Reporter.log("financialReportingGroupCode attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("hasRigidPlasticPackagingContainerInd")) {
                  hasRigidPlasticPackagingContainerInd = jsonObjAttribute.getString("hasRigidPlasticPackagingContainerInd");}
            else{LOGGER.debug("hasRigidPlasticPackagingContainerInd attribute is not present in the response");
            Reporter.log("hasRigidPlasticPackagingContainerInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isRetailNotifyStoreInd")) {
                  isRetailNotifyStoreInd = jsonObjAttribute.getString("isRetailNotifyStoreInd");}
            else{LOGGER.debug("isRetailNotifyStoreInd attribute is not present in the response");
            Reporter.log("isRetailNotifyStoreInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("itemChangeSendWalmartWeekNbr")) {
                  itemChangeSendWalmartWeekNbr = jsonObjAttribute.getString("itemChangeSendWalmartWeekNbr");}
            else{LOGGER.debug("itemChangeSendWalmartWeekNbr attribute is not present in the response");
            Reporter.log("itemChangeSendWalmartWeekNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("supplyItemStatusChangeTimestamp")) {
                  supplyItemStatusChangeTimestamp = jsonObjAttribute.getString("supplyItemStatusChangeTimestamp");}
            else{LOGGER.debug("supplyItemStatusChangeTimestamp attribute is not present in the response");
            Reporter.log("supplyItemStatusChangeTimestamp attribute is not present in the response");}
            
            if (jsonObjAttribute.has("finelineNbr")) {
                  finelineNbr = jsonObjAttribute.getString("finelineNbr");}
            else{LOGGER.debug("finelineNbr attribute is not present in the response");
            Reporter.log("finelineNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("hasRfidInd")) {
                  hasRfidInd = jsonObjAttribute.getString("hasRfidInd");}
            else{LOGGER.debug("hasRfidInd attribute is not present in the response");
            Reporter.log("hasRfidInd attribute is not present in the response");}
            

            if (jsonObjAttribute.has("isBackroomScaleInd")) {
                  isBackroomScaleInd = jsonObjAttribute.getString("isBackroomScaleInd");}
            else{LOGGER.debug("isBackroomScaleInd attribute is not present in the response");
            Reporter.log("isBackroomScaleInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("omitTraitCode")) {
                  omitTraitCode = jsonObjAttribute.getString("omitTraitCode");}
            else{LOGGER.debug("omitTraitCode attribute is not present in the response");
            Reporter.log("omitTraitCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("supplyItemExpireDate")) {
                  supplyItemExpireDate = jsonObjAttribute.getString("supplyItemExpireDate");}
            else{LOGGER.debug("supplyItemExpireDate attribute is not present in the response");
            Reporter.log("supplyItemExpireDate attribute is not present in the response");}
            
            if (jsonObjAttribute.has("lastUpdateProgramId")) {
                  lastUpdateProgramId = jsonObjAttribute.getString("lastUpdateProgramId");}
            else{LOGGER.debug("lastUpdateProgramId attribute is not present in the response");
            Reporter.log("lastUpdateProgramId attribute is not present in the response");}
            
            if (jsonObjAttribute.has("isImportInd")) {
                  isImportInd = jsonObjAttribute.getString("isImportInd");}
            else{LOGGER.debug("isImportInd attribute is not present in the response");
            Reporter.log("isImportInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("accountingDeptNbr")) {
                  accountingDeptNbr = jsonObjAttribute.getString("accountingDeptNbr");}
            else{LOGGER.debug("accountingDeptNbr attribute is not present in the response");
            Reporter.log("accountingDeptNbr attribute is not present in the response");}
            
            if (jsonObjAttribute.has("orderablePackGtin")) {
                  orderablePackGtin = jsonObjAttribute.getString("orderablePackGtin");}
            else{LOGGER.debug("orderablePackGtin attribute is not present in the response");
            Reporter.log("orderablePackGtin attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("assortmentTypeCode")) {
                  assortmentTypeCode = jsonObjAttribute.getString("assortmentTypeCode");}
            else{LOGGER.debug("assortmentTypeCode attribute is not present in the response");
            Reporter.log("assortmentTypeCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("lastUpdateUserid")) {
                  lastUpdateUserid = jsonObjAttribute.getString("lastUpdateUserid");}
            else{LOGGER.debug("lastUpdateUserid attribute is not present in the response");
            Reporter.log("lastUpdateUserid attribute is not present in the response");}
            
            if (jsonObjAttribute.has("reserveMerchandiseTypeCode")) {
                  reserveMerchandiseTypeCode = jsonObjAttribute.getString("reserveMerchandiseTypeCode");}
            else{LOGGER.debug("reserveMerchandiseTypeCode attribute is not present in the response");
            Reporter.log("reserveMerchandiseTypeCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("baseDivisionCode")) {
                 baseDivisionCode = jsonObjAttribute.getString("baseDivisionCode");}
            else{LOGGER.debug("baseDivisionCode attribute is not present in the response");
            Reporter.log("baseDivisionCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("warehousePackCalcMethodCode")) {
                  warehousePackCalcMethodCode = jsonObjAttribute.getString("warehousePackCalcMethodCode");}
            else{LOGGER.debug("warehousePackCalcMethodCode attribute is not present in the response");
            Reporter.log("warehousePackCalcMethodCode attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("warehousePackQtyUomCode")) {
            	warehousePackQtyUomCode = jsonObjAttribute.getString("warehousePackQtyUomCode");}
            else{LOGGER.debug("warehousePackQtyUomCode attribute is not present in the response");
            Reporter.log("warehousePackQtyUomCode attribute is not present in the response");}
            
            
            JSONArray itemEligibilityStateCode1=jsonObjAttribute.getJSONArray("itemEligibilityStateCode");
            itemEligibilityStateCode = itemEligibilityStateCode1.toString();
            itemEligibilityStateCode=itemEligibilityStateCode.replace("[", "");
            itemEligibilityStateCode=itemEligibilityStateCode.replace("]", "");
            
            if (jsonObjAttribute.has("isShelfLabelRequiredInd")) {
                  isShelfLabelRequiredInd = jsonObjAttribute.getString("isShelfLabelRequiredInd");}
            
            else{LOGGER.debug("isShelfLabelRequiredInd attribute is not present in the response");
            Reporter.log("isShelfLabelRequiredInd attribute is not present in the response");}
            
            if (jsonObjAttribute.has("supplierSeqNbr")) {
                  supplierSeqNbr = jsonObjAttribute.getString("supplierSeqNbr");}
            else{LOGGER.debug("supplierSeqNbr attribute is not present in the response");
            Reporter.log("supplierSeqNbr attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("supplyItemPrimaryDescription")) {
                  supplyItemPrimaryDescription = jsonObjAttribute.getString("supplyItemPrimaryDescription");}
            else{LOGGER.debug("supplyItemPrimaryDescription attribute is not present in the response");
            Reporter.log("supplyItemPrimaryDescription attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("supplyItemEffectiveDate")) {
                  supplyItemEffectiveDate = jsonObjAttribute.getString("supplyItemEffectiveDate");}
            else{LOGGER.debug("supplyItemEffectiveDate attribute is not present in the response");
            Reporter.log("supplyItemEffectiveDate attribute is not present in the response");}
            
            
            JSONArray originCountryCode1=jsonObjAttribute.getJSONArray("originCountryCode");
            originCountryCode=originCountryCode1.toString();
            originCountryCode=originCountryCode.replace("[", "");
            originCountryCode=originCountryCode.replace("]", "");
            originCountryCode=originCountryCode.replaceAll("\"", "");
            
            
            if (jsonObjAttribute.has("hasSecurityTagInd")) {
                   hasSecurityTagInd = jsonObjAttribute.getString("hasSecurityTagInd");}
            
            else{LOGGER.debug("hasSecurityTagInd attribute is not present in the response");
            Reporter.log("hasSecurityTagInd attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("isVariablePriceComparisionInd")) {
                  isVariablePriceComparisionInd = jsonObjAttribute.getString("isVariablePriceComparisionInd");}
            else{LOGGER.debug("isVariablePriceComparisionInd attribute is not present in the response");
            Reporter.log("isVariablePriceComparisionInd attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("supplyItemStatusCode")) {
                  supplyItemStatusCode = jsonObjAttribute.getString("supplyItemStatusCode");}
            else{LOGGER.debug("supplyItemStatusCode attribute is not present in the response");
            Reporter.log("supplyItemStatusCode attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("informationProviderId")) {
                  informationProviderId = jsonObjAttribute.getString("informationProviderId");}
            else{LOGGER.debug("informationProviderId attribute is not present in the response");
            Reporter.log("informationProviderId attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("supplyItemChangeReasonCode")) {
                  supplyItemChangeReasonCode = jsonObjAttribute.getString("supplyItemChangeReasonCode");}
            
            else{LOGGER.debug("supplyItemChangeReasonCode attribute is not present in the response");
            Reporter.log("supplyItemChangeReasonCode attribute is not present in the response");}
            
            if (jsonObjAttribute.has("supplierStockId")) {
                  supplierStockId = jsonObjAttribute.getString("supplierStockId");}
            else{LOGGER.debug("supplierStockId attribute is not present in the response");
            Reporter.log("supplierStockId attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("shelfLabel1Description")) {
                  shelfLabel1Description = jsonObjAttribute.getString("shelfLabel1Description");}
            else{LOGGER.debug("shelfLabel1Description attribute is not present in the response");
            Reporter.log("shelfLabel1Description attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("isVariableWeightInd")) {
                  isVariableWeightInd = jsonObjAttribute.getString("isVariableWeightInd");}
            
            else{LOGGER.debug("isVariableWeightInd attribute is not present in the response");
            Reporter.log("isVariableWeightInd attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("supplyItemCreateDate")) {
                  supplyItemCreateDate = jsonObjAttribute.getString("supplyItemCreateDate");}
            
            else{LOGGER.debug("supplyItemCreateDate attribute is not present in the response");
            Reporter.log("supplyItemCreateDate attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("orderablePackCostAmt")) {
                  orderablePackCostAmt = jsonObjAttribute.getString("orderablePackCostAmt");}
            else{LOGGER.debug("orderablePackCostAmt attribute is not present in the response");
            Reporter.log("orderablePackCostAmt attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("warehousePackQty")) {
                  warehousePackQty = jsonObjAttribute.getString("warehousePackQty");}
            else{LOGGER.debug("warehousePackQty attribute is not present in the response");
            Reporter.log("warehousePackQty attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("customerRetailAmt")) {
                  customerRetailAmt = jsonObjAttribute.getString("customerRetailAmt");}
            else{LOGGER.debug("customerRetailAmt attribute is not present in the response");
            Reporter.log("customerRetailAmt attribute is not present in the response");}
            
            
            if (jsonObjAttribute.has("itemTypeCode")) {
                  itemTypeCode = jsonObjAttribute.getString("itemTypeCode");}
            else{LOGGER.debug("itemTypeCode attribute is not present in the response");
            Reporter.log("itemTypeCode attribute is not present in the response");}
            
            
            
            if (jsonObjAttribute.has("warehouseRotationTypeCode")) {
                  warehouseRotationTypeCode = jsonObjAttribute.getString("warehouseRotationTypeCode");
            System.out.println("==========warehouseRotationTypeCode=============>warehouseRotationTypeCode:"+warehouseRotationTypeCode);      
            }
            else{LOGGER.debug("warehouseRotationTypeCode attribute is not present in the response");
            Reporter.log("warehouseRotationTypeCode attribute is not present in the response");}
            
            
            
            if(jsonObjAttribute.has("baseRetailAmt")){
            	baseRetailAmt=jsonObjAttribute.getString("baseRetailAmt");}
            else{LOGGER.debug("baseRetailAmt attribute is not present in the response");
            Reporter.log("baseRetailAmt attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("mbmTypeCode")){
            	 mbmTypeCode = jsonObjAttribute.getString("mbmTypeCode");}
            else{LOGGER.debug("mbmTypeCode attribute is not present in the response");
            Reporter.log("mbmTypeCode attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("orderablePackQty")){
            	orderablePackQty=jsonObjAttribute.getString("orderablePackQty");}
            else{LOGGER.debug("orderablePackQty attribute is not present in the response");
            Reporter.log("orderablePackQty attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("targetMarketCode")){
            	targetMarketCode=jsonObjAttribute.getString("targetMarketCode");}
            else{LOGGER.debug("targetMarketCode attribute is not present in the response");
            Reporter.log("targetMarketCode attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("legacyProductNbr")){
            	legacyProductNbr=jsonObjAttribute.getString("legacyProductNbr");}
            else{LOGGER.debug("legacyProductNbr attribute is not present in the response");
            Reporter.log("legacyProductNbr attribute is not present in the response");}
            
            if(jsonObjAttribute.has("replenishmentGroupNbr")){
            	replenishmentGroupNbr=jsonObjAttribute.getString("replenishmentGroupNbr");}
            else{LOGGER.debug("replenishmentGroupNbr attribute is not present in the response");
            Reporter.log("replenishmentGroupNbr attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("unitCostAmt")){
            	unitCostAmt=jsonObjAttribute.getString("unitCostAmt");}
            else{LOGGER.debug("unitCostAmt attribute is not present in the response");
            Reporter.log("unitCostAmt attribute is not present in the response");}
            
            if(jsonObjAttribute.has("supplierDeptNbr")){
            	supplierDeptNbr=jsonObjAttribute.getString("supplierDeptNbr");}
            else{LOGGER.debug("supplierDeptNbr attribute is not present in the response");
            Reporter.log("supplierDeptNbr attribute is not present in the response");}
            	
            
            if(jsonObjAttribute.has("isReplenishedByUnitInd")){
            	isReplenishedByUnitInd=jsonObjAttribute.getString("isReplenishedByUnitInd");}
            else{LOGGER.debug("isReplenishedByUnitInd attribute is not present in the response");
            Reporter.log("isReplenishedByUnitInd attribute is not present in the response");}
            
            if(jsonObjAttribute.has("isRetailVatInclusiveInd")){
            	isRetailVatInclusiveInd=jsonObjAttribute.getString("isRetailVatInclusiveInd");}
            else{LOGGER.debug("isRetailVatInclusiveInd attribute is not present in the response");
            Reporter.log("isRetailVatInclusiveInd attribute is not present in the response");}
            
            
            if(jsonObjAttribute.has("merchandiseFamilyID")){
            	merchandiseFamilyID=jsonObjAttribute.getString("merchandiseFamilyID");}
            else{LOGGER.debug("merchandiseFamilyID attribute is not present in the response");
            Reporter.log("merchandiseFamilyID attribute is not present in the response");}
            
            
            JSONArray destinationFormatCodearray=jsonObjAttribute.getJSONArray("destinationFormatCode");

              destinationFormatCode=destinationFormatCodearray.toString();
              destinationFormatCode=destinationFormatCode.replace("[", "");
              destinationFormatCode=destinationFormatCode.replace("]", "");
              destinationFormatCode=destinationFormatCode.replaceAll("\"", "");

            }
            
            
            List<String> AttributesValue=new ArrayList<String>();
           
            
    Collections.addAll(AttributesValue,"hierarchyNode:"+hierarchyNode, "sellTotalContentQty:"+sellTotalContentQty,
    	 "sellTotalContentUomCode:"+sellTotalContentUomCode,"isReplenishableInd:"+isReplenishableInd,
          "supplierNbr:"+supplierNbr,"warehouseMinOrderQty:"+warehouseMinOrderQty,"replacedItemNbr:"+replacedItemNbr,
          "isCorporateReplenishableInd:"+isCorporateReplenishableInd,"lastUpdatePgmId:"+lastUpdatePgmId,
          "warehousePackSellAmt:"+warehousePackSellAmt,"isCannedOrderInd:"+isCannedOrderInd,
          "isEcommerceReplenishableInd:"+isEcommerceReplenishableInd,"itemNbr:"+itemNbr,"lifecycleStateCode:"+lifecycleStateCode,
          "supplierLeadTimeUomCode:"+supplierLeadTimeUomCode,"recipientGln:"+recipientGln,
          "shelfLabel2Description:"+shelfLabel2Description,"warehousePackCostAmt:"+warehousePackCostAmt,"deptNbr:"+deptNbr,
          "lastUpdateTimestamp:"+lastUpdateTimestamp,"orderablePackTypeCode:"+orderablePackTypeCode,
          "buyingRegionCode:"+buyingRegionCode,"isCancelWhenOutInd:"+isCancelWhenOutInd,"lastUpdateTs:"+lastUpdateTs,
          "isOfferedForSaleInd:"+isOfferedForSaleInd,"warehouseAlignmentCode:"+warehouseAlignmentCode,
          "createTs:"+createTs,"orderablePackQtyUomCode:"+orderablePackQtyUomCode,"orderablePackQty:"+orderablePackQty,"consumableGtin:"+consumableGtin,
          "baseRetailUomCode:"+baseRetailUomCode,"createUserid:"+createUserid,"subclassNbr:"+subclassNbr,
          "replenishSubTypeCode:"+replenishSubTypeCode,"informationProviderTypeCode:"+informationProviderTypeCode,
          "fppRetardRangeInd:"+fppRetardRangeInd,"sendStoreDate:"+sendStoreDate,"financialReportingGroupCode:"+financialReportingGroupCode,
          "finelineNbr:"+finelineNbr,"hasRigidPlasticPackagingContainerInd:"+hasRigidPlasticPackagingContainerInd,
          "isRetailNotifyStoreInd:"+isRetailNotifyStoreInd,"itemChangeSendWalmartWeekNbr:"+itemChangeSendWalmartWeekNbr,
          "supplyItemStatusChangeTimestamp:"+supplyItemStatusChangeTimestamp,"hasRfidInd:"+hasRfidInd,
          "isBackroomScaleInd:"+isBackroomScaleInd,"omitTraitCode:"+omitTraitCode,"supplyItemExpireDate:"+supplyItemExpireDate,
          "lastUpdateProgramId:"+lastUpdateProgramId,"isImportInd:"+isImportInd,"accountingDeptNbr:"+accountingDeptNbr,
          "orderablePackGtin:"+orderablePackGtin,"supplierSeqNbr:"+supplierSeqNbr,"assortmentTypeCode:"+assortmentTypeCode,
          "lastUpdateUserid:"+lastUpdateUserid,"reserveMerchandiseTypeCode:"+reserveMerchandiseTypeCode,
          "baseDivisionCode:"+baseDivisionCode,"warehousePackCalcMethodCode:"+warehousePackCalcMethodCode,
          "isShelfLabelRequiredInd:"+isShelfLabelRequiredInd,"informationProviderId:"+informationProviderId,
          "supplyItemPrimaryDescription:"+supplyItemPrimaryDescription,"supplyItemEffectiveDate:"+supplyItemEffectiveDate,
          "hasSecurityTagInd:"+hasSecurityTagInd,"isVariablePriceComparisionInd:"+isVariablePriceComparisionInd,
          "supplyItemStatusCode:"+supplyItemStatusCode,"itemTypeCode:"+itemTypeCode,"warehouseRotationTypeCode:"+warehouseRotationTypeCode,
          "destinationFormatCode:"+destinationFormatCode,"customerRetailAmt:"+customerRetailAmt,"warehousePackQtyUomCode:"+warehousePackQtyUomCode,
          "warehousePackQty:"+warehousePackQty,"supplyItemChangeReasonCode:"+supplyItemChangeReasonCode,
          "supplierStockId:"+supplierStockId,"shelfLabel1Description:"+shelfLabel1Description,"isVariableWeightInd:"+isVariableWeightInd,
          "supplyItemCreateDate:"+supplyItemCreateDate,"orderablePackCostAmt:"+orderablePackCostAmt,"factoryId:"+factoryId,"baseRetailAmt:"+baseRetailAmt,"originCountryCode:"+originCountryCode,"mbmTypeCode:"+mbmTypeCode,"targetMarketCode:"+targetMarketCode,"itemEligibilityStateCode:"+itemEligibilityStateCode,
  		"legacyProductNbr:"+legacyProductNbr,"replenishmentGroupNbr:"+replenishmentGroupNbr,"unitCostAmt:"+unitCostAmt,"supplierDeptNbr:"+supplierDeptNbr,"isReplenishedByUnitInd:"+isReplenishedByUnitInd,"isRetailVatInclusiveInd:"+isRetailVatInclusiveInd,"merchandiseFamilyID:"+merchandiseFamilyID);
 Collections.sort(AttributesValue);
            return AttributesValue;
     }

public List<String> siItemGetResponse(Response response) throws JSONException{
		String outputResponse = response.asString();
		String id="defaultString",hierarchyNode="defaultString",sellTotalContentQty="defaultString",sellTotalContentUomCode="defaultString",isReplenishableInd="defaultString",supplierNbr="defaultString"
		 ,warehouseMinOrderQty="defaultString",replacedItemNbr="defaultString",isCorporateReplenishableInd="defaultString",lastUpdatePgmId = "defaultString",warehousePackSellAmt = "defaultString",isCannedOrderInd = "defaultString",isEcommerceReplenishableInd="defaultString"
		 ,itemNbr="defaultString",lifecycleStateCode="defaultString",supplierLeadTimeUomCode="defaultString",recipientGln="defaultString",shelfLabel2Description="defaultString",
		 warehousePackCostAmt="defaultString",deptNbr="defaultString",lastUpdateTimestamp="defaultString",orderablePackTypeCode="defaultString",buyingRegionCode="defaultString",isCancelWhenOutInd="defaultString",lastUpdateTs="defaultString",isOfferedForSaleInd="defaultString",
				warehouseAlignmentCode="defaultString",createTs="defaultString",orderablePackQtyUomCode="defaultString",consumableGtin="defaultString",
				baseRetailUomCode="defaultString",createUserid="defaultString",subclassNbr="defaultString",replenishSubTypeCode="defaultString",
				informationProviderTypeCode="defaultString",fppRetardRangeInd="defaultString",sendStoreDate="defaultString",financialReportingGroupCode="defaultString",
		finelineNbr="defaultString",hasRigidPlasticPackagingContainerInd="defaultString",isRetailNotifyStoreInd="defaultString",itemChangeSendWalmartWeekNbr="defaultString",
		supplyItemStatusChangeTimestamp="defaultString",hasRfidInd="defaultString",isBackroomScaleInd="defaultString",omitTraitCode="defaultString",
		supplyItemExpireDate="defaultString",lastUpdateProgramId="defaultString",isImportInd="defaultString",accountingDeptNbr="defaultString",orderablePackGtin="defaultString",
		supplierSeqNbr="defaultString",assortmentTypeCode="defaultString",lastUpdateUserid="defaultString",reserveMerchandiseTypeCode="defaultString",
		baseDivisionCode="defaultString",warehousePackCalcMethodCode="defaultString",isShelfLabelRequiredInd="defaultString",informationProviderId="defaultString",
		supplyItemPrimaryDescription="defaultString",supplyItemEffectiveDate="defaultString",hasSecurityTagInd="defaultString",isVariablePriceComparisionInd="defaultString",supplyItemStatusCode="defaultString"
		,itemTypeCode="defaultString",warehouseRotationTypeCode="defaultString",customerRetailAmt="defaultString",warehousePackQty="defaultString",supplyItemChangeReasonCode="defaultString",
		supplierStockId="defaultString",shelfLabel1Description="defaultString",isVariableWeightInd="defaultString",supplyItemCreateDate="defaultString",orderablePackCostAmt="defaultString",
		baseRetailAmt="defaultString";String mbmTypeCode="defaultString",orderablePackQty="defaultString",targetMarketCode="defaultString",legacyProductNbr="defaultString",replenishmentGroupNbr="defaultString",unitCostAmt="defaultString",supplierDeptNbr="defaultString",isReplenishedByUnitInd="defaultString",isRetailVatInclusiveInd="defaultString",merchandiseFamilyID="defaultString"
		,destinationFormatCode="defaultString",factoryId="defaultString",itemEligibilityStateCode="defaultString",originCountryCode="defaultString",warehousePackQtyUomCode="defaultString";
		String userKey="defaultString",merchandiseCategoryNbr="defaultString",inforemReorderTypeCode="defaultString",publishedGtin="defaultString",supplyItemStatusChangeDate="defaultString",sellTotalContentQty1="defaultString",supplyItemReplenishmentGroupNbr="defaultString",warehousePackGtin="defaultString",buyerUserId="defaultString",merchandiseSubcategoryNbr="defaultString",omitTraitSequenceNbr="defaultString",omitTraitConnectorCode="defaultString",omitTraitNbr="defaultString";
		List<String> AttributesValue=new ArrayList<String>();
		JSONObject jsonobject = new JSONObject(outputResponse);
		
//		JSONArray jsonarray = jsonobject.getJSONArray("nodeinstance");
		
//		for(int i=0; i<jsonarray.length(); i++){
//			JSONObject jsonobject1 =jsonarray.getJSONObject(i);
		    id=jsonobject.getString("id");
		    if(jsonobject.has("userKey"))
            {
            userKey=jsonobject.getString("userKey");}
		if(jsonobject.has("hierarchyNode"))
		{hierarchyNode=jsonobject.getString("hierarchyNode");}
		else{LOGGER.debug("The response not having hierarchyNode attribute");
		Reporter.log("The response not having hierarchyNode attribute");}
		
		JSONObject jsonObjAttribute = jsonobject.getJSONObject("attributes");
		
		if(jsonObjAttribute.has("sellTotalContent"))
		{
		JSONArray sellTotalContent=jsonObjAttribute.getJSONArray("sellTotalContent");
		   for(int m=0;m<sellTotalContent.length();m++)
		    {
		    JSONObject sellTotalContentobject=sellTotalContent.getJSONObject(m);
		    if(sellTotalContentobject.has("sellTotalContentQty")){
		  sellTotalContentQty=sellTotalContentobject.getString("sellTotalContentQty");}
		
		if(sellTotalContentobject.has("sellTotalContentUomCode")){
		  sellTotalContentUomCode=sellTotalContentobject.getString("sellTotalContentUomCode");}
		}
		} 
		else{LOGGER.debug("The response doesn't have sellTotalContent attribute");
		Reporter.log("The response doesn't have sellTotalContent attribute");}
		  
		if (jsonObjAttribute.has("sellTotalContentQty")) {
			sellTotalContentQty1 = jsonObjAttribute.getString("sellTotalContentQty");}
			else{LOGGER.debug("sellTotalContentQty attribute is not present in the response");
			Reporter.log("sellTotalContentQty attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("supplyItemReplenishmentGroupNbr")) {
			supplyItemReplenishmentGroupNbr = jsonObjAttribute.getString("supplyItemReplenishmentGroupNbr");}
			else{LOGGER.debug("supplyItemReplenishmentGroupNbr attribute is not present in the response");
			Reporter.log("supplyItemReplenishmentGroupNbr attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("warehousePackGtin")) {
			warehousePackGtin = jsonObjAttribute.getString("warehousePackGtin");}
			else{LOGGER.debug("warehousePackGtin attribute is not present in the response");
			Reporter.log("warehousePackGtin attribute is not present in the response");}
		
		    if (jsonObjAttribute.has("isReplenishableInd")) {
		  isReplenishableInd = jsonObjAttribute.getString("isReplenishableInd");}
		else{LOGGER.debug("isReplenishableInd attribute is not present in the response");
		Reporter.log("isReplenishableInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("supplierNbr")) {
		  supplierNbr = jsonObjAttribute.getString("supplierNbr");}
		else{LOGGER.debug("supplierNbr attribute is not present in the response");
		Reporter.log("supplierNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("warehouseMinOrderQty")) {
		  warehouseMinOrderQty = jsonObjAttribute.getString("warehouseMinOrderQty"); }
		else{LOGGER.debug("warehouseMinOrderQty attribute is not present in the response");
		Reporter.log("warehouseMinOrderQty attribute is not present in the response");}
		
		if (jsonObjAttribute.has("replacedItemNbr")) {
		  replacedItemNbr = jsonObjAttribute.getString("replacedItemNbr");}
		else{LOGGER.debug("replacedItemNbr attribute is not present in the response");
		Reporter.log("replacedItemNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isCorporateReplenishableInd")) {
		  isCorporateReplenishableInd = jsonObjAttribute.getString("isCorporateReplenishableInd"); }
		else{LOGGER.debug("isCorporateReplenishableInd attribute is not present in the response");
		Reporter.log("isCorporateReplenishableInd attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("lastUpdatePgmId")) {
		  lastUpdatePgmId = jsonObjAttribute.getString("lastUpdatePgmId");}
		else{LOGGER.debug("lastUpdatePgmId attribute is not present in the response");
		Reporter.log("lastUpdatePgmId attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("warehousePackSellAmt")) {
		  warehousePackSellAmt = jsonObjAttribute.getString("warehousePackSellAmt");}
		else{LOGGER.debug("warehousePackSellAmt attribute is not present in the response");
		Reporter.log("warehousePackSellAmt attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isCannedOrderInd")) {
		  isCannedOrderInd = jsonObjAttribute.getString("isCannedOrderInd");}
		else{LOGGER.debug("isCannedOrderInd attribute is not present in the response");
		Reporter.log("isCannedOrderInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isEcommerceReplenishableInd")) {
		  isEcommerceReplenishableInd = jsonObjAttribute.getString("isEcommerceReplenishableInd"); }
		else{LOGGER.debug("isEcommerceReplenishableInd attribute is not present in the response");
		Reporter.log("isEcommerceReplenishableInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("itemNbr")) {
		  itemNbr = jsonObjAttribute.getString("itemNbr");}
		else{LOGGER.debug("itemNbr attribute is not present in the response");
		Reporter.log("itemNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("lifecycleStateCode")) {
		  lifecycleStateCode = jsonObjAttribute.getString("lifecycleStateCode");}
		else{LOGGER.debug("lifecycleStateCode attribute is not present in the response");
		Reporter.log("lifecycleStateCode attribute is not present in the response");}
		
		if(jsonObjAttribute.has("supplierLeadTime"))
		{
		JSONArray supplierLeadTime=jsonObjAttribute.getJSONArray("supplierLeadTime");
		for(int l=0;l<supplierLeadTime.length();l++)
		{
		JSONObject supplierLeadTimeobject=supplierLeadTime.getJSONObject(l);
		if(supplierLeadTimeobject.has("supplierLeadTimeUomCode"))
		{supplierLeadTimeUomCode=supplierLeadTimeobject.getString("supplierLeadTimeUomCode");}
		}
		}
		else{LOGGER.debug("supplierLeadTime attribute is not present in the response");
		Reporter.log("supplierLeadTime attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("recipientGln")) {
		  recipientGln = jsonObjAttribute.getString("recipientGln");}
		else{LOGGER.debug("recipientGln attribute is not present in the response");
		Reporter.log("recipientGln attribute is not present in the response");}
		
		if (jsonObjAttribute.has("shelfLabel2Description")) {
		  shelfLabel2Description = jsonObjAttribute.getString("shelfLabel2Description"); }
		else{LOGGER.debug("shelfLabel2Description attribute is not present in the response");
		Reporter.log("shelfLabel2Description attribute is not present in the response");}
		
		if (jsonObjAttribute.has("warehousePackCostAmt")) {
		  warehousePackCostAmt = jsonObjAttribute.getString("warehousePackCostAmt");}
		else{LOGGER.debug("warehousePackCostAmt attribute is not present in the response");
		Reporter.log("warehousePackCostAmt attribute is not present in the response");}
		
		if (jsonObjAttribute.has("deptNbr")) {
		  deptNbr = jsonObjAttribute.getString("deptNbr"); }
		else{LOGGER.debug("deptNbr attribute is not present in the response");
		Reporter.log("deptNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("lastUpdateTimestamp")) {
		  lastUpdateTimestamp = jsonObjAttribute.getString("lastUpdateTimestamp"); }
		else{LOGGER.debug("lastUpdateTimestamp attribute is not present in the response");
		Reporter.log("lastUpdateTimestamp attribute is not present in the response");}
		
		if (jsonObjAttribute.has("orderablePackTypeCode")) {
		  orderablePackTypeCode = jsonObjAttribute.getString("orderablePackTypeCode"); }
		else{LOGGER.debug("orderablePackTypeCode attribute is not present in the response");
		Reporter.log("orderablePackTypeCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("buyingRegionCode")) {
		  buyingRegionCode = jsonObjAttribute.getString("buyingRegionCode");}
		else{LOGGER.debug("buyingRegionCode attribute is not present in the response");
		Reporter.log("buyingRegionCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isCancelWhenOutInd")) {
		  isCancelWhenOutInd = jsonObjAttribute.getString("isCancelWhenOutInd");}
		else{LOGGER.debug("isCancelWhenOutInd attribute is not present in the response");
		Reporter.log("isCancelWhenOutInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("lastUpdateTs")) {
		  lastUpdateTs = jsonObjAttribute.getString("lastUpdateTs"); }
		else{LOGGER.debug("lastUpdateTs attribute is not present in the response");
		Reporter.log("lastUpdateTs attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isOfferedForSaleInd")) {
		  isOfferedForSaleInd = jsonObjAttribute.getString("isOfferedForSaleInd");}
		else{LOGGER.debug("isOfferedForSaleInd attribute is not present in the response");
		Reporter.log("isOfferedForSaleInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("warehouseAlignmentCode")) {
		  warehouseAlignmentCode = jsonObjAttribute.getString("warehouseAlignmentCode");}
		else{LOGGER.debug("warehouseAlignmentCode attribute is not present in the response");
		Reporter.log("warehouseAlignmentCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("createTs")) {
		  createTs = jsonObjAttribute.getString("createTs");}
		else{LOGGER.debug("createTs attribute is not present in the response");
		Reporter.log("createTs attribute is not present in the response");}
		
		if (jsonObjAttribute.has("orderablePackQtyUomCode")) {
		  orderablePackQtyUomCode = jsonObjAttribute.getString("orderablePackQtyUomCode");}
		else{LOGGER.debug("orderablePackQtyUomCode attribute is not present in the response");
		Reporter.log("orderablePackQtyUomCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("consumableGtin")) {
		  consumableGtin = jsonObjAttribute.getString("consumableGtin");}       
		else{LOGGER.debug("consumableGtin attribute is not present in the response");
		Reporter.log("consumableGtin attribute is not present in the response");}
		
		if (jsonObjAttribute.has("baseRetailUomCode")) {
		  baseRetailUomCode = jsonObjAttribute.getString("baseRetailUomCode");}
		else{LOGGER.debug("baseRetailUomCode attribute is not present in the response");
		Reporter.log("baseRetailUomCode attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("createUserid")) {
		  createUserid = jsonObjAttribute.getString("createUserid");}
		else{LOGGER.debug("createUserid attribute is not present in the response");
		Reporter.log("createUserid attribute is not present in the response");}
		
		if (jsonObjAttribute.has("subclassNbr")) {
		  subclassNbr = jsonObjAttribute.getString("subclassNbr");}
		else{LOGGER.debug("subclassNbr attribute is not present in the response");
		Reporter.log("subclassNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("replenishSubTypeCode")) {
		  replenishSubTypeCode = jsonObjAttribute.getString("replenishSubTypeCode");}
		            
		
		if (jsonObjAttribute.has("informationProviderTypeCode")) {
		  informationProviderTypeCode = jsonObjAttribute.getString("informationProviderTypeCode");}
		else{LOGGER.debug("informationProviderTypeCode attribute is not present in the response");
		Reporter.log("informationProviderTypeCode attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("factoryId")){
		JSONArray ArryfactoryId=jsonObjAttribute.getJSONArray("factoryId");
		factoryId=ArryfactoryId.toString();
		factoryId=factoryId.replace("[", "");
		factoryId=factoryId.replace("]", "");	
		}
		else{LOGGER.debug("factoryId attribute is not present in the response");
		Reporter.log("factoryId attribute is not present in the response");}
		
		if (jsonObjAttribute.has("fppRetardRangeInd")) {
		  fppRetardRangeInd = jsonObjAttribute.getString("fppRetardRangeInd"); }
		else{LOGGER.debug("fppRetardRangeInd attribute is not present in the response");
		Reporter.log("fppRetardRangeInd attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("sendStoreDate")) {
		  sendStoreDate = jsonObjAttribute.getString("sendStoreDate");}
		  else{LOGGER.debug("sendStoreDate attribute is not present in the response");
		  Reporter.log("sendStoreDate attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("financialReportingGroupCode")) {
		  financialReportingGroupCode = jsonObjAttribute.getString("financialReportingGroupCode");}
		else{LOGGER.debug("financialReportingGroupCode attribute is not present in the response");
		Reporter.log("financialReportingGroupCode attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("hasRigidPlasticPackagingContainerInd")) {
		  hasRigidPlasticPackagingContainerInd = jsonObjAttribute.getString("hasRigidPlasticPackagingContainerInd");}
		else{LOGGER.debug("hasRigidPlasticPackagingContainerInd attribute is not present in the response");
		Reporter.log("hasRigidPlasticPackagingContainerInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isRetailNotifyStoreInd")) {
		  isRetailNotifyStoreInd = jsonObjAttribute.getString("isRetailNotifyStoreInd");}
		else{LOGGER.debug("isRetailNotifyStoreInd attribute is not present in the response");
		Reporter.log("isRetailNotifyStoreInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("itemChangeSendWalmartWeekNbr")) {
		  itemChangeSendWalmartWeekNbr = jsonObjAttribute.getString("itemChangeSendWalmartWeekNbr");}
		else{LOGGER.debug("itemChangeSendWalmartWeekNbr attribute is not present in the response");
		Reporter.log("itemChangeSendWalmartWeekNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("supplyItemStatusChangeTimestamp")) {
		  supplyItemStatusChangeTimestamp = jsonObjAttribute.getString("supplyItemStatusChangeTimestamp");}
		else{LOGGER.debug("supplyItemStatusChangeTimestamp attribute is not present in the response");
		Reporter.log("supplyItemStatusChangeTimestamp attribute is not present in the response");}
		
		if (jsonObjAttribute.has("finelineNbr")) {
		  finelineNbr = jsonObjAttribute.getString("finelineNbr");}
		else{LOGGER.debug("finelineNbr attribute is not present in the response");
		Reporter.log("finelineNbr attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("buyerUserId")) {
			buyerUserId = jsonObjAttribute.getString("buyerUserId");}
			else{LOGGER.debug("buyerUserId attribute is not present in the response");
			Reporter.log("buyerUserId attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("merchandiseSubcategoryNbr")) {
			merchandiseSubcategoryNbr = jsonObjAttribute.getString("merchandiseSubcategoryNbr");}
			else{LOGGER.debug("merchandiseSubcategoryNbr attribute is not present in the response");
			Reporter.log("merchandiseSubcategoryNbr attribute is not present in the response");}
		
		if(jsonObjAttribute.has("destinationOmitTraits")){
			JSONArray destinationOmitTraits1=jsonObjAttribute.getJSONArray("destinationOmitTraits");
			JSONObject destinationOmitTraitsObject=destinationOmitTraits1.getJSONObject(0);
			JSONArray traitGroup=destinationOmitTraitsObject.getJSONArray("traitGroup");
			List<String> destinationOmittraitsArray=new ArrayList<String>();
			
			for(int j=0; j<3; j++){
				JSONObject traitGroupObject=traitGroup.getJSONObject(j);

				
					if(traitGroupObject.has("omitTraitSequenceNbr")){
						omitTraitSequenceNbr =traitGroupObject.getString("omitTraitSequenceNbr");
					}
					if(traitGroupObject.has("omitTraitConnectorCode")){
						omitTraitConnectorCode =traitGroupObject.getString("omitTraitConnectorCode");
					}
					if(traitGroupObject.has("omitTraitNbr")){
						omitTraitNbr =traitGroupObject.getString("omitTraitNbr");
					}
//					Collections.addAll(AttributesValue,"omitTraitSequenceNbr:"+omitTraitSequenceNbr,"omitTraitConnectorCode:"+omitTraitConnectorCode,"omitTraitNbr:"+omitTraitNbr);
			
			}
		}
		
		
		
		if (jsonObjAttribute.has("hasRfidInd")) {
		  hasRfidInd = jsonObjAttribute.getString("hasRfidInd");}
		else{LOGGER.debug("hasRfidInd attribute is not present in the response");
		Reporter.log("hasRfidInd attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("isBackroomScaleInd")) {
		  isBackroomScaleInd = jsonObjAttribute.getString("isBackroomScaleInd");}
		else{LOGGER.debug("isBackroomScaleInd attribute is not present in the response");
		Reporter.log("isBackroomScaleInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("omitTraitCode")) {
		  omitTraitCode = jsonObjAttribute.getString("omitTraitCode");}
		else{LOGGER.debug("omitTraitCode attribute is not present in the response");
		Reporter.log("omitTraitCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("supplyItemExpireDate")) {
		  supplyItemExpireDate = jsonObjAttribute.getString("supplyItemExpireDate");}
		else{LOGGER.debug("supplyItemExpireDate attribute is not present in the response");
		Reporter.log("supplyItemExpireDate attribute is not present in the response");}
		
		if (jsonObjAttribute.has("lastUpdateProgramId")) {
		  lastUpdateProgramId = jsonObjAttribute.getString("lastUpdateProgramId");}
		else{LOGGER.debug("lastUpdateProgramId attribute is not present in the response");
		Reporter.log("lastUpdateProgramId attribute is not present in the response");}
		
		if (jsonObjAttribute.has("isImportInd")) {
		  isImportInd = jsonObjAttribute.getString("isImportInd");}
		else{LOGGER.debug("isImportInd attribute is not present in the response");
		Reporter.log("isImportInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("accountingDeptNbr")) {
		  accountingDeptNbr = jsonObjAttribute.getString("accountingDeptNbr");}
		else{LOGGER.debug("accountingDeptNbr attribute is not present in the response");
		Reporter.log("accountingDeptNbr attribute is not present in the response");}
		
		if (jsonObjAttribute.has("orderablePackGtin")) {
		  orderablePackGtin = jsonObjAttribute.getString("orderablePackGtin");}
		else{LOGGER.debug("orderablePackGtin attribute is not present in the response");
		Reporter.log("orderablePackGtin attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("assortmentTypeCode")) {
		  assortmentTypeCode = jsonObjAttribute.getString("assortmentTypeCode");}
		else{LOGGER.debug("assortmentTypeCode attribute is not present in the response");
		Reporter.log("assortmentTypeCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("lastUpdateUserid")) {
		  lastUpdateUserid = jsonObjAttribute.getString("lastUpdateUserid");}
		else{LOGGER.debug("lastUpdateUserid attribute is not present in the response");
		Reporter.log("lastUpdateUserid attribute is not present in the response");}
		
		if (jsonObjAttribute.has("reserveMerchandiseTypeCode")) {
		  reserveMerchandiseTypeCode = jsonObjAttribute.getString("reserveMerchandiseTypeCode");}
		else{LOGGER.debug("reserveMerchandiseTypeCode attribute is not present in the response");
		Reporter.log("reserveMerchandiseTypeCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("baseDivisionCode")) {
		 baseDivisionCode = jsonObjAttribute.getString("baseDivisionCode");}
		else{LOGGER.debug("baseDivisionCode attribute is not present in the response");
		Reporter.log("baseDivisionCode attribute is not present in the response");}
		
		if (jsonObjAttribute.has("warehousePackCalcMethodCode")) {
		  warehousePackCalcMethodCode = jsonObjAttribute.getString("warehousePackCalcMethodCode");}
		else{LOGGER.debug("warehousePackCalcMethodCode attribute is not present in the response");
		Reporter.log("warehousePackCalcMethodCode attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("warehousePackQtyUomCode")) {
		warehousePackQtyUomCode = jsonObjAttribute.getString("warehousePackQtyUomCode");}
		else{LOGGER.debug("warehousePackQtyUomCode attribute is not present in the response");
		Reporter.log("warehousePackQtyUomCode attribute is not present in the response");}
		
		
		JSONArray itemEligibilityStateCode1=jsonObjAttribute.getJSONArray("itemEligibilityStateCode");
		itemEligibilityStateCode = itemEligibilityStateCode1.toString();
		itemEligibilityStateCode=itemEligibilityStateCode.replace("[", "");
		itemEligibilityStateCode=itemEligibilityStateCode.replace("]", "");
		
		if (jsonObjAttribute.has("isShelfLabelRequiredInd")) {
		  isShelfLabelRequiredInd = jsonObjAttribute.getString("isShelfLabelRequiredInd");}
		
		else{LOGGER.debug("isShelfLabelRequiredInd attribute is not present in the response");
		Reporter.log("isShelfLabelRequiredInd attribute is not present in the response");}
		
		if (jsonObjAttribute.has("supplierSeqNbr")) {
		  supplierSeqNbr = jsonObjAttribute.getString("supplierSeqNbr");}
		else{LOGGER.debug("supplierSeqNbr attribute is not present in the response");
		Reporter.log("supplierSeqNbr attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("supplyItemPrimaryDescription")) {
		  supplyItemPrimaryDescription = jsonObjAttribute.getString("supplyItemPrimaryDescription");}
		else{LOGGER.debug("supplyItemPrimaryDescription attribute is not present in the response");
		Reporter.log("supplyItemPrimaryDescription attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("supplyItemEffectiveDate")) {
		  supplyItemEffectiveDate = jsonObjAttribute.getString("supplyItemEffectiveDate");}
		else{LOGGER.debug("supplyItemEffectiveDate attribute is not present in the response");
		Reporter.log("supplyItemEffectiveDate attribute is not present in the response");}
		
		
		JSONArray originCountryCode1=jsonObjAttribute.getJSONArray("originCountryCode");
		originCountryCode=originCountryCode1.toString();
		originCountryCode=originCountryCode.replace("[", "");
		originCountryCode=originCountryCode.replace("]", "");
		originCountryCode=originCountryCode.replaceAll("\"", "");
		
		
		if (jsonObjAttribute.has("hasSecurityTagInd")) {
		   hasSecurityTagInd = jsonObjAttribute.getString("hasSecurityTagInd");}
		
		else{LOGGER.debug("hasSecurityTagInd attribute is not present in the response");
		Reporter.log("hasSecurityTagInd attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("isVariablePriceComparisionInd")) {
		  isVariablePriceComparisionInd = jsonObjAttribute.getString("isVariablePriceComparisionInd");}
		else{LOGGER.debug("isVariablePriceComparisionInd attribute is not present in the response");
		Reporter.log("isVariablePriceComparisionInd attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("supplyItemStatusCode")) {
		  supplyItemStatusCode = jsonObjAttribute.getString("supplyItemStatusCode");}
		else{LOGGER.debug("supplyItemStatusCode attribute is not present in the response");
		Reporter.log("supplyItemStatusCode attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("informationProviderId")) {
		  informationProviderId = jsonObjAttribute.getString("informationProviderId");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("supplyItemChangeReasonCode")) {
		  supplyItemChangeReasonCode = jsonObjAttribute.getString("supplyItemChangeReasonCode");}
		
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		if (jsonObjAttribute.has("supplierStockId")) {
		  supplierStockId = jsonObjAttribute.getString("supplierStockId");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("shelfLabel1Description")) {
		  shelfLabel1Description = jsonObjAttribute.getString("shelfLabel1Description");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("isVariableWeightInd")) {
		  isVariableWeightInd = jsonObjAttribute.getString("isVariableWeightInd");}
		
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("supplyItemCreateDate")) {
		  supplyItemCreateDate = jsonObjAttribute.getString("supplyItemCreateDate");}
		
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("orderablePackCostAmt")) {
		  orderablePackCostAmt = jsonObjAttribute.getString("orderablePackCostAmt");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("warehousePackQty")) {
		  warehousePackQty = jsonObjAttribute.getString("warehousePackQty");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("customerRetailAmt")) {
		  customerRetailAmt = jsonObjAttribute.getString("customerRetailAmt");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		if (jsonObjAttribute.has("itemTypeCode")) {
		  itemTypeCode = jsonObjAttribute.getString("itemTypeCode");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if (jsonObjAttribute.has("warehouseRotationTypeCode")) {
		  warehouseRotationTypeCode = jsonObjAttribute.getString("warehouseRotationTypeCode");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		
		if(jsonObjAttribute.has("baseRetailAmt")){
		baseRetailAmt=jsonObjAttribute.getString("baseRetailAmt");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("mbmTypeCode")){
		 mbmTypeCode = jsonObjAttribute.getString("mbmTypeCode");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("orderablePackQty")){
		orderablePackQty=jsonObjAttribute.getString("orderablePackQty");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("targetMarketCode")){
		targetMarketCode=jsonObjAttribute.getString("targetMarketCode");}
		else{LOGGER.debug("supplierSeqNbr attribute is not present in the response");
		Reporter.log("supplierSeqNbr attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("legacyProductNbr")){
		legacyProductNbr=jsonObjAttribute.getString("legacyProductNbr");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		if(jsonObjAttribute.has("replenishmentGroupNbr")){
		replenishmentGroupNbr=jsonObjAttribute.getString("replenishmentGroupNbr");}
		else{LOGGER.debug("replenishmentGroupNbr attribute is not present in the response");
		Reporter.log("replenishmentGroupNbr attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("unitCostAmt")){
		unitCostAmt=jsonObjAttribute.getString("unitCostAmt");}
		else{LOGGER.debug("unitCostAmt attribute is not present in the response");
		Reporter.log("unitCostAmt attribute is not present in the response");}
		
		if(jsonObjAttribute.has("supplierDeptNbr")){
		supplierDeptNbr=jsonObjAttribute.getString("supplierDeptNbr");}
		else{LOGGER.debug("supplierDeptNbr attribute is not present in the response");
		Reporter.log("supplierDeptNbr attribute is not present in the response");}
			
		
		if(jsonObjAttribute.has("isReplenishedByUnitInd")){
		isReplenishedByUnitInd=jsonObjAttribute.getString("isReplenishedByUnitInd");}
		else{LOGGER.debug("isReplenishedByUnitInd attribute is not present in the response");
		Reporter.log("isReplenishedByUnitInd attribute is not present in the response");}
		
		if(jsonObjAttribute.has("isRetailVatInclusiveInd")){
		isRetailVatInclusiveInd=jsonObjAttribute.getString("isRetailVatInclusiveInd");}
		else{LOGGER.debug("isRetailVatInclusiveInd attribute is not present in the response");
		Reporter.log("isRetailVatInclusiveInd attribute is not present in the response");}
		
		
		if(jsonObjAttribute.has("merchandiseCategoryNbr")){
			merchandiseCategoryNbr=jsonObjAttribute.getString("merchandiseCategoryNbr");}
			else{LOGGER.debug("merchandiseCategoryNbr attribute is not present in the response");
			Reporter.log("merchandiseCategoryNbr attribute is not present in the response");}
		
		if(jsonObjAttribute.has("merchandiseFamilyID")){
		merchandiseFamilyID=jsonObjAttribute.getString("merchandiseFamilyID");}
		else{LOGGER.debug("merchandiseFamilyID attribute is not present in the response");
		Reporter.log("merchandiseFamilyID attribute is not present in the response");}
		
		if(jsonObjAttribute.has("inforemReorderTypeCode")){
			inforemReorderTypeCode=jsonObjAttribute.getString("inforemReorderTypeCode");}
			else{LOGGER.debug("inforemReorderTypeCode attribute is not present in the response");
			Reporter.log("inforemReorderTypeCode attribute is not present in the response");}
		
		if(jsonObjAttribute.has("publishedGtin")){
			publishedGtin=jsonObjAttribute.getString("publishedGtin");}
			else{LOGGER.debug("publishedGtin attribute is not present in the response");
			Reporter.log("publishedGtin attribute is not present in the response");}
		
		if(jsonObjAttribute.has("supplyItemStatusChangeDate")){
			supplyItemStatusChangeDate=jsonObjAttribute.getString("supplyItemStatusChangeDate");}
			else{LOGGER.debug("supplyItemStatusChangeDate attribute is not present in the response");
			Reporter.log("supplyItemStatusChangeDate attribute is not present in the response");}
		
		
		JSONArray destinationFormatCodearray=jsonObjAttribute.getJSONArray("destinationFormatCode");
		
		  destinationFormatCode=destinationFormatCodearray.toString();
		  destinationFormatCode=destinationFormatCode.replace("[", "");
		  destinationFormatCode=destinationFormatCode.replace("]", "");
		  destinationFormatCode=destinationFormatCode.replaceAll("\"", "");
		
		    
		   
		  
		Collections.addAll(AttributesValue,"hierarchyNode:"+hierarchyNode, "sellTotalContentQty:"+sellTotalContentQty,
		 "sellTotalContentUomCode:"+sellTotalContentUomCode,"isReplenishableInd:"+isReplenishableInd,
		  "supplierNbr:"+supplierNbr,"warehouseMinOrderQty:"+warehouseMinOrderQty,"replacedItemNbr:"+replacedItemNbr,
		  "isCorporateReplenishableInd:"+isCorporateReplenishableInd,"lastUpdatePgmId:"+lastUpdatePgmId,
		  "warehousePackSellAmt:"+warehousePackSellAmt,"isCannedOrderInd:"+isCannedOrderInd,
		  "isEcommerceReplenishableInd:"+isEcommerceReplenishableInd,"itemNbr:"+itemNbr,"lifecycleStateCode:"+lifecycleStateCode,
		  "supplierLeadTimeUomCode:"+supplierLeadTimeUomCode,"recipientGln:"+recipientGln,
		  "shelfLabel2Description:"+shelfLabel2Description,"warehousePackCostAmt:"+warehousePackCostAmt,"deptNbr:"+deptNbr,
		  "lastUpdateTimestamp:"+lastUpdateTimestamp,"orderablePackTypeCode:"+orderablePackTypeCode,
		  "buyingRegionCode:"+buyingRegionCode,"isCancelWhenOutInd:"+isCancelWhenOutInd,"lastUpdateTs:"+lastUpdateTs,
		  "isOfferedForSaleInd:"+isOfferedForSaleInd,"warehouseAlignmentCode:"+warehouseAlignmentCode,
		  "createTs:"+createTs,"orderablePackQtyUomCode:"+orderablePackQtyUomCode,"orderablePackQty:"+orderablePackQty,"consumableGtin:"+consumableGtin,
		  "baseRetailUomCode:"+baseRetailUomCode,"createUserid:"+createUserid,"subclassNbr:"+subclassNbr,
		  "replenishSubTypeCode:"+replenishSubTypeCode,"informationProviderTypeCode:"+informationProviderTypeCode,
		  "fppRetardRangeInd:"+fppRetardRangeInd,"sendStoreDate:"+sendStoreDate,"financialReportingGroupCode:"+financialReportingGroupCode,
		  "finelineNbr:"+finelineNbr,"hasRigidPlasticPackagingContainerInd:"+hasRigidPlasticPackagingContainerInd,
		  "isRetailNotifyStoreInd:"+isRetailNotifyStoreInd,"itemChangeSendWalmartWeekNbr:"+itemChangeSendWalmartWeekNbr,
		  "supplyItemStatusChangeTimestamp:"+supplyItemStatusChangeTimestamp,"hasRfidInd:"+hasRfidInd,
		  "isBackroomScaleInd:"+isBackroomScaleInd,"omitTraitCode:"+omitTraitCode,"supplyItemExpireDate:"+supplyItemExpireDate,
		  "lastUpdateProgramId:"+lastUpdateProgramId,"isImportInd:"+isImportInd,"accountingDeptNbr:"+accountingDeptNbr,
		  "orderablePackGtin:"+orderablePackGtin,"supplierSeqNbr:"+supplierSeqNbr,"assortmentTypeCode:"+assortmentTypeCode,
		  "lastUpdateUserid:"+lastUpdateUserid,"reserveMerchandiseTypeCode:"+reserveMerchandiseTypeCode,
		  "baseDivisionCode:"+baseDivisionCode,"warehousePackCalcMethodCode:"+warehousePackCalcMethodCode,
		  "isShelfLabelRequiredInd:"+isShelfLabelRequiredInd,"informationProviderId:"+informationProviderId,
		  "supplyItemPrimaryDescription:"+supplyItemPrimaryDescription,"supplyItemEffectiveDate:"+supplyItemEffectiveDate,
		  "hasSecurityTagInd:"+hasSecurityTagInd,"isVariablePriceComparisionInd:"+isVariablePriceComparisionInd,
		  "supplyItemStatusCode:"+supplyItemStatusCode,"itemTypeCode:"+itemTypeCode,"warehouseRotationTypeCode:"+warehouseRotationTypeCode,
		  "destinationFormatCode:"+destinationFormatCode,"customerRetailAmt:"+customerRetailAmt,"warehousePackQtyUomCode:"+warehousePackQtyUomCode,
		  "warehousePackQty:"+warehousePackQty,"supplyItemChangeReasonCode:"+supplyItemChangeReasonCode,
		  "supplierStockId:"+supplierStockId,"shelfLabel1Description:"+shelfLabel1Description,"isVariableWeightInd:"+isVariableWeightInd,
		  "supplyItemCreateDate:"+supplyItemCreateDate,"orderablePackCostAmt:"+orderablePackCostAmt,"factoryId:"+factoryId,"baseRetailAmt:"+baseRetailAmt,"originCountryCode:"+originCountryCode,"mbmTypeCode:"+mbmTypeCode,"targetMarketCode:"+targetMarketCode,"itemEligibilityStateCode:"+itemEligibilityStateCode,
		"legacyProductNbr:"+legacyProductNbr,"replenishmentGroupNbr:"+replenishmentGroupNbr,"unitCostAmt:"+unitCostAmt,"supplierDeptNbr:"+supplierDeptNbr,"isReplenishedByUnitInd:"+isReplenishedByUnitInd,"isRetailVatInclusiveInd:"+isRetailVatInclusiveInd,
		"merchandiseFamilyID:"+merchandiseFamilyID,"omitTraitSequenceNbr:"+omitTraitSequenceNbr,"omitTraitConnectorCode:"+omitTraitConnectorCode,"omitTraitNbr:"+omitTraitNbr,
		"inforemReorderTypeCode:"+inforemReorderTypeCode,"publishedGtin:"+publishedGtin,"userKey:"+userKey,"supplyItemStatusChangeDate:"+supplyItemStatusChangeDate,
		"merchandiseCategoryNbr:"+merchandiseCategoryNbr,"sellTotalContentQty:"+sellTotalContentQty1,"supplyItemReplenishmentGroupNbr:"+supplyItemReplenishmentGroupNbr,"warehousePackGtin:"+warehousePackGtin,"buyerUserId:"+buyerUserId,"merchandiseSubcategoryNbr:"+merchandiseSubcategoryNbr);
		Collections.sort(AttributesValue);
		  for(int m=0; m<AttributesValue.size(); m++){
				
				 if(AttributesValue.get(m).contains("defaultString")||AttributesValue.get(m).equalsIgnoreCase("defaultString")||AttributesValue.get(m).matches("defaultString")){
//					 System.out.println("=================>"+AttributesValue.get(m));
					 AttributesValue.remove(m);
				 }
				}
		  for(int m=0; m<AttributesValue.size(); m++){
				
				 if(AttributesValue.get(m).contains("defaultString")||AttributesValue.get(m).equalsIgnoreCase("defaultString")||AttributesValue.get(m).matches("defaultString")){
//					 System.out.println("=================>"+AttributesValue.get(m));
					 AttributesValue.remove(m);
				 }
				}
//		for(int m=0; m<AttributesValue.size(); m++){
//			if(AttributesValue.get(m).contains("replacedItemNbr")){
//				 if(AttributesValue.get(m).contains("null")){
//					 AttributesValue.remove(AttributesValue.get(m));
//					
//				 }
//			 }
//		  }
		  Collections.sort(AttributesValue);
		   return AttributesValue;
		}

 public List<List<String>> siAttributeValidationgetbyconsumableGTIN(Response response)throws JSONException {
//	String description = null, destinationOmittraits=null,userKey = null,omitTraitSequenceNbr = null, omitTraitConnectorCode=null,omitTraitNbr=null,itemEligibilityStateCode=null;
//	String id = null, hierarchyNode = null, sellTotalContentQty = null, sellTotalContentUomCode = null, isReplenishableInd = null, supplierNbr = null, warehouseMinOrderQty = null, replacedItemNbr = null, isCorporateReplenishableInd = null, lastUpdatePgmId = null, warehousePackSellAmt = null, isCannedOrderInd = null, isEcommerceReplenishableInd = null, itemNbr = null, lifecycleStateCode = null, supplierLeadTimeUomCode = null, recipientGln = null, shelfLabel2Description = null, warehousePackCostAmt = null, deptNbr = null, lastUpdateTimestamp = null, orderablePackTypeCode = null, buyingRegionCode = null, isCancelWhenOutInd = null, lastUpdateTs = null, isOfferedForSaleInd = null, warehouseAlignmentCode = null, createTs = null, orderablePackQtyUomCode = null, consumableGtin = null, baseRetailUomCode = null, createUserid = null, subclassNbr = null, replenishSubTypeCode = null, informationProviderTypeCode = null, fppRetardRangeInd = null, sendStoreDate = null, financialReportingGroupCode = null, finelineNbr = null, hasRigidPlasticPackagingContainerInd = null, isRetailNotifyStoreInd = null, itemChangeSendWalmartWeekNbr = null, supplyItemStatusChangeTimestamp = null, hasRfidInd = null, isBackroomScaleInd = null, omitTraitCode = null, supplyItemExpireDate = null, lastUpdateProgramId = null, isImportInd = null, accountingDeptNbr = null, orderablePackGtin = null, supplierSeqNbr = null, assortmentTypeCode = null, lastUpdateUserid = null, reserveMerchandiseTypeCode = null, baseDivisionCode = null, warehousePackCalcMethodCode = null, isShelfLabelRequiredInd = null, informationProviderId = null, supplyItemPrimaryDescription = null, supplyItemEffectiveDate = null, hasSecurityTagInd = null, isVariablePriceComparisionInd = null, supplyItemStatusCode = null, itemTypeCode = null, warehouseRotationTypeCode = null, customerRetailAmt = null, warehousePackQty = null, supplyItemChangeReasonCode = null, supplierStockId = null, shelfLabel1Description = null, isVariableWeightInd = null, supplyItemCreateDate = null, orderablePackCostAmt = null, baseRetailAmt = null;
//	String mbmTypeCode = null, orderablePackQty = null, targetMarketCode = null, legacyProductNbr = null, replenishmentGroupNbr = null, unitCostAmt = null, supplierDeptNbr = null, isReplenishedByUnitInd = null, isRetailVatInclusiveInd = null, merchandiseFamilyID = null, destinationFormatCode = null, factoryId = null, originCountryCode = null, warehousePackQtyUomCode = null, orderablePackCostCurrencyCode = null;
//	String inforemReorderTypeCode = null, merchandiseSubcategoryNbr = null, merchandiseCategoryNbr = null, seasonCode = null, seasonYearNbr = null;
//	String warehousePackGtin = null, warehousePackSellCurrencyCode = null, shelfLabel4Description = null, warehousePackCostCurrencyCode = null;
//	String shelfLabel3Description = null, merchandiseProgramId = null,sendTraitConnectorCode=null,sendTraitSequenceNbr=null,sendTraitNbr=null;
	String outputResponse = response.asString();
	JSONArray jsonarray = new JSONArray(outputResponse);
	
	List<String> reponselistItem1=new ArrayList<String>();
	List<String> reponselistItem2= new ArrayList<String>();
	List<List<String>> responseValidate = new ArrayList<List<String>>();
	List<String> ItemNumber=new ArrayList<String>();
	int count=0;
	List<String> responselist=new ArrayList<String>();
	
	for (int i = 0; i < jsonarray.length(); i++) {
		String description = null,assembledOriginCountryCode=null, destinationOmittraits=null,userKey = null,omitTraitSequenceNbr = null, omitTraitConnectorCode=null,omitTraitNbr=null,itemEligibilityStateCode=null;
		String id = null, hierarchyNode = null, sellTotalContentQty = null, sellTotalContentUomCode = null, isReplenishableInd = null, supplierNbr = null, warehouseMinOrderQty = null, replacedItemNbr = null, isCorporateReplenishableInd = null, lastUpdatePgmId = null, warehousePackSellAmt = null, isCannedOrderInd = null, isEcommerceReplenishableInd = null, itemNbr = null, lifecycleStateCode = null, supplierLeadTimeUomCode = null, recipientGln = null, shelfLabel2Description = null, warehousePackCostAmt = null, deptNbr = null, lastUpdateTimestamp = null, orderablePackTypeCode = null, buyingRegionCode = null, isCancelWhenOutInd = null, lastUpdateTs = null, isOfferedForSaleInd = null, warehouseAlignmentCode = null, createTs = null, orderablePackQtyUomCode = null, consumableGtin = null, baseRetailUomCode = null, createUserid = null, subclassNbr = null, replenishSubTypeCode = null, informationProviderTypeCode = null, fppRetardRangeInd = null, sendStoreDate = null, financialReportingGroupCode = null, finelineNbr = null, hasRigidPlasticPackagingContainerInd = null, isRetailNotifyStoreInd = null, itemChangeSendWalmartWeekNbr = null, supplyItemStatusChangeTimestamp = null, hasRfidInd = null, isBackroomScaleInd = null, omitTraitCode = null, supplyItemExpireDate = null, lastUpdateProgramId = null, isImportInd = null, accountingDeptNbr = null, orderablePackGtin = null, supplierSeqNbr = null, assortmentTypeCode = null, lastUpdateUserid = null, reserveMerchandiseTypeCode = null, baseDivisionCode = null, warehousePackCalcMethodCode = null, isShelfLabelRequiredInd = null, informationProviderId = null, supplyItemPrimaryDescription = null, supplyItemEffectiveDate = null, hasSecurityTagInd = null, isVariablePriceComparisionInd = null, supplyItemStatusCode = null, itemTypeCode = null, warehouseRotationTypeCode = null, customerRetailAmt = null, warehousePackQty = null, supplyItemChangeReasonCode = null, supplierStockId = null, shelfLabel1Description = null, isVariableWeightInd = null, supplyItemCreateDate = null, orderablePackCostAmt = null, baseRetailAmt = null;
		String mbmTypeCode = null, orderablePackQty = null, targetMarketCode = null, legacyProductNbr = null, replenishmentGroupNbr = null, unitCostAmt = null, supplierDeptNbr = null, isReplenishedByUnitInd = null, isRetailVatInclusiveInd = null, merchandiseFamilyID = null, destinationFormatCode = null, factoryId = null, originCountryCode = null, warehousePackQtyUomCode = null, orderablePackCostCurrencyCode = null;
		String inforemReorderTypeCode = null, merchandiseSubcategoryNbr = null, merchandiseCategoryNbr = null, seasonCode = null, seasonYearNbr = null;
		String warehousePackGtin = null, warehousePackSellCurrencyCode = null, shelfLabel4Description = null, warehousePackCostCurrencyCode = null;
		String shelfLabel3Description = null, merchandiseProgramId = null,sendTraitConnectorCode=null,sendTraitSequenceNbr=null,sendTraitNbr=null;
		String componentOriginCountryCode=null,informationProviderGln=null;
		//		String outputResponse = response.asString();
		
//		responselist.clear();
		JSONObject jsonobject = jsonarray.getJSONObject(i);
		if (jsonobject.has("id")) {
			id = jsonobject.getString("id");
		} else {
			LOGGER.debug("The response not having id attribute");
			Reporter.log("The response not having id attribute");
		}
		if (jsonobject.has("description")) {
			description = jsonobject.getString("description");
		} else {
			LOGGER.debug("The response not having description attribute");
			Reporter.log("The response not having description attribute");
		}
		if (jsonobject.has("userKey")) {
			userKey = jsonobject.getString("userKey");
		} else {
			LOGGER.debug("The response not having userKey attribute");
			Reporter.log("The response not having userKey attribute");
		}
		if (jsonobject.has("hierarchyNode")) {
			hierarchyNode = jsonobject.getString("hierarchyNode");
		} else {
			LOGGER.debug("The response not having hierarchyNode attribute");
			Reporter.log("The response not having hierarchyNode attribute");
		}
		JSONObject attributes = jsonobject.getJSONObject("attributes");
		if (attributes.has("isReplenishableInd")) {
			isReplenishableInd = attributes.getString("isReplenishableInd");
		} else {
			LOGGER.debug("isReplenishableInd attribute is not present in the response");
			Reporter.log("isReplenishableInd attribute is not present in the response");
		}
		if (attributes.has("supplierNbr")) {
			supplierNbr = attributes.getString("supplierNbr");
		} else {
			LOGGER.debug("supplierNbr attribute is not present in the response");
			Reporter.log("supplierNbr attribute is not present in the response");
		}
		if (attributes.has("warehouseMinOrderQty")) {
			warehouseMinOrderQty = attributes
					.getString("warehouseMinOrderQty");
		} else {
			LOGGER.debug("warehouseMinOrderQty attribute is not present in the response");
			Reporter.log("warehouseMinOrderQty attribute is not present in the response");
		}
		if (attributes.has("isCorporateReplenishableInd")) {
			isCorporateReplenishableInd = attributes
					.getString("isCorporateReplenishableInd");
		} else {
			LOGGER.debug("isCorporateReplenishableInd attribute is not present in the response");
			Reporter.log("isCorporateReplenishableInd attribute is not present in the response");
		}
		if (attributes.has("lastUpdatePgmId")) {
			lastUpdatePgmId = attributes.getString("lastUpdatePgmId");
		} else {
			LOGGER.debug("lastUpdatePgmId attribute is not present in the response");
			Reporter.log("lastUpdatePgmId attribute is not present in the response");
		}
		if (attributes.has("warehousePackSellAmt")) {
			warehousePackSellAmt = attributes
					.getString("warehousePackSellAmt");
		} else {
			LOGGER.debug("warehousePackSellAmt attribute is not present in the response");
			Reporter.log("warehousePackSellAmt attribute is not present in the response");
		}
		if (attributes.has("isCannedOrderInd")) {
			isCannedOrderInd = attributes.getString("isCannedOrderInd");
		} else {
			LOGGER.debug("isCannedOrderInd attribute is not present in the response");
			Reporter.log("isCannedOrderInd attribute is not present in the response");
		}
		// ------------------------------------------------------
		if (attributes.has("isEcommerceReplenishableInd")) {
			isEcommerceReplenishableInd = attributes
					.getString("isEcommerceReplenishableInd");
		} else {
			LOGGER.debug("isEcommerceReplenishableInd attribute is not present in the response");
			Reporter.log("isEcommerceReplenishableInd attribute is not present in the response");
		}
		if (attributes.has("itemNbr")) {
			itemNbr = attributes.getString("itemNbr");
		} else {
			LOGGER.debug("itemNbr attribute is not present in the response");
			Reporter.log("itemNbr attribute is not present in the response");
		}
		if(attributes.has("createTs")){
			createTs=attributes.getString("createTs");
			createTs=createTs.toString();
		}else{
			LOGGER.debug("createTs attribute is not present in the response");
			Reporter.log("createTs attribute is not present in the response");
		}
		if (attributes.has("orderablePackCostCurrencyCode")) {
			orderablePackCostCurrencyCode = attributes
					.getString("orderablePackCostCurrencyCode");
		} else {
			LOGGER.debug("orderablePackCostCurrencyCode attribute is not present in the response");
			Reporter.log("orderablePackCostCurrencyCode attribute is not present in the response");
		}
		if (attributes.has("lifecycleStateCode")) {
			lifecycleStateCode = attributes.getString("lifecycleStateCode");
		} else {
			LOGGER.debug("lifecycleStateCode attribute is not present in the response");
			Reporter.log("lifecycleStateCode attribute is not present in the response");
		}
		if (attributes.has("shelfLabel2Description")) {
			shelfLabel2Description = attributes
					.getString("shelfLabel2Description");
		} else {
			LOGGER.debug("shelfLabel2Description attribute is not present in the response");
			Reporter.log("shelfLabel2Description attribute is not present in the response");
		}
		if (attributes.has("warehousePackCostAmt")) {
			warehousePackCostAmt = attributes
					.getString("warehousePackCostAmt");
		} else {
			LOGGER.debug("warehousePackCostAmt attribute is not present in the response");
			Reporter.log("warehousePackCostAmt attribute is not present in the response");
		}
		if (attributes.has("deptNbr")) {
			deptNbr = attributes.getString("deptNbr");
		} else {
			LOGGER.debug("deptNbr attribute is not present in the response");
			Reporter.log("deptNbr attribute is not present in the response");
		}
		if (attributes.has("lastUpdateTimestamp")) {
			lastUpdateTimestamp = attributes
					.getString("lastUpdateTimestamp");
		} else {
			LOGGER.debug("lastUpdateTimestamp attribute is not present in the response");
			Reporter.log("lastUpdateTimestamp attribute is not present in the response");
		}
		if (attributes.has("orderablePackTypeCode")) {
			orderablePackTypeCode = attributes
					.getString("orderablePackTypeCode");
		} else {
			LOGGER.debug("orderablePackTypeCode attribute is not present in the response");
			Reporter.log("orderablePackTypeCode attribute is not present in the response");
		}
		if (attributes.has("buyingRegionCode")) {
			buyingRegionCode = attributes.getString("buyingRegionCode");
		} else {
			LOGGER.debug("buyingRegionCode attribute is not present in the response");
			Reporter.log("buyingRegionCode attribute is not present in the response");
		}
		if (attributes.has("inforemReorderTypeCode")) {
			inforemReorderTypeCode = attributes
					.getString("inforemReorderTypeCode");
		} else {
			LOGGER.debug("inforemReorderTypeCode attribute is not present in the response");
			Reporter.log("inforemReorderTypeCode attribute is not present in the response");
		}
		if (attributes.has("isCancelWhenOutInd")) {
			isCancelWhenOutInd = attributes.getString("isCancelWhenOutInd");
		} else {
			LOGGER.debug("isCancelWhenOutInd attribute is not present in the response");
			Reporter.log("isCancelWhenOutInd attribute is not present in the response");
		}
		if (attributes.has("lastUpdateTs")) {
			lastUpdateTs = attributes.getString("lastUpdateTs");
		} else {
			LOGGER.debug("lastUpdateTs attribute is not present in the response");
			Reporter.log("lastUpdateTs attribute is not present in the response");
		}
		if (attributes.has("merchandiseSubcategoryNbr")) {
			merchandiseSubcategoryNbr = attributes
					.getString("merchandiseSubcategoryNbr");
		}

		else {
			LOGGER.debug("merchandiseSubcategoryNbr attribute is not present in the response");
			Reporter.log("merchandiseSubcategoryNbr attribute is not present in the response");
		}
		if (attributes.has("isOfferedForSaleInd")) {
			isOfferedForSaleInd = attributes
					.getString("isOfferedForSaleInd");
		} else {
			LOGGER.debug("isOfferedForSaleInd attribute is not present in the response");
			Reporter.log("isOfferedForSaleInd attribute is not present in the response");
		}
		if (attributes.has("warehouseAlignmentCode")) {
			warehouseAlignmentCode = attributes
					.getString("warehouseAlignmentCode");
		} else {
			LOGGER.debug("warehouseAlignmentCode attribute is not present in the response");
			Reporter.log("warehouseAlignmentCode attribute is not present in the response");
		}
		if (attributes.has("isEcommerceReplenishableInd")) {
			isEcommerceReplenishableInd = attributes.getString("isEcommerceReplenishableInd");
		} else {
			LOGGER.debug("isEcommerceReplenishableInd attribute is not present in the response");
			Reporter.log("isEcommerceReplenishableInd attribute is not present in the response");
		}
		if (attributes.has("orderablePackQtyUomCode")) {
			orderablePackQtyUomCode = attributes
					.getString("orderablePackQtyUomCode");
		} else {
			LOGGER.debug("orderablePackQtyUomCode attribute is not present in the response");
			Reporter.log("orderablePackQtyUomCode attribute is not present in the response");
		}
		if (attributes.has("consumableGtin")) {
			consumableGtin = attributes.getString("consumableGtin");
		} else {
			LOGGER.debug("consumableGtin attribute is not present in the response");
			Reporter.log("consumableGtin attribute is not present in the response");
		}
		if (attributes.has("baseRetailUomCode")) {
			baseRetailUomCode = attributes.getString("baseRetailUomCode");
		} else {
			LOGGER.debug("baseRetailUomCode attribute is not present in the response");
			Reporter.log("baseRetailUomCode attribute is not present in the response");
		}
		if (attributes.has("createUserid")) {
			createUserid = attributes.getString("createUserid");
		} else {
			LOGGER.debug("createUserid attribute is not present in the response");
			Reporter.log("createUserid attribute is not present in the response");
		}
		if (attributes.has("subclassNbr")) {
			subclassNbr = attributes.getString("subclassNbr");
		} else {
			LOGGER.debug("subclassNbr attribute is not present in the response");
			Reporter.log("subclassNbr attribute is not present in the response");
		}
		if (attributes.has("merchandiseCategoryNbr")) {
			merchandiseCategoryNbr = attributes
					.getString("merchandiseCategoryNbr");
		} else {
			LOGGER.debug("merchandiseCategoryNbr attribute is not present in the response");
			Reporter.log("merchandiseCategoryNbr attribute is not present in the response");
		}
		if (attributes.has("replenishSubTypeCode")) {
			replenishSubTypeCode = attributes
					.getString("replenishSubTypeCode");
		} else {
			LOGGER.debug("replenishSubTypeCode attribute is not present in the response");
			Reporter.log("replenishSubTypeCode attribute is not present in the response");
		}
		if (attributes.has("seasonCode")) {
			seasonCode = attributes.getString("seasonCode");
		} else {
			LOGGER.debug("seasonCode attribute is not present in the response");
			Reporter.log("seasonCode attribute is not present in the response");
		}
		if (attributes.has("informationProviderTypeCode")) {
			informationProviderTypeCode = attributes
					.getString("informationProviderTypeCode");
		} else {
			LOGGER.debug("informationProviderTypeCode attribute is not present in the response");
			Reporter.log("informationProviderTypeCode attribute is not present in the response");
		}
		if(attributes.has("factoryId")){
		JSONArray factoryid=attributes.getJSONArray("factoryId");
		factoryId=factoryid.toString();
		factoryId=factoryId.substring(1, factoryId.length()-1);
		}
		else{
			LOGGER.debug("factoryid attribute is not present in the response");
			Reporter.log("factoryid attribute is not present in the response");
		}
		
		if(attributes.has("assembledOriginCountryCode")){
			JSONArray AssembledOriginCountryCode=attributes.getJSONArray("assembledOriginCountryCode");
			assembledOriginCountryCode=AssembledOriginCountryCode.toString();
			assembledOriginCountryCode=assembledOriginCountryCode.substring(2, assembledOriginCountryCode.length()-2);
		}
			else{
				LOGGER.debug("assembledOriginCountryCode attribute is not present in the response");
				Reporter.log("assembledOriginCountryCode attribute is not present in the response");
			}
		
		if(attributes.has("componentOriginCountryCode")){
			JSONArray ComponentOriginCountryCode=attributes.getJSONArray("componentOriginCountryCode");
			componentOriginCountryCode=ComponentOriginCountryCode.toString();
			componentOriginCountryCode=componentOriginCountryCode.substring(2, componentOriginCountryCode.length()-2);
			}
			else{
				LOGGER.debug("componentOriginCountryCode attribute is not present in the response");
				Reporter.log("componentOriginCountryCode attribute is not present in the response");
			}
		if (attributes.has("informationProviderGln")) {
			informationProviderGln = attributes.getString("informationProviderGln");
		} else {
			LOGGER.debug("informationProviderGln attribute is not present in the response");
			Reporter.log("informationProviderGln attribute is not present in the response");
		}
		
		
		if (attributes.has("fppRetardRangeInd")) {
			fppRetardRangeInd = attributes.getString("fppRetardRangeInd");
		} else {
			LOGGER.debug("fppRetardRangeInd attribute is not present in the response");
			Reporter.log("fppRetardRangeInd attribute is not present in the response");
		}
		if (attributes.has("seasonYearNbr")) {
			seasonYearNbr = attributes.getString("seasonYearNbr");
		} else {
			LOGGER.debug("seasonYearNbr attribute is not present in the response");
			Reporter.log("seasonYearNbr attribute is not present in the response");
		}
		if (attributes.has("warehousePackGtin")) {
			warehousePackGtin = attributes.getString("warehousePackGtin");
		} else {
			LOGGER.debug("warehousePackGtin attribute is not present in the response");
			Reporter.log("warehousePackGtin attribute is not present in the response");
		}
		if (attributes.has("sendStoreDate")) {
			sendStoreDate = attributes.getString("sendStoreDate");
		} else {
			LOGGER.debug("sendStoreDate attribute is not present in the response");
			Reporter.log("sendStoreDate attribute is not present in the response");
		}
		if (attributes.has("warehousePackSellCurrencyCode")) {
			warehousePackSellCurrencyCode = attributes
					.getString("warehousePackSellCurrencyCode");
		} else {
			LOGGER.debug("warehousePackSellCurrencyCode attribute is not present in the response");
			Reporter.log("warehousePackSellCurrencyCode attribute is not present in the response");
		}
		if (attributes.has("shelfLabel4Description")) {
			shelfLabel4Description = attributes
					.getString("shelfLabel4Description");
		} else {
			LOGGER.debug("shelfLabel4Description attribute is not present in the response");
			Reporter.log("shelfLabel4Description attribute is not present in the response");
		}
		if (attributes.has("financialReportingGroupCode")) {
			financialReportingGroupCode = attributes
					.getString("financialReportingGroupCode");
		} else {
			LOGGER.debug("financialReportingGroupCode attribute is not present in the response");
			Reporter.log("financialReportingGroupCode attribute is not present in the response");
		}
		// destinationOmitTraits
		if(attributes.has("destinationOmitTraits")){
		JSONArray destinationOmitTraits1=attributes.getJSONArray("destinationOmitTraits");
		JSONObject destinationOmitTraitsObject=destinationOmitTraits1.getJSONObject(0);
		JSONArray traitGroup=destinationOmitTraitsObject.getJSONArray("traitGroup");
		List<String> destinationOmittraitsArray=new ArrayList<String>();
		
		for(int j=0; j<3; j++){
			JSONObject traitGroupObject=traitGroup.getJSONObject(j);

			
				if(traitGroupObject.has("omitTraitSequenceNbr")){
					omitTraitSequenceNbr =traitGroupObject.getString("omitTraitSequenceNbr");
				}
				if(traitGroupObject.has("omitTraitConnectorCode")){
					omitTraitConnectorCode =traitGroupObject.getString("omitTraitConnectorCode");
				}
				if(traitGroupObject.has("omitTraitNbr")){
					omitTraitNbr =traitGroupObject.getString("omitTraitNbr");
				}
				Collections.addAll(responselist,"omitTraitSequenceNbr:"+omitTraitSequenceNbr,"omitTraitConnectorCode:"+omitTraitConnectorCode,"omitTraitNbr:"+omitTraitNbr);
		
		}
	
		}
		else{
			LOGGER.debug("destinationOmitTraits attribute is not present in the response");
			Reporter.log("destinationOmitTraits attribute is not present in the response");
		}
		if (attributes.has("baseRetailAmt")) {
			baseRetailAmt = attributes.getString("baseRetailAmt");
		} else {
			LOGGER.debug("baseRetailAmt attribute is not present in the response");
			Reporter.log("baseRetailAmt attribute is not present in the response");
		}
		if (attributes.has("finelineNbr")) {
			finelineNbr = attributes.getString("finelineNbr");
		} else {
			LOGGER.debug("finelineNbr attribute is not present in the response");
			Reporter.log("finelineNbr attribute is not present in the response");
		}
		if (attributes.has("warehousePackCostCurrencyCode")) {
			warehousePackCostCurrencyCode = attributes
					.getString("warehousePackCostCurrencyCode");
		} else {
			LOGGER.debug("warehousePackCostCurrencyCode attribute is not present in the response");
			Reporter.log("warehousePackCostCurrencyCode attribute is not present in the response");
		}
		if (attributes.has("supplyItemExpireDate")) {
			supplyItemExpireDate = attributes
					.getString("supplyItemExpireDate");
		} else {
			LOGGER.debug("supplyItemExpireDate attribute is not present in the response");
			Reporter.log("supplyItemExpireDate attribute is not present in the response");
		}
		if (attributes.has("lastUpdateProgramId")) {
			lastUpdateProgramId = attributes.getString("lastUpdateProgramId");
		} else {
			LOGGER.debug("lastUpdateProgramId attribute is not present in the response");
			Reporter.log("lastUpdateProgramId attribute is not present in the response");
		}
		if (attributes.has("shelfLabel3Description")) {
			shelfLabel3Description = attributes
					.getString("shelfLabel3Description");
		} else {
			LOGGER.debug("shelfLabel3Description attribute is not present in the response");
			Reporter.log("shelfLabel3Description attribute is not present in the response");
		}
		if (attributes.has("isImportInd")) {
			isImportInd = attributes.getString("isImportInd");
		} else {
			LOGGER.debug("isImportInd attribute is not present in the response");
			Reporter.log("isImportInd attribute is not present in the response");
		}
		if (attributes.has("accountingDeptNbr")) {
			accountingDeptNbr = attributes.getString("accountingDeptNbr");
		} else {
			LOGGER.debug("accountingDeptNbr attribute is not present in the response");
			Reporter.log("accountingDeptNbr attribute is not present in the response");
		}
		if (attributes.has("orderablePackGtin")) {
			orderablePackGtin = attributes.getString("orderablePackGtin");
		} else {
			LOGGER.debug("orderablePackGtin attribute is not present in the response");
			Reporter.log("orderablePackGtin attribute is not present in the response");
		}
		if (attributes.has("lastUpdateUserid")) {
			lastUpdateUserid = attributes.getString("lastUpdateUserid");
		} else {
			LOGGER.debug("lastUpdateUserid attribute is not present in the response");
			Reporter.log("lastUpdateUserid attribute is not present in the response");
		}
		if (attributes.has("reserveMerchandiseTypeCode")) {
			reserveMerchandiseTypeCode = attributes
					.getString("reserveMerchandiseTypeCode");
		} else {
			LOGGER.debug("reserveMerchandiseTypeCode attribute is not present in the response");
			Reporter.log("reserveMerchandiseTypeCode attribute is not present in the response");
		}
		if (attributes.has("baseDivisionCode")) {
			baseDivisionCode = attributes.getString("baseDivisionCode");
		} else {
			LOGGER.debug("baseDivisionCode attribute is not present in the response");
			Reporter.log("baseDivisionCode attribute is not present in the response");
		}
		if (attributes.has("warehousePackCalcMethodCode")) {
			warehousePackCalcMethodCode = attributes
					.getString("warehousePackCalcMethodCode");
		} else {
			LOGGER.debug("warehousePackCalcMethodCode attribute is not present in the response");
			Reporter.log("warehousePackCalcMethodCode attribute is not present in the response");
		}
		// item eligibility status code
		if(attributes.has("itemEligibilityStateCode")){
		JSONArray itemEligibilityStateCode1=attributes.getJSONArray("itemEligibilityStateCode");
		itemEligibilityStateCode=itemEligibilityStateCode1.toString();
		itemEligibilityStateCode=itemEligibilityStateCode.substring(1, itemEligibilityStateCode.length()-1);

		}
		else{
			LOGGER.debug("itemEligibilityStateCode attribute is not present in the response");
			Reporter.log("itemEligibilityStateCode attribute is not present in the response");
		}
		if (attributes.has("isShelfLabelRequiredInd")) {
			isShelfLabelRequiredInd = attributes
					.getString("isShelfLabelRequiredInd");
		} else {
			LOGGER.debug("isShelfLabelRequiredInd attribute is not present in the response");
			Reporter.log("isShelfLabelRequiredInd attribute is not present in the response");
		}
		if (attributes.has("supplierSeqNbr")) {
			supplierSeqNbr = attributes.getString("supplierSeqNbr");
		} else {
			LOGGER.debug("supplierSeqNbr attribute is not present in the response");
			Reporter.log("supplierSeqNbr attribute is not present in the response");
		}
		if (attributes.has("supplyItemPrimaryDescription")) {
			supplyItemPrimaryDescription = attributes
					.getString("supplyItemPrimaryDescription");
		} else {
			LOGGER.debug("supplyItemPrimaryDescription attribute is not present in the response");
			Reporter.log("supplyItemPrimaryDescription attribute is not present in the response");
		}
		if (attributes.has("merchandiseProgramId")) {
			merchandiseProgramId = attributes
					.getString("merchandiseProgramId");
		} else {
			LOGGER.debug("merchandiseProgramId attribute is not present in the response");
			Reporter.log("merchandiseProgramId attribute is not present in the response");
		}
		if (attributes.has("supplyItemEffectiveDate")) {
			supplyItemEffectiveDate = attributes
					.getString("supplyItemEffectiveDate");
		} else {
			LOGGER.debug("supplyItemEffectiveDate attribute is not present in the response");
			Reporter.log("supplyItemEffectiveDate attribute is not present in the response");
		}
		// origin country code
		if(attributes.has("originCountryCode")){
		JSONArray originCountryCode1=attributes.getJSONArray("originCountryCode");
		originCountryCode=originCountryCode1.toString();
		originCountryCode=originCountryCode.substring(1, originCountryCode.length()-1);
		originCountryCode=originCountryCode.replaceAll("\"", "");

		}
		else{
			LOGGER.debug("originCountryCode attribute is not present in the response");
			Reporter.log("originCountryCode attribute is not present in the response");	
		}
		if (attributes.has("supplyItemStatusCode")) {
			supplyItemStatusCode = attributes
					.getString("supplyItemStatusCode");
		} else {
			LOGGER.debug("supplyItemStatusCode attribute is not present in the response");
			Reporter.log("supplyItemStatusCode attribute is not present in the response");
		}
		if (attributes.has("informationProviderId")) {
			informationProviderId = attributes
					.getString("informationProviderId");
		} else {
			LOGGER.debug("informationProviderId attribute is not present in the response");
			Reporter.log("informationProviderId attribute is not present in the response");
		}
		if (attributes.has("shelfLabel1Description")) {
			shelfLabel1Description = attributes
					.getString("shelfLabel1Description");
		} else {
			LOGGER.debug("shelfLabel1Description attribute is not present in the response");
			Reporter.log("shelfLabel1Description attribute is not present in the response");
		}
		if (attributes.has("supplyItemCreateDate")) {
			supplyItemCreateDate = attributes
					.getString("supplyItemCreateDate");
		} else {
			LOGGER.debug("supplyItemCreateDate attribute is not present in the response");
			Reporter.log("supplyItemCreateDate attribute is not present in the response");
		}
		if (attributes.has("orderablePackCostAmt")) {
			orderablePackCostAmt = attributes
					.getString("orderablePackCostAmt");
		} else {
			LOGGER.debug("orderablePackCostAmt attribute is not present in the response");
			Reporter.log("orderablePackCostAmt attribute is not present in the response");
		}
		if (attributes.has("warehousePackQty")) {
			warehousePackQty = attributes.getString("warehousePackQty");
		} else {
			LOGGER.debug("warehousePackQty attribute is not present in the response");
			Reporter.log("warehousePackQty attribute is not present in the response");
		}
		if (attributes.has("customerRetailAmt")) {
			customerRetailAmt = attributes.getString("customerRetailAmt");
		} else {
			LOGGER.debug("customerRetailAmt attribute is not present in the response");
			Reporter.log("customerRetailAmt attribute is not present in the response");
		}
		if (attributes.has("itemTypeCode")) {
			itemTypeCode = attributes.getString("itemTypeCode");
		} else {
			LOGGER.debug("itemTypeCode attribute is not present in the response");
			Reporter.log("itemTypeCode attribute is not present in the response");
		}
		if(attributes.has("destinationFormatCode")){
		JSONArray destinationFormatCode1=attributes.getJSONArray("destinationFormatCode");
		destinationFormatCode=destinationFormatCode1.toString();
		destinationFormatCode=destinationFormatCode.substring(1, destinationFormatCode.length()-1);
		destinationFormatCode=destinationFormatCode.replaceAll("\"", "");

		}
		else{
			LOGGER.debug("destinationFormatCode attribute is not present in the response");
			Reporter.log("destinationFormatCode attribute is not present in the response");
		}
		if (attributes.has("orderablePackQty")) {
			orderablePackQty = attributes.getString("orderablePackQty");
		} else {
			LOGGER.debug("orderablePackQty attribute is not present in the response");
			Reporter.log("orderablePackQty attribute is not present in the response");
		}
		if (attributes.has("targetMarketCode")) {
			targetMarketCode = attributes.getString("targetMarketCode");
		} else {
			LOGGER.debug("targetMarketCode attribute is not present in the response");
			Reporter.log("targetMarketCode attribute is not present in the response");
		}
		if (attributes.has("warehousePackQtyUomCode")) {
			warehousePackQtyUomCode = attributes
					.getString("warehousePackQtyUomCode");
		} else {
			LOGGER.debug("warehousePackQtyUomCode attribute is not present in the response");
			Reporter.log("warehousePackQtyUomCode attribute is not present in the response");
		}
		if (attributes.has("legacyProductNbr")) {
			legacyProductNbr = attributes.getString("legacyProductNbr");
		} else {
			LOGGER.debug("legacyProductNbr attribute is not present in the response");
			Reporter.log("legacyProductNbr attribute is not present in the response");
		}
		if (attributes.has("replenishmentGroupNbr")) {
			replenishmentGroupNbr = attributes
					.getString("replenishmentGroupNbr");
		} else {
			LOGGER.debug("replenishmentGroupNbr attribute is not present in the response");
			Reporter.log("replenishmentGroupNbr attribute is not present in the response");
		}

		if (attributes.has("supplierDeptNbr")) {
			supplierDeptNbr = attributes.getString("supplierDeptNbr");
		} else {
			LOGGER.debug("supplierDeptNbr attribute is not present in the response");
			Reporter.log("supplierDeptNbr attribute is not present in the response");
		}
		if (attributes.has("isReplenishedByUnitInd")) {
			isReplenishedByUnitInd = attributes
					.getString("isReplenishedByUnitInd");
		} else {
			LOGGER.debug("isReplenishedByUnitInd attribute is not present in the response");
			Reporter.log("isReplenishedByUnitInd attribute is not present in the response");
		}
		if (attributes.has("supplierDeptNbr")) {
			supplierDeptNbr = attributes.getString("supplierDeptNbr");
		} else {
			LOGGER.debug("supplierDeptNbr attribute is not present in the response");
			Reporter.log("supplierDeptNbr attribute is not present in the response");
		}
		if (attributes.has("merchandiseFamilyID")) {
			merchandiseFamilyID = attributes
					.getString("merchandiseFamilyID");
		} else {
			LOGGER.debug("merchandiseFamilyID attribute is not present in the response");
			Reporter.log("merchandiseFamilyID attribute is not present in the response");
		}
		if (attributes.has("mbmTypeCode")) {
			mbmTypeCode = attributes.getString("mbmTypeCode");
		} else {
			LOGGER.debug("mbmTypeCode attribute is not present in the response");
			Reporter.log("mbmTypeCode attribute is not present in the response");
		}
		
	    if(attributes.has("destinationSendTraits")){
	    	JSONArray destinationSendTraits=attributes.getJSONArray("destinationSendTraits");
	    	JSONObject destinationSendTraitsobject=destinationSendTraits.getJSONObject(0);
	    	JSONArray traitGroup=destinationSendTraitsobject.getJSONArray("traitGroup");
	    	JSONObject sendTraits=traitGroup.getJSONObject(0);
	    	if(sendTraits.has("sendTraitConnectorCode")){
	    		sendTraitConnectorCode=sendTraits.getString("sendTraitConnectorCode");
	    		
	    	}
	    	if(sendTraits.has("sendTraitNbr")){
	    		sendTraitNbr=sendTraits.getString("sendTraitNbr");
	    		
	    	}
	    	if(sendTraits.has("sendTraitSequenceNbr")){
	    		sendTraitSequenceNbr=sendTraits.getString("sendTraitSequenceNbr");
	    		
	    	}
	    }	
	    else{LOGGER.debug("destinationSendTraits attribute is not present in the response");
		Reporter.log("destinationSendTraits attribute is not present in the response");}
		
	    // Adding the newly added 21 attributes byDate 12 JAN
	    if (attributes.has("assortmentTypeCode")) {
	    	assortmentTypeCode = attributes.getString("assortmentTypeCode");
		} else {
			LOGGER.debug("assortmentTypeCode attribute is not present in the response");
			Reporter.log("assortmentTypeCode attribute is not present in the response");
		}
	    
	    if (attributes.has("hasRfidInd")) {
	    	hasRfidInd = attributes.getString("hasRfidInd");
		} else {
			LOGGER.debug("hasRfidInd attribute is not present in the response");
			Reporter.log("hasRfidInd attribute is not present in the response");
		}
	    
	    if (attributes.has("hasSecurityTagInd")) {
	    	hasSecurityTagInd = attributes.getString("hasSecurityTagInd");
		} else {
			LOGGER.debug("hasSecurityTagInd attribute is not present in the response");
			Reporter.log("hasSecurityTagInd attribute is not present in the response");
		}
	    
	    if (attributes.has("isBackroomScaleInd")) {
	    	isBackroomScaleInd = attributes.getString("isBackroomScaleInd");
		} else {
			LOGGER.debug("isBackroomScaleInd attribute is not present in the response");
			Reporter.log("isBackroomScaleInd attribute is not present in the response");
		}
	    
	    if (attributes.has("isRetailNotifyStoreInd")) {
	    	isRetailNotifyStoreInd = attributes.getString("isRetailNotifyStoreInd");
		} else {
			LOGGER.debug("isRetailNotifyStoreInd attribute is not present in the response");
			Reporter.log("isRetailNotifyStoreInd attribute is not present in the response");
		}
	    
	    if (attributes.has("isRetailVatInclusiveInd")) {
	    	isRetailVatInclusiveInd = attributes.getString("isRetailVatInclusiveInd");
		} else {
			LOGGER.debug("isRetailVatInclusiveInd attribute is not present in the response");
			Reporter.log("isRetailVatInclusiveInd attribute is not present in the response");
		}
	    
	    if (attributes.has("isVariablePriceComparisionInd")) {
	    	isVariablePriceComparisionInd = attributes.getString("isVariablePriceComparisionInd");
		} else {
			LOGGER.debug("isVariablePriceComparisionInd attribute is not present in the response");
			Reporter.log("isVariablePriceComparisionInd attribute is not present in the response");
		}
	    
	    if (attributes.has("isVariableWeightInd")) {
	    	isVariableWeightInd = attributes.getString("isVariableWeightInd");
		} else {
			LOGGER.debug("isVariableWeightInd attribute is not present in the response");
			Reporter.log("isVariableWeightInd attribute is not present in the response");
		}
	    
	    if (attributes.has("itemChangeSendWalmartWeekNbr")) {
	    	itemChangeSendWalmartWeekNbr = attributes.getString("itemChangeSendWalmartWeekNbr");
		} else {
			LOGGER.debug("itemChangeSendWalmartWeekNbr attribute is not present in the response");
			Reporter.log("itemChangeSendWalmartWeekNbr attribute is not present in the response");
		}
	    
	    if (attributes.has("omitTraitCode")) {
	    	omitTraitCode = attributes.getString("omitTraitCode");
		} else {
			LOGGER.debug("omitTraitCode attribute is not present in the response");
			Reporter.log("omitTraitCode attribute is not present in the response");
		}
	    
	    if (attributes.has("recipientGln")) {
	    	recipientGln = attributes.getString("recipientGln");
		} else {
			LOGGER.debug("recipientGln attribute is not present in the response");
			Reporter.log("recipientGln attribute is not present in the response");
		}
	    
	    if (attributes.has("replacedItemNbr")) {
	    	replacedItemNbr = attributes.getString("replacedItemNbr");
		} else {
			LOGGER.debug("replacedItemNbr attribute is not present in the response");
			Reporter.log("replacedItemNbr attribute is not present in the response");
		}
	    
	    
	    if(attributes.has("sellTotalContent")){
			JSONArray sellTotalContent=attributes.getJSONArray("sellTotalContent");
			JSONObject sellTotalContentObject=sellTotalContent.getJSONObject(0);
	    
	    if (sellTotalContentObject.has("sellTotalContentQty")) {
	    	sellTotalContentQty = sellTotalContentObject.getString("sellTotalContentQty");
		} else {
			LOGGER.debug("sellTotalContentQty attribute is not present in the response");
			Reporter.log("sellTotalContentQty attribute is not present in the response");
		}
	    
	    if (sellTotalContentObject.has("sellTotalContentUomCode")) {
	    	sellTotalContentUomCode = sellTotalContentObject.getString("sellTotalContentUomCode");
		} else {
			LOGGER.debug("sellTotalContentUomCode attribute is not present in the response");
			Reporter.log("sellTotalContentUomCode attribute is not present in the response");
		}
	    }
	    else {
			LOGGER.debug("sellTotalContent Array is not present in the response");
			Reporter.log("sellTotalContent Array is not present in the response");
		}
	    
	    if(attributes.has("supplierLeadTime")){
			JSONArray supplierLeadTime=attributes.getJSONArray("supplierLeadTime");
			JSONObject supplierLeadTimeObject=supplierLeadTime.getJSONObject(0);
	    
	    if (supplierLeadTimeObject.has("supplierLeadTimeUomCode")) {
	    	supplierLeadTimeUomCode = supplierLeadTimeObject.getString("supplierLeadTimeUomCode");
		} else {
			LOGGER.debug("supplierLeadTimeUomCode attribute is not present in the response");
			Reporter.log("supplierLeadTimeUomCode attribute is not present in the response");
		}
	    }
	    else {
			LOGGER.debug("supplierLeadTime Array is not present in the response");
			Reporter.log("supplierLeadTime Array is not present in the response");
		}
	    
	    if (attributes.has("supplierStockId")) {
	    	supplierStockId = attributes.getString("supplierStockId");
		} else {
			LOGGER.debug("supplierStockId attribute is not present in the response");
			Reporter.log("supplierStockId attribute is not present in the response");
		}
	    
	    if (attributes.has("supplyItemChangeReasonCode")) {
	    	supplyItemChangeReasonCode = attributes.getString("supplyItemChangeReasonCode");
		} else {
			LOGGER.debug("supplyItemChangeReasonCode attribute is not present in the response");
			Reporter.log("supplyItemChangeReasonCode attribute is not present in the response");
		}
	    
	    if (attributes.has("supplyItemStatusChangeTimestamp")) {
	    	supplyItemStatusChangeTimestamp = attributes.getString("supplyItemStatusChangeTimestamp");
		} else {
			LOGGER.debug("supplyItemStatusChangeTimestamp attribute is not present in the response");
			Reporter.log("supplyItemStatusChangeTimestamp attribute is not present in the response");
		}
	    if (attributes.has("unitCostAmt")) {
	    	unitCostAmt = attributes.getString("unitCostAmt");
		} else {
			LOGGER.debug("unitCostAmt attribute is not present in the response");
			Reporter.log("unitCostAmt attribute is not present in the response");
		}
	    
	    if (attributes.has("warehouseRotationTypeCode")) {
	    	warehouseRotationTypeCode = attributes.getString("warehouseRotationTypeCode");
		} else {
			LOGGER.debug("warehouseRotationTypeCode attribute is not present in the response");
			Reporter.log("warehouseRotationTypeCode attribute is not present in the response");
		}
	    
	    if (attributes.has("hasRigidPlasticPackagingContainerInd")) {
	    	hasRigidPlasticPackagingContainerInd = attributes.getString("hasRigidPlasticPackagingContainerInd");
		} else {
			LOGGER.debug("hasRigidPlasticPackagingContainerInd attribute is not present in the response");
			Reporter.log("hasRigidPlasticPackagingContainerInd attribute is not present in the response");
		}
	    
	    
		Collections.addAll(responselist,"sendTraitConnectorCode:"+sendTraitConnectorCode,"sendTraitNbr:"+sendTraitNbr,"sendTraitSequenceNbr:"+sendTraitSequenceNbr,"userKey:"+userKey,"isReplenishableInd:"+isReplenishableInd,"supplierNbr:"+supplierNbr,"warehouseMinOrderQty:"+warehouseMinOrderQty,"isCorporateReplenishableInd:"+isCorporateReplenishableInd,
				"lastUpdatePgmId:"+lastUpdatePgmId,"warehousePackSellAmt:"+warehousePackSellAmt,"isCannedOrderInd:"+isCannedOrderInd,"isEcommerceReplenishableInd:"+isEcommerceReplenishableInd,"itemNbr:"+itemNbr,"orderablePackCostCurrencyCode:"+orderablePackCostCurrencyCode,"lifecycleStateCode:"+lifecycleStateCode,
				"shelfLabel2Description:"+shelfLabel2Description,"warehousePackCostAmt:"+warehousePackCostAmt,"deptNbr:"+deptNbr,"lastUpdateTimestamp:"+lastUpdateTimestamp,"orderablePackTypeCode:"+orderablePackTypeCode,"buyingRegionCode:"+buyingRegionCode,"inforemReorderTypeCode:"+inforemReorderTypeCode,"isCancelWhenOutInd:"+isCancelWhenOutInd,
				"lastUpdateTs:"+lastUpdateTs,"merchandiseSubcategoryNbr:"+merchandiseSubcategoryNbr,"isOfferedForSaleInd:"+isOfferedForSaleInd,"warehouseAlignmentCode:"+warehouseAlignmentCode,"orderablePackQtyUomCode:"+orderablePackQtyUomCode,"consumableGtin:"+consumableGtin,"baseRetailUomCode:"+baseRetailUomCode,
				"createUserid:"+createUserid,"subclassNbr:"+subclassNbr,"merchandiseCategoryNbr:"+merchandiseCategoryNbr,"replenishSubTypeCode:"+replenishSubTypeCode,"seasonCode:"+seasonCode,"informationProviderTypeCode:"+informationProviderTypeCode,"factoryId:"+factoryId,"fppRetardRangeInd:"+fppRetardRangeInd,"warehousePackGtin:"+warehousePackGtin,
				"sendStoreDate:"+sendStoreDate,"warehousePackSellCurrencyCode:"+warehousePackSellCurrencyCode,"shelfLabel4Description:"+shelfLabel4Description,"financialReportingGroupCode:"+financialReportingGroupCode,"baseRetailAmt:"+baseRetailAmt,"finelineNbr:"+finelineNbr,"warehousePackCostCurrencyCode:"+warehousePackCostCurrencyCode
				,"supplyItemExpireDate:"+supplyItemExpireDate,"lastUpdateProgramId:"+lastUpdateProgramId,"shelfLabel3Description:"+shelfLabel3Description,"isImportInd:"+isImportInd,"accountingDeptNbr:"+accountingDeptNbr,"orderablePackGtin:"+orderablePackGtin,"lastUpdateUserid:"+lastUpdateUserid,"reserveMerchandiseTypeCode:"+reserveMerchandiseTypeCode,
				"baseDivisionCode:"+baseDivisionCode,"warehousePackCalcMethodCode:"+warehousePackCalcMethodCode,"itemEligibilityStateCode:"+itemEligibilityStateCode,"isShelfLabelRequiredInd:"+isShelfLabelRequiredInd,"supplierSeqNbr:"+supplierSeqNbr,"supplyItemPrimaryDescription:"+supplyItemPrimaryDescription,
				"merchandiseProgramId:"+merchandiseProgramId,"supplyItemEffectiveDate:"+supplyItemEffectiveDate,"originCountryCode:"+originCountryCode,"supplyItemStatusCode:"+supplyItemStatusCode,"informationProviderId:"+informationProviderId,"shelfLabel1Description:"+shelfLabel1Description,"supplyItemCreateDate:"+supplyItemCreateDate,
				"orderablePackCostAmt:"+orderablePackCostAmt,"warehousePackQty:"+warehousePackQty,"customerRetailAmt:"+customerRetailAmt,"itemTypeCode:"+itemTypeCode,"destinationFormatCode:"+destinationFormatCode,"orderablePackQty:"+orderablePackQty,"targetMarketCode:"+targetMarketCode,"warehousePackQtyUomCode:"+warehousePackQtyUomCode,"legacyProductNbr:"+legacyProductNbr,
				"assortmentTypeCode:"+assortmentTypeCode ,"hasRfidInd:"+hasRfidInd ,"hasRigidPlasticPackagingContainerInd:"+hasRigidPlasticPackagingContainerInd ,"hasSecurityTagInd:"+hasSecurityTagInd ,"isBackroomScaleInd:"+isBackroomScaleInd ,"isRetailNotifyStoreInd:"+ isRetailNotifyStoreInd ,"isRetailVatInclusiveInd:"+ isRetailVatInclusiveInd ,
				"isVariablePriceComparisionInd:"+isVariablePriceComparisionInd ,"isVariableWeightInd:"+isVariableWeightInd ,"itemChangeSendWalmartWeekNbr:"+ itemChangeSendWalmartWeekNbr ,"omitTraitCode:"+omitTraitCode ,"recipientGln:"+recipientGln ,"replacedItemNbr:"+replacedItemNbr ,"supplierStockId:"+supplierStockId ,
				"supplyItemChangeReasonCode:"+supplyItemChangeReasonCode ,"supplyItemStatusChangeTimestamp:"+supplyItemStatusChangeTimestamp ,"unitCostAmt:"+unitCostAmt ,"warehouseRotationTypeCode:"+warehouseRotationTypeCode ,"sellTotalContentQty:"+sellTotalContentQty ,"sellTotalContentUomCode:"+sellTotalContentUomCode ,
				"supplierLeadTimeUomCode:"+supplierLeadTimeUomCode,
				"replenishmentGroupNbr:"+replenishmentGroupNbr,"supplierDeptNbr:"+supplierDeptNbr,"isReplenishedByUnitInd:"+isReplenishedByUnitInd,"merchandiseFamilyID:"+merchandiseFamilyID,"mbmTypeCode:"+mbmTypeCode,"createTs:"+createTs,"assembledOriginCountryCode:"+assembledOriginCountryCode,"componentOriginCountryCode:"+componentOriginCountryCode,
				"informationProviderGln:"+informationProviderGln,"hierarchyNode:"+hierarchyNode);
		
		Collections.addAll(ItemNumber, id);
	}// main loop ends here
	LOGGER.debug("responselist----------->"+responselist);
	for(int k=0;k<4;k++){
	for(int m=0; m<responselist.size(); m++){
	 if(responselist.get(m).contains("null")){
		 System.out.println("===>"+responselist.get(m));
		 responselist.remove(m);
	 }
	}
	}
//	for(int m=0; m<responselist.size(); m++){
//	if(responselist.get(m).contains("sendTraitNbr")){
//		 if(responselist.get(m).contains("null")){
//			 responselist.remove(responselist.get(m));
//			
//		 }
//	 }}
	
	int fromindex=0;
	for(int n=0; n<responselist.size();n++){
		
		if(responselist.get(n).contains("hierarchyNode")){
			
			Collections.sort(responselist.subList(fromindex, n+1));
//			responseValidate.add(n, Collections.sort(responselist.subList(fromindex, n+1)));
			responseValidate.add(responselist.subList(fromindex, n+1));
		
		fromindex=n+1;
		}
	}
	for(int l=0;l<responseValidate.size();l++){
		Collections.sort(responseValidate.get(l));
System.out.println("value of l =======>"+l);
System.out.println("------->"+responseValidate.get(l));
	}
	responseValidate.add(ItemNumber);
//	LOGGER.debug("------->"+responseValidate.get(0));
//	LOGGER.debug("------->"+responseValidate.get(1));
	return responseValidate;

}

	public List<String> siGetNegativeScenario() throws JSONException{
		String outputResponse = response.asString();
		JSONArray jsonarray = new JSONArray(outputResponse);
		String id=null;
		List<String> ID=new ArrayList<String>();
		for(int i=0; i<jsonarray.length();i++){
			JSONObject jsonobj=jsonarray.getJSONObject(i);
			if (jsonobj.has("id")) {
				id = jsonobj.getString("id");
			} else {
				LOGGER.debug("id attribute is not present in the response");
				Reporter.log("id attribute is not present in the response");
			}
			JSONObject attributes =jsonobj.getJSONObject("attributes");
			if(attributes.length()==0){
				LOGGER.debug(" attributes are not present in the response");
				Reporter.log(" attributes is not present in the response");

			}
			else{
				Assert.assertTrue(false);
}
			Collections.addAll(ID, id);
		}
		return ID;
		
		
	}
	
	public void siUpdateValidate( Response responseupdate,String factoryid,String isimportInd) throws JSONException{
	
		String outputResponse = responseupdate.asString();
		JSONObject jsonobj = new JSONObject(outputResponse);
		JSONObject attribute=jsonobj.getJSONObject("attributes");

		
//		if(attributes.has("factoryId") & attributes.has("isImportInd")){
		if(!(factoryid.equalsIgnoreCase("null") & isimportInd.equalsIgnoreCase("null"))){
			if(!factoryid.equalsIgnoreCase("null")){//when both our excel data is not equal to null
			JSONArray factoryId=attribute.getJSONArray("factoryId");
			String FactoryId=factoryId.toString();
			Assert.assertEquals(FactoryId, factoryid);
			String isImportInd=attribute.getString("isImportInd");
			Assert.assertEquals(isImportInd, isimportInd);
			LOGGER.debug("Both the attributes has updated");
			Reporter.log("Both the attributes has updated");
		}
		else if(factoryid.equalsIgnoreCase("null") | factoryid.equalsIgnoreCase("null")) {// if any of the excel data is null
			if(!factoryid.equalsIgnoreCase("null")){//if excel factory id not equal to null
				JSONArray factoryId=attribute.getJSONArray("factoryId");	
				String FactoryId=factoryId.toString();
				Assert.assertEquals(FactoryId, factoryid);
//				Assert.assertEquals("null", isimportInd);
			}
			else{
			Assert.assertFalse(attribute.has("factoryId"));
			}
			if(!isimportInd.equalsIgnoreCase("null")){// if excel import id not equal to null
				String isImportInd=attribute.getString("isImportInd");
				Assert.assertEquals(isImportInd, isimportInd);
			}
			else{
				Assert.assertFalse(attribute.has("isImportInd"));
			}
			
		}
		else {// if both are null

			Assert.assertFalse(attribute.has("factoryId"));
			Assert.assertFalse(attribute.has("isImportInd"));

		}
			
		
	}
}

	
	
}

