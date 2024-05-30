import AboutPage from "./Pages/AboutPage";
import ContactPage from "./Pages/ContactPage";
import HomePage from "./Pages/HomePage";
import ProductPage from "./Pages/ProductPage";
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import ProductList from "./Components/ProductsList";
import AddProduct from "./Components/AddProduct";
import UpdateProduct from "./Components/UpdateProduct";
import Display from "./Pages/Inventory/Display";
import Cart from "./Pages/Cart/Cart";
import './App.css';
import NotificationPage from "./Pages/NotificationPage";
import OrderSuccess from "./Pages/OrderSuccess/OrderSuccess";
import Login from "./Components/Login";
import Register from "./Components/Register";
import ValidateOtp from "./Components/ValidateOtp";
import PasswordReset from "./Components/PasswordReset";
import ProfilePage from "./Pages/Profile/ProfilePage";
import AdminPage from "./Pages/Admin/AdminPage";
import DeleteProduct from "./Components/DeleteProduct";







function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={ <HomePage/> } />
        <Route path="/products" element={ <ProductPage /> } />
        <Route path="/about" element={ <AboutPage /> } />
        <Route path="/contact" element={ <ContactPage /> } />
        <Route exact path="/register" element={<Register />} />
          <Route exact path="/validate-otp" element={<ValidateOtp/>} />
          <Route exact path="/login" element={<Login/>} /> 
          <Route exact path="/reset-password" element={<PasswordReset/>} />
          <Route exact path="/profile/:userId" element={<ProfilePage/>} />
        <Route exact path="/inventory" element={<ProductList />} />
          <Route path="/add" element={<AddProduct />} />
          <Route path="/update/:productId" element={<UpdateProduct />} />
          <Route path="/delete/:productId" element={<DeleteProduct />} />
          <Route path="/display" element={<Display/>} />
          <Route path="/cart" element={<Cart/>} />
          <Route path="/notifications/:userId" element={<NotificationPage />} />
          <Route path="/order-success" element={<OrderSuccess />} />
          <Route path="/admin" element={<AdminPage />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
