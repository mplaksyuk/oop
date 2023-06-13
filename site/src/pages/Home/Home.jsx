import React, { useState, useEffect, useContext } from 'react';
import Container from 'react-bootstrap/Container';

import Header from '../../components/Header/Header';
import { Product } from '../../components/Product/Product';
import Axios from '../../Axios';
import Button from 'react-bootstrap/esm/Button';
import ProductModal from '../../components/Product/ProductModal';

import { UserContext } from '../../App';

export default function Home () {
    const [ products = [], setProducts ] = useState();

    const [ show, setShow ] = useState(false);

    const { User } = useContext(UserContext);

    useEffect(() => {
        Axios.get("products").then(res => {
            const products = res.data;
            setProducts( products );
        })
    }, [])

    const handleShow = () => {
        setShow(true);
    }

    const handleClose = () => {
        setShow(false);
    }

    return (
        <>
            <Header />
            <Container>
                { User?.role === 'ADMIN' && (
                    <Container>
                        <Button variant="danger" onClick={handleShow}>Додати новий</Button>
                    </Container>
                ) }

                { show && (
                    <ProductModal show={show} onHide={handleClose}/>
                ) }
                <div className="row">
                    {
                        products.map(product =>
                            <div className="col-sm-6 col-md-4 col-lg-3 p-3">
                                <Product
                                    key={ product.id }
                                    className="h-100"
                                    productInfo={ product }
                                    />
                            </div>)
                    }
                </div>
            </Container>
        </>    
    )
}