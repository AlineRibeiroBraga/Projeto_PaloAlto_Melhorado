package br.com.invillia.projetoPaloAlto.integration.legalEntity;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class DeleteBadRequestFound {

    private String url = "/legal-entity";

    private RequestSpecification requestSpecification;

    private Response response;

    private LegalEntityDTO legalEntityDTO;

    private Faker faker = new Faker();

    @Given("This url {string}")
    public void thisUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("This KEY {string}")
    public void thisKEY(String key) {
        this.url = this.url.concat(key);
    }

    @And("verify if this Legal Entity with partners exists")
    public void verifyIfThisLegalEntityWithPartnersExists() {

        this.requestSpecification = given();
        this.response = this.requestSpecification.get(this.url);

        if(this.response.getStatusCode() == 200){
            this.legalEntityDTO = this.response.getBody().as(LegalEntityDTO.class);

            if(legalEntityDTO.getActive()){
                this.requestSpecification = given();
                this.response = this.requestSpecification.delete(this.url);
            }
        }
        else{
            boolean flg;

            do {
                flg = false;
                legalEntityDTO = createLegalEntity();

                this.requestSpecification = given().contentType(ContentType.JSON).with().body(legalEntityDTO);
                this.response = this.requestSpecification.post("/legal-entity");

                if (this.response.getStatusCode() == 200) {
                    String id = this.response.getBody().path("response");
                    this.url = "/legal-entity/";
                    this.url = this.url.concat(id);
                    this.requestSpecification = given();
                    this.response = this.requestSpecification.delete(this.url);
                }
                else{
                    flg = true;
                }
            }while(flg);
        }
    }

    private LegalEntityDTO createLegalEntity() {

        LegalEntityDTO legalEntityDTO = new LegalEntityDTO();

        legalEntityDTO.setName(faker.name().name());
        legalEntityDTO.setDocument(faker.number().digits(14));
        legalEntityDTO.setTradeName(faker.name().name());
        legalEntityDTO.setActive(true);
        legalEntityDTO.setAddressesDTO(new ArrayList<>());
        legalEntityDTO.getAddressesDTO().add(createAddressDTO());

        for(AddressDTO addressDTO : legalEntityDTO.getAddressesDTO()){
            addressDTO.setLegalEntityDTO(legalEntityDTO);
        }

        return legalEntityDTO;
    }

    public AddressDTO createAddressDTO() {

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setDistrict(faker.name().name());
        addressDTO.setNumber(faker.number().digit());
        addressDTO.setCity(faker.name().name());
        addressDTO.setState(faker.name().name());
        addressDTO.setZipCode(faker.code().gtin8());
        addressDTO.setMain(true);

        return addressDTO;
    }

    @When("user makes a Delete")
    public void userMakesADelete() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(this.url);
    }

    @Then("the server must return a {int}")
    public void theServerMustReturnAStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    @And("the message is {string}")
    public void theMessageIs(String message) {
        Assertions.assertEquals(message,this.response.getBody().path("message"));
    }
}
