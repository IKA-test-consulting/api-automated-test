package mock;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.core.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MockService {
    private AuthMock authMock = new AuthMock();
    private Hoverfly hoverfly = new Hoverfly(HoverflyMode.SIMULATE);
    private Set<RequestResponsePair> requestResponsePairs = new HashSet<>();

    public MockService() {
        hoverfly.start();
        requestResponsePairs.add(authMock.getTokenSuccess());
    }

    public MockService withAuthMock() {
        requestResponsePairs.add(authMock.getTokenInvalidCredentials());
        requestResponsePairs.add(authMock.getTokenMissingCredentials());
        return this;
    }

    public void start(){
        HoverflyData simulationData = new HoverflyData(requestResponsePairs, new GlobalActions(new ArrayList<>()));
        hoverfly.simulate(SimulationSource.simulation(new Simulation(simulationData, new HoverflyMetaData())));
    }
}
