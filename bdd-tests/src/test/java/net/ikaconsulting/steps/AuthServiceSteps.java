package net.ikaconsulting.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import service.AuthService;
import stub.StubServiceHandler;
import utility.EnvironmentConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthServiceSteps {
    private Response response;
    private final EnvironmentConstants env = new EnvironmentConstants();

    public AuthServiceSteps() {
        new StubServiceHandler(env.HOST).withAuthStub(env.AUTH_SERVICE).start();
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        response = new AuthService(env.urls()).getToken("fakeId", "fakePassword");
    }

    @Then("I will received a token")
    public void iWillReceivedAToken() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        assertEquals("fake_token", response.jsonPath().getString("token"), "Token");
    }

    @When("I enter invalid credentials")
    public void iEnterInvalidCredentials() {
        response = new AuthService(env.urls()).getToken("invalidClientId", "invalidClientPassword");
    }

    @When("I enter missing credentials")
    public void iEnterMissingCredentialDetails() {
        response = new AuthService(env.urls()).getToken("missingHeader");
    }

    @Then("I will receive an invalid error response")
    public void iWillReceiveAnInvalidErrorResponse() {
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("Error", json.getString("status"), "Message type");
        assertEquals("Invalid Credentials", json.getString("reason"), "Message reason");
    }

    @Then("I will receive an missing error response")
    public void iWillReceiveAnMissingErrorResponse() {
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("Error", json.getString("status"), "Message type");
        assertEquals("Missing Credentials", json.getString("reason"), "Message reason");
    }
}
