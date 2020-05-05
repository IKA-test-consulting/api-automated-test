package stub.service;

import io.specto.hoverfly.junit.core.model.Request;
import io.specto.hoverfly.junit.core.model.RequestFieldMatcher;
import io.specto.hoverfly.junit.core.model.RequestResponsePair;
import io.specto.hoverfly.junit.core.model.Response;
import org.apache.http.HttpStatus;
import stub.builder.StubRequest;
import stub.builder.StubResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stub.builder.StubMethod.GET;

public class AuthServiceStub {
    private final String HOST;
    private final String AUTH_SERVICE;

    private static final String HEADER_CLIENT_ID = "x-client-id";
    private static final String HEADER_CLIENT_PASSWORD = "x-client-password";

    public AuthServiceStub(String host, String service) {
        HOST = host;
        AUTH_SERVICE = service;
    }

    private Response createResponse(int statusCode, String body) {
        return new StubResponse().status(statusCode).body(body).build();
    }

    @SuppressWarnings("rawtypes")
    private Request createRequest(String clientId, String clientPassword) {
        Map<String, List<RequestFieldMatcher>> headers = new HashMap<>();
        headers.put(HEADER_CLIENT_ID, Collections.singletonList(RequestFieldMatcher.newExactMatcher(clientId)));
        headers.put(HEADER_CLIENT_PASSWORD, Collections.singletonList(RequestFieldMatcher.newExactMatcher(clientPassword)));
        return new StubRequest().headers(headers).host(HOST).path(AUTH_SERVICE).method(GET).build();
    }

    public RequestResponsePair getTokenSuccess() {
        return new RequestResponsePair(createRequest("fakeId", "fakePassword"),
                createResponse(HttpStatus.SC_OK, "{'token':'fake_token'}"));
    }

    public RequestResponsePair getTokenInvalidCredentials() {
        return new RequestResponsePair(createRequest("invalidClientId", "invalidClientPassword"),
                createResponse(HttpStatus.SC_FORBIDDEN, "{'status': 'Error', 'reason':'Invalid Credentials'}"));
    }

    public RequestResponsePair getTokenMissingCredentials() {
        return new RequestResponsePair(new StubRequest()
                .addHeader(HEADER_CLIENT_ID, "missingHeader").host(HOST).path(AUTH_SERVICE).method(GET).build(),
                createResponse(HttpStatus.SC_BAD_REQUEST, "{'status': 'Error', 'reason':'Missing Credentials'}"));
    }
}
