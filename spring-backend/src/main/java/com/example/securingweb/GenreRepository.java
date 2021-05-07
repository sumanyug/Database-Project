package com.example.securingweb;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.securingweb.Genre;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface GenreRepository extends Neo4jRepository <Genre, Integer>{
    @Query("MATCH (g:Genre) RETURN g")
    List<Genre> findAllGenres();

    @Query("MATCH (g:Genre)<-[:HAS_GENRE]-(m:Movie) WHERE g.name = $genre RETURN m ORDER BY m.rating DESC LIMIT 5")
    List<Movie> findTop5(String genre);
}