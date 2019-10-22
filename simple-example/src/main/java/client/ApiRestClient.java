package client;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiRestClient {
    private RequestSpecification request = RestAssured.with().log().uri().accept("application/json");

    public ApiRestClient(String token) {
        request.header("Authorization", "Bearer " + token);
    }

    public ApiRestClient() {
        //used by auth service as no token is available
    }

    public Response get(String url, Map<String, Object> queryParams) {
        return request.queryParams(queryParams).get(url);
    }

    public Response get(String url) {
        return request.get(url);
    }

    public Response getAuth(String url, Map<String, String> headers) {
        return request.headers(headers).get(url);
    }
}
