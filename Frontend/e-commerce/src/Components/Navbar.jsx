
// import React from 'react';
// import { Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import useNotificationCount from './useNotificationCount'; 
// import "./Navbar.css";

// const Navbar = () => {
//     const userId = 1; 
//     const notificationCount = useNotificationCount(userId);

//     return (
//         <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
//             <Link className="navbar-brand" to="/">EShop</Link>
//             <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
//                 <span className="navbar-toggler-icon"></span>
//             </button>

//             <div className="collapse navbar-collapse" id="navbarSupportedContent">
//                 <ul className="navbar-nav mr-auto">
//                     <li className="nav-item active">
//                         <Link className="nav-link" to="/">Home</Link>
//                     </li>
//                     <li className="nav-item">
//                         <Link className="nav-link" to="/products">Products</Link>
//                     </li>
//                     <li className="nav-item">
//                         <Link className="nav-link" to="/about">About</Link>
//                     </li>
//                     <li className="nav-item">
//                         <Link className="nav-link" to="/contact">Contact</Link>
//                     </li>
//                     <li className="nav-item">
//                         <Link className="nav-link" to={`/notifications/${userId}`}>
//                             Notifications {notificationCount > 0 && (
//                                 <span className="badge badge-pill badge-danger">{notificationCount}</span>
//                             )}
//                         </Link>
//                     </li>
                    // {/* <li className="nav-item">
                    //     <Link className="nav-link" to="/register">Register</Link>
                    // </li>
                    // <li className="nav-item">
                    //     <Link className="nav-link" to="/login">Login</Link>
                    // </li> */}
//                     {/* <li className="nav-item">
//                         <Link className="nav-link" to={`/profile/${userId}`}>Profile</Link>
//                     </li> */}
//                 </ul>
//                 <Link className="btn btn-outline-light ml-2" to="/cart">
//                     Cart
//                 </Link>
//             </div>
//         </nav>
//     );
// };

// export default Navbar;


import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import useNotificationCount from './useNotificationCount'; 
import "./Navbar.css";


const Navbar = () => {
    const userId = 1; 
    const notificationCount = useNotificationCount(userId);

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <Link className="navbar-brand" to="/">EShop</Link>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item active">
                        <Link className="nav-link" to="/">Home</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/products">Products</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/about">About</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/contact">Contact</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to={`/notifications/${userId}`}>
                            <span className="notification-icon">Notifications</span>
                            {notificationCount > 0 && (
                                <span className="badge badge-pill badge-danger">{notificationCount}</span>
                            )}
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/admin">Admin</Link>
                    </li>
                    {/* <li className="nav-item">
                        <Link className="nav-link" to="/register">Register</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/login">Login</Link>
                    </li> */}
                </ul>
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="btn btn-outline-light" to="/cart">
                            Cart
                        </Link>
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;