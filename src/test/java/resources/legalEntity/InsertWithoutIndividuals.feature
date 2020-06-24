Feature: Insert a Legal Entity

  Scenario Outline: Insert a Legal Entity
    Given A Legal Entity
    And The name's Legal Entity is "<name>"
    And The trade Name's Legal Entity is "<tradeName>"
    And The document's Legal Entity is "<document>"
    And The active's Legal Entity "<active>"
    And A address
    And The district's Legal Entity is "<district>"
    And The number's Legal Entity is "<number>"
    And The city's Legal Entity is "<city>"
    And The state's Legal Entity is "<state>"
    And The zipCode's Legal Entity is "<zipCode>"
    And The main's Legal Entity is "<main>"
    When User executes a post with "<document>"
    Then The server should return <httpStatusCode>

    Examples:
      |        name       | tradeName |    document    | active |       district         | number |    city    |  state | zipCode  | main |httpStatusCode|  message  |
      | Art & Moviment    |    A&M    | 54331445000183 |  true  | Beco Francisco Eugênio | 123    | Rio branco | Manaus | 69043158 | true |    200       |   exits   |
      | Moviment & Art    |    M&A    | 73525921000141 |  true  | Beco Francisco         |  23    | Rio preto  | Manaus | 69043157 | true |    200       | not exists|
      | Art & Moviment    |    A&M    | 54331445000183 |  true  | Beco Francisco Eugênio | 123    | Rio branco | Manaus | 69043158 | true |    404       | This legal entity already exists!|
      | Ballet & Moviment |    B&M    | 48384326000196 |  false | Beco Francisco Eugênio | 123    | Rio branco | Manaus | 69043157 | true |    404       | Invalided legal entity!          |
      | Ballet & Moviment |    B&M    | 48384326000196 |  true  | Beco Francisco Eugênio | 123    | Rio branco | Manaus | 69043157 | false|    404       | there isn't main address!        |