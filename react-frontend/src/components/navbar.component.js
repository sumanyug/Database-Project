import React, { Component } from "react";
import { NavDropdown } from "react-bootstrap";

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav'
import { Form, FormControl, Button } from 'react-bootstrap';

import { Router, Link } from "react-router-dom";

export default class MyNavbar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            query: ""
        }
        this.onQueryChange = this.onQueryChange.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    onQueryChange(event) {
        this.setState({query: event.target.value});
    }

    handleSearch(event) {
        let query = this.state.query;
        this.props.history.push({
            pathname: "/movie-search",
            state: { query: query}
        });
    }
    render() {
        return (
            <Navbar bg="light" expand="lg">
            {/* <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand> */}
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                <Nav.Link href="/hello">Home</Nav.Link>
                <Nav.Link href="/friends">Friends</Nav.Link>
                <Nav.Link href="/watchlist">Watchlist</Nav.Link>
                <Nav.Link href="/logout">Logout</Nav.Link>
                {/* <NavDropdown title="Dropdown" id="basic-nav-dropdown"> */}
                    {/* <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item> */}
                    {/* <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item> */}
                    {/* <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item> */}
                    {/* <NavDropdown.Divider /> */}
                    {/* <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item> */}
                {/* </NavDropdown> */}
                </Nav>
                <Form inline>
                    <FormControl type="text" placeholder="Search" className="search-input" value={this.state.query} onChange={this.onQueryChange} />
                    <Link to={{ pathname: "/movie-search", state: {query: this.state.query} }}>Search</Link>
                </Form>
                {/* <Form inline> */}
                {/* <FormControl type="text" placeholder="Search" className="mr-sm-2" /> */}
                {/* <Button variant="outline-success">Search</Button> */}
                {/* </Form> */}
            </Navbar.Collapse>
            </Navbar>
        )
    }
}