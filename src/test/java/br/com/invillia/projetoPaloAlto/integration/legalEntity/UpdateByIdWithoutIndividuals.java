package br.com.invillia.projetoPaloAlto.integration.legalEntity;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import com.github.javafaker.Faker;
import io.cucumber.java.Before;
import io.cucumber.java.bs.A;
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

public class UpdateByIdWithoutIndividuals {

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

    @Given("this url {string}")
    public void thisUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("this key {string}")
    public void thisKey(String key) {
        this.url = this.url.concat(key);
    }

    @And("Verify if This Legal Entity is registered {string},{string},{string},{string},{string},{string},{string}," +
            "{string},{string},{string}")
    public void verifyIfThisLegalEntityIsRegistered(String name, String tradeName, String document, String active,
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
                    this.response = this.requestSpecification.post("/legal-entity");
                    this.url = "/legal-entity/";
                    this.url = this.url.concat(this.response.getBody().path("response"));
                }
            }while (flg);
        }
    }

    @When("the user updates a Legal Entity")
    public void theUserUpdatesALegalEntity() {
        this.requestSpecification = given().contentType(ContentType.JSON).with().body(this.legalEntityDTO);
        this.response = this.requestSpecification.put(url);
    }

    @Then("server must return a {int}")
    public void serverMustReturnAHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    @And("Validate the fields")
    public void validateTheFields() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(url);
        this.legalEntityDTO = this.response.getBody().as(LegalEntityDTO.class);
    }

    @And("this name is {string}")
    public void thisNameIs(String name) {
        Assertions.assertEquals(name,this.legalEntityDTO.getName());
    }

    @And("this tradeName is {string}")
    public void thisTradeNameIs(String tradeName) {
        Assertions.assertEquals(tradeName,this.legalEntityDTO.getTradeName());
    }

    @And("this document is {string}")
    public void thisDocumentIs(String document) {
        Assertions.assertEquals(secondDocument,this.legalEntityDTO.getDocument());
    }

    @And("this active is {string}")
    public void thisActiveIs(String active) {
        Assertions.assertEquals(active,this.legalEntityDTO.getActive().toString());
    }

    @And("Any Address")
    public void anyAddress() {
        this.addressDTO = this.legalEntityDTO.getAddressesDTO().get(0);
    }

    @And("this district is {string}")
    public void thisDistrictIs(String district) {
        Assertions.assertEquals(district,addressDTO.getDistrict());
    }

    @And("this number is {string}")
    public void thisNumberIs(String number) {
        Assertions.assertEquals(number,addressDTO.getNumber());
    }

    @And("this city is {string}")
    public void thisCityIs(String city) {
        Assertions.assertEquals(city,addressDTO.getCity());
    }

    @And("this state is {string}")
    public void thisStateIs(String state) {
        Assertions.assertEquals(state,addressDTO.getState());
    }

    @And("this zipCode is {string}")
    public void thisZipCodeIs(String zipCode) {
        Assertions.assertEquals(zipCode,addressDTO.getZipCode());
    }

    @And("this main is {string}")
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
