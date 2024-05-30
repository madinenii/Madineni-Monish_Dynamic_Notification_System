

// import React from 'react';
// import { Link, useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import { ToastContainer, toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
// // import "./Product.css";

// const Product = (props) => {
//     const { id, name, price, quantityAvailable } = props.data;
//     const navigate = useNavigate();

//     const addToCart = () => {
//         const userId = 1; 
//         axios.post(`http://localhost:8080/api/cart/addToCart`, null, {
//             params: {
//                 userId,
//                 productId: id,
//                 quantity: 1 
//             }
//         })
//         .then(response => {
//             toast.success("Product added to cart successfully!");
//             setTimeout(() => {
//                 navigate('/cart'); 
//             }, 5000);
//         })
//         .catch(error => {
//             toast.error('Error adding product to cart.');
//             console.error('Error adding product to cart:', error);
//         });
//     };

//     return (
//         <>
//             <ToastContainer />
//             <div className="col-md-4 mb-4">
//                 <div className="card h-100 shadow-sm">
//                     <img src={props.data.images} className="card-img-top" alt={props.data.title} />
//                     <div className="card-body">
//                         <h5 className="card-title">{name}</h5>
//                         <p className="card-text text-muted">Price: &#8377; {price}</p>
//                         <p className="card-text"><small className="text-muted">Stock: {quantityAvailable}</small></p>
//                         <button onClick={addToCart} className="btn btn-primary btn-block">
//                             Add to Cart
//                         </button>
//                     </div>
//                     <div className="card-footer bg-white border-top-0">
//                         <Link to={`/products/detail/${id}`} className="btn btn-outline-primary btn-sm btn-block">
//                             View Details
//                         </Link>
//                     </div>
//                 </div>
//             </div>
//         </>
//     );
// }

// export default Product;


import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './Product.css';

const Product = (props) => {
    const { id, name, price, quantityAvailable, images, title } = props.data;
    const navigate = useNavigate();

    const addToCart = () => {
        const userId = 1; 
        axios.post(`http://localhost:8080/api/cart/addToCart`, null, {
            params: {
                userId,
                productId: id,
                quantity: 1 
            }
        })
        .then(response => {
            toast.success("Product added to cart successfully!");
            setTimeout(() => {
                navigate('/cart'); 
            }, 5000);
        })
        .catch(error => {
            toast.error('Error adding product to cart.');
            console.error('Error adding product to cart:', error);
        });
    };

    return (
        <>
            <ToastContainer />
            <div className="card h-100 shadow-sm">
                <img src={images} className="card-img-top" alt={title} />
                <div className="card-body">
                    <h5 className="card-title">{name}</h5>
                    <p className="card-text text-muted">Price: &#8377; {price}</p>
                    <p className="card-text"><small className="text-muted">Stock: {quantityAvailable}</small></p>
                    <button onClick={addToCart} className="btn btn-primary btn-block">
                        Add to Cart
                    </button>
                </div>
                <div className="card-footer bg-white border-top-0">
                    <Link to={`/products/detail/${id}`} className="btn btn-outline-primary btn-sm btn-block">
                        View Details
                    </Link>
                </div>
            </div>
        </>
    );
}

export default Product;
