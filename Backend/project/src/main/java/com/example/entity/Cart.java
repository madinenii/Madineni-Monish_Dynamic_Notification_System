package com.example.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "cart_products",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "cart_product_quantity",
            joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantityMap = new HashMap<>();

    @Column(nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus= OrderStatus.CART;
    
    public void clearCart() {
        this.products.clear();
        this.productQuantityMap.clear();
        this.totalPrice = 0.0; // Reset total price
    }

    public Cart() {
    }

    public Cart(Users user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        addProductWithQuantity(product, 1); // Default quantity is 1
    }

    public void addProductWithQuantity(Product product, int quantity) {
        if (productQuantityMap.containsKey(product)) {
            int currentQuantity = productQuantityMap.get(product);
            productQuantityMap.put(product, currentQuantity + quantity);
        } else {
            products.add(product);
            productQuantityMap.put(product, quantity);
        }
        updateTotalPrice();
    }

    public void updateProductQuantity(Product product, int quantity) {
        if (productQuantityMap.containsKey(product)) {
            productQuantityMap.put(product, quantity);
            updateTotalPrice();
        }
    }

    public void removeProduct(Product product) {
        if (productQuantityMap.containsKey(product)) {
            products.remove(product);
            productQuantityMap.remove(product);
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += product.getPrice() * quantity;
        }
    }

    public Map<Product, Integer> getProductQuantityMap() {
        return productQuantityMap;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    public void placeOrder() {
        this.orderStatus = OrderStatus.PLACED;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

	public void setProductQuantityMap(Map<Product, Integer> productQuantityMap) {
		this.productQuantityMap = productQuantityMap;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
    
}
