import React, { Component } from "react";

import AdminService from "../services/admin.service";
import AuthService from "../services/auth.service";
import userService from "../services/user.service";

export default class Admin extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: AuthService.getCurrentUser(),
            title: "",
            rating: 0
        }
        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
        this.onTitleChange = this.onTitleChange.bind(this);
        this.onRatingChange = this.onRatingChange.bind(this);
        this.handleAddMovie = this.handleAddMovie.bind(this);
    }

    componentDidMount() {
        AdminService.checkAdmin().then(
            response => {
                if(response.data == "Error!"){
                    this.props.history.push("/hello");
                }
            },
            error => {
                console.log(error);
            }
        )
    }

    handleAddMovie(event) {
        event.preventDefault();
        let title = this.state.title;
        let rating = this.state.rating;
        AdminService.addMovie(title, rating).then(
            response => {
                console.log(response);
                window.location.reload();
            },
            error => {
                console.log(error);
            }
        );
    }

    onTitleChange(event) {
        this.setState({title: event.target.value});
    }

    onRatingChange(event) {
        this.setState({rating: event.target.value});
    }
    render () {
        return (
            <div>
                <div className="center-text">
                    <h2>Admin</h2>
                </div>
                <div className="center-text">
                    <h4> Add Movie</h4>
                    <form onSubmit={this.handleAddMovie}>
                        <div className="label-div">
                            <label>Movie Title</label>
                        </div>
                        <div className="input-div">
                            <input 
                                type="text"
                                name="title"
                                value={this.state.title}
                                onChange={this.onTitleChange}
                            />
                        </div>
                        <div className="label-div">
                            <label>Average Rating</label>
                        </div>
                        <div className="input-div">
                            <input type="number" step="0.1" min="1" max="5" value={this.state.rating} onChange={this.onRatingChange} />
                        </div>
                        <div className="submit-div">
                            <input type="submit" value="Submit" />
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}