package com.core.framework.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.framework.web.GUIComponents;
import com.thoughtworks.selenium.Selenium;

import autoitx4java.AutoItX;

@SuppressWarnings({"unused", "deprecation"})
public class PublicVariables {
	/**
	 * File system paths related global variables
	 * */

	public static String Browser = "lE";
	public static String Environment = "QA";
	public static String BrowserPath = System.getProperty("user.dir")+ "//IEDriverServer—2 .45. 0.exe";
	public static String URL;
	public static String Path_TestData = System.getProperty("user.dir")+ "\\src\\test\\resources\\testData\\";
	public static String html_ReportPath = System.getProperty("user.dir")+	"\\reports\\html\\";

	/*
	 * Datatable accessing and assignment global variables
	 * */

	public static String xlDataFileName;
	public static String xlDataFilePath;
	public static String xlSheetName;
	public static DataTable dataTable;
	public static Map<String, String> tcRowData = new HashMap<String, String>();

	/** 
	 * HTML Reporter global variable
	 * */
	public static HtmlReporter reporter;

	/**
	 * Selenium webdriver global objects
	 * */

	public static WebDriver driver=null;
	public static WebDriver childDriver = null;
	public static Selenium selenium = null;
	public static AutoItX autoIT = null;
	public static String htmlDriver=null;

	/**
	 * Synchronization Timeout objects
	 * */
	public static WebDriverWait wait;
	public static WebDriverWait objWait;
	public static WebDriverWait pageWait;

	/**
	 * GUI components object — Global
	 * */
	public static GUIComponents obj = new GUIComponents(driver);

	/**
	 * Application related global variables
	 * */

	public static String applicationName;
	public static int pageTimeOut;
	public static int objTimeOut;
	public static String currentTestName;

	/**
	 * ALM related global variables
	 */

	public static String almSync;
	public static String almURL;
	public static String almDomain;
	public static String almProject;
	public static String almUser;
	public static String almEncryptedPassword;
	public static String almTestLabFolder;
	public static String almTestSetName;
	public static HashMap<String, String> almInputs = new HashMap<String, String>();

	/**
	 * Test Results — Variables
	 * */
	public static String testStatus=null;
	public static String uploadHtmlReportPath;
	public static HashMap<String, String> testSteps = new HashMap<String, String>();
	public static HashMap<String, String> screenshotSteps = new HashMap<String, String>();
	public static int stepCount=0;
	public static String currentScreenShot=null;
	public static String screenShotsOnEveryStep="yes";
	public static String detailedReport="yes";

}
