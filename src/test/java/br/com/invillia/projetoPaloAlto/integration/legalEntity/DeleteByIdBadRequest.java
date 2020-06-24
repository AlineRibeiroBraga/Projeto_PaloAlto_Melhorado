package br.com.invillia.projetoPaloAlto.integration.legalEntity;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class DeleteByIdBadRequest {

    private String url = "/legal-entity";
    private String id;

    private Faker faker = new Faker();

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("The URL {string}")
    public void theURL(String url) {
        this.url = this.url.concat(url);
    }

    @And("The KEY {string}")
    public void theKEY(String key) {
        this.url = this.url.concat(key);
        this.id = key;
    }

    @And("Verify if this Legal Entity with partners exists")
    public void verifyIfThisLegalEntityExists() {

        boolean flg;

        do{
            flg = false;
            this.requestSpecification = given();
            this.response = this.requestSpecification.delete(this.url);

            if(this.response.getStatusCode() == 200){
                flg = true;
                this.url = "/legal-entity/";

                if(this.id.length() == 14){
                    this.id = faker.number().digits(14);
                }
                else{
                    this.id = faker.number().digit();
                }

                this.url = this.url.concat(id);
            }
        }while(flg);
    }

    @When("The User makes a Delete")
    public void theUserMakesADelete() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(this.url);
    }

    @Then("The server must return a {int}")
    public void theServerMustReturnAStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    @And("The Message {string}")
    public void theMessage(String message){
        Assertions.assertEquals(message,this.response.getBody().path("message"));
    }
}
