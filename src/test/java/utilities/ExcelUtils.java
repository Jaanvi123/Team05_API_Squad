package utilities;

import java.io.FileInputStream;
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

}
