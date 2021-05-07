package com.example.securingweb;

import java.util.Optional;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepository extends Neo4jRepository <User, String>{
    //List<User> findByUsername(String username);

    @Query("MATCH (n:User) RETURN n")
    List<User> findAllActiveUsers();
}

//public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("MATCH (n:User) RETURN n")
//    List<User> findAllActiveUsers();
//}