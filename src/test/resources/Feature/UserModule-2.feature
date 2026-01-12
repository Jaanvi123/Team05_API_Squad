Feature: GET operations for User Module

Background: 
Given Admin has valid Bearer token


@GETuserbyProgramID
Scenario Outline: Check if Admin is able to retrieve user with valid/invalid Program ID
    Given Admin creates GET Request with valid/invalid Program Id for "<Scenario>"
    When Admin sends HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |GETuserbyValidProgramID|
      |GETuserbyInValidProgramID|
      |GETuserbyDeletedProgramID|
  
@GETuserbyRoleID
Scenario Outline: Check if Admin is able to retrieve user with valid/invalid Role ID
    Given Admin creates GET Request with valid/invalid Role ID for "<Scenario>"
    When Admin sends HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |GetuserbyValidRoleID|
      |GetuserbyInValidRoleID|
      |GetuserbyDeletedRoleID|
 
@GETuserbyRoleIDV2
Scenario Outline: Check if Admin is able to retrieve user with valid/invalid Role ID V2
    Given Admin creates GET Request with valid/invalid Role ID V2 for "<Scenario>"
    When Admin sends HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |GetuserbyValidRoleIDV2|
      |GetuserbyInValidRoleIDV2|
      |GetuserbyDeletedRoleIDV2|

@GETuserBatchIDbyUserID
Scenario Outline: Check if Admin is able to retrieve users batchid with valid/invalid userid
    Given Admin creates GET Request with Valid User ID for "<Scenario>"
    When Admin sends HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |GETuserBatchIDbyValidUserID|
      |GETuserBatchIDbyInValidUserID|
      |GETuserBatchIDbyDeletedUserID|
      
@GETuserdetailsbyID
Scenario Outline: Check if Admin is able to retreive user details by ID
    Given Admin creates GET Request with valid/invalid User ID for "<Scenario>"
    When Admin sends HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |GETuserdetailsbyValidID|
      |GETuserdetailsbyInValidID|
      |GETuserdetailsbyDeletedID|
      
@PUTupdateuser
Scenario Outline: Check if Admin is able to update user details for existing User ID
    Given Admin creates PUT Request to update user details for existing User ID for "<Scenario>"
    When Admin sends HTTPS Request and request Body
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |Update Existing User ID with mandatory fields|
      |Update Existing User ID without mandatory fields|
      |Update Existing User ID with missing fields|
      |Update Invalid User ID with mandatory fields|
      |Update user details with Deleted User ID|
      
@PUTupdateuserRoleID
Scenario Outline: Check if Admin is able to update user Role ID for existing User ID
    Given Admin creates PUT Request to update user Role ID for existing User ID for "<Scenario>"
    When Admin sends HTTPS Request and request Body
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|  
      |Update with Valid RoleID|
      |Update with Invalid RoleID|
      |Update with Existing RoleID|
      
@PUTuserRoleProgrambatchstatus
Scenario Outline: Check if Admin is able to assign User Role to Program/Batch
    Given Admin creates PUT Request to assign User Role to Program/Batch for "<Scenario>"
    When Admin sends HTTPS Request and request Body
    Then Admin receives StatusCode with statusText
    Examples:
      |Scenario|
      |Update with Valid UserID|
      |Update with Invalid UserID|
      |Update Valid userID with missing fields|
      |Update Valid userID with Invalid BatchID|

@PUTuserLoginStatus
Scenario Outline: Check if Admin is able to update User Login Status 
    Given Admin creates PUT Request to to update User Login Status for "<Scenario>"
    When Admin sends HTTPS Request and request Body
    Then Admin receives StatusCode with statusText
    Examples: 
      |Scenario|
      |Update status with Valid UserID|
      |Update status with InValid UserID|
      |Update status with missing fields|
      
@DELETEuser
Scenario Outline: Check if Admin is able to delete Valid/Invalid UserID 
    Given Admin creates DELETE Request with Valid/Invalid UserID for "<Scenario>"
    When Admin sends HTTPS Request and request Body
    Then Admin receives StatusCode with statusText
    Examples: 
      |Scenario|
      |Delete with Valid UserID|
      |Delete with Invalid UserID|

 