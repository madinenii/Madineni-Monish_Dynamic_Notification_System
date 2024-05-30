import React, { useState, useEffect } from "react";
import axios from "axios";
import { useFormik } from "formik";
import * as Yup from "yup";
import { Link } from "react-router-dom";

const RegisterPage = () => {
  const initialValues = {
    firstName: "",
    email: "",
    mobile: "",
    password: "",
  };
  const onSubmit = (values) => {
    console.log(values);
  };

  // validation schema
  const validationSchema = Yup.object({
    firstName: Yup.string().required("first name is required"),
    email: Yup.string()
      .required("email is required")
      .email("email should be a valid email"),
    mobile: Yup.string().required("mobile is required"),
    password: Yup.string()
      .required("password is required")
      .min(6, "password should be at least 6 characters"),
  });

  const formik = useFormik({
    initialValues,
    onSubmit,
    validationSchema
  });

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-3"></div>
        <div className="col-md-6">
          <div className="wrapper">
            <h2>Register</h2>
            <hr />
            <h2>{formik.values.firstName}</h2>
            <form onSubmit={formik.handleSubmit}>
              <div className="form-group">
                <label>First Name</label>
                <input
                  type="text"
                  name="firstName"
                  className="form-control"
                  value={formik.values.firstName}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.firstName ? (<small className="text-danger">{ formik.errors.firstName }</small>) : null}
              </div>

              <div className="form-group">
                <label>Email</label>
                <input
                  type="text"
                  name="email"
                  className="form-control"
                  value={formik.values.email}
                  onChange={formik.handleChange}
                />
              </div>

              <div className="form-group">
                <label>Mobile</label>
                <input
                  type="text"
                  name="mobile"
                  className="form-control"
                  value={formik.values.mobile}
                  onChange={formik.handleChange}
                />
              </div>

              <div className="form-group">
                <label>Password</label>
                <input
                  type="password"
                  name="password"
                  className="form-control"
                  value={formik.values.password}
                  onChange={formik.handleChange}
                />
              </div>

              <input
                type="submit"
                value="Register"
                className="btn btn-primary btn-block"
              />
            </form>
            <br />
            <p className="text-center">
              Already Registered? <Link to="/login">Click Here</Link>
            </p>
          </div>
        </div>
        <div className="col-md-3"></div>
      </div>
    </div>
  );
};
export default RegisterPage;
