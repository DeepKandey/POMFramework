/**
 * @author Deepak Rai
 */
package com.qa.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private String path = null;
	private FileInputStream fis = null;
	private FileOutputStream fileOut = null;
	private Workbook workBook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;

	public ExcelUtil(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workBook = WorkbookFactory.create(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@summary returns the row count in a sheet}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public int getRowCount(String sheetName) {
		return workBook.getSheet(sheetName).getPhysicalNumberOfRows();
	}

	/**
	 * {@summary returns the data from a cell}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {

		try {

			if (rowNum <= 0 || sheet.getRow(rowNum - 1) == null)
				return "";

			int colNum = -1;
			row = workBook.getSheet(sheetName).getRow(0);

			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					colNum = i;
					break;
				}
			}

			if (colNum == -1)
				return "";

			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();
			else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

				String cellText = String.valueOf(cell.getNumericCellValue());

				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}
				return cellText;
			} else if (cell.getCellType().name().equals("BLANK"))
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in excel workbook";
		}
	}

	/**
	 * {@summary returns the data from a cell}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public String getCellData(String sheetName, int colNum, int rowNum) {

		try {
			if (rowNum <= 0)
				return "";

			int index = workBook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workBook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();

			else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType().name().equals("BLANK"))
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	/**
	 * {@summary returns true if data is set successfully else false}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {

			if (rowNum <= 0)
				return false;

			int index = workBook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workBook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {

				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * {@summary returns true if sheet is created successfully else false}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public boolean addSheet(String sheetname) {

		try {
			workBook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * {@summary returns true if sheet is removed successfully else false if sheet
	 * does not exist}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public boolean removeSheet(String sheetName) {

		int index = workBook.getSheetIndex(sheetName);
		if (index == -1)
			return false;

		try {
			workBook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * {@summary returns true if column is created successfully}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public boolean addColumn(String sheetName, String colName) {

		try {

			int index = workBook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			CellStyle style = workBook.createCellStyle();
			sheet = workBook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * {@summary removes a column and all the contents}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public boolean removeColumn(String sheetName, int colNum) {

		try {
			if (!isSheetExist(sheetName))
				return false;

			sheet = workBook.getSheet(sheetName);
			CellStyle style = workBook.createCellStyle();

			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);

				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}

			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * {@summary returns true if sheets exists does not exist}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public boolean isSheetExist(String sheetName) {
		return workBook.getSheetIndex(sheetName) == -1 ? false : true;
	}

	/**
	 * {@summary returns number of columns in a sheet}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public int getColumnCount(String sheetName) {

		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		return workBook.getSheet(sheetName).getRow(0).getPhysicalNumberOfCells();
	}

	/**
	 * {@summary returns row number for the given column name and value}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 */
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}
}