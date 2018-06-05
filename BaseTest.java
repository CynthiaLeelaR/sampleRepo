package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import javafx.beans.property.SetProperty;

public class BaseTest {
	
	public static WebDriver driver =null;
	@Test
	public static void launch(){
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sa765030\\Downloads\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-extensions");
		driver = new ChromeDriver(options);
		driver.get("www.google.com");
		driver.close();
		
	}

}
