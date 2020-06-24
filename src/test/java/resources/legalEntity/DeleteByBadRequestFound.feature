Feature: Delete Legal Entity with a wrong key

  Scenario Outline: Delete a Legal Entity with a wrong key
    Given This url "<url>"
    And This KEY "<key>"
    And verify if this Legal Entity with partners exists
    When user makes a Delete
    Then the server must return a <statusCode>
    And the message is "<message>"

    Examples:
      |    url     |       key      | statusCode |             message                    |
      |     /      |       9        |    404     | This Legal Entity was already deleted! |
      |     /      |       10       |    404     | This Legal Entity was already deleted! |
      |     /      |      3000      |    404     | This Legal Entity was already deleted! |
      | /document/ | 53405195202487 |    404     | This Legal Entity was already deleted! |
      | /document/ | 71053522149853 |    404     | This Legal Entity was already deleted! |
      | /document/ | 83518084000187 |    404     | This Legal Entity was already deleted! |