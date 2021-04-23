package com.example.securingweb;

import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.authentication.AuthenticationProvider; 
// import org.springframework.security.authentication.BadCredentialsException; 
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.AuthenticationException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Component;

@Component
public class Auth implements AuthenticationProvider{
    private UDetService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userrepo;

    @Autowired
    public Auth(UDetService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userrepo){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userrepo = userrepo;
    }

    public Authentication authenticate(Authentication authentication) 
   throws AuthenticationException { 
      String username = authentication.getName(); 
      return authentication;
   } 

   @Override
   public boolean supports(Class<?> authentication) { 
      return true; 
   }


}