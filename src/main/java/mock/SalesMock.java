package mock;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.StubServiceBuilder;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.badRequest;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

public class SalesMock {
    public SalesMock(Hoverfly hoverfly, String service, String salesEndpoint) {
        StubServiceBuilder simulatedService = HoverflyDsl.service(service);
        getExampleSuccessResponse(hoverfly, salesEndpoint, simulatedService);
        getExampleBadResponse(hoverfly, salesEndpoint, simulatedService);
    }

    private void getExampleSuccessResponse(Hoverfly hoverfly, String salesEndpoint, StubServiceBuilder simulatedService) {
        //mock a successful response for the example api tests
        new MockFramework(hoverfly).simulate(simulatedService
                .get(salesEndpoint).queryParam("client", 123), success()
                .body(jsonWithSingleQuotes("{'sales':[{'id':1,'name':'HCI'},{'id':2,'name':'HCA'}]}")));
    }

    private void getExampleBadResponse(Hoverfly hoverfly, String salesEndpoint, StubServiceBuilder simulatedService) {
        //mock a failed response for the example api tests
        new MockFramework(hoverfly).simulate(simulatedService.get(salesEndpoint), badRequest()
                .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Missing client id parameter'}")));
    }
}
