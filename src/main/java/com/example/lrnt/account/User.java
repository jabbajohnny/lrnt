package com.example.lrnt.account;

import com.example.lrnt.database.SqlHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.example.lrnt.account.ResponseStatus.*;
import static com.example.lrnt.controllers.MyFirstApp.jdbcTemplate;

public record User(String username, String email, String password, String passwordRepeat) {
    public String validate() {

        if (SqlHelper.count(username, "name") != 0) {
            return USERNAME_ALREADY_USED.toString();
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            return EMAIL_NOT_VALID.toString();
        }

        if (SqlHelper.count(email, "email") != 0) {
            return EMAIL_ALREADY_USED.toString();
        }

        if (!password.equals(passwordRepeat)) {
            return PASSWORDS_DONT_MATCH.toString();
        }

        return VALID.toString();
    }

    public String register() {
        String status = validate();
        if (!status.equals(VALID.toString())) {
            return status;
        }

        String id = RandomStringUtils.randomAlphanumeric(7);

        String count = "SELECT COUNT(1) " +
                "FROM lrnt.users " +
                "WHERE id=?";

        while (jdbcTemplate.queryForObject(count, Integer.class, id) != 0) {
            id = RandomStringUtils.randomAlphanumeric(7);
        }

        String sql = "INSERT INTO lrnt.users " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, id,
                false,
                username(),
                email(),
                BCrypt.hashpw(password(), BCrypt.gensalt()),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        AccountVerifier accountVerifier = new AccountVerifier();
        accountVerifier.email = email;
        accountVerifier.id = id;
        accountVerifier.start();

        return status;
    }
}
