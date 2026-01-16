package stepDefinitions;

import requests.ProgramBatchRequests;
import java.io.IOException;
import java.io.InvalidClassException;
import java.text.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utilities.CommonUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramBatchSteps {
	
	ProgramBatchRequests batchrequest = new ProgramBatchRequests();
    private RequestSpecification requestSpec;
    private Response response;
   
   
    @Given("Admin set Authorization for batch")
    public void admin_set_authorization_for_batch() {
    	requestSpec = batchrequest.setAuth();
    }
    @Given("Admin creates Batch POST Request  with valid data in requestBody for {string}")
    public void admin_creates_batch_post_request_with_valid_data_in_request_body_for(String scenario)
	    		throws IOException, InvalidClassException, ParseException, InvalidFormatException{
    	batchrequest.createBatch(scenario);
        requestSpec = batchrequest.buildRequest(requestSpec);
    }
    @When("Admin sends Batch HTTPS Request with endpoint")
    public void admin_sends_https_request_with_endpoint() {
    	 response = batchrequest.sendRequest(requestSpec);
    }
	   
    @Then("Admin receives StatusCode for batch with statusText {string}")
    public void admin_receives_status_code_for_batch_with_status_text(String scenario) {
        System.out.println(response.asString());
       
        if (response == null) {
            throw new AssertionError("Response is null. API call might have failed.");
        }
       
        SoftAssert sa = new SoftAssert();
       
        /*************************status code validation********************************/
        int actualStatusCode = response.getStatusCode();
        System.out.println("Actual Status code is: " + actualStatusCode);
        System.out.println("Expected Status code is: " + batchrequest.getStatusCode());
        sa.assertEquals(actualStatusCode, batchrequest.getStatusCode(), "Status code does not match!");
       
     /*************************status Text validation********************************/
        System.out.println("Status Line: " + response.getStatusLine());
        String expectedStatusText = batchrequest.getStatusText();
        if (response.getStatusLine().contains(expectedStatusText)) {
            System.out.println("Status line contains: " + expectedStatusText);
            sa.assertTrue(true);
        } else {
            sa.assertTrue(false);
            System.out.println("Status line does not contain '" + expectedStatusText + "'. Actual: " + response.getStatusLine());
        }
       
        /*************************Response validation based on scenario******************/
        if (scenario.contains("Get")) {
            // GET requests
            if (actualStatusCode == 200) {
                CommonUtils.validateGetResponseSchema(response, scenario);
                if (scenario.contains("valid Program Id")) {
                    batchrequest.validateGetBatchByProgramIdResponse(response);
                }
            }
        } else if (!scenario.contains("Delete")) {
            // POST/PUT requests
            if (actualStatusCode == 200 || actualStatusCode == 201) {
                batchrequest.saveResponseBody(response);
                batchrequest.validateBatchResponseBodyDetails(response);
            }
        }	     
        sa.assertAll();
    }
   
    	  
    @Given("Admin creates GET Request of Batch with valid or invalid Program Id for {string}")
    public void admin_creates_get_request_of_batch_with_valid_or_invalid_program_id_for(String scenario)  throws IOException, InvalidFormatException, ParseException {
    	batchrequest.loadScenarioData(scenario);
        requestSpec = batchrequest.buildRequest(requestSpec);
    }
   
   
    @When("Admin sends Batch HTTPS Request with {string} endpoint")
    public void admin_sends_batch_https_request_with_endpoint(String endpointType) {
    	response = batchrequest.sendRequest(requestSpec);
        // endpointType can be used to determine if it's programId or regular endpoint
      //  response = batchrequest.sendGetRequestByProgramId(requestSpec, endpointType);
    }









}
