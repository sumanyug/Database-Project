package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public class GenreTransactions {


    final private UserRepository userRepository;

    @Autowired
    public GenreTransactions(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}