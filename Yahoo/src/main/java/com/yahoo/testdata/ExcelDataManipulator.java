package com.yahoo.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.drivers.Driver;

public class ExcelDataManipulator extends Driver {

	public static File file;
	public static FileInputStream fis;
	public static XSSFWorkbook excelWBook;
	public static XSSFSheet excelWSheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static String strTestCaseName;
	public static String strTestClassName;
	public static DataFormatter formatter = new DataFormatter();
	public static HashMap<String, String> testDataHMap = new HashMap<String, String>();
	public static HashMap<String, HashMap<String, String>> testCaseHMap = new HashMap<String, HashMap<String, String>>();
	public static HashMap<String, HashMap<String, HashMap<String, String>>> testClassHMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();

	// This method stores all the data present in TestData sheet in a 3-level nested
	// Hashmap object
	public static void storeDataToHashMapFromExcel() {

		int sheetCount = 0;

		try {
			file = new File(prop.getProperty("testDataFilePath"));
			fis = new FileInputStream(file);
			excelWBook = new XSSFWorkbook(fis);

			// retrieving the number of sheets having data in testdata file
			sheetCount = excelWBook.getNumberOfSheets();
			log.info("Storing data into nested Hashmap");
			for (int k = 1; k <= sheetCount; k++) {
				int rowCount, columnCount = 0;
				testCaseHMap.clear();

				// Reading first sheet in the TestData document
				excelWSheet = excelWBook.getSheetAt(k - 1);
				rowCount = excelWSheet.getLastRowNum();
				row = excelWSheet.getRow(0);
				columnCount = row.getLastCellNum();

				// Read Sheet data
				for (int i = 1; i <= rowCount; i++) {
					String strTestCaseName = null;
					for (int j = 1; j <= columnCount; j++) {
						String strValue, strHeader = null;
						// Column header
						cell = excelWSheet.getRow(0).getCell(j);
						strHeader = formatter.formatCellValue(cell);
						// Column value
						cell = excelWSheet.getRow(i).getCell(j);
						strValue = formatter.formatCellValue(cell);

						// storing test data into testDataHMap in inner most Hashmap
						if (!strHeader.equals(null) || !strHeader.isEmpty()) {
							if (!strValue.isEmpty()) {
								testDataHMap.put(strHeader, strValue);
							}
						}
					}

					// storing Test Data [Column Header and Data] pairs against TestCases
					cell = excelWSheet.getRow(i).getCell(0);
					strTestCaseName = formatter.formatCellValue(cell);
					testCaseHMap.put(strTestCaseName, new HashMap<String, String>(testDataHMap));
					testDataHMap.clear();

				}
				// storing Test cases of each sheet against the sheet name
				strTestClassName = excelWBook.getSheetName(k - 1);
				if (!strTestClassName.equals(null) || !testDataHMap.isEmpty()) {
					testClassHMap.put(strTestClassName, new HashMap<String, HashMap<String, String>>(testCaseHMap));
				}
			}

			excelWBook.close();
			log.info("Storing data to nested Hashmap SUCCESSFUL!");
		} catch (FileNotFoundException e) {
			log.error("Failed to store data into hashmap due missing file");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Failed to store data into hashmap");
			e.printStackTrace();
		}

	}

	// Retrieving data from the hashmap by providing Testcase name and column header
	public static String getTestData(String tcName, String strHeader) {
		String strValue = "";
		if (!testClassHMap.isEmpty()) {
			strValue = testClassHMap.get(ApplicationConstants.testClassName).get(tcName).get(strHeader);
		} else {
			log.info("testClassHMap Hashmap is empty");
		}

		return strValue;
	}

}