package com.sparta.wzz.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.en.Given;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExternalDataSteps {
    private final ScenarioContext context;
    public ExternalDataSteps(ScenarioContext context) { this.context = context; }

    @Given("I load Spartan request for test case {string} from {string}")
    public void loadSpartanTestCase(String caseName, String resourcePath) throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) throw new IllegalArgumentException("Missing test data resource: " + resourcePath);
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> cases;
        try (stream) { cases = mapper.readValue(stream, new TypeReference<>() {}); }
        String name = cases.stream()
            .filter(row -> caseName.equals(row.get("case")))
            .map(row -> row.get("firstName"))
            .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown test case: " + caseName));
        context.setRequestBody(mapper.writeValueAsString(Map.of("firstName", name, "lastName", "Spartan")));
    }
}
