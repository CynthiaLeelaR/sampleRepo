package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseTest;
import page.ElementsAndMethods;

/**
 * 
 * @author SA765030 
 * Task : On yatra.com -- after sign in --choose an option of bus and
 * provide bangalore and hyderabad as details for source and destination
 */
public class Assignment2_Yatra extends BaseTest {
	@Test
	public static void Yatra() throws InterruptedException {
		String url = "https://www.yatra.com/";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.signinYatra();
		obj.addingSourceAndDestination();
	}

}
