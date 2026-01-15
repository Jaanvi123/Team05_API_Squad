package utilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.SkillMasterPojo;

public class CommonUtils {

    public static List<Map<String, String>> excelData;
    public static Map<String, String> currentRow;

    public static ResourceBundle endpoints = ResourceBundle.getBundle("config");
    public static String baseURI = endpoints.getString("baseUrl");
    public static String filePath = endpoints.getString("excelPath");

    // ============================================================
    // EXISTING METHODS (unchanged)
    // ============================================================

    public static Map<String, String> getCurrentRow(String scenario, String sheetName) {
        try {
            excelData = ExcelUtils.readExcelData(filePath, sheetName);

            for (Map<String, String> row : excelData) {
                currentRow = row;
                String excelScenario = currentRow.get("ScenarioName");
                if (excelScenario.equalsIgnoreCase(scenario)) {
                    return currentRow;
                }
            }
            throw new RuntimeException("Failed to find row for test case in Excel file: " + scenario);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file.", e);
        }
    }

    public static Response getResponse(RequestSpecification requestSpec, String endpoint) {
        if (requestSpec == null || currentRow == null) {
            throw new IllegalStateException("Request or currentRow is not initialized.");
        }

        String method = currentRow.get("Method");
        Response response;

        switch (method.toUpperCase()) {
            case "POST":
                response = requestSpec.when().post(endpoint);
                break;
            case "GET":
                response = requestSpec.when().get(endpoint);
                break;
            case "PUT":
                response = requestSpec.when().put(endpoint);
                break;
            case "DELETE":
                response = requestSpec.when().delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        return response;
    }

    public static void validateResponseSchema(Response response, String schemaPath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    public static String validateResponseMessage(String expectedStatusText, int actualStatusCode, String scenario,
                                                 Response response) {
        String actualStatusMessage = null;

        if (expectedStatusText != null) {
            if (!scenario.contains("InvalidID")
                    && !scenario.contains("NoAuth")
                    && !scenario.contains("InvalidToken")
                    && !scenario.contains("InvalidBaseURI")
                    && !scenario.contains("InvalidEndpoint")
                    && actualStatusCode != 200
                    && actualStatusCode != 201) {

                actualStatusMessage = response.jsonPath().getString("message");
                boolean actualSuccess = response.jsonPath().getBoolean("success");

                assertThat("Status Text does not match!", actualStatusMessage, containsString(expectedStatusText));
            }
        }
        return actualStatusMessage;
    }

    // ============================================================
    // EXISTING POST SUPPORT
    // ============================================================

    public static SkillMasterPojo generateSkillMasterPayload() {
        SkillMasterPojo pojo = new SkillMasterPojo();

        String randomSkillName = "Skill_" + java.util.UUID.randomUUID().toString().substring(0, 8);
        String now = java.time.OffsetDateTime.now().toString();

        pojo.setSkillName(randomSkillName);
        pojo.setCreationTime(now);
        pojo.setLastModTime(now);

        return pojo;
    }

    public static void writeSkillMasterResponseToExcel(Response response, String sheetName) {
        try {
            int rowIndex = ExcelUtils.getRowIndexByScenarioName(filePath, sheetName, "Create Skill");

            String skillId = String.valueOf(response.jsonPath().getInt("skillId"));
            String skillName = response.jsonPath().getString("skillName");
            String creationTime = response.jsonPath().getString("creationTime");
            String lastModTime = response.jsonPath().getString("lastModTime");

            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 4, skillId);
            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 5, skillName);
            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 6, creationTime);
            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 7, lastModTime);

        } catch (Exception e) {
            throw new RuntimeException("Failed to write SkillMaster response to Excel", e);
        }
    }

    public static SkillMasterPojo readSkillMasterFromExcel(String sheetName) {
        try {
            int rowIndex = ExcelUtils.getRowIndexByScenarioName(filePath, sheetName, "Create Skill");
            List<Map<String, String>> sheetData = ExcelUtils.readExcelData(filePath, sheetName);
            Map<String, String> row = sheetData.get(rowIndex - 1);

            SkillMasterPojo pojo = new SkillMasterPojo();
            pojo.setSkillId(Integer.parseInt(row.get("SkillId")));
            pojo.setSkillName(row.get("SkillName"));
            pojo.setCreationTime(row.get("CreationTime"));
            pojo.setLastModTime(row.get("LastModTime"));

            return pojo;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read SkillMaster data from Excel", e);
        }
    }

    // ============================================================
    // ⭐ NEW METHODS FOR PUT SUPPORT ⭐
    // ============================================================

    // 1️⃣ Generate unique skill name for PUT
    public static String generateUniqueSkillNameForPut() {
        return "UpdatedSkill_" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }

    // 2️⃣ Read existing SkillId from Excel (from POST response row)
    public static int getExistingSkillIdFromExcel(String sheetName) {
        try {
            int rowIndex = ExcelUtils.getRowIndexByScenarioName(filePath, sheetName, "Create Skill");
            List<Map<String, String>> sheetData = ExcelUtils.readExcelData(filePath, sheetName);
            Map<String, String> row = sheetData.get(rowIndex - 1);

            return Integer.parseInt(row.get("SkillId"));

        } catch (Exception e) {
            throw new RuntimeException("Failed to read existing SkillId from Excel", e);
        }
    }

    // 3️⃣ Write PUT request data to Excel
    public static void writePutRequestToExcel(String sheetName, String updatedSkillName) {
        try {
            int rowIndex = ExcelUtils.getRowIndexByScenarioName(filePath, sheetName, "Update Skill");

            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 5, updatedSkillName);

        } catch (Exception e) {
            throw new RuntimeException("Failed to write PUT request to Excel", e);
        }
    }

    // 4️⃣ Write PUT response data to Excel
    public static void writePutResponseToExcel(Response response, String sheetName) {
        try {
            int rowIndex = ExcelUtils.getRowIndexByScenarioName(filePath, sheetName, "Update Skill");

            String skillId = String.valueOf(response.jsonPath().getInt("skillId"));
            String skillName = response.jsonPath().getString("skillName");
            String creationTime = response.jsonPath().getString("creationTime");
            String lastModTime = response.jsonPath().getString("lastModTime");

            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 4, skillId);
            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 5, skillName);
            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 6, creationTime);
            ExcelUtils.writeCell(filePath, sheetName, rowIndex, 7, lastModTime);

        } catch (Exception e) {
            throw new RuntimeException("Failed to write PUT response to Excel", e);
        }
    }
}
