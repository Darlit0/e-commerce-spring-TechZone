package com.TechZone.TechZone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Ligne_Commandes")
public class OrderLine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite", nullable = false)
    private int quantity;

    @Column(name = "prix_unitaire", nullable = false)
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Product product;

    

    public OrderLine() {
    }

    public OrderLine(int quantity, double unitPrice, Order order, Product product) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.order = order;
        this.product = product;
    }

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
        if (product != null){
            this.unitPrice = product.getPrice();
        }
    }
    
}
