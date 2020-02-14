package service;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public class SalesResponse {
    private final JsonPath json;

    public SalesResponse(Response response) {
        json = response.jsonPath();
    }

    public Object getFieldForSalesId(int id, String fieldName) {
        List<HashMap<String, Object>> jsonList = json.getList("sales");
        for (HashMap<String, Object> record : jsonList) {
            if ((int) (record).get("id") == id) {
                return (record).get(fieldName);
            }
        }
        return null;
    }
}
