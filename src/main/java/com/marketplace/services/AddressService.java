package com.marketplace.services;

import com.marketplace.dtos.request.AddressCreateRequestDTO;
import com.marketplace.dtos.response.AddressResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {
    AddressResponseDTO createAddress(UUID userId, AddressCreateRequestDTO request);
    List<AddressResponseDTO> getUserAddresses(UUID userId);
    AddressResponseDTO getAddress(UUID addressId);
    AddressResponseDTO updateAddress(UUID addressId, AddressCreateRequestDTO request);
    void deleteAddress(UUID addressId);
    AddressResponseDTO setDefaultAddress(UUID userId, UUID addressId);
}