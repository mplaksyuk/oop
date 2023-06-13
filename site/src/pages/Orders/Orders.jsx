import React, { useState, useEffect, useContext } from 'react';
import Container from 'react-bootstrap/Container';

import Header from '../../components/Header/Header';
import Axios from '../../Axios';
import OrdersTable from '../../components/Orders/OrdersTable';

import { UserContext } from '../../App';

export default function Orders () {
    const [ orders, setOrders ] = useState([]);

    const { User } = useContext(UserContext);

    useEffect(() => {
        Axios.get("/orders").then(res => {
            const orders = res.data;
            setOrders( orders );
        })
    }, [])

    return (
        <>
            <Header />
            <Container>
                <OrdersTable orders={ orders } user={ User }/>
            </Container>
        </>    
    )
}