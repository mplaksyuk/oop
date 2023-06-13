import React, { useContext, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

import './Auth.css';
import { UserContext } from '../../App';

export default function Registration() {

    const navigate = useNavigate();

    const { register, handleSubmit } = useForm();

    const { signUp, User } = useContext(UserContext);

    const onSubmit = (data, e) => {
        signUp(data);
    };

    useEffect(() => {
        if(User) {
            navigate('/');
        }
    }, [User])

    const onError = (errors, e) => console.log(errors, e);

    return (
        <Form className="auth-form border rounded p-3" onSubmit={handleSubmit(onSubmit, onError)}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Name</Form.Label>
                <Form.Control type="text" placeholder="Your Name" { ...register('name', { required : true }) } />
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" placeholder="youremail@gmail.com" { ...register('email', { required : true }) } />
            </Form.Group>
            
            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="********" { ...register('password', { required : true }) } />
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicConfirmPassword">
                <Form.Label>Confirm password</Form.Label>
                <Form.Control type="password" placeholder="********" { ...register('confirmPassword', { required : true }) }/>
            </Form.Group>
            <div class="d-flex justify-content-between align-items-center">
                <Button type="submit" variant="success">Sign up</Button>
                <a href="/login"> or Sign In</a>
            </div>
        </Form>
    )
}