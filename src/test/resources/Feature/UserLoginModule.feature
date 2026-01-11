Feature: Admin Login Controller

  This feature covers all scenarios related to admin authentication,
  including login, forgot password, logout, and password reset.

  Background:
    Given Admin sets No Auth

 
  # Token (Login) Scenarios
  
  Scenario: Admin generates token with valid credentials
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 201 Created with auto-generated token

  Scenario: Admin generates token with invalid method
    Given Admin creates request with valid credentials
    When Admin calls GET HTTPS method with POST endpoint
    Then Admin receives 405 Method Not Allowed

  Scenario: Admin generates token with invalid base URL
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid base URL
    Then Admin receives 400 Bad Request

  Scenario: Admin generates token with invalid content type
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid content type
    Then Admin receives 415 Unsupported Media Type

  Scenario: Admin generates token with invalid endpoint
    Given Admin creates request with valid credentials
    When Admin calls POST HTTPS method with invalid endpoint
    Then Admin receives 401 Unauthorized

  Scenario: Admin generates token with special characters in email
    Given Admin creates request with special characters in email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with special characters in password
    Given Admin creates request with special characters in password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with numbers in email
    Given Admin creates request with numbers in email
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with numbers in password
    Given Admin creates request with numbers in password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Bad Credentials" and false success

  Scenario: Admin generates token with null password
    Given Admin creates request with null password
    When Admin calls POST HTTPS method with valid endpoint
    Then Admin receives 401 with message "Password is Mandatory" and false success

