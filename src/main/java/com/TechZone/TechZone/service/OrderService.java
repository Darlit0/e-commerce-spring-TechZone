package com.TechZone.TechZone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TechZone.TechZone.dto.response.OrderDetailResponse;
import com.TechZone.TechZone.dto.response.OrderDetailResponse.LigneCommandeResponse;
import com.TechZone.TechZone.entity.Order;
import com.TechZone.TechZone.entity.OrderLine;
import com.TechZone.TechZone.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDetailResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);
        
        return orders.stream()
                .map(this::mapToDetailResponse)
                .collect(Collectors.toList());
    }

    private OrderDetailResponse mapToDetailResponse(Order order) {
        List<LigneCommandeResponse> lines = order.getOrderLines().stream()
                .map(this::mapOrderLine)
                .collect(Collectors.toList());

        return new OrderDetailResponse(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotal(),
                lines
        );
    }

    private LigneCommandeResponse mapOrderLine(OrderLine line) {
        return new LigneCommandeResponse(
                line.getProduct().getId(),
                line.getProduct().getName(),
                line.getQuantity(),
                line.getUnitPrice(),
                line.getQuantity() * line.getUnitPrice()
        );
    }
}
