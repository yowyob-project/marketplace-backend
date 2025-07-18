package com.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:4010").build();
    }

    @Bean
    public WebClient paymentServiceWebClient() {
        return WebClient.builder()
                .baseUrl("https://gateway.yowyob.com/payment-service")
                .build();
    }

    @Bean
    public WebClient notificationServiceWebClient() {
        return WebClient.builder()
                .baseUrl("https://gateway.yowyob.com/notification-service")
                .build();
    }
}

