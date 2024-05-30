package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/user/registration","/user/validate-otp","/user/resend-otp", 
						"/user/initiate-password-reset","/user/login","/user/reset-password",
						"/user/id/{userId}","/user/verifyUser/{email}","/user/login/{email}/{password}/{role}","/api/products/getall",
						"/api/products/add","/api/products/{productId}","/api/cart/addToCart",
						"api/cart/updateQuantity","/api/cart/removeProduct","/api/cart/placeOrder",
						"/api/cart/getCartItems","/api/cart/getCartDetails","/api/products/{productId}/updateQuantity",
						"/api/products/{productId}/updatePrice","/api/products/{productId}/update","/api/cart/PendingOrder",
						"/api/cart/sendPendingOrderNotificationsToAll","/api/cart/notifications/{userId}","/api/notifications/{userId}",
						"api/notifications/markAsRead/{userId}/{notificationId}","api/notifications/count/{userId}","/api/order/placeOrder",
						"/user/details/id/{userId}","/api/products/deleteitem/{productId}").permitAll()
//				.requestMatchers("/user/**").hasAuthority("User")
//				.requestMatchers("/user/login/{email}/{password}/{role}").hasAnyAuthority("ROLE_User")
				.and().build();
				
	}
 
}