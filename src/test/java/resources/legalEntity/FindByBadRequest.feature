Feature: Find a Legal Entity with wrong key

  Scenario Outline: Find a Legal Entity with wrong key
    Given URL "<url>"
    And KEY "<key>"
    When The user makes a Get
    Then The server must return <statusCode>
    And the Message "<message>"

    Examples:
      |    url    |       key      | statusCode |         message                 |
      |     /     |      5000      |    404     | This Legal Entity wasn't found! |
      |/document/ | 11248845000117 |    404     | This Legal Entity wasn't found! |