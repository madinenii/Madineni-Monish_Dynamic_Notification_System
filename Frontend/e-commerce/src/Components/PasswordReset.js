import React, { useState } from 'react';
import axios from 'axios';

const PasswordReset = () => {
    const [email, setEmail] = useState('');
    const [resetToken, setResetToken] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [step, setStep] = useState(1);

    const handleInitiateReset = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/user/initiate-password-reset', null, {
                params: { email: email }
            });
            alert(response.data);
            setStep(2);
        } catch (error) {
            alert(error.response.data);
        }
    };

    const handleResetPassword = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/user/reset-password', null, {
                params: {
                    email: email,
                    resetToken: resetToken,
                    newPassword: newPassword
                }
            });
            alert(response.data);
        } catch (error) {
            alert(error.response.data);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Password Reset</h2>
            {step === 1 ? (
                <form onSubmit={handleInitiateReset}>
                    <div className="form-group">
                        <label>Email</label>
                        <input type="email" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <button type="submit" className="btn btn-primary">Initiate Password Reset</button>
                </form>
            ) : (
                <form onSubmit={handleResetPassword}>
                    <div className="form-group">
                        <label>Reset Token</label>
                        <input type="text" className="form-control" value={resetToken} onChange={(e) => setResetToken(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label>New Password</label>
                        <input type="password" className="form-control" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} />
                    </div>
                    <button type="submit" className="btn btn-primary">Reset Password</button>
                </form>
            )}
        </div>
    );
};

export default PasswordReset;
