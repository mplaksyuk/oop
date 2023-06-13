import { useEffect, useState } from "react";
import './Quantity.css';

export default function Quantity({ startQuantity, onChange }) {
    const [quantity, setQuantity] = useState(startQuantity);

    const increment = () => {
        setQuantity(quantity + 1);
    }

    const decrement = () => {
        setQuantity(quantity > 1 ? quantity - 1 : quantity);
    }

    return (
        <div className="quantity-input">
          <button className="quantity-input__modifier quantity-input__modifier--left" onClick={decrement}>
            &mdash;
          </button>
          <input className="quantity-input__screen" type="text" onChange={ onChange(quantity) } value={quantity} readOnly/>
          <button className="quantity-input__modifier quantity-input__modifier--right" onClick={increment}>
            &#xff0b;
          </button>  
        </div>  
    )
}