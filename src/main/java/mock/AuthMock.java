package mock;

import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.StubServiceBuilder;
import org.apache.http.HttpStatus;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.badRequest;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

public class AuthMock {

    private final String endpoint;
    private StubServiceBuilder simulatedService;

    public AuthMock(String service, String endpoint) {
        simulatedService = HoverflyDsl.service(service);
        this.endpoint = endpoint;
        successResponse();
        errorResponseInvalidCredentials();
        errorResponseMissingCredentials();
    }

    private void successResponse() {
        //return fake token
        new MockFramework().simulate(
                simulatedService.get(endpoint)
                        .header("x-client-id", "fakeId")
                        .header("x-client-password", "fakePassword"),
                success().body(jsonWithSingleQuotes("{'token':'fake_token'}")));
    }

    private void errorResponseInvalidCredentials() {
        //return error response for invalid credentials
        new MockFramework().simulate(
                simulatedService.get(endpoint)
                        .header("x-client-id", "invalidClientId")
                        .header("x-client-password", "invalidClientPassword"),
                badRequest()
                        .status(HttpStatus.SC_FORBIDDEN)
                        .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Invalid Credentials'}")));
    }

    private void errorResponseMissingCredentials() {
        //return error response for invalid credentials
        new MockFramework().simulate(
                simulatedService.get(endpoint)
                        .header("x-client-id", "missingHeader"),
                badRequest()
                        .status(HttpStatus.SC_BAD_REQUEST)
                        .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Missing Credentials'}")));
    }
}
