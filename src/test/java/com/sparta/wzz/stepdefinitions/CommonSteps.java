
package com.sparta.wzz.stepdefinitions;

import com.sparta.wzz.auth.TokenManager;
import com.sparta.wzz.client.ApiClient;
import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CommonSteps {

    private final ApiClient apiClient = new ApiClient();
    private final TokenManager tokenManager =
            new TokenManager(apiClient);

    private final ScenarioContext context;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("I am authenticated")
    public void iAmAuthenticated() {
        context.setToken(tokenManager.getToken());
    }

    @Given("I have the following request body:")
    public void iHaveRequestBody(String json) {
        context.setRequestBody(json);
    }

    @When("I send a GET request to {string}")
    public void iSendGetRequest(String endpoint) {
        context.setResponse(context.getToken() == null
                ? apiClient.get(endpoint)
                : apiClient.get(endpoint, context.getToken()));
    }

    @When("I send a POST request to {string}")
    public void iSendPostRequest(String endpoint) {

        context.setResponse(
                apiClient.post(
                        endpoint,
                        context.getRequestBody(),
                        context.getToken()
                )
        );

        System.out.println("Status code: "
                + context.getResponse().statusCode());

        System.out.println("Response body:");
        context.getResponse().prettyPrint();
    }

    @When("I send a PUT request to {string}")
    public void iSendPutRequest(String endpoint) {
        context.setResponse(
                apiClient.put(
                        endpoint,
                        context.getRequestBody(),
                        context.getToken()
                )
        );
    }

    @When("I send a DELETE request to {string}")
    public void iSendDeleteRequest(String endpoint) {
        context.setResponse(
                apiClient.delete(endpoint, context.getToken())
        );
    }

    @Given("I use the bearer token {string}")
    public void iUseTheBearerToken(String token) {
        context.setToken(token);
    }

    @When("I send an unauthenticated GET request to {string}")
    public void iSendUnauthenticatedGetRequest(String endpoint) {
        context.setResponse(apiClient.get(endpoint));
    }

    @When("I send an unauthenticated DELETE request to {string}")
    public void iSendUnauthenticatedDeleteRequest(String endpoint) {
        context.setResponse(apiClient.delete(endpoint));
    }

    @Then("the response status code should be {int}")
    public void responseStatusCodeShouldBe(int statusCode) {
        assertThat(
                context.getResponse().statusCode(),
                equalTo(statusCode)
        );
    }

    @When("I send an unauthenticated POST request to {string}")
    public void iSendUnauthenticatedPostRequest(String endpoint) {
        context.setResponse(
                apiClient.post(endpoint, context.getRequestBody())
        );
    }

    @When("I send an unauthenticated PUT request to {string}")
    public void iSendUnauthenticatedPutRequest(String endpoint) {
        context.setResponse(
                apiClient.put(endpoint, context.getRequestBody())
        );
    }
}
