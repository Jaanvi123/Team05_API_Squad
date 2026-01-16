@UserLoginModule1
Feature: User Controller Operations
  Background:
    Given Admin sets No Auth
  @ForgotPassword
  Scenario Outline: Admin forgot password
    Given Admin creates forgot password request with "<ScenarioName>"
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response
    Examples:
      | ScenarioName           |
      | valid email            |
      | special chars in email |
      | invalid email          |
      | null body              |
      | invalid content        |
      | invalid endpoint       |
  @ResetPassword
  Scenario Outline: Admin Reset Password Validation
    Given Admin sets the auth "<AuthType>"
    And Admin creates reset password request with "<ScenarioName>"
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response
    Examples:
      | ScenarioName                           | AuthType     |
      | valid email and new password           | Bearer Token |
      | valid email and old password           | No Auth      |
      | special characters in password         | Bearer Token |
      | invalid email                          | Bearer Token |
        @Logout
  Scenario Outline: Admin logout
    Given Admin sets authorization "<authType>" and creates logout request
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response
    Examples:
      | authType     |
      | Bearer Token |
      | No Auth      |
      | Old Token    |