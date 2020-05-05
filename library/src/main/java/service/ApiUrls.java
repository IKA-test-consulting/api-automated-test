package service;

import java.util.Map;

public class ApiUrls {
    private final Map<String, String> urls;

    public ApiUrls(Map<String, String> urls) {
        this.urls = urls;
    }

    public String get(ServiceName service) {
        if (urls.containsKey(service.name())) {
            return urls.get(service.name());
        } else
            throw new RuntimeException("Service '" + service.name() + "' is not yet added to the environment constants");
    }
}
