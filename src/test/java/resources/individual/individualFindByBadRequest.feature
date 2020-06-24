Feature: Find a individual with a invalided key

  Scenario Outline: Find a individual
    Given Url "<url>"
    And Key: "<key>"
    When The user executes a Get
    Then The server should return the statusCode <httpStatusCode>
    And The message is "<message>"

    Examples:
      | httpStatusCode |    url      |     key    |           message               |
      |      404       |     /       |    1000    | This Individual wasn't found! |
      |      404       |  /document/ |40328944091 | This Individual wasn't found! |