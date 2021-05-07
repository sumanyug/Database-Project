package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
            System.out.println("HIIII");
            System.out.println(username1);
            System.out.println(username2);
            if (userRepository.checkRequest(username1, username2) != null) state = 1;
            else if(userRepository.checkRequest(username2, username1) != null) state = 2;
            System.out.println(state);
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
                boolean added = false;
                String username1 = primaryUser.getUsername();
                String username2 = personFound.getUsername();
                if (userRepository.checkRequest(username2, username1) != null &&
                userRepository.checkFriend(username1, username2) == null) {
                    primaryUser.AddFriend(personFound);
                    userRepository.deleteFriendRequest(username2, username1);
                    userRepository.save(primaryUser);
                    added = true;
                }
                response.putAll(personFound.toMap());
                response.put("added", added);
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;

    }

    public Map<String, Object> addRequest(String username){
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
                boolean added = false;
                String username1 = primaryUser.getUsername();
                String username2 = personFound.getUsername();
                if (userRepository.checkRequest(username2, username1) == null &&
                        userRepository.checkFriend(username1, username2) == null) {
                    primaryUser.AddFriendRequest(personFound);
                    userRepository.save(primaryUser);
                    added = true;
                }
                response.putAll(personFound.toMap());
                response.put("added", added);
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;

    }

    public Map<String, Object> deleteRequest(String username){
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
                boolean removed = false;
                String username1 = primaryUser.getUsername();
                String username2 = personFound.getUsername();
                userRepository.deleteFriendRequest(username1, username2);
                response.putAll(personFound.toMap());
                response.put("removed", true);
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;

    }

    public Map<String, Object> rejectRequest(String username){
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
                boolean removed = false;
                String username1 = primaryUser.getUsername();
                String username2 = personFound.getUsername();
                userRepository.deleteFriendRequest(username2, username1);
                response.putAll(personFound.toMap());
                response.put("removed", true);
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;
    }

    public Map<String, Object> deleteFriend(String username){
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
                boolean removed = true;
                String username1 = primaryUser.getUsername();
                String username2 = personFound.getUsername();
                userRepository.deleteFriend(username1, username2);
                response.putAll(personFound.toMap());
                response.put("removed", removed);
            }
            else {
                response.put("error", "Too many such person exists, big error in DB");
            }
        }

        return response;
    }


}