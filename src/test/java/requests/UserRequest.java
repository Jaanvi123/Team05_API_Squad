package requests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import commons.Commons;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.ProgramPayload;
import payload.UserPayload;
import pojo.UserPojo;
import utilities.CommonUtils;
import utilities.TokenManager;

public class UserRequest extends CommonUtils {
	/private List<Map<String, String>> excelData;
	
    private Map<String, String> currentRow;
    private Response response;
    private UserPojo userPojo;
    private static final String INVALID_TOKEN = "jbnsjokfi";
    // ---------------- AUTH ----------------
    public RequestSpecification setAuth() {
        RestAssured.baseURI = CommonUtils.endpoints.getString("baseUrl");
        TokenManager.setToken("");
        return given()
                .header("Authorization", "Bearer " + TokenManager.getToken());
    }
    // ---------------- LOAD USER FROM EXCEL ----------------
    public void createUser(String scenario)
            throws IOException, InvalidFormatException, ParseException {
        Map<String, Object> userDetails = new UserPayload().getDataFromExcel(scenario);
        if (userDetails != null) {
            this.userPojo = (UserPojo) userDetails.get("userPojo");
            this.currentRow = (Map<String, String>) userDetails.get("currentRow");
        }
    }
    // ---------------- BUILD REQUEST ----------------
    public RequestSpecification buildRequest(RequestSpecification requestSpec) {
        if (requestSpec == null)
            throw new IllegalStateException("RequestSpecification is not initialized.");
        if (currentRow == null)
            throw new IllegalStateException("currentRow is null. Did you forget to call createUser()?");
        String scenarioName = currentRow.get("ScenarioName");
        // AUTH variations
        if (scenarioName.contains("NoAuth")) {
            requestSpec = given();
        } else if (scenarioName.contains("InvalidToken")) {
            requestSpec = given()
                    .header("Authorization", "Bearer " + INVALID_TOKEN);
        } else if (scenarioName.contains("InvalidBaseURI")) {
            RestAssured.baseURI = CommonUtils.endpoints.getString("invalidBaseUrl");
            requestSpec = given()
                    .header("Authorization", "Bearer " + TokenManager.getToken());
        }
        // Always set content type for LMS POST
        requestSpec.contentType(currentRow.get("ContentType"));
        // LMS RULE: POST must ALWAYS have a body
        boolean isPost = currentRow.get("Method").equalsIgnoreCase("POST");
        if (isPost && !scenarioName.contains("WithoutRequestBody")) {
            requestSpec.body(userPojo);
        }
        return requestSpec;
    }
    // ---------------- SEND REQUEST ----------------
    public Response sendRequest(RequestSpecification requestSpec) {
        String endpoint = currentRow.get("EndPoint");
        response = CommonUtils.getResponse(requestSpec, endpoint);
        return response;
    }
    // ---------------- STATUS CODE ----------------
    public int getStatusCode() {
        return (int) Double.parseDouble(currentRow.get("StatusCode"));
    }
    // ---------------- STATUS TEXT ----------------
    public String getStatusText() {
        String scenarioName = currentRow.get("ScenarioName");
        if (scenarioName.equalsIgnoreCase("Invalid Endpoint")
                || scenarioName.equalsIgnoreCase("Mandatory")
                || scenarioName.equalsIgnoreCase("Full Details")) {
            return null;
        }
        return currentRow.get("StatusText");
    }
    // ---------------- SAVE RESPONSE ----------------
    public void saveResponseBody(Response response) {
        String userId = response.jsonPath().getString("userId");
        Commons.setuserId(userId);
    }
}