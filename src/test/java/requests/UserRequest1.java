package requests;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Commons;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.UserPayload1;
import pojo.UserPojo1;
import utilities.CommonUtils;
import utilities.TokenManager;

public class UserRequest1 extends CommonUtils {
	
    private Map<String, String> currentRow;
    private Response response;
    private UserPojo1 userPojo;
    private static final String INVALID_TOKEN = "jbnsjokfi";
    public RequestSpecification setAuth() {
        RestAssured.baseURI = CommonUtils.endpoints.getString("baseUrl");
        TokenManager.setToken("");
        return given().header("Authorization", "Bearer " + TokenManager.getToken());
    }
    public void createUser(String scenario) throws IOException, InvalidFormatException, ParseException {
        Map<String, Object> userDetails = new UserPayload1().getDataFromExcel(scenario);
        if (userDetails != null) {
            this.userPojo = (UserPojo1) userDetails.get("userPojo");
            this.currentRow = (Map<String, String>) userDetails.get("currentRow");
        }
    }
    public RequestSpecification buildRequest(RequestSpecification requestSpec) {
        if (requestSpec == null) throw new IllegalStateException("RequestSpecification is not initialized.");
        if (currentRow == null) throw new IllegalStateException("currentRow is null. Call createUser() first.");
        String scenarioName = currentRow.get("ScenarioName");
        // AUTH variations
        if (scenarioName.contains("NoAuth")) {
            requestSpec = given();
        } else if (scenarioName.contains("InvalidToken")) {
            requestSpec = given().header("Authorization", "Bearer " + INVALID_TOKEN);
        } else if (scenarioName.contains("InvalidBaseURI")) {
            RestAssured.baseURI = CommonUtils.endpoints.getString("invalidBaseUrl");
            requestSpec = given().header("Authorization", "Bearer " + TokenManager.getToken());
        }
        // Content type
        requestSpec.contentType(currentRow.get("ContentType"));
        // CRITICAL FIX: Log request body BEFORE sending
        logRequestBody();
        // Always add body for POST/PUT (LMS requirement)
        if (userPojo != null && !scenarioName.contains("WithoutRequestBody")) {
            requestSpec.body(userPojo);
        }
     // Path parameters
        String roleId = currentRow.get("roleId");
        if (roleId != null && !roleId.trim().isEmpty()) {
            requestSpec.pathParam("roleId", roleId);
        }
        String userId = currentRow.get("userId");
        if (userId != null && !userId.trim().isEmpty()) {
            requestSpec.pathParam("userId", userId);
        }
        return requestSpec;
    }
    private void logRequestBody() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(userPojo);
           // LOGGER.info("=== SENDING REQUEST BODY ===\n{}\n========================", jsonBody);
        } catch (Exception e) {
           // LOGGER.error("Failed to log request body", e);
        }
    }
    public Response sendRequest(RequestSpecification requestSpec) {
        String endpoint = currentRow.get("EndPoint");
        response = CommonUtils.getResponse(requestSpec, endpoint);
        return response;
    }
    public int getStatusCode() {
        return (int) Double.parseDouble(currentRow.get("StatusCode"));
    }
    public String getStatusText() {
        String scenarioName = currentRow.get("ScenarioName");
        if (scenarioName.equalsIgnoreCase("Invalid Endpoint") ||
            scenarioName.equalsIgnoreCase("Mandatory") ||
            scenarioName.equalsIgnoreCase("Full Details")) {
            return null;
        }
        return currentRow.get("StatusText");
    }
    public void saveResponseBody(Response response) {
        String userId = response.jsonPath().getString("userId");
        Commons.setuserId(userId);
    }
    public Map<String, Object> getUsersByRoleIdData(String scenario, UserPojo1 userPojo) throws Exception {
        UserPayload1 userPayload = new UserPayload1();
        Map<String, Object> testData = userPayload.getUsersByRoleData(scenario, userPojo);
        String roleId = (String) testData.get("roleId");
        if (roleId == null || roleId.trim().isEmpty()) {
            throw new IllegalArgumentException("roleId cannot be null or empty for scenario: " + scenario);
        }
        testData.put("endpoint", "/users/role/" + roleId);
        return testData;
    }
    public Map<String, Object> getUserDetailsData(String scenario, UserPojo1 userPojo) throws Exception {
        UserPayload1 userPayload = new UserPayload1();
        Map<String, Object> testData = userPayload.getUserDetails(scenario, userPojo);
        String userId = (String) testData.get("userId");
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty for scenario: " + scenario);
        }
        String endpoint = userId.contains("id") ? "/users/details/" + userId : "/users/" + userId;
        testData.put("endpoint", endpoint);
        testData.put("userId", userId);
        return testData;
    }
}


