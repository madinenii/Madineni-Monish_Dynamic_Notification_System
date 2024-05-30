

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Navbar from '../Components/Navbar'; 
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './NotificationPage.css'; 

const NotificationPage = () => {
    const userId = 1; 
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchNotifications(userId);
    }, [userId]);

    const fetchNotifications = async (userId) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/notifications/${userId}`);
            const sortedNotifications = response.data.sort((a, b) => new Date(b.notificationDate) - new Date(a.notificationDate));
            setNotifications(sortedNotifications);
        } catch (error) {
            setError('Error fetching notifications');
            console.error('Error fetching notifications:', error);
        } finally {
            setLoading(false);
        }
    };

    const markAsRead = async (userId, notificationId) => {
        try {
            await axios.post(`http://localhost:8080/api/notifications/markAsRead/${userId}/${notificationId}`);
            setNotifications(prevNotifications =>
                prevNotifications.map(notification =>
                    notification.id === notificationId ? { ...notification, status: true } : notification
                )
            );
            toast.success('Notification marked as read');
        } catch (error) {
            toast.error('Error marking notification as read');
            console.error('Error marking notification as read:', error);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    const unreadCount = notifications.filter(notification => !notification.status).length;

    const formatDateTime = (dateString) => {
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false,
        };
        return new Intl.DateTimeFormat('en-GB', options).format(new Date(dateString));
    };

    return (
        <div>
            <Navbar />
            <div className="container mt-5">
                <h2>Notifications ({unreadCount})</h2>
                <ToastContainer />
                <div className="list-group">
                    {notifications.length > 0 ? (
                        notifications.map(notification => (
                            <div key={notification.id} className={`list-group-item ${notification.status ? 'read' : ''}`}>
                                <p>{notification.message}</p>
                                <div className="action-buttons">
                                    <p className="notification-date">
                                        Date: {formatDateTime(notification.notificationDate)}
                                    </p>
                                    {!notification.status && (
                                        <button
                                            className="btn btn-primary"
                                            onClick={() => markAsRead(userId, notification.id)}
                                        >
                                            Mark as Read
                                        </button>
                                    )}
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="list-group-item no-notifications">
                            No notifications found.
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default NotificationPage;
