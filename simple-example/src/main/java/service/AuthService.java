package service;

import client.ApiRestClient;
import io.restassured.response.Response;
import utility.EnvironmentConstants;

import java.util.Map;

@SuppressWarnings("unused")
public class AuthService {
    private static final String CLIENT_PASSWORD_HEADER = "x-client-password";
    private static final String CLIENT_ID_HEADER = "x-client-id";
    private final String authEndPoint = EnvironmentConstants.AUTH_SERVICE;
    private final String service = EnvironmentConstants.HOST;

    public String getToken() {
        return new ApiRestClient()
                .getAuth(service + authEndPoint, Map.of(CLIENT_ID_HEADER, "fakeId", CLIENT_PASSWORD_HEADER, "fakePassword"))
                .jsonPath()
                .getString("token");
    }

    public Response get(String clientId, String clientPassword) {
        return new ApiRestClient().getAuth(service + authEndPoint, Map.of(CLIENT_ID_HEADER, clientId, CLIENT_PASSWORD_HEADER, clientPassword));
    }

    public Response getMissingCredentials() {
        return new ApiRestClient().getAuth(service + authEndPoint, Map.of(CLIENT_ID_HEADER, "missingHeader"));
    }
}
