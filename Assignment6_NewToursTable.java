package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import page.ElementsAndMethods;
import base.BaseTest;
/**
 * 
 * @author SA765030
 * Task : Get the text available on all the table fields in 
		  http://newtours.demoaut.com.   (New York to San Fransico).
		  Try to get all the fields of the above table 

 */
public class Assignment6_NewToursTable extends BaseTest{
@Test
	public static void newToursTable() throws InterruptedException{
		String url = "http://newtours.demoaut.com";
		ElementsAndMethods obj = PageFactory.initElements(driver, ElementsAndMethods.class);
		obj.getURL(url);
		obj.tourTableText();
	} 
}
