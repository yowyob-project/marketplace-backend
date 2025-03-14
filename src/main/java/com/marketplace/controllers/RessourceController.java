package com.marketplace.controllers;

import com.marketplace.models.RessourceRequest;
import com.marketplace.models.RessourceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/ressource-client")
@Tag(name = "Ressource", description = "Ressource management APIs")
public class RessourceController {

    private final WebClient webClient;

    // Créer une ressource
    @PostMapping("/create")
    @Operation(summary = "Create ressource",
            description = "Create a new ressource")
    @ApiResponse(responseCode = "200", description = "New Ressource successfully")
    public ResponseEntity<RessourceResponse> createRessource(
            @RequestBody RessourceRequest request,
            @RequestParam UUID ownerId,
            @RequestParam UUID categorieId
    ) {
        try {
            RessourceResponse response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/ressource/create")
                            .queryParamIfPresent("ownerId", Optional.ofNullable(ownerId))
                            .queryParamIfPresent("categorieId", Optional.ofNullable(categorieId))
                            .build())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(RessourceResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Récupérer une ressource par ID
    @GetMapping("/{id}")
    @Operation(summary = "Get ressource",
            description = "Get a ressource")
    @ApiResponse(responseCode = "200", description = "Get Ressource successfully")
    public ResponseEntity<RessourceResponse> getRessourceById(@PathVariable UUID id) {
        try {
            RessourceResponse response = webClient.get()
                    .uri("/api/v1/ressource/{id}", id)
                    .retrieve()
                    .bodyToMono(RessourceResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Récupérer toutes les ressources
    @GetMapping
    @Operation(summary = "Get all ressources",
            description = "Get all ressources")
    @ApiResponse(responseCode = "200", description = "Get Ressources successfully")
    public ResponseEntity<List<RessourceResponse>> getAllRessources(
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) UUID categorieId
    ) {
        try {
            List<RessourceResponse> responses = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/ressource")
                            .queryParamIfPresent("ownerId", Optional.ofNullable(ownerId))
                            .queryParamIfPresent("categorieId", Optional.ofNullable(categorieId))
                            .build())
                    .retrieve()
                    .bodyToFlux(RessourceResponse.class)
                    .collectList()
                    .block(); // BLOQUANT

            return ResponseEntity.ok(responses);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Mettre à jour une ressource
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a ressource",
            description = "Update a ressource")
    @ApiResponse(responseCode = "200", description = "Ressource update successfully")
    public ResponseEntity<RessourceResponse> updateRessource(
            @PathVariable UUID id,
            @RequestBody RessourceRequest request,
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) UUID catalogueId
    ) {
        try {
            RessourceResponse response = webClient.put()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/ressource/update/{id}")
                            .queryParamIfPresent("ownerId", ownerId == null ? null : Optional.ofNullable(ownerId))
                            .queryParamIfPresent("catalogueId", catalogueId == null ? null : Optional.ofNullable(catalogueId))
                            .build(id))
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(RessourceResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Supprimer une ressource
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Dalete ressource",
            description = "Delete a ressource")
    @ApiResponse(responseCode = "200", description = "Delete a ressource successfully")
    public ResponseEntity<String> deleteRessource(@PathVariable UUID id) {
        try {
            String response = webClient.delete()
                    .uri("/api/v1/ressource/delete/{id}", id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }
}
