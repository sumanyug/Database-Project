import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import UserService from "../services/user.service";
import AuthService from "../services/auth.service";


const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required
            </div>
        )
    }
};

const vusername = value => {

};

const vpassword = value => {

};


export default class GetStarted extends Component {
    constructor(props) {
        super(props);

        this.state = {
            gender: 'male',
            age: 20,
            occupation: 'jobless',
            currentUser: AuthService.getCurrentUser()
        };
        this.handleGenderChange = this.handleGenderChange.bind(this);
        this.handleAgeChange = this.handleAgeChange.bind(this);
        this.handleOccupationChange = this.handleOccupationChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        if(!this.state.currentUser){
            this.props.history.push('/login');
        }
    }

    handleGenderChange(event) {
        this.setState({gender: event.target.value});
    }

    handleAgeChange (event) {
        this.setState({age: event.target.value});
    }

    handleOccupationChange (event) {
        this.setState({occupation: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();

        UserService.getStarted(
            this.state.gender,
            this.state.age,
            this.state.occupation
        ).then(
            response => {
                console.log(response);
                this.props.history.push('/hello')

            }
        );
    }

    render() {
        return (
            <div className="card card-container">
                <div className="center-text">
                    <h4>Let's get started with some personal details</h4>
                </div>
                <form onSubmit = {this.handleSubmit}>
                    <div className="label-div">
                    <label>
                        Age
                    </label>
                    </div>
                    <div className="input-div">
                        <input
                            name="age"
                            type="number"
                            min="1"
                            value={this.state.age}
                            onChange={this.handleAgeChange}
                        />
                    </div>
                    <div className="label-div">
                        <label>
                            Gender
                        </label>
                    </div>
                    <div className="input-div">
                        <select value={this.state.value} onChange={this.handleGenderChange}>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                        </select>
                    </div>
                    <div className="label-div">
                    <label>
                        Occupation
                    </label>
                    </div>
                    <div className="input-div">
                        <input
                            name="occupation"
                            type="text"
                            value={this.state.occupation}
                            onChange={this.handleOccupationChange}
                        />
                    </div>
                    <div className="submit-div">
                        <input type="submit" value="Submit" />
                    </div>
                </form>
            </div>
        )
    }
}