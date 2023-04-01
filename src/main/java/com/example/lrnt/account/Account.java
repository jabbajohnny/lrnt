package com.example.lrnt.account;

public class Account {
    public String username;
    public String email;

    public String login(User user) {
        if(AccountVerifier.checkCredentials(user.email(), user.password())) {
            return ResponseStatus.LOGGED_IN.toString();
        }
        return ResponseStatus.INVALID.toString();
    }
}
