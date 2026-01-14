Feature: User Module1 [Post Operation]
   # Description: Check if Admin is able to create a new user with various valid and invalid data inputs.

    Background:
        Given Admin sets Bearer token
    @createuser
    Scenario Outline: Check if admin is able to create user with valid/invalid details
        Given Admin creates POST Request for the LMS API endpoint with data from Excel "<scenario>"
        When Admin sends HTTPS Request and request Body for user1
        Then Admin receives StatusCode and response body for "<scenario>"

    Examples:
        | scenario                      |
        | Create_Valid_User           |
       # | Create_Valid_Staff            |
        #| Create_Valid_Student          |
        #| Existing_Phone_Number         |
        #| Missing_Mandatory_Fields      |
        #| Invalid_TimeZone              |
        #| Invalid_VisaStatus            |
        #| Post_NoAuth                   |
        #| Post_Without_RequestBody      |
 
 

@GetallUsers
Scenario Outline: Check if admin able to retrieve all users with valid/invalid Endpoints
    Given Admin creates get request (all users) Request for the LMS API with "<scenario>"
    When Admin sends get request (all users) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getallusers "<scenario>"

Examples:
    | scenario                   |
    | GetallusersValidEndpoint   |
   # | GetallusersInvalidEndpoint |
    #| GetallusersNoAuth          |
    #| GetallusersInvalidMethod   |
 
@GetActiveUsers
Scenario Outline: Check if admin able to retrieve active users with valid/invalid Endpoints
    Given Admin creates get request (active users) Request for the LMS API with "<scenario>"
    When Admin sends get request (active users) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getactiveusers "<scenario>"

Examples:
    | scenario                    |
    | GetActiveUsersValid         |
    | GetActiveUsersInvalidPath   |
    | GetActiveUsersNoAuth        |
 
 

@GetUserByID
Scenario Outline: Check if admin able to retrieve user by ID with valid/invalid details
    Given Admin creates get request (user by ID) Request for the LMS API with "<scenario>"
    When Admin sends get request (user by ID) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getuserbyID "<scenario>"

Examples:
    | scenario               |
    | GetUserValidID         |
    | GetUserInvalidID       |
    | GetUserNoAuth          |
    | GetUserInvalidMethod   |
    
    
    
    
@GetallRoles
Scenario Outline: Check if admin able to retrieve all roles
    Given Admin creates get request (all roles) Request for the LMS API with "<scenario>"
    When Admin sends get request (all roles) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getallroles "<scenario>"

Examples:
    | scenario                |
    | GetallrolesValid        |
    | GetallrolesNoAuth       |    
   
    
@GetUserCount
Scenario Outline: Check if admin able to retrieve user counts by status/role
    Given Admin creates get request (user count) Request for the LMS API with "<scenario>"
    When Admin sends get request (user count) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getusercount "<scenario>"

Examples:
    | scenario                    |
    | GetCountAllUsers            |
    | GetCountBySpecificRole      |
    | GetCountInvalidRoleID       |
    | GetCountNoAuth              |    
    
    
 

 
 @GetActiveUserEmails
Scenario Outline: Check if admin able to retrieve active user emails with valid/invalid Endpoints
    Given Admin creates get request (active user emails) Request for the LMS API with "<scenario>"
    When Admin sends get request (active user emails) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getactiveuseremails "<scenario>"

Examples:
    | scenario                      |
    | GetActiveEmailsValid          |
    | GetActiveEmailsInvalidPath    |
    | GetActiveEmailsNoAuth         |
    
    
    
 
@GetUsersWithRoles
Scenario Outline: Check if admin able to retrieve users with roles
    Given Admin creates get request (users with roles) Request for the LMS API with "<scenario>"
    When Admin sends get request (users with roles) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getuserswithroles "<scenario>"

Examples:
    | scenario                    |
    | GetUsersWithRolesValid      |
    | GetUsersWithRolesNoAuth     |
    | GetUsersWithRolesInvalid    |
    
 
@GetUserByBatch
Scenario Outline: Check if admin able to retrieve users by program batch ID
    Given Admin creates get request (user by batch) Request for the LMS API with "<scenario>"
    When Admin sends get request (user by batch) HTTPS Request with endpoint
    Then Admin receives StatusCode with statusText for getuserbybatch "<scenario>"

Examples:
    | scenario                  |
    | GetUserValidBatchID       |
    | GetUserInvalidBatchID     |
    | GetUserBatchNoAuth        |  
    
     
 
 
 
 
 
 
 