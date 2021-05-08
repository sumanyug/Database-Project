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
    searchFriend(query){
        return axios.get(API_URL + 'searchPeople?searchQuery=' + query, { headers: authHeader() });
    }
    searchMovie(query){
        return axios.get(API_URL + "searchmovie?searchQuery=" + query, { headers: authHeader() });
    }
    getWatchlist(){
        return axios.get(API_URL + 'watchlist', { headers: authHeader() });
    }
    getMovie(movie){
        console.log(movie);
        return axios.get(API_URL + 'movie?movieid=' + movie, { 
            headers: authHeader() 
        });
    }
    getFriends(){
        return axios.get(API_URL + 'myfriends', { headers: authHeader() });
    }
    getRequests(){
        return axios.get(API_URL + 'myrequests', { headers: authHeader() });
    }
    toggleWatchlist(movie) {
        return axios.post(API_URL + 'movie?movieid=' + movie, {}, { headers: authHeader() } );
    }
    giveFeedback(movieid, rating) {
        return axios.post(API_URL + 'feedback', { 
            movieid: movieid,
            rating: rating
        }, { headers: authHeader() }
        );
    }
    getBootstrap() {
        return axios.get(API_URL + "bootstrap", { headers: authHeader() });
    }
    getGenreMovies(genre_name){
        return axios.get(API_URL + "genremovies?genrename=" + genre_name, { headers: authHeader() });
    }
    bootstrapMovie(movie){
        return axios.post(API_URL + "bootstrap?movieid="+movie, {}, { headers: authHeader() });
    }
}

export default new UserService();