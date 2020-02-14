package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.client.ClientRequest;
import utility.EnvironmentConstants;

import java.util.HashMap;
import java.util.Map;

public class ClientService {
    private final String clientService = EnvironmentConstants.CLIENT_SERVICE;
    private final String host = EnvironmentConstants.HOST;
    private final RequestSpecification request = RestAssured.with().log().uri().accept("application/json");
    private final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

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

    public Response createClient(ClientRequest clientRequest) {
        Map<String, ClientRequest> requestBody = new HashMap<>();
        requestBody.put("client-request", clientRequest);
        String body = gsonBuilder.toJson(requestBody);
        return request.headers(new AuthService().getTokenHeader()).body(body).post(host + clientService + "/client");
    }

    public Response updateClient(ClientRequest clientRequest) {
        Map<String, ClientRequest> requestBody = new HashMap<>();
        requestBody.put("client-request", clientRequest);
        String body = gsonBuilder.toJson(requestBody);
        return request.headers(new AuthService().getTokenHeader()).body(body).patch(host + clientService + "/client");
    }
}
