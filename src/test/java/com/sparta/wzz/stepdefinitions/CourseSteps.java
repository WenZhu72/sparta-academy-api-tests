
package com.sparta.wzz.stepdefinitions;

import com.sparta.wzz.client.ApiClient;
import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourseSteps {

    private final ApiClient apiClient = new ApiClient();
    private final ScenarioContext context;

    public CourseSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I retrieve all courses")
    public void iRetrieveAllCourses() {

        context.setResponse(
                apiClient.get("/api/Courses")
        );
    }

    @When("I retrieve course with ID {int}")
    public void iRetrieveCourseWithId(int id) {

        context.setResponse(
                apiClient.get("/api/Courses/" + id)
        );
    }

    @And("the response should contain courses")
    public void responseShouldContainCourses() {

        assertThat(
                context.getResponse().jsonPath().getList("$"),
                is(not(empty()))
        );
    }

    @And("all courses should have valid IDs")
    public void allCoursesShouldHaveValidIds() {

        assertThat(
                context.getResponse()
                        .jsonPath()
                        .getList("id", Integer.class),
                everyItem(greaterThan(0))
        );
    }

    @And("all courses should have valid names")
    public void allCoursesShouldHaveValidNames() {

        assertThat(
                context.getResponse()
                        .jsonPath()
                        .getList("name", String.class),
                everyItem(not(emptyOrNullString()))
        );
    }

    @And("the course name should be {string}")
    public void courseNameShouldBe(String expectedName) {

        assertThat(
                context.getResponse()
                        .jsonPath()
                        .getString("name"),
                equalTo(expectedName)
        );
    }
}
