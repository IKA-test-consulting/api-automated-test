Feature: 1. Auth service will return a token if credentials are valid

  Scenario: 1.1 Requests with valid user credentials will be given a token
    When I enter valid credentials
    Then I will received a token

  Scenario Outline: 1.2 Requests with invalid user credentials will be given an appropriate error response
    When I enter <incorrect> credentials
    Then I will receive an <incorrect> error response
    Examples: Invalid credentials
      | incorrect |
      | invalid   |
      | missing   |
