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

public class IndividualDeleteByDocument {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll()
    private void setUp(){
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("url is {string}")
    public void urlIs(String url) {
        this.url = this.url.concat(url);
    }

    @And("document is {string}")
    public void documentIs(String document) {
        this.url = this.url.concat(document);
    }

    @When("the user executes a Delete by document")
    public void theUserExecutesADeleteByDocument() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(url);
    }

    @Then("the server should return {string}")
    public void theServerShouldReturn(String response) {
        Assertions.assertEquals(response,this.response.getBody().path("response"));
    }

    @And("the statusCode is {int}")
    public void theStatusCodeIsHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }
}
