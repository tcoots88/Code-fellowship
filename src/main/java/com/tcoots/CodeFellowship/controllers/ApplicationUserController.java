package com.tcoots.CodeFellowship.controllers;


import com.tcoots.CodeFellowship.models.ApplicationUser;
import com.tcoots.CodeFellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;




//took some from demo from class
@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public RedirectView createNewApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio){
        System.out.println("You are adding a user");
        // make the user AND salt and hash the password
        // this does the salting and hashing for you
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);

        // save the user to db
        applicationUserRepository.save(newUser);

        // send them back home
        return new RedirectView("/");
    }

//    TODO: login is returning the error below:
//    org.springframework.security.authentication.InternalAuthenticationServiceException: UserDetailsService returned null, which is an interface contract violation


    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }
    @GetMapping("/users/{id}")
    public String getUserInfo(){
        return "userInfo";
    }

}
