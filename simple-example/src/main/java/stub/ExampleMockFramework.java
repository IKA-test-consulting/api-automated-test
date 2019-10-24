package stub;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import org.apache.http.HttpStatus;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.badRequest;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static utility.EnvironmentConstants.*;

public class ExampleMockFramework {

    public ExampleMockFramework() {
        Hoverfly hoverfly = new Hoverfly(HoverflyMode.SIMULATE);
        hoverfly.start();
        hoverfly.simulate(SimulationSource.dsl(
                HoverflyDsl.service(HOST)
                        /*AUTH SERVICE*/
                        //return fake token
                        .get(AUTH_SERVICE)
                        .header("x-client-id", "fakeId")
                        .header("x-client-password", "fakePassword")
                        .willReturn(
                                success().body(jsonWithSingleQuotes("{'token':'fake_token'}")))

                        //return error response for invalid credentials
                        .get(AUTH_SERVICE)
                        .header("x-client-id", "invalidClientId")
                        .header("x-client-password", "invalidClientPassword")
                        .willReturn(
                                badRequest()
                                        .status(HttpStatus.SC_FORBIDDEN)
                                        .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Invalid Credentials'}")))
                        //return error response for missing credentials
                        .get(AUTH_SERVICE)
                        .header("x-client-id", "missingHeader")
                        .willReturn(
                                badRequest()
                                        .status(HttpStatus.SC_BAD_REQUEST)
                                        .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Missing Credentials'}")))
                        /*SALES SERVICE*/
                        //mock a successful response for the example api tests
                        .get(SALES_SERVICE).queryParam("client", 123)
                        .willReturn(success()
                                .body(jsonWithSingleQuotes("{'sales':[{'id':1,'name':'HCI'},{'id':2,'name':'HCA'}]}")))
                        //mock a failed response for the example api tests
                        .get(SALES_SERVICE).willReturn(
                        badRequest()
                                .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Missing client id parameter'}")))
                )
        );
    }
}
