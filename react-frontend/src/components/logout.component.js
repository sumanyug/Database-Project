import React , { Component } from "react";
import Button from "react-bootstrap/Button";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service"; 

import MyNavbar from "./navbar.component";

export default class Logout extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: AuthService.getCurrentUser()            
        }
        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    handleLogout(){
        AuthService.logout();
        this.props.history.push("/login");
    }
    render() {
        return (
            <div>
            <MyNavbar />
            <div className="center-text">
                Click <Button onClick={() => this.handleLogout()}> here</Button> to logout.
            </div>
            </div>
        )
    }
}