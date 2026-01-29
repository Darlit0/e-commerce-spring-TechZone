package com.TechZone.TechZone.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "commandes")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private User user;

    @Column(name = "date_commande", nullable = false)
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<OrderLine> orderLines;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now().toLocalDate();

    }

    public Order() {
    }
    public Order(User user, LocalDate orderDate) {
        this.user = user;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public java.util.List<OrderLine> getOrderLines() {
        return orderLines;
    }
    public void setOrderLines(java.util.List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }

   
    @Transient
    public Double getTotal() {
        if (orderLines == null || orderLines.isEmpty()) {
            return 0.0;
        }
        return orderLines.stream()
                .mapToDouble(line -> line.getQuantity() * line.getUnitPrice())
                .sum();
    }

}
