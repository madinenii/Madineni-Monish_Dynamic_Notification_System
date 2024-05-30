import React, { useState, useEffect } from "react";
import axios from "axios";
import Category from "./Category";

const CategoryList = () => {
  const [categories, setCategories] = useState([]);

  const getData = () => {
    axios
      .get("https://api.escuelajs.co/api/v1/categories")
      .then((response) => setCategories(response.data))
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div className="container">
      <h2 className="text-center">All Categoriers</h2>
      <div class="row">
        {categories.map((category) => (
          <Category key={category.id} data={category} />
        ))}
      </div>
    </div>
  );
};
export default CategoryList;
