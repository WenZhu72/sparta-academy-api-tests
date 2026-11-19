@Spartans @Boundary
Feature: Spartan first-name length validation
  Background:
    Given I am authenticated

  # These tests verify the specific field error
  @SadPath
  Scenario Outline: Reject first names outside the allowed length of 6 to 50
    Given I have the following request body:
      """
      {"firstName":"<firstName>","lastName":"Spartan"}
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400
    And the validation errors should include "FirstName"

    Examples:
      | firstName                                           |
      | A                                                   |
      | ABCDE                                               |
      | ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXY |

  @SadPath
  Scenario: Reject a null first name
    Given I have the following request body:
      """
      {"firstName":null,"lastName":"Spartan"}
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 400
    And the validation errors should include "FirstName"
