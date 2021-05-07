package com.example.securingweb;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;



@Node ("Movie")
public class Movie {

    @Id
    @GeneratedValue
    private long movieid;

    private String name;

    private double rating;

    private Movie(){}

    public Movie(String name){
        this.name = name;
        this.rating = 0.0;
    }
    
    /*@Override
    public Collection<GrantedAuthority> getAuthorities() { 
        return List.of(() -> "read"); 
    }

    @Override 
    public boolean isAccountNonExpired() { 
        return true; 
    } 
    @Override
    public boolean isAccountNonLocked() { 
        return true; 
    } 
    @Override public boolean isCredentialsNonExpired() { 
        return true; 
    } 
    @Override public boolean isEnabled() { 
    return true; 
    }*/

    public String getName(){
        return name;
    }

    public double getRating(){
        return rating;
    }

    public long getMovieID(){
        return movieid;
    }

    /*@Override
   public String getPassword(){
        return password;
    } 

    public void setPassword(String password){
        this.password = password;
    }*/


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        return name.equals(movie.name);
    }

    public String toString() {
        return "Movie{" +
                ", name='" + name +
                '}';
    }
    public Map<String, Object> toMap(){
        Map<String, Object> map= new HashMap<>();
        map.put("movieid", movieid);
        map.put("name", name);
        map.put("averageRating", rating);

        return map;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map= new HashMap<>();
        map.put("movieid", movieid);
        map.put("name", name);
        map.put("averageRating", rating);

        return map;
    }

}