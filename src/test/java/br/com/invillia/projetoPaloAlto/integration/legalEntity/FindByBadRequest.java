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

public class FindByBadRequest {

    private String url = "/legal-entity";

    private String key;

    private Faker faker = new Faker();

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("URL {string}")
    public void url(String url) {
        this.url = this.url.concat(url);
        this.key = url;
    }

    @And("KEY {string}")
    public void key(String key) {
        this.url = this.url.concat(key);
    }

    @When("The user makes a Get")
    public void theUserMakesAGet() {


        do {
            this.requestSpecification = given();
            this.response = this.requestSpecification.get(this.url);

            if(this.response.getStatusCode() == 200){
                int tam = 14;

                if(key.length() == tam){
                    this.url = this.key.concat(faker.number().digits(14));
                }
                else{
                    this.url = this.key.concat(faker.number().digit());
                }
            }

        }while(this.response.getStatusCode() == 200);
    }

    @Then("The server must return {int}")
    public void theServerMustReturn(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    @And("the Message {string}")
    public void theMessage(String message) {
        Assertions.assertEquals(message,this.response.getBody().path("message"));
    }
}
