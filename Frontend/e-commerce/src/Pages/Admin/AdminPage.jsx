


import React from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Navbar from '../../Components/Navbar';

const AdminPage = () => {
    const sendPendingOrderNotifications = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/cart/sendPendingOrderNotificationsToAll');
            toast.success(response.data);
        } catch (error) {
            toast.error('Failed to send pending order notifications. Please try again later.');
            console.error('Error sending notifications:', error.response ? error.response.data : error.message);
        }
    };

    return (
        <div>
            <Navbar />
            <div className="container mt-5">
                <h1>Admin Dashboard</h1>
                <p>Welcome to the admin dashboard. Here you can manage the application settings, users, and more.</p>
                <button className="btn btn-primary" onClick={sendPendingOrderNotifications}>
                    Send Pending Order Notifications
                </button>
            </div>
            <ToastContainer />
        </div>
    );
};

export default AdminPage;
