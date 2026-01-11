   @UserLogin
   Feature: Admin Login Controller
 


  Background:
    Given Admin sets No Auth

  @Adminlogin
  Scenario Outline: Admin login token generation
    Given Admin creates request with "<requestData>"
    When Admin calls HTTPS method
    Then Admin validates response

    Examples:
      | requestData                     |
      | valid credentials               |
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

@ForgotPassword
  Scenario Outline: Admin forgot password
    Given Admin creates forgot password request with "<requestData>"
    When Admin calls HTTPS method
    Then Admin validates response

    Examples:
      | requestData              |
      | valid email              |
      | special chars in email   |
      | invalid email            |
      | null body                |
      | invalid content          |
      | invalid endpoint         |

  @ResetPassword
  Scenario Outline: Admin reset password
    Given Admin sets authorization to Bearer Token
    Given Admin creates reset password request with "<requestData>"
    When Admin calls HTTPS method
    Then Admin validates response

    Examples:
      | requestData                      |
      | valid email and new password     |
      | valid email and old password     |
      | special chars in password        |
      | invalid email                    |
      | valid data invalid content       |

      
      
      
   @Logout
  Scenario Outline: Admin logout
    Given Admin sets authorization "<authType>" and creates logout request
    When Admin calls HTTPS method
    Then Admin validates response

    Examples:
      | authType        |
      | Bearer Token    |
      | No Auth         |
      | Old Token       |