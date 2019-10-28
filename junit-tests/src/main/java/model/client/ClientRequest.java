package model.client;

public class ClientRequest {
    private String businessId;
    private String externalClientId;
    private Client client;
    private Preferences preferences;

    public ClientRequest market(String market) {
        businessId = market;
        return this;
    }

    public ClientRequest externalClientId(String id) {
        externalClientId = id;
        return this;
    }

    public ClientRequest client(Client client) {
        this.client = client;
        return this;
    }

    public ClientRequest preferences(Preferences preferences) {
        this.preferences = preferences;
        return this;
    }
}
