package service;

import client.ApiRestClient;
import io.restassured.response.Response;
import mock.SalesMock;
import util.EnvironmentUtility;

import java.util.Map;

public class SalesService {
    private String salesEndpoint = EnvironmentUtility.getProperty("simple.service.sales");
    private String service = EnvironmentUtility.getProperty("simple.service.url");

    public SalesService() {
        new SalesMock(service, salesEndpoint);
    }

    //Get a list of sales by client id
    public Response getSales(String token, int clientId) {
        return new ApiRestClient(token).get(service + salesEndpoint, Map.of("client", clientId));
    }

    //Get a list of sales without a client id
    public Response getSales(String token) {
        return new ApiRestClient(token).get(service + salesEndpoint);
    }
}
