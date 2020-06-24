package br.com.invillia.projetoPaloAlto.integration.individual;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class IndividualFindByDocument {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTO;

    @BeforeAll()
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("A url {string}")
    public void aUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("A key: {string}")
    public void aKey(String key) {
        url = url.concat(key);
    }

    @When("the user executes a Get")
    public void theUserExecutesAGet() {
        this.requestSpecification = RestAssured.given();
        response = this.requestSpecification.get(url);
    }

    @Then("The response will be a Individual")
    public void theResponseWillBeAIndividual() {
        this.individualDTO = response.getBody().as(IndividualDTO.class);
    }

    @And("the StatusCode is {int}")
    public void theStatusCodeIsHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }

    @And("the Name is {string}")
    public void theNameIs(String name) {
        Assertions.assertEquals(individualDTO.getName(),name);
    }

    @And("the MotherName is {string}")
    public void theMotherNameIs(String motherName) {
        Assertions.assertEquals(individualDTO.getMotherName(),motherName);
    }

    @And("the Rg is {string}")
    public void theRgIs(String rg) {
        Assertions.assertEquals(individualDTO.getRg(),rg);
    }

    @And("the BirthDate is {string}")
    public void theBirthDateIs(String birthDate) {
        Assertions.assertEquals(individualDTO.getBirthDate().toString(),birthDate);
    }

    @And("the Active is {string}")
    public void theActiveIs(String active) {
        Assertions.assertEquals(individualDTO.getActive().toString(),active);
    }

    @And("the District is {string}")
    public void theDistrictIs(String district) {
        this.addressDTO = individualDTO.getAddressesDTO().get(0);
        Assertions.assertEquals(addressDTO.getDistrict(),district);
    }

    @And("the Number is {string}")
    public void theNumberIs(String number) {
        Assertions.assertEquals(addressDTO.getNumber(),number);
    }

    @And("the City is {string}")
    public void theCityIs(String city) {
        Assertions.assertEquals(addressDTO.getCity(),city);
    }

    @And("the State is {string}")
    public void theStateIs(String state) {
        Assertions.assertEquals(addressDTO.getState(),state);
    }

    @And("the ZipCode is {string}")
    public void theZipCodeIs(String zipCode) {
        Assertions.assertEquals(addressDTO.getZipCode(),zipCode);
    }

    @And("the Main is {string}")
    public void theMainIs(String main) {
        Assertions.assertEquals(addressDTO.getMain().toString(),main);
    }
}
