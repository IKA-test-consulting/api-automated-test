package stub.service;

import io.specto.hoverfly.junit.core.model.RequestResponsePair;
import io.specto.hoverfly.junit.core.model.Response;
import org.apache.http.HttpStatus;
import stub.builder.StubMethod;
import stub.builder.StubRequest;
import stub.builder.StubResponse;

public class ClientServiceStub {
    private final String HOST;
    private final String CLIENT_SERVICE;

    private static final String HEADER_AUTH = "Authorization";

    public ClientServiceStub(String host, String service) {
        HOST = host;
        CLIENT_SERVICE = service;
    }

    private Response createResponse(int httpStatus, String body) {
        return new StubResponse().status(httpStatus).body(body).build();
    }

    private StubRequest createRequest(StubMethod method, String endpoint) {
        return new StubRequest().method(method).host(HOST).path(CLIENT_SERVICE + endpoint);
    }

    public RequestResponsePair getPingError() {
        return new RequestResponsePair(
                createRequest(StubMethod.GET, "/ping").build(),
                createResponse(HttpStatus.SC_FORBIDDEN, "{'status':'error','reason':'Missing Token'}"));
    }

    public RequestResponsePair getPing() {
        return new RequestResponsePair(createRequest(StubMethod.GET, "/ping").addHeader(HEADER_AUTH, "Bearer fake_token").build(),
                createResponse(HttpStatus.SC_OK, "{'service':'client', 'status':'UP'}"));
    }

    public RequestResponsePair createMarketXClientSuccess() {
        return new RequestResponsePair(createRequest(StubMethod.POST, "/client")
                .addHeader(HEADER_AUTH, "Bearer fake_token")
                .partialBodyMatch("{'client-request': {'businessId':'MarketX', 'client' :{'foreName':'John', 'surName':'Doe'}}}")
                .build(),
                createResponse(HttpStatus.SC_OK, "{'status': 'success', 'message': 'Client created', 'result': {'clientId': 'X-123'}}")
        );
    }

    public RequestResponsePair createMarketYClientSuccess() {
        return new RequestResponsePair(createRequest(StubMethod.POST, "/client")
                .addHeader(HEADER_AUTH, "Bearer fake_token")
                .partialBodyMatch("{'client-request': {'businessId':'MarketY', 'client' :{'foreName':'James', 'surName':'Doe', 'identificationNumber':'ID-123', 'identificationType':'PASSPORT'}}}")
                .build(),
                createResponse(HttpStatus.SC_OK, "{'status': 'success', 'message': 'Client created', 'result': {'clientId': 'Y-123'}}")
        );
    }

    public RequestResponsePair createMarketXClientMandatoryError() {
        return new RequestResponsePair(createRequest(StubMethod.POST, "/client")
                .addHeader(HEADER_AUTH, "Bearer fake_token")
                .partialBodyMatch("{'client-request': {'businessId':'MarketX', 'client' :{'emailAddress':'missingMandatory@test.test'}}}")
                .build(),
                createResponse(HttpStatus.SC_BAD_REQUEST, "{'status': 'error', 'message': 'Request Missing Fields: foreName and surName'}")
        );
    }

    public RequestResponsePair createMarketYClientMandatoryError() {
        return new RequestResponsePair(createRequest(StubMethod.POST, "/client")
                .addHeader(HEADER_AUTH, "Bearer fake_token")
                .partialBodyMatch("{'client-request': {'businessId':'MarketY', 'client' :{'emailAddress':'missingMandatory@test.test'}}}")
                .build(),
                createResponse(HttpStatus.SC_BAD_REQUEST, "{'status': 'error', 'message': 'Request Missing Fields: identificationNumber and identificationType'}")
        );
    }

    public RequestResponsePair updateClientSuccess() {
        return new RequestResponsePair(createRequest(StubMethod.PATCH, "/client")
                .addHeader(HEADER_AUTH, "Bearer fake_token")
                .build(),
                createResponse(HttpStatus.SC_OK, "{'status': 'success', 'message': 'Client Updated'}"));
    }
}
