package com.example.securingweb;

import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepository extends Neo4jRepository <User, String>{

    @Query("Match (u: User) Where u.username = $username return u")
    List<User> findOneByUsername(String username);

    @Query("Match (u: User)-[:Request] ->(v:User) where u.username = $username return v")
    List<User> findRequests(String username);

    @Query("Match (u: User)-[:Friend] ->(v:User) where u.username = $username return v")
    List<User> findFriends(String username);

    @Query("Match (u: User)-[:Request] ->(v:User) where u.username = $username1 and v.username = $username2 return v")
    User checkRequest(String username1, String username2);
    @Query("Match (u: User)-[:Request] ->(v:User) where (u.username = $username1 and v.username = $username2) return v")
    User checkFriend(String username1, String username2);









}