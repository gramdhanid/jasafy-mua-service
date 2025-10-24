package com.jasafy.muaservice.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class FeignConfig {
    /**
     * Logging level for Feign
     * NONE: No logging (production)
     * BASIC: Log only request method, URL, response status and execution time
     * HEADERS: Log basic + request and response headers
     * FULL: Log headers, body, and metadata (development only)
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;  // Change to BASIC in production
    }

    /**
     * Custom request options (timeouts)
     */
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                5000,  // connectTimeout in milliseconds
                TimeUnit.MILLISECONDS,
                10000, // readTimeout in milliseconds
                TimeUnit.MILLISECONDS,
                true   // followRedirects
        );
    }

    /**
     * Retry configuration
     * Default: 5 retries with exponential backoff
     * Custom: 3 retries with 1 second interval
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                1000L,  // period: initial retry interval (ms)
                5000L,  // maxPeriod: max retry interval (ms)
                3       // maxAttempts: max retry attempts
        );
    }
}
