package com.marketplace.services.impl;

import com.marketplace.models.NotificationRequest;
import com.marketplace.models.NotificationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NotificationServiceImpl {
    private final WebClient notificationServiceWebClient;

    public NotificationServiceImpl(WebClient notificationServiceWebClient) {
        this.notificationServiceWebClient = notificationServiceWebClient;
    }

    public Mono<NotificationResponse> sendEmailNotification(NotificationRequest notificationRequest) {
        return notificationServiceWebClient.post()
                .uri("/api/v1/notifications/email")
                .header("Content-Type", "application/json")
                .bodyValue(notificationRequest)
                .retrieve() // Utilise `retrieve()` pour récupérer la réponse
                .bodyToMono(NotificationResponse.class) // Mappe la réponse en NotificationResponse
                .onErrorResume(e -> Mono.error(new Exception("Notification failed with error: " + e.getMessage())));
    }
}
