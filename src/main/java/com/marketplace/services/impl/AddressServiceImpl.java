package com.marketplace.services.impl;

import com.marketplace.dtos.request.AddressCreateRequestDTO;
import com.marketplace.dtos.response.AddressResponseDTO;
import com.marketplace.entities.Address;
import com.marketplace.exceptions.ResourceNotFoundException;
import com.marketplace.repositories.AddressRepository;
import com.marketplace.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional
    public AddressResponseDTO createAddress(UUID userId, AddressCreateRequestDTO request) {
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setUserId(userId);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setZipCode(request.getZipCode());
        address.setIsDefault(request.getIsDefault());
        address.setCreatedAt(LocalDateTime.now());

        // If this is the first address or set as default, make it default
        if (request.getIsDefault() || addressRepository.findByUserId(userId).isEmpty()) {
            setAsDefaultAddress(userId, address);
        }

        address = addressRepository.save(address);
        return convertToDTO(address);
    }

    @Override
    public List<AddressResponseDTO> getUserAddresses(UUID userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponseDTO getAddress(UUID addressId) {
        return convertToDTO(findAddressById(addressId));
    }

    @Override
    @Transactional
    public AddressResponseDTO updateAddress(UUID addressId, AddressCreateRequestDTO request) {
        Address address = findAddressById(addressId);

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setZipCode(request.getZipCode());

        if (request.getIsDefault()) {
            setAsDefaultAddress(address.getUserId(), address);
        }

        address = addressRepository.save(address);
        return convertToDTO(address);
    }

    @Override
    @Transactional
    public void deleteAddress(UUID addressId) {
        Address address = findAddressById(addressId);
        addressRepository.delete(address);
    }

    @Override
    @Transactional
    public AddressResponseDTO setDefaultAddress(UUID userId, UUID addressId) {
        Address address = findAddressById(addressId);
        setAsDefaultAddress(userId, address);
        address = addressRepository.save(address);
        return convertToDTO(address);
    }

    private Address findAddressById(UUID addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    private void setAsDefaultAddress(UUID userId, Address newDefaultAddress) {
        // Remove default from other addresses
        addressRepository.findByUserId(userId).stream()
                .filter(addr -> addr.getIsDefault() && !addr.getId().equals(newDefaultAddress.getId()))
                .forEach(addr -> {
                    addr.setIsDefault(false);
                    addressRepository.save(addr);
                });

        newDefaultAddress.setIsDefault(true);
    }

    private AddressResponseDTO convertToDTO(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setUserId(address.getUserId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setZipCode(address.getZipCode());
        dto.setIsDefault(address.getIsDefault());
        dto.setCreatedAt(address.getCreatedAt());
        return dto;
    }
}