package com.example.lrnt.controllers;

import com.example.lrnt.account.*;
import com.example.lrnt.config.TokenService;
import com.example.lrnt.database.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApiController {

    final UserManager userManager;
    private final UserRepository repository;
    private final TokenService tokenService;


    public AccountApiController(UserManager userManager, UserRepository repository, TokenService tokenService) {
        this.userManager = userManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/register")
    @ResponseBody
    @PreAuthorize("!hasRole('ROLE_USER')")
    public ResponseEntity<JsonNode> registerUser(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(String.format("{\"error\": \"%s\"}", userManager.registerUser(user)));
        return ResponseEntity.ok(json);
    }

    @PostMapping ("/api/login")
    @ResponseBody
    @PreAuthorize("!hasRole('ROLE_USER')")
    public ResponseEntity<JsonNode> login(@RequestBody User user) throws JsonProcessingException {
        Account account = new Account(repository);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(String.format("{\"result\": \"%s\"}", account.login(user)));
        return ResponseEntity.ok(json);
    }

    @PostMapping("/api/token")
    @ResponseBody
    @PreAuthorize("!hasRole('ROLE_USER')")
    public ResponseEntity<JsonNode> generateToken(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(String.format("{\"token\": \"%s\"}", tokenService.generateToken(user.email(), user.password())));
        return ResponseEntity.ok(json);
    }

}
