package requests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import api.commons.Commons;
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

    private static final String INVALID_Batch_ID = "405";
    private static final String INVALID_TOKEN = "njbsjkbfk";
    
    
    public RequestSpecification setAuth(){
        RestAssured.baseURI = endpoints.getString("baseUrl");
        return given()
                .header("Authorization", "Bearer " + TokenManager.getToken());
    }
    
    
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
        }
    
    
    
    public RequestSpecification buildRequest(RequestSpecification requestSpec){

        if (requestSpec ==null) {
            throw new IllegalStateException("RequestSpecification is not initialized.");

        }
        return requestSpec.contentType(currentRow.get("ContentType")).body(batch);
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
	
	
//	public void saveResponseBody(Response response) {
//		int programId = response.jsonPath().getInt("programId");
//		Commons.setProgramId(programId);
//		String programName = response.jsonPath().getString("programName");
//		Commons.setProgramName(programName);
//		//JSONschema response is same for both post and put requests
//		String schemaPath = endpoints.getString("createProgramSchemaPath");
//		CommonUtils.validateResponseSchema(response,schemaPath);
//	}
    
    
    
    
    

}
