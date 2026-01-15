package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.SkillMasterPayload;
import pojo.SkillMasterPojo;
import utilities.CommonUtils;
import utilities.TokenManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SkillMasterSteps {

    private RequestSpecification request;
    private Response response;

    private static final String SHEET_NAME = "SkillMaster";

    // ============================================================
    // BACKGROUND
    // ============================================================

    @Given("Admin Authorization to Bearer token")
    public void admin_authorization_to_bearer_token() {
        // Token handled by TokenManager
    }

    // ============================================================
    // POST: CREATE SKILL
    // ============================================================

    @Given("Admin creates POST Request for the Skill Master LMS API endpoint")
    public void admin_creates_post_request_for_the_skill_master_lms_api_endpoint() {

        CommonUtils.getCurrentRow("Create Skill", SHEET_NAME);

        request = given()
                .baseUri(CommonUtils.baseURI)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", CommonUtils.currentRow.get("ContentType"));
    }

    @When("Admin sends HTTPS Request and request Body for skill with mandatory fields")
    public void admin_sends_https_request_and_request_body_for_skill_with_mandatory_fields() {

        SkillMasterPojo payload = CommonUtils.generateSkillMasterPayload();

        String endpoint = CommonUtils.currentRow.get("EndPoint");

        request.body(payload);

        response = CommonUtils.getResponse(request, endpoint);

        CommonUtils.writeSkillMasterResponseToExcel(response, SHEET_NAME);
    }

    @Then("Admin receives 201 Created Status with response body for Skill Master post operation")
    public void admin_receives_201_created_status_with_response_body_for_skill_master_post_operation() {
        response.then().statusCode(201);
    }

    // ============================================================
    // POST: DUPLICATE SKILL
    // ============================================================

    @When("Admin sends HTTPS Request and request Body for valid endpoint with mandatory fields")
    public void admin_sends_https_request_and_request_body_for_valid_endpoint_with_mandatory_fields() throws Exception {

        CommonUtils.getCurrentRow("Existing values", SHEET_NAME);

        SkillMasterPojo saved = CommonUtils.readSkillMasterFromExcel(SHEET_NAME);

        SkillMasterPojo payload = new SkillMasterPojo();
        payload.setSkillName(saved.getSkillName());
        payload.setCreationTime(saved.getCreationTime());
        payload.setLastModTime(saved.getLastModTime());

        String endpoint = CommonUtils.currentRow.get("EndPoint");

        request = given()
                .baseUri(CommonUtils.baseURI)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", CommonUtils.currentRow.get("ContentType"))
                .body(payload);

        response = CommonUtils.getResponse(request, endpoint);
    }

    @Then("Admin receives 400 Bad Request Status with message cannot create Skill Master since already exists")
    public void admin_receives_400_bad_request_status_with_message_cannot_create_skill_master_since_already_exists() {
        response.then().statusCode(400);
    }

    // ============================================================
    // POST: MISSING FIELDS
    // ============================================================

    @When("Admin sends HTTPS Request and request Body with some mandatory fields missing")
    public void admin_sends_https_request_and_request_body_with_some_mandatory_fields_missing() {

        CommonUtils.getCurrentRow("Missing Mandatory Fields", SHEET_NAME);

        SkillMasterPojo payload = new SkillMasterPojo();
        payload.setSkillName("");
        payload.setCreationTime(null);
        payload.setLastModTime(null);

        String endpoint = CommonUtils.currentRow.get("EndPoint");

        request = given()
                .baseUri(CommonUtils.baseURI)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", CommonUtils.currentRow.get("ContentType"))
                .body(payload);

        response = CommonUtils.getResponse(request, endpoint);
    }

    @Then("Admin receives {int} Error for Skill master endpoint")
    public void admin_receives_error_for_skill_master_endpoint(Integer expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    // ============================================================
    // GET: ALL SKILLS
    // ============================================================

    @Given("Admin creates GET Request for the Skill Master LMS API endpoint")
    public void admin_creates_get_request_for_the_skill_master_lms_api_endpoint() {

        CommonUtils.getCurrentRow("GetAllSkills", SHEET_NAME);

        request = given()
                .baseUri(CommonUtils.baseURI)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", CommonUtils.currentRow.get("ContentType"));
    }

    @When("Admin sends HTTPS Getall Request for skill master module")
    public void admin_sends_https_getall_request_for_skill_master_module() {

        String endpoint = CommonUtils.currentRow.get("EndPoint");

        response = CommonUtils.getResponse(request, endpoint);
    }

    @Then("Admin receives 200 Status with response body showing all the list of skills for skill Master")
    public void admin_receives_200_status_with_response_body_showing_all_the_list_of_skills_for_skill_master() {
        response.then().statusCode(200);
    }

    // ============================================================
    // GET: SKILL BY NAME (VALID)
    // ============================================================

    @Given("Admin creates GET Request for the LMS API endpoint for GETSkill")
    public void admin_creates_get_request_for_the_lms_api_endpoint_for_get_skill() {

        CommonUtils.getCurrentRow("GetSkillByName", SHEET_NAME);

        request = given()
                .baseUri(CommonUtils.baseURI)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", CommonUtils.currentRow.get("ContentType"));
    }

    @When("Admin sends HTTPS Request with SkillMasterName")
    public void admin_sends_https_request_with_skill_master_name() {

        SkillMasterPojo saved = CommonUtils.readSkillMasterFromExcel(SHEET_NAME);

        String endpoint = CommonUtils.currentRow.get("EndPoint")
                .replace("{SkillName}", saved.getSkillName());

        response = CommonUtils.getResponse(request, endpoint);
    }

    @Then("Admin receives 200 Status with response body for GETSkill operation")
    public void admin_receives_200_status_with_response_body_for_get_skill_operation() {
        response.then().statusCode(200);
        response.then().body("skillName", notNullValue());
    }

    // ============================================================
    // GET: SKILL BY NAME (INVALID)
    // ============================================================

    @When("Admin sends HTTPS Request with invalid SkillMasterName")
    public void admin_sends_https_request_with_invalid_skill_master_name() {

        String invalidName = "InvalidSkill_999";

        String endpoint = CommonUtils.currentRow.get("EndPoint")
                .replace("{SkillName}", invalidName);

        response = CommonUtils.getResponse(request, endpoint);
    }

    @Then("Admin receives 404 Not Found Status with message for skill master GETSkill")
    public void admin_receives_404_not_found_status_with_message_for_skill_master_get_skill() {
        response.then().statusCode(404);
    }

    // ============================================================
    // PUT: UPDATE SKILL (VALID)
    // ============================================================

    @Given("Admin creates PUT Request for the LMS API endpoint for PUT Skill")
    public void admin_creates_put_request_for_the_lms_api_endpoint_for_put_skill() {

        CommonUtils.getCurrentRow("Update Skill", SHEET_NAME);

        request = given()
                .baseUri(CommonUtils.baseURI)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", CommonUtils.currentRow.get("ContentType"));
    }

    @When("Admin sends HTTPS Request and request Body with mandatory fields for PUT operation for skill master")
    public void admin_sends_https_request_and_request_body_with_mandatory_fields_for_put_operation_for_skill_master() {

        int skillId = CommonUtils.getExistingSkillIdFromExcel(SHEET_NAME);

        String updatedSkillName = CommonUtils.generateUniqueSkillNameForPut();

        SkillMasterPojo payload = SkillMasterPayload.buildUpdateSkillPayload(updatedSkillName);

        CommonUtils.writePutRequestToExcel(SHEET_NAME, updatedSkillName);

        String endpoint = CommonUtils.currentRow.get("EndPoint")
                .replace("{Skillid}", String.valueOf(skillId));

        request.body(payload);

        response = CommonUtils.getResponse(request, endpoint);

        CommonUtils.writePutResponseToExcel(response, SHEET_NAME);
    }

    @Then("Admin receives 200 Status with updated response body for valid PUT operation for skill master")
    public void admin_receives_200_status_with_updated_response_body_for_valid_put_operation_for_skill_master() {
        response.then().statusCode(200);
    }

    // ============================================================
    // PUT: UPDATE SKILL (INVALID)
    // ============================================================

    @When("Admin sends HTTPS Request and request Body with mandatory with wrong skillID")
    public void admin_sends_https_request_and_request_body_with_mandatory_with_wrong_skill_id() {

        int invalidSkillId = 999999;

        String updatedSkillName = CommonUtils.generateUniqueSkillNameForPut();

        SkillMasterPojo payload = SkillMasterPayload.buildUpdateSkillPayload(updatedSkillName);

        String endpoint = CommonUtils.currentRow.get("EndPoint")
                .replace("{Skillid}", String.valueOf(invalidSkillId));

        request.body(payload);

        response = CommonUtils.getResponse(request, endpoint);
    }

    @Then("Admin receives 404 Bad Request with error Bad Request for skill Master Invalid endpoint operation")
    public void admin_receives_404_bad_request_with_error_bad_request_for_skill_master_invalid_endpoint_operation() {
        response.then().statusCode(404);
    }
}
