package net.ikaconsulting.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.client.Client;
import model.client.ClientRequest;
import model.client.Preferences;
import org.apache.http.HttpStatus;
import service.AuthService;
import service.ClientService;
import stub.StubServiceHandler;
import utility.EnvironmentConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServiceSteps {
    private static final EnvironmentConstants env = new EnvironmentConstants();
    private Response response;
    private ClientRequest clientRequest;

    public ClientServiceSteps() {
        new StubServiceHandler(env.HOST).withAuthStub(env.AUTH_SERVICE).withClientStub(env.CLIENT_SERVICE).start();
    }

    @When("I ping the Client service with no Auth token")
    public void iPingTheClientServiceWithNoAuthToken() {
        response = new ClientService(env.urls()).ping();
    }

    @Then("I will get a no token error response from the client service")
    public void iWillGetANoTokenErrorResponseFromTheClientService() {
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "Message status");
        assertEquals("Missing Token", json.getString("reason"), "Message reason");
    }

    @When("I ping the Client service with an Auth token")
    public void iPingTheClientServiceWithAnAuthToken() {
        response = new ClientService(env.urls()).ping(new AuthService(env.urls()).getToken());
    }

    @Then("I will get response with the service Up status")
    public void iWillGetResponseWithTheServiceUpStatus() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("client", json.getString("service"), "Service");
        assertEquals("UP", json.getString("status"), "Ping Status");
    }

    @Given("I have an existing client record")
    public void iHaveAnExistingClientRecord() {
        clientRequest = new ClientRequest()
                .market("MarketY")
                .externalClientId("Y-123")
                .client(new Client().withMandatory("Jack", "Doe").EmailAddress("missingMandatory@test.test"))
                .preferences(new Preferences());
    }

    @When("I update the client record")
    public void iUpdateTheClientRecord() {
        response = new ClientService(env.urls()).updateClient(clientRequest);
    }

    @Then("I will be informed the client is updated")
    public void iWillBeInformedTheClientIsUpdated() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Client Updated", json.getString("message"), "message");
    }
}
