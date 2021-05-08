import React, { Component } from "react";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";

import Card from "react-bootstrap/Card";

import MyNavbar from "./navbar.component";

import { Link } from "react-router-dom";

export default class Movie extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            currentUser: AuthService.getCurrentUser(),
            title: "",
            rating: 0,
            inWatchlist: false,
            isLiked: false,
            id: 0
        }
        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
        this.handleWatchlist = this.handleWatchlist.bind(this);
        this.watchNow = this.watchNow.bind(this);
    }

    componentDidMount() {
        const { match: { params }} = this.props;
        this.setState({id: parseInt(params.id)});
        console.log("FUCK");
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
                    id: id
                })
            },
            error => {
                console.log(error);
            }
        );
    }
    
    handleWatchlist() {
        let id = this.state.id;
        UserService.toggleWatchlist(id).then(
            response => {
                let inWatchlist = !this.state.inWatchlist;
                this.setState({inWatchlist: inWatchlist});
                this.props.history.push('/watchlist');
            },
            error => {
                console.log(error);
            }
        )
    }

    watchNow() {

    }

    render() {
        let title = this.state.title;
        let rating = this.state.rating;
        let inWatchlist = this.state.inWatchlist;
        let isLiked = this.state.isLiked;
        return (
            <div>
                < MyNavbar />
                <div className="center-card">
                < Card style={{ width: '30rem' }}>
                    <Card.Body>
                        <Card.Title>{title}</Card.Title>
                        <Card.Text>
                            Rating = {rating} <br />
                            {!inWatchlist && <button onClick={this.handleWatchlist}>Add To Watchlist</button>}
                            {inWatchlist && <button onClick={this.handleWatchlist}>Remove from Watchlist</button>}
                            <Link to={{ pathname: "/watch/" + this.state.id }}> Watch Now </Link>
                        </Card.Text>
                    </Card.Body>
                </Card>
                </div>
            </div>
        )
    }
}