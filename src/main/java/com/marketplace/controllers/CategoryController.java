package com.marketplace.controllers;

import com.marketplace.models.CategorieRequest;
import com.marketplace.models.CategorieResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/category-client")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    private final WebClient webClient;

    // Créer une ressource
    @Operation(summary = "Create a category",
            description = "Create a category for a product to ba added")
    @PostMapping("/create")
    public ResponseEntity<CategorieResponse> createCategory(
            @RequestBody CategorieRequest request,
            @RequestParam UUID ownerId,
            @RequestParam UUID categorieId
    ) {
        try {
            CategorieResponse response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/categorie/create")
                            .queryParamIfPresent("ownerId", Optional.ofNullable(ownerId))
                            .queryParamIfPresent("categorieId", Optional.ofNullable(categorieId))
                            .build())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CategorieResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Récupérer une ressource par ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a category by id",
            description = "Get a category by id")
    public ResponseEntity<CategorieResponse> getCategoryById(@PathVariable UUID id) {
        try {
            CategorieResponse response = webClient.get()
                    .uri("/api/v1/categorie/{id}", id)
                    .retrieve()
                    .bodyToMono(CategorieResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Récupérer toutes les ressources
    @GetMapping
    @Operation(summary = "Get all categories",
            description = "Get all categories")
    public ResponseEntity<List<CategorieResponse>> getAllCategories(
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) UUID categorieId
    ) {
        try {
            List<CategorieResponse> responses = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/categorie")
                            .queryParamIfPresent("ownerId", Optional.ofNullable(ownerId))
                            .queryParamIfPresent("categorieId", Optional.ofNullable(categorieId))
                            .build())
                    .retrieve()
                    .bodyToFlux(CategorieResponse.class)
                    .collectList()
                    .block(); // BLOQUANT

            return ResponseEntity.ok(responses);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Mettre à jour une ressource
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a category by its id",
            description = "Get a category by its id")
    public ResponseEntity<CategorieResponse> updateCategory(
            @PathVariable UUID id,
            @RequestBody CategorieRequest request,
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) UUID catalogueId
    ) {
        try {
            CategorieResponse response = webClient.put()
                    .uri("/api/v1/categorie/update/{id}", id)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CategorieResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Supprimer une ressource
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete categories",
            description = "Delete categories")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        try {
            String response = webClient.delete()
                    .uri("/api/v1/categorie/delete/{id}", id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }
}
