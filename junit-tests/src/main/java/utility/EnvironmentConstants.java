package utility;

import service.ApiUrls;
import service.ServiceName;
import util.PropertiesReader;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentConstants {
    private static final PropertiesReader properties = new PropertiesReader("/src/main/resources/", "junit-environment.properties");
    public static final String AUTH_SERVICE = properties.getProperty("AUTH_SERVICE");
    public static final String HOST = properties.getProperty("HOST");
    public static final String CLIENT_SERVICE = properties.getProperty("CLIENT_SERVICE");
    public static final String SALE_SERVICE = properties.getProperty("SALE_SERVICE");
    public static final ApiUrls urls = new ApiUrls(toMap());

    private static Map<String, String> toMap(){
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put(ServiceName.HOST.name(), HOST);
        urlMap.put(ServiceName.AUTH.name(), AUTH_SERVICE);
        urlMap.put(ServiceName.CLIENT.name(), CLIENT_SERVICE);
        urlMap.put(ServiceName.SALE.name(), SALE_SERVICE);
        return urlMap;
    }
}
