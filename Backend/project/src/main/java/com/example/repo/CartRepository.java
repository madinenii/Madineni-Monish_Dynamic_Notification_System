package com.example.repo;


import com.example.entity.Cart;
import com.example.entity.OrderStatus;
import com.example.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(Users user);
    List<Cart> findByOrderStatus(OrderStatus orderStatus);
}
