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

import static io.restassured.RestAssured.given;

public class DeleteWithoutIndividuals {

    private String url = "/legal-entity";
    private String secondDocument;
    private String id;

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private Faker faker = new Faker();

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("Any url {string}")
    public void anyUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("Any key {string}")
    public void anyKey(String key) {
        this.url = this.url.concat(key);
    }

    @And("Verify if this Legal entity is registered {string},{string},{string},{string},{string},{string},{string}," +
            "{string},{string},{string}")
    public void verifyIfThisLegalEntityIsRegistered(String name, String tradeName, String document, String active,
                                                    String district, String number, String city, String state,
                                                    String zipCode, String main) {
        this.secondDocument = document;
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(this.url);

        boolean flag;

        if(response.getStatusCode() == 200){
            this.legalEntityDTO = this.response.getBody().as(LegalEntityDTO.class);

            if(!this.legalEntityDTO.getDocument().equals(document) || !this.legalEntityDTO.getActive()){

                do {
                    flag = false;

                    this.url = "legal-entity/document/";
                    this.url = this.url.concat(document);

                    requestSpecification = given();
                    response = requestSpecification.get(this.url);

                    if (response.getStatusCode() == 200) {
                        document = faker.number().digits(14);
                        this.secondDocument = document;
                        flag = true;
                    }
                }while(flag);

                legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                legalEntityDTO.getAddressesDTO().add(createAddress(district,number,city,state,zipCode,main));
                legalEntityDTO.getAddressesDTO().get(0).setLegalEntityDTO(legalEntityDTO);

                endPointValidation(legalEntityDTO);
            }
        }
        else if(response.getStatusCode() == 404){

            do {
                flag = false;

                this.url = "legal-entity/document/";
                this.url = this.url.concat(document);

                requestSpecification = given();
                response = requestSpecification.get(this.url);

                if (response.getStatusCode() == 200) {
                    document = faker.number().digits(14);
                    this.secondDocument = document;
                    flag = true;
                }
            }while(flag);

            legalEntityDTO = createLegalEntity(name,tradeName,document,active);
            legalEntityDTO.getAddressesDTO().add(createAddress(district,number,city,state,zipCode,main));
            legalEntityDTO.getAddressesDTO().get(0).setLegalEntityDTO(legalEntityDTO);
            endPointValidation(legalEntityDTO);
        }
    }

    @When("The user makes a Delete")
    public void theUserMakesADelete() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(this.url);
    }

    @Then("A statusCode is {int}")
    public void aStatusCodeIsHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    private void endPointValidation(LegalEntityDTO legalEntityDTO){

        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
        Response response = requestSpecification.post("/legal-entity");

        this.id = response.getBody().path("response");
        this.url = "/legal-entity/";
        this.url = this.url.concat(id);
    }

    private AddressDTO createAddress(String district, String number, String city, String state, String zipCode, String main) {

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setMain(Boolean.valueOf(main));
        addressDTO.setDistrict(district);
        addressDTO.setNumber(number);
        addressDTO.setCity(city);
        addressDTO.setState(state);
        addressDTO.setZipCode(zipCode);

        return addressDTO;
    }

    private LegalEntityDTO createLegalEntity(String name, String tradeName, String document, String active) {

        LegalEntityDTO legalEntityDTO = new LegalEntityDTO();

        legalEntityDTO.setName(name);
        legalEntityDTO.setTradeName(tradeName);
        legalEntityDTO.setDocument(document);
        legalEntityDTO.setActive(Boolean.valueOf(active));
        legalEntityDTO.setAddressesDTO(new ArrayList<>());

        return legalEntityDTO;
    }
}
