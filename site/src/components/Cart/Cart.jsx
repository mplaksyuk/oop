import Image from 'react-bootstrap/Image'
import Modal from 'react-bootstrap/Modal';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import { useEffect, useState } from "react";

import { GrClose } from 'react-icons/gr';
import Quantity from '../Quantity/Quantity';

import Axios from "../../Axios";

export default function Cart({ show, onHide}) {
    const [ products, setProducts ] = useState([]);
    const [ totalPrice, setTotalPrice ] = useState(0);
    const [ totalWeight, setTotalWeight ] = useState(0);

    useEffect(() => {
        const cartItems = JSON.parse(localStorage.getItem('cart'));

        if(cartItems) 
            setProducts(cartItems);

    }, [])

    const handleChangeProductQuantity = (product) => {
        const changedProduct = products.find(prod => prod.id === product.id)
        if(changedProduct) {
            changedProduct.quantity = product.quantity;
        }

        localStorage.setItem('cart', JSON.stringify(products));

        setTotalWeight(products.reduce((acc, product) => { return acc + product.weight * product.quantity }, 0));
        setTotalPrice(products.reduce((acc, product) => { return acc + product.price  * product.quantity }, 0));
    }

    const handleRemoveCartItem = (product) => {
        const newProducts = products.filter(prod => prod.id !== product.id);
        setProducts(newProducts);
        localStorage.setItem('cart', JSON.stringify(newProducts));

        setTotalWeight(newProducts.reduce((acc, product) => { return acc + product.weight * product.quantity }, 0));
        setTotalPrice(newProducts.reduce((acc, product) => { return acc + product.price  * product.quantity }, 0));
    }

    const emptyCart = () => {
        setProducts([]);
        localStorage.setItem('cart', JSON.stringify([]));
        setTotalWeight(0);
        setTotalPrice(0);
    }

    const createOrder = () => {
        const data = Array.from(products.reduce((acc, product) => {
            for (let i = 0; i < product.quantity; i++) {
                acc.push({ ...product });
            }
            return acc;
        }, [])).map(product => {
            const { quantity, ...rest } = product;
            return rest;
        })

        Axios.post('/orders/add', { products : data }).then(emptyCart)
    }
    

    return (
        <Modal show={ show } onHide={ onHide }>
            <Modal.Header closeButton>
                Кошик
            </Modal.Header>
            <Modal.Body className="px-4">
                {products.length > 0 && (
                    <>
                        { products.map(product => <CartItem 
                            key={ product.id } 
                            productInfo={ product } 
                            onChangeQuantity={ handleChangeProductQuantity } 
                            handleRemoveCartItem={ handleRemoveCartItem }/>
                        ) }
                        <Row>
                            <Col>Загальна Вага: {totalWeight}г</Col>
                            <Col>Загальна Ціна: {totalPrice}грн</Col>
                        </Row>
                    </>
                ) }
                { products.length === 0 && (
                    <h2 className="text-center">Кошик порожній</h2>
                ) }
            </Modal.Body>

            { products.length > 0 && (
                <Modal.Footer className="flex justify-content-between">
                    <Button variant="outline-danger" onClick={emptyCart}>Видалити все</Button>
                    <Button variant="success" onClick={ createOrder }>Замовити</Button>
                </Modal.Footer>
            ) }
        </Modal>
    )
}

const CartItem = ({productInfo, onChangeQuantity, handleRemoveCartItem}) => {
    const [ product ] = useState(productInfo);
    
    function handleChangeQuantity(q) {
        if(product) {
            product.quantity = q;
            onChangeQuantity(product);
        }
    }

    const handleRemove = () => {
        handleRemoveCartItem(product);
    }

    return (
        <Row className="p-2 mb-2 border align-items-center">
            <Col md={2}>
                <Image src={product?.image} width="100%"/>
            </Col>
            <Col md={10} className="d-flex justify-content-between">
                <div>
                    <h5>{ product?.name }</h5>
                    <Quantity startQuantity={product?.quantity} onChange={handleChangeQuantity}/>
                </div>
                <Button onClick={ handleRemove } className="bg-none" ><GrClose/></Button>
            </Col>
        </Row>
    )
}