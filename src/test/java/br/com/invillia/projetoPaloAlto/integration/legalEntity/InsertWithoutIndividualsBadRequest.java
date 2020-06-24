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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class InsertWithoutIndividualsBadRequest {

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

    @Given("A legal entity")
    public void aLegalEntity() {
        this.legalEntityDTO = new LegalEntityDTO();
    }

    @And("The Name's Legal Entity is {string}")
    public void theNameSLegalEntityIs(String name) {
        this.legalEntityDTO.setName(name);
    }

    @And("The Trade Name's Legal Entity is {string}")
    public void theTradeNameSLegalEntityIs(String tradeName) {
        this.legalEntityDTO.setTradeName(tradeName);
    }

    @And("The Document's Legal Entity is {string}")
    public void theDocumentSLegalEntityIs(String document) {
        this.legalEntityDTO.setDocument(document);
    }

    @And("The Active's Legal Entity {string}")
    public void theActiveSLegalEntity(String active) {
        this.legalEntityDTO.setActive(Boolean.valueOf(active));
    }

    @And("This address")
    public void thisAddress() {
        this.addressDTO = new AddressDTO();
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addressesDTO.add(addressDTO);
        this.legalEntityDTO.setAddressesDTO(addressesDTO);
    }

    @And("The District's Legal Entity is {string}")
    public void theDistrictSLegalEntityIs(String district) {
        this.addressDTO.setDistrict(district);
    }

    @And("The Number's Legal Entity is {string}")
    public void theNumberSLegalEntityIs(String number) {
        this.addressDTO.setNumber(number);
    }

    @And("The City's Legal Entity is {string}")
    public void theCitySLegalEntityIs(String city) {
        this.addressDTO.setCity(city);
    }

    @And("The State's Legal Entity is {string}")
    public void theStateSLegalEntityIs(String state) {
        this.addressDTO.setState(state);
    }

    @And("The ZipCode's Legal Entity is {string}")
    public void theZipCodeSLegalEntityIs(String zipCode) {
        this.addressDTO.setZipCode(zipCode);
    }

    @And("The Main's Legal Entity is {string}")
    public void theMainSLegalEntityIs(String main) {
        this.addressDTO.setMain(Boolean.valueOf(main));
    }

    @When("User Executes a post with {string}")
    public void userExecutesAPostWith(String document) {
        String url;

        url = "/legal-entity/document/";
        url = url.concat(document);
        RequestSpecification requestSpecification = given();
        Response response = requestSpecification.get(url);

        if(response.getStatusCode() == 404){
            this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
            this.response = this.requestSpecification.post(this.url);
        }

        this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
        this.response = this.requestSpecification.post(this.url);
    }

    @Then("The Server should return this {int}")
    public void theServerShouldReturnThisHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }
}
