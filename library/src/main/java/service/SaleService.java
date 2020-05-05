package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.sale.SaleRequest;

import java.util.HashMap;
import java.util.Map;

public class SaleService {
    private final String SALES_SERVICE;
    private final RequestSpecification request = RestAssured.with().log().uri().accept("application/json");
    private final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
    private final ApiUrls urls;

    public SaleService(ApiUrls urls) {
        this.urls = urls;
        SALES_SERVICE = urls.get(ServiceName.HOST) + urls.get(ServiceName.SALE);
    }

    /*Get the service status*/
    public Response ping(String token) {
        return request
                .headers(Map.of("Authorization", "Bearer " + token))
                .get(SALES_SERVICE + "/ping");
    }

    /*Get the service status - Used for no token testing*/
    public Response ping() {
        return request.get(SALES_SERVICE + "/ping");
    }

    /*Add a sale to a client record*/
    public Response addSale(SaleRequest saleRequest) {
        Map<String, SaleRequest> requestBody = new HashMap<>();
        requestBody.put("sale-request", saleRequest);
        String body = gsonBuilder.toJson(requestBody);
        return request.headers(new AuthService(urls).getTokenHeader()).body(body).post(SALES_SERVICE + "/sale");
    }
}
