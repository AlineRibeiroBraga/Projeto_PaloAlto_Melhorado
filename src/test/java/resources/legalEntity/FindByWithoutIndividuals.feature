Feature: Find a Legal Entity by key

  Scenario Outline: Find a Legal Entity without individuals by key
    Given a url "<url>"
    And a key "<key>"
    And Verify if this Legal Entity is registered "<name>","<tradeName>","<document>","<active>","<district>","<number>","<city>","<state>","<zipCode>","<main>"
    When The user executes a get
    Then The server should return a Legal Entity
    And Any name "<name>"
    And Any trade name "<tradeName>"
    And Any document "<document>"
    And Any active "<active>"
    And any Address
    And Any district's Legal Entity is "<district>"
    And Any number's Legal Entity is "<number>"
    And Any city's Legal Entity is "<city>"
    And Any state's Legal Entity is "<state>"
    And Any zipCode's Legal Entity is "<zipCode>"
    And Any main's Legal Entity is "<main>"
    And a statusCode <httpStatusCode>

    Examples:
      |     url    |       key      |httpStatusCode|         name         |  tradeName  |    document    | active |            district               | number |    city    |   state   | zipCode  | main |
      |     /      |        1       |      200     | DXC Tecnology        |   DXC       | 39409257000137 | true   | Via de Acesso Engenheiro Ivo Najm | 3800   | Araraquara | São Paulo | 14808100 | true |
      |     /      |      4000      |      200     | AACD                 | Sorrisos    | 67737478000170 | true   | Rua Hilário Gouveia               | 98     | Araras     | São Paulo | 04384010 | true |
      | /document/ | 25288750000131 |      200     | JN Moura Informática |   Moura     | 25288750000131 | true   | R. Dr. Aldo Cariani               | 733    | Araraquara | São Paulo | 14801470 | true |
      | /document/ | 23981274000104 |      200     | Avon                 | Nova Pessoa | 42988800000102 |true    | Rua Alcantilado                   | 134    | Tambaú     | São Paulo | 02979120 | true |