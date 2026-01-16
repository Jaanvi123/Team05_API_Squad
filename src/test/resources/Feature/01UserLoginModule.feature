@UserLogin
   Feature: Admin Login Controller
  Background:
    Given Admin sets No Auth
  @Adminlogin
  Scenario Outline: Admin login token generation
    Given Admin creates request with "<ScenarioName>"
    When Admin calls login HTTPS method with endpoint
    Then Admin validates response
    Examples:
      | ScenarioName                    |
      | valid credentials               |
     | invalid method					|
      | special chars in email          |
     | special chars in password       |
     | numbers in email                |
      | numbers in password             |
      | null password                   |
      | null email                      |
      | null body                       |
      | invalid endpoint                |
      | invalid base URL                |
      | invalid content                 |
      | GET method test                 |