package utility;

import service.ApiUrls;
import service.ServiceName;
import util.PropertiesReader;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentConstants {
    public final String AUTH_SERVICE;
    public final String HOST;
    public final String CLIENT_SERVICE;
    public final String SALE_SERVICE;

    public EnvironmentConstants() {
        PropertiesReader properties = new PropertiesReader("/src/main/resources/", "bdd-environment.properties");
        AUTH_SERVICE = properties.getProperty("AUTH_SERVICE");
        HOST = properties.getProperty("HOST");
        CLIENT_SERVICE = properties.getProperty("CLIENT_SERVICE");
        SALE_SERVICE = properties.getProperty("SALE_SERVICE");
    }

    public ApiUrls urls() {
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put(ServiceName.HOST.name(), HOST);
        urlMap.put(ServiceName.AUTH.name(), AUTH_SERVICE);
        urlMap.put(ServiceName.CLIENT.name(), CLIENT_SERVICE);
        urlMap.put(ServiceName.SALE.name(), SALE_SERVICE);
        return new ApiUrls(urlMap);
    }
}
