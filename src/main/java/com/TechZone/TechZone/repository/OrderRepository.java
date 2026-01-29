package com.TechZone.TechZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Order;
import com.TechZone.TechZone.entity.User;
import com.TechZone.TechZone.entity.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndStatus(User user, OrderStatus status);
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
}
