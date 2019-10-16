package mock;

import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.StubServiceBuilder;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.badRequest;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

public class SalesMock {
    public SalesMock(String service, String salesEndpoint) {
        StubServiceBuilder simulatedService = HoverflyDsl.service(service);
        getExampleSuccessResponse(salesEndpoint, simulatedService);
        getExampleBadResponse(salesEndpoint, simulatedService);
    }

    private void getExampleSuccessResponse(String salesEndpoint, StubServiceBuilder simulatedService) {
        //mock a successful response for the example api tests
        new MockFramework().simulate(simulatedService
                .get(salesEndpoint).queryParam("client", 123), success()
                .body(jsonWithSingleQuotes("{'sales':[{'id':1,'name':'HCI'},{'id':2,'name':'HCA'}]}")));
    }

    private void getExampleBadResponse(String salesEndpoint, StubServiceBuilder simulatedService) {
        //mock a failed response for the example api tests
        new MockFramework().simulate(simulatedService.get(salesEndpoint), badRequest()
                .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Missing client id parameter'}")));
    }
}
