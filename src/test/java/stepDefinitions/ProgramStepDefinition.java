package stepDefinitions;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsString;
import java.io.IOException;
import java.text.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;


//import org.testng.Assert;
import utilities.CommonUtils;
import requests.ProgramRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramStepDefinition {

	ProgramRequest programrequest = new ProgramRequest();
	private RequestSpecification requestSpec;
	private Response response; 
	
	@Given("Admin set Authorization to Bearer token")
	public void admin_set_authorization_to_bearer_token() {
		requestSpec = programrequest.setAuth();
		
	}

	@Given("Admin creates Program for the LMS with request body {string}")
	public void admin_creates_program_for_the_lms_with_request_body(String scenario) 
			throws IOException, InvalidFormatException, ParseException {
			programrequest.createProgram(scenario);
			requestSpec = programrequest.buildRequest(requestSpec);
	}

	@When("Admin sends Post HTTPS Request and request Body with endpoint for Program")
	public void admin_sends_post_https_request_and_request_body_with_endpoint_for_program() {
			response = programrequest.sendRequest(requestSpec);
	}

	@Then("Admin receives StatusCode with statusText {string} for Program")
	public void admin_receives_status_code_with_status_text_for_program(String string) {
		if (response == null) {
			throw new AssertionError("Response is null. API call might have failed.");
		}
	}

	@Given("Admin creates Get Request All Program for the LMS with request body {string}")
	public void admin_creates_get_request_all_program_for_the_lms_with_request_body(String scenario) 
	  throws InvalidFormatException, IOException, ParseException {
		programrequest.createProgram(scenario);
		requestSpec = programrequest.buildRequest(requestSpec);
	}
	
	@When("Admin sends Get HTTPS Request and request Body with {string} endpoint for Program")
	public void admin_sends_get_https_request_and_request_body_with_endpoint_for_program(String putEndpoint) {
		response = programrequest.sendPutRequest(requestSpec,putEndpoint);
	}
	
	@Then("Admin receives the StatusCode with statusText {string} for Program")
	public void admin_receives_the_status_code_with_status_text_for_program(String scenario) {
		if (response == null) {
			throw new AssertionError("Response is null. API call might have failed.");
		}
		/*************************status code validation********************************/
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, programrequest.getStatusCode(), "Status code does not match!");
		/*************************status Text validation********************************/
		String expectedStatusText = programrequest.getStatusText();
		CommonUtils.validateResponseMessage(expectedStatusText,actualStatusCode,scenario,response);
		/*************************saving and validating response body for post/put request********************************/
		if(!scenario.contains("Get") && !scenario.contains("Delete") ) {
			if(actualStatusCode ==200 || actualStatusCode ==201)
			{
				programrequest.saveResponseBody(response);
				programrequest.validateProgramResponseBodyDetails(response);
			}
		}
	}
	
	@Then("Admin receives All Programs with users {string} for Get request")
	public void admin_receives_all_programs_with_users_for_get_request(String string) {
		if(response.getStatusCode() == 200)
		{
			programrequest.validateGetProgramIDResponseBodyDetails(response);
		}
	}
}
