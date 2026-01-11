
Feature: Program Post Request 

Background:
Given Admin set Authorization

Scenario: Check if Admin able to Create a program Post request with valid/invalid details 
Given Admin creates POST Request for the LMS with request body
When Admin sends Post HTTPS request and request Body with endpoint
Then Admin should receive StatusCode with statustext

Scenario: Check if admin is able to Update program by programID with valid/invalid details
Given Admin creates Put Request for the LMS with request body
When Admin sends Put HTTPS Request and request Body with "programId" endpoint
Then Admin receives StatusCode with statusText 

Scenario: Check if admin is able to Update program by programName with valid/invalid details
Given Admin creates Put Request for the LMS with request body 
When Admin sends Get HTTPS Request and request Body with "programName" endpoint
Then Admin receives StatusCode with statusText 
 
Scenario: Check if admin is able to GET program by programID with valid/invalid details
Given Admin creates Get Request for the LMS with request body 
When Admin sends Get HTTPS Request and request Body with "programId" endpoint
Then Admin receives StatusCode with statusText 
And Admin receives Responce Body for the given programId

Scenario: Check if admin is able to GetAllProgramUsers/GetAllProgram with valid/invalid details
Given Admin creates Get Request for the LMS with request body 
When Admin sends Get HTTPS Request and request Body with "No" endpoint
Then Admin receives StatusCode with statusText 
And Admin receives all programs with users 



