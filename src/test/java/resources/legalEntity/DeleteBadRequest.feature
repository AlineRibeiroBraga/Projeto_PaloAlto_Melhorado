Feature: Delete Legal Entity with a wrong key

  Scenario Outline: Delete a Legal Entity with a wrong key
    Given The URL "<url>"
    And The KEY "<key>"
    And Verify if this Legal Entity with partners exists
    When The User makes a Delete
    Then The server must return a <statusCode>
    And The Message "<message>"

    Examples:
      |    url     |       key      | statusCode |                message                 |
      |     /      |      7000      |     404    | This Legal Entity wasn't found!        |
      | /document/ | 39838621000184 |     404    | This Legal Entity wasn't found!        |