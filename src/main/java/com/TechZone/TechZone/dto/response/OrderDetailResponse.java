package com.TechZone.TechZone.dto.response;

import com.TechZone.TechZone.entity.OrderStatus;
import java.time.LocalDate;
import java.util.List;

public class OrderDetailResponse {
    private Long id;
    private LocalDate dateCommande;
    private OrderStatus status;
    private Double totalCommande;
    private List<LigneCommandeResponse> lignes;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long id, LocalDate dateCommande, OrderStatus status, Double totalCommande, List<LigneCommandeResponse> lignes) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.status = status;
        this.totalCommande = totalCommande;
        this.lignes = lignes;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(Double totalCommande) {
        this.totalCommande = totalCommande;
    }

    public List<LigneCommandeResponse> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeResponse> lignes) {
        this.lignes = lignes;
    }

    public static class LigneCommandeResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private Double unitPrice;
        private Double sousTotal;

        public LigneCommandeResponse() {
        }

        public LigneCommandeResponse(Long productId, String productName, Integer quantity, Double unitPrice, Double sousTotal) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.sousTotal = sousTotal;
        }

        
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Double getSousTotal() {
            return sousTotal;
        }

        public void setSousTotal(Double sousTotal) {
            this.sousTotal = sousTotal;
        }
    }
}
