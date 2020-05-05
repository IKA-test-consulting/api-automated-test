package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class AuthService {
    private static final String CLIENT_PASSWORD_HEADER = "x-client-password";
    private static final String CLIENT_ID_HEADER = "x-client-id";
    private final String AUTH_SERVICE;
    private final RequestSpecification request = RestAssured.with().log().uri().accept("application/json");

    public AuthService(ApiUrls urls) {
        AUTH_SERVICE = urls.get(ServiceName.HOST) + urls.get(ServiceName.AUTH);
    }

    /*Get the token using the default login details*/
    public String getToken() {
        return request
                .headers(Map.of(CLIENT_ID_HEADER, "fakeId", CLIENT_PASSWORD_HEADER, "fakePassword"))
                .get(AUTH_SERVICE)
                .jsonPath()
                .getString("token");
    }

    /*Token request with id and password specified by the test*/
    public Response getToken(String clientId, String clientPassword) {
        return request
                .headers(Map.of(CLIENT_ID_HEADER, clientId, CLIENT_PASSWORD_HEADER, clientPassword))
                .get(AUTH_SERVICE);
    }

    /*Token request with a missing header, e.g. client password*/
    public Response getToken(String headerValue) {
        return request
                .headers(Map.of(CLIENT_ID_HEADER, headerValue))
                .get(AUTH_SERVICE);
    }

    public Map<String, String> getTokenHeader() {
        return Map.of("Authorization", "Bearer " + getToken());
    }
}
