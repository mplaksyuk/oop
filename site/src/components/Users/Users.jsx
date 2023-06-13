import React from "react";

import Axios from "../../Axios";


export default class Users extends React.Component {
    state = {
        user : {}
    }

    componentDidMount() {
        Axios.get("users/11").then(res => {
            const user = res.data;
            this.setState({user})
        })
    }

    render() {
        let user = Object.entries(this.state.user)
        return (
            <ul>
            {
                user.map(([k, v]) => <li>{k} : {v}</li> )
            }
            </ul>
        )
    }
}