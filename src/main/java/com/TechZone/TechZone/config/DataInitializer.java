package com.TechZone.TechZone.config;

import com.TechZone.TechZone.entity.*;
import com.TechZone.TechZone.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;

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
        
        userRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category smartphones = new Category();
        smartphones.setName("Smartphones");
        
        Category laptops = new Category();
        laptops.setName("Laptops");
        
        Category smartHome = new Category();
        smartHome.setName("Smart Home");
        
        Category wearables = new Category();
        wearables.setName("Wearables");
        
        Category accessories = new Category();
        accessories.setName("Accessories");

        smartphones = categoryRepository.save(smartphones);
        laptops = categoryRepository.save(laptops);
        smartHome = categoryRepository.save(smartHome);
        wearables = categoryRepository.save(wearables);
        accessories = categoryRepository.save(accessories);

        createProduct("iPhone 15 Pro Max", "Smartphone Apple flagship avec puce A17 Pro", 
            "Découvrez l'apogée de l'innovation avec l'iPhone 15 Pro Max. Doté d'un magnifique écran Super Retina XDR de 6,7 pouces, d'un système de caméra avancé avec capteur principal de 48MP et d'une conception en titane. Alimenté par la puce A17 Pro pour des performances exceptionnelles.",
            1199.99, 25, smartphones, "https://picsum.photos/id/10/500/500", false);

        createProduct("Samsung Galaxy S24 Ultra", "Flagship Android premium avec S Pen", 
            "Le Samsung Galaxy S24 Ultra offre une technologie de pointe avec un écran Vision Booster dynamique, des fonctionnalités Galaxy AI avancées et un système de caméra polyvalent avec capteur principal de 200MP. Conçu pour les utilisateurs exigeants qui demandent l'excellence.",
            1299.99, 0, smartphones, "https://picsum.photos/id/11/500/500", false);  // Rupture de stock

        createProduct("Google Pixel 8 Pro", "Smartphone alimenté par IA avec puce Tensor", 
            "Découvrez l'Android pur avec Google Pixel 8 Pro. Doté d'une caméra principale innovante de 50MP avec photographie informatique avancée, assistance Gemini IA intégrée et expérience Android pure avec mises à jour garanties.",
            999.99, 30, smartphones, "https://picsum.photos/id/12/500/500", true);  // EN PROMOTION

        createProduct("MacBook Pro 16 pouces M3 Max", "Ordinateur portable puissant pour professionnels", 
            "Le MacBook Pro 16 pouces avec M3 Max offre des performances exceptionnelles pour les travaux créatifs exigeants. Doté d'écran Liquid Retina XDR, d'une efficacité thermique exceptionnelle, d'une autonomie toute la journée et jusqu'à 96GB de mémoire unifiée.",
            3499.00, 10, laptops, "https://picsum.photos/id/13/500/500", false);

        createProduct("Dell XPS 15", "Ordinateur portable Windows premium avec écran OLED", 
            "Expérience informatique premium avec Dell XPS 15. Doté d'un magnifique écran OLED 4K InfinityEdge, du dernier processeur Intel Core, des graphiques NVIDIA RTX et d'une conception unibody en aluminium. Parfait pour les créateurs et les professionnels.",
            2299.00, 0, laptops, "https://picsum.photos/id/14/500/500", true);  // Rupture + PROMOTION

        createProduct("Lenovo ThinkPad X1 Carbon", "Ultrabook professionnel avec fiabilité légendaire", 
            "Le Lenovo ThinkPad X1 Carbon Gen 12 combine portabilité et puissance. Doté du processeur Intel Core Ultra, d'un écran OLED 16 pouces, d'une autonomie exceptionnelle et de fonctionnalités de sécurité robustes pour les professionnels.",
            1899.00, 18, laptops, "https://picsum.photos/id/15/500/500", false);

        createProduct("Apple Watch Ultra", "Montre intelligente sportive premium", 
            "L'Apple Watch Ultra est conçue pour l'aventure avec un boîtier en titane robuste, un écran Retina toujours allumé, un suivi fitness avancé et des fonctionnalités d'urgence incluant la connectivité satellite. Étanche jusqu'à 100 mètres.",
            799.00, 5, wearables, "https://picsum.photos/id/16/500/500", true);  // EN PROMOTION

        createProduct("Garmin Epix", "Montre intelligente GPS multisport avancée", 
            "Garmin Epix combine la technologie de pointe avec l'expertise sportive. Doté d'écran AMOLED, d'applications sportives complètes, de métriques de santé avancées et de support multi-GNSS. Parfait pour les athlètes et les passionnés de plein air.",
            699.99, 0, wearables, "https://picsum.photos/id/17/500/500", false);  // Rupture de stock

        createProduct("Amazon Echo Show 15", "Grand écran intelligent pour la maison", 
            "Transformez votre maison avec Echo Show 15. Doté d'un écran Full HD 15,6 pouces, commande vocale Alexa, intégration maison intelligente et affichage de contenu adaptatif. Parfait pour la cuisine, le salon ou le bureau.",
            349.99, 22, smartHome, "https://picsum.photos/id/18/500/500", false);

        createProduct("Google Nest Hub Max", "Écran intelligent premium avec appels vidéo", 
            "Google Nest Hub Max offre divertissement immersif et vie connectée. Doté d'écran 10 pouces, appels vidéo Duo, reconnaissance de gestes et intégration transparente Google Home pour un contrôle maison intelligente complet.",
            299.99, 8, smartHome, "https://picsum.photos/id/19/500/500", true);  // EN PROMOTION

        createProduct("Kit de démarrage Philips Hue", "Système d'éclairage LED intelligent premium", 
            "Créez l'ambiance parfaite avec Philips Hue. Contrôlez plus de 16 millions de couleurs, programmez l'éclairage, compatibilité commande vocale et automatisation avancée. Inclus : pont et 3 ampoules pour éclairage intelligent complet.",
            199.99, 45, smartHome, "https://picsum.photos/id/20/500/500", false);

        createProduct("Sony WH-1000XM5", "Casque audio premium avec réduction de bruit", 
            "Expérience audio ultime avec Sony WH-1000XM5. Doté d'une réduction de bruit de premier plan, 30 heures d'autonomie, connexion multi-point et qualité sonore premium avec support audio Hi-Res.",
            399.99, 2, accessories, "https://picsum.photos/id/21/500/500", true);  // EN PROMOTION - Stock faible

        createProduct("Apple AirPods Pro 2", "Écouteurs sans fil avec réduction de bruit active", 
            "Apple AirPods Pro 2 offrent un audio premium en forme compacte. Doté d'audio adaptatif, mode transparence, audio sans perte avec Apple Vision Pro, longue autonomie et intégration transparente des appareils Apple.",
            249.00, 50, accessories, "https://picsum.photos/id/22/500/500", false);

        createProduct("Samsung Galaxy Buds2 Pro", "Écouteurs sans fil véritables premium", 
            "Samsung Galaxy Buds2 Pro offrent une qualité audio supérieure avec réduction de bruit active, contrôle du bruit ambiant, résistance IPX7 et autonomie de 8 heures par charge avec étui de charge sans fil.",
            229.99, 0, accessories, "https://picsum.photos/id/23/500/500", true);  // Rupture + PROMOTION

        createProduct("iPad Pro 12,9 pouces", "Tablette puissante avec puce M2", 
            "iPad Pro 12,9 pouces avec puce M2 offre des performances au niveau du bureau. Doté d'écran Liquid Retina XDR magnifique, support Apple Pencil, excellent pour les créatifs et la productivité professionnelle.",
            1099.00, 12, accessories, "https://picsum.photos/id/24/500/500", false);

        createProduct("Samsung Galaxy Tab S9 Ultra", "Tablette Android premium", 
            "Expérience informatique premium avec Galaxy Tab S9 Ultra. Doté d'un énorme écran AMOLED 14,6 pouces, processeur Snapdragon, S Pen inclus, haut-parleurs exceptionnels et autonomie toute la journée.",
            1099.99, 3, accessories, "https://picsum.photos/id/25/500/500", true);  // EN PROMOTION

        System.out.println("✅ 15 Premium Products created!");

        User admin1 = new User();
        admin1.setUsername("AdminJohn");
        admin1.setEmail("admin1@techzone.com");
        admin1.setPassword(passwordEncoder.encode("Admin123!"));
        admin1.setRole(Role.ADMIN);
        userRepository.save(admin1);

        User admin2 = new User();
        admin2.setUsername("AdminSarah");
        admin2.setEmail("admin2@techzone.com");
        admin2.setPassword(passwordEncoder.encode("Admin123!"));
        admin2.setRole(Role.ADMIN);
        userRepository.save(admin2);

        User user1 = new User();
        user1.setUsername("JohnDoe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword(passwordEncoder.encode("User123!"));
        user1.setRole(Role.USER);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("EmilySmith");
        user2.setEmail("emily.smith@example.com");
        user2.setPassword(passwordEncoder.encode("User123!"));
        user2.setRole(Role.USER);
        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("AlexTechLover");
        user3.setEmail("alex.tech@example.com");
        user3.setPassword(passwordEncoder.encode("User123!"));
        user3.setRole(Role.USER);
        userRepository.save(user3);

        System.out.println("✅ Users created: 2 ADMIN + 3 USER");
        System.out.println("🚀 DATA LOADED SUCCESSFULLY!");
    }

    private void createProduct(String name, String shortDescription, String longDescription, 
                               double price, int stock, Category category, String imagePath, boolean isPromotion) {
        Product product = new Product();
        product.setName(name);
        product.setShortDescription(shortDescription);
        product.setLongDescription(longDescription);
        product.setPrice(price);
        product.setStock(stock);
        product.setStatus(stock > 0);  // True si en stock, false si rupture
        product.setCategory(category);
        product.setImagePath(imagePath);
        product.setPromotion(isPromotion);
        productRepository.save(product);
    }
}