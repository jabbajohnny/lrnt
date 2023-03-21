package com.example.lrnt.database;

import com.example.lrnt.controllers.MyFirstApp;

public class SqlHelper {

    public static int count(String key, String type) {
        String countMail = "SELECT COUNT(1) " +
                "FROM lrnt.users " +
                "WHERE email=?";

        String countUsername = "SELECT COUNT(1) " +
                "FROM lrnt.users " +
                "WHERE name=?";

        switch (type) {
            case "name" -> {
                return MyFirstApp.jdbcTemplate.queryForObject(countUsername, Integer.class, key);
            }
            case "email" -> {
                return MyFirstApp.jdbcTemplate.queryForObject(countMail, Integer.class, key);
            }

        }

        return -1;
    }



}
