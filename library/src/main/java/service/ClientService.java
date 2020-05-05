package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.client.ClientRequest;

import java.util.HashMap;
import java.util.Map;

public class ClientService {
    private final ApiUrls urls;
    private final String CLIENT_SERVICE;
    private final RequestSpecification request = RestAssured.with().log().uri().accept("application/json");
    private final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

    public ClientService(ApiUrls urls) {
        this.urls = urls;
        CLIENT_SERVICE = urls.get(ServiceName.HOST) + urls.get(ServiceName.CLIENT);
    }

    /*Get the service status*/
    public Response ping(String token) {
        return request
                .headers(Map.of("Authorization", "Bearer " + token))
                .get(CLIENT_SERVICE + "/ping");
    }

    /*Get the service status - Used for no token testing*/
    public Response ping() {
        return request.get(CLIENT_SERVICE + "/ping");
    }

    public Response createClient(ClientRequest clientRequest) {
        Map<String, ClientRequest> requestBody = new HashMap<>();
        requestBody.put("client-request", clientRequest);
        String body = gsonBuilder.toJson(requestBody);
        return request.headers(new AuthService(urls).getTokenHeader()).body(body).post(CLIENT_SERVICE + "/client");
    }

    public Response updateClient(ClientRequest clientRequest) {
        Map<String, ClientRequest> requestBody = new HashMap<>();
        requestBody.put("client-request", clientRequest);
        String body = gsonBuilder.toJson(requestBody);
        return request.headers(new AuthService(urls).getTokenHeader()).body(body).patch(CLIENT_SERVICE + "/client");
    }
}
