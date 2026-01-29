# TechZone - E-Commerce Platform

## 📋 Description du projet

TechZone est une plateforme e-commerce complète spécialisée dans la vente de produits technologiques (smartphones, laptops, accessoires, wearables, smart home). Le projet implémente un système de gestion de produits, de catégories, de panier d'achat et de commandes avec authentification et axutorisation des utilisateurs.

### Objectifs
- Offrir une interface utilisateur intuitive pour la navigation et l'achat de produits technologiques
- Gérer efficacement le catalogue de produits avec catégorisation et promotions
- Implémenter un système de panier d'achat et de gestion des commandes
- Fournir un panel d'administration pour la gestion des produits, catégories et utilisateurs
- Assurer la sécurité des données avec authentification et autorisation basées sur les rôles

## 🛠️ Stack Technique

### Backend
- **Java 17** - Langage de programmation
- **Spring Boot 3.5.9** - Framework principal
- **Spring Security** - Authentification et autorisation
- **Spring Data JPA** - Accès aux données et ORM
- **Hibernate** - Implémentation JPA
- **Maven** - Gestion des dépendances et build

### Frontend
- **Thymeleaf** - Moteur de templates côté serveur
- **HTML5/CSS3** - Structure et style
- **JavaScript** - Interactivité côté client

### Base de données
- **H2 Database** - Base de données en mémoire (mode développement)
- **HikariCP** - Pool de connexions

### Documentation API
- **SpringDoc OpenAPI** - Documentation Swagger/OpenAPI

### Outils de développement
- **Spring Boot DevTools** - Rechargement à chaud
- **Lombok** (optionnel) - Réduction du code boilerplate

## 🏗️ Architecture du Projet

### Structure des packages

```
com.TechZone.TechZone/
├── config/                    # Configuration de l'application
│   ├── DataInitializer.java  # Initialisation des données de test
│   └── OpenApiConfig.java    # Configuration Swagger/OpenAPI
│
├── controller/               # Contrôleurs MVC et REST API
│   ├── api/                 # Endpoints REST API
│   └── mvc/                 # Contrôleurs MVC pour les vues
│       ├── HomeController.java
│       ├── AdminDashboardController.java
│       └── ...
│
├── dto/                      # Data Transfer Objects
│   ├── request/             # DTOs pour les requêtes
│   └── response/            # DTOs pour les réponses
│
├── entity/                   # Entités JPA
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── Order.java
│   ├── OrderLine.java
│   ├── Role.java (enum)
│   └── OrderStatus.java (enum)
│
├── exception/               # Gestion des exceptions
│   ├── GlobalExceptionHandler.java
│   └── IntegrityConstraintException.java
│
├── repository/              # Repositories Spring Data JPA
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── CategoryRepository.java
│   ├── OrderRepository.java
│   └── OrderLineRepository.java
│
├── security/                # Configuration de sécurité
│   ├── SecurityConfig.java
│   ├── WebConfig.java
│   └── CustomUserDetailsService.java
│
├── service/                 # Logique métier
│   ├── UserService.java
│   ├── ProductService.java
│   ├── CategoryService.java
│   ├── CartService.java
│   └── OrderService.java
│
└── TechZoneApplication.java # Classe principale Spring Boot
```

### Architecture en couches

1. **Couche Présentation** (Controller)
   - Contrôleurs MVC pour les vues Thymeleaf
   - Contrôleurs REST API pour les endpoints JSON

2. **Couche Service** (Business Logic)
   - Logique métier et règles de gestion
   - Orchestration des opérations
   - Validation des données

3. **Couche Repository** (Data Access)
   - Accès aux données via Spring Data JPA
   - Requêtes personnalisées

4. **Couche Entity** (Domain Model)
   - Modèles de données JPA
   - Relations entre entités

## 🚀 Instructions de Lancement

### Prérequis

- **Java 17** ou supérieur ([Télécharger ici](https://adoptium.net/))
- **Maven 3.6+** (inclus avec le wrapper Maven)
- **Port 8080** disponible

### Commandes de lancement

#### 1. Avec Maven Wrapper (recommandé)

**Sur macOS/Linux :**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

**Sur Windows :**
```cmd
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

#### 2. Avec Maven installé
```bash
mvn clean install
mvn spring-boot:run
```

#### 3. Avec l'IDE (IntelliJ IDEA, Eclipse, VS Code)
- Ouvrir le projet en tant que projet Maven
- Lancer la classe `TechZoneApplication.java`

### URLs principales

| Service | URL | Description |
|---------|-----|-------------|
| **Application** | http://localhost:8080 | Page d'accueil |
| **Console H2** | http://localhost:8080/h2-console | Interface base de données |
| **API Docs** | http://localhost:8080/swagger-ui.html | Documentation Swagger |
| **Panel Admin** | http://localhost:8080/admin/dashboard | Tableau de bord admin |

### Configuration H2 Console

Pour accéder à la console H2 :
- **JDBC URL** : `jdbc:h2:mem:maevdb`
- **Username** : `sa`
- **Password** : *(laisser vide)*

## 👥 Comptes de Test

### Comptes Administrateurs (ADMIN)

| Nom d'utilisateur | Email | Mot de passe |
|-------------------|-------|--------------|
| AdminJohn | admin1@techzone.com | Admin123! |
| AdminSarah | admin2@techzone.com | Admin123! |

**Permissions ADMIN :**
- Accès au dashboard administrateur
- Gestion des produits (CRUD)
- Gestion des catégories (CRUD)
- Gestion des utilisateurs (CRUD)
- Consultation des commandes

### Comptes Utilisateurs (USER)

| Nom d'utilisateur | Email | Mot de passe |
|-------------------|-------|--------------|
| JohnDoe | john.doe@example.com | User123! |
| EmilySmith | emily.smith@example.com | User123! |
| AlexTechLover | alex.tech@example.com | User123! |

**Permissions USER :**
- Navigation et consultation des produits
- Ajout de produits au panier
- Passage de commandes
- Consultation de son profil et historique de commandes

## 🤖 Utilisation de l'Intelligence Artificielle

### Outils IA Employés

#### GitHub Copilot
- **Utilisation** : Assistant de code IA pour l'autocomplétion et la génération de code
- **Contexte** : Utilisé pour :
  - Génération de méthodes répétitives (getters/setters, constructeurs)
  - Suggestion de requêtes JPA
  - Création de templates Thymeleaf
  - Écriture de commentaires et documentation
  - Patterns de code Spring Boot

#### ChatGPT / Claude
- **Utilisation** : Assistance pour la résolution de problèmes et la conception
- **Contexte** : Utilisé pour :
  - Résolution de problèmes de configuration Spring Security
  - Compréhension des concepts JPA et Hibernate
  - Optimisation des requêtes de base de données
  - Suggestions d'architecture et de bonnes pratiques
  - Débogage d'erreurs complexes

### Impact de l'IA sur le développement

L'utilisation d'outils IA a permis de :
- ✅ Accélérer le développement des fonctionnalités répétitives
- ✅ Améliorer la qualité du code avec des suggestions de bonnes pratiques
- ✅ Réduire le temps de débogage
- ✅ Explorer rapidement différentes approches architecturales
- ✅ Générer une documentation claire et structurée

**Note** : Tout le code généré par IA a été relu, testé et adapté aux besoins spécifiques du projet.

## 📦 Données de Test

Le projet inclut un `DataInitializer` qui charge automatiquement au démarrage :
- **5 catégories** : Smartphones, Laptops, Smart Home, Wearables, Accessories
- **15 produits** technologiques avec descriptions, prix, stock et promotions
- **5 utilisateurs** (2 admins + 3 utilisateurs standards)

Les données sont rechargées à chaque démarrage de l'application (base de données en mémoire).

## 📝 Fonctionnalités Principales

### Pour tous les utilisateurs
- 🏠 Page d'accueil avec produits en promotion
- 🔍 Navigation par catégories
- 📱 Détails des produits
- 🔐 Inscription et connexion

### Pour les utilisateurs connectés (USER)
- 🛒 Gestion du panier d'achat
- 💳 Passage de commandes
- 📦 Suivi des commandes
- 👤 Gestion du profil

### Pour les administrateurs (ADMIN)
- 📊 Dashboard administratif
- ➕ Création/Modification/Suppression de produits
- 🏷️ Gestion des catégories
- 👥 Gestion des utilisateurs
- 📋 Consultation des commandes

## 🔒 Sécurité

- Authentification basée sur Spring Security
- Mots de passe encodés avec BCrypt
- Autorisation basée sur les rôles (ROLE_USER, ROLE_ADMIN)
- Protection CSRF
- Sessions sécurisées

## 📄 License

Ce projet est développé dans un cadre éducatif - **Coda**.

---

**Développé avec ☕ Java et 💚 Spring Boot**
