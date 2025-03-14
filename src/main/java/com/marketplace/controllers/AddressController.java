package com.marketplace.controllers;

import com.marketplace.dtos.request.AddressCreateRequestDTO;
import com.marketplace.dtos.response.AddressResponseDTO;
import com.marketplace.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/addresses")
@Tag(name = "Address", description = "Address management APIs")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    @Operation(summary = "Create a new address",
            description = "Creates a new address for the user")
    @ApiResponse(responseCode = "200", description = "Address created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<AddressResponseDTO> createAddress(
            @RequestHeader("User-Id") UUID userId,
            @Valid @RequestBody AddressCreateRequestDTO request) {
        return ResponseEntity.ok(addressService.createAddress(userId, request));
    }

    @GetMapping
    @Operation(summary = "Get user's addresses",
            description = "Retrieves all addresses for the user")
    @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully")
    public ResponseEntity<List<AddressResponseDTO>> getUserAddresses(
            @RequestHeader("User-Id") UUID userId) {
        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    @GetMapping("/{addressId}")
    @Operation(summary = "Get address by ID",
            description = "Retrieves a specific address by its ID")
    @ApiResponse(responseCode = "200", description = "Address retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Address not found")
    public ResponseEntity<AddressResponseDTO> getAddress(
            @PathVariable UUID addressId) {
        return ResponseEntity.ok(addressService.getAddress(addressId));
    }

    @PutMapping("/{addressId}")
    @Operation(summary = "Update address",
            description = "Updates an existing address")
    @ApiResponse(responseCode = "200", description = "Address updated successfully")
    @ApiResponse(responseCode = "404", description = "Address not found")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressCreateRequestDTO request) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "Delete address",
            description = "Deletes an address")
    @ApiResponse(responseCode = "200", description = "Address deleted successfully")
    @ApiResponse(responseCode = "404", description = "Address not found")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{addressId}/set-default")
    @Operation(summary = "Set address as default",
            description = "Sets an address as the default address for the user")
    @ApiResponse(responseCode = "200", description = "Address set as default successfully")
    @ApiResponse(responseCode = "404", description = "Address not found")
    public ResponseEntity<AddressResponseDTO> setDefaultAddress(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable UUID addressId) {
        return ResponseEntity.ok(addressService.setDefaultAddress(userId, addressId));
    }
}