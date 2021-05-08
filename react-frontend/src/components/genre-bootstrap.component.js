import React, { Component } from "react";

import UserService from "../services/user.service";

import { Link } from "react-router-dom";

function GenreList(props) {
    const genres = props.genres;
    const listItems = genres.map((genre) =>
        <li key={genre.name}>
            <Link to={{ pathname:"/bootstrap/"+genre.name }}>{genre.name}</Link>
        </li>
    )

    return (
        <div>
           <ul>{listItems}</ul> 
        </div>
    )
}

export default class GenreBootstrap extends Component {
    constructor(props) {
        super(props);
        this.state = {
            genres: []
        }
    }

    componentDidMount() {
        UserService.getBootstrap().then(
            response => {
                this.setState({ genres: response.data });
                console.log(response);
                console.log(this.state.genres);
            },
            error => {
                console.log(error);
            }
        )
    }

    render() {
        return (
            <div>
                <div className="center-text">
                Choose a few genres that you like
                </div>
                < GenreList genres={this.state.genres} />
                <div className="center-text">
                    <Link to="/hello">Done</Link>
                </div>
            </div>
        )
    }
}