import React, { useEffect, useContext } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Auth0Button } from '../../components/Auth0/Auth0Button'

import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

import './Auth.css';

import { UserContext } from '../../App';



export default function Login() {
    const navigate = useNavigate();

    const { register, handleSubmit } = useForm();

    const { logIn, User } = useContext(UserContext);

    useEffect(() => {
        if(User) {
            navigate('/');
        }
    }, [User])

    const onSubmit = (data, e) => {
        logIn(data);
    };

    const onError = (errors, e) => console.log(errors, e);

    return (
        <Form className="auth-form border rounded p-3" onSubmit={handleSubmit(onSubmit, onError)}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" placeholder="youremail@gmail.com" { ...register('email', { required : true }) } />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="********" { ...register('password', { required : true }) } />
            </Form.Group>
            <div className="d-flex justify-content-between align-items-center">
                <Button type="submit" variant="success">Sign in</Button>
                <Auth0Button />
                <a href="/register"> or Sign Up</a>
            </div>
        </Form>
    )
}