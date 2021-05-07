import React, { Component } from "react";
import { NavLink } from "react-router-dom";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import MyNavbar from "./navbar.component";
import { Link } from "react-router-dom";

function FriendList(props) {
    const friends = props.friends;
    const listItems = friends.map((friend) =>
        <li key={friend}>
            <Link to={"person/"+friend}>{friend}</Link>
        </li>
    );
    return (
        <div>
            <h4>My Friends</h4>
            <ul>{listItems}</ul>
        </div>
    );
}

export default class Friends extends Component {
    constructor (props) {
        super(props);
        
        this.state = {
            currentUser: AuthService.getCurrentUser(),
            friends: []
        }
        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    componentDidMount() {
        UserService.getFriends().then(
            response => {
                console.log(response);
                this.setState({ friends: response.data });
                console.log(this.state.friends);
            },
            error => {
                console.log(error);
            }
        );
    }
    render () {
        return ( 
            <div>
                <MyNavbar />
                <div className="center-text">
                    <NavLink to="friend-search">Search for friends</NavLink>
                </div>
                <FriendList friends={this.state.friends} />
            </div>
        )
    }
}