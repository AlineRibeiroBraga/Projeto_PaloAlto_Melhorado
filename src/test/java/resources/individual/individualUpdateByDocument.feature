Feature: Update a Individual by document

  Scenario Outline: Update a Individual by document
    Given any individual
    And A name "<name>"
    And A motherName "<motherName>"
    And A document "<document>"
    And A rg "<rg>"
    And A birthDate "<birthDate>"
    And A active "<active>"
    And any address
    And A district "<district>"
    And A number "<number>"
    And A city "<city>"
    And A state "<state>"
    And A zipCode "<zipCode>"
    And A main "<main>"
    When User executes the update by document
    Then Server should return a <httpStatusCode>
    And the document "<document>"

    Examples:
      |         name         |    motherName   |   document  |    rg     |  birthDate | active |      district    | number |       city     |    state     | zipCode  | main | httpStatusCode |        message        |
      |Daniela Ribeiro Braga | Daniela Ribeiro | 96802146487 | 144492738 | 2000-10-05 | true   | Rua ABC Paulista | 30A    | São Paulo      | São Paulo    | 17865123 |true  |     200        | Update the name       |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-10-05 | true   | Rua ABC Paulista | 30A    | São Paulo      | São Paulo    | 17865123 |true  |     200        | Update the motherName |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-11-05 | true   | Rua ABC Paulista | 30A    | São Paulo      | São Paulo    | 17865123 |true  |     200        | Update the birthDate  |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-11-05 | true   | Av. ABC Paulista | 30A    | São Paulo      | São Paulo    | 17865123 |true  |     200        | Update the distict    |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-11-05 | true   | Av. ABC Paulista | 30     | São Paulo      | São Paulo    | 17865123 |true  |     200        | Update the number     |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-11-05 | true   | Av. ABC Paulista | 30     | Belo Horizonte | São Paulo    | 17865123 |true  |     200        | Update the city       |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-11-05 | true   | Av. ABC Paulista | 30     | Belo Horizonte | Minas Gerais | 17865123 |true  |     200        | Update the state      |
      |Daniela Ribeiro Braga | Daniela ribeiro | 96802146487 | 144492738 | 2000-11-05 | true   | Av. ABC Paulista | 30     | Belo Horizonte | Minas Gerais | 17865121 |true  |     200        | Update the zipCode    |