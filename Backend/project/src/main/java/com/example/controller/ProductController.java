package com.example.controller;


import com.example.entity.Product;
import com.example.service.ProductService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    
//    @PutMapping("/{productId}/updateQuantity")
//    public ResponseEntity<Product> updateProductQuantity(@PathVariable Long productId, @RequestParam int quantityAvailable) {
//        Product updatedProduct = productService.updateProductQuantity(productId, quantityAvailable);
//        if (updatedProduct != null) {
//            return ResponseEntity.ok(updatedProduct);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/{productId}/updatePrice")
//    public ResponseEntity<Product> updateProductPrice(@PathVariable Long productId, @RequestParam double price) {
//        Product updatedProduct = productService.updateProductPrice(productId, price);
//        if (updatedProduct != null) {
//            return ResponseEntity.ok(updatedProduct);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request) {
        Product updatedProduct = productService.updateProduct(productId, request.getQuantityAvailable(), request.getPrice());
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/deleteitem/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean isRemoved = productService.deleteProduct(productId);
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    static class UpdateProductRequest {
        private int quantityAvailable;
        private double price;

        public int getQuantityAvailable() {
            return quantityAvailable;
        }

        public void setQuantityAvailable(int quantityAvailable) {
            this.quantityAvailable = quantityAvailable;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
   
}
