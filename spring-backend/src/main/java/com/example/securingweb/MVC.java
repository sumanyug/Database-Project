package com.example.securingweb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
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
    private GenreRepository genrerepo;


    private UserRepository userrep;
    private UserTransactions usert;
    private MovieTransactions movieTransactions;


    @Autowired
    public MVC(UDetService det, PasswordEncoder passwordEncoder, UserRepository userrep, UserTransactions usert,
               MovieRepository movierepo, GenreRepository genrerepo, MovieTransactions movietransactions){
        this.det = det;
        this.passwordEncoder = passwordEncoder;
        this.userrep = userrep;
        this.usert = usert;
        this.movierepo = movierepo;
        this.genrerepo = genrerepo;
        this.movieTransactions = movietransactions;
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
    public long feedback(@RequestParam long movieid) {
        return movieid; 
    }

    @PostMapping(path = "/feedback", consumes = "application/json", produces = "application/json")
    public void addFeedbackRelationship(@RequestBody Map<String, Object> data){
        String s_movieid = String.valueOf(data.get("movieid"));
        String s_rating = String.valueOf(data.get("rating"));

        long movieid = Long.parseLong(s_movieid);
        double rating = Double.parseDouble(s_rating);

        Date date = new Date();
        long diff = date.getTime();

        movieTransactions.addRating(movieid, rating, diff);

    }

    @GetMapping("/movie")
    public Map<String, Object> movie(@RequestParam long movieid){
        
        Map<String, Object> response = new HashMap<>();
        // need to extract the rating by taking an average over all the users.
        // Long myid = new Long(movieid);
        // Optional<Movie> optional_movie = movierepo.findById(myid);
        Movie movie = movierepo.matchByMovieId(movieid);
        String name = movie.getName();
        double avg_rating = movierepo.getAvgRating(movieid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentPrincipal = (User) authentication.getPrincipal();
        String username = currentPrincipal.getUsername();

        boolean in_watchlist = movierepo.checkInWatchlist(movieid, username);
        boolean is_liked = movierepo.checkIfLiked(movieid, username);

        double user_rating=0.0;

        if(movierepo.checkIfRatingExists(movieid, username))
            user_rating = movierepo.getRating(username, movieid);

        response.put("id", movieid);
        response.put("moviename", name);
        response.put("avg_rating", avg_rating);
        response.put("user_rating", user_rating);
        response.put("in_watchlist", in_watchlist);
        response.put("is_liked", is_liked);

        return response;
    }

    // add/remove from watchlist, on the "movie" URL
    @PostMapping(path = "/movie", params="movieid")
    public void editMovieRelationship(@RequestParam long movieid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        boolean in_watchlist = movierepo.checkInWatchlist(movieid, currentPrincipal.getUsername());

        if(in_watchlist)
            movierepo.deleteMovieRelationship(currentPrincipal.getUsername(), movieid);
        else
            movierepo.addMovieRelationship(currentPrincipal.getUsername(), movieid);
    }

    /*@PostMapping(path = "/movie", params="moviename")
    public void editLike(@RequestParam String moviename) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        boolean is_liked = movierepo.checkIfLiked(moviename, currentPrincipal.getUsername());

        if(is_liked)
            movierepo.dislike(currentPrincipal.getUsername(), moviename);
        else
            movierepo.like(currentPrincipal.getUsername(), moviename);
    }*/

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
        
        String a_rating = String.valueOf(body.get("avg_rating"));

        double avg_rating = Double.parseDouble(a_rating);

        Movie movie = new Movie(name);
        movie = movierepo.save(movie);

        Long movieid = movie.getKey();
        System.out.println(movieid);

        movierepo.setMovieID(movieid);

        Date date = new Date();
        long diff = date.getTime();
        movierepo.addFeedbackRelationship("admin", movieid, avg_rating, diff); //adds a relationship from the admin to the movie 

        //movierepo.addMovie(name, avg_rating);
    }

   
    @GetMapping("/getstarted")
    public String getStarted() {
        return "Get Started!";
    }

    @PostMapping("/getstarted")
    public String getStarted(@RequestBody Map <String, Object> data){
        String s_age = String.valueOf(data.get("age"));
        String gender = (String) data.get("gender");
        String occupation = (String) data.get("occupation");

        int age = Integer.parseInt(s_age);

        //System.out.println("Age Dtype : "+data.get("age").getClass().getName());
        //System.out.println("Gender Dtype : "+data.get("gender").getClass().getName());
        //System.out.println("Occupation Dtype : "+data.get("occupation").getClass().getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        String username = currentPrincipal.getUsername();

        det.setUserProperties(username,age,gender,occupation);
        return "started";
    }

    @GetMapping("/bootstrap")
    public List<Genre> sendGenres() {
        Map<String, List<Movie> > response = new HashMap<>();

        //System.out.println("hi1");
        List<Genre> Genres = genrerepo.findAllGenres();
        //System.out.println("hi2");
        // List<String> GenreNames = new ArrayList<String>();
        // for(Genre genre: Genres){
            // String name = genre.getName();
            // GenreNames.add(name);
            // System.out.println(name);
            // List<Movie> movies = genrerepo.findTop5(name);
            // response.put(name, movies);
        // }
        return Genres;
    }

    @GetMapping("/genremovies")
    public List<Movie> getGenreMovies(@RequestParam String genrename){
        List<Long> movies_needed = genrerepo.findTop5(genrename);
        //System.out.println(movies_needed);

        Map<String, Long> id_name = new HashMap<>();
        List<Movie> movie_list = new ArrayList<>();
        for(Long movieid: movies_needed){
            Movie movie = movierepo.matchByMovieId(movieid);
            double avg_rating = movierepo.getAvgRating(movieid);
            movie.setRating(avg_rating);
            //System.out.println(movie.getName());
            // String moviename=movie.getName();
            // id_name.put(moviename, movieid);
            movie_list.add(movie);
        }

        //System.out.println(id_name);

        return movie_list;
    }

    // @PostMapping("/bootstrap")
    // public String receivemovies(@RequestBody Map <String, Object> data){
// 
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User currentPrincipal = (User) authentication.getPrincipal();
        // String username = currentPrincipal.getUsername();
// 
        // for (String name : data.keySet()){
            // System.out.println("key: " + name);
            // no use of key, just adding for the sake of it.
        // }
// 
        // for (Object name : data.values()){
            // for (String s_movieid : (List<String>) name){
                // System.out.println(movie);
                // long movieid = Long.parseLong(s_movieid);
                // if(movierepo.checkIfRatingExists(movieid, username))
                    // movierepo.setRating(username, movieid, 5.0);
                // else
                    // movierepo.addFeedbackRelationship(username, movieid, 5.0);
            // }
        // }
// 
        // return "started";
    // }

    @PostMapping("/bootstrap")
    public void addMovies (@RequestParam long movieid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        String username = currentPrincipal.getUsername();

        Date date = new Date();
        long diff = date.getTime();

        if(movierepo.checkIfRatingExists(movieid, username))
            movierepo.updateFeedbackRelationship(username, movieid, 5.0, diff);
        else
            movierepo.addFeedbackRelationship(username, movieid, 5.0, diff);

        System.out.println("Job Done");

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
        return usert.addRequest(username);

    }

    @PostMapping("/removerequest") // To delete a sent request
    public Map<String, Object> removeRequest(@RequestParam String username){
        return usert.rejectRequest(username);

    }

    @PostMapping("/deleterequest") // To delete a received request
    public Map<String, Object> deleteRequest(@RequestParam String username){
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

    @GetMapping("/myrequests")
    public List<String> findAllRequests() {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        return userrep.findAllRequests(primaryUser.getUsername());
    }

    @PostMapping("/deleteUser")
    public String deleteUser(){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        userrep.deleteOneByUsername(primaryUser.getUsername());
        return "Deleted";

    }




    @GetMapping("/searchmovie")
    public List<Movie> searchMovie(@RequestParam String searchQuery) {
        String finalSearchQuery = ".*" + searchQuery + ".*";
        System.out.println(finalSearchQuery);
        List<Movie> movies = movierepo.getMovies(finalSearchQuery);
        List<Movie> ls = new ArrayList<>();

        for(Movie movie : movies){
            long movieid = movie.getKey();
            double avg_rating = movierepo.getAvgRating(movieid);
            movie.setRating(avg_rating);
            ls.add(movie);
        }
        return ls;
        //return movies;
    }

    @GetMapping("/homereco")
    public String homeRecommendations() throws IOException, InterruptedException {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        HttpClient client = HttpClient.newHttpClient();
        String uri = "http://localhost:7474/graphaware/home/" + primaryUser.getUsername();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
         System.out.println(response.body());
        return response.body();
    }

    @GetMapping("/moviereco")
    public String movieRecommendations(@RequestParam int movieid) throws IOException, InterruptedException {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        HttpClient client = HttpClient.newHttpClient();
        String uri = "http://localhost:7474/graphaware/home/" + primaryUser.getUsername() + "/movie/"+movieid;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
         System.out.println(response.body());
        return response.body();
    }


    @GetMapping("/trendingreco")
    public String trendingMovieRecommendations() throws IOException, InterruptedException {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;
        HttpClient client = HttpClient.newHttpClient();
        String uri = "http://localhost:7474/graphaware/home/"+primaryUser.getUsername()+"/trending";
        System.out.println(uri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
         System.out.println(response.body());
        return response.body();
    }

    @GetMapping("/delete")
    public void deleteAccount(){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;

        String username = primaryUser.getUsername();

        userrep.delete(username);
    }

}