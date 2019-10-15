package fixture;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.StubServiceBuilder;
import mock.AuthMock;
import util.EnvironmentUtility;

public class AuthService {
    private RequestSpecification request = RestAssured.with().log().uri();
    private String authEndPoint = EnvironmentUtility.getProperty("simple.service.auth");
    private String service = EnvironmentUtility.getProperty("simple.service.url");
    private StubServiceBuilder simulatedService;

    public AuthService(Hoverfly hoverfly) {
        new AuthMock(hoverfly, service, authEndPoint);
    }

    public String getToken() {
        return request
                .header("x-client-id", "fakeId")
                .header("x-client-password", "fakePassword")
                .get(service + authEndPoint)
                .jsonPath()
                .getString("token");
    }
}
