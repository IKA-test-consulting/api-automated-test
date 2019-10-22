package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentUtility {
    private static Properties properties;

    private static void loadPropertiesFromFile() {
        try {
            InputStream in = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/simple.properties");
            properties = new Properties();
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String propertyName) {
        if (properties == null) {
            loadPropertiesFromFile();
        }
        return properties.getProperty(propertyName);
    }
}
