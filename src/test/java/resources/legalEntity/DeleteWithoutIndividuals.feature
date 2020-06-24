Feature: Delete a Legal Entity without individuals

  Scenario Outline: Delete a Legal Entity without Individuals
    Given Any url "<url>"
    And Any key "<key>"
    And Verify if this Legal entity is registered "<name>","<tradeName>","<document>","<active>","<district>","<number>","<city>","<state>","<zipCode>","<main>"
    When The user makes a Delete
    Then A statusCode is <httpStatusCode>

    Examples:
      |     url    |      key       |httpStatusCode|          name         |  tradeName  |    document    | active |            district               | number |       city          |   state   | zipCode  | main |
      |      /     |       5        |     200      | Asus & Tecnologias    | ASUS        | 35528978000105 | true   | Rua Quatro                        | 87     | Bauru               | São Paulo | 17035610 | true |
      |      /     |     5000       |     200      | Dell computadores     | DELL        | 72681386000155 | true   | Rua Marquês de Maricá             | 45     | São José dos Campos | São Paulo | 12234400 | true |
      | /document/ | 52958668000140 |     200      | Lenovo novidades      | Lenovo      | 52958668000140 | true   | Rua Engenheiro Antônio Fernando   | 12     | São Vicente         | São Paulo | 11348180 | true |
      | /document/ | 33235815000109 |     200      | Acer Notebook         | Acer        | 33235815000109 | true   | Rua São Judas Tadeu               | 12354  | Santo André         | São Paulo | 09132666 | true |