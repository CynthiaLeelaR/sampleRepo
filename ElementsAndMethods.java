package page;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.seleniumemulation.WaitForPageToLoad;
import org.openqa.selenium.support.FindBy;

import base.BaseTest;

public class ElementsAndMethods extends BaseTest {

	// Assignment 1 - Picking date from Calendar

	@FindBy(xpath = "//li[@class='w170 datepicker']")
	private WebElement yatraFrmDate;

	@FindBy(id = "25/11/2018")
	private WebElement dateNov25;

	// Assignment 2 - Yatra

	@FindBy(xpath = "//li[@id='userLoginBlock']/a")
	private WebElement myAcc;

	@FindBy(xpath = "//a[@id='signInBtn']")
	private WebElement signInBtn;

	@FindBy(xpath = "//button[@id='login-continue-btn']")
	private WebElement continueBtn;

	@FindBy(xpath = "//input[@id='login-input']")
	private WebElement userTxt;

	@FindBy(xpath = "//input[@id='login-password']")
	private WebElement pwdTxt;

	@FindBy(xpath = "//button[@id='login-submit-btn']")
	private WebElement loginBtn;

	@FindBy(xpath = "//a[@id='booking_engine_buses']")
	private WebElement busOption;

	@FindBy(xpath = "//input[@id='BE_bus_from_station']")
	private WebElement fromBus;

	@FindBy(xpath = "//input[@id='BE_bus_to_station']")
	private WebElement toBus;

	// Assignment_3 - New Tours

	@FindBy(xpath = "(//tr[@class='mouseOut'])[1]/td[2]")
	private WebElement beforeMouseMove;

	@FindBy(xpath = "(//tr[@class='mouseOut'])[1]/td[1]")
	private WebElement afterMouseMove;

	// Assignment_4 - HDFC

	@FindBy(xpath = "//div[@id='div-close']")
	private WebElement popupClosebtn;

	@FindBy(xpath = "//*[text()='Customer Care']")
	private WebElement customerecareBtn;

	@FindBy(xpath = "(//a[@id='loan-against-securities'])[2]")
	private WebElement callusBtn;

	@FindBy(xpath = "//ul[@class='discbullet']/li[1]/a")
	private WebElement resCusBtn;

	@FindBy(xpath = "//select[@id='state']")
	private WebElement stateDrpdwn;

	@FindBy(xpath = "//select[@id='state']/option[23]")
	private WebElement stateKerala;

	// Assignment 6 - NewTours Table reading

	@FindBy(xpath = "//input[@name='userName']")
	private WebElement userNameField;

	@FindBy(xpath = "//input[@name='password']")
	private WebElement pwdField;

	@FindBy(xpath = "//input[@name='login']")
	private WebElement tourSignInBtn;

	@FindBy(xpath = "//select[@name='fromPort']")
	private WebElement frmDrpdwn;

	@FindBy(xpath = "//select[@name='fromPort']/option[4]")
	private WebElement frmNewyork;

	@FindBy(xpath = "//select[@name='toPort']")
	private WebElement toDrpdwn;

	@FindBy(xpath = "//select[@name='toPort']/option[7]")
	private WebElement toSan;

	@FindBy(xpath = "//input[@name='findFlights']")
	private WebElement tourContinueBtn;

	@FindBy(xpath = "//form[@action='mercurypurchase.php']/table[1]/tbody/tr")
	private List<WebElement> rowCountOfFlightTable;

	// Assignment7_Rediff

	@FindBy(id = "srchinputcopy")
	private WebElement searchBox;

	@FindBy(id = "srchword")
	private WebElement searchTxt;

	@FindBy(xpath = "//div[@id='sugbox']/div/p")
	private List<WebElement> suggList;

	// Assignment9_Credit Card HDFC

	@FindBy(xpath = "//li[@class='productWrap']/div/a")
	private WebElement productsLink;

	@FindBy(xpath = "//div[@class='account_products']/div/div/h3/a")
	private List<WebElement> hdfcProductsList;

	@FindBy(xpath = "(//div[@class='account_products']/div/div/h3/a)[3]")
	private WebElement hdfcCards;

	// Assignments10_ Radio Button

	@FindBy(xpath = "//input[@type='radio']")
	private List<WebElement> totalNumofRadio;

	@FindBy(xpath = "//td[@class='table5']//input[@type='radio']")
	private List<WebElement> radiobuttons;

	// =======================================Methods=================================================================================/

	/**
	 * Launching URL
	 * 
	 * @param url
	 * @throws InterruptedException
	 */
	public void getURL(String url) throws InterruptedException {
		System.out
				.println("Launching browswer and Navigating to the following URL : "
						+ url);
		driver.get(url);
		implicitWait(3);

	}

	/**
	 * Assignment1 - Picking particular date from Calender
	 * 
	 * @throws InterruptedException
	 */
	public void pickingDateFrmCal() throws InterruptedException {
		System.out.println("Clicking Start date Calender");
		yatraFrmDate.click();
		Thread.sleep(1000);
		System.out.println("Selecting November 25 2018 from the calender");
		dateNov25.click();
	}

	/**
	 * Assignment 2 - Sign in into Yatra.com
	 * 
	 * @throws InterruptedException
	 */
	public void signinYatra() throws InterruptedException {
		System.out.println("Clicking My Account button");
		myAcc.click();
		// Thread.sleep(500);
		implicitWait(2);
		driver.manage().deleteAllCookies();
		System.out.println("Clicking Sign In button");
		signInBtn.click();
		userTxt.click();
		System.out.println("Entering User name in UserName Text box");
		userTxt.sendKeys("sagusehwag@gmail.com");
		System.out.println("Clicking Continue button");
		continueBtn.click();
		pwdTxt.click();
		System.out.println("Entering Password in PassWord Text box");
		pwdTxt.sendKeys("sagu@121");
		System.out.println("Clicking Login button");
		loginBtn.click();
		implicitWait(2);
		System.out
				.println("Sucessfully logged in Yatra and Logged user name is "
						+ driver.findElement(
								By.xpath("//li[@id='userLoginBlock']/a"))
								.getText());

	}

	/**
	 * Assignment 2 - Selecting Bus option and Providing Start and End
	 * destination.
	 * 
	 * @throws InterruptedException
	 */
	public void addingSourceAndDestination() {
		System.out.println("Selecting Buses option");
		busOption.click();
		fromBus.click();
		System.out.println("Entering Start point of travel");
		fromBus.sendKeys("Bangalore");
		toBus.click();
		System.out.println("Entering End point of travel");
		toBus.sendKeys("Hyderabad");
		System.out.println("Task completed sucessfully..!!!");
	}

	/**
	 * Assignment_3 - Getting background color of element before and after
	 * moving mouse
	 * 
	 * @throws InterruptedException
	 */
	public void gettingBGcolor() throws InterruptedException {
		implicitWait(2);
		System.out
				.println("Getting backround color of element with out moving mouse");
		String clr1 = beforeMouseMove.getCssValue("background-color");
		if (clr1.equalsIgnoreCase("rgba(255, 196, 85, 1)")) {
			System.out
					.println("Before Mouse Movement,Background Color of element is "
							+ clr1 + "===== Orange");
		}
		Actions act = new Actions(driver);
		System.out.println("Moving mouse over the element");
		act.moveToElement(beforeMouseMove).clickAndHold().build().perform();
		Thread.sleep(500);
		System.out
				.println("Getting backround color of element after moving mouse");
		String clr2 = afterMouseMove.getCssValue("background-color");
		if (clr2.equalsIgnoreCase("rgba(0, 0, 0, 0)")) {
			System.out
					.println("After Mouse Movement,Background Color of element is "
							+ clr2 + "========= Black");
		}
	}

	/**
	 * Assignment 4 - On hdfcbank.com, click on customer care, click on call us,
	 * call us new pop up opens, click here, next page selecting Kerala as state
	 * 
	 * @throws InterruptedException
	 */
	public void customerCareCall() throws InterruptedException {
		implicitWait(2);
		System.out.println("Clicking Customer Care link ");
		customerecareBtn.click();
		implicitWait(2);
		System.out.println("Clicking Call us option");
		callusBtn.click();
		Thread.sleep(1000);
		System.out.println("Switching into new frame");
		driver.switchTo().frame("ceeboxiframe");
		System.out.println("Clicking Resdential Customer option");
		resCusBtn.click();
		System.out.println("Switching into newly opened window");
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
		System.out
				.println("Selecting state as Kerala from State/City Dropdown");
		stateDrpdwn.click();
		Thread.sleep(1000);
		stateKerala.click();

	}

	/**
	 * Assignment 6 - Reading and printing all the text values present in result
	 * table
	 */
	public void tourTableText() {
		System.out
				.println("Signing in with registered user in New Tours website");
		userNameField.click();
		userNameField.sendKeys("sagu121");
		pwdField.click();
		pwdField.sendKeys("sagu12345");
		tourSignInBtn.click();
		System.out.println("Selecting NewYork from Departing From dropdown");
		frmDrpdwn.click();
		frmNewyork.click();
		System.out.println("Selecting San Fransico from Arriving In dropdown");
		toDrpdwn.click();
		toSan.click();
		System.out.println("Clicking Continue button");
		tourContinueBtn.click();
		for (int i = 2; i <= rowCountOfFlightTable.size(); i++) {
			List<WebElement> a = driver
					.findElements(By
							.xpath("//form[@action='mercurypurchase.php']/table[1]/tbody/tr["
									+ i + "]/td/font"));
			for (int j = 0; j < a.size(); j++) {
				String tableTxt = driver
						.findElements(
								By.xpath("//form[@action='mercurypurchase.php']/table[1]/tbody/tr["
										+ i + "]/td/font")).get(j).getText();
				System.out.println("Text from row " + (i - 1) + ": " + tableTxt
						+ "\n");
			}
		}
	}

	/**
	 * Assignment7_Rediff - Printing all the text available in suggestion list
	 * 
	 * @throws InterruptedException
	 */
	public void rediffSuggestionList() throws InterruptedException {
		Thread.sleep(500);
		searchBox.click();
		searchTxt.sendKeys("h");
		System.out.println("The suggesstion list of letter h is \n");
		for (int i = 0; i < suggList.size(); i++) {
			System.out
					.println(i + 1 + " . " + suggList.get(i).getText() + "\n");

		}
	}

	/**
	 * Assignmenet 8 - Printing all the search links that come up in
	 * www.google.com
	 */
	public void gettingSearchLinks() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		int totalNum = driver.findElements(By.tagName("a")).size();
		System.out.println("Total Numbers of Links Available : " + totalNum);
		for (int i = 0; i < totalNum; i++) {
			String allLinks = links.get(i).getAttribute("href");

			System.out.println(i + 1 + ". " + allLinks);
		}
	}

	/**
	 * Assignment9- Verifying all the products of HDFC and Checking Credit Cards
	 * option in HDFC portal
	 */
	public void creditCardHDFC() {
		System.out.println("Selecting Products links from HDFC menu bar");
		productsLink.click();
		System.out.println("Displaying all the products of HDFC bank");
		for (int a = 0; a < hdfcProductsList.size(); a++) {
			System.out.println(hdfcProductsList.get(a).getText() + "\n");
		}
		System.out.println("Verifying HDFC have Cards options or not");
		for (int i = 0; i < hdfcProductsList.size(); i++) {
			if (hdfcProductsList.get(i).getText().equalsIgnoreCase("Cards")) {
				System.out.println("Verified HDFC have Cards options");
				System.out.println("Selecting Cards options from product list");
				hdfcCards.click();
				System.out
						.println("Navigated into Credits Card page, Page title is \n"
								+ driver.getTitle());
			}
		}

	}

	/**
	 * Assignment 10 - Checking for html radio buttons, And printing total count
	 * of radio buttons in page, Then validating for checked option and
	 * unchecked option
	 */
	public void verifyingRadioButtons() {
		System.out
				.println("Total Number of Radio Buttons present in the page : "
						+ totalNumofRadio.size());
		System.out
				.println("Total number of Radio buttons avalibale in result output table  : "
						+ radiobuttons.size());
		for (int i = 0; i < radiobuttons.size(); i++) {
			if (radiobuttons.get(i).getAttribute("checked") != null) {
				System.out.println(radiobuttons.get(i).getAttribute("value")
						+ "----radio button is CHECKED by default");
			} else {
				System.out.println(radiobuttons.get(i).getAttribute("value")
						+ "----radio button is NOT checked by default");
			}
		}
	}
}
