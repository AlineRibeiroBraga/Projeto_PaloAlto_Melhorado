package br.com.invillia.projetoPaloAlto.integration.legalEntity;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
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

public class InsertWithoutIndividuals {

    private String url = "/legal-entity";

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private RequestSpecification requestSpecification;

    private Response response;

    private Faker faker = new Faker();

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("A Legal Entity")
    public void aLegalEntity() {
        this.legalEntityDTO = new LegalEntityDTO();
    }

    @And("The name's Legal Entity is {string}")
    public void theNameSLegalEntityIs(String name) {
        this.legalEntityDTO.setName(name);
    }

    @And("The trade Name's Legal Entity is {string}")
    public void theTradeNameSLegalEntityIs(String tradeName) {
        this.legalEntityDTO.setTradeName(tradeName);
    }

    @And("The document's Legal Entity is {string}")
    public void theDocumentSLegalEntityIsIs(String document) {
        this.legalEntityDTO.setDocument(document);
    }

    @And("The active's Legal Entity {string}")
    public void theActiveSLegalEntity(String active) {
        this.legalEntityDTO.setActive(Boolean.valueOf(active));
    }

    @And("A address")
    public void aAddress() {
        this.addressDTO = new AddressDTO();
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addressesDTO.add(addressDTO);
        this.legalEntityDTO.setAddressesDTO(addressesDTO);
    }

    @And("The district's Legal Entity is {string}")
    public void theDistrictSLegalEntityIs(String district) {
        this.addressDTO.setDistrict(district);
    }

    @And("The number's Legal Entity is {string}")
    public void theNumberSLegalEntityIs(String number) {
        this.addressDTO.setNumber(number);
    }

    @And("The city's Legal Entity is {string}")
    public void theCitySLegalEntityIs(String city) {
        this.addressDTO.setCity(city);
    }

    @And("The state's Legal Entity is {string}")
    public void theStateSLegalEntityIs(String state) {
        this.addressDTO.setState(state);
    }

    @And("The zipCode's Legal Entity is {string}")
    public void theZipCodeSLegalEntityIs(String zipCode) {
        this.addressDTO.setZipCode(zipCode);
    }

    @And("The main's Legal Entity is {string}")
    public void theMainSLegalEntityIs(String main) {
        this.addressDTO.setMain(Boolean.valueOf(main));
    }

    @When("User executes a post with {string}")
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

    @Then("The server should return {int}")
    public void theServerShouldReturnThisHttpStatusCode(int httpStatusCode) {
//        Assertions.assertEquals(htt);
    }
}
