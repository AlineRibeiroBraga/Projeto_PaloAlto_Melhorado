package br.com.invillia.projetoPaloAlto.integration.legalEntity;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class DeleteWithIndividuals {

    private String url = "/legal-entity";
    private String id;
    private String secondDocument;

    private Faker faker = new Faker();

    private RequestSpecification requestSpecification;

    private Response response;

    private LegalEntityDTO legalEntityDTO;

    private AddressDTO addressDTO;

    private IndividualDTO individualDTO;

    private AddressDTO addressDTOI;

    @Given("any url {string}")
    public void anyUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("any key {string}")
    public void anyKey(String key) {
        this.url = this.url.concat(key);
    }

    @And("Verify if this Legal entity is registered {string},{string},{string},{string},{string},{string},{string}," +
        "{string},{string},{string}, {string},{string},{string},{string}, {string}, {string}, {string},{string}," +
            "{string},{string},{string},{string}")
    public void verifyIfThisLegalEntityIsRegistered(String name, String tradeName, String document, String active,
                                                    String district, String number, String city, String state, String zipCode, String main,
                                                    String nameI, String documentI, String rgI, String motherNameI, String birthDateI,
                                                    String activeI, String districtI, String numberI, String cityI, String stateI,
                                                    String zipCodeI, String mainI){

        this.requestSpecification = given();
        this.response = this.requestSpecification.get(this.url);

        int flag;

        if(response.getStatusCode() == 200){
            this.legalEntityDTO = this.response.getBody().as(LegalEntityDTO.class);

            if(!this.legalEntityDTO.getDocument().equals(document)){

                do {
                    flag = 0;

                    this.url = "legal-entity/document/";
                    this.url = this.url.concat(document);

                    requestSpecification = given();
                    response = requestSpecification.get(this.url);

                    if (response.getStatusCode() == 200) {
                        document = faker.number().digits(14);
                        this.secondDocument = document;
                        flag = 1;
                    }
                }while(flag == 1);

                legalEntityDTO = createLegalEntity(name,tradeName,document,active);
                legalEntityDTO.getAddressesDTO().add(createAddress(district,number,city,state,zipCode,main));
                legalEntityDTO.getAddressesDTO().get(0).setLegalEntityDTO(legalEntityDTO);
                legalEntityDTO.setIndividualsDTO(new ArrayList<>());
                individualDTO = createIndividual(nameI,documentI,rgI,motherNameI,birthDateI,activeI);
                individualDTO.getAddressesDTO().add(createAddress(districtI,numberI,cityI,stateI,zipCodeI,mainI));
                individualDTO.getAddressesDTO().get(0).setIndividualDTO(individualDTO);
                legalEntityDTO.getIndividualsDTO().add(individualDTO);

                endPointValidation(legalEntityDTO);
            }
        }
        else if(response.getStatusCode() == 404){

            do {
                flag = 0;

                this.url = "legal-entity/document/";
                this.url = this.url.concat(document);

                requestSpecification = given();
                response = requestSpecification.get(this.url);

                if (response.getStatusCode() == 200) {
                    document = faker.number().digits(14);
                    this.secondDocument = document;
                    flag = 1;
                }
            }while(flag == 1);

            legalEntityDTO = createLegalEntity(name,tradeName,document,active);
            legalEntityDTO.getAddressesDTO().add(createAddress(district,number,city,state,zipCode,main));
            legalEntityDTO.getAddressesDTO().get(0).setLegalEntityDTO(legalEntityDTO);
            individualDTO = createIndividual(nameI,documentI,rgI,motherNameI,birthDateI,activeI);
            individualDTO.getAddressesDTO().add(createAddress(districtI,numberI,cityI,stateI,zipCodeI,mainI));
            individualDTO.getAddressesDTO().get(0).setIndividualDTO(individualDTO);
            legalEntityDTO.getIndividualsDTO().add(individualDTO);
            endPointValidation(legalEntityDTO);
        }
    }

    @When("the user makes a Delete")
    public void theUserMakesADelete() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(this.url);
    }

    @Then("The server must return the statusCode {int}")
    public void theServerMustReturnTheStatusCodeHttpStatusCode(int httpStatusCode) {
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
        legalEntityDTO.setIndividualsDTO(new ArrayList<>());

        return legalEntityDTO;
    }
}
