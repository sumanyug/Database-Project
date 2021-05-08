import React, { Component } from "react";

import UserService from "../services/user.service";
import MyNavbar from "./navbar.component";

export default class WatchNow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: 0,
            title: "",
            inWatchlist: false,
            isLiked: false
        }

        this.onChangeRating = this.onChangeRating.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        const { match: { params }} = this.props;
        UserService.getMovie(parseInt(params.id)).then(
            response => {
                console.log(response);
                let movie = response.data;
                let rating = movie.avg_rating;
                let inWatchlist = movie.in_watchlist
                let isLiked = movie.is_liked;
                let title = movie.moviename; 
                let id = movie.id;
                this.setState({
                    title: title,
                    rating: rating,
                    inWatchlist: inWatchlist,
                    isLiked: isLiked,
                    rating: 1,
                    id: id
                })
            },
            error => {
                console.log(error);
            }
        );
        
    }

    onChangeRating(event) {
        this.setState({ rating: event.target.value });
    }

    handleSubmit(event) {
        event.preventDefault();
        let id = this.state.id;
        let rating = this.state.rating;
        UserService.giveFeedback(id, rating).then(
            response => {
                console.log(response);
            },
            error => {
                console.log(error);
            }
        )
        this.props.history.push("/hello");
    }
    render() {
        let title = this.state.title;
        let rating = this.state.rating;
        return (
            <div>
                < MyNavbar />
                <div className="center-text">
                    <h3>Thanks for Watching the Movie {title} </h3><br />
                    Please give it a rating below <br />
                    <form onSubmit={this.handleSubmit}>
                        <div className="input-div">
                            <input type="number" min="1" max="5" step="1" value={rating} onChange={this.onChangeRating} />
                        </div>
                        <div classname="submit-div">
                            <input type="submit" value="Submit" />
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}