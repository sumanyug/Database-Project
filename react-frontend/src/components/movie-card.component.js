import React, { Component } from "react";

import Card from "react-bootstrap/Card"
export default class MovieCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: this.props.title,
            rating: this.props.rating
        }
    }

    render() {
        return (
            <Card>
                <Card.Body>
                    <Card.Title>{this.state.title}</Card.Title>
                    <Card.Text>Rating: {this.state.rating}</Card.Text>
                </Card.Body>
            </Card>
        )
    }
}