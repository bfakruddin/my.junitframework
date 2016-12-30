package com.core.framework.utils;

/**
4 @author N642052 (Baba Fakruddin Dudekula)
5 * Class : DataTable
6 * Description : Excel Data management with P01
7 * Category : Generic
 * */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class DataTable extends PublicVariables{

	public static String File = null;
	public String xlDataFileName;
	public String xlSheetName;
	public static Map<String, String> RowData = new HashMap<String, String>();
	private String path;
	public FileInputStream inputStream = null;
	public FileOutputStream outputStream = null;
	public String hname, value;
	public int i, j;
	public File file;
	public Workbook workBook;
	public Sheet sheet;

	public DataTable(){

	}

	/**
	 * @author N642052
	 * @category : Excel Data Management
	 * @param : xlPath
	 * @throws : IOException
	 * Description: Constructor ó Which set Excel file for next operations
	 */
	public DataTable(String xlPath){
		this.path = xlPath;
		this.file = new File(path);

		try{
			inputStream = new FileInputStream(path);
			if(path.split("\\.")[1].equalsIgnoreCase("xlsx")) {
				workBook = new XSSFWorkbook(inputStream);
			} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
				workBook = new HSSFWorkbook(inputStream);
			}

			sheet = workBook.getSheetAt(0);
			inputStream.close();
		}catch (IOException e){

			/*
			 * try{ workBook = WorkbookFactory.create(file); }catch
			 * (InvalidFormatException I IOException e1) { // TODO
			 * Autoógenerated catch block el.printStackTrace; }
			 */
			Log.info("IOException occured" + e.getMessage());
			e.printStackTrace();
		}
	}

	/* @author N642052
	 * @category : Excel Data Management
	 * @param : xlPath, sheetName
	 * @throws IOException
	 * @throws FileNotFoimdException
	 * @throws : IOException
	 * Description: Constructor ó Which set Excel file for next operations
	 * */
	public DataTable (String xlPath, String sheetName) throws IOException{
		this.path = xlPath;
		this.file = new File(path);
		try{
			inputStream = new FileInputStream(path);
			if (path.split("\\.")[1].equalsIgnoreCase("xlsx")) {
				workBook = new XSSFWorkbook(inputStream);
			} else if (path.split("\\.")[1].equalsIgnoreCase("xls")) {
				workBook = new HSSFWorkbook(inputStream);
			}
			sheet = workBook.getSheet(sheetName);
			inputStream.close();
		}catch (FileNotFoundException e) {
			Log.info("FileNotFoundException occured" + e.getMessage());
			e.printStackTrace();
		}
	}


	/*
	 * public DataTable(File file, XSSFWorkboolc workBoolc)( this.file = file;
	 * this.worlcBooic = workBook; System.out.println("For Creating object"); }
	 */

	/* @author N642052
	 * @category : Excel Data Management
	 * @param : file, workBook, sheet
	 * @throws : IOException
	 * Description: Constructor ó Which set Excel file for next operations
	 * */
	public DataTable(File file, Workbook workBook, Sheet sheet) throws IOException {
		inputStream = new FileInputStream(file);
		this.path = file.toString();
		if (path.split("\\.") [1].equalsIgnoreCase("xlsx")){
			workBook = new XSSFWorkbook(inputStream);
		} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
			workBook = new HSSFWorkbook(inputStream);
		}
		this.file = file;
		this.workBook = workBook;
		this.sheet = sheet;
		if (inputStream != null){
			inputStream.close();
		}
	}

	/*
	 * Function: exportHTMLtoExcel
	 * @author N642052
	 * @param HTMLFi1e, Target Excel file path (New)
	 * @throws IOException
	 * return : void
	 **/
	@SuppressWarnings("resource")
	public void exportHTMLtoExcel(String htmlFilePath, String excelFilePath, String... sheetName) throws IOException {
		File file = new File(excelFilePath);
		if (file.exists()){
			file.delete();
		}

		DataTable dataTable = new DataTable();
		dataTable.createWorkBook(excelFilePath);

		if (sheetName.length > 0){
			dataTable.setFileAndSheet(excelFilePath, sheetName[0]);
		}else{
			dataTable.setFileAndSheet(excelFilePath);
		}

		file = new File (htmlFilePath);
		FileReader fileReader = new FileReader (file);
		BufferedReader br = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null){
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}

		String everything = sb.toString();
		System.out.println(everything);

		Document html = Jsoup.parse(everything);

		Elements elmnts = html.getElementsByTag("table");

		int rowNum = 0;
		int colNum = 0;
		String cellValue = null;
		for (Element elmnt : elmnts){
			Elements tbodyElements = elmnt.getElementsByTag("tbody");
			for (Element tbodyElmnt : tbodyElements){
				Elements rowElements = tbodyElmnt.getElementsByTag("tr");
				for (Element rowElmnt : rowElements){
					rowNum = rowNum + 1;
					colNum = 1;
					Elements colElements = rowElmnt.getElementsByTag("td");
					for (Element colElmnt : colElements){
						cellValue = colElmnt.ownText();
						// System.out.prmntln("Row, Column, Value óó> "+rowNum +
						// ", "-4-colNum-4-", "+cellValue);
						if (cellValue != null)
							dataTable.setCellData(rowNum, colNum, cellValue);
						// System.cut.println(cellValue);
						colNum = colNum + 1;
						cellValue = null;
					}
				}
			}
		}
	}

	/*
	213 * Function: getColumnData
	214 * @author N642052
	215 * @category : Excel Test Data management
	216 * @param ColumnNasne, startRow (vararge)
	217 * @returns : List<String>
	218 * */
	public List<String> getColumnData(String ColumnName, int... startRow){
		// Cell actCol = getColumn(columnName, 1);
		Row row = null;
		int colNo = getColumnNumber(ColumnName, startRow);
		List<String> actVals = new ArrayList<String>();

		if (startRow.length > 0){
			row = sheet.getRow(startRow[0] - 1);
		}else{
			row = sheet.getRow(0);
		}

		boolean runflag = false;
		if (colNo != -1){
			for (Row rows : sheet){
				if (!runflag){
					if (rows != row){
						continue;
					}
				}
				row = rows;
				Cell c = rows.getCell(colNo - 1);

				if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK){
					// Nothing in the cell in this row, skip it
				}else{
					actVals.add(c.toString());
				}
				runflag = true;
			}
		}

		return actVals;
	}

	/* Function: getColurnnData
	 * @author N642052
	 * @category : Excel Test Data management
	 * @param ColumnName
	 * @deprecated
	 @ret :
	 **/
	public List<String> getColumnData(String ColumnName){
		// Cell actCol = getColumn(ColuxrnNante, 1);
		Row row = null;
		int colNo = getColumnNumber(ColumnName, 1);
		List<String> actVals = new ArrayList<String>();

		row = sheet.getRow(0);

		boolean runflag = false;
		if (colNo != -1){
			for (Row rows : sheet){
				if (!runflag){
					if (rows != row){
						continue;
					}
				}
				row = rows;
				Cell c = rows.getCell(colNo - 1);

				if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK){
					// Nothing in the cell in this row, skip it
				}else{
					actVals.add(c.toString());
				}
				runflag = true;
			}
		}
		return actVals;
	}

	/* Method Nane: getRowData Description: Get entire row data into single
	 * public hash map <columnName, cellValue> tcRowData Developed By: Baba
	 * Fakruddin D (N642052) Developed on: 2015 Syntax: getRowData(filePath,
	 * SheetName, CellValuelnFirstColumn) Returns: NA verified and working fine
	 * */

	/* Function: getRowData
	 * @author N642052
	 * @category : Excel Test Data management
	 * @param xlPath, Sheetname, TCID
	 * @returns : void
	 * @throws : {@link InvalidFonaatException), {@link IOException}
	 * */
	public static void getRowData(String xlPath, String Sheetname, String TCI0) throws InvalidFormatException, IOException {
		// InputStreaxr.inputStreaxr.= null;
		FileInputStream inputStream= null;
		String hname;
		String value;
		File file;
		XSSFWorkbook workBook;
		XSSFSheet sheet;
		int rowNum, colNum, i, j;
		tcRowData.clear();

		// create map to store
		/*
		 * file = new File(xlPath); input5c.xeam new rneinpuc.ac.LeamLtne,;
		 */

		try{
			file = new File(xlPath);
			inputStream = new FileInputStream(file);
		}catch (FileNotFoundException e){
			System.out.println("File not found in the specified pathS");
			Log.info("FileNotFoundException occured" + e.getMessage());
			e.printStackTrace();
		}
		try{
			// filesystem = new POIFSFileSystem(inputStream);
			int rownum = 0;

			// XSSFWorkbook wb = new XSSFWorkbook(inputStream);

			workBook = new XSSFWorkbook(inputStream);
			sheet = workBook.getSheet(Sheetname);
			rowNum = sheet.getLastRowNum() + 1;
			colNum = sheet.getRow(0).getLastCellNum();
			for (i = 1; i < rowNum; i++){
				XSSFRow row = sheet.getRow(i);
				j = 0;
				XSSFCell cell = row.getCell(j);
				value = cell.toString();
				if (value.equalsIgnoreCase(TCI0)){
					int rowTestcase = row.getRowNum();
					rownum = rowTestcase;
					break;
					// int colTestcase=cell.getCellNuin();
				}
			}
			XSSFCell cell = null;
			// loop to fetch values from excel
			for (i = 0; i < colNum; i++){
				cell = sheet.getRow(rownum).getCell(i);
				if (cell != null){
					// System.out.println(cell.getStringCellValue);
					value = cell.toString();
					hname = sheet.getRow(0).getCell(i).toString();
					// System.out.prmntln("coluxn is ó "-rsheet.getRow(Op.getCe//ja1;
					// System.out.prmntln("value is ó "+value);
					// allvalues = allvalues + hname+":"+value+"\n";
					// Map<String, String> map = new MashMap<String, String>();
					// add all row values to hash map
					tcRowData.put(hname, value);
					// System.out.println("data is ó "ncRowData.get(hname).toString);
				}else{
					value = "";
					hname = sheet.getRow(0).getCell(i).toString();
					tcRowData.put(hname, value);
				}
			}
		}catch (IOException e){
			Log.info("IOException occured" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Function: getRowCount
	 * @author N642052
	 * @category : Excel Test Data management
	 * @param sheetName
	 * @returns : rowCount
	 */
	public int getRowCount (String sheetName){
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1){
			return -1;
		}else{
			sheet = workBook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}



	/**
	 * Function: setFile Description: Set the excel file based on input filePath
	 * @author N642052
	 * @category Test Data management
	 * @param filePath
	 * @return void
	 * @throws FileNotFoundExcctz:z
	 */
	public void setFile (String filePath) throws FileNotFoundException {
		inputStream = null;
		workBook = null;
		file = null;
		this.path = filePath;
		try{
			file = new File(filePath);
			inputStream = new FileInputStream(file);
		}catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			System.out.println("File not found in the specified path.");
			Log.info("FileNotFoundException occured" + e.getMessage());
			e.printStackTrace();
		}
		try{
			if (path.split("\\.")[1].equalsIgnoreCase("xlsx")){
				workBook = new XSSFWorkbook(inputStream);
			} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
				workBook = new HSSFWorkbook(inputStream);
			}
			// workBook = new XSSFWorkbook(inputStream);
			inputStream.close();
		}catch (IOException e){
			// TODO Autoógenerated catch block
			Log.info("IOException occured" + e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * Function: createWorkbook Description: Creates an excel work book in
	 * filepath with "Sheetl" sheet as sent as parameter
	 *
	 * @author N642052 (Baba)
	 * @param filePath
	 * @return Workbook
	 * @category Excel Test Data management
	 * @throws IOExcepticn
	 */
	public Workbook createWorkBook(String filePath) throws IOException{
		inputStream = null;
		workBook = null;
		sheet = null;
		file = null;
		this.path = filePath;
		file = new File(filePath);
		if (file.exists()){
			inputStream = new FileInputStream(file);
		} else {
			if (path.split("\\.")[1] .equalsIgnoreCase("xlsx")){
				workBook = new XSSFWorkbook();
			} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
				workBook = new HSSFWorkbook() ;
			}
			// workBook = new XSSFWorkbook();
			outputStream = new FileOutputStream(file);
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			inputStream = new FileInputStream(file);
		}

		if (path.split("\\.")[1].equalsIgnoreCase("xlsx")){
			workBook = new XSSFWorkbook(inputStream);
		} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
			workBook = new HSSFWorkbook(inputStream);
		}

		if (workBook.getNumberOfSheets() == 0)
			sheet = workBook.createSheet();
		// DataTableMethods.oreateSheet(MSheetl");
		//		if sheet = workBook.createSheet("Sheetl");
		inputStream.close();
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();


		if (file.exists()){
			return workBook;
		}else{
			return null;
		}
	}

	/* Function: createWorkBook Description: Creates an excel workbook and in
	 * filepath and sheetName as sent as parameter
	 * @author N642052
	 * @param filePath, sheetName
	 * @return XSSFWorkbook
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	public Workbook createWorkBook(String filePath, String sheetName) throws IOException {
		inputStream = null;
		workBook = null;
		sheet = null;
		file = new File(filePath);
		this.path = file.toString();
		if (file.exists()){
			inputStream = new FileInputStream(file);
		}else{
			if (path.split("\\.")[1] .equalsIgnoreCase("xlsx")){
				workBook = new XSSFWorkbook();
			} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
				workBook = new HSSFWorkbook();
			}
			// workBook = new XSSFW:rkbook();
			outputStream = new FileOutputStream(file);
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			inputStream = new FileInputStream(file);
		}

		if (path.split("\\.")[1].equalsIgnoreCase("xlsx")){
			workBook = new XSSFWorkbook(inputStream);
		} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
			workBook = new HSSFWorkbook(inputStream);
		}
		// workBook = new XSSFWorkbook(inputStream);

		DataTable dts = new DataTable(file, workBook, sheet);
		dts.createSheet(sheetName);

		// DataTableMethods.createSheet (sheetName);
		// sheet = workBook.createSheet("Sheetl");

		inputStream.close();
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();

		if (file.exists()){
			return workBook;
		}else{
			return null;
		}
	}

	/**
	 * Function: createSheet Description: Creates excel sheet in workbook which
	 * set as global, use setfile to set one excel file
	 *
	 * @author N642052
	 * @param sheetName
	 * @return Sheet
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	@SuppressWarnings ("unused")
	public Sheet createSheet(String sheetName) throws IOException {
		int shtCnt = workBook.getNumberOfSheets();
		Boolean blnFlag;
		if (shtCnt > 0){
			for (int i = 0; i < shtCnt; i++){
				blnFlag = true;
				for (int j = 0; j < shtCnt; j++){
					if (workBook.getSheetName(j) .equalsIgnoreCase(sheetName)) {
						blnFlag = false;
						break;
					}
				}
				if (blnFlag) {
					sheet = workBook.createSheet(sheetName);
					break;
				}else{
					break;
				}
			}
		} else if (shtCnt == 0){
			sheet = workBook.createSheet(sheetName);
		}

		sheet = workBook.getSheet(sheetName);
		Row row = sheet.createRow(0);
		row.createCell(0);
		try {
			if (inputStream != null){
				inputStream.close();
			}
			outputStream = new FileOutputStream(file);
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}catch (IOException e){
			// TODO Autoógenerated catch block
			Log.info("IOException occured" + e.getMessage());
			e.printStackTrace();
		}
		return sheet;
	}


	/**
	 * Function: saveAs Description: Save an excel workBook as excel file in the
	 * path sent as paraxr.eter
	 *
	 * @author N642052
	 * @param sheetName
	 * @return void
	 * @throws IOException
	 * @category Excel Test Data management
	 */
	public void saveAs(String filePath) throws IOException {
		outputStream = new FileOutputStream(new File(filePath));
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/* Function: closeworkBook
	 * Description: Close work book
	 *
	 * @author N642052
	 * @return void
	 * @category Excel Test Data management
	 */
	public void closeworkBook(){
		try{
			outputStream.flush();
			outputStream.close();
		}catch (IOException e){
			// TODO Autoógenerated catch block
			Log.info("IOException occured" + e.getMessage());
			e.printStackTrace();
		}
	}

	/* Function: setSheet
	 * Description: setSheet
	 *
	 * @author N642052
	 * @return Sheet
	 * @param sheetName
	 * @category Excel Test Data management
	 * */
	public Sheet setsheet(String sheetName){
		sheet = workBook.getSheet(sheetName);
		return sheet;
	}


	/*
655 * Function: getSheetName
659 * Description: Get Sheet Name
660 *
661 * @author N642052
662 * @return String
663 * @category Excel Test Data management
664 * */
	public String getSheetName(){
		return sheet.getSheetName();
	}



	/**
	 * Function: setFileAndSheet
	 * Description: Set XLFi1e and Sheet
	 *
	 * @author N642052
	 * @return void
	 * @param filePath, sheetName(varargs)
		 @category Excel Test Data management
	 * @throws IOException
	 * */
	@SuppressWarnings ("unused")
	public void setFileAndSheet(String filePath, String... sheetName) throws IOException {
		/*
		 * inputStreaxr.= null; workBook = null; sheet = null; DataTable dts =
		 * new DataTable(filePath); dts.setFile(filePath);
		 * dts.setSheet(sheetName);
		 */

		try{
			inputStream = new FileInputStream(filePath);
			this.path = filePath;
			if (path.split("\\..")[1].equalsIgnoreCase("xlsx")){
				workBook = new XSSFWorkbook(inputStream);
			} else if (path.split("\\.")[1].equalsIgnoreCase("xls")){
				workBook = new HSSFWorkbook(inputStream);
			}
			if (sheetName.length > 0){
				sheet = workBook.getSheet(sheetName[0]);
			}else{
				for (int i = 0; 1< workBook.getNumberOfSheets(); i++) {
					// Systein.out.println(workBook.getsheetName(i));
					sheet = workBook.getSheet(workBook.getSheetName(i));
					break;
				}
				/*
				 * wor]c3cck.setActjve5heet(()); int in =
				 * workBook.getActivesheetlndex(); sheet =
				 * workBook.getSheetAt(mn);
				 */
				// sheet = workBook.getsheetAt(0);
			}

			File file = new File (filePath);
			DataTable dts = new DataTable(file, workBook, sheet);
			inputStream.close();
		}catch (FileNotFoundException e){
			Log.info("FileNotFoundException occured" + e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * Function: value
	 * Description: capture value of inputs cell by row and column
	 *
	 * @author N642052
	 * @return String(Cell Value)
	 * @param row, column
	 * @category Excel Test Data management
	 * */
	public String value (int row, int column){
		row = row - 1;
		column = column - 1;
		Cell cellValue = sheet.getRow(row).getCell(column);
		if (cellValue != null){
			return cellValue.toString();
		}else{
			System.out.println("No Value contains in Row/Column : " + row + "/" + column);
			return null;
		}
	}


	/**
	 * Function: getRowData
	 * Description: Get entire row data into Map<String, String>
	 *
	 * @author N642052
	 * @return Map<String, String>
	 * @param rowNun
	 * @category Excel Test Data management
	 * */
	public Map<String, String> getRowData(int rowNum){
		HashMap<String, String> opMap = new HashMap<String, String>();
		Row actRow = getRow(rowNum);
		if (actRow == null){
			return null;
		}
		int i=0;
		// int colCount = getCclusnnCount(rowNusn);
		Iterator<Cell> cellIterator = actRow.iterator();
		while (cellIterator.hasNext()){
			i=i+1;
			Cell cell = cellIterator.next();

			opMap.put("Cell" + i, cell.getStringCellValue().toString());
		}
		return opMap;
	}

	/**
	 * Function: getRowNumber
	 * Description: Get Row Number by Cell Value and column
	 *
	 * @author N642052
	 * @return mt
	 * @param cellValue, column
	 * @category Excel Test Data management
	 * */
	public int getRowNumber(String cellValue, int column){
		int i=0;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()){
			Row row = rowIterator.next() ;
			// System.out.println(row.toString);

			Cell cell = row.getCell(column - 1);
			if (cell != null){
				if (cell.toString().equalsIgnoreCase(cellValue)){
					return i + 1;
				}
			}

			i=i+1;
		}
		return -1;
	}	



	/**
	 * Function: getRow
	 * Description: Get Row by Cell Value and column
	 *
	 * @author N642052
	 * @return Row
	 * @param cellValue, column
	 * @category Excel Test Data management
	 * */
	public Row getRow(String cellValue, int column){
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()){
			Row row = rowIterator.next();
			Cell cell = row.getCell(column - 1);
			if (cell != null){
				if (cell.toString().equalsIgnoreCase(cellValue)){
					return row;
				}
			}
		}
		return null;
	}


	/** Function: getRow
	 * Description: Get Row by rcwNurnber
	 *
	 * @authcr N642052
	 * @return Row
	 * @param row
	 * @category Excel Test Data management
	 */
	public Row getRow(int row){
		int lastRowNo = sheet.getLastRowNum();
		if (row - 1 <= lastRowNo){
			return sheet.getRow(row - 1);
		}else{
			return null;
		}
	}



	/** Function: getRow
	 * Description: Get Row by rowCellText
	 *
					 @authcr N642052
	 * @return Row
	 * @param row
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	public Row getRow(String rowCellText) throws IOException {
		DataTable dts = new DataTable(file, workBook, sheet);
		int rowNum = dts.getRowNumber(rowCellText);
		return dts.getRow(rowNum);
	}

	/**
	 * Function: getRowNuraber
	 * Description: Get Row Number by first column cell value
	 *
	 * @author N642052
	 * @return int
	 * @param tcID
	 * @category Excel Test Data management
	 * */
	public int getRowNumber(String tCID){
		int i=0;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()){
			Row row = rowIterator.next();
			Cell cell = row.getCell(0);
			if (cell.toString().equalsIgnoreCase(tCID)){
				return i + 1;
			}
			i=i+1;
		}
		return -1;
	}

	/**
	 * Function: getCclumnNumber
	 * Description: Get Column Number by cellValue and rowNumber
	 *
	 * @author N642052
	 * @return int
	 * @param cellValue, rowNumber
	 * @category Excel Test Data management
	 * */
	public int getColumnNumber(String cellValue, int rowNumber){
		int i=0;
		rowNumber = rowNumber - 1;
		// sheet.getRow(rowNumber) .getPhysicalNumberOfCells;
		Iterator<Cell> colIterator = sheet.getRow(rowNumber).cellIterator();
		while (colIterator.hasNext()){
			Cell cell = colIterator.next();
			if (cell.getStringCellValue().toString().equalsIgnoreCase(cellValue)){
				return i + 1;
			}
			i=i+1;
		}
		return -1;
	}


	/* Function: getColumn
	 * Description: Get Column Number by cellValue and rcwNuxnber
	 *
	 * @author N642052
	 * @return Cell
			 @param cellValue, rowNumber
	 * @category Excel Test Data management
	 */
	public Cell getColumn(String cellValue, int rowNumber){
		int i = 0;
		rowNumber = rowNumber - 1;
		Iterator<Cell> colIterator = sheet.getRow(rowNumber).cellIterator();
		while (colIterator.hasNext()){
			Cell cell = colIterator.next();
			if (cell.getStringCellValue().toString().equalsIgnoreCase(cellValue)){
				return cell;
			}
			i=i+1;
		}
		return null;
	}


	/* Function: getRowCount
	 * Description: Get Row Count of the sheet
	 *
	 * @author N642052
	 * @return int
	 * @category Excel Test Data management
	 * */
	public int getRowCount(){
		return sheet.getLastRowNum() + 1;
	}

	/* Function: getColumnCount
	 * Description: Get Column Count of the sheet

	 * @author N642052
	 * @return mt
	 * @param row, sheetName
	 * @category Excel Test Data nanagenent
	 * @throws IOException
	 */
	public int getColumnCount(int row, String sheetName) throws IOException {
		DataTable dts = new DataTable(file, workBook, sheet);
		XSSFSheet sheet = (XSSFSheet) dts.setsheet(sheetName);
		return sheet.getRow(row - 1).getLastCellNum();
	}

	/**
	 * Function: getColumnCount
	 * Description: Get Column Count of the sheet
	 *
	 * @authcr N642052
	 * @return mt
	 * @param sheetName
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	public int getColumnCount(String sheetName) throws IOException{
		DataTable dts = new DataTable(file, workBook, sheet);
		XSSFSheet sheet = (XSSFSheet) dts.setsheet(sheetName);
		return sheet.getRow(0).getLastCellNum();
	}

	/* Function: getColumnCount
	 * Description: Get Column Count of the sheet
	 *
	 * @author N642052
	 * @return mt
	 * @param row
	 * @category Excel Test Data management
	 */
	public int getColumnCount(int row){
		// DataTable dts = new DataTable(file, workBook, sheet);
		return sheet.getRow(row - 1) .getLastCellNum();
	}

	/**
	 * Function: getColumnCount
	 * Description: Get Column Count of the sheet
	 *
	 * @author N642052
	 * @return mt
	 * @category Excel Test Data management
	 */
	public int getColumnCount() {
		// DataTable dts = new DataTable(file, workBook, sheet);
		return sheet.getRow(0) .getLastCellNum();
	}

	/**
	 * Function: getSheetCount
	 * Description: Get Sheet Count of the workBook
	 *
	 * @author N6q2052
	 * @return mt
	 * @category Excel Test Data management
	 * */
	public int getSheetCount(){
		return workBook.getNumberOfSheets();
	}

	/** 
	 * Function: getSheetNuinber
	 * Description: Get Sheet Number of the workBook
	 *
	 * @author N642052
	 * @return tnt
	 * @param sheetName
	 * @category Excel Test Data management
	 * */
	public int getSheetNumber(String sheetName){
		int i, shtCnt;
		shtCnt = getSheetCount();
		for (i = 1; i <= shtCnt; i++){
			if (workBook.getSheetAt(i - 1).getSheetName().toString().equalsIgnoreCase(sheetName)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Function: getCclumnNuinber
	 * Description: Get Column Number by column name in sheet
	 *
	 * @author N642052
	 * @return mt
	 * @param columnName
	 * @category Excel Test Data management
	 * */
	public int getColumnNumber(String columnName){
		int i = 0;
		Row row = sheet.getRow(0);
		Iterator<Cell> colIterator = row.cellIterator();
		while (colIterator.hasNext()){
			Cell cell = colIterator.next();
			if (cell.getStringCellValue() .toString().equalsIgnoreCase(columnName)){
				return i + 1;
			}
			i=i+1;
		}
		return -1;
	}

	/**
	 * Function: getColumnNumber
	 * Description: Get Column Number by column name in sheet
	 *
	 * @author N642052
	 * @return tnt
	 * @param columnName, row<varargs>
	 * @category Excel Test Data management
	 * */
	public int getColumnNumber(String columnName, int... row){
		int i=0;

		Row actRow = null;
		if (row.length > 0){
			actRow = sheet.getRow(row[0] - 1);
		} else{
			actRow = sheet.getRow(0);
		}

		Iterator<Cell> colIterator = actRow.cellIterator();
		while (colIterator.hasNext()){
			Cell cell = colIterator.next();
			if (cell.getStringCellValue().toString().equalsIgnoreCase(columnName)){
				return i + 1;
			}
			i=i+1;
		}
		return - 1;
	}


	/**
	 * Function: addNewRow
	 * Description: Add New Row to sheet in workBook
	 * author N642052
	 * @return Row
	 * @par rowNo
	 * @category Excel Test Data management
	 * @throws IOException
	 */
	public Row addNewRow(int rowNo) throws IOException {
		boolean op = false;
		Row row = sheet.createRow(rowNo - 1);
		Cell cell = row.createCell(0);
		cell.setCellValue("New Row");
		if (row != null){
			op = true;
		}
		;
		/*
		 * if (inputStream!ónull){ inputstreaxn.close(); ) outputStream = new
		 * FileOutputStreaxn(file); workBook.write (outputStream);
		 * outputStreami.flush(); outputStream.close();
		 */

		if (op){
			return row;
		} else{
			return null;
		}
	}

	/**
	 * Function: addNewColumn
	 * Description: Add New Column to sheet in workBook
	 *
	 * @author N642052
	 * @return Cell
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	public Cell addNewColumn() throws IOException {
		boolean op = false;
		DataTable dts = new DataTable(file, workBook, sheet);
		int cc = dts.getColumnCount();
		Cell cell = null;
		if (cc != -1){
			cell = sheet.getRow(0).createCell(cc);
		}
		if (cell != null){
			op = true;
		}
		if (inputStream != null){
			inputStream.close();
		}
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();

		if (op) {
			return cell;
		}else{
			return null;
		}
	}


	/** Function: addNewColumn
	 * Description: Add New Column to sheet in workBook
	 *
	 * @author N642052
	  @return Cell
			 k @param row
			  @category Excel Test Data management
			  @throws IOException
	 */
	public Cell addNewColumn(int row) throws IOException {
		boolean op = false;
		DataTable dts = new DataTable(file, workBook, sheet);
		int cc = dts.getColumnCount();
		Cell cell = null;
		if (cc == -1){
			cell = sheet.getRow(row).createCell(cc + 1);
		} else {
			cell = sheet.getRow(row).createCell(cc);
		}
		if (cell != null){
			op = true;
		}
		if (inputStream != null){
			inputStream.close();
		}
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		if (op){
			return cell;
		}else{
			return null;
		}
	}

	/**
	 * Function: setCellData
	 * Description: Set Cell Data in Excel sheet
	 *
	 * @author N642052
	 * @return void
	 * @param row, column, cellValue
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	public void setCellData(int row, int column, String cellValue) throws IOException{
		// inputStream = new FileInputStream(file)
		row = row - 1;
		column = column - 1;
		DataTable dts = new DataTable(file, workBook, sheet);
		Row actRow = sheet.getRow(row);
		if (actRow == null){
			// System.out.println("Added New Row in the Sheet and Set Cell Value");
			actRow = dts.addNewRow(row + 1);
		}

		Cell actCell = actRow.getCell(column);
		if (actCell == null){
			try{
				actCell = actRow.createCell(column);
			}catch (IllegalArgumentException e){
				e.printStackTrace();
				Log.info("IllegalArgumentException occured" + e.getMessage());
				System.out.println(column+ "column doesn't exist.exiting method");
				return;
			}
		}

		actCell.setCellType(Cell.CELL_TYPE_STRING);
		actCell.setCellValue(cellValue);

		if (inputStream != null){
			inputStream.close() ;
		}
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}




	/* Function: setcellData
	 * Description: Set Cell Data in Excel sheet
	 *
	 * @author N642052
	 * @return boolean
	 * @param row, String column, ceilValue
	 * @category Excel Test Data management
	 * @throws IOException
	 */
	public boolean setCellData(int row, String column, String cellValue) throws IOException{
		row = row - 1;
		DataTable dts = new DataTable(file, workBook, sheet);
		int colNum = dts.getColumnNumber(column);
		if (colNum != -1){
			colNum = colNum - 1;
		}else{
			System.out.println("Colusnn : " + column + " doesn't exist");
			return false;
		}

		Row actRow = sheet.getRow(row);
		if (actRow == null){
			System.out.println("Added New Row in the Sheet and Set Cell Value");
					actRow = dts.addNewRow(row);
		}
		Cell actCell = actRow.getCell(colNum);
		// if(actCellnull){
		// actCell = actRow.createCell(colNum);
		// )

		if (actCell == null){
			try {
				actCell = actRow.createCell(colNum);
			}catch (IllegalArgumentException e){
				e.printStackTrace();
				Log.info("I1lerza√rJrn:Exception occured" + e.getMessage());
				System.out.println(column + " column doesn't exist");
						return false;
			}
		}

		actCell.setCellType(Cell.CELL_TYPE_STRING);
		actCell.setCellValue(cellValue);
		if (inputStream != null){
			inputStream.close();
		}
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		return true;
	}

	/**
	 * Below function is getting some problem of saving next function sets
	 * */
	/**
	 * Function: setCellData
	 * Description: Set Cell Data in Excel sheet
	 *
	 * @author N642052
	 * @return void
	 * @param rowCellText, column, targetColumn, ceilValue
	 * @categcry Excel Test Data management
	 * @throws IOException
	 * */
	public void setCellData(String rowCellText, int column, int trgtColumn, String cellValue) throws IOException {
		DataTable dts = new DataTable(file, workBook, sheet);
		Row actRow = dts.getRow(rowCellText, column);

		if (actRow == null) {
			System.out.println("No such cell value exists : " + rowCellText + " in column : " + column);
			return;
		}
		Cell actCell = actRow.getCell(column - 1);
		if (actCell == null){
			System.out.println("No such Cell value exists : " + rowCellText + " in column : " + column);
			return;
			// actCell = actRow.createCell(trgtColumnó1);
		}

		Cell trgtCell = actRow.getCell(trgtColumn - 1);
		if (trgtCell == null){
			try {
				trgtCell = actRow.createCell(trgtColumn - 1);
			}catch (IllegalArgumentException e){
				e.printStackTrace();
				Log.info("IZZe::azArgunientException occured" + e.getMessage());
				System.out.println("column doesn't exist");
				return;
			}

			// trgtCell.setCellType (Cell.CELL TYPE STRING);
			trgtCell.setCellValue(cellValue);

		}else{
			trgtCell.setCellType(Cell.CELL_TYPE_STRING);
			trgtCell.setCellValue(cellValue) ;
		}

		if (inputStream != null){
			inputStream.close();
		}
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		// return true;
	}






	/**
	 * Function: setRowData
	 * Description: Set Row Data in Excel sheet
	 *
	 * @author N642052
	 * @return void
	 * @param row, rowData<Map>
	 * @category Excel Test Data management
	 * @throws IOException
	 * */
	@SuppressWarnings ("rawtypes")
	public void setRowData(int row, Map<String, String> rowData) throws IOException {
		DataTable dts = new DataTable(file, workBook, sheet);
		Row actRow = dts.getRow(row);
		Cell cell = null;
		Iterator entries = rowData.entrySet() .iterator();
		while (entries.hasNext()){
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			int colNum = dts.getColumnNumber(key.toString());
			if (colNum != -1){
				cell = actRow.getCell(colNum);
				if (cell == null){
					cell = actRow.createCell(colNum);
					cell.setCellValue(value.toString());
				}else{
					cell.setCellValue(value.toString());
				}
				// actRow.getCell (colNum) .setCellValue(va1ue.toString);
				// actRcw.getCell(cclNum) .setCellValue("Hello");
			}else{
				System.out.println("No such column exists in table");
			}
		}
		if (inputStream != null){
			inputStream.close();
		}
		outputStream = new FileOutputStream(file);
		workBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/* Function: setRowData
		1400 * Description: Set Row Data in Excel sheet
		1401 *
		1402 * @author N642052
		1403 * @return void
		1404 * @param rowCellText, rowData<Map>
		1405 * @category Excel Test Data management
		1406 * @throws IOException
		1407 * */
	 @SuppressWarnings ("unused")
	public void setRowData(String rowCellText, Map<String, String> rowData) throws IOException {
		 DataTable dts = new DataTable(file, workBook, sheet);
		 Row actRow = dts.getRow(rowCellText);
		
		 Cell cell = null;
		
		 Iterator<Entry<String, String>> entries = rowData.entrySet().iterator();
		 while (entries.hasNext()){
			 Entry<?, ?> thisEntry = (Entry<?, ?>) entries.next();
			 Object key = thisEntry.getKey();
				 Object value = thisEntry.getValue();
				 int colNum = dts.getColumnNumber(key.toString());
				 if (colNum != -1) {
						 dts.setCellData(rowCellText, 1, colNum, value.toString());
				 cell = actRow.getCell(colNum);

				 /*
		 * if(cellnull){ cell = actRow.createCell(colNum);
		 * cell.setCellValue(value.tc5tring); )else{
		 * cell.setCellValue(value.toString); )
		 */
				 // actRow.getCell(colNum) .setCellValue(value.tc5tring);
				 cell = null;
				 }else{
					 System.out.println("Nc Column exists with name : " + key.toString());
				 }
		 }
			 if (inputStream == null){
				 inputStream.close();
			 }
				 outputStream = new FileOutputStream(file);
			 workBook.write(outputStream);
			 outputStream.flush();
			 outputStream.close();
	 }


			}
