import React, { Component } from "react";

import UserService from "../services/user.service";
import MyNavbar from "./navbar.component";
import { Link } from "react-router-dom";

function ResultList(props) {
    const results = props.results;
    const listItems = results.map((result) =>
        <li key={result.username}>
            <Link to={"/person/"+result.username}>{result.username}</Link>
        </li>
    );
    return (
        <div>
            {results.length > 0 &&
                <h4>Results</h4>
            }
            <ul>{listItems}</ul>
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
        UserService.searchFriend(query).then(
            response => {
                console.log(response);
                let results = response.data;
                let results2 = results.map((result) => result);
                this.setState({results: results});
                console.log(results2);
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
                Type in a username...
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