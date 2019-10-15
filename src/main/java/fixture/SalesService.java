package fixture;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.specto.hoverfly.junit.core.Hoverfly;
import mock.SalesMock;
import util.EnvironmentUtility;

public class SalesService {
    private RequestSpecification request = RestAssured.with().log().uri();
    private String salesEndpoint = EnvironmentUtility.getProperty("simple.service.sales");
    private String service = EnvironmentUtility.getProperty("simple.service.url");

    public SalesService(Hoverfly hoverfly) {
        new SalesMock(hoverfly, service, salesEndpoint);
    }

    //Get a list of sales by client id
    public Response getSales(String token, int clientId) {
        return request.accept("application/json")
                .header("Authorization", "Bearer " + token)
                .header("", "")
                .queryParams("client", clientId)
                .get(service + salesEndpoint);
    }

    //Get a list of sales without a client id
    public Response getSales(String token) {
        return request.accept("application/json")
                .header("Authorization", "Bearer " + token)
                .header("", "")
                .get(service + salesEndpoint);
    }
}
