package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class UserTransactions {


    final private UserRepository userRepository;

    @Autowired
    public UserTransactions(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int personStatus(String username1, String username2) {
        Map<String, Object> response = new HashMap<>();
        int state = 0;
        if(userRepository.checkFriend(username1, username2)!= null){
            state = 3;
        }
        else {
            if (userRepository.checkRequest(username1, username2) != null) state = 2;
            else if(userRepository.checkRequest(username2, username1) != null) state = 1;
        }
        return state;

    }
    public Map<String, Object> addFriend(String username){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;

        Map<String, Object> response = new HashMap<>();
        List<User> userList = userRepository.findOneByUsername(username);
        if(userList.size() == 0){
            response.put("error", "No such person exists");
        }
        else{
            if(userList.size() == 1){
                User personFound = userList.get(0);
                personFound.AddFriend(primaryUser);
                primaryUser.AddFriend(personFound);
                userRepository.save(primaryUser);
                userRepository.save(personFound);
                response.putAll(personFound.toMap());
                response.put("added", "true");
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;

    }

}