package com.internship.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public String user(Model model, @AuthenticationPrincipal UserDetails user){

        model.addAttribute("username", user.getUsername());
        return "user";
    }

}
