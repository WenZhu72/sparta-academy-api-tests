package com.sparta.wzz.stepdefinitions;

import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.en.Then;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseSteps {
    private final ScenarioContext context;
    public ResponseSteps(ScenarioContext context) { this.context = context; }

    @Then("the response field {string} should equal {int}")
    public void integerFieldEquals(String field, int expected) {
        assertThat(context.getResponse().jsonPath().getInt(field), is(expected));
    }

    @Then("the response should contain the field {string}")
    public void fieldExists(String field) {
        Map<String, Object> body = context.getResponse().jsonPath().getMap("$");
        assertThat("Missing response field: " + field, body.containsKey(field), is(true));
    }

    @Then("the response should be a JSON array")
    public void responseIsArray() {
        List<?> list = context.getResponse().jsonPath().getList("$");
        assertThat(list, is(notNullValue()));
    }

    @Then("the validation errors should include {string}")
    public void validationErrorIncludes(String field) {
        Map<String, Object> errors = context.getResponse().jsonPath().getMap("errors");
        assertThat("Expected validation errors object", errors, is(notNullValue()));
        assertThat("Expected validation error for " + field, errors.containsKey(field), is(true));
    }
}
