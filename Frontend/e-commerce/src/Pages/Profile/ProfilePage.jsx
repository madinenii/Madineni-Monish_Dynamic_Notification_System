import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Navbar from '../../Components/Navbar';
import "../Profile/ProfilePage.css"

const ProfilePage = () => {
    const userId = 1; 
    const [userDetails, setUserDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchUserDetails();
    }, []);

    const fetchUserDetails = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/user/details/id/${userId}`);
            setUserDetails(response.data);
        } catch (error) {
            setError('Error fetching user details');
            console.error('Error fetching user details:', error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div>
            <Navbar/>
            <h2>Profile Page</h2>
            {userDetails ? (
                <div>
                    <p>First Name: {userDetails.firstName}</p>
                    <p>Last Name: {userDetails.lastName}</p>
                    <p>Email: {userDetails.email}</p>
                    <p>Username: {userDetails.username}</p>
                    <p>Role: {userDetails.role}</p>
                    {/* Add other user details here */}
                </div>
            ) : (
                <p>No user details found</p>
            )}
        </div>
    );
};

export default ProfilePage;
