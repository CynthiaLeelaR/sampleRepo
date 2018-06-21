package com.cynthia.qa.OrionService.CatalogService;

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
 * @author chvv1
 * @Date: 27-12-2016
 *  @Modified chvv1 
 * 
 * Covering the scenario : Getting PRODUCT details using Search API Service.
***/

public class GetSerachAPIservice {
	
	
	private static final Logger LOGGER = Logger.getLogger(GetSerachAPIservice.class);
	private Client client;
	OrionServicePage orionServicePage = new OrionServicePage();
	Properties prop = new Properties();
    InputStream input = null;
	
	/**
	 * Covering the scenario :Getting all the PRODUCT details through API get service 
	 * Getting the response and validating the status
	 * @param uri
	 * @param ExceptedStatusCode
	 * @throws Exception
	 */
	@Test(enabled = true,priority=1,dataProvider = "getTestData")
	public void SearchAPI(String uri,String ExceptedStatusCode) throws Exception 
	{
		
		String[] outputResponse = orionServicePage.getAPIserach(client,uri);		
		LOGGER.debug("Response code for Get API Search GET service --> " +outputResponse[0]);
		Assert.assertEquals(outputResponse[0],ExceptedStatusCode,"Exptected response is different");
		LOGGER.debug("Json Response " +outputResponse[1]);
      
		
	    
	}
	
	/**
	* @return parameters from the OrionServiceData.xlsx-->sheet name "GetAPISearch"
	* We can iterate over with invalid values as well using the below DataProvider
	* @param uri
	* @param exptectedStatusCode
	*/
	
	@DataProvider
	public Object[][] getTestData()
	{
		XlsReader testDataXls = new XlsReader(prop.getProperty("testDataXls"));
		return TestUtil.getData(testDataXls, "GetAPISearch");
	}
	
	/**
	 * Initializing the Client setup before sending a request through client
	 * Generating the authorization header for given uri's
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp()
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
          
        }
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		client =  Client.create(clientConfig);
		SecretKeySpec  EMIPRODUCTKEY=new SecretKeySpec(prop.getProperty("PRODUCTPRIVATEKEY").getBytes(), prop.getProperty("ALGORITHM"));
		client.addFilter(new HmacAuthenticationFilter(prop.getProperty("PRODUCTPUBLICKEY"),EMIPRODUCTKEY));	
		
		
	}
	
}