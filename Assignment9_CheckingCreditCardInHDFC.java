package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import page.ElementsAndMethods;
import base.BaseTest;


/**
 * 
 * @author SA765030
 * Task: hdfcbank.com and check for select product and if credit card is there click on it
 */

public class Assignment9_CheckingCreditCardInHDFC extends BaseTest {
	@Test
	public static void checkingCredit() throws InterruptedException {
		String url = "https://www.hdfcbank.com/";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.creditCardHDFC();
	}

}
