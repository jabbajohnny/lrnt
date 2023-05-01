package com.example.lrnt.controllers;

import com.example.lrnt.account.*;
import com.example.lrnt.config.JwtUtils;
import com.example.lrnt.config.TokenService;
import com.example.lrnt.database.DatabaseUser;
import com.example.lrnt.database.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApiController {

    final UserManager userManager;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;


    public AccountApiController(UserManager userManager, UserRepository repository, TokenService tokenService, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userManager = userManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
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

    @PostMapping("/api/token")
    @ResponseBody
    public ResponseEntity<JsonNode> generateToken(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(String.format("{\"token\": \"%s\"}", tokenService.generateToken(user.email(), user.password())));
        return ResponseEntity.ok(json);
    }

    @GetMapping("/api/getUserData")
    @ResponseBody
    public ResponseEntity<JsonNode> getUserData(@CookieValue(name = "token", defaultValue = "") String token) throws JsonProcessingException {
        DatabaseUser databaseUser = repository.findAllByEmail(jwtUtils.getEmail(token)).get(0);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(
                String.format("{\"username\": \"%s\", \"email\": \"%s\", \"join_date\": \"%s\"}",
                        databaseUser.getUsername(), databaseUser.getEmail(), databaseUser.getJoinDate()));
        return ResponseEntity.ok(json);
    }

}
