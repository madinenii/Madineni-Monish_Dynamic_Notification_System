import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const PendingOrderNotification = ({ userId }) => {
    const [pendingOrderCount, setPendingOrderCount] = useState(0);

    useEffect(() => {
        const fetchPendingOrders = async () => {
            try {
                const response = await axios.get(`/api/orders/pending/${userId}`);
                setPendingOrderCount(response.data.count);
            } catch (error) {
                console.error('Failed to fetch pending orders:', error);
            }
        };

        fetchPendingOrders();
    }, [userId]);

    return (
        <Link className="nav-link" to="/pending-orders">
            <span className="notification-icon">Pending Orders</span>
            {pendingOrderCount > 0 && (
                <span className="badge badge-pill badge-warning">{pendingOrderCount}</span>
            )}
        </Link>
    );
};

export default PendingOrderNotification;
