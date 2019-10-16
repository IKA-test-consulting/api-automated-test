package service;

import client.ApiRestClient;
import mock.AuthMock;
import util.EnvironmentUtility;

import java.util.Map;

public class AuthService {
    private String authEndPoint = EnvironmentUtility.getProperty("simple.service.auth");
    private String service = EnvironmentUtility.getProperty("simple.service.url");

    public AuthService() {
        new AuthMock(service, authEndPoint);
    }

    public String getToken() {
        return new ApiRestClient()
                .getAuth(service + authEndPoint, Map.of("x-client-id", "fakeId", "x-client-password", "fakePassword"))
                .jsonPath()
                .getString("token");
    }
}
