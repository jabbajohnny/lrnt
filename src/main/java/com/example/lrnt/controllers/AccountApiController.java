package com.example.lrnt.controllers;

import com.example.lrnt.account.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApiController {

    final UserManager userManager;
    private final UserRepository repository;

    public AccountApiController(UserManager userManager, UserRepository repository) {
        this.userManager = userManager;
        this.repository = repository;
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<JsonNode> registerUser(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(String.format("{\"error\": \"%s\"}", userManager.registerUser(user)));
        return ResponseEntity.ok(json);
    }

    @PostMapping ("/api/login")
    @ResponseBody
    public ResponseEntity<JsonNode> login(@RequestBody User user) throws JsonProcessingException {
        Account account = new Account(repository);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(String.format("{\"result\": \"%s\"}", account.login(user)));
        return ResponseEntity.ok(json);
    }

}
