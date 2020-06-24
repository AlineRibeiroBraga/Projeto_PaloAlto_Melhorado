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

public class IndividualUpdateByBadRequest {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTO;

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("The url is {string}")
    public void theUrlIs(String url) {
        this.url = this.url.concat(url);
    }

    @And("The Key is {string}")
    public void theKeyIs(String key) {
        this.url = this.url.concat(key);
    }

    @And("the individual")
    public void theIndividual() {
        this.individualDTO = new IndividualDTO();
    }

    @And("a name {string}")
    public void aName(String name) {
        individualDTO.setName(name);
    }

    @And("a motherName {string}")
    public void aMotherName(String motherName) {
        individualDTO.setMotherName(motherName);
    }

    @And("a document {string}")
    public void aDocument(String document) {
        individualDTO.setDocument(document);
    }

    @And("a rg {string}")
    public void aRg(String rg) {
        individualDTO.setRg(rg);
    }

    @And("a birthDate {string}")
    public void aBirthDate(String birthDate) {
        individualDTO.setBirthDate(LocalDate.parse(birthDate));
    }

    @And("a active {string}")
    public void aActive(String active) {
        individualDTO.setActive(Boolean.valueOf(active));
    }

    @And("the address")
    public void theAddress() {
        List<AddressDTO> addressesDTO = new ArrayList<>();
        this.addressDTO = new AddressDTO();
        addressesDTO.add(addressDTO);
        this.addressDTO.setIndividualDTO(individualDTO);
        this.individualDTO.setAddressesDTO(addressesDTO);
    }

    @And("a district {string}")
    public void aDistrict(String district) {
        addressDTO.setDistrict(district);
    }

    @And("a number {string}")
    public void aNumber(String number) {
        addressDTO.setNumber(number);
    }

    @And("a city {string}")
    public void aCity(String city) {
        addressDTO.setCity(city);
    }

    @And("a state {string}")
    public void aState(String state) {
        addressDTO.setState(state);
    }

    @And("a zipCode {string}")
    public void aZipCode(String zipCode) {
        addressDTO.setZipCode(zipCode);
    }

    @And("a main {string}")
    public void aMain(String main) {
        addressDTO.setMain(Boolean.valueOf(main));
    }

    @When("user executes the update")
    public void userExecutesTheUpdate() {
        this.requestSpecification = given().contentType(ContentType.JSON).with().body(individualDTO);
        this.response = this.requestSpecification.put(this.url);
    }

    @Then("the server should return the {int}")
    public void theServerShouldReturnAHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }

    @And("the message {string}")
    public void theMessage(String message) {
        Assertions.assertEquals(message,response.getBody().path("message"));
    }
}
