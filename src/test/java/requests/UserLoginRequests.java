package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.UserLoginPayload;
import pojo.UserLoginPojo;
import utilities.CommonUtils;
import utilities.TokenManager;
public class UserLoginRequests extends CommonUtils{
	
	private UserLoginPojo userLoginPojo;
    public RequestSpecification setNoAuth() {
        return RestAssured.given()
        		.log().all() // This prints EVERYTHING (URL, Headers, Body) to the console
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"); // Specifically ask for JSON
    }
    public RequestSpecification setAuth() {
        return RestAssured.given()
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", "application/json");
    }
    public void createLoginRequest(String scenario) throws Exception {
        userLoginPojo = UserLoginPayload.getLoginPayload(scenario);
        currentRow = CommonUtils.getCurrentRow(scenario, "Login");
    }
    public void createGenericRequest(String scenario, String sheetName) {
        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
    }
    public RequestSpecification buildRequest(RequestSpecification reqSpec) {
        if (userLoginPojo != null) {
            reqSpec.body(userLoginPojo);
        }
        return reqSpec;
    }
    public Response sendRequest(RequestSpecification reqSpec) {
    	// 1. Get the endpoint from Excel (e.g., "/login")
        String endpoint = currentRow.get("EndPoint");
        // 2. FORCE the Base URI from your config file into RestAssured
        // This prevents it from defaulting to localhost:8080
        reqSpec.baseUri(CommonUtils.baseURI);
        // 3. Send the request
        return CommonUtils.getResponse(reqSpec, endpoint);
    }
    public void saveToken(Response response) {
        if (response.getStatusCode() == 200) {
            TokenManager.setToken(response.jsonPath().getString("token"));
            TokenManager.setUserId(response.jsonPath().getString("userId"));
        }
    }
    public int getStatusCode() {
    	String statusCodeStr = currentRow.get("StatusCode");
        // This handles "200.0" by converting to double first,
        // then dropping the decimal to return 200 as an int.
        return (int) Double.parseDouble(statusCodeStr);
    }
    public String getStatusText() {
        return currentRow.get("StatusText");
    }
	public RequestSpecification buildForgotPasswordRequest(RequestSpecification requestSpec) {
		// 1. Get Base URI from your properties file
	    RestAssured.baseURI = endpoints.getString("baseUrl");
	    // 2. Retrieve the token that was saved during the Login step
	    String token = TokenManager.getToken();
	    // 3. Return the request specification with the Bearer header
	    return RestAssured.given()
	            .header("Authorization", "Bearer " + token)
	            .header("Content-Type", "application/json");
	}
}
