import React, { Component } from "react";

import UserService from "../services/user.service";

import { Link } from "react-router-dom";
import MovieCard from "./movie-card.component";
import BootCard from "./boot-card.component";

import CardColumns from "react-bootstrap/CardColumns";

function MovieList(props) {
    const movies = props.movies;
    const genre = props.genre;
    const listItems = movies.map((movie) =>
            <BootCard title={movie.name} rating={movie.rating} id={movie.id} key={movie.id} />
    )

    return (
        <div>
            <h4>Movies of Genre {genre}</h4>
            <CardColumns>
                {listItems}
            </CardColumns>
        </div>
    )
}

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
                < MovieList movies={this.state.movies} genre={this.state.genre}/> 
                <Link to="/bootstrap">Go back</Link>
            </div>
        )
    }
}