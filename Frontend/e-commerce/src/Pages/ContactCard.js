
import React from 'react';
import photographImage from '../Pages/profile.jpg';
const ContactCard = () => {
    return (
        <div className="row justify-content-center">
                <div className="card mt-4">
                    <img className="card-img-top" src={photographImage} alt="Monish" style={{ width: '200px', height: '200px' }} />
                    <div className="card-body">
                        <h5 className="card-title"> Name: Madineni Monish</h5>
                        <p className="card-text">Profession: Software developer trainee</p>
                        <p className="card-text">Location: Hyderabad</p>
                        <p className="card-text">Mail: madinenimounish2001@gmail.com</p>
                    </div>
                </div>
    </div>
    );
};

export default ContactCard;
