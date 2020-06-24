Feature: Insert legal entity with individuals

  Scenario Outline: Insert legal entity with individuals
    Given a legal entity
    And the name's Legal Entity is "<name>"
    And the trade Name's Legal Entity is "<tradeName>"
    And the document's Legal Entity is "<document>"
    And the active's Legal Entity "<active>"
    And a address
    And the district's Legal Entity is "<district>"
    And the number's Legal Entity is "<number>"
    And the city's Legal Entity is "<city>"
    And the state's Legal Entity is "<state>"
    And the zipCode's Legal Entity is "<zipCode>"
    And the main's Legal Entity is "<main>"
    And a Individual
    And the name's Individual is "<nameI>"
    And The motherName's Individual is "<motherNameI>"
    And The document's Individual  is "<documentI>"
    And The rg's Individual id "<rgI>"
    And The birthDate's Individual is "<birthDateI>"
    And The active's Individual is "<activeI>"
    And a Address
    And The district's Individual is "<districtI>"
    And The number's Individual is "<numberI>"
    And The city's Individual is "<cityI>"
    And The state's Individual is "<stateI>"
    And The zipCode's Individual is "<zipCodeI>"
    And The main's Individual is "<mainI>"
    When user executes a post with "<document>"
    Then the server should return this <httpStatusCode>

    Examples:
    |httpStatusCode|   name    |   tradeName     |    document    | active |       district    | number |    city     |   state   | zipCode  | main |        nameI        |             motherNameI         |  documentI  |     rgI   | birthDateI |activeI|         districtI        | numberI |   cityI    |   stateI  | zipCodeI | mainI|  Message   |
    |       200    | Savegnago | Sempre com você | 13852226000162 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805533 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | exists     |
    |       404    | Savegnago | Sempre com você | 26187327000108 | false  | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805533 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | Invalided Legal Entity!          |
    |       404    | Savegnago | Sempre com você | 26187327000108 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | false| Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805533 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | There isn't main address!        |
    |       404    | Savegnago | Sempre com você | 26187327000108 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805531 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | Invalided document!              |
    |       404    | Savegnago | Sempre com você | 26187327000108 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303211 | 277805533 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | Invalided document!              |
    |       404    | Savegnago | Sempre com você | 26187327000108 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805533 | 1999-04-12 | false | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | true | Invalided Individual!            |
    |       404    | Savegnago | Sempre com você | 26187327000108 | true   | Rua Pedro Alvares | 1234   | Arararquara | São Paulo | 18976334 | true | Letícia Ferriera    | Patrícia Ferreira               | 26733303213 | 277805533 | 1999-04-12 | true  | Rua 11 dde Setembro      |   145   | Ubatuba    | São Paulo | 19921344 | false| There isn't main address!        |

    |       200    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225341 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | not exists |
    |       404    | Tonin     | O menor preço   | 98131639000100 | false  | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225341 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | Invalided Legal Entity!          |
    |       404    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | false| Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225341 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | There isn't main address!        |
    |       404    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225340 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | Invalided document!              |
    |       404    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944097 | 911225341 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | Invalided document!              |
    |       404    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225341 | 2000-10-05 | false | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | true | Invalided Individual!            |
    |       404    | Tonin     | O menor preço   | 98131639000100 | true   | Av. Colombo       | 878    | Matão       | São Paulo | 18976314 | true | Aline Ribeiro Braga | Angelica Ribeiro de Paula Braga | 40328944098 | 911225341 | 2000-10-05 | true  | Av. João Soares e Arruda | 1444    | Araraquara | São Paulo | 14801790 | false| There isn't main address!        |