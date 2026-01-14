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
	    private List<Map<String, String>> excelData;
	    private Map<String, String> currentRow;
	    private Response response;
	    private UserPojo userPojo;
	    private static final String INVALID_PROGRAM_ID = "goi";
		private static final int INVALID_PROGRAM_NAME = 4567;
		private static final String INVALID_TOKEN = "jbnsjokfi";
	    

		
	
			
		
		public RequestSpecification setAuth() {
			RestAssured.baseURI = CommonUtils.endpoints.getString("baseUrl");
			TokenManager.setToken("");
			return given()
					.header("Authorization", "Bearer " + TokenManager.getToken());
		}

		public  void createUser(String scenario) 
				throws IOException, InvalidFormatException, ParseException {

			Map<String, Object> UserDetails = new UserPayload().getDataFromExcel(scenario);
			if(UserDetails != null) {
				if(UserDetails.get("userPojo") != null) {
					this.userPojo = (UserPojo) UserDetails.get("userPojo");
				}
				if(UserDetails.get("currentRow") != null) {
					this.currentRow =  (Map<String, String>) UserDetails.get("currentRow");
				}
			}
		}  
	    
		public RequestSpecification buildRequest(RequestSpecification requestSpec) {
			if (requestSpec == null) {
				throw new IllegalStateException("RequestSpecification is not initialized.");
			}
			
			if (currentRow == null) { throw new IllegalStateException("currentRow is null. Did you forget to call createUser(scenario)?"); }
			String scenarioName = currentRow.get("ScenarioName");
			if(scenarioName.contains("NoAuth")) {
				requestSpec = given();
			}
			else if(scenarioName.contains("InvalidToken")) {
				requestSpec = given()
						.header("Authorization", "Bearer " + INVALID_TOKEN);
			}
			else if(scenarioName.contains("InvalidBaseURI")) {
				RestAssured.baseURI = CommonUtils.endpoints.getString("invalidBaseUrl");
				return given()
						.header("Authorization", "Bearer " + TokenManager.getToken());
			}

			// Set content type from currentRow
			requestSpec.contentType(currentRow.get("ContentType"));
			// Conditionally add the request body
			if (!scenarioName.contains("WithoutRequestBody")
					&& !scenarioName.contains("Post")
					&& !scenarioName.contains("Put")
					&& !scenarioName.contains("Get")
					&& !scenarioName.contains("Delete")
					) 
			{
				requestSpec.body(userPojo);
			}
			return requestSpec;
		}
		
	    
		public Response sendRequest(RequestSpecification requestSpec) {

			String endpoint = currentRow.get("EndPoint");
			response = CommonUtils.getResponse(requestSpec,endpoint);
			return response;
		}

		public int getStatusCode() {
			String expectedStatusCodeString = currentRow.get("StatusCode");
			int expectedStatusCode = (int) Double.parseDouble(expectedStatusCodeString); // Convert "201.0" to 201
			return expectedStatusCode;
		}

		public String getStatusText() {
			String scenarioName = currentRow.get("ScenarioName");
			if(!scenarioName.equalsIgnoreCase("Invalid Endpoint")&&
					(!scenarioName.equalsIgnoreCase("Mandatory"))
					&&(!scenarioName.equalsIgnoreCase("Full Details")))
			{
				String expectedStatusText = currentRow.get("StatusText");
				return expectedStatusText;
			}
			else
			{
				return null;
			}
		}
		
		public void saveResponseBody(Response response) {
			String userId = response.jsonPath().getString("userId");
			Commons.setuserId(userId);
//			JSONschema response is same for both post and put requests
//			String schemaPath = config.getString("createProgramSchemaPath");
//			CommonUtils.validateResponseSchema(response,schemaPath);
		}
		
}
