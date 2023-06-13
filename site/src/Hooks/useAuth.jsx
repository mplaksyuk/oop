import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";
import Axios from "../Axios";

export const useAuth = () => {
    const navigate = useNavigate();

    const [ User, setUser ] = useState(null);

    const { logout } = useAuth0();

    const logIn = (data) => {
        Axios.post('auth/login', data).then(res => {
            const {accessToken, refreshToken} = res.data;
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("refreshToken", refreshToken);
            
            Axios.get("users/current").then(res => {
                const user = res.data;
                setUser( user );
            })
        })
    }

    const auth0Login = (data) => {
        Axios.post('/auth/auth0/login', data).then(res => {
            const {accessToken, refreshToken} = res.data;
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("refreshToken", refreshToken);
            
            Axios.get("users/current").then(res => {
                const user = res.data;
                setUser( user );
            })
        })
    }

    const signUp = (data) => {
        Axios.post('auth/register', data).then(res => {
            const {accessToken, refreshToken} = res.data;
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("refreshToken", refreshToken);
            
            Axios.get("users/current").then(res => {
                const user = res.data;
                setUser( user );
            })
        })
    }

    const logOut = () => {
        localStorage.clear();
        logout({ logoutParams: { returnTo: window.location.origin } })
        setUser(null);
        navigate('/login');
    }

    useEffect(() => {
        if(localStorage.getItem('accessToken'))
            Axios.get("users/current").then(res => {
                const user = res.data;
                setUser( user );
            }).catch(logOut)
            
    }, [])

    return { logIn, signUp, logOut, auth0Login, User };
}