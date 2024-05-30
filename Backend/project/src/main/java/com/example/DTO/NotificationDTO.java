package com.example.DTO;

import java.util.Date;

public class NotificationDTO {
    private int id;
    private String message;
    private boolean status;
    private Date notificationDate;

    public NotificationDTO(int id, String message, boolean status, Date notificationDate) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.notificationDate = notificationDate;
    }

    // Getters and setters omitted for brevity

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }
}
