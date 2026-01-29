package com.TechZone.TechZone.config;

import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           ProductRepository productRepository,
                           CategoryRepository categoryRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("⏳ LOADING DATA...");

        Category info = new Category();
        info.setName("Technology");
        Category gaming = new Category();
        gaming.setName("Gaming");
        Category smart = new Category();
        smart.setName("Smart Home");

        info = categoryRepository.save(info);
        gaming = categoryRepository.save(gaming);
        smart = categoryRepository.save(smart);

        Random random = new Random();
        
        for (int i = 1; i <= 50; i++) {
            Product p = new Product();
            p.setName("TechZone Product #" + i);

            if (i % 3 == 0) {
                p.setImagePath("https://via.placeholder.com/300");
                p.setCategory(info);
            } else if (i % 3 == 1) {
                p.setImagePath("https://via.placeholder.com/300");
                p.setCategory(gaming);
            } else {
                p.setImagePath("https://via.placeholder.com/300");
                p.setCategory(smart);
            }
            
            double price = 10 + (1000 - 10) * random.nextDouble();
            p.setPrice(Math.round(price * 100.0) / 100.0);

            if (i % 5 == 0) {
                p.setStock(0); 
            } else {
                p.setStock(1 + random.nextInt(100)); 
            }

            p.setStatus(true); 

            p.setPromotion(i % 5 == 0);
            
            p.setShortDescription("Short description for product #" + i);
            p.setLongDescription("This is a much longer description to test the display of product details #" + i + ". Lorem ipsum dolor sit amet.");

            if (i % 3 == 0) {
                p.setCategory(info);
            } else if (i % 3 == 1) {
                p.setCategory(gaming);
            } else {
                p.setCategory(smart);
            }

            productRepository.save(p);
        }
        System.out.println("✅ 50 Products generated!");

        User user = new User();
        user.setUsername("Alice Martin");
        user.setEmail("alice@test.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.USER);

        Order order1 = new Order();
        order1.setStatus(OrderStatus.PENDING);
        order1.setUser(user);
        
        Product starProduct = new Product();
        starProduct.setName("iPhone 15 Pro Max");
        starProduct.setPrice(1450.00);
        starProduct.setStock(10);
        starProduct.setCategory(info);
        productRepository.save(starProduct);

        OrderLine line1 = new OrderLine();
        line1.setQuantity(1);
        line1.setProduct(starProduct);
        line1.setUnitPrice(starProduct.getPrice());
        
        order1.setOrderLines(new ArrayList<>());
        order1.addOrderLine(line1);

        user.setOrders(new ArrayList<>());
        user.getOrders().add(order1);

        userRepository.save(user);

        User admin = new User();
        admin.setUsername("Admin");
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        System.out.println("🚀 DATA LOADED SUCCESSFULLY!");
    }
}