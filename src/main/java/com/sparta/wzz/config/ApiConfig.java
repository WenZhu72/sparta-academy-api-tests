package com.sparta.wzz.config;

public class ApiConfig {

    private static final String DEFAULT_BASE_URL =
            "http://localhost:8080";
    // https://spartaacademyapi20240530152521.azurewebsites.net

    private ApiConfig(){
        // Prevent instantiation
    }

    public static String getBaseUrl(){
        return System.getProperty(
                "api.base.url",
                DEFAULT_BASE_URL
        );
    }
}
