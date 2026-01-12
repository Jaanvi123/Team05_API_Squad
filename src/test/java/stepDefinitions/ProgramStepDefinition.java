package stepDefinitions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import java.io.IOException;
import java.text.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
//import api.Utility.CommonUtils;
//import api.requests.ProgramRequests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ProgramStepDefinition {

//	ProgramRequests programrequest = new ProgramRequests();
	private RequestSpecification requestSpec;
	private Response response;
	
	/*@Given("Admin set Authorization to Bearer token")
	public void admin_set_authorization_to_bearer_token() {
		requestSpec = programrequest.setAuth();
	}*/

	@Given("Admin creates Post Request for the LMS with request body {string}")
	public void admin_creates_post_request_for_the_lms_with_request_body(String scenario) 
			throws IOException, InvalidFormatException, ParseException {
	//			programrequest.createProgram(scenario);
	//			requestSpec = programrequest.buildRequest(requestSpec);
	}

	@When("Admin sends Post HTTPS Request and request Body with endpoint")
	public void admin_sends_post_https_request_and_request_body_with_endpoint() {
	  
	}

	@Then("Admin receives StatusCode with statusText {string}")
	public void admin_receives_status_code_with_status_text(String string) {
	 
	}

	@Given("Admin creates Put Request for the LMS with request body {string}")
	public void admin_creates_put_request_for_the_lms_with_request_body(String string) {
	   
	}

	@When("Admin sends Put HTTPS Request and request Body with {string} programId endpoint")
	public void admin_sends_put_https_request_and_request_body_with_program_id_endpoint(String string) {
	  
	}

	@Then("Admin receives StatusCode with statusText {string} for Put request for programId")
	public void admin_receives_status_code_with_status_text_for_put_request_for_program_id(String string) {
	
	}

	@Given("Admin creates Put Request with request body {string}")
	public void admin_creates_put_request_with_request_body(String string) {
	  
	}

	@When("Admin sends Put HTTPS Request and request Body with {string} programName endpoint")
	public void admin_sends_put_https_request_and_request_body_with_program_name_endpoint(String string) {
	  
	}

	@Then("Admin receives StatusCode with statusText {string} for Put request by programName")
	public void admin_receives_status_code_with_status_text_for_put_request_by_program_name(String string) {
	  
	}

	@Given("Admin creates Get Request for the LMS with request body {string}")
	public void admin_creates_get_request_for_the_lms_with_request_body(String string) {
	  
	}

	@When("Admin sends Get HTTPS Request and request Body with {string} endpoint")
	public void admin_sends_get_https_request_and_request_body_with_endpoint(String string) {
	  
	}

	@Then("Admin receives StatusCode with statusText {string} for GetProgramById")
	public void admin_receives_status_code_with_status_text_for_get_program_by_id(String string) {
	   
	}

	@Then("Admin receives Response Body for the given programId")
	public void admin_receives_response_body_for_the_given_program_id() {
	
	}

	@Given("Admin creates Request for the LMS with request body {string}")
	public void admin_creates_request_for_the_lms_with_request_body(String string) {
	   
	}

	@When("Admin sends HTTPS Request and request Body with {string} endpoint")
	public void admin_sends_https_request_and_request_body_with_endpoint(String string) {

	}

	@Then("Admin receives the StatusCode with statusText {string}")
	public void admin_receives_the_status_code_with_status_text(String string) {
	   
	}

	@Then("Admin receives all programs with users {string} for Get request")
	public void admin_receives_all_programs_with_users_for_get_request(String string) {
	  
	}

}
