Feature: Find a individual by document

  Scenario Outline: Find a individual by document
    Given A url "<url>"
    And A key: "<key>"
    When the user executes a Get
    Then The response will be a Individual
    And the StatusCode is <httpStatusCode>
    And the Name is "<name>"
    And the MotherName is "<motherName>"
    And the Rg is "<rg>"
    And the BirthDate is "<birthDate>"
    And the Active is "<active>"
    And the District is "<district>"
    And the Number is "<number>"
    And the City is "<city>"
    And the State is "<state>"
    And the ZipCode is "<zipCode>"
    And the Main is "<main>"

    Examples:
      |      url     |     key       | httpStatusCode |         name        |     motherName                  |    rg     | birthDate  | active | district                 | number |   city      | state     | zipCode  | main |
      |  /document/  |  40328944098  |     200        | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 911225341 | 2000-10-05 |  true  | Av. João Soares e Arruda | 1444   | Araraquara  | São Paulo | 14801790 | true |