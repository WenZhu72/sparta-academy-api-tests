
package com.sparta.wzz.context;

import io.restassured.response.Response;

public class ScenarioContext {

    private Response response;
    private String token;
    private String requestBody;
    private Integer spartanId;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getSpartanId() {
        return spartanId;
    }

    public void setSpartanId(Integer spartanId) {
        this.spartanId = spartanId;
    }
}
