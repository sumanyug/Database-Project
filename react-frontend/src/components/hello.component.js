import React, { Component } from "react";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import HomeReco from "./home-reco.component";
import TrendingReco from "./trending-reco.component";
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
                {/* <HomeReco /> */}
                {/* <TrendingReco /> */}
            </div>
        )
    }
}