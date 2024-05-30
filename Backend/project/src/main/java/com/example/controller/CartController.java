package com.example.controller;

import com.example.DTO.NotificationDTO;
import com.example.entity.Cart;
import com.example.entity.Notification;
import com.example.entity.OrderStatus;
import com.example.entity.Product;
import com.example.entity.Users;
import com.example.service.CartService;
import com.example.service.EmailService;
import com.example.service.NotificationService;
import com.example.service.ProductService;
import com.example.service.user_services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private user_services userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestParam int userId, @RequestParam Long productId, @RequestParam int quantity) {
        Users user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        if (user == null || product == null) {
            return ResponseEntity.badRequest().body("User or Product not found");
        }

        cartService.addProductToCart(user, product, quantity);

        Cart cart = cartService.getCartByUser(user); // Fetch updated cart
        emailService.sendProductAddedToCartNotification(user, product, cart);

        return ResponseEntity.ok("Product added to cart and notification sent successfully");
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestParam int userId, @RequestParam Long productId, @RequestParam int quantity) {
        Users user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        if (user == null || product == null) {
            return ResponseEntity.badRequest().body("User or Product not found");
        }

        cartService.updateProductInCart(user, product, quantity);
//        emailService.sendProductUpdatedInCartNotification(user, product, quantity);

        return ResponseEntity.ok("Product quantity updated successfully");
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<String> removeProduct(@RequestParam int userId, @RequestParam Long productId) {
        Users user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        cartService.removeProductFromCart(user, productId);

        return ResponseEntity.ok("Product removed from cart successfully");
    }
    
    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestParam int userId) {
        Users user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Cart cart = cartService.getCartByUser(user);
        if (cart == null || cart.getProducts().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty. Add products to cart before placing an order.");
        }

        try {
            cartService.placeOrder(user);
            Cart updatedCart = cartService.getCartByUser(user);
            emailService.sendOrderConfirmation(user, updatedCart);
            return ResponseEntity.ok("Order placed successfully");
        } catch (Exception e) {
            System.err.println("Failed to place order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to place order. Please try again later.");
        }
    }
    
    @GetMapping("/getCartItems")
    public ResponseEntity<List<Product>> getCartItems(@RequestParam int userId) {
        Users user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Cart cart = cartService.getCartByUser(user);
        if (cart == null) {
            return ResponseEntity.ok(new ArrayList<>()); // Return an empty list if cart is empty
        }

        return ResponseEntity.ok(cart.getProducts());
    }
    
    @GetMapping("/getCartDetails")
    public ResponseEntity<Cart> getCartDetails(@RequestParam int userId) {
        Users user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Cart cart = cartService.getCartByUser(user);
        if (cart == null) {
            return ResponseEntity.ok(new Cart()); // Return an empty cart if cart is empty
        }

        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/PendingOrder")
    public ResponseEntity<String> sendPendingOrderNotification(@RequestParam int userId) {
        Users user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Cart cart = cartService.getCartByUser(user);
        if (cart == null || cart.getProducts().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty. Cannot send pending order notification.");
        }
        if (cart.getOrderStatus() != OrderStatus.CART) {
            return ResponseEntity.badRequest().body("Cart order status is not 'CART'. Cannot send pending order notification.");
        }

        try {
            emailService.sendPendingOrderNotification(user, cart);
            return ResponseEntity.ok("Pending order notification sent successfully");
        } catch (Exception e) {
            System.err.println("Failed to send pending order notification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send pending order notification. Please try again later.");
        }
    }
    @PostMapping("/sendPendingOrderNotificationsToAll")
    public ResponseEntity<String> sendPendingOrderNotificationsToAll() {
        try {
            cartService.sendPendingOrderNotifications();
            return ResponseEntity.ok("Pending order notifications sent to all users successfully");
        } catch (Exception e) {
            System.err.println("Failed to send pending order notifications: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send pending order notifications. Please try again later.");
        }
    }
//    @GetMapping("/notifications/{userId}")
//    public ResponseEntity<List<Notification>> getNotifications(@PathVariable int userId) {
//        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
//        return ResponseEntity.ok(notifications);
//    }
    
    @GetMapping("/notifications/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable int userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }

}
