package net.ikaconsulting.steps;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import stub.StubServiceHandler;
import utility.EnvironmentConstants;

public class SaleServiceSteps {
    private Response response;
    private final EnvironmentConstants env = new EnvironmentConstants();

    public SaleServiceSteps() {
        new StubServiceHandler(env.HOST).withAuthStub(env.AUTH_SERVICE).withSaleStub(env.SALE_SERVICE).start();
    }

    @Given("I am a dummy step")
    public void iAmADummyStep() {

    }

    /*TODO convert to steps
    static void setup() {
        new StubServiceHandler(HOST).withAuthStub(AUTH_SERVICE).withSaleStub(SALE_SERVICE).start();
    }

    @Test
    void requestWithNoTokenWillError() {
        System.out.println("5.2) A request with no token will be given an appropriate error response");

        Response response = new SaleService(urls).ping();
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "Message status");
        assertEquals("Missing Token", json.getString("reason"), "Message reason");
    }

    @Test
    void pingRequestWithTokenWillGetServiceStatus() {
        System.out.println("5.1) All requests to services must use a token retrieved from the Auth service");
        System.out.println("5.3) All services will provide their 'UP' status");

        Response response = new SaleService(urls).ping(new AuthService(urls).getToken());
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("client", json.getString("service"), "Service");
        assertEquals("UP", json.getString("status"), "Ping Status");
    }

    @Test
    void addSaleToClient() {
        System.out.println("4.1.2) A client can make a purchase");
        System.out.println("4.1.2) A purchase can contain 1 or more products within a market");
        System.out.println("4.1.3) A successful purchase will provide a summary of the purchase cost and relevant amounts");

        SaleRequest request = new SaleRequest()
                .market("MarketX")
                .coupons(Collections.singletonList("X-123"))
                .client(new Client().ClientId("int-1"))
                .products(Collections.singletonList(new Product().getByName("product 1")));

        Response response = new SaleService(urls).addSale(request);
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Sale successful for John Doe", json.getString("message"), "message");
        JsonPath details = json.setRootPath("details");
        assertEquals("GBP", details.getString("currency"), "Currency");
        assertEquals(123.45, details.getDouble("totalSale"), "Total Sale");
        assertEquals(23.45, details.getDouble("totalTax"), "Total Tax");
        assertEquals(1.23, details.getDouble("totalDiscount"), "Total Discount");
        assertEquals(12.12, details.getDouble("totalPostage"), "Total Postage");
        List<String> productList = details.getList("products");
        assertTrue(productList.contains("Product 1") && productList.contains("Product 2") && productList.contains("Product 3"), "Product list contains expected products");
        assertEquals("Leave outside by the pool.", details.getString("additionalDetails"));
    }
     */
}
