package net.ikaconsulting.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.client.Client;
import model.client.ClientRequest;
import model.client.Preferences;
import org.apache.http.HttpStatus;
import service.ClientService;
import stub.StubServiceHandler;
import utility.EnvironmentConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServiceMarketSteps {
    private static final EnvironmentConstants env = new EnvironmentConstants();
    private Response response;

    public ClientServiceMarketSteps() {
        new StubServiceHandler(env.HOST).withAuthStub(env.AUTH_SERVICE).withClientStub(env.CLIENT_SERVICE).start();
    }

    @When("I add a client to MarketX")
    public void iAddAClientToMarketX() {
        ClientRequest clientRequest = new ClientRequest()
                .market("MarketX")
                .externalClientId("X-123")
                .client(new Client().withMandatory("John", "Doe"))
                .preferences(new Preferences());

        response = new ClientService(env.urls()).createClient(clientRequest);
    }

    @Then("I will be informed the client is created in MarketX")
    public void iWillBeInformedTheClientIsCreatedInMarketX() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Client created", json.getString("message"), "message");
        assertEquals("X-123", json.getString("result.clientId"), "Client id");
    }

    @When("I add a client to MarketX with missing mandatory data")
    public void iAddAClientToMarketXWithMissingMandatoryData() {
        ClientRequest clientRequest = new ClientRequest()
                .market("MarketX")
                .externalClientId("X-123")
                .client(new Client().EmailAddress("missingMandatory@test.test"))
                .preferences(new Preferences());

        response = new ClientService(env.urls()).createClient(clientRequest);
    }

    @Then("I will be informed the client was not created")
    public void iWillBeInformedTheClientWasNotCreated() {
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "status");
        assertEquals("Request Missing Fields: foreName and surName", json.getString("message"), "message");
    }

    @When("I add a client to MarketY with identification details")
    public void iAddAClientToMarketYWithIdentificationDetails() {
        ClientRequest clientRequest = new ClientRequest()
                .market("MarketY")
                .externalClientId("Y-123")
                .client(new Client().withMandatory("James", "Doe").withIdMandatory())
                .preferences(new Preferences());

        response = new ClientService(env.urls()).createClient(clientRequest);
    }

    @Then("I will be informed the client is created in MarketY")
    public void iWillBeInformedTheClientIsCreatedInMarketY() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Client created", json.getString("message"), "message");
        assertEquals("Y-123", json.getString("result.clientId"), "Client id");
    }

    @When("I add a client to MarketY without identification details")
    public void iAddAClientToMarketYWithoutIdentificationDetails() {
        ClientRequest clientRequest = new ClientRequest()
                .market("MarketY")
                .externalClientId("Y-123")
                .client(new Client().withMandatory("Jack", "Doe").EmailAddress("missingMandatory@test.test"))
                .preferences(new Preferences());

        response = new ClientService(env.urls()).createClient(clientRequest);
    }

    @Then("I will be informed the client was not created in MarketY")
    public void iWillBeInformedTheClientWasNotCreatedInMarketY() {
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "status");
        assertEquals("Request Missing Fields: identificationNumber and identificationType", json.getString("message"), "message");
    }
}
