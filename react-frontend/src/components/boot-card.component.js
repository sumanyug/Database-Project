import React, { Component } from "react";
import { Link } from "react-router-dom";

import Card from "react-bootstrap/Card"

import UserService from "../services/user.service";

export default class BootCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: this.props.title,
            rating: this.props.rating,
            id: this.props.id
        }
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(event){
        event.preventDefault();
        UserService.bootstrapMovie(this.state.id);
    }
    render() {
        return (
            <Card>
                <Card.Body>
                    <Card.Title><button onClick={this.handleClick}>{this.state.title}</button></Card.Title>
                    <Card.Text>Rating: {this.state.rating}</Card.Text>
                </Card.Body>
            </Card>
        )
    }
}