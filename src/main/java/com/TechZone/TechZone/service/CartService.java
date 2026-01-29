package com.TechZone.TechZone.service;

import com.TechZone.TechZone.dto.request.AddToCartDto;
import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CartService {

    private final OrderRepository commandeRepository;
    private final OrderLineRepository ligneCommandeRepository;
    private final ProductRepository produitRepository;
    private final UserRepository utilisateurRepository;

    public CartService(OrderRepository commandeRepository, 
                         OrderLineRepository ligneCommandeRepository, 
                         ProductRepository produitRepository, 
                         UserRepository utilisateurRepository) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Transactional
    public void addItem(AddToCartDto dto) {
        
        User user = utilisateurRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        Product product = produitRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        
        if (product.getStock() < dto.getQuantity()) {
            throw new RuntimeException("Insufficient stock for this product.");
        }

        
        Order cart = commandeRepository.findByUserAndStatus(user, OrderStatus.PENDING)
                .orElseGet(() -> {
                    Order c = new Order();
                    c.setUser(user);
                    c.setStatus(OrderStatus.PENDING);
                    c.setOrderDate(LocalDate.now());
                    return commandeRepository.save(c);
                });

        
        Optional<OrderLine> existingLine = ligneCommandeRepository.findByOrderAndProduct(cart, product);

        if (existingLine.isPresent()) {
            
            OrderLine line = existingLine.get();
            int newQuantity = line.getQuantity() + dto.getQuantity();
            
            
            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Insufficient stock to add this quantity.");
            }
            
            line.setQuantity(newQuantity);
            ligneCommandeRepository.save(line);
        } else {
            
            OrderLine line = new OrderLine();
            line.setOrder(cart);
            line.setProduct(product);
            line.setQuantity(dto.getQuantity());
            line.setUnitPrice(product.getPrice()); 
            ligneCommandeRepository.save(line);
        }
    }

    @Transactional
    public void removeLine(Long lineId, User user) {
        OrderLine line = ligneCommandeRepository.findById(lineId)
                .orElseThrow(() -> new RuntimeException("Line not found"));

        
        if (!line.getOrder().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized action: This is not your cart.");
        }

        ligneCommandeRepository.delete(line);
    }

    @Transactional
    public void validateCart(User user) {
        
        Order cart = commandeRepository.findByUserAndStatus(user, OrderStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("No cart to validate"));

        
        var lines = ligneCommandeRepository.findAllByOrder(cart);
        if (lines.isEmpty()) {
            throw new RuntimeException("Cannot validate an empty cart.");
        }

        
        for (OrderLine line : lines) {
            Product p = line.getProduct();
            
            if (p.getStock() < line.getQuantity()) {
                throw new RuntimeException("Out of stock for product: " + p.getName());
            }
            
            
            p.setStock(p.getStock() - line.getQuantity());
            produitRepository.save(p);
        }

        
        cart.setStatus(OrderStatus.PROCESSING); 
        cart.setOrderDate(LocalDate.now()); 

        commandeRepository.save(cart);
    }

    public Order getCartEntity(User user) {
        return commandeRepository.findByUserAndStatus(user, OrderStatus.PENDING)
                .orElse(null);
    }
}