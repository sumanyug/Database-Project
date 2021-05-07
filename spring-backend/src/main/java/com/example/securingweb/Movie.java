package com.example.securingweb;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;
import java.util.Collection; 
import java.util.List;
import java.util.Objects; 
import org.springframework.security.core.GrantedAuthority; 



@Node ("Movie")
public class Movie {

    @Id
    private String movieid;

    private double rating;

    private String name;

    private Movie(){}

    public Movie(String movieid, double rating, String name){
        this.rating = rating;
        this.name = name;
        this.movieid = movieid;
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

        return name.equals(movie.name) && rating==movie.rating && movieid.equals(movie.movieid);
    }

    public String toString() {
        return "Movie{" +
                ", movieid='" + movieid + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }

}