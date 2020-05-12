package net.ikaconsulting.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.client.Client;
import model.sale.Product;
import model.sale.SaleRequest;
import org.apache.http.HttpStatus;
import service.AuthService;
import service.SaleService;
import stub.StubServiceHandler;
import utility.EnvironmentConstants;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaleServiceSteps {
    private Response response;
    private SaleRequest salesIntent;
    private final EnvironmentConstants env = new EnvironmentConstants();

    public SaleServiceSteps() {
        new StubServiceHandler(env.HOST).withAuthStub(env.AUTH_SERVICE).withSaleStub(env.SALE_SERVICE).start();
    }


    @Given("I have a Sales intent")
    public void iHaveASalesIntent() {
        salesIntent = new SaleRequest()
                .market("MarketX")
                .coupons(Collections.singletonList("X-123"))
                .client(new Client().ClientId("int-1"))
                .products(Collections.singletonList(new Product().getByName("product 1")));
    }

    @Given("I have a Sales intent with no products")
    public void iHaveASalesIntentWithNoProducts() {
        salesIntent = new SaleRequest()
                .market("MarketX")
                .coupons(Collections.singletonList("X-123"))
                .client(new Client().ClientId("int-1"));

    }

    @When("I ping the Sale service with no Auth token")
    public void iPingTheSaleServiceWithNoAuthToken() {
        response = new SaleService(env.urls()).ping();
    }

    @When("I ping the Sale service with an Auth token")
    public void iPingTheSaleServiceWithAnAuthToken() {
        response = new SaleService(env.urls()).ping(new AuthService(env.urls()).getToken());
    }

    @Then("I will get a no token error response from the sale service")
    public void iWillGetANoTokenErrorResponseFromTheSaleService() {
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("error", json.getString("status"), "Message status");
        assertEquals("Missing Token", json.getString("reason"), "Message reason");
    }

    @Then("I will get response with the sale service Up status")
    public void iWillGetResponseWithTheSaleServiceUpStatus() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("client", json.getString("service"), "Service");
        assertEquals("UP", json.getString("status"), "Ping Status");
    }

    @When("I make a Sales")
    public void iMakeASales() {
        response = new SaleService(env.urls()).addSale(salesIntent);
    }

    @Then("I will be informed my Sales purchase is successful")
    public void iWillBeInformedMySalesPurchaseIsSuccessful() {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Http status");
        JsonPath json = response.jsonPath();
        assertEquals("success", json.getString("status"), "status");
        assertEquals("Sale successful for John Doe", json.getString("message"), "message");
    }

    @And("I will see a summary of my Sales purchase")
    public void iWillSeeASummaryOfMySalesPurchase() {
        JsonPath details = response.jsonPath().setRootPath("details");
        assertEquals("GBP", details.getString("currency"), "Currency");
        assertEquals(123.45, details.getDouble("totalSale"), "Total Sale");
        assertEquals(23.45, details.getDouble("totalTax"), "Total Tax");
        assertEquals(1.23, details.getDouble("totalDiscount"), "Total Discount");
        assertEquals(12.12, details.getDouble("totalPostage"), "Total Postage");
        List<String> productList = details.getList("products");
        assertTrue(productList.contains("Product 1") && productList.contains("Product 2") && productList.contains("Product 3"), "Product list contains expected products");
        assertEquals("Leave outside by the pool.", details.getString("additionalDetails"));
    }

    @Then("I will be informed my Sales purchase is unsuccessful")
    public void iWillBeInformedMySalesPurchaseIsUnsuccessful() {
        //TODO create a mock for an empty products list
        throw new io.cucumber.java.PendingException("Create a mock for an empty products list and then add verifications");
    }
}
