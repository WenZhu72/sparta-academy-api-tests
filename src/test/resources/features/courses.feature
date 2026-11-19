@Courses
Feature: Course API

  As an API consumer
  I want to retrieve course information
  So that I can view available courses

  @Smoke @HappyPath
  Scenario: Retrieve all courses successfully
    When I retrieve all courses
    Then the response status code should be 200
    And the response should contain courses
    And all courses should have valid IDs
    And all courses should have valid names

  @HappyPath
  Scenario Outline: Retrieve an existing course by ID
    When I retrieve course with ID <id>
    Then the response status code should be 200
    And the course name should be "<name>"

    Examples:
      | id | name     |
      | 1  | TECH 300 |
      | 2  | TECH 301 |
      | 3  | TECH 302 |
      | 4  | TECH 303 |
      | 5  | DATA 304 |

  @SadPath @defect
  Scenario: Retrieve a nonexistent course
    When I retrieve course with ID 999999
    Then the response status code should be 404
