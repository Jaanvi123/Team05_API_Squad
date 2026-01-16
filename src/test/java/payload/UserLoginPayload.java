package payload;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import pojo.UserLoginPojo;
import utilities.ExcelUtils;


public class UserLoginPayload {
	
	// Sheet name must match the tab name in your Excel file
    private static final String SHEET_NAME = "Login";
    // File path from your config/logs
    private static final String FILE_PATH = "src/test/resources/TestData/Team05-APISquadTestData.xlsx";
    public static UserLoginPojo getLoginPayload(String scenario) throws IOException {
        // Use trim() to handle any accidental spaces in the feature file scenario name
        if (scenario != null && scenario.trim().equalsIgnoreCase("null body")) {
            return null;
        }
        Map<String, String> data = getRowData(scenario);
        // Mapping to the exact Excel headers: "EmailId" and "Password"
        return new UserLoginPojo(
                data.get("EmailId"),
                data.get("Password")
        );
    }
    private static Map<String, String> getRowData(String scenario) throws IOException {
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(FILE_PATH, SHEET_NAME);
        return excelData.stream()
                .filter(row -> {
                    // Extracting the correct column header "ScenarioName"
                    String excelScenario = row.get("ScenarioName");
                    // Use trim() on both values to prevent "Scenario not found" errors due to hidden spaces
                    return excelScenario != null && excelScenario.trim().equalsIgnoreCase(scenario.trim());
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Scenario not found in Excel: " + scenario));
    }
}


