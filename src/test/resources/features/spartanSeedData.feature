@Spartans @Validation @SeedData
Feature: Seeded Spartan data integrity

  Background:
    Given I am authenticated

  @defect
  Scenario: Existing Spartans comply with first name validation rules
    When I send a GET request to "/api/spartans"
    Then the response status code should be 200
    And all Spartan "firstName" values should contain between 6 and 50 characters

  @defect
  Scenario: Existing Spartans comply with last name validation rules
    When I send a GET request to "/api/spartans"
    Then the response status code should be 200
    And all Spartan "lastName" values should contain between 6 and 50 characters