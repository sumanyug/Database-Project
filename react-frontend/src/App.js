import './App.css';
import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";

import AuthService from "./services/auth.service";
import Login from "./components/login.component";
import Register from "./components/register.component";
import Hello from "./components/hello.component";
import GetStarted from "./components/get-started.component"

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
        </Switch>
      </div>
    )
  }
}

export default App;
