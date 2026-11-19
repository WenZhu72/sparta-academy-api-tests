package com.sparta.wzz.tests;

import com.sparta.wzz.client.ApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseApiTest {

    private final ApiClient apiClient = new ApiClient();

    @Test
    void shouldRetrieveAllCourses() {

        Response response = apiClient.get("/api/Courses");

        assertEquals(200, response.statusCode());

        assertFalse(
                response.jsonPath().getList("$").isEmpty(),
                "Course list should not be empty"
        );
    }
}
