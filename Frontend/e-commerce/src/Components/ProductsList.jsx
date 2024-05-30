
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllProducts, deleteProduct } from '../Services/ProductService';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [productsPerPage] = useState(5);

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = () => {
    getAllProducts().then(response => {
      setProducts(response.data);
    });
  };

  const handleDelete = (productId) => {
    deleteProduct(productId).then(() => {
      toast.success("Product deleted successfully");
      loadProducts();
    }).catch(error => {
      toast.error("Error deleting product");
      console.error('Error deleting product:', error);
    });
  };

  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = products.slice(indexOfFirstProduct, indexOfLastProduct);

  const paginate = pageNumber => setCurrentPage(pageNumber);

  return (
    <div className="container mt-4">
      <ToastContainer />
      <h2 className="text-center mb-4">Product List</h2>
      <Link to="/add" className="btn btn-primary mb-4">Add Product</Link>
      <div className="row">
        <div className="col">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {currentProducts.map(product => (
                <tr key={product.id}>
                  <td>{product.name}</td>
                  <td>{product.quantityAvailable}</td>
                  <td>&#8377;{product.price.toFixed(2)}</td>
                  <td>
                    <Link to={`/update/${product.id}`} className="btn btn-sm btn-outline-primary mr-2">Update</Link>
                    <button 
                      onClick={() => handleDelete(product.id)} 
                      className="btn btn-sm btn-outline-danger"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <nav>
            <ul className="pagination justify-content-center">
              {Array.from({ length: Math.ceil(products.length / productsPerPage) }, (_, i) => (
                <li key={i} className={`page-item ${currentPage === i + 1 ? 'active' : ''}`}>
                  <button onClick={() => paginate(i + 1)} className="page-link">
                    {i + 1}
                  </button>
                </li>
              ))}
            </ul>
          </nav>
        </div>
      </div>
    </div>
  );
};

export default ProductList;

