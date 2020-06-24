package br.com.invillia.projetoPaloAlto.integration.individual;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
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

public class IndividualDeleteById {

    private String url = "/individual";

    private RequestSpecification requestSpecification;

    private Response response;

    @BeforeAll
    private void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Given("Url is {string}")
    public void urlIs(String url) {
        this.url = this.url.concat(url);
    }

    @And("Id is {string}")
    public void keyIs(String id) {
        this.url = this.url.concat(id);
    }

    @When("The user executes a Delete by id")
    public void theUserExecutesADelete() {
        this.requestSpecification = given();
        this.response = this.requestSpecification.delete(url);
    }

    @Then("The server should return a {string}")
    public void theServerShouldReturnA(String key) {
        Assertions.assertEquals(key,response.getBody().jsonPath().getString("response"));
    }

    @And("The StatusCode is {int}")
    public void theStatusCodeIsHttpStatusCode(int httpStatusCode) {
        Assertions.assertEquals(httpStatusCode,this.response.getStatusCode());
    }
}
