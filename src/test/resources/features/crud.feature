@Spartans @CRUD
Feature: Spartan management

  # Creating a Spartan with an existing Course returns HTTP 500
  @HappyPath @Create @defect
  Scenario: Create a new Spartan
    Given I am authenticated
    And I have the following request body:
      """
      {
        "id": 0,
        "firstName": "Testing",
        "lastName": "Spartan",
        "university": "Newcastle University",
        "degree": "Computer Science",
        "course": {
          "id": 1,
          "name": "TECH 300 C# Dev",
          "stream": {
            "id": 1,
            "name": "Development",
            "courses": []
          },
          "trainer": "Phil Windridge",
          "courseId": 1
        },
        "graduated": true
      }
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 201
    And I store the created Spartan ID

  @HappyPath @Read
  Scenario: Retrieve an existing Spartan
    Given I am authenticated
    When I send a GET request to "/api/spartans/1"
    Then the response status code should be 200

    # duplicate error as well
  @HappyPath @Create @Read @defect
  Scenario: Create and retrieve a Spartan
    Given I am authenticated
    And I have the following request body:
      """
      {
        "id": 0,
        "firstName": "Testing",
        "lastName": "Spartan",
        "university": "Newcastle University",
        "degree": "Computer Science",
        "course": {
          "id": 1,
          "name": "TECH 300 C# Dev",
          "stream": {
            "id": 1,
            "name": "Development",
            "courses": []
          },
          "trainer": "Phil Windridge",
          "courseId": 1
        },
        "graduated": true
      }
      """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 201
    And I store the created Spartan ID
    When I send a GET request to the created Spartan
    Then the response status code should be 200
    And the Spartan ID should match the created Spartan
