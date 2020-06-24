Feature: Delete a Individual

  Scenario Outline: Delete a Individual by id
    Given Url is "<url>"
    And Id is "<id>"
    When The user executes a Delete by id
    Then The server should return a "<response>"
    And The StatusCode is <httpStatusCode>

    Examples:
      | url | id | response | httpStatusCode |
      | /   | 3  |     3    |      200       |