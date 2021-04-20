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
    @Autowired private UDetService userDetailsService; 
    @Autowired private PasswordEncoder passwordEncoder; 
    @Autowired private UserRepository userrepo; 

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