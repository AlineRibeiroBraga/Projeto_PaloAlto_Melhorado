package br.com.invillia.projetoPaloAlto.integration.individual;

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

public class IndividualFindByBadRequest {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll()
    public void setUp(){
        RestAssured.baseURI = "http://local:8080";
    }

    @Given("Url {string}")
    public void url(String url) {
        this.url = this.url.concat(url);
    }

    @And("Key: {string}")
    public void key(String key) {
        this.url = this.url.concat(key);
    }

    @When("The user executes a Get")
    public void theUserExecutesAGet() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.get(url);
    }

    @Then("The server should return the statusCode {int}")
    public void theServerShouldReturnTheStatusCodeHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,response.getStatusCode());
    }

    @And("The message is {string}")
    public void theMessageIs(String message) {
        Assertions.assertEquals(message,response.then().extract().path("message"));
    }
}
