package com.sparta.wzz.stepdefinitions;

import com.sparta.wzz.client.ApiClient;
import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthenticationSteps {

    private final ApiClient apiClient = new ApiClient();
    private final ScenarioContext context;

    private Map<String, String> credentials;

    private static final String LOGIN_ENDPOINT = "/Auth/login";

    public AuthenticationSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("I have valid login credentials")
    public void iHaveValidLoginCredentials() {

        credentials = new HashMap<>();
        credentials.put("username", "sparta");
        credentials.put("password", "global");
    }

    @Given("I have invalid login credentials")
    public void iHaveInvalidLoginCredentials() {

        credentials = new HashMap<>();
        credentials.put("username", "invalid");
        credentials.put("password", "wrongpassword");
    }

    @Given("I have no login credentials")
    public void iHaveNoLoginCredentials() {
        credentials = new HashMap<>();
    }

    @Given("I have login credentials {string} and {string}")
    public void iHaveLoginCredentials(
            String username,
            String password) {

        credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
    }

    @When("I submit a login request")
    public void iSubmitALoginRequest() {

        context.setResponse(
                apiClient.post(LOGIN_ENDPOINT, credentials)
        );
    }

    @Then("the response should contain a JWT token")
    public void responseShouldContainAJwtToken() {

        String token = context.getResponse()
                .jsonPath()
                .getString("token");

        assertThat(
                "JWT should not be empty",
                token,
                not(emptyOrNullString())
        );

        String[] segments = token.split("\\.", -1);

        assertThat(
                "JWT should contain three segments",
                segments.length,
                equalTo(3)
        );

        for (String segment : segments) {
            assertThat(
                    "JWT segment should not be empty",
                    segment,
                    not(emptyString())
            );
        }
    }

    @Then("the login request should be rejected")
    public void loginRequestShouldBeRejected() {

        assertThat(
                context.getResponse().statusCode(),
                allOf(
                        greaterThanOrEqualTo(400),
                        lessThan(500)
                )
        );
    }
}
