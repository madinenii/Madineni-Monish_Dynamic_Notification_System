import React, { useState, useEffect } from "react";
import axios from "axios";
import Navbar from '../Components/Navbar';
import { useParams } from "react-router-dom";

const ProductDetailPage = () => {
  const {id} = useParams()
  const [products, setProduct] = useState({});

  const fetchData = () => {
    axios
      // .get("https://api.escuelajs.co/api/v1/products/" + id)
      .get("http://localhost:8080/api/products/getall"+ id)
      .then((response) => setProduct(response.data))
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    fetchData();
  }, [id]);

  return (
    <>
      <Navbar />
      <div className="container">
        <div
          style={{
            backgroundColor: "#fff",
            padding: "40px",
            marginTop: "80px",
          }}
        >
          <div className="row">
            <div className="col-md-6">
                <img src={ products.images } alt="" className="img-fluid" />
            </div>
            <div className="col-md-6">
                <h3>{ products.title }</h3>
                <p>{ products.description }</p>
                <br />
                <h2>
                    <span>&#8377;</span>
                    { products.price }
                </h2>
                <br />
                <button className="btn btn-primary">Add To Cart</button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default ProductDetailPage;
