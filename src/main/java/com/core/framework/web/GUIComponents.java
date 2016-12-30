package com.core.framework.web;

import org.openqa.selenium.WebDriver;
/* @author N6420S21
 * Description - All HTMl 4 web page objects and its operations
6 '-**/
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import com.core.framework.utils.Database;
import com.core.framework.utils.DriverFactory;
import com.core.framework.utils.HtmlReporter;
import com.core.framework.utils.Log;
import com.core.framework.utils.PublicVariables;
import com.core.framework.utils.Utilities;
import java.io.IOException;
import java.util.*;

public class GUIComponents extends PublicVariables{

	// public static WebDriver driver = null;
	public static boolean bResultFlag=false;
	public static String sBCDescription = "***";
	public static boolean bObjectValidation = false;
	Utilities utils = new Utilities();
	//Default constructor
	private String strColumnNante;

	/**
	 * @author Dhilip
	 * @paraxn driver
	 * Description Constructor
	 */
	public GUIComponents (WebDriver driver) {
		GUIComponents.driver = DriverFactory.driver;;
		bResultFlag = HtmlReporter.bResultFlag;
		sBCDescription= HtmlReporter.sBCDescription;
	}

	/**
	 * Function : switchFraxr.es
	 * Developed on : 7//8/2016
	 * Description : Switch Frames
	 * @author N6420S2 (Baba Faicruddin 0)
	 * @return void
	 * @param currDriver, frames
	 * @throws InterruptedException I
	 * @throws Exception
	 * */
	@SuppressWarnings("static-access")
	public static void switchFrames(WebDriver currDriver, String[] frames) throws IOException, InterruptedException{
		try{
			Thread.sleep(1000);
			currDriver.switchTo().defaultContent();

			if(frames.length==0){
				reporter.Log("switchFrames", "No frames to switch", "pass");
				return;
			}
			if (frames.length==1) {
				currDriver.switchTo().frame(frames[0]);
			}else{
				for(String frm:frames){
					Thread.sleep(600);
					try {
						currDriver.switchTo().frame(frm);
					}catch(NoSuchFrameException e) {
						Log.error("NoSuchFrameException occured "+ frm +" -- "+ e.getMessage());
					}
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("switchFrames", "Switch Frames got an exception", "fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("switch frames got failed", false);
		}
	}

	/* Function: waitForElementLoad
	 * Description: Function to wait for element to exist on application
	 * @author N6420S2
	 * Developed on: S//3/2016
	 * @parasn Element identifier as string, expected timeout to wait
	 * @return boolean
	 * @throws IOException
	 * */
	public static boolean waitForElementLoad(WebElement element, int timeout) throws IOException{
		boolean flag = false;

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, timeout);

		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			flag =true;
		}catch(NoSuchElementException e) {
			flag =false;
			Log.error("NoSuchElementException occured "+ e.getMessage());
			HtmlReporter.Log("waitForElementLoad", "No Such Element Exception occured : "+ e.getMessage(), "fail");
		}
		wait = null;
		// driver.manage().timeouts ().implicitlyWait(objTimeOut, Timeunit.SECONDS);
		return flag;
	}


	/**
	 * Function: isExists
	 * Description: Validates the element is existing or not
	 * @author N6420S2
	 * Developed on: S//6/2016
	 * @param WebElement, timeout
	 * @return Boolean
	 * @throws IOException
	 */
	public boolean isExists(WebElement pageFactoryElement, int timeout) throws IOException{
		boolean isPresent=false;

		if(GUIComponents.waitForElementLoad(pageFactoryElement, timeout)) {
			if(pageFactoryElement.isDisplayed()) isPresent = true;
		}else{
			isPresent = false;
		}
		return isPresent;
	}

	/* Function: isExists
				193 * Description: Validates the element is existing or not
				194 * @author N6420S2
				19S Developed on: S//6/2016
				196 * @param WebElement
				197 * @return Boolean
						198 * @throws IoException
						199 */
	public boolean isExists(WebElement pageFactoryElement) throws IOException{
		boolean isPresent=false;

		if(GUIComponents.waitForElementLoad(pageFactoryElement, 2)) {
			if(pageFactoryElement.isDisplayed()) isPresent = true;
		}else{
			isPresent = false;
		}

		return isPresent;

		/*Final String elementl = DriverFactory.getPageFactoryLocatorString (element);
			if(elementl.contains (n//n)) {
					if (driver.findElements(By.xpath(elementl)).isEmpty()){
						isPresent = false;
					)
					}else{
						if (driver.findElements(By.id(elementl)).isEmpty()){
							isPresent = false;
					)
						}
					 return isPresent;*/
	}




	/* Function: getRowcount
	 * Description: Get WebTable row count
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @parazn WebTable element
	 * @return rowCount
	 * @throws Exception
	 */
	public int getRowCount(WebElement pageFactory_webTable) throws IOException{
		final String webTable1 = DriverFactory.getPageFactoryLocatorString(pageFactory_webTable);
		int outPut = -1;
		try{
			if(webTable1.contains("//")){
				if (driver.findElement(By.xpath(webTable1)).isDisplayed()) {
					List<WebElement> rows = driver.findElements(By.xpath(webTable1+"/tbody/tr"));
					if((rows.size())!=0){
						outPut = rows.size()-1;
					}
				}else{
					outPut = -1;
				}
			}else{
				if (driver.findElement(By.id(webTable1)).isDisplayed()) {
					String convToXPath = "//table[@id='"+webTable1+"']";
					List<WebElement> rows = driver.findElements(By.xpath(convToXPath+"/tbody/tr"));
					if((rows.size())!=0){
						outPut = rows.size()-1;
					}
				}else{
					outPut = -1;
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("getRowCount", "Get Row count from WebTable: "+pageFactory_webTable.toString(), "fail");
			Log.error("cS;:hEementException occured "+ e.getMessage());
			Assert.assertTrue("Get Row count of WebTable is got failed", false);
		}
		return outPut;
	}




	/*
	 * Function: getRowcount
	 * Description: Get WebTable row count
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element
	 * @return rowCount
	 * @throws Exception
	 * */
	public int getRowCount(WebElement webTable, int... tbody) throws IOException{
		try{
			final WebElement webTable1 = webTable; //DriverFactory.geta'eFaz::r1Z:catorString(webTable);
			int outPut;
			if (webTable1.isDisplayed()){
				if(tbody.length==0){
					outPut = webTable1.findElements(By.xpath("//tbody[l]/tr")).size();
				}else{
					outPut = webTable1.findElements(By.xpath("//tbody["+tbody[0]+"]/tr")).size();
				}
			}else{
				outPut = -1;
			}
			return outPut;
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("getRowCount", "Failed to Get Row count of WebTable : "+webTable.toString(), "fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Failed to Get Row count of WebTable : "+webTable.toString(), false);
			return -1;
		}
	}

	/**
	 * Function : clickWebTableColumnfleader
	 * Description: Click a header of table (we can sort columns by clicking on tables)
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param webTable, coIname
	 * @return boolean
	 * @throws IOException
	 * */
	public boolean clickWebTableColumnHeader(WebElement webTable, String coIname) throws IOException {
		try{
			int cellndex = GUIComponents.getColumnIndexFromWebTable(webTable, coIname);
			if (cellndex==0){
				Log.info("Failed in clickWebTableColumnHeader--- Column Index returned 0");
				return false;
			}
			try{
				WebElement cell = webTable.findElement(By.xpath("./tbody[l]/tr["+1+"]")).findElement(By.xpath("./th["+cellndex+"]"));
				utils.highlightMe(cell);
				Thread.sleep(400);
				cell.click();
				Thread.sleep(400);
				return true;
			}catch(InterruptedException e) {
				Log.info("Failed in clickWebTableColumnlleader--- " + e.getMessage());
				HtmlReporter.Log("clickwebTableColumnHeader", "Failed Click WebTable Header Column of table: "+webTable.toString(), "fail");
				Assert.assertTrue("Failed click webtable header column", false);
				return false;
			}
		}catch(IOException e){
			Log.info("Failed in clickWebTableColumnHeader--- " + e.getMessage());
			HtmlReporter.Log("clickWebTableColumnHeader", "Failed Click WebTable Header Column of table: "+webTable.toString(), "fail");
			Assert.assertTrue("Failed click webtable header column", false);
			return false;
		}
	}


	/**
	 * Function: clickCellInWebTable
	 * Description: click Cell in webtable with webtable, tbody, row and column
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, tbody, row, col
	 * @return rowCount
	 * @throws IOException
	 */
	public boolean clickCellInWebTable(WebElement webTable, int tbody, int row, int col) throws IOException{
		try{
			WebElement cell = webTable.findElement(By.xpath("./tbody["+tbody+"]/tr["+row+"]")).findElement(By.xpath("./td["+col+"]"));
			utils.highlightMe(cell);
			Thread.sleep (400);
			cell.click();
			Thread.sleep (400);
			return true;
		}catch(InterruptedException e) {
			HtmlReporter.Log("clickCellInWebTable", "Failed Click cell in WebTable of table: "+webTable.toString(), "fail");
			Log.info("Failed in clickCellInWebTable--- " + e.getMessage());
			Assert.assertTrue("Failed Click cell in WebTable", false);
			return false;
		}
	}

	/* Function: clickCellinWebTable
	 * Description: Click cell in WebTable
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable Element, int row, int col
	 * @return boolean
	 * @throws IOException
	 * */
	public boolean clickCellInWebTable(WebElement webTable, int row, int col) throws IOException {
		try{
			WebElement cell = webTable.findElement(By.xpath("./tbody[l]/tr["+row+"]")).findElement(By.xpath("./td["+col+"I"));
			utils.highlightMe(cell);
			Thread.sleep(400);
			cell.click();
			Thread.sleep(400);
			return true;
		}catch(InterruptedException e) {
			HtmlReporter.Log("clickcellInwebTable", "Failed click cell in webtable of table: " + webTable.toString(), "fail");
			Log.info("Failed in clickCellInWebTable--- " + e.getMessage());
			Assert.assertTrue("Failed Click cell in WebTable", false);
			return false;
		}
	}

	/* Function: clickHeaderCellInwebTable
	 * Description: Click header cell in webtable
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String coIname
	 * @return boolean
	 * @throws IOException
	 * */
	public boolean clickHeaderCellInwebTable(WebElement webTable, String coIname) throws IOException{
		try{
			WebElement cell = null;

			if (webTable.findElement(By.xpath("./thead[1]Itr/thlfa[contains(text(),  '"+coIname+"')]")).isDisplayed()) {
				cell = webTable.findElement(By.xpath("./thead[1]/tr/th//a[contains (text(),'"+coIname+"')]"));
			}else if(webTable.findElement(By.xpath("./tbody[1]/tr/th//a[contains(text(),'"+coIname+"')]")).isDisplayed()){
				cell = webTable.findElement(By.xpath("./thead[1]Itrlthl/a[contains(text(), '"+coIname+"')]"));
			}

			//WebElement cell = webTable.findElement(By.xpath("./tbody[1J/tr["+row-4-"]")).findElement(By.xpath("./td("+col+"]"));
			if (cell!=null){
				utils.highlightMe (cell);
				Thread.sleep(400);
				cell.click();
				Thread.sleep(400);
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			HtmlReporter.Log("clickHeaderCellInwebTable", "Failed click header cell in web table: "+webTable.toString(), "fail");
			Log.info("Failed in clickCellInWebTable--- " + e.getMessage());
			Assert.assertTrue("Failed Click Header in WebTable", false);
			return false;
		}
	}

	/* Function: getRowcount
	 * Description: Get WebTable row count
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @paran String tableIdentifier
	 * @return rowCount
	 * @throws IOException
	 * */
	public int getRowCount (String webTable) throws IOException{
		try {
			final String webTable1 = webTable;
			int outPut = -1;
			if(webTable1.contains("//")) {
				if (driver.findElement(By.xpath(webTable1)).isDisplayed()) {
					List<WebElement> rows = driver.findElements(By.xpath(webTable1+"/tbody/tr"));
					if((rows.size()) !=0){
						outPut = rows.size();
					}
				}else{
					outPut = -1;
				}
			}else{
				if (driver.findElement(By.id(webTable1)).isDisplayed()) {
					String convToXPath = "//table[@id'"+webTable1+"']";
					List<WebElement> rows = driver.findElements(By.xpath(convToXPath+"/tbody/tr"));
					if((rows.size()) !=0){
						outPut = rows.size();
					}
				}else{
					outPut = -1;
				}
			}
			return outPut;
		}catch(Exception e){
			HtmlReporter.Log("getRowCount", "Failed to Get row count of Table: "+webTable.toString(), "fail");
			Log.info("Failed in getRowCount----- " + e.getMessage());
			Assert.assertTrue("Failed to Get row count of Table: "+webTable.toString(), false);
			return -1;
		}
	}

	/**
	437 * <hl>ByncPage</hl>
	488 * The ByncPage program wait till page loads
	489
	490 * @author Dhilip
	491 * Updated By : Baba Faicruddin D
	492 * @version 1.0
	493 * @category Web Page Related
	494 * @pararn
	49S */
	public static void ByncPage(){
		driver.manage().timeouts().implicitlyWait(PublicVariables.pageTimeOut, TimeUnit.SECONDS);
	}

	/**
	 * Function: set
	 * Description: Set Value in Web text field using Selenium - type method
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @parasn WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	@SuppressWarnings ("deprecatic E ")
	public void set(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("Set", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", "Fail");
				// throw new Exception(elementIdentifierString + "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in appiication.not exists, throwing exception here", false);
			} else {
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "Set" + "," + input);
				try {
					if(isExists(webElement)) {
						selenium.type(elementIdentifierString, input);
						if(detailedReport.equalsIgnoreCase("yes")) {
							HtmlReporter.Log( "Set", "Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "Set", "Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'." , "Fail");
						// throw new Exception(elementIdentifierString + "is not displaying");
						Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not displaying, throwing exception here", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "Set", "Set the Value '" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
					throw e;
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log( "Set", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElerrentException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false) ;
			throw e;
		}
	}

	/**
	 * Function: setSecureFassword
	 * Description: Set decrypted password Value in Web text passwcrd field using selenium - type method
	 * @author N6420S2 (Baba Fakruddin Dudeirula)
	 * @category Web Object Related
	 * @param WebTable element, String encryptedPassword
	 * @return void
	 * @throws IOException
	 */
	@SuppressWarnings({"static-access", "deprecation"})
	public void setSecurePassword(WebElement webElement, String encryptedPasswor0) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("setSecurePassword", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// throw new Exception(elementIdentifierString + "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application.not exists, throwing exception here", false );
			}else{
				// Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + n," + "Set" + "," + input);
				try{
					if(isExists(webElement)) {
						byte[] decryptedPassword = utils.DecryptPassword(encryptedPasswor0);
						String op = new String(decryptedPassword);
						selenium.type(elementIdentifierString, op);
						if(detailedReport.equalsIgnoreCase("yes") ) {
							HtmlReporter.Log( "Set", "Enter the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "'.", "Pass") ;
						}
					}else{
						HtmlReporter.Log( "Set", "Enter the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("Enter the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "'.", false );
						// throw new Exception(elementIdentifierString + "is not displaying");
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "Set", "Set the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Set the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log( "Set", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false );
		}
	}


	/* Function: typeSecurePassword
	 * Description: Type decrypted password Value in Web text password field using webdriver - sendlceys method
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String encryptedPassword
	 * @return void
	 * @throws IOException
	 * */
	@SuppressWarnings ("static-access")
	public void typeSecurePassword(WebElement webElement, String encryptedPasswor0) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("setSecurePassword", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence.n application.not exists, throwang exception here", false);
				// throw new Exception(elementIdentifierString + "is not displaying");
			}else{
				// Log.info("Start - PerformAction - Arguments (" + elernenCdentfterString + "," + webElement + "," + "Set" + "," + input);
				try{
					if(isExists(webElement)){
						byte[] decryptedPassword = utils.DecryptPassword(encryptedPasswor0);
						String op = new String(decryptedPassword);
						webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END) ,op);
						if(detailedReport.equalsIgnoreCase("yes")) {
							HtmlReporter.Log( "Set", "Enter the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "Set", "Enter the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("Enter the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "'.", false );
						//						throw new Exception(elementIdentifierString + "is not displaying");
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "Set", "Set the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "' » Error : ." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage ());
					Assert.assertTrue("Set the Value '" + "It's Password" + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log( "Set", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/* Function: type
	 * Description: Type Value in Web text field using webdriver - sendkeys method
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void type(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("type", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				// throw new Exception(elementIdentifierString+ "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
			}else{
				Log.info("Start - PerformAction - Argunents (" + elementIdentifierString + "," + webElement + "," + "type" + "," + input);
				try{
					if(isExists(webElement)) {
						webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END) ,input);
						if(detailedReport.equalsIgnoreCase("yes")) {
							HtmlReporter.Log( "type", "Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "type", "Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'." ,"Fail");
						Assert.assertTrue("Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'.", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "type", "type the Value '" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("type the Value '" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log( "type", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail") ;
			Log.error("NS:hElerertException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}


	/* Function: textAreaType
	 * Description: Type Value in Web text Area field using webdriver - sendlreys method
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void textAreaType(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString (webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("text area type", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue (false);
				// throw new Exception(elementIdentifierString + "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "text area type" + "," + input);
				try{
					if(isExists(webElement)){
						webElement.click();
						webElement.sendKeys(input);
						if(detailedReport.equalsIgnoreCase("yes")){
							HtmlReporter.Log( "text area type", "Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'.", "Pass");
						}
					}else{
						HtmlReporter.Log( "text area type", "Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'.", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "text area type", "type the Value '" + input + "' in the field '" + elementIdentifierString + "'>> Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("type the Value '" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log( "text axa type", "Validating Object" + elementIdentifierString + " existence in application, not exists,throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: typeNotNul].
	 * Description: Type not null Value in Web text field using webdriver - sendkeys method
	 * @author N6420S2 (Baba Fatruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void typeNotNull(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("typeNotNull", "Validating Object" + elementIdentifierString + " existence in application. not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				// throw new Exception(elementIdentfierString + "is not displaying");
				Assert.assertTrue("type the Value '" + input + "' in the field is not visible'" + elementIdentifierString, false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "typeNotNull" + "," + input);
				try{
					if(isExists(webElement)){
						if(webElement.getText().isEmpty()){
							webElement.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END) ,input);
							if(detailedReport.equalsIgnoreCase("yes")) {
								HtmlReporter.Log( "typeNotNull", "Enter the Value if Null'" + input + "' in the field '" + elementIdentifierString + "'." , "Pass");
							}
						}
					}else{
						HtmlReporter.Log("typeNotNull", "Enter the Value if Null'" + input + " in the field '" + elementIdentifierString + "." ,"Fail");
						Assert.assertTrue("Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'i", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log("typeNotNull", "type the Value if Null'" + input + "' in the field '" + elementIdentifierString + "'>> Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("type the Value '" + input + "' in the field '" + elementIdentifierString + " » Error :." + e.getMessage(), false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("typeNotNull", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false) ;
		}
	}

	/* Function: Select
	 * Description: Select Value in Web list field using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void select(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)){
				HtmlReporter.Log("select", "Validating Object" + elementIdentifierString + " existence in applicaton not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				// throw new Exception(elementIdentifierString + "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not displaying, throwing exception here ", false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "select" + "," + input);
				try{
					if(isExists (webElement)) {
						Select dropdown = new Select(webElement);
						dropdown.selectByVisibleText(input);
						if(detailedReport.equalsIgnoreCase("yes") ) {
							HtmlReporter.Log( "select", "Select the Value '" + input + "' in the field '" + elementIdentifierString + "'.", "Pass") ;
						}
					}else{
						HtmlReporter.Log("select", "Select the value'" + input + "' in the field '" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("Enter the Value '" + input + "' in the field '" + elementIdentifierString + "'.", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log("select", "Select the Value '" + input + "' in the field '" + elementIdentifierString + "' >> Error :." + e.getMessage(), "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("type the Value '" + input + "' in the field '" + elementIdentifierString + "' >> Error :." + e.getMessage(), false );
				}

			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("select", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error ("NoSuohElerrentxoeption occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + "existence in application.nct exists, throwing exception here", false);
		}
	}

	/**
	 * Function: selectNotNull
	 * Description: Select Not Null Value in Web list field using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @parasn WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void selectNotNull(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString (webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("selectNotNull", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				// throw new Exception(elementIdentifierString + "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "selectNotNull" + "," + input);
				try{
					if(isExists(webElement)){
						Select dropdown = new Select(webElement);
						WebElement option=dropdown.getFirstSelectedOption();
						if(option.getText().isEmpty()){
							dropdown.selectByVisibleText(input);
							if(detailedReport.equalsIgnoreCase("yes")){
								HtmlReporter.Log( "selectNotNull", "Select the Value '" + input + "' in the field '" + elementIdentifierString + "'.", "Pass");
							}
						}
					}else{
						HtmlReporter.Log("selectNotNujjl", "Select the value in dropdown if it contains empty'" + input + "' in the field '" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("Select the value in dropdown if it contains empty'" + input + "' in the field '" + elementIdentifierString + "'.", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log("selectNotNull", "Select the value in dropdown if it contains empty'" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Select the value in dropdown if it contains empty'" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("selectNotNull", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: se].ectByIndex
	 * Description: Select Value by index in Web list field using webdriver
	 * @author N6420S2 (Baba Faicruddin Dudeicula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void selectBIndex(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("selectByIndex", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				// throw new Exception(elementIdentifierString + "is not displaying");
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence n application.nct exists, throwin exception here", false);
			}else{
				Log.info("Start - PerforrnAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "selectByIndex" + "," + input);
				try{
					if(isExists(webElement)){
						Select dropdown = new Select(webElement);
						dropdown.selectByIndex (Integer.parseInt (input));
						if(detailedReport.equalsIgnoreCase("yes")){
							HtmlReporter.Log( "selectByIndex", "Select the Value by index'" + input + "' in the field '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log("selectByIndex", "Select the value by index'" + input + "' in the field '" + elementIdentifierString + "'.", "Fail");
						Assert.assertTrue("Select the value by index'" + input + "' in the field '" + elementIdentifierString + "'.", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log("selectByIndex", "Select the Value By index'" + input + "' in the field '" + elementIdentifierString + "' » Error ;." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Select the Value By index'" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("selectByIndex", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing xcption here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: selectByInstring
	 * Description: Select Value by partial text in Web list field using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 */
	public void selectByInstring(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("selectByInstring", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing xcption here", false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "selectByInstring" + "," + input);
				try{
					if(isExists(webElement)){
						String sListOption;
						Boolean bInFlag= false;
						Select dropdown = new Select(webElement);
						List<WebElement> allOptions=webElement.findElements(By.tagName("option"));
						for(int i=0;i<allOptions.size();i++){
							sListOption = allOptions.get(i).getText();
							if(sListOption.contains(input)){
								dropdown.selectByVisibleText(sListOption);
								// GUIComponents.waitForPageLoad (driver);
								if(detailedReport.equalsIgnoreCase("yes")) {
									HtmlReporter.Log( "selectByInstring", "Select the Value by contains string value '" + input + "' in the field '" + elementIdentifierString + "'.", "Pass");
								}
								bInFlag= true;
								break;
							}
						}
						if(!bInFlag){
							HtmlReporter.Log( "selectByInstring", "Select the Value by contains string value'" + input + "' in the field '" + elementIdentifierString + "'.", "Fail");
							Assert.assertTrue("Select the Value by contains string value'" + input + "' in the field '" + elementIdentifierString + "'.", false );
						}
					}else{
						HtmlReporter.Log("selectByInstring", "Select the Value by contains string value'" + input + "' in the field '" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("Select the Value by contains string value'" + input + "' in the field '" + elementIdentifierString + ",.", false );
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log("selectByInstring", "Select the Value by contains string value'" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage() , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Select the Value by contains string value'" + input + "' in the field '" + elementIdentifierString + "' » Error :." + e.getMessage(), false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("selectByInstring", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Objec:" + elementIdentifierString +  " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: checkBoxOnOff
	 * Description: Check box On or Off using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @paraxn WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void checkBoxOnOff(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("click", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exption here", false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "Check On Off" + "," + input);
				try{
					if(isExists (webElement)) {
						if(input.toString().toLowerCase().equalsIgnoreCase("on")){
							if (webElement.isSelected()){
								webElement.click();
								if(detailedReport.equalsIgnoreCase("yes")){
									HtmlReporter.Log( "CheckEox Off", "Check Off the object is successful -->'" + elementIdentifierString + ".." , "Pass");
								}
							}
						}else if(input.toString().toLowerCase().equalsIgnoreCase("off")){
							if (webElement.isSelected()){
								webElement.click();
								HtmlReporter.Log( "CheckEox Off", "Check Off the object is successful -->'" + elementIdentifierString + ".." , "Pass");
							}
						}
					}else{
						HtmlReporter.Log( "CheckBox On Off", "not existing the object -S>'" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("not existing the object >'" + elementIdentifierString + "'.", false );
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "CheckBox On Off", "Failed the object click operation >'" + elementIdentifierString + "'." , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Failed the object click operation >'" + elementIdentifierString + "'.", false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("CheckBox On Off ", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: click
	 * Description: click Element using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element
	 * @return void
	 * @throws IOException
	 * */
	public void click(WebElement webElement) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("click", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception rieret", false) ;
			}else{
				Log.info("Start - PerformAction - Arguments C" + elementIdentifierString + " " + webElement + "," + "click");
				try{
					if(isExists (webElement)) {
						webElement.click();
						if(detailedReport.equalsIgnoreCase ("yes")) {
							HtmlReporter.Log( "click", "click the object is successful -->" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "click", "not existing the object -->'" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("not existing the object >'" + elementIdentifierString + "'i", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "click", "Failed the object click operation >'" + elementIdentifierString + "'." , "Fail");
					Log.error("NoSuchEleinentException occured "+ e.getMessage());
					Assert.assertTrue("Failed the object click operation >'" + elementIdentifierString + "'.", false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("click", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: actionClick
	 * Description: action click Element using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @paran WebTable element
	 * @return void
	 * @throws IOException
	 * */
	public void actionClick(WebElement webElement, String[]... input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("actionClick", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception nere", false) ;
			}else{
				if(input.length>0) Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "actionClick" + "," + input[0]);
				try{
					if (isExists(webElement)) {
						WebElement elem = webElement;
						new Actions (driver).click(elem).perform();
						if(detailedReport.equalsIgnoreCase("yes")) {
							HtmlReporter.Log("actionClick", "Click the object '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "actionClick", "not existing the object -->'" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("not existing the object -->'" + elementIdentifierString + "'.",false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "actionClick", "Failed the object click operation >'" + elementIdentifierString + "'." , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Failed the object click operation -->" + elementIdentifierString + "'.", false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("actionClick", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: actionDoubleClick
	 * Description: action double click Element using webdriver
	 * @author N630S24 (Nitesh)
	 * @category Web Object Related
	 * @param WebElement
	 * @return void
	 * @throws IOException
	 * */
	public void actionDoubleClick(WebElement webElement) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("actionDoubleClick", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue (false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
			}else{
				Log.info("Start - PerformAction - Arguments (" + elementIdentifierString + "," + webElement + "," + "actionDoubleClick");
				try{
					if(isExists(webElement)){
						WebElement elem = webElement;
						new Actions (driver).doubleClick(elem).perform();
						if(detailedReport.equalsIgnoreCase("yes")) {
							HtmlReporter.Log("actionDoubleClick", "Click the object '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "actionDoubleClick", "not existing the object -->'" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("not existing the object -->'" + elementIdentifierString + "'.",false );
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "actionDoubleClick", "Failed the object click operation -->'" + elementIdentifierString + "'." , "Fail");
					Log.error("cS;oheretException occured "+ e.getMessage());
					Assert.assertTrue("Failed the object click operation >'" + elementIdentifierString + "e.", false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("actionDoubleClick", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: hoverClick
	 * Description: hover click Element using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void hoverClick(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString(webElement);

		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("hoverclick", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue (false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
			}else{
				Log.info("Start - PerforinAction - Arguments C" + elementIdentifierString + "," + webElement + "," + "hoverClick" + "," + input);
				try{
					if(isExists(webElement)){
						WebElement elem = webElement;
						new Actions (driver).moveToElement(elem).build().perform();
						objWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText(input))));
						driver.findElement(By.linkText (input)).click();
						if(detailedReport.equalsIgnoreCase("yes")){
							HtmlReporter.Log("hoverClick", "Hover Click the object '" + elementIdentifierString + "'." , "Pass");
						}
					}else{
						HtmlReporter.Log( "hoverClick", "not existing the object -->'" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("not existing the object >'" + elementIdentifierString + "'.", false);
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "hoverClick", "Failed the object click operation --->'" + elementIdentifierString + "'." , "Fail");
					Log.error("NcS:.rh3llenentException occured "+ e.getMessage());
					Assert.assertTrue("Failed the object click operation --->'" + elementIdentifierString + "'.", false);
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("hoverClick", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Function: hover
	 * Description: hover on Element using webdriver
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable element, String input
	 * @return void
	 * @throws IOException
	 * */
	public void hover(WebElement webElement, String input) throws IOException{
		String elementIdentifierString = DriverFactory.getPageFactoryLocatorString (webElement);
		try{
			if (!waitForElementLoad(webElement, PublicVariables.objTimeOut)) {
				HtmlReporter.Log("hover", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
				// Assert.assertTrue(false);
				Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
			}else{
				Log.info("Start - PerfornAction - Arguments C" + elementIdentifierString + "," + webElement + "," + "hover" + "," + input);
				try{
					if(isExists(webElement)){
						WebElement elem = webElement;
						// new
						//Actions(driver).click(elem).noveoelement(driver.findElement(By.linkText(sData))).click().build().perform();
						new Actions (driver).moveToElement(elem).build().perform();
						objWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText(input))));
						if(detailedReport.equalsIgnoreCase("yes")) {
							HtmlReporter.Log("hover", "Hover over the object '" + elementIdentifierString + "." , "Pass");
						}
					}else{
						HtmlReporter.Log( "hover", "not existing the object >'" + elementIdentifierString + "'." , "Fail");
						Assert.assertTrue("not existing the object --->'" + elementIdentifierString + "'.", false );
					}
				}catch(NoSuchElementException e) {
					HtmlReporter.Log( "hover", "Failed the object hover operation -->'" + elementIdentifierString + "'." , "Fail");
					Log.error("NoSuchElementException occured "+ e.getMessage());
					Assert.assertTrue("Failed the object hover operation -->'" + elementIdentifierString + "'.", false );
				}
			}
		}catch(NoSuchElementException e) {
			HtmlReporter.Log("hover", "Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here" , "Fail");
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Assert.assertTrue("Validating Object" + elementIdentifierString + " existence in application, not exists, throwing exception here", false);
		}
	}

	/**
	 * Get the list value that is selected in a list
	 * @parasn webElement
	 * @return String
	 * @throws IoException
	 */
	public String getSelectedListValue(WebElement webElement) throws IOException{
		try{
			Select sel = new Select (webElement);
			String selectedValue = sel.getFirstSelectedOption().getText();
			return selectedValue;
		}catch(NoSuchElementException e) {
			Log.error("NoSuchElementException occured "+ e.getMessage());
			HtmlReporter.Log("getSelectedListValue", "Failed to capture the selected value from list box", "fail");
			Assert.assertTrue("Failed to capture the selected value from list box", false);
		}
		return null;
	}

	// ==========WebTab le = = ====== = = = = = ==//

	/**
	 * <hl>getColumnValuesFromWebTable</hl>
	 * The getColumnValuesFromWebTable program implements action to return the Column values of the specified Column within a Webtable
	 * @author Kiran
	 * @version 1.0
	 * @category Web Object Related
	 * @para.m Webtable
	 * @param ColumnName
	 * @return List<String> - column values
	 * @throws Exception
	 */
	public static List<String> getColumnValuesFromWebTable(WebElement Webtable, String ColumnName) throws IOException{

		List<String> returnColumnData = new ArrayList<>();
		WebElement table = null;
		int col_position = 0;

		try{
			/*//code to identify the column position
			List<WebElement> th=Webtable.findElements(By.xpath("./thead/tr/th"));
			int col position=0;
			for(int colunin=0;columnn<th.size();columnn++){
				if(ColumnName.equalsIgnoreCase(th.get(columnn).gettext(){
					col position=column+1;
					Log.info("Columnn position of '" + ColumnnName + "' is: " + col_position);
					break;
				}*/
			col_position = getColumnIndexFromWebTable(Webtable,ColumnName);

			if (col_position ==0){
				Log.info("No Column with name :'" + ColumnName +"' found in the Webtable");
				return returnColumnData;
			}

			//identify the table if it has tbody tag
			List<WebElement> tbodies= Webtable.findElements(By.tagName("tbody"));
			if (tbodies != null){
				table = tbodies.get(0);
			}else{
				table = Webtable;
			}

			//get the table data for the specified column
			List<WebElement> ColumnsData = table.findElements(By.xpath("./tr/td["+col_position+"]"));
			for(WebElement e: ColumnsData){
				returnColumnData.add(e.getText());
			}
		}catch (NoSuchElementException e) {
			Log.error("UzSa:hElementExcepc.on occured "+ e.getMessage());
			HtmlReporter.Log("getColumnValuesFrcmWebTable", "Failed to capture the column values from webtable : "+Webtable.toString(), "fail");
			Assert.assertTrue("Failed to capture the column values from webtable : "+Webtable.toString(), false);
		}
		Log.info("Colurnn data:" + returnColumnData);
		return returnColumnData;
	}

	/**
	 * Function: validateColumnNamesInWebTable
	 * Description: Validates all column names in webtable based on our input
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @category Web Object Related
	 * @param WebTable, Column Names
	 * @return boolean
	 * @throws IOException
	 * */
	public boolean validateColumnNamesInWebTable(WebElement webTable, List<String> coInames) throws IOException{
		boolean actOutput = false;
		String colValue = null;
		try{
			ListIterator<String> itr = coInames.listIterator();

			while (itr.hasNext()) {
				colValue = itr.next();
				if (GUIComponents.getColumnIndexFromWebTable(webTable, itr.next()) !=0) {
					actOutput = true;
				}else{
					Log.info("Column Name : "+colValue+" is not available in webtable");
					actOutput = false;
					break;
				}
			}
			return actOutput;
		}catch(NoSuchElementException e) {
			Log.error("NoSuchEleraentException occured "+ e.getMessage());
			HtmlReporter.Log("getColumnValuesFroxnWebtable", "Failed to validate the column names from webtable : "+webTable.toString(), "fail");
			Assert.assertTrue("Failed to validate the column names from webtable : "+webTable.toString(), false);
		}
		return actOutput;
	}

	/**
	 * <hl>getColumnIndexFromWebTable</hl>
	 * The getColumnIndexFroniWebTable program implements action to return the Column index within a Webtable
	 * @author ¡(iran
	 * @version 1.0
	 * @category Web Object Related
	 * @param Webtable
	 * @param ColumnName
	 * @return Integer - column index
	 * @throws IOException
										.*/
	public static Integer getColumnIndexFromWebTable(WebElement webtable, String ColumnName) throws IOException{
		Integer col_position = 0;

		try{
			List<WebElement> th=webtable.findElements(By.xpath("./*/tr/th"));

			for(int column=0;column<th.size() ;column++){
				if(ColumnName.equalsIgnoreCase(th.get(column).getText().trim())){
					col_position=column+1;
					if(detailedReport.equalsIgnoreCase("yes")) {
						HtmlReporter.Log("getColumnIndexFromwebTable", "column index :"+col_position+ "--> from webtable based on Column Name: "+ColumnName, "Pass");
					}
					Log.info("Column position of '" + ColumnName + "' is: " + col_position);
					break;
				}
			}
		}catch (NoSuchElementException e) {
			Log.error("0;:S_chElementExctjcon occured "+ e.getMessage());
			HtmlReporter.Log("getcolumnIndexFromWebTable", "Failed to get column index from webtable : "+webtable.toString(), "fail");
			Assert.assertTrue("Failed to get column index from webtable : "+webtable.toString(), false);
		}

		return col_position;
	}

	/**
	 * <hl>getCellValueFrcitwebTable</hl>
	 * The getCellValueFrorcWebTable program inplenents action to return the Column index within a Webtable
	 * @author ¡(iran
	 * @version 1.0
	 * @category Web Object Related
	 * @param Webtable
	 * @param ReturnCoIname - column name whose cell value to return
	 * @param DependentCoIname - dependent column name
	 * @param DependentColVal - dependent column value
	 * @return String - column cell value
	 * @throws IOException
	 */
	public static String getCellValueFromWebTable(WebElement webtable, String ReturnColname, String DependentColname, String DependentColVal) throws IOException{

		String CellValue = null;
		List<String> dependentColData = new ArrayList<>();
		List<String> returnColumnData = new ArrayList<>();
		int rowindex = 0;

		try
		{
			dependentColData = getColumnValuesFromWebTable(webtable,DependentColname);
			returnColumnData = getColumnValuesFromWebTable(webtable,ReturnColname);
			rowindex = dependentColData.indexOf (DependentColVal);
			if(rowindex >= 0){
				CellValue = returnColumnData.get(rowindex);
				// System.out.printIn("Returnded Cell value: " + CellValue);
				return CellValue;
			}else{
				Log.info("Failed in getCellValueFromwebTable--- ");
				Log.info("Dependent Cell Value: '" + DependentColname +"' has no cell with value: '" + DependentColVal +"");
				return CellValue;
			}
		}catch (NoSuchElementException e) {
			Log.error("NoSuchEleinentException occured "+ e.getMessage());
			HtmlReporter.Log("getColumnIndexFromwebTable", "Failed to get column index from webtable : "+webtable.toString(), "fail") ;
			Assert.assertTrue("Failed to get column index from webtable : "+webtable.toString(), false);
		}
		return CellValue;
	}


	/** <hl>getCellValueFrorrwebTable</hl>
	 * The getCellValueFrcrr.WebTable program implements action to return the Column index within a Webtable
	 * @author Kiran
	 * @version 1M
	 * @category Web Object Related
	 * @param Webtable
	 * @param ReturnCoIname - column name whose cell value to return
	 * @param DependentcoIname - dependent column name
	 * @param DependentColVal - dependent column value
	 * @return String - column cell value
	 * @throws IOException
	 */
	public static String getCellValueFromWebTable(WebElement Webtable, String ReturnCoIname, int row) throws IOException{
		String CellValue = null;
		int rowindex = 0;
		List<String> returnColumnData = new ArrayList<>();
		try
		{
			returnColumnData = getColumnValuesFromWebTable(Webtable, ReturnCoIname);
			rowindex = row;
			if( rowindex >= 0){
				CellValue = returnColumnData.get(rowindex);
				// System.out.printIn("Returnded Cell value: " + CellValue);
				return CellValue;
			}else{
				Log.info("Failed in getCellValueFromWebTable--- ");
				return CellValue;
			}
		}catch (NoSuchElementException e) {
			Log.error("NoSuchElementException occured "+ e.getMessage());
			HtmlReporter.Log("getCellValueFrouiwebTable", "Failed to get cell value from webtable : "+Webtable.toString(), "fail");
			Assert.assertTrue("Failed to get cell value from webtable : "+Webtable.toString(), false);
		}
		return CellValue;
	}

	/**
	 * <hl>getRowValuesFromwelable</hl>
	 * The getRowValuesFromWeTable program implements action to return the Row values of the specified Column from Webtable
	 * @author Kiran
	 * @version 1.0
	 * @category Web Object Related
	 * @param Webtable
	 * @param RowIndex
	 * @return List<String> - row values
	 * @throws IOException
	 */
	public static List<String> getRowValuesFromWebtable(WebElement Webtable, Integer RowIndex) throws IOException{
		List<String> returnRowData = new ArrayList<>();
		List<WebElement> allrows = null;
		WebElement table = null;
		try
		{
			List<WebElement> tbodies= Webtable.findElements(By.tagName("tbody"));
			if (tbodies != null){
				table = tbodies.get(0);
			}else{
				table = Webtable;
			}

			//System.out.printIn("Table: " + table.toString());
			allrows=table.findElements(By.tagName("tr"));
			Log.info("Total Rows: " + allrows.size());
			if (allrows.size() == 0){
				Log.info("No Rows found in the Webtable");
				return returnRowData;
			}else{
				if (RowIndex < 0 || RowIndex >= allrows.size()){
					//if (RowIndex > a11rows.sizee){
					Log.info("webTable has '" +allrows.size() + "' rows, whereas passed RowIndex is " + RowIndex );
					return returnRowData;
				}
			}

			//WebElement row = table.findElement(By.tagName("tr["+ RowIndex +9"));!! get the desired row
			WebElement row = allrows.get(RowIndex-1);// get the desired row
			List<WebElement> Columns_row = row.findElements(By.tagName("td")); //To locate columns(cells) of that specific row
			int columns_count = Columns_row.size(); //To calculate no of columns(cells) In that specific row
			// System.out.printIn("Total Columns: " + columns_count);
			for (int column = 0; column < columns_count; column++) {
				// To retrieve text from that specific cell.
				String celtext = Columns_row.get(column).getText();
				returnRowData.add(celtext);
			}
		}catch (NoSuchElementException e) {
			Log.error("NoSuchElementException occured "+ e.getMessage());
			HtmlReporter.Log("getCellValueFromWebTable", "Failed to get row values from webtable "+Webtable.toString(), "fail");
			Assert.assertTrue("Failed to get row values from webtable : "+Webtable.toString(), false);
		}
		return returnRowData;
	}

	/** <hl>getRowNoFromweable</hl>
	 * The getRowNoFromWeable program implements action to return the Row number containing the specified text from Webtable
	 * @author Kiran
	 * @version 1.0
	 * @category Web Object Related
	 * @paran Webtable
	 * @param RowIndex
	 * @return Integer - row number
	 * @throws IOException
	 */
	public static Integer getRowNoFromWebTable(WebElement Webtable, String Value) throws IOException{
		int rowNo=0;
		try
		{
			List<WebElement> rows=Webtable.findElements(By.xpath("./tbody[1]/tr"));
			for(int i=0;i<rows.size();i++)
			{
				WebElement row = Webtable.findElement(By.xpath("./tbody[1]/tr["+(i+1)+"]"));
				if(row.getText().trim().contains(Value))
				{
					rowNo=i+1;
					Log.info("Row Number containing '"+ Value +" is :" + rowNo);
					break;
				}
			}
		}catch(NoSuchElementException e) {
			Log.error("NoSuchElementException occured "+ e.getMessage());
			HtmlReporter.Log("etPrwNoFromWeTable", "Failed to get row no from webtable : "+Webtable.toString(), "fail");
			Assert.assertTrue("Failed to get row no from webtable : "+Webtable.toString(), false);
		}
		return rowNo;
	}

	/**
	 * <hl>getRowNoFroxnWeTable</hl>
	 * The getRowNoFromWeTable program implements action to return the Row number containing the specified text from Webtable
	 * @author Sandeep Kandula
	 * @version 1.0
	 * @category Web Object Related
	 * @param Webtable
	 * @param RowIndex
	 * @return Integer - row number
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static Integer getRowNoFromWebTable(WebElement Webtable, int coInum, String Value) throws IOException, InterruptedException{
		int rowNo=0;
		try
		{
			Utilities ut = new Utilities();
			ut.highlightMe(Webtable);
			List<WebElement> tbody=Webtable.findElements(By.tagName("tbody"));
			List<WebElement> rows=tbody.get(0).findElements(By.tagName("tr"));
			for(int i=1; i<rows.size() ;i++)
			{
				ut.highlightMe(rows.get(1));
				List<WebElement> tds=rows.get(1).findElements(By.tagName("td"));
				if(tds.get(coInum-1).getText().trim().equalsIgnoreCase(Value))
				{
					ut.highlightMe(tds.get(coInum-1));
					rowNo=i+1 ;
					Log.info("Row Number containing '"+ Value +"' is :" + rowNo);
					break;
				}
			}
		}catch (NoSuchElementException e) {
			Log.error("NoSuchElementExcepton occured "+ e.getMessage());
			HtmlReporter.Log("getRowNoFromweTable", "Failed to get row no from webtable : "+Webtable.toString(), "fail");
			Assert.assertTrue("Failed to get row no from webtable : "+Webtable.toString(), false);
		}
		return rowNo;
	}

	/*
	 * Function: setcellValueInTable
	 * Description: Set a value in text box which resides in a webtable
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @version 1
	 * @category Web Object Related
	 * @paran webTable, findRowByValue, findColByValue, cellInput
	 * @return void
	 * @throws IOException
	 */
	public void setCellValueInWebTable(WebElement webTable, String findRowByValue, String findColByValue, String cellInput) throws IOException{
		int coInum = GUIComponents.getColumnIndexFromWebTable(webTable, findColByValue);
		int rowNum = GUIComponents.getRowNoFromWebTable(webTable, findRowByValue);
		final String webTable1 = DriverFactory.getPageFactoryLocatorString(webTable);

		try{
			driver.findElement(By.xpath(webTable1+"/tbody[l]/tr["+rowNum+"]/td("+coInum+"]//input")).click();
			driver.findElement(By.xpath(webTable1+"/tbody[1]/tr["+rowNum+"]/td["+coInum+"]//input")).sendKeys(cellInput);
			driver.findElement(By.xpath(webTable1+"/tbody[1]/tr["+rowNum+"]/td["+coInum+"]//input")).sendKeys(Keys.TAB);
		}catch(NoSuchElementException e) {
			Log.error ("NoSuchElementException occured "+ e.getMessage ());
			HtmlReporter.Log("setCellValueInWebTable", "Failed to set cell value in webtable : "+webTable.toString(), "fail");
			Assert.assertTrue("Failed to set cell value in webtable : "+webTable.toString(), false);
			e.printStackTrace();
		}
	}


	/*'
	 * Function: findElementTypeInWebTable
	 * Description: Find an element type in webtable cell
	 * @author N6420S2 (Baba Faicruddin Dudeicula)
	 * @version 1
	 * @category Web Object Related
	 * @param webTable, objtype, row, col
	 * @return void
	 * @throws IOException
	 */
	@SuppressWarnings ("static-access")
	public static boolean findElementTypeInWebTable(WebElement webTable, String ObjType, int row, int col) throws IOException {
		try{
			List<WebElement> trs = webTable.findElements(By.tagName("tr"));
			boolean flag = false;
			List<WebElement> tds = trs.get(row - 1).findElements(By.tagName("td"));
			if (tds.size() > 0) {
				WebElement ele = tds.get(col-1);

				switch (ObjType.toLowerCase().trim()) {
				case "checkbox":
					List<WebElement> innerElements = ele.findElements(By.tagName("input"));
					if (innerElements.size() > 0) {
						for (WebElement in : innerElements) {
							if (in.getAttribute("type").equals("checkbox") || in.getAttribute("class").contains("checkbox")) {
								if (in.isEnabled() && !(in.isSelected())) {
									flag = true;
									if(detailedReport.equalsIgnoreCase("yes")){
										reporter.Log("Checkbox Type", "Checkbox clicked successfully", "pass");
									}
									break;
								}
							}
						}
					}
					break;
				case "link":
					List<WebElement> innerLinkElements = ele.findElements(By.tagName("a")) ;
					if (innerLinkElements.size() > 0) {
						for (WebElement in : innerLinkElements) {
							if (! (in.getAttribute("type").equals("hidden"))) {
								flag = true;
								if(detailedReport.equalsIgnoreCase("yes")) {
									reporter.Log("Link Type", "Link in cell clicked susseccfully", "pass");
								}
								break;
							}
						}
					}
					break;
				case "image":
					WebElement innerImgelement = ele.findElement(By.tagName("img"));
					if (Integer.parseInt(innerImgelement.getAttribute("width"))>0) {

						flag = true;
						if(detailedReport.equalsIgnoreCase("yes")){
							reporter.Log("Image Type", "Link in cell clicked susseccfully", "pass");
						}
						break;
					}
					break;
				}
			}
			return flag;
		}catch(NoSuchElementException e) {
			Log.error("NoSuchElementException occurcu "+ e.getMessage());
			HtmlReporter.Log("findElementTypeInWebTable", "Failed to find WebElement ", "fail");
			Assert.assertTrue("Failed to find WebElement ", false);
			return false;
		}
	}

	/*,
	 * Function: getElementBylypeInWebTable
	 * Description: Get Element by type in WebTable Cell
	 * @author N6420S2 (Baba Faicruddin Dudeicula)
	 * @version 1
	 * @category Web Object Related
	 * @param webTable, objtype, row, col
	 * @return void
	 * @throws IOException
	 */
	@SuppressWarnings ("static-access")
	public static WebElement getElementByTypeInWebTable(WebElement webtable, String ObjType, int row, int col) throws IOException {
		WebElement actualElement = null;
		try{
			List<WebElement> trs = webtable.findElements(By.tagName("tr"));
			// boolean flag = false;
			List<WebElement> tds = trs.get(row - 1).findElements(By.tagName("td"));
			if (tds.size() > 0) {
				WebElement ele = tds.get(col-1);

				switch (ObjType.toLowerCase().trim()) {
				case "checkbox":
					List<WebElement> innerElements = ele.findElements(By.tagName("input"));
					if (innerElements.size() > 0) {
						for (WebElement in : innerElements) {
							if (in.getAttribute("type").equals("checkbox") || in.getAttribute("class").contains("Checkbox")) {
								if (in.isEnabled() && !(in.isSelected())) {
									actualElement = in;
									// flag = true;
									if(detailedReport.equalsIgnoreCase("yes")){
										reporter.Log("checkbox Type", "CheckEox clicked susseccfully", "pass");
									}
									break;
								}
							}
						}
					}
					break;
				case "link":
					List<WebElement> innerLinkElements = ele.findElements(By.tagName("a"));
					if (innerLinkElements.size() > 0) {
						for (WebElement in : innerLinkElements) {
							if (!(in.getAttribute("type").equals("hidden"))) {
								actualElement = in;
								// flag = true;
								if(detailedReport.equalsIgnoreCase("yes")){
									reporter.Log("Zink lype", "Link in cell clicked susseccruiiy", "pass");
								}
								break;
							}
						}
					}
					break;
				case "image":
					WebElement innerImgelement = ele.findElement(By.tagName("img"));
					if (Integer.parseInt(innerImgelement.getAttribute("width"))>0) {
						actualElement = innerImgelement;
						// flag = true;
						if(detailedReport.equalsIgnoreCase("yes")) {
							reporter.Log("Image Type", "Image type in cell found susseccfully", "pass");
						}
						break;
					}
					break;
				case "span":
					WebElement innerSpanelement = ele.findElement(By.tagName("span"));
					actualElement = innerSpanelement;
					// flag = true;
					if(detailedReport.equalsIgnoreCase("yes")){
						reporter.Log("Span Type", "Color in cell is matched susseccfully", "pass");
					}
					break;
				}
			}
			return actualElement;
		}catch(NoSuchElementException e){
			Log.error("T;:SohE1ementException occured "+ e.getMessage());
			HtmlReporter.Log("getElementByTypeInWebTable", "Failed to Get Element by type in cell in webtable : "+webtable.toString(), "fail");
			Assert.assertTrue("Failed to Get Element by type in cell in webtable : "+webtable.toString(), false);
		}
		return actualElement;
	}

	/**
	 * Function: waitForPageLoad
	 * Description: Wait for page load
	 * @author N6420S2 (Baba Faicruddin Dudekula)
	 * @version 1
	 * @category Web Page Related
	 * @param driver
	 * @return void
	 * */
	public static void waitForPageLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, PublicVariables.pageTimeOut);
		wait.until(pageLoadCondition);
	}

	/**
	 * Function: acceptAlert
	 * Description: Accept Alert
	 * @author N6420S2 (Baba Fakruddin Dudekula)
	 * @version 1
	 * @category Web Object Related
	 * @param driver
	 * @return void
	 * @throws IOException
	 * */
	public void acceptAlert() throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			GUIComponents.waitForPageLoad(driver);
		} catch(NoAlertPresentException e) {
			Log.error("NoAlertPresentException occuxeci" + e.getMessage()) ;
			HtmlReporter.Log("acceptAlert", "Failed to Accept alert on browser : ", "fail");
			Assert.assertTrue("Failed to accept alert on browser ", false);
			//exception handling
		}
	}

	/**'w
	 * Switches to the alert on screen, and makes
	 * decision based on parameter string value
	 * @author E707276
	 * @param alertDecision (accept, dismiss)
	 * (Parameter Values: accept or dismiss)
	 * @throws Exception
	 */
	public void acceptAlert (String alertDecision) throws IOException {
		alertDecision = alertDecision.toLowerCase();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			if(alertDecision.equals("accept")){
				alert.accept();
			}else if(alertDecision.equals("dismiss")){
				alert.dismiss();
			}
		} catch (NoAlertPresentException e) {
			Log.error("NoAlertPresenttxception occured "+ e.getMessage());
			HtmlReporter.Log("acceptAlert", "Failed to Accept alert on browser : ", "fail");
			Assert.assertTrue("Failed to accept alert on browser, Alert input"+e, false);
			e.printStackTrace();
		}
	}

	/**
	 * Checks to see if if there is an alert present
	 * Otherwise returns false
	 * @return
	 * @throws Exception
	 */
	public boolean alertIsPresent() throws IOException{
		try{
			driver.switchTo().alert();
			return true;
		}catch (NoAlertPresentException e){
			Log.error("NoAlertPresentException occured "+ e.getMessage());
			HtmlReporter.Log("alertIsPresent", "Alert is not present ", "fail");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Function: Check Uncheck CheckboxesInATable
	 * Description: Check Uncheck checkboxes in a table
	 * @author Dhilip
	 * @version 1
	 * @category Web Object Related
	 * @param WebElement Webtable,String strColumnName,int StartRow,int EndRow,String strCondition, boolean AliRow
	 * @return void
	 * @throws IOException
	 */
	public void Check_Uncheck_CheckboxesInATable(WebElement Webtable,String strColumnName,int StartRow,int EndRow,String strCondition, boolean AllRow) throws IOException
	{
		List<WebElement> allrows = null;
		WebElement table = null;
		try
		{
			List<WebElement> tbodies= Webtable.findElements(By.tagName("tbody"));
			if (tbodies != null){
				table = tbodies.get(0);
			}else{
				table = Webtable;
			}
			allrows=table.findElements(By.tagName("tr"));
			Log.info("Totla Rows: " + allrows.size());

			if (allrows.size() == 0){
				Log.info("No Rows found in the Webtable");
				HtmlReporter.Log("CheckUnCheckCheckboxesInATable", "No Rows found in the Webtable" , "Fail");
				return ;
			}

			WebElement row = allrows.get(0);
			int colno=0;
			List<WebElement> Columns_row = row.findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			System.out.println("Total Columns: " + columns_count +" "+ allrows.size() );

			for (int column = 0; column < columns_count; column++) {
				String celtext = Columns_row.get(column).getText();
				System.out.println (celtext);
				if(celtext.equalsIgnoreCase (strColumnName))
				{
					System.out.println(strColumnName);
					colno=column;
					break;
				}
			}
			if (AllRow==true)
			{
				for (int i=1;i<=allrows.size()-2;i++)
				{
					WebElement els=allrows.get(i).findElements(By.tagName("td")).get(colno).findElements(By.tagName("input")).get(0);
					if( els.getAttribute("type").equalsIgnoreCase("checkbox")) {
						if(strCondition.equalsIgnoreCase("Check"))
						{
							if(!els.isSelected())
							{
								els.click();
								if(detailedReport.equalsIgnoreCase("yes")) {
									HtmlReporter.Log("Check_Uncheck_CheckboxesInATable", "Checkbox is checked for Row "+i+" and Column" + strColumnName , "pass");
								}
							}
							else
							{
								if (detailedReport.equalsIgnoreCase ("yes")) {
									HtmlReporter.Log("CheckuncheckCheckboxesInATable", "Checkbox is checked for Row rI+j+rI arid Column "+ strColumnName , "Pass");
								}
							}
						}
						else{
							if(els.isSelected())
							{
								els.click() ;
								if(detailedReport.equalsIgnoreCase("yes")){
									HtmlReporter.Log("CheckUnchecJcCheckboxIriATable", "Checkbox is Unchecked for Row r"+i+"and Column "+ strColumnName , "Pass");
								}
							}
							else
							{
								if(detailedReport.equalsIgnoreCase("yes")) {
									HtmlReporter.Log("CheckUnchecJcCheckboxIriATable", "Checkbox is Unchecked for Row r"+i+"and Column "+ strColumnName , "Pass");
								}
							}
						}
					}
					else
					{
						HtmlReporter.Log("CheckUncheckChec)cboxe-Jable", "Row "+i+ " and Column "+strColumnName +" is not a checkbox" , "Fail") ;
					}
				}
			}
			else
			{
				if (EndRow < 0 || EndRow >= allrows.size()){
					Log.info("WebTable has '" +allrows.size() + "' rows, whereas end RowIndex is " + EndRow );
					HtmlReporter.Log("CheckuncheckCheckboxesInATable", "WebTable has '" +allrows.size() + "' rows, whereas end RowIndex is " + EndRow , "Fail");
					return ;
				}
				for (int i	=StartRow;i<=EndRow;i++)
				{
					WebElement els=allrows.get(i).findElements(By.tagName("td")).get(colno).findElements(By.tagName("input")).get(0);
					if(els.getAttribute("type").equalsIgnoreCase("checkbox")){
						if(strCondition.equalsIgnoreCase("Check"))
						{
							if(!els.isSelected())
							{
								els.click();
								if(detailedReport.equalsIgnoreCase("yes")) {
									HtmlReporter.Log("CheckuncheckcheckboxesInATable", "checkbox is checked for Row "+i+" and Column "+strColumnName , "Pass");
								}
							}
							else
							{
								if(detailedReport.equalsIgnoreCase("yes")) {
									HtmlReporter.Log("CheckUncheckCheckhoxe3InATable", "Checkbox is checked for Row "+i+" and Column "+strColumnName , "Pass");
								}

							}
						}
						else
						{
							if(els.isSelected())
							{
								els.click();
								if(detailedReport.equalsIgnoreCase("yes")){
									HtmlReporter.Log("CheckUncheckCheckboxesInATable", "Checkbox is Unchecked for Row "+i+" and Column "+ strColumnName , "Pass");
								}
							}
							else
							{
								if(detailedReport.equalsIgnoreCase("yes")){
									HtmlReporter.Log("CheckUncheckCheckboxesInATable", "Checkbox is Unchecked for Row "+i+" and Column "+ strColumnName , "Pass");
								}
							}
						}
					}
					else
					{
						HtmlReporter.Log("CheckUnchecktheckboxesInATable", "Row '"+i+ " and Column "+strColumnName +" is not a checkbox" , "Fail");
					}
				}
			}
		}
		catch (NoSuchElementException e){
			Log.error("NoSuchElementException occured "+ e.getMessage());
			Log.info("Failed in Check Uncheck CheckboxesInATable--- " + e.getMessage());
			HtmlReporter.Log("Check_Uncheck_CheckboxesInATable", "Failed to check Checkbox in a Table","Fail");
			Assert.assertTrue("Failed to check Checkbox in a Table", false);
		}
	}






	/**
	 * Function: VerifyCheckuncheckCheckboxesInAlable
	 * Description: Verify Check Uncheck checkboxes in a table
	 * @author Dhilip
	 * @version 1
	 * @category Web Object Related
	 * @param WebElement Webtable,String strColumnName,int StartRow,int EndRow,String strCondition,boolean AllRow
	 * @return void
	 * @throws IOException
	 * */
	public void VerifyCheck_Uncheck_CheckboxesInATable(WebElement Webtable,String strColumnName,int StartRow,int EndRow,String strCondition, boolean AllRow) throws IOException
	{
		List<WebElement> allrows = null;
		WebElement table = null;
		try
		{
			List<WebElement> tbodies= Webtable.findElements(By.tagName("tbody"));
			if (tbodies != null){
				table = tbodies.get(0);
			}else{
				table = Webtable;
			}
			allrows=table.findElements(By.tagName("tr"));
			Log.info("Totla Rows: " + allrows.size());

			if (allrows.size() == 0){
				Log.info("No Rows found in the Webtable");
				HtmlReporter.Log("VerifyCheckUncheckCheckboxesInATable", "No Rows found in the Webtable" , "Fail");
				Assert.assertTrue("No Rows available in Table to verify chec]cboxes ", false);
				return ;
			}

			WebElement row = allrows.get(0);
			int colno=0;
			List<WebElement> Columns_row = row.findElements(By.tagName("td"));
			int columns_count = Columns_row.size();
			System.out.println("Total Columns: " + columns_count +" "+allrows.size());

			for (int column = 0; column < columns_count; column++) {
				String celtext = Columns_row.get(column).getText();
				System.out.println(celtext);
				if(celtext.equalsIgnoreCase(strColumnName))
				{
					System.out.println(strColumnName);
					colno=column;
					break;
				}
			}

			if (AllRow==true)
			{
				for (int i =1;i<=allrows.size()-2;i++)
				{
					WebElement els=allrows.get(1).findElements(By.tagName("td")).get(colno).findElements(By.tagName("input")).get(0);
					if( els.getAttribute("type").equalsIgnoreCase("checkbox")) {
						if(strCondition.equalsIgnoreCase("Check")){
							if(els.isSelected())
							{
								HtmlReporter.Log("rzf1Jzez:* ", ""+1+" ari liZ "+ strColumnName ,"Pass");
							}
							else
							{
								HtmlReporter.Log("VerifyCheckUncheckCheckboxesInATable", "Checkbox is unchecked for Row "+i+" and Column "+ strColumnNante , "Fail") ;
							}
						}
						else
							if(!els.isSelected())
							{
								HtmlReporter.Log("VerifyCheckUncheckCheckboxesInAlable", "Checkbox is Unchecked for Row "+i+" and Column "+ strColumnName , "Pass");
							}
							else
							{
								HtmlReporter.Log("VerifyCheckUncheckCheckboxesInATable", "Checkbox is checked for Row "+i+" and Column "+ strColumnName , "Fail") ;
							}
					}
					else
					{
						HtmlReporter.Log("VerifyCbeckuncheckCheckboxesInATable", "Row "+i+ " and Column "+strColumnName +" is not a chec]cbox", "Fail") ;
					}
				}
			}
			else
			{
				if (EndRow < 0 || EndRow >= allrows.size()){
					Log.info("webTable has '" +allrows.size() + "' rows, whereas end RowIndex is " + EndRow );
					HtmlReporter.Log("VerifyCheckUncheckCbeckboxesInATable", "WebTable has '" +allrows.size() + "' rows, whereas end RowIndex is " + EndRow , "Fail");
					return ;
				}

				for (int i=StartRow;i<=EndRow;i++)
				{
					WebElement els=allrows.get(1).findElements(By.tagName("td")).get(colno).findElements(By.tagName("input")).get(0);
					if(els.getAttribute("type").equalsIgnoreCase("checkbox")){
						if(strCondition.equalsIgnoreCase("Check"))
						{
							if(els.isSelected())
							{
								HtmlReporter.Log("VerifyCheckunchec]ctheckboxesInATable", "Checirbox is checked for Row "+i+" and Column "+ strColumnName , "Pass");
							}
							else
							{
								HtmlReporter.Log("VerifyCheckUncheckCheckboxesInATable", "Checkbox is unchecked for Row "+i+" and Column "+ strColumnName , "Fail");
							}
						}
						else
						{
							if(!els.isSelected())
							{
								HtmlReporter.Log("VerifyCheckUncheckCheckboxesInATable", "Checkbox is Unchecked for Row "+i+" and Column "+ strColumnName , "Pass");
							}
							else
							{
								HtmlReporter.Log("VerifyCheckUncheckCheckboxesInATable", "checkbox is checked for Row "+i+" and Column "+ strColumnName , "Fail");
							}
						}
					}
					else
					{
						HtmlReporter.Log("VerifyCheckuncheckChecktoxesInATable", "Row "+i+ " and Column "+strColumnName +" is not a checkbox", "Fail");
					}
				}

			}

		}
		catch(NoSuchElementException ex)
		{
			Log.error("NoSuchElementException occured "+ ex.getMessage());
			HtmlReporter.Log("VerifyCheckuncheckCheckboxesInATable", "Failed to Verify Checkbox in a Table", "Fail");
			Assert.assertTrue("Failed to Verify Checkbox in a Table", false);
		}
	}

	/**
	 * Function : scroll
	 * Description: Used to scroll up/down based on input given
	 * @author F579596 (Sandeep Kandula)
	 * @category Browser Related
	 * @paran direction(up or down)
	 * @return none
	 */
	public void scroll(String direction){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		switch(direction.toLowerCase()){
		case "up":
			js.executeScript("window.scrollBy(0,250)", "");
			break;
		case "down":
			js.executeScript("window.scrollBy(0,—250)","");
			break;
		}
	}
}

