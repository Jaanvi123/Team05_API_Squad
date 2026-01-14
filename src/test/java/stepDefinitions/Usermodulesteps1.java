package stepDefinitions;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requests.UserRequest;

public class Usermodulesteps1 {
	
	private UserRequest userRequest;
    private RequestSpecification requestSpec;
    private Response response;
	
	@Given("Admin sets Bearer token")
	public void admin_sets_bearer_token() {
	    
		// Initialize UserRequest and set base URI + token
        userRequest = new UserRequest();
        requestSpec = userRequest.setAuth();
	}

	@Given("Admin creates POST Request for the LMS API endpoint with data from Excel {string}")
	public void admin_creates_post_request_for_the_lms_api_endpoint_with_data_from_excel(String scenario) throws InvalidFormatException, IOException, ParseException {
	   
		userRequest = new UserRequest(); 
		// Load Excel row first — this sets currentRow 
		userRequest.createUser(scenario); 
		// Then set base URI + token 
		requestSpec = userRequest.setAuth(); 
		// Now build request safely 
		requestSpec = userRequest.buildRequest(requestSpec);
	}

	@When("Admin sends HTTPS Request and request Body for user1")
	public void admin_sends_https_request_and_request_body_for_user1() {
		// Send request using endpoint from Excel currentRow
        response = userRequest.sendRequest(requestSpec);
        // Optionally store response body details
        userRequest.saveResponseBody(response);
	}

	@Then("Admin receives StatusCode and response body for {string}")
	public void admin_receives_status_code_and_response_body_for(String scenario) {
		// Expected status code from Excel
		System.out.println(response);}
   
	

	@Given("Admin creates get request \\(all users) Request for the LMS API with {string}")
	public void admin_creates_get_request_all_users_request_for_the_lms_api_with(String scenario) throws InvalidFormatException, IOException, ParseException {
	    
		userRequest = new UserRequest();
	    userRequest.createUser(scenario);         // load row for this scenario
	    requestSpec = userRequest.setAuth();
	    requestSpec = userRequest.buildRequest(requestSpec);
	}

	@When("Admin sends get request \\(all users) HTTPS Request with endpoint")
	public void admin_sends_get_request_all_users_https_request_with_endpoint() {
		response = userRequest.sendRequest(requestSpec);
	}

	@Then("Admin receives StatusCode with statusText for getallusers {string}")
	public void admin_receives_status_code_with_status_text_for_getallusers(String string) {
		System.out.println(response);
	}

	@Given("Admin creates get request \\(active users) Request for the LMS API with {string}")
	public void admin_creates_get_request_active_users_request_for_the_lms_api_with(String string) {
	    
	}

	@When("Admin sends get request \\(active users) HTTPS Request with endpoint")
	public void admin_sends_get_request_active_users_https_request_with_endpoint() {
	    
	}

	@Then("Admin receives StatusCode with statusText for getactiveusers {string}")
	public void admin_receives_status_code_with_status_text_for_getactiveusers(String string) {
	   
	}

	@Given("Admin creates get request \\(user by ID) Request for the LMS API with {string}")
	public void admin_creates_get_request_user_by_id_request_for_the_lms_api_with(String string) {
	    
	}

	@When("Admin sends get request \\(user by ID) HTTPS Request with endpoint")
	public void admin_sends_get_request_user_by_id_https_request_with_endpoint() {
	    
	}

	@Then("Admin receives StatusCode with statusText for getuserbyID {string}")
	public void admin_receives_status_code_with_status_text_for_getuserby_id(String string) {
	   
	}

	@Given("Admin creates get request \\(all roles) Request for the LMS API with {string}")
	public void admin_creates_get_request_all_roles_request_for_the_lms_api_with(String string) {
	 
	}

	@When("Admin sends get request \\(all roles) HTTPS Request with endpoint")
	public void admin_sends_get_request_all_roles_https_request_with_endpoint() {
	    
	}

	@Then("Admin receives StatusCode with statusText for getallroles {string}")
	public void admin_receives_status_code_with_status_text_for_getallroles(String string) {
	    
	}

	@Given("Admin creates get request \\(user count) Request for the LMS API with {string}")
	public void admin_creates_get_request_user_count_request_for_the_lms_api_with(String string) {
	   
	}

	@When("Admin sends get request \\(user count) HTTPS Request with endpoint")
	public void admin_sends_get_request_user_count_https_request_with_endpoint() {
	
	}

	@Then("Admin receives StatusCode with statusText for getusercount {string}")
	public void admin_receives_status_code_with_status_text_for_getusercount(String string) {
	   
	}

	@Given("Admin creates get request \\(active user emails) Request for the LMS API with {string}")
	public void admin_creates_get_request_active_user_emails_request_for_the_lms_api_with(String string) {
	   
	}

	@When("Admin sends get request \\(active user emails) HTTPS Request with endpoint")
	public void admin_sends_get_request_active_user_emails_https_request_with_endpoint() {
	   
	}

	@Then("Admin receives StatusCode with statusText for getactiveuseremails {string}")
	public void admin_receives_status_code_with_status_text_for_getactiveuseremails(String string) {
	   
	}

	@Given("Admin creates get request \\(users with roles) Request for the LMS API with {string}")
	public void admin_creates_get_request_users_with_roles_request_for_the_lms_api_with(String string) {
	  
	}

	@When("Admin sends get request \\(users with roles) HTTPS Request with endpoint")
	public void admin_sends_get_request_users_with_roles_https_request_with_endpoint() {
	    
	}

	@Then("Admin receives StatusCode with statusText for getuserswithroles {string}")
	public void admin_receives_status_code_with_status_text_for_getuserswithroles(String string) {
	   
	}

	@Given("Admin creates get request \\(user by batch) Request for the LMS API with {string}")
	public void admin_creates_get_request_user_by_batch_request_for_the_lms_api_with(String string) {
	   
	}

	@When("Admin sends get request \\(user by batch) HTTPS Request with endpoint")
	public void admin_sends_get_request_user_by_batch_https_request_with_endpoint() {
	  
	}

	@Then("Admin receives StatusCode with statusText for getuserbybatch {string}")
	public void admin_receives_status_code_with_status_text_for_getuserbybatch(String string) {
	  
	}

}
