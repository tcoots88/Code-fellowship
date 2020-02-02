package com.tcoots.CodeFellowship.controllers;


import com.tcoots.CodeFellowship.models.ApplicationUser;
import com.tcoots.CodeFellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;


//took some from demo from class
@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users/create")
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
        return new RedirectView("/myprofile");
    }

    @GetMapping("/users")
    public String getUsers(Model m, Principal p){
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        List<ApplicationUser> users = applicationUserRepository.findAll();
        System.out.println(Arrays.asList(users).toString());
        Set<ApplicationUser> friends = currentUser.getFriends();
        System.out.println(Arrays.asList(friends).toString());


        users.remove(currentUser);
        m.addAttribute("currentUser", currentUser);
        m.addAttribute("users",users);
        m.addAttribute("friends", friends);

        return "users";
    }


    @PostMapping("/users/{id}/friends")
    public RedirectView addFriend(@PathVariable Long id, Long friend, Principal p, Model m) {
        ApplicationUser currentUser = applicationUserRepository.findById(id).get();
        ApplicationUser newFriend = applicationUserRepository.findById(friend).get();
        if(!currentUser.username.equals(p.getName()) || !newFriend.username.equals(p.getName())) {
            throw new UsernameNotFoundException("");
        }

        currentUser.friends.add(newFriend);
        newFriend.friends.add(currentUser);
        applicationUserRepository.save(currentUser);
        applicationUserRepository.save(newFriend);

        return new RedirectView("/users/" + id);
    }

    @GetMapping("/feed")
    public String getFeed(Principal p, Model m) {
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);

        return "userInfo";
    }





    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false, defaultValue = "") String showMessage, Model m){
        m.addAttribute("ShowMessage", !showMessage.equals("") );
        return "login";
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(@PathVariable long id, Model m, Principal p){
        ApplicationUser user = applicationUserRepository.findById(id);
        if(user.getUsername().equals(p.getName())){
            m.addAttribute("user", user);
            return "userInfo";
        } else {
            throw new UsernameNotFoundException("Username not found");
        }

    }

    @GetMapping("/myprofile")
    public RedirectView getProfilePage(Principal p, Model m){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        Long id = user.id;
        m.addAttribute("user", user);
        return new RedirectView("/users/" + id);
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup";
    }



}
