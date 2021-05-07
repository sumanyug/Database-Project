import React, { Component } from "react";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import MyNavbar from "./navbar.component";

export default class Hello extends Component {
    constructor(props){
        super(props);

        this.state = {
            content: "",
            currentUser: AuthService.getCurrentUser()
        };
        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    componentDidMount() {
        UserService.getHello().then(
            response => {
                this.setState({
                    content: response.data
                });
            },
            error => {
                this.setState({
                    content:
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString()
                });
            }
        );
    }
    render() {
        return (
            <div className="container">
                <MyNavbar />
                <header className="jumbotron">
                    <h3>{this.state.content}</h3>
                </header>
            </div>
        )
    }
}