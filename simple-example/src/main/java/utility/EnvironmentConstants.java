package utility;

import util.PropertiesReader;

public class EnvironmentConstants {
    private static final PropertiesReader properties = new PropertiesReader("/src/main/resources/", "simple.properties");
    public static final String HOST = properties.getProperty("simple.service.url");
    public static final String AUTH_SERVICE = properties.getProperty("simple.service.auth");
    public static final String SALES_SERVICE = properties.getProperty("simple.service.sales");
}
