package model.sale;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void emptyEntityJsonIsValid() {
        String expectedBody = "{\"baseCost\":0.0,\"percentVAT\":0.0,\"discountAmount\":0.0,\"amount\":0}";
        Product product = new Product();
        assertEquals(expectedBody, new Gson().toJson(product), "Product Json");
    }

    @Test
    void defaultEntityJsonIsValid() {
        String expectedBody = "{\"name\":\"Test product\",\"description\":\"A delectable product of awesome quality\",\"baseCost\":10.99,\"percentVAT\":0.21,\"discountAmount\":1.0,\"amount\":1}";
        Product product = new Product().getByName("Test product");
        assertEquals(expectedBody, new Gson().toJson(product), "Product Json");
    }
}