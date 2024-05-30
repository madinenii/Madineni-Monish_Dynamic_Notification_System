
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import Product from '../../Components/Product';
// // import "../../Pages/Inventory/Display.css"

// const Display= () => {
//     const [products, setProducts] = useState([]);

//     useEffect(() => {
//         axios.get('http://localhost:8080/api/products/getall')
//             .then(response => {
//                 setProducts(response.data);
//             })
//             .catch(error => {
//                 console.error('There was an error fetching the products!', error);
//             });
//     }, []);

//     return (
//         <div className="container">
//             <div className="row">
//                 {products.map(product => (
//                     <Product key={product.id} data={product} />
//                 ))}
//             </div>
//         </div>
//     );
// }

// export default Display;



import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Product from '../../Components/Product';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Display.css';

const Display = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/products/getall')
            .then(response => {
                setProducts(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the products!', error);
            });
    }, []);

    return (
        <div className="container mt-5">
            <div className="row">
                {products.map(product => (
                    <div key={product.id} className="col-lg-4 col-md-6 col-sm-12 mb-4">
                        <Product data={product} />
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Display;
