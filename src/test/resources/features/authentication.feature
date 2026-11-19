@Authentication
Feature: API Authentication

  As an API consumer
  I want to authenticate using my credentials
  So that I can access protected endpoints

  @Smoke @HappyPath
  Scenario: Login with valid credentials
    Given I have valid login credentials
    When I submit a login request
    Then the response status code should be 200
    And the response should contain a JWT token

  @SadPath
  Scenario: Login with invalid credentials
    Given I have invalid login credentials
    When I submit a login request
    Then the login request should be rejected

  @SadPath
  Scenario: Login with missing credentials
    Given I have no login credentials
    When I submit a login request
    Then the login request should be rejected

  @SadPath
  Scenario Outline: Reject incorrect login credentials
    Given I have login credentials "<username>" and "<password>"
    When I submit a login request
    Then the login request should be rejected

    Examples:
      | username | password      |
      | invalid  | global        |
      | sparta   | wrongpassword |
      | invalid  | wrongpassword |
      |          | global        |
      | sparta   |               |
