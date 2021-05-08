import './App.css';
import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";

import AuthService from "./services/auth.service";
import Login from "./components/login.component";
import Register from "./components/register.component";
import Hello from "./components/hello.component";
import GetStarted from "./components/get-started.component"
import Friends from "./components/friends.component";
import Person from "./components/person.component";
import Logout from "./components/logout.component";
import FriendSearch from "./components/friend-search.component";
import Watchlist from "./components/watchlist.component";
import Movie from "./components/movie.component";
import Admin from "./components/admin.component";
import MovieSearch from "./components/movie-search.component";
import WatchNow from "./components/watchnow.component";
import MovieBootstrap from "./components/movie-bootstrap.component";
import GenreBootstrap from "./components/genre-bootstrap.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    return (
      <div>
        <Switch>
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/hello" component={Hello} />
          <Route exact path="/getstarted" component={GetStarted} />
          <Route exact path="/friends" component={Friends} />
          <Route exact path="/logout" component={Logout} />
          <Route exact path="/friend-search" component={FriendSearch} />
          <Route exact path="/watchlist" component={Watchlist} />
          <Route exact path="/admin" component={Admin} />
          <Route exact path="/movie-search" component={MovieSearch} />
          <Route exact path="/bootstrap" component={GenreBootstrap} />
          <Route path="/bootstrap/:id" component={MovieBootstrap} />
          <Route path = "/person/:id" component={Person} />
          <Route path ="/movie/:id" component={Movie} />
          <Route path ="/watch/:id" component={WatchNow} />
        </Switch>
      </div>
    )
  }
}

export default App;
