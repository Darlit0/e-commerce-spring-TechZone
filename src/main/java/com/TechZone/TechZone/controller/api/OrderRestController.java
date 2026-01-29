package com.TechZone.TechZone.controller.api;

import com.TechZone.TechZone.dto.response.OrderDetailResponse;
import com.TechZone.TechZone.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/orders") 
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDetailResponse>> getUserHistory(@PathVariable Long id) {
        List<OrderDetailResponse> history = orderService.getUserOrders(id);
        
        
        return ResponseEntity.ok(history);
    }
}