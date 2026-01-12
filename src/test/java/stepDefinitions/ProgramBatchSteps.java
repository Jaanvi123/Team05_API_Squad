package stepDefinitions;

import requests.ProgramBatchRequests;

import java.io.IOException;
import java.io.InvalidClassException;
import java.text.ParseException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

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

	    @Given("Admin creates POST Request  with valid data in requestBody for {string}")
	    public void admin_creates_post_request_with_valid_data_in_request_body_for(String scenario)
	    		throws IOException, InvalidClassException, ParseException, InvalidFormatException{
	    	batchrequest.createBatch(scenario);
	        requestSpec = batchrequest.buildRequest(requestSpec);
	    }

	    @When("Admin sends HTTPS Request with endpoint")
	    public void admin_sends_https_request_with_endpoint() {
	    	 response = batchrequest.sendRequest(requestSpec);
	    }

	    @Then("Admin receives StatusCode for batch with statusText")
	    public void admin_receives_status_code_for_batch_with_status_text(String scenario) {
	    	System.out.println(response);
	        if (response == null) {
	            throw new AssertionError("Response is null. API call might have failed.");
	      }
	        
	        /*************************status code validation********************************/
			int actualStatusCode = response.getStatusCode();
			Assert.assertEquals(actualStatusCode, batchrequest.getStatusCode(), "Status code does not match!");
			
			/*************************status Text validation********************************/
			String expectedStatusText = batchrequest.getStatusText();
			CommonUtils.validateResponseMessage(expectedStatusText,actualStatusCode,scenario,response);
			
			/*************************saving and validating response body for post/put request********************************/
//			if(!scenario.contains("Get") && !scenario.contains("Delete") ) {
//				if(actualStatusCode ==200 || actualStatusCode ==201)
//				{
//					batchrequest.saveResponseBody(response);
//					batchrequest.validateProgramResponseBodyDetails(response);
//				}
//			}
	    }

}
