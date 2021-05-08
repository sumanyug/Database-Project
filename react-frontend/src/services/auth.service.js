import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/auth/"


class AuthService {
    login(username, password) {

        return axios
            .post(API_URL + "authenticate", {
                username,
                password
            })
            .then(response => {
                if (response.data.token) {
                    // console.log(response);
                    localStorage.setItem("user", JSON.stringify(response.data));
                }
                
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    delete(){
        return axios.post(API_URL + "deleteAccount", { headers: authHeader() });
    }
    register(username, password) {
        return axios.post(API_URL + "register", {
            username,
            password
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();