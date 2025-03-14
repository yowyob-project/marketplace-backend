package com.marketplace.services.client;

import com.marketplace.models.RessourceRequest;
import com.marketplace.models.RessourceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RessourceClient {
    private final WebClient webClient;

    public RessourceResponse createRessource(RessourceRequest request, UUID ownerId, UUID categorieId) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/create")
                        .queryParam("ownerId", ownerId)
                        .queryParam("categorieId", categorieId)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RessourceResponse.class)
                .block(); // Bloque pour attendre la réponse (synchronisation)
    }

    public RessourceResponse getRessourceById(UUID id) {
        return webClient.get()
                .uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RessourceResponse.class)
                .block();
    }

    public List<RessourceResponse> getAllRessources(UUID ownerId, UUID categorieId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParamIfPresent("ownerId", ownerId != null ? java.util.Optional.of(ownerId) : java.util.Optional.empty())
                        .queryParamIfPresent("categorieId", categorieId != null ? java.util.Optional.of(categorieId) : java.util.Optional.empty())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(RessourceResponse.class)
                .collectList()
                .block();
    }

    public RessourceResponse updateRessource(UUID id, RessourceRequest request, UUID ownerId, UUID catalogueId) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path("/update/{id}")
                        .queryParamIfPresent("ownerId", ownerId != null ? java.util.Optional.of(ownerId) : java.util.Optional.empty())
                        .queryParamIfPresent("catalogueId", catalogueId != null ? java.util.Optional.of(catalogueId) : java.util.Optional.empty())
                        .build(id))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RessourceResponse.class)
                .block();
    }
}
