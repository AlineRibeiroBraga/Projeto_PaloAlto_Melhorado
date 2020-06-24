Feature: Individual
  How user enter with dates
  To register yourself

  Scenario Outline: Insert one Individual
    Given A Individual
    And The name is "<name>"
    And The motherName is "<motherName>"
    And The document is "<document>"
    And The rg id "<rg>"
    And The birthDate is "<birthDate>"
    And The active is "<active>"
    And A Address
    And The district is "<district>"
    And The number is "<number>"
    And The city is "<city>"
    And The state is "<state>"
    And The zipCode is "<zipCode>"
    And The main is "<main>"
    When User executes a Post
    Then The server should return a <httpStatusCode>

    Examples:
      |         name        |     motherName  |  document   |    rg     | birthDate  | active |district           | number| city                      | state     | zipCode | main | httpStatusCode|            message             |
      |Alice Ribeiro Braga  |Angélica Ribeiro | 63352516987 | 231147041 | 2000-02-05 | true   | Rua Antonio Garcia| 301   | Santa Rita do Passa Quatro| São Paulo | 02469125| true | 201           |              ok                |
      |Aline Ribeiro Braga  |Angélica Ribeiro | 63352516987 | 231147041 | 2000-05-10 | true   | Rua Antonio Garcia| 31    | Porto Ferreira            | São Paulo | 19910222| true | 404           | Document and Rg already exists |
      |Albertina Ribeiro    |Arminda Ribeiro  | 90322337267 | 231147041 | 1983-04-12 | true   | Rua Antonio Garcia| 32    | Osasco                    | São Paulo | 17206203| true | 404           | Rg already exists              |
      |Angélica Ribeiro     |Arminda Ribeiro  | 63352516987 | 386109746 | 1976-12-24 | true   | Rua Antonio Garcia| 33    | Ribeirão Preto            | São Paulo | 17306202| true | 404           | Document already exists        |
      |Sergio Ribeiro       |Cida Braga       | 20387451102 | 497941247 | 1977-12-24 | false  | Rua Antonio Garcia| 36    | Leme                      | São Paulo | 17306201| true | 404           | Individual isn't active        |
      |Arminda Ribeiro      |Maria Ribeiro    | 37200265829 | 277958714 | 1934-07-21 | true   | Rua Antonio Garcia| 34    | Pirassunuga               | São Paulo | 17106200| false| 404           | There isn't a main address     |