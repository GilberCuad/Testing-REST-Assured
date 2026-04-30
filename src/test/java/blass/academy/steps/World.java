package blass.academy.steps;

import blass.academy.utils.Config;
import blass.academy.utils.Logs;
import blass.academy.utils.RequestFilter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Path;

public class World {
    RequestSpecification request;
    Response response;

    @Before
    public void setup() {
        request = RestAssured
                .given()
                .spec(buildRequestSpecification());
    }

    @After
    public void afterScenario(Scenario scenario) {
        Logs.info(
                "afterScenario: %s, status: %s",
                scenario.getName(),
                scenario.getStatus()
        );
    }

    public static RequestSpecification buildRequestSpecification() {
        return new RequestSpecBuilder()
                .addFilter(new RequestFilter())
                .setBaseUri(Config.get("base.url"))
                .setContentType(ContentType.JSON)
                .build();
    }

    @Given("Se usa el base path {string}")
    public void asignarBasePath(String url) {
        request.basePath(url);
    }

    @When("Se envia el request con el metodo {metodoHttp}")
    public void enviarRequest(Method metodo) {
        response = request.request(metodo);
    }

    @Then("Se verifica que el status code sea {int}")
    public void verificarStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @ParameterType("GET|POST|PUT|PATCH|DELETE")
    public Method metodoHttp(String metodo) {
        return Method.valueOf(metodo.toUpperCase());
    }

    @And("Se usa el payload {string}")
    public void seUsaElPayload(String archivo) {
        final var path = Path.of(Config.get("path.payloads"), archivo);
        request.body(path.toFile());
    }

    @And("Se asigna el path param {string}={string}")
    public void seAsignaElPathParam(String key, String value) {
        request.pathParams(key, value);
    }

    @And("Se asigna los query params {string}={string}")
    public void asignarQueryParams(String key, String value) {
        request.queryParam(key, value);
    }
}