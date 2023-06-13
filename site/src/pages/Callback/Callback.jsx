import React, { useContext, useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../../App";

export default function Profile() {
    const { User, auth0Login } = useContext(UserContext);
    const { user, isAuthenticated, getAccessTokenSilently } = useAuth0();

    const navigate = useNavigate();

    // const [userMetadata, setUserMetadata] = useState(null);

    useEffect(() => {
        if(User) {
            navigate('/');
        }
    }, [User])

    // const { email, given_name, family_name } = user;

    // Axios.post('/auth/auth0/login', { email : email, first_name : given_name, last_name : family_name }).then(() => {
    //     navigate('/');
    // })

    return (
    isAuthenticated && user && (
        auth0Login({ email : user.email, name : user.given_name})
    )
    );
};