package com.cynthia.qa.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;


public class OrionServicePage {
	
	public String testDataXls = "src/test/resources/OrionServicetest_testdata/OrionServiceData.xlsx";
	public String testDataPath = "src/test/resources/OrionServicetest_testdata/";
	public ArrayList<String> attribute = new ArrayList<String>();
	private static final Logger LOGGER = Logger.getLogger(OrionServicePage.class);	
	private static SoftAssert s_assertAssert=new SoftAssert();
	Properties prop = new Properties();
    InputStream input = null;
   
    /*public Object fetchingValuesfromPropertyFile() throws Exception
    {
    	try 
    	{
    		input = new FileInputStream("src/test/resources/OrionServicetest_testdata/serviceConfig.properties");
            prop.load(input);
            LOGGER.debug(prop.getProperty("baseUrl"));
            LOGGER.debug(prop.getProperty("ALGORITHM"));
            LOGGER.debug(prop.getProperty("PRODUCTPRIVATEKEY"));
            LOGGER.debug(prop.getProperty("PRODUCTPRIVATEKEY_QA"));
            LOGGER.debug(prop.getProperty("PRODUCTPUBLICKEY"));
            LOGGER.debug("EXCEL PATH : "+prop.getProperty("testDataXls"));
            input.close();
            return prop;
            
    	} 
    	catch (Exception ex) {
           ex.printStackTrace();
           input.close();
           return null;
    	}
    }*/
//    dfasd
   
	public void ProductAttributeComparsion(String jsonResponse_get,String GTIN) throws Exception
	{
		try 
    	{
    		input = new FileInputStream(testDataPath+"serviceConfig.properties");
            prop.load(input);
            input.close();
                        
    	} 
    	catch (Exception ex) {
           ex.printStackTrace();
           input.close();
         }
		
		
		String categoryName = prop.getProperty("categoryName");
		String fuelrestrictionindicator = prop.getProperty("fuelrestrictionindicator");	
		String californiaresidentsprop65warningrequired = prop.getProperty("californiaresidentsprop65warningrequired");
		String multipackindicator = prop.getProperty("multipackindicator");
		String aerosolindicator = prop.getProperty("aerosolindicator");
		String productname = prop.getProperty("productname");
		String isprivatelabelunbranded = prop.getProperty("isprivatelabelunbranded");
		String brandcode = prop.getProperty("brandcode");
		String pesticideindicator = prop.getProperty("pesticideindicator");	
		String segregation = prop.getProperty("segregation");
		String istemperaturesensitive = prop.getProperty("istemperaturesensitive");
		
		//category 
		JSONObject jsonobject = new JSONObject(jsonResponse_get);
	    JSONObject jsonobject1 = jsonobject.getJSONObject("payload");
	    JSONObject jsonobject2 = jsonobject1.getJSONObject("productAttributes");
	    JSONObject jsonobject3 = jsonobject2.getJSONObject("ironbank_category");
	    JSONArray jsonarray = jsonobject3.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray.length(); i++) 
	    {
			JSONObject jsonobject4 = jsonarray.getJSONObject(i);
			String Category1 = jsonobject4.getString("value");
			LOGGER.debug("Category Value is: "+Category1);
			s_assertAssert.assertEquals(Category1, categoryName,"Category is not same");
		}
		//fuel restriction indicator
			 
	    JSONObject jsonobject5 = jsonobject2.getJSONObject("fuel_restriction_indicator");
	    JSONArray jsonarray2 = jsonobject5.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray2.length(); i++) 
	    {
	    	JSONObject jsonobject6 = jsonarray2.getJSONObject(i);
	    	String fuelvalue = jsonobject6.getString("source_value");
			LOGGER.debug("fuel restriction indicator Value is: "+fuelvalue);
			s_assertAssert.assertEquals(fuelvalue, fuelrestrictionindicator ,"fuel_restriction_indicator  is not same"); 
	    }
	    // california_residents_prop_65_warning_required
	    JSONObject jsonobject7 = jsonobject2.getJSONObject("california_residents_prop_65_warning_required");
	    JSONArray jsonarray3 = jsonobject7.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray3.length(); i++)
	    {
	    	JSONObject jsonobject8 = jsonarray3.getJSONObject(i);
	    	String calif_value = jsonobject8.getString("source_value");
			LOGGER.debug("california_residents_prop_65_warning_required Value is: "+calif_value);
			s_assertAssert.assertEquals(calif_value, californiaresidentsprop65warningrequired ,"california_residents_prop_65_warning_required  is not same");
		    	
	    }
	    //multipack_indicator
	    JSONObject jsonobject9 = jsonobject2.getJSONObject("multipack_indicator");
	    JSONArray jsonarray4 = jsonobject9.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray4.length(); i++)
	    {
	    	JSONObject jsonobject10 = jsonarray4.getJSONObject(i);
	    	String multipack_value = jsonobject10.getString("source_value");
			LOGGER.debug("multipack_indicator Value is: "+multipack_value);
			s_assertAssert.assertEquals(multipack_value, multipackindicator,"multipack_indicator is not same");
	    }
		        
	    //aerosol_indicator
	    JSONObject jsonobject11 = jsonobject2.getJSONObject("aerosol_indicator");
	    JSONArray jsonarray5 = jsonobject11.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray5.length(); i++) 
	    {
	    	JSONObject jsonobject12 = jsonarray5.getJSONObject(i);
	    	String aerosol_value = jsonobject12.getString("source_value");
			LOGGER.debug("aerosol_indicator Value is: "+aerosol_value);			
			s_assertAssert.assertEquals(aerosol_value, aerosolindicator,"aerosol_indicator is not same");
	    }
		       
	    //product_name
	    JSONObject jsonobject13 = jsonobject2.getJSONObject("product_name");
	    JSONArray jsonarray6 = jsonobject13.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray6.length(); i++)
	    {
	    	JSONObject jsonobject14 = jsonarray6.getJSONObject(i);
	    	String productname_value = jsonobject14.getString("source_value");
	    	LOGGER.debug("product name Value is: "+productname_value);
			s_assertAssert.assertEquals(productname_value, productname,"product_name is not same");
	    }
		        
	    // is_private_label_unbranded
	    JSONObject jsonobject15 = jsonobject2.getJSONObject("is_private_label_unbranded");
	    JSONArray jsonarray7 = jsonobject15.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray7.length(); i++) 
	    {
	    	JSONObject jsonobject16 = jsonarray7.getJSONObject(i);
	    	String private_label_value = jsonobject16.getString("source_value");
			LOGGER.debug("is_private_label_unbranded Value is: "+private_label_value);
			s_assertAssert.assertEquals(private_label_value, isprivatelabelunbranded,"is_private_label_unbranded  is not same");
	    }
		        
	    //brand_code
	    JSONObject jsonobject17 = jsonobject2.getJSONObject("brand_code");
	    JSONArray jsonarray8 = jsonobject17.getJSONArray("attributeValues");
	    for (int i = 0; i < jsonarray8.length(); i++) 
	    {
	    	JSONObject jsonobject18 = jsonarray8.getJSONObject(i);
	    	String brand_code_value = jsonobject18.getString("source_value");
			LOGGER.debug("brand_code Value is: "+brand_code_value);
			s_assertAssert.assertEquals(brand_code_value, brandcode,"brand_code  is not same");
	    }
		        
	    //is_temperature_sensitive
		JSONObject jsonobject19 = jsonobject2.getJSONObject("is_temperature_sensitive");
		JSONArray jsonarray9 = jsonobject19.getJSONArray("attributeValues");
		for (int i = 0; i < jsonarray9.length(); i++) 
		{
			JSONObject jsonobject20 = jsonarray9.getJSONObject(i);
			String temp_sensv_value = jsonobject20.getString("source_value");
			LOGGER.debug("is_temperature_sensitive Value is: "+temp_sensv_value);
			s_assertAssert.assertEquals(temp_sensv_value, istemperaturesensitive,"is_temperature_sensitive  is not same");
		}
		        
		        
		JSONObject jsonobject21 = jsonobject2.getJSONObject("segregation");
		JSONArray jsonarray10 = jsonobject21.getJSONArray("attributeValues");
		for (int i = 0; i < jsonarray10.length(); i++) 
		{
			JSONObject jsonobject22 = jsonarray10.getJSONObject(i);
			String segregation_value = jsonobject22.getString("source_value");
			LOGGER.debug("segregation Value is: "+segregation_value);
			s_assertAssert.assertEquals(segregation_value, segregation,"segregation  is not same");
		}
		JSONObject jsonobject23 = jsonobject2.getJSONObject("pesticide_indicator");
		JSONArray jsonarray11 = jsonobject23.getJSONArray("attributeValues");
		for (int i = 0; i < jsonarray11.length(); i++) 
		{
			JSONObject jsonobject24 = jsonarray11.getJSONObject(i);
			String pesticide_indicator_value = jsonobject24.getString("source_value");
			LOGGER.debug("pesticide_indicator Value is: "+pesticide_indicator_value);
			s_assertAssert.assertEquals(pesticide_indicator_value, pesticideindicator,"pesticide_indicator  is not same");
		}
		LOGGER.debug("ALL THE ATTRIBUTE  VALIDATED");
		s_assertAssert.assertAll();
		LOGGER.debug("ALL THE ATTRIBUTES GOT UPDATED SUCCESSFULLY- PRODUCT CREATED");
	}

	public void emiqueryChangeValidation(String jsonResponse) throws JSONException 
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
        JSONObject metadataObject = jsonobject.getJSONObject("metadata");
        if(metadataObject.toString().contains("main_image_")!=true)
        {
        	LOGGER.debug("Single valued attributes displayed without any values appended to it");
        }
	}

	public void CheckforRemovedAttributes(String jsonResponse) throws JSONException 
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
	    JSONObject metadataObject = jsonobject.getJSONObject("metadata");
		if((metadataObject.toString().contains("Consumable Item Cube")!=true)||(metadataObject.toString().contains("Brand Acquisition Type system")!=true))
		{
	      	LOGGER.debug("system, auditable and workflow attributes should not get retrieved in the response generated when system=false is specified");
		}
	}
	
	//Modified Methods
	public void getAttributeDetails(String jsonResponse) throws JSONException
	{
		JSONObject result = new JSONObject(jsonResponse);
        JSONArray Item = new JSONArray();
        for(int i=0;i<result.length();i++ )
        {
        	Item = result.names();
        }
        LOGGER.debug(Item);
        for(int j=0;j<Item.length();j++)
        {
        	JSONObject one =result.getJSONObject((String) Item.get(j));
            String definition = one.getString("definition");
            LOGGER.debug("Definition for the metadata Attribute "+(String) Item.get(j) +" ==> "+ definition);
        }
        LOGGER.debug("All the attributes definitions are retrived");
	}	
	
	public void getAttributeTightGroup(String jsonResponse) throws JSONException
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
        JSONArray Item = new JSONArray();
        for(int i=0;i<jsonobject.length();i++ ) 
        {
               Item = jsonobject.names();      
        }
        LOGGER.debug(Item);
        LOGGER.debug("All the attributes are Tightly Grouped");
	}
	
	public void GetProductAttribute(String jsonResponse) throws JSONException
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
        JSONObject jsonobject1 = jsonobject.getJSONObject("metadata");
       // LOGGER.debug(jsonobject1);
        JSONArray Item = new JSONArray();
        for(int i=0;i<jsonobject1.length();i++ ) {
               Item = jsonobject1.names();}
        LOGGER.debug(Item);
        
        LOGGER.debug("All the attributes Related to cat and subCar are retrived");
	}
	
	public void GetImageDetails(String jsonResponse) throws JSONException
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
		JSONArray jsonarray = jsonobject.getJSONArray("searchResultList");
		JSONObject jsonobject4 = jsonarray.getJSONObject(0);
		JSONArray jsonarray1 = jsonobject4.getJSONArray("assets"); 
		for(int i=0;i<jsonarray1.length();i++ ) 
		{
	       String  ImageDetails = jsonarray1.getString(i);
	       LOGGER.debug("Image Details Under Asset: "+ImageDetails);
	    }	
	}
	
	public void checkForRemovedAttributes(String jsonResponse) throws JSONException 
	{
		
		JSONObject jsonobject = new JSONObject(jsonResponse);
		JSONObject metadataObject = jsonobject.getJSONObject("metadata");
		int count=0;String metadata_Attributes[] = new String [1000];
		String[] attributes;
		JSONArray Item = new JSONArray();
        for(int i=0;i<metadataObject.length();i++ ) 
        {
        	Item = metadataObject.names();
        }
        //LOGGER.debug(Item);
        for(int j=0;j<Item.length();j++)
        {
        	JSONObject one =metadataObject.getJSONObject((String) Item.get(j));
        	JSONArray jsonaray = one.getJSONArray("attributeClassification");
//          metadata_Attributes[count]= one.get("name").toString();
//          attributes = metadata_Attributes.clone();
            for(int k= 0; k<jsonaray.length();k++)
            {
                String DB2_Value2 = jsonaray.getString(k).toString();
                attribute.add(DB2_Value2);
            }
            count++;
            Assert.assertFalse(attribute.contains("system"),"Attributes not removed");
            Assert.assertFalse(attribute.contains("workflow"),"Attributes not removed");
            Assert.assertFalse(attribute.contains("auditable"),"Attributes not removed");
        }
	}

	public void checkforAttributes(String jsonResponse) throws JSONException 
	{	
		JSONObject jsonobject = new JSONObject(jsonResponse);
	    JSONObject metadataObject = jsonobject.getJSONObject("metadata");
	    int count=0;int flag=0;String metadata_Attributes[] = new String [1000];
	    String[] attributes;
	    JSONArray Item = new JSONArray();
	    for(int i=0;i<metadataObject.length();i++ ) 
	    {
	    	Item = metadataObject.names();
	    }
	    for(int j=0;j<Item.length();j++)
	    {
		    JSONObject one =metadataObject.getJSONObject((String) Item.get(j));
		    JSONArray jsonaray = one.getJSONArray("attributeClassification");
		    metadata_Attributes[count]= one.get("name").toString();
		    attributes = metadata_Attributes.clone();
		    for(int k= 0; k<jsonaray.length();k++)
		    {
		    String DB2_Value2 = jsonaray.getString(k).toString();
		    attribute.add(DB2_Value2);
		    }
		    count++;
		    if((attribute.contains("system"))||(attribute.contains("workflow"))||(attribute.contains("auditable")))
		    {
		    flag++;
		    }                  
	    }
	    if(flag>0)
	    {
	    	LOGGER.debug("Attributes not removed");
	    }       // LOGGER.debug("****"+attribute);
	}

	public void verifyTitleAndField(String jsonResponse) throws JSONException 
	{
		// Verify the title and field value for P/O Attributes
		
		JSONObject jsonobject = new JSONObject(jsonResponse);
		JSONArray columns = jsonobject.getJSONArray("columns");
		for (int i = 0; i < columns.length(); i++) 
		{
			JSONObject obj = columns.getJSONObject(i);
			try
			{
			    if((obj.getString("title").contains("Swatch Image URL (#1) swatch_images")) && (obj.getString("field").contains("swatch_image_url_fromUIFor_swatch_images_1"))!=true)
			    LOGGER.debug("Title and Field doesnot contain the parent name"); 
			}
			catch(Exception e)
			{
			   LOGGER.debug("Title and Field Value contains the parent name");
			}
		}
	}

	public void verifyMetadata(String jsonResponse) throws JSONException 
	{
		//2-->Verify the metadata for attributes having relation
		 /*object/metadata/attribute/label
		   object/metadata/attribute/concept
		   object/metadata/attribute/attributeGroup
		  */
		JSONObject jsonobject = new JSONObject(jsonResponse);
	    JSONObject metadataObject = jsonobject.getJSONObject("metadata");
	    ArrayList<String> labelArray=new ArrayList<String>();
	    ArrayList<String> attributeGroupArray=new ArrayList<String>();
	    JSONArray Item = new JSONArray();
	    for(int i=0;i<metadataObject.length();i++ ) 
	    {
	    	Item = metadataObject.names();
	    }
	    //LOGGER.debug(Item);
	    for(int j=0;j<Item.length();j++)
	    {
	         JSONObject attribute =metadataObject.getJSONObject((String) Item.get(j));
	         //Check the presence of label ,concept and attribute group for all the metadata Attributes
	         try
	         {
	        	 	JSONArray conceptAry = attribute.getJSONArray("concept");
	                for(int k= 0; k<conceptAry.length();k++)
	                {
	                	String conceptvalue = conceptAry.getString(k).toString();
	                	if(conceptvalue!=null)
	                    {
	                		LOGGER.debug("The concept value for Metadata Attribute --> "+(String) Item.get(j)+"-->"+conceptvalue);
	                    }
	                    else
	                    {
	                    	LOGGER.debug("Concept is not present in Metadata Attribute");
	                    }
	                 }
	                 String label = attribute.getString("label");
	                 labelArray.add(label);
	                 String attributeGroup = attribute.getString("attributeGroup");
	                 attributeGroupArray.add(attributeGroup);
	                 if((label!=null) || (attributeGroup!=null))
	                 {
	                	 LOGGER.debug("The Label and AttributeGroup value for Metadata Attribute --> "+(String) Item.get(j)+"-->"+label+" and "+attributeGroup); 
	                 }
	         	}
	            catch(Exception e)
	            {
	               LOGGER.debug("The Label value is not there for the Metadata Attribute --> "+(String) Item.get(j));
	            }
	    }
	}
	public void checkDelimiterinAttributes(String jsonResponse) throws JSONException 
	{	
		//check Attribute names should not contain “_ofOffer	
		JSONObject jsonobject = new JSONObject(jsonResponse);
		JSONObject metadataObject = jsonobject.getJSONObject("metadata");
		JSONArray Item = new JSONArray();
		for(int i=0;i<metadataObject.length();i++ )
		{
			Item = metadataObject.names();
		}
		//LOGGER.debug(Item);
		for(int j=0;j<Item.length();j++)
		{
			String attributeNoDelimit=(String) Item.get(j)+"_ofOffer";
			Assert.assertFalse(metadataObject.toString().contains(attributeNoDelimit),"Attribute names  contains _ofOffer which is not Expected");
			LOGGER.info("No Metadata Attributes contains _ofOffer in their names");	  
		}
	}
	
	
	public void checkAttributeList(String jsonResponse) throws JSONException 
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
		 JSONArray Item = new JSONArray();
	        for(int i=0;i<jsonobject.length();i++ ) {
	               Item = jsonobject.names();}
	        LOGGER.debug(Item);
	        
	        List<String> arrayList = new ArrayList<String>();
	        for (int i=0; i<Item.length(); i++) {
	        	arrayList.add( Item.getString(i) );}

	        Collections.sort(arrayList);	
	        ArrayList<String> srcAttriblist=new ArrayList<String>();
	        srcAttriblist.addAll(Arrays.asList("warehouseMinOrderQty","replacedItemNbr","variant_group_id","isCorporateReplenishableInd","gots_certification","product_id_type_2","product_id_type_1","product_id_type_3","clothing_size_type","clothing_size","alt_text_1","additional_product_attributes","contains_paper_wood","deptNbr","alt_text_2","character","alt_text_3","assembled_product_width","consumerShippingDimensionsUomCode","palletTiQty","sellTotalContentQty","model","has_warranty","asset_url_3","asset_url_2","personalizable","brand","minimum_storage_temperature","asset_url_1","consumableGtin","isShelfRotationInd","baseRetailUomCode","multipack_indicator","seasonCode","factoryId","productOfferingSiteStartDate","price_per_unit_quantity","assembled_product_length_unitOfMeasure","warranty_length","product_id_3","product_id_2","shelfLabel4Description","hasRigidPlasticPackagingContainerInd","product_id_1","recycled_material_content","consumerGtin","hazardous_materials_indicator","product_short_description","baseRetailAmt","shelfLabel3Description","signingDescription","brand_code","gender","warehouseAreaCode","made_from_recycled_materials","pattern","autographed","supplyItemPrimaryDescription","country_of_origin_components","consumerShippingDimensionsHeightQty","hasSecurityTagInd","product_long_description","assembled_product_height","sport_type","warehousePackQty","age_group","itemTypeCode","warehouseRotationTypeCode","destinationFormatCode","shelf_description","maximum_storage_temperature_unitOfMeasure","battery_type_and_quantity","cpsc_regulated_indicator","pesticide_indicator","assembled_product_width_unitOfMeasure","batteries_included","autographed_by","chemical_indicator","crushFactorCode","isEcommerceReplenishableInd","consumerShippingDimensionsDepthQty","omitTraitNbr","warehouseMinLifeRemainingToReceiveQty","fuel_restriction_indicator","features","product_identifiers","buyingRegionCode","maternity","isOfferedForSaleInd","warehouseAlignmentCode","palletHiQty","segregation","prop_65_warning_text","sellTotalContentUomCode","state_restrictions_spec","createUserid","isSoldByWeightInd","replenishSubTypeCode","athlete","informationProviderTypeCode","aerosol_indicator","assembled_product_weight","warehousePackGtin","seasonYearNbr","sendStoreDate","product_name","productOfferingSiteEndDate","additional_asset_attributes_3","additional_asset_attributes_1","additional_asset_attributes_2","assembled_product_weight_unitOfMeasure","finelineNbr","hasRfidInd","has_warnings","assembled_product_height_unitOfMeasure","isBackroomScaleInd","supplyItemExpireDate","assembledOriginCountryCode","shelf_life_unitOfMeasure","occasion","orderablePackGtin","swatch_images","pluNbr","consumerShippingDimensionsWidthQty","small_parts_warning_code","supplierId","color","batteryTypeCode","supplyItemSecondaryDescription","sendTraitNbr","main_image","variant_attribute_names","asset_type_1","warranty_information","sports_team","asset_type_3","asset_type_2","has_state_restrictions","shelf_life","merchandiseProgramId","california_residents_prop_65_warning_required","is_temperature_sensitive","supplyItemEffectiveDate","originCountryCode","sock_style","informationProviderId","warranty_url","supplierStockId","warehouseMaxOrderQty","minimum_storage_temperature_unitOfMeasure","isVariableWeightInd","isDeaReportedInd","warnings","orderablePackQty","consumerShippingWeightQty","fabric_care_instructions","maximum_storage_temperature","assembled_product_length","certifications","sock_size","apparel_category","is_private_label_unbranded","consumerShippingWeightUomCode","numberOfBoxesforProductQty","package_quantity","compositeWoodCertificationCode","unitCostAmt","has_expiration","allowedTimeInWarehouseQty","receiptDescription","is_primary_variant","sports_league","additional_assets"));
	        Collections.sort(srcAttriblist);
	        boolean exist =false;
	    	if(srcAttriblist.equals(arrayList))
	    	{
	    		exist=true;
	    	LOGGER.debug("attributes list are same as source list");
	    	}
	    	else
	    	{  exist =false;
	    		LOGGER.debug("Attributes list are not same as source list ");
	    	}

	}

	
	public void checkVariantDetails(String jsonResponse) throws JSONException 
	{
		JSONObject jsonobject = new JSONObject(jsonResponse);
	       
		/**Returning the Product Id form the Json response**/
       JSONArray jsonarray = jsonobject.getJSONArray("searchResultList");
		 for (int i = 0; i < jsonarray.length(); i++) {
			    JSONObject jsonobject1 = jsonarray.getJSONObject(i);
			    String productId = jsonobject1.getString("productId");
		LOGGER.debug("productId Value is: "+productId);
		String BVProductId ="0RJ3G9QD34WC";
		/**Asserting if the product id with BVSHELL productClassType is displayed**/
		Assert.assertNotEquals(productId,BVProductId ,"The BVSHEll Product id is also dispalyed");
		LOGGER.debug("Only the prodcut with varient ProductClasstype is displayed");
		 }		
	}

	
	public void checkService(String status,String correct,String incorrect) throws JSONException 
	{
		boolean code = false;
		if(status.equals(correct))
		{
	LOGGER.debug("The Service is Not down: Working fine --> with Status "+status);
	code = true;
		}
else if(status.equals(incorrect))
{
	LOGGER.debug("The Service currently down:Not Working fine --> with Status "+status);
	code = true;
}
else
{
	LOGGER.debug("Some thing went Wrong --> with Status "+status);
	code = false;
}
		s_assertAssert.assertTrue(code, "Some thing went Wrong --> with Status "+status);
		s_assertAssert.assertAll();
	}
	//Munivara added usefull api service methods

	public String[] getAPIWebResourceResponse(Client client1, String uri) 
	{
		try
			{
			WebResource webResource = client1.resource(uri);
		ClientResponse clientResponse = webResource.header("Content-Type", "application/vnd.com.cynthia.labs.dto.PCF+json").get(ClientResponse.class);
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	public String[] getAPIWebResourceResponseByPassingType(Client client1, String uri,String inputType) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource.type(inputType).get(ClientResponse.class);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseCreateUnassignedProductService(Client client1, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource
					.header("Content-Type", "application/vnd.com.cynthia.labs.dto.PCF+json").post(ClientResponse.class,requestBody);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}	
	}
	public String[] postAPIWebResourceResponseDataConversionFactorService(Client client1, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource.type("application/json")
					.header("WMT-type-code", "dev").header("WMT-user-id", "rlbuyus")
					.header("WMT-correlation-id", "1234").post(ClientResponse.class,requestBody);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseIntegratewithFeedgaetway(Client client1, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource.type("application/json")
					.header("wmt-user-id","rakor1").post(ClientResponse.class,requestBody);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] getAPIserach(Client client1, String uri) 
	{ 
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource
					.header("Host","catalog-test.wal-mart.com")
					.header("Content-Type","application/vnd.metadata-2+json").get(ClientResponse.class);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	
	public String[] getallattributes(Client client1, String uri) 
	{ 
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource.type("application/json")
					.header("WM_SVC.VERSION", "x.y.z")
					.header("WM_SVC.NAME", "test")
					.header("WM_CONSUMER.ID", "0affa926-2eef-44b3-83a8-bcfd77549203")
					.header("WM_QOS.CORRELATION_ID","test").get(ClientResponse.class);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseUpdateAsset(Client client1, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource.type("application/json")
					.header("wmt-user-id","spatlol").post(ClientResponse.class,requestBody);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseRemoveBrandId(Client client1, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource
					.header("Content-Type", "application/vnd.cynthia.emi.TradeItemGenericDto-1+json").post(ClientResponse.class,requestBody);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseExceptionForAssetupdate(Client client1, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			ClientResponse clientResponse = webResource.header("wmt-user-id","rlbuyus").type("application/json")
					.header("WMT-type-code","dev")
					.header("WMT-correlation-id","1234")
					.header("Accept-Type","application/json")
					.header("Host","catalog-cert.wal-mart.com").post(ClientResponse.class,requestBody);
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseProductFeedUpdateSerivce(Client client, String uri,String requestBody) 
	{
		try
		{
			WebResource webResource = client.resource(uri);
			ClientResponse clientResponse = webResource.type("application/json")
					.header("wmt-user-id", "rakor1").post(ClientResponse.class,requestBody);
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] postAPIWebResourceResponseItemSetupFileUpload(Client client1, String uri,File input) 
	{
		try
		{
			WebResource webResource = client1.resource(uri);
			MultiPart multipartEntity = new FormDataMultiPart().field("file", input, MediaType.APPLICATION_OCTET_STREAM_TYPE);
	        ClientResponse clientResponse = webResource.type(MediaType.MULTIPART_FORM_DATA)
	                .header("WMT-USER-ID", "rakor1")
	                .header("WMT-USER-TYPE-CODE", "dev")
	                .header("wmt-correlation-id", "1234")
	                .post(ClientResponse.class, multipartEntity);
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] productFeedCreationResponse(Client client, String uri,String filePath,String GTIN) 
	{
		try
		{
		WebResource webResource = client.resource(uri);
		String requestBody1 = FileUtils.readFileToString(new File(filePath));
		LOGGER.debug("Request body or payload  modified by replace gtin to create product feed");
		String replaceGtinRequestBody=requestBody1.replaceAll("New_GTIN",GTIN);
		ClientResponse clientResponse = webResource.type("application/json")
				.header("wmt-user-id", "rakor1")
				.post(ClientResponse.class, replaceGtinRequestBody);
		LOGGER.debug("Request body or payload  modified by replace gtin to create product feed");
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] productFeedGETServiceResponse(Client client, String uri) 
	{
			
		try
		{
		WebResource webResource = client.resource(uri);
		ClientResponse clientResponse = webResource.type("application/json")
				.header("WM_CONSUMER.ID", "test")
				.header("WM_QOS.CORRELATION_ID","test")
				.header("WM_SVC.NAME","test")
				.header("WM_SVC.ENV","test")
				.header("WM_SVC.VERSION","test").get(ClientResponse.class);
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	public String[] productFeedIQSGETServiceResponse(Client client, String uri) 
	{
			
		try
		{
		WebResource webResource = client.resource(uri);
		ClientResponse clientResponse = webResource.type("application/json")
				.header("WM_SVC.VERSION", "x.y.z")
				.header("WM_SVC.NAME", "test")
				.header("WM_CONSUMER.ID", "0affa926-2eef-44b3-83a8-bcfd77549203")
				.header("WM_QOS.CORRELATION_ID","test").get(ClientResponse.class);
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	
	public String[] offerCreationServiceResponse(Client client, String uri,String filePath) throws Exception 
	{
			
		try 
    	{
    		input = new FileInputStream(testDataPath+"serviceConfig.properties");
            prop.load(input);
            input.close();
                        
    	} 
    	catch (Exception ex) {
           ex.printStackTrace();
           input.close();
         }
		
		
		String CnsmrShipDimDepth = prop.getProperty("CnsmrShipDimDepth");
		String CnsmrShipDimWidth = prop.getProperty("CnsmrShipDimWidth");	
		String ReceiptDescription = prop.getProperty("ReceiptDescription");
		String ShopDescription = prop.getProperty("ShopDescription");
		String SigningDescription = prop.getProperty("ShopDescription")+"_"+prop.getProperty("ReceiptDescription");
				
		try
		{
		WebResource webResource = client.resource(uri);
		String requestBody1 = FileUtils.readFileToString(new File(filePath));
		requestBody1=requestBody1.replaceAll("Cnsmr_Shipping_Dimensions_Depth",CnsmrShipDimDepth);    
        requestBody1=requestBody1.replaceAll("Cnsmr_Shipping_Dimensions_Width",CnsmrShipDimWidth);
        requestBody1=requestBody1.replaceAll("Cnsmr_Shipping_Dimensions_Weight",CnsmrShipDimWidth);
        requestBody1=requestBody1.replaceAll("Cstmr_Shelf_Life_Tolerance",CnsmrShipDimWidth);
        requestBody1=requestBody1.replaceAll("Receipt_Description",ReceiptDescription);
        requestBody1=requestBody1.replaceAll("Shop_Description",ShopDescription);
        requestBody1=requestBody1.replaceAll("Signing_Description",SigningDescription);
		
		ClientResponse clientResponse = webResource.type("application/vnd.com.cynthia.services.iso.common.FullOffer-1+json")
								.post(ClientResponse.class, requestBody1);
		LOGGER.debug("Request body or payload  modified by replace gtin to create product feed");
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	
	public String[] offerIQSGETServiceResponse(Client client, String uri) throws Exception 
	{
			
		
		try
		{
		WebResource webResource = client.resource(uri);
		ClientResponse clientResponse = webResource.type("application/json")
				.header("WM_SVC.VERSION", "x.y.z")
                .header("WM_SVC.NAME", "test")
                .header("WM_CONSUMER.ID", "0affa926-2eef-44b3-83a8-bcfd77549203")
                .header("WM_QOS.CORRELATION_ID","test").get(ClientResponse.class);
		
		LOGGER.debug("Request body or payload  modified by replace gtin to create product feed");
			
			String[] output = new String[3];
			int status=clientResponse.getStatus();
			output[0] = String.valueOf(status);
			output[1] = clientResponse.getEntity(String.class);
			output[2] = clientResponse.toString();		
			return output;
		}
		catch(Exception ie)
		{
			System.out.println("Exception occeured:"+ie);
			return null;
		}
	}
	
	
	public void check(String jsonResponseget) throws Exception{
		
		try 
    	{
    		input = new FileInputStream(testDataPath+"serviceConfig.properties");
            prop.load(input);
            input.close();
                        
    	} 
    	catch (Exception ex) {
           ex.printStackTrace();
           input.close();
         }
		
		
		String CnsmrShipDimDepth = prop.getProperty("CnsmrShipDimDepth");
		String CnsmrShipDimWidth = prop.getProperty("CnsmrShipDimWidth");	
		String ReceiptDescription = prop.getProperty("ReceiptDescription");
//		String ShopDescription = prop.getProperty("ShopDescription");
//		String SigningDescription = prop.getProperty("ShopDescription")+"_"+prop.getProperty("ReceiptDescription");
				
		
		JSONObject jsonobject = new JSONObject(jsonResponseget);
        JSONObject payload = jsonobject.getJSONObject("payload");
        JSONArray offerings = payload.getJSONArray("Offerings");
        JSONObject Offer = offerings.getJSONObject(0);
        JSONObject offerAttributes = Offer.getJSONObject("Offer");
        JSONArray ShippingDimensionsDepthQty = offerAttributes.getJSONArray("offerAttributes");
        
//get value of consumerShippingDimensionsDepthQty                
        JSONObject ShippingDimensionsDepthQtyval = ShippingDimensionsDepthQty.getJSONObject(28); 
        JSONArray ShppngDmnsinDpthQtyval = ShippingDimensionsDepthQtyval.getJSONArray("attributeValues");
        for (int i = 0; i < ShppngDmnsinDpthQtyval.length(); i++) {
        JSONObject ShppngDmnsinDpthQtyvalue = ShppngDmnsinDpthQtyval.getJSONObject(i);
        String consumerShippingDimensionsDepthQty = ShppngDmnsinDpthQtyvalue.getString("attributeValue");
   //     System.out.println("consumerShippingDimensionsDepthQty Value is: "+Category1);
        Assert.assertEquals(consumerShippingDimensionsDepthQty, CnsmrShipDimDepth,"Cnsmr_Shipping_Dimensions_Depth value got updated");
   }
//get value of consumerShippingDimensionsWidthQty    
        JSONObject ShippingDimensionsWidthQtyval = ShippingDimensionsDepthQty.getJSONObject(26); 
        JSONArray ShppngDmnsinWidthQtyval = ShippingDimensionsWidthQtyval.getJSONArray("attributeValues");
        for (int j = 0; j < ShppngDmnsinWidthQtyval.length(); j++) {
        JSONObject ShppngDmnsinWdthQtyvalue = ShppngDmnsinWidthQtyval.getJSONObject(j);
        String consumerShippingDimensionsWidthQty = ShppngDmnsinWdthQtyvalue.getString("attributeValue");
   //     System.out.println("consumerShippingDimensionsDepthQty Value is: "+Category1);
        Assert.assertEquals(consumerShippingDimensionsWidthQty, CnsmrShipDimWidth,"Cnsmr_Shipping_Dimensions_Width value got updated");
  }
        
//get value of consumerShippingDimensionsWeightQty    
        JSONObject ShippingDimensionsWeightQtyval = ShippingDimensionsDepthQty.getJSONObject(25); 
        JSONArray ShppngDmnsinWeightQtyval = ShippingDimensionsWeightQtyval.getJSONArray("attributeValues");
        for (int j = 0; j < ShppngDmnsinWeightQtyval.length(); j++) {
        JSONObject ShppngDmnsinWeghtQtyvalue = ShppngDmnsinWeightQtyval.getJSONObject(j);
        String consumerShippingDimensionsWeightQty = ShppngDmnsinWeghtQtyvalue.getString("attributeValue");
   //     System.out.println("consumerShippingDimensionsDepthQty Value is: "+Category1);
        Assert.assertEquals(consumerShippingDimensionsWeightQty, CnsmrShipDimWidth,"Cnsmr_Shipping_Dimensions_Weight value got updated");
  }
    
//get value of Receipt description    
        JSONObject Receiptdesc = ShippingDimensionsDepthQty.getJSONObject(16); 
        JSONArray recptdescval = Receiptdesc.getJSONArray("attributeValues");
        for (int j = 0; j < recptdescval.length(); j++) {
        JSONObject recptdescvalue = recptdescval.getJSONObject(j);
        String receiptdescvalue = recptdescvalue.getString("attributeValue");
   //     System.out.println("consumerShippingDimensionsDepthQty Value is: "+Category1);
        Assert.assertEquals(receiptdescvalue, ReceiptDescription,"Receipt description value got updated");
  }
    
	}
	
}

