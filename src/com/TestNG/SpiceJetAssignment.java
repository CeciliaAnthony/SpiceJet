package com.TestNG;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SpiceJetAssignment {

	private WebDriver driver;
	public static String url = "https://www.spicejet.com";
	public static String titleToBeVerified = "Cheap Air Tickets Online, International Flights to India, Cheap International Flight Deals | SpiceJet Airlines";
	public static String urlToBeVerified = "https://book.spicejet.com/Select.aspx";
	public static String fromPlace ="Goa";
	public static String toPlace ="Delhi";
	public static String adults ="3";
	
	@BeforeClass
	public void driverInitialization() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/com/drivers/chromedriver.exe");

		// Instantiate the web driver and load the page
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("disable-extensions");
		options.addArguments("start-maximized");

		driver = new ChromeDriver(options);

		driver.navigate().to(url);
		
		}

	@Test
	public void spiceJetValidation() throws Exception {
		
	
			System.out.println("test");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainContent_rbtnl_Trip_1")));

		driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).clear();
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).sendKeys(fromPlace);
		Thread.sleep(2000);
		driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT")).sendKeys(toPlace);
		
		//Below method will select from date
		datePicker(0);

		driver.findElement(By.id("ctl00_mainContent_view_date2")).click();
		
		//Below method will select to date
		datePicker(10);
		
		//Below will select the no of adults
		driver.findElement(By.id("divpaxinfo")).click();
		Select ddAdult = new Select(driver.findElement(By.id("ctl00_mainContent_ddl_Adult")));
		ddAdult.selectByValue(adults);
		Thread.sleep(2000);
		
		driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency")).click();
		Select ddCurrency = new Select(driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency")));
		List<WebElement> ddCurrencyList = ddCurrency.getOptions();
		
		//Below will select the last currency from the list
		WebElement select = ddCurrencyList.get(ddCurrencyList.size() - 1);
		select.click();
		driver.findElement(By.id("ctl00_mainContent_btn_FindFlights")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Login")));
		Actions actions = new Actions(driver);
		
		// Retrieve WebElement loginSignup to perform mouse hover
		WebElement loginSignup = driver.findElement(By.id("Login"));
		
		// Mouse hover Login
		actions.moveToElement(loginSignup).perform();
		System.out.println("Done Mouse hover on 'Music' from Menu");
		
		//Below will fetch and print the list of options
		WebElement list = driver.findElement(By.id("menu-list-login"));
		List<WebElement> options = list.findElements(By.tagName("a"));
		for (int i = 1; i < options.size(); i++) {
			System.out.println(options.get(i).getText());
		}

		String getTitle = driver.getTitle();
		System.out.println(getTitle);
		System.out.println(driver.getCurrentUrl());
		Assert.assertEquals(titleToBeVerified, driver.getTitle().trim(), "Verify Title");
		Assert.assertEquals(urlToBeVerified, driver.getCurrentUrl().trim(), "Verify Url");
	
		}

	@AfterClass
	public void closeDriverSessions() {
		driver.quit();
	}

	public void datePicker(int fromTodayDate) throws Exception{
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, fromTodayDate); // Adding days
		String output = sdf.format(c.getTime());
		System.out.println(output);

		String today = output.substring(0, 2);
		;
		System.out.println("Date to be selected: " + today + "\n");

		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']"));

		// These are the rows of the from date picker table
		List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));

		// These are the columns of the from date picker table
		List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

		for (WebElement cell : columns) {
			
			// Select Date
			if (cell.getText().equals(today)) {
				cell.click();
				break;
			}
		}
	
}
}
