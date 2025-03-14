package com.marketplace.controllers;

import com.marketplace.models.ProductPostRequest;
import com.marketplace.models.ProductPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/product_post-client")
@Tag(name = "ProductPost", description = "ProductPost management APIs")
public class ProductPostController {

    private final WebClient webClient;

    // Créer une ressource
    @PostMapping("/create")
    @Operation(summary = "Create product_post",
            description = "Create a new product_post")
    @ApiResponse(responseCode = "200", description = "New product_post successfully")
    public ResponseEntity<ProductPostResponse> createCategory(
            @RequestBody ProductPostRequest request
    ) {
        try {
            ProductPostResponse response = webClient.post()
                    .uri("/api/v1/product_post/create")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ProductPostResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Récupérer une ressource par ID
    @GetMapping("/{id}")
    @Operation(summary = "Get product_post",
            description = "Get a product_post")
    @ApiResponse(responseCode = "200", description = "Get product_post successfully")
    public ResponseEntity<ProductPostResponse> getCategoryById(@PathVariable UUID id) {
        try {
            ProductPostResponse response = webClient.get()
                    .uri("/api/v1/product_post/{id}", id)
                    .retrieve()
                    .bodyToMono(ProductPostResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Récupérer toutes les ressources
    @GetMapping
    @Operation(summary = "Get all product_post",
            description = "Get all ressproduct_postources")
    @ApiResponse(responseCode = "200", description = "Get Ressources successfully")
    public ResponseEntity<List<ProductPostResponse>> getAllCategories(
            @RequestParam(required = false) UUID marchandId,
            @RequestParam(required = false) UUID variationId,
            @RequestParam(required = false) UUID categorieId
    ) {
        try {
            List<ProductPostResponse> responses = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/product_post")
                            .queryParamIfPresent("marchandId", Optional.ofNullable(marchandId))
                            .queryParamIfPresent("variationId", Optional.ofNullable(variationId))
                            .queryParamIfPresent("categorieId", Optional.ofNullable(categorieId))
                            .build())
                    .retrieve()
                    .bodyToFlux(ProductPostResponse.class)
                    .collectList()
                    .block(); // BLOQUANT

            return ResponseEntity.ok(responses);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Mettre à jour une ressource
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a product_post",
            description = "Update a product_post")
    @ApiResponse(responseCode = "200", description = "product_post update successfully")
    public ResponseEntity<ProductPostResponse> updateCategory(
            @PathVariable UUID id,
            @RequestBody ProductPostRequest request
    ) {
        try {
            ProductPostResponse response = webClient.put()
                    .uri("/api/v1/product_post/update/{id}", id)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ProductPostResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    // Supprimer une ressource
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Dalete product_post",
            description = "Delete a product_post")
    @ApiResponse(responseCode = "200", description = "Delete a product_post successfully")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        try {
            String response = webClient.delete()
                    .uri("/api/v1/product_post/delete/{id}", id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }
}
