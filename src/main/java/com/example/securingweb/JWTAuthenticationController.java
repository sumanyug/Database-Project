package com.example.securingweb;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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
        udetservice.createUser(user);
        return ResponseEntity.ok(user);
    }


    private void authenticate(String username, String password) throws Exception{
        try{
            authMan.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }







}
