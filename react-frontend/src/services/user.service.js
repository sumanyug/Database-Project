import axios from "axios";
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/user/';

class UserService {
    getHello() {
        return axios.get(API_URL + 'hello', { headers: authHeader() });
    }
    getStarted(gender, age, occupation) {
        return axios.post(API_URL + 'getstarted', {
            gender,
            age,
            occupation 
        },{ headers: authHeader() }   
        )
        .then(
        );
    }
    getPerson(username) {
        return axios.get(API_URL + 'friend' , 
        { 
            headers: authHeader(), 
            params: { username: username } 
        });
    }
    sendRequest(username){
        console.log(username);
        return axios.post(API_URL + 'addrequest?username=' + username,
        {},
         { headers: authHeader() }
        )
        .then(

        );
    }
    acceptRequest(username){
        return axios.post(API_URL + 'addfriend?username=' + username,
        {},
        { headers: authHeader() }
        )
        .then(

        );
    }
    rejectRequest(username){
        return axios.post(API_URL + 'removerequest?username=' + username,
        {},
        { headers: authHeader() }
        )
        .then(

        );
    }
    deleteRequest(username){
        return axios.post(API_URL + 'deleterequest?username=' + username,
        {},
        { headers: authHeader() }
        )
        .then(

        );
    }
    removeFriend(username){
        return axios.post(API_URL + 'deletefriend?username=' + username,
        {},
        { headers: authHeader() }
        )
        .then(

        );
    }
}

export default new UserService();