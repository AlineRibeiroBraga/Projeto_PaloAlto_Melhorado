Feature: The user executes a bad request

  Scenario Outline: The user executes a bad request
    Given The url is "<url>"
    And The Key is "<key>"
    And the individual
    And a name "<name>"
    And a motherName "<motherName>"
    And a document "<document>"
    And a rg "<rg>"
    And a birthDate "<birthDate>"
    And a active "<active>"
    And the address
    And a district "<district>"
    And a number "<number>"
    And a city "<city>"
    And a state "<state>"
    And a zipCode "<zipCode>"
    And a main "<main>"
    When user executes the update
    Then the server should return the <httpStatusCode>
    And the message "<message>"

    Examples:
      |     url    |      key    |       name       |     motherName    |  document   |    rg     | birthDate  | active |       district      | number |   city    |   state   | zipCode  | main  | httpStatusCode |                                 message                               |
      |      /     | 3000        | Ricardo Braga    | Dairce Nadir      | 95186570048 | 400757896 | 1987-04-09 | true   | Av. Paulista        | 39     | São Paulo | São Paulo | 17865110 | true  |   404          | This Individual wasn't found!                                         |
      |      /     | 11          | Ricardo Braga    | Dairce Nadir      | 95186570047 | 400757896 | 1987-04-09 | true   | Av. Paulista        | 39     | São Paulo | São Paulo | 17865110 | true  |   404          | This Individual wasn't found!                                         |
      |      /     | 11          | Ricardo Braga    | Dairce Nadir      | 95186570048 | 400757895 | 1987-04-09 | true   | Av. Paulista        | 39     | São Paulo | São Paulo | 17865110 | true  |   404          | This Individual wasn't found!                                         |
      |      /     | 11          | Ricardo Braga    | Dairce Nadir      | 95186570048 | 400757896 | 1987-04-09 | false  | Av. Paulista        | 39     | São Paulo | São Paulo | 17865110 | true  |   404          | This Individual wasn't found!                                         |
      |      /     | 11          | Ricardo Braga    | Dairce Nadir      | 95186570048 | 400757896 | 1987-04-09 | true   | Av. Paulista        | 39     | São Paulo | São Paulo | 17865110 | false |   404          | There are more than one main address or There isn't one main address! |

      | /document/ | 26733303212 | Letícia Ferriera | Patrícia Ferreira | 26733303213 | 277805533 | 1999-04-12 | true   | Rua 11 dde Setembro | 145    | Ubatuba   | São Paulo | 19921344 | true  |   404          | This Individual wasn't found!                                         |
      | /document/ | 26733303213 | Letícia Ferriera | Patrícia Ferreira | 26733303212 | 277805533 | 1999-04-12 | true   | Rua 11 dde Setembro | 145    | Ubatuba   | São Paulo | 19921344 | true  |   404          | This Individual wasn't found!                                         |
      | /document/ | 26733303213 | Letícia Ferriera | Patrícia Ferreira | 26733303213 | 277805531 | 1999-04-12 | true   | Rua 11 dde Setembro | 145    | Ubatuba   | São Paulo | 19921344 | true  |   404          | This Individual wasn't found!                                         |
      | /document/ | 26733303213 | Letícia Ferriera | Patrícia Ferreira | 26733303213 | 277805533 | 1999-04-12 | false  | Rua 11 dde Setembro | 145    | Ubatuba   | São Paulo | 19921344 | true  |   404          | This Individual wasn't found!                                         |
      | /document/ | 26733303213 | Letícia Ferriera | Patrícia Ferreira | 26733303213 | 277805533 | 1999-04-12 | true   | Rua 11 dde Setembro | 145    | Ubatuba   | São Paulo | 19921344 | false |   404          | There are more than one main address or There isn't one main address! |