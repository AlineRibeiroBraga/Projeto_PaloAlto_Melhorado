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

public class IndividualFindById {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTO;

    @BeforeAll()
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("The url {string}")
    public void theId(String url) {
        this.url = this.url.concat(url);
    }

    @And("The key: {string}")
    public void theUrl(String key)   {
        url = url.concat(key);
    }

    @When("User executes a Get")
    public void userExecutesAGet() {
        this.requestSpecification = RestAssured.given();
        response = this.requestSpecification.get(url);
    }

    @Then("The server should return a Individual")
    public void theServerShouldReturnAIndividual() {
        this.individualDTO = response.getBody().as(IndividualDTO.class);
    }

    @And("The statusCode is {int}")
    public void theStatusCodeIsHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }

    @And("Name is {string}")
    public void theNameIs(String name) {
        Assertions.assertEquals(individualDTO.getName(),name);
    }

    @And("MotherName is {string}")
    public void theMotherNameIs(String motherName) {
        Assertions.assertEquals(individualDTO.getMotherName(),motherName);
    }

    @And("Document is {string}")
    public void theDocumentIs(String document) {
        Assertions.assertEquals(individualDTO.getDocument(),document);
    }

    @And("Rg is {string}")
    public void theRgId(String rg) {
        Assertions.assertEquals(individualDTO.getRg(),rg);
    }

    @And("BirthDate is {string}")
    public void theBirthDateIs(String birthDate) {
        Assertions.assertEquals(individualDTO.getBirthDate().toString(),birthDate);
    }

    @And("Active is {string}")
    public void theActiveIsActive(String active) {
        Assertions.assertEquals(individualDTO.getActive().toString(),active);
    }

    @And("District is {string}")
    public void theDistrictIs(String district) {
        this.addressDTO = individualDTO.getAddressesDTO().get(0);
        Assertions.assertEquals(addressDTO.getDistrict(),district);
    }

    @And("Number is {string}")
    public void theNumberIs(String number) {
        Assertions.assertEquals(addressDTO.getNumber(),number);
    }

    @And("City is {string}")
    public void theCityIs(String city) {
        Assertions.assertEquals(addressDTO.getCity(),city);
    }

    @And("State is {string}")
    public void theStateIs(String state) {
        Assertions.assertEquals(addressDTO.getState(),state);
    }

    @And("ZipCode is {string}")
    public void theZipCodeIs(String zipCode) {
        Assertions.assertEquals(addressDTO.getZipCode(),zipCode);
    }

    @And("Main is {string}")
    public void theMainIs(String main) {
        Assertions.assertEquals(addressDTO.getMain().toString(), main);
    }
}