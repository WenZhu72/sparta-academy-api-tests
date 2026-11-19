package com.sparta.wzz.auth;

import com.sparta.wzz.client.ApiClient;
import io.restassured.response.Response;

import java.util.Map;

public class TokenManager {

    private final ApiClient apiClient;
    private String token;

    public TokenManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String getToken() {

        if (token == null || token.isBlank()) {
            login();
        }

        return token;
    }

    public void login() {

        String username = System.getenv()
                .getOrDefault("API_USERNAME", "sparta");

        String password = System.getenv()
                .getOrDefault("API_PASSWORD", "global");

        Map<String, String> credentials = Map.of(
                "username", username,
                "password", password
        );

        Response response = apiClient.post(
                "/Auth/login",
                credentials
        );

        token = response.jsonPath().getString("token");

        if (response.statusCode() != 200) {
            throw new IllegalArgumentException(
                    "Authentication failed. Status: "
                    + response.statusCode()
            );
        }
    }

    public void clearToken() {
        token = null;
    }
}
