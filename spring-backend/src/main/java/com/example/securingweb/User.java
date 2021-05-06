package com.example.securingweb;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.*;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority; 



@Node ("User")
public class User  implements UserDetails{

    @Id
    private String username;

    private String password;

    private String name;

    @Relationship(type="Request", direction= Relationship.Direction.OUTGOING)
    Set<User> requests;

    @Relationship(type="Friend", direction= Relationship.Direction.OUTGOING)
    Set<User> friends;



    public User(String username, String password, String name, Set<User> requests, Set<User> friends){
        this.username = username;
        if( this.username == null) this.username = "";
        this.password = password;
        if( this.password == null) this.password = "";
        this.name = name;
        if( this.name == null) this.name = "";
        this.requests = requests;
        if( this.requests == null) this.requests = new HashSet<>();
        this.friends = friends;
        if( this.friends == null) this.friends = new HashSet<>();
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
    public Map<String,String> toMap(){
        Map<String, String> userToString  = new HashMap<>();
        userToString.put("username", username);
        userToString.put("name", name);
        return userToString;
    }

    public void AddFriendRequest(User frequest){
        requests.add(frequest);

    }

    public void AddFriend(User frequest){
        friends.add(frequest);

    }

    public Set<User> getFriends(){
        return friends;
    }

    public Set<User> getRequests(){
        return requests;
    }

    public void removeRequest(User user){
        requests.remove(user);
    }
    public void removeFriend(User user){
        friends.remove(user);
    }





}