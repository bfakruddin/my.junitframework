package com.core.framework.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

@SuppressWarnings({"unused","deprecation"})
public class DriverFactory extends PublicVariables{
	/**r
	 * <hl>DriverFactory</hl> The DriverFactory program implements initializing
	 * Driver Object, read the BrowserType and URL from constants, navigate to URL
	 * and returns the WebDriver object
	 *
	 * @author Kiran
	 * @version 1.0
	 */

	//public static WebDriver driver = null;
	// This method reads from Constant file & opens the browser
	public static WebDriver initilazeDriver(String Browser, String URL,boolean... nativeEventsFlag) throws InterruptedException, IOException {
		if(pageTimeOut==0) {
			pageTimeOut = 30;
			System.out.println("No Page time out defined in application project — So, framework default timeout assigned for page time out: 30");
		}
		if(objTimeOut==0) {
			objTimeOut = 10;
			System.out.println("No object time out defined in application project — So, framework default timeout assigned for object time out:10");
		}

		if(currentTestName==null){
			currentTestName = "No Naine give";
			System.out.println("No currenttestName defined in application project — So, framework default currentTestName assigned as No Name given");
		}
		String browserType = Browser;
		Log.info("Launching " + browserType + " Browser");

		// FireFox Browser or lE Driver
		try{
			if (browserType.equals("Mozilla") | browserType.contains("firefox") | browserType.equalsIgnoreCase ("ff") | browserType.equalsIgnoreCase("") | browserType==null) {
				driver = new FirefoxDriver();
				Thread.sleep(3000);
				driver.get(URL);
				Log.info("Mozilla browser started...");
				HtmlReporter.Log("Launch Browser", browserType + ": browser started...", "PASS");
			} else if(browserType.equalsIgnoreCase("IE") | browserType.equalsIgnoreCase("InternetExplorer")) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver . INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				capabilities.setCapability("ignorezoomSetting", true);
				capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
				capabilities.setBrowserName("internet explorer");

				if(nativeEventsFlag.length>0) {
					capabilities.setCapability("nativeEvents", nativeEventsFlag[0]);
				}else{
					capabilities.setCapability("nativeEvents", false); //will suppress never notify settings of user control account settings — But it’s not working
				}
				capabilities.setCapability("enablePersistentflover", false);
				capabilities.setCapability("requirewindowFocus", true);
				// capabilities.setVersion(”lO”);
				// Systen.out.println(capabilities.internetExplcrer () .getVersion);
				// capabilities.internetExplorer() .setVersion(”10”);
				// capabilities.setCapability( (InternetExplorerÐriver., value);
				// capabilities.setCapability(”EnableNativeFvents “, false);
				capabilities. setCapability("initialBrowserurl", URL);

				File file = new File (PublicVariables.BrowserPath);
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				// capabilities.setcapability(”igncrePrctectedModeSettings”, true);
				driver = new InternetExplorerDriver(capabilities);
				// driver.manage().window().maximize();
				Log.info("IE browser started");
				HtmlReporter.Log("Launch Browser", browserType + ": browser started...", "PASS");
			} else if(browserType.equalsIgnoreCase("GridMozilla")){
				URL hubUrl = new URL("http://localhost:4444/wd/hub");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName("firefox");
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				capabilities.setCapability("initialBrowserUrl", URL);
				File file = new File(PublicVariables.BrowserPath);
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new RemoteWebDriver(hubUrl, capabilities);
				Log.info("mozilla browser started...");
				driver.get(URL);
			} else if(browserType.equalsIgnoreCase("GridIE")) {
				URL hubUrl = new URL("http://localhost:4444/wd/hub");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName("iexplore");
				capabilities.setVersion("ll");
				capabilities.setCapability("igncrePrctectedMcdeSettings", true);
				capabilities.setCapability("initialNrcwserUrl", URL);
				File file = new File(PublicVariables.BrowserPath);
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new RemoteWebDriver(hubUrl, capabilities);
				Log.info("Mozilla browser started...");
				driver.get(URL);
			} else if (browserType.equals("html")) {
				System.out.println("Brcwser Type" + browserType);
				driver = new HtmlUnitDriver();
				driver.get(htmlDriver);
			} else {
				Log.info("Browser type unsupported for :" + browserType);
			}
			driver.manage() .window() .maximize();
			int implicitWaitTime = (15);
			driver.manage().timeouts().implicitlyWait(pageTimeOut,TimeUnit.SECONDS);
		} catch (IOException e) {
			Log.info("Not able to open the Browser ——— " + e.getMessage());
			HtmlReporter.Log("Open browser Driver", "Not able to open the Browser ---- "+ e.getMessage(), "fail");
			Assert.assertTrue("Not able to open the Browser ——- "+ e.getMessage(), false);
		}

		selenium = new WebDriverBackedSelenium(driver, URL);
		// wait = new WebDriverWait(driver, apppublicVariables.driverTimeOut);
		// objWait = new WebDriverWait(driver, appPublicVariables.objTimeout);
		// pageWait = new WebDriverWait(driver, appPublicVariables.pageTimeout);
		return driver;
	}


	/** @author N642052
	 *	@return String
	 * 	@param element
	 * Function : getPageFactoryLocat.ox5uxiny
	 * Description: Returns the
	 * identifier string we used in page factory Developed By : Baba Fakruddin D
	 * (N642052) Developed On: 5/6/2016 Inputs: WebElement (pagefacotry element)
	 * */
	public static String getPageFactoryLocatorString(WebElement element) {
		driver.manage() .timeouts() .implicitlyWait(1, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 1);
		final String op = element.toString();
		if (op.contains ("Proxy element for: org.openqa.seleniun.support.pagefactory.DefaultElementLocator")) {
			return null;
		}

		String[] vals = element.toString().split("->");
		String lastVal = vals[1].trim().split(": ")[1].toString().trim(); // vals[vals.length—1j;
		driver.manage() .timeouts() .implicitlyWait(PublicVariables.pageTimeOut, TimeUnit.SECONDS);
		return lastVal.trim().substring(0, lastVal.trim().length() - 1).trim().toString(); 
	}

	/**
	* @author N642052
	* @param element, timeOut
	* @return String
	* Function : getPageFactoryLocatorString
	* Description: Returns the
	* identifier string we used in page factory
	* Developed On: 5/6/2016 Inputs: WebElement (pagefacotry element)
	* */
	public static String getPageFactoryLocatorString(WebElement element, int timeOut) {
		driver.manage() .timeouts() .implicitlyWait(1, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, timeOut);
		final String op = element.toString();
		if (op.contains ("Proxy element for: org.openqa.seleniuir..support.pagefactory.DefaultElementLocator")) {
			return null;
		}
		String[] vals = element.toString() .split("—>");
		String lastVal = vals[1].trim().split(": ")[1].toString().trim(); // vals[vals.length—1];
		driver.manage() .timeouts() .implicitlyWait(PublicVariables.pageTimeOut, TimeUnit.SECONDS);
		return lastVal.trim().substring(0, lastVal.trim().length() - 1).trim().toString();
	}

	/**
	* @author N642052
	* @param element
	* @return String
	* Function : getPageFactoryLocatorString Description: Returns the
	* identifier type we used in page factory (id, name, xpath, css, etc.,)
	* Developed On: 5/17/2016
	* Inputs: WebElement (pagefacotry element) Outputs: String
	* */
	public static String getPageFactoryLocatorldentify3y(WebElement element) {
		String[] vals = element.toString().split(":");
		String identifier = vals[1].split("—>")[1].trim();

		return identifier;
	}
}

