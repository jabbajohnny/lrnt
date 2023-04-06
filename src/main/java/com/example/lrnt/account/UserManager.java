package com.example.lrnt.account;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.lrnt.account.ResponseStatus.*;

@Controller
public class UserManager {

    final UserRepository userRepository;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(User user) {
        String status = validate(user);
        if (!status.equals(VALID.toString())) {
            return status;
        }

        String key = RandomStringUtils.randomAlphanumeric(7);

        while(userRepository.existsById(key)) {
            key = RandomStringUtils.randomAlphanumeric(7);
        }

        DatabaseUser databaseUser = new DatabaseUser(key, 0,
                user.username(), user.email(),
                BCrypt.hashpw(user.password(), BCrypt.gensalt()),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        userRepository.save(databaseUser);

        AccountVerifier verifier = new AccountVerifier();
        verifier.email = user.email();
        verifier.id = key;
        verifier.start();

        return status;
    }

    public String validate(User user) {
        if (userRepository.existsByEmail(user.email())) {
            return EMAIL_ALREADY_USED.toString();
        }

        if (!EmailValidator.getInstance().isValid(user.email())) {
            return EMAIL_NOT_VALID.toString();
        }

        if (userRepository.existsByUsername(user.username())) {
            return USERNAME_ALREADY_USED.toString();
        }

        if (!user.password().equals(user.passwordRepeat())) {
            return PASSWORDS_DONT_MATCH.toString();
        }

        return VALID.toString();
    }
}
