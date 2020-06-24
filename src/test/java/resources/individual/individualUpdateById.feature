Feature: Update a Individual by Id

  Scenario Outline: Update a Individual by id
    Given Any individual
    And the key is "<id>"
    And the name is "<name>"
    And the motherName is "<motherName>"
    And the document is "<document>"
    And the rg id "<rg>"
    And the birthDate is "<birthDate>"
    And the active is "<active>"
    And Any address
    And the district is "<district>"
    And the number is "<number>"
    And the city is "<city>"
    And the state is "<state>"
    And the zipCode is "<zipCode>"
    And the main is "<main>"
    When User executes the update by id
    Then the server should return a <httpStatusCode>
    And the id "<id>"

    Examples:
      | id |    name    | motherName   |   document  |    rg     |  birthDate | active |     district      | number |   city  |    state  | zipCode  | main | httpStatusCode |        message        |
      | 9  |Amanda Piza | Rafaela Piza | 15381698194 | 468527643 | 1999-12-25 | true   | Rua 7 de setembro | 89     | Leme    | São Paulo | 98180109 |true  | 200            | Update the name       |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-25 | true   | Rua 7 de setembro | 89     | Leme    | São Paulo | 98180109 |true  | 200            | Update the motherName |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-20 | true   | Rua 7 de setembro | 89     | Leme    | São Paulo | 98180109 |true  | 200            | Update the birthDate  |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-20 | true   | Av. 7 de setembro | 89     | Leme    | São Paulo | 98180109 |true  | 200            | Update the district   |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-20 | true   | Av. 7 de setembro | 90     | Leme    | São Paulo | 98180109 |true  | 200            | Update the number     |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-20 | true   | Av. 7 de setembro | 90     | Araras  | São Paulo | 98180109 |true  | 200            | Update the city       |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-20 | true   | Av. 7 de setembro | 90     | Araras  | Alagoas   | 98180109 |true  | 200            | Update the state      |
      | 9  |Amanda Piza | Rafaela piza | 15381698194 | 468527643 | 1999-12-20 | true   | Av. 7 de setembro | 90     | Araras  | Alagoas   | 98180101 |true  | 200            | Update the zipCode    |