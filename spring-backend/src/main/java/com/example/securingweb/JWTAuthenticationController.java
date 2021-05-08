package com.example.securingweb;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@CrossOrigin(origins= "http://localhost:3000")
@RequestMapping("/api/auth/")
public class JWTAuthenticationController {

    final private AuthenticationManager authMan;
    final private JWTTokenUtil tokenUtil;
    final private UDetService udetservice;

    @Autowired
    public JWTAuthenticationController(AuthenticationManager authMan, JWTTokenUtil tokenUtil, UDetService udetservice){
        this.authMan = authMan;
        this.tokenUtil = tokenUtil;
        this.udetservice = udetservice;
    }

    @GetMapping("/register")
    public String registerResponse(){
        return "Please register";
    }

    @GetMapping("/authenticate")
    public String authenticateResponse(){
        return "Please authenticate";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = udetservice
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = tokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JWTResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        System.out.println(user.getAge() + "\n" + user.getGender() + "\n" + user.getOccupation() +"\n"+ user.getZipcode());
        if(user.getName()== null)
            user.setName("");
        if(user.getAge() == null)
            user.setAge(-1);
        if(user.getGender() == null)
            user.setGender("O");
        if(user.getOccupation() == null)
            user.setOccupation("None");
        if(user.getZipcode()== null)
            user.setZipcode(0);
        udetservice.createUser(user);
        return ResponseEntity.ok(user);
    }


    private void authenticate(String username, String password) throws Exception{
        try{
            authMan.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch(RuntimeException e){
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
        }
    }







}
