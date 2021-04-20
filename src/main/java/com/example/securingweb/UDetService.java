package com.example.securingweb;
import java.util.List;
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.provisioning.UserDetailsManager; 
import org.springframework.stereotype.Service;

@Service
public class UDetService implements UserDetailsService{
    @Autowired
    private UserRepository userrepo;

    private Optional<User> findUserByUserName(String username){
        User u = null;
        List<User> Users = userrepo.findAll();
        for (User user: Users){
            if(user.getUsername().equals(username)){
                u = user;
                break;
            }

        }
        Optional<User> opt = Optional.ofNullable(u);
        return opt;
        
    }

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        User user = findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return user;
    }

    public void createUser(User user) { 
        userrepo.save((User) user); 
    } 
}