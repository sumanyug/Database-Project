import React, { Component } from "react";
import { useParams } from "react-router-dom";

import UserService from "../services/user.service";
import MyNavbar from "./navbar.component";

function SendRequest(props) {
    return (
        <div>
            Friend Request
        </div>
    )
}

function Requested(props) {
    return (
        <div className="center-text">
            Friend Request Sent
        </div>
    )
}

function Received(props) {
    return (
        <div>
            Friend request received
        </div>
    )
}

function Friends(props) {
    return (
        <div>
            Already Friends
        </div>
    )
}

export default class Person extends Component {
    constructor (props) {
        super(props);

        this.state = {
            username: "",
            status: 0
        };
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
    render () {
        let username = this.state.username;
        let status = this.state.status;
        let item;
        if (status==0) {
            item = <SendRequest />;
        }
        else if (status == 1) {
            item = <Requested />;
        }
        else if (status == 2) {
            item = <Received />;
        }
        else {
            item = <Friends />;
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

