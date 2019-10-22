package service;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;

public class SalesResponse {
    private final JsonPath json;

    public SalesResponse(Response response) {
        json = response.jsonPath();
    }

    public Object getFieldForSalesId(int id, String fieldName) {
        for (Object record : json.getList("sales")) {
            if ((int) ((HashMap) record).get("id") == id) {
                return ((HashMap) record).get(fieldName);
            }
        }
        return null;
    }
}
