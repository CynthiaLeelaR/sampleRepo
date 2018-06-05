package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import page.ElementsAndMethods;
import base.BaseTest;


/**
 * 
 * @author SA765030
 * Task: print the name of all the search links that come up in www.google.com
 */

@Test
public class Assignment8_GettingAllSearchLinks extends BaseTest {
	public static void searchLinks() throws InterruptedException {
		String url = "https://www.google.com/";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.gettingSearchLinks();
	}
}
