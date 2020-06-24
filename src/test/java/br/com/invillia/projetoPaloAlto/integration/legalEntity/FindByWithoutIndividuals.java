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

public class FindByWithoutIndividuals {

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private String url = "/legal-entity";
    private String secondDocument;
    private RequestSpecification requestSpecification;

    private Response response;

    private String id;

    private Faker faker = new Faker();

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("a url {string}")
    public void aUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("a key {string}")
    public void aKey(String key) {
        this.url = this.url.concat(key);
    }

    @And("Verify if this Legal Entity is registered {string},{string},{string},{string},{string},{string},{string}," +
            "{string},{string},{string}")
    public void verifyIfThisIsRegistered(String name, String tradeName, String document, String active, String district,
                                         String number, String city, String state, String zipCode, String main) {

        boolean flg;
        secondDocument = document;
        do{
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
                    }
                    else{
                        this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                        this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                        this.addressDTO.setLegalEntityDTO(legalEntityDTO);
                        this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);

                        this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
                        this.response = this.requestSpecification.post( "/legal-entity");
                        this.id = response.getBody().path("response");
                        this.url = "/legal-entity/";
                        this.url = this.url.concat(id);
                        this.requestSpecification = given();
                        this.response = this.requestSpecification.get(url);
                        this.legalEntityDTO = this.response.as(LegalEntityDTO.class);
                    }
                }
            }
            else{
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
                else{
                    this.legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                    this.addressDTO = createAddress(district,number,city,state,zipCode,main);
                    this.addressDTO.setLegalEntityDTO(legalEntityDTO);
                    this.legalEntityDTO.getAddressesDTO().add(this.addressDTO);

                    this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
                    this.response = this.requestSpecification.post( "/legal-entity");
                    this.id = response.getBody().path("response");
                    this.url = "/legal-entity/";
                    this.url = this.url.concat(id);
                    this.requestSpecification = given();
                    this.response = this.requestSpecification.get(url);
                    this.legalEntityDTO = this.response.as(LegalEntityDTO.class);
                }
            }
        }while(flg);
    }

    private void createLegalEntity(String name, String tradeName, String document, String active, String district,
                                   String number, String city, String state, String zipCode, String main) {

        LegalEntityDTO legalEntityDTO = new LegalEntityDTO();

        legalEntityDTO.setName(name);
        legalEntityDTO.setTradeName(tradeName);
        legalEntityDTO.setDocument(document);
        legalEntityDTO.setActive(Boolean.valueOf(active));
        legalEntityDTO.setAddressesDTO(new ArrayList<>());

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setMain(Boolean.valueOf(main));
        addressDTO.setDistrict(district);
        addressDTO.setNumber(number);
        addressDTO.setCity(city);
        addressDTO.setState(state);
        addressDTO.setZipCode(zipCode);

        legalEntityDTO.getAddressesDTO().add(addressDTO);
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
        Response response = requestSpecification.post("/legal-entity");

        this.id = response.getBody().path("response");
        this.url = "/legal-entity/";
        this.url = this.url.concat(id);
    }

    @When("The user executes a get")
    public void theUserExecutesAGet() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(this.url);
    }

    @Then("The server should return a Legal Entity")
    public void theServerShouldReturnALegalEntity() {
        this.legalEntityDTO = this.response.as(LegalEntityDTO.class);
    }

    @And("Any name {string}")
    public void anyName(String name) {
        Assertions.assertEquals(name,this.legalEntityDTO.getName());
    }

    @And("Any trade name {string}")
    public void anyTradeName(String tradeName) {
        Assertions.assertEquals(tradeName,this.legalEntityDTO.getTradeName());
    }

    @And("Any document {string}")
    public void anyDocument(String document) {
        Assertions.assertEquals(secondDocument,this.legalEntityDTO.getDocument());
    }

    @And("Any active {string}")
    public void anyActive(String active) {
        Assertions.assertEquals(active,this.legalEntityDTO.getActive().toString());
    }

    @And("any Address")
    public void anyAddress() {
       this.addressDTO = this.legalEntityDTO.getAddressesDTO().get(0);
    }

    @And("Any district's Legal Entity is {string}")
    public void anyDistrictSLegalEntityIs(String district) {
        Assertions.assertEquals(district,addressDTO.getDistrict());
    }

    @And("Any number's Legal Entity is {string}")
    public void anyNumberSLegalEntityIs(String number) {
        Assertions.assertEquals(number,addressDTO.getNumber());
    }

    @And("Any city's Legal Entity is {string}")
    public void anyCitySLegalEntityIs(String city) {
        Assertions.assertEquals(city,addressDTO.getCity());
    }

    @And("Any state's Legal Entity is {string}")
    public void anyStateSLegalEntityIs(String state) {
        Assertions.assertEquals(state,addressDTO.getState());
    }

    @And("Any zipCode's Legal Entity is {string}")
    public void anyZipCodeSLegalEntityIs(String zipCode) {
        Assertions.assertEquals(zipCode,addressDTO.getZipCode());
    }

    @And("Any main's Legal Entity is {string}")
    public void anyMainSLegalEntityIs(String main) {
        Assertions.assertEquals(main,addressDTO.getMain().toString());
    }

    @And("a statusCode {int}")
    public void theStatusCode(int httpStatusCode) {
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
