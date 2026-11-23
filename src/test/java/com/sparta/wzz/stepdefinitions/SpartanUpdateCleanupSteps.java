
package com.sparta.wzz.stepdefinitions;

import com.sparta.wzz.client.ApiClient;
import com.sparta.wzz.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class SpartanUpdateCleanupSteps {

    private static final String ENDPOINT = "/api/spartans/21";

    private final ApiClient client = new ApiClient();
    private final ScenarioContext context;
    private boolean restoreRequired;

    public SpartanUpdateCleanupSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("the original seeded Spartan 21 is available for restoration")
    public void checkOriginalSeed() {
        Response response = client.get(ENDPOINT, context.getToken());

        assertEquals(200, response.statusCode(),
                "Cannot inspect original Spartan");

        assertEquals("Rosalind",
                response.jsonPath().getString("firstName"),
                "Spartan 21 is not in its original seeded state");

        assertEquals("Franklin",
                response.jsonPath().getString("lastName"));

        assertEquals("TECH 300",
                response.jsonPath().getString("course"));

        restoreRequired = true;
    }

    @After("@RestoreSeededSpartan")
    public void restoreSeededSpartan() {

        if (!restoreRequired) {
            return;
        }

        String original = """
                {
                  "id": 21,
                  "firstName": "Rosalind",
                  "lastName": "Franklin",
                  "university": "University of Cambridge",
                  "degree": "Natural Science",
                  "course": {
                    "id": 1,
                    "name": "TECH 300",
                    "stream": {
                      "id": 1,
                      "name": "C# Dev",
                      "courses": []
                    },
                    "trainer": "Phil Windridge",
                    "startDate": "2023-03-01T00:00:00",
                    "endDate": "2023-05-01T00:00:00",
                    "courseId": 1
                  },
                  "graduated": false
                }
                """;

        Response update = client.put(
                ENDPOINT,
                original,
                context.getToken()
        );

        assertEquals(204, update.statusCode(),
                "Failed to restore seeded Spartan: " + update.asString());

        Response restored = client.get(
                ENDPOINT,
                context.getToken()
        );

        assertEquals(200, restored.statusCode(),
                "Cannot verify restored Spartan");

        assertAll("Verify original Spartan restored",
                () -> assertEquals("Rosalind",
                        restored.jsonPath().getString("firstName")),
                () -> assertEquals("Franklin",
                        restored.jsonPath().getString("lastName")),
                () -> assertEquals("University of Cambridge",
                        restored.jsonPath().getString("university")),
                () -> assertEquals("Natural Science",
                        restored.jsonPath().getString("degree")),
                () -> assertEquals("TECH 300",
                        restored.jsonPath().getString("course")),
                () -> assertFalse(
                        restored.jsonPath().getBoolean("graduated"))
        );
    }
}
