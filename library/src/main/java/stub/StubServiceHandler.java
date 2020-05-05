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

public class StubServiceHandler {
    private static final Hoverfly hoverfly = new Hoverfly(HoverflyMode.SIMULATE);
    private static final Set<RequestResponsePair> requestResponsePairs = new HashSet<>();
    private final String HOST;

    public StubServiceHandler(String host) {
        HOST = host;
        hoverfly.start();
    }

    public StubServiceHandler withAuthStub(String service) {
        AuthServiceStub authServiceStub = new AuthServiceStub(HOST, service);
        requestResponsePairs.add(authServiceStub.getTokenSuccess());
        requestResponsePairs.add(authServiceStub.getTokenInvalidCredentials());
        requestResponsePairs.add(authServiceStub.getTokenMissingCredentials());
        return this;
    }

    public void start() {
        HoverflyData simulationData = new HoverflyData(requestResponsePairs, new GlobalActions(new ArrayList<>()));
        hoverfly.simulate(SimulationSource.simulation(new Simulation(simulationData, new HoverflyMetaData())));
        System.out.println("Simulation: " + hoverfly.toString());
    }

    public StubServiceHandler withClientStub(String service) {
        ClientServiceStub clientServiceStub = new ClientServiceStub(HOST, service);
        requestResponsePairs.add(clientServiceStub.getPingError());
        requestResponsePairs.add(clientServiceStub.getPing());
        requestResponsePairs.add(clientServiceStub.createMarketXClientMandatoryError());
        requestResponsePairs.add(clientServiceStub.createMarketYClientMandatoryError());
        requestResponsePairs.add(clientServiceStub.createMarketXClientSuccess());
        requestResponsePairs.add(clientServiceStub.createMarketYClientSuccess());
        requestResponsePairs.add(clientServiceStub.updateClientSuccess());
        return this;
    }

    public StubServiceHandler withSaleStub(String service) {
        SaleServiceStub saleServiceStub = new SaleServiceStub(HOST, service);
        requestResponsePairs.add(saleServiceStub.getPingError());
        requestResponsePairs.add(saleServiceStub.getPing());
        requestResponsePairs.add(saleServiceStub.createSaleSuccess());
        return this;
    }
}
