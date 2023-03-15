package com.example.lrnt.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class AccountApiController {

    @PostMapping("/api/register")
    public void registerUser(@RequestBody User user) {

        String count = "SELECT COUNT(*) AS count " +
                "FROM [lrnt].[dbo].[users]";

        String sql = "INSERT INTO [lrnt].[dbo].[users] " +
                "VALUES (?, ?, ?, ?, ?)";

        MyFirstApp.jdbcTemplate.update(sql, MyFirstApp.jdbcTemplate.queryForObject(count, Integer.class) + 1,
                user.name,
                user.email,
                BCrypt.hashpw(user.password, BCrypt.gensalt()),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );


    }

    public static class User {
        String name;
        String email;
        String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
