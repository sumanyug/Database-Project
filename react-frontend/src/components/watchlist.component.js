import React, { Component } from "react";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import MyNavbar from "./navbar.component";

export default class Watchlist extends Component {
    constructor(props) {
        super(props);
        this.state = {
            movies: [],
            currentUser: AuthService.getCurrentUser()
        }

        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    componentDidMount() {
        UserService.getWatchlist().then(
            response => {
                console.log(response);
            },
            error => {
                console.log(error);
            }
        )
    }

    render() {
        return (
            <div>
                < MyNavbar />
                Watchlist
            </div>
        )
    }
}