@Spartans @Delete
Feature: Spartan deletion request handling
  Background:
    Given I am authenticated

  @SadPath
  Scenario: Return not found when deleting an unknown Spartan
    When I send a DELETE request to "/api/spartans/999999"
    Then the response status code should be 404

  @SadPath
  Scenario: Reject a nonnumeric Spartan ID
    When I send a DELETE request to "/api/spartans/invalid"
    Then the response status code should be 400
