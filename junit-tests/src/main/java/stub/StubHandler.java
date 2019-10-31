package stub;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.core.model.*;
import stub.service.AuthServiceStub;
import stub.service.ClientServiceStub;
import stub.service.SaleServiceStub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StubHandler {
    private static AuthServiceStub authServiceStub = new AuthServiceStub();
    private static Hoverfly hoverfly = new Hoverfly(HoverflyMode.SIMULATE);
    private static Set<RequestResponsePair> requestResponsePairs = new HashSet<>();

    public StubHandler() {
        hoverfly.start();
        requestResponsePairs.add(authServiceStub.getTokenSuccess());
    }

    public StubHandler withAuthStub() {
        requestResponsePairs.add(authServiceStub.getTokenInvalidCredentials());
        requestResponsePairs.add(authServiceStub.getTokenMissingCredentials());
        return this;
    }

    public void start() {
        HoverflyData simulationData = new HoverflyData(requestResponsePairs, new GlobalActions(new ArrayList<>()));
        hoverfly.simulate(SimulationSource.simulation(new Simulation(simulationData, new HoverflyMetaData())));
    }

    public StubHandler withClientStub() {
        ClientServiceStub clientServiceStub = new ClientServiceStub();
        requestResponsePairs.add(clientServiceStub.getPingError());
        requestResponsePairs.add(clientServiceStub.getPing());
        requestResponsePairs.add(clientServiceStub.createMarketXClientMandatoryError());
        requestResponsePairs.add(clientServiceStub.createMarketYClientMandatoryError());
        requestResponsePairs.add(clientServiceStub.createMarketXClientSuccess());
        requestResponsePairs.add(clientServiceStub.createMarketYClientSuccess());
        requestResponsePairs.add(clientServiceStub.updateClientSuccess());
        return this;
    }

    public StubHandler withSaleStub() {
        SaleServiceStub saleServiceStub = new SaleServiceStub();
        requestResponsePairs.add(saleServiceStub.getPingError());
        requestResponsePairs.add(saleServiceStub.getPing());
        requestResponsePairs.add(saleServiceStub.createSaleSuccess());
        return this;
    }
}
