package stub.builder;

import io.specto.hoverfly.junit.core.model.Response;

import java.util.*;

import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.jsonWithSingleQuotes;

@SuppressWarnings({"unused"})
public class StubResponse {
    private Integer status;
    private String body;
    private boolean isEncodedBody = false;
    private boolean isTemplate = false;
    private Map<String, List<String>> headers = new HashMap<>();
    private Map<String, String> transitionsState = new HashMap<>();
    private List<String> removeStates = new ArrayList<>();

    public StubResponse status(Integer status) {
        this.status = status;
        return this;
    }

    public StubResponse body(String body) {
        this.body = body;
        return this;
    }

    public StubResponse encodedBody(boolean encodedBody) {
        isEncodedBody = encodedBody;
        return this;
    }

    public StubResponse template(boolean template) {
        isTemplate = template;
        return this;
    }

    public StubResponse headers(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

    public StubResponse addHeader(String field, String value) {
        headers.put(field, Collections.singletonList(value));
        return this;
    }

    public StubResponse transitionsState(Map<String, String> transitionsState) {
        this.transitionsState = transitionsState;
        return this;
    }

    public StubResponse removeStates(List<String> removeStates) {
        this.removeStates = removeStates;
        return this;
    }

    public Response build() {
        return new Response(status,
                jsonWithSingleQuotes(body).body(),
                isEncodedBody,
                isTemplate,
                headers,
                transitionsState,
                removeStates);
    }
}
