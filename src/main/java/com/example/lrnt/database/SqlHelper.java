package com.example.lrnt.database;

import com.example.lrnt.controllers.MyFirstApp;

public class SqlHelper {

    public static int count(String key, String type) {
        String countMail = "SELECT COUNT(1) " +
                "FROM lrnt.users " +
                "WHERE ?=?";

        String countUsername = "SELECT COUNT(1) " +
                "FROM lrnt.users " +
                "WHERE name=?";

        switch (type) {
            case "name" -> {
                return MyFirstApp.jdbcTemplate.queryForObject(countUsername, Integer.class, key);
            }
            case "mail" -> {
                return MyFirstApp.jdbcTemplate.queryForObject(countMail, Integer.class, key);
            }

        }

        return -1;
    }



}
