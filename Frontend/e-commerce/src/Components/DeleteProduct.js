

import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProductById, deleteProduct } from '../Services/ProductService';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const DeleteProduct = () => {
  const { productId } = useParams();
  const navigate = useNavigate();
  
  const [product, setProduct] = useState(null);

  useEffect(() => {
    getProductById(productId).then(response => {
      setProduct(response.data);
    });
  }, [productId]);

  const handleDelete = () => {
    deleteProduct(productId).then(() => {
      toast.success("Product deleted successfully");
      setTimeout(() => {
        navigate('/products');
      }, 8000);
    }).catch(error => {
      toast.error("Error deleting product");
      console.error('Error deleting product:', error);
    });
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mt-4">
      <ToastContainer />
      <h2 className="mb-4">Delete Product</h2>
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">{product.name}</h5>
          <p className="card-text">Quantity Available: {product.quantityAvailable}</p>
          <p className="card-text">Price: &#8377;{product.price}</p>
          <button className="btn btn-danger" onClick={handleDelete}>Delete Product</button>
        </div>
      </div>
    </div>
  );
};

export default DeleteProduct;
