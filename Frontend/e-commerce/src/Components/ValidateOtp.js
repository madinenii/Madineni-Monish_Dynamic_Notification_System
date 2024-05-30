import React, { useState } from 'react';
import axios from 'axios';

const ValidateOtp = () => {
    const [email, setEmail] = useState('');
    const [otp, setOtp] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/user/validate-otp', null, {
                params: {
                    email: email,
                    enteredOtp: otp
                }
            });
            alert(response.data);
        } catch (error) {
            alert(error.response.data);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Validate OTP</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Email</label>
                    <input type="email" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>OTP</label>
                    <input type="text" className="form-control" value={otp} onChange={(e) => setOtp(e.target.value)} />
                </div>
                <button type="submit" className="btn btn-primary">Validate OTP</button>
            </form>
        </div>
    );
};

export default ValidateOtp;
