import  React, { useContext, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

// import { BsPersonCircle } from 'react-icons/bs'
import { BsCart, BsListOl } from 'react-icons/bs';
import { IoMdRestaurant } from 'react-icons/io';
import { FaRegNewspaper } from "react-icons/fa";

import { GiSushis } from 'react-icons/gi';

import { UserContext } from '../../App';


import Axios from "../../Axios";
import Cart from '../Cart/Cart';

export default function Header () {

    const [ showCart, setShowCart ] = useState(false);

    const handleShowCart = () => {
        setShowCart(true);
    }

    const handleCloseCart = () => {
        setShowCart(false);
    }

    const { logOut, User } = useContext(UserContext);

    return (
        <>
            <header>
                <Navbar>
                    <Container>
                        <Navbar.Brand href="/" className="d-flex align-items-center gap-1">
                            <GiSushis size={40}/>
                            Sakura
                        </Navbar.Brand>

                        <Nav className="ms-auto">
                            <Nav.Link href="/" className="d-flex align-items-center gap-1">
                                <IoMdRestaurant/>
                                Меню
                            </Nav.Link>
                            {
                                User?.role === 'ADMIN' &&
                                (<Nav.Link href="/reports" className="d-flex align-items-center gap-1">
                                    <FaRegNewspaper />
                                    Звітність
                                </Nav.Link>)
                            }
                            <Nav.Link href="/orders" className="d-flex align-items-center gap-1">
                                <BsListOl/>
                                Замовлення
                            </Nav.Link>
                            <Nav.Link className="d-flex align-items-center gap-1" onClick={ handleShowCart }>
                                <BsCart/>
                                Кошик
                            </Nav.Link>
                            <NavDropdown title={User?.name}>
                                <NavDropdown.Item href="/profile">Особистий кабінет</NavDropdown.Item>
                                <NavDropdown.Item onClick={logOut} href="/login">
                                    Вийти
                                </NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Container>
                </Navbar>
            </header>
            { showCart && (
                <Cart show={ showCart } onHide={ handleCloseCart }/>
            ) }
        </>
    );
}