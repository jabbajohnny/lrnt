package com.example.lrnt.controllers;

import com.example.lrnt.account.AccountVerifier;
import com.example.lrnt.content.LearningAsset;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyFirstApp {

    public static JdbcTemplate jdbcTemplate;

    public MyFirstApp(JdbcTemplate jdbcTemplate) {
        MyFirstApp.jdbcTemplate = jdbcTemplate;
    }


    @RequestMapping("/")
    public String index() {
        return "start";
    }

    @RequestMapping("/account")
    public String account() {
        return "account";
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseBody postAsset(@ModelAttribute LearningAsset learningAsset) {
        System.out.println("SUccess!");

        return null;
    }

    @RequestMapping("/confirm")
    public String confirm(@RequestParam("id") String id) {
        if (AccountVerifier.verify(id).equals("confirmed")) {
            return "confirm";
        }
        return "Error";
    }
}
