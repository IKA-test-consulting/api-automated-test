package model.client;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreferencesTest {
    @Test
    public void clientWithIdMandatoryValues() {
        String expected = "{\"gpdrAccepted\":true,\"tncAccepted\":true,\"optInEmail\":false,\"optInPostal\":false,\"optInSMS\":false,\"optInPhone\":false}";
        Preferences preferences = new Preferences();
        assertEquals(expected, new Gson().toJson(preferences), "Preference JSON");
    }
}
