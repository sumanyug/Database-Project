package com.example.securingweb;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user/")
public class MVC {
    private UDetService det;
    private PasswordEncoder passwordEncoder;
    private MovieRepository movierepo;

    @Autowired
    public MVC(UDetService det, PasswordEncoder passwordEncoder, MovieRepository movierepo) {
        this.det = det;
        this.passwordEncoder = passwordEncoder;
        this.movierepo = movierepo;
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    /*
     * @GetMapping("/hello") public List<Movie> helloWorld(){ //createMovie();
     * List<Movie> Movies = movierepo.findAll();
     * System.out.println("Size of the list : "+Movies.size()); for (Movie movie:
     * Movies){
     * System.out.println("From the internals : "+movie.getName()+", rating : "
     * +movie.getRating()); } //return "Hello Mofos!"; return Movies; }
     */

    @GetMapping("/watchlist")
    public List<Movie> watchList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        //System.out.println(currentPrincipal.getUsername());
        List<Movie> Movies = movierepo.findAllMoviesForUsername(currentPrincipal.getUsername());
        return Movies;
    }

    @GetMapping("/feedback")
    public String feedback(@RequestParam String moviename) {
        return moviename; 
    }

    @PostMapping(path = "/feedback", consumes = "application/json", produces = "application/json")
    public void addFeedbackRelationship(@RequestBody Movie movie){
        String moviename = movie.getName();
        double rating = movie.getRating();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        String username = currentPrincipal.getUsername();

        movierepo.addFeedbackRelationship(username, moviename, rating);
    }

    @GetMapping("/movie")
    public Map<String, Object> movie(@RequestParam String moviename){

        Map<String, Object> response = new HashMap<>();

        String name = moviename;
        Movie movie = movierepo.findByName(name);
        double avg_rating = movie.getRating();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        String username = currentPrincipal.getUsername();

        boolean in_watchlist = movierepo.checkInWatchlist(moviename, username);
        boolean is_liked = movierepo.checkIfLiked(moviename, username);

        response.put("moviename", name);
        response.put("avg_rating", avg_rating);
        response.put("in_watchlist", in_watchlist);
        response.put("is_liked", is_liked);

        return response;
    }

    // add/remove from watchlist, on the "movie" URL
    @PostMapping(path = "/movie", params="movie_name")
    public void editMovieRelationship(@RequestParam String movie_name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        boolean in_watchlist = movierepo.checkInWatchlist(movie_name, currentPrincipal.getUsername());

        if(in_watchlist)
            movierepo.deleteMovieRelationship(currentPrincipal.getUsername(), movie_name);
        else
            movierepo.addMovieRelationship(currentPrincipal.getUsername(), movie_name);
    }

    @PostMapping(path = "/movie", params="moviename")
    public void editLike(@RequestParam String moviename) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        boolean is_liked = movierepo.checkIfLiked(moviename, currentPrincipal.getUsername());

        if(is_liked)
            movierepo.dislike(currentPrincipal.getUsername(), moviename);
        else
            movierepo.like(currentPrincipal.getUsername(), moviename);
    }

    @GetMapping("/admin")
    public String adminPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        String username = currentPrincipal.getUsername();

        if(username.equals("admin"))
            return "Welcome Admin!";
        else
            return "Error!";
    }

    @PostMapping(path = "/admin")
    public void addMovie(@RequestBody Map<String, Object> body){
        String name = (String) body.get("name");
        double avg_rating = (double) body.get("avg_rating");
        movierepo.addMovie(name, avg_rating);
    }

    // @GetMapping("/")
    // public String index() {
    // return "index";
    // }
    // @GetMapping("/login")
    // public String login(HttpServletRequest request, HttpSession session) {
    // session.setAttribute(
    // "error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")
    // );
    // return "login";
    // }
    //
    //
    // @GetMapping("/username")
    // @ResponseBody
    // public Map<String, String> currentUserName() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // User currentPrincipal = (User)authentication.getPrincipal();
    // Map<String,String> mp = new HashMap<>();
    // mp.put("username", currentPrincipal.getUsername());
    // mp.put("name", currentPrincipal.getName());
    // return mp;
    // }
    // @GetMapping("/register")
    // public String register() {
    // return "register";
    // }
    //
    // @PostMapping(
    // value = "/register",
    // consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
    // MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    // )
    // public void addUser(@RequestParam Map<String, String> body) {
    // User user = new User(body.get("username"),
    // passwordEncoder.encode(body.get("password")), body.get("name"));
    // det.createUser(user);
    // }
    // private String getErrorMessage(HttpServletRequest request, String key) {
    // Exception exception = (Exception) request.getSession().getAttribute(key);
    // String error = "Invalid username and password!";
    // return error;
    // }
}