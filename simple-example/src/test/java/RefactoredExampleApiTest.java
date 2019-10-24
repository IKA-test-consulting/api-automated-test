import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stub.ExampleMockFramework;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.SalesResponse;
import service.SalesService;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Refactored version of the example api tests that removes some of the duplicated code as well as
 * abstracting some of the rest-assured code
 * </br>
 * <b>Refactor</b>
 * * Mocking has been moved into a mock framework that only instantiated for the test
 * * The requests have been moved into service specific classes that handle that service's request
 * </br>
 * <b>Potential steps</b>
 * * Move rest-assured JsonPath into a separate class so that the tests are unaware of any non-core java methods
 */

class RefactoredExampleApiTest {
    private String token;

    @BeforeAll
    static void setupMock() {
        new ExampleMockFramework();
    }

    @BeforeEach
    void setup() {
        token = new AuthService().getToken();
        assertEquals("fake_token", token);
    }

    @Test
    void salesNameWillBeIncludedInSale() {
        Response response = new SalesService().getSales(token, 123);
        //Verify the response has the correct name for a sale id in a list of sales
        JsonPath json = response.getBody().jsonPath();
        assertEquals(2, json.getList("sales").size(), "List has all sales for client");
        assertEquals("HCI", new SalesResponse(response).getFieldForSalesId(1, "name"), "Sale name");
    }

    @Test
    void badRequestWillHaveErrorStatus() {
        Response response = new SalesService().getSales(token);
        //Verify response has the correct status and message
        JsonPath json = response.getBody().jsonPath();
        assertEquals("Error", json.getString("status"), "Status");
        assertEquals("Missing client id parameter", json.getString("reason"));
    }
}