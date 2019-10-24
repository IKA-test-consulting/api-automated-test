package stub;

import io.specto.hoverfly.junit.core.model.Request;
import io.specto.hoverfly.junit.core.model.RequestResponsePair;
import io.specto.hoverfly.junit.core.model.Response;
import org.apache.http.HttpStatus;
import utility.EnvironmentConstants;

class ClientServiceStub {
    private static final String HOST = EnvironmentConstants.HOST;
    private static final String CLIENT_SERVICE = EnvironmentConstants.CLIENT_SERVICE;

    private static final String HEADER_AUTH = "Authorization";

    RequestResponsePair getPingStatusUp(String token) {
        return new RequestResponsePair(createRequest(token),
                createResponse(HttpStatus.SC_OK, "{'service':'client', 'status':'UP'}"));
    }

    RequestResponsePair getPingStatusDown(String token) {
        //TODO add a state change so first request is always down and the second is always up
        return new RequestResponsePair(createRequest(token),
                createResponse(HttpStatus.SC_FORBIDDEN, "{'service':'client', 'status':'DOWN'}"));
    }

    private Response createResponse(int httpStatus, String body) {
        return null;
    }

    private Request createRequest(String token) {
        return null;
    }
}
