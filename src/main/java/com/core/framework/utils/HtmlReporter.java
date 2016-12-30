package com.core.framework.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class HtmlReporter extends PublicVariables{
	public static int iStepCounter = 0;
	public static String strNewHTMLLocation;
	public static String fileName;
	public static String SSfileName;
	public static String SfileName;
	public static String sBCDescription = "***";
	static Hashtable<String, String> RunTime_hash = new Hashtable<>();
	public static Boolean bResultFlag = true;
	// public static WebDriver driver = null;
	public static String getHTMLLocation() throws IOException {
		if (strNewHTMLLocation == null) {
			CreateReportFolder();
			return strNewHTMLLocation;
		}else{
			return strNewHTMLLocation;
		}
	}

	/**
	 w <hl>CreateReportFolder</hl> The CreateReportFolder program creates the
	 * folder for a Module
	 *
	 * @author Dhilip
	 * @version 1.0
	 * @category Generic
	 * @param
	 *
	 */
	public static String CreateReportFolder() throws IOException {
		String strHTMLLocation = html_ReportPath; // System.getProperty("user.dir")
		// +
		// "\\reports\\mb\\servicing\\solonon\\htal\\";
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		String sDateStart = dateFormat.format(date);

		new File(strHTMLLocation + "\\" + sDateStart + "\\screenshots").mkdirs();
		strNewHTMLLocation = strHTMLLocation + sDateStart;
		// strNewflTMLLocation = strHTMLLocation +
		// appPublicVariables . testSuiteName+ "+sDateStart; //strHTMLLocation +
		// "\\" + sDateStart;
		return strNewHTMLLocation;
	}

	/**
	 * Capture screenshot using robot
	 * @author N642052 (baba)
	 * @category Generic
	 * @throws IOException
	 * @Description Capture screenshot of entire Desktop and store in classpath using Robot
	 * */
	@SuppressWarnings ("unused")
	public static void captureScreen() throws IOException{
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			String sDateStart = dateFormat.format(date);

			Robot robot = new Robot();
			String format = "png";
			String fileName = strNewHTMLLocation + "\\screenshots\\"+ sDateStart + ".png";

			/*Capture screensnct with Rubor entire desktop screen*/
			/*Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkitg.getScreenSizee);
			 *Bufferedlmage screenFulllmage = robot.createScreenCapture (screenRect);
			 *ImagelO.write(screenFulllmage, format, new File (fileName)); */
			try{
				File scrFile=((TakesScreenshot)driver) .getScreenshotAs(OutputType. FILE);
				FileUtils.copyFile(scrFile, new File(fileName));
			}catch(NullPointerException ne) {
				System.err.println(ne);
				Log.info("Unable to capture screenshot of Driver due to null pointer ó exception is : "+ne);
			}

			// FileUtils.copyFile(scrFile, new File(strNewHTMLLocation + "\\screenshots\\"+ sDateStart + ".png"));
			SfileName = sDateStart + ".png";
			currentScreenShot = fileName.toString();
			// System.out.println("A full screenshot saved!");
		}catch(AWTException ex){
			System. err.println (ex);
			Log.info("AWTException occured" + ex.getMessage());
		}
	}

	/**
	 * <hl>CreateScenarioHTMLRepcrt</hl> The CreateScenarioHTMLRepozlt program
	 * creates the HTML file for a Test
	 *
	 * @authcr Ohilip
	 * @version 1.0
	 * @category Generic
	 * @param String
	 * sTestCaseName
	 */
	// public static appPublicVariables asx = new appPublicVariablesfl;

	public static void CreateScenarioHTMLReport(String sTestCaseName) throws IOException {
		// driver = DriverFactory.driver;
		iStepCounter = 0;
		stepCount=0;
		RunTime_hash.put("htmlReport", "BC");
		fileName = strNewHTMLLocation + "\\" + sTestCaseName + ".html";
		uploadHtmlReportPath = fileName.toString();
		try {
			File tcHTMLReport = new File(fileName);
			if (!tcHTMLReport.exists()) {
				tcHTMLReport.createNewFile();
			}
			// The name of the file to open.
		} catch (NoSuchFileException e) {
			System.out.println(e.getMessage());
			Log.info("NoSuchFi1eEceptior c.cured" + e.getMessage());
		}
		try{
			if(applicationName==null){
				applicationName = "No Application Name given in appPublicVariables.java in maven project";
			}
			System.out.println("Application Name : "+ PublicVariables.applicationName);
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("<HTML><HEAD>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<title> " + PublicVariables.applicationName+ " Automation Report </title>");
			bufferedWriter.write (System.lineSeparator());
			bufferedWriter.write("</HEAD><BODY>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<P><B><U><CENTER><FONT face=Verdana color=#0033CC size=4>"+ PublicVariables.applicationName+ "</FONT></CENTER></U></B></P>") ;
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<TABLE height=10 width='lOO%' borderColorLight=#008080 border=2>&nbsp");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<TR>");
			bufferedWriter.write (System.lineSeparator());
			bufferedWriter.write("<TR>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<TD vAlign=Left align=Left width='3%' bgColor=#e1e1e1 height=20>");
			bufferedWriter.write (System.lineSeparator());
			bufferedWriter.write("<FONT face=Verdana color=#0033CC size=2><B>Execution Date : ");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("</TR>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<B></FONT><TD>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter .write ("</Table>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<br>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<TABLE height=10 width='l00%' borderColorLight=#008080 border=2>&nbsp");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.write("<TD vAlign=middle align=middle width='5%' bgColor=#e1e1e1 height=24><FONT face=Verdana color=#0033CC size=3><B>TC Step No </B></FONT></TD>");
			bufferedWriter.write("<TD vAlign=middle align=middle width='l5%' bgcolor=#e1e1e1 height=24><FONT face=Verdana color=#0033CC size=3><B>Function Name </B></FONT></TD>");
			bufferedWriter.write("<TD vAlign=middle align=middle width='75%' bgColor=#e1e1e1 height=24><FONT face=Verdana color=#0033CC size=3><B>Description </B></FONT></TD>");
			bufferedWriter.write("<TD vAlign=middle align=middle width='lO%' bgColor=#e1e1e1 height=24><FONT face=Verdana color=#0033CC size=3><B>Status </B></Font></TD>");
			bufferedWriter.write(System.lineSeparator());
			bufferedWriter.close();
		} catch(IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			Log.info("IOException occured" + ex.getMessage());
		}
	}

	/**
	 * <hl>takeScreenshots</hl> The takeScreenshots program take Screenshot
	 *
	 * @author Dhilip
	 * @version 1.0
	 * @category Generic
	 * @param WebDriver
	 * driver,String sfileName
	 * @throws Exception
	 */
	public static String takeScreenshots(WebDriver driver, String sfileName) throws Exception {
		if (driver == null) {
			return null;
		}
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			String sDateStart = dateFormat.format(date);

			// throwing Error So commented it ó Sep 16 2015 ó Need to Debugó
			// Ohilip
			File scrFile = ((TakesScreenshot) driver) .getScreenshotAs(OutputType.FILE);
			// System.out.println("screenshot taken ª");
			FileUtils.copyFile(scrFile, new File(sfileName + "\\screenshots\\"+ sDateStart + ".png"));
			SSfileName = sDateStart + ".png";
			return SSfileName;
		} catch (FileNotFoundException e) {
			Log.error("Class tftil3 I Method takeScreen3hot I Exception occured while capturing ScreenShot : "+ e.getMessage());
			throw new Exception();
		}
		// return 3fileName;
	}

	//" <hl>EndBC</hl> The EndBC program implements to print HTML Report
	/**
	 * @author Dhilip
	 * @version 1.0
	 * @category Generic
	 * @parani String
	 * strStatus
	 * @throws IOException
	 *
	 */

	public static void EndBC(String strBusComName, String strStatus) throws IOException {
		if(testStatus==null || testStatus.equalsIgnoreCase("warning") || testStatus.equalsIgnoreCase("pass") || testStatus.equalsIgnoreCase(
				"passed") || testStatus.equalsIgnoreCase("true")){
			if(strStatus.toLowerCase() .contains("pass") || strStatus.toLowerCase() .contains ("warning")) {
				testStatus = "Passed";
			}else if(strStatus.toLowerCase() .contains("fail")){
				testStatus = "Failed";
			}
		}else if(strStatus.toLowerCase() .contains("fail")){
			testStatus = "Failed";
		}

		iStepCounter = iStepCounter + 1;
		String ColorVal;

		// HtmlReporter.ta)ceScreenshots (driver, strNewHlMLLccation);
		if(screenShotsOnEveryStep.equalsIgnoreCase("yes") ) {
			captureScreen();
		}else if(strStatus.toLowerCase() .contains("fail")){
			captureScreen();
		}

		// takeScreehcts (driver, strNewHTMtLocation);
		System.out.println(strBusComName + "<<<" + strStatus);
		if (strStatus.toLowerCase() .contains("pass")) {
			ColorVal = "009966";
			bResultFlag = true;
		} else {
			ColorVal = "FF3300";
			bResultFlag = false;
			// Assert. fail (strDescription);
		}

		try{
			// Assume default encoding.
			FileWriter filewriter = new FileWriter(fileName, true);

			// Always wrap FileWriter in bufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(filewriter) ;

			// Pxzrt Step Oo//rr.
			bufferedWriter.write("<TR><TD align=center width='2%' bgColor=#ffffe1 height=19>");

			bufferedWriter.write("<FONT face=Verdana size=2><b> "+ iStepCounter + " </b></Font></TD>");
			bufferedWriter.write(System.lineSeparator());
			// Print Function/Business Component Column
			bufferedWriter.write("<TD align=center width='2%' bgColor*ffffel height=19>");
			bufferedWriter.write("<FONT face=Verdana size=2><b> "+ strBusComName + " </b></Font></TD>");
			bufferedWriter.write(System.lineSeparator());
			// Print Description Column
			bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19> <FONT face=Verdana size=2><b> "+ sBCDescription + "</b></Font></TD>");
			bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19>");
			bufferedWriter.write(System.lineSeparator());
			// Print Status & Attach screenshot
			if(currentScreenShot!=null) {
				bufferedWriter.write("<a href = .\\screenshots\\" + SfileName + " > <FONT face=Verdana color= " + ColorVal + " size=2><b> " +strStatus + " </b></Font></a></TD></TR>");
				currentScreenShot = null;
			}else{
				bufferedWriter.write(" <FONT face=Verdana color= " + ColorVal + " size=2><b> " + strStatus + " </b></Font></TD></TR>");
			}
			bufferedWriter.write(System.lineSeparator());
			// Always close files.
			bufferedWriter.close();
			RunTime_hash.put("htmlReport", "End BC");
			sBCDescription = "";
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + ".");
			Log.error("IOException occured"+ ex.getMessage());

			RunTime_hash.put("htmlReport", "End BC");
			sBCDescription ="";
		}
	}

	public static void EndBC(String strBusComName, String strStartDesc, String strEndDesc, String strStatus) throws Exception {
		if(testStatus==null || testStatus.equalsIgnoreCase("warning") || testStatus.equalsIgnoreCase("pass") || testStatus.equalsIgnoreCase("passed") || testStatus .equalsIgnoreCase ("true")) {
			if(strStatus.toLowerCase().contains("pass") || strStatus.toLowerCase().contains("warning")) {
				testStatus = "Passed";
			}else if(strStatus.toLowerCase() .contains("fail")){
				testStatus = "Failed";
			}
		}else if(strStatus.toLowerCase().contains("fail")){
			testStatus = "Failed";
		}
		iStepCounter = iStepCounter + 1;
		String ColorVal;
		// take5cree&cts(driver, strNewHTMLLocation;
		if(screenShotsOnEveryStep.equalsIgnoreCase("yes") ) {
			captureScreen();
		}else if(strStatus.toLowerCase().contains("fail")){
			captureScreen();
		}

		if (strStatus.toLowerCase().contains("pass")) {
			ColorVal = "009966";
			bResultFlag = true;
		} else {
			ColorVal = "FF3300";
			bResultFlag = false;
			// Assert.fail (str–escription);
		}

		try{
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName, true);

			// Always wrap FileWriter in bufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Print Step Column
			bufferedWriter.write("<TR><TD align=center width='2%' bgColor=#ffffel height=19>");
			bufferedWriter.write("<FONT face=Verdana size=2><b> "+ iStepCounter + "</b></Font></TD>");
			bufferedWriter.write(System.lineSeparator());

			// Print Function/Business Component Column
			bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19>");
			bufferedWriter.write("<FONT face=Verdana size=2><b> "+ strBusComName + " </b></Font></TD>");
			bufferedWriter.write(System.lineSeparator());

			// Print Description Column
			bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19> <FONT face=Verdana size=2><b> "+ strStartDesc+ sBCDescription+ strEndDesc+ " </b></Font></TD>");
			bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19>");
			bufferedWriter.write(System.lineSeparator());

			// Print Status & Attach screenshot
			if(currentScreenShot!=null) {
				bufferedWriter.write("<a bref = .\\screenshots\\" + SfileName + " > <FONT face=Verdana color= " + ColorVal + " size=2><b> " + strStatus + "</b></Font></a></TD></TR>");
				currentScreenShot = null;
			}else{
				bufferedWriter.write(" <FONT face=Verdana color " + ColorVal + " size=2><b> " + strStatus + " </b></Font></TD></TR>");
			}
			bufferedWriter.write(System.lineSeparator());
			// Always close files.
			bufferedWriter.close();
			RunTime_hash.put("htmlReport", "End BC");
			sBCDescription = "";
		}catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			Log.error("IOException occured "+ ex.getMessage());
			RunTime_hash.put("htmlReport", "End BC");
			sBCDescription = "";
		}
	}

	/**
	 * <hl>StartBC</hl> The StartBC program implements to invoke HTML Report for
	 * Business Components mode
	 *
	 * @author Dhilip
	 * @version 1.0
	 * @category Generic
	 * @param
	 */

	public static void StartBC() {
		RunTime_hash.put("htmlReport", "Start BC");
		sBCDescription = "";
	}

	/*
	 * <hl>Log</hl> The Log program implements to Print HTML Report foxj Business
	 * Components node & Individual Generic / Customized
	 *
	 * @author Dhilip
	 * @version 1.0
	 * @category Generic
	 * @parazn String sFunctionName,String strDescription , String strStatus
	 * @throws IoException
	 */

	public static void Log(String sFunctionName, String strDescription, String strStatus) throws IOException {
		stepCount = stepCount+1;
		if(testStatus==null || testStatus.equalsIgnoreCase("warning") || testStatus.equalsIgnoreCase("pass") || testStatus.equalsIgnoreCase("passed") || testStatus.equalsIgnoreCase("true")) {
			if(strStatus.toLowerCase().contains("pass") || strStatus.toLowerCase().contains("warning")) {
				testStatus = "Passed";
			}else if(strStatus.toLowerCase().contains("fail")) {
				testStatus = "Failed";
			}
		}else if(strStatus.toLowerCase().contains("fail")) {
			testStatus = "Failed";
		}

		String ColorVal, strHtmlDesc;
		// takeScreenshots(driver, strNewHtMLLocation);
		if(screenShotsOnEveryStep.equalsIgnoreCase("yes")) {
			captureScreen();
		}else if(strStatus.toLowerCase().contains("fail")){
			captureScreen();
		}

		if (strStatus.toLowerCase().contains("pass")) {
			ColorVal = "009966";
			testSteps.put("Status"+stepCount, "Passed");
			if(currentScreenShot!=null) {
				screenshotSteps.put("Screenshot"+stepCount, currentScreenShot);
			}

			// bResultFlag = true;
		} else if(strStatus.toLowerCase().contains("fail")){
			ColorVal = "FF3300";
			testSteps.put("Status"+stepCount, "Failed");
			if(currentScreenShot!=null) {
				screenshotSteps.put("Screenshot"+stepCount, currentScreenShot);
			}
			HtmlReporter.bResultFlag = false;
			// Assert . fail (strOescription);
		} else if(strStatus.toLowerCase() .contains("warning")) {
			ColorVal = "FFA5OO";
			testSteps.put("Status"+stepCount, "Passed");

			if(currentScreenShot!=null) {
				screenshotSteps.put("Screenshot"+stepCount, currentScreenShot);
			}
			HtmlReporter.bResultFlag = true;
		} else{
			ColorVal = "FFFFOO";
		}

		testSteps.put("Description"+stepCount, strDescription);
		try {
			if (RunTime_hash.get("htmlReport") == "End BC") {
				// Assume default encoding.
				FileWriter filewriter = new FileWriter(fileName, true);
				//Always wrap Filewriter in bufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
				bufferedWriter.write("<TR><TD align=center width='2%' bgColor=#ffffel height=19>");
				bufferedWriter.write("<FONT face=Verdana size=2><b> " + iStepCounter + " </b></Font></TD>");
				bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19>");
				bufferedWriter.write("<FONT face=Verdana size=2><b> " + sFunctionName + " </b></Font></T–>");
				bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19>");
				strHtmlDesc = "<FONT face=Verdana size=2 color= " + ColorVal+ "> <b> " + strDescription + " </b></font>";
				bufferedWriter.write(strHtmlDesc);
				bufferedWriter.write("<TD align=center width='2%' bgColor=#ffffel height=19>");

				if(currentScreenShot!=null) {
					bufferedWriter.write("<a bref = .\\screenshots\\" + SfileName + " > <FONT face=Verdana color " + ColorVal + " size=2><b> " + strStatus + " </b></Font></a></TD></TR>");
				}else{
					bufferedWriter.write(" <FONT face=Verdana color= "+ ColorVal + " size=2><b> " + strStatus + " </b></Font></TD></TR>");
				}
				// bufferedWriter.write("<a bref = .\\screenshots\\" + SfileName + "ITGETInewI><img src=.\\screenshots\V'+Sfileflame+" 'border'no' height' 80' width'l20' alt'Click to maximize'> </a></TD></TR>");
				// Always close files.
				bufferedWriter.close();
			}else {
				if (!(strDescription == "")) {
					strHtmlDesc = "<FONT face=Verdana size=2 color= " + ColorVal + "> <b> " + strDescription + " </b></Font>";
					sBCDescription = sBCDescription + "<br> <br>" + strHtmlDesc;
				}
			}
		}catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			Log.info("Exception occured" + ex.getMessage());
		}
	}

	//<hl>EndTC</hl> The EndTC progran implements to Close the !TML Report for
	/* a Test
	 *
	 * @author Dhilip
	 * @version 1.0
	 * @category Generic
	 * @param
	 *
	 */
	public static void EndTC() {
		try {
			// Assume default encoding.
			FileWriter filewriter = new FileWriter(fileName, true);

			// Always wrap FileWriter in bufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(filewriter);

			bufferedWriter.write("</Body>");
			bufferedWriter.write("</HTML>");
			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "");
			Log.info("IOException occured" + ex.getMessage());
		}
	}
}
