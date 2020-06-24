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

public class IndividualUpdateById {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTO;

    @BeforeAll()
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("Any individual")
    public void anyIndividual() {
        this.individualDTO = new IndividualDTO();
    }

    @And("the key is {string}")
    public void theKeyIs(String key) {
        this.url = this.url.concat("/").concat(key);
    }

    @And("the name is {string}")
    public void theNameIs(String name) {
        individualDTO.setName(name);
    }

    @And("the motherName is {string}")
    public void theMotherNameIs(String motherName) {
        individualDTO.setMotherName(motherName);
    }

    @And("the document is {string}")
    public void theDocumentIs(String document) {
        individualDTO.setDocument(document);
    }

    @And("the rg id {string}")
    public void theRgId(String rg) {
        individualDTO.setRg(rg);
    }

    @And("the birthDate is {string}")
    public void theBirthDateIs(String birthDate) {
        individualDTO.setBirthDate(LocalDate.parse(birthDate));
    }

    @And("the active is {string}")
    public void theActiveIs(String active) {
        individualDTO.setActive(Boolean.valueOf(active));
    }

    @And("Any address")
    public void anyAddress() {
        List<AddressDTO> addressesDTO = new ArrayList<>();
        this.addressDTO = new AddressDTO();
        addressesDTO.add(addressDTO);
        this.addressDTO.setIndividualDTO(individualDTO);
        this.individualDTO.setAddressesDTO(addressesDTO);
    }

    @And("the district is {string}")
    public void theDistrictIs(String district) {
        addressDTO.setDistrict(district);
    }

    @And("the number is {string}")
    public void theNumberIs(String number) {
        addressDTO.setNumber(number);
    }

    @And("the city is {string}")
    public void theCityIs(String city) {
        addressDTO.setCity(city);
    }

    @And("the state is {string}")
    public void theStateIs(String state) {
        addressDTO.setState(state);
    }

    @And("the zipCode is {string}")
    public void theZipCodeIs(String zipCode) {
        addressDTO.setZipCode(zipCode);
    }

    @And("the main is {string}")
    public void theMainIs(String main) {
        addressDTO.setMain(Boolean.valueOf(main));
    }

    @When("User executes the update by id")
    public void userExecutesAPutById() {
        this.requestSpecification = given().contentType(ContentType.JSON).with().body(individualDTO);
        this.response = this.requestSpecification.put(url);
    }

    @Then("the server should return a {int}")
    public void theServerShouldReturnAHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode, response.getStatusCode());
    }

    @And("the id {string}")
    public void theId(String key) {
        Assertions.assertEquals(key, response.getBody().jsonPath().getString("response"));
    }
}
