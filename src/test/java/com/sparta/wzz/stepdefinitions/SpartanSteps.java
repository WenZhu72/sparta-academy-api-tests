
package com.sparta.wzz.stepdefinitions;

import com.sparta.wzz.client.ApiClient;
import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanSteps {

    private static final String SPARTANS_ENDPOINT = "/api/spartans";

    private final ApiClient apiClient = new ApiClient();
    private final ScenarioContext context;

    public SpartanSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("I store the created Spartan ID")
    public void iStoreCreatedSpartanId() {

        int id = context.getResponse()
                .jsonPath()
                .getInt("id");

        assertThat(
                "Created Spartan ID must be positive",
                id,
                greaterThan(0)
        );

        context.setSpartanId(id);
    }

    @When("I send a GET request to the created Spartan")
    public void iRetrieveCreatedSpartan() {

        String endpoint = SPARTANS_ENDPOINT
                + "/" + context.getSpartanId();

        context.setResponse(
                apiClient.get(
                        endpoint,
                        context.getToken()
                )
        );
    }

    @Then("the Spartan ID should match the created Spartan")
    public void spartanIdShouldMatch() {

        int returnedId = context.getResponse()
                .jsonPath()
                .getInt("id");

        assertThat(
                returnedId,
                equalTo(context.getSpartanId())
        );
    }

    @Then("the Spartan first name should be {string}")
    public void spartanFirstNameShouldBe(String expectedFirstName) {
        String actualFirstName =
                context.getResponse().jsonPath().getString("firstName");

        assertThat(actualFirstName, equalTo(expectedFirstName));
    }

    @Then("the Spartan last name should be {string}")
    public void spartanLastNameShouldBe(String expectedLastName) {
        String actualLastName =
                context.getResponse().jsonPath().getString("lastName");

        assertThat(actualLastName, equalTo(expectedLastName));
    }
}
