package com.example.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
    
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http 
      .csrf().disable()
      .authorizeRequests().antMatchers("/register**")
      .permitAll() .anyRequest().authenticated() 
      .and() 
      .formLogin() .loginPage("/login")
      .permitAll() 
      .and() 
      .logout() .invalidateHttpSession(true) 
      .clearAuthentication(true) .permitAll(); 
	}

}