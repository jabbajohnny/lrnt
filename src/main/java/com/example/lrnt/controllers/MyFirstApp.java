package com.example.lrnt.controllers;

import com.example.lrnt.account.AccountVerifier;
import com.example.lrnt.config.JwtUtils;
import com.example.lrnt.database.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyFirstApp {

    private final UserRepository repository;
    private final JwtUtils jwtUtils;

    public MyFirstApp(UserRepository repository, JwtUtils jwtUtils) {
        this.repository = repository;
        this.jwtUtils = jwtUtils;
    }


    @RequestMapping("/")
    public String index(@CookieValue(name = "token", defaultValue = "") String token) {
        System.out.println(token);
        try {
            jwtUtils.getAuthenticationFromJwt(token).isAuthenticated();
            return "/auth/start";
        } catch (Exception e) {
            return "/default/start";
        }
    }

    @RequestMapping("/account")
    public String account(@CookieValue(name = "token", defaultValue = "") String token) {
        System.out.println(token);
        try {
            jwtUtils.getAuthenticationFromJwt(token).isAuthenticated();
            return "/auth/account";
        } catch (Exception e) {
            return "/default/account";
        }
    }

    @RequestMapping("/confirm")
    public String confirm(@RequestParam("id") String id) {
        if (AccountVerifier.verify(id, repository).equals("confirmed")) {
            return "/default/confirm";
        }
        return "Error";
    }
}
