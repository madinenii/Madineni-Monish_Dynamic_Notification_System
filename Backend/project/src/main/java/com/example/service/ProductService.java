package com.example.service;


import com.example.entity.NotificationType;
import com.example.entity.Product;
import com.example.entity.Users;
import com.example.repo.ProductRepository;
import com.example.repo.user_repo;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private user_repo userRepository;
    
    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationService notificationService;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    

    @Transactional
    public Product updateProduct(Long productId, int quantityAvailable, double price) {
        Product product = getProductById(productId);
        if (product != null) {
            boolean quantityUpdated = false;
            boolean priceUpdated = false;

            if (product.getQuantityAvailable() != quantityAvailable) {
                product.setQuantityAvailable(quantityAvailable);
                quantityUpdated = true;
            }

            if (Double.compare(product.getPrice(), price) != 0) {
                product.setPrice(price);
                priceUpdated = true;
            }

            if (quantityUpdated || priceUpdated) {
                productRepository.save(product);
                if (quantityUpdated) {
                    notifyUsers(product, "Quantity updated to: " + quantityAvailable, "Quantity updated to: " + quantityAvailable);
                    
                }
                if (priceUpdated) {
                    notifyUsers(product, "Price updated to: " + price, "Price updated to: " + price);
                }
            }
        }
        return product;
    }

    private void notifyUsers(Product product, String emailUpdateDetails, String dbUpdateDetails) {
        List<Users> users = getAllUsers();
        for (Users user : users) {
            String emailMessage = buildEmailMessage(user, product, emailUpdateDetails);
            emailService.sendProductPriceUpdateEmail(user.getEmail(), product, emailMessage);
            notificationService.sendNotification(user, dbUpdateDetails, NotificationType.EMAIL);
        }
    }

    private String buildEmailMessage(Users user, Product product, String updateDetails) {
        return String.format(
            "Hello %s,\n\n" +
            "We have an exciting update regarding one of our products!\n\n" +
            "Product Name: %s\n" +
            "%s\n\n" +
            "If you are interested in purchasing this product, please visit our website.\n\n" +
            "Thank you for being a valued customer!\n\n" +
            "Best regards,\n" +
            "The Team",
            user.getFirstName(),
            product.getName(),
            updateDetails
        );
    }

    // Replace with actual logic to get users
    private List<Users> getAllUsers() {
        // Implement logic to fetch users from database or repository
        return userRepository.findAll(); // Example assuming you have a UserRepository
    }
    
    @Transactional
    public boolean deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }
}
