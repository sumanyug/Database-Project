import React, { Component } from "react";

import UserService from "../services/user.service";

import { Link } from "react-router-dom";

export default class MovieBootstrap extends Component {
    constructor(props) {
        super(props);
        this.state = {
            genre: "",
            movies: []
        }
    }

    componentDidMount() {
        const { match: { params }} = this.props;
        this.setState({ genre: params.id });
        UserService.getGenreMovies(params.id).then(
            response => {
                this.setState({ movies: response.data });
                console.log(this.state.movies);
            },
            error =>{
                console.log(error);
            }
        )
    }
    render() {
        return(
            <div>
                Hello
                <Link to="/bootstrap">Go back</Link>
            </div>
        )
    }
}