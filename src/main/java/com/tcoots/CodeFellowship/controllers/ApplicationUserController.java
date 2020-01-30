package com.tcoots.CodeFellowship.controllers;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tcoots.CodeFellowship.models.ApplicationUser;
import com.tcoots.CodeFellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


//took some from demo from class
@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users")
    public RedirectView createNewApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirthString, String bio){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate dateOfBirth;
        dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);


        // make the user AND salt and hash the password
        // this does the salting and hashing for you
        ApplicationUser newUser = new ApplicationUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);

        // save the user to db
        applicationUserRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        // send them back home
        return new RedirectView("/ h ");
    }

//    TODO: login is returning the error below:
//    org.springframework.security.authentication.InternalAuthenticationServiceException: UserDetailsService returned null, which is an interface contract violation


    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false, defaultValue = "") String showMessage, Model m){
        m.addAttribute("ShowMessage", !showMessage.equals("") );
        return "login";
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(@PathVariable long id, Model m, Principal p){
        ApplicationUser user = ApplicationUserRepository.findById(id);
        if(user.getUsername().equals(p.getName())){
            m.addAttribute("user", user);
            return "userInfo";
        } else {
            throw new UsernameNotFoundException("Username not found");
        }

    }

    @GetMapping("/myprofile")
    public String getProfilePage(Principal p, Model m){
        ApplicationUser user = ApplicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        return "userInfo";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup";
    }



}
