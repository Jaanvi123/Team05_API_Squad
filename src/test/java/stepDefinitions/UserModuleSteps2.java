package stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requests.UserRequest;


public class UserModuleSteps2 {
	
	private UserRequest UserRequest;
    private RequestSpecification requestSpec;
    private Response response;
	
	@Given("Admin has valid Bearer token")
	public void admin_has_valid_bearer_token() {
		// Initialize UserRequest and set base URI + token
        UserRequest = new UserRequest();
        requestSpec = UserRequest.setAuth();
	    
	}

	@Given("Admin creates GET Request with valid or invalid Program Id for {string}")
	public void admin_creates_get_request_with_valid_or_invalid_program_id_for(String Scenario) {
		
		// Load data from Excel and populate userPojo + currentRow
        UserRequest.createUser(Scenario);

        // Build the final RequestSpecification (headers, body, negative cases logic)
        requestSpec = UserRequest.buildRequest(requestSpec);
	    
	}

	@When("Admin sends HTTPS user Request with endpoint")
	public void admin_sends_https_user_request_with_endpoint() {
		// Send request using endpoint from Excel currentRow
        response = UserRequest.sendRequest(requestSpec);
        // Optionally store response body details
        UserRequest.saveResponseBody(response);
	    
	}

	@Then("Admin receives StatusCode with statusText")
	public void admin_receives_status_code_with_status_text() {
	   
	    
	}

	@Given("Admin creates GET Request with valid or invalid Role ID for {string}")
	public void admin_creates_get_request_with_valid_or_invalid_role_id_for(String string) {
	   
	    
	}

	@Given("Admin creates GET Request with valid or invalid Role ID V2 for {string}")
	public void admin_creates_get_request_with_valid_or_invalid_role_id_v2_for(String string) {
	   
	    
	}

	@Given("Admin creates GET Request with Valid User ID for {string}")
	public void admin_creates_get_request_with_valid_user_id_for(String string) {
	   
	    
	}

	@Given("Admin creates GET Request with valid or invalid User ID for {string}")
	public void admin_creates_get_request_with_valid_or_invalid_user_id_for(String string) {
	   
	    
	}

	@Given("Admin creates PUT Request to update user details for existing User ID for {string}")
	public void admin_creates_put_request_to_update_user_details_for_existing_user_id_for(String string) {
	   
	    
	}

	@Given("Admin creates PUT Request to update user Role ID for existing User ID for {string}")
	public void admin_creates_put_request_to_update_user_role_id_for_existing_user_id_for(String string) {
	   
	    
	}

	@Given("Admin creates PUT Request to assign User Role to Program\\/Batch for {string}")
	public void admin_creates_put_request_to_assign_user_role_to_program_batch_for(String string) {
	   
	    
	}

	@Given("Admin creates PUT Request to to update User Login Status for {string}")
	public void admin_creates_put_request_to_to_update_user_login_status_for(String string) {
	   
	    
	}

	@Given("Admin creates DELETE Request with valid or invalid UserID for {string}")
	public void admin_creates_delete_request_with_valid_or_invalid_user_id_for(String string) {
	   
	    
	}
	
}
