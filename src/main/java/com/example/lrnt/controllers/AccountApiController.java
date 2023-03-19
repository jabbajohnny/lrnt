package com.example.lrnt.controllers;

import com.example.lrnt.account.User;
import com.example.lrnt.database.SqlHelper;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.el.util.Validation;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class AccountApiController {

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return user.register();
    }

}
