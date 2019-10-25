package stub.builder;

import io.specto.hoverfly.junit.core.model.Request;
import io.specto.hoverfly.junit.core.model.RequestFieldMatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;

public class StubRequest {
    private String path;
    private String method;
    private String host;
    private String body;
    private Map<String, List<RequestFieldMatcher>> query = new HashMap<>();
    private Map<String, List<RequestFieldMatcher>> headers = new HashMap<>();
    private Map<String, String> requiresState = new HashMap<>();

    public StubRequest path(String path) {
        this.path = path;
        return this;
    }

    public StubRequest method(StubMethod method) {
        this.method = method.name();
        return this;
    }

    public StubRequest host(String host) {
        this.host = host;
        return this;
    }

    public StubRequest body(String body) {
        this.body = body;
        return this;
    }

    public StubRequest query(Map<String, List<RequestFieldMatcher>> query) {
        this.query = query;
        return this;
    }

    public StubRequest headers(Map<String, List<RequestFieldMatcher>> headers) {
        this.headers = headers;
        return this;
    }

    public StubRequest addHeader(String field, String value) {
        headers.put(field, Collections.singletonList(RequestFieldMatcher.newExactMatcher(value)));
        return this;
    }


    public StubRequest requiresState(Map<String, String> requiresState) {
        this.requiresState = requiresState;
        return this;
    }

    public Request build() {
        Request request = new Request();
        request.setMethod(Collections.singletonList(RequestFieldMatcher.newExactMatcher(method)));
        request.setPath(Collections.singletonList(RequestFieldMatcher.newExactMatcher(path)));
        request.setDestination(Collections.singletonList(RequestFieldMatcher.newExactMatcher(host.substring(host.lastIndexOf("/") + 1))));
        request.setScheme(Collections.singletonList(RequestFieldMatcher.newExactMatcher(host.substring(0, host.indexOf(":")))));

        if (headers != null && !headers.isEmpty()) request.setHeaders(headers);
        if (query != null && !query.isEmpty()) request.setQuery(query);
        if (requiresState != null && !requiresState.isEmpty()) request.setRequiresState(requiresState);
        if (body != null)
            request.setBody(Collections.singletonList(RequestFieldMatcher.newExactMatcher(jsonWithSingleQuotes(body).body())));

        return request;
    }
}
