Feature: Update a Legal Entity without partners

  Scenario Outline: Update a Legal Entity without partners
    Given this Url "<url>"
    And Verify if this Legal Entity is registered with "<name>","<tradeName>","<document>","<active>","<district>","<number>","<city>","<state>","<zipCode>","<main>"
    When the user updates any Legal Entity
    Then server must return any <httpStatusCode>
    And Validates the fields
    And This name is "<name>"
    And This tradeName is "<tradeName>"
    And This document is "<document>"
    And This active is "<active>"
    And Any Address's Legal Entity
    And This district is "<district>"
    And This number is "<number>"
    And This city is "<city>"
    And This state is "<state>"
    And This zipCode is "<zipCode>"
    And This main is "<main>"

    Examples:
      |    url     |       name        | tradeName   |    document    | active |         district      | number |        city        |   state   | zipCode  | main |httpStatusCode|
      | /document/ | Acer Notebooks    | Acer        | 71784857000198 | true   | Avenida Rio Jari      | 76     | Macapá             | Amapá     | 68911020 | true |     200      |
      | /document/ | Americanas        | Menor preço | 15752374000130 | true   | Rua Altair Ferreira   | 43     | Franca             | São Paulo | 14406773 | true |     200      |