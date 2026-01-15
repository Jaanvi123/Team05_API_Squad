package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	  public static List<Map<String, String>> readExcelData(String filePath, String sheetName) throws IOException {
	        FileInputStream fis = new FileInputStream(filePath);
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheet(sheetName);

	        List<Map<String, String>> data = new ArrayList<>();
	        Row headerRow = sheet.getRow(0);
	        int rowCount = sheet.getPhysicalNumberOfRows();

	        for (int i = 1; i < rowCount; i++) { // Start from row 1, skipping the header row
	            Row currentRow = sheet.getRow(i);

	            if (currentRow == null || isRowEmpty(currentRow)) {
	                continue; // Skip empty rows
	            }

	            Map<String, String> rowData = new HashMap<>();
	            for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
	                String header = headerRow.getCell(j).getStringCellValue();
	                Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

	                rowData.put(header, cell.toString().trim()); // Handle empty cells gracefully
	            }

	            data.add(rowData);
	        }

	        workbook.close();
	        fis.close();
	        return data;
	    }

	    private static boolean isRowEmpty(Row row) {
	        for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
	            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	            if (cell != null && !cell.toString().trim().isEmpty()) {
	                return false; // Row contains data
	            }
	        }
	        return true; // Row is empty
	    }
	 // ---------------- NEW METHODS ADDED BELOW ---------------- /** * Write a value into a specific cell. */
	    public static void writeCell(String filePath, String sheetName, int rowIndex, int colIndex, String value) {
	    	try (FileInputStream fis = new FileInputStream(filePath);
	    			Workbook workbook = new XSSFWorkbook(fis))
	    	{ Sheet sheet = workbook.getSheet(sheetName); 
	    	Row row = sheet.getRow(rowIndex); if (row == null) { row = sheet.createRow(rowIndex);
	    	} 
	    	Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    	cell.setCellValue(value);
	    	try (FileOutputStream fos = new FileOutputStream(filePath))
	    	{ workbook.write(fos); 
	    	}
	    	} catch (IOException e)
	    	{ throw new RuntimeException("Error writing to Excel file", e);
	    	} 
	    	} 
	    /** * Find the row index for a given ScenarioName. */ 
	    public static int getRowIndexByScenarioName(String filePath, String sheetName, String scenarioName) 
	    { try (FileInputStream fis = new FileInputStream(filePath);
	    		Workbook workbook = new XSSFWorkbook(fis)) 
	    	{ Sheet sheet = workbook.getSheet(sheetName); 
	    	int lastRow = sheet.getLastRowNum();
	    	for (int i = 1; i <= lastRow; i++) 
	    	{ 
	    		// Skip header row 
	    		Row row = sheet.getRow(i); 
	    		if (row == null) continue; 
	    		Cell cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    		String cellValue = cell.toString().trim();
	    		if (cellValue.equalsIgnoreCase(scenarioName)){
	    			return i;
	    		} 
	    	} return -1; // Not found 
	    		} catch (IOException e) {
	    
	    {
	    	throw new RuntimeException("Error reading Excel file", e);
	    	} 
	    } 
	    }
}
