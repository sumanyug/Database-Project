package com.example.securingweb;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType; 
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
public class MVC{
    private UDetService det;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public MVC(UDetService det, PasswordEncoder passwordEncoder){
        this.det = det;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }
//    @GetMapping("/login")
//    public String login(HttpServletRequest request, HttpSession session) {
//        session.setAttribute(
//            "error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")
//        );
//        return "login";
//    }
//
//
//    @GetMapping("/username")
//    @ResponseBody
//    public Map<String, String> currentUserName() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentPrincipal = (User)authentication.getPrincipal();
//        Map<String,String> mp = new HashMap<>();
//        mp.put("username", currentPrincipal.getUsername());
//        mp.put("name", currentPrincipal.getName());
//        return mp;
//    }
//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }
//
//    @PostMapping(
//        value = "/register",
//        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
//        MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
//    )
//    public void addUser(@RequestParam Map<String, String> body) {
//        User user = new User(body.get("username"), passwordEncoder.encode(body.get("password")), body.get("name"));
//        det.createUser(user);
//    }
//    private String getErrorMessage(HttpServletRequest request, String key) {
//        Exception exception = (Exception) request.getSession().getAttribute(key);
//        String error = "Invalid username and password!";
//        return error;
//    }
}