@Spartans @Update
Feature: Spartan update request handling
  Background:
    Given I am authenticated

  @SadPath
  Scenario: Reject mismatched URL and body IDs
    Given I have the following request body:
      """
      {"id":2,"firstName":"Testing","lastName":"Spartan"}
      """
    When I send a PUT request to "/api/spartans/1"
    Then the response status code should be 400

  @SadPath
  Scenario: Return not found when updating an unknown Spartan
    Given I have the following request body:
      """
      {"id":999999,"firstName":"Testing","lastName":"Spartan"}
      """
    When I send a PUT request to "/api/spartans/999999"
    Then the response status code should be 404
