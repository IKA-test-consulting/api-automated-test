package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.EnvironmentConstants;

import java.util.Map;

public class AuthService {
    private static final String CLIENT_PASSWORD_HEADER = "x-client-password";
    private static final String CLIENT_ID_HEADER = "x-client-id";
    private String authEndPoint = EnvironmentConstants.AUTH_SERVICE;
    private String service = EnvironmentConstants.HOST;
    private RequestSpecification request = RestAssured.with().log().uri().accept("application/json");

    /*Get the token using the default login details*/
    public String getToken() {
        return request
                .headers(Map.of(CLIENT_ID_HEADER, "fakeId", CLIENT_PASSWORD_HEADER, "fakePassword"))
                .get(service + authEndPoint)
                .jsonPath()
                .getString("token");
    }

    /*Token request with id and password specified by the test*/
    public Response getToken(String clientId, String clientPassword) {
        return request
                .headers(Map.of(CLIENT_ID_HEADER, clientId, CLIENT_PASSWORD_HEADER, clientPassword))
                .get(service + authEndPoint);
    }

    /*Token request with a missing header, e.g. client password*/
    public Response getToken(String headerValue) {
        return request
                .headers(Map.of(CLIENT_ID_HEADER, headerValue))
                .get(service + authEndPoint);
    }

    public Map<String, String> getTokenHeader() {
        return Map.of("Authorization", "Bearer " + getToken());
    }
}
