package com.example.securingweb;

import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository <User, String>{
}