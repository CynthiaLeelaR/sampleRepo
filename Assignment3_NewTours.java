package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import page.ElementsAndMethods;
import base.BaseTest;
/**
 * 
 * @author SA765030
 * Task : On  http://newtours.demoaut.com, try to get background colour before 
		  and after mouse movements
 */
public class Assignment3_NewTours extends BaseTest {
	@Test
	public static void newTours() throws InterruptedException {
		String url = "http://newtours.demoaut.com";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.gettingBGcolor();
	}

}
