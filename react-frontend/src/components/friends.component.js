import React, { Component } from "react";
import { NavLink } from "react-router-dom";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import MyNavbar from "./navbar.component";
import { Link } from "react-router-dom";

function PersonList(props) {
    const friends = props.friends;
    const listItems = friends.map((friend) =>
        <li key={friend}>
            <Link to={"person/"+friend}>{friend}</Link>
        </li>
    );
    return (
        <div>
            <h4>{props.title}</h4>
            <ul>{listItems}</ul>
        </div>
    );
}

export default class Friends extends Component {
    constructor (props) {
        super(props);
        
        this.state = {
            currentUser: AuthService.getCurrentUser(),
            friends: [],
            requests: []
        }
        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    componentDidMount() {
        UserService.getFriends().then(
            response => {
                this.setState({ friends: response.data });
            },
            error => {
                console.log(error);
            }
        );
        UserService.getRequests().then(
            response => {
                this.setState({ requests: response.data });
            },
            error => {
                console.log(error);
            }
        )
    }
    render () {
        return ( 
            <div>
                <MyNavbar />
                <div className="center-text">
                    <NavLink to="friend-search">Search for friends</NavLink>
                </div>
                <PersonList friends={this.state.friends} title="My Friends" />
                <PersonList friends={this.state.requests} title="Pending Requests" />
            </div>
        )
    }
}