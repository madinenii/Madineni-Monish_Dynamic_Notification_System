package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.NotificationDTO;
import com.example.entity.Notification;
import com.example.entity.NotificationType;
import com.example.entity.Users;
import com.example.service.NotificationService;
import com.example.service.user_services;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private user_services userService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam int userId,
            @RequestParam String message,
            @RequestParam String type) {

        Users recipient = userService.getUserById(userId);
        if (recipient != null) {
            NotificationType notificationType = NotificationType.valueOf(type.toUpperCase());
            notificationService.sendNotification(recipient, message, notificationType);
            return ResponseEntity.ok("Notification sent successfully");
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable int userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }
    @PostMapping("/markAsRead/{userId}/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable int userId, @PathVariable int notificationId) {
        notificationService.markNotificationAsRead(userId, notificationId);
        return ResponseEntity.ok("Notification marked as read");
    }
    @GetMapping("/count/{userId}")
    public ResponseEntity<Map<String, Integer>> getUnreadNotificationCount(@PathVariable int userId) {
        int count = notificationService.getUnreadNotificationCount(userId);
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}
