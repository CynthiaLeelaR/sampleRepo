package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import page.ElementsAndMethods;
import base.BaseTest;

/**
 * 
 * @author SA765030
 * Task: http://in.rediff.com/, send in " h" into the field near 
			search and print all the text available 

 */


public class Assignment7_Rediff extends BaseTest {
	@Test
	public void rediff() throws InterruptedException {
		String url = "http://in.rediff.com/";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.rediffSuggestionList();
	}

}
