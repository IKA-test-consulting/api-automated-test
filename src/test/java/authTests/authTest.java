package authTests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import service.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class authTest {
    //Requirement: 1.1) Requests with valid user credentials will be given a token
    @Test
    void tokenReturnedIfValidCredentials() {
        Response response = new AuthService().get("fakeId", "fakePassword");
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Status code");
        assertEquals("fake_token", response.jsonPath().getString("token"));
    }

    //1.2) Requests with invalid user credentials will be given an appropriate error response
    @Test
    void errorResponseIfInvalidCredentials() {
        Response response = new AuthService().get("invalidClientId", "invalidClientPassword");
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Status code");
        assertEquals("Error", response.jsonPath().getString("status"), "Status");
        assertEquals("Invalid Credentials", response.jsonPath().getString("reason"), "Reason");
    }

    //1.2) Requests with invalid user credentials will be given an appropriate error response
    @Test
    void errorResponseIfMissingCredentials() {
        Response response = new AuthService().getMissingCredentials();
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode(), "Status code");
        assertEquals("Error", response.jsonPath().getString("status"), "Status");
        assertEquals("Missing Credentials", response.jsonPath().getString("reason"), "Reason");
    }

    //These will be tested in the individual services as each service will need to implement these requirements
    //1.3) All requests to all other services must use the provided token
    //1.4) A request with no token will be given an appropriate error response
}
