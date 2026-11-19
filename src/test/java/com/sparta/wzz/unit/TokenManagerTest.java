package com.sparta.wzz.unit;

import com.sparta.wzz.auth.TokenManager;
import com.sparta.wzz.client.ApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TokenManagerTest {
    @Test
    void cachesSuccessfulLoginToken() {
        ApiClient client = mock(ApiClient.class);
        Response response = mock(Response.class);
        io.restassured.path.json.JsonPath json = mock(io.restassured.path.json.JsonPath.class);
        when(client.post(eq("/Auth/login"), any(Map.class))).thenReturn(response);
        when(response.jsonPath()).thenReturn(json);
        when(json.getString("token")).thenReturn("test-token");
        when(response.statusCode()).thenReturn(200);
        TokenManager manager = new TokenManager(client);
        assertEquals("test-token", manager.getToken());
        assertEquals("test-token", manager.getToken());
        verify(client, times(1)).post(eq("/Auth/login"), any(Map.class));
    }

    @Test
    void throwsWhenLoginFails() {
        ApiClient client = mock(ApiClient.class);
        Response response = mock(Response.class);
        io.restassured.path.json.JsonPath json = mock(io.restassured.path.json.JsonPath.class);
        when(client.post(eq("/Auth/login"), any(Map.class))).thenReturn(response);
        when(response.jsonPath()).thenReturn(json);
        when(response.statusCode()).thenReturn(401);
        TokenManager manager = new TokenManager(client);
        assertThrows(IllegalArgumentException.class, manager::getToken);
    }

    @Test
    void clearTokenForcesNewLogin() {
        ApiClient client = mock(ApiClient.class);
        Response response = mock(Response.class);
        io.restassured.path.json.JsonPath json = mock(io.restassured.path.json.JsonPath.class);
        when(client.post(eq("/Auth/login"), any(Map.class))).thenReturn(response);
        when(response.jsonPath()).thenReturn(json);
        when(json.getString("token")).thenReturn("test-token");
        when(response.statusCode()).thenReturn(200);
        TokenManager manager = new TokenManager(client);
        manager.getToken();
        manager.clearToken();
        manager.getToken();
        verify(client, times(2)).post(eq("/Auth/login"), any(Map.class));
    }
}
