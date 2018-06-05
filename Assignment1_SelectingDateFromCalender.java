package test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import page.ElementsAndMethods;
import base.BaseTest;
/**
 * 
 * @author SA765030
 * Task : On  www.yatra.com.com  select a date in the calendar and click  on the specific date
 *
 */
public class Assignment1_SelectingDateFromCalender extends BaseTest {
	@Test
	public static void selectiongDate() throws InterruptedException {
		String url = "https://www.yatra.com/";
		ElementsAndMethods obj = PageFactory.initElements(driver,
				ElementsAndMethods.class);
		obj.getURL(url);
		obj.pickingDateFrmCal();
	}
}
