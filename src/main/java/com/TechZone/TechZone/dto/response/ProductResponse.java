package com.TechZone.TechZone.dto.response;

public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private Boolean status;
    private String categoryName;
    private String shortDescription;
    private String longDescription;
    private String imagePath;

    
    public ProductResponse() {
    }

    
    public ProductResponse(Long id, String name, Double price, Integer stock, Boolean status, String categoryName, String shortDescription, String longDescription, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.categoryName = categoryName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imagePath = imagePath;
    }

    
    public Long getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Integer getStock() { return stock; }
    public Boolean isStatus() { return status; } 
    public String getCategoryName() { return categoryName; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public String getImagePath() { return imagePath; }

    
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = price; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setStatus(Boolean status) { this.status = status; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}