package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.BaseTest;
import page.ElementsAndMethods;


/**
 * 
 * @author SA765030
 * Task : On  http://www.echoecho.com/htmlforms10.htm  check for  html radio buttons.
Validate for checked option
 */

public class Assignments10_RadioButtons extends BaseTest {
	@Test
	public static void radioButton() throws InterruptedException {
		String url = "http://www.echoecho.com/htmlforms10.htm";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.verifyingRadioButtons();
	}

}
