import React, { Component } from "react";

import UserService from "../services/user.service";
import MovieCard from "./movie-card.component";

import CardDeck from 'react-bootstrap/CardDeck'

function RecList(props){
    let movies = props.movies;
    const listItems = movies.map((movie) =>
        < MovieCard title={movie.item} id={movie.uuid} rating={movie.score.totalScore} />
    );
   return (
        <div>
            <h3>Home Screen Reccomendations</h3>
            <CardDeck>{listItems}</CardDeck>
        </div>
    )
    
}

export default class HomeReco extends Component {
    constructor(props) {
        super(props);
        this.state = {
            movies : []
        }
    }

    componentDidMount() {
        UserService.getHomeReco().then(
            response => {
                console.log(response.data)
                this.setState({ movies: response.data });
            },
            error => {
                console.log(error);
            }
        )
    }

    render () {
        return(
            <div>
                <RecList movies={this.state.movies} />
            </div>
        )
    }
}