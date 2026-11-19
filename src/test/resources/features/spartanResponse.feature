@Spartans @ResponseValidation
Feature: Spartan GET responses have expected structure
  Background:
    Given I am authenticated

  @HappyPath
  Scenario: Retrieve Spartan one with the correct ID
    When I send a GET request to "/api/spartans/1"
    Then the response status code should be 200
    And the response field "id" should equal 1

  @HappyPath
  Scenario: Retrieve a Spartan with names
    When I send a GET request to "/api/spartans/1"
    Then the response status code should be 200
    And the response should contain the field "firstName"
    And the response should contain the field "lastName"

  @HappyPath
  Scenario: Retrieve the Spartan collection as an array
    When I send a GET request to "/api/spartans"
    Then the response status code should be 200
    And the response should be a JSON array

  @SadPath
  Scenario: Missing Spartan returns no content as implemented
    When I send a GET request to "/api/spartans/999999"
    Then the response status code should be 204
