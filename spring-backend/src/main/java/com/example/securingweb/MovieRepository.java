package com.example.securingweb;

import java.util.Optional;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface MovieRepository extends Neo4jRepository <Movie, Long>{
    Movie findByName(String name);
    Movie findByMovieid(long movieid);

    @Query("MATCH (n:Movie)<-[:WATCH_LIST]-(u:User) WHERE u.username = $username RETURN n")
    List<Movie> findAllMoviesForUsername(String username);

    @Query("MATCH (n:Movie)<-[r:WATCH_LIST]-(u:User) WHERE n.movieid = $movieid and u.username = $username DELETE r")
    void deleteMovieRelationship(String username, long movieid);

    @Query("MATCH (n:Movie), (u:User) WHERE n.movieid = $movieid and u.username = $username CREATE (u)-[r:WATCH_LIST]->(n)")
    void addMovieRelationship(String username, long movieid);

    @Query("MATCH (n:Movie), (u:User) WHERE n.movieid = $movieid and u.username = $username CREATE (u)-[r:HAS_RATED {rating: $rating}]->(n)")
    void addFeedbackRelationship(String username, long movieid, double rating);

    @Query("MATCH (m:Movie), (u:User) WHERE m.movieid = $movieid AND u.username = $username RETURN exists((m)<-[:WATCH_LIST]-(u))")
    boolean checkInWatchlist(long movieid, String username);

    @Query("MATCH (m:Movie), (u:User) WHERE m.movieid = $movieid AND u.username = $username RETURN exists((m)<-[:HAS_RATED {rating: 5.0}]-(u))")
    boolean checkIfLiked(long movieid, String username);

    @Query("MATCH (m:Movie), (u:User) WHERE m.movieid = $movieid AND u.username = $username RETURN exists((m)<-[:HAS_RATED]-(u))")
    boolean checkIfRatingExists(long movieid, String username);

    //@Query("CREATE (m:Movie {name: $name, rating: $rating})")
    //void addMovie(String name, double rating);

    @Query("MATCH (m:Movie {movieid: $movieid})<-[r:HAS_RATED]-(u:User {username: $username}) SET r.rating=$rating")
    void setRating(String username, long movieid, double rating);

    @Query("MATCH (m:Movie {movieid: $movieid})<-[r:HAS_RATED]-(u:User) RETURN coalesce(avg(r.rating),0.0)")
    double getAvgRating(long movieid);

    @Query("MATCH (m:Movie) where m.name =~ $movienameregex return m")
    List<Movie> getMovies(String movienameregex);

    //@Query("MATCH (n:Movie)<-[r:LIKED]-(u:User) WHERE n.name = $moviename and u.username = $username DELETE r")
    //void dislike(String username, String moviename);

    //@Query("MATCH (n:Movie), (u:User) WHERE n.name = $moviename and u.username = $username CREATE (u)-[r:LIKED]->(n)")
    //void like(String username, String moviename);
}