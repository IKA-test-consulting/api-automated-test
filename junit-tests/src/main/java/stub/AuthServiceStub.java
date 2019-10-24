package stub;

import io.specto.hoverfly.junit.core.model.Request;
import io.specto.hoverfly.junit.core.model.RequestFieldMatcher;
import io.specto.hoverfly.junit.core.model.RequestResponsePair;
import io.specto.hoverfly.junit.core.model.Response;
import org.apache.http.HttpStatus;
import utility.EnvironmentConstants;

import java.util.*;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;

class AuthServiceStub {
    private static final String HOST = EnvironmentConstants.HOST;
    private static final String AUTH_SERVICE = EnvironmentConstants.AUTH_SERVICE;

    private static final String HEADER_CLIENT_ID = "x-client-id";
    private static final String HEADER_CLIENT_PASSWORD = "x-client-password";

    RequestResponsePair getTokenSuccess() {
        return new RequestResponsePair(createRequest("fakeId", "fakePassword"),
                createResponse(HttpStatus.SC_OK, "{'token':'fake_token'}"));
    }

    RequestResponsePair getTokenInvalidCredentials() {
        return new RequestResponsePair(createRequest("invalidClientId", "invalidClientPassword"),
                createResponse(HttpStatus.SC_FORBIDDEN, "{'status': 'Error', 'reason':'Invalid Credentials'}"));
    }

    RequestResponsePair getTokenMissingCredentials() {
        Map<String, List<RequestFieldMatcher>> headers = new HashMap<>();
        headers.put(HEADER_CLIENT_ID, Collections.singletonList(RequestFieldMatcher.newExactMatcher("missingHeader")));

        return new RequestResponsePair(createFullRequest(headers),
                createResponse(HttpStatus.SC_BAD_REQUEST, "{'status': 'Error', 'reason':'Missing Credentials'}"));
    }

    private Request createRequest(String clientId, String clientPassword) {
        Map<String, List<RequestFieldMatcher>> headers = new HashMap<>();
        headers.put(HEADER_CLIENT_ID, Collections.singletonList(RequestFieldMatcher.newExactMatcher(clientId)));
        headers.put(HEADER_CLIENT_PASSWORD, Collections.singletonList(RequestFieldMatcher.newExactMatcher(clientPassword)));
        return createFullRequest(headers);
    }

    //TODO may need to convert to Lombok builder once multiple services are created
    private Request createFullRequest(Map<String, List<RequestFieldMatcher>> headers) {
        Request request = new Request();
        request.setHeaders(headers);
        request.setMethod(Collections.singletonList(RequestFieldMatcher.newExactMatcher("GET")));
        request.setPath(Collections.singletonList(RequestFieldMatcher.newExactMatcher(AUTH_SERVICE)));
        request.setDestination(Collections.singletonList(RequestFieldMatcher.newExactMatcher(HOST.substring(HOST.lastIndexOf("/") + 1))));
        request.setScheme(Collections.singletonList(RequestFieldMatcher.newExactMatcher(HOST.substring(0, HOST.indexOf(":")))));
        return request;
    }

    //TODO may need to convert to Lombok builder if more than status and body are different
    private Response createResponse(int statusCode, String body) {
        return new Response(statusCode,
                jsonWithSingleQuotes(body).body(),
                false,
                false,
                new HashMap<>(),
                new HashMap<>(),
                new ArrayList<>());
    }
}
