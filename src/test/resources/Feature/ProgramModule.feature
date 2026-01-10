Feature: Validating Program Module API

Scenario: Check if Admin able to create a program Post request 
Given Admin creates POST Request for the LMS with request body
When Admin sends Post request
Then Admin should receive status code with response body and message for post request


