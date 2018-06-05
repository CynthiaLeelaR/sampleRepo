package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import page.ElementsAndMethods;
import base.BaseTest;
/**
 * 
 * @author SA765030
 * Task: On hdfcbank.com, click on customer care, click on call us, call us
new pop up opens,  click here, next page select Any State
 */
public class Assignment4_HDFC extends BaseTest{
	@Test
	public static void hdfc() throws InterruptedException{
		String url = "https://www.hdfcbank.com/";
		ElementsAndMethods obj = PageFactory.initElements(driver, ElementsAndMethods.class);
		obj.getURL(url);
		obj.customerCareCall();
	}

}
