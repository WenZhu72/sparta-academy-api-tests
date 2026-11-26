@Security @Spartans
Feature: Protected Spartan endpoints require a valid bearer token

  @SadPath
  Scenario: Reject GET without a token
    When I send an unauthenticated GET request to "/api/spartans"
    Then the response status code should be 401

  @SadPath
  Scenario: Reject GET with an invalid token
    Given I use the bearer token "invalid-token"
    When I send a GET request to "/api/spartans"
    Then the response status code should be 401

  @HappyPath
  Scenario: Accept GET with a valid token
    Given I am authenticated
    When I send a GET request to "/api/spartans"
    Then the response status code should be 200

  @SadPath
  Scenario: Reject DELETE without a token
    When I send an unauthenticated DELETE request to "/api/spartans/1"
    Then the response status code should be 401

  @SadPath
  Scenario: Reject DELETE with an invalid token
    Given I use the bearer token "invalid-token"
    When I send a DELETE request to "/api/spartans/1"
    Then the response status code should be 401

  @SadPath
  Scenario: Reject POST without a token
    Given I have the following request body:
    """
    {}
    """
    When I send an unauthenticated POST request to "/api/spartans"
    Then the response status code should be 401

  @SadPath
  Scenario: Reject POST with an invalid token
    Given I use the bearer token "invalid-token"
    And I have the following request body:
    """
    {}
    """
    When I send a POST request to "/api/spartans"
    Then the response status code should be 401

  @SadPath
  Scenario: Reject PUT without a token
    Given I have the following request body:
    """
    {}
    """
    When I send an unauthenticated PUT request to "/api/spartans/1"
    Then the response status code should be 401

  @SadPath
  Scenario: Reject PUT with an invalid token
    Given I use the bearer token "invalid-token"
    And I have the following request body:
    """
    {}
    """
    When I send a PUT request to "/api/spartans/1"
    Then the response status code should be 401