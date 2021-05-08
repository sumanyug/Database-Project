import React, { Component } from "react";
import { Link } from "react-router-dom";

import Card from "react-bootstrap/Card"
export default class MovieCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: this.props.title,
            rating: this.props.rating,
            id: this.props.id
        }
    }

    render() {
        return (
            <Card>
                <Card.Body>
                    <Card.Title><Link to={"/movie/" + this.state.id}>{this.state.title}</Link></Card.Title>
                    <Card.Text>Rating: {this.state.rating}</Card.Text>
                </Card.Body>
            </Card>
        )
    }
}