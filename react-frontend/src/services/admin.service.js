import axios from "axios";
import authHeader from "./auth-header";
import authService from "./auth.service";

const API_URL = 'http://localhost:8080/api/user/';

class  AdminService {
    checkAdmin() {
        return axios.get(API_URL + "admin", { headers: authHeader() });
    }

    addMovie(title, rating) {
        return axios.post(API_URL + "admin", 
        {
            name: title,
            avg_rating: rating
        }, { headers: authHeader() }
        );
    }
}

export default new AdminService();