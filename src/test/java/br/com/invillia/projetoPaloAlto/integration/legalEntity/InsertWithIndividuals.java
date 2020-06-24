package br.com.invillia.projetoPaloAlto.integration.legalEntity;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class InsertWithIndividuals {

    private String url = "/legal-entity";

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTOI;

    private RequestSpecification requestSpecification;

    private Response response;

    private Faker faker = new Faker();

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("a legal entity")
    public void aLegalEntity() {
        this.legalEntityDTO = new LegalEntityDTO();
    }

    @And("the name's Legal Entity is {string}")
    public void theNameSLegalEntityIs(String name) {
        this.legalEntityDTO.setName(name);
    }

    @And("the trade Name's Legal Entity is {string}")
    public void theTradeNameSLegalEntityIs(String tradeName) {
        this.legalEntityDTO.setTradeName(tradeName);
    }

    @And("the document's Legal Entity is {string}")
    public void theDocumentSLegalEntityIsIs(String document) {
        this.legalEntityDTO.setDocument(document);
    }

    @And("the active's Legal Entity {string}")
    public void theActiveSLegalEntity(String active) {
        this.legalEntityDTO.setActive(Boolean.valueOf(active));
    }

    @And("a address")
    public void aAddress() {
        this.addressDTO = new AddressDTO();
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addressesDTO.add(addressDTO);
        this.legalEntityDTO.setAddressesDTO(addressesDTO);
    }

    @And("the district's Legal Entity is {string}")
    public void theDistrictSLegalEntityIs(String district) {
        this.addressDTO.setDistrict(district);
    }

    @And("the number's Legal Entity is {string}")
    public void theNumberSLegalEntityIs(String number) {
        this.addressDTO.setNumber(number);
    }

    @And("the city's Legal Entity is {string}")
    public void theCitySLegalEntityIs(String city) {
        this.addressDTO.setCity(city);
    }

    @And("the state's Legal Entity is {string}")
    public void theStateSLegalEntityIs(String state) {
        this.addressDTO.setState(state);
    }

    @And("the zipCode's Legal Entity is {string}")
    public void theZipCodeSLegalEntityIs(String zipCode) {
        this.addressDTO.setZipCode(zipCode);
    }

    @And("the main's Legal Entity is {string}")
    public void theMainSLegalEntityIs(String main) {
        this.addressDTO.setMain(Boolean.valueOf(main));
    }

    @And("a Individual")
    public void aIndividual() {
        this.individualDTO = new IndividualDTO();
        List<IndividualDTO> individualsDTO = new ArrayList<>();
        individualsDTO.add(individualDTO);
        this.legalEntityDTO.setIndividualsDTO(individualsDTO);
    }

    @And("the name's Individual is {string}")
    public void theNameSIndividualIs(String name) {
        individualDTO.setName(name);
    }

    @And("The motherName's Individual is {string}")
    public void theMotherNameSIndividualIs(String motherName) {
        individualDTO.setMotherName(motherName);
    }

    @And("The document's Individual  is {string}")
    public void theDocumentSIndividualIs(String document) {
        individualDTO.setDocument(document);
    }

    @And("The rg's Individual id {string}")
    public void theRgSIndividualId(String rg) {
        individualDTO.setRg(rg);
    }

    @And("The birthDate's Individual is {string}")
    public void theBirthDateSIndividualIs(String birthDate) {
        individualDTO.setBirthDate(LocalDate.parse(birthDate));
    }

    @And("The active's Individual is {string}")
    public void theActiveSIndividualIs(String active) {
        individualDTO.setActive(Boolean.valueOf(active));
    }

    @And("a Address")
    public void Address() {
        this.addressDTOI = new AddressDTO();
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addressesDTO.add(addressDTOI);
        this.individualDTO.setAddressesDTO(addressesDTO);
    }

    @And("The district's Individual is {string}")
    public void theDistrictSIndividualIs(String district) {
        this.addressDTOI.setDistrict(district);
    }

    @And("The number's Individual is {string}")
    public void theNumberSIndividualIs(String number) {
        this.addressDTOI.setNumber(number);
    }

    @And("The city's Individual is {string}")
    public void theCitySIndividualIs(String city) {
        this.addressDTOI.setCity(city);
    }

    @And("The state's Individual is {string}")
    public void theStateSIndividualIs(String state) {
        this.addressDTOI.setState(state);
    }

    @And("The zipCode's Individual is {string}")
    public void theZipCodeSIndividualIs(String zipCode) {
        this.addressDTOI.setZipCode(zipCode);
    }

    @And("The main's Individual is {string}")
    public void theMainSIndividualIs(String main) {
        this.addressDTOI.setMain(Boolean.valueOf(main));
    }

    @When("user executes a post with {string}")
    public void userExecutesAPost(String document) {

        String url;
        int flg;

        do {
            flg = 0;
            url = "/legal-entity/document/";
            url = url.concat(document);
            RequestSpecification requestSpecification = given();
            Response response = requestSpecification.get(url);

            if(response.getStatusCode() == 200){
                legalEntityDTO.setDocument(faker.number().digits(14));
                document = legalEntityDTO.getDocument();
                flg = 1;
            }

        } while (flg == 1);

        this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
        this.response = this.requestSpecification.post(this.url);
    }

    @Then("the server should return this {int}")
    public void theServerShouldReturnThisHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }
}
