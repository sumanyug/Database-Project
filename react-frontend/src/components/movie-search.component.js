import React, { Component } from "react";

import UserService from "../services/user.service";
import MyNavbar from "./navbar.component";

import MovieCard from "./movie-card.component";

import CardColumns from 'react-bootstrap/CardColumns'

function MovieList(props) {
    const movies = props.movies;
    const listItems = movies.map((movie) =>
        <li key={movie.movieID}>
            <MovieCard title={movie.name} rating={movie.rating} id={movie.id}/>
        </li>
    )

    return (
        <div>
            <h4>Results</h4>
            <CardColumns>
                {listItems}
            </CardColumns>
        </div>
    )
}

export default class MovieSearch extends Component {
    constructor(props){
        super(props);
        this.state = {
            query: "",
            movies: []
        }
    }

    componentDidMount() {
        const { query } = this.props.location.state;
        console.log(query);
        UserService.searchMovie(query).then(
            response => {
                console.log(response);
                this.setState({ movies: response.data });
            },
            error => {
                console.log(error);
            }
        )
    }
    render() {
        let movies = this.state.movies;
        return (
            <div>
                < MyNavbar />
                < MovieList movies={movies} />
            </div>
        )
    }
}