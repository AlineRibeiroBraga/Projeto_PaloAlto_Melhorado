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

public class IndividualDeleteByBadRequest {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll()
    private void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("the url {string}")
    public void theUrl(String url) {
        this.url = this.url.concat(url);
    }

    @And("the key: {string}")
    public void theKey(String key) {
        this.url = this.url.concat(key);
    }

    @When("The user executes a Delete")
    public void theUserExecutesADelete() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(url);
    }

    @Then("The server should return a statusCode {int}")
    public void theServerShouldReturnAStatusCodeHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }

    @And("a message {string}")
    public void aMessage(String message) {
        Assertions.assertEquals(message,this.response.then().extract().path("message"));
    }
}
