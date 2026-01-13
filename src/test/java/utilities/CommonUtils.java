package utilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
//import utilities.ExcelUtils;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonUtils {

	public static List<Map<String, String>> excelData;
	public static Map<String, String> currentRow;

	public static ResourceBundle endpoints = ResourceBundle.getBundle("config");
	public static String baseURI = endpoints.getString("baseUrl");
	public static String filePath = endpoints.getString("excelPath");
	

	public static Map<String, String> getCurrentRow(String scenario,String sheetName){
		try {
				excelData = ExcelUtils.readExcelData(filePath, sheetName);
			// Loop through the Excel data and compare each row's scenario with the passed scenario
			for (Map<String, String> row : excelData) {
				currentRow = row;
				String excelScenario = currentRow.get("ScenarioName");
				if (excelScenario.equalsIgnoreCase(scenario)) {
					return currentRow;
				}
			}throw new RuntimeException("Failed to find row for test case in Excel file: " + scenario);
		}catch (IOException e) {
			//LOGGER.error("Failed to read Excel file.", e);
			throw new RuntimeException("Failed to read Excel file.", e);
		}
	}
	
	
	public static Response getResponse(RequestSpecification requestSpec, String endpoint) {
		if (requestSpec == null || currentRow == null) {
			throw new IllegalStateException("Request or currentRow is not initialized.");
		}

		 String method = currentRow.get("Method");
	        //String endpoint = currentRow.get("EndPoint");
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
		if(expectedStatusText!=null)
		{
			if(!scenario.contains("InvalidID")
					&& !scenario.contains("NoAuth")
					&& !scenario.contains("InvalidToken")
					&& !scenario.contains("InvalidBaseURI")
					&& !scenario.contains("InvalidEndpoint")
					&& actualStatusCode!=200
					&& actualStatusCode!=201)
			{
				actualStatusMessage = response.jsonPath().getString("message");
				boolean actualSuccess = response.jsonPath().getBoolean("success");
				//return actualStatusMessage;
				assertThat("Status Text does not match!", actualStatusMessage, containsString(expectedStatusText));
			}
		}
		return actualStatusMessage;
	}
	
}
