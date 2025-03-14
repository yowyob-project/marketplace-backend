package com.marketplace.services.impl;

import com.marketplace.entities.Order;
import com.marketplace.entities.Organisation;
import com.marketplace.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentHistoryService {


    private final OrderRepository orderRepository;

    public PaymentHistoryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<UUID> getUsersWhoPaidByOrganisation(UUID organisationId) {
        List<Order> paidOrders = orderRepository.findByOrganisationIdAndStatus(organisationId, "PAID");
        return paidOrders.stream()
                .map(Order::getUserId)
                .distinct()
                .collect(Collectors.toList());
    }
}
