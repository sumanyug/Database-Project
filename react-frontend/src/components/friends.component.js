import React, { Component } from "react";
import { NavLink } from "react-router-dom";

import UserService from "../services/user.service";
import MyNavbar from "./navbar.component";

export default class Friends extends Component {
    constructor (props) {
        super(props);
    }
    render () {
        return ( 
            <div>
                <MyNavbar />
                <div className="center-text">
                    <NavLink to="friend-search">Search for friends</NavLink>
                </div>
            </div>
        )
    }
}