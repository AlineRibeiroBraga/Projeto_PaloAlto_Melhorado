Feature: Delete a Individual

  Scenario Outline: Delete a Individual by document
    Given url is "<url>"
    And document is "<document>"
    When the user executes a Delete by document
    Then the server should return "<response>"
    And the statusCode is <httpStatusCode>

    Examples:
      |     url    |   document  | response    | httpStatusCode |
      | /document/ | 53513641400 | 53513641400 |      200       |