package com.example.securingweb;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepository extends Neo4jRepository <User, String>{


    @Query("MATCH (n:User) RETURN n")
    List<User> findAllActiveUsers();



    @Query("Match (u: User) Where u.username = $username return u")
    List<User> findOneByUsername(String username);
    @Query("Match (u: User) Where u.username = $username detach delete u")
    List<User> deleteOneByUsername(String username);


    @Query("Match (u: User)-[:REQUEST] ->(v:User) where u.username = $username return v")
    Set<User> findRequests(String username);

    @Query("Match (u: User)-[:FRIENDS_WITH] -(v:User) where u.username = $username return v")
    Set<User> findFriends(String username);

    @Query("Match (u: User)-[:REQUEST] ->(v:User) where u.username = $username1 and v.username = $username2 return v")
    User checkRequest(String username1, String username2);

    @Query("Match (u: User)-[:FRIENDS_WITH] -(v:User) where (u.username = $username1 and v.username = $username2) return v")
    User checkFriend(String username1, String username2);

    @Query("Match " +
            "(u: User), " +
            "(v:User) " +
            "where u.username = $username1 and " +
            "v.username = $username2 " +
            "Create (u)-[:REQUEST] ->(v) return v")
    User addRequest(String username1, String username2);

    @Query("Match " +
            "(u: User), " +
            "(v:User) " +
            "where u.username = $username1 and " +
            "v.username = $username2 " +
            "Create (u)-[:FRIENDS_WITH] ->(v) return v")
    User addFriend(String username1, String username2);

    @Query("Match (u:User)-[r:REQUEST]-> (v:User) where u.username = $username1 and v.username = $username2 delete r")
    void deleteFriendRequest(String username1, String username2);

    @Query("Match (u:User)-[r:FRIENDS_WITH]-(v:User) where u.username = $username1 and v.username = $username2 delete r")
    void deleteFriend(String username1, String username2);

    @Query("Match (u:User)-[r:FRIENDS_WITH]-(v:User) where u.username = $username  " +
            "return v.username as username")
    List<String> findAllFriends(String username);

    @Query("Match (u:User) where u.username=~ $searchQuery  " +
            "return u")
    List<User> searchPerson(String searchQuery);

    @Query("Match (u:User)-[r:REQUEST]->(v:User) where v.username = $username  " +
            "return u.username as username")
    List<String> findAllRequests(String username);

    @Query("MATCH (n:User) where n.username = $username SET n.age = $age SET n.gender = $gender SET n.occupation = $occupation")
    void setProperties(String username, int age, String gender, String occupation);
}