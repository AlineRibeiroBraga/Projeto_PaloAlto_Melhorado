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

public class UpdateByDocumentWithoutIndividuals {

    private String url = "/legal-entity";
    private String secondDocument;

    private RequestSpecification requestSpecification;

    private Response response;

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private Faker faker = new Faker();

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("this Url {string}")
    public void thisUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("this Key {string}")
    public void thisKey(String key) {
        this.url = this.url.concat(key);
    }

    @And("Verify if this Legal Entity is registered with {string},{string},{string},{string},{string},{string}," +
            "{string},{string},{string},{string}")
    public void verifyIfThisLegalEntityIsRegisteredWith(String name, String tradeName, String document, String active,
                                                        String district, String number, String city, String state,
                                                        String zipCode, String main) {

        secondDocument = document;
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(url);

        if(this.response.getStatusCode() == 200){
            this.legalEntityDTO = this.response.getBody().as(LegalEntityDTO.class);

            if(legalEntityDTO.getDocument().equals(document)){
                this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                this.addressDTO.setLegalEntityDTO(this.legalEntityDTO);
                this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);
            }
            else{
                boolean flg;

                do {
                    flg = false;

                    this.url = "/legal-entity/document/";
                    this.url = this.url.concat(document);

                    this.requestSpecification = given();
                    this.response = this.requestSpecification.get(url);

                    if (this.response.getStatusCode() == 200) {
                        document = faker.number().digits(14);
                        secondDocument = document;
                        flg = true;
                    } else {
                        this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                        this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                        this.addressDTO.setLegalEntityDTO(this.legalEntityDTO);
                        this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);

                        this.url = "/legal-entity";
                        this.requestSpecification = given().contentType(ContentType.JSON).with().body(this.legalEntityDTO);
                        this.response = this.requestSpecification.post(url);
                        this.url = this.url.concat(this.response.getBody().path("response"));
                    }
                }while (flg);
            }
        }
        else{
            boolean flg;

            do {
                flg = false;

                this.url = "/legal-entity/document/";
                this.url = this.url.concat(document);

                this.requestSpecification = given();
                this.response = this.requestSpecification.get(url);

                if (this.response.getStatusCode() == 200) {
                    document = faker.number().digits(14);
                    secondDocument = document;
                    flg = true;
                } else {
                    this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                    this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                    this.addressDTO.setLegalEntityDTO(this.legalEntityDTO);
                    this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);

                    this.requestSpecification = given().contentType(ContentType.JSON).with().body(this.legalEntityDTO);
                    this.url = "/legal-entity";
                    this.response = this.requestSpecification.post(url);
                    this.url = this.url.concat("/document");
                }
            }while (flg);
        }
    }

    @When("the user updates any Legal Entity")
    public void theUserUpdatesAnyLegalEntity() {
        this.requestSpecification = given().contentType(ContentType.JSON).with().body(this.legalEntityDTO);
        this.response = this.requestSpecification.put(url);
        System.out.println("/legal-entity/document");
    }

    @Then("server must return any {int}")
    public void serverMustReturnAnyHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    @And("Validates the fields")
    public void validatesTheFields() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(url);
        this.legalEntityDTO = this.response.getBody().as(LegalEntityDTO.class);
    }

    @And("This name is {string}")
    public void thisNameIs(String name) {
        Assertions.assertEquals(name,this.legalEntityDTO.getName());
    }

    @And("This tradeName is {string}")
    public void thisTradeNameIs(String tradeName) {
        Assertions.assertEquals(tradeName,this.legalEntityDTO.getTradeName());
    }

    @And("This document is {string}")
    public void thisDocumentIs(String secondDocument) {
        Assertions.assertEquals(secondDocument,this.legalEntityDTO.getDocument());
    }

    @And("This active is {string}")
    public void thisActiveIs(String active) {
        Assertions.assertEquals(active,this.legalEntityDTO.getActive().toString());
    }

    @And("Any Address's Legal Entity")
    public void anyAddressSLegalEntity() {
        this.addressDTO = this.legalEntityDTO.getAddressesDTO().get(0);
    }

    @And("This district is {string}")
    public void thisDistrictIs(String district) {
        Assertions.assertEquals(district,addressDTO.getDistrict());
    }

    @And("This number is {string}")
    public void thisNumberIs(String number) {
        Assertions.assertEquals(number,addressDTO.getNumber());
    }

    @And("This city is {string}")
    public void thisCityIs(String city) {
        Assertions.assertEquals(city,addressDTO.getCity());
    }

    @And("This state is {string}")
    public void thisStateIs(String state) {
        Assertions.assertEquals(state,addressDTO.getState());
    }

    @And("This zipCode is {string}")
    public void thisZipCodeIs(String zipCode) {
        Assertions.assertEquals(zipCode,addressDTO.getZipCode());
    }

    @And("This main is {string}")
    public void thisMainIs(String main) {
        Assertions.assertEquals(main,addressDTO.getMain().toString());
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

    private AddressDTO createAddress(String district, String number, String city, String state, String zipCode, String main) {

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setDistrict(district);
        addressDTO.setNumber(number);
        addressDTO.setCity(city);
        addressDTO.setState(state);
        addressDTO.setZipCode(zipCode);
        addressDTO.setMain(Boolean.valueOf(main));

        return addressDTO;
    }
}
