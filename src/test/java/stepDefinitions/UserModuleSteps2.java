package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserModuleSteps2 {
	
	private RequestSpecification requestSpec;
    private Response response;
	@Given("Admin has valid Bearer token")
	public void admin_has_valid_bearer_token() {
	    
	    
	}

	@Given("Admin creates GET Request with valid\\/invalid Program Id for {string}")
	public void admin_creates_get_request_with_valid_invalid_program_id_for(String string) {
	    
	    
	}

	@When("Admin sends HTTPS Request with endpoint")
	public void admin_sends_https_request_with_endpoint() {
	    
	    
	}

	@Then("Admin receives StatusCode with statusText")
	public void admin_receives_status_code_with_status_text() {
	    
	    
	}

	@Given("Admin creates GET Request with valid\\/invalid Role ID for {string}")
	public void admin_creates_get_request_with_valid_invalid_role_id_for(String string) {
	    
	    
	}

	@Given("Admin creates GET Request with valid\\/invalid Role ID V2 for {string}")
	public void admin_creates_get_request_with_valid_invalid_role_id_v2_for(String string) {
	    
	    
	}

	@Given("Admin creates GET Request with Valid User ID for {string}")
	public void admin_creates_get_request_with_valid_user_id_for(String string) {
	    
	    
	}

	@Given("Admin creates GET Request with valid\\/invalid User ID for {string}")
	public void admin_creates_get_request_with_valid_invalid_user_id_for(String string) {
	    
	    
	}

	@Given("Admin creates PUT Request to update user details for existing User ID for {string}")
	public void admin_creates_put_request_to_update_user_details_for_existing_user_id_for(String string) {
	    
	    
	}

	@When("Admin sends HTTPS Request and request Body")
	public void admin_sends_https_request_and_request_body() {
	    
	    
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

	@Given("Admin creates DELETE Request with Valid\\/Invalid UserID for {string}")
	public void admin_creates_delete_request_with_valid_invalid_user_id_for(String string) {
	    
	    
	}

}
