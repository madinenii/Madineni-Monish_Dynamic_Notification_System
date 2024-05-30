package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.entity.Users;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendProductAddedToCartNotification(Users user, Product product, Cart cart) {
        String emailText = buildCartEmailText(user, cart, "added", product);
        sendEmail(user.getEmail(), "Product Added to Cart", emailText);
    }

    public void sendProductUpdatedInCartNotification(Users user, Product product, Cart cart) {
        String emailText = buildCartEmailText(user, cart, "updated", product);
        sendEmail(user.getEmail(), "Product Quantity Updated in Cart", emailText);
    }

    public void sendProductRemovedFromCartNotification(Users user, Product product, Cart cart) {
        String emailText = buildCartEmailText(user, cart, "removed", product);
        sendEmail(user.getEmail(), "Product Removed from Cart", emailText);
    }

    private String buildCartEmailText(Users user, Cart cart, String action, Product product) {
        StringBuilder emailText = new StringBuilder();
        emailText.append("Dear ").append(user.getFirstName()).append(",\n\n")
                 .append("You have ").append(action).append(" the following product in your cart:\n")
                 .append("Product Name: ").append(product.getName()).append("\n\n")
                 .append("Here is your updated cart:\n");

        for (Map.Entry<Product, Integer> entry : cart.getProductQuantityMap().entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();
            emailText.append("Product Name: ").append(p.getName()).append("\n")
                     .append("Quantity: ").append(quantity).append("\n")
                     .append("Price: $ ").append(p.getPrice() * quantity).append("\n\n");
        }

        emailText.append("Total Price: $ ").append(cart.getTotalPrice()).append("\n\n")
                 .append("Thank you for shopping with us!\n\n")
                 .append("Best regards,\nThe Team");

        return emailText.toString();
    }
    private String buildOrderConfirmationEmailText(Users user, Cart cart) {
        StringBuilder emailText = new StringBuilder();
        emailText.append("Dear ").append(user.getFirstName()).append(",\n\n")
                .append("Your order has been successfully placed!\n\n")
                .append("Order Details:\n");

        for (Map.Entry<Product, Integer> entry : cart.getProductQuantityMap().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            emailText.append("Product Name: ").append(product.getName()).append("\n")
                    .append("Quantity: ").append(quantity).append("\n")
                    .append("Price: ").append(product.getPrice() * quantity).append("\n\n");
        }

        emailText.append("Total Price: $ ").append(cart.getTotalPrice()).append("\n\n")
                .append("Your order will be processed shortly.\n\n")
                .append("Thank you for shopping with us!\n\n")
                .append("Best regards,\nThe Team");

        return emailText.toString();
    }
    public void sendOrderConfirmation(Users user, Cart cart) {
        String emailText = buildOrderConfirmationEmailText(user, cart);
        sendEmail(user.getEmail(), "Order Confirmation", emailText);
    }

    // Optionally add a method for sending pending order notification
    public void sendPendingOrderNotification(Users user, Cart cart) {
    	String subject = "Your Order is Pending";
    	String emailText = buildPendingOrderEmailText(user, cart);
        sendEmail(user.getEmail(), "Order Pending", emailText);
    }

    private String buildPendingOrderEmailText(Users user, Cart cart) {
        StringBuilder emailText = new StringBuilder();
        emailText.append("Dear ").append(user.getFirstName()).append(",\n\n");
        emailText.append("Your order is pending. Here are the details of your cart:\n\n");

        List<Product> products = cart.getProducts();
        for (Product product : products) {
            emailText.append("Product: ").append(product.getName())
                    .append(", Quantity: ").append(cart.getProductQuantityMap().get(product))
                    .append(", Price: $").append(product.getPrice()).append("\n");
        }

        emailText.append("\nTotal Price: $").append(cart.getTotalPrice()).append("\n\n");
        emailText.append("Thank you for shopping with us!\n");
        emailText.append("Best regards,\nYour Company");

        return emailText.toString();
    }
    
    public void sendProductPriceUpdateEmail(String to, Product product, String message) {
        String emailText = buildProductPriceUpdateEmail(product, message);
        sendEmail(to, "Product Price Update", emailText);
    }

    private String buildProductPriceUpdateEmail(Product product, String message) {
        return String.format(
            "Dear Customer,\n\n" +
            "The price of %s has been updated.\n\n" +
            "New Price: $ %.2f\n\n" +
            "%s\n\n" +
            "To purchase this product, click here: https://example.com/products/%d\n\n" +
            "Best regards,\nThe Team",
            product.getName(),
            product.getPrice(),
            message,
            product.getId()
        );
    }
    
}
