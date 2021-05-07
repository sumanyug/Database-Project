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

    @Id
    private int id;

    private String name;

    private Genre(){}

    public Genre(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
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