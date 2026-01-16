@skillMaster
Feature: Validating Skill Master Module API

  Background:
    Given Admin Authorization to Bearer token

  @PostOperationSkill
  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body non existing values
    Given Admin creates POST Request for the Skill Master LMS API endpoint
    When Admin sends HTTPS Request and request Body for skill with mandatory fields
    Then Admin receives 201 Created Status with response body for Skill Master post operation

  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body ( existing values)
    Given Admin creates POST Request for the Skill Master LMS API endpoint
    When Admin sends HTTPS Request and request Body for valid endpoint with mandatory fields
    Then Admin receives 400 Bad Request Status with message cannot create Skill Master since already exists

  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body missing some mandatory fields
    Given Admin creates POST Request for the Skill Master LMS API endpoint
    When Admin sends HTTPS Request and request Body with some mandatory fields missing
    Then Admin receives 400 Error for Skill master endpoint
  
  @GetAllSkill
  Scenario: Check if admin able to get all Skill Master with valid endpoint
    Given Admin creates GET Request for the Skill Master LMS API endpoint
    When Admin sends HTTPS Getall Request for skill master module
    Then Admin receives 200 Status with response body showing all the list of skills for skill Master

@GetSkill
  Scenario: Check if admin able to get Skill Master Name with valid endpoint
    Given Admin creates GET Request for the LMS API endpoint for GETSkill 
    When Admin sends HTTPS Request with SkillMasterName
    Then Admin receives 200 Status with response body for GETSkill operation

  Scenario: Check if admin able to get Skill Master Name with invalid endpoint
    Given Admin creates GET Request for the LMS API endpoint for GETSkill 
    When Admin sends HTTPS Request with invalid SkillMasterName
    Then Admin receives 404 Not Found Status with message for skill master GETSkill

  @PutSKill
  Scenario: Check if admin able to update New Skill Master with valid endpoint and request body
    Given Admin creates PUT Request for the LMS API endpoint for PUT Skill 
    When Admin sends HTTPS Request and request Body with mandatory fields for PUT operation for skill master
    Then Admin receives 200 Status with updated response body for valid PUT operation for skill master

  Scenario: Check if admin able to update New Skill Master with invalid endpoint and request body
    Given Admin creates PUT Request for the LMS API endpoint for PUT Skill 
    When Admin sends HTTPS Request and request Body with mandatory with wrong skillID
    Then Admin receives 404 Bad Request with error Bad Request for skill Master Invalid endpoint operation

  