import React, { Component } from "react";

import UserService from "../services/user.service";

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
            </div>
        )
    }
}