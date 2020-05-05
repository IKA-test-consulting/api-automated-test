import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.client.Client;
import model.client.ClientRequest;
import model.client.Preferences;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.ClientService;
import stub.StubServiceHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utility.EnvironmentConstants.*;

class ClientServiceTest {
    @BeforeAll
    static void setup() {
        new StubServiceHandler(HOST).withAuthStub(AUTH_SERVICE).withClientStub(CLIENT_SERVICE).start();
    }

    @Test
    void requestWithNoTokenWillError() {
        System.out.println("5.2) A request with no token will be given an appropriate error response");

        Response response = new ClientService(urls).ping();
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "Message status");
        assertEquals("Missing Token", json.getString("reason"), "Message reason");
    }

    @Test
    void pingRequestWithTokenWillGetServiceStatus() {
        System.out.println("5.1) All requests to services must use a token retrieved from the Auth service");
        System.out.println("5.3) All services will provide their 'UP' status");

        Response response = new ClientService(urls).ping(new AuthService(urls).getToken());
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("client", json.getString("service"), "Service");
        assertEquals("UP", json.getString("status"), "Ping Status");
    }

    @Test
    void addClientForMarketXWithMandatoryData() {
        System.out.println("2.1.2) Creating a client with mandatory data will be given a success response");
        System.out.println("2.2.1) Client does not have additional mandatory fields");

        ClientRequest clientRequest = new ClientRequest()
                .market("MarketX")
                .externalClientId("X-123")
                .client(new Client().withMandatory("John", "Doe"))
                .preferences(new Preferences());

        Response response = new ClientService(urls).createClient(clientRequest);
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Client created", json.getString("message"), "message");
        assertEquals("X-123", json.getString("result.clientId"), "Client id");
    }

    @Test
    void addClientForMarketXWithMissingMandatoryData() {
        System.out.println("2.1.2) Creating a client with mandatory data will be given a success response");
        System.out.println("2.2.1) Client does not have additional mandatory fields");

        ClientRequest clientRequest = new ClientRequest()
                .market("MarketX")
                .externalClientId("X-123")
                .client(new Client().EmailAddress("missingMandatory@test.test"))
                .preferences(new Preferences());

        Response response = new ClientService(urls).createClient(clientRequest);

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "status");
        assertEquals("Request Missing Fields: foreName and surName", json.getString("message"), "message");
    }

    @Test
    void addClientForMarketYWithMandatoryData() {
        System.out.println("2.1.2) Creating a client with mandatory data will be given a success response");
        System.out.println("2.3.1) Client mandatory fields also include identification details");

        ClientRequest clientRequest = new ClientRequest()
                .market("MarketY")
                .externalClientId("Y-123")
                .client(new Client().withMandatory("James", "Doe").withIdMandatory())
                .preferences(new Preferences());

        Response response = new ClientService(urls).createClient(clientRequest);
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Client created", json.getString("message"), "message");
        assertEquals("Y-123", json.getString("result.clientId"), "Client id");
    }

    @Test
    void addClientForMarketYWithMissingMandatoryData() {
        System.out.println("2.1.2) Creating a client with mandatory data will be given a success response");
        System.out.println("2.3.1) Client mandatory fields also include identification details");

        ClientRequest clientRequest = new ClientRequest()
                .market("MarketY")
                .externalClientId("Y-123")
                .client(new Client().withMandatory("Jack", "Doe").EmailAddress("missingMandatory@test.test"))
                .preferences(new Preferences());

        Response response = new ClientService(urls).createClient(clientRequest);

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "status");
        assertEquals("Request Missing Fields: identificationNumber and identificationType", json.getString("message"), "message");
    }

    @Test
    void updateClientAdditionalData() {
        System.out.println("2.1.3) A client record can be updated with additional data");

        ClientRequest clientRequest = new ClientRequest()
                .market("MarketY")
                .externalClientId("Y-123")
                .client(new Client().withMandatory("Jack", "Doe").EmailAddress("missingMandatory@test.test"))
                .preferences(new Preferences());

        Response response = new ClientService(urls).updateClient(clientRequest);

        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Client Updated", json.getString("message"), "message");
    }
}
