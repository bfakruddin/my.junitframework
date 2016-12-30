package com.core.framework.utils;

/* @author N642052
 * Description utilities methods*/

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.imageio.ImageIO;
import java.text.ParseException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.os.WindowsUtils;

import autoitx4java.AutoItX;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;
import com.sun.jna.platform.WindowUtils;

@SuppressWarnings ("unused")
public class Utilities extends PublicVariables{
	/**
	 * Method: uploadFileToALMlestLabTest
	 * @author n642052 (Baba Fakruddin Dudekula)
	 * @category AtM Related
	 * @param almInputs(HashNap)
	 * @throws InterruptedException
	 * */
	@SuppressWarnings({ "deprecation", "unused" })
	public static void uploadFileToAtMTestLabTest(HashMap<String, String> alrnlnputs) throws InterruptedException{
		try{
			if (!almSync.equalsIgnoreCase("yes")){
				System.out.println("AlM synchronization is off by properties, (almSync — Yes/No}");
				return;
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			System.out.println("A1M synchronization property is not defined and assigned values, property must be (aimSync — Yes/No}");
			Log.error("NullPointerException occured "+ e.getMessage());
			return;
		}

		Utilities.initializeJacobs();
		String almURL = almInputs.get("ALMURL");
		String domain = almInputs.get("Domain");
		String project = almInputs.get("Project");
		String almUserName = almInputs.get("AlMUserName");

		String testFolder = almInputs.get("TestFolder");
		String testSetName = almInputs.get("TestSetName");
		String testName = almInputs.get("TestName");
		String uploadFilePath = almInputs.get("UploadFilePath");

		if(almURL==null||almURL.equalsIgnoreCase("")){
			System.out.println("URLs sent as null");
			return;
		}

		if(domain==null||domain.equalsIgnoreCase("")){
			System.out.println("Domain is sent as null");
			return;
		}

		if(project==null||project.equalsIgnoreCase("")){
			System.out.println("project is sent as null");
			return;
		}

		if(almUserName==null||almUserName.equalsIgnoreCase("")){
			System.out.println("almuserNane is sent as null");
			return;
		}

		if(almInputs.get("ALMEncryptedPassword")==null||almInputs.get("AtMEncryptedPassword") .equalsIgnoreCase("")){
			System.out.println("Password is sent as null");
			return;
		}

		String almEncryptedPassword = almInputs.get("ALMEncryptedPassword");

		if(almInputs.get("ALMEncryptedPassword").contains("=") ){
			byte[] decryptedPassword = DecryptPassword(almInputs.get("ALMEncryptedPassword"));
			almEncryptedPassword = new String(decryptedPassword);
		}else{
			almEncryptedPassword = almInputs.get("ALMEncryptedPassword").trim();
		}

		if(testFolder==null||testFolder.equalsIgnoreCase("")){
			System.out.println("testFolder is sent as null");
			return;
		}

		if(testSetName==null||testSetName.equalsIgnoreCase("")){
			System.out.println("Test Set Name is sent as null");
			return;
		}

		if(testName==null||testName.equalsIgnoreCase("")){
			System.out.println("Test Name is sent as null");
			return;
		}

		if(uploadFilePath==null||uploadFilePath.equalsIgnoreCase("")){
			System.out.println("tlpload File Path is sent as null");
			return;
		}

		ActiveXComponent almConnection=new ActiveXComponent("TDAPIOLE8O.TDConnection");
		Dispatch.call(almConnection, "InitConnectionEx", almURL);
		Dispatch.call(almConnection, "login", almUserName,almEncryptedPassword);
		Dispatch.call(almConnection, "connect",domain,project);

		/**By me on Test set factory*/
		Dispatch testSetTreeManager = Dispatch.get(almConnection, "TestSetTreeManager").toDispatch();
		Dispatch tsFolder = Dispatch.call(testSetTreeManager, "NodeByPath", testFolder).toDispatch();
		//		Dispatch subFolderNode = Dispatch.get(tsFolder, "NewList").toDispatch;
		Dispatch testSetNode = Dispatch.get(tsFolder, "TestSetFactory").toDispatch();
		Dispatch testSetList = Dispatch.call(testSetNode, "NewList", "").toDispatch();

		//		 Dispatch tsList = Dispatch.call(tsFolder, "FindTestSets", testSetName).toDispatch();

		Variant count = Dispatch.get(testSetList, "Count");
		// System.out.println(count);
		int toInt = count.toInt();
		// String[] testflanes = new String[toInt];
		boolean blnFlag = false;

		for (int testSetIndex = 1; testSetIndex <= toInt; testSetIndex++ ){
			Dispatch testSetItem = Dispatch.call(testSetList, "Item", new Variant(testSetIndex)).toDispatch();
			String testSetActName = Dispatch.get(testSetItem, "Name").toString();


			if (testSetActName.toString().equalsIgnoreCase(testSetName)){
				System.out.println("TestSet Name "+testSetActName);
				Dispatch testSetTestFactory = Dispatch.get(testSetItem, "TSTestFActory") .toDispatch();
				Dispatch testList = Dispatch.call(testSetTestFactory, "NewList", "").toDispatch();
				Variant testsCount = Dispatch.get(testList, "Count");
				// System. out.printìn(testsCount);
				int testsCountInt = testsCount.toInt();
				for(int testIndex=1; testIndex<=testsCountInt; testIndex++){
					Dispatch testItem = Dispatch.call(testList, "Item", new Variant(testIndex)).toDispatch();
					String testActName = Dispatch.get(testItem, "TestName").toString().replaceAll("\\W", "_"); //Regular expression to replace all special characters apart of [A—Za—zO—9] to ""
					String testActName1 = Dispatch.get(testItem, "TestName") .toString() .replaceAll("\\W", ""); //Regular expression to replace all special characters apart of [A—Za—zO—9_] to
					if(testActName.equalsIgnoreCase(testName) ||testActName1.equalsIgnoreCase(testName)){
						System.out.println("Test Name : "+testActName);
						try {
							Dispatch nowTest = testItem;
							Dispatch attachmentFactory = Dispatch.get(testItem, "Attachments").toDispatch();
							// Dispatch attachList = Dispatch.call(attachmentFactory, "NewList", "").toDispatch;
							/*Dispatch.put (nowTest, "Status", teststatus);
					Dispatch.call (nowTest, "Post");
					Thread. sleep (300) ; */
							//System. DBNull .Value
							Variant paramVal = new Variant();
							paramVal.putNull();
							Dispatch nowAttachnent = Dispatch.call(attachmentFactory, "AddItem", paramVal).toDispatch();

							Dispatch.put(nowAttachnent, "Description", Calendar.getInstance().getTime());
							Dispatch.put(nowAttachnent, "Filename", uploadFilePath);
							Dispatch.put(nowAttachnent, "Type", 1);
							Dispatch.call(nowAttachnent, "Post");

							/** Updating steps in this test */
							// Create run factory for this test
							Dispatch runF = Dispatch.get(testItem, "RunFactory").toDispatch();
							// Add a name to this runfactory test
							Dispatch runA = Dispatch.call(runF, "AddItem", "Automated").toDispatch();
							Dispatch runAA = Dispatch.call(runF, "NewList","") .toDispatch();

							Variant runCount = Dispatch.get(runAA, "Count");
							int runsCountInt = runCount.toInt();

							for(int run=1; run<=runsCountInt; run++){
								Dispatch runItem = Dispatch.call(runAA, "Item", new Variant(run)).toDispatch();
								Variant runName = Dispatch.get(runItem, "Name");

								// Create Stepfactory for this Run
								Dispatch oStep = Dispatch.get(runItem, "StepFactory").toDispatch();

								//Create steps of current test and update result
								// int stepsont = testSteps.size(); ¡I this will return size of hash map has both status and description values
								Dispatch oStepDetails = null;
								String runItemStatus="Passed";
								for(int i=1; i<=stepCount; i++){
									if(testSteps.get("Description"+i) !=null){
										// System.out.println("Step "+i+"  "+ testSteps.get("Description"+i) + " <——> "+testSteps.get("Status"+i));
										Dispatch.call(oStep, "AddItem", "Step "+i+" --> "+ testSteps.get("Description"+i));
										oStepDetails = Dispatch.call(oStep, "NewList", "").toDispatch();
										Dispatch curStep = Dispatch.call(oStepDetails, "Item", new Variant(i)).toDispatch();
										Variant stp = Dispatch.get(curStep, "Name");
										try{
											Dispatch.put(curStep, "Status", testSteps.get("Status"+i));
											//											System.out.println(curStep.toString());
											if(testSteps.get("Status"+i).equalsIgnoreCase("failed")){
												runItemStatus = "Failed";
											}
											Dispatch.call(curStep, "Post");


											if(screenShotsOnEveryStep.equalsIgnoreCase("yes")){
												/*Attachments to current executing step*/
												Dispatch stepAttachmentFactory = Dispatch.get(curStep, "Attachments").toDispatch();
												Variant stepParamVal = new Variant();
												stepParamVal.putNull ();
												Dispatch stepNowAttachment = Dispatch.call(stepAttachmentFactory, "AddItem", stepParamVal).toDispatch();
												Dispatch.put(stepNowAttachment, "Description", Calendar.getInstance().getTime());
												// System.out.println(screenshotSteps.get("Screenshot"+i));
												Dispatch.put(stepNowAttachment, "Filename", screenshotSteps.get("Screenshot"+i));
												Dispatch.put(stepNowAttachment, "Type", 1);
												Dispatch.call(stepNowAttachment, "Post");
											}else if(testSteps.get("Status"+i).toLowerCase().contains("fail")){
												/*Attachments to current executing step*/
												Dispatch stepAttachmentFactory = Dispatch.get(curStep, "Attachments").toDispatch();
												Variant stepParamVal = new Variant();
												stepParamVal.putNull();
												Dispatch stepNowAttachment = Dispatch.call(stepAttachmentFactory, "AddItem", stepParamVal).toDispatch();
												Dispatch.put(stepNowAttachment, "Description", Calendar.getInstance().getTime());
												// System.out.println(screenshotSteps.get("Screenshot"+i));
												Dispatch.put(stepNowAttachment, "Filename", screenshotSteps.get("Screenshot"+i));
												Dispatch.put(stepNowAttachment, "Type", 1);
												Dispatch.call(stepNowAttachment, "Post");
											}

										}catch(ComFailException e){
											Dispatch.put(nowTest, "Status", testStatus);
											Dispatch.call(nowTest, "Post");
											Thread.sleep(3000);
											System.out.println("COM Fail Exception occured, Exited without updates in ALM Status or FileName upload or Description or Type");
											Dispatch.call(almConnection, "Disconnect");
											Dispatch.call(almConnection, "Logout");
											Dispatch.call(almConnection, "ReleaseConnection");
											stepCount=0;
											testStatus = null;
											testSteps.clear();
											Log.error("ComffailException occured "+ e.getMessage());

											return;
										}
									}

								}

								Dispatch.put(runItem, "Status", runItemStatus);
								Dispatch.call(runItem, "Post");
								break;
							}

							blnFlag=true;
							break;
						}catch(ComFailException i){
							System.out.println("COM Fail Exception occured, Exited without updates in ALM Status or FileName upload or Description or Type —— step level");
							Dispatch.call(almConnection, "Disconnect");
							Dispatch.call(almConnection, "Logout");
							Dispatch.call(almConnection, "ReleaseConnection");
							System.out.println("Status Updates and Uploads are not done with ALM Test : "+testName + "Exception occured : "+i);
							stepCount=0;
							testStatus = null;
							testSteps.clear();
							Log.error("ComFailException occured "+ i.getMessage());
							return;
							// Assert.assertTrue(false);
							// System.exit(—l);
						}

					}
				}
				if(blnFlag) break;
			}
		}
		stepCount=0;
		testStatus = null;
		testSteps.clear();
		Dispatch.call(almConnection, "Disconnect");
		Dispatch.call(almConnection, "Logout");
		Dispatch.call(almConnection, "ReleaseConnection");
		System.out.println("Status Updates and Uploads are done with AtM Test : "+testName);
	}


	/* * Method: getTodayDate
	 * @author n642052 (Baba Fakruddin Dudekula)
	 * @category Date Related
	 * @param format as String
	 * */
	public String getTodayDate(String format){
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(format);
		System.out.println(ft.format(date));
		return ft.format(date);
	}


	/* Method: getRndNumber
	 * @author n642052 (Baba Fakruddin Dudelcula)
	 * @category Number Related
	 * @param len as mt
	 * */

	public int getRndNumber(int len) {
		Random random = new Random();
		int randomNumber = 0;
		boolean loop = true;
		while (loop) {
			randomNumber = random.nextInt();
			if (Integer.toString(randomNumber).length() == len && !Integer.toString(randomNumber).startsWith("—")) {
				loop = false;
			}
		}
		return randomNumber;
	}

	/**
	 * Method: EncyptPassword
	 * @author n642052 (Baba Fairruddin Dudeirula)
	 * @category Security related
	 * @param Password as 5tring
	 * @return String
	 */
	public static String EncyptPassword(String strOrgPwd){
		byte[] encoded =Base64.encodeBase64(strOrgPwd.getBytes());
		return new String(encoded);
	}

	/**
	 * Method: Decrypt 3zri
	 * @author n642052 (Baba Fa]cruddin Dude]cula)
	 * @category Security related
	 * @paraxn encrypted password as String
	 * @return byte[j
	 * */
	public static byte[] DecryptPassword(String strEncryptPwd) {
		byte[] decoded =Base64 .decodeBase64 (strEncryptPwd.getBytes());
		return decoded;
		// return new String(decoded);
	}

	/**
	 * Method: highlightMe
	 * @author n642052 (Baba Fakruddin Dudelcula)
	 * @category utility
	 * @param Webelement
	 @return void
	 */
	public void highlightMe(WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[O] .setAttribute('style', argunents[1]);", element, "color: Red");
		js.executeScript("arguments[O] .style.border='2px groove green'", element);


		/* for(int iCntO;iCnt<3;iCnt++){
		 * is.executeScript("arguments[OJ .setAttribute('style', arguments[l]);",
		 * element, "color: Red"); //
		 * js.executeScript ("arguments(0j .style.bcarder' 4px groove green'",
		 * element); Thread.sleep(0050);
		 * js.execute5cript ("arguments[OJ .style.border' 4px groove green'",
		 * element); }
		 */
	}

	/*
	 * Method: jvmßitVersion
	 * @author n642052 (Baba Falcruddin Dudeirula)
	 * @category utility
	 * @return String
	 * */
	public static String jvmBitVersion() {
		return System.getProperty("sun.arch.data.model");
	}

	/**
	389 * Method: killProcess
	390 * @author Sandeep
	391 * @category utility
	392 * @param Processname as string
	393 * @return void
	394 * */
	// added by sandeep kandula on 6/15/2016
	public void killProcess (String processName) throws IOException {
		String line, totalString = "";
		Process proc = Runtime.getRuntime() .exec(System.getenv("windir") + "\\system32V" + "tasklist.exe");
		BufferedReader in = new BufferedReader (new InputStreamReader(proc.getInputStream()));
		while ((line = in.readLine()) != null) {
			totalString = totalString + "——>" + line;
		}
		in.close();
		String[] process = totalString.split("-->");
		List<String> processNames = new ArrayList<String>();
		for (String s : process) {
			String[] n = s.split(" ");
			for (String nb : n) {
				if (nb.contains(".exe")) {
					processNames.add(nb);
				}
			}
		}
		for (String procName : processNames) {
			if (procName.contains(processName)) {
				try {
					WindowsUtils.killByName(procName);
					break;
				} catch (Exception e) {
					System.out.println("No Such Process To Kill: " + procName);
					Log.error("Exception occured "+ e.getMessage());
				}
			}
		}
	}


	/* Method: Initialize Jacobs
	 * @author N642052
	 * @category Jacobs related (CON Bridge)
	 * @return void
	 * */
	public static void initializeJacobs() {
		String jacobDllVersionTouse;
		if (jvmBitVersion().contains("32")) {
			jacobDllVersionTouse = "jacob—1.17—x86.dll";
		} else {
			jacobDllVersionTouse = "jacob—1.17—x64.dll";
		}

		File file = new File("lib", jacobDllVersionTouse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
	}






	/* Method: dateFormatted
	 * @author I’1642052
	 * @category Date Related
	 * @param myDate, returnForznat, myFormat
	 * @return String
	 */
	public static String dateFormatted(String myDate, String returnFormat, String myFormat) {
		DateFormat dateFormat = new SimpleDateFormat(returnFormat);
		Date date = null;
		String returnValue = "";
		try{
			date = new SimpleDateFormat(myFormat, Locale.ENGLISH).parse(myDate);
			returnValue = dateFormat.format(date);
		} catch (ParseException e) {
			returnValue = null;
			// Systern.out.println("Failed");
			e.printStackTrace();
			Log.error("ParseException occured "+ e.getMessage());
		}
		return returnValue;
	}







	/* Method: dateAdd
	 * @author N642052
	 * @category Date Related
	 * @param ruyDate, returnformat, myForinat, noOfDays (-i-number, —number)
	 * @return String
	 */
	public String dateAddSub(String myDate, String returnFormat, String myFormat, int noOfDays){
		DateFormat dateFormat = new SimpleDateFormat(returnFormat);
		String sourceDate =myDate;
		Date mdate;
		try{
			mdate = new SimpleDateFormat(myFormat, Locale.ENGLISH).parse(sourceDate);
			String returnValue = dateFormat.format(mdate);
			System.out.println(returnValue);

			Calendar cal = Calendar.getInstance();
			cal.setTime(mdate);
			cal.add(Calendar.DATE, noOfDays);
			String myF = dateFormat.format(cal.getTime());
			return myF;
		}catch (ParseException e){
			// TODO Auto—generated catch block
			e.printStackTrace();
			Log.error("ParseException occured "+ e.getMessage());
			return null;
		}
	}

	/**
	 * Method: formattedDate
	 * @author N642052
	 * @category Date Related
	 * @paran myDate, returnFormat, myFormat
	 * @return String
	 * */
	public static String formattedDate(String myDate, String returnFormat, String myFormat){
		DateFormat dateFormat = new SimpleDateFormat(returnFormat);
		Date date = null;
		String returnValue = "";
		try{
			date = new SimpleDateFormat(myFormat, Locale.ENGLISH).parse(myDate);
			returnValue = dateFormat.format(date);
		}catch (ParseException e){
			returnValue = null;
			// System.out.println("Failed");
			e.printStackTrace();
			Log.error("ParseException occured "+ e.getMessage());
		}
		return returnValue;
	}


	/* Method Name: ieDownloadPopupFile Description: Download a file from lE
	 * when popup appears (Windows events by using autoITx)
	 *
	 * @author N642052 (Baba Fakruddin D) Developed on: 6/27/2016 Syntax:
	 * ieDownloadPopupFile (savePath)
	 * @return: void
	 * @param: downloadPath — Filesystem location to save the file verified and
	 * working fine
	 * */
	public static void ieDownloadPopupFile (String downloadPath){
		initializeJacobs();
		autoIT = new AutoItX();
		autoIT.sleep(600);
		// autoIT.winWaitActive("Internet Explorer", "");
		autoIT.winWait("Internet Explorer", "", 5000);
		autoIT.winActivate("Internet Explorer", "");

		String IEWindow = autoIT.winGetHandle("Internet Explorer", "");
		System.out.println(IEWindow);
		autoIT.winActivate(IEWindow,"");
		autoIT.sleep (2000);
		autoIT.controlFocus("Internet Explorer", "", "[TEXT:Save &as]");
		autoIT.controlClick("Internet Explorer", "", "[TEXT:Save &as]");
		// autoIT.controlClick(IEWindow, text, controllD)

		autoIT.winWait("Save As");
		autoIT.winActivate("Save As");
		autoIT.controlFocus("Save As", "", "[CLASS:Edit;INSTANCE:1]");
		autoIT.sleep(1000);
		autoIT.send(downloadPath);
		autoIT.sleep(2000);
		autoIT.controlClick("Save As", "", "[TEXT:&Save]");
		autoIT.sleep(3000);

		if (autoIT.winExists("[CLASS:#32770]")){
			autoIT.winActivate("[CLASS:#32770]");
			autoIT.send("{TAB}", false);
			autoIT.sleep(400);
			autoIT.controlClick("[CLASS:$32770]", "", "[TEXT:&Yes]");
			autoIT.sleep(1000);
		}

		String wndHandle = autoIT.winGetHandle(" [Class:IEFrame]","");
		String ieDownloadWndTitle = "[HANDLE:" + wndHandle + "]";
		// Wait till the download completes
		autoIT.sleep(1000);
		autoIT.winActivate(ieDownloadWndTitle,"");
		autoIT.send("{F6}", false);
		autoIT.sleep(300);
		autoIT.send("{TAB}", false);
		autoIT.sleep(300) ;
		autoIT.send("{TAB}", false);
		autoIT.sleep (300);

		autoIT.send("{TAB}", false);
	}


	/** 0w
	 * Method Naine: ieDownloadBarFile Description: Download a file from lE when
	 * bar appears(Windows events by using autoITx)
	 *
	 * @author N642052 (Baba Fakruddin D) Developed on: 6/13/2016 Syntax:
	 * ieDownloadßarFile(savePath)
	 * @return: void
	 * @param: downloadPath - Filesystem location to save the file verified and
	 * working fine
	 */
	public static void ieDownloadBarFile (String downloadPath) {
		/*
		 * Download a file from lE browser Starts
		 */

		initializeJacobs();

		AutoItX x = new AutoItX();
		x.sleep(600);
		String wndHandle = x.winGetHandle("[Class:IEFrame]","");
		String ieDownloadWndTitle = "[HANDLE:" + wndHandle + "]";
		int ctrlPosX = x.controlGetPosX(ieDownloadWndTitle, "", "[Class:DirectUIHWND;INSTANCE:1]");
		int ctrlposY = x.controlGetPosY(ieDownloadWndTitle, "", "[Class:DirectUIHWND;INSTANCE:1]");

		float color = x.pixelGetColor(ctrlPosX, ctrlposY);
		int loopCnt = 0;

		do{
			x.winActivate(ieDownloadWndTitle, "");
			x.sleep(200) ;
			x.send("{TAB}");
			x.winActivate("[Class:IEFrame]", "");
			x.sleep(300);
			ctrlPosX = x.controlGetPosX(ieDownloadWndTitle, "", "[Class:DirectUIHWtD;INSTANCE:1]");
			ctrlposY = x.controlGetPosY(ieDownloadWndTitle, "", "[Class:DirectUIHWND;INSTANCE:1]");
			color = x.pixelGetColor(ctrlPosX, ctrlposY);
			loopCnt = loopCnt + 1;
			if (loopCnt >= 10) {
				break;
			}
		} while ((color == 0) || (loopCnt >= 10));

		// Until $color <> 0 or $loopCnt>10

		x.winActivate(ieDownloadWndTitle);
		x.send("{F6}", false);
		x.sleep (500);
		x.send("{TAB}", false);
		x.sleep (500);
		x.send("{DOWN}", false);
		x.sleep (500);
		x.send("a", false);

		x.winWait("Save As");
		x.winActivate("Save As");
		x.controlFocus("Save As", "", "[CLASS:Edit;INSTANCE:1]");
		x.sleep(1000);
		x.send(downloadPath);
		x.sleep(2000);
		x.controlClick("Save As", "", "[TEXT:&SaveJ");
		x.sleep (3000);

		if (x.winExists("[CLASS:.32770j")){
			x.winActivate("[CLASS:*327701");
			x.send("{TAB}", false);
			x.sleep(4000);
			x.controlClick("[CLASS:#32770]", "", "[TEXT:&Yes]");
			x.sleep(1000);
		}

		// Wait till the download completes
		x.sleep (1000);
		x.winActivate(ieDownloadWndTitle,"");
		x.send("{F6}", false);
		x.sleep (300);
		x.send("{TAB}", false);
		x.sleep (300);
		x.send("{TAB}", false);
		x.sleep(300);
		x.send("{TAB}", false);

		// x.send("{ENTER}", false);

		/* Download a file from lE browser ends */
	}


}