package com.example.lrnt.controllers;

import com.example.lrnt.account.AccountVerifier;
import com.example.lrnt.database.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyFirstApp {

    private final UserRepository repository;

    public MyFirstApp(UserRepository repository) {
        this.repository = repository;
    }


    @RequestMapping("/")
    public String index() {
        return "start";
    }

    @RequestMapping("/account")
    public String account() {
        return "account";
    }

    @RequestMapping("/confirm")
    public String confirm(@RequestParam("id") String id) {
        if (AccountVerifier.verify(id, repository).equals("confirmed")) {
            return "confirm";
        }
        return "Error";
    }
}
