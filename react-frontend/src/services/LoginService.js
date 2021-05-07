import axios from 'axios';

const LOGIN_API_BASE_URL = 'http://localhost:8080/register'

class LoginService{
    getData(){
        return axios.get(LOGIN_API_BASE_URL);
    }
}

export default new LoginService()