package com.example.securingweb;
import java.util.List;
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.provisioning.UserDetailsManager; 
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UDetService implements UserDetailsService{
    @Autowired
    private UserRepository userrepo;

    @Autowired
    private PasswordEncoder brcyptEncoder;

    /*@Autowired
    public UDetService(UserRepository userrepo){
        this.userrepo = userrepo;
    }*/

    private Optional<User> findUserByUserName(String username){
        List<User> users = userrepo.findOneByUsername(username);
        User u;
        if(users == null){
            u = null;
        }
        else{
            if(users.size() == 0){
                u = null;
            }
            else{
                u = users.get(0);
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
        user.setPassword(brcyptEncoder.encode(user.getPassword()));
        userrepo.save((User) user); 
    }
    
    public List<User> getAllUsers(){
        List<User> Users = userrepo.findAll();
        System.out.println("Size of the list : "+Users.size());
        for (User user: Users){
            System.out.println("From the internals : "+user.getUsername());
        }
        return userrepo.findAll();
    }

    public void setUserProperties(String username, int age, String gender, String occupation){
        userrepo.setProperties(username, age, gender, occupation);
    }
}