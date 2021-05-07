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
        },{
        headers: authHeader()
            }   
        )
        .then(
        );
    }
}

export default new UserService();