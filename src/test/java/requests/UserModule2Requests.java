package requests;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.ProgramPayload;
import pojo.UserModule2Pojo;
import utilities.CommonUtils;
import utilities.TokenManager;

public class UserModule2Requests extends CommonUtils {
	    private List<Map<String, String>> excelData;
	    private Map<String, String> currentRow;
	    private Response response;
	    
	    
	    public void setCurrentRow(Map<String, String> row) {
	        this.currentRow = row;
	    }
	    
	    public RequestSpecification setAuth() {
	        RestAssured.baseURI = config.getString("baseUrl");
	        return given()
	                .header("Authorization", "Bearer " + TokenManager.getToken());
	    }
	    
	    public RequestSpecification buildRequest(RequestSpecification requestSpec, Object requestBody) {
	        if (requestSpec == null) {
	            throw new IllegalStateException("RequestSpecification is not initialized.");
	        }
	       
	        return requestSpec
	                .contentType(currentRow.get("ContentType"))
	                .body(requestBody);  
	    }
	    
	    public Response sendRequest(RequestSpecification requestSpec) {
	        if (currentRow == null) {
	            throw new IllegalStateException("Current row data not set.");
	        }
	        String endpoint = currentRow.get("EndPoint");
	        response = CommonUtils.getResponse(requestSpec, endpoint);
	        return response;
	    }
	    
	    public int getStatusCode() {
	        String statusCodeStr = currentRow.get("StatusCode");
	        return Integer.parseInt(statusCodeStr.replace(".0", "")); 
	    }
	    
	    public String getStatusText() {
	        String scenarioName = currentRow.get("ScenarioName");
	            if (!scenarioName.equalsIgnoreCase("Invalid Endpoint") &&
	            !scenarioName.equalsIgnoreCase("Mandatory") &&
	            !scenarioName.equalsIgnoreCase("Full Details")) {
	            return currentRow.get("StatusText");
	        }
	        return null;
	    }
	}