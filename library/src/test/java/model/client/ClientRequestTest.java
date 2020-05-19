package model.client;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientRequestTest {

    @Test
    void clientRequestJsonIsValid() {
        ClientRequest actualBody = new ClientRequest()
                .client(new Client())
                .externalClientId("123")
                .market("MarketX")
                .preferences(new Preferences());
        String expectedBody = "{\"businessId\":\"MarketX\",\"externalClientId\":\"123\",\"client\":{\"middleName\":\"Fitzgerald\",\"emailAddress\":\"test@test.123\",\"contactNumber\":\"+997271773662\",\"postalAddress1\":\"PO Box 123\",\"postalAddress2\":\"\",\"postalAddressState\":\"\",\"postalAddressCity\":\"PostalCity\",\"postalAddressPostCode\":\"A18-123\",\"billingAddress1\":\"123 Garden Vista\",\"billingAddress2\":\"Hill Street\",\"billingAddressState\":\"Eden\",\"billingAddressCity\":\"BillingCity\",\"billingAddressPostCode\":\"E18-123\"},\"preferences\":{\"gpdrAccepted\":true,\"tncAccepted\":true,\"optInEmail\":false,\"optInPostal\":false,\"optInSMS\":false,\"optInPhone\":false}}";
        assertEquals(expectedBody, new Gson().toJson(actualBody), "Request Body matches");
    }
}
