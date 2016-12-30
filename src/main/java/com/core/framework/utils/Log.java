package com.core.framework.utils;

import org.apache.log4j.Logger;

public class Log{
	private static Logger Log = Logger.getLogger(Log.class.getName());
	// This is to print log for the beginning of the test case, as we usually
	// run so many test cases as a test suite
	/**
	 * @author Dhilip
	 * @param sTestCaseName
	 * @return void
	 * */
	public static void startTestCase (String sTestCaseName) {
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$ " + sTestCaseName+ "         $$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}

	/**
	 * @author Dhilip
	@pararn endTestCase
	 * @return void
	 */
	// This is to print log for the ending of the test case
	public static void endTestCase (String sTestCaseName) {
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXX " + "-E---N---Ð-"+ " XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX ");
		Log.info("X");
		Log.info("X");
		Log.info("X");
		Log.info("X");
	}

	/**
	 * @author Dhilip
	 * @param info
	 * @return vcid
	 */
	// Need to create these methods, so that they cari be called
	public static void info (String message) {
		Log.info (message);
	}

	/**
	 * @author Dhilip
	 * @param warn
	 * @return void
	 * */
	public static void warn (String message) {
		Log.warn (message);
	}

	/**
	 * @author Dhilip
	 * @param error
	 * @return void
	 * */
	public static void error (String message) {
		Log.error (message);
	}
	/**
	 * @author Dhilip
	 * @param fatal
	 * @return void
	 */
	public static void fatal (String message) {
		Log.fatal (message);
	}

	/**
	 * @author Dhilip
	 * @paran debug
	 * @return void
	 * */
	public static void debug(String message) {
		Log.debug (message);
	}
}
