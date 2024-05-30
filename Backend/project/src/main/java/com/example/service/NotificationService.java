package com.example.service;

import java.util.List;

import com.example.DTO.NotificationDTO;
import com.example.entity.Notification;
import com.example.entity.Users;
import com.example.entity.NotificationType;

public interface NotificationService {
    void sendNotification(Users recipient, String message, NotificationType type);
   // List<Notification> getNotificationsForUser(int userId);
    List<NotificationDTO> getNotificationsForUser(int userId);
    void markNotificationAsRead(int userId, int notificationId);
    int getUnreadNotificationCount(int userId);
}
