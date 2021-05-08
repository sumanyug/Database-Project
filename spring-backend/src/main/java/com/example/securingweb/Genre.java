package com.example.securingweb;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;



@Node ("Genre")
public class Genre{

    @Relationship(type="HAS_GENRE", direction=Relationship.Direction.INCOMING)
    Set<Movie> movies;

    @Id
    private Integer id;

    private String name;

    private Genre(){}

    public Genre(Integer id, String name, Set<Movie> movies){
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public String getName(){
        return name;
    }

    public Set<Movie> getMovies(){
        return movies;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        return id==genre.id && name.equals(genre.name);
    }

    public String toString() {
        return "Genre{" +
                ", id='" + id + '\'' +
                ", name=" + name +
                '}';
    }
    
}