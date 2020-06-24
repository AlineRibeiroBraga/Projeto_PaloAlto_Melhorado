Feature: Delete a Legal Entity wit individuals

  Scenario Outline: Delete a Legal Entity with Individuals
    Given any url "<url>"
    And any key "<key>"
    And Verify if this Legal entity is registered "<name>","<tradeName>","<document>","<active>","<district>","<number>","<city>","<state>","<zipCode>","<main>", "<nameI>","<documentI>","<rgI>","<motherNameI>", "<birthDateI>", "<activeI>", "<districtI>","<numberI>","<cityI>","<stateI>","<zipCodeI>","<mainI>"
    When the user makes a Delete
    Then The server must return the statusCode <httpStatusCode>

    Examples:
      |     url    |       key      |httpStatusCode|           name         |  tradeName  |    document    | active |            district               | number |         city          |   state   | zipCode  | main |     nameI       |  documentI  |     rgI   |   motherNameI   | birthDateI | activeI |        districtI    | numberI |    cityI     |        stateI       | zipCodeI | mainI |
      |      /     |       52       |     200      | Motorola & Tecnologias | Motorola    | 88973223107046 | true   | Rua Tres                          | 86     | Leme                  | São Paulo | 17135610 | true | Fernando Braga  | 94276982413 | 216299913 | Alice Ribeiro   | 1978-09-04 | true    | Rua do Rosário      | 874     | Lages        | Santa Catarina      | 88502240 | true  |
      |      /     |     2000       |     200      | Sangung computadores   | Sangung     | 18649310000105 | true   | Rua Marquês                       | 46     | São João da Boa vista | São Paulo | 12134400 | true | Fernanda Braga  | 67149242162 | 314459509 | Manuela Ribeiro | 1968-04-17 | true    | Av. Alameda Gustavo | 627     | Blumenau     | Santa Catarina      | 89015420 | true  |
      | /document/ | 32213562000100 |     200      | LG novidades           | LG          | 32213562000100 | true   | Rua Engenheiro Antônio Fernando   | 13     | São João das Pedras   | São Paulo | 11448180 | true | Julio Francisco | 51296037614 | 140353823 | Fabiana Clarice | 1969-09-10 | true    | Rua Bruno Schreiber | 140     | Americo      | Araraquara          | 12900900 | true  |
      | /document/ | 23039733000135 |     200      | Xiaome                 | Xiaome      | 23039733000135 | true   | Rua São Judas Tadeu               | 154    | Matão                 | São Paulo | 99132666 | true | Lorenzo Porto   | 88142281597 | 480126148 | Milena Isabelle | 1968-10-09 | true    | Rua Pessoa          | 13      | Chapecó      | São Paulo           | 89804170 | true  |
