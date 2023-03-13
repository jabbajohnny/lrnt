package com.example.lrnt;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountApiController {

    @PostMapping("/api/register")
    public void registerUser() {
        System.out.println("Registered");
    }
}
