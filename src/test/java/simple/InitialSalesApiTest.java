package simple;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.StubServiceBuilder;
import io.specto.hoverfly.junit5.HoverflyExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import util.EnvironmentUtility;

import java.util.HashMap;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.badRequest;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Quick and dirty example of rest-assured tests using static mocks
 * <p>
 * Mocks are used when the service/endpoint is not yet ready as the test can be created against the mocks
 * and then when the service is ready they can be commented out
 * <p>
 * <b>Next Steps</b>
 * If mocks are needed regularly then create a mock handler to reduce potential for errors in setup
 * Create a wrapper for the rest-assured boilerplate
 * Create a wrapper for the json navigation (just for the For loop)
 */

// @ExtendWith allows the junit runner to keep the hoverfly runner around for the tests to use
// With Junit 4 this would be replaced with a @Rule
@ExtendWith(HoverflyExtension.class)
class InitialSalesApiTest {
    private StubServiceBuilder simulatedService;
    private RequestSpecification request = RestAssured.with().log().uri();
    private String service = EnvironmentUtility.getProperty("simple.service.url");
    private String salesEndpoint = EnvironmentUtility.getProperty("simple.service.sales");
    private String authEndPoint = EnvironmentUtility.getProperty("simple.service.auth");
    private Hoverfly hoverfly;
    private String token;


    //Service requires a token so a mock auth endpoint is used to get a token before each test
    @BeforeEach
    void setupSimulatedService(Hoverfly hoverfly) {
        this.hoverfly = hoverfly;
        simulatedService = HoverflyDsl.service(service);
        hoverfly.simulate(
                dsl(simulatedService
                        .get(authEndPoint)
                        .willReturn(
                                success()
                                        .body(jsonWithSingleQuotes("{'token':'fake_token'}")))));
        token = request
                .header("x-client-id", "fakeId")
                .header("x-client-password", "fakePassword")
                .get(service + authEndPoint)
                .jsonPath()
                .getString("token");
    }

    @Test
    void salesNameWillBeIncludedInSale() {
        //Mock a successful response displaying a list of sales for a client id
        int clientId = 123;
        hoverfly.simulate(
                dsl(simulatedService
                        .get(salesEndpoint).anyQueryParams()
                        .willReturn(
                                success()
                                        .body(jsonWithSingleQuotes("{'sales':[{'id':1,'name':'HCI'},{'id':2,'name':'HCA'}]}")))));

        //Send a request to get a list of sales
        Response response = request.accept("application/json")
                .header("Authorization", "Bearer " + token)
                .header("", "")
                .queryParams("client", clientId)
                .get(service + salesEndpoint);

        //Verify the response has the correct name for a sale id in a list of sales
        JsonPath json = response.getBody().jsonPath();
        assertEquals(2, json.getList("sales").size(), "List has all sales for client");
        for (Object record : json.getList("sales")) {
            if ((int) ((HashMap) record).get("id") == 1) {
                assertEquals("HCI", ((HashMap) record).get("name"), "Sale name");
            }
        }
    }

    @Test
    void badRequestWillHaveErrorStatus() {
        //Mock a response where the request is missing details
        hoverfly.simulate(
                dsl(simulatedService.get(salesEndpoint)
                        .willReturn(
                                badRequest()
                                        .body(jsonWithSingleQuotes("{'status': 'Error', 'reason':'Missing client id parameter'}")))));

        //Send a request with missing query parameter
        Response response = request.accept("application/json")
                .header("Authorization", "Bearer " + token)
                .header("", "")
                .get(service + salesEndpoint);

        //Verify response has the correct status and message
        JsonPath json = response.getBody().jsonPath();
        assertEquals("Error", json.getString("status"), "Status");
        assertEquals("Missing client id parameter", json.getString("reason"));
    }
}