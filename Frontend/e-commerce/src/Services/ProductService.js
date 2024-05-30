// src/services/ProductService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/products';

const getAllProducts = () => axios.get(`${API_URL}/getall`);
const getProductById = (productId) => axios.get(`${API_URL}/${productId}`);
const addProduct = (product) => axios.post(`${API_URL}/add`, product);
const updateProduct = (productId, product) => axios.put(`${API_URL}/${productId}/update`, product);
const deleteProduct = (productId) => axios.delete(`${API_URL}/deleteitem/${productId}`);

export { getAllProducts, getProductById, addProduct, updateProduct, deleteProduct };
