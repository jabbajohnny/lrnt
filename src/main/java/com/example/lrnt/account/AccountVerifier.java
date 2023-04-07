package com.example.lrnt.account;


import com.example.lrnt.database.UserRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AccountVerifier extends Thread {
    String id;
    String email;

    @Autowired
    private UserRepository repository;

    private ApiValues apiValues;

    public AccountVerifier() {
        apiValues = new ApiValues();
    }

    @Override
    public void run() {
        try {
            sendConfirmationEmail();
            Thread.sleep(6000000);

            if(repository.findAllById(id).get(0).confirmed == 0) {
                repository.deleteById(id);
            }

        } catch (InterruptedException | UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public static String verify(String id, UserRepository repository) {
        repository.confirmUser(id);
        return "confirmed";
    }

    public static boolean checkCredentials(String email, String password, UserRepository repository) {
        if (!repository.existsByEmail(email)) return false;

        return BCrypt.checkpw(password, repository.findAllByEmail(email).get(0).getPassword());
    }


    public void sendConfirmationEmail() throws UnirestException {
        String link = String.format(apiValues.url + "/confirm?id=%s", id);
        HttpResponse<String> response = Unirest.post("https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", apiValues.apiKey)
                .header("X-RapidAPI-Host", "rapidprod-sendgrid-v1.p.rapidapi.com")
                .body("{\r\n    \"personalizations\": [\r\n        {\r\n            \"to\": [\r\n                {\r\n                    \"email\": \"" + email + "\"\r\n                }\r\n            ],\r\n            \"subject\": \"Verify your account!\"\r\n        }\r\n    ],\r\n    \"from\": {\r\n        \"email\": \"confirm@lrnt.com\"\r\n    },\r\n    \"content\": [\r\n        {\r\n            \"type\": \"text/plain\",\r\n            \"value\": \"" + link + "\"\r\n        }\r\n    ]\r\n}")
                .asString();
    }
}
