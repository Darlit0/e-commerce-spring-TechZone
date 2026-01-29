package com.TechZone.TechZone.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TechZone.TechZone.entity.Order;
import com.TechZone.TechZone.entity.OrderLine;
import com.TechZone.TechZone.entity.Product;
import java.util.List;
import java.util.Optional;


public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findAllByOrder(Order order);
    Optional<OrderLine> findByOrderAndProduct(Order order, Product product);
}
