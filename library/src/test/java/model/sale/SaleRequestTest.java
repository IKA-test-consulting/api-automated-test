package model.sale;

import com.google.gson.Gson;
import model.client.Client;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaleRequestTest {
    @Test
    void saleRequestBodyValid() {
        String expectedBody = "{\"businessId\":\"MarketX\",\"client\":{\"middleName\":\"Fitzgerald\",\"clientId\":\"int-1\",\"emailAddress\":\"test@test.123\",\"contactNumber\":\"+997271773662\",\"postalAddress1\":\"PO Box 123\",\"postalAddress2\":\"\",\"postalAddressState\":\"\",\"postalAddressCity\":\"PostalCity\",\"postalAddressPostCode\":\"A18-123\",\"billingAddress1\":\"123 Garden Vista\",\"billingAddress2\":\"Hill Street\",\"billingAddressState\":\"Eden\",\"billingAddressCity\":\"BillingCity\",\"billingAddressPostCode\":\"E18-123\"},\"products\":[{\"name\":\"product 1\",\"description\":\"A delectable product of awesome quality\",\"baseCost\":10.99,\"percentVAT\":0.21,\"discountAmount\":1.0,\"amount\":1}],\"coupons\":[\"X-123\"]}";
        SaleRequest actualBody = new SaleRequest()
                .market("MarketX")
                .coupons(Collections.singletonList("X-123"))
                .client(new Client().ClientId("int-1"))
                .products(Collections.singletonList(new Product().getByName("product 1")));
        assertEquals(expectedBody, new Gson().toJson(actualBody), "Request Body");
    }
}
