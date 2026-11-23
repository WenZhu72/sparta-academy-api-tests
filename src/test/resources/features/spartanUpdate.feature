
@Spartans @Update
Feature: Spartan update request handling

  Background:
    Given I am authenticated

  @HappyPath @RestoreSeededSpartan
  Scenario: Successfully update an existing Spartan
    Given the original seeded Spartan 21 is available for restoration
    And I have the following request body:
      """
      {
        "id": 21,
        "firstName": "UpdatedSpartan",
        "lastName": "TestingSpartan",
        "university": "Newcastle University",
        "degree": "Computer Science",
        "course": {
          "id": 1,
          "name": "TECH 300",
          "stream": {
            "id": 1,
            "name": "C# Dev",
            "courses": []
          },
          "trainer": "Phil Windridge",
          "startDate": "2023-03-01T00:00:00",
          "endDate": "2023-05-01T00:00:00",
          "courseId": 1
        },
        "graduated": true
      }
      """
    When I send a PUT request to "/api/spartans/21"
    Then the response status code should be 204
    When I send a GET request to "/api/spartans/21"
    Then the response status code should be 200
    And the Spartan first name should be "UpdatedSpartan"
    And the Spartan last name should be "TestingSpartan"

  @SadPath
  Scenario: Reject mismatched URL and body IDs
    Given I have the following request body:
      """
      {
        "id": 2,
        "firstName": "Testing",
        "lastName": "Spartan"
      }
      """
    When I send a PUT request to "/api/spartans/1"
    Then the response status code should be 400

  @SadPath
  Scenario: Return not found when updating an unknown Spartan
    Given I have the following request body:
      """
      {
        "id": 999999,
        "firstName": "Testing",
        "lastName": "Spartan",
        "university": "Newcastle University",
        "degree": "Computer Science",
        "course": {
          "id": 1,
          "name": "TECH 300",
          "stream": {
            "id": 1,
            "name": "C# Dev",
            "courses": []
          },
          "trainer": "Phil Windridge",
          "startDate": "2023-03-01T00:00:00",
          "endDate": "2023-05-01T00:00:00",
          "courseId": 1
        },
        "graduated": false
      }
      """
    When I send a PUT request to "/api/spartans/999999"
    Then the response status code should be 404
