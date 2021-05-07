import React, { Component } from "react";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import MyNavbar from "./navbar.component";
import Button from 'react-bootstrap/Button'

function SendRequest(props) {
    return (
        <div className="center-text">
            <Button onClick={() => props.onClick()}>Add Friend</Button>
        </div>
    )
}

function Requested(props) {
    return (
        <div className="center-text">
            Friend Request Sent <br />
            <Button onClick={() => props.onDelete()}> Delete Request</Button>
        </div>
    )
}

function Received(props) {
    return (
        <div className="center-text">
            <Button onClick={() => props.onAccept()}> Accept Request</Button> <br />
            <Button onClick={() => props.onReject()}> Reject Request</Button>
        </div>
    )
}

function Friends(props) {
    return (
        <div className="center-text">
            Already Friends <br />
            <Button onClick={() => props.onRemove()}> Remove Friend</Button>
        </div>
    )
}

export default class Person extends Component {
    constructor (props) {
        super(props);

        this.state = {
            username: "",
            status: 5,
            currentUser: AuthService.getCurrentUser()
        };

        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    componentDidMount() {
        const { match: { params } } = this.props;

        UserService.getPerson(params.id).then(
            response => {
                console.log(response);
                this.setState(
                    {
                        username:response.data.username,
                        status: response.data.state
                    }
                );
                console.log(this.state);
            },
            error => {
                console.log(error);
            }
        );

    }

    handleAddFriend(){
        let username = this.state.username;
        UserService.sendRequest(username).then(
            response => {
                this.setState({status: 1});
                this.props.history.push('/person/'+username);
            },
            error => {
                console.log.error();
            }
        )
    }

    handleAcceptRequest(){
        let username = this.state.username;
        UserService.acceptRequest(username).then(
            response => {
                this.setState({status: 3});
                this.props.history.push('/person/'+username);
            },
            error => {
                console.log(error);
            }
        )
    }

    handleRejectRequest(){
        let username = this.state.username;
        UserService.rejectRequest(username).then(
            response => {
                this.setState({status: 0});
                this.props.history.push('/person/'+username);
            },
            error => {
                console.log(error);
            }
        )
    }
    handleDeleteRequest(){
        let username = this.state.username;
        UserService.deleteRequest(username).then(
            response => {
                this.setState({status: 0});
                this.props.history.push('/person/'+username);
            },
            error => {
                console.log(error);
            }
        )
    }
    handleRemoveFriend(){
        let username = this.state.username;
        UserService.removeFriend(username).then(
            response => {
                this.setState({status: 0});
                this.props.history.push('/person/'+username);
            },
            error => {
                console.log(error);
            }
        )
    }
    render () {
        let username = this.state.username;
        let status = this.state.status;
        let item;
        if (status === 0) {
            item = <SendRequest onClick={() => this.handleAddFriend()}/>;
        }
        else if (status === 1) {
            item = <Requested onDelete={() => this.handleDeleteRequest()}/>;
        }
        else if (status === 2) {
            item = <Received onAccept={() => this.handleAcceptRequest()} onReject={() => this.handleRejectRequest()}/>;
        }
        else if (status === 3) {
            item = <Friends onRemove={() => this.handleRemoveFriend()}/>;
        }
        return (
            <div>
                < MyNavbar />
                <div className="center-text">
                    <h2> Username: { username }</h2>
                </div>
                {item}
            </div>
        )
    }
}

