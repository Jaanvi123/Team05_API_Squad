@skillMaster
Feature: Validating Skill Master Module API

  Background:
    Given Admin Authorization to Bearer token


  @PostOperationSkill
  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (non existing values)
    Given Admin creates POST Request for the LMS API endpoint
    When Admin sends HTTPS Request and request Body with mandatory fields
    Then Admin receives 201 Created Status with response body

  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body ( existing values)
    Given Admin creates POST Request for the LMS API endpoint
    When Admin sends HTTPS Request and request Body with mandatory
    Then Admin receives 400 Bad Request Status with message cannot create skillMaster , since already exists

  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (missing some mandatory fields)
    Given Admin creates POST Request for the LMS API endpoint
    When Admin sends HTTPS Request and  request Body with some mandatory fields missing
    Then Admin receives 500 Error

##  @GetAllSkill
 ## Scenario: Check if admin able to get all  Skill Master with valid endpoint
    Given Admin creates GET Request for the LMS API endpoint
    When Admin sends HTTPS Request
    Then Admin receives 200 Status with response body(showing all the list of skills)

##  @GetSkill
 ## Scenario: Check if admin able to get Skill Master Name with valid endpoint
    Given Admin creates GET Request for the LMS API endpoint
    When Admin sends HTTPS Request with SkillMasterName
    Then Admin receives 200 Status with response body

##  Scenario: Check if admin able to get Skill Master Name with invalid endpoint
    Given Admin creates GET Request for the LMS API endpoint
    When Admin sends HTTPS Request with invalid SkillMasterName
    Then Admin receives 404 Not Found Status with message

##  @PutSKill
##  Scenario: Check if admin able to update New Skill Master with valid endpoint and request body
    Given Admin creates  PUT Request for the LMS API endpoint
    When Admin sends HTTPS Request and  request Body with mandatory
    Then Admin receives 200 Status with updated response body.

 ## Scenario: Check if admin able to update New Skill Master with invalid endpoint and request body
    Given Admin creates  PUT Request for the LMS API endpoint
    When Admin sends HTTPS Request and request Body with mandatory with wrong skillID
    Then Admin receives 400 Bad Request with error:Bad Request

 ## @DeleteSkill
## Scenario: Check if admin able to Delete  Skill ID  with valid endpoint
    Given Admin creates DELETE Request for the LMS API endpoint
    When Admin sends HTTPS Request
    Then Admin receives 200 Status

##  Scenario: Check if admin able to Delete  Skill ID  with invalid endpoint
    Given Admin creates DELETE Request for the LMS API endpoint
    When Admin sends HTTPS Request
    Then Admin receives 404 Error with response body no record found with skillId
