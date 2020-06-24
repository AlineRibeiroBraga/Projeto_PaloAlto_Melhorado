package br.com.invillia.projetoPaloAlto.integration.individual;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
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

public class IndividualUpdateByDocument {

    private String url = "/individual/document/";

    private RequestSpecification requestSpecification;

    private Response response;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTO;

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("any individual")
    public void anyIndividual() {
        this.individualDTO = new IndividualDTO();
    }

    @And("A name {string}")
    public void aName(String name) {
        individualDTO.setName(name);
    }

    @And("A motherName {string}")
    public void aMotherName(String motherName) {
        individualDTO.setMotherName(motherName);
    }

    @And("A document {string}")
    public void aDocument(String document) {
        individualDTO.setDocument(document);
    }

    @And("A rg {string}")
    public void aRg(String rg) {
        individualDTO.setRg(rg);
    }

    @And("A birthDate {string}")
    public void aBirthDate(String birthDate) {
        individualDTO.setBirthDate(LocalDate.parse(birthDate));
    }

    @And("A active {string}")
    public void aActive(String active) {
        individualDTO.setActive(Boolean.valueOf(active));
    }

    @And("any address")
    public void anyAddress() {
        List<AddressDTO> addressesDTO = new ArrayList<>();
        this.addressDTO = new AddressDTO();
        addressesDTO.add(addressDTO);
        this.addressDTO.setIndividualDTO(individualDTO);
        this.individualDTO.setAddressesDTO(addressesDTO);
    }

    @And("A district {string}")
    public void aDistrict(String district) {
        addressDTO.setDistrict(district);
    }

    @And("A number {string}")
    public void aNumber(String number) {
        addressDTO.setNumber(number);
    }

    @And("A city {string}")
    public void aCity(String city) {
        addressDTO.setCity(city);
    }

    @And("A state {string}")
    public void aState(String state) {
        addressDTO.setState(state);
    }

    @And("A zipCode {string}")
    public void aZipCode(String zipCode) {
        addressDTO.setZipCode(zipCode);
    }

    @And("A main {string}")
    public void aMain(String main) {
        addressDTO.setMain(Boolean.valueOf(main));
    }

    @When("User executes the update by document")
    public void userExecutesTheUpdateByDocument() {
        this.requestSpecification = given().contentType(ContentType.JSON).with().body(individualDTO);
        this.response = this.requestSpecification.put(this.url);
    }

    @Then("Server should return a {int}")
    public void serverShouldReturnAHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }

    @And("the document {string}")
    public void theDocument(String document) {
        Assertions.assertEquals(document, response.getBody().jsonPath().getString("response"));
    }
}