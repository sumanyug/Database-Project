package com.example.securingweb;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import java.util.Collection; 
import java.util.List;
import java.util.Objects;import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.GrantedAuthority; 



@Node ("User")
public class User  implements UserDetails{

    @Id
    private String username;

    private String password;

    private String name;


    private User(){}

    public User(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
    }
    
    @Override
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
    }

    public String getName(){
        return name;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof User)) return false;

        User user = (User) o;

        return username.equals(user.username) && password.equals(user.password);
    }

    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name=" + name +
                '}';
    }

}