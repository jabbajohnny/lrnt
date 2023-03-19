package com.example.lrnt.account;

import com.example.lrnt.controllers.MyFirstApp;
import com.example.lrnt.database.SqlHelper;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.lrnt.account.ResponseStatus.*;

public record User(String username, String email, String password, String passwordRepeat) {
    public ResponseStatus validate() {

        if (SqlHelper.count(username, "name") != 0) {
            return USERNAME_ALREADY_USED;
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            return EMAIL_NOT_VALID;
        }

        if (SqlHelper.count(email, "email") != 0) {
            return EMAIL_ALREADY_USED;
        }

        if (!password.equals(passwordRepeat)) {
            return PASSWORDS_DONT_MATCH;
        }

        return VALID;
    }

    public ResponseEntity<String> register() {
        ResponseStatus status = validate();
        if (status != VALID) {
            return new ResponseEntity<>(status.toString(), HttpStatus.BAD_REQUEST);
        }

        String count = "SELECT COUNT(*) AS count " +
                "FROM lrnt.users";

        String sql = "INSERT INTO lrnt.users " +
                "VALUES (?, ?, ?, ?, ?)";

        MyFirstApp.jdbcTemplate.update(sql, MyFirstApp.jdbcTemplate.queryForObject(count, Integer.class) + 1,
                username(),
                email(),
                BCrypt.hashpw(password(), BCrypt.gensalt()),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        return new ResponseEntity<>(status.toString(), HttpStatus.ACCEPTED);
    }
}
