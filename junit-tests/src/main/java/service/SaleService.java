package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.sale.SaleRequest;
import utility.EnvironmentConstants;

import java.util.HashMap;
import java.util.Map;

public class SaleService {
    private final String saleService = EnvironmentConstants.SALE_SERVICE;
    private final String host = EnvironmentConstants.HOST;
    private final RequestSpecification request = RestAssured.with().log().uri().accept("application/json");
    private final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

    /*Get the service status*/
    public Response ping(String token) {
        return request
                .headers(Map.of("Authorization", "Bearer " + token))
                .get(host + saleService + "/ping");
    }

    /*Get the service status - Used for no token testing*/
    public Response ping() {
        return request.get(host + saleService + "/ping");
    }

    /*Add a sale to a client record*/
    public Response addSale(SaleRequest saleRequest) {
        Map<String, SaleRequest> requestBody = new HashMap<>();
        requestBody.put("sale-request", saleRequest);
        String body = gsonBuilder.toJson(requestBody);
        return request.headers(new AuthService().getTokenHeader()).body(body).post(host + saleService + "/sale");
    }
}
