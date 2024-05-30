

import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Navbar from '../../Components/Navbar';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './Cart.css'; 

const Cart = () => {
    const [cart, setCart] = useState(null);
    const userId = 1; 
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://localhost:8080/api/cart/getCartDetails', {
            params: { userId }
        })
        .then(response => {
            // Map quantities from productQuantityMap to products
            const productQuantities = response.data.productQuantityMap;
            const productsWithQuantities = response.data.products.map(product => ({
                ...product,
                quantity: productQuantities[Object.keys(productQuantities).find(key => key.includes(product.id))]
            }));
            setCart({ ...response.data, products: productsWithQuantities });
        })
        .catch(error => {
            console.error('There was an error fetching the cart details!', error);
        });
    }, [userId]);

    const updateQuantity = (productId, quantity) => {
        axios.put('http://localhost:8080/api/cart/updateQuantity', null, {
            params: { userId, productId, quantity }
        })
        .then(response => {
            setCart(prevCart => {
                const updatedProducts = prevCart.products.map(product => 
                    product.id === productId ? { ...product, quantity: parseInt(quantity) } : product
                );
                return { ...prevCart, products: updatedProducts, totalPrice: response.data.totalPrice };
            });
            toast.success("Quantity updated successfully!");
        })
        .catch(error => {
            toast.error('There was an error updating the quantity!');
            console.error('There was an error updating the quantity!', error);
        });
    };

    const removeItem = (productId) => {
        axios.delete('http://localhost:8080/api/cart/removeProduct', {
            params: { userId, productId }
        })
        .then(response => {
            setCart(prevCart => {
                const updatedProducts = prevCart.products.filter(product => product.id !== productId);
                return { ...prevCart, products: updatedProducts, totalPrice: response.data.totalPrice };
            });
            toast.success("Product removed successfully!");
        })
        .catch(error => {
            toast.error('There was an error removing the product!');
            console.error('There was an error removing the product!', error);
        });
    };

    const placeOrder = () => {
        axios.post('http://localhost:8080/api/cart/placeOrder', null, {
            params: { userId }
        })
        .then(response => {
            toast.success("Order placed successfully!");
            setTimeout(() => {
                setCart(null); 
                navigate('/order-success'); 
            }, 2000);
        })
        .catch(error => {
            toast.error('There was an error placing the order!');
            console.error('There was an error placing the order!', error);
        });
    };

    if (!cart) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Navbar />
            <ToastContainer />
            <div className="container cart-container">
                <h2 className="mb-4">Your Cart</h2>
                {cart.products.length === 0 ? (
                    <p className="text-center">Your cart is empty.</p>
                ) : (
                    <div>
                        {cart.products.map(product => (
                            <div key={product.id} className="cart mb-3">
                                <div className="cart-body">
                                    <h4 className="cart-title">{product.name}</h4>
                                    <p className="cart-text">Price: &#8377;{product.price}</p>
                                    <div className="form-group">
                                        <label>Quantity:</label>
                                        <input 
                                            type="number" 
                                            value={product.quantity} 
                                            onChange={(e) => updateQuantity(product.id, e.target.value)} 
                                            min="1"
                                            className="form-control"
                                        />
                                    </div>
                                    {/* <p className="card-text">Total Quantity: {product.quantity}</p> */}
                                    <button onClick={() => removeItem(product.id)} className="btn btn-danger mr-2">Remove</button>
                                </div>
                            </div>
                        ))}
                        <h3 className="text-right mt-4">Total Price: &#8377;{cart.totalPrice}</h3>
                        <div className="text-center">
                            <button onClick={placeOrder} className="btn btn-success mt-3">Place Order</button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Cart;
