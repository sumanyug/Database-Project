package com.example.securingweb;

import java.util.Optional;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface MovieRepository extends Neo4jRepository <Movie, Long>{
    Movie findByName(String name);
    Movie findByMovieid(Long movieid);

    // @Query("MATCH (n:Movie) WHERE id(n) = $movieid RETURN n")
    // Optional<Movie> findById(Long movieid);

    @Query("MATCH (n:Movie)<-[:WATCH_LIST]-(u:User) WHERE u.username = $username RETURN n")
    List<Movie> findAllMoviesForUsername(String username);

    @Query("MATCH (n:Movie)<-[r:WATCH_LIST]-(u:User) WHERE id(n) = $movieid and u.username = $username DELETE r")
    void deleteMovieRelationship(String username, Long movieid);

    @Query("MATCH (n:Movie), (u:User) WHERE id(n) = $movieid and u.username = $username CREATE (u)-[r:WATCH_LIST]->(n)")
    void addMovieRelationship(String username, Long movieid);

    @Query("MATCH (n:Movie), (u:User) WHERE id(n) = $movieid and u.username = $username CREATE (u)-[r:RATED {rating: $rating, timestamp: $timestamp}]->(n)")
    void addFeedbackRelationship(String username, Long movieid, double rating, long timestamp);

    @Query("MATCH (n:Movie) where id(n) = $movieid return n")
    Movie matchByMovieId(Long movieid);

    @Query("MATCH (u:User)-[r:RATED]->(n:Movie) WHERE id(n) = $movieid and u.username = $username set r.rating = $rating, r.timestamp = $timestamp")
    void updateFeedbackRelationship(String username, Long movieid, double rating, long timestamp);

    @Query("MATCH (m:Movie), (u:User) WHERE id(m) = $movieid AND u.username = $username RETURN exists((m)<-[:WATCH_LIST]-(u))")
    boolean checkInWatchlist(Long movieid, String username);

    @Query("MATCH (m:Movie), (u:User) WHERE id(m) = $movieid AND u.username = $username RETURN exists((m)<-[:RATED {rating: 5.0}]-(u))")
    boolean checkIfLiked(Long movieid, String username);

    @Query("MATCH (m:Movie), (u:User) WHERE id(m) = $movieid AND u.username = $username RETURN exists((m)<-[:RATED]-(u))")
    boolean checkIfRatingExists(Long movieid, String username);

    //@Query("CREATE (m:Movie {name: $name, rating: $rating})")
    //void addMovie(String name, double rating);

    @Query("MATCH (m:Movie {movieid: $movieid})<-[r:RATED]-(u:User {username: $username}) SET r.rating=$rating")
    void setRating(String username, Long movieid, double rating);

    @Query("MATCH (m:Movie {movieid: $movieid})<-[r:RATED]-(u:User {username: $username}) RETURN r.rating")
    double getRating(String username, Long movieid);

    @Query("MATCH (m:Movie)<-[r:RATED]-(u:User) WHERE id(m) = $movieid RETURN coalesce(avg(r.rating),0.0)")
    double getAvgRating(Long movieid);

    @Query("MATCH (m:Movie) where m.name =~ $movienameregex return m")
    List<Movie> getMovies(String movienameregex);

    //@Query("MATCH (n:Movie)<-[r:LIKED]-(u:User) WHERE n.name = $moviename and u.username = $username DELETE r")
    //void dislike(String username, String moviename);

    //@Query("MATCH (n:Movie), (u:User) WHERE n.name = $moviename and u.username = $username CREATE (u)-[r:LIKED]->(n)")
    //void like(String username, String moviename);

    @Query("MATCH (m:Movie) where ID(m)=$movieid SET m.movieid=$movieid")
    void setMovieID(Long movieid);
}