package model.client;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    private final Client client = new Client();

    @Test
    public void defaultClientValues() {
        String expected = "{\"middleName\":\"Fitzgerald\",\"emailAddress\":\"test@test.123\",\"contactNumber\":\"+997271773662\",\"postalAddress1\":\"PO Box 123\",\"postalAddress2\":\"\",\"postalAddressState\":\"\",\"postalAddressCity\":\"PostalCity\",\"postalAddressPostCode\":\"A18-123\",\"billingAddress1\":\"123 Garden Vista\",\"billingAddress2\":\"Hill Street\",\"billingAddressState\":\"Eden\",\"billingAddressCity\":\"BillingCity\",\"billingAddressPostCode\":\"E18-123\"}";
        assertEquals(expected, new Gson().toJson(client), "Client JSON");
    }

    @Test
    public void clientWithMandatoryValues() {
        String expected = "{\"foreName\":\"testForeName\",\"middleName\":\"Fitzgerald\",\"surName\":\"testSurName\",\"emailAddress\":\"test@test.123\",\"contactNumber\":\"+997271773662\",\"postalAddress1\":\"PO Box 123\",\"postalAddress2\":\"\",\"postalAddressState\":\"\",\"postalAddressCity\":\"PostalCity\",\"postalAddressPostCode\":\"A18-123\",\"billingAddress1\":\"123 Garden Vista\",\"billingAddress2\":\"Hill Street\",\"billingAddressState\":\"Eden\",\"billingAddressCity\":\"BillingCity\",\"billingAddressPostCode\":\"E18-123\"}";
        client.withMandatory("testForeName", "testSurName");
        assertEquals(expected, new Gson().toJson(client), "Client JSON");
    }

    @Test
    public void clientWithIdMandatoryValues() {
        String expected = "{\"middleName\":\"Fitzgerald\",\"identificationNumber\":\"ID-123\",\"identificationType\":\"PASSPORT\",\"emailAddress\":\"test@test.123\",\"contactNumber\":\"+997271773662\",\"postalAddress1\":\"PO Box 123\",\"postalAddress2\":\"\",\"postalAddressState\":\"\",\"postalAddressCity\":\"PostalCity\",\"postalAddressPostCode\":\"A18-123\",\"billingAddress1\":\"123 Garden Vista\",\"billingAddress2\":\"Hill Street\",\"billingAddressState\":\"Eden\",\"billingAddressCity\":\"BillingCity\",\"billingAddressPostCode\":\"E18-123\"}";
        client.withIdMandatory();
        assertEquals(expected, new Gson().toJson(client), "Client JSON");
    }
}
