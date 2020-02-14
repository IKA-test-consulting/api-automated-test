package utility;

import util.PropertiesReader;

public class EnvironmentConstants {
    private static final PropertiesReader properties = new PropertiesReader("/src/main/resources/", "junit-environment.properties");
    public static final String AUTH_SERVICE = properties.getProperty("AUTH_SERVICE");
    public static final String HOST = properties.getProperty("HOST");
    public static final String CLIENT_SERVICE = properties.getProperty("CLIENT_SERVICE");
    public static final String SALE_SERVICE = properties.getProperty("SALE_SERVICE");
}
