package com.example.serviceImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.NotificationDTO;
import com.example.entity.Notification;
import com.example.entity.Users;
import com.example.repo.NotificationRepository;
import com.example.repo.user_repo;
import com.example.service.NotificationService;
import com.example.service.EmailService;
import com.example.entity.NotificationType;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    @Autowired
    private user_repo userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;


//    @Override
//    public void sendNotification(Users recipient, String message, NotificationType type) {
//        Notification notification = new Notification();
//        notification.setRecipient(recipient);
//        notification.setMessage(message);
//        notification.setStatus(false);
//        notificationRepository.save(notification);
//
//        if (type == NotificationType.EMAIL) {
//            emailService.sendEmail(recipient.getEmail(), "Notification", message);
//        }
//        
//    }
//
//    @Override
//    public List<NotificationDTO> getNotificationsForUser(int userId) {
//        Users user = userRepository.findById(userId).orElse(null);
//        if (user != null) {
//            List<Notification> notifications = notificationRepository.findByRecipient(user);
//            return notifications.stream()
//                    .map(notification -> new NotificationDTO(
//                            notification.getId(),
//                            notification.getMessage(),
//                            notification.isStatus() // Ensure to use isStatus() method
//                    ))
//                    .collect(Collectors.toList());
//        }
//        return Collections.emptyList();
//    }
    @Override
    public void sendNotification(Users recipient, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setStatus(false);
        notification.setNotificationDate(new Date()); // Set current date/time

        notificationRepository.save(notification);

        if (type == NotificationType.EMAIL) {
            emailService.sendEmail(recipient.getEmail(), "Notification", message);
        }
       
    }

    @Override
    public List<NotificationDTO> getNotificationsForUser(int userId) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Notification> notifications = notificationRepository.findByRecipient(user);
            return notifications.stream()
                    .map(notification -> new NotificationDTO(
                            notification.getId(),
                            notification.getMessage(),
                            notification.isStatus(),
                            notification.getNotificationDate()
                    ))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    @Override
    public void markNotificationAsRead(int userId, int notificationId) {
        notificationRepository.markAsRead(userId, notificationId);
    }
    @Override
    public int getUnreadNotificationCount(int userId) {
        return notificationRepository.countByRecipientIdAndStatusFalse(userId);
    }
}
