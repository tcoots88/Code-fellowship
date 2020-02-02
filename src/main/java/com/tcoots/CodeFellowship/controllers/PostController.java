package com.tcoots.CodeFellowship.controllers;


import com.tcoots.CodeFellowship.models.ApplicationUser;
import com.tcoots.CodeFellowship.models.ApplicationUserRepository;
import com.tcoots.CodeFellowship.models.Post;
import com.tcoots.CodeFellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;


@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("posts/add")
    public String getPostCreator(){
        return "createPost";
    }

    @PostMapping("/posts")
    public RedirectView addPost(String body, Principal p){
        Post newPost = new Post(body);
        ApplicationUser me = applicationUserRepository.findByUsername(p.getName());
        newPost.creator = me;
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }



}
