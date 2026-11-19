@Spartans @Validation
Feature: Spartan POST request validation
  Background:
    Given I am authenticated

  @SadPath
  Scenario: Reject an empty request body
    Given I have the following request body:
      """
      {}
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400

  @SadPath
  Scenario: Reject a missing first name
    Given I have the following request body:
      """
      {"lastName":"Spartan"}
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400
    And the validation errors should include "FirstName"

  @SadPath
  Scenario: Reject a missing course
    Given I have the following request body:
      """
      {"firstName":"Testing","lastName":"Spartan"}
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400
    And the validation errors should include "Course"

  @SadPath
  Scenario: Reject a missing last name
    Given I have the following request body:
      """
      {"firstName":"Testing"}
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400
    And the validation errors should include "LastName"
