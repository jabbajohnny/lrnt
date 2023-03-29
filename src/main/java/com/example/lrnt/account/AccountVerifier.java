package com.example.lrnt.account;


import com.example.lrnt.controllers.MyFirstApp;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

public class AccountVerifier extends Thread {
    String id;
    String email;

    private ApiValues apiValues;

    public AccountVerifier() {
        apiValues = new ApiValues();
    }

    @Override
    public void run() {
        try {
            sendConfirmationEmail();
            Thread.sleep(60000);

            String sql = "SELECT confirm " +
                    "FROM lrnt.users " +
                    "WHERE id=?";

            String removeSql = "DELETE " +
                    "FROM lrnt.users " +
                    "WHERE id=?";

            if (!Boolean.TRUE.equals(MyFirstApp.jdbcTemplate.queryForObject(sql, Boolean.class, id))) {
                MyFirstApp.jdbcTemplate.update(sql, removeSql);
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
