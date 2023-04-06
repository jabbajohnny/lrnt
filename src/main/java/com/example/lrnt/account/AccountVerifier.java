package com.example.lrnt.account;


import com.example.lrnt.controllers.MyFirstApp;
import com.example.lrnt.database.SqlHelper;
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

    public static String verify(String id) {
        String sqlUpdate = "UPDATE lrnt.users " +
                "SET confirmed=1 " +
                "WHERE id=?";

        MyFirstApp.jdbcTemplate.update(sqlUpdate, id);

        return "confirmed";
    }

    public static boolean checkCredentials(String email, String password) {
        if (SqlHelper.count(email, "email") == 0 ) return false;

        String sqlSelect = String.format("SELECT password " +
                "FROM lrnt.users " +
                "WHERE email='%s'", email);

        String passwordFromDatabase = MyFirstApp.jdbcTemplate.query(sqlSelect,
                (rs, rowNum) -> rs.getString("password")).get(0);

        return BCrypt.checkpw(password, passwordFromDatabase);
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
