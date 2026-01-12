@program
Feature: Validating Program Module API 

Background:
Given Admin set Authorization to Bearer token

@postProgram
Scenario Outline: Check if admin is able to create program with valid/invalid details
Given Admin creates Post Request for the LMS with request body "<Scenario>"
When Admin sends Post HTTPS Request and request Body with endpoint
Then Admin receives StatusCode with statusText "<Scenario>"

Examples:
| Scenario               |
| InvalidEndpoint        |
| Existing Program       |
| Invalid Method         |
| Missing Values         |
| NoAuth                 |
| PostWithoutRequestBody |
| Invalid Request Body   |
| Mandatory            |
| Full Details         |


@putProgramById
Scenario Outline: Check if admin is able to update program by programID with valid/invalid details
Given Admin creates Put Request for the LMS with request body "<Scenario>"
When Admin sends Put HTTPS Request and request Body with "programId" programId endpoint
Then Admin receives StatusCode with statusText "<Scenario>" for Put request for programId

Examples:
| Scenario                    |
| PutProgramByInvalidID       |
| PutInvalidRequestBodyByID   |
| PutWithoutRequestBodyByID   |
| PutInvalidMethodByID        |
| PutValidProgramIdWithNoAuth |
| PutValidProgramId           |

@putProgramByName
Scenario Outline: Check if admin is able to update program by programName with valid/invalid details
Given Admin creates Put Request with request body "<Scenario>"
When Admin sends Put HTTPS Request and request Body with "programName" programName endpoint
Then Admin receives StatusCode with statusText "<Scenario>" for Put request by programName

Examples:
| Scenario                      |
| PutProgramByInvalidName       |
| PutMissingMandatoryByName     |
| PutInvalidValuesByName        |
| PutInvalidProgramDescByName   |
| PutValidProgramNameWithNoAuth |
| PutWithoutRequestBodyByName   |
| InvalidToken                  |
| PutValidProgramName           |
| PutStatusByProgramName        |

@getProgramById
Scenario Outline: Check if admin is able to GET program by programID with valid/invalid details
Given Admin creates Get Request for the LMS with request body "<Scenario>"
When Admin sends Get HTTPS Request and request Body with "programId" endpoint
Then Admin receives StatusCode with statusText "<Scenario>" for GetProgramById
And Admin receives Response Body for the given programId

Examples:
| Scenario                          |
| GetProgramByInvalidID             |
| GetProgramByIdWithNoAuth          |
| GetProgramByIdWithInvalidEndpoint |
| GetProgramByIdWithInvalidBaseURI  |
| GetProgramByvalidID               |

@getAllProgramWithUsers @getAllProgram
Scenario Outline: Check if admin is able to GetAllProgramUsers/GetAllProgram with valid/invalid details
Given Admin creates Request for the LMS with request body "<Scenario>"
When Admin sends HTTPS Request and request Body with "No" endpoint
Then Admin receives the StatusCode with statusText "<Scenario>" 
And Admin receives all programs with users "<Scenario>" for Get request

Examples:
| Scenario                              |
| GetAllProgramUsersWithInvalidEndpoint |
| GetAllProgramUsersWithInvalidMethod   |
| GetAllProgramUsersWithNoAuth          |
| GetAllProgramUserWithInvalidBaseURI   |
| GetAllProgramUsersValid               |
| GetAllProgramWithInvalidEndpoint      |
| GetAllProgramWithInvalidMethod        |
| GetAllProgramWithInvalidBaseURI       |
| GetAllProgramWithNoAuth               |
| GetAllProgramValid                    |



