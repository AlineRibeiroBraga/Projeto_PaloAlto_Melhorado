Feature: Find a individual with a invalided key

  Scenario Outline: Delete a individual
    Given the url "<url>"
    And the key: "<key>"
    When The user executes a Delete
    Then The server should return a statusCode <httpStatusCode>
    And a message "<message>"

    Examples:
      | httpStatusCode |    url      |     key    |                message               |         messages         |
      |      404       |     /       |    2000    | This Individual wasn't found!        |                          |
      |      404       |     /       |     5      | This Individual was already deleted! | id: 5 -> active = false  |
      |      404       |     /       |     6      | This Individual was already deleted! | id: 6 -> tem que deletar |
      |      404       |  /document/ |40328944092 | This Individual wasn't found!        |                          |
      |      404       |  /document/ |05173661852 | This Individual was already deleted! | id: 7 -> active = false  |
      |      404       |  /document/ |75884560192 | This Individual was already deleted! | id: 8 -> tem que deletar |
