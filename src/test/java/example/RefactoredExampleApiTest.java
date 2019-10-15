package example;

import fixture.AuthService;
import fixture.SalesService;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit5.HoverflyExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Quick and dirty example of rest-assured tests using static mocks
 * <p>
 * Mocks are used when the service/endpoint is not yet ready as the test can be created against the mocks
 * and then when the service is ready they can be commented out.</br>
 * This test framework only requires the EnvironmentUtility class
 * <p>
 * <b>Next Steps</b>
 * If mocks are needed regularly then create a mock handler to reduce potential for errors in setup
 * Create a wrapper for the rest-assured boilerplate
 * Create a wrapper for the json navigation (just for the For loop)
 */

// @ExtendWith allows the junit runner to keep the hoverfly runner around for the tests to use
// With Junit 4 this would be replaced with a @Rule
@ExtendWith(HoverflyExtension.class)
class RefactoredExampleApiTest {
    private Hoverfly hoverfly;
    private String token;

    @BeforeEach
    void setupSimulatedService(Hoverfly hoverfly) {
        this.hoverfly = hoverfly;
        token = new AuthService(hoverfly).getToken();
    }


    @Test
    void salesNameWillBeIncludedInSale() {
        Response response = new SalesService(hoverfly).getSales(token, 123);
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
        Response response = new SalesService(hoverfly).getSales(token);
        //Verify response has the correct status and message
        JsonPath json = response.getBody().jsonPath();
        assertEquals("Error", json.getString("status"), "Status");
        assertEquals("Missing client id parameter", json.getString("reason"));
    }
}