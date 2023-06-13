import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import './Product.css';
import { useEffect, useState } from 'react';


export function Product({productInfo}) {

    const [ product, setProduct ] = useState(null);

    
    useEffect(() => {
        setProduct(productInfo);
    }, [])

    const handleAddToCart = () => {
        let cartItems = [];
        if(localStorage.getItem('cart')) {
            cartItems = JSON.parse(localStorage.getItem('cart'));
        }

        const existingProduct = cartItems.find(cartItem => cartItem.id === product.id);
        if(existingProduct) {
            existingProduct.quantity++;
        } else {
            cartItems.push({...product, quantity : 1});
        }


        localStorage.setItem('cart', JSON.stringify(cartItems));
    }

    return (
        <Card>
            <Card.Img className="product-image" variant="top" src={ product?.image }/>
            <Card.Body>
                <Card.Title className="product-title">{ product?.name }</Card.Title>
                <Card.Text className="product-description overflow-hidden">
                    <span className="product-weight">{ product?.weight }г</span>-
                    { product?.ingredients.map(i => i.name ).join(', ') }
                </Card.Text>
                <div className="d-flex gap-4 align-items-center">
                    <Button variant="success" onClick={ handleAddToCart }> В кошик </Button>
                    <span className="product-price">{ product?.price } грн</span>
                </div>
            </Card.Body>
        </Card>
    )
}

export function ProductMin() {
    <Card>

    </Card>
}