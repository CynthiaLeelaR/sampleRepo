package com.walmart.pageObj;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.walmart.base.WalmartBaseTest;
/**
 * 
 * @author cylee1
 *
 */
public class CatalogUIPage extends WalmartBaseTest {
	public static final Logger LOGGER = Logger.getLogger(CatalogUIPage.class);
	public CatalogUIPage(){}
	public CatalogUIPage(WebDriver driver)
	{
		super.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//textarea[@name='q']")
	private WebElement queryTextBox;
	
	@FindBy(xpath="//button[@type='submit']")
	private WebElement ExecuteButton;
	
	@FindBy(xpath="//div[@id='response']")
	private WebElement Response;
	
	@FindBy(xpath="//code[@ng-bind-html='response.data | highlight:lang | unsafe']")
	private WebElement WholeResponse;
	
	@FindBy(xpath="//*[@id='response']/pre/code/span/span[contains(text(),'numFound')]/following-sibling::span[1]/span")
	private WebElement NumberOfRecordFoundinresponse;
	
	@FindBy(xpath="(//*[@id='response']/pre/code/span/span/span[contains(text(),'q')]/following-sibling::span[1]/span)[1]")
	
	private WebElement QueryinResponse;
	
//	@FindBy(xpath="//*[@id='response']/pre/code//span[contains(text(),'context')]/following-sibling::span[1]")
	@FindBy(xpath="//span[contains(text(),'context')]/following-sibling::span[1]")
	private List<WebElement>  ContextsUI;
	
//	@FindBy(xpath="//span[contains(text(),'consumerItemNbr')]/following-sibling::span[1]")
//	private WebElement ConsumerItemNbr;
	
	@FindBy(xpath="//span[contains(text(),'consumerItemNbr')]/following-sibling::span[1]")
	private List<WebElement> ConsumerItemNbr;
	
	@FindBy(xpath="//*[@id='response']/pre/code/span[4]/span[8]/span[2]/span")
	private WebElement ID1;	
	
	@FindBy(xpath="//*[@id='response']/pre/code/span[4]/span[8]/span[58]/span")
	private WebElement ID2;	
	
	@FindBy(xpath="//*[contains(text(),'factoryId')]")
	private WebElement Factoryid;
	
	@FindBy(xpath="//*[contains(text(),'factoryId')]/following-sibling::span[1]")
	private WebElement Factoryidvalue;
	
	@FindBy(xpath="//*[contains(text(),'isImportInd')]")
	private WebElement IsimportInd;
	@FindBy(xpath="//*[contains(text(),'isImportInd')]/following-sibling::span[1]")
	private WebElement IsimportIndvalue;
	
	
	
	public void enterQuery(String query)
	{
		JavascriptExecutor je = ((JavascriptExecutor)driver);
		je.executeScript("arguments[0].scrollIntoView(true);",queryTextBox);
		queryTextBox.click();
		queryTextBox.clear();
		LOGGER.debug("Cleared the query text box");
		Reporter.log("Cleared the query text box");
		queryTextBox.sendKeys(query);
		
	}
	
	public void executeQuery() throws InterruptedException
	{
		JavascriptExecutor je = ((JavascriptExecutor)driver);
		je.executeScript("arguments[0].scrollIntoView(true);",ExecuteButton);
		ExecuteButton.click();
		LOGGER.debug("Clicked on the execute button");
		Reporter.log("Clicked on the execute button");
		Thread.sleep(3000);
	}
	
	public String getResponse()
	{
		String Output = Response.getText();
		return Output;
	}
	public void responseValidation(String response, String queryGiven)
	{
		int ZeroRecord =0 ;
		LOGGER.debug("Validating which query is getting execute");
		Reporter.log("Validating which query is getting execute");
		String query=QueryinResponse.getText();
		LOGGER.debug("The query from response is : "+query);
		Reporter.log("The query from response is : "+query);
		Assert.assertEquals("\""+queryGiven+"\"",query);
		LOGGER.debug("Validated the query which got executed");
		Reporter.log("Validated the query which got executed");
		String record=NumberOfRecordFoundinresponse.getText();
		int RecordInResponse = Integer.parseInt(record);
		LOGGER.debug("The number of reords returned through response is : "+RecordInResponse);
		LOGGER.debug("Validating the query is not returning zero record. the query have some record.");
		Reporter.log("The number of reords returned through response is : "+RecordInResponse);
		Reporter.log("Validating the query is not returning zero record. the query have some record.");
		Assert.assertNotSame(RecordInResponse, ZeroRecord);
		LOGGER.debug("Validated the query is not returning zero record. the query have some record.");
		Reporter.log("Validated the query is not returning zero record. the query have some record.");
		
	}
	public void responseValidationSolr(String response, String Query)
	{
		int ZeroRecord =0 ;
		List<String> ID= new ArrayList<String>();
		LOGGER.debug("Validating which query is getting execute");
		Reporter.log("Validating which query is getting execute");
		String query=QueryinResponse.getText();
		LOGGER.debug("The query from response is : "+query);
		Reporter.log("The query from response is : "+query);
		Assert.assertEquals("\""+Query+"\"",query);
		LOGGER.debug("Validated the query which got executed");
		Reporter.log("Validated the query which got executed");
		String record=NumberOfRecordFoundinresponse.getText();
		int RecordInResponse = Integer.parseInt(record);
		LOGGER.debug("The number of reords returned through response is : "+RecordInResponse);
		LOGGER.debug("Validating the query is not returning zero record. the query have some record.");
		Reporter.log("The number of reords returned through response is : "+RecordInResponse);
		Reporter.log("Validating the query is not returning zero record. the query have some record.");
		Assert.assertNotSame(RecordInResponse, ZeroRecord);
		LOGGER.debug("Validated the query is not returning zero record. the query have some record.");
		Reporter.log("Validated the query is not returning zero record. the query have some record.");
		
	}
	
	public void responseValidationDelete(String response, String Query)
	{
		int ZeroRecord = 0 ;
		LOGGER.debug("Validating which query is getting execute");
		Reporter.log("Validating which query is getting execute");
		String query=QueryinResponse.getText();
		LOGGER.debug("The query from response is : "+query);
		Reporter.log("The query from response is : "+query);
		Assert.assertEquals("\""+Query+"\"",query);
		LOGGER.debug("Validated the query which got executed");
		Reporter.log("Validated the query which got executed");
		String record=NumberOfRecordFoundinresponse.getText();
		int RecordInResponse = Integer.parseInt(record);
		LOGGER.debug("The number of reords returned through response is : "+RecordInResponse);
		LOGGER.debug("Validating the query is not returning any record. All the record should get delete");
		Reporter.log("The number of reords returned through response is : "+RecordInResponse);
		Reporter.log("Validating the query is not returning any record. All the record should get delete");
		Assert.assertEquals(RecordInResponse, ZeroRecord);
		LOGGER.debug("Validated the query is not returning any record. All the available record got deleted.");
		Reporter.log("Validated the query is not returning any record. All the available record got deleted.");
		
	}
	
	public boolean contextValidation(String context)
	{
		boolean flag = false;
		List<String> ContextSolr = new ArrayList<String>();
		for(int i=0;i<ContextsUI.size();i++)
		{
			ContextSolr.add(ContextsUI.get(i).getText());
//			String abc=ContextsUI.get(i).getText();
//			System.out.println("from ui :--->"+(ContextsUI.get(i).getText()));
//			System.out.println("from excel:--->"+context);
			if((ContextsUI.get(i).getText()).contains(context)){flag= true; break;}
			else{flag= false;}
		}
		if(flag==true)
		{
			LOGGER.debug("Record is found for the TI with the given context in the Solr");
			Reporter.log("Record is found for the TI with the given context in the Solr");

		}
		else
		{
			LOGGER.debug("Not able to find the TI details with the mentioned context in Solr UI" );
			Reporter.log("Not able to find the TI details with the mentioned context in Solr UI" );
		}
		return flag;
	}	
		
		
	
	public String tiSolrConsumeritemNbrUpdatevalidation(String context)
	{

		String ConsumerItemNbr_UI=null;

		
		for(int l=0;l<=ConsumerItemNbr.size();l++)
		{
//			System.out.println("((//span[contains(text(),'consumerItemNbr')]/following-sibling::span[1])["+l+"]//preceding::span[contains(text(),'informationProviderId:9238,informationProviderTypeCode:SUPPLIER_NUMBER,languageCode:en,recipientGln:0078742000008,targetMarketCode:US')])[1]");
//		  WebElement ConsumerItemList=driver.findElement(By.xpath("((//span[contains(text(),'consumerItemNbr')]/following-sibling::span[1])["+l+"]//preceding::span[contains(text(),'informationProviderId:9238,informationProviderTypeCode:SUPPLIER_NUMBER,languageCode:en,recipientGln:0078742000008,targetMarketCode:US')])[1]"));
			try{
			 WebElement ConsumerItemList=driver.findElement(By.xpath("((//span[contains(text(),'consumerItemNbr')]/following-sibling::span[1])["+l+"]//preceding::span[contains(text(),'"+context+"')])[1]"));
//		 	  System.out.println("inside if conditionnnnnnn");
		 	  System.out.println(ConsumerItemList.getText());
		 	  WebElement consumeritemNbrValue=driver.findElement(By.xpath("(//span[contains(text(),'consumerItemNbr')]/following-sibling::span[1])["+l+"]"));
			  ConsumerItemNbr_UI =consumeritemNbrValue.getText(); 
//			  System.out.println("itmnbr>>>>>>>"+ConsumerItemNbr_UI);
			  break;
			
		   }
			catch(Exception e){System.out.println(e);continue;}
		}
	return 	ConsumerItemNbr_UI;
		
	}

	
	public boolean solrRecords(String response, String queryGiven){
		
		boolean flag = false;
		LOGGER.debug("Validating which query is getting execute");
		Reporter.log("Validating which query is getting execute");
		String query=QueryinResponse.getText();
		LOGGER.debug("The query from response is : "+query);
		Reporter.log("The query from response is : "+query);
		Assert.assertEquals("\""+queryGiven+"\"",query);
		LOGGER.debug("Validated the query which got executed");
		Reporter.log("Validated the query which got executed");
		String record=NumberOfRecordFoundinresponse.getText();
		int RecordInResponse = Integer.parseInt(record);
		LOGGER.debug("The number of reords returned through response is : "+RecordInResponse);
		Reporter.log("The number of reords returned through response is : "+RecordInResponse);
		if(RecordInResponse>0){flag=true;}else{flag=false;}
		return flag;
	} 
	
	public void responseValidationNegative(String response, String queryGiven)
	{
		int ZeroRecord = 0 ;
		LOGGER.debug("Validating which query is getting execute");
		Reporter.log("Validating which query is getting execute");
		String query=QueryinResponse.getText();
		LOGGER.debug("The query from response is : "+query);
		Reporter.log("The query from response is : "+query);
		Assert.assertEquals("\""+queryGiven+"\"",query);
		LOGGER.debug("Validated the query which got executed");
		Reporter.log("Validated the query which got executed");
		String record=NumberOfRecordFoundinresponse.getText();
		int RecordInResponse = Integer.parseInt(record);
		LOGGER.debug("The number of reords returned through response is : "+RecordInResponse);
		Assert.assertEquals(RecordInResponse, ZeroRecord);
		LOGGER.debug("Validated the query is not returning any record.");
		Reporter.log("Validated the query is not returning any record.");
		
	}
	public void solrReponseUpdate(String factoryid,String isimportInd,String OutputresponseUI) throws InterruptedException{
	
		String factId = null,isimport = null;  //dummy variables initialization
//		if((factoryid.equalsIgnoreCase("null"))){}else{}
		System.out.println(OutputresponseUI);
		Thread.sleep(5000);
		if(factoryid.equalsIgnoreCase("null"))
		{
			Assert.assertFalse(OutputresponseUI.contains("factoryId"));	
		}
		else{
		if(OutputresponseUI.contains("factoryId")){
			Thread.sleep(2000);
			 factId=Factoryidvalue.getText();
			 System.out.println(factId);
			factId=factId.replaceAll("\"","");
			factId=factId.replaceAll("\\s+"," ");
			factId=factId.replaceAll(" ", "");
			System.out.println(factoryid);
			System.out.println(factId);
			Assert.assertEquals(factId, factoryid); 
		}}
//		else{
//			System.out.println(OutputresponseUI.contains("factoryId"));
//			@@@@@@
//			Assert.assertFalse(OutputresponseUI.contains("factoryId"));
//		}
//		if((!isimportInd.equalsIgnoreCase("null"))){
		if(isimportInd.equalsIgnoreCase("null")){
			Assert.assertFalse(OutputresponseUI.contains("isImportInd"));
		}
		else{
		if(OutputresponseUI.contains("isImportInd")){
			Thread.sleep(2000);
			isimport=IsimportIndvalue.getText();
			System.out.println(isimport);
			Assert.assertEquals(isimportInd, isimport);
		}}
//		else{
//			
////			Assert.assertTrue(isimport.isEmpty());
//		}
	}
}
