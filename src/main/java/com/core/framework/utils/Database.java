package com.core.framework.utils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.core.framework.utils.HtmlReporter;

@SuppressWarnings("unused")

public class Database {
	private String driver,dbType;
	private final String userName;
	private final String password;
	private final String tableSchema;
	private final String dbUrl;
	private Connection conn = null;

	/**
	 * @category : DB Related
	 * @param : dbType, dbUrl, tableSchema, userNazue, password
	 * @author : Sandeep
	 * */
	public Database(String dbType,String dbUrl,String tableSchema,String userName,String password) {
		this.dbUrl = dbUrl;
		this.userName = userName;
		this.password = password;
		this.dbType = dbType;
		this.tableSchema = tableSchema;
		switch(dbType.toLowerCase()){
		case "db2": this.driver = "com.ibm.db2.jcc.DB2Driver";
		break;
		case "oracle": this.driver = "oracle.jdbc.driver.OracleDriver";
		break;
		case "mysql" : this.driver = "com.mysql.jdbc.Driver";
		break;
		default : try{
			HtmlReporter.Log("DB connection for DB Type", "DB Type:"+dbType+" is not handled in switch or it is not given", "fail");
		} catch(IOException e){
			// TODO Auto—generated catch block
			Log.info("IOException occured" + e.getMessage());
		}
		}
	}

	/**
@author Sandeep
@category : DB Related
@parasn : null
	 * @return : void
Description : get connection with db
@throws ClassNotFoundException */
	private void getDBConnection(){
		try{
			Class.forName(driver);
			System.out.println("Driver Got Loaded");
		}catch (ClassNotFoundException err){
			Log.info("ClassNotFoundException occured" + err.getMessage());
			System.err.println("FAILED to load the "+dbType+"driver.Exiting program.");
			err.printStackTrace (System.err);
		}
		try{
			System.out.println("Connecting to database...");
			conn =DriverManager.getConnection (dbUrl, userName, password);
			System.out.println("Database connected.");
		}catch (SQLException err){
			Log.info("SQLException occured" + err.getMessage());
			System.err.println("SQL error.");
			err.printStackTrace (System.err);
		}catch (Exception e){
			Log.info("Exception occured" + e.getMessage());

			e.printStackTrace ();
		}
	}

	/**
	98 * @author : Sandeep Kandula
	99 * @categcry : DB Related
	100 * @return : List hashMap<String, String>
	101 * @paraxr.: query
	102 * Description : Execute Query
	103*/

	public List<HashMap<String,String>> executQuery(String query){
		ResultSet rs = null;
		Statement s = null;
		List<HashMap<String,String>> results = new ArrayList<HashMap<String,String>>();
		try{
			System.out.println("Connecting to database...");
			getDBConnection();
			System.out.println("Database connected.");
			s = conn.createStatement();
			rs = s.executeQuery(query);
			System.out.println("Query executed");
			ResultSetMetaData rsmd = rs.getMetaData();
			List<String> columns = new ArrayList<String>();
			int colCount = rsmd.getColumnCount();
			if(colCount>0){
				for(int i=1;i<=colCount;i++){
					columns.add(rsmd.getColumnName(i));
				}
			}
			while (rs.next())

			{
				HashMap<String,String> hm = new HashMap<String, String>();
				for(String colName:columns){
					hm.put(colName, rs.getString(colName));
				}
				results.add(hm);
			}

		}catch (SQLException err){
			Log.info("SQLException occured" + err.getMessage());
			System.err.println("SQL errer.");
			err.printStackTrace (System.err);
		}catch (Exception e){
			Log.info("Exception occured" + e.getMessage());
			e.printStackTrace();
		}
		// STEP 7: Clean up the environment
		finally{
			try{
				rs.close();
			}catch (Exception e){
			}
			try{
				s.close();
			}catch (Exception e){
			}

			try{
				conn.close();
			}catch (Exception e){

			}
		}
		return results;
	}


	/* * @author : Sandeep Kandula
	 * @category : DB Related
	 * @return : List<String>
	 * @param : query
	 * Description : Get Column Naines based on Query
	 * */
	public List<String> getColunnNanes (String query){
		List<String> columns = new ArrayList<String>();
		ResultSet rs = null;
		Statement s = null;

		try{
			System.out.println("Connecting to database...");
			getDBConnection();
			System.out.println("Database connected.");
			s = conn.createStatement();
			rs = s.executeQuery(query);
			System.out.println("Query executed");
			ResultSetMetaData rsmd = rs.getMetaData();

			int colCount = rsmd.getColumnCount();
			if(colCount>0){
				for(int i=1;i<=colCount;i++){
					columns.add(rsmd.getColumnName(i));
				}
			}
		}catch (SQLException err) {
			Log.info("SQLException occured" + err.getMessage());
			System.err.println("SQL error.");
			err.printStackTrace (System.err);
		}catch (Exception e){
			Log.info("Exception occured" + e.getMessage());
			e.printStackTrace();
		}
		// STEP 7: Clean up the envircr.r*:
		finally{
			try{
				rs.close();
			}catch (Exception e){
			}
			try{
				s.close();
			}catch (Exception e){

			}
			try{
				conn.close();
			}catch (Exception e){

			}
		}
		return columns;
	}

	/** @author : Sandeep Kandula
	215 * @category : DB Related
	216 * @return : List<String>
	217 * @param : connection
	218 * Description : Get schemas by Connection
	219 */

	private static List<String> getSchemas (Connection conn){
		List<String> schemasList = new ArrayList<String>();
		try{
			String tableSchema=null;

			DatabaseMetaData md = conn.getMetaData();
			ResultSet schemas = md.getSchemas();
			while (schemas.next()){
				tableSchema = schemas.getString(1); // "TABLE SCITEM"
				schemasList.add(tableSchema);
			}

		}catch (SQLException e){
			Log.info("SQLException occured" + e.getMessage());
			String theError = e.getSQLState();
			System.out.println("Can’t query DB metadata: " + theError);
			// System.exit(1);
		}catch (Exception e){
			Log.info("Exceptaon occured" + e.getMessage());
			e.printStackTrace();
		}
		return schemasList;
	}

	/* @author Sandeep Kandula
	 * @category : DB Related
	 * @return : List<String>
	 * @paraxn : connection, tableSchema
		Description : Get Tables cf connection and Table schema
	 * */
	private static List<String> getTables(Connection conn,String tableSchema){
		List<String> tableNames = new ArrayList<String>();
		try{
			DatabaseMetaData md = conn.getMetaData();

			ResultSet r = md.getTables(null, tableSchema, "%", null);
			while (r.next()){
				tableNames.add(r.getString("TABLE_NAME")); // or getString(3)
			}
		}catch (SQLException e){
			Log.info("SQLException occured" + e.getMessage());
			String theError = e.getSQLState();
			System.out.println("Can't query DB metaciata: " + theError);
			// System.exit(1);
		}catch (Exception e){
			Log.info("Exception occured" + e.getMessage());
			e.printStackTrace();
		}
		return tableNames;
	}
}

