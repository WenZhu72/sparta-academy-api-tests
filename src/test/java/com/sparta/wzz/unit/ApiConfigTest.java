package com.sparta.wzz.unit;

import com.sparta.wzz.config.ApiConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiConfigTest {
    @Test
    void usesDefaultBaseUrlWhenPropertyMissing() {
        String previous = System.getProperty("api.base.url");
        try {
            System.clearProperty("api.base.url");
            assertEquals("http://localhost:8080", ApiConfig.getBaseUrl());
        } finally {
            if (previous == null) System.clearProperty("api.base.url");
            else System.setProperty("api.base.url", previous);
        }
    }
}
