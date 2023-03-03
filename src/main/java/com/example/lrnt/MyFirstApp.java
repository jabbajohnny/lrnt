package com.example.lrnt;

import com.example.lrnt.content.LearningAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/post-asset")
    public void postAsset(@RequestParam("title") String title, @RequestParam("description") String description) {
        LearningAsset learningAsset = new LearningAsset(title,
                description,
                "no name",
                "random shit");
    }
}
