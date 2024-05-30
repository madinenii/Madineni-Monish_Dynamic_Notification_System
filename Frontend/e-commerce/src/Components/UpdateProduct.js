
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProductById, updateProduct } from '../Services/ProductService';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const UpdateProduct = () => {
  const { productId } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [quantity, setQuantity] = useState(0);
  const [price, setPrice] = useState(0.0);

  useEffect(() => {
    getProductById(productId).then(response => {
      const product = response.data;
      setName(product.name);
      setQuantity(product.quantityAvailable);
      setPrice(product.price);
    });
  }, [productId]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const product = { name, quantityAvailable: quantity, price };
    updateProduct(productId, product).then(() => {
      toast.success("Product updated successfully");
      // navigate('/');
      setTimeout(() => {
        navigate('/products');
      }, 8000);
    }).catch(error => {
      toast.error("Error updating product");
      console.error('Error updating product:', error);
    });
  };

  return (
    <div className="container mt-4">
      <ToastContainer />
      <h2 className="mb-4">Update Product</h2>
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
        <button type="submit" className="btn btn-primary">Update Product</button>
      </form>
    </div>
  );
};

export default UpdateProduct;
