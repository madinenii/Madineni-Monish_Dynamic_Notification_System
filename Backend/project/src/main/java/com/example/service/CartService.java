package com.example.service;

import com.example.entity.Cart;
import com.example.entity.NotificationType;
import com.example.entity.OrderStatus;
import com.example.entity.Product;
import com.example.entity.Users;
import com.example.repo.CartRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private EmailService emailService; 

    public void addProductToCart(Users user, Product product, int quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
            cart.setOrderStatus(OrderStatus.CART);
        }

        cart.addProductWithQuantity(product, quantity);
        emailService.sendProductAddedToCartNotification(user, product, cart);
        String notificationMessage = "Product added to cart: " + product.getName();
        notificationService.sendNotification(user, notificationMessage, NotificationType.EMAIL);
        cartRepository.save(cart);
    }

//    public void updateProductInCart(Users user, Product product, int quantity) {
//        Cart cart = cartRepository.findByUser(user);
//        if (cart != null && cart.getProductQuantityMap().containsKey(product)) {
//            cart.updateProductQuantity(product, quantity);
//            cartRepository.save(cart);
//            String notificationMessage = "cart updated:" + product.getName();
//            notificationService.sendNotification(user, notificationMessage, NotificationType.EMAIL);
//            emailService.sendProductUpdatedInCartNotification(user, product, cart);
//        }
//    }
    public void updateProductInCart(Users user, Product product, int quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null && cart.getProductQuantityMap().containsKey(product)) {
            int oldQuantity = cart.getProductQuantityMap().get(product);
            cart.updateProductQuantity(product, quantity);
            cartRepository.save(cart);
            
            // Fetch updated quantity from cart after save
            int updatedQuantity = cart.getProductQuantityMap().get(product);

            // Prepare notification message
            String notificationMessage = "Cart updated: " + product.getName() +
                                         "\nQuantity updated: " + oldQuantity + " -> " + updatedQuantity;
            
            // Send notification
            notificationService.sendNotification(user, notificationMessage, NotificationType.EMAIL);
            
            // Send email notification
            emailService.sendProductUpdatedInCartNotification(user, product, cart);
        }
    }

//    public void removeProductFromCart(Users user, Long productId) {
//        Cart cart = cartRepository.findByUser(user);
//        if (cart != null) {
//            Product productToRemove = cart.getProducts().stream()
//                    .filter(product -> product.getId().equals(productId))
//                    .findFirst()
//                    .orElse(null);
//
//            if (productToRemove != null) {
//                cart.removeProduct(productToRemove);
//                cartRepository.save(cart);
//                emailService.sendProductRemovedFromCartNotification(user, productToRemove, cart);
//            }
//        }
//    }
    public void removeProductFromCart(Users user, Long productId) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            Product productToRemove = cart.getProducts().stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (productToRemove != null) {
                cart.removeProduct(productToRemove);
                cartRepository.save(cart);

                // Prepare notification message
                String notificationMessage = "Product removed from cart:\n" +
                                             "Product Name: " + productToRemove.getName();

                // Send notification
                notificationService.sendNotification(user, notificationMessage, NotificationType.EMAIL);

                // Send email notification
                emailService.sendProductRemovedFromCartNotification(user, productToRemove, cart);
            } else {
                System.out.println("Product with ID " + productId + " not found in the cart for user " + user.getUsername());
            }
        } else {
            System.out.println("Cart not found for user " + user.getUsername());
        }
    }

    public Cart getCartByUser(Users user) {
        return cartRepository.findByUser(user);
    }
    
    public void placeOrder(Users user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            cart.placeOrder();
            cartRepository.save(cart);
            notificationService.sendNotification(user, "Your oder has been placed successfully", NotificationType.EMAIL);
            emailService.sendOrderConfirmation(user, cart);
        }
    }
    
    public void sendPendingOrderNotifications() {
        List<Cart> carts = cartRepository.findByOrderStatus(OrderStatus.CART);
        for (Cart cart : carts) {
            Users user = cart.getUser();
            notificationService.sendNotification(user, "You have pending items in your cart. Please complete your purchase.", NotificationType.EMAIL);
            emailService.sendPendingOrderNotification(user, cart);
        }
    }
}
