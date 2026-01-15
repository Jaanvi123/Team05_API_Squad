package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.SkillMasterPojo;
import utilities.CommonUtils;
import utilities.ExcelUtils;
import utilities.TokenManager;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SkillMasterRequests {

    private final String baseUrl = CommonUtils.baseURI;
    private final String bearerToken = TokenManager.getToken();

    public SkillMasterRequests() {
        RestAssured.baseURI = baseUrl;
    }

    private RequestSpecification baseRequest(String contentType) {
        return given()
                .header("Authorization", "Bearer " + bearerToken)
                .header("Content-Type", contentType)
                .header("Accept", "application/json");
    }

    public Response createSkill(String endpoint, SkillMasterPojo payload, String contentType) {
        return baseRequest(contentType)
                .basePath(endpoint)
                .body(payload)
                .log().all()
                .post()
                .then().log().all()
                .extract().response();
    }

    public Response getAllSkills(String endpoint, String contentType) {
        return baseRequest(contentType)
                .basePath(endpoint)
                .log().all()
                .get()
                .then().log().all()
                .extract().response();
    }

    public Response getSkillByName(String endpoint, String skillName, String contentType) {
        String resolvedEndpoint = endpoint.replace("{SkillName}", skillName);
        return baseRequest(contentType)
                .basePath(resolvedEndpoint)
                .log().all()
                .get()
                .then().log().all()
                .extract().response();
    }

    public Response updateSkillById(String endpoint, int skillId, SkillMasterPojo payload, String contentType) {
        String resolvedEndpoint = endpoint.replace("{Skillid}", String.valueOf(skillId));
        return baseRequest(contentType)
                .basePath(resolvedEndpoint)
                .body(payload)
                .log().all()
                .put()
                .then().log().all()
                .extract().response();
    }

    public Response deleteSkillById(String endpoint, int skillId, String contentType) {
        String resolvedEndpoint = endpoint.replace("{skillId}", String.valueOf(skillId));
        return baseRequest(contentType)
                .basePath(resolvedEndpoint)
                .log().all()
                .delete()
                .then().log().all()
                .extract().response();
    }
 // ========= // ⭐ MODULE-SPECIFIC EXCEL MAPPER (SkillMaster only) // ===============
    public static class ExcelMapper {
    	private static final String FILE = CommonUtils.filePath; 
    	private static final String SHEET = "SkillMaster"; 
    	// ---------------- READ ---------------- 
    	public static int getCreatedSkillId() {
    	    try {
    	        int rowIndex = ExcelUtils.getRowIndexByScenarioName(FILE, SHEET, "Create Skill");
    	        List<Map<String, String>> data = ExcelUtils.readExcelData(FILE, SHEET);
    	        return Integer.parseInt(data.get(rowIndex - 1).get("SkillId"));
    	    } catch (Exception e) {
    	        throw new RuntimeException("Failed to read SkillId from Excel", e);
    	    }
    	}

    	public static String getCreatedSkillName() {
    	    try {
    	        int rowIndex = ExcelUtils.getRowIndexByScenarioName(FILE, SHEET, "Create Skill");
    	        List<Map<String, String>> data = ExcelUtils.readExcelData(FILE, SHEET);
    	        return data.get(rowIndex - 1).get("SkillName");
    	    } catch (Exception e) {
    	        throw new RuntimeException("Failed to read SkillName from Excel", e);
    	    }
    	}

    	// ---------------- WRITE: POST RESPONSE ----------------
    	public static void writeCreateSkillResponse(Response response) {
    		int rowIndex = ExcelUtils.getRowIndexByScenarioName(FILE, SHEET, "Create Skill");
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 4, response.jsonPath().getString("skillId"));
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 5, response.jsonPath().getString("skillName")); 
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 6, response.jsonPath().getString("creationTime"));
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 7, response.jsonPath().getString("lastModTime")); 
    		} 
    	// ---------------- WRITE: PUT REQUEST ---------------- 
    	public static void writePutRequest(String updatedSkillName) { 
    		int rowIndex = ExcelUtils.getRowIndexByScenarioName(FILE, SHEET, "Update Skill"); 
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 5, updatedSkillName);
    		} 
    	// ---------------- WRITE: PUT RESPONSE ---------------- 
    	public static void writePutResponse(Response response) { 
    		int rowIndex = ExcelUtils.getRowIndexByScenarioName(FILE, SHEET, "Update Skill"); 
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 4, response.jsonPath().getString("skillId"));
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 5, response.jsonPath().getString("skillName"));
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 6, response.jsonPath().getString("creationTime"));
    		ExcelUtils.writeCell(FILE, SHEET, rowIndex, 7, response.jsonPath().getString("lastModTime"));
    		}
    }
}
