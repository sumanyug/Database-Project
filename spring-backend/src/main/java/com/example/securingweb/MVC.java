package com.example.securingweb;

import java.util.*;


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


    private UserRepository userrep;
    private UserTransactions usert;


    @Autowired
    public MVC(UDetService det, PasswordEncoder passwordEncoder, UserRepository userrep, UserTransactions usert, MovieRepository movierepo){
        this.det = det;
        this.passwordEncoder = passwordEncoder;
        this.userrep = userrep;
        this.usert = usert;
        this.movierepo = movierepo;
    }


    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }


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

   
    @PostMapping("/getstarted")
    public String getStarted(@RequestBody Map <String, Object> data){
        System.out.println(data);
        return "started";
    }


    @GetMapping("/friend")
//    @Transactional
    public Map<String, Object> friendProfile(@RequestParam String username){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;


        Map<String, Object> response = new HashMap<>();
        List<User> userList = userrep.findOneByUsername(username);
        if(userList.size() == 0){
            response.put("error", "No such person exists");
        }
        else{

            if(userList.size() == 1){
                User personFound = userList.get(0);
                /*
                *  state -> 0 // should show add friend in front end
                *  state -> 1 // should show that friend request has been sent by current user
                *  state -> 2 // should show that friend request has to be accepted or rejected by the current user
                *  state -> 3 // both must be friends
                * */
                int state = 0;
                personFound.toString();

                state = usert.personStatus(primaryUser.getUsername(), personFound.getUsername());
                System.out.println("Status of rohan");
                System.out.println(state);
                response.putAll(personFound.toMap());
                response.put("state", state);
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;
    }

    @PostMapping("/addfriend")
    public Map<String, Object> addFriend(@RequestParam String username){
        return usert.addFriend(username);
    }
    @PostMapping("/addrequest")
    public Map<String, Object> addRequest(@RequestParam String username){
        System.out.println("REQUEST SENT TO");
        System.out.println(username);
        return usert.addRequest(username);

    }

    @PostMapping("/removerequest") // To delete a sent request
    public Map<String, Object> removeRequest(@RequestParam String username){
        return usert.rejectRequest(username);

    }

    @PostMapping("/deleterequest") // To delete a received request
    public Map<String, Object> deleteRequest(@RequestParam String username){
        System.out.println("DELETING REQUEST");
        return usert.deleteRequest(username);

    }

    @PostMapping("/deletefriend") // To delete a received request
    public Map<String, Object> deleteFriend(@RequestParam String username) {
        return usert.deleteFriend(username);
    }

    @GetMapping("/myfriends")
    public List<String> findAllFriends() {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        return userrep.findAllFriends(primaryUser.getUsername());
    }

    @GetMapping("/searchPeople")
    public List<Map<String, Object>> searchPeople(@RequestParam String searchQuery) {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        searchQuery = ".*" + searchQuery + ".*";
        List<User> users = userrep.searchPerson(searchQuery);
        List<Map<String, Object>> usersdetails = new ArrayList<>();
        Iterator<User> it = users.iterator();
        while(it.hasNext()){
            User currUser = it.next();
            if(currUser.getUsername().equals(primaryUser.getUsername())) continue;
            int state = usert.personStatus(primaryUser.getUsername(), currUser.getUsername());
            Map<String, Object> uDet = new HashMap<>();
            uDet.putAll(currUser.toMap());
            uDet.put("state", state);
            usersdetails.add(uDet);
        }

        return usersdetails;
    }



}