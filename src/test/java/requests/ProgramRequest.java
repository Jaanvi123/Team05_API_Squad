package requests;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;
import java.text.ParseException;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import utilities.CommonUtils;
import utilities.TokenManager;

import commons.Commons;
import payload.ProgramPayload;
import pojo.ProgramPojo;
import io.restassured.RestAssured;
import java.util.ResourceBundle;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramRequest extends CommonUtils{

	private Response response;
	private static Map<String, String> currentRow;
	private ProgramPojo programPojo;
	private static final String INVALID_PROGRAM_ID = "goi";
	private static final int INVALID_PROGRAM_NAME = 4567;
	private static final String INVALID_TOKEN = "jbnsjokfi";
	
	public RequestSpecification setAuth() {
		RestAssured.baseURI = config.getString("baseUrl");
		return given()
				.header("Authorization", "Bearer " + TokenManager.getToken());
	}

	public  void createProgram(String scenario) 
			throws IOException, InvalidFormatException, ParseException {

		Map<String, Object> programDetails = new ProgramPayload().getDataFromExcel(scenario);
		if(programDetails != null) {
			if(programDetails.get("programPojo") != null) {
				this.programPojo = (ProgramPojo) programDetails.get("programPojo");
			}
			if(programDetails.get("currentRow") != null) {
				this.currentRow =  (Map<String, String>) programDetails.get("currentRow");
			}
		}
	}

	public RequestSpecification buildRequest(RequestSpecification requestSpec) {
		if (requestSpec == null) {
			throw new IllegalStateException("RequestSpecification is not initialized.");
		}
		String scenarioName = currentRow.get("ScenarioName");
		if(scenarioName.contains("NoAuth")) {
			requestSpec = given();
		}
		else if(scenarioName.contains("InvalidToken")) {
			requestSpec = given()
					.header("Authorization", "Bearer " + INVALID_TOKEN);
		}
		else if(scenarioName.contains("InvalidBaseURI")) {
			RestAssured.baseURI = config.getString("invalidBaseUrl");
			return given()
					.header("Authorization", "Bearer " + TokenManager.getToken());
		}

		// Set content type from currentRow
		requestSpec.contentType(currentRow.get("ContentType"));
		// Conditionally add the request body
		if (!scenarioName.contains("WithoutRequestBody")
				&& !scenarioName.contains("Get")
				&& !scenarioName.contains("Delete")
				) 
		{
			requestSpec.body(programPojo);
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
		int programId = response.jsonPath().getInt("programId");
		Commons.setProgramId(programId);
		String programName = response.jsonPath().getString("programName");
		Commons.setProgramName(programName);
		//JSONschema response is same for both post and put requests
		String schemaPath = config.getString("createProgramSchemaPath");
		CommonUtils.validateResponseSchema(response,schemaPath);
	}
	
	
}
