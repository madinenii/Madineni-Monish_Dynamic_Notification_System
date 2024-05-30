package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Notification;
import com.example.entity.Users;

import jakarta.transaction.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByRecipient(Users recipient);
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = true WHERE n.id = :notificationId AND n.recipient.id = :userId")
    void markAsRead(int userId, int notificationId);
    int countByRecipientIdAndStatusFalse(int userId);
}
