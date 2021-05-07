import React, { Component } from "react";
import AuthService from "../services/auth.service";

export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: AuthService.getCurrentUser()
        };
    }

    
}