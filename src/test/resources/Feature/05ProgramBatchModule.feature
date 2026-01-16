@Batch
Feature: To test the API request of Batch Module
  Background:
    Given Admin set Authorization for batch
  @postbatch
  Scenario Outline: Check if admin is able to create a Batch with valid/invalid details "<Scenario>"
  Given Admin creates Batch POST Request  with valid data in requestBody for "<Scenario>"
  When Admin sends Batch HTTPS Request with endpoint
  Then Admin receives StatusCode for batch with statusText "<Scenario>"
  Examples:
  | Scenario                       			 |
  | ExistingBatchNameBatchPost           |
  | MissingMandatoryFieldStatusBatchPost |
  | MissingAdditionalFieldsBatchPost     |
  | InvalidBatchNameFormatBatchPost      |
  | MissingBatchNameBatchPost            |
  | SequenceMoreThan99BatchPost          |
  | NegativeClassesBatchPost             |
  | InvalidEndpointBatchPost             |
  | WithoutRequestBodyBatchPost          |
  | InvalidStatusBatchPost               |
  | NoAuthBatchPost                      |
  | InvalidTokenBatchPost                |
  | InactiveProgIdBatchPost              |
  | InvalidMethodCreateBatchPost  			 |
  | ValidDetailsBatchPost                |
  #@getbatchbyprogramid @vijitrial
  #Scenario Outline: Check if admin able to retrieve a batch with valid/invalid Program ID
    #Given Admin creates GET Request of Batch with valid or invalid Program Id for "<Scenario>"
    #When Admin sends Batch HTTPS Request with "<programId>" endpoint
    #Then Admin receives StatusCode for batch with statusText "<Scenario>"
#
    #Examples:
      #| Scenario                          |
      #| GetBatchByProgIdNoAuth            |
      #| GetBatchByProgIdAlphaProgramId    |
      #| GetBatchByProgIdinvalidEndpoint   |
      #| GetBatchByProgIdDeletedProgramId  |
      #| GetBatchByProgIdInvalidMethod     |
      #| GetBatchByProgIdInvalidPathParam  |
      #| GetBatchByProgIdValidProgramId    |
  #@deleteBatchByBatchId
  #Scenario Outline: Check if admin is able to  delete a Batch  with valid/invalid details
    #Given Admin creates delete a Batch Request for the LMS with request body "<scenario>"
    #When Admin sends HTTPS delete a Batch Request and request Body with "batchId" endpoint
    #Then Admin receives StatusCode with statusText for delete a Batch "<Scenario>"
#
    #Examples:
      #| scenario                   |
      #| DeletebatchvalidbatchId    |
      #| DeletebatchInvalidbatchId  |
      #| DeleteBatchInvalidEndpoint |
      #| DeleteBatchInvalidEndpoint |
      #| DeleteBatchInvalidMethod   |
#      | DeleteBatchInvalidNoAuth   |