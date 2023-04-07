package com.example.lrnt.account;

import com.example.lrnt.database.UserRepository;

public class Account {
    public String username;
    public String email;

    private UserRepository repository;

    public Account(UserRepository repository) {
        this.repository = repository;
    }

    public String login(User user) {
        if(AccountVerifier.checkCredentials(user.email(), user.password(), repository)) {
            return ResponseStatus.LOGGED_IN.toString();
        }
        return ResponseStatus.INVALID.toString();
    }
}
