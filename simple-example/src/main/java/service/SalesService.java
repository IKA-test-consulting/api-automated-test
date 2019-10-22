package service;

import client.ApiRestClient;
import io.restassured.response.Response;

import java.util.Map;

import static utility.EnvironmentConstants.HOST;
import static utility.EnvironmentConstants.SALES_SERVICE;

public class SalesService {

    //Get a list of sales by client id
    public Response getSales(String token, int clientId) {
        return new ApiRestClient(token).get(HOST + SALES_SERVICE, Map.of("client", clientId));
    }

    //Get a list of sales without a client id
    public Response getSales(String token) {
        return new ApiRestClient(token).get(HOST + SALES_SERVICE);
    }
}
