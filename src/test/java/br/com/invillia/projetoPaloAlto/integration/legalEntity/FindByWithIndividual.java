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

import java.time.LocalDate;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class FindByWithIndividual {

    private String url = "/legal-entity";
    private String secondDocument;

    private Faker faker = new Faker();

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTOI;

    private String id;

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("a url is {string}")
    public void aUrlIs(String url) {
        this.url = this.url.concat(url);
    }

    @And("a key is {string}")
    public void aKeyIs(String key) {
        this.url = this.url.concat(key);
    }

    @And("Verify if this legal entity is registered {string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string},{string}, {string}, {string}, {string},{string},{string},{string},{string},{string}")
    public void verifyIfThisLegalEntityIsRegistered(String name, String tradeName, String document, String active,
                                String district, String number, String city, String state, String zipCode, String main,
                                String nameI, String documentI, String rgI, String motherNameI, String birthDateI,
                                String activeI, String districtI, String numberI, String cityI, String stateI,
                                                    String zipCodeI, String mainI) {
        boolean flg;
        secondDocument = document;
        do {
            flg = false;
            this.requestSpecification = given();
            this.response = this.requestSpecification.get(this.url);

            if (this.response.getStatusCode() == 200) {

                this.legalEntityDTO = this.response.as(LegalEntityDTO.class);

                if (!this.legalEntityDTO.getDocument().equals(document)) {
                    this.url = "/legal-entity/document/";
                    this.url = this.url.concat(document);
                    this.requestSpecification = given();
                    this.response = this.requestSpecification.get(url);

                    if (this.response.getStatusCode() == 200) {
                        document = faker.number().digits(14);
                        secondDocument = document;
                        this.url = "/legal-entity/document/";
                        this.url = url.concat(document);
                        flg = true;
                    } else {
                        this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                        this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                        this.addressDTO.setLegalEntityDTO(legalEntityDTO);
                        this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);
                        this.legalEntityDTO.setIndividualsDTO(new ArrayList<>());

                        this.individualDTO = createIndividual(nameI, documentI, rgI, motherNameI, birthDateI, activeI);
                        this.addressDTOI = createAddress(districtI, numberI, cityI, stateI, zipCodeI, mainI);
                        this.addressDTOI.setIndividualDTO(this.individualDTO);
                        this.individualDTO.getAddressesDTO().add(this.addressDTOI);
                        this.legalEntityDTO.getIndividualsDTO().add(this.individualDTO);

                        this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
                        this.response = this.requestSpecification.post("/legal-entity");
                        this.id = response.getBody().path("response");
                        this.url = "/legal-entity/";
                        this.url = this.url.concat(id);
                        this.requestSpecification = given();
                        this.response = this.requestSpecification.get(url);
                        this.legalEntityDTO = this.response.as(LegalEntityDTO.class);
                    }
                }
            } else {
                this.url = "/legal-entity/document/";
                this.url = this.url.concat(document);
                this.requestSpecification = given();
                this.response = this.requestSpecification.get(url);

                if (this.response.getStatusCode() == 200) {
                    document = faker.number().digits(14);
                    secondDocument = document;
                    this.url = "/legal-entity/document/";
                    this.url = this.url.concat(document);
                    flg = true;
                }
                else {
                    this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                    this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                    this.addressDTO.setLegalEntityDTO(legalEntityDTO);
                    this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);
                    this.legalEntityDTO.setIndividualsDTO(new ArrayList<>());

                    this.individualDTO = createIndividual(nameI, documentI, rgI, motherNameI, birthDateI, activeI);
                    this.addressDTOI = createAddress(districtI, numberI, cityI, stateI, zipCodeI, mainI);
                    this.addressDTOI.setIndividualDTO(this.individualDTO);
                    this.individualDTO.getAddressesDTO().add(this.addressDTOI);
                    this.legalEntityDTO.getIndividualsDTO().add(this.individualDTO);

                    this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
                    this.response = this.requestSpecification.post("/legal-entity");
                    this.id = response.getBody().path("response");
                    this.url = "/legal-entity/";
                    this.url = this.url.concat(id);
                    this.requestSpecification = given();
                    this.response = this.requestSpecification.get(url);
                    this.legalEntityDTO = this.response.as(LegalEntityDTO.class);
                }
            }
        } while (flg);
    }

    @When("the user executes a get")
    public void theUserExecutesAGet() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(this.url);
    }

    @Then("the server should return a Legal Entity")
    public void theServerShouldReturnALegalEntity() {
        this.legalEntityDTO = this.response.as(LegalEntityDTO.class);
    }

    @And("any name {string}")
    public void anyName(String name) {
        Assertions.assertEquals(name,this.legalEntityDTO.getName());
    }

    @And("any trade name {string}")
    public void anyTradeName(String tradeName) {
        Assertions.assertEquals(tradeName,this.legalEntityDTO.getTradeName());
    }

    @And("any document {string}")
    public void anyDocument(String document) {

        Assertions.assertEquals(secondDocument,this.legalEntityDTO.getDocument());
    }

    @And("any active {string}")
    public void anyActive(String active) {
        Assertions.assertEquals(active,this.legalEntityDTO.getActive().toString());
    }

    @And("this address")
    public void thisAddress() {
        this.addressDTO = this.legalEntityDTO.getAddressesDTO().get(0);
    }

    @And("any district's Legal Entity is {string}")
    public void anyDistrictSLegalEntityIs(String district) {
        Assertions.assertEquals(district,addressDTO.getDistrict());
    }

    @And("any number's Legal Entity is {string}")
    public void anyNumberSLegalEntityIs(String number) {
        Assertions.assertEquals(number,addressDTO.getNumber());
    }

    @And("any city's Legal Entity is {string}")
    public void anyCitySLegalEntityIs(String city) {
        Assertions.assertEquals(city,addressDTO.getCity());
    }

    @And("any state's Legal Entity is {string}")
    public void anyStateSLegalEntityIs(String state) {
        Assertions.assertEquals(state,addressDTO.getState());
    }

    @And("any zipCode's Legal Entity is {string}")
    public void anyZipCodeSLegalEntityIs(String zipCode) {
        Assertions.assertEquals(zipCode,addressDTO.getZipCode());
    }

    @And("any main's Legal Entity is {string}")
    public void anyMainSLegalEntityIs(String main) {
        Assertions.assertEquals(main,addressDTO.getMain().toString());
    }

    @And("this Individual")
    public void thisIndividual() {
        this.individualDTO = legalEntityDTO.getIndividualsDTO().get(0);
    }

    @And("this name {string}")
    public void thisName(String name) {
        Assertions.assertEquals(name,this.individualDTO.getName());
    }

    @And("this document {string}")
    public void thisDocument(String document) {
        Assertions.assertEquals(document, this.individualDTO.getDocument());
    }

    @And("this rg {string}")
    public void thisRg(String rg) {
        Assertions.assertEquals(rg,this.individualDTO.getRg());
    }

    @And("this motherName {string}")
    public void thisMotherName(String motherName) {
        Assertions.assertEquals(motherName,this.individualDTO.getMotherName());
    }

    @And("this birthDate {string}")
    public void thisBirthDate(String birthDate) {
        Assertions.assertEquals(birthDate,this.individualDTO.getBirthDate().toString());
    }

    @And("this active {string}")
    public void thisActive(String active) {
        Assertions.assertEquals(active, this.individualDTO.getActive().toString());
    }

    @And("Address")
    public void address() {
        this.addressDTOI = individualDTO.getAddressesDTO().get(0);
    }

    @And("this district {string}")
    public void thisDistrict(String district) {
        Assertions.assertEquals(district,this.addressDTOI.getDistrict());
    }

    @And("this number {string}")
    public void thisNumber(String number) {
        Assertions.assertEquals(number,this.addressDTOI.getNumber());
    }

    @And("this city {string}")
    public void thisCity(String city) {
        Assertions.assertEquals(city, this.addressDTOI.getCity());
    }

    @And("this state {string}")
    public void thisState(String state) {
        Assertions.assertEquals(state,this.addressDTOI.getState());
    }

    @And("this zipCode {string}")
    public void thisZipCode(String zipCode) {
        Assertions.assertEquals(zipCode, this.addressDTOI.getZipCode());
    }

    @And("this main {string}")
    public void thisMain(String main) {
        Assertions.assertEquals(main,this.addressDTOI.getMain().toString());
    }

    @And("this statusCode {int}")
    public void aStatusCodeHttpStatusCode(int httpStatusCode) {
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

    private IndividualDTO createIndividual(String nameI, String documentI, String rgI, String motherNameI,
                                           String birthDateI, String activeI) {

        IndividualDTO individualDTO = new IndividualDTO();

        individualDTO.setName(nameI);
        individualDTO.setDocument(documentI);
        individualDTO.setRg(rgI);
        individualDTO.setMotherName(motherNameI);
        individualDTO.setBirthDate(LocalDate.parse(birthDateI));
        individualDTO.setActive(Boolean.valueOf(activeI));
        individualDTO.setAddressesDTO(new ArrayList<>());

        return individualDTO;
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
