package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requests.UserLoginRequests;
import utilities.CommonUtils;
import static org.testng.Assert.assertEquals;

public class UserLoginSteps {
	
    UserLoginRequests loginReq = new UserLoginRequests();
    RequestSpecification requestSpec;
    Response response;
    @Given("Admin sets No Auth")
    public void admin_sets_no_auth() {
        requestSpec = loginReq.setNoAuth();
    }
    @Given("Admin creates request with {string}")
    public void admin_creates_request(String scenario) throws Exception {
        // Load Excel data row and POJO payload
        loginReq.createLoginRequest(scenario);
        // Handle logic for 'invalid base URL'
        if (scenario.equalsIgnoreCase("invalid base URL")) {
            requestSpec.baseUri("https://invalid-lms-backend.herokuapp.com");
        } else {
            requestSpec.baseUri(CommonUtils.baseURI);
        }
        requestSpec = loginReq.buildRequest(requestSpec);
    }
    @When("Admin calls login HTTPS method with endpoint")
    public void admin_calls_login_https_method_with_endpoint() {
        System.out.println("------- SENDING LOGIN REQUEST -------");
        response = loginReq.sendRequest(requestSpec);
        response.then().log().all();
        loginReq.saveToken(response);
    }
    @Then("Admin validates response")
    public void admin_validates_response() {
        // 1. Status Code Validation (converts Excel 200.0 to 200)
        int expectedCode = loginReq.getStatusCode();
        assertEquals(response.getStatusCode(), expectedCode, "Status Code Mismatch!");
        // 2. Schema Validation for successful login
        if (response.getStatusCode() == 200) {
            CommonUtils.validateResponseSchema(response, "schema/UserLogin.json");
        }
        // 3. Message Validation from StatusText column
        String expectedMsg = loginReq.getStatusText();
        loginReq.validateResponseMessage(expectedMsg, response.getStatusCode(), "scenario", response);
    }
    @Given("Admin creates forgot password request with {string}")
    public void admin_creates_forgot_password_request_with(String scenario) {
        loginReq.createGenericRequest(scenario, "Login"); // Using 'Login' sheet as per your CSV
        requestSpec = loginReq.buildForgotPasswordRequest(requestSpec);
    }
}


