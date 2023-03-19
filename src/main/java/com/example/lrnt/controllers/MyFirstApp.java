package com.example.lrnt.controllers;

import com.example.lrnt.content.LearningAsset;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
