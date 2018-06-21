package com.cynthia.qa.OrionService.seleniumtests;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.cynthia.qa.pageobjects.OrionServicePage;
import com.cynthia.qa.util.HmacAuthenticationFilter;
import com.cynthia.qa.util.TestUtil;
import com.cynthia.qa.util.XlsReader;


		/**
		 * @author allon1
		 * Covering the scenario :Creating an Offer feed  
		 * Verifying offer Attributes value through IQS -get service
		 ***/

public class OfferCreationPOSTGETServices {
	
	private static final Logger LOGGER = Logger.getLogger(OfferCreationPOSTGETServices.class);
	public String jsonResponseget;private Client client;
	OrionServicePage orionServicePage = new OrionServicePage();
	Properties prop = new Properties();
    InputStream input = null;
    
   
   
    /**Creating Offer postservices
	 * Getting the response and validating the status
	 * @param UploadFileName
	 * @throws Exception
	 **/
    
    
    @Test(enabled = true,priority=1,dataProvider = "getTestData")
    public void OfferCreation(String UploadFileName) throws Exception 
    {

                    
    	String mainUrl = prop.getProperty("baseUrl")+prop.getProperty("offerCreationUrl");
		LOGGER.debug("Url from the Property file and Testdata -->"+mainUrl);
		String filepath = prop.getProperty("UploadPath")+UploadFileName;
		String[] outputResponse = orionServicePage.offerCreationServiceResponse(client, mainUrl,filepath);
    	LOGGER.debug("Response code for service to update asset --> " +outputResponse[0]);
		Assert.assertEquals(outputResponse[0],prop.getProperty("offerExpectedStatus"),"Response did not match");
		LOGGER.info("Successfully executed the payload and thefile is uploaded"+outputResponse[1]);
		
                  
    }

    /**Getting OfferFeedGetService response through IQSservices
	 * Verifying Offer Attributes value through IQS -get service
	 * Getting the response and validating the status
	 * @param UploadFileName
	 * @throws Exception
	 **/
    
	@Test(enabled = true,priority=2,dataProvider = "getTestData")
    public void offerIQSGETService(String UploadFileName) throws Exception 
    {

                    
		String mainUrl = prop.getProperty("offerIQSGETServiceUrl");
		LOGGER.debug("Url from the Property file and Testdata -->"+mainUrl);
		String[] outputResponse = orionServicePage.offerIQSGETServiceResponse(client, mainUrl);
    	LOGGER.debug("Response code for service to update asset --> " +outputResponse[0]);
		Assert.assertEquals(outputResponse[0],prop.getProperty("exceptedStatusCode"),"Response did not match");
		LOGGER.info("Successfully executed the payload and thefile is uploaded"+outputResponse[1]);
		jsonResponseget = outputResponse[1];
		LOGGER.debug("Json Response for Product IQS GET " +jsonResponseget);
		orionServicePage.check(jsonResponseget);
                    
                    

    }
	
	/**
	 * @return Below parameters from the OrionTestData.xlsx-->sheet name "AggregateCacheForPOAttributes"
	 * @param UploadFileName
	**/
	public XlsReader testDataXls = new XlsReader(orionServicePage.testDataXls);
	@DataProvider
	public Object[][] getTestData()
	{
		return TestUtil.getData(testDataXls, "OfferCreationPOSTGETServices");
	}

      
       
       /**
   	 * Initializing the Client setup before sending a request through client
   	 * Generating the authorization header for given uri's
   	 * @throws Exception
   	 */
   	@BeforeTest(alwaysRun = true)
   	public void setUp()throws Exception
   	{
   		try 
       	{
       		input = new FileInputStream(orionServicePage.testDataPath+"serviceConfig.properties");
               prop.load(input);
               input.close();
           } 
       	catch (Exception ex) 
       	{
              ex.printStackTrace();
              input.close();
           }
   		ClientConfig clientConfig = new DefaultClientConfig();
   		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
   		client =  Client.create(clientConfig);
   		SecretKeySpec  EMIPRODUCTKEY=new SecretKeySpec(prop.getProperty("PRODUCTPRIVATEKEY").getBytes(), prop.getProperty("ALGORITHM"));
   		client.addFilter(new HmacAuthenticationFilter(prop.getProperty("PRODUCTPUBLICKEY"),EMIPRODUCTKEY));		
   	} 
       
       
       
}
