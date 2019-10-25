package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.EnvironmentConstants;

import java.util.Map;

public class ClientService {
    private String clientService = EnvironmentConstants.CLIENT_SERVICE;
    private String host = EnvironmentConstants.HOST;
    private RequestSpecification request = RestAssured.with().log().uri().accept("application/json");

    /*Get the service status*/
    public Response ping(String token) {
        return request
                .headers(Map.of("Authorization", "Bearer " + token))
                .get(host + clientService + "/ping");
    }
    /*Get the service status - Used for no token testing*/
    public Response ping() {
        return request.get(host + clientService + "/ping");
    }
}
