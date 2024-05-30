package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.NotificationService;
import com.example.serviceImpl.NotificationServiceImpl;


@Configuration
public class AppConfig {
	
    
    @Bean
    public NotificationService notificationService() {
        return new NotificationServiceImpl();
    }
}
