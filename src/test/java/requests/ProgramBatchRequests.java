package requests;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import commons.Commons;
import utilities.TokenManager;
import payload.ProgramBatchPayload;
import pojo.ProgramBatchPojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.CommonUtils;


public class ProgramBatchRequests extends CommonUtils {
	
	private RequestSpecification requestSpec;
    private List<Map<String, String>> excelData;
    private Map<String, String> currentRow;
    private ProgramBatchPojo batch;
    private Response response;
   // private String token ="";
    private static final String INVALID_Batch_ID = "405";
    private static final String INVALID_TOKEN = "njbsjkbfk";
    public RequestSpecification setAuth(){
    	System.out.println("Base URI is: "+endpoints.getString("baseUrl")+" and Token is: "+TokenManager.getToken());
        RestAssured.baseURI = endpoints.getString("baseUrl");
        return given()
                .header("Authorization", "Bearer " + TokenManager.getToken());
    }
    // Method for scenarios that NEED batch object (POST/PUT)
    public void createBatch(String scenario)
            throws IOException, InvalidFormatException, ParseException{
            Map<String, Object> batchDetails =new ProgramBatchPayload().getDataFromExcel(scenario);
            if(batchDetails !=null) {
               if(batchDetails.get("batch") !=null) {
                this.batch = (ProgramBatchPojo)  batchDetails.get("batch");
               }
            if(batchDetails.get("currentRow") !=null) {
                this.currentRow = (Map<String, String>) batchDetails.get("currentRow");
               }
            }
         // Validate batch was created
            if (this.batch == null) {
                throw new RuntimeException("Batch object is null for scenario: " + scenario);
            }
        }
    // Method for scenarios that DON'T need batch object (GET/DELETE)
    public void loadScenarioData(String scenario)
            throws IOException, InvalidFormatException, ParseException {
        Map<String, Object> batchDetails = new ProgramBatchPayload().getDataFromExcel(scenario);
        if (batchDetails != null && batchDetails.get("currentRow") != null) {
            this.currentRow = (Map<String, String>) batchDetails.get("currentRow");
        }
    }
    //-----------------Build Requests-----------------
      public RequestSpecification buildRequest(RequestSpecification requestSpec){
	 
    	  if (requestSpec == null) {
    	        throw new IllegalStateException("RequestSpecification is not initialized.");
    	    }
    	  if (currentRow == null) {
              throw new IllegalStateException("currentRow is not initialized. Call createBatch() or loadScenarioData() first.");
          }
    	   
    	    String scenarioName = currentRow.get("ScenarioName");
    	   
    	    // Handle authentication
    	    if (scenarioName.contains("NoAuth")) {
    	        return given();
    	    } else if (scenarioName.contains("InvalidToken")) {
    	        return given()
    	                .header("Authorization", "Bearer " + INVALID_TOKEN);
    	    } else if (scenarioName.contains("InvalidBaseURL")) {
    	        RestAssured.baseURI = endpoints.getString("invalidBaseUrl");
    	        return given()
    	                .header("Authorization", "Bearer " + TokenManager.getToken());
    	    }
    	   
    	    // For GET/DELETE operations, don't set Content-Type
    	    boolean isGetOrDeleteOperation = scenarioName.contains("GetBatch") ||
    	                                     scenarioName.contains("DeleteBatch");
    	   if (isGetOrDeleteOperation) {
    	        return requestSpec;
    	    }
    	   
    	    // For POST/PUT operations
    	    String contentType = currentRow.get("ContentType");
    	    if (contentType != null && !contentType.trim().isEmpty()) {
    	        requestSpec = requestSpec.contentType(contentType);
    	    }
    	   
    	    // Conditionally add request body for POST/PUT operations
    	    if (!scenarioName.contains("WithoutRequestBody") &&
    	        !scenarioName.contains("Get") &&
    	        !scenarioName.contains("Delete")) {
    	    	
    	    	requestSpec.body(batch);    	    
    	        }
    	   
    	    return requestSpec;
   }
    public Response sendRequest(RequestSpecification requestSpec) {
    	String endpoint = currentRow.get("EndPoint");
        // Replace placeholder with actual programId
        if (endpoint != null && endpoint.contains("{programId}")) {
            String scenarioName = currentRow.get("ScenarioName");
            // For invalid endpoint scenarios
            if (scenarioName.contains("InvalidEndpoint") || scenarioName.contains("GetBatchByProgIdAlphaProgramId")||
                scenarioName.contains("GetBatchByProgIdinvalidEndpoint")) {
                endpoint = endpoint.replace("{programId}", "PROGRAM_123");
            }
            // For valid scenarios
            else if (scenarioName.contains("GetBatchByProgramId") ||
                     endpoint.contains("/batches/program/")) {
                int programId = Commons.getProgramId();
                endpoint = endpoint.replace("{programId}", String.valueOf(programId));
            }
        }
        response = CommonUtils.getResponse(requestSpec, endpoint);
        return response;
    }
    public int getStatusCode() {
		String expectedStatusCodeString = currentRow.get("StatusCode");
		int expectedStatusCode = (int) Double.parseDouble(expectedStatusCodeString); // Convert "201.0" to 201
		return expectedStatusCode;
	}
	public String getStatusText() {
			String expectedStatusText = currentRow.get("StatusText");
			return expectedStatusText;
	}
	
	
	public void saveResponseBody(Response response) {
		int batchId = response.jsonPath().getInt("batchId");
		Commons.setbatchId(batchId);
		String batchName = response.jsonPath().getString("batchName");
		Commons.setbatchName(batchName);
		//JSONschema response is same for both post and put requests
		String schemaPath = endpoints.getString("createBatchSchemaPath");
		CommonUtils.validateResponseSchema(response,schemaPath);
	}
	
	//Post/Put response body validations
	public void validateBatchResponseBodyDetails(Response response) {
			SoftAssert sa = new SoftAssert();
			String actualBatchName = response.jsonPath().getString("batchName");
			sa.assertEquals(actualBatchName, batch.getBatchName(), "Batch Name in response does not match!");
			String actualBatchDescription = response.jsonPath().getString("batchDescription");
			System.out.println(currentRow);
			sa.assertEquals(actualBatchDescription, batch.getBatchDescription(), "Batch Description in response does not match!");
			String actualBatchStatus = response.jsonPath().getString("batchStatus");
			sa.assertEquals(actualBatchStatus, batch.getBatchStatus(), "Batch Status in response does not match!");
				
			
			int actualbatchNoOfClasses = response.jsonPath().getInt("batchNoOfClasses");
			sa.assertEquals(actualbatchNoOfClasses, batch.getBatchNoOfClasses(), "No Of Batch Classes in response does not match!");
			
			sa.assertAll();
		}
	
	
	public void createGetRequestByProgramId(String scenario)
	        throws IOException, InvalidFormatException, ParseException {
	   
	    // Get data from Excel (same method can be reused)
	    Map<String, Object> batchDetails = new ProgramBatchPayload().getDataFromExcel(scenario);
	   
	    if (batchDetails != null) {
	        this.currentRow = (Map<String, String>) batchDetails.get("currentRow");
	       
	        // For GET requests, we might not need batch object
	        // But we need programId from currentRow
	        if (batchDetails.get("batch") != null) {
	            this.batch = (ProgramBatchPojo) batchDetails.get("batch");
	        }
	    }
	}
//	private String getProgramIdForScenario(String endpointType, String excelProgramId) {
//	   
//	    String scenarioName = currentRow.get("ScenarioName");
//	   
//	    if ("valid Program Id".equalsIgnoreCase(scenarioName.trim())) {
//	        // Use a valid existing programId from Commons
//	        return String.valueOf(Commons.getProgramId());
//	    } else if ("deleted program id".equalsIgnoreCase(scenarioName.trim())) {
//	        // Use a deleted programId (might need to store deleted IDs)
//	//        return String.valueOf(Commons.getDeletedProgramId());
//	    } else if ("invalid Program Id".equalsIgnoreCase(scenarioName.trim())) {
//	        // Use invalid programId from Excel
//	        return excelProgramId != null ? excelProgramId : "999999";
//	    } else if ("invalid endpoint".equalsIgnoreCase(scenarioName.trim())) {
//	        // For invalid endpoint, programId doesn't matter
//	        return "123"; // any dummy value
//	    } else {
//	        // Default: use from Excel
//	        return excelProgramId != null ? excelProgramId : String.valueOf(Commons.getProgramId());
//	    }
//	}
//	
	// Add validation for GET response
	public void validateGetBatchByProgramIdResponse(Response response) {
	    SoftAssert sa = new SoftAssert();
	    String scenarioName = currentRow.get("ScenarioName");
	   
	    if ("valid Program Id".equalsIgnoreCase(scenarioName.trim())) {
	        // Validate response contains array of batches
	        List<Map<String, Object>> batches = response.jsonPath().getList("$");
	        sa.assertNotNull(batches, "Response should contain batches array");
	       
	        // If there are batches, validate their structure
	        if (batches != null && !batches.isEmpty()) {
	            Map<String, Object> firstBatch = batches.get(0);
	            sa.assertTrue(firstBatch.containsKey("batchId"), "Batch should have batchId");
	            sa.assertTrue(firstBatch.containsKey("batchName"), "Batch should have batchName");
	            sa.assertTrue(firstBatch.containsKey("programId"), "Batch should have programId");
	           
	            // Validate programId matches
	            int responseProgramId = (int) firstBatch.get("programId");
	            int expectedProgramId = Integer.parseInt(currentRow.get("programId"));
	            sa.assertEquals(responseProgramId, expectedProgramId, "Program ID should match");
	        }
	    }
	   
	    sa.assertAll();
	}

}
