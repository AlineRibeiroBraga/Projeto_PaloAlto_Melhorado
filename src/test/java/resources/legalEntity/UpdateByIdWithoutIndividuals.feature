Feature: Update a Legal Entity without partners

  Scenario Outline: Update a Legal Entity without partners by Id
    Given this url "<url>"
    And this key "<key>"
    And Verify if This Legal Entity is registered "<name>","<tradeName>","<document>","<active>","<district>","<number>","<city>","<state>","<zipCode>","<main>"
    When the user updates a Legal Entity
    Then server must return a <httpStatusCode>
    And Validate the fields
    And this name is "<name>"
    And this tradeName is "<tradeName>"
    And this document is "<document>"
    And this active is "<active>"
    And Any Address
    And this district is "<district>"
    And this number is "<number>"
    And this city is "<city>"
    And this state is "<state>"
    And this zipCode is "<zipCode>"
    And this main is "<main>"

    Examples:
      |    url     |       key      |       name        | tradeName   |    document    | active |         district      | number |        city        |   state   | zipCode  | main |httpStatusCode|
      |     /      | 8000           | Avon              | Nova Pessoa | 14112372799472 | true   | Rua Alcantilado       | 134    | Tambaú             | São Paulo | 02979120 | true |     200      |
      |     /      | 13             | Dell computadores | DELL        | 72681386000155 | true   | Rua Marquês de Maricá | 45     | São José dos Campos| São Paulo | 12234400 | true |     200      |