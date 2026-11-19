@Spartans @ExternalData @Boundary
Feature: Load parameterised negative test inputs from external JSON
  Background:
    Given I am authenticated

  @SadPath
  Scenario Outline: Invalid first names are rejected
    Given I load Spartan request for test case "<case>" from "testdata/spartan-invalid-names.json"
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400
    And the validation errors should include "FirstName"

    Examples:
      | case            |
      | one character   |
      | five characters |
      | 51 characters   |
