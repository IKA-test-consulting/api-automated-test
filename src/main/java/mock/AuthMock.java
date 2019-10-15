package mock;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.StubServiceBuilder;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

public class AuthMock {
    public AuthMock(Hoverfly hoverfly, String service, String endpoint) {
        StubServiceBuilder simulatedService = HoverflyDsl.service(service);
        new MockFramework(hoverfly).simulate(simulatedService.get(endpoint),
                success().body(jsonWithSingleQuotes("{'token':'fake_token'}")));
    }
}
