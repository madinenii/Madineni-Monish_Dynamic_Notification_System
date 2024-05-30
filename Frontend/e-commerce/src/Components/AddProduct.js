

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addProduct } from '../Services/ProductService';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddProduct = () => {
  const [name, setName] = useState('');
  const [quantity, setQuantity] = useState(0);
  const [price, setPrice] = useState(0.0);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const product = { name, quantityAvailable: quantity, price };
    addProduct(product).then(() => {
      toast.success("Product added successfully");
      // navigate('/');
      setTimeout(() => {
        navigate('/products');
      }, 1000);
    }).catch(error => {
      toast.error("Error adding product");
      console.error('Error adding product:', error);
    });
  };

  return (
    <div className="container mt-4">
      <ToastContainer />
      <h2>Add Product</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Name:</label>
          <input type="text" className="form-control" value={name} onChange={(e) => setName(e.target.value)} required />
        </div>
        <div className="form-group">
          <label>Quantity:</label>
          <input type="number" className="form-control" value={quantity} onChange={(e) => setQuantity(e.target.value)} required />
        </div>
        <div className="form-group">
          <label>Price:</label>
          <input type="number" className="form-control" value={price} onChange={(e) => setPrice(e.target.value)} step="0.01" required />
        </div>
        <button type="submit" className="btn btn-primary">Add Product</button>
      </form>
    </div>
  );
};

export default AddProduct;
