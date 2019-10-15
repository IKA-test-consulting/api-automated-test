package mock;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.RequestMatcherBuilder;
import io.specto.hoverfly.junit.dsl.ResponseBuilder;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;

class MockFramework {
    private final Hoverfly hoverfly;

    MockFramework(Hoverfly hoverfly) {
        this.hoverfly = hoverfly;
    }

    void simulate(RequestMatcherBuilder request, ResponseBuilder response) {
        hoverfly.simulate(dsl(request.willReturn(response)));
    }
}
