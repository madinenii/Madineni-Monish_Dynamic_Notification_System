import React from 'react';
import Navbar from '../../Components/Navbar'; 

const OrderSuccess = () => {
    return (
        <div>
            <Navbar />
            <div className="container text-center mt-5">
                <h2>Your Order Was Successfully Placed!</h2>
                <p className="mt-3">Thank you for shopping with us.</p>
                <p className="mt-3">
                    You will receive an email confirmation shortly.
                </p>
                <button className="btn btn-primary mt-4" onClick={() => window.location.href = '/'}>Back to Home</button>
            </div>
        </div>
    );
}

export default OrderSuccess;
