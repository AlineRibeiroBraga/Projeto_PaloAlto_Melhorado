Feature: Bad request to insert a legal entity without individuals

  Scenario Outline: Bad request to insert a legal entity without individuals
    Given A legal entity
    And The Name's Legal Entity is "<name>"
    And The Trade Name's Legal Entity is "<tradeName>"
    And The Document's Legal Entity is "<document>"
    And The Active's Legal Entity "<active>"
    And This address
    And The District's Legal Entity is "<district>"
    And The Number's Legal Entity is "<number>"
    And The City's Legal Entity is "<city>"
    And The State's Legal Entity is "<state>"
    And The ZipCode's Legal Entity is "<zipCode>"
    And The Main's Legal Entity is "<main>"
    When User Executes a post with "<document>"
    Then The Server should return this <httpStatusCode>

    Examples:
      |httpStatusCode|   name    |   tradeName     |    document    | active |       district    | number |    city     |   state   | zipCode  | main |        nameI        |             motherNameI         |  documentI  |     rgI   | birthDateI |activeI|         districtI        | numberI |   cityI    |   stateI  | zipCodeI | mainI|  Message   |
      |       404    | Savegnago | Sempre com você | 13852226000162 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805533 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | exists     |
      |       404    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225341 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | not exists |
