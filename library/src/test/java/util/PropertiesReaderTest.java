package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesReaderTest {
    //If the file name and path are incorrect then the tests will fail due to null pointer exception

    @Test
    void loadPropertyFileAndGetProperty() {
        PropertiesReader reader = new PropertiesReader("/src/test/resources/", "library-test.properties");
        assertEquals(reader.getProperty("test.property"), "test_property", "Test property loaded");
        assertNull(reader.getProperty("fake_property"), "Test property does not exist");
    }
}