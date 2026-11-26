
package com.sparta.wzz.client;

import com.sparta.wzz.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    // Create a reusable request specification
    public RequestSpecification request() {
        return RestAssured.given()
                .baseUri(ApiConfig.getBaseUrl())
                .accept("application/json");
    }

    // Public GET request
    public Response get(String endpoint) {
        return request()
                .when()
                .get(endpoint);
    }

    // Authenticated GET request
    public Response get(String endpoint, String token) {
        return request()
                .auth()
                .oauth2(token)
                .when()
                .get(endpoint);
    }

    // Public POST request
    public Response post(String endpoint, Object body) {
        return request()
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    // Authenticated POST request
    public Response post(String endpoint, Object body, String token) {
        return request()
                .auth()
                .oauth2(token)
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    // Authenticated PUT request
    public Response put(String endpoint, Object body, String token) {
        return request()
                .auth()
                .oauth2(token)
                .contentType("application/json")
                .body(body)
                .when()
                .put(endpoint);
    }

    // Public DELETE request, useful for authorization tests.
    public Response delete(String endpoint) {
        return request().when().delete(endpoint);
    }

    // Authenticated
    public Response delete(String endpoint, String token) {
        return request()
                .auth()
                .oauth2(token)
                .when()
                .delete(endpoint);
    }

    // Unauthenticated PUT request
    public Response put(String endpoint, Object body) {
        return request()
                .contentType("application/json")
                .body(body)
                .when()
                .put(endpoint);
    }
}
