package stub.service;

import io.specto.hoverfly.junit.core.model.RequestResponsePair;
import io.specto.hoverfly.junit.core.model.Response;
import org.apache.http.HttpStatus;
import stub.builder.StubMethod;
import stub.builder.StubRequest;
import stub.builder.StubResponse;

public class SaleServiceStub {
    private final String HOST;
    private final String SALE_SERVICE;

    private static final String HEADER_AUTH = "Authorization";

    public SaleServiceStub(String host, String service) {
        HOST = host;
        SALE_SERVICE = service;
    }

    private Response createResponse(int httpStatus, String body) {
        return new StubResponse().status(httpStatus).body(body).build();
    }

    private StubRequest createRequest(StubMethod method, String endpoint) {
        return new StubRequest().method(method).host(HOST).path(SALE_SERVICE + endpoint);
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

    public RequestResponsePair createSaleSuccess() {
        return new RequestResponsePair(createRequest(StubMethod.POST, "/sale")
                .addHeader(HEADER_AUTH, "Bearer fake_token")
                .build(),
                createResponse(HttpStatus.SC_OK, "{'status':'success','message':'Sale successful for John Doe','details':{'currency':'GBP','totalSale':123.45,'totalTax':23.45,'totalDiscount':1.23,'totalPostage':12.12,'products':['Product 1','Product 2','Product 3'],'additionalDetails':'Leave outside by the pool.'}}")
        );
    }
}
