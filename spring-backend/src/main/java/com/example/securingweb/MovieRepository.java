package com.example.securingweb;

import java.util.Optional;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface MovieRepository extends Neo4jRepository <Movie, String>{
    Movie findByName(String name);

    @Query("MATCH (n:Movie)<-[:WATCH_LIST]-(u:User) WHERE u.username = $username RETURN n")
    List<Movie> findAllMoviesForUsername(String username);

    @Query("MATCH (n:Movie)<-[r:WATCH_LIST]-(u:User) WHERE n.name = $moviename and u.username = $username DELETE r")
    void deleteMovieRelationship(String username, String moviename);

    @Query("MATCH (n:Movie), (u:User) WHERE n.name = $moviename and u.username = $username CREATE (u)-[r:WATCH_LIST]->(n)")
    void addMovieRelationship(String username, String moviename);

    @Query("MATCH (n:Movie), (u:User) WHERE n.name = $moviename and u.username = $username CREATE (u)-[r:HAS_RATED {rating: $rating}]->(n)")
    void addFeedbackRelationship(String username, String moviename, double rating);

    @Query("MATCH (m:Movie), (u:User) WHERE m.name = $moviename AND u.username = $username RETURN exists((m)<-[:WATCH_LIST]-(u))")
    boolean checkInWatchlist(String moviename, String username);

    @Query("MATCH (m:Movie), (u:User) WHERE m.name = $moviename AND u.username = $username RETURN exists((m)<-[r:HAS_RATED]-(u) WHERE r.rating=5.0)")
    boolean checkIfLiked(String moviename, String username);

    @Query("MATCH (m:Movie), (u:User) WHERE m.name = $moviename AND u.username = $username RETURN exists((m)<-[:HAS_RATED]-(u))")
    boolean checkIfRatingExists(String moviename, String username);

    @Query("CREATE (m:Movie {name: $name, rating: $rating})")
    void addMovie(String name, double rating);

    @Query("MATCH (m:Movie {name: $name})<-[r:HAS_RATED]-(u:User {username: $username}) SET r.rating=$rating")
    void setRating(String username, String name, double rating);

    //@Query("MATCH (n:Movie)<-[r:LIKED]-(u:User) WHERE n.name = $moviename and u.username = $username DELETE r")
    //void dislike(String username, String moviename);

    //@Query("MATCH (n:Movie), (u:User) WHERE n.name = $moviename and u.username = $username CREATE (u)-[r:LIKED]->(n)")
    //void like(String username, String moviename);
}