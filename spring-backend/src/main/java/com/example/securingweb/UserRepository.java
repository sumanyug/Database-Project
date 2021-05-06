package com.example.securingweb;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepository extends Neo4jRepository <User, String>{

    @Query("Match (u: User) Where u.username = $username return u")
    List<User> findOneByUsername(String username);

    @Query("Match (u: User)-[:Request] ->(v:User) where u.username = $username return v")
    Set<User> findRequests(String username);

    @Query("Match (u: User)-[:Friend] ->(v:User) where u.username = $username return v")
    Set<User> findFriends(String username);

    @Query("Match (u: User)-[:Request] ->(v:User) where u.username = $username1 and v.username = $username2 return v")
    User checkRequest(String username1, String username2);
    @Query("Match (u: User)-[:Request] ->(v:User) where (u.username = $username1 and v.username = $username2) return v")
    User checkFriend(String username1, String username2);

    @Query("Match " +
            "(u: User), " +
            "(v:User) " +
            "where u.username = $username1 and " +
            "v.username = $username2 " +
            "Create (u)-[:Request] ->(v) return v")
    User addRequest(String username1, String username2);

    @Query("Create (u: User)-[:Friend] ->(v:User) where (u.username = $username1 and v.username = $username2) return v")
    User addFriend(String username1, String username2);

    @Query("Match (u:User)-[r:Request]-> (v:User) where u.username = $username1 and v.username = $username2 delete r")
    void deleteFriendRequest(String username1, String username2);

    @Query("Match (u:User)-[r:Friends]-(v:User) where u.username = $username1 and v.username = $username2 delete r")
    void deleteFriend(String username1, String username2);

}