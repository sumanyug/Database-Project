import React, { Component } from "react";

import UserService from "../services/user.service";
import MyNavbar from "./navbar.component";

import { Link } from "react-router-dom";
import MovieCard from "./movie-card.component";

import CardColumns from 'react-bootstrap/CardColumns'

function ResultList(props) {
    const results = props.results;
    const listItems = results.map((movie) =>
        <li key={movie.id}>
            <MovieCard title={movie.name} rating={movie.rating} id={movie.id}/>
        </li>
    );
    return (
        <div>
            {results.length > 0 &&
                <h4>Results</h4>
            }
           <CardColumns>
                {listItems}
            </CardColumns>
        </div>
    );
}

export default class FriendSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            query: "",
            results: []
        }

        this.onChangeQuery = this.onChangeQuery.bind(this);
        this.handleQuery = this.handleQuery.bind(this);
    }
    onChangeQuery(event) {
        this.setState({
            query: event.target.value
        });
    }

    handleQuery (event) {
        event.preventDefault();
        let query = this.state.query;
        UserService.searchMovie(query).then(
            response => {
                console.log(response);
                let results = response.data;
                this.setState({results: results});
            },
            error => {
                console.log(error);
            }
        );
    } 
    render () {
        return (
            <div>
                < MyNavbar />
                Type in a movie name...
                <div className="center-text">
                    <form>
                        <input type="text" value={this.state.query} onChange={this.onChangeQuery} />
                        <button onClick={this.handleQuery}> Search </button>
                    </form>
                </div>
                <ResultList results={this.state.results} />
            </div>
        )
    }
}