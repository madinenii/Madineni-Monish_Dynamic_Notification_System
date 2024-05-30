
import { useState, useEffect } from 'react';
import axios from 'axios';

const useNotificationCount = (userId, refreshNotifications) => {
    const [notificationCount, setNotificationCount] = useState(0);

    useEffect(() => {
        const fetchNotificationCount = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/notifications/count/${userId}`);
                setNotificationCount(response.data.count);
            } catch (error) {
                console.error('Error fetching notification count:', error);
            }
        };

        fetchNotificationCount();
    }, [userId, refreshNotifications]);

    return notificationCount;
};

export default useNotificationCount;
